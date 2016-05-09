$(document).ready(function() {
	$renameRoleModal.unbind();
	
	$renameRoleModal.find('#save-role-name').on('click', function() {
		saveRoleName();
	});
	
	$renameRoleModal.keypress(function(e) {
		if (e.which == 13) {
			saveRoleName();
		}
	});
	
	$renameRoleModal.find('.cancel').on('click', function() {
		var $error = $renameRoleModal.find('.error');
		var $roleName = $renameRoleModal.find('input[name="new-role-name"]');
		
		$renameRoleModal.dialog('close');
		
		$error.hide();
		$roleName.removeClass('errorMsgInput');
	});
});

function saveRoleName() {
	var $error = $renameRoleModal.find('.error');
	var $roleName = $renameRoleModal.find('input[name="new-role-name"]');
	
	if ($roleName.val().length > 0) {
		$('span#role-name').text($roleName.val());
		$('input[name="roleName"]').val($roleName.val());
		
		$error.hide();
		$roleName.removeClass('errorMsgInput');
		
		$renameRoleModal.dialog('close');
	}
	else {
		$error.find('.errorMsg').text('Role Name is required.');
		$roleName.addClass('errorMsgInput');
		$error.show();
	}
}

//# sourceURL=rename-role-modal.js