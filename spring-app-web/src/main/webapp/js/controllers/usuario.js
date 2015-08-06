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


    var dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 275,
        width: 300,
        modal: true
    });

    btnAdd.click(function () {
        $.limpiarCampos($('#formUsuario'));
        $('#idUsuario').val('0');
        dialog.dialog('option', 'title', 'Nuevo Usuario');
        dialog.dialog('open');
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
                success: function (data) {
                    console.log(data);
                    $('#idUsuario').val(data.respuesta.idUsuario);
                    $('#usuario').val(data.respuesta.usuario);
                    $('#contrasenna').val(data.respuesta.contrasenna);
                    dialog.dialog('option', 'title', 'Editar Usuario');
                    dialog.dialog('open');
                },
                error: function () {
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
            $("#dialog-confirm").dialog('open');
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
            success: function (data) {
                if (!data.hasOwnProperty('error')) {
                    jqgrid.trigger("reloadGrid");
                }
                else {
                    alert('Ocurrio un error eliminando el registro');
                }
            },
            error: function (error) {
                alert(error);
            }
        });
    });

    btnCancel.click(function () {
        dialog.dialog('close');
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

    $(window).bind('resize', function () {
        var width = $('.jqgridWrapper').width();
        jqgrid.setGridWidth(width);
    });
});


