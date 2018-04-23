<%-- 
    Document   : clientes_agregar
    Created on : Apr 7, 2018, 1:57:54 PM
    Author     : FoxHG
--%>

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
            String index = o.getvariable("campa");
//            if (!index.isEmpty()) {
//                elem = new PuntoMuestralJpaController(emf).findPuntoMuestral(Integer.parseInt(index));
//            }
%>
<form id="formNuevoCliente" action="Login" method="POST" data-parsley-validate="true">
    <div class="panel-body">
        <div class="form-group">
            <label>Nombre</label>
            <input type="text" class="form-control" placeholder="Razón social" name="nombreCliente">
        </div>
        <div class="form-group">
            <label>Dirección</label>
            <input type="text" class="form-control" placeholder="Dirección" name="direccionCliente">
        </div>
        <div class="form-group">
            <label>Email</label>
            <input type="text" class="form-control" placeholder="Email" name="emailCliente">
        </div>
        <div class="form-group">
            <label>NIT</label>
            <input type="text" class="form-control" placeholder="NIT" name="nitCliente">
        </div>
        <div class="form-group">
            <label>Telefono</label>
            <input type="text" class="form-control" placeholder="Telefono" name="telefonoCliente">
        </div>
    </div>
    <div class="modal-footer">
        <div>
            <input type="hidden" name="modulo" value="6"/>
            <input type="hidden" name="rfid" value="<%=rfid%>"/>
            <input type="hidden" name="index" value="<%=index%>"/>
            <label onclick="

                    if ($('#formNuevoCliente').parsley().isValid()) {
                        peticionAjax('Campanas', $('#formNuevoCliente').serialize());
                    } else {
                        $('#formNuevoCliente').submit();
                    }
                     " class="pull-right btn btn-success m-r-5 m-b-5">Guardar</label>
            <button type="button"  onclick="closeModal()" class="pull-right btn btn-default m-r-5 m-b-5">Cerrar</button>

        </div>
    </div>
</form>
<%        } else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<%        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }%>