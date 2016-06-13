var $rolePermissions = $('#role-permissions');
var $roleHierarchy = $('#role-hierarchy');
var roleId = $('input[name="roleId"]').val();
var tempBaseRoleId=$('#tempBaseRoleId').val();
var $renameRoleModal = $('#rename-role-modal');
var $deactivateModal = $('#deactivate-modal');
var $editRoleForm = $('#edit-role-form');
var $editOrCopy = $('#editOrCopy').val();

$(document).ready(function() { 
	// Hierarchy call is here because it can't find the .jsp when it recurses.
	if($editOrCopy==='E'){
		$.post('./get-edit-role-hierarchy.htm',
				{ 'roleId': roleId, 'flag' : 0 },
				function(data) {
					$roleHierarchy.html(data);
				});
	}else{
		$.post('./get-edit-role-hierarchy.htm',
				{ 'roleId': tempBaseRoleId , 'flag' : 1},
				function(data) {
					$roleHierarchy.html(data);
				});
	}

	selectCurrentNavigation("tab-security", "left-nav-roles");
	$('#back').on('click', function(){
		parent.history.back();
		return false;
	});
	
	/* -------------- Modal Declarations --------------- */
	$renameRoleModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 350,
		minHeight: 100,
		resizable: false,
		title: 'Rename Role',
		closeOnEscape: false
	});
	
	$deactivateModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight: 100,
		resizable: false,
		title: 'Deactivate Role',
		closeOnEscape: false
	});
	
	/* ---------------- Initialize Page ---------------- */
	markCheckboxes();
	
	/* ---------------- Modify Controls ---------------- */
	
	// Populate the role hierarchy and permissions based on the chosen base role.
	$('[name="baseRoleId"]').on('change', function() {
		var baseRoleId = $(this).val();
		var isSameBaseRoll=false;
		$rolePermissions.empty();
		$roleHierarchy.empty();
		/*
		 1. ONE- If choosed  roleid(dropdown) = current edit role's base_id than call populate
		 2. TWO - If choosed  roleid(dropdown) = current edit role's base_id just call Create
		 */
		
		if (baseRoleId.length > 0 && baseRoleId >0) {
			if(tempBaseRoleId===baseRoleId){
				isSameBaseRoll=true;
			}
			
			if(isSameBaseRoll){
				// Get the role hierarchy tree.
				$.post('./get-edit-role-hierarchy.htm',
						{ 'roleId': roleId, 'flag' : 0 },
						function(data) {
							$roleHierarchy.html(data);
						});
				// Get the role permission accordions.
				$.post('./get-edit-role-permissions.htm',
						{'roleId': roleId},
						function(data) {
							$rolePermissions.html(data);
							markCheckboxes();
						});
			}else{
				// Get the role hierarchy tree.
				$.post('./get-edit-role-hierarchy.htm',
						{ 'roleId': baseRoleId, 'flag' : 1},
						function(data) {
							$roleHierarchy.html(data);
						});
				
				// Get the role permission accordions.
				$.post('./get-role-permissions.htm',
						{'roleId': baseRoleId},
						function(data) {
							$rolePermissions.html(data);
							markCheckboxes();
						});
			}
		}
	});
	
	
	$('#rename-role').on('click', function() {
		$renameRoleModal.find('input[name="new-role-name"]').val($('span#role-name').text());
		
		$renameRoleModal.dialog('open');
	});
	
	$('a#deactivate-role').on('click', function() {
		$.get('./deactivate-role.htm',
				{'roleId': roleId},
				function(data) {
					$deactivateModal.html(data);
					$deactivateModal.dialog('open');
				});
	});
	
	/* ---------------- Saving Changes ---------------- */
	$('#save-role').on('click', function() {
		var $error = $('#modify-role-buttons').find('.error');
		var $editOrCopy = $('#editOrCopy').val();
		var serialized = $editRoleForm.serialize();
		var functionIds = [];
		var errorMsg = '';
		// Get each of the permissions checked by the user.
		$('.accordion').find('.function-check').each(function() {
			if ($(this).is(':checked')) {
				var functionId = $(this).closest('.function-name').find('.function-id').val();
				functionIds.push(functionId);
			}
		});
		
		errorMsg = validateForm();
		// Something is incorrect on the form.
		if (errorMsg.length > 0) {
			$error.find('.errorMsg').text(errorMsg);
			$error.show();
		}
		// No permissions were chosen.
		else if (functionIds.length == 0) {
			$error.find('.errorMsg').text('No permissions are selected for this role.');
			$error.show();
		}
		// Role is valid, submit information.
		else {
		// If there are no permissions selected, then don't save the changes.
	//	if (functionIds.length > 0) {
			$error.hide();
			
				serialized = serialized + '&functionIds=' + functionIds.join();
			if($editOrCopy==='C'){
				/*$.post('./insert-role.htm',
					    serialized,
					    function(data) {
							location.assign('./roles.htm');
						});
				*/
				
				$.ajax({
					  type: "POST",
					  url: "./insert-role.htm",
					  data: serialized,
					  processData : false,
					  success: function(data){
						location.assign('./roles.htm');
					 },
					  error: function(XMLHttpRequest, textStatus, errorThrown) {
						  if(XMLHttpRequest.responseText.indexOf('An active role already exists with the role name')>0){
							  var roleName=$("#roleName").val();
							  var other=$("#baseRoleId option:selected").text();
							  $error.find('.errorMsg').text('An active role already exists with the role name::'+roleName+'.');
							 // $('.error-messages-container').removeClass('displayNone');
							  $error.show();
						  }
						
					  }
					});
			}else{
				/*	$.post('./modify-role-submit.htm',
						serialized,
						function(data) {
							location.assign('./roles.htm');
						});
				*/
				$.ajax({
					  type: "POST",
					  url: "./modify-role-submit.htm",
					  data: serialized,
					  processData : false,
					  success: function(data){
						location.assign('./roles.htm');
					 },
					  error: function(XMLHttpRequest, textStatus, errorThrown) {
						  if(XMLHttpRequest.responseText.indexOf('An active role already exists with the role name')>0){
							  var roleName=$("#roleName").val();
							  var other=$("#baseRoleId option:selected").text();
							  $error.find('.errorMsg').text('An active role already exists with the role name::'+roleName+'.');
							 // $('.error-messages-container').removeClass('displayNone');
							  $error.show();
						  }
						
					  }
					});
			}
		}
		/*else {
			$error.find('.errorMsg').text('No permissions are selected for this role.');
			$error.show();
		}*/
	});
});

