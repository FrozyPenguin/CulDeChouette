/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette;

import cul_de_chouette.pojo.Joueur;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author frozy
 */
@WebServlet(name = "ConnectionServlet", urlPatterns = {"/ConnectionServlet"})
public class ConnectionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        response.setContentType("application/json;charset=UTF-8");
        
        try(PrintWriter out = response.getWriter()) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulDeChouettePU");
            EntityManager em = emf.createEntityManager();
            
            String pseudonyme = request.getParameter("pseudonyme");
            String mdp = request.getParameter("motDePasse");
            
            if(
                mdp == null || pseudonyme == null ||
                "".equals(mdp) || "".equals(pseudonyme)
            ) {
                response.setStatus(500);
                JSONObject error = new JSONObject();
                error.put("status", "500");
                error.put("error", "Requete invalide !");
                out.print(error.toString());
                return;
            }

            try {

                Joueur joueur = em.find(Joueur.class, pseudonyme);
                
                if(joueur == null || !joueur.getPseudonyme().equals(pseudonyme)) {
                    response.setStatus(404);
                    JSONObject error = new JSONObject();
                    error.put("status", "404");
                    error.put("error", "Pseudonyme invalide !");
                    out.print(error.toString());
                    return;
                }
                
                if(!joueur.getMotDePasse().equals(mdp)) {
                    response.setStatus(401);
                    JSONObject error = new JSONObject();
                    error.put("status", "401");
                    error.put("error", "Mot de passe invalide !");
                    out.print(error.toString());
                    return;
                }
            }
            catch(JSONException ex) {                
                response.setStatus(500);
                JSONObject error = new JSONObject();
                error.put("status", "500");
                error.put("error", "Erreur de connexion !");
                out.print(error.toString());
                return;
            }

            session.removeAttribute("pseudo");
            session.setAttribute("pseudo", pseudonyme);
            
            response.setStatus(200);
            JSONObject message = new JSONObject();
            message.put("status", "200");
            message.put("message", "Connecté !");
            out.print(message.toString());
            
            em.close();
        }
        catch(Exception ex) {
            PrintWriter out = response.getWriter();
            response.setStatus(500);
            JSONObject error = new JSONObject();
            error.put("status", "500");
            error.put("error", "Erreur de connexion !");
            out.print(error.toString());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
