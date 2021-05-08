/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author frozy
 */
@ServerEndpoint("/game/{pseudo}/{id}")
public class WSGame {
    // la liste des websockets : en static pour être partagée
    // Faire une hashMap qui associe un Identifiant de channel à une ArrayList de session utilisateur
    private static final ArrayList<Session> listeWS = new ArrayList<>();
    JSONParser parser = new JSONParser();
    
    @OnMessage
    public String onMessage(@PathParam("pseudo") String pseudo, @PathParam("id") String channel, String message) {
        System.out.println(pseudo);
        System.out.println(message);
        JSONObject object;
        JSONArray content;
        try {
            object = (JSONObject) this.parser.parse(message);
            content = (JSONArray) object.get("message");
        } catch (ParseException ex) {
            return WSGame.createMessage(ex.toString(), "error");
        }
        
        return WSGame.createMessage("Nothing", "ACK");
    }
    
    @OnOpen
    public void onOpen(@PathParam("id") String channel, Session session) throws IOException {
        /*
        System.out.println(session);
        // à l'ouverture d'une connexion, on rajoute la WS dans la liste
        JSONArray response = new JSONArray();
        WSMenu.listeWS.forEach(ws -> {
            response.add(ws.getPathParameters().get("pseudo"));
        });
        session.getBasicRemote().sendText(WSMenu.createMessage(response, "CONNECTEDPLAYERS"));
        
        WSMenu.listeWS.add(session);
        
        System.out.println("Utilisateur connecté : " + WSMenu.listeWS.size());
        this.broadcast(WSMenu.createMessage((String) session.getPathParameters().get("pseudo"), "ADDTOLIST"), new String[]{session.getPathParameters().get("pseudo")});
        */
    }
    
    @OnClose
    public void onClose(@PathParam("id") String channel, Session session, CloseReason reason) {
        /*
        System.out.println("Fermeture de la WS");
        System.out.println(reason);
        
        WSMenu.listeWS.remove(session);
        
        System.out.println("Utilisateur connecté : " + WSMenu.listeWS.size());
        
        this.broadcast(WSMenu.createMessage((String) session.getPathParameters().get("pseudo"), "REMOVEFROMLIST"));
        */
    }
    
    @OnError
    public void onError(Throwable t) {
        System.err.println("Erreur WS : " + t);
    }
    
    // TODO: A modifier pour prendre en compte le channel
    public void broadcast(@PathParam("id") String channel, String message, String[] ignoresPlayer) {
        List<String> ignores = Arrays.asList(ignoresPlayer);
        // on parcourt toutes les WS pour leur envoyer une à une le message
        WSGame.listeWS.forEach(ws -> {
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
    
    public void broadcast(@PathParam("id") String channel, String message) {
        this.broadcast(channel, message, new String[0]);
    }
    
    // TODO: A modifier pour prendre en compte le channel
    public void sendToUser(@PathParam("id") String channel, String user, String message, String type) {
        WSGame.listeWS.forEach(ws -> {
            try {
                ws.getBasicRemote().sendText(WSGame.createMessage(message, type));
            }
            catch (IOException ex) {
                System.err.println("Erreur de communication");
            }
        });
    }
    
    private static String createMessage(Object message, String type) {
        JSONObject response = new JSONObject();
        response.put(type, message);
        return response.toJSONString();
    }
}
