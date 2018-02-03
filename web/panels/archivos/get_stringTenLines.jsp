<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>

<%@page import="com.opencsv.ICSVParser"%>
<%@page import="java.io.FileReader"%>
<%@page import="com.opencsv.CSVReader"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="java.io.File"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.util.List"%>
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

//session
                File file = (File) session.getAttribute("FileArchivo");
                if (file != null) {
                    System.out.println("files: " + file.getName());
                    char separador = o.getvariable("separador").charAt(0);
                    if ("tab".equals(o.getvariable("separador"))) {
                        separador = '\t';
                    } else if ("space".equals(o.getvariable("separador"))) {
                        separador = ' ';
                    }else if("default".equals(o.getvariable("separador"))) {
                        separador= ICSVParser.DEFAULT_SEPARATOR;
                                }
                    int linea = Integer.parseInt(o.getvariable("linea"));
                    
//                    int col_fecha = o.getvariable("col_fecha")==null||o.getvariable("col_fecha").isEmpty()?0:Integer.parseInt(o.getvariable("col_fecha"));
                    //      leer archivo
                    System.out.println("sep1:: " + o.getvariable("separador"));
                    System.out.println("sep:: " + separador);

                    CSVReader reader = new CSVReader(new FileReader(file), separador, ICSVParser.DEFAULT_QUOTE_CHARACTER, linea);
//                    CSVReader reader = new CSVReader(reader, line, ICSVParser.DEFAULT_ESCAPE_CHARACTER);
                    List myEntries = reader.readAll();
                    session.setAttribute("listCSV", myEntries);


%>


<legend>Pre-visualizacion</legend>
<div style="overflow-x: scroll" class="col-md-12">
    
<table class="table table-condensed">
    <tbody>
        <%   int line = 0;
            for (Object elem : myEntries) {
                String[] s = (String[]) elem;
                
        %>
        <tr>
            <td><%=++line%></td>
            <%
                for (int c = 0; c < s.length - 1; c++) {
                  
            %>
            <td><b><%=s[c]%></b></td>
            
          
            <%}%>
        </tr>

        <%if(line >7)break;}%>
    </tbody>

</table>

</div>


<b class="">

   Mostrando 8 de <%=myEntries.size()%> filas.
</b>
<script>
    $("#continuar_btn").removeAttr("disabled");
</script>




<%
    }
} else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<% }
} else {
%>
<script type="text/javascript">
    location.href = 'logout.jsp';
</script>
<%
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }%>