/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.CampanasJpaController;
import com.statics.dao.EstacionesJpaController;
import com.statics.dao.PuntoMuestralJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Fechas;
import com.statics.vo.Campanas;
import com.statics.vo.Estaciones;
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
@WebServlet(name = "Servlet_campanas", urlPatterns = {"/Campanas"})
public class Servlet_campanas extends HttpServlet {

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
                CampanasJpaController ejc = new CampanasJpaController(emf);
                PuntoMuestralJpaController pmjc = new PuntoMuestralJpaController(emf);

                String modulo = o.getvariable("modulo");
                boolean exito = true;
                String errores = "";

                if (modulo.equals("1")) {

                    Campanas elem = new Campanas();

                    if (!o.getvariable("index").isEmpty()) {
                        elem = ejc.findCampanas(Integer.parseInt(o.getvariable("index")));
                    }
                    elem.setCampNombre(o.getvariable("Nombre"));
                    elem.setCampDescripcion(o.getvariable("descripcion"));
                    elem.setEstaId(1);
                    elem.setCampRegistradapor(user.getUsuaId());
                    elem.setCampFechacambio(Fechas.getFechaHoraTimeStamp());
                    elem.setGrupId(user.getGrupoUsuariosList().get(0).getGrupo());
                    if (exito) {
                        if (elem.getCampId()!= null) {
                            ejc.edit(elem);
                        } else {
                            ejc.create(elem);
                        }
                        session.setAttribute("campana", elem);
                    }

                    if (exito) {
                        out.println(" RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=" + o.getvariable("rfid") + "&index="+elem.getCampId()+"','content');");
                    } else {
                        out.println("alerta('Error','" + errores + "');");
                    }

                } else if (modulo.equals("2")) {
                    Campanas elem = ejc.findCampanas(Integer.parseInt(o.getvariable("index")));
                   elem.setEstaId(0);
                    elem.setCampRegistradapor(user.getUsuaId());
                    elem.setCampFechacambio(Fechas.getFechaHoraTimeStamp());
                   ejc.edit(elem);
                    out.println("alerta('OK','¡Eliminado con exito!'); RecargaPanel('panels/campanas/campanas.jsp?rfid=" + o.getvariable("rfid") + "','content');");


                } else if (modulo.equals("3")) {
//                    agregar punto muestral
                    Campanas campana = ejc.findCampanas(Integer.parseInt(o.getvariable("campa")));
                    System.out.println("campa --> "+campana.getCampNombre());
                
                    PuntoMuestral elem = new PuntoMuestral();

                    if (!o.getvariable("index").isEmpty()) {
                        elem = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("index")));
                    }
                    elem.setCampId(campana);
                    elem.setPumuNombre(o.getvariable("Nombre"));
                    elem.setPumuDescripcion(o.getvariable("descripcion"));
                    elem.setPumuLong(o.getvariable("Longitud"));
                    elem.setPumuLat(o.getvariable("Latitud"));
                    elem.setEstaId(new EstacionesJpaController(emf).findEstaciones(Integer.parseInt(o.getvariable("Estacion"))));
                    elem.setPumuRegistradopor(user.getUsuaId());
                    elem.setPumuFechacambio(Fechas.getFechaHoraTimeStamp());
                    if (exito) {
                        if (elem.getPumuId()!= null) {
                            pmjc.edit(elem);
                        } else {
                            pmjc.create(elem);
                        }
                      
                    }

                    if (exito) {
                        out.println("alerta('OK','¡Operacion exitosa!'); RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=" + o.getvariable("rfid") + "&index="+elem.getCampId().getCampId()+"  #pumuContent','pumuContainer','closeModal()');");
                    } else {
                        out.println("alerta('Error','" + errores + "');");
                    }
                
                } else if (modulo.equals("4")) {
                    try{
                    PuntoMuestral elem = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("index")));
                
                   ejc.destroy(elem.getPumuId());
              out.println("alerta('OK','¡Operacion exitosa!'); RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=" + o.getvariable("rfid") + "&index="+elem.getCampId().getCampId()+"  #pumuContent','pumuContainer');");

                    }catch(Exception ex){
                  out.println("alerta('ERROR','¡Este punto muestral ya posee datos por lo tanto no se puede remover!');");

                    }

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
