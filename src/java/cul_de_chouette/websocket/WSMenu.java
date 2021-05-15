/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Frozy
 */
@ServerEndpoint("/menu/{pseudo}")
public class WSMenu {
    // la liste des websockets : en static pour être partagée
    private static final ArrayList<Session> listeWS = new ArrayList<>();
    
    @OnMessage
    public String onMessage(@PathParam("pseudo")String pseudo, String message) {
        System.out.println(pseudo);
        System.out.println(message);
        JSONObject object;
        JSONArray content;
        String reachPoint;
        String gameId;
        try {
            object = new JSONObject(message).getJSONObject("message");
            content = object.getJSONArray("users");
            reachPoint = object.getString("reachPoint");
        } catch (JSONException ex) {
            return WSMenu.createMessage(ex.toString(), "error");
        }
        
        // Génération d'un id preque unique
        gameId = Base64.getEncoder().encodeToString((object.toString() + pseudo + String.valueOf(Math.random())).getBytes());
        
        JSONObject sender = new JSONObject();
        sender.put("id", gameId);
        sender.put("pseudo", pseudo);
        sender.put("reachPoint", reachPoint);
        
        content.forEach(user -> {
            JSONObject contentUser = (JSONObject) user;
            sender.put("role", "player");
            sender.put("gameUrl", "game.jsp?pseudo=" + contentUser.getString("pseudo") + "&id=" + gameId);
            this.sendToUser(contentUser.get("pseudo").toString(), sender , "GAMEREQUEST");
        });
        
        sender.put("role", "leader");
        sender.put("users", content);        
        sender.put("gameUrl", "game.jsp?pseudo=" + pseudo + "&id=" + gameId + "&game=" + Base64.getEncoder().encodeToString(sender.toString().getBytes()));
        
        return WSMenu.createMessage(sender, "ACK");
    }
    
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println(session);
        // à l'ouverture d'une connexion, on rajoute la WS dans la liste
        JSONArray response = new JSONArray();
        WSMenu.listeWS.forEach(ws -> {
            response.put(ws.getPathParameters().get("pseudo"));
        });
        session.getBasicRemote().sendText(WSMenu.createMessage(response, "CONNECTEDPLAYERS"));
        
        WSMenu.listeWS.add(session);
        
        System.out.println("Utilisateur connecté : " + WSMenu.listeWS.size());
        this.broadcast(WSMenu.createMessage((String) session.getPathParameters().get("pseudo"), "ADDTOLIST"), new String[]{session.getPathParameters().get("pseudo")});
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Fermeture de la WS");
        System.out.println(reason);
        
        WSMenu.listeWS.remove(session);
        
        System.out.println("Utilisateur connecté : " + WSMenu.listeWS.size());
        
        this.broadcast(WSMenu.createMessage((String) session.getPathParameters().get("pseudo"), "REMOVEFROMLIST"));
    }
    
    @OnError
    public void onError(Throwable t) {
        System.err.println("Erreur WS : " + t);
    }
    
    public void broadcast(String message, String[] ignoresPlayer) {
        List<String> ignores = Arrays.asList(ignoresPlayer);
        // on parcourt toutes les WS pour leur envoyer une à une le message
        WSMenu.listeWS.forEach(ws -> {
            if(!ignores.contains(ws.getPathParameters().get("pseudo"))) {
                try {
                    ws.getBasicRemote().sendText(message);
                }
                catch (IOException ex) {
                    System.err.println("Erreur de communication");
                }
            }
        });
    }
    
    public void broadcast(String message) {
        this.broadcast(message, new String[0]);
    }
    
    public void sendToUser(String user, Object message, String type) {
        WSMenu.listeWS.forEach(ws -> {
            try {
                if(user.equals(ws.getPathParameters().get("pseudo"))) {
                    ws.getBasicRemote().sendText(WSMenu.createMessage(message, type));
                }
            }
            catch (IOException ex) {
                System.err.println("Erreur de communication");
            }
        });
    }
    
    private static String createMessage(Object message, String type) {
        JSONObject response = new JSONObject();
        response.put(type, message);
        return response.toString();
    }
}
