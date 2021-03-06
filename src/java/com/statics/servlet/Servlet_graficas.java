/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

//import com.statics.dao.ArchivosJpaController;
import com.statics.carga.DataJson;
import com.statics.dao.CampanasJpaController;
import com.statics.dao.ClienteJpaController;
import com.statics.dao.DatoProcesadoJpaController;
import com.statics.dao.NivelMaximoJpaController;
import com.statics.dao.ParametroFactorconversionJpaController;
import com.statics.dao.ParametrosJpaController;
import com.statics.dao.PuntoMuestralJpaController;
import com.statics.dao.UnidadmedidaParametroJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Constantes;
import com.statics.util.Fechas;
import com.statics.vo.Campanas;
import com.statics.vo.CargaParametro;
import com.statics.vo.Cliente;
import com.statics.vo.DatoProcesado;
import com.statics.vo.Datos;
import com.statics.vo.ParametroFactorconversion;
import com.statics.vo.Parametros;
import com.statics.vo.PuntoMuestral;
//import com.statics.vo.Archivos;
//import com.statics.vo.Grafica;
import com.statics.vo.Usuarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
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
                    int idPuntoMuestral = Integer.parseInt(o.getvariable("index"));
                    DatoProcesadoJpaController datoProcesadoDao = new DatoProcesadoJpaController(emf);
                    List<Parametros> listaParametros = datoProcesadoDao.findParametrosByIdPuntoMuestral(idPuntoMuestral);
                    String cadena = "";
                    for (Parametros p : listaParametros) {
                        cadena += "<option value='" + p.getParaId() + "' >" + p.getPareNombre() + "</option>";
                    }

