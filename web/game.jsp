<%-- 
    Document   : game
    Created on : 8 mai 2021, 02:25:26
    Author     : frozy
--%>
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
                String pseudoReq = request.getParameter("pseudo");
                if(pseudo == null || pseudoReq == null || !pseudo.equals(pseudoReq)) {
            %>
                <div class="alert alert-danger" role="alert" id="alert">
                    Vous n'êtes pas connecté !
                </div>
            <%
                }
                else {
            %>
                <div class="row">
                    <div class="col-9" id="actions">

                    </div>
                    <div class="col-3">
                        <div id="onlinePlayersContainer">
                            <p>Points à atteindre : <span id="reachPoint">343</span></p>
                            <p>Joueurs en ligne : <span id="actualPlayers">0</span>/<span id="totalPlayers">0</span></p>
                            <ol class="list">

                            </ol>
                        </div>
                    </div>

                    <div id="loader">
                    <%
                        String gameParam = request.getParameter("game");
                        if(gameParam != null) {
                    %>
                        <button class="btn btn-primary text-center w-100" id="launchGame" disabled>Lancer la partie</button>
                    <%
                        }
                    %>
                    </div>

                    <div class="d-flex justify-content-start" id="controls">
                        <button class="btn btn-light butonas" type="button" id="lancerDes" style="width: 20%; height: 40%; margin-bottom: 10px; margin-right: 10px;  background-color: rgb(221,172,24); color: rgb(255,255,255); font-size: 17px;" disabled>Lancer les dés</button>
                        <button class="btn btn-light butonas" type="button" id="suite" style="width: 20%; height: 40%; margin-bottom: 10px; margin-right: 10px;  background-color: rgb(221,172,24); color: rgb(255,255,255); font-size: 17px;" disabled>"Grelotte ça picote !"</button>
                        <button class="btn btn-light butonas" type="button" id="chouetteVelue" style="width: 20%; height: 40%; margin-bottom: 10px; margin-right: 10px;  background-color: rgb(221,172,24); color: rgb(255,255,255); font-size: 17px;" disabled>"Pas mou le caillou !"</button>
                    </div>

                </div>
            <%
                }
            %>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/js/iziToast.min.js"></script>
        <script src="assets/js/game.js" type="module"></script>
    </body>
</html>
