/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.websocket;

import cul_de_chouette.Game;
import cul_de_chouette.GameException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author frozy
 */
@ServerEndpoint("/game/{pseudo}/{id}")
public class WSGame {
    // la liste des websockets : en static pour être partagée
    // Faire une hashMap qui associe un Identifiant de channel à une ArrayList de session utilisateur
    private static final HashMap<String, Game> listeRoom = new HashMap<>();
    
    @OnMessage
    public String onMessage(@PathParam("pseudo") String pseudo, @PathParam("id") String channel, String message) {
        if(null != message) switch (message) {
            case "start":
                WSGame.listeRoom.get(channel).startGame();
                break;
            case "ROLL":
                WSGame.listeRoom.get(channel).executeTurn();
                break;
            case "startCountdown":
                WSGame.listeRoom.get(channel).broadcast("STARTCOUNTDOWN", new String[0]);
                break;
            default:
                break;
        }
        return WSGame.createMessage("Ok", "ACK");
    }
    
    @OnOpen
    public void onOpen(@PathParam("id") String channel, Session session) throws IOException {
        // Création d'une game si on est le leader sinon tenter de la rejoindre
        
        String gameHash = session.getPathParameters().get("game");
        
        String pseudo = session.getPathParameters().get("pseudo");
        String id = session.getPathParameters().get("id");
        
        // On est leader
        if(gameHash != null) {
            JSONObject gameInfo = null;
            try {
                gameInfo = new JSONObject(new String(Base64.getDecoder().decode(gameHash)));
            } catch (JSONException ex) {
                session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, "Info de la partie invalide !"));
            }
            
            if(null != gameInfo) {
                String jsonLeader = gameInfo.getString("pseudo");
                String jsonId = gameInfo.getString("id");
                
                if((pseudo == null ? jsonLeader == null : pseudo.equals(jsonLeader)) && (id == null ? jsonId == null : id.equals(jsonId))) {
                    // on créer la game
                    Game game;
                    try {
                        game = new Game(gameInfo);
                        game.addUser(session);
                        game.sendToUser(pseudo, WSGame.createMessage(game.getReachPoint(), "REACH"));
                    }
                    catch(GameException ex) {
                        session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, "Id invalide !"));
                        return;
                    }
                    
                    WSGame.listeRoom.put(id, game);
                }
                else {
                    // On se fait passer pour le leader mais on ne l'est pas
                    session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, "Vous n'êtes pas leader !"));
                }
            }
            
        }
        // On est un simple joueur
        else {
            try {
                Game game = WSGame.listeRoom.get(id);
                game.addUser(session);
                game.sendToUser(pseudo, WSGame.createMessage(game.getReachPoint(), "REACH"));
            } catch (GameException|NullPointerException ex) {
                System.out.println(ex);
                session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, "Vous n'êtes pas autorisé à rejoindre la partie !"));
            }
        }
    }
    
    @OnClose
    public void onClose(@PathParam("id") String channel, Session session, CloseReason reason) throws IOException {
        // Si plus personne est dans le channel ou si le leader est partie alors on détruit la game
        
        // Si le leader se deconnecte alors on déconnecte tout le monde
        // A voir si on laisse comme ça ou si on choisit une autre stratégie
        
        Game game;
        try {
            game = WSGame.listeRoom.get(channel);
        }
        catch(NullPointerException ex) {
            if(session.isOpen())
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Une erreur est survenu !"));
            return;
        }
        
        try {
            game.removeUser(session, null);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        System.out.println(game.getLeader());
        
        if(session.getPathParameters().get("pseudo") == null ? game.getLeader() == null : session.getPathParameters().get("pseudo").equals(game.getLeader())) {
            game.removeAllUsers(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Le leader est partie !"));
        }
        
        System.out.println(game.getUsersWS().size());
        if(game.getUsersWS().size() <= 0) {
            WSGame.listeRoom.remove(channel);
        }
    }
    
    @OnError
    public void onError(@PathParam("id") String channel, Session session, Throwable t) throws IOException {
        System.err.println("Erreur WS : " + t);
        System.err.println(t);
        try {
            WSGame.listeRoom.get(channel).removeUser(session, new CloseReason(CloseCodes.NORMAL_CLOSURE, "Une erreur est survenu !"));
        }
        catch(NullPointerException ex) {
            if(session.isOpen())
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Une erreur est survenu !"));
        }
    }
    
    public void broadcast(String channel, String message, String[] ignoresPlayer) {
        WSGame.listeRoom.get(channel).broadcast(message, ignoresPlayer);
    }
    
    public void broadcast(String channel, String message) {
        WSGame.listeRoom.get(channel).broadcast(message, new String[0]);
    }
    
    public void sendToUser(String channel, String user, String message) {
        WSGame.listeRoom.get(channel).sendToUser(user, message);
    }
    
    public static String createMessage(Object message, String type) {
        JSONObject response = new JSONObject();
        response.put(type, message);
        return response.toString();
    }
}
