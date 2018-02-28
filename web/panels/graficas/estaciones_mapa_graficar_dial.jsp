<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>



<%@page import="com.statics.carga.DataJson"%>
<%@page import="com.statics.dao.PuntoMuestralJpaController"%>
<%@page import="com.statics.vo.PuntoMuestral"%>
<%@page import="com.statics.vo.Campanas"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="java.util.List"%>

<%@page import="javax.persistence.EntityManager"%>

<%@page import="com.statics.util.Fechas"%>
<%@page import="com.statics.dao.UsuariosJpaController"%>
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
            PuntoMuestral pumu = new PuntoMuestralJpaController(emf).findPuntoMuestral(Integer.parseInt(o.getvariable("cod")));
            String ramdom = Fechas.getCadena();
            
   DataJson datos = (DataJson) session.getAttribute(o.getvariable("nomsess"));



%>

<style type="text/css">
    .panelFloatGraf{
        width: 420px;
       background: rgba(0,0,0,0.7); 
       overflow:hidden;  
       color: white
        
        
    }
    #graficaDialMap<%=ramdom%>{
      position: fixed;
      top: <%=o.getvariable("ypos") %>px;
    left: <%=o.getvariable("xpos") %>px;    
    }
    .data_header{
        
        border: 1px solid lightgray;
        
        
    }
    svg{
        width: 390px;
    }
    .panel{background: rgba(0,0,0,0.7)}
    
</style>
<div id="graficaDialMap<%=ramdom%>" class="panel  panelFloatGraf" style="" >
    <div class="panel-heading fixed">
        <div class="panel-heading-btn">
            <a href="javascript:;" class="" data-click="panel-remove"><i style="color: white" class="fa fa-times"></i></a>
        </div>
        <h2 class="panel-title"> <b> 
            <%= pumu.getPumuNombre()%></h2>
            </b> 
    </div>
    <div id="scrollSlim<%=ramdom%>">
        
        <div class="panel-body" style="">
            <div id="accordion<%=ramdom%>" class="row"   >
                
               <%
                   boolean isFirst = true;
                    for (DataJson.DataUnit dat : datos.getDatos()) {%>
               <div class=" panel col-md-12 data_header"  >
                    
                   <span data-parent="#accordion<%=ramdom%>" data-toggle="collapse" style="cursor: pointer" data-target="#collapse<%=dat.hashCode()+ramdom%>"><%=dat.getLabel()%></span>
                    <i   data-parent="#accordion<%=ramdom%>" data-toggle="collapse" style="cursor: pointer" data-target="#collapse<%=dat.hashCode()+ramdom%>" class="glyphicon glyphicon glyphicon-chevron-down pull-right"></i>
                    

                    <div  id="collapse<%=dat.hashCode()+ramdom%>" class="collapse <%=isFirst?"in":"" %>">
                        <div id="chart<%=dat.hashCode()+ramdom%>" class="height-sm"></div>
                    </div>
                
                </div>
                    <% isFirst = false; }%>
    

            </div>

        </div>
                    
    </div>

</div>

<script>
    $("#graficaDialMap<%=ramdom%>").draggable({
        //containment: "parent"
    });
    $("#graficaDialMap<%=ramdom%>").resizable({
//        containment: "parent",
//        maxHeight: 250,
//        maxWidth: 350,
        minHeight: 100,
        minWidth: 200,
        resize: function (event, ui) {
//    ui.size.height = Math.round( ui.size.height / 30 ) * 30;
            console.log(ui.size.height);
            $("#scrollSlim<%=ramdom%>").slimScroll({
                height: ui.size.height + 'px'
            });
        }
    });
    
//    colores

var colors = ['#00FFFF','#00FF00','#fdfe02','#fe00f6','#fe0000','#FF9A00','#45FDF8','#FE5BFF'];
    
 <%for (DataJson.DataUnit dat : datos.getDatos()) {%>

  var chart = c3.generate({
 data: {
        xs: {
             '<%=dat.getLabel()%>': '<%=dat.getX()%>'
        },
        xFormat: '%Y-%m-%d %H:%M',
        columns: [
    <%
      
            StringBuilder x = new StringBuilder("['" + dat.getX() + "',");
            for (String s : dat.getFechas()) {
                x.append("'");
                x.append(s);
                x.append("',");
            }
            x.deleteCharAt(x.length() - 1);
            x.append("]");
            x.append(",");
            out.println(x.toString());

            StringBuilder data = new StringBuilder("['" + dat.getLabel() + "',");
            for (String s : dat.getDatos()) {
                data.append(s);
                data.append(",");

            }
            data.deleteCharAt(data.length() - 1);
            data.append("],");
            out.println(data.toString());

        %>

            ],
        type:'area',
        colors: {
            '<%=dat.getLabel()%>': colors[Math.floor((Math.random() * 8) + 1)]
            
        }
    },
    axis: {
        x: {
            type: 'timeseries',
            tick: {
                format: '%Y-%m-%d %H:%M',
                   rotate: 75,
                multiline: false
            }
//            height: 130
        }
    },
     zoom: {
        enabled: true
    }
});
$("#chart<%=dat.hashCode()+ramdom%>").append(chart.element);

<% }%>

</script>




<%            } else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<%
    }
    } catch (Exception ex) {
System.out.println("--> ERROR:::");
        ex.printStackTrace();
    }%>