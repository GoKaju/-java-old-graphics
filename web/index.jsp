<%-- 
    Document   : index
    Created on : 13/11/2016, 10:14:01 PM
    Author     : D4V3
--%>

<%@page import="com.statics.vo.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   
    Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
    if(user!=null && user.getUsuaId()!= null){
response.sendRedirect("Inicio.jsp?r=0");
    }
    else{
    session.removeAttribute("usuarioVO");
    session.invalidate();
response.sendRedirect("login.jsp");
    }
    

%>



