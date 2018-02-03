<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


<%@page import="java.util.List"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.TypedQuery"%>
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


%>
<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
    <li>Inicio</li>
    <li>Datos</li>
    <li class="active">Nueva carga</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Nueva carga </h1>
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

                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Campaña  *</label>

                        <select name="Campana" class="form-control"  required="" data-parsley-id="7052" onchange="recargarEstaciones(this.value)">
                            <option value="">Seleccone...</option>
                            <%  EntityManager em = emf.createEntityManager();
                                TypedQuery<Campanas> consulta = em.createNamedQuery("Campanas.findByGroupId", Campanas.class);
                                consulta.setParameter("grupId", user.getGrupoUsuariosList().get(0).getGrupId().getGrupId());
                                List<Campanas> lista = consulta.getResultList();

                                if (!lista.isEmpty()) {

                                    for (Campanas s : lista) {
                                        String sel = "";


                            %>

                            <option <%=sel%> value="<%=o.notEmpty(s.getCampId().toString())%>" title=""><%=o.notEmpty(s.getCampNombre())%></option>

                            <%
                                    }
                                }
                            %>
                        </select>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Punto Muestral  *</label>

                        <select id="pumu_sel" name="pumu_sel" class="form-control"  required="" data-parsley-id="7052">
                    
                        </select>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
             
                <div class="col-md-12">
                    <div class="form-group ">
                        <label>Descripcion *</label>
                        <textarea name="Descripcion" class="form-control" placeholder="Observaciones" rows="4" required data-parsley-id="7052"></textarea>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>    
                <div class="col-md-12">
                    <div class="form-group ">
                        <label>Observaciones </label>
                        <textarea name="Observaciones" class="form-control" placeholder="Observaciones" rows="4"  data-parsley-id="7052"></textarea>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>    


            </div> 
        </div> 

        <div class="modal-footer">
            <div>
                <input type="hidden" name="modulo" value="1"/>
                <input type="hidden" name="rfid" value="<%=rfid%>"/>
                <button type="button"  onclick="

                        if ($('#FormAplication').parsley().isValid()) {
                            peticionAjax('Cargas', $('#FormAplication').serialize());

                        } else {
                            $('#FormAplication').submit();
                        }
                        " class="pull-right btn btn-success m-r-5 m-b-5">Continuar</button>
                <button type="button"  onclick="RecargaPanel('panels/datos/datos.jsp?rfid=<%=rfid%>', 'content')" class="pull-right btn btn-default m-r-5 m-b-5">Atras</button>

            </div>
        </div>
    </form>
</div>
<link rel="stylesheet" href="assets/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" />              
<script src="assets/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script>
           
          function recargarEstaciones(x){
              console.log(x)
              peticionAjax('Cargas','modulo=2&index='+x+'');
              
          }
           
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