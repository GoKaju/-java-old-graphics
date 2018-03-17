<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


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
    <li class="">Nuevo</li>
    <li class="active">Puntos muestrales</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Campañas <small><%=index.isEmpty() ? "Agregar" : "Editar"%> puntos de monitoreo</small></h1>
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
    <div id="pumuContainer" class="panel-body">

        <div id="pumuContent" class="row">
            <div class=" col-md-12 table-responsive">
                <table class="table table-condensed table-hover table-striped">
                    <caption>Lista de puntos de monitoreo campaña <%=o.notEmpty(elem.getCampNombre())%></caption>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Estacion</th>
                            <th>Nombre</th>
                            <th>descripcion</th>
                            <th>Long</th>
                            <th>Lat</th>
                            <!--<th>Fecha Inicial</th>-->
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        <% for (PuntoMuestral p : elem.getPuntoMuestralList()) {%>
                        <tr>
                            <td><%=p.getPumuId()%></td> 
                            <td><%=p.getEstaId().getEstaNombre()%></td> 
                            <td><%=p.getPumuNombre()%></td> 
                            <td><%=p.getPumuDescripcion()%></td> 
                            <td><%=p.getPumuLong()%></td> 
                            <td><%=p.getPumuLat()%></td> 
                            <td>           


                                <a onclick="   modalDialog('Editar Punto Muestral(<%=o.notEmpty(elem.getCampNombre())%>)', 'panels/campanas/campanas_agregarpm_dial.jsp', 'campa=<%=index%>&rfid=<%=rfid%>&index=<%=p.getPumuId()%>', 'large')" class="btn btn-warning btn-icon btn-circle" title="Editar"><i class="fa fa-pencil"></i></a>
                                    <% if (p.getCargasList().isEmpty()) {%>
                                <a onclick=" peticionAjaxConfirm('Campanas', 'index=<%=p.getPumuId()%>&rfid=<%=rfid%>&modulo=4', '¿Esta seguro de remover este punto muestral?');" class="btn btn-danger btn-icon btn-circle" title="Remover"><i class="fa fa-remove"></i></a>
                                    <%}%>

                            </td>
                        </tr>
                        <%}%>

                    </tbody>


                </table>

            </div>                    </div>
    </div> 

    <div class="modal-footer">
        <div>
            <input type="hidden" name="modulo" value="1"/>
            <input type="hidden" name="rfid" value="<%=rfid%>"/>
            <input type="hidden" name="index" value="<%=index%>"/>
            <button type="button"  onclick="
                    modalDialog('Agregar Punto Muestral(<%=o.notEmpty(elem.getCampNombre())%>)', 'panels/campanas/campanas_agregarpm_dial.jsp', 'campa=<%=index%>&rfid=<%=rfid%>', 'large')
                    " class="pull-right btn btn-success m-r-5 m-b-5">Agregar</button>
            <button type="button"  onclick="RecargaPanel('panels/campanas/campanas_nuevo.jsp?rfid=<%=rfid%>&index=<%=index%>', 'content')" class="pull-right btn btn-default m-r-5 m-b-5">Atras</button>

        </div>
    </div>
</div>

<%        } else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<%        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }%>