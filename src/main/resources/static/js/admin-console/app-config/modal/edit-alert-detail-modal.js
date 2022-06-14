$(document).ready(function() {
	$editDetailModal.unbind();
	
	/* ----------- Edit Alert Name ----------- */
	$editDetailModal.on('click', '#edit-alert-name', function() {
		// Toggle the display of the elements used to change the alert name.
		$editDetailModal.find('.alert-change').toggle();
	});
	
	$editDetailModal.on('click', '#cancel-edit-alert-name', function() {
		var origAlertName = $editDetailModal.find('#alert-name').text();
		
		$('#alert-name-change').removeClass('errorMsgInput');
		
		// Reset the text field value back to the original alert name.
		$editDetailModal.find('#alert-name-change').val(origAlertName);
		
		// Toggle the display of the elements used to change the alert name.
		$editDetailModal.find('.alert-change').toggle();
	});
	
	$editDetailModal.on('click', '#save-alert-name', function() {
		var newAlertName = $editDetailModal.find('#alert-name-change').val();
		
		if (newAlertName.length > 0) {
			$('#alert-name-change').removeClass('errorMsgInput');
			
			// Save the new alert name and display it to the user.
			$editDetailModal.find('#alert-name').text(newAlertName);
			
			// Toggle the display of the elements used to change the alert name.
			$editDetailModal.find('.alert-change').toggle();
		}
		else {
			$('#alert-name-change').addClass('errorMsgInput');
		}
	});
	
	/* ----------- Edit Template Name ----------- */
	$editDetailModal.on('click', '#edit-template-name', function() {
		// Toggle the display of the elements used to change the template.
		$editDetailModal.find('.template-change').toggle();
	});

	$editDetailModal.on('click', '#cancel-edit-template-name', function() {
		var $templateDropdown = $editDetailModal.find('#template-name-change');
		var origTemplateName = $editDetailModal.find('#template-name').text();
		
		$('#template-name-change').removeClass('errorMsgInput');
		
		// Iterate through the list of templates to select the original template.
		$templateDropdown.find('option').each(function() {
			if (origTemplateName == $(this).text()) {
				$(this).prop('selected', true);
			}
		});
		
		// Toggle the display of the elements used to change the template.
		$editDetailModal.find('.template-change').toggle();
	});
	
	$editDetailModal.on('click', '#save-template-name', function() {
		var newTemplateName = $editDetailModal.find('#template-name-change option:selected').text();
		var newTemplateId = $editDetailModal.find('#template-name-change').val();
		var alertId = $editDetailModal.find('[name="alertId"]').val();
		var validTemplateId = true;
		var $error = $editDetailModal.find('.error');
		
		var rows = $alertTable.dataTable().fnGetNodes();
		
		for (var z = 0; z < rows.length; z++) {
			var $row = $(rows[z]);
			
			// This only applies to Alert Details
			if ($row.find('.alert-type').text() == 'DETAIL' && validTemplateId) {
				var rowAlertId = $row.find('[name="alertId"]').val();
				var rowTemplateId = $row.find('[name="templateId"]').val();
				
				// If the template Id is already associated with another alert.
				if (rowTemplateId == newTemplateId && rowAlertId != alertId) {
					validTemplateId = false;
				}
			}
		}
		
		if (!validTemplateId) {
			$('#template-name-change').addClass('errorMsgInput');
			$error.find('.errorMsg').text("Please select a non-associated template.");
			$error.show();
		}
		else if (newTemplateName.length == 0) {
			$('#template-name-change').addClass('errorMsgInput');
		}
		else {
			$('#template-name-change').removeClass('errorMsgInput');
			
			$error.hide();
			
			// Save the new template name and display it to the user.
			$editDetailModal.find('#template-name').text(newTemplateName);
			
			// Toggle the display of the elements used to change the template.
			$editDetailModal.find('.template-change').toggle();
		}
	});
	
	$editHeaderModal.find('input[name="displaySequence"]').on('blur', function() {
		var $displaySequence = $(this);
		var value = $displaySequence.val();
		
		if (value.length > 0 && $.isNumeric(value) && parseInt(value) >= 0) {
			$displaySequence.removeClass('errorMsgInput');
		}
	});
	
	/* ----------- Save and Submit Alert Form ----------- */
	$editDetailModal.on('click', '.save', function() {
		var $form = $editDetailModal.find('#edit-detail-form');
		var $error = $editDetailModal.find('.error');
		
		// Validate the form.
		var errorMsg = validateDetailForm($form);

		// If no error message was returned, then hide any error messages and submit the form.
		if (errorMsg.length == 0) {
			$error.hide();
			
				$.post('./update-alert-detail',
					$form.serialize(),
					function(data) {
					location.reload();
				});
		}
		// If an error message was sent back, then display the error message and don't submit the form.
		else {
			$error.find('.errorMsg').text(errorMsg);
			$error.show();
		}
	});
	
	$editDetailModal.on('keypress', function(e) {
		if (e.which == 13) {
			$editDetailModal.find('.save').trigger('click');
		}
	});
	
	$editDetailModal.find('form').submit(function() {
		return false;
	});
	
	$editDetailModal.find('input[name="displaySequence"]').on('blur', function() {
		var $displaySequence = $(this);
		var value = $displaySequence.val();
		
		if (value.length > 0 && $.isNumeric(value) && parseInt(value) >= 0) {
			$displaySequence.removeClass('errorMsgInput');
		}
	});
	
	$editDetailModal.find('select[name="visibility"]').on('change', function() {
		var $visibility = $(this);
		var value = $visibility.val();
		
		if (value.length > 0) {
			$visibility.removeClass('errorMsgInput');
		}
	});
	
	/* ----------- Cancel Alert Change ----------- */
	$editDetailModal.on('click', '.cancel', function() {
		// Close the modal.
		$editDetailModal.dialog('close');
	});
});

function validateDetailForm($form) {
	var $alertNameChange = $form.find('[name="alertName"]');
	var $templateNameChange = $form.find('[name="templateId"]');
	var $displaySequence = $form.find('[name="displaySequence"]');
	var $visibility = $form.find('[name="visibility"]');
	
	var errorMsg = '';
	
	// If the text field to change the alert name is still visible (meaning that the header name hasn't been saved yet).
	if ($alertNameChange.is(':visible')) {
		return 'Save the Alert Name.';
	}
	// If the dropdown to change the template is still visible (meaning that the header name hasn't been saved yet).
	else if ($templateNameChange.is(':visible')) {
		return 'Save the Associated Search Template.';
	}
	
	// The display sequence cannot be blank.
	if ($displaySequence.val().length == 0) {
		$displaySequence.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$displaySequence.removeClass('errorMsgInput');
	}
	
	// A visibility must be chosen.
	if ($visibility.val().length == 0) {
		$visibility.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$visibility.removeClass('errorMsgInput');
	}
	
	if (errorMsg.length > 0) {
		return errorMsg;
	}
	
	// The display sequence must be a number.
	if (!$.isNumeric($displaySequence.val())) {
		$displaySequence.addClass('errorMsgInput');
		
		errorMsg = 'Display Sequence must be a number.';
	}
	else {
		$displaySequence.removeClass('errorMsgInput');
	}
	
	// If the form passes all checks, then do not pass an error message back.
	return errorMsg;
}

//# sourceURL=edit-alert-detail-modal.js