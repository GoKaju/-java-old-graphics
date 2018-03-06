<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


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
<form id="FormModalAplication" action="Login" method="POST" class="margin-bottom-0" data-parsley-validate="true">
    <div class="panel-body">
        <div class="row">   
            <div class="divider"></div>
            <div class="col-md-4">
                <div class="form-group ">
                    <label>Nombre del punto *</label>
                    <input type="text" name="nombrePunto" placeholder="Nombre del punto" 
                           class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuNombre() : ""%>"  
                           required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                </div>
            </div>
            <div class="col-md-8">
                <div class="form-group ">
                    <label>Cliente *</label>
                    <input type="text" name="cliente" placeholder="Cliente" 
                           class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuNombre() : ""%>"  
                           required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group">
                    <label>Descripción del punto</label>
                    <textarea class="form-control" type="text" name="descripcion"
                              placeholder="Descripción"></textarea>
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
                        <option <%=sel%> value="<%=o.notEmpty(m.getNombre().toString())%>"><%=o.notEmpty(m.getNombre())%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
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
                        <option <%=sel%> value="<%=o.notEmpty(m.getNombre().toString())%>"><%=o.notEmpty(m.getNombre())%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Dirección</label>
                    <input class="form-control" type="text" name="direccion"/>
                </div>
            </div>
            <div class="col-md-4">
                <label>Foto1</label>
                <div class="input-group">
                    <label class="input-group-btn" for="fileImg1">
                        <span class="btn btn-primary">
                            Examinar&hellip; <input id="fileImg1" 
                                                    class="fileInputs" 
                                                    type="file" 
                                                    style="display: none;" 
                                                    accept="image/*"
                                                    onchange="readURL(event)">
                        </span>
                    </label>
                    <input type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="col-md-8">
                <div class="fileImg1" src="" alt="No image here"></div>
            </div>
            <div class="col-md-12">
                <label for="fileImg2">Foto2</label>
                <input id="fileImg2" type="file" class="btn btn-default" value="Subir" name="foto2" accept="image/*"/>
                <div clas="img1"></div>
            </div>
            <div class="col-md-12">
                <label>Foto3</label>
                <input type="file" class="btn btn-default" value="Subir" name="foto3"/>
                <div clas="img1"></div>
            </div>
            <div class="col-md-12">
                <label>Foto4</label>
                <input type="file" class="btn btn-default" value="Subir" name="foto4"/>
                <div clas="img1"></div>
            </div>

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
                        <option <%=sel%> value="<%=o.notEmpty(m.getNombre().toString())%>"><%=o.notEmpty(m.getNombre())%></option>
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
                                if (elem.getPumuId() != null && elem.getIdMacrolocalizacion().getIdTiempo().getNombre().equals(m)) {
                                    sel = "selected";
                                }
                        %>
                        <option <%=sel%> value="<%=o.notEmpty(m.getNombre().toString())%>"><%=o.notEmpty(m.getNombre())%></option>
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
                                if (elem.getPumuId() != null && elem.getIdMacrolocalizacion().getIdEmisionDominante().getNombre().equals(m)) {
                                    sel = "selected";
                                }
                        %>
                        <option <%=sel%> value="<%=o.notEmpty(m.getNombre().toString())%>"><%=o.notEmpty(m.getNombre())%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group">
                    <label>Observaciones lvl i-iv</label>
                    <textarea class="form-control" name="obslvl1"></textarea>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Distancia al borde</label>
                    <input type="number" class="form-control" name="distanciaBorde" placeholder="Kms"/>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Ancho vía</label>
                    <input type="number" class="form-control" name="anchoVia" placeholder="Mts"/>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Velocidad promedio</label>
                    <input type="number" class="form-control" name="velocidadPromedio" placeholder="Kms/h"/>
                </div>
            </div>
            <div class="col-md-3">
                <!--<label>Sentidos de trafico</label>-->
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="sentidoUno"> Trafico sentido 1
                    </label>
                    <label>
                        <input type="checkbox" name="sentidoDos"> Trafico sentido 2
                    </label>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group ">
                    <label>Estacion  *</label>
                    <select name="Estacion" class="form-control"  required="" data-parsley-id="7052">
                        <%
                            for (Estaciones s : new EstacionesJpaController(emf).findEstacionesEntities()) {
                                String sel = "";
                                if (elem.getPumuId() != null && elem.getEstaId().equals(s)) {
                                    sel = "selected";
                                }
                        %>
                        <option <%=sel%> value="<%=o.notEmpty(s.getEstaId().toString())%>" title=""><%=o.notEmpty(s.getEstaNombre())%></option>
                        <%}
                        %>
                    </select>
                    <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                </div>
            </div> 
            <div class="col-md-3">
                <div class="form-group ">
                    <label>Longitud <i class="glyphicon glyphicon-question-sign info" onclick="alertaCoordenadas()"></i></label>
                    <input type="text" name="Longitud" placeholder="Longitud" class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuLong() : ""%>"  data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group ">
                    <label>Latitud <i class="glyphicon glyphicon-question-sign info" onclick="alertaCoordenadas()"></i></label>
                    <input type="text" name="Latitud" placeholder="Latitud" class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuLat() : ""%>"  data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group ">
                    <label>Descripcion *</label>
                    <textarea name="descripcion" class="form-control" placeholder="Descripcion" rows="5" required data-parsley-id="7052"><%=elem.getPumuId() != null ? elem.getPumuDescripcion() : ""%></textarea>
                    <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                </div>
            </div>    


        </div>

    </div>

    <div class="modal-footer">
        <div>
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
<script type="text/javascript">
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
    function readURL(event) {
        var reader = new FileReader();
        reader.onload = function () {
            var divImage = $("." + event.target.id);
            divImage.src = reader.result;
        }
        console.log(event);
        console.log(divImage);
        reader.readAsDataURL(event.target.files[0]);
    }
    $(document).on('change', ':file', function () {
        var input = $(this),
                numFiles = input.get(0).files ? input.get(0).files.length : 1,
                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

    // We can watch for our custom `fileselect` event like this
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