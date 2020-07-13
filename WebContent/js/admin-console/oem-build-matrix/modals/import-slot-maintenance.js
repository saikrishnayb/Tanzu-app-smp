$('#sample-input').on('change', function(){
	if($(this).val().trim() != '')
		$('#import-slots-btn').removeClass('buttonDisabled');
	else
		$('#import-slots-btn').addClass('buttonDisabled');
});

$("#import-slots-btn").on("click", function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	$("#import-form").submit();
});


//# sourceURL=import-slot-maintenance.js