function markCheckboxes() {
	// If all functions under a tab are automatically checked, then check the 'Check to apply all tab functions.' checkbox.
	$rolePermissions.find('.tab').each(function() {
		var $tab = $(this);
		var allChecked = true;
		
		$tab.find('.function-check').each(function() {
			if (!$(this).is(':checked')) {
				allChecked = false;
				return;
			}
		});
		
		if (allChecked) {
			$tab.find('.header-span input[type="checkbox"]').attr('checked', 'checked');
		}
	});
}



function validateForm() {
	var $roleName = $editRoleForm.find('input[name="roleName"]');
	var $baseRoleId = $editRoleForm.find('select[name="baseRoleId"]');
	var $roleDescription =$('#roleDescription');
	
	var errorMsg = '';
	
	if ($roleName.val().trim().length == 0) {
		$roleName.addClass('errorMsgInput');
		errorMsg = 'A required field is missing.';
	}
	else {
		$roleName.removeClass('errorMsgInput');
	}
	
	if ($baseRoleId.val().length == 0 || $baseRoleId.val()==0) {
		$baseRoleId.addClass('errorMsgInput');
		errorMsg = 'A required field is missing.';
	}
	else {
		$baseRoleId.removeClass('errorMsgInput');
	}
	
	if ($roleDescription.val().trim().length == 0) {
		$roleDescription.addClass('errorMsgInput');
		errorMsg = 'A required field is missing.';
	}
	else {
		$roleDescription.removeClass('errorMsgInput');
	}
	
	return errorMsg;
}