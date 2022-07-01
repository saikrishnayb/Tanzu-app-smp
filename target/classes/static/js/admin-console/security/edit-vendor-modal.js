var $editVendorModal  = $('#edit-vendor-modal');

$(document).ready(function() {
	$editVendorModal.unbind();
	
	$editVendorModal.on('click', '.save', function() {
		var $form = $editVendorModal.find('form');
		var $error = $form.find('.error');
		
		var errorMsg = validateEditForm($form);
		
		if (errorMsg.length == 0) {
			var vendor = $form.find(':not(.primary-contact, .secondary-contact)').serialize();
			
			$error.hide();
			
			// Only include the primary contact if the user entered information for it.
			if ($form.find('.primary-contact:not([type="hidden"])[value!=""]').length > 0) {
				vendor = vendor + '&' + $form.find('.primary-contact').serialize();
			}
			if ($form.find('.secondary-contact:not([type="hidden"])[value!=""]').length > 0) {
				vendor = vendor + '&' + $form.find('.secondary-contact').serialize();
			}
			
			$.post('./modify-vendor-info',
					vendor,
					function(data) {
						ModalUtil.closeModal($globalModal);
						updateRow(data);			
					});
		}
		else {
			$error.find('.errorMsg').text(errorMsg);
			$error.show();
		}
	});
	
	$editVendorModal.on('keypress', function(e) {
		if (e.which == 13) {
			$editVendorModal.find('.save').trigger('click');
		}
	});
	
	$editVendorModal.find('form').submit(function() {
		return false;
	});
	
	$editVendorModal.on('click', '.cancel', function() {
		ModalUtil.closeModal($globalModal);
	});
});

function updateRow(data){
	var tableRow = $("input[name=vendorId]").filter(function() {
	    return $(this).val() == data.vendorId;
	}).closest("tr");
	var rowHtml = '<td class="actions">'
						+ '<input class="update-checkbox" type="checkbox" />'
						+ '<div class="dropdown">'
						+ '<a class="bootStrapDropDown dropdown-toggle" data-toggle="dropdown">Actions<span class="caret"></span></a>'
						+ '<ul class="dropdown-menu">'
						+ '<li><a class="view-vendor">Vendor Information</a></li>'
						+ '<li><a class="edit-vendor">Modify Vendor</a></li>'
						+ '<li><a class="purchasing-details">Purchasing Details</a></li>'
						+ '</ul>'
						+ '</div>'
						+ '<input type="hidden" name="vendorId"';
	if(data.vendorId){
		rowHtml = rowHtml + ' value= "' + data.vendorId + '" />';
	}
	else{
		rowHtml = rowHtml + '/>';
	}
	rowHtml = rowHtml + '<input type="hidden" name="notificationException"';
	if(data.notificationException){
		rowHtml = rowHtml + ' value="'+ data.notificationException + '" />';
	}
	else{
		rowHtml = rowHtml + '/>';
	}
	rowHtml = rowHtml + '<input type="hidden" name="supplySpecialist"';
	if(data.supplySpecialist && data.supplySpecialist.userId){
		rowHtml = rowHtml + ' value="' + data.supplySpecialist.userId + '" />';
	}
	else{
		rowHtml = rowHtml + '/>';
	}
	rowHtml = rowHtml 
					+'</td>'
					+'<td class="corp-code">';
	if(data.corpCode){
		rowHtml = rowHtml + data.corpCode;
	}
	rowHtml = rowHtml + '</td>'
					+'<td class="vendor-name">';
	if(data.vendorName){
		rowHtml = rowHtml + data.vendorName;
	}
	rowHtml = rowHtml + '</td>'
					+'<td class="vendor-number">';
	if(data.vendorNumber){
		rowHtml = rowHtml + data.vendorNumber;
	}
	rowHtml = rowHtml + '</td>'
					+'<td class="mfr-code">';
	if(data.manufacturerCode){
		rowHtml = rowHtml + data.manufacturerCode;
	}
	rowHtml = rowHtml + '</td>'
					+'<td class="annual-agreement">';
	if(data.annualAgreement){
		if(data.annualAgreement == 'Y')
			rowHtml = rowHtml + 'Yes';
		else
			rowHtml = rowHtml + 'No';
	}
	rowHtml = rowHtml					
					+'</td>'
					+'<td class="primary-contact">';
	if(data.primaryContact && data.primaryContact.firstName){
		rowHtml = rowHtml + data.primaryContact.firstName;
	}
	if(data.primaryContact && data.primaryContact.lastName){
		rowHtml = rowHtml + ' ' +data.primaryContact.lastName;
	}
	rowHtml = rowHtml + '</td>'
					+'<td class="contact-phone">';
	if(data.primaryContact && data.primaryContact.phoneNumber){
		rowHtml = rowHtml + data.primaryContact.phoneNumber;
	}				
	rowHtml = rowHtml + '</td>'
					+'<td class="planning-analyst">'
						+'<input name="planningAnalyst" type="hidden"';
	if(data.planningAnalyst && data.planningAnalyst.userId){
		rowHtml = rowHtml + ' value="' + data.planningAnalyst.userId + '"';
	}				
	rowHtml = rowHtml + ' />'
						+'<span>';
	if(data.planningAnalyst && data.planningAnalyst.firstName){
		rowHtml = rowHtml + data.planningAnalyst.firstName;
	}
	if(data.planningAnalyst && data.planningAnalyst.lastName){
		rowHtml = rowHtml + ' ' +data.planningAnalyst.lastName;
	}
	rowHtml = rowHtml + '</span>'
					+'</td>';
	$(tableRow).html(rowHtml);	
}

