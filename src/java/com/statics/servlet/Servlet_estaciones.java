/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.EstacionesJpaController;
import com.statics.dao.EstadosJpaController;
import com.statics.dao.FormatofechasJpaController;
import com.statics.dao.ParametroLabelsJpaController;
import com.statics.dao.ParametrosJpaController;
import com.statics.dao.SeparadoresJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Fechas;
import com.statics.vo.Estaciones;
import com.statics.vo.Estados;
import com.statics.vo.ParametroLabels;
import com.statics.vo.Parametros;
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
@WebServlet(name = "Servlet_estaciones", urlPatterns = {"/Estaciones"})
public class Servlet_estaciones extends HttpServlet {

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
                EstacionesJpaController ejc = new EstacionesJpaController(emf);

                String modulo = o.getvariable("modulo");
                boolean exito = true;
                String errores = "";

                if (modulo.equals("1")) {

                    Estaciones elem = new Estaciones();

                    if (!o.getvariable("index").isEmpty()) {
                        elem = ejc.findEstaciones(Integer.parseInt(o.getvariable("index")));
                    }
                    elem.setEstaNombre(o.getvariable("Nombre"));
                    elem.setSepaId(new SeparadoresJpaController(emf).findSeparadores(Integer.parseInt(o.getvariable("separador"))));
                    elem.setFofeId(new FormatofechasJpaController(emf).findFormatofechas(Integer.parseInt(o.getvariable("formatofecha"))));
                    elem.setEstaIdestado(new EstadosJpaController(emf).findEstados(1));
                    elem.setEstaRutacargaservidor(o.getvariable("rutaserver"));
                    elem.setEstaCarpetacarga(o.getvariable("carpetaserver"));
                    elem.setEstaRegistradopor(user.getUsuaId());
                    elem.setEstaFechacambio(Fechas.getFechaHoraTimeStamp());
                    elem.setGrupId(user.getGrupoUsuariosList().get(0).getGrupo());
                
              

                    if (exito) {

                        if (elem.getEstaId()!= null) {
                            ejc.edit(elem);


                        } else {
                            ejc.create(elem);


                        }

                    }

                    if (exito) {
                        out.println("alerta('OK','¡Creado con exito!'); RecargaPanel('panels/estaciones/estaciones.jsp?rfid=" + o.getvariable("rfid") + "','content');");
                    } else {
                        out.println("alerta('Error','" + errores + "');");
                    }

                } else if (modulo.equals("2")) {
                    Estaciones elem = ejc.findEstaciones(Integer.parseInt(o.getvariable("index")));
                   elem.setEstaIdestado(new EstadosJpaController(emf).findEstados(2));
                    elem.setEstaRegistradopor(user.getUsuaId());
                    elem.setEstaFechacambio(Fechas.getFechaHoraTimeStamp());
                   ejc.edit(elem);
                    out.println("alerta('OK','¡Eliminado con exito!'); RecargaPanel('panels/estaciones/estaciones.jsp?rfid=" + o.getvariable("rfid") + "','content');");


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
