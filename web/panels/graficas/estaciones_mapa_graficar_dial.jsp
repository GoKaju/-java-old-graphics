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
            String jh = o.getvariable("nomsess");
            DataJson datos = (DataJson) session.getAttribute(jh);


%>

<style type="text/css">
    .panelFloatGraf{
        max-width: 420px;
        width: 40%;
        background: rgba(0,0,0,0.7); 
        overflow:hidden;  
        color: white
    }
    #graficaDialMap<%=ramdom%>{
        position: fixed;
        top: <%=o.getvariable("ypos")%>px;
        left: <%=o.getvariable("xpos")%>px;    
    }

    .panel{background: rgba(0,0,0,0.7)}
    .red-color{color: #cd0a0a}
    .green-color{color: #00CF00}

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

        <div class="panel-body" style="padding:0 15px">
            <div id="accordion<%=ramdom%>" class="row"   >
                <%
                    String[] color = new String[8];
                    color[0] = "#BDBDBD";
                    color[1] = "#30f90c";
                    color[2] = "#fff80f";
                    color[3] = "#ff7600";
                    color[4] = "#ff0000";
                    color[5] = "#ef00ff";
                    color[6] = "#925904";
                    color[7] = "#000";
                    int i = 0;
                    for (DataJson.DataUnit dat : datos.getDatos()) {
                %>
                <div class=" panel col-md-12 data_header" style="margin-bottom: 1px; padding: 0px; font-size: 0.8em">
                    <div class="row ">
                        <div class="col-md-12 col-sm-12">
                            <div data-parent="#accordion<%=ramdom%>" data-toggle="collapse" style=" display: inline-block; cursor: pointer; width: 99%;height: 15px;padding-top: 3px;padding-left: 10px;" data-target="#collapse<%=dat.hashCode() + ramdom%>"><%=dat.getLabel()%>

                                <i   data-parent="#accordion<%=ramdom%>" data-toggle="collapse" style="cursor: pointer; display: inline-block;height: 15px" data-target="#collapse<%=dat.hashCode() + ramdom%>" class="glyphicon glyphicon glyphicon-chevron-down pull-right"></i>
                                <i style="margin-left: 5px;margin-right: 8px;" class="pull-right glyphicon  <%=Double.parseDouble(dat.getUltimoDato()) == 0?"glyphicon-alert red-color":"glyphicon-ok green-color" %>"   ></i>
                                <span class="text-center pull-right" style="background-color: <%= color[dat.getColor()] %>;height: 15px;width:15px;color:<%= dat.getColor()!=1&dat.getColor()!=2&dat.getColor()!=3?"white":"black" %>"><%=dat.getHoraICA()==0?"": dat.getHoraICA() %></span>
                                <span class="pull-right" style="">(UltimaHora):<%=dat.getUltimoDato()+ " "+ dat.getUnidadMedida()%></span>
                            </div>
                        </div>
                    </div>
                    <div  id="collapse<%=dat.hashCode() + ramdom%>" class="collapse ">
                        <div id="chart<%=dat.hashCode() + ramdom%>" class=></div>
                    </div>
                </div>
                <%
                        i++;
                    }
                %>
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
    var colors = ['#00FFFF', '#00FF00', '#fdfe02', '#fe00f6', '#fe0000', '#FF9A00', '#45FDF8', '#FE5BFF'];

    <%for (DataJson.DataUnit dat : datos.getDatos()) {%>
    var chart = c3.generate({
        size: {
            height: 230,
            width: 380
        },
        data: {
            xs: {
                '<%=dat.getLabel()%>': '<%=dat.getX()%>'
            },
            xFormat: '%Y-%m-%d %H:%M:%S',
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
        data.append("]");
        out.println(data.toString());
    %>
            ],
            type: 'area',
            colors: {
                '<%=dat.getLabel()%>': '<%= dat.getColorBorde() != null ? dat.getColorBorde() : "#fff"%>'
                        //colors[Math.floor((Math.random() * 8) + 1)]

            }
        },
        axis: {
            x: {
                type: 'timeseries',
                tick: {
                    format: '%H:%M',
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
    $("#chart<%=dat.hashCode() + ramdom%>").append(chart.element);
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