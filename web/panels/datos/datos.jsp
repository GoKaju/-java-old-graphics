<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


<%@page import="com.statics.util.Fechas"%>
<%@page import="com.statics.vo.Cargas"%>
<%@page import="com.statics.vo.PuntoMuestral"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="com.statics.vo.Campanas"%>
<%@page import="com.statics.dao.CampanasJpaController"%>
<%@page import="com.statics.vo.Estaciones"%>
<%@page import="com.statics.dao.EstacionesJpaController"%>
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
    <li>Datos</li>
    <li class="active">lista</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Datos <small>Lista de cargas </small></h1>
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
                    <th>Campaña</th>
                    <th>Punto Muestral</th>
                    <th>Nombre</th>
                    <th>Descripcion</th>
                    <th>Total datos</th>
                    <th>Exito</th>
                    <th>Error</th>
                    <th>Fecha inicial carga</th>
                    <th>Fecha cambio carga</th>
                    <th>Ultimo dato cargado</th>
                    <!--<th></th>-->
                </tr>
            </thead>
            <tbody>
                <%//   filtrar por grupo
                    EntityManager em = emf.createEntityManager();
                    TypedQuery<Campanas> consulta = em.createNamedQuery("Campanas.findByGroupId", Campanas.class);
                    consulta.setParameter("grupId", user.getGrupoUsuariosList().get(0).getGrupId().getGrupId());
                    List<Campanas> lista = consulta.getResultList();
                    em.close();

                    if (!lista.isEmpty()) {
                        for (Campanas u : lista) {
                            if (u.getEstaId() != 0) {

                                for (PuntoMuestral pumu : u.getPuntoMuestralList()) {
                                    
                                    for(Cargas c : pumu.getCargasList()){
                                     
                %>
                <tr >
                    <td>
                        <%=o.notEmpty(c.getCargId().toString())%>
                    </td>
                    <td>
                        <%=o.notEmpty(u.getCampNombre())%>
                    </td>
                    <td>
                        <%=o.notEmpty(pumu.getPumuNombre())%>
                    </td>
                    <td>
                        <b> <%=o.notEmpty(c.getCargArchivo())%></b>
                    </td>
                    <td>
                        <%=o.notEmpty(c.getCargDescripcion())%>
                    </td>
                    <td>
                        <%=c.getCargCantidadtotal()%>
                    </td>
                    <td>
                        <%=c.getCargExitosos()%>
                    </td>
                    <td>
                        <%=c.getCargErrores()%>
                    </td>
                    <td>
                        <%=Fechas.DevuelveFormatoHora(c.getCargFechainicial())%>
                    </td>
                    <td>
                        <%=Fechas.DevuelveFormatoHora(c.getCargFechacambio())%>
                    <td>
                        <%=Fechas.DevuelveFormatoHora(c.getUltimaFechacargada())%>
                    </td>
                
                </tr>
                <%  }

}
                            }
                        }
                    }
                %>

            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <div>
            <%if (rf.getRofuOperacion().contains("A")) {%>
            <button type="button" onclick="RecargaPanel('panels/datos/datos_nueva_carga.jsp?rfid=<%=rfid%>', 'content')" class="pull-right btn btn-primary m-r-5 m-b-5">Cargar</button>
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

                function  verDetallePumu(x, campana) {

                    modalDialog('Ver Puntos Muestrales(' + campana + ')', 'panels/campanas/campanas_verpm_dial.jsp', 'campa=' + x + '', 'large');


                }
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