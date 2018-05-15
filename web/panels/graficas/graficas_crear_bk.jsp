<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>


<%@page import="com.statics.carga.DataJson"%>
<%@page import="java.util.List"%>
<%@page import="com.statics.vo.Campanas"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="com.statics.vo.Estaciones"%>
<%@page import="com.statics.dao.EstacionesJpaController"%>

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
            DataJson datos = (DataJson) session.getAttribute(o.getvariable("var"));

            if (datos != null) {
%>

<style type="text/css">
    .c3 svg {
        width: 100%;
    }
</style>
<div class="panel panel-inverse">
    <div class="panel-heading">
        <div class="panel-heading-btn">
            <a onclick="resizechart()" href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-success" data-click="panel-reload"><i class="fa fa-repeat"></i></a>
            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-warning" data-click="panel-collapse"><i class="fa fa-minus"></i></a>
            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-danger" data-click="panel-remove"><i class="fa fa-times"></i></a>
        </div>
        <h2 class="panel-title"><%=o.notEmpty(datos.getNombreGraphic())%></h2>
    </div>
    <div class="panel-body" >

        <div  style="width: 100%" id="<%=o.getvariable("var")%>" >

        </div> 

<script type="text/javascript">
    function resizechart() {
        setTimeout(function () {
            chart.resize();
            chart.internal.selectChart.style('max-height', 'none');
        }, 200);

    }


    var chart = c3.generate({
        bindto: '#<%=o.getvariable("var")%>',
        data: {
            xs: {
    <%for (DataJson.DataUnit dat : datos.getDatos()) {%>
                '<%=dat.getLabel()%>':'<%=dat.getX()%>',
    <% }%>
            },
            xFormat: '%Y-%m-%d %H:%M',
            columns: [
    <%
        for (DataJson.DataUnit dat : datos.getDatos()) {
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

        }%>

            ], type: 'area'
        },
        axis: {
            x: {
                type: 'timeseries',
                tick: {
                    format: '%Y-%m-%d %H:%M',
                    rotate: 25
                }
            }
        },
        grid: {
        y: {
            lines: [
                {value: 50, text: 'Label 50 for y'},
                {value: 1300, text: 'Label 1300 for y2', axis: 'y2', position: 'start'},
                {value: 350, text: 'Label 350 for y', position: 'middle'}
            ]
        }
    },
        subchart: {
            show: true
        },
        zoom: {
            enabled: true
        },
        point: {
            r: false,
            focus: {    
                expand: {
                    r: 4
                }
            }
        }
    
    });


</script>


<%
} else {
%>
<script type="text/javascript">
    alert("error al crear grafica");
</script>
<%        }
} else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp'
</script>
<%        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }%>