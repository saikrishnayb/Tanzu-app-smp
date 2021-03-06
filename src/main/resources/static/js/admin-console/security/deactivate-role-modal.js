$(document).ready(function() {
	var $deactivateModal = $('#deactivate-modal');
	
	$deactivateModal.unbind();
	
	$deactivateModal.on('click', '.confirm', function() {
		var roleId = $deactivateModal.find('input[name="role-id"]').val();
		
		$.post('./deactivate-role-confirm',
				{'roleId': roleId},
				function(data) {
					location.assign('./roles');
				});
	});
	
	$deactivateModal.on('click', '.cancel', function() {
		$('.modal').dialog('close');
	});
});

//# sourceURL=deactivate-role-modal.js