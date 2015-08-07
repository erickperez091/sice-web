<%-- 
    Document   : menu
    Created on : 16/07/2015, 08:14:56 PM
    Author     : Erick
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<tilesx:useAttribute name="current" />

<nav id="top-bar" class="collapse top-bar-collapse">
    <ul class="nav navbar-nav pull-left">
        <li class="">
        </li>
        <li class="">
        </li>

    </ul>

    <ul class="nav navbar-nav pull-right">
        <li class="">
            <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
                <i class="fa fa-user"></i>
               
                ${usuario}
                <span class="caret"></span>
            </a>

            <ul class="dropdown-menu" role="menu">
                <li>
                    <a href="<c:url value='/Home/Logout'/>">
                        <i class="fa fa-sign-out"></i> 
                        &nbsp;&nbsp;Logout
                    </a>
                </li>
            </ul>
        </li>
    </ul>
</nav> <!-- /#top-bar -->
<div id="sidebar-wrapper" class="collapse sidebar-collapse">
    <div id="search">
        <form>
        </form>		
    </div> <!-- #search -->
    <nav id="sidebar">		
        <ul id="main-nav" class="open-active">			
            <li class="${current == 'Principal' ? 'active' : ''}">				
                <a href="<c:url value='/Home/Principal'/>">
                    <i class="fa fa-dashboard"></i>
                    Inicio
                </a>				
            </li>
            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <li class="${current == 'mantenimientousuario' ? 'active' : ''}">				
                    <a href="<c:url value='/Usuario/Mantenimiento-Usuarios'/>">
                        <i class="fa fa-dashboard"></i>
                        Mantenimiento Usuarios
                    </a>				
                </li>
            </sec:authorize>
        </ul>

    </nav> <!-- #sidebar -->

</div> <!-- /#sidebar-wrapper -->
