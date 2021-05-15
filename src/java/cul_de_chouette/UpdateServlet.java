/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette;

import cul_de_chouette.pojo.Joueur;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author frozy
 */
@WebServlet(name = "updateServlet", urlPatterns = {"/updateServlet"})
public class UpdateServlet extends HttpServlet {

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
            EntityTransaction trans;

            trans = em.getTransaction();
            
            String pseudo = (String) session.getAttribute("pseudo");
            
            if(pseudo == null) {
                response.setStatus(500);
                JSONObject error = new JSONObject();
                error.put("status", "500");
                error.put("error", "Session invalide !");
                out.print(error.toString());
                return;
            }

            Joueur joueur = em.find(Joueur.class, pseudo);

            if(joueur == null) {
                response.setStatus(404);
                JSONObject error = new JSONObject();
                error.put("status", "500");
                error.put("error", "Le joueur n'existe pas !");
                out.print(error.toString());
                return;
            }
            
            String mail = request.getParameter("mail");
            String mdp = request.getParameter("motDePasse");
            String naiss = request.getParameter("naiss");
            String sexe = request.getParameter("sexe");
            String ville = request.getParameter("ville");
            
            if(
                mail == null || mdp == null || naiss == null || sexe == null || ville == null ||
                "".equals(mail) || "".equals(mdp) || "".equals(naiss) || "".equals(sexe) || "".equals(ville)
            ) {
                response.setStatus(500);
                JSONObject error = new JSONObject();
                error.put("status", "500");
                error.put("error", "Requete invalide !");
                out.print(error.toString());
                return;
            }

            try {
                trans.begin();

                joueur.setEmail(mail);
                joueur.setMotDePasse(mdp);
                joueur.setVille(ville);
                joueur.setSexe(sexe);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

                //convert String to LocalDate
                LocalDate dateNaiss = LocalDate.parse(naiss, formatter);
                joueur.setDateNaissance(dateNaiss);

                trans.commit();
            }
            catch(Exception ex) {
                if(trans != null) trans.rollback();
            }
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
