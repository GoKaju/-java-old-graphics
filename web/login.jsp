<%-- 
    Document   : login
    Created on : 14/11/2016, 12:30:29 AM
    Author     : D4V3
--%>

<%@page import="com.statics.util.Constantes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   session.removeAttribute("usuarioVO");
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="es">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>Login <%=Constantes.APPNAME%></title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	
	<!-- ================== BEGIN BASE CSS STYLE ================== -->
	<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
	<link href="assets/plugins/jquery-ui/themes/base/minified/jquery-ui.min.css" rel="stylesheet" />
	<link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	<link href="assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
	<link href="assets/css/animate.min.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet" />
	<link href="assets/css/style-responsive.min.css" rel="stylesheet" />
	<link href="assets/css/theme/default.css" rel="stylesheet" id="theme" />
	<!-- ================== END BASE CSS STYLE ================== -->
		<link href="assets/plugins/parsley/src/parsley.css" rel="stylesheet" />
                <link href="assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet">

	<!-- ================== BEGIN BASE JS ================== -->
	<script src="assets/plugins/pace/pace.min.js"></script>
	<!-- ================== END BASE JS ================== -->
</head>
<body class="pace-top bg-white">
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	
	<!-- begin #page-container -->
	<div id="page-container" class="fade">
	    <!-- begin login -->
        <div class="login login-with-news-feed">
            <!-- begin news-feed -->
            <div class="news-feed">
                <div class="news-image">
                    <img src="assets/img/login-bg/bg-7.jpg" data-id="login-cover-image" alt="" />
                </div>
                <div class="news-caption">
                    <h4 class="caption-title"><i class="fa fa-diamond text-success"></i>  <%=Constantes.APPNAME%>
                        <small><%=Constantes.APP_DESC %></small></h4>
                    <p>
                        
                    </p>
                </div>
            </div>
            <!-- end news-feed -->
            <!-- begin right-content -->
            <div class="right-content">
                <!-- begin login-header -->
                <div class="login-header">
                    <div class="brand">
                        <span class="logo"></span><%=Constantes.APPNAME%>
                        <small><%=Constantes.APP_DESC %></small>
                    </div>
                    <div class="icon">
                        <!--<i class="fa fa-sign-in"></i>-->
                    </div>
                </div>
                <!-- end login-header -->
                <!-- begin login-content -->
                <div class="login-content">
                    <form id="LoginForm" action="Login" method="POST" class="margin-bottom-0" data-parsley-validate="true">
                        <div class=" form-group m-b-15">
                            <input type="email" name="email" class="form-control input-lg" placeholder="Email" data-parsley-required="true"/>
                        </div>
                        <div class="form-group m-b-15">
                            <input type="password" name="pass" class="form-control input-lg" placeholder="Contraseña"  required=""/>
                        </div>
<!--                        <div class="checkbox m-b-30">
                            <label>
                                <input type="checkbox" /> Remember Me
                            </label>
                        </div>-->
                        <div class="login-buttons">
                        <input type="hidden" name="ip_ocu" id="ip_ocu" />
                                        <script type="text/javascript" src="http://l2.io/ip.js?var=myip"></script>
                                        <!-- ^^^^ -->
                                        <button type="button" onclick="
                                          
    if( $('#LoginForm').parsley().isValid()){
                                                  peticionAjax('Login',$('#LoginForm').serialize());
                                                   
                                               }else {
                                                 $('#LoginForm').submit();  
                                               }
                                                " class="btn btn-success btn-block btn-lg">Ingresar</button>
                        </div>
<!--                        <div class="m-t-20 m-b-40 p-b-40">
                            Not a member yet? Click <a href="register_v3.html" class="text-success">here</a> to register.
                        </div>-->
                        <hr />
                        <p class="text-center text-inverse">
                        SICA &copy; powered by Ambientalia 
                        </p>
                        <img class="image img-responsive" style="max-width: 70%; margin: auto" src="assets/img/Ambientalia_XL.png" alt="" />
                    </form>
                </div>
                
                <!-- end login-content -->
            </div>
            <!-- end right-container -->
        </div>
        <!-- end login -->
        
        <!-- begin theme-panel -->
        <div class="theme-panel" hidden="">
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
<!--                <div class="row m-t-10">
                    <div class="col-md-5 control-label double-line">Sidebar Styling</div>
                    <div class="col-md-7">
                        <select name="sidebar-styling" class="form-control input-sm">
                            <option value="1">default</option>
                            <option value="2">grid</option>
                        </select>
                    </div>
                </div>-->
<!--                <div class="row m-t-10">
                    <div class="col-md-5 control-label">Sidebar</div>
                    <div class="col-md-7">
                        <select name="sidebar-fixed" class="form-control input-sm">
                            <option value="1">fixed</option>
                            <option value="2">default</option>
                        </select>
                    </div>
                </div>
                <div class="row m-t-10">
                    <div class="col-md-5 control-label double-line">Sidebar Gradient</div>
                    <div class="col-md-7">
                        <select name="content-gradient" class="form-control input-sm">
                            <option value="1">disabled</option>
                            <option value="2">enabled</option>
                        </select>
                    </div>
                </div>-->
<!--                <div class="row m-t-10">
                    <div class="col-md-5 control-label double-line">Content Styling</div>
                    <div class="col-md-7">
                        <select name="content-styling" class="form-control input-sm">
                            <option value="1">default</option>
                            <option value="2">black</option>
                        </select>
                    </div>
                </div>-->
<!--                <div class="row m-t-10">
                    <div class="col-md-12">
                        <a href="#" class="btn btn-inverse btn-block btn-sm" data-click="reset-local-storage"><i class="fa fa-refresh m-r-3"></i> Reset Local Storage</a>
                    </div>
                </div>-->
            </div>
        </div>
        <!-- end theme-panel -->
	</div>
	<!-- end page container -->
	
	<!-- ================== BEGIN BASE JS ================== -->
	<script src="assets/plugins/jquery/jquery-1.9.1.min.js"></script>
	<script src="assets/plugins/jquery/jquery-migrate-1.1.0.min.js"></script>
	<script src="assets/plugins/jquery-ui/ui/minified/jquery-ui.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!--[if lt IE 9]>
		<script src="assets/crossbrowserjs/html5shiv.js"></script>
		<script src="assets/crossbrowserjs/respond.min.js"></script>
		<script src="assets/crossbrowserjs/excanvas.min.js"></script>
	<![endif]-->
	<script src="assets/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/plugins/jquery-cookie/jquery.cookie.js"></script>
	<!-- ================== END BASE JS ================== -->
        <script src="assets/plugins/gritter/js/jquery.gritter.js"></script>
        <script src="js/Manejador.js"></script>
	
	<!-- ================== BEGIN PAGE LEVEL JS ================== -->
        	<script src="assets/plugins/parsley/dist/parsley.js"></script>
	<script src="assets/js/apps.min.js"></script>

	<!-- ================== END PAGE LEVEL JS ================== -->

	<script>
		$(document).ready(function() {
                    
                    
//  $('#LoginForm').parsley().isValid()



                window.location.hash = "no-back-button";
                window.location.hash = "Again-No-back-button" //chrome
                window.onhashchange = function () {
                    window.location.hash = "no-back-button";
                }
			App.init();
                        $("#ip_ocu").val(myip);

		});
	</script>
</body>
</html>

