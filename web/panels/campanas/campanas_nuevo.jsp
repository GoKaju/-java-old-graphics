<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


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
            Campanas elem = new Campanas();
            if (!index.isEmpty()) {
                elem = new CampanasJpaController(emf).findCampanas(Integer.parseInt(index));
            }


%>
<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
    <li>Inicio</li>
    <li>Administracion</li>
    <li class="">Campañas</li>
    <li class="active">Nuevo</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Campañas <small> <%=index.isEmpty()?"Nueva":"Editar" %> campaña</small></h1>
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
                <div class="col-md-6">
                    <div class="form-group ">
                        <label>Nombre  *</label>
                        <input type="text" name="Nombre" placeholder="Nombre" class="form-control " value="<%=elem.getCampId()!= null ? elem.getCampNombre(): ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group ">
                        <label>Cliente  *</label>
                        <input type="text" name="cliente" placeholder="Cliente" class="form-control " 
                               value="<%=elem.getCampId()!= null ? elem.getCliente(): ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
                    <div class="col-md-12">
                    <div class="form-group ">
                        <label>Descripcion *</label>
                        <textarea name="descripcion" class="form-control" placeholder="Descripcion" rows="5" required data-parsley-id="7052"><%=elem.getCampId()!= null ? elem.getCampDescripcion(): ""%></textarea>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
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
                            peticionAjax('Campanas', $('#FormAplication').serialize());

                        } else {
                            $('#FormAplication').submit();
                        }
                        " class="pull-right btn btn-success m-r-5 m-b-5">Continuar</button>
                <button type="button"  onclick="RecargaPanel('panels/campanas/campanas.jsp?rfid=<%=rfid%>', 'content')" class="pull-right btn btn-default m-r-5 m-b-5">Atras</button>

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