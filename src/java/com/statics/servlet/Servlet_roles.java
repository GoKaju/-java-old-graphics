/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.FuncionalidadesJpaController;
import com.statics.dao.RolesJpaController;
import com.statics.dao.RolfuncionalidadJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Fechas;
import com.statics.vo.Funcionalidades;
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
@WebServlet(name = "Servlet_roles", urlPatterns = {"/Roles"})
public class Servlet_roles extends HttpServlet {

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
            RolesJpaController rdao = new RolesJpaController(emf);
            FuncionalidadesJpaController fdao = new FuncionalidadesJpaController(emf);
            RolfuncionalidadJpaController rfdao = new RolfuncionalidadJpaController(emf);
String modulo = o.getvariable("modulo");

            if(modulo.equals("nuevo")){
                Roles rol = new Roles();
                rol.setRoleDescripcion(o.getvariable("Nombre"));
                rol.setRoleEstado("ACTIVO");
                rol.setRoleFechacambio(Fechas.getFechaHoraTimeStamp());
                rol.setRoleregistradopor(user.getUsuaId());
                rdao.create(rol);
                 String func[] = request.getParameterValues("funcheck[]");
                 if(func!=null){
                     for(String fid: func){
                 Funcionalidades f = fdao.findFuncionalidades(Integer.parseInt(fid));
                 Rolfuncionalidad rf = new Rolfuncionalidad();
                   rf.setFuncId(f);
                   rf.setRofuFechacambio(Fechas.getFechaHoraTimeStamp());
                   rf.setRofuOperacion("VAME");
                   rf.setRoleRegistradopor(user.getUsuaId());
                   rf.setRoleId(rol);
                   rfdao.create(rf);
                   
                     }
                 }
            
            out.println("alerta('OK','¡Creado con exito!'); RecargaPanel('panels/roles.jsp?rfid="+o.getvariable("rfid")+"','content');");
            
            }else
            if(modulo.equals("editar")){
                
                
                
            }else
            if(modulo.equals("eliminar")){
                Roles r = rdao.findRoles(Integer.parseInt(o.getvariable("rid")));
                for (Rolfuncionalidad rf:r.getRolfuncionalidadList()){
                rfdao.destroy(rf.getRofuId());
                }
                rdao.destroy(r.getRoleId());
            out.println("alerta('OK','¡Eliminado con exito!'); RecargaPanel('panels/roles.jsp?rfid="+o.getvariable("rfid")+"','content');");
                
            }else{
            out.println("alerta('ERROR','¡Modulo no encontrado!');");
            }

   
           
  
        }else{
   out.println("location.href='logout.jsp'");
        }
        }catch(Exception ex){ex.printStackTrace();}
         finally {
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
