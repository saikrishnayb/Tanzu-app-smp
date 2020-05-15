$(document).ready(function() {
	selectCurrentNavigation("tab-security", "left-nav-org");
	$('.back').on('click', function(){
		parent.history.back();
		return false;
	});
	//$('.org-form-container #role-hierarchy').hide();
});
//# sourceURL=create-org.js