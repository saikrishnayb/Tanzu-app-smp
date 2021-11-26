
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
		},
		onClose : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
		},
		beforeShow : function(input, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar_selected.png');
		},
		//Override for beforeShowDay, since this can't be after the selected end date
		beforeShowDay: function (date) {
			var endDatepicker = $(this).closest('.row').find('.end-date');
			var endDate = endDatepicker.datepicker('getDate');
			var dayIsSelectable = !endDate || date.getTime() <= endDate.getTime();
			return [dayIsSelectable, ""];
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
		},
		onClose : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
		},
		beforeShow : function(input, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar_selected.png');
		},
		//Override for beforeShowDay, since this can't be before the selected end date
		beforeShowDay: function (date) {
			var startDatepicker = $(this).closest('.row').find('.start-date');
			var startDate = startDatepicker.datepicker('getDate');
			var dayIsSelectable = !startDate || date.getTime() >= startDate.getTime();
			return [dayIsSelectable, ""];
		}
	});

	$(".start-date").mask("99/99/9999");
	$(".end-date").mask("99/99/9999");

}
//# sourceURL=set-offline-dates-modal.js