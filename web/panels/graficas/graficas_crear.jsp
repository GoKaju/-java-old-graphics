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

        <div style="width:100%;">
            <canvas id="<%=o.getvariable("var")%>"></canvas>
        </div>

        <script type="text/javascript">


            function hexToRGB(hex, alpha) {
                var r = parseInt(hex.slice(1, 3), 16),
                        g = parseInt(hex.slice(3, 5), 16),
                        b = parseInt(hex.slice(5, 7), 16);
                if (alpha) {
                    return "rgba(" + r + ", " + g + ", " + b + ", " + alpha + ")";
                } else {
                    return "rgb(" + r + ", " + g + ", " + b + ")";
                }
            }

            var y;
            var ramdomNumbers = [];

            function  getColor(x) {
                if (x) {
                    var ram;
                    do {
                        ram = Math.floor((Math.random() * 20));
                    } while (ramdomNumbers.includes(ram))
                    ramdomNumbers.push(ram);
                    y = colors.colors[ram];
                }
                return y;
            }
            var colors = {colors: [
                    {color: '#3366CC', bgcolor: hexToRGB('#3366CC', 0.5)},
                    {color: '#DC3912', bgcolor: hexToRGB('#DC3912', 0.5)},
                    {color: '#FF9900', bgcolor: hexToRGB('#FF9900', 0.5)},
                    {color: '#109618', bgcolor: hexToRGB('#109618', 0.5)},
                    {color: '#990099', bgcolor: hexToRGB('#990099', 0.5)},
                    {color: '#3B3EAC', bgcolor: hexToRGB('#3B3EAC', 0.5)},
                    {color: '#0099C6', bgcolor: hexToRGB('#0099C6', 0.5)},
                    {color: '#DD4477', bgcolor: hexToRGB('#DD4477', 0.5)},
                    {color: '#66AA00', bgcolor: hexToRGB('#66AA00', 0.5)},
                    {color: '#B82E2E', bgcolor: hexToRGB('#B82E2E', 0.5)},
                    {color: '#316395', bgcolor: hexToRGB('#316395', 0.5)},
                    {color: '#994499', bgcolor: hexToRGB('#994499', 0.5)},
                    {color: '#22AA99', bgcolor: hexToRGB('#22AA99', 0.5)},
                    {color: '#AAAA11', bgcolor: hexToRGB('#AAAA11', 0.5)},
                    {color: '#6633CC', bgcolor: hexToRGB('#6633CC', 0.5)},
                    {color: '#E67300', bgcolor: hexToRGB('#E67300', 0.5)},
                    {color: '#329262', bgcolor: hexToRGB('#329262', 0.5)},
                    {color: '#8B0707', bgcolor: hexToRGB('#8B0707', 0.5)},
                    {color: '#5574A6', bgcolor: hexToRGB('#5574A6', 0.5)},
                    {color: '#3B3EAC', bgcolor: hexToRGB('#3B3EAC', 0.5)}]};

            var ctx = document.getElementById("<%=o.getvariable("var")%>").getContext('2d');
            var myChart = new Chart(ctx, {
                type: '<%= datos.getTipo().replace("only","") %>',
                data: {
                    labels: ["<%=datos.getConcatX()%>"],
                    datasets: [
            <%for (DataJson.DataUnit du : datos.getDatos()) {%>
                        {
                            backgroundColor: getColor(true).bgcolor,
                            borderColor: getColor(false).color,
                            label: '<%=du.getLabel()%>(<%=du.getUnidadMedida()%>)',
                            data: [<%=du.getConcatDatos()%>],
                            borderWidth: 1,
                            fill:<%=datos.getTipo().equalsIgnoreCase("onlyline")?false:true %>
                        },
            <%}%>

                    ]
                },
                options: {
                    scales: {
                        xAxes: [{
                                type: 'time',
                                time: {
                                    format: 'YYYY-MM-DD HH:mm',
                                    displayFormat: 'HH:mm',
                unit: 'minute',
                min: '<%=datos.getMin()%>',
                max: '<%= datos.getMax()%>'
                                },
                                    ticks: {
                                        source: 'labels'
                                    }
                            }]
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