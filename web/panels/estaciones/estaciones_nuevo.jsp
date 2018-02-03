<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


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
            Estaciones elem = new Estaciones();
            if (!index.isEmpty()) {
                elem = new EstacionesJpaController(emf).findEstaciones(Integer.parseInt(index));

            }


%>
<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
    <li>Inicio</li>
    <li>Administracion</li>
    <li class="">Estaciones</li>
    <li class="active">Nuevo</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Estaciones <small><%=index.isEmpty()?"Nueva":"Editar" %> estacion</small></h1>
<!-- end page-header -->

<div class="panel panel-inverse">
    <div class="panel-heading">
        <div class="panel-heading-btn">
            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-success" data-click="panel-reload"><i class="fa fa-repeat"></i></a>
            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-warning" data-click="panel-collapse"><i class="fa fa-minus"></i></a>
            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-danger" data-click="panel-remove"><i class="fa fa-times"></i></a>
        </div>
        <h2 class="panel-title">&zwj;</h2>
    </div>
    <form id="FormAplication" action="Login" method="POST" class="margin-bottom-0" data-parsley-validate="true">
        <div class="panel-body">
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group ">
                        <label>Nombre  *</label>
                        <input type="text" name="Nombre" placeholder="Nombre" class="form-control " value="<%=elem.getEstaId()!= null ? elem.getEstaNombre(): ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-4">
                    <div class="form-group ">
                        <label>Separador  *</label>
                        
                        <select name="separador" class="form-control"  required="" data-parsley-id="7052">
                                <%
                                for(Separadores s : new SeparadoresJpaController(emf).findSeparadoresEntities()){
                                    String sel = "";
                                if(elem.getEstaId()!= null && elem.getSepaId().equals(s)){
                                sel="selected";
                                }
                                %>
                                
                                <option <%=sel %> value="<%=o.notEmpty(s.getSepaId().toString()) %>"><%=o.notEmpty(s.getSepaDescripcion()) %>(<%=o.notEmpty(s.getSepaSeparador())%>)</option>
                                
                                <%}
                                %>
                            </select>
                       <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-4">
                    <div class="form-group ">
                        <label>Formato fecha  *</label>
                        
                        <select name="formatofecha" class="form-control"  required="" data-parsley-id="7052">
                                <%
                                for(Formatofechas s : new FormatofechasJpaController(emf).findFormatofechasEntities()){
                                     String sel = "";
                                if(elem.getEstaId()!= null && elem.getFofeId().equals(s)){
                                sel="selected";
                                }
                                %>
                                
                                <option <%=sel %> value="<%=o.notEmpty(s.getFofeId().toString()) %>"><%=o.notEmpty(s.getFofeLabel()) %></option>
                                
                                <%}
                                %>
                            </select>
                       <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-8">
                    <div class="form-group ">
                        <label>Ruta carga servidor  *</label>
                        <input type="text" name="rutaserver" placeholder="ejem: \\home\\" class="form-control " value="<%=elem.getEstaId()!= null ? elem.getEstaRutacargaservidor(): ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-4">
                    <div class="form-group ">
                        <label>Carpeta carga servidor  *</label>
                        <input type="text" name="carpetaserver" placeholder="ejem: carpeta\\" class="form-control " value="<%=elem.getEstaId()!= null ? elem.getEstaCarpetacarga(): ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
            </div> 
            </div> 

        <div class="modal-footer">
            <div>
                <input type="hidden" name="modulo" value="1"/>
                <input type="hidden" name="rfid" value="<%=rfid%>"/>
                <input type="hidden" name="index" value="<%=index%>"/>
                <button type="button"  onclick="

                        if ($('#FormAplication').parsley().isValid()) {
                            peticionAjax('Estaciones', $('#FormAplication').serialize());

                        } else {
                            $('#FormAplication').submit();
                        }
                        " class="pull-right btn btn-success m-r-5 m-b-5">Guardar</button>
                <button type="button"  onclick="RecargaPanel('panels/estaciones/estaciones.jsp?rfid=<%=rfid%>', 'content')" class="pull-right btn btn-default m-r-5 m-b-5">Atras</button>

            </div>
        </div>
    </form>
</div>
                <link rel="stylesheet" href="assets/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" />              
  <script src="assets/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script>
                    $(document).ready(function () {
//        App.init();
//			TableManageResponsive.init();
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