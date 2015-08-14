$.getContextPath = function () {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
};

$.ejecutarBtnAccept = function (url, form, grid, mensajes)
{
    $.ajax({
        url: url, //contextPath + '/Colaborador/AgregarColaborador',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        // contentType: 'application/json',
        dataType: 'JSON',
        data: $('#formColaborador').serialize(),
        // data:
        // JSON.stringify($.serializeObject($('#formColaborador'))),
        beforeSend: function () {
            $.lockScreen();
        },
        success: function (data) {
            if (!data.errorActual) {
                grid.trigger("reloadGrid");
                form.modal('hide');
                $.alerta(mensajes.exito, true);
            } else {
                $.alerta(mensajes.error, false);
            }
            $.unblockUI();
        },
        error: function (error) {
            $.unblockUI();
            $.alerta(mensajes.error, false);
        }
    });
};

$.confirmDelete = function (instancia, url, grid, mensajes) {
    bootbox.confirm({
        size: 'small',
        message: mensajes.pregunta,
        callback: function (result) {
            if (result) {
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'JSON',
                    contentType: 'application/json',
                    data: JSON.stringify(instancia),
                    beforeSend: function () {
                        $.lockScreen();
                    },
                    success: function (data) {
                        $.unblockUI();
                        grid.trigger("reloadGrid");
                        $.alerta(mensajes.exitoso, true);
                    },
                    error: function () {
                        $.unblockUI();
                        $.alerta(mensajes.fallido, false);
                    }
                });
            }
        }
    });
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
        }
    });
};
/*
 * (function ($) { $.extend({ confirmDelete: function (instancia, url, grid,
 * mensajes) { bootbox.confirm({ size: 'small', message: mensajes.pregunta,
 * callback: function (result) { if (result) { $.ajax({ url: url, type: 'POST',
 * dataType: 'JSON', contentType: 'application/json', data:
 * JSON.stringify(instancia), beforeSend: function () { $.lockScreen(); },
 * success: function (data) { $.unblockUI(); grid.trigger("reloadGrid");
 * $.alerta(mensajes.exitoso); }, error: function () { $.unblockUI();
 * $.alerta(mensajes.fallido); } }); } } }); } }); })(jQuery);
 */
