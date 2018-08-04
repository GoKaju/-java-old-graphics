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
%>
<form id="formNuevoGrupo" action="Login" method="POST" data-parsley-validate="true">
    <div class="panel-body">
        <div class="form-group">
            <label>Nombre</label>
            <input type="text" class="form-control" placeholder="" name="nombre">
        </div>
        <div class="form-group ">
            <label>Descripcion *</label>
            <textarea name="descripcion" class="form-control" placeholder="Descripcion" rows="5" required data-parsley-id="7052"></textarea>
            <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
        </div>


    </div>
    <div class="modal-footer">
        <div>
            <input type="hidden" name="modulo" value="nuevoGrupo"/>
            <input type="hidden" name="rfid" value="<%=rfid%>"/>
            <label onclick="

                    if ($('#formNuevoGrupo').parsley().isValid()) {
                            peticionAjax('Usuarios', $('#formNuevoGrupo').serialize());
                    } else {
                        $('#formNuevoGrupo').submit();
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