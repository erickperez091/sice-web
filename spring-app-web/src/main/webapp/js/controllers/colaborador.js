$(document).ready(function () {
    var getContextPath = function () {
        return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    };
    var btnAdd = $('#btnAdd');
    var btnSearch = $('#btnSearch');
    var btnDel = $('#btnDel');
    var colaborador;

    var jqgrid = $('#jqgrid').jqGrid({
        url: getContextPath() + '/Colaborador/ListarColaboradores',
        mtype: 'POST',
        datatype: 'json',
        jsonReader: {
            repeatitems: false,
            root: 'rows'
        },
        colNames: ['Id', 'Nombre', 'Identificaci&oacute;n', 'Tel&eacute;fono', 'Direcci&oacute;n', 'Edad'],
        colModel: [
            {name: 'idColaborador', index: 'idColaborador', align: 'center', width: 0, search: false, hidden: true},
            {name: 'nombre', index: 'nombre', align: 'center', width: 100, search: false, editable: true},
            {name: 'identificacion', index: 'identificacion', align: 'center', width: 80, search: false, editable: true},
            {name: 'telefono', index: 'telefono', align: 'center', width: 60, search: false, editable: true},
            {name: 'direccion', index: 'direccion', align: 'center', width: 150, search: false, editable: true},
            {name: 'edad', index: 'edad', align: 'center', width: 30, search: false, editable: true}
        ],
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

    var dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 275,
        width: 300,
        modal: true
    });
     
    btnAdd.click(function () {
        $.limpiarCampos($('#formColaborador'));
        dialog.dialog('option', 'title', 'Nuevo Colaborador');
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
            var datos = jqgrid.jqGrid('getRowData', idRow, 'idColaborador');
            $('#idColaborador').val(datos.idColaborador);
            $('#identificacion').val(datos.identificacion);
            $('#nombre').val(datos.nombre);
            $('#direccion').val(datos.direccion);
            $('#telefono').val(datos.telefono);
            $('#edad').val(datos.edad);
            //dialog.dialog('option', 'title', 'Editar Colaborador');
            //dialog.dialog('open');
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
            colaborador = jqgrid.jqGrid('getRowData', idRow, 'idColaborador');
            $('#idColaborador').val(colaborador.idColaborador);
            $('#identificacion').val(colaborador.identificacion);
            $('#nombre').val(colaborador.nombre);
            $('#direccion').val(colaborador.direccion);
            $('#telefono').val(colaborador.telefono);
            $('#edad').val(colaborador.edad);
            //$("#dialog-confirm").dialog('open');
        }
    });

    /*$("#dialog-confirm").dialog({
        autoOpen: false,
        resizable: false,
        height: 180,
        modal: true,
        buttons: {
            'Aceptar': function () {
                $.ajax({
                    url: getContextPath() + '/Colaborador/EliminarColaborador',
                    type: 'POST',
                    dataType: 'JSON',
                    data: $('#formColaborador').serialize(),
                    success: function (data) {
                        if (!data.hasOwnProperty('error')) {
                            jqgrid.trigger("reloadGrid");
                        }
                        else {
                            alert('Ocurrio un error eliminando el registro');
                        }
                    }
                });
                $(this).dialog('close')
            },
            'Cancelar': function () {
                $.limpiarCampos($('#formColaborador'));
                $(this).dialog('close');
            }
        }
    });*/

    $.limpiarCampos = function (form) {
        $(':hidden').val('');
        $(form).each(function () {
            this.reset();
        });
    };

});


