/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette;

import cul_de_chouette.pojo.Action;
import cul_de_chouette.pojo.ActionPK;
import cul_de_chouette.pojo.Interraction;
import cul_de_chouette.pojo.InterractionPK;
import cul_de_chouette.pojo.Joueur;
import cul_de_chouette.pojo.Partie;
import cul_de_chouette.pojo.Resultats;
import cul_de_chouette.pojo.ResultatsPK;
import cul_de_chouette.websocket.WSGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    private final HashMap<Integer, Joueur> users = new HashMap<>();
    private String id = null;
    private int reachPoint = 343;
    private int turn = 0;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulDeChouettePU");
    private final EntityManager em = emf.createEntityManager();
    private EntityTransaction trans;
    private Partie partie;
    private Collection<Action> actions;
    private Collection<Resultats> resultats;
    private double pendingPoints;
    private String pendingInteract = null;
    private HashSet<String> arrivalOrder = new HashSet<>();
    
    public Game(JSONObject gameInfo) throws GameException {
        this.id = gameInfo.optString("id");
        if(this.id == null) throw new GameException("Id vide !");
        
        if(gameInfo.optInt("reachPoint") > 0 && gameInfo.optInt("reachPoint") <= 343) {
            this.reachPoint = gameInfo.getInt("reachPoint");
        }
        
        if(gameInfo.optString("pseudo") == null || gameInfo.optJSONArray("users") == null || gameInfo.optJSONArray("users").isEmpty()) {
            throw new GameException("Information de partie invalide !");
        }
        
        this.users.put(1, this.em.find(Joueur.class, gameInfo.getString("pseudo")));
        
        JSONArray gameInfoUsers = gameInfo.getJSONArray("users");
        for(Object userObject : gameInfoUsers) {
            JSONObject user = (JSONObject) userObject;
            if(user.getString("pseudo") == null || user.getString("position") == null) {
                throw new GameException("Informations sur les joueurs invalide !");
            }
            
            this.users.put(user.getInt("position"), this.em.find(Joueur.class, user.getString("pseudo")));
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
            // TODO ajouter les joueurs
            
            em.persist(this.partie);
            em.flush();

            trans.commit();
        }
        catch(Exception ex) {
            if(trans != null) trans.rollback();
            this.broadcast(WSGame.createMessage("Erreur de création de partie", "error"), new String[0]);
            return;
        }
        
        this.users.entrySet().forEach(entry -> {
            int key = entry.getKey();
            Joueur value = entry.getValue();
            Resultats result = new Resultats();
            result.setJoueur(value);
            result.setOrdre(key);
            result.setPartie(this.partie);
            
            ResultatsPK resultatsPK = new ResultatsPK();
            resultatsPK.setIdPartie(this.partie.getIdPartie());
            resultatsPK.setPseudonyme(value.getPseudonyme());
            
            result.setResultatsPK(resultatsPK);
            
            this.resultats.add(result);
        });
        
        System.out.println("Début de la partie !");
        this.broadcast(WSGame.createMessage("empty", "START"), new String[0]);
        this.nextTurn();
    }
    
    public void endGame(Joueur gagnant) {
        // Faudra créer les resultats
        // Pour obtenir le nombre de chouette velu perdu il faut juste regarder dans les actions les effets négatif et compter le nombre d'apparition du joueur
        // Pour les suites c'est la même chose mais en comptant les effets positifs
        // Pour le score final, on additionne les scores du joueur à chaque tour
        
        // Enregistrement des resultats et des actions en base
        this.trans.begin();
        this.partie.setActionCollection(this.actions);
        this.partie.setResultatsCollection(this.resultats);
        this.trans.commit();
    }
    
    public void nextTurn() {
        // retourne le joueur qui doit jouer
        this.turn ++;
        
        this.broadcast(WSGame.createMessage(this.getUserTurn(), "TURN"), new String[0]);
    }
    
    public static Integer roll() {
        Random rand = new Random();
        return rand.nextInt((6 - 1) + 1) + 1;
    }
    
    public void executeTurn() {
        this.pendingPoints = 0;
        this.pendingInteract = null;
        this.arrivalOrder.clear();
        JSONObject dice = new JSONObject();
        
        JSONArray chouette = new JSONArray(new Integer[]{Game.roll(), Game.roll()});
        
        dice.put("chouette", chouette);
        dice.put("cul", Game.roll());
        dice.put("pseudo", this.getUserTurn());
        dice.put("position", this.getPosForUser(this.getUserTurn()));
        
        this.broadcast(WSGame.createMessage(dice, "ROLL"), new String[0]);
        
        this.processDice(dice);
    }
    
    public int getPosForUser(String pseudo) {
        return this.users.keySet()
                        .stream()
                        .filter(key -> em.find(Joueur.class, pseudo).equals(this.users.get(key)))
                        .findFirst().get();
    }
    
    private void processDice(JSONObject dice) {        
        int chouette1 = dice.getJSONArray("chouette").getInt(0);
        int chouette2 = dice.getJSONArray("chouette").getInt(1);
        int cul = dice.getInt("cul");
        
        Action action = new Action();
        action.setPartie(this.partie);
        action.setChouette1((short)chouette1);
        action.setChouette2((short)chouette2);
        action.setCul((short)cul);
        // Il manque les points peut etre non ?
        
        ActionPK pk = new ActionPK();
        pk.setIdPartie(this.partie.getIdPartie());
        pk.setTour(this.turn);
        
        action.setActionPK(pk);
        
        JSONObject result = new JSONObject();
        result.put("pseudo", dice.getString("pseudo"));
        result.put("position", dice.getInt("position"));
        
        if(cul == chouette1 + chouette2 && cul != chouette1) {
            result.put("action", "Velute");
            result.put("points", Math.pow(cul, 2));
        }
        else if(chouette1 == chouette2 && chouette2 != cul) {
            result.put("action", "Chouette");
            result.put("points", Math.pow(chouette1, 2));
        }
        else if(chouette1 == chouette2 && chouette2 == cul) {
            result.put("action", "Cul de chouette");
            result.put("points", 40 + chouette1 * 10);
        }
        else if(chouette1 == chouette2 && chouette1 + chouette2 == cul) {
            result.put("action", "Chouette velute");
            result.put("interact", "Pas mou le caillou !");
            this.pendingPoints = Math.pow(cul, 2);
            this.pendingInteract = "Chouette velute";
        }
        else {
            int resultArray[] = {chouette1, chouette2, cul};
            Arrays.sort(resultArray);
            
            if(resultArray[0] + 1 == resultArray[1] && resultArray[1] + 1 == resultArray[2]) {
                result.put("action", "Suite");
                result.put("interact", "Grelotte ça picote !");
                this.pendingPoints = -10;
                this.pendingInteract = "Suite";
            }
            
            result.put("points", 0);
        }
        
        if(!result.has("interact")) {
            Resultats resultatInCollection = this.resultats.stream().filter(resultat -> resultat.getJoueur().getPseudonyme().equals(dice.getString("pseudo"))).findFirst().get();
            resultatInCollection.setScore(resultatInCollection.getScore() + result.getInt("points"));
            result.put("points", resultatInCollection.getScore());
            
            if(!this.isOver()) this.nextTurn();
        }
        
        this.actions.add(action);
        
        this.broadcast(WSGame.createMessage(result, "RESULT"), new String[0]);
        
        this.isOver();
    }
    
    public void handleInteraction(String joueur) {
        // Gérer l'ordre d'arrivé et agir en conséquence
        this.arrivalOrder.add(joueur);
        
        if(this.arrivalOrder.size() < this.users.size()) return;
        
        // TODO: Construire l'interraction
        Interraction interraction = new Interraction();
        
        InterractionPK interractionPK = new InterractionPK();
        interractionPK.setIdPartie(this.partie.getIdPartie());
        interractionPK.setTour(this.turn);
        
        interraction.setInterractionPK(interractionPK);
        
        Joueur joueurAffecte;
        
        if(this.pendingInteract.equals("Suite")) {
            interraction.setEffet('N'); // Ou 'P' pour positif
            joueurAffecte = em.find(Joueur.class, this.arrivalOrder.toArray(new String[0])[this.arrivalOrder.size() - 1]);
        }
        else {
            interraction.setEffet('P');
            joueurAffecte = em.find(Joueur.class, this.arrivalOrder.toArray(new String[0])[0]);
        }
        
        if(joueurAffecte == null) {
            this.broadcast(WSGame.createMessage("Error lors de l'intéraction !", "error"), new String[0]);
            return;
        }
        
        interraction.setJoueurAffecte(joueurAffecte);
        
        Resultats resultatInCollection = this.resultats.stream().filter(resultat -> resultat.getJoueur().getPseudonyme().equals(joueurAffecte.getPseudonyme())).findFirst().get();
        resultatInCollection.setScore(resultatInCollection.getScore() + (int) this.pendingPoints);
        
        JSONObject interactObject = new JSONObject();
        interactObject.put("pseudo", joueurAffecte.getPseudonyme());
        interactObject.put("position", this.getPosForUser(joueurAffecte.getPseudonyme()));
        interactObject.put("points", resultatInCollection.getScore());
        
        this.broadcast(WSGame.createMessage(this.getPosForUser(joueurAffecte.getPseudonyme()), "INTERACT"), new String[0]);
        
        // Ajout de l'interraction à la dernière actions enregistré dans la partie
        this.actions.toArray(new Action[0])[this.actions.size() - 1].setInterraction(interraction);
        
        if(!this.isOver()) this.nextTurn();
    }
    
    public boolean isOver() {
        Joueur gagnant = this.resultats.stream().filter(result -> (result.getScore() >= this.reachPoint)).findFirst().get().getJoueur();
        if(gagnant != null) {
            this.endGame(gagnant);
            return true;
        }
        
        return false;
    }
    
    public void addUser(Session session) throws GameException {
        Joueur joueur = em.find(Joueur.class, session.getPathParameters().get("pseudo"));
        if(this.users.containsValue(joueur)) {
            this.listeWS.add(session);
            
            JSONArray connectedList = new JSONArray();
            this.listeWS.forEach(user -> {
                JSONObject userObject = new JSONObject();
                userObject.put("pseudo", user.getPathParameters().get("pseudo"));
                userObject.put("position", this.getPosForUser(user.getPathParameters().get("pseudo")));
                
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
                    .filter(key ->  em.find(Joueur.class, user.getPathParameters().get("pseudo")).equals(this.users.get(key)))
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
        return this.users.get(1).getPseudonyme();
    }
    
    public String getId() {
        return this.id;
    }
    
    public Session getUserSession(String pseudo) {
        return this.listeWS.stream().filter(ws -> pseudo.equals(ws.getPathParameters().get("pseudo"))).findFirst().orElse(null);
    }
    
    public void sendToUser(String pseudo, String message) {
        this.listeWS.forEach(ws -> {
            try {
                if(pseudo.equals(ws.getPathParameters().get("pseudo"))) {
                    ws.getBasicRemote().sendText(message);
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
        return this.users.size() > pos ? this.users.get(pos).getPseudonyme() : "";
    }
    
    public ArrayList<Session> getUsersWS() {
        return this.listeWS;
    }
    
    public String getUserTurn() {
        return this.getUserAtPos(((this.turn - 1) % this.users.size()) + 1);
    }
    
    public int getTurn() {
        return this.turn;
    }
    
    public int getReachPoint() {
        return this.reachPoint;
    }
}
