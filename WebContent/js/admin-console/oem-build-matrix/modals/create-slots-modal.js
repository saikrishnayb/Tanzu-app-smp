var $generateButton = $('#generate-slots')

$('#years, #slot-types').on('change', function(){
	if($('#years').val() == ''|| $('#slot-types').val() == ''){
		$generateButton.addClass('buttonDisabled');
	}
	else {
		$generateButton.removeClass('buttonDisabled');
	}
});

$generateButton.on('click', function(){
	if($generateButton.hasClass('buttonDisabled'))
		return false;
	
	var year = $('#years').val();
	var slotTypeId = $('#slot-types').val();
	
	var $checkSlotsExistPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/check-slots-exist',
		data: {
			year: year,
			slotTypeId: slotTypeId,
		}
	});
	$checkSlotsExistPromise.done(function(slotsExist){
		if(slotsExist) {
			$('#create-slots-form').find('.errorMsg').text('Slots already exist for this slot type/year combo');
			$('#create-slots-form').find('.error').removeClass('hidden');
		}
		else {
			$('#create-slots-form').find('.error').addClass('hidden');
			
			var $createSlotsPromise = $.ajax({
				type: "POST",
				url: baseBuildMatrixUrl + '/create-slots',
				data: {
					year: year,
					slotTypeId: slotTypeId,
				}
			});
			
			$createSlotsPromise.done(function(data){
				window.location.href = baseBuildMatrixUrl + "/prod-slot-maintenance.htm?slotType=" + slotTypeId + "&year=" + year;
			});
		}
	});
	
});

//# sourceURL=create-slots-modal.js