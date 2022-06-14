$(document).ready(function() {
	var $tabNavUser=$('#tabNavUser').val();
	selectCurrentNavigation("tab-security",$tabNavUser);
	selectCurrentNavigation("tab-security", $tabNavUser);
	$('.back').on('click', function(){
		parent.history.back();
		return false;
	});
});
//# sourceURL=create-users.js