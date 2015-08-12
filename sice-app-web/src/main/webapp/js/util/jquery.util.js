$.getContextPath = function() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
};

$.confirmDelete = function(instancia, url, grid, mensajes) {
	bootbox.confirm({
		size : 'small',
		message : mensajes.pregunta,
		callback : function(result) {
			if (result) {
				$.ajax({
					url : url,
					type : 'POST',
					dataType : 'JSON',
					contentType : 'application/json',
					data : JSON.stringify(instancia),
					beforeSend : function() {
						$.lockScreen();
					},
					success : function(data) {
						$.unblockUI();
						grid.trigger("reloadGrid");
						$.alerta(mensajes.exitoso, true);
					},
					error : function() {
						$.unblockUI();
						$.alerta(mensajes.fallido, false);
					}
				});
			}
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
