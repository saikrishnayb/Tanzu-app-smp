$(document).ready(function() {
	$addRuleModal.unbind();
	
	$addRuleModal.on('click', '.save', function() {
		var $form = $addRuleModal.find('#add-rule-form');
		var $error = $addRuleModal.find('.error');
		
		var errorMsg = '';
		
		// Validate the form.
		errorMsg = validateAddForm($form);
		
		// If no error message was returned, hide any errors and submit the form data.
		if (errorMsg.length == 0) {
			$error.hide();
			
		/*	$.post('./add-dynamic-rule', 
					$form.serialize(), 
					function(data) {
						// Reload the page, because the rule's priority may have affected other rules.
						location.reload();
					});*/
			
			
			
			$.ajax({
				  type: "POST",
				  url: "./add-dynamic-rule",
				  data: $form.serialize(),
				  success: function(data){
					  location.reload();
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
	
	$addRuleModal.on('keypress', function(e) {
		if (e.which == 13) {
			$addRuleModal.find('.save').trigger('click');
		}
	});
	
	$addRuleModal.find('form').submit(function() {
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
	$(document.body).on('click', '.cancel-modal', function() {
		var $modal = $(this).closest('.modal');
		
		$modal.find('.error').hide();
		
		$modal.dialog('close');
	});
	
	// When the manufacturer is changed, get the models and populate the model dropdown.
	$addRuleModal.on('change', '[name="manufacturer"]', function() {
		var $corpCode = $addRuleModal.find('[name="corpCode"]');
		var $make = $addRuleModal.find('[name="manufacturer"]');
		var $model = $addRuleModal.find('[name="model"]');
		var $modelYear = $addRuleModal.find('[name="modelYear"]');
		var $priority = $addRuleModal.find('[name="priority"]');
		
		// Store the corpCode and make dropdowns to store later. This reduces the number of queries needed to re-populate the modal.
		var $corpDropdown = $corpCode.html();
		var $makeDropdown = $make.html();
		
		// Get form information to store later after the modal is re-populated.
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
				{'make': make, 'modalName': 'add'},
				function(data) {
					$addRuleModal.html(data);
					
					var $corpCode = $addRuleModal.find('[name="corpCode"]');
					var $make = $addRuleModal.find('[name="manufacturer"]');
					var $modelYear = $addRuleModal.find('[name="modelYear"]');
					var $priority = $addRuleModal.find('[name="priority"]');
					
					// Replace dropdowns from the original modal.
					$corpCode.html($corpDropdown);
					$make.html($makeDropdown);

					// Put the original values back into the modal.
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
function validateAddForm($form) {
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
		errorMsg = 'Priority reached max integer length';
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

//# sourceURL=add-rule-modal.js