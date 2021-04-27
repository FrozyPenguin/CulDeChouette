/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.websocket;

import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// https://www.jmdoudoux.fr/java/dej/chap-api_websocket.htm
// https://www.baeldung.com/java-websockets

/**
 *
 * @author frozy
 */
@ServerEndpoint("/test")
public class WSTest {

    // la liste des websockets : en static pour être partagée
    private static final ArrayList<Session> listeWS = new ArrayList<>();
    
    @OnMessage
    public String onMessage(String message) {
        System.out.println(message);
        
        String response = "Bien reçu";
        return response;
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session);
        // à l'ouverture d'une connexion, on rajoute la WS dans la liste
        WSTest.listeWS.add(session);
        
        System.out.println("Utilisateur connecté : " + WSTest.listeWS.size());
        
        this.broadcast("compteur:" + Integer.toString(WSTest.listeWS.size()));
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Fermeture de la WS");
        System.out.println(reason);
        
        WSTest.listeWS.remove(session);
        
        System.out.println("Utilisateur connecté : " + WSTest.listeWS.size());
        
        this.broadcast("compteur:" + Integer.toString(WSTest.listeWS.size()));
    }
    
    @OnError
    public void onError(Throwable t) {
        System.err.println("Erreur WS : " + t);
    }
    
    public void broadcast(String message) {
        // on parcourt toutes les WS pour leur envoyer une à une le message
        WSTest.listeWS.forEach(ws -> {
            try {
                ws.getBasicRemote().sendText(message);
            }
            catch (IOException ex) {
                System.err.println("Erreur de communication");
            }
        });
    }
}
