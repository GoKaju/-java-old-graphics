<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


<%@page import="com.statics.dao.CriterioPmJpaController"%>
<%@page import="com.statics.vo.CriterioPm"%>
<%@page import="com.statics.dao.ItemPmJpaController"%>
<%@page import="com.statics.vo.ItemPm"%>
<%@page import="com.statics.dao.ClimaPmJpaController"%>
<%@page import="com.statics.vo.ClimaPm"%>
<%@page import="com.statics.dao.EmisionDominantePmJpaController"%>
<%@page import="com.statics.vo.EmisionDominantePm"%>
<%@page import="com.statics.dao.TiempoPmJpaController"%>
<%@page import="com.statics.vo.TiempoPm"%>
<%@page import="com.statics.dao.TipoAreaPmJpaController"%>
<%@page import="com.statics.vo.TipoAreaPm"%>
<%@page import="com.statics.dao.DepartamentoJpaController"%>
<%@page import="com.statics.vo.Departamento"%>
<%@page import="com.statics.dao.MunicipioJpaController"%>
<%@page import="com.statics.vo.Municipio"%>
<%@page import="com.statics.dao.PuntoMuestralJpaController"%>
<%@page import="com.statics.vo.PuntoMuestral"%>
<%@page import="com.statics.dao.CampanasJpaController"%>
<%@page import="com.statics.vo.Campanas"%>
<%@page import="com.statics.dao.FormatofechasJpaController"%>
<%@page import="com.statics.vo.Formatofechas"%>
<%@page import="com.statics.dao.SeparadoresJpaController"%>
<%@page import="com.statics.vo.Separadores"%>
<%@page import="com.statics.dao.EstacionesJpaController"%>
<%@page import="com.statics.vo.Estaciones"%>
<%@page import="com.statics.vo.Roles"%>
<%@page import="com.statics.dao.RolesJpaController"%>
<%@page import="com.statics.dao.RolfuncionalidadJpaController"%>
<%@page import="com.statics.vo.Rolfuncionalidad"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="com.statics.util.Cadenas"%>
<%@page import="com.statics.vo.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    try {
        Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
        if (user != null && user.getUsuaId() != null) {
            Cadenas o = new Cadenas(request);
            String rfid = o.getvariable("rfid");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
            String index = o.getvariable("index");
            String campa = o.getvariable("campa");
            PuntoMuestral elem = new PuntoMuestral();
            Campanas campana = new Campanas();
            if (!index.isEmpty()) {
                elem = new PuntoMuestralJpaController(emf).findPuntoMuestral(Integer.parseInt(index));
            }

            if (!campa.isEmpty()) {
                campana = new CampanasJpaController(emf).findCampanas(Integer.parseInt(campa));

            }
%>
<form id="FormModalAplication" action="Login" method="POST" class="margin-bottom-0" data-parsley-validate="true" enctype="multipart/form-data">
    <div class="panel-body">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">Datos basicos</a>
                    </h4>
                </div>
                <div id="collapse1" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <div class="divider"></div>
                        <div class="col-md-4">
                            <div class="form-group ">
                                <label>Nombre del punto *</label>
                                <input type="text" name="nombrePunto" placeholder="Nombre del punto" 
                                       class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuNombre() : ""%>"  
                                       required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group ">
                                <label>Cliente *</label>
                                <input type="text" name="cliente" placeholder="Cliente" 
                                       class="form-control " value="<%=elem.getPumuId() != null ? elem.getIdCliente().getNombre() : ""%>"  
                                       required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group ">
                                <label>Estacion *</label>
                                <select name="estacion" class="form-control"  required="" data-parsley-id="7052">
                                    <%
                                        for (Estaciones s : new EstacionesJpaController(emf).findEstacionesEntities()) {
                                            String sel = "";
                                            if (elem.getPumuId() != null && elem.getEstaId().equals(s)) {
                                                sel = "selected";
                                            }
                                    %>
                                    <option <%=sel%> value="<%= s.getEstaId()%>" title=""><%=o.notEmpty(s.getEstaNombre())%></option>
                                    <%}
                                    %>
                                </select>
                                <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                            </div>
                        </div> 
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Descripción del punto</label>
                                <textarea class="form-control" type="text" name="descripcionPunto" placeholder="Descripción" 
                                          ><%=elem.getPumuId() != null ? elem.getPumuDescripcion() : ""%></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Ubicacion del punto</a>
                    </h4>
                </div>
                <div id="collapse2" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Departamento</label>
                                <select class="form-control" name="departamento">
                                    <%
                                        for (Departamento m : new DepartamentoJpaController(emf).findDepartamentoEntities()) {
                                            String sel = "";
                                            if (elem.getPumuId() != null && elem.getIdUbicacion().getIdMunicipio().equals(m)) {
                                                sel = "selected";
                                            }
                                    %>
                                    <option <%=sel%> value="<%= m.getId()%>"><%= o.notEmpty(m.getNombre())%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Municipio</label>
                                <select class="form-control" name="municipio" >
                                    <%
                                        for (Municipio m : new MunicipioJpaController(emf).findMunicipioEntities()) {
                                            String sel = "";
                                            if (elem.getPumuId() != null && elem.getIdUbicacion().getIdMunicipio().equals(m)) {
                                                sel = "selected";
                                            }
                                    %>
                                    <option <%=sel%> value="<%= m.getId()%>"><%=o.notEmpty(m.getNombre())%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Dirección</label>
                                <input class="form-control" type="text" name="direccion" 
                                       value="<%=elem.getPumuId() != null ? elem.getIdUbicacion().getDireccion() : ""%>"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group ">
                                <label>Coordenadas * <i class="glyphicon glyphicon-question-sign info" onclick="alertaCoordenadas()"></i></label>
                                <input type="text" name="longitud" placeholder="Longitud" 
                                       class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuLong() : ""%>"  
                                       data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group ">
                                <label><i class="glyphicon glyphicon-question-sign info" onclick="alertaCoordenadas()"></i></label>
                                <input type="text" name="latitud" placeholder="Latitud" class="form-control " 
                                       value="<%=elem.getPumuId() != null ? elem.getPumuLat() : ""%>"  
                                       data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Fotos del punto</a>
                    </h4>
                </div>
                <div id="collapse3" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="row col-md-12">
                            <div class="col-md-4">
                                <label>Foto1</label>
                                <div class="input-group">
                                    <label class="input-group-btn">
                                        <span class="btn btn-default">
                                            Examinar&hellip; 
                                            <input type="file" style="display:none" 
                                                   id="fileImg1" onchange="readURL(this)" name="img1"/>
                                        </span>
                                    </label>
                                    <input type="text" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <img class="fileImg1" src="" alt="No image here" />
                            </div>
                        </div>
                        <div class="row col-md-12">
                            <div class="col-md-4">
                                <label>Foto2</label>
                                <div class="input-group">
                                    <label class="input-group-btn">
                                        <span class="btn btn-default">
                                            Examinar&hellip; 
                                            <input type="file" style="display:none" 
                                                   id="fileImg2" onchange="readURL(this)" name="img2"/>
                                        </span>
                                    </label>
                                    <input type="text" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <img class="fileImg2" src="" alt="No image here" />
                            </div>
                        </div>
                        <div class="row col-md-12">
                            <div class="col-md-4">
                                <label>Foto3</label>
                                <div class="input-group">
                                    <label class="input-group-btn">
                                        <span class="btn btn-default">
                                            Examinar&hellip; 
                                            <input type="file" style="display:none" 
                                                   id="fileImg3" onchange="readURL(this)" name="img3"/>
                                        </span>
                                    </label>
                                    <input type="text" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <img class="fileImg3" src="" alt="No image here" />
                            </div>
                        </div>
                        <div class="row col-md-12">
                            <div class="col-md-4">
                                <label>Foto4</label>
                                <div class="input-group">
                                    <label class="input-group-btn">
                                        <span class="btn btn-default">
                                            Examinar&hellip; 
                                            <input type="file" style="display:none" 
                                                   id="fileImg4" onchange="readURL(this)" name="img4"/>
                                        </span>
                                    </label>
                                    <input type="text" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <img class="fileImg4" src="" alt="No image here" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse4">Niveles I a III</a>
                    </h4>
                </div>
                <div id="collapse4" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Tipo de area</label>
                                <select class="form-control" name="tipoArea">
                                    <%
                                        for (TipoAreaPm m : new TipoAreaPmJpaController(emf).findTipoAreaPmEntities()) {
                                            String sel = "";
                                            if (elem.getPumuId() != null && elem.getIdMacrolocalizacion().getIdTipoArea().equals(m)) {
                                                sel = "selected";
                                            }
                                    %>
                                    <option <%=sel%> value="<%= m.getId()%>"><%=o.notEmpty(m.getNombre())%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Tiempo</label>
                                <select class="form-control" name="tiempo">
                                    <%
                                        for (TiempoPm m : new TiempoPmJpaController(emf).findTiempoPmEntities()) {
                                            String sel = "";
                                            if (elem.getPumuId() != null && elem.getIdMacrolocalizacion().getIdTiempo().equals(m)) {
                                                sel = "selected";
                                            }
                                    %>
                                    <option <%=sel%> value="<%= m.getId()%>"><%= o.notEmpty(m.getNombre())%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Emision dominante</label>
                                <select class="form-control" name="emisionDominante">
                                    <%
                                        for (EmisionDominantePm m : new EmisionDominantePmJpaController(emf).findEmisionDominantePmEntities()) {
                                            String sel = "";
                                            if (elem.getPumuId() != null && elem.getIdMacrolocalizacion().getIdEmisionDominante().equals(m)) {
                                                sel = "selected";
                                            }
                                    %>
                                    <option <%=sel%> value="<%= m.getId()%>"><%=o.notEmpty(m.getNombre())%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Observaciones lvl i-iv</label>
                                <textarea class="form-control" name="observacionesLv1"
                                          ><%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getObservacionEmisionDominante() : ""%></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse5">Nivel IV</a>
                    </h4>
                </div>
                <div id="collapse5" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="panel-group" id="accordion2">
                            <div class="panel panel-default">
                                <div class="panel-heading accordionSub">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion2" href="#collapseSub1">Trafico</a>
                                    </h4>
                                </div>
                                <div id="collapseSub1" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Distancia al borde</label>
                                                <input type="number" class="form-control" name="distanciaBorde" placeholder="Kms" 
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getDistanciaAlBorde() : 0%>"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Ancho vía</label>
                                                <input type="number" class="form-control" name="anchoVia" placeholder="Mts"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getAnchoVia() : 0%>"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Velocidad promedio</label>
                                                <input type="number" class="form-control" name="velocidadPromedio" placeholder="Kms/h"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getVelocidadPromedio() : 0%>"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <label> </label>
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="sentidoUno" 
                                                           <%= elem.getPumuId() != null && elem.getIdMacrolocalizacion().getTraficoDiario1() ? "checked" : ""%>> Trafico sentido 1
                                                </label>   
                                                <label>
                                                    <input type="checkbox" name="sentidoDos"
                                                           <%= elem.getPumuId() != null && elem.getIdMacrolocalizacion().getTraficoDiario2() ? "checked" : ""%>> Trafico sentido 2
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Vehiculos pesados</label>
                                                <input type="number" class="form-control" name="vehiculosPesados" placeholder="%"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getPorcentajeVehiculosPesados() : 0%>"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Estado de la via</label>
                                                <input type="text" class="form-control" name="estadoVia" placeholder="via"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getAnchoVia() : ""%>"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading accordionSub">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion2" href="#collapseSub2">Indicativas</a>
                                    </h4>
                                </div>
                                <div id="collapseSub2" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="col-md-6">
                                            <div class="form-group ">
                                                <label>Tiempo de muestreo</label>
                                                <input type="number" name="tiempoMuestreo" placeholder="Días" 
                                                       class="form-control " data-parsley-id="7052"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getTiempoMuestreo() : 0%>">
                                                <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Clima</label>
                                                <select class="form-control" name="clima">
                                                    <%
                                                        for (ClimaPm c : new ClimaPmJpaController(emf).findClimaPmEntities()) {
                                                            String selected = "";
                                                            if (elem.getPumuId() != null && elem.getIdMacrolocalizacion().getIdClima().equals(c)) {
                                                                selected = "selected";
                                                            }
                                                    %>
                                                    <option <%= selected%> value="<%= c.getId()%>"><%= c.getNombre()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading accordionSub">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion2" href="#collapseSub3">Industrial</a>
                                    </h4>
                                </div>
                                <div id="collapseSub3" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="form-group col-md-4">
                                            <label>Tipo</label>
                                            <input class="form-control" type="text" name="tipo" placeholder="Tipo"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getTipo() : ""%>"/>
                                        </div>
                                        <div class="form-group col-md-4">
                                            <label>Distancia de la fuente</label>
                                            <input class="form-control" type="number" name="distanciaFuente" placeholder="Distancia de la fuente"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getDistanciaFuente() : 0%>"/>
                                        </div>
                                        <div class="form-group col-md-4">
                                            <label>Direccion grados</label>
                                            <input class="form-control" type="text" name="direccionGrados" placeholder="Direccion en grados"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getDireccionGrados() : ""%>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading accordionSub">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion2" href="#collapseSub4">Punto critico</a>
                                    </h4>
                                </div>
                                <div id="collapseSub4" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="form-group col-md-9">
                                            <label>Fuente evaluada</label>
                                            <input class="form-control" type="text" name="fuenteEvaluada" placeholder="Fuente evaluada"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getFuenteEvaluada() : ""%>"/>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="checkbox">
                                                <p>
                                                    <label>
                                                        <input type="checkbox" name="calleLibre" 
                                                               <%= elem.getPumuId() != null && elem.getIdMacrolocalizacion().getLibre() ? "checked" : ""%> > Libre
                                                    </label>
                                                </p>
                                                <p>
                                                    <label>
                                                        <input type="checkbox" name="calleEncajonada"
                                                               <%= elem.getPumuId() != null && elem.getIdMacrolocalizacion().getEncajonada() ? "checked" : ""%>/> Encajonada
                                                    </label>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="form-group col-md-12">
                                            <label>Observacion</label>
                                            <textarea class="form-control" name="observacionPuntoCritico" placeholder="Observacion"
                                                      ><%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getObservacionPuntoCritico() : ""%></textarea>
                                        </div>
                                    </div> 
                                </div> 
                            </div> 
                            <div class="panel panel-default">
                                <div class="panel-heading accordionSub">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion2" href="#collapseSub5">Rurales de fondo</a>
                                    </h4>
                                </div>
                                <div id="collapseSub5" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="form-group col-md-6">
                                            <label>Ciudades cercanas</label>
                                            <input class="form-control" type="text" name="cercanaCiudades" placeholder="Ciudades cercanas"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getCiudadesCercanas() : ""%>"/>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label>Regionales</label>
                                            <input class="form-control" type="text" name="regionales" placeholder="Regionales"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getRegionales() : ""%>"/>
                                        </div>
                                        <div class="form-group col-md-12">
                                            <label>Observacion</label>
                                            <textarea class="form-control" name="observacionRuralesFondo" placeholder="Observaciones"
                                                      ><%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getObservacionesRuralesFondo() : ""%></textarea>
                                        </div>
                                    </div> 
                                </div> 
                            </div> 
                        </div> 
                    </div> 
                </div> 
            </div> 
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse6">Datos de logistica</a>
                    </h4>
                </div>
                <div id="collapse6" class="panel-collapse collapse">
                    <div class="panel-body">
                        <%
                            String rta = "";
                            String obs = "";
                            for (ItemPm i : new ItemPmJpaController(emf).findItemPmEntities()) {
                                rta = "rta" + i.getId();
                                obs = "obs" + i.getId();
                        %>
                        <div class="row">
                            <div class="col-md-3"><%= i.getNombre()%></div>
                            <div class="col-md-3">
                                <input class="form-control" type="text" value=""
                                       name="<%= rta%>" placeholder="<%= i.getDescripcion()%>"/>
                            </div>
                            <div class="col-md-6">
                                <textarea class="form-control" name="<%= obs%>" rows="2" placeholder="Observacion"></textarea>
                            </div>
                        </div>
                        <hr class="half-rule"/>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse7">Criterios de microlocalizacion</a>
                    </h4>
                </div>
                <div id="collapse7" class="panel-collapse collapse">
                    <div class="panel-body">
                        <%
                            String cumpleCriterio = "";
                            String obsCriterio = "";
                            for (CriterioPm i : new CriterioPmJpaController(emf).findCriterioPmEntities()) {
                                cumpleCriterio = "cumpleCriterio" + i.getId();
                                obsCriterio = "obsCriterio" + i.getId();
                        %>
                        <div class="row">
                            <div class="col-md-4"><%= i.getNombre()%></div>
                            <div class="col-md-1">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" 
                                               data-toggle="toggle" 
                                               data-size="mini"
                                               class="toggleCheck"
                                               data-onstyle="success" 
                                               name="<%= cumpleCriterio%>">
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-7">
                                <textarea class="form-control" name="<%= obsCriterio%>" 
                                          rows="2" placeholder="Observacion"></textarea>
                            </div>
                        </div>
                        <hr class="half-rule"/>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse8">Datos adicionales</a>
                    </h4>
                </div>
                <div id="collapse8" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="form-group col-md-2">
                            <label>Descripcion</label>
                            <input class="form-control" type="text" name="descripcionDA1">
                        </div>
                        <div class="form-group col-md-2">
                            <label>Nombres</label>
                            <input class="form-control" type="text" name="nombresDA1">
                        </div>
                        <div class="form-group col-md-2">
                            <label>Celular</label>
                            <input class="form-control" type="text" name="celularDA1">
                        </div>
                        <div class="form-group col-md-2">
                            <label>Fijo</label>
                            <input class="form-control" type="text" name="fijoDA1">
                        </div>
                        <div class="form-group col-md-2">
                            <label>Email</label>
                            <input class="form-control" type="text" name="emailDA1">
                        </div>
                        <div class="form-group col-md-2">
                            <label>Otros</label>
                            <input class="form-control" type="text" name="otrosDA1">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <div>
            <input type="hidden" name="cantDatosAdicionales" value="1"/>
            <input type="hidden" name="modulo" value="3"/>
            <input type="hidden" name="rfid" value="<%=rfid%>"/>
            <input type="hidden" name="index" value="<%=index%>"/>
            <input type="hidden" name="campa" value="<%=campa%>"/>
            <label   onclick="

                    if ($('#FormModalAplication').parsley().isValid()) {
                        peticionAjax('Campanas', $('#FormModalAplication').serialize());
                    } else {
                        $('#FormModalAplication').submit();
                    }
                     " class="pull-right btn btn-success m-r-5 m-b-5">Guardar</label>
            <button type="button"  onclick="closeModal()" class="pull-right btn btn-default m-r-5 m-b-5">Cerrar</button>

        </div>
    </div>
</form>
<script>

    function peticionAjaxImagenes() {
        var img1 = $('#fileImg1')[0].files[0];
        var img2 = $('#fileImg2')[0].files[0];
        var img3 = $('#fileImg3')[0].files[0];
        var img4 = $('#fileImg4')[0].files[0];
        var form = $('#FormModalAplication')[0];
        var formData = new FormData(form);
        $.ajax({
            url: 'UploadImages.jsp',
            type: 'POST',
            success: function (e) {
                alerta('OK', '¡Operacion exitosa!');
                RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=<%=rfid%>&index=<%=index%>  #pumuContent', 'pumuContainer');

                eval(e.trim())
            },
            error: function (e) {
                alert(e);
            },
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        });
    }
    function alertaCoordenadas() {

        bootbox.alert({
            message: "<legend>Formatos admitidos</legend>" +
                    //"Grados, minutos y segundos (GMS): 41°24'12.2\"N 2°10'26.5\"E" +
                    // "</br>" +
                    //"Grados y minutos decimales (GMD): 41 24.2028, 2 10.4418" +
                    "</br>" +
                    "Grados decimales (GD): 41.40338, 2.17403"

        });
    }


    function readURL(input) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var divImage = $("." + input.id);
            console.log(divImage);
            divImage.attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }


    $(document).on('change', ':file', function () {
        var input = $(this),
                numFiles = input.get(0).files ? input.get(0).files.length : 1,
                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

    $(document).ready(function () {
        $(':file').on('fileselect', function (event, numFiles, label) {

            var input = $(this).parents('.input-group').find(':text'),
                    log = numFiles > 1 ? numFiles + ' files selected' : label;

            if (input.length) {
                input.val(log);
            } else {
                if (log)
                    alert(log);
            }

        });
        $('.toggleCheck').bootstrapToggle({
            on: 'Sí',
            off: 'No'
        });
    });
</script>
<%        } else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<%        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }%>