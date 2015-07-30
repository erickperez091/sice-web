<%-- 
    Document   : login
    Created on : 28/05/2015, 06:03:04 PM
    Author     : Erick
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>

        <title>Login</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="">
        <meta name="author" content="" />
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800" type="text/css">
        <link href="../css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="../css/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/App.css" rel="stylesheet" type="text/css"/>
        <link href="../css/login.css" rel="stylesheet" type="text/css"/>
        <link href="../css/custom.css" rel="stylesheet" type="text/css"/>
        <style>
            .errorblock {
                color: #ff0000;
                background-color: #ffEEEE;
                border: 3px solid #ff0000;
                padding: 8px;
                margin: 16px;
            }
        </style>

    </head>

    <body onload='document.login.j_username.focus();'>

        <div id="login-container">

            <div id="logo">
                <a href="./login.html">
                    <img src="../css/img/logo-login.png" alt="Logo" />
                </a>
            </div>

            <div id="login">

                <h3>Bienvenido al Sistema.</h3>

                <h5>Por favor inicie Sesi&oacute;n</h5>
                <c:if test="${not empty param.error}">
                    <div class="errorblock">
                        Your login attempt was not successful, try again.<br /> Caused :
                        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                    </div>
                </c:if>
                <form name="login" id="login-form" action="<c:url value='../j_spring_security_check' />" class="form" method="POST">
                    <div class="form-group">
                        <label for="login-username">Usuario</label>
                        <input type="text" class="form-control" name="j_username" id="login-username" placeholder="Usuario">
                    </div>
                    <div class="form-group">
                        <label for="login-password">Contrase&ntilde;a</label>
                        <input type="password" class="form-control" name="j_password" id="login-password" placeholder="Contrase&ntilde;a">
                    </div>
                    <div class="form-group">
                        <button type="submit" id="login-btn" class="btn btn-primary btn-block">Iniciar &nbsp; <i class="fa fa-play-circle"></i></button>
                    </div>
                </form>
            </div> <!-- /#login -->
        </div> <!-- /#login-container -->

        <script src="../js/libs/jquery-1.11.3.js" type="text/javascript"></script>
        <script src="../js/libs/jquery-ui.js" type="text/javascript"></script>
        <script src="../js/libs/bootstrap.min.js"></script>

        <script src="../js/libs/App.js"></script>
        <script src="../js/libs/Login.js" type="text/javascript"></script>
    </body>
</html>
