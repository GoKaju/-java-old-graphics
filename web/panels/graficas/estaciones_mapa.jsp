<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>



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

            String rfid = o.getvariable("rfid");
            Rolfuncionalidad rf = null;

            if (!rfid.isEmpty()) {
                rf = new RolfuncionalidadJpaController(emf).findRolfuncionalidad(Integer.parseInt(rfid));

            }
            if (rf != null && rf.getRofuId() != null) {


%>
<style type="text/css">

    .c3 .c3-axis-x path,
    .c3 .c3-axis-x line,
    .c3 .c3-axis-y path,
    .c3 .c3-axis-y line {
        stroke: white;
    }

    .c3 .c3-axis-x g,
    .c3 .c3-axis-y g,
    .c3 .c3-legend-item-data text,
    .c3 .c3-axis-y-label,
    .c3 .c3-axis-x-label
    {
        fill: whitesmoke;
    }
    .c3 .c3-legend-item {
        display: none;
    }

    /*when displaying the data on the chart */
    .c3 text.c3-text { 
        fill: whitesmoke!important;
        stroke: whitesmoke!important;
        stroke-opacity: 0.3!important;
    }

    /* for the tooltip text color */
    .c3 .c3-tooltip-container {
        color:darkgrey;
    }
</style>
<!--mapa-->
<div id="map" style="width: 100%; "></div>
<script>





    function initMap() {
        var uluru = {lat: 4.570868, lng: -74.29733299999998};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 6,
            center: uluru,
            fullscreenControl: true,
             mapTypeId: 'hybrid'
        });
        
        var icon = {
            url: "/graphics/assets/img/map_marker.svg", // url
            scaledSize: new google.maps.Size(50, 70), // scaled size
            origin: new google.maps.Point(0, 0) // origin

        };
        var markers = [];

    <%        EntityManager em = emf.createEntityManager();
        TypedQuery<Campanas> consulta = em.createNamedQuery("Campanas.findByGroupIdActivas", Campanas.class);
        consulta.setParameter("grupId", user.getGrupoUsuariosList().get(0).getGrupId().getGrupId());
        List<Campanas> lista = consulta.getResultList();

        if (!lista.isEmpty()) {
//                                if (false) {

            for (Campanas s : lista) {

                for (PuntoMuestral pumu : s.getPuntoMuestralList()) {

    %>
        var marker = new google.maps.Marker({
            position: {lat:<%=pumu.getPumuLat()%>, lng:<%=pumu.getPumuLong()%>},
            // position: {lat: -34.397, lng: 150.644},
            map: map,
//    icon:'http://reygif.com/media/detector-humo-64925.gif',
//    animation: google.maps.Animation.BOUNCE,
            animation: google.maps.Animation.DROP,
            label: {
                text: '<%=pumu.getPumuNombre()%>',
                color: 'white',
                fontSize: "20px",
                fontWeight: "bold"
            },
            title: '<%=pumu.getPumuDescripcion()%>',
            icon: icon
        });
        marker.addListener('click', function () {
            
            peticionAjax('Graficas','modulo=4&cod=<%=pumu.getPumuId()%>');
           
        });
        markers.push(marker);

    <%
                }
            }
        }

    %>

        var bounds = new google.maps.LatLngBounds();
        for (var i = 0; i < markers.length; i++) {
            bounds.extend(markers[i].getPosition());
        }

        map.fitBounds(bounds);


    }


    


    function graficarDial(x,nombre) {
        console.log("graficarDial: " + x)
        console.log("x:" + currentMousePos.x + " y:" + currentMousePos.y)
        var y;
        $.post("panels/graficas/estaciones_mapa_graficar_dial.jsp?cod=" + x + "&nomsess="+nombre+"&xpos=" + currentMousePos.x + "&ypos=" + currentMousePos.y, function (data) {
            console.log(data)
            $("#map").parent().append(data);
        });


    }
    var currentMousePos = {x: -1, y: -1};
    $("#map").height($(window).height() - 100);
    $(document).mousemove(function (event) {
        currentMousePos.x = event.pageX;
        currentMousePos.y = event.pageY;
    });

</script>
<!--maps-->
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDhy30cgEkgUO4C62eYLZb5J5fylMlanQ0&callback=initMap">
</script>






<%            } else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<%
    }
} else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<%        }
    } catch (Exception ex) {
        System.out.println("--> ERROR:::");
        ex.printStackTrace();
    }%>