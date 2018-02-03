<%-- 
        DESAROLLADOR : Alejandro 
        NOMBRE DEL DOCUMENTO   : logout
        FECHA DE CREACION : 30-oct-2015, 6:42:25  
        HISTORIA DE USUARIO :
        DESCRIPCION FUNCIONAL DEL DOCUMENTO : 
--%>

<%
    session.removeAttribute("usuarioVO");
    session.invalidate();
    response.sendRedirect("index.jsp");
%>