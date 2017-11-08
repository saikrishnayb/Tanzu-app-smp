$(document).ready(function() {
	$editSubjectModal.unbind();
	
	$editSubjectModal.on('click', '.save', function() {
		var $form = $('#edit-subject-form');
		var $error = $editSubjectModal.find('.error');
		var errorMsg = '';
		
		// Validate the form.
		errorMsg = validateEditForm($form);
		displayFlag=false;
		// If no error message was returned, hide any errors and submit the form data.
		if (errorMsg.length == 0) {
			$error.hide();
			
			$.post('./modify-subject.htm', 
					$form.serialize(), 
					function(data) {
						location.assign('./subject-management.htm');
					});
		}
		// If an error message was sent back, then display the error message and don't submit the form.
		else {
			$error.find('.errorMsg').text(errorMsg);
			$error.show();
		}
	});
	
	$editSubjectModal.on('keypress', function(e) {
		if (e.which == 13) {
			$editSubjectModal.find('.save').trigger('click');
			event.preventDefault();
		}
	});
	
	$addSubjectModal.find('form').submit(function() {
		return false;
	});
	
	/* ------------- Form Controls ------------- */
	$editSubjectModal.find('input').on('blur', function() {
		var $element = $(this);
		var value = $element.val();
		
		if (value.length > 0) {
			$element.removeClass('errorMsgInput');
		}
	});
	
	$editSubjectModal.find('select').on('change', function() {
		var $element = $(this);
		var value = $element.val();
		
		if (value != -1) {
			$element.removeClass('errorMsgInput');
		}
	});
	
	/* ------------ Modal Controls ------------ */
	$editSubjectModal.on('click', '.cancel-modal', function() {
		$editSubjectModal.dialog('close');
	});
});

/**
 * This function validates the form to make sure that the data is valid. If invalid data is found, then
 * an error message is sent back to be displayed to the user.
 * 
 * @param $form	- the form being validated
 * @returns		- the error message
 */
function validateEditForm($form) {
	var $subjectName = $form.find('[name="subjectName"]');
	var $department = $form.find('[name="department"]');
	var $type = $form.find('[name="type"]');
	
	var errorMsg = '';
	
	if ($subjectName.val().length == 0) {
		$subjectName.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$subjectName.removeClass('errorMsgInput');
	}
	
	if ($department.val() == -1) {
		$department.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$department.removeClass('errorMsgInput');
	}
	
	if ($type.val() == -1) {
		$type.addClass('errorMsgInput');
		
		errorMsg = 'A required field is missing.';
	}
	else {
		$type.removeClass('errorMsgInput');
	}
	
	return errorMsg;
}

//# sourceURL=edit-subject-modal.js