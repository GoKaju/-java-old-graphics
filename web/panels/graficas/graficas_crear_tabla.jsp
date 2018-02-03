<%-- 
    Document   : graficas_crear_tabla
    Created on : 3/01/2018, 10:41:26 PM
    Author     : Usuario
--%>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.statics.dao.ParametrosJpaController"%>
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
          
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
           
             String Param[] = (String[]) session.getAttribute(o.getvariable("paramsess"));
             List<Object[]> lis = (List<Object[]>)session.getAttribute(o.getvariable("idsess"));
           

%>
<div class="panel-body table-responsive" style="max-height: 600px">
         
        <script type="text/javascript" src="assets/plugins/tableExport/tableExport.js"></script>
        <script type="text/javascript" src="assets/plugins/tableExport/jquery.base64.js"></script>
 
      
        
        <table id="tableExcel324" class="table table-striped" >
	<thead>			
		<tr class='primary'>
			<th>FECHA</th>
                    <%
                    for (String elem : Param) {
                         %>
                        <th><%=new ParametrosJpaController(emf).findParametros(Integer.parseInt(elem)).getPareNombre() %></th>
                        <%   
                        }
                    %>
		
		</tr>
	</thead>
	<tbody>
            
                      <%
                    for (Object[] elem : lis) {
                        HashMap map = new HashMap();
                        if(elem[1]!=null){
                        String[] x = ((String)elem[1]).split(";");
                        for (String e : x) {
                              System.out.println("--> data "+e);
                            String[] y = e.split("#");
                            if(y!= null&& y.length>1){
                                System.out.println("--> data table "+ y[0]+" "+y[1]);
                            map.put(y[0],y[1]);
                            }
                            }
                        }

                         %>
                       <tr>
			<td><%=elem[0] %></td>
			  <%
                    for (String e : Param) {
                         %>
                         <td><%=map.get(e)!=null?map.get(e):"" %></td>
                        <%   
                        }
                    %>
		</tr>
                         
                         
                        <%   
                        }
                    %>
	
	</tbody>
</table> 

        </div>

        <div class="modal-footer">
            <div>
        
                          <label  onclick="$('#tableExcel324').tableExport({type:'excel',tableName:'yourTableName',escape:'false'});" class="pull-right btn btn-success m-r-5 m-b-5">Descargar</label>
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


