<%-- 
    Document   : default
    Created on : 14/05/2015, 06:12:30 PM
    Author     : erick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="javascripts"/>
<tiles:importAttribute name="stylesheets"/>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="">
        <meta name="author" content="" />
        
        <c:forEach var="css" items="${stylesheets}">
            <link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
        </c:forEach>
<!--        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800" type="text/css">
        <link href="../css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="../css/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/jqgrid/ui.jqgrid.css" rel="stylesheet" type="text/css"/>
        <link href="../css/jqgrid/ui.jqgrid-bootstarp.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css" type="text/css" />
        <link rel="stylesheet" href="./js/plugins/select2/select2.css" type="text/css" />
        <link rel="stylesheet" href="./js/plugins/fullcalendar/fullcalendar.css" type="text/css" />
        <link href="../css/App.css" rel="stylesheet" type="text/css"/>
        <link href="../css/custom.css" rel="stylesheet" type="text/css"/>-->
    </head>
    <body>
        <div id="wrapper">
            <tiles:insertAttribute name="header"/>

            <tiles:insertAttribute name="menu" />

            <tiles:insertAttribute name="body" />

        </div> <!-- #wrapper -->
        <tiles:insertAttribute name="footer" />

        <c:forEach var="script" items="${javascripts}">
            <script src="<c:url value="${script}"/>"></script>
        </c:forEach>
        <!--        <script src="../js/libs/jquery-1.11.3.js" type="text/javascript"></script>
                <script src="../js/libs/jquery-ui.js" type="text/javascript"></script>
                <script src="../js/libs/bootstrap.min.js"></script>
                <script src="../js/libs/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
        
                <script src="../js/libs/jquery.magnific-popup.min.js" type="text/javascript"></script>
                <script src="../js/libs/howl.js" type="text/javascript"></script>
                <script src="../js/libs/jqgrid/grid.locale-es.js" type="text/javascript"></script>
                <script src="./js/plugins/icheck/jquery.icheck.min.js"></script>
                <script src="./js/plugins/select2/select2.js"></script>
                <script src="./js/plugins/tableCheckable/jquery.tableCheckable.js"></script>
        
        
                <script src="../js/libs/App.js"></script>-->
        <!--<script src="./js/libs/raphael-2.1.2.min.js"></script>
        <script src="./js/plugins/morris/morris.min.js"></script>-->

        <!--<script src="./js/demos/charts/morris/area.js"></script>
        <script src="./js/demos/charts/morris/donut.js"></script>

        <script src="./js/plugins/sparkline/jquery.sparkline.min.js"></script>

        <script src="./js/plugins/fullcalendar/fullcalendar.min.js"></script>
        <script src="./js/demos/calendar.js"></script>-->

        <!--<script src="../js/libs/dashboard.js"></script>-->

    </body>
</html>