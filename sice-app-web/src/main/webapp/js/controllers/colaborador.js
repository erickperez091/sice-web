$(document).ready(function () {

    var contextPath = $.getContextPath();

    var btnAdd = $('#btnAdd');
    var btnSearch = $('#btnSearch');
    var btnDel = $('#btnDel');
    var btnAccept = $('#btnAccept');
    var btnCancel = $('#btnCancel');
    var colaboradorForm = $('#modalColaborador');
    var tituloModal = $('#modalColaborador .modal-title');
    var isNew = false;
    var colaborador;

    $.alerta = function (mensaje, esExitoso) {

        var boton;
        if (esExitoso) {
            boton = {
                label: 'Exitoso',
                claseCss: 'btn-success'
            }
        } else {
            boton = {
                label: 'Fallido',
                claseCss: 'btn-danger'
            }
        }
        bootbox.alert({
            size: 'small',
            message: mensaje,
            buttons: {
                ok: {
                    label: boton.label,
                    className: boton.claseCss
                }
            }
        });
    };

    btnAdd.click(function () {
        $.limpiarCampos($('#formColaborador'));
        tituloModal.text('Nuevo Colaborador');
        $('#idColaborador').val('0');
        $('#edad').val('');
        isNew = true;
        colaboradorForm.modal('show');
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
            var datos = jqgrid.jqGrid('getRowData', idRow, 'idColaborador');
            $.ajax({
                url: contextPath + '/Colaborador/ObtenerColaborador',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'identificacion': datos.idColaborador
                },
                beforeSend: function () {
                    $.lockScreen();
                },
                success: function (data) {
                    isNew = false;
                    $('#idColaborador').val(data.respuesta.idColaborador);
                    $('#identificacion').val(data.respuesta.identificacion);
                    $('#nombre').val(data.respuesta.nombre);
                    $('#direccion').val(data.respuesta.direccion);
                    $('#telefono').val(data.respuesta.telefono);
                    $('#edad').val(data.respuesta.edad);
                    $.unblockUI();
                    tituloModal.text('Editar Colaborador');
                    colaboradorForm.modal('show');
                },
                error: function () {
                    $.unblockUI();
                    $.howl({
                        type: $(this).data('type'),
                        title: 'Advertencia',
                        sticky: $(this).data('sticky'),
                        content: 'Ocurri&oacute; un error cargando el colaborador.',
                        lifetime: 3000,
                        iconCls: $(this).data('icon')
                    });
                }
            });
        }
    });

    btnAccept.click(function () {
        if (isNew) {
            var mensajes = {
                exito: 'Colaborador agregado exitosamente',
                error: 'Ocurrio un error al agregar el nuevo colaborador'
            };
            $.ejecutarBtnAccept(contextPath + '/Colaborador/AgregarColaborador', colaboradorForm, jqgrid, mensajes);
        }
        else {
            var mensajes = {
                exito: 'Colaborador actualizado exitosamente',
                error: 'Ocurrio un error al actualizar el nuevo colaborador'
            };
            $.ejecutarBtnAccept(contextPath + '/Colaborador/ActualizarColaborador', colaboradorForm, jqgrid, mensajes);
        }
    });

    btnCancel.click(function () {
        colaboradorForm.modal('hide');
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
            colaborador = jqgrid.jqGrid('getRowData', idRow, 'idColaborador');
            var mensajes = {
                pregunta: 'Seguro que desea eliminar el colaborador seleccionado?',
                exitoso: 'Eliminado exitosamente',
                fallido: 'Ocurrio un error al eliminar el colaborador'
            };
            $.confirmDelete(colaborador, contextPath + '/Colaborador/EliminarColaborador', jqgrid, mensajes);
        }
    });

    $.limpiarCampos = function (form) {
        $(':hidden').val('');
        $(form).each(function () {
            this.reset();
        });
    };

    $.serializeObject = function (form) {
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

    var jqgrid = $('#jqgrid').jqGrid({
        url: contextPath + '/Colaborador/ListarColaboradores',
        mtype: 'POST',
        datatype: 'json',
        jsonReader: {
            repeatitems: false,
            root: 'rows'
        },
        colNames: ['Id', 'Nombre', 'Identificaci&oacute;n', 'Tel&eacute;fono', 'Direcci&oacute;n', 'Edad'],
        colModel: [{
                name: 'idColaborador',
                index: 'idColaborador',
                align: 'center',
                width: 0,
                search: false,
                hidden: true
            }, {
                name: 'nombre',
                index: 'nombre',
                align: 'center',
                width: 100,
                search: false,
                editable: true
            }, {
                name: 'identificacion',
                index: 'identificacion',
                align: 'center',
                width: 80,
                search: false,
                editable: true
            }, {
                name: 'telefono',
                index: 'telefono',
                align: 'center',
                width: 60,
                search: false,
                editable: true
            }, {
                name: 'direccion',
                index: 'direccion',
                align: 'center',
                width: 150,
                search: false,
                editable: true
            }, {
                name: 'edad',
                index: 'edad',
                align: 'center',
                width: 30,
                search: false,
                editable: true
            }],
        caption: 'Lista de Colaboradores',
        pager: '#pager',
        height: 'auto',
        rowNum: 15,
        autowidth: true,
        rowList: [15, 30, 45],
        sortname: 'idColaborador',
        sortorder: 'asc',
        viewrecords: true,
        pagination: true,
        pgbuttons: true,
        emptyrecords: "No hay datos disponibles"
    });
});
