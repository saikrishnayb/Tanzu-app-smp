$('#sample-input').on('change', function(){
	if($(this).val().trim() != '')
		$('#import-slots-btn').removeClass('buttonDisabled');
	else
		$('#import-slots-btn').addClass('buttonDisabled');
});

$("#import-slots-btn").on("click", function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var slotDesc = $(this).data('slot-desc');
	var year = $(this).data('year');
	var slotDescArray = slotDesc.split(" ");
	var fileNameArray = $('#file-name').val().split('\\')[2].split('_');
	var slotTypeIncorrect = false;
	var yearIncorrect = false;
	
	slotDescArray.forEach(function(item, index) {
		if(slotDescArray[index] != fileNameArray[index])
			slotTypeIncorrect = true;
	});
	
	if(year != fileNameArray[slotDescArray.length])
		yearIncorrect = true;
	
	if(slotTypeIncorrect || yearIncorrect){
		$('.errorMsg').text("Slot Type and/or Year in the filename does not match the Slot Type and/or Year being imported. Check the file to see if it is the correct one.");
		$('#errorMessage').show();
		
		return false;
	}
	
	
	$("#import-form").submit();
});


//# sourceURL=import-slot-maintenance.js