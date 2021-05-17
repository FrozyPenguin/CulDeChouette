<%-- 
    Document   : game
    Created on : 8 mai 2021, 02:25:26
    Author     : frozy
--%>
<%@page import="java.util.Arrays"%>
<%@page import="cul_de_chouette.pojo.Interraction"%>
<%@page import="cul_de_chouette.pojo.Action"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="cul_de_chouette.pojo.Resultats"%>
<%@page import="cul_de_chouette.pojo.Partie"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="cul_de_chouette.pojo.Joueur"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/css/iziToast.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Quicksand">
        <link rel="stylesheet" href="assets/css/OcOrato---Login-form.css">
        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body class="container-image" style="padding-top: 20px;">        
        <div class="container box">
            <%
                String pseudo = (String) session.getAttribute("pseudo");
                if(pseudo == null) {
            %>
                <div class="alert alert-danger" role="alert" id="alert">
                    Vous n'êtes pas connecté !
                </div>
            <%
                }
                else {
            %>
                <div class="row">
                    <div class="col">
                        <div class="accordion bg-dark" id="accordionPlayer">
                        <%
                            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulDeChouettePU");
                            EntityManager em = emf.createEntityManager();

                            TypedQuery<Joueur> query = em.createNamedQuery("Joueur.findAll", Joueur.class);
                            List<Joueur> joueurs = query.getResultList();

                            for(Joueur joueur : joueurs) {
                        %>
                            <div class="accordion-item playerInfo">
                                <h2 class="accordion-header" id="collapse-<%= joueur.getPseudonyme() %>-heading">
                                    <button class="accordion-button collapsed bg-dark" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-<%= joueur.getPseudonyme() %>" aria-expanded="false" aria-controls="collapse-<%= joueur.getPseudonyme() %>">
                                        <%= joueur.getPseudonyme() %>
                                    </button>
                                </h2>  
                                <div id="collapse-<%= joueur.getPseudonyme() %>" class="accordion-collapse collapse" aria-labelledby="collapse-<%= joueur.getPseudonyme() %>-heading" data-bs-parent="#accordionPlayer">
                                    <div class="accordion-body bg-dark">
                                        <p>Pseudo: <%= joueur.getPseudonyme() %></p>
                                        <p>Age: <%= joueur.getAge() %></p>
                                        <p>Date de naissance: <%= joueur.getDateNaissance() %></p>
                                        <p>Mail: <%= joueur.getEmail() %></p>
                                        <p>Sexe: <%= joueur.getSexe() %></p>
                                        <p>Ville: <%= joueur.getVille() %></p>
                                        <p>Partie(s) jouée(s): <%= joueur.getNbPartiesJouee() %></p>
                                        <p>Partie(s) gagnée(s): <%= joueur.getNbPartiesGagnee() %></p>
                                        <p>Chouette(s) velute(s) perdue(s): <%= joueur.getMoyChouetteVeluesPerdu() %></p>
                                        <p>Suite(s) gagnée(s): <%= joueur.getMoySuiteGagnee() %></p>
                                        <p>Score moyen: <%= joueur.getMoyScore() %></p>
                                        <%
                                            if(!joueur.getResultatsCollection().isEmpty()) {
                                        %>
                                            <p>Parties jouées par l'utilisateur :</p>
                                            <div class="accordion bg-dark" id="accordionPartie-<%= joueur.getPseudonyme() %>">
                                            <%
                                                Collection<Resultats> results = joueur.getResultatsCollection();
                                                List<Partie> parties = new ArrayList<>();
                                                for(Resultats result : results) {
                                                    parties.add(result.getPartie());
                                                }
                                                for(Partie partie : parties) {
                                                    
                                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");  
                                                
                                            %>

                                                <div class="accordion-item partiesInfo">
                                                    <h2 class="accordion-header" id="collapse-<%= joueur.getPseudonyme() %>-heading-<%= partie.getIdPartie() %>">
                                                        <button class="accordion-button collapsed bg-dark" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>" aria-expanded="false" aria-controls="collapse-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>">
                                                            <%= partie.getIdPartie() + " [" + dateFormat.format(partie.getDateDebut()) + "]" %>
                                                        </button>
                                                    </h2>  
                                                    <div id="collapse-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>" class="accordion-collapse collapse" aria-labelledby="collapse-<%= joueur.getPseudonyme() %>-heading-<%= partie.getIdPartie() %>" data-bs-parent="#accordionPartie-<%= joueur.getPseudonyme() %>">
                                                        <div class="accordion-body bg-dark">
                                                            <p>ID: <%= partie.getIdPartie() %></p>
                                                            <p>Début de la partie: <%= dateFormat.format(partie.getDateDebut()) %></p>
                                                            <% if(partie.getDuree() != null) {%>
                                                                <p>Durée de la partie: <%= partie.getDuree()/60 %> minutes</p>
                                                            <% } %>
                                                            <p>Hôte de la partie: <%= partie.getHote().getPseudonyme() %></p>
                                                            <p>Points de fin de partie: <%= partie.getObjectif() %></p>
                                                            <% if(partie.getVainqueur() != null) {%>
                                                                <p>Vainqueur de la partie: <%= partie.getVainqueur().getPseudonyme() %></p>
                                                            <% } %>
                                                            <%
                                                                if(!partie.getActionCollection().isEmpty()) {
                                                            %>
                                                                <p>Actions lors de la partie :</p>
                                                                <div class="accordion bg-dark" id="accordionActions-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>">
                                                                <%
                                                                    for(Action action : partie.getActionCollection()) {

                                                                %>

                                                                    <div class="accordion-item partiesInfo">
                                                                        <h2 class="accordion-header" id="collapse-<%= joueur.getPseudonyme() %>-heading-<%= partie.getIdPartie() %>-<%= action.getActionPK().getTour() %>">
                                                                            <button class="accordion-button collapsed bg-dark" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>-<%= action.getActionPK().getTour() %>" aria-expanded="false" aria-controls="collapse-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>-<%= action.getActionPK().getTour() %>">
                                                                                Tour <%= action.getActionPK().getTour() %>
                                                                            </button>
                                                                        </h2>  
                                                                        <div id="collapse-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>-<%= action.getActionPK().getTour() %>" class="accordion-collapse collapse" aria-labelledby="collapse-<%= joueur.getPseudonyme() %>-heading-<%= partie.getIdPartie() %>-<%= action.getActionPK().getTour() %>" data-bs-parent="#accordionActions-<%= joueur.getPseudonyme() %>-<%= partie.getIdPartie() %>">
                                                                            <div class="accordion-body bg-dark">
                                                                                <p>Tour: <%= action.getActionPK().getTour() %></p>
                                                                                <p>Chouette 1: <%= action.getChouette1() %></p>
                                                                                <p>Chouette 2: <%= action.getChouette2() %></p>
                                                                                <p>Cul: <%= action.getCul() %></p>
                                                                                <p>Tour de: <%= action.getJoueurCourant().getPseudonyme() %></p>
                                                                                <%
                                                                                    if(action.getCul() == action.getChouette1() + action.getChouette2() && action.getCul() != action.getChouette1()) {
                                                                                        out.println("<p>Résultat: Velute</p>");
                                                                                        out.println("<p>Le joueur " + action.getJoueurCourant().getPseudonyme() + " a remporté: " + Math.pow(action.getCul(), 2) + " points</p>");
                                                                                    }
                                                                                    else if(action.getChouette1() == action.getChouette2() && action.getChouette2() != action.getCul()) {
                                                                                        out.println("<p>Résultat: Chouette</p>");
                                                                                        out.println("<p>Le joueur " + action.getJoueurCourant().getPseudonyme() + " a remporté: " + Math.pow(action.getChouette1(), 2) + " points</p>");
                                                                                    }
                                                                                    else if(action.getChouette1() == action.getChouette2() && action.getChouette2() == action.getCul()) {
                                                                                        out.println("<p>Résultat: Cul de chouette</p>");
                                                                                        out.println("<p>Le joueur " + action.getJoueurCourant().getPseudonyme() + " a remporté: " + (40 + action.getChouette1() * 10) + " points</p>");
                                                                                    }
                                                                                    else if(action.getChouette1() == action.getChouette2() && action.getChouette1() + action.getChouette2() == action.getCul()) {
                                                                                        out.println("<p>Résultat: Chouette velute</p>");
                                                                                        if(action.getInterraction() != null) {
                                                                                            out.println("<p>Le joueur " + action.getInterraction().getJoueurAffecte().getPseudonyme() + " a remporté: " + Math.pow(action.getCul(), 2) + " points</p>");
                                                                                        }                                                                                            
                                                                                    }
                                                                                    else {
                                                                                        int resultArray[] = {action.getChouette1(), action.getChouette2(), action.getCul()};
                                                                                        Arrays.sort(resultArray);

                                                                                        if(resultArray[0] + 1 == resultArray[1] && resultArray[1] + 1 == resultArray[2]) {
                                                                                            out.println("<p>Résultat: Suite</p>");
                                                                                            if(action.getInterraction() != null) {
                                                                                                out.println("<p>Le joueur " + action.getInterraction().getJoueurAffecte().getPseudonyme() + " a perdu: 10 points</p>");
                                                                                            }
                                                                                        }
                                                                                        else {
                                                                                            out.println("<p>Résultat: Néant</p>");
                                                                                            out.println("<p>Le joueur " + action.getJoueurCourant().getPseudonyme() + " n'a rien gagné</p>");
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                <%
                                                                    }
                                                                %>
                                                                </div>
                                                            <%
                                                                }
                                                            %>
                                                        </div>
                                                    </div>
                                                </div>
                                            <%
                                                }
                                            %>
                                            </div>
                                        <%
                                            }
                                        %>
                                    </div>
                                </div>
                            </div>
                        <%
                            }
                        %>
                        </div>
                    </div>
                </div>
            <%
                }
            %>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/js/iziToast.min.js"></script>
    </body>
</html>