//                 cadena = elem.getPuntoMuestralList().stream().map((puntoMuestral) -> <).reduce(cadena, String::concat);
                    System.out.println("cadena-->" + cadena);
                    out.println("$('#para_sel').html(\"" + cadena + "\"); $('#para_sel').multipleSelect('refresh'); ");

                } else if (modulo.equals("3")) {

                    String tipo = o.getvariable("tipoGrafica");
                    String tipoGrafica1 = o.getvariable("tipoGrafica1");

                    switch (tipo) {
                        case "1":
                            DataJson datos = new DataJson();
                            datos.setNombreGraphic("Nombre grafica");
                            datos.setTipo(tipoGrafica1);
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
                                String nombreGrafica="";
                                int horas = Integer.parseInt(o.getvariable("horario"));
                                //int idUnidadmedidaDeseada = Integer.parseInt(o.getvariable("unidadDeseada"));;
                                int idUnidadmedidaDeseada = 0;
                                PuntoMuestral pumu = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("pumu_sel")));
                                ParametroFactorconversionJpaController pfcDao = new ParametroFactorconversionJpaController(emf);
                                UnidadmedidaParametroJpaController unidadMedidaDao = new UnidadmedidaParametroJpaController(emf);
                                //String unidadMedida=unidadMedidaDao.findUnidadmedidaParametro(idUnidadmedidaDeseada).getIdUnidadMedida().getDescripcion();

                                datos = new DataJson();

                                String fechaini = o.getvariable("fini_txt");
                                String fechafin = o.getvariable("ffin_txt");
                                datos.setMin(fechaini);
                                datos.setMax(fechafin);
                                datos.setTipo(tipoGrafica1);
                                datos.setDatos(new ArrayList());
                                String Param[] = request.getParameterValues("para_sel");
                                nombreGrafica+=pumu.getPumuNombre()+"_";
                                int con = 0;
                                for (String p : Param) {
                                    Parametros parametro = new ParametrosJpaController(emf).findParametros(Integer.parseInt(p));
                                    nombreGrafica+=parametro.getPareNombre()+"_";
                                    String unidadMedida = pfcDao.findPFCByIdParametro(parametro.getParaId()).get(0).getIdUnidadMedida().getDescripcion();
                                    //Query cons = em.createNativeQuery(" SELECT dp.* FROM dato_procesado dp "
                                    //        + "INNER JOIN parametro_factorconversion pfc ON dp.id_parametro_factorconversion=pfc.id "
                                    //        + "WHERE dp.id_punto_muestral=? AND pfc.id_parametro=? AND fecha BETWEEN ? AND ?", DatoProcesado.class);
                                    //cons.setParameter(1, pumu.getPumuId());
                                    //cons.setParameter(2, parametro.getParaId());
                                    //cons.setParameter(3, fechaini);
                                    //cons.setParameter(4, fechafin);
                                    StoredProcedureQuery query = em
                                            .createStoredProcedureQuery("calculaPromediosPorHorario")
                                            .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                                            .setParameter(1, horas)
                                            .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                                            .setParameter(2, fechaini)
                                            .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                                            .setParameter(3, fechafin)
                                            .registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN)
                                            .setParameter(4, parametro.getParaId())
                                            .registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
                                            .setParameter(5, pumu.getPumuId());
                                    List<Object[]> lis = query.getResultList();
                                    System.out.println("--> " + lis.size());
                                    DataJson.DataUnit dat = datos.new DataUnit();
                                    dat.setX("x" + con);
                                    dat.setLabel(parametro.getPareNombre());
                                    dat.setFechas(new ArrayList());
                                    dat.setDatos(new ArrayList());
                                    dat.setUnidadMedida(unidadMedida);
                                    dat.setColorBorde(parametro.getPareColorBorde());
                                    dat.setColorFondo(parametro.getPareColorBackground());
                                    //NivelMaximo nm=nivelMaximoDao.
                                    //      findMaximoByIdParameterAndIdUnidadTiempo(parametro.getParaId(),idUnidadTiempo);
                                    //if (nm!=null) {
                                    //  dat.setMaxValue(nm.getNivelMaximo());
                                    //}
                                    double convertido = 0;
                                    for (Object[] d : lis) {
                                        

                                        dat.getFechas().add(d[4].toString());
                                        dat.getDatos().add(d[5].toString());
                                    }

                                    if (datos.getConcatX() == null || datos.getConcatX().isEmpty()) {
                                        datos.setConcatX(String.join("\",\"", dat.getFechas()));
                                    }
                                    datos.getDatos().add(dat);
                                    con++;
                                }
                                nombreGrafica+=fechaini + "_" + fechafin;
                                datos.setNombreGraphic(nombreGrafica);
                                String nombre = "grap" + Fechas.getCadena();
                                session.setAttribute(nombre, datos);

                                out.println(" crearGrafica('" + nombre + "');");
                            } else {
                                // descarga de excel 
                                PuntoMuestral pumu = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("pumu_sel")));
                                String nombreGrafica="";
                                int horas = Integer.parseInt(o.getvariable("horario"));
                                String fechaini = o.getvariable("fini_txt");
                                String fechafin = o.getvariable("ffin_txt");

                                datos = new DataJson();
                                datos.setNombreGraphic(pumu.getPumuNombre() + " del " + fechaini + " al " + fechafin);

                                String Param[] = request.getParameterValues("para_sel");
                                if (Param != null && Param.length > 0) {
                                    /*Query cons = em.createNativeQuery("SELECT\n"
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
                                     + "date_format(d.dato_fecha, \"%Y-%m-%d\")\n"
                                     + "ORDER BY\n"
                                     + "d.dato_fecha ASC");*/
                                    Query cons = em.createStoredProcedureQuery("calculaPromediosPorHorarioExcel")
                                            .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                                            .setParameter(1, horas)
                                            .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                                            .setParameter(2, fechaini)
                                            .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                                            .setParameter(3, fechafin)
                                            .registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN)
                                            .setParameter(4, pumu.getPumuId());
                                    List<Object[]> lis = cons.getResultList();

                                    for (Object[] a : lis) {
                                        System.out.println("DATO EXCEL --> "
                                                + a[0]
                                                + " "
                                                + a[1]);
                                    }
                                    nombreGrafica+=fechaini + "_" + fechafin;
                                datos.setNombreGraphic(nombreGrafica);
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
                    System.out.println("GENERACION GRAF24 SERV INIT::: " + Fechas.getFechaHoraTimeStamp());
                    int idPuntoMuestral = Integer.parseInt(o.getvariable("cod"));
                    String tipo = o.getvariable("tipoGrafica");

                    //PuntoMuestralJpaController puntoMuestralDao=new PuntoMuestralJpaController(emf);
                    DatoProcesadoJpaController datoProcesadoDao = new DatoProcesadoJpaController(emf);
                    ParametroFactorconversionJpaController pfcDao = new ParametroFactorconversionJpaController(emf);

                    DataJson datos = new DataJson();
                    datos.setNombreGraphic("Nombre grafica");
                    datos.setTipo("Timeseries");
                    datos.setDatos(new ArrayList());
                    List<Object[]> listaDatosProcesados;
                    List<DatoProcesado> datoPromedio;
                    int color = 0;
                    int horarioICA = 0;
                    double valor = 0;
                    List<ParametroFactorconversion> listaParametroFactorconversions = pfcDao.findPFCInPunto(idPuntoMuestral);
