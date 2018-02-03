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
            String campa = o.getvariable("campa");
            Campanas elem = new Campanas();
          
            
if(!campa.isEmpty()){
    elem = new CampanasJpaController(emf).findCampanas(Integer.parseInt(campa));

}
            


%>
        <div class="panel-body">
          <div id="pumuContent" class="row">
            <div class=" col-md-12 table-responsive">
                <table class="table table-condensed table-hover table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Estacion</th>
                            <th>Nombre</th>
                            <th>descripcion</th>
                            <th>Long</th>
                            <th>Lat</th>
                            <!--<th>Fecha Inicial</th>-->
                            


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
                 
                        </tr>
                        <%}%>

                    </tbody>


                </table>

            </div>                    </div>
        </div>

        <div class="modal-footer">
            <div>
        
                        <button type="button"  onclick="closeModal()" class="pull-right btn btn-default m-r-5 m-b-5">Cerrar</button>

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