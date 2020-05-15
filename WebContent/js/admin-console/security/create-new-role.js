var $rolePermissions = $('#role-permissions');
var $roleHierarchy = $('#role-hierarchy');
var $newRoleForm = $('#new-role-form');

$(document).ready(function() { 

	/* ------------- Initialize Page ------------- */ 
	selectCurrentNavigation("tab-security", "left-nav-roles");
	$('#back').on('click', function(){
		parent.history.back();
		return false;
	});
	
	sortBaseRoles($('select[name="baseRoleId"]'));
	$newRoleForm.find('[name="roleName"]').focus();
	$("#vendor").multiselect({minWidth : 200});
	$(".ui-multiselect-checkboxes label").removeAttr('font-weight').css("font-weight","normal"); /* Added this line of code to make the Multi-select box values in normal font instead of bold (Default)*/
	$(".ui-multiselect").css("float","left"); 
	$(".ui-multiselect").removeAttr('width').css("width",'200px'); 
	$(".ui-multiselect-menu").css("width",'288px'); 
	/* -------------- Form Controls -------------- */
	// Populate the role hierarchy and permissions based on the chosen base role.
	$('[name="baseRoleId"]').on('change', function() {
		var baseRoleId = $(this).val();
		
		$rolePermissions.empty();
		$roleHierarchy.empty();
		
		if (baseRoleId.length > 0 && baseRoleId >0) {
			// Get the role hierarchy tree.
			$.post('./get-create-role-hierarchy.htm',
					{'roleId': baseRoleId},
					function(data) {
						$roleHierarchy.html(data);
					});
			
			// Get the role permission accordions.
			$.post('./get-role-permissions.htm',
					{'roleId': baseRoleId},
					function(data) {
						$rolePermissions.html(data);
					});
		}
	});
	
	// Save the new role.
	$('.save').on('click', function() {
		var $error = $('#new-role-buttons').find('.error');
		
		var serialized = $newRoleForm.serialize();
		var errorMsg = '';
		var functionIds = [];
		
		// For each permission that is checked, add it to the permissions to be given to the role.
		$('.accordion').find('.function-check').each(function() {
			if ($(this).is(':checked')) {
				var functionId = $(this).closest('.function-name').find('.function-id').val();
				functionIds.push(functionId);
			}
		});
		
		serialized = serialized + '&functionIds=' + functionIds.join();
		
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
			$error.hide();
			
			// Add the role.
			/*$.post('./insert-role.htm',
				    serialized,
				    function(data) {
						location.assign('./roles.htm');
					});*/
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
		}
	});
	
	// Allow submission with the enter key.
	$newRoleForm.on('keypress', function(e) {
		if (e.which == 13) {
			$('.save').trigger('click');
		}
	});
	
	// Prevent the actual form from submitting when enter is pressed (since the submission is an Ajax call, not a form submit).
	$newRoleForm.submit(function() {
		return false;
	});
});

function validateForm() {
	var $roleName = $newRoleForm.find('input[name="roleName"]');
	var $baseRoleId = $newRoleForm.find('select[name="baseRoleId"]');
	var $roleDescription =$('#roleDescription');
	
	var errorMsg = '';
	if ($roleName.val().trim().length == 0) {
		$roleName.addClass('errorMsgInput');
		errorMsg = 'A required field is missing.';
	}
	else {
		$roleName.removeClass('errorMsgInput');
	}
	
	if ($baseRoleId.val().length == 0) {
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

function sortBaseRoles($dropdown) {
	var $options = $dropdown.find('option:nth-child(n+3)');
	
	$options.sort(function (a, b) {
	    return a.text.toUpperCase() == b.text.toUpperCase() ? 0 : a.text.toUpperCase() < b.text.toUpperCase() ? -1 : 1;
	});
	
	$options.remove();
	
	$dropdown.append($options);
}