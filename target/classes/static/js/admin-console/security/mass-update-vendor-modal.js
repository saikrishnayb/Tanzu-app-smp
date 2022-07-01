var $massUpdateModal = $('#mass-update-modal');

$(document).ready(function() {
	$massUpdateModal.unbind();
	
	$massUpdateModal.on('click', '.save', function() {
		var $form = $('#mass-update-form');
		var $error = $form.find('.error');
		
		var vendorData = $form.serialize();
		var vendorIds = [];
		
		var errorMsg = validateMassUpdateForm($form);
		
		if (errorMsg.length == 0) {
			$error.hide();
			
			$vendorTable.find('tr').each(function() {
				var $checkbox = $(this).find('.update-checkbox');
				var vendorId = $(this).find('[name="vendorId"]').val();
				
				if ($checkbox.is(':checked')) {
					vendorIds.push(vendorId);
				}
			});
			
			vendorData = vendorData + '&vendorIds=' + vendorIds.join();
			
			$.post('./mass-update-vendors',
					vendorData,
					function(data) {
						//location.reload();
						//location.assign('./vendors');
						 submitAdvancedSearch();//vod-982 change.
					});
		}
		else {
			$error.find('.errorMsg').text(errorMsg);
			$error.show();
		}
	});
	
	$massUpdateModal.on('keypress', function(e) {
		if (e.which == 13) {
			$massUpdateModal.find('.save').trigger('click');
		}
	});
	
	$massUpdateModal.find('form').submit(function() {
		return false;
	});
	
	$massUpdateModal.on('click', '.cancel', function() {
		ModalUtil.closeModal($globalModal);
	});
});

function validateMassUpdateForm($form) {
	var $notificationException = $form.find('[name="notificationException"]');
	var $planningAnalyst = $form.find('[name="planningAnalyst.userId"]');
	//var $supplySpecialist = $form.find('[name="supplySpecialist.userId"]');
	
	var errorMsg = '';
	
	if ($notificationException.val().length == 0) {
		$notificationException.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$notificationException.removeClass('errorMsgInput');
	}
	
	if ($planningAnalyst.val().length == 0) {
		$planningAnalyst.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$planningAnalyst.removeClass('errorMsgInput');
	}
	
/*	if ($supplySpecialist.val().length == 0) {
		$supplySpecialist.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$supplySpecialist.removeClass('errorMsgInput');
	}
*/	
	return errorMsg;
}

//# sourceURL=mass-update-vendor-modal.js