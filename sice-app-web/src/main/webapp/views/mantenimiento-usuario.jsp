<%-- 
    Document   : mantenimiento-usuarios
    Created on : 14/05/2015, 06:12:30 PM
    Author     : erick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div id="content">
    <div id="content-header">
        <h1>Mantenimiento Usuarios</h1>
    </div> <!-- #content-header -->	


    <div id="content-container">
        <br />
        <div class="row">
            <div class="col-md-12">
                <%--<a data-toggle="modal" href="#styledModal" class="ui-state-default ui-corner-all"><i class="fa fa-plus-circle"></i></a>--%>
                <button id="btnAdd" class="ui-state-default ui-corner-all" type="button"><i class="fa fa-plus-circle"><a data-toggle="modal" href="#styledModal"></a></i></button>
                <button id="btnSearch" class="ui-state-default ui-corner-all howler" data-icon="fa fa-exclamation" data-sticky="true" data-type="warning" type="button"><i class="fa fa-search"></i></button>
                <button id="btnDel" class="ui-state-default ui-corner-all howler" data-icon="fa fa-exclamation" data-sticky="true" data-type="warning" type="button"><i class="fa fa-trash-o"></i></button>
                <div class="jqgridWrapper">
                    <table id="jqgrid"></table>
                    <div id="pager"></div>
                </div>
            </div>
        </div> <!-- /.row -->

        <div class="row">
        </div> <!-- /.row -->
    </div> <!-- /#content-container -->   
</div> <!-- #content -->	



<div id="dialog-form" title="Nuevo Usuario">
    <p class="validateTips"></p>

</div>



<div id="styledModal" class="modal modal-styled fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form:form id="formUsuario" modelAttribute="usuario">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title">Usuario</h3>
                </div>
                <div class="modal-body">

                    <form:hidden id="idUsuario" path="idUsuario" />
                    <fieldset>
                        <table>
                            <tr>
                                <td><label for="usuario">Nombre de Usuario: &nbsp;
                                        &nbsp;</label></td>
                                <td><form:input type="text" path="usuario"
                                            id="usuario" class="text ui-widget-content ui-corner-all" /></td>
                            </tr>
                            <tr>
                                <td><label for="contrasenna">Contrase&ntilde;a: &nbsp;</label></td>
                                <td><form:password path="contrasenna" id="contrasenna" class="text ui-widget-content ui-corner-all" /></td>
                            </tr>
                        </table>
                        <br />
                    </fieldset>

                </div>
                <div class="modal-footer">
                    <button id="btnCancel" type="button" class="btn btn-tertiary" data-dismiss="modal">Close</button>
                    <button id="btnAccept" type="button" class="btn btn-primary">Save changes</button>
                </div>
            </form:form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->