$(document).ready(function() { 
	
	var $accordion = $('.accordion');
	
	$accordion.unbind();
			
	$accordion.accordion({
		active: 0,
		collapsible: true,
		heightStyle: "content"
	});
	
	// If the user checks the "Check to apply all tab functions" checkbox, then check all functions in that tab.
	// If the user unchecks the "Check to apply all tab functions" checkbox, then uncheck all functions in that tab.
	$accordion.find('.accordion-header input[type="checkbox"]').on('click', function(e) {
		var $checkbox = $(this);
		
		if ($checkbox.is(':checked')) {
			$checkbox.closest('.accordion').find('.ui-accordion-content [type="checkbox"]').prop('checked', true);
		}
		else {
			$checkbox.closest('.accordion').find('.ui-accordion-content [type="checkbox"]').prop('checked', false);
		}

		e.stopPropagation();
	});
	
	// If any function is unchecked, make sure that the checkbox in the accordion header is not checked.
	// Also, if all functions are checked, make sure that the checkbox in the accordion header is checked.
	$accordion.find('.function-name input[type="checkbox"]').on('click', function(e) {
		var $checkbox = $(this);
		
		if (!$checkbox.is(':checked')) {
			$checkbox.closest('.accordion').find('.accordion-header input[type="checkbox"]').prop('checked', false);
		}
		else {
			var unchecked = false;
			
			$checkbox.closest('.ui-accordion-content').find('input[type="checkbox"]').each(function() {
				if (!unchecked && !$(this).is(':checked')) {
					unchecked = true;
				}
			});
			
			if (!unchecked) {
				$checkbox.closest('.accordion').find('.accordion-header input[type="checkbox"]').prop('checked', true);
			}
		}

		e.stopPropagation();
	});
		
});

//# sourceURL=role-permissions.js