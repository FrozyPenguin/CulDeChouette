<%-- 
    Document   : game
    Created on : 8 mai 2021, 02:25:26
    Author     : frozy
--%>

<%@page import="java.util.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/css/iziToast.min.css"/>
        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body>        
        <div class="container">
            <div class="row">
                <div class="col-9" id="actions">
                    
                </div>
                <div class="col-3">
                    <div id="onlinePlayersContainer">
                        <p>Point à atteindre : <span id="reachPoint">343</span></p>
                        <p>Joueur en ligne : <span id="actualPlayers">0</span>/<span id="totalPlayers">0</span></p>
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
            </div>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/js/iziToast.min.js"></script>
        <script src="assets/js/game.js" type="module"></script>
    </body>
</html>