function validateEditForm($form) {
	var $notificationException = $form.find('[name="notificationException"]');
	var $annualAgreement = $form.find('[name="annualAgreement"]');
	var $planningAnalyst = $form.find('[name="planningAnalyst.userId"]');
	//var $supplySpecialist = $form.find('[name="supplySpecialist.userId"]');
	
	var errorMsg = '';
	var primaryExists = false;
	var secondaryExists = false;
	
	// Check for required fields.
	if ($notificationException.val().length == 0) {
		$notificationException.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$notificationException.removeClass('errorMsgInput');
	}
	
	if ($annualAgreement.val().length == 0) {
		$annualAgreement.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$annualAgreement.removeClass('errorMsgInput');
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
	if (errorMsg.length > 0) {
		return errorMsg;
	}
	
	// Check primary contact information.
	var $pFirstName = $form.find('[name="primaryContact.firstName"]');
	var $pLastName = $form.find('[name="primaryContact.lastName"]');
	var $pPhoneNumber = $form.find('[name="primaryContact.phoneNumber"]');
	var $pEmail = $form.find('[name="primaryContact.email"]');
	var $pResponsibility = $form.find('[name="primaryContact.responsibility"]');
	
	if ($pFirstName.val().length > 0 || $pLastName.val().length > 0 || $pPhoneNumber.val().length > 0 || $pEmail.val().length > 0 || $pResponsibility.val().length > 0) {
		primaryExists = true;
		
		if ($pFirstName.val().length == 0) {
			$pFirstName.addClass('errorMsgInput');
			
			errorMsg = 'Required Primary Contact information is missing.';
		}
		else {
			$pFirstName.removeClass('errorMsgInput');
		}
		
		if ($pLastName.val().length == 0) {
			$pLastName.addClass('errorMsgInput');

			errorMsg = 'Required Primary Contact information is missing.';
		}
		else {
			$pLastName.removeClass('errorMsgInput');
		}
		
		if ($pEmail.val().length == 0) {
			$pEmail.addClass('errorMsgInput');

			errorMsg = 'Required Primary Contact information is missing.';
		}
		else {
			$pEmail.removeClass('errorMsgInput');
		}
		
		if ($pResponsibility.val().length == 0) {
			$pResponsibility.addClass('errorMsgInput');

			errorMsg = 'Required Primary Contact information is missing.';
		}
		else {
			$pResponsibility.removeClass('errorMsgInput');
		}
		
		if (errorMsg.length > 0) {
			return errorMsg;
		}
	}
	else {
		$pFirstName.removeClass('errorMsgInput');
		$pLastName.removeClass('errorMsgInput');
		$pPhoneNumber.removeClass('errorMsgInput');
		$pEmail.removeClass('errorMsgInput');
		$pResponsibility.removeClass('errorMsgInput');
	}
	
	// Check secondary contact information.
	var $sFirstName = $form.find('[name="secondaryContact.firstName"]');
	var $sLastName = $form.find('[name="secondaryContact.lastName"]');
	var $sPhoneNumber = $form.find('[name="secondaryContact.phoneNumber"]');
	var $sEmail = $form.find('[name="secondaryContact.email"]');
	var $sResponsibility = $form.find('[name="secondaryContact.responsibility"]');
	
	if ($sFirstName.val().length > 0 || $sLastName.val().length > 0 || $sPhoneNumber.val().length > 0 || $sEmail.val().length > 0 || $sResponsibility.val().length > 0) {
		secondaryExists = true;
		
		if ($sFirstName.val().length == 0) {
			$sFirstName.addClass('errorMsgInput');
			
			errorMsg = 'Required Secondary Contact information is missing.';
		}
		else {
			$sFirstName.removeClass('errorMsgInput');
		}
		
		if ($sLastName.val().length == 0) {
			$sLastName.addClass('errorMsgInput');

			errorMsg = 'Required Secondary Contact information is missing.';
		}
		else {
			$sLastName.removeClass('errorMsgInput');
		}
		
		if ($sEmail.val().length == 0) {
			$sEmail.addClass('errorMsgInput');

			errorMsg = 'Required Secondary Contact information is missing.';
		}
		else {
			$sEmail.removeClass('errorMsgInput');
		}
		
		if ($sResponsibility.val().length == 0) {
			$sResponsibility.addClass('errorMsgInput');

			errorMsg = 'Required Secondary Contact information is missing.';
		}
		else {
			$sResponsibility.removeClass('errorMsgInput');
		}
		
		if (errorMsg.length > 0) {
			return errorMsg;
		}
	}
	else {
		$sFirstName.removeClass('errorMsgInput');
		$sLastName.removeClass('errorMsgInput');
		$sPhoneNumber.removeClass('errorMsgInput');
		$sEmail.removeClass('errorMsgInput');
		$sResponsibility.removeClass('errorMsgInput');
	}
	
	// Contact Responsibilities
	if (primaryExists && !secondaryExists && $pResponsibility.val() != "3") {
		$pResponsibility.addClass('errorMsgInput');
		errorMsg = 'Primary Contact Responsibility is incorrect.';
	}
	else if (!primaryExists && secondaryExists && $sResponsibility.val() != "3") {
		$sResponsibility.addClass('errorMsgInput');
		errorMsg = 'Secondary Contact Responsibility is incorrect.';
	}
	else if (primaryExists && secondaryExists) {
		if ($pResponsibility.val() != "3" && ($sResponsibility.val() == $pResponsibility.val() || $sResponsibility.val() == "3")) {
			$pResponsibility.addClass('errorMsgInput');
			$sResponsibility.addClass('errorMsgInput');
			errorMsg = 'Contact Responsiblities are incorrect.';
		}
		else if ($sResponsibility.val() != "3" && ($pResponsibility.val() == $sResponsibility.val() || $pResponsibility.val() == "3")) {
			$pResponsibility.addClass('errorMsgInput');
			$sResponsibility.addClass('errorMsgInput');
			errorMsg = 'Contact Responsiblities are incorrect.';
		}
		else if ($pResponsibility.val() == "3" && $sResponsibility.val() != "3" || $sResponsibility.val() == "3" && $pResponsibility.val() != "3") {
			$pResponsibility.addClass('errorMsgInput');
			$sResponsibility.addClass('errorMsgInput');
			errorMsg = 'Contact Responsiblities are incorrect.';
		}
		else {
			$pResponsibility.removeClass('errorMsgInput');
			$sResponsibility.removeClass('errorMsgInput');
		}
	}
	else {
		$pResponsibility.removeClass('errorMsgInput');
		$sResponsibility.removeClass('errorMsgInput');
	}
	
	return errorMsg;
}

//# sourceURL=edit-vendor-modal.js