$(document).ready(function() {
	$editHeaderModal.unbind();
	
	/* ----------- Edit Alert Header Name ----------- */
	$editHeaderModal.on('click', '#edit-header-name', function() {
		// Toggle the display of the elements used to change the header name.
		$editHeaderModal.find('.header-change').toggle();
	});
	
	$editHeaderModal.on('click', '#cancel-edit-header-name', function() {
		var origHeaderName = $editHeaderModal.find('#header-name').text();
		
		$('#header-name-change').removeClass('errorMsgInput');
		
		// Reset the text field value back to the original header name.
		$editHeaderModal.find('#header-name-change').val(origHeaderName);
		
		// Toggle the display of the elements used to change the header name.
		$editHeaderModal.find('.header-change').toggle();
	});
	
	$editHeaderModal.on('click', '#save-header-name', function() {
		var newHeaderName = $editHeaderModal.find('#header-name-change').val();
		
		if (newHeaderName.length > 0) {
			$('#header-name-change').removeClass('errorMsgInput');
			
			// Save the new header name and display it to the user.
			$editHeaderModal.find('#header-name').text(newHeaderName);
			
			// Toggle the display of the elements used to change the header name.
			$editHeaderModal.find('.header-change').toggle();
		}
		else {
			$('#header-name-change').addClass('errorMsgInput');
		}
	});
	
	/* ----------- Save and Submit Alert Form ----------- */
	$editHeaderModal.on('click', '.save', function() {
		var $form = $editHeaderModal.find('#edit-header-form');
		var $error = $editHeaderModal.find('.error');
		
		// Validate the form.
		var errorMsg = validateHeaderForm($form);

		// If no error message was returned, then hide any error messages and submit the form.
		if (errorMsg.length == 0) {
			$error.hide();
			
			$.post('./update-alert-header',
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
	
	$editHeaderModal.on('keypress', function(e) {
		if (e.which == 13) {
			$editHeaderModal.find('.save').trigger('click');
		}
	});
	
	$editHeaderModal.find('form').submit(function() {
		return false;
	});
	
	$editHeaderModal.find('input[name="displaySequence"]').on('blur', function() {
		var $displaySequence = $(this);
		var value = $displaySequence.val();
		
		if (value.length > 0 && $.isNumeric(value) && parseInt(value) >= 0) {
			$displaySequence.removeClass('errorMsgInput');
		}
	});
	
	/* ----------- Cancel Alert Change ----------- */
	$editHeaderModal.on('click', '.cancel', function() {
		// Close the modal.
		$editHeaderModal.dialog('close');
	});
});

/**
 * This method validates the form that was submitted and returns an error message if something is incorrect.
 * 
 * @param $form	- the form element being validated
 * @returns		- an error message; a blank string will be interpreted as the form being valid
 */
function validateHeaderForm($form) {
	var $headerNameChange = $form.find('#header-name-change');
	var $displaySequence = $form.find('[name="displaySequence"]');
	
	// If the text field to change the alert name is still visible (meaning that the header name hasn't been saved yet).
	if ($headerNameChange.is(':visible')) {
		return 'Save the Header Name.';
	}
	
	// The display sequence cannot be blank.
	if ($displaySequence.val().length == 0) {
		$displaySequence.addClass('errorMsgInput');
		
		return 'A required field is missing.';
	}
	else {
		$displaySequence.removeClass('errorMsgInput');
	}
	
	// The display sequence must be a number.
	if (!$.isNumeric($displaySequence.val())) {
		$displaySequence.addClass('errorMsgInput');
		
		return 'Display Sequence must be a number.';
	}
	else {
		$displaySequence.removeClass('errorMsgInput');
	}
	
	// If the form passes all checks, then do not pass an error message back.
	return '';
}

//# sourceURL=edit-alert-header-modal.js