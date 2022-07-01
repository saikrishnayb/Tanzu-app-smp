$(document).ready(function() { 
	var $permissionsAccordion = $('.permission-tab-accordions');

	$permissionsAccordion.accordion({
		active: 0,
		clearStyle: true, 
		autoHeight: false,
		collapsible: true            
	});
});
//# sourceURL=permissions-accordion.js