<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>

<%@page import="com.statics.vo.ParametroLabels"%>
<%@page import="com.statics.vo.Parametros"%>
<%@page import="com.statics.dao.ParametrosJpaController"%>
<%@page import="com.statics.util.Fechas"%>
<%@page import="com.statics.dao.UsuariosJpaController"%>
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
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");

            String rfid = o.getvariable("rfid");
            Rolfuncionalidad rf = null;

            if (!rfid.isEmpty()) {
                rf = new RolfuncionalidadJpaController(emf).findRolfuncionalidad(Integer.parseInt(rfid));

            }
            if (rf != null && rf.getRofuId() != null) {

%>
<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
    <li>Inicio</li>
    <li>Contaminantes</li>
    <li class="active">lista</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Contaminantes <small>Lista de Contaminantes </small></h1>
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
    <div class="panel-body">

        <table id="data-table" class="table table-striped table-bordered nowrap" width="100%">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>Codigo</th>
                    <th>Descripcion</th>
                    <th>Unidad de medida</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <%       
                    ParametrosJpaController parametrosJpaController = new ParametrosJpaController(emf);
                    if (!parametrosJpaController.findParametrosEntities().isEmpty()) {
                        for (Parametros u : parametrosJpaController.findParametrosEntities()) {
                          if(u.getParaEstado()!=0){  
                %>
                <tr >
                    <td>
                        <%=o.notEmpty(u.getParaId().toString()) %>
                    </td>
                    <td>
                        <%=o.notEmpty(u.getPareNombre()) %>
                    </td>
                    <td>
                        <b>
                        <%=u.getParaCodigo()%>
                        </b>
                    </td>
                    <td>
                        <%=o.notEmpty(u.getPareDescripcion()) %>
                    </td>
                    <td>
                        <%= !u.getParametroFactorconversionList().isEmpty()?
                            u.getParametroFactorconversionList().get(0).getIdUnidadMedida().getDescripcion():""
                        %>
                    </td>
                    <td>           
                     
                        <% if (rf.getRofuOperacion().contains("M")) {%>
                        <a onclick="RecargaPanel('panels/contaminantes/contaminantes_nuevo.jsp?rfid=<%=rfid%>&index=<%=u.getParaId() %>', 'content')" class="btn btn-warning btn-icon btn-circle" title="Editar"><i class="fa fa-pencil"></i></a>
                        <%}

                           if (rf.getRofuOperacion().contains("E")) {%>
                        <a onclick=" peticionAjaxConfirm('Contaminantes', 'index=<%=u.getParaId()%>&rfid=<%=rfid%>&modulo=2','¿Esta seguro de eliminar este parametro?');" class="btn btn-danger btn-icon btn-circle" title="Remover"><i class="fa fa-remove"></i></a>
                            <%} %>
                    </td>

                </tr>
                <%}
}
                    }
                %>

            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <div>
            <%if (rf.getRofuOperacion().contains("A")) {%>
            <button type="button" onclick="RecargaPanel('panels/contaminantes/contaminantes_nuevo.jsp?rfid=<%=rfid%>', 'content')" class="pull-right btn btn-primary m-r-5 m-b-5">Nuevo</button>
            <%}%>

        </div>
    </div>
</div>

<script src="assets/plugins/DataTables/media/js/jquery.dataTables.js"></script>
<script src="assets/plugins/DataTables/media/js/dataTables.bootstrap.min.js"></script>
<script src="assets/plugins/DataTables/extensions/Responsive/js/dataTables.responsive.min.js"></script>
<script src="assets/js/table-manage-responsive.demo.min.js"></script>
<script>
                $(document).ready(function () {
//			App.init();
                    TableManageResponsive.init();
                });
</script>
<%            } else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp'
</script>
<%
    }
} else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp'
</script>
<%        }
    } catch (Exception ex) {
        ex.printStackTrace();
                }%>