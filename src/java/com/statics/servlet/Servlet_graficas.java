/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

//import com.statics.dao.ArchivosJpaController;
import com.statics.carga.DataJson;
import com.statics.dao.CampanasJpaController;
import com.statics.dao.ParametrosJpaController;
import com.statics.dao.PuntoMuestralJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Constantes;
import com.statics.util.Fechas;
import com.statics.vo.Campanas;
import com.statics.vo.CargaParametro;
import com.statics.vo.Cargas;
import com.statics.vo.Datos;
import com.statics.vo.Parametros;
import com.statics.vo.PuntoMuestral;
//import com.statics.vo.Archivos;
//import com.statics.vo.Grafica;
import com.statics.vo.Usuarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
@WebServlet(name = "Servlet_graficas", urlPatterns = {"/Graficas"})
public class Servlet_graficas extends HttpServlet {

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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
        EntityManager em = emf.createEntityManager();
        try {
            HttpSession session = request.getSession(true);
            Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
            if (user != null && user.getUsuaId() != null) {
                Cadenas o = new Cadenas(request);

                CampanasJpaController cjc = new CampanasJpaController(emf);
                PuntoMuestralJpaController pmjc = new PuntoMuestralJpaController(emf);
                String modulo = o.getvariable("modulo");

                if (modulo.equals("1")) {

                    Campanas elem = cjc.findCampanas(Integer.parseInt(o.getvariable("index")));
                    String cadena = "<option value='' >Seleccione...</option>";

                    for (PuntoMuestral pm : elem.getPuntoMuestralList()) {
                        cadena += "<option value='" + pm.getPumuId() + "' >" + pm.getPumuNombre() + "</option>";

                    }

//                 cadena = elem.getPuntoMuestralList().stream().map((puntoMuestral) -> <).reduce(cadena, String::concat);
                    System.out.println("cadena-->" + cadena);
                    out.println(" $('#pumu_sel').html(\"" + cadena + "\"); $('#carg_sel').html('')");

                } else if (modulo.equals("2")) {

                    PuntoMuestral elem = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("index")));
                    String cadena = "";
                    List<Cargas> listCargas = elem.getCargasList();
                    Collections.reverse(listCargas);
                    for (Cargas pm : listCargas) {
                        cadena += "<option value='" + pm.getCargId() + "' >" + pm.getCargArchivo() + "</option>";

                    }

//                 cadena = elem.getPuntoMuestralList().stream().map((puntoMuestral) -> <).reduce(cadena, String::concat);
                    System.out.println("cadena-->" + cadena);
                    out.println(" $('#carg_sel').html(\"" + cadena + "\");");

                } else if (modulo.equals("3")) {

                    String tipo = o.getvariable("tipoGrafica");

                    switch (tipo) {
                        case "1":
                            DataJson datos = new DataJson();
                            datos.setNombreGraphic("Nombre grafica");
                            datos.setTipo("Timeseries");
                            datos.setDatos(new ArrayList());

                            String cargas[] = request.getParameterValues("carg_sel");
                            List<Integer> cargasList = new ArrayList();
                            for (int i = 0; i < cargas.length; i++) {
                                cargasList.add(Integer.parseInt(cargas[i]));
                            }

                            if (cargas != null) {

                                TypedQuery<Parametros> consulta = em.createNamedQuery("Parametros.findByCargaParametro", Parametros.class);
                                consulta.setParameter("Cargas", cargasList);
                                consulta.setParameter("tipo", Constantes.TIPO_GRAFICA_LINEA);
                                List<Parametros> lista = consulta.getResultList();

                                String Param[] = request.getParameterValues("para_sel");

                                Iterator<Parametros> it = lista.iterator();

                                while (it.hasNext()) {
                                    Parametros parametros = it.next();
                                    boolean esta = false;
                                    for (String string : Param) {

                                        if (string.equals(parametros.getParaId().toString())) {
                                            esta = true;
                                        }
                                    }
                                    if (!esta) {
                                        it.remove();
                                    }

                                }

                                int con = 0;
                                for (Parametros parametro : lista) {

                                    TypedQuery<CargaParametro> cons = em.createNamedQuery("CargaParametroGrafica1", CargaParametro.class);
                                    cons.setParameter("cargas", cargasList);
                                    cons.setParameter("para", parametro);
                                    List<CargaParametro> lis = cons.getResultList();
                                    System.out.println("--> " + lis.size());
                                    DataJson.DataUnit dat = datos.new DataUnit();
                                    dat.setX("x" + con);
                                    dat.setLabel(parametro.getPareNombre());
                                    dat.setFechas(new ArrayList());
                                    dat.setDatos(new ArrayList());

                                    for (CargaParametro li : lis) {
                                        for (Datos d : li.getDatosList()) {
                                            dat.getFechas().add(Fechas.DevuelveFormato(d.getDatoFecha(), "yyyy-MM-dd HH:mm"));
                                            dat.getDatos().add(d.getDatoData());

                                        }

                                    }
                                    datos.getDatos().add(dat);
                                    con++;
                                }
                                String nombre = "grap" + Fechas.getCadena();
                                session.setAttribute(nombre, datos);

                                out.println(" crearGrafica('" + nombre + "');");

                            }
                            break;
                        case "2":
                            if (o.getvariable("excel").equals("")) {
                                PuntoMuestral pumu = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("pumu_sel")));

                                datos = new DataJson();

                                String fechaini = o.getvariable("fini_txt");
                                String fechafin = o.getvariable("ffin_txt");

                                datos.setNombreGraphic(pumu.getPumuNombre() + " del " + fechaini + " al " + fechafin);
                                datos.setTipo("Timeseries");
                                datos.setDatos(new ArrayList());
                                String Param[] = request.getParameterValues("para_sel");
                                int con = 0;
                                for (String p : Param) {
                                    Parametros parametro = new ParametrosJpaController(emf).findParametros(Integer.parseInt(p));

                                    Query cons = em.createNativeQuery("SELECT\n"
                                            + "d.*\n"
                                            + "FROM\n"
                                            + "Datos as d\n"
                                            + "INNER JOIN carga_parametro as c ON d.papu_id=c.capa_id\n"
                                            + "INNER JOIN cargas ca ON ca.carg_id=c.carg_id\n"
                                            + "WHERE\n"
                                            + "DATE_FORMAT(d.dato_fecha, \"%Y-%m-%d\") BETWEEN ? AND ? \n"
                                            + "AND c.para_id=?\n"
                                            + "AND ca.pumu_id=?", Datos.class);
                                    cons.setParameter(1, fechaini);
                                    cons.setParameter(2, fechafin);
                                    cons.setParameter(3, parametro.getParaId());
                                    cons.setParameter(4, pumu.getPumuId());
                                    List<Datos> lis = cons.getResultList();

                                    System.out.println("--> " + lis.size());
                                    DataJson.DataUnit dat = datos.new DataUnit();
                                    dat.setX("x" + con);
                                    dat.setLabel(parametro.getPareNombre());
                                    dat.setFechas(new ArrayList());
                                    dat.setDatos(new ArrayList());

                                    for (Datos d : lis) {
                                        dat.getFechas().add(Fechas.DevuelveFormato(d.getDatoFecha(), "yyyy-MM-dd HH:mm"));
                                        dat.getDatos().add(d.getDatoData());

                                    }

                                    datos.getDatos().add(dat);
                                    con++;
                                }
                                String nombre = "grap" + Fechas.getCadena();
                                session.setAttribute(nombre, datos);

                                out.println(" crearGrafica('" + nombre + "');");
                            } else {
                                // descarga de excel 
                                PuntoMuestral pumu = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("pumu_sel")));

                                datos = new DataJson();

                                String fechaini = o.getvariable("fini_txt");
                                String fechafin = o.getvariable("ffin_txt");
                                datos.setNombreGraphic(pumu.getPumuNombre() + " del " + fechaini + " al " + fechafin);

                                String Param[] = request.getParameterValues("para_sel");
                                if (Param != null && Param.length > 0) {
                                    Query cons = em.createNativeQuery("SELECT\n"
                                            + "d.dato_fecha,\n"
                                            + "group_concat(concat(c.para_id,'#'),d.dato_data SEPARATOR ';')\n"
                                            + "FROM\n"
                                            + "Datos as d\n"
                                            + "INNER JOIN carga_parametro as c ON d.papu_id=c.capa_id\n"
                                            + "INNER JOIN cargas ca ON ca.carg_id=c.carg_id\n"
                                            + "WHERE\n"
                                            + " DATE_FORMAT(d.dato_fecha, \"%Y-%m-%d\") BETWEEN ? AND ?\n"
                                            + " AND\n"
                                            + " ca.pumu_id=?\n"
                                            + "GROUP BY\n"
                                            + "d.dato_fecha\n"
                                            + "ORDER BY\n"
                                            + "d.dato_fecha ASC");

                                    cons.setParameter(1, fechaini);
                                    cons.setParameter(2, fechafin);
                                    cons.setParameter(3, pumu.getPumuId());
                                    List<Object[]> lis = cons.getResultList();

                                    for (Object[] a : lis) {
                                        System.out.println("DATO EXCEL --> "
                                                + a[0]
                                                + " "
                                                + a[1]);
                                    }

                                    String nombre = "tabla" + Fechas.getCadena();
                                    String paramList = "paramList" + Fechas.getCadena();
                                    session.setAttribute(paramList, Param);
                                    session.setAttribute(nombre, lis);

                                    out.println(" verDatosTabla('" + nombre + "','" + paramList + "','" + datos.getNombreGraphic() + "');");
                                } else {
                                    out.println("alerta(\"ERROR\",\"¡Seleccione algun parametro!\");");

                                }
                            }
                            break;
                        default:
                            throw new AssertionError();
                    }

                } else if (modulo.equals("4")) {

//                    cargar punto muestral
                    PuntoMuestral pumu = new PuntoMuestralJpaController(emf).findPuntoMuestral(Integer.parseInt(o.getvariable("cod")));

                    

                    
                    String tipo = o.getvariable("tipoGrafica");
                    DataJson datos = new DataJson();
                    datos.setNombreGraphic("Nombre grafica");
                    datos.setTipo("Timeseries");
                    datos.setDatos(new ArrayList());

                    List<Integer> cargasList = new ArrayList();
                   
                    if(pumu.getCargasList()!=null &&!pumu.getCargasList().isEmpty()){
                    int ultima = pumu.getCargasList().get( pumu.getCargasList().size()-1).getCargId();
                    cargasList.add(ultima);
                    }
                 
                    if (!cargasList.isEmpty() ) {

                        TypedQuery<Parametros> consulta = em.createNamedQuery("Parametros.findByCargaParametro", Parametros.class);
                        consulta.setParameter("Cargas", cargasList);
                        consulta.setParameter("tipo", Constantes.TIPO_GRAFICA_LINEA);
                        List<Parametros> lista = consulta.getResultList();


                        int con = 0;
                        for (Parametros parametro : lista) {
                            TypedQuery<CargaParametro> cons = em.createNamedQuery("CargaParametroGrafica1", CargaParametro.class);
                            cons.setParameter("cargas", cargasList);
                            cons.setParameter("para", parametro);
                            List<CargaParametro> lis = cons.getResultList();
                            System.out.println("--> " + lis.size());
                            DataJson.DataUnit dat = datos.new DataUnit();
                            dat.setX("x" + con);
                            dat.setLabel(parametro.getPareNombre());
                            dat.setFechas(new ArrayList());
                            dat.setDatos(new ArrayList());

                            for (CargaParametro li : lis) {
                                for (Datos d : li.getDatosList()) {
                                    dat.getFechas().add(Fechas.DevuelveFormato(d.getDatoFecha(), "yyyy-MM-dd HH:mm"));
                                    dat.getDatos().add(d.getDatoData());

                                }

                            }
                            datos.getDatos().add(dat);
                            con++;
                        }
                        String nombre = "grap" + Fechas.getCadena();
                        session.setAttribute(nombre, datos);

                        out.println(" graficarDial('" + pumu.getPumuId() + "','" + nombre + "');");

                    }

                } else {

                    out.println("alerta(\"ERROR\",\"¡Modulo no encontrado!\");");
                }

            } else {
                out.println("location.href=\"logout.jsp\"");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
            em.close();
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