System.out.println("GENERACION GRAF24 SERV INIT::1: " + Fechas.getFechaHoraTimeStamp());
                    int con = 0;
                    for (ParametroFactorconversion pfc : listaParametroFactorconversions) {
                        valor = 0;
                        int paraId = pfc.getIdParametro().getParaId();
                        DataJson.DataUnit dat = datos.new DataUnit();
                        dat.setX("x" + con);
                        dat.setLabel(pfc.getIdParametro().getPareNombre());
                        dat.setFechas(new ArrayList());
                        dat.setDatos(new ArrayList());
                        dat.setUnidadMedida(pfc.getIdUnidadMedida().getDescripcion());
                        dat.setColorBorde(pfc.getIdParametro().getPareColorBorde());
                        listaDatosProcesados = datoProcesadoDao.findDatosByIdPuntoAndParametro24Hours(idPuntoMuestral, pfc.getId());
                        System.out.println("GENERACION GRAF24 SERV INIT::2: " + Fechas.getFechaHoraTimeStamp());
                        switch (paraId) {
                            case 5:
                                datoPromedio = datoProcesadoDao.findPromedioDatosPorHorario(24, idPuntoMuestral, pfc.getId());
                                horarioICA =24;
                                if (!datoPromedio.isEmpty()) {
                                    valor = datoPromedio.get(0).getValor();
                                }   if (valor > 0 && valor < 54) {
                                    color = 1;
                                } else if (valor >= 54 && valor < 155) {
                                    color = 2;
                                } else if (valor >= 155 && valor < 254) {
                                    color = 3;
                                } else if (valor >= 254 && valor < 354) {
                                    color = 4;
                                } else if (valor >= 355 && valor < 424) {
                                    color = 5;
                                } else if (valor >= 425) {
                                    color = 6;
                                }   break;
                            case 6:
                                horarioICA =24;
                                datoPromedio = datoProcesadoDao.findPromedioDatosPorHorario(24, idPuntoMuestral, pfc.getId());
                                if (!datoPromedio.isEmpty()) {
                                    valor = datoPromedio.get(0).getValor();
                                }   if (valor > 0 && valor < 12) {
                                    color = 1;
                                } else if (valor >= 13 && valor < 37) {
                                    color = 2;
                                } else if (valor >= 38 && valor < 55) {
                                    color = 3;
                                } else if (valor >= 56 && valor < 150) {
                                    color = 4;
                                } else if (valor >= 151 && valor < 250) {
                                    color = 5;
                                } else if (valor >= 251) {
                                    color = 6;
                                }   break;
                            case 9:
                                horarioICA =1;
                                datoPromedio = datoProcesadoDao.findPromedioDatosPorHorario(1, idPuntoMuestral, pfc.getId());
                                if (!datoPromedio.isEmpty()) {
                                    valor = datoPromedio.get(0).getValor() * 2.16185;
                                }   if (valor > 0 && valor < 93) {
                                    color = 1;
                                } else if (valor >= 94 && valor < 197) {
                                    color = 2;
                                } else if (valor >= 198 && valor < 486) {
                                    color = 3;
                                } else if (valor >= 487 && valor < 797) {
                                    color = 4;
                                } else if (valor >= 798 && valor < 1583) {
                                    color = 5;
                                } else if (valor >= 1584) {
                                    color = 6;
                                }   break;
                            case 2:
                                horarioICA =1;
                                datoPromedio = datoProcesadoDao.findPromedioDatosPorHorario(1, idPuntoMuestral, pfc.getId());
                                if (!datoPromedio.isEmpty()) {
                                    valor = datoPromedio.get(0).getValor() * 1.880;
                                }   if (valor > 0 && valor < 100) {
                                    color = 1;
                                } else if (valor >= 101 && valor < 189) {
                                    color = 2;
                                } else if (valor >= 190 && valor < 677) {
                                    color = 3;
                                } else if (valor >= 678 && valor < 1221) {
                                    color = 4;
                                } else if (valor >= 1222 && valor < 2349) {
                                    color = 5;
                                } else if (valor >= 2350) {
                                    color = 6;
                                }   break;
                            default:
                                datoPromedio = datoProcesadoDao.findPromedioDatosPorHorario(24, idPuntoMuestral, pfc.getId());
                                horarioICA =0;
                                if (!datoPromedio.isEmpty()) {
                                    valor = datoPromedio.get(0).getValor();
                                }   color = 7;
                                break;
                        }
                        System.out.println("GENERACION GRAF24 SERV INIT::3: " + Fechas.getFechaHoraTimeStamp());
                        dat.setDatoPromediado(valor);
                        dat.setColor(color);
                        dat.setHoraICA(horarioICA);
                        dat.setUltimoDato("0");
                        for (Object[] dp : listaDatosProcesados) {
                            dat.getFechas().add(dp[4].toString());
                            dat.getDatos().add(dp[5].toString());
                            dat.setUltimoDato(dp[5].toString());
                        }

                        datos.getDatos().add(dat);
                        con++;
                    }
                    String nombre = "grap" + Fechas.getCadena();
                    session.setAttribute(nombre, datos);
                    System.out.println("GENERACION GRAF24 SERV FIN::: " + Fechas.getFechaHoraTimeStamp());
                    out.println(" graficarDial('" + idPuntoMuestral + "','" + nombre + "');");

                    
                } else if (modulo.equals("5")) {
                    int idCliente = Integer.parseInt(o.getvariable("index"));
                    CampanasJpaController clienteDao = new CampanasJpaController(emf);
                    List<Campanas> listaCampanas = clienteDao.findCampanasByCliente(idCliente);
                    String cadena = "<option value='' >Seleccione...</option>";
                    for (Campanas p : listaCampanas) {
                        cadena += "<option value='" + p.getCampId() + "' >" + p.getCampNombre() + "</option>";
                    }

//                 cadena = elem.getPuntoMuestralList().stream().map((puntoMuestral) -> <).reduce(cadena, String::concat);
                    System.out.println("cadena-->" + cadena);
                    out.println("$('#camp_sel').html(\"" + cadena + "\"); $('#camp_sel')");
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
