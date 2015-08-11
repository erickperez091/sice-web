$(document).ready(function () {

    var getContextPath = function () {
        return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    };

    var contextPath = getContextPath();

    var btnAdd = $('#btnAdd');
    var btnSearch = $('#btnSearch');
    var btnDel = $('#btnDel');
    var btnAccept = $('#btnAccept');
    var btnCancel = $('#btnCancel');
    var usuarioForm = $('#modalUsuario');
    var tituloModal = $('#modalColaborador .modal-title');
    var usuario;

    var jqgrid = $('#jqgrid').jqGrid({
        url: contextPath + '/Usuario/ListarUsuarios',
        mtype: 'POST',
        datatype: 'json',
        jsonReader: {
            repeatitems: false,
            root: 'rows'
        },
        colNames: ['Id', 'Nombre Usuario', 'Contrase&ntilde;a'],
        colModel: [
            {name: 'idUsuario', index: 'idUsuario', align: 'center', width: 0, search: false, hidden: true},
            {name: 'usuario', index: 'usuario', align: 'center', width: 100, search: false, editable: true},
            {name: 'contrasenna', index: 'contrasenna', align: 'center', width: 80, search: false, editable: true}
        ],
        caption: 'Lista de Usuarios',
        pager: '#pager',
        height: 'auto',
        rowNum: 15,
        autowidth: true,
        rowList: [15, 30, 45],
        sortname: 'idUsuario',
        sortorder: 'asc',
        viewrecords: true,
        pagination: true,
        pgbuttons: true,
        emptyrecords: "No hay datos disponibles"
    });
    
    $.alerta = function(mensaje){
        bootbox.alert({
            size: 'small',
            message: mensaje
        });
    };
    
    btnAdd.click(function () {
        $.limpiarCampos($('#formUsuario'));
        usuarioForm.modal('show');
        tituloModal.text('Nuevo Usuario');
        $('#idUsuario').val('0');
    });

    btnSearch.click(function () {
        var idRow = jqgrid.jqGrid('getGridParam', 'selrow');
        if (!idRow) {
            $.howl({
                type: $(this).data('type'),
                title: 'Advertencia',
                sticky: $(this).data('sticky'),
                content: 'Debe seleccionar un registro',
                lifetime: 3000,
                iconCls: $(this).data('icon')
            });
        } else {
            var datos = jqgrid.jqGrid('getRowData', idRow, 'idUsuario');
            $.ajax({
                url: contextPath + '/Usuario/ObtenerUsuario',
                type: 'GET',
                dataType: 'JSON',
                data: {'identificacion': datos.idUsuario},
                beforeSend: function () {
                    $.lockScreen();
                },
                success: function (data) {
                    console.log(data);
                    $('#idUsuario').val(data.respuesta.idUsuario);
                    $('#usuario').val(data.respuesta.usuario);
                    $('#contrasenna').val(data.respuesta.contrasenna);
                    $.unblockUI();
                    tituloModal.text('Editar Usuario');
                    usuarioForm.modal('show');
                },
                error: function () {
                    $.unblockUI();
                    $.howl({
                        type: $(this).data('type'),
                        title: 'Advertencia',
                        sticky: $(this).data('sticky'),
                        content: 'Ocurri&oacute; un error cargando el usuario.',
                        lifetime: 3000,
                        iconCls: $(this).data('icon')
                    });
                }
            });
        }
    });

    btnDel.click(function () {
        var idRow = jqgrid.jqGrid('getGridParam', 'selrow');
        if (!idRow) {
            $.howl({
                type: $(this).data('type'),
                title: 'Advertencia',
                sticky: $(this).data('sticky'),
                content: 'Debe seleccionar un registro',
                lifetime: 3000,
                iconCls: $(this).data('icon')
            });
        } else {
            usuario = jqgrid.jqGrid('getRowData', idRow, 'idUsuario');
            var mensajes = {
                pregunta: 'Seguro que desea eliminar el colaborador seleccionado?',
                exitoso: 'Eliminado exitosamente',
                fallido: 'Ocurrio un error al eliminar el colaborador'
            };
            $.confirmDelete(usuario, contextPath + '/Usuario/EliminarUsuario', jqgrid, mensajes);
        }
    });

    btnAccept.click(function () {
        $.ajax({
            url: contextPath + '/Usuario/AgregarUsuario',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            //contentType: 'application/json',
            dataType: 'JSON',
            data: $('#formUsuario').serialize(),
            //data: JSON.stringify($.serializeObject($('#formUsuario'))),
            beforeSend: function () {
                $.lockScreen();
            },
            success: function (data) {
                if (!data.errorActual) {
                    jqgrid.trigger("reloadGrid");
                    usuarioForm.modal('hide');
                    $.alerta('Registro creado exitosamente');
                }
                else {
                    alert('Ocurrio un error agregando el registro');
                }
                $.unblockUI();
            },
            error: function (error) {
                $.unblockUI();
                $.alerta('Ocurrio un error agregando el registro');
            }
        });
    });

    btnCancel.click(function () {
        usuarioForm.modal('hide');
    });

    $("#dialog-confirm").dialog({
        autoOpen: false,
        resizable: false,
        height: 180,
        modal: true,
        buttons: {
            'Aceptar': function () {
                $.ajax({
                    url: contextPath + '/Usuario/EliminarUsuario',
                    type: 'POST',
                    dataType: 'JSON',
                    data: $('#formUsuario').serialize(),
                    success: function (data) {
                        if (!data.hasOwnProperty('error')) {
                            jqgrid.trigger("reloadGrid");
                        }
                        else {
                            alert('Ocurrio un error eliminando el registro');
                        }
                    }
                });
                $(this).dialog('close');
            },
            'Cancelar': function () {
                $.limpiarCampos($('#formUsuario'));
                $(this).dialog('close');
            }
        }
    });

    $.limpiarCampos = function (form) {
        $(':hidden').val('');
        $(form).each(function () {
            this.reset();
        });
    };

    $.serializeObject = function (form)
    {
        var o = {};
        var a = form.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    $.lockScreen = function () {
        $.blockUI({
            message: 'Espere, por favor...',
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#000',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .5,
                color: '#fff'
            }});
    };

    $(window).bind('resize', function () {
        var width = $('.jqgridWrapper').width();
        jqgrid.setGridWidth(width);
    });
});


