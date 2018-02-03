/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.CampanasJpaController;
import com.statics.dao.PuntoMuestralJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Fechas;
import com.statics.vo.Campanas;
import com.statics.vo.Cargas;
import com.statics.vo.Parametros;
import com.statics.vo.PuntoMuestral;
import com.statics.vo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
@WebServlet(name = "Servlet_cargas", urlPatterns = {"/Cargas"})
public class Servlet_cargas extends HttpServlet {

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
            Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
            if (user != null && user.getUsuaId() != null) {
                Cadenas o = new Cadenas(request);
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
                CampanasJpaController cjc = new CampanasJpaController(emf);
         

                String modulo = o.getvariable("modulo");
                boolean exito = true;
                String errores = "";

                if (modulo.equals("1")) {
                    Cargas elem = new Cargas();
                    
                    elem.setPumuId(new PuntoMuestralJpaController(emf).findPuntoMuestral(Integer.parseInt(o.getvariable("pumu_sel"))));
                    elem.setCargDescripcion(o.getvariable("Descripcion"));
                    elem.setCargObservaciones(o.getvariable("Observaciones"));
                    
                    
                    session.setAttribute("carga_manual", elem);
                        out.println(" RecargaPanel('panels/datos/datos_nueva_carga_arch.jsp?rfid=" + o.getvariable("rfid") + "','content');");

         

                } else if (modulo.equals("2")) {
                    Campanas elem = cjc.findCampanas(Integer.parseInt(o.getvariable("index")));
                 String cadena = "";
                 
                    for (PuntoMuestral pm : elem.getPuntoMuestralList()) {
                        cadena += "<option value='"+pm.getPumuId()+"' >"+pm.getPumuNombre()+"</option>";

                    }
                 
//                 cadena = elem.getPuntoMuestralList().stream().map((puntoMuestral) -> <).reduce(cadena, String::concat);
                    System.out.println("cadena-->"+cadena);
                    out.println(" $('#pumu_sel').html(\""+cadena+"\");");


                } else {
                    out.println("alerta('ERROR','¡Modulo no encontrado!');");
                }

            } else {
                out.println("location.href='logout.jsp'");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
