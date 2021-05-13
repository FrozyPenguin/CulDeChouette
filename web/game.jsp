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
        <h1>Hello World!</h1>
        
        <%
            String decodedGameString = "Not a leader !";
            String gameParam = request.getParameter("game");
            if(gameParam != null) {
                byte[] decodedGameBytes = Base64.getDecoder().decode(gameParam);
                decodedGameString = new String(decodedGameBytes);
            }
        %>
        
        <p>
            Parameters : <%= request.getParameterMap().toString() %>
            Game Info : <%= decodedGameString %>
        </p>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/js/iziToast.min.js"></script>
        <script src="assets/js/game.js" type="module"></script>
    </body>
</html>
