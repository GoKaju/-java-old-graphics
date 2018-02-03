/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.RolesJpaController;
import com.statics.dao.UsuariorolJpaController;
import com.statics.dao.UsuariosJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Fechas;
import com.statics.vo.Roles;
import com.statics.vo.Usuariorol;
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
@WebServlet(name = "Servlet_usuarios", urlPatterns = {"/Usuarios"})
public class Servlet_usuarios extends HttpServlet {

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
                String modulo = o.getvariable("modulo");

                RolesJpaController rdao = new RolesJpaController(emf);
                UsuariosJpaController udao = new UsuariosJpaController(emf);
                UsuariorolJpaController urdao = new UsuariorolJpaController(emf);
                

                if (modulo.equals("nuevo")) {
                    Usuarios u = new Usuarios();
                    u.setUsuaContrasena(o.getvariable("pass"));
                    u.setUsuaEstado("ACTIVO");
                    u.setUsuaFechacambio(Fechas.getFechaHoraTimeStamp());
                    u.setUsuaImg(o.getvariable("firma_txt"));
                    u.setUsuaMail(o.getvariable("email"));
                    u.setUsuaNombres(o.getvariable("Nombre"));
                    u.setUsuaRegistradopor(user.getUsuaId());
                    u.setUsuaTele(o.getvariable("tele"));
                    u.setUsuaUsuario(u.getUsuaMail());
                    udao.create(u);
                    
                    
                    String func[] = request.getParameterValues("funcheck[]");
                    if (func != null) {
                        for (String fid : func) {
                            Roles r = rdao.findRoles(Integer.parseInt(fid));
                            Usuariorol ur = new Usuariorol();
                            ur.setRoleId(r);
                            ur.setUsroFechacambio(Fechas.getFechaHoraTimeStamp());
                            ur.setUsroRegistradopor(user.getUsuaId());
                            ur.setUsuaId(u);
                          urdao.create(ur);

                        }
                    }

                    out.println("alerta('OK','¡Creado con exito!'); RecargaPanel('panels/usuarios.jsp?rfid=" + o.getvariable("rfid") + "','content');");

                } else if (modulo.equals("editar")) {

                } else if (modulo.equals("eliminar")) {
                    Usuarios u = udao.findUsuarios(Integer.parseInt(o.getvariable("uid")));
                    for (Usuariorol ur : u.getUsuariorolList()) {
                        urdao.destroy(ur.getUsroId());
                    }
                    udao.destroy(u.getUsuaId());
                    out.println("alerta('OK','¡Eliminado con exito!'); RecargaPanel('panels/usuarios.jsp?rfid=" + o.getvariable("rfid") + "','content');");

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
