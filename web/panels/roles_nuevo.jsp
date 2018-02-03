<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>

<%@page import="com.statics.dao.FuncionalidadesJpaController"%>
<%@page import="com.statics.vo.Funcionalidades"%>
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


%>
<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
    <li>Inicio</li>
    <li>Administracion</li>
    <li class="">Roles</li>
    <li class="active">Nuevo rol</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Nuevo rol <small></small></h1>
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
            <div class="col-md-4">
                <div class="form-group ">
                    <label>Nombre</label>
                    <input type="text" name="Nombre" placeholder="Nombre" class="form-control"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                </div>
            </div> 
            
              <table id="data-table" class="table table-striped table-bordered nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>Descripción</th>
                                        <th>Codigo</th>
                                        
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                FuncionalidadesJpaController fdao = new FuncionalidadesJpaController(emf);
                                    if(!fdao.findFuncionalidadesEntities().isEmpty()){
                                        for(Funcionalidades f :fdao.findFuncionalidadesEntities()){
                                    %>
                                    <tr >
                                    
                                            
                                        <td>
                                            <div class="checkbox">
                                                <label><input type="checkbox" id="mincheck<%=f.getFuncId() %>" name="funcheck[]" data-parsley-mincheck="1" required="" value="<%=f.getFuncId() %>"  data-parsley-multiple="mincheck" > <%=o.notEmpty(f.getFuncDescripcion()) %></label><ul class="parsley-errors-list" id="parsley-id-multiple-mincheck"></ul></div>
                                            </td>
                                        <td><%=o.notEmpty(f.getFuncCodigo()) %></td>
                                      
                                      
                                    </tr>
                                 <%}}
                                 %>
                                
                                </tbody>
                            </table>

    </div>
    <div class="modal-footer">
        <div>
            <input type="hidden" name="modulo" value="nuevo"/>
            <input type="hidden" name="rfid" value="<%=rfid%>"/>
            <button type="button"  onclick="

                        if ($('#FormAplication').parsley().isValid()) {
                            peticionAjax('Roles', $('#FormAplication').serialize());

                        } else {
                            $('#FormAplication').submit();
                        }
                        " class="pull-right btn btn-success m-r-5 m-b-5">Guardar</button>
            <button type="button"  onclick="RecargaPanel('panels/roles.jsp?rfid=<%=rfid%>','content')" class="pull-right btn btn-default m-r-5 m-b-5">Atras</button>

        </div>
    </div>
        </form>
</div>
<script>
    $(document).ready(function () {
        App.init();
//			TableManageResponsive.init();
    });
</script>
<%        } else {
            %>
            <script type="text/javascript">
                location.href='logout.jsp'
            </script>
            <%
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }%>