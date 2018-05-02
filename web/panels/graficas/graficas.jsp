<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


<%@page import="com.statics.util.Constantes"%>
<%@page import="com.statics.vo.Parametros"%>
<%@page import="java.util.List"%>
<%@page import="com.statics.vo.Campanas"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="javax.persistence.EntityManager"%>
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
    <li>Graficas</li>
    <li class="active">lista</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">Graficas <small>Lista de graficas </small></h1>
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
                <div class="col-md-12">
                    <div class="form-group ">
                        <label>Tipo de grafica  *</label>
                        <select name="tipoGrafica" class="form-control"  required="" data-parsley-id="7052" onchange="recargarTipoGrafica(this.value)">
                            <option value="2">X punto muestral - Fechas</option>
                        </select>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
            </div>
            <div class="row" id="graf01" >
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
                        <select onchange="recargarParametros(this.value)" id="pumu_sel" name="pumu_sel" class="form-control"  required="" data-parsley-id="7052">
                        </select>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>   
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Parametros *</label>
                        <select id="para_sel" name="para_sel" style="height: 150px" class=" form-control"  required="" data-parsley-id="7052" multiple>
                            
                        </select>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>    

            </div> 
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Fecha inicial *</label>
                        <input type="text"   id="fini_txt" name="fini_txt" class="form-control datepicker"   disabled="" />
                        <ul class="parsley-errors-list" ></ul>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Fecha Final *</label>
                        <input type="text"   id="ffin_txt" name="ffin_txt" class="form-control datepicker"   disabled=""  />
                        <ul class="parsley-errors-list" ></ul>
                    </div>
                </div>
            </div>


        </div>
        <div class="modal-footer">
            <div>
                <%if (rf.getRofuOperacion().contains("A")) {%>
                <input type="hidden" name="modulo" value="3"/>
                <input type="hidden" name="rfid" value="<%=rfid%>"/>
                <button type="button"  onclick="

                        if ($('#FormAplication').parsley().isValid()) {
                            peticionAjax('Graficas', $('#FormAplication').serialize());

                        } else {
                            $('#FormAplication').submit();
                        }
                        " class="pull-right btn btn-success m-r-5 m-b-5">Graficar</button>
                <button style="visibility: hidden" id="btn_datos"  type="button"  onclick="
                        if ($('#FormAplication').parsley().isValid()) {
                            peticionAjax('Graficas', $('#FormAplication').serialize() + '&excel=1');

                        } else {
                            $('#FormAplication').submit();
                        }

                        " class="pull-right btn btn-primary m-r-5 m-b-5">Descargar</button>

                <%}%>

            </div>
        </div>
    </form>
</div>

<div class="row" id="graphiContainer"></div>
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" />
<!--<link href="assets/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet" />-->
<script src="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.js"></script>
<!--<script src="assets/plugins/bootstrap-select/bootstrap-select.min.js"></script>-->
<script>
$('#btn_datos').css('visibility', 'visible');
                                $('#carg_sel').removeAttr('required');
                                $('#carg_sel').attr('disabled', 'true');
                                $('#carg_sel').parent().parent().hide();

                                $('#fini_txt').attr('required', 'true');
                                $('#ffin_txt').attr('required', 'true');
                                $('#fini_txt').removeAttr('disabled');
                                $('#ffin_txt').removeAttr('disabled');
                                $('#FormAplication').parsley().reset();
                    $('.datepicker').datepicker({
                        todayHighlight: true,
                        language: 'es',
                        format: 'yyyy-mm-dd'
                    });
              




                    function recargarTipoGrafica(x) {
                        console.log(x)


                        switch (x) {
                            case '1':
                                $('#btn_datos').css('visibility', 'hidden');
                                $('#fini_txt').attr('disabled', 'true');
                                $('#ffin_txt').attr('disabled', 'true');
                                $('#fini_txt').removeAttr('required');
                                $('#ffin_txt').removeAttr('required');

                                $('#carg_sel').attr('required', 'true');
                                $('#carg_sel').removeAttr('disabled');
                                $('#carg_sel').parent().parent().show();
                                $('#FormAplication').parsley().reset();
                                break;
                            case '2':

                                $('#btn_datos').css('visibility', 'visible');
                                $('#carg_sel').removeAttr('required');
                                $('#carg_sel').attr('disabled', 'true');
                                $('#carg_sel').parent().parent().hide();

                                $('#fini_txt').attr('required', 'true');
                                $('#ffin_txt').attr('required', 'true');
                                $('#fini_txt').removeAttr('disabled');
                                $('#ffin_txt').removeAttr('disabled');
                                $('#FormAplication').parsley().reset();
                                break;

                            default:

                                break;
                        }


                    }
                    function recargarEstaciones(x) {
                        console.log(x);
                        peticionAjax('Graficas', 'modulo=1&index=' + x + '');

                    }
                    function recargarParametros(x) {
                        console.log(x);
                        peticionAjax('Graficas', 'modulo=2&index=' + x + '');

                    }
                    var cont = 0;

                    function crearGrafica(x) {
                        $("#graphiContainer").append(' <div  id="graphiContent' + cont + '"></div>');
                        $("#graphiContent" + cont + "").load("panels/graficas/graficas_crear.jsp?var=" + x + "")


                        cont++;
                    }

                    function  verDatosTabla(idsess, paramsess, title) {
                        console.log('-->verDatosTabla() ')

                        modalDialog('Datos(' + title + ')', 'panels/graficas/graficas_crear_tabla.jsp', 'idsess=' + idsess + '&paramsess=' + paramsess + '', 'large');


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