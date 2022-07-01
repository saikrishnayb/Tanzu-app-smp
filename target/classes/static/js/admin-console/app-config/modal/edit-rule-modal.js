$(document).ready(function() {
	$editRuleModal.unbind();
	
	$editRuleModal.on('click', '.save', function() {
		var $form = $editRuleModal.find('#edit-rule-form');
		var $error = $editRuleModal.find('.error');
		var errorMsg = '';
		
		// Validate the form.
		errorMsg = validateEditForm($form);
		
		// If no error message was returned, hide any errors and submit the form data.
		if (errorMsg.length == 0) {
			$error.hide();
			
			/*$.post('./modify-dynamic-rule', 
					$form.serialize(), 
					function(data) {
						// Reload the page, because the rule's priority may have affected other rules.
						location.assign('./dynamic-rules');
					});*/
			
			$.ajax({
				  type: "POST",
				  url: "./modify-dynamic-rule",
				  data: $form.serialize(),
				  success: function(data){
					  location.assign('./dynamic-rules');
				  },
				  error: function(XMLHttpRequest, textStatus, errorThrown) {
					  if(XMLHttpRequest.responseText.indexOf('CORP/MANUFACTURER/MODEL/MODEL_YEAR Already exists for a different Dynamic Rule withactive status')>0){
						  $error.find('.errorMsg').text('CORP/MANUFACTURER/MODEL/MODEL_YEAR Already exists for a different Dynamic Rule with active status');
						  $error.show();
						  return view();
					  }
					  else if(XMLHttpRequest.responseText.indexOf('CORP/MANUFACTURER/MODEL/MODEL_YEAR Already exists for a different Dynamic Rule withinactive status')>0){
						  $error.find('.errorMsg').text('CORP/MANUFACTURER/MODEL/MODEL_YEAR Already exists for a different Dynamic Rule with inactive status');
						  $error.show();
						  return view();
					  }
					
				  }
				});
		}
		// If an error was found, display it to the user and do not submit the form data.
		else {
			$error.find('.errorMsg').text(errorMsg);
			$error.show();
		}
	});
	
	$editRuleModal.on('keypress', function(e) {
		if (e.which == 13) {
			$editRuleModal.find('.save').trigger('click');
		}
	});
	
	$editRuleModal.find('form').submit(function() {
		return false;
	});
	
	/* ------------- Form Controls ------------- */
	$addRuleModal.find('input').on('blur', function() {
		var $element = $(this);
		var value = $element.val();
		
		if (value.length > 0 && $.isNumeric(value) && parseInt(value) >= 0) {
			$element.removeClass('errorMsgInput');
		}
	});
	
	$addRuleModal.find('select').on('change', function() {
		var $element = $(this);
		var value = $element.val();
		
		if (value.length > 0) {
			$element.removeClass('errorMsgInput');
		}
	});
	
	/* ------------- Modal Controls ------------- */
	$editRuleModal.on('click', '.cancel-modal', function() {
		$editRuleModal.find('.error').hide();
		
		$editRuleModal.dialog('close');
	});
	
	// When the manufacturer is changed, get the models and populate the model dropdown.
	$editRuleModal.on('change', '[name="manufacturer"]', function() {
		var $corpCode = $editRuleModal.find('[name="corpCode"]');
		var $make = $editRuleModal.find('[name="manufacturer"]');
		var $model = $editRuleModal.find('[name="model"]');
		var $modelYear = $editRuleModal.find('[name="modelYear"]');
		var $priority = $editRuleModal.find('[name="priority"]');
		
		// Store the corpCode and make dropdowns to store later. This reduces the number of queries needed to re-populate the modal.
		var $corpDropdown = $corpCode.html();
		var $makeDropdown = $make.html();
		
		// Get form information to store later after the modal is re-populated.
		var dynamicRuleId = $('#editDynamicRuleId').val(); //$('[name="dynamicRuleId"]').val();
		var corpCode = $corpCode.val();
		var modelYear = $modelYear.val();
		var priority = $priority.val();
		var make = $make.val();
		
		var corpError = $corpCode.hasClass('errorMsgInput');
		var makeError = $make.hasClass('errorMsgInput');
		var modelYearError = $modelYear.hasClass('errorMsgInput');
		var priorityError = $priority.hasClass('errorMsgInput');
		
		var error = '';
		
		// If the model field is the only one with an error, then don't pass the error message along,
		// since the model field is going to be reset.
		if ($('.errorMsgInput').length == 1 && $model.hasClass('errorMsgInput')) {
			$('.error .errorMsg').text();
		}
		
		// Remove all of the options in the model dropdown except the first (the default "Select..." option).
		$model.find('option:not(:first)').remove();
		
		// Get the list of models from the database.
		$.post('./get-models-by-make',
				{'make': make, 'modalName': 'edit'},
				function(data) {
					$editRuleModal.html(data);
					
					var $corpCode = $editRuleModal.find('[name="corpCode"]');
					var $make = $editRuleModal.find('[name="manufacturer"]');
					var $modelYear = $editRuleModal.find('[name="modelYear"]');
					var $priority = $editRuleModal.find('[name="priority"]');
					var showErrorMsg = true;
					// Replace dropdowns from the original modal.
					$corpCode.html($corpDropdown);
					$make.html($makeDropdown);

					// Put the original values back into the modal.
					$editRuleModal.find('[name="dynamicRuleId"]').val(dynamicRuleId);
					$corpCode.val(corpCode);
					$make.val(make);
					$modelYear.val(modelYear);
					$priority.val(priority);
					
					// Reset any error fields.
					if (corpError) {
						$corpCode.addClass('errorMsgInput');
					}
					
					if (makeError) {
						$make.addClass('errorMsgInput');
					}
					
					if (modelYearError) {
						$modelYear.addClass('errorMsgInput');
					}
					
					if (priorityError) {
						$priority.addClass('errorMsgInput');
					}
					
					if (error.length > 0) {
						$('.error .errorMsg').text(error);
						$('.error').show();
					}
				});
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
	var $corpCode = $form.find('[name="corpCode"]');
	var $make = $form.find('[name="manufacturer"]');
	var $model = $form.find('[name="model"]');
	var $modelYear = $form.find('[name="modelYear"]');
	var $priority = $form.find('[name="priority"]');
	
	var errorMsg = '';
	var showErrorMsg = true;
	// Check that required fields are filled.
if ($corpCode.val().length > 0) {
		
		showErrorMsg = false;
	}
	
	if ($make.val().length > 0) {
		if(showErrorMsg)
		showErrorMsg = false;
	}
	
	if ($model.val().length > 0) {
		
		if(showErrorMsg)
		showErrorMsg = false;
	
	}
	
	if ($modelYear.val().length > 0) {
		
		
		if(showErrorMsg)
		  showErrorMsg = false;
	
	}else if($modelYear.val().length>0 &&  $modelYear.val()>2147483647){
		$priority.addClass('errorMsgInput');
		errorMsg = 'Model Year reached max integer length';
	}
	if(showErrorMsg){
		errorMsg = 'Any one of non required field to be selected.';
	}
	if ($priority.val().length == 0) {
		$priority.addClass('errorMsgInput');
		errorMsg = 'A required field is missing.';
	}else if($priority.val() == 0){
		$priority.addClass('errorMsgInput');
		errorMsg = 'Priority should be greaterthan 0';
	}else if($priority.val().length>0 &&  $priority.val()>2147483647){
		$priority.addClass('errorMsgInput');
		errorMsg = 'Model Priority reached max integer length';
	}
	else {
		$priority.removeClass('errorMsgInput');
	}
	if (errorMsg.length > 0) {
		return errorMsg;
	}
	
	// Check that values are valid.
	if ($model.val().length > 0 && !$.isNumeric($modelYear.val())) {
		$modelYear.addClass('errorMsgInput');
		
		return 'Model Year must be a number.';
	}
	else {
		$modelYear.removeClass('errorMsgInput');
	}
	
	
	if ($priority.val().length > 0 && !$.isNumeric($priority.val())) {
		$priority.addClass('errorMsgInput');
		return 'Priority must be a number.';
	}
	else {
		$priority.removeClass('errorMsgInput');
	}
	
	
	return errorMsg;
}

//# sourceURL=edit-rule-modal.js