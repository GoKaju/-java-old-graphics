/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.FuncionalidadesJpaController;
import com.statics.dao.ParametroLabelsJpaController;
import com.statics.dao.ParametrosJpaController;
import com.statics.dao.RolesJpaController;
import com.statics.dao.RolfuncionalidadJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Fechas;
import com.statics.vo.Funcionalidades;
import com.statics.vo.ParametroLabels;
import com.statics.vo.Parametros;
import com.statics.vo.Roles;
import com.statics.vo.Rolfuncionalidad;
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
@WebServlet(name = "Servlet_contaminantes", urlPatterns = {"/Contaminantes"})
public class Servlet_contaminantes extends HttpServlet {

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
                ParametrosJpaController pjc = new ParametrosJpaController(emf);
                ParametroLabelsJpaController pljc = new ParametroLabelsJpaController(emf);

                String modulo = o.getvariable("modulo");
                boolean exito = true;
                String errores = "";

                if (modulo.equals("1")) {

                    Parametros elem = new Parametros();

                    if (!o.getvariable("index").isEmpty()) {
                        elem = pjc.findParametros(Integer.parseInt(o.getvariable("index")));
                    }
                    elem.setPareNombre(o.getvariable("Nombre"));
                    elem.setPareDescripcion(o.getvariable("descripcion"));
                    elem.setParaCodigo(Integer.parseInt(o.getvariable("Codigo")));
                    elem.setParaEstado(1);
                    elem.setPareRegistradopor(user.getUsuaId());
                    elem.setPareFechacambio(Fechas.getFechaHoraTimeStamp());
                    String[] labels = null;
                    if (!o.getvariable("labels").isEmpty()) {
                        labels = request.getParameterValues("labels");
                    } else {
                        exito = false;
                        errores = "Debe agregar minimo 1 label.";
                    }

                    if (exito) {

                        if (elem.getParaId() != null) {
                            pjc.edit(elem);

//                        borrar anteriores
                            for (ParametroLabels label : elem.getParametroLabelsList()) {
                                pljc.destroy(label.getPalaId());
                            }

                            for (String label : labels) {
                                ParametroLabels pl = new ParametroLabels();
                                pl.setParaId(elem);
                                pl.setPalaLabel(label);
                                pl.setPalaRegistradopor(user.getUsuaId());
                                pl.setPalaFechacambio(Fechas.getFechaHoraTimeStamp());
                                pljc.create(pl);
                            }

                        } else {
                            pjc.create(elem);

                            for (String label : labels) {
                                ParametroLabels pl = new ParametroLabels();
                                pl.setParaId(elem);
                                pl.setPalaLabel(label);
                                pl.setPalaRegistradopor(user.getUsuaId());
                                pl.setPalaFechacambio(Fechas.getFechaHoraTimeStamp());
                                pljc.create(pl);

                            }

                        }

                    }

                    if (exito) {
                        out.println("alerta('OK','¡Creado con exito!'); RecargaPanel('panels/contaminantes/contaminantes.jsp?rfid=" + o.getvariable("rfid") + "','content');");
                    } else {
                        out.println("alerta('Error','" + errores + "');");
                    }

                } else if (modulo.equals("2")) {
                    Parametros elem = pjc.findParametros(Integer.parseInt(o.getvariable("index")));
                    elem.setParaEstado(0);
                   elem.setPareFechacambio(Fechas.getFechaHoraTimeStamp());
                   elem.setPareRegistradopor(user.getUsuaId());
                   pjc.edit(elem);
                    out.println("alerta('OK','¡Eliminado con exito!'); RecargaPanel('panels/contaminantes/contaminantes.jsp?rfid=" + o.getvariable("rfid") + "','content');");


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
