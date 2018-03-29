<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


<%@page import="com.statics.vo.DatosAdicionalesPm"%>
<%@page import="com.statics.dao.DatosAdicionalesPmJpaController"%>
<%@page import="com.statics.dao.DatosadicionalesMicrolocalizacionJpaController"%>
<%@page import="com.statics.vo.DatosadicionalesMicrolocalizacion"%>
<%@page import="com.statics.dao.CriterioMicrolocalizacionJpaController"%>
<%@page import="com.statics.vo.CriterioMicrolocalizacion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.statics.vo.ItemLogistica"%>
<%@page import="com.statics.dao.ItemLogisticaJpaController"%>
<%@page import="com.statics.vo.FotoPuntomuestral"%>
<%@page import="java.util.List"%>
<%@page import="com.statics.dao.FotoPuntomuestralJpaController"%>
<%@page import="com.statics.vo.Ruta"%>
<%@page import="io.minio.MinioClient"%>
<%@page import="com.statics.dao.RutaJpaController"%>
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

            RutaJpaController rutaDao = new RutaJpaController(emf);
            FotoPuntomuestralJpaController fotoPmDao = new FotoPuntomuestralJpaController(emf);
            Ruta ruta = rutaDao.findRuta(1);
            MinioClient mc = new MinioClient(ruta.getUrl(), ruta.getAccessKey(), ruta.getSecretKey());

            ItemLogisticaJpaController itemLogisticaDao = new ItemLogisticaJpaController(emf);
            DatosAdicionalesPmJpaController datosAdicionalesDao = new DatosAdicionalesPmJpaController(emf);
            CriterioMicrolocalizacionJpaController criterioMicrolocalizacionDao = new CriterioMicrolocalizacionJpaController(emf);

%>

