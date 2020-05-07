
$(document).ready(initializeDatePicker);

function initializeDatePicker() {
	$(".start-date").datepicker({
		dateFormat : 'mm/dd/yy',
		changeMonth : true,
		changeYear : true,
		showOn : "button",
		buttonImage : "../../../images/calendar.png",
		buttonImageOnly : true,
		monthNamesShort : $.datepicker._defaults.monthNames,
		showButtonPanel : true,
		buttonText : "Open Calendar",
		altField : ".datepickerStartHidden",
		altFormat : "mm/dd/yy",
		onSelect : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
			$('#save-offline-dates').removeClass("buttonDisabled");
		},
		onClose : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
		},
		closeText:'Clear',
		beforeShow : function(input, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar_selected.png');
			setTimeout(function() {
				var clearButton = $(input )
					.datepicker( "widget" )
					.find( ".ui-datepicker-close" );
				clearButton.unbind("click").bind("click",function(){$.datepicker._clearDate( input );});
				}, 1 );
		}
	});

	$(".end-date").datepicker({
		dateFormat : 'mm/dd/yy',
		changeMonth:true,
		changeYear:true,
		showOn : "button",
		buttonImage : "../../../images/calendar.png",
		buttonImageOnly : true,
		monthNamesShort : $.datepicker._defaults.monthNames,
		showButtonPanel : true,
		buttonText : "Open Calendar",
		altField : ".datepickerEndHidden",
		altFormat : "mm/dd/yy",
		onSelect : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
			$('#save-offline-dates').removeClass("buttonDisabled");
		},
		onClose : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
		},
		closeText:'Clear',
		beforeShow : function(input, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar_selected.png');
			setTimeout(function() {
				var clearButton = $(input )
					.datepicker( "widget" )
					.find( ".ui-datepicker-close" );
				clearButton.unbind("click").bind("click",function(){$.datepicker._clearDate( input );});
				}, 1 );
		}
	});

	$(".start-date").mask("99/99/9999");
	$(".end-date").mask("99/99/9999");

}