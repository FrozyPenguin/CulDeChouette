/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette;

import cul_de_chouette.pojo.Action;
import cul_de_chouette.pojo.Joueur;
import cul_de_chouette.pojo.Partie;
import cul_de_chouette.pojo.Resultats;
import cul_de_chouette.websocket.WSGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Gestion du déroulement d'une partie
 * @author frozy
 */
public class Game {
    private final ArrayList<Session> listeWS = new ArrayList<>();
    // On associe une position à un pseudo
    private final HashMap<Integer, String> users = new HashMap<>();
    private String id = null;
    private int reachPoint = 343;
    private int turn = 0;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulDeChouettePU");
    private final EntityManager em = emf.createEntityManager();
    private EntityTransaction trans;
    private Partie partie;
    private Collection<Action> actions;
    private Collection<Resultats> resultats;
    
    public Game(JSONObject gameInfo) throws GameException {
        this.id = gameInfo.optString("id");
        if(this.id == null) throw new GameException("Id vide !");
        
        if(gameInfo.optInt("reachPoint") > 0 && gameInfo.optInt("reachPoint") <= 343) {
            this.reachPoint = gameInfo.getInt("reachPoint");
        }
        
        if(gameInfo.optString("pseudo") == null || gameInfo.optJSONArray("users") == null || gameInfo.optJSONArray("users").isEmpty()) {
            throw new GameException("Information de partie invalide !");
        }
        
        this.users.put(1, gameInfo.getString("pseudo"));
        
        JSONArray gameInfoUsers = gameInfo.getJSONArray("users");
        for(Object userObject : gameInfoUsers) {
            JSONObject user = (JSONObject) userObject;
            if(user.getString("pseudo") == null || user.getString("position") == null) {
                throw new GameException("Informations sur les joueurs invalide !");
            }
            
            this.users.put(user.getInt("position"), user.getString("pseudo"));
        }
        
        // Affiche ce que contient la hashmap
        this.users.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }
    
    public void startGame() {
        // Créer la game en base
        // A chaque tour, on enregistre les résultats de dès des joueurs en base
        // Comme ça à la fin d'une partie, on a tous enregistré et on a juste à élire le vainqueur
        // Faudra voir par rapport à la base qu'on a
                
        try {
            this.trans = this.em.getTransaction();

            trans.begin();
            
            this.partie = new Partie();
            Joueur hote = this.em.find(Joueur.class, this.getLeader());
            if(hote == null) throw new Exception("Leader introuvable !");
            this.partie.setHote(hote);
            this.partie.setDateDebut(new Date());
            this.partie.setObjectif(this.reachPoint);
            
            em.persist(this.partie);
            em.flush();

            trans.commit();
        }
        catch(Exception ex) {
            if(trans != null) trans.rollback();
            this.broadcast(WSGame.createMessage("Erreur de création de partie", "error"), new String[0]);
            return;
        }
        
        System.out.println("Début de la partie !");
        this.broadcast(WSGame.createMessage("empty", "START"), new String[0]);
    }
    
    public String nextTurn() {
        this.turn ++;
        // retourne le joueur qui doit jouer
        return "";
    }
    
    public static Integer roll() {
        Random rand = new Random();
        return rand.nextInt((6 - 1) + 1) + 1;
    }
    
    public void addUser(Session session) throws GameException {
        if(this.users.containsValue(session.getPathParameters().get("pseudo"))) {
            this.listeWS.add(session);
            
            JSONArray connectedList = new JSONArray();
            this.listeWS.forEach(user -> {
                JSONObject userObject = new JSONObject();
                userObject.put("pseudo", user.getPathParameters().get("pseudo"));
                userObject.put("position", 
                    this.users.keySet()
                        .stream()
                        .filter(key -> user.getPathParameters().get("pseudo").equals(this.users.get(key)))
                        .findFirst().get()
                );
                connectedList.put(userObject);
            });
            
            JSONObject connectedObject = new JSONObject();
            connectedObject.put("connected", connectedList);
            connectedObject.put("total", this.users.size());
            
            this.broadcast(WSGame.createMessage(connectedObject, "CONNECTED"), new String[0]);
        }
        else {
            throw new GameException("L'utilisateur n'est pas autorisé à rejoindre la salle de jeu.");
        }
    }
    
    public void removeUser(Session session, CloseReason reason) throws IOException {
        this.closeUserConnection(session, reason);
        this.listeWS.remove(session);
        
        JSONArray connectedList = new JSONArray();
        this.listeWS.forEach(user -> {
            JSONObject userObject = new JSONObject();
            userObject.put("pseudo", user.getPathParameters().get("pseudo"));
            userObject.put("position", 
                this.users.keySet()
                    .stream()
                    .filter(key -> user.getPathParameters().get("pseudo").equals(this.users.get(key)))
                    .findFirst().get()
            );
            connectedList.put(userObject);
        });

        JSONObject connectedObject = new JSONObject();
        connectedObject.put("connected", connectedList);
        connectedObject.put("total", this.users.size());
        this.broadcast(WSGame.createMessage(connectedObject, "DISCONNECTED"), new String[0]);
    }
    
    public void closeUserConnection(Session session, CloseReason reason) throws IOException {
        if(session.isOpen()) session.close(reason);
    }
    
    public void removeAllUsers(CloseReason reason) {
        while(this.listeWS.size() > 0) {
            try {
                this.listeWS.get(0).close(reason);
            } catch (IOException ex) {
                System.out.println("Erreur de la fermeture de la session !");
            }
        }
        
        this.listeWS.clear();
    }
    
    public String getLeader() {
        return this.users.get(1);
    }
    
    public String getId() {
        return this.id;
    }
    
    public Session getUserSession(String pseudo) {
        return this.listeWS.stream().filter(ws -> pseudo.equals(ws.getPathParameters().get("pseudo"))).findFirst().orElse(null);
    }
    
    public void sendToUser(String pseudo, Object message, String type) {
        this.listeWS.forEach(ws -> {
            try {
                if(pseudo.equals(ws.getPathParameters().get("pseudo"))) {
                    ws.getBasicRemote().sendText(WSGame.createMessage(message, type));
                }
            }
            catch (IOException ex) {
                System.err.println("Erreur de communication");
            }
        });
    }
    
    public void broadcast(String message, String[] ignoresPlayer) {
        List<String> ignores = Arrays.asList(ignoresPlayer);
        // on parcourt toutes les WS pour leur envoyer une à une le message
        this.listeWS.forEach(ws -> {
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
    
    public String getUserAtPos(int pos) {
        return this.users.size() > pos ? this.users.get(pos) : "";
    }
    
    public ArrayList<Session> getUsersWS() {
        return this.listeWS;
    }
    
    public int getReachPoint() {
        return this.reachPoint;
    }
}