<form id="FormModalAplication" action="Login" method="POST" class="margin-bottom-0" data-parsley-validate="true" enctype="multipart/form-data">
    <div class="panel-body">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">Datos básicos</a>
                    </h4>
                </div>
                <div id="collapse1" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <div class="divider"></div>
                        <div class="col-md-8">
                            <div class="form-group ">
                                <label>Nombre del punto *</label>
                                <input type="text" name="nombrePunto" placeholder="Nombre del punto" 
                                       class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuNombre() : ""%>"  
                                       data-parsley-required="true"
                                       data-parsley-error-message="Datos básicos: El nombre del punto es necesario."
                                       data-parsley-errors-container="#errorsParsley">
                                <ul class="parsley-errors-list"></ul>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group ">
                                <label>Estación *</label>
                                <select name="estacion" class="form-control"  
                                        data-parsley-required="true"
                                        data-parsley-error-message="Datos básicos: La estación es necesaria."
                                        data-parsley-errors-container="#errorsParsley">
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
                                <ul class="parsley-errors-list"></ul>
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
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Ubicación del punto</a>
                    </h4>
                </div>
                <div id="collapse2" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Departamento</label>
                                <select class="form-control" name="departamento" id="cmbDepartamento" 
                                        onchange="recargaMunicipio(this.value)"
                                        data-parsley-required="true"
                                        data-parsley-error-message="Ubicación: El departamento es necesario."
                                        data-parsley-errors-container="#errorsParsley">
                                    <option value="">Seleccione...</option>
                                    <%
                                        for (Departamento m : new DepartamentoJpaController(emf).findDepartamentoEntities()) {
                                            String sel = "";
                                            if (elem.getPumuId() != null && elem.getIdUbicacion().getIdDepartamento().equals(m)) {
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
                                <select class="form-control" name="municipio" id="cmbMunicipio">
                                    <%
                                        if (elem.getPumuId() != null) {
                                            for (Municipio m : new MunicipioJpaController(emf)
                                                    .findMunicipiosByDepartamento(elem.getIdUbicacion().getIdDepartamento().getId())) {
                                                String sel = "";
                                                if (elem.getIdUbicacion().getIdMunicipio().equals(m)) {

                                                    sel = "selected";
                                                }
                                    %>
                                    <option <%=sel%> value="<%= m.getId()%>"><%=o.notEmpty(m.getNombre().toUpperCase())%></option>
                                    <%
                                            }
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
                                       data-parsley-required="true"
                                       data-parsley-error-message="Ubicación: La longitud del punto es necesaria."
                                       data-parsley-errors-container="#errorsParsley">
                                <ul class="parsley-errors-list"></ul>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group ">
                                <label><i class="glyphicon glyphicon-question-sign info" onclick="alertaCoordenadas()"></i></label>
                                <input type="text" name="latitud" placeholder="Latitud" class="form-control " 
                                       value="<%=elem.getPumuId() != null ? elem.getPumuLat() : ""%>"
                                       data-parsley-required="true"
                                       data-parsley-error-message="Ubicación: La latitud del punto es necesaria."
                                       data-parsley-errors-container="#errorsParsley">
                                <ul class="parsley-errors-list"></ul>
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
                        <%
                            List<FotoPuntomuestral> listaFotos = new ArrayList();
                            String campanaBucket = "";
                            String imgName = "img";
                            String imgLabel = "Foto";
                            String imgId = "fileImg";
                            String urlFoto = "";
                            String imgOrientacion = "";

                            if (elem.getPumuId() != null) {
                                listaFotos = fotoPmDao.findFotosByPuntoMuestral(elem.getPumuId());
                                campanaBucket = elem.getCampId().getCampBucket();
                            }
                            for (int i = 1; i <= 4; i++) {
                                if (listaFotos.size() != 0) {
                                    urlFoto = mc.presignedGetObject(campanaBucket, listaFotos.get(0).getNombre());
                                    listaFotos.remove(0);
                                }
                                imgName = imgName + i;
                                imgId = imgId + i;
                                imgLabel = imgLabel + " " + i;
                                imgOrientacion = "assets/img/" + i + ".PNG";
                        %>
                        <div class="row col-md-12">
                            <div class="col-md-1">
                                <img src="<%= imgOrientacion%>" alt="" width="40" style="padding-top:7px"/>
                            </div>
                            <div class="col-md-4">
                                <label><%= imgLabel%></label>
                                <div class="input-group">
                                    <label class="input-group-btn">
                                        <span class="btn btn-default">
                                            Examinar&hellip; 
                                            <input type="file" style="display:none" accept="image/*"
                                                   id="<%= imgId%>" onchange="readURL(this)" name="<%= imgName%>"/>
                                        </span>
                                    </label>
                                    <input type="text" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-7">
                                <img class="<%= imgId%>" src="<%= urlFoto%>" alt="No image here" />
                            </div>
                        </div>
                        <%
                                imgName = "img";
                                imgLabel = "Foto";
                                imgId = "fileImg";
                                urlFoto = "";
                            }
                        %>
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
                                <label>Tipo de área</label>
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
                                <label>Emisión dominante</label>
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
                                        <a data-toggle="collapse" data-parent="#accordion2" href="#collapseSub1">Tráfico</a>
                                    </h4>
                                </div>
                                <div id="collapseSub1" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Distancia al borde</label>
                                                <input type="text" class="form-control" name="distanciaBorde" placeholder="metros" 
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getDistanciaAlBorde() : ""%>"
                                                       data-parsley-pattern="^[0-9]\d*(\.\d+)?$"
                                                       data-parsley-error-message="Nivel IV-Tráfico: Distancia al borde debe ser numérico. (1.2)"
                                                       data-parsley-errors-container="#errorsParsley"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Ancho vía</label>
                                                <input type="text" class="form-control" name="anchoVia" placeholder="metros"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getAnchoVia() : ""%>"
                                                       data-parsley-pattern="^[0-9]\d*(\.\d+)?$"
                                                       data-parsley-error-message="Nivel IV-Tráfico: Ancho vía debe ser numérico. (10.4)"
                                                       data-parsley-errors-container="#errorsParsley"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Velocidad promedio</label>
                                                <input type="text" class="form-control" name="velocidadPromedio" placeholder="Kms/h"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getVelocidadPromedio() : ""%>"
                                                       data-parsley-pattern="^[0-9]\d*(\.\d+)?$"
                                                       data-parsley-error-message="Nivel IV-Tráfico: Velocidad promedio debe ser numérico. (20.0)"
                                                       data-parsley-errors-container="#errorsParsley"/>
                                            </div>
                                        </div>

                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>% Vehiculos pesados</label>
                                                <input type="text" class="form-control" name="vehiculosPesados" placeholder="%"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getPorcentajeVehiculosPesados() : ""%>"
                                                       data-parsley-pattern="^[0-9]\d*(\.\d+)?$"
                                                       data-parsley-error-message="Nivel IV-Tráfico: Vehiculos pesados debe ser numérico. (10)"
                                                       data-parsley-errors-container="#errorsParsley"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Estado de la vía</label>
                                                <!--<input type="text" class="form-control" name="estadoVia" placeholder="via"
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getAnchoVia() : ""%>"/>-->
                                                <select class="form-control" name="estadoVia">
                                                    <option value="Pavimentada">Pavimentada</option>
                                                    <option value="Destapada">Destapada</option>
                                                </select>
                                            </div>
                                        </div>
                                                <div class="col-md-3" style="margin-left:20px; margin-top:7px">
                                            <!--                                            <label> </label>-->
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="sentidoUno" 
                                                           <%= elem.getPumuId() != null && elem.getIdMacrolocalizacion().getTraficoDiario1() ? "checked" : ""%>> Tráfico Diario Sentido 1
                                                </label>   
                                                <label>
                                                    <input type="checkbox" name="sentidoDos"
                                                           <%= elem.getPumuId() != null && elem.getIdMacrolocalizacion().getTraficoDiario2() ? "checked" : ""%>> Tráfico Diario Sentido 2
                                                </label>
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
                                                <input type="text" name="tiempoMuestreo" placeholder="Días" class="form-control "
                                                       value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getTiempoMuestreo() : ""%>"
                                                       data-parsley-pattern="^[0-9]\d*(\.\d+)?$"
                                                       data-parsley-error-message="Nivel IV-Indicativas: Tiempo de muestreo debe ser numérico. (1.2)"
                                                       data-parsley-errors-container="#errorsParsley">
                                                <ul class="parsley-errors-list"></ul>
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
                                            <input class="form-control" type="text" name="distanciaFuente" placeholder="Metros"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getDistanciaFuente() : ""%>"
                                                   data-parsley-pattern="^[0-9]\d*(\.\d+)?$"
                                                   data-parsley-error-message="Nivel IV-Industrial: Distancia de fuente debe ser numérico. (1.2)"
                                                   data-parsley-errors-container="#errorsParsley"/>
                                        </div>
                                        <div class="form-group col-md-4">
                                            <label>Dirección grados</label>
                                            <input class="form-control" type="text" name="direccionGrados" placeholder="Dirección en grados"
                                                   value="<%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getDireccionGrados() : ""%>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading accordionSub">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion2" href="#collapseSub4">Punto crítico/Rurales fondo</a>
                                    </h4>
                                </div>
                                <div id="collapseSub4" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="form-group col-md-6">
                                            <select name="puntoCritico" class="form-control">
                                                <option value="Fuente Evaluada">Fuente evaluada</option>
                                                <option value="Calle libre">Calle libre</option>
                                                <option value="Calle encajonada">Calle encajonada</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <select name="ruralesFondo" class="form-control">
                                                <option value="Cercana ciudades">Cercana ciudades</option>
                                                <option value="Regionales">Regionales</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-12">
                                            <label>Observación</label>
                                            <textarea class="form-control" name="observacionPuntoCritico" placeholder="Observación"
                                                      ><%= elem.getPumuId() != null ? elem.getIdMacrolocalizacion().getObservacionPuntoCritico() : ""%></textarea>
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
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse6">Datos de logística</a>
                    </h4>
                </div>
                <div id="collapse6" class="panel-collapse collapse">
                    <div class="panel-body">
                        <%
                            List<ItemLogistica> listaItemsLogistica = new ArrayList();
                            String nameRta = "";
                            String nameObs = "";
                            String respuesta = "";
                            String observacionItem = "";
                            if (elem.getPumuId() != null) {
                                listaItemsLogistica = itemLogisticaDao.findItemLogisticaByLogistica(elem.getIdLogistica().getId());
                            }
                            for (ItemPm i : new ItemPmJpaController(emf).findItemPmEntities()) {
                                nameRta = "rta" + i.getId();
                                nameObs = "obs" + i.getId();
                                if (listaItemsLogistica.size() != 0) {
                                    respuesta = listaItemsLogistica.get(0).getRespuesta();
                                    observacionItem = listaItemsLogistica.get(0).getObservacion();
                                    listaItemsLogistica.remove(0);
                                }
                        %>
                        <div class="row">
                            <div class="col-md-3"><%= i.getNombre()%></div>
                            <div class="col-md-3">
                                <input class="form-control" type="text" value="<%= respuesta%>"
                                       name="<%= nameRta%>" placeholder="<%= i.getDescripcion()%>"/>
                            </div>
                            <div class="col-md-6">
                                <textarea class="form-control" name="<%= nameObs%>" rows="2" placeholder="Observación"
                                          ><%= observacionItem%></textarea>
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
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse7">Criterios de microlocalización</a>
                    </h4>
                </div>
                <div id="collapse7" class="panel-collapse collapse">
                    <div class="panel-body">
                        <%
                            List<CriterioMicrolocalizacion> listaCriteriosMicrolocalizacion = new ArrayList();
                            boolean cumpleCriterio = false;
                            String observacionCriterio = "";
                            String nameCumpleCriterio = "";
                            String nameObsCriterio = "";
                            if (elem.getPumuId() != null) {
                                listaCriteriosMicrolocalizacion
                                        = criterioMicrolocalizacionDao.findCriterioByMicrolocalizacion(elem.getIdMicrolocalizacion().getId());
                            }
                            for (CriterioPm i : new CriterioPmJpaController(emf).findCriterioPmEntities()) {
                                nameCumpleCriterio = "cumpleCriterio" + i.getId();
                                nameObsCriterio = "obsCriterio" + i.getId();
                                if (listaCriteriosMicrolocalizacion.size() != 0) {
                                    cumpleCriterio = listaCriteriosMicrolocalizacion.get(0).getCumpleCriterio();
                                    observacionCriterio = listaCriteriosMicrolocalizacion.get(0).getObservacionCriterio();
                                    listaCriteriosMicrolocalizacion.remove(0);
                                }
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
                                               name="<%= nameCumpleCriterio%>"
                                               <%= cumpleCriterio ? "checked" : ""%>/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-7">
                                <textarea class="form-control" name="<%= nameObsCriterio%>" 
                                          rows="2" placeholder="Observación"><%= observacionCriterio%></textarea>
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
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse8">Personal de contacto</a>
                    </h4>
                </div>
                <div id="collapse8" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="row" id="rowsDatosAdicionales">
                            <%
                                List<DatosAdicionalesPm> listaDatosAdicionales = new ArrayList();
                                int cantDatosAdicionales = 1;

                                String nameDescripcion = "descripcionDA";
                                String nameNombres = "nombresDA";
                                String nameCelular = "celularDA";
                                String nameFijo = "fijoDA";
                                String nameEmail = "emailDA";

                                String descripcionDA = "";
                                String nombresDA = "";
                                String celularDA = "";
                                String fijoDA = "";
                                String emailDA = "";

                                if (elem.getPumuId() != null) {
                                    listaDatosAdicionales
                                            = datosAdicionalesDao.findDatosadicionalesByMicrolocalizacion(elem.getIdMicrolocalizacion().getId());

                                    for (int i = 1; i <= listaDatosAdicionales.size(); i++) {
                                        descripcionDA = listaDatosAdicionales.get(i - 1).getDecripcion();
                                        nombresDA = listaDatosAdicionales.get(i - 1).getNombreApellido();
                                        celularDA = listaDatosAdicionales.get(i - 1).getCelular().toString();
                                        fijoDA = listaDatosAdicionales.get(i - 1).getFijo().toString();
                                        emailDA = listaDatosAdicionales.get(i - 1).getEmail();

                                        nameDescripcion += i;
                                        nameNombres += i;
                                        nameCelular += i;
                                        nameFijo += i;
                                        nameEmail += i;
                                        cantDatosAdicionales = i;
                            %>
                            <div class="row">
                                <div class="form-group col-md-3">
                                    <label>Descripción</label>
                                    <input class="form-control" type="text" 
                                           name="<%= nameDescripcion%>" value="<%= descripcionDA%>"/>
                                </div>
                                <div class="form-group col-md-3">
                                    <label>Nombres</label>
                                    <input class="form-control" type="text" 
                                           name="<%= nameNombres%>" value="<%= nombresDA%>"/>
                                </div>
                                <div class="form-group col-md-2">
                                    <label>Celular</label>
                                    <input class="form-control" type="text" 
                                           name="<%= nameCelular%>" value="<%= celularDA%>"/>
                                </div>
                                <div class="form-group col-md-2">
                                    <label>Fijo</label>
                                    <input class="form-control" type="text" 
                                           name="<%= nameFijo%>" value="<%= fijoDA%>"/>
                                </div>
                                <div class="form-group col-md-2">
                                    <label>Email</label>
                                    <input class="form-control" type="text" 
                                           name="<%= nameEmail%>" value="<%= emailDA%>"/>
                                </div>
                            </div>
                            <%
                                        nameDescripcion = "descripcionDA";
                                        nameNombres = "nombresDA";
                                        nameCelular = "celularDA";
                                        nameFijo = "fijoDA";
                                        nameEmail = "emailDA";
                                    }
                                }
                            %>
                        </div>
                        <div class="row">
                            <input type="button" class="btn btn-block btn-primary" onclick="addDatoAdicional()" 
                                   value="Agregar Contacto" width="100%"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row col-md-12">
            <div id="errorsParsley"></div>
        </div>
    </div>
    <div class="modal-footer">
        <div>
            <input type="hidden" id="cantDAs" name="cantDatosAdicionales" value="<%= cantDatosAdicionales%>"/>
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
    function addDatoAdicional() {
        var indexRow = 0;
        var parent = document.querySelector('#rowsDatosAdicionales');
        indexRow = parent.childElementCount + 1;
        console.log(indexRow);
        var htmlRow = "";
        htmlRow += '<div class="row">';
        htmlRow += '<div class="form-group col-md-3">';
        htmlRow += '<label>Descripción</label>';
        htmlRow += '<input class="form-control" type="text" name="descripcionDA' + indexRow + '">';
        htmlRow += '</div>';
        htmlRow += '<div class="form-group col-md-3">';
        htmlRow += '<label>Nombres</label>';
        htmlRow += '<input class="form-control" type="text" name="nombresDA' + indexRow + '">';
        htmlRow += '</div>';
        htmlRow += '<div class="form-group col-md-2">';
        htmlRow += '<label>Celular</label>';
        htmlRow += '<input class="form-control" type="text" name="celularDA' + indexRow + '">';
        htmlRow += '</div>';
        htmlRow += '<div class="form-group col-md-2">';
        htmlRow += '<label>Fijo</label>';
        htmlRow += '<input class="form-control" type="text" name="fijoDA' + indexRow + '">';
        htmlRow += '</div>';
        htmlRow += '<div class="form-group col-md-2">';
        htmlRow += '<label>Email</label>';
        htmlRow += '<input class="form-control" type="text" name="emailDA' + indexRow + '">';
        htmlRow += '</div>';
        htmlRow += '</div>';
        $('#rowsDatosAdicionales').append(htmlRow);
        $('#cantDAs').attr('value', indexRow);
    }
    function recargaMunicipio(val, selected) {
        if (selected === undefined) {
            peticionAjax('Campanas', 'modulo=5&idDepartamento=' + val);
        }
    }
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
                RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=<%=rfid%>&index=<%=campa%>  #pumuContent', 'pumuContainer', 'closeModal();');
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

    $(document).on('change', '#cmbDepartamento', function () {
        var val = $(this).val();
        $.ajax({
            url: 'UploadImages.jsp',
            type: 'POST',
            success: function (e) {
                alerta('OK', '¡Operacion exitosa!');
                RecargaPanel('panels/campanas/campanas_agregarpm.jsp?rfid=<%=rfid%>&index=<%=campa%>  #pumuContent', 'pumuContainer', 'closeModal();');
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