/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import cul_de_chouette.pojo.Joueur;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityTransaction;
import org.json.JSONObject;

/**
 *
 * @author nbechtel
 */
@WebServlet(name = "InscriptionServlet", urlPatterns = {"/InscriptionServlet"})
public class InscriptionServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulDeChouettePU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction trans = em.getTransaction();
            
            try {
                trans.begin();
            
                Joueur joueur = new Joueur();
                joueur.setPseudonyme(request.getParameter("pseudonyme"));
                joueur.setEmail(request.getParameter("email"));
                joueur.setMotDePasse(request.getParameter("motDePasse"));
                joueur.setSexe(request.getParameter("sexe"));
                joueur.setVille(request.getParameter("ville"));
            
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

                //convert String to LocalDate
                LocalDate dateNaiss = LocalDate.parse(request.getParameter("dateNaissance"), formatter);
                joueur.setDateNaissance(dateNaiss);
            
                em.persist(joueur);
                trans.commit();
            } 
            catch(Exception ex) {
                if(trans != null) trans.rollback();
                
                response.setStatus(500);
                JSONObject error = new JSONObject();
                error.put("status", "500");
                error.put("error", "Erreur d'enregistrement en base !");
                out.print(error.toString());
            }
            
            response.setStatus(200);
            JSONObject message = new JSONObject();
            message.put("status", "200");
            message.put("message", "Bien enregistré !");
            out.print(message.toString());
            
            em.close();
        } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
