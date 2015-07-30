<%-- 
    Document   : header
    Created on : 16/07/2015, 08:13:45 PM
    Author     : Erick
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header id="header">
    <h1 id="site-logo">
        <a href="<c:url value='/Home/Principal'/>">
            <img src="../css/img/logo.png" alt="Site Logo" />
        </a>
    </h1>
    <a href="javascript:;" data-toggle="collapse" data-target=".top-bar-collapse" id="top-bar-toggle" class="navbar-toggle collapsed">
        <i class="fa fa-cog"></i>
    </a>
    <a href="javascript:;" data-toggle="collapse" data-target=".sidebar-collapse" id="sidebar-toggle" class="navbar-toggle collapsed">
        <i class="fa fa-reorder"></i>
    </a>
</header> <!-- header -->