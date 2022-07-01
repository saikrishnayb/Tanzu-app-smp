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
	var region = $(this).data('region');
	var slotDescArray = slotDesc.split(" ");
	var fileNameArray = $('#file-name').val().split('\\')[2].split('_');
	var slotTypeIncorrect = false;
	var yearIncorrect = false;
	var regionIncorrect = false;
	
	if('Region' != fileNameArray[0])
		regionIncorrect = true;
	else if(region != fileNameArray[1])
		regionIncorrect = true;
	
	slotDescArray.forEach(function(item, index) {
		if(slotDescArray[index] != fileNameArray[index + 2])
			slotTypeIncorrect = true;
	});
	
	if(year != fileNameArray[slotDescArray.length + 2])
		yearIncorrect = true;
	
	if(slotTypeIncorrect || yearIncorrect || regionIncorrect){
		$('.errorMsg').text("Region, Slot Type, and/or Year in the filename does not match the Region, Slot Type, and/or Year being imported. Check the file to see if it is the correct one.");
		$('#errorMessage').show();
		
		return false;
	}
	
	
	$("#import-form").submit();
});


//# sourceURL=import-region-slot-maintenance.js