<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>

<%@page import="com.statics.vo.UnidadMedida"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Query"%>
<%@page import="com.statics.vo.ParametroLabels"%>
<%@page import="com.statics.dao.ParametrosJpaController"%>
<%@page import="com.statics.vo.Parametros"%>
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
            String index = o.getvariable("index");
            Parametros elem = new Parametros();
            if (!index.isEmpty()) {
                elem = new ParametrosJpaController(emf).findParametros(Integer.parseInt(index));

            }


%>
<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
    <li>Inicio</li>
    <li>Administracion</li>
    <li class="">Contaminantes</li>
    <li class="active">Nuevo</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Contaminantes <small><%=index.isEmpty() ? "Nuevo" : "Editar"%> Contaminante</small></h1>
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
                        <label>Nombre contaminante *</label>
                        <input type="text" name="Nombre" placeholder="Nombre" class="form-control" value="<%=elem.getParaId() != null ? elem.getPareNombre() : ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-2">
                    <div class="form-group ">
                        <label>Codigo*</label>
                        <input type="number" name="Codigo" placeholder="Codigo" class="form-control" value="<%=elem.getParaId() != null ? elem.getParaCodigo() : ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-4" hidden>
                    <div class="form-group ">
                        <label>Labels *</label> <br />
                        <select class="form-control" name="labels" multiple data-role="tagsinput" required="" data-parsley-id="7052" >
                            <%if (elem.getParaId() != null) {
                                    for (ParametroLabels pl : elem.getParametroLabelsList()) {%>
                            <option value="<%=o.notEmpty(pl.getPalaLabel())%>"><%=o.notEmpty(pl.getPalaLabel())%></option>
                            <%}
                            } else {%>
                            <option value="label" selected>label</option>
                            <%}%>
                        </select>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-2">
                    <div class="form-group ">
                        <label>Unidad *</label>
                        <select type="text" id="horario" name="unidadDeseada" class="form-control" >
                            <%
                                EntityManager em = emf.createEntityManager();
                                Query q = em.createNativeQuery("select * from unidad_medida", UnidadMedida.class);
                                List<UnidadMedida> listaUnidades = q.getResultList();
                                Integer idUnidad = 0;
                                if (elem.getParaId() != null) {
                                    if(!elem.getParametroFactorconversionList().isEmpty())
                                    idUnidad = elem.getParametroFactorconversionList().get(0).getIdUnidadMedida().getId();
                                }
                                if (!listaUnidades.isEmpty()) {
                                    String sel = "";
                                    for (UnidadMedida s : listaUnidades) {
                                        if (idUnidad == s.getId()) {
                                            sel = "selected";
                                        } else {
                                            sel = "";
                                        }
                            %>
                            <option <%=sel%> value="<%=o.notEmpty(s.getId().toString())%>" title=""><%=o.notEmpty(s.getDescripcion())%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                        <ul class="parsley-errors-list" ></ul>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Color borde</label>
                        <input class="form-control" type="text" name="colorBorde" value="<%=elem.getParaId()!=null?elem.getPareColorBorde():""%>" placeholder="Borde"/>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Color fondo</label>
                        <input class="form-control" type="text" name="colorFondo" value="<%=elem.getParaId()!=null?elem.getPareColorBackground():""%>" placeholder="Background"/>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group ">
                        <label>Descripcion *</label>
                        <textarea name="descripcion" class="form-control" placeholder="Descripcion" rows="5" required data-parsley-id="7052"><%=elem.getParaId() != null ? elem.getPareDescripcion() : ""%></textarea>
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
                            peticionAjax('Contaminantes', $('#FormAplication').serialize());

                        } else {
                            $('#FormAplication').submit();
                        }
                        " class="pull-right btn btn-success m-r-5 m-b-5">Guardar</button>
                <button type="button"  onclick="RecargaPanel('panels/contaminantes/contaminantes.jsp?rfid=<%=rfid%>', 'content')" class="pull-right btn btn-default m-r-5 m-b-5">Atras</button>

            </div>
        </div>
    </form>
</div>
<link rel="stylesheet" href="assets/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" />              
<script src="assets/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script>
                    $(document).ready(function () {
                        $("input[name=colorBorde]").colorpicker();
                        $("input[name=colorFondo]").colorpicker();
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