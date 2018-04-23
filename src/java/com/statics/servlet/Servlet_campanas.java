/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

import com.statics.dao.CampanasJpaController;
import com.statics.dao.ClienteJpaController;
import com.statics.dao.ClimaPmJpaController;
import com.statics.dao.CriterioMicrolocalizacionJpaController;
import com.statics.dao.CriterioPmJpaController;
import com.statics.dao.DatosAdicionalesPmJpaController;
import com.statics.dao.DatosadicionalesMicrolocalizacionJpaController;
import com.statics.dao.DepartamentoJpaController;
import com.statics.dao.EmisionDominantePmJpaController;
import com.statics.dao.EstacionesJpaController;
import com.statics.dao.ItemLogisticaJpaController;
import com.statics.dao.ItemPmJpaController;
import com.statics.dao.LogisticaPmJpaController;
import com.statics.dao.MacrolocalizacionPmJpaController;
import com.statics.dao.MicrolocalizacionPmJpaController;
import com.statics.dao.MunicipioJpaController;
import com.statics.dao.PuntoMuestralJpaController;
import com.statics.dao.RutaJpaController;
import com.statics.dao.TiempoPmJpaController;
import com.statics.dao.TipoAreaPmJpaController;
import com.statics.dao.UbicacionPmJpaController;
import com.statics.util.Cadenas;
import com.statics.util.Fechas;
import com.statics.vo.Campanas;
import com.statics.vo.Cliente;
import com.statics.vo.ClimaPm;
import com.statics.vo.CriterioMicrolocalizacion;
import com.statics.vo.CriterioPm;
import com.statics.vo.DatosAdicionalesPm;
import com.statics.vo.DatosadicionalesMicrolocalizacion;
import com.statics.vo.Departamento;
import com.statics.vo.EmisionDominantePm;
import com.statics.vo.Estaciones;
import com.statics.vo.ItemLogistica;
import com.statics.vo.ItemPm;
import com.statics.vo.LogisticaPm;
import com.statics.vo.MacrolocalizacionPm;
import com.statics.vo.MicrolocalizacionPm;
import com.statics.vo.Municipio;
import com.statics.vo.PuntoMuestral;
import com.statics.vo.Ruta;
import com.statics.vo.TiempoPm;
import com.statics.vo.TipoAreaPm;
import com.statics.vo.UbicacionPm;
import com.statics.vo.Usuarios;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import io.minio.MinioClient;
import java.io.File;
import java.util.Iterator;
import javax.servlet.ServletContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
                    RutaJpaController rutaDao = new RutaJpaController(emf);
                    ClienteJpaController clienteDao=new ClienteJpaController(emf);
                    String bucketName = "";
                    Ruta ruta = rutaDao.findRuta(1);

                    Campanas elem = new Campanas();

                    if (!o.getvariable("index").isEmpty()) {
                        elem = ejc.findCampanas(Integer.parseInt(o.getvariable("index")));
                    } else {
                        for (int i = 0; i < 10; i++) {
                            int numero = (int) (Math.random() * 9) + 1;
                            bucketName += numero;
                        }
                        MinioClient mc = new MinioClient(ruta.getUrl(), ruta.getAccessKey(), ruta.getSecretKey());
                        mc.makeBucket(bucketName);
                        elem.setCampBucket(bucketName);
                    }
                    Cliente cliente=clienteDao.findCliente(Integer.parseInt(o.getvariable("idCliente")));
                    
                    elem.setCampNombre(o.getvariable("Nombre"));
                    elem.setCampDescripcion(o.getvariable("descripcion"));
                    elem.setIdCliente(cliente);
                    elem.setEstaId(1);
                    elem.setCampRegistradapor(user.getUsuaId());
                    elem.setCampFechacambio(Fechas.getFechaHoraTimeStamp());
                    elem.setGrupId(user.getGrupoUsuariosList().get(0).getGrupId());
                    if (exito) {
                        if (elem.getCampId() != null) {
                            ejc.edit(elem);
                        } else {
                            ejc.create(elem);
                        }
                    }

                    if (exito) {
                        out.println(" RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=" + o.getvariable("rfid") + "&index=" + elem.getCampId() + "','content');");
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
//Se quita Cliente y se deja en campaña
                    System.out.println("INGRESO POR ACÁ");
                    int cantDatosAdicionales = !o.getvariable("cantDatosAdicionales").equals("") ? Integer.parseInt(o.getvariable("cantDatosAdicionales")) : 0;
                    int idCampana = !o.getvariable("campa").equals("") ? Integer.parseInt(o.getvariable("campa")) : 0;

                    String nombrePM = o.getvariable("nombrePunto");
                    int estacionPM = !o.getvariable("estacion").equals("") ? Integer.parseInt(o.getvariable("estacion")) : 0;
                    String descripcionPM = o.getvariable("descripcionPunto");
                    int departamentoPM = !o.getvariable("departamento").equals("") ? Integer.parseInt(o.getvariable("departamento")) : 0;
                    int municipioPM = !o.getvariable("municipio").equals("") ? Integer.parseInt(o.getvariable("municipio")) : 0;
                    String direccionPM = o.getvariable("direccion");
                    String longitudPM = o.getvariable("longitud");
                    String latitudPM = o.getvariable("latitud");
                    int tipoAreaPM = !o.getvariable("tipoArea").equals("") ? Integer.parseInt(o.getvariable("tipoArea")) : 0;
                    int tiempoPM = !o.getvariable("tiempo").equals("") ? Integer.parseInt(o.getvariable("tiempo")) : 0;
                    int emisionDominantePM = !o.getvariable("emisionDominante").equals("") ? Integer.parseInt(o.getvariable("emisionDominante")) : 0;
                    double distanciaBordePM = !o.getvariable("distanciaBorde").equals("") ? Double.parseDouble(o.getvariable("distanciaBorde")) : 0;
                    double anchoViaPM = !o.getvariable("anchoVia").equals("") ? Double.parseDouble(o.getvariable("anchoVia")) : 0;
                    double velocidadPromedioPM = !o.getvariable("velocidadPromedio").equals("") ? Double.parseDouble(o.getvariable("velocidadPromedio")) : 0;
                    int vehiculosPesadosPM = !o.getvariable("vehiculosPesados").equals("") ? Integer.parseInt(o.getvariable("vehiculosPesados")) : 0;
                    String estadoViaPM = o.getvariable("estadoVia");
                    boolean sentidoUnoPM = o.getvariable("sentidoUno").equals("on");
                    boolean sentidoDosPM = o.getvariable("sentidoDos").equals("on");
                    int tiempoMuestreoPM = !o.getvariable("tiempoMuestreo").equals("") ? Integer.parseInt(o.getvariable("tiempoMuestreo")) : 0;
                    int climaPM = !o.getvariable("clima").equals("") ? Integer.parseInt(o.getvariable("clima")) : 0;
                    String tipoPM = o.getvariable("tipo");
                    double distanciaFuentePM = !o.getvariable("distanciaFuente").equals("") ? Double.parseDouble(o.getvariable("distanciaFuente")) : 0;
                    String direccionGradosPM = o.getvariable("direccionGrados");
                    String puntoCriticoPM = o.getvariable("puntoCritico");
                    String observacionPuntoCriticoPM = o.getvariable("observacionPuntoCritico");

                    String ruralesFondoPM = o.getvariable("ruralesFondo");
                    String descripcionRutaPM = o.getvariable("descripcionRuta");

                    LogisticaPmJpaController logisticaDao = new LogisticaPmJpaController(emf);
                    ItemLogisticaJpaController itemLogisticaDao = new ItemLogisticaJpaController(emf);
                    ItemPmJpaController itemDao = new ItemPmJpaController(emf);
                    CriterioPmJpaController criterioDao = new CriterioPmJpaController(emf);
                    UbicacionPmJpaController ubicacionDao = new UbicacionPmJpaController(emf);
                    DepartamentoJpaController departamentoDao = new DepartamentoJpaController(emf);
                    MunicipioJpaController municipioDao = new MunicipioJpaController(emf);
                    MacrolocalizacionPmJpaController macrolocalizacionDao = new MacrolocalizacionPmJpaController(emf);
                    TipoAreaPmJpaController tipoAreaDao = new TipoAreaPmJpaController(emf);
                    TiempoPmJpaController tiempoDao = new TiempoPmJpaController(emf);
                    EmisionDominantePmJpaController emisionDominanteDao = new EmisionDominantePmJpaController(emf);
                    ClimaPmJpaController climaDao = new ClimaPmJpaController(emf);
                    MicrolocalizacionPmJpaController microlocalizacionDao = new MicrolocalizacionPmJpaController(emf);
                    DatosAdicionalesPmJpaController datosAdicionalesDao = new DatosAdicionalesPmJpaController(emf);
                    DatosadicionalesMicrolocalizacionJpaController datosAdicionalesMicrolocalizacionDao = new DatosadicionalesMicrolocalizacionJpaController(emf);
                    CriterioMicrolocalizacionJpaController criterioMicrolocalizacionDao = new CriterioMicrolocalizacionJpaController(emf);
                    CampanasJpaController campanaDao = new CampanasJpaController(emf);
                    PuntoMuestralJpaController puntoMuestralDao = new PuntoMuestralJpaController(emf);
                    EstacionesJpaController estacionDao = new EstacionesJpaController(emf);
                    RutaJpaController rutaDao = new RutaJpaController(emf);

                    //Ubicacion
                    Departamento departamento = departamentoDao.findDepartamento(departamentoPM);
                    Municipio municipio = municipioDao.findMunicipio(municipioPM);
                    UbicacionPm ubicacion = new UbicacionPm(direccionPM, departamento, municipio);
                    ubicacionDao.create(ubicacion);

                    //Macrolocalzacion
                    TipoAreaPm tipoArea = tipoAreaDao.findTipoAreaPm(tipoAreaPM);
                    TiempoPm tiempo = tiempoDao.findTiempoPm(tiempoPM);
                    EmisionDominantePm emisionDominante = emisionDominanteDao.findEmisionDominantePm(emisionDominantePM);
                    ClimaPm clima = climaDao.findClimaPm(climaPM);
                    MacrolocalizacionPm macrolocalizacion
                            = new MacrolocalizacionPm(
                                    puntoCriticoPM, ruralesFondoPM, distanciaBordePM, anchoViaPM, sentidoUnoPM,
                                    sentidoDosPM, velocidadPromedioPM, vehiculosPesadosPM, estadoViaPM,
                                    tiempoMuestreoPM, tipoPM, distanciaFuentePM, direccionGradosPM,
                                    observacionPuntoCriticoPM, tipoArea, tiempo,
                                    emisionDominante, clima);
                    macrolocalizacionDao.create(macrolocalizacion);

                    //Microlocalizacion y datos adicionales
                    MicrolocalizacionPm microlocalizacion = new MicrolocalizacionPm(new Date());
                    microlocalizacionDao.create(microlocalizacion);
                    DatosadicionalesMicrolocalizacion datosAdicionalesMicrolocalizacion;
                    DatosAdicionalesPm datosAdicionales;

                    for (int i = 1; i <= cantDatosAdicionales; i++) {
                        String descripcionDA = o.getvariable("descripcionDA" + i);
                        String nombresDA = o.getvariable("nombresDA" + i);
                        Long celular = !o.getvariable("celularDA" + i).equals("") ? Long.parseLong(o.getvariable("celularDA" + i)) : 0;
                        int fijo = !o.getvariable("fijoDA" + i).equals("") ? Integer.parseInt(o.getvariable("fijoDA" + i)) : 0;
                        String email = o.getvariable("emailDA" + i);
                        String otros = o.getvariable("otrosDA" + i);
                        datosAdicionales
                                = new DatosAdicionalesPm(descripcionDA, nombresDA,
                                        celular, fijo, email, otros);
                        datosAdicionalesDao.create(datosAdicionales);

                        datosAdicionalesMicrolocalizacion
                                = new DatosadicionalesMicrolocalizacion(datosAdicionales,
                                        microlocalizacion);
                        datosAdicionalesMicrolocalizacionDao.create(datosAdicionalesMicrolocalizacion);
                    }

                    //Logistica e items
                    LogisticaPm logistica = new LogisticaPm(descripcionRutaPM);
                    logisticaDao.create(logistica);
                    List<ItemPm> items = itemDao.findItemPmEntities();
                    ItemLogistica itemLogistica;
                    for (ItemPm i : items) {
                        String rta = o.getvariable("rta" + i.getId());
                        String obs = o.getvariable("obs" + i.getId());
                        itemLogistica = new ItemLogistica(rta, obs, i, logistica);
                        itemLogisticaDao.create(itemLogistica);
                    }

                    //Criterios microlocalizacion
                    List<CriterioPm> criterios = criterioDao.findCriterioPmEntities();
                    CriterioMicrolocalizacion criterioMicrolocalizacion;
                    for (CriterioPm i : criterios) {
                        boolean cumpleCriterio = o.getvariable("cumpleCriterio" + i.getId()).equals("on");
                        String observacionCriterio = o.getvariable("obsCriterio" + i.getId());
                        criterioMicrolocalizacion
                                = new CriterioMicrolocalizacion(cumpleCriterio, observacionCriterio,
                                        microlocalizacion, i);
                        criterioMicrolocalizacionDao.create(criterioMicrolocalizacion);
                    }

                    //Punto muestral
                    Campanas campana = campanaDao.findCampanas(idCampana);

                    Estaciones estacion = estacionDao.findEstaciones(estacionPM);
                    PuntoMuestral puntoMuestral = new PuntoMuestral();
                    if (!o.getvariable("index").isEmpty()) {
                        puntoMuestral = puntoMuestralDao.findPuntoMuestral(Integer.parseInt(o.getvariable("index")));
                    }
//                    puntoMuestral=
//                            new PuntoMuestral(macrolocalizacion,microlocalizacion,ubicacion,
//                                    cliente,logistica,nombrePM,descripcionPM,longitudPM,latitudPM,new Date(),
//                                    user.getUsuaId(),new Date(),estacion,campana);
                    puntoMuestral.setIdMacrolocalizacion(macrolocalizacion);
                    puntoMuestral.setIdMicrolocalizacion(microlocalizacion);
                    puntoMuestral.setIdUbicacion(ubicacion);
                    puntoMuestral.setIdLogistica(logistica);
                    puntoMuestral.setPumuNombre(nombrePM);
                    puntoMuestral.setPumuDescripcion(descripcionPM);
                    puntoMuestral.setPumuLong(longitudPM);
                    puntoMuestral.setPumuLat(latitudPM);
                    puntoMuestral.setPumuFechainicial(Fechas.getFechaHoraTimeStamp());
                    puntoMuestral.setPumuRegistradopor(user.getUsuaId());
                    puntoMuestral.setPumuFechacambio(Fechas.getFechaHoraTimeStamp());
                    puntoMuestral.setEstaId(estacion);
                    puntoMuestral.setCampId(campana);
                    if (exito) {
                        if (puntoMuestral.getPumuId() != null) {
                            puntoMuestralDao.edit(puntoMuestral);
                        } else {
                            puntoMuestralDao.create(puntoMuestral);
                        }
                    }
                    session.setAttribute("campana", campana);
                    session.setAttribute("punto", puntoMuestral);
                    if (exito) {
                        out.println("peticionAjaxImagenes();");
                    } else {
                        out.println("alerta('Error','" + errores + "');");
                    }
                } else if (modulo.equals("4")) {
                    try {
                        PuntoMuestral elem = pmjc.findPuntoMuestral(Integer.parseInt(o.getvariable("index")));

                        ejc.destroy(elem.getPumuId());
                        out.println("alerta('OK','¡Operacion exitosa!'); RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=" + o.getvariable("rfid") + "&index=" + elem.getCampId().getCampId() + "  #pumuContent','pumuContainer');");

                    } catch (Exception ex) {
                        out.println("alerta('ERROR','¡Este punto muestral ya posee datos por lo tanto no se puede remover!');");

                    }
//RECRAGAR MUNICIOIO DEPENDIENTE
                } else if (modulo.equals("5")) {
                    String cadena = "";
                    for (Municipio m : new MunicipioJpaController(emf)
                            .findMunicipiosByDepartamento(Integer.parseInt(o.getvariable("idDepartamento")))) {
                        cadena += "<option value=" + m.getId() + ">" + m.getNombre().toUpperCase() + "</option>";

                    }
                    System.out.println("cadena-->" + cadena);
                    out.println(" $('#cmbMunicipio').html(\"" + cadena + "\");");
                    //AGREGAR CLIENTE
                } else if (modulo.equals("6")) {
                    int idCliente = o.getvariable("idCliente") != "" ? Integer.parseInt(o.getvariable("idCliente")) : 0;

                    String nombre = o.getvariable("nombreCliente");
                    String direccion = o.getvariable("direccionCliente");
                    String email = o.getvariable("emailCliente");
                    String nit = o.getvariable("nitCliente");
                    String telefono = o.getvariable("telefonoCliente");

                    ClienteJpaController clienteDao = new ClienteJpaController(emf);
                    Cliente cliente;
                    if (idCliente != 0) {
                        cliente=clienteDao.findCliente(idCliente);
                        cliente.setNombre(nombre);
                        cliente.setDireccion(direccion);
                        cliente.setEmail(email);
                        cliente.setNit(nit);
                        cliente.setTelefono(telefono);
                        clienteDao.edit(cliente);
                    } else {
                        cliente=new Cliente(nombre, direccion, email, nit, telefono, user.getUsuaId(), new Date());
                        clienteDao.create(cliente);
                    }
                    out.println("alerta('OK','¡Operacion exitosa!'); RecargaPanel('panels/campanas/campanas_nuevo.jsp?rfid=" + o.getvariable("rfid") + "&index="+o.getvariable("index")+"','content','closeModal()');");
                } else {
                    out.println("alerta('ERROR','¡Modulo no encontrado!');");

                }

            } else {
                out.println("location.href='logout.jsp'");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
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
