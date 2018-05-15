<%-- 
    Document   : Inicio
    Created on : 13/11/2016, 11:14:40 PM
    Author     : D4V3
--%>

<%@page import="com.statics.util.Cadenas"%>
<%@page import="com.statics.vo.Usuariorol"%>
<%@page import="com.statics.vo.Rolfuncionalidad"%>
<%@page import="com.statics.vo.Usuarios"%>
<%@page import="com.statics.util.Constantes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<%
    try {
        Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
        if (user != null && user.getUsuaId() != null) {
            Cadenas o = new Cadenas(request);

%>
<html lang="es">
    <!--<![endif]-->
    <head>
        <meta charset="utf-8" />
        <title><%=Constantes.APPNAME%></title>
        <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />

        <!-- ================== BEGIN BASE CSS STYLE ================== -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
        <!--<link href="assets/plugins/jquery-ui/themes/base/minified/jquery-ui.min.css" rel="stylesheet" />-->
        <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
       
        <link href="assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
        <link href="assets/css/animate.min.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet" />
        <link href="assets/css/style-responsive.min.css" rel="stylesheet" />
        <link href="assets/css/theme/default.css" rel="stylesheet" id="theme" />
        <!-- ================== END BASE CSS STYLE ================== -->
        <link href="assets/plugins/DataTables/media/css/dataTables.bootstrap.min.css" rel="stylesheet" />
        <link href="assets/plugins/DataTables/extensions/Responsive/css/responsive.bootstrap.min.css" rel="stylesheet" />
        <link href="assets/plugins/parsley/src/parsley.css" rel="stylesheet" />
        <link href="assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet">
        <link href="assets/css/Loader.css" rel="stylesheet">
        <!--<link href="assets/css/Loader.css" rel="stylesheet">-->
        <!-- ================== BEGIN BASE JS ================== -->
        <script src="assets/plugins/pace/pace.min.js"></script>
        
        <!-- ================== END BASE JS ================== -->
        <link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
<!--        <link href="assets/plugins/C3/c3.min.css" rel="stylesheet" />-->
 <!-- Load c3.css -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.14/c3.min.css" rel="stylesheet">
        <link href="assets/css/bootstrap-toggle.min.css" rel="stylesheet" type="text/css"/>
        <link href="assets/css/customStyles.css" rel="stylesheet" />

    </head>
    <body>
        <!-- begin #page-loader -->
        <div id="page-loader" class="fade in"><span class="spinner"></span></div>


        <div class="modalLoader" hidden="" >

            <div class="cssload-container">
                <div class="cssload-whirlpool"></div>
            </div>
        </div>
        <!-- end #page-loader -->

        <!-- begin #page-container -->
        <div id="page-container" class="fade page-sidebar-fixed page-header-fixed page-with-wide-sidebar">
            <!-- begin #header -->
            <div id="header" class="header navbar navbar-default navbar-fixed-top">
                <!-- begin container-fluid -->
                <div class="container-fluid">
                    <!-- begin mobile sidebar expand / collapse button -->
                    <div class="navbar-header">
                        <a href="Inicio.jsp?r=<%=o.getvariable("r")%>" class="navbar-brand"><span class="navbar-logo"></span> <%=Constantes.APPNAME%></a>
                        <button type="button" class="navbar-toggle" data-click="sidebar-toggled">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <!-- end mobile sidebar expand / collapse button -->

                    <!-- begin header navigation right -->
                    <ul class="nav navbar-nav navbar-right">
                   
                        <li class="dropdown navbar-user">
                            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                                <img src="<%=user.getUsuaImg()%>" alt="avatar de usuario" /> 
                                <span class="hidden-xs"><%=user.getUsuaNombres()%></span> <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu animated fadeInLeft">
                                <li class="arrow"></li>
                                <li><a href="javascript:;">Roles</a>
                                    <ul>
                                        <%
                                            int c = 0;
                                            
                                            

                                      for (Usuariorol ur : user.getUsuariorolList()) {
                                        %>
                                        <li><a href="Inicio.jsp?r=<%=c++%>"><%= ur.getRoleId().getRoleDescripcion()%></a>
                                            <%}%>
                                    </ul>
                                </li>
                                <li class="divider"></li>
                                <!--<li><a href="javascript:;">Editar Usuario</a></li>-->
                                <!--<li><a href="javascript:;"><span class="badge badge-danger pull-right">2</span> Inbox</a></li>-->
                                <!--<li><a href="javascript:;">Calendar</a></li>-->
                                <!--<li><a href="javascript:;">Setting</a></li>-->
                                <li><a href="logout.jsp">Cerrar sesión</a></li>
                            </ul>
                        </li>
                    </ul>
                    <!-- end header navigation right -->
                </div>
                <!-- end container-fluid -->
            </div>
            <!-- end #header -->

            <!-- begin #sidebar -->
            <div id="sidebar" class="sidebar">
                <!-- begin sidebar scrollbar -->
                <div data-scrollbar="true" data-height="100%">
                    <!-- begin sidebar user -->
                    <ul class="nav">
                        <li class="nav-profile">
                            <div class="image">
                                <a href="javascript:;"><img src="<%=user.getUsuaImg()%>" alt="" /></a>
                            </div>
                            <div class="info">
                                <%=user.getUsuaNombres()%>
                            </div>
                        </li>
                    </ul>
                    <!-- end sidebar user -->
                    <!-- begin sidebar nav -->
                    <ul class="nav">
                        <li class="nav-header">Navegación</li>
                            <%
                                if (user.getUsuariorolList().size() > Integer.parseInt(o.notEmpty(o.getvariable("r"), "0"))) {
                                    Usuariorol ur = user.getUsuariorolList().get(Integer.parseInt(o.notEmpty(o.getvariable("r"), "0")));

                                    if (ur != null) {
                                        for (Rolfuncionalidad rf : ur.getRoleId().getRolfuncionalidadList()) {
                                            if (!rf.getFuncId().getFuncCodigo().contains("_")) {
                            %>
                       <li class="<%=rf.getFuncId().getFunTienehijos()?"has-sub":"" %>">
                            <a href="javascript:;" <%if(!rf.getFuncId().getFunTienehijos()){%>onclick="RecargaPanel('<%=rf.getFuncId().getFuncUrl()%>.jsp?rfid=<%=rf.getRofuId()%>', 'content')"<%}%>>
                                <b class="<%=rf.getFuncId().getFunTienehijos()?"caret pull-right":"" %>"></b>
                             
                                <i class="<%=rf.getFuncId().getFuncIcon()%>"></i>

                                <span><%=rf.getFuncId().getFuncDescripcion()%> </span>
                            </a>

                            <ul class="sub-menu">
                                <%
                                    for (Rolfuncionalidad rf2 : ur.getRoleId().getRolfuncionalidadList()) {
                                        if (rf2.getFuncId().getFuncCodigo().indexOf(rf.getFuncId().getFuncCodigo() + "_") != -1 && rf2.getFuncId().getFuncCodigo().length() == 5) {
                                %>

                                <li>
                                    <i class="<%=rf2.getFuncId().getFuncIcon()%>"></i>
                                    <a href="javascript:;" onclick="RecargaPanel('<%=rf2.getFuncId().getFuncUrl()%>.jsp?rfid=<%=rf2.getRofuId()%>', 'content')"><%=rf2.getFuncId().getFuncDescripcion()%></a>
                                    <ul class="sub-menu">
                                        <%
                                            for (Rolfuncionalidad rf3 : ur.getRoleId().getRolfuncionalidadList()) {
                                                if (rf3.getFuncId().getFuncCodigo().indexOf(rf2.getFuncId().getFuncCodigo() + "_") != -1 && rf3.getFuncId().getFuncCodigo().length() == 8) {
                                        %>
                                        <li>
                                            <i class="<%=rf3.getFuncId().getFuncIcon()%>"></i>
                                            <a href="#" onclick="RecargaPanel('<%=rf3.getFuncId().getFuncUrl()%>.jsp', 'content')"><%=rf3.getFuncId().getFuncDescripcion()%></a>
                                        </li>
                                        <%}
                                            }%>
                                    </ul>  

                                </li>

                                <%}
                                    }%>
                            </ul>
                        </li>
                        <%}
                            }
                        } else {
                        %>
                        <li class="has-sub">
                            <a href="javascript:;">
                                <!--<b class="caret pull-right"></b>-->
                                <i class="fa fa-warning"></i>
                                <span>Seleccione un rol...</span>
                            </a>

                        </li>
                        <%
                            }
                        } else {
                        %>
                        <li class="has-sub">
                            <a href="javascript:;">
                                <!--<b class="caret pull-right"></b>-->
                                <i class="fa fa-warning"></i>
                                <span>Seleccione un rol...</span>
                            </a>

                        </li>
                        <%
                            }


                        %>


                        <!-- begin sidebar minify button -->
                        <li><a href="javascript:;" class="sidebar-minify-btn" data-click="sidebar-minify"><i class="fa fa-angle-double-left"></i></a></li>
                        <!-- end sidebar minify button -->
                    </ul>
                    <!-- end sidebar nav -->
                </div>
                <!-- end sidebar scrollbar -->
            </div>
            <div class="sidebar-bg"></div>
            <!-- end #sidebar -->

            <!-- begin #content -->
            <div id="content" class="content">
                <!--aqui se cargaran las vistas--> 
                <!-- begin breadcrumb -->
                <ol class="breadcrumb pull-right">
                    <li class="active">Inicio</li>
                    <li class="active">Bienvenido</li>
                    <!--<li class="active">Page with Wide Sidebar</li>-->
                </ol>
                <!-- end breadcrumb -->
                <!-- begin page-header -->
                <h1 class="page-header">Hola <small><%=user.getUsuaNombres()%></small></h1>
                <!-- end page-header -->

                <div class="panel panel-inverse">
                    <div class="panel-heading">
                        <div class="panel-heading-btn">
                            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
                            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-success" data-click="panel-reload"><i class="fa fa-repeat"></i></a>
                            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-warning" data-click="panel-collapse"><i class="fa fa-minus"></i></a>
                            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-danger" data-click="panel-remove"><i class="fa fa-times"></i></a>
                        </div>
                        <h2 class="panel-title">&thinsp;</h2>
                    </div>
                    <div class="panel-body">
                        descripción del aplicativo
                    </div>
                </div>
            </div>
            <!-- end #content -->

            <!-- begin theme-panel -->
            <div class="theme-panel">
                <a href="javascript:;" data-click="theme-panel-expand" class="theme-collapse-btn"><i class="fa fa-cog"></i></a>
                <div class="theme-panel-content">
                    <h5 class="m-t-0">Color </h5>
                    <ul class="theme-list clearfix">
                        <li class="active"><a href="javascript:;" class="bg-green" data-theme="default" data-click="theme-selector" data-toggle="tooltip" data-trigger="hover" data-container="body" data-title="Default">&nbsp;</a></li>
                        <li><a href="javascript:;" class="bg-red" data-theme="red" data-click="theme-selector" data-toggle="tooltip" data-trigger="hover" data-container="body" data-title="Red">&nbsp;</a></li>
                        <li><a href="javascript:;" class="bg-blue" data-theme="blue" data-click="theme-selector" data-toggle="tooltip" data-trigger="hover" data-container="body" data-title="Blue">&nbsp;</a></li>
                        <li><a href="javascript:;" class="bg-purple" data-theme="purple" data-click="theme-selector" data-toggle="tooltip" data-trigger="hover" data-container="body" data-title="Purple">&nbsp;</a></li>
                        <li><a href="javascript:;" class="bg-orange" data-theme="orange" data-click="theme-selector" data-toggle="tooltip" data-trigger="hover" data-container="body" data-title="Orange">&nbsp;</a></li>
                        <li><a href="javascript:;" class="bg-black" data-theme="black" data-click="theme-selector" data-toggle="tooltip" data-trigger="hover" data-container="body" data-title="Black">&nbsp;</a></li>
                    </ul>
                    <div class="divider"></div>
                    <div class="row m-t-10">
                        <div class="col-md-5 control-label double-line">Estilo de la cabecera</div>
                        <div class="col-md-7">
                            <select name="header-styling" class="form-control input-sm">
                                <option value="1">por defecto</option>
                                <option value="2">invertido</option>
                            </select>
                        </div>
                    </div>
                    <div class="row m-t-10">
                        <div class="col-md-5 control-label">Cabecera</div>
                        <div class="col-md-7">
                            <select name="header-fixed" class="form-control input-sm">
                                <option value="1">Fijo</option>
                                <option value="2">default</option>
                            </select>
                        </div>
                    </div>

                </div>
            </div>
            <!-- end theme-panel -->

            <!-- begin scroll to top btn -->
            <a href="javascript:;" class="btn btn-icon btn-circle btn-success btn-scroll-to-top fade" data-click="scroll-top"><i class="fa fa-angle-up"></i></a>
            <!-- end scroll to top btn -->
        </div>
        <!-- end page container -->

        <!-- ================== BEGIN BASE JS ================== -->
        <script src="assets/plugins/jquery/jquery-1.9.1.min.js"></script>
        <script src="assets/plugins/jquery/jquery-migrate-1.1.0.min.js"></script>
        <script src="assets/plugins/jquery-ui/ui/minified/jquery-ui.min.js"></script>
        <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>

        <!--[if lt IE 9]>
                <script src="assets/crossbrowserjs/html5shiv.js"></script>
                <script src="assets/crossbrowserjs/respond.min.js"></script>
                <script src="assets/crossbrowserjs/excanvas.min.js"></script>
        <![endif]-->
        <script src="assets/plugins/slimscroll/jquery.slimscroll.min.js"></script>
        <script src="assets/plugins/jquery-cookie/jquery.cookie.js"></script>
        <!-- ================== END BASE JS ================== -->
        <script src="assets/plugins/gritter/js/jquery.gritter.js"></script>
        <script src="assets/plugins/parsley/dist/parsley.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.2/d3.min.js"></script>
        

<!-- Load d3.js and c3.js -->
<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.14/c3.min.js"></script>
        <!-- ================== CHARTJS ================== -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>
        <script src="assets/plugins/chart-js/Chart.min.js"></script>
        <!-- ================== END CHARTJS ================== -->
        <script src="js/Manejador.js"></script>

        <script src="assets/js/apps.min.js"></script>

        <script>
                                                $(document).ready(function () {
                                                    App.init();
//                                                    $( "#content" ).draggable();
RecargaPanel('panels/graficas/estaciones_mapa.jsp?rfid=33', 'content')
                                                });
        </script>
        <script src="assets/js/customJs.js"></script>
        <script src="assets/js/bootstrap-toggle.min.js" type="text/javascript"></script>
    </body>
</html>
<%
        } else {
            response.sendRedirect("logout.jsp");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }%>

