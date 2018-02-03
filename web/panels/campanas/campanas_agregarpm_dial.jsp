<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


<%@page import="com.statics.dao.PuntoMuestralJpaController"%>
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
            String campa = o.getvariable("campa");
            PuntoMuestral elem = new PuntoMuestral();
            Campanas campana = new Campanas();
            if (!index.isEmpty()) {
                elem = new PuntoMuestralJpaController(emf).findPuntoMuestral(Integer.parseInt(index));
            }
            
if(!campa.isEmpty()){
    campana = new CampanasJpaController(emf).findCampanas(Integer.parseInt(campa));

}
            


%>
 <form id="FormModalAplication" action="Login" method="POST" class="margin-bottom-0" data-parsley-validate="true">
        <div class="panel-body">
            <div class="row">




                <div class="divider"></div>
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Nombre  *</label>
                        <input type="text" name="Nombre" placeholder="Nombre" class="form-control " value="<%=elem.getPumuId()!= null ? elem.getPumuNombre(): ""%>"  required="" data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Estacion  *</label>

                        <select name="Estacion" class="form-control"  required="" data-parsley-id="7052">
                            <%

                                for (Estaciones s : new EstacionesJpaController(emf).findEstacionesEntities()) {
                                    String sel = "";
                                    if (elem.getPumuId() != null && elem.getEstaId().equals(s)) {
                                        sel = "selected";
                                    }
                            %>

                            <option <%=sel%> value="<%=o.notEmpty(s.getEstaId().toString())%>" title=""><%=o.notEmpty(s.getEstaNombre())%></option>

                            <%}
                            %>
                        </select>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div> 
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Longitud <i class="glyphicon glyphicon-question-sign info" onclick="alertaCoordenadas()"></i></label>
                        <input type="text" name="Longitud" placeholder="Longitud" class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuLong(): ""%>"  data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group ">
                        <label>Latitud <i class="glyphicon glyphicon-question-sign info" onclick="alertaCoordenadas()"></i></label>
                        <input type="text" name="Latitud" placeholder="Latitud" class="form-control " value="<%=elem.getPumuId() != null ? elem.getPumuLat(): ""%>"  data-parsley-id="7052"><ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group ">
                        <label>Descripcion *</label>
                        <textarea name="descripcion" class="form-control" placeholder="Descripcion" rows="5" required data-parsley-id="7052"><%=elem.getPumuId() != null ? elem.getPumuDescripcion(): ""%></textarea>
                        <ul class="parsley-errors-list" id="parsley-id-7052"></ul>
                    </div>
                </div>    


            </div>

        </div>

        <div class="modal-footer">
            <div>
                <input type="hidden" name="modulo" value="3"/>
                <input type="hidden" name="rfid" value="<%=rfid%>"/>
                <input type="hidden" name="index" value="<%=index%>"/>
                <input type="hidden" name="campa" value="<%=campa%>"/>
                <label   onclick="

                        if ($('#FormModalAplication').parsley().isValid()) {
                            peticionAjax('Campanas', $('#FormModalAplication').serialize());

                        } else {
                            $('#FormModalAplication').submit();
                        }
                        " class="pull-right btn btn-success m-r-5 m-b-5">Guardar</label>
                        <button type="button"  onclick="closeModal()" class="pull-right btn btn-default m-r-5 m-b-5">Cerrar</button>

            </div>
        </div>
 </form>
                <script type="text/javascript">
                       function alertaCoordenadas() {

        bootbox.alert({
            message:"<legend>Formatos admitidos</legend>"+
                    //"Grados, minutos y segundos (GMS): 41°24'12.2\"N 2°10'26.5\"E" +
                   // "</br>" +
                    //"Grados y minutos decimales (GMD): 41 24.2028, 2 10.4418" +
                    "</br>" +
                    "Grados decimales (GD): 41.40338, 2.17403"
            
        });
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