$( "#start-date" ).datepicker({ 
	dateFormat: 'mm/dd/yy',
    //changeMonth:true,
    //changeYear:true,
    showOn: "button",
    buttonImage: "../../../images/calendar.png",
    buttonImageOnly: true,
    monthNamesShort: $.datepicker._defaults.monthNames,
    showButtonPanel:true,
    buttonText: "Open Calendar",
      altField: "#datepickerStartHidden",
      altFormat: "mm/dd/yy",
    onSelect: function(dateText, inst) {
          $(this).datepicker('option','buttonImage','../../../images/calendar.png');
          },
    onClose: function(dateText, inst) {
    	  $(this).datepicker('option','buttonImage','../../../images/calendar.png');
          },
    beforeShow: function(input, inst) {
        $(this).datepicker('option','buttonImage','../../../images/calendar_selected.png');
    }
});

$( "#end-date" ).datepicker({ 
	dateFormat: 'mm/dd/yy',
    //changeMonth:true,
    //changeYear:true,
    showOn: "button",
    buttonImage: "../../../images/calendar.png",
    buttonImageOnly: true,
    monthNamesShort: $.datepicker._defaults.monthNames,
    showButtonPanel:true,
    buttonText: "Open Calendar",
      altField: "#datepickerEndHidden",
      altFormat: "mm/dd/yy",
    onSelect: function(dateText, inst) {
          $(this).datepicker('option','buttonImage','../../../images/calendar.png');
          },
    onClose: function(dateText, inst) {
    	  $(this).datepicker('option','buttonImage','../../../images/calendar.png');
          },
    beforeShow: function(input, inst) {
        $(this).datepicker('option','buttonImage','../../../images/calendar_selected.png');
    }
});
