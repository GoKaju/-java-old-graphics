/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.UsuariosJpaController;
import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.util.Fechas;
import com.statics.vo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author D4V3
 */
@WebServlet(name = "Servlet_login", urlPatterns = {"/Login"})
public class Servlet_login extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
        HttpSession session = request.getSession(true);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
            UsuariosJpaController udao = new UsuariosJpaController(emf);
            System.out.println("pass "+ request.getParameter("pass"));
            
        EntityManager em = emf.createEntityManager();
        TypedQuery<Usuarios> consultaDocumento = em.createNamedQuery("Usuarios.findByUsuaMail", Usuarios.class);
        consultaDocumento.setParameter("usuaMail", request.getParameter("email"));
        List<Usuarios> lista = consultaDocumento.getResultList();
            Usuarios user = null; 
        for (Usuarios p : lista) {
            user = p;
        }
   if(user!=null && user.getUsuaId()!= null){
       System.out.println("entre ");
//       falta encriptar pass
       if(user.getUsuaContrasena().equals(request.getParameter("pass"))){
           if(!user.getUsuaEstado().equals("0")){
           if(!user.getGrupoUsuariosList().isEmpty()){
       user.setUsuaUltimoacceso(Fechas.getFechaHoraTimeStamp());
       user.setUsuaUltimaip(request.getParameter("ip_ocu") );
           try {
               udao.edit(user);
               
               session.setAttribute("usuarioVO", user);
               
            out.println("location.href='Inicio.jsp?r=0'");
           } catch (NonexistentEntityException ex) {
               Logger.getLogger(Servlet_login.class.getName()).log(Level.SEVERE, null, ex);
           } catch (Exception ex) {
               Logger.getLogger(Servlet_login.class.getName()).log(Level.SEVERE, null, ex);
           }
           }else{
            out.println("alerta('ERROR','¡Usuario no atado a un grupo!');");
           
           }}else{
            out.println("alerta('ERROR','¡Usuario inactivo!');");
           }
           
       
       }else{
            out.println("alerta('ERROR','¡Datos invalidos!');");
       
       }
   
   
   }else{
            out.println("alerta('ERROR','¡Datos invalidos!');");
   
   }

    
      
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
