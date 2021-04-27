<%-- 
    Document   : test
    Created on : 19 avr. 2021, 18:47:27
    Author     : frozy
--%>

<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="cul_de_chouette.pojo.Joueur"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulDeChouettePU");
    EntityManager em = emf.createEntityManager();

    Joueur joueur = em.find(Joueur.class, "Bonjour");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>Email : <%= joueur.getEmail() %></p>
        <p>Pseudo : <%= joueur.getPseudonyme() %></p>
    </body>
</html>
