/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.websocket;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Frozy
 */
@ServerEndpoint("/menu/{pseudo}")
public class WSMenu {
    // la liste des websockets : en static pour être partagée
    private static final ArrayList<Session> listeWS = new ArrayList<>();
    JSONParser parser = new JSONParser();
    
    @OnMessage
    public String onMessage(@PathParam("pseudo")String pseudo, String message) {
        System.out.println(pseudo);
        System.out.println(message);
        JSONObject object;
        JSONArray content;
        String gameId;
        try {
            object = (JSONObject) this.parser.parse(message);
            content = (JSONArray) object.get("message");
        } catch (ParseException ex) {
            return WSMenu.createMessage(ex.toString(), "error");
        }
        
        MessageDigest md;
        
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return WSMenu.createMessage("Erreur de génération d'id de partie !", "error");
        }
        
        // Génération d'un id preque unique
        md.update((object.toJSONString() + pseudo + String.valueOf(Math.random())).getBytes()); 
        byte[] digest = md.digest();
        gameId = DatatypeConverter.printHexBinary(digest).toUpperCase();
        
        content.forEach(user -> {
            JSONObject contentUser = (JSONObject) user;
            JSONObject sender = new JSONObject();
            sender.put("id", gameId);
            sender.put("pseudo", pseudo);
            this.sendToUser(contentUser.get("pseudo").toString(), sender , "GAMEREQUEST");
        });
        
        return WSMenu.createMessage(gameId, "ACK");
    }
    
    @OnOpen
    public void onOpen(Session session) throws IOException {
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
        return response.toJSONString();
    }
}
