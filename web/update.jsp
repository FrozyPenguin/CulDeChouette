<%-- 
    Document   : update
    Created on : 15 mai 2021, 11:20:37
    Author     : frozy
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="cul_de_chouette.pojo.Joueur"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/css/iziToast.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Quicksand">
        <link rel="stylesheet" href="assets/css/OcOrato---Login-form.css">
        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body class="container-image">
        <%
            String pseudo = (String) session.getAttribute("pseudo");
            String pseudoReq = request.getParameter("pseudo");
            if(pseudo == null || pseudoReq == null || !pseudo.equals(pseudoReq)) {
        %>
            <div class="container box">
                <div class="alert alert-danger m-0" role="alert">
                    Vous n'êtes pas connecté !
                </div>
            </div>
        <%
            }
            else {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulDeChouettePU");
                EntityManager em = emf.createEntityManager();

                Joueur joueur = em.find(Joueur.class, pseudo);

                if(joueur == null) {  
                %>
                    <div class="container box">
                        <div class="alert alert-danger m-0" role="alert">
                            Vous n'êtes pas connecté !
                        </div>
                    </div>
                <%
                }
                else {
                    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String naiss = joueur.getDateNaissance().format(formatters);
        %>
            <div class="d-flex justify-content-center align-items-center container-center">
                <form class="box" id="form" style="font-family: Quicksand, sans-serif;background-color: rgba(44,40,52,0.73); height: fit-content;" >
                    <div id="errorBox" class="alert alert-danger" role="alert" style="display: none">
                    </div>
                    <h1 id="head" style="color:rgb(193,166,83);">Modification de <%=pseudo%></h1>
                    <div class="d-flex justify-content-center"><img class="rounded img-fluid image" id="image" style="width:auto;height:auto;" src="assets/img/logo.png"></div>
                    <div class="form-group"><input class="form-control formum" type="mail" id="mail" placeholder="Email" value="<%= joueur.getEmail() %>" required></div>
                    <div class="form-group"><input class="form-control formum" type="password" id="motDePasse" placeholder="Mot de passe"></div>
                    <div class="form-group"><input class="form-control formum" type="date" id="naiss" value="<%=naiss%>" placeholder="Date de naissance" required></div>
                    <div class="form-group">
                        <select class="form-select formum" id="sexe" required>
                            <option disabled hidden>Sexe</option>
                            <option value="H" <%=joueur.getSexe() == "H" ? "selected" : ""%>>Homme</option>
                            <option value="F" <%=joueur.getSexe() == "F" ? "selected" : ""%>>Femme</option>
                            <option value="A" <%=joueur.getSexe() == "A" ? "selected" : ""%>>Autre</option>
                          </select>
                    </div>
                    <div class="form-group"><input class="form-control formum" type="text" id="ville" placeholder="Ville" value="<%=joueur.getVille()%>" required></div>
                    <button class="btn btn-light butonas" style="width: 100%;height: 100%;margin-top: 10px; margin-bottom: 10px;background-color: rgb(221,172,24);color: rgb(255,255,255);font-size: 17px;"
                        type="submit">Modifier</button>
                    <a class="linkas" style="font-size: 17px;margin: auto;margin-left: 0;margin-right: 0;margin-bottom: 0;margin-top: 0;padding-left: 0;padding-right: 0;color: rgb(177,151,70);" href="javascript:history.back()">Annuler</a>
                </form>
            </div>
        <%
                }

                if(em != null) {
                    em.close();
                    em = null;
                }
            }
        %>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/js/iziToast.min.js"></script>
        <script src="assets/js/update.js"></script>
    </body>
</html>
