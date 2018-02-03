<%-- 
    Document   : base
    Created on : 14/11/2016, 10:07:54 AM
    Author     : D4V3
--%>

<%@page import="com.statics.util.Fechas"%>
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
                List linescsv = (List) session.getAttribute("listCSV");
                if (linescsv != null) {

                    int lineas = Integer.parseInt(o.getvariable("lineas"));
                    boolean todo = lineas == 0 ? true : false;

                    int col_fecha = o.getvariable("col_fecha") == null || o.getvariable("col_fecha").isEmpty() ? 0
                            : Integer.parseInt(o.getvariable("col_fecha"));

%>
<div class="col-md-12" >
    <div class="col-md-3">
        <div class="form-group ">
            <label style="color: blue">Columna de fecha?</label>
            <select required="" onchange="recargaRowsTable();verificarFormatoFechas(this.value)" name="col_fecha" id="col_fecha" class="form-control"  title="Elija la columna de fechas" >
                <option value="">Seleccione..</option>
                <%                                String[] ss = (String[]) linescsv.get(0);
                    for (int c = 0; c < ss.length - 1; c++) {
                %>
                <option  value="<%=c%>" <%=c == col_fecha ? "selected=''" : ""%>><%=c + 1%></option>
                <%
                    }
                %>
            </select>
        </div>
    </div> 
    <div class="col-md-12" style="">
        <table class="table table-condensed table-hover">
            <tbody>
                <%   int line = 0;
                    for (Object elem : linescsv) {
                        String[] s = (String[]) elem;

                %>
                <tr>
                    <td>

                        <div style="float: left; width: 65px">
                            <div style="float: left; width: 50px">
                                <a onclick="
                                        peticionAjax('Archivos', 'op=2&rfid=<%=rfid%>&hash=<%=line%>')" href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-primary" title="Agregar una linea antes de esta"><i class="fa fa-plus"></i></a>
                                <a onclick="
                                        peticionAjaxConfirm('Archivos', 'op=3&rfid=<%=rfid%>&hash=<%=line%>', '¿Esta seguro de remover la linea <%=(line + 1)%>?')" href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-danger" title="Remover esta linea"><i class="fa fa-times"></i></a>
                            </div>
                            <div style="float: left; width: 15px;text-align: center">
                                <b>
                                    <%=++line%>
                                </b>
                            </div>
                        </div>
                    </td>
                    <%
                        for (int c = 0; c < s.length - 1; c++) {
                            if (c == col_fecha) {
                    %>

                    <td>
                        <a style="font-weight: bold" href="#" class="edit" data-type="text" row="<%=line - 1%>" col="<%=c%>"  data-pk="<%=line + "," + c%>" data-title=""> 
                            <%=s[c]%>
                        </a>

                    </td>

                    <%} else {%>

                    <td>
                        <a  href="#" class="edit" data-type="text" row="<%=line - 1%>" col="<%=c%>"  data-pk="<%=line + "," + c%>" data-title=""> 
                            <%=s[c]%>
                        </a>

                    </td>
                    <%}
                        }%>
                </tr>

                <%if (!todo && line > lineas - 1) {
                            break;
                        }
                    }%>
            </tbody>

        </table>

    </div>
</div>



<script>
    $('#Nfilas').html("<b>  Mostrando <%=lineas == 0 ? linescsv.size() : lineas%> de <%=linescsv.size()%> filas.</b>");
    $('#parametros').val('<% for(String st :(String[]) linescsv.get(0)){out.print("-"+" "+st+"</br>"); }%>');
    $('.edit').editable({

        success: function (response, newValue) {

            peticionAjax('Archivos', 'op=4&rfid=<%=rfid%>&row=' + $(this).attr('row') + '&col=' + $(this).attr('col') + '&val=' + newValue + '');

        }
    });

    function verificarFormatoFechas(x) {
        if (x >= 0) {
            peticionAjax("Archivos", "op=5&rfid=<%=rfid%>&col=" + x + "");
        }

    }
    
//    funciones fecha

function pedirfechahhmm(x){
bootbox.prompt({
    size: "small",
    title: "He encontrado en formato de fecha 'hhmm' Selecciona un dia para complementar...",
    inputType: 'date',
     buttons: {
        confirm: {
            label: 'Ok',
            className: 'btn-success'
        }
    },
    callback: function (result) {
        peticionAjax("Archivos","op=6&fecha="+result+"&col="+x+"")
    }
});
}
    
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