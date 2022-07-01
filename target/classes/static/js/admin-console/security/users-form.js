var $errMsg = $('.edit-buttons').find('.error-messages-container').find('.errorMsg');
var $ldapUserinfoModal;
$(document).ready(function() { 
	var $permissionsAccordion = $('.permission-tab-accordions');
	var $templateAccordions = $('.templates-accordion');
	var $editModal = $('#edit-modal');
	var $userType = $('#user-type');
	var $userRole = $('#user-role');
	var $ssoId = $('#sso-id');
	var $email = $('#email');
	var $userDept = $('#user-dept');
	var $vendor = $('#vendor');
	var $location = $('#location');
	var $ssoIdDiv = $('#sso-id-div');
	var $username = $('#username');
	var $firstNameContianer = $('.first-name-container');
	var $emailContianer = $('.email-container');
	var $lastNameContianer = $('.last-name-container');
	var $phoneContianer = $('.phone-container');
	var $templatesDiv = $('#templates');
	var $rolePermissionsDiv = $('#role-permissions');
	var $signFileModal = $('#sign-file-modal'); 
	var $initFileModal = $('#init-file-modal');
	var $signFileHiddenInput = $(".sign-file-hidden-input");
	var $initFileHiddenInput = $(".init-file-hidden-input");
	var $ssoRefreshModal = $('#sso-updated-information'); 
	$ldapUserinfoModal=$("#ldap-userinfo-modal");
	
	//SSO Refresh modal
	$ssoRefreshModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 470,
		minHeight: 150,
		resizable: false,
		closeOnEscape: false
	});


	//signature file preview modal
	$signFileModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: "auto",
		height: "auto",
		resizable: false,
		closeOnEscape: false
	});

	//initials file preview modal
	$initFileModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: "auto",
		height: "auto",
		resizable: false,
		closeOnEscape: false
	});
	
	$ldapUserinfoModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: "auto",
		height: "auto",
		resizable: false,
		closeOnEscape: false
	});

	//templates header accordion
	$('#templates-header-div').accordion({
		active: 0,
		clearStyle: true, 
		autoHeight: false,
		collapsible: true            
	});

	//all location accordions
	$templateAccordions.accordion({
		active: 0,
		clearStyle: true, 
		autoHeight: false,
		collapsible: true            
	});

	//all permissions accordions
	$permissionsAccordion.accordion({
		active: 0,
		clearStyle: true, 
		autoHeight: false,
		collapsible: true            
	});

	//all po-sub category component tables
	$('.po-cat-table').dataTable( { //All of the below are optional
		"aaSorting": [[ 4, "desc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": false, //Showing 1 to 10 of 11 entries
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": 5 , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		"fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
			var paginateRow = $(this).parent().children('div.dataTables_paginate');
			var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

			if (pageCount > 1)  {
				paginateRow.css("display", "block");
			} else {
				paginateRow.css("display", "none");
			}

			//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
			var infoRow = $(this).parent().children('div.dataTables_info');
			var rowCount = this.fnSettings().fnRecordsDisplay();
			if (rowCount > 0) {
				infoRow.css("display", "block");
			} else {
				infoRow.css("display", "none");
			}
		}
	} );

	//-------------------------------------------edit modal-----------------------------------

	$email.on('keyup blur', function(){
		var userType = $userType.val();
		var isSupplierUser = (userType == "2");
		var username = $email.val();
		if(isSupplierUser){
			$username.val(username);
		}
	});

	$ssoId.on('keyup blur', function(){
		var userType = $userType.val();
		var isPenskeUser = (userType == "1");
		var username = $ssoId.val();
		if(isPenskeUser){
			$username.val(username);
		}
	});

	//changes form based on user type
	$userType.change(function(){
		var userType = $userType.val();
		var isPenskeUser = (userType == "1");
		var isSupplierUser = (userType == "2");
		var $userDeptDiv = $('#user-dept-div');
		var $vendorDiv = $('#vendor-div');
		var $locationDiv = $('#location-div');
		var $editSignFileDiv =$('#signature-file-div');
		var $editInitFileDiv =$('#initials-file-div');


		$ssoId.val('');
		$userDept.val('');
		
			//$vendor.val('');
			//$location.val('');
	
		$userRole.val('');
		//$('.vendor-locations').remove();

		if(isSupplierUser){

			$templatesDiv.children().remove();
			$rolePermissionsDiv.children().remove();
			$rolePermissionsDiv.html("<h3><center>Select User Role to display permission</center></h3>");
			$('#username').val($('#email').val());

				//$ssoId.removeAttr('name');
			//	$ssoIdDiv.addClass('displayNone');				//sso id
				$ssoId.addClass('optional');

				$userDept.removeAttr('name');
				$userDeptDiv.addClass('displayNone');			//userDept
				$userDept.addClass('optional');

				$editSignFileDiv.addClass('displayNone');		//sign file

				$editInitFileDiv.addClass('displayNone');		//init file

				$vendor.attr('name', 'vendor.vendorName');
				$vendorDiv.removeClass('displayNone');			//vendor dropdown
				$vendor.removeClass('optional');
				
				$phoneContianer.removeClass('displayNone');
				$firstNameContianer.removeClass('displayNone');
				$lastNameContianer.removeClass('displayNone');
				$emailContianer.removeClass('displayNone');

				$locationDiv.removeClass('displayNone');		//location div
				
				$userRole.attr("disabled", "true");				// role select
		}else if(isPenskeUser){

			$rolePermissionsDiv.children().remove();
			$rolePermissionsDiv.html("<h3><center>Select User Role to display permission</center></h3>");
			$templatesDiv.children().remove();

			$('#username').val($ssoId.val());

			$vendor.attr('name', 'vendor.vendorName');
			$vendorDiv.addClass('displayNone');
			$vendor.addClass('optional');

			$locationDiv.addClass('displayNone');

			$ssoId.attr('name', 'ssoId');
			//$ssoIdDiv.removeClass('displayNone');

			$userDept.attr('name', 'userDept.userDeptId');
			$userDeptDiv.removeClass('displayNone');

			$editSignFileDiv.removeClass('displayNone');
			$editSignFileDiv.removeClass('optional');
			$editInitFileDiv.removeClass('displayNone');
			$editInitFileDiv.removeClass('optional');
			
			$phoneContianer.removeClass('displayNone');
			$firstNameContianer.removeClass('displayNone');
			$lastNameContianer.removeClass('displayNone');
			$emailContianer.removeClass('displayNone');

			$userRole.removeAttr("disabled");

			var userTypeId = $userType.val();
			var $getPenskeUserRolesPromise = $.get('get-role-list', {userTypeId:userTypeId});
			
			$getPenskeUserRolesPromise.done(function(data){
				$userRole.children().remove();
				var roleList = data;
				$userRole.append("<option selected>Select Role</option>");
				$.each(roleList, function(key, value){
					var role = value;
					$userRole.append("<option value=" + role.roleId + ">" + role.roleName + "</option>");
				});
			});
		}
	});
	if(document.getElementById("ssolookup-buttons-div")){
		$userType.trigger("change");
	}
	//--------------------------------------------listeners
	//permissions accordions
	$userRole.change(function(){
		var roleId = $(this).val();
		var isRoleEmpty = ((roleId == '') || (roleId=='Select Role'));
		if(isRoleEmpty){
			$('#role-permissions').children().remove();
			$('#role-permissions').html("<h3><center>Select User Role to display permission</center></h3>");
		} else {
			var $permissionsAccordionPromise = $.get('get-permissions-accordion-content', {roleId:roleId});

			$permissionsAccordionPromise.done(function(data){
				$('#role-permissions').html(data);
			});
		}
	});

	//vendor locations
	$vendor.change(function(){

		var vendorName = $vendor.val();
		var isVendorNameEmpty = (vendorName == '');
		
		$userRole.attr("disabled", "true");
		$templatesDiv.children().remove();
		if(isVendorNameEmpty){
			$('.vendor-locations').remove();
		} else {
			var $vendorLocationsPromise = $.get('get-vendor-locations-content', {vendorName:vendorName});
			$('.vendor-locations').remove();
			$vendorLocationsPromise.done(function(data){
				var vendorLocationsList = data;

				$.each(vendorLocationsList, function(key, value){
					var location = value;

					$('#location').append('<div class="vendor-locations">'
							+ '<input type="checkbox" id="' + location.vendorId + '" class="vendor-location-box" value="' + location.vendorId + '"/>'
							+ '<label for="' + location.vendorId + '" class="textLabel">' + location.city + ', ' +  location.state + ' - ' + location.vendorNumber + '</label>'
							+ '</div>');
				});
			});
		}
	});
	
	//create user
	$('.create').on("click", function(){
		var ssoId = $('#sso-id').val();
		var userId = 0;
		
		var $isUserNameValidPromise = $.ajax({
											type: "POST",
											url:'./is-username-available',
											global: false,
											data: {ssoId:ssoId, userId:userId}
										});
		
		$isUserNameValidPromise.fail(function(xhr, ajaxOptions, thrownError){
			var responseText = xhr.responseText;
			if(responseText.indexOf('user')>0){
				  $errMsg.text(responseText.substring(10,responseText.length));
				  $('.error-messages-container').removeClass('displayNone');
			  }
		}).done(function(){
			var $userForm = $('#user-form');
			var vendorIds = [];
			
			$('.error-messages-container').addClass('displayNone');
			
			var isValid = validate($userForm);
			
			if(isValid){
				$('.vendor-location-box').each(function(){
					if($(this).is(":checked")){
						vendorIds.push($(this).val());
					}
				});
				var browserSupportsFormData = window.FormData != undefined;
				if(browserSupportsFormData){
					var signatureFile = $signFileHiddenInput.get(0).files[0];
					var initialsFile = $initFileHiddenInput.get(0).files[0];
					var vendorIds = [];
					var formData = new FormData();
					
					$('.error-messages-container').addClass('displayNone');
					
					var isValid = validate($userForm);
					
					if(isValid){
					$('.vendor-location-box').each(function(){
						if($(this).is(":checked")){
							vendorIds.push($(this).val());
						}
					});
					
					$userForm.find('select, input:not(.vendor-location-box, input[type="file"], .file-text-box)').each(function(){
						var $input = $(this);
						var inputName = $input.attr("name");
						var inputValue = $input.val();
						formData.append(inputName, inputValue);
					});
	
					formData.append("vendorIds", vendorIds);
					formData.append("signatureImage", signatureFile);
					formData.append("initialsImage", initialsFile);
					var $createUserPromise =$.ajax({
						url: './create-user',
				        data: formData,
				        processData: false,
				        contentType: false,
				        type: 'POST'
					});
						
					$createUserPromise.done(function() {
						location.assign('./users');
					});
					$createUserPromise.fail(function(xhr, ajaxOptions, thrownError) {
						 if(xhr.responseText.indexOf('Error while creating user')>0){
							  $errMsg.text('Error occured while creating user..');
							  $('.error-messages-container').removeClass('displayNone');
						  }
					});
					} else {
						$('#vendor-ids').val(vendorIds);
						$('#user-type').removeAttr('disabled');
						$('#vendor').removeAttr('disabled');
						$userForm.submit();
					}
					
				}
			}else{
				$('.error-messages-container').removeClass('displayNone');
			}
		 //Error while creating
		});
	});

	//edit user
	$('.save').on("click", function(){
		var ssoId = $('#sso-id').val();
		var userId = $('#user-id').val();
		
		var $isUserNameValidPromise = $.ajax({
			type: "POST",
			url:'./is-username-available',
			global: false,
			data: {ssoId:ssoId, userId:userId}
		});
		
		$isUserNameValidPromise.fail(function(xhr, ajaxOptions, thrownError){
			var responseText = (xhr.responseText);
			$('#customized-ajax-error').find('p').html(responseText);
			$('#customized-ajax-error').dialog("open");
		}).done(function(){
			var $userForm = $('#user-form');
			var vendorIds = [];
			
			$('.error-messages-container').addClass('displayNone');
			
			var isValid = validate($userForm);
			
			if(isValid){
				$('.vendor-location-box').each(function(){
					if($(this).is(":checked")){
						vendorIds.push($(this).val());
					}
				});
				
				var browserSupportsFormData = window.FormData != undefined;
				
				if(browserSupportsFormData){
					var signatureFile = $signFileHiddenInput.get(0).files[0];
					var initialsFile = $initFileHiddenInput.get(0).files[0];
					var vendorIds = [];
					var formData = new FormData();
					
					$('.error-messages-container').addClass('displayNone');
					
					var isValid = validate($userForm);
					
					if(isValid){
					$('.vendor-location-box').each(function(){
						if($(this).is(":checked")){
							vendorIds.push($(this).val());
						}
					});
					
					$userForm.find('select, input:not(.vendor-location-box, input[type="file"], .file-text-box)').each(function(){
						var $input = $(this);
						var inputName = $input.attr("name");
						var inputValue = $input.val();
						formData.append(inputName, inputValue);
					});
	
					formData.append("vendorIds", vendorIds);
					formData.append("signatureImage", signatureFile);
					formData.append("initialsImage", initialsFile);
	
					var $editUserPromise = $.ajax({
						url: 'edit-user-static',
				        data: formData,
				        processData: false,
				        contentType: false,
				        type: 'POST'
					});
					
					$editUserPromise.done(function(data){
						var userId = $('#user-id').val();
						$('.user-id').each(function(){
							var userIdMatch = $(this).val();
							var isUserIdMatch = (userIdMatch == userId);
							
							if(!isUserIdMatch) return true;
							
							var $userRow = $(this).closest('.user-row');
							var nRow = $userRow[0];
			
							$('#users-table').dataTable().fnUpdate(data.firstName, nRow, 1,false);
							$('#users-table').dataTable().fnUpdate(data.lastName, nRow, 2,false);
							$('#users-table').dataTable().fnUpdate(data.email, nRow, 3,false);
							$('#users-table').dataTable().fnUpdate(data.phone, nRow, 4,false);
							$('#users-table').dataTable().fnUpdate(data.userType.userType, nRow, 6,false);
							$('#users-table').dataTable().fnUpdate(data.role.roleName + "<input class='role-id' type=hidden value='" + data.role.roleId + "'/>", nRow, 7,false);
			
							closeModal($editModal);
						});
					});
				} else {
					$('#vendor-ids').val(vendorIds);
					$('#user-type').removeAttr('disabled');
					$('#vendor').removeAttr('disabled');
					$userForm.submit();
					}
				}
			} else {
				$('.error-messages-container').removeClass('displayNone');
			}
		});
	});
	// refresh SSO Data
	$('#refreshSSODetails').on("click", function(){
		
		var userId = $('#user-id').val();
		var userType = $('#user-type').val();
		var currentTimeStamp = new Date().getTime();
		//var $ssoUserLookup = $.get('sso-user-lookup', {ssoId: ssoId});
		showLoading();
		$.ajax({
			  type: "GET",
			  url: "./sso-user-lookup-refresh?userId="+ userId + '&userType='+userType+ '&_=' + currentTimeStamp + '&isV2=' + false, 
			  global: false,
			  success: function(data){
				 hideLoading();
					  $("#sso-updated-information").html(data);
				 
				
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown) {
				  hideLoading();
				  if(XMLHttpRequest.responseText.indexOf('SSO')>0){
					  $errMsg.text('SSO doesnot exist.');
					  $('.error-messages-container').removeClass('displayNone');
				  }
				  else if(XMLHttpRequest.responseText.indexOf('Error while loading user data')>0){
					  $errMsg.text('Error while loading user data for this SSO.');
					  $('.error-messages-container').removeClass('displayNone');
				  }
				
			  }
			 
			});
		 openModal($('#sso-updated-information'));
	});
	

	//signature file
	$signFileHiddenInput.on("change", function(){
		var $hiddenFileInput = $(this);
		var $fileNameTextBox = $hiddenFileInput.closest(".file-input-container").find('.file-text-box');
		var filePathName = $hiddenFileInput.val().replace("C:\\fakepath\\", "");
		$fileNameTextBox.val(filePathName);
	});
	
	//initials file
	$initFileHiddenInput.on("change", function(){
		var $hiddenFileInput = $(this);
		var $fileNameTextBox = $hiddenFileInput.closest(".file-input-container").find('.file-text-box');
		var filePathName = $hiddenFileInput.val().replace("C:\\fakepath\\", "");
		$fileNameTextBox.val(filePathName);
	});
	
	// Add intial-file
	
	
	$('#intials-add').on('click', function(){
		
		$('.init-file-hidden-input').trigger('click');
		
	});
	//Add signature-file
	
$('#signature-add').on('click', function(){
		
		$('.sign-file-hidden-input').trigger('click');
		
	});
	//signature preview
	$('#signature-preview').on('click', function(){
		var userId = $('#user-id').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({ 
		    url : "get-signature-preview?userId=" + userId + '&_=' + currentTimeStamp, 
		    processData : false,
		    success: function(data){
		    $("#sign-file-modal").html('<img src="data:image/png;base64,'+data +'"'+'/>');
		    console.log('<img src="data:image/png;base64,'+data +'/>');
		    }
		});
		openModal($('#sign-file-modal'));
	});

	//phone masking
	$('#phone').mask("(999)999-9999");
	
	//phone char restriction
	$('#phone').keypress(function(key) {
		if(key.charCode < 48 || key.charCose > 57)
			return false;
	});
	
	$('#extension').keypress(function(key) {
		if($('#extension').val().length > 3){
			return false;
		}
	});
	
	$('#extension').keypress(function(key) {
		if(key.charCode < 48 || key.charCose > 57){
			return false;
		}
	});
	
	//initials preview
	$('#intials-preview').on('click', function(){
		var userId = $('#user-id').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({ 
		    url : "get-intials-preview?userId=" + userId + '&_=' + currentTimeStamp , 
		    processData : false,
		    success: function(data){
		    $("#init-file-modal").html('<img src="data:image/png;base64,'+data +'"'+'/>');
		    }
		});
		openModal($('#init-file-modal'));
	});
	
	
	
	//initials delete
	$('#intials-delete').on('click', function(){
		var userId = $('#user-id').val();
		var ssoId = $('#sso-id').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({ 
		    url : "delete-intials?userId=" + userId + '&ssoId='+ssoId + '&_=' + currentTimeStamp , 
		    processData : false,
		    success: function(){
		    	$('#hasInitFile').val('false');
		    	$('#init-file-name').val('');
		    	$('#intials-delete').hide();
		    	$('#intials-preview').hide();
		    }
		});
	});
	
	
	//signature delete
	$('#signature-delete').on('click', function(){
		var userId = $('#user-id').val();
		var ssoId = $('#sso-id').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({ 
		    url : "delete-signature?userId=" + userId + '&ssoId='+ssoId + '&_=' + currentTimeStamp , 
		    processData : false,
		    success: function(){
		    	$('#hasSignFile').val('false');
		    	$('#sign-file-name').val('');
		    	$('#signature-delete').hide();
		    	$('#signature-preview').hide();
		    }
		});
	});
	
	
	$('.ssolookup').on("click", function(){
		var ssoId = $('#sso-id').val();
		//var $ssoUserLookup = $.get('sso-user-lookup', {ssoId: ssoId});
		showLoading();
		$.ajax({
			  type: "GET",
			  url: "./sso-user-lookup",
			  data: {ssoId: ssoId},
			  global: false,
			  success: function(data){
				  hideLoading();
				  var $userForm = $('.user-form-container');
					var vendorIds = [];
					
					$('.error-messages-container').addClass('displayNone');
					 $('#sso-id').val(data["ssoId"]);
					  $('#first-name').val(data["firstName"]);
					  $('#last-name').val(data["lastName"]);
					  $('#phone').val(data["phone"]);
			          $("#email").val(data["email"]);
			          $("#ge-sso-id").val(data["gessouid"]);
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown) {
				  hideLoading();
				  if(XMLHttpRequest.responseText.indexOf('SSO')>0){
					  $errMsg.text('SSO doesnot exist.');
					  $('.error-messages-container').removeClass('displayNone');
				  }
				  else if(XMLHttpRequest.responseText.indexOf('Error while loading user data')>0){
					  $errMsg.text('Error while loading user data for this SSO.');
					  $('.error-messages-container').removeClass('displayNone');
				  }
				
			  }
			});
		
	});
	
	
	$(".refresh-modal").on("click", ".cancel, .close-modal", function(){
		var $this = $(this);
		var $closestModal = $this.closest(".modal");
		
		$closestModal.dialog('close').empty();
	});
	
	$ssoRefreshModal.on("click",'.refresh-confirm',function(){
		var userId = $('#user-id').val();
		var userType = $('#user-type').val();
		var currentTimeStamp = new Date().getTime();
		var $editUserPromise = $.ajax({
			url: 'refresh-user-with-sso-data?userId='+ userId + '&userType='+userType+ '&_=' + currentTimeStamp,
	        processData: false,
	        contentType: false,
	        type: 'POST'
		});
		
		$editUserPromise.done(function(data){
			var userId = $('#user-id').val();
			$('.user-id').each(function(){
				var userIdMatch = $(this).val();
				var isUserIdMatch = (userIdMatch == userId);
				
				if(!isUserIdMatch) return true;
				
				var $userRow = $(this).closest('.user-row');
				var nRow = $userRow[0];

				$('#users-table').dataTable().fnUpdate(data.firstName, nRow, 1,false);
				$('#users-table').dataTable().fnUpdate(data.lastName, nRow, 2,false);
				$('#users-table').dataTable().fnUpdate(data.email, nRow, 3,false);
				//var phone=formatPhone(data.phone);
				$('#users-table').dataTable().fnUpdate(data.phone, nRow, 4,false);
				$('#users-table').dataTable().fnUpdate(data.userType.userType, nRow, 6,false);
				$('#users-table').dataTable().fnUpdate(data.role.roleName + "<input class='role-id' type=hidden value='" + data.role.roleId + "'/>", nRow, 7,false);
				closeModal($ssoRefreshModal);
				closeModal($editModal);
			});
		});
	});
	
	
	
	//create vendor user
	$('.createVendorUser').on("click", function(){
		
		if($(this).hasClass( "buttonPrimary")){
			
			var ssoId = $('#sso-id').val();
			
			if (!isUserIdValid(ssoId)) return;
			
			var userId = 0;
			
			var $isUserNameValidPromise = $.ajax({
												type: "POST",
												url:'./is-username-available',
												global: false,
												data: {ssoId:ssoId, userId:userId}
											});
			
			$isUserNameValidPromise.fail(function(xhr, ajaxOptions, thrownError){
				var responseText = xhr.responseText;
				if(responseText.indexOf('user')>0){
					  $errMsg.text(responseText.substring(10,responseText.length));
					  $('.error-messages-container').removeClass('displayNone');
				  }
	
			}).done(function(){
				var $userForm = $('#user-form-vendor');
				var vendorIds = [];
				$('.error-messages-container').addClass('displayNone');
				var isValid = validate($userForm);
				if(isValid){
					
					
					var $createUserPromise = $.post('./create-vendor-user', $userForm.serialize());
					$createUserPromise.done(function() {
						location.assign('./vendor-users');
					});
					$createUserPromise.fail(function(xhr, ajaxOptions, thrownError) {
						 if(xhr.responseText.indexOf('USER_SERVICE_DUP_SSO:1')>0){
							  $errMsg.text('UserID '+ssoId+' already exists. Please choose a different UserID.');
							  $('.error-messages-container').removeClass('displayNone');
						  }
						 else if(xhr.responseText.indexOf('USER_SERVICE_NOT_STANDARD_SSO:11')>0){
							  $errMsg.text('UserID '+ ssoId + ' does not conform to standards.');
							  $('.error-messages-container').removeClass('displayNone');
						  }
						 else if(xhr.responseText.indexOf('Error while creating user')>0){
							  $errMsg.text('Error occured while creating user..');
							  $('.error-messages-container').removeClass('displayNone');
						  }
					});
				}else{
					$('.error-messages-container').removeClass('displayNone');
				}
			 //Error while creating
			});
			
		}
	});

	$('#sso-id').on("blur", function(){
		var $ssoVal=$(this).val();
		//var $emailVal=$('#email').val();
		var isCreateOrEdit=$('#isCreateOrEdit').val();
		if($.trim($ssoVal).length>0 && isCreateOrEdit=='true'){
		//	&& $.trim($ssoVal) !==$.trim($emailVal)){
			validateEmailOrUserId(isCreateOrEdit);
		}
	});
	
	$('.cancelLdap').on("click",function(){
		closeModal($ldapUserinfoModal);
	});
	
	$('.goLDAP').on("click",function(){
		$("#first-name").val($("#fnameM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
		$("#last-name").val($("#lnameM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
		$("#phone").val($("#phoneM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
		$("#email").val($("#emailM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
		closeModal($ldapUserinfoModal);
	});
	
	//edit vendor user
	$('.saveVendor').on("click", function(){
		if($(this).hasClass( "buttonPrimary")){
			var ssoId = $('#sso-id').val();
			var userId = $('#user-id').val();
			
			var $isUserNameValidPromise = $.ajax({
				type: "POST",
				url:'./is-username-available',
				global: false,
				data: {ssoId:ssoId, userId:userId}
			});
			
			$isUserNameValidPromise.fail(function(xhr, ajaxOptions, thrownError){
				var responseText = (xhr.responseText);
				$('#customized-ajax-error').find('p').html(responseText);
				$('#customized-ajax-error').dialog("open");
			}).done(function(){
				var $userForm = $('#user-form-vendor');
				var vendorIds = [];
				
				$('.error-messages-container').addClass('displayNone');
				
				var isValid = validate($userForm);
				
				if(isValid){
						$('.error-messages-container').addClass('displayNone');
						var $editUserPromise = $.post('./edit-vendor-user-static', $userForm.serialize());
											
						$editUserPromise.done(function(data){
							var userId = $('#user-id').val();
							$('.user-id').each(function(){
								var userIdMatch = $(this).val();
								var isUserIdMatch = (userIdMatch == userId);
								
								if(!isUserIdMatch) return true;
								
								var $userRow = $(this).closest('.user-row');
								var nRow = $userRow[0];
				
								$('#users-table').dataTable().fnUpdate(data.firstName, nRow, 1,false);
								$('#users-table').dataTable().fnUpdate(data.lastName, nRow, 2,false);
								$('#users-table').dataTable().fnUpdate(data.email, nRow, 3,false);
								$('#users-table').dataTable().fnUpdate(data.phone, nRow, 4,false);
								//$('#users-table').dataTable().fnUpdate(data.userType.userType, nRow, 6,false);
								$('#users-table').dataTable().fnUpdate(data.role.roleName + "<input class='role-id' type=hidden value='" + data.role.roleId + "'/>", nRow, 5,false);
				
								closeModal($editModal);
							});
						});
						$editUserPromise.fail(function(xhr, ajaxOptions, thrownError) {
							
							 if(xhr.responseText.indexOf('USER_SERVICE_DUP_SSO:1')>0){
								  $errMsg.text('UserID '+ssoId+' already exists. Please choose a different UserID.');
								  $('.error-messages-container').removeClass('displayNone');
							  }
							 else if(xhr.responseText.indexOf('USER_SERVICE_NOT_STANDARD_SSO:11')>0){
								  $errMsg.text('UserID '+ ssoId + ' does not conform to standards.');
								  $('.error-messages-container').removeClass('displayNone');
							  }
							 else  if(xhr.responseText.indexOf('Error while updating user')>0){
								  $errMsg.text('Error occured while updating user..');
								  $('.error-messages-container').removeClass('displayNone');
							  }
						});
				} 
				else {
					$('.error-messages-container').removeClass('displayNone');
				}
			});
		}
	});
	
});
function msieversion() {

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))     // If Internet Explorer, return version number
       return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
    else                 // If another browser, return 0
        return 0;
}




function validate($editForm){

	var flag = true;
	var userTypeId = $('#user-type').val();
	if(validateFormTextFields($editForm) == false){
		
		if($('#phone').hasClass('errorMsgInput')){
			$errMsg.text('Error phone number invalid!');
		}
		if($('#last-name').hasClass('errorMsgInput')){
			$errMsg.text('Error last name invalid!');
		}
		if($('#first-name').hasClass('errorMsgInput')){
			$errMsg.text('Error first name invalid!');
		}
		if($('#email').hasClass('errorMsgInput')){
			$errMsg.text('Error email invalid!');
		}
		if($('#sso-id').hasClass('errorMsgInput')){
			$errMsg.text('Error sso id invalid!');
		}
		if($('#user-dept').hasClass('errorMsgInput')){
			$errMsg.text('No department selected!');
		}
		if($('#user-role').hasClass('errorMsgInput')){
			$errMsg.text('No role selected!');
		}
		if($('#bulist').hasClass('errorMsgInput')){
			$errMsg.text('No Business Unit selected!');
		}
		if($('#user-type').hasClass('errorMsgInput')){
			$errMsg.text('No user type selected!');
		}
		flag = false;
	}
	if(userTypeId != 2){	
		var signFileName = $('#sign-file-name').val();
		var initFileName = $('#init-file-name').val();
		
		var blankSignFile = signFileName.length === 0 || !$.trim(signFileName);
		var blankInitFile = initFileName.length === 0 || !$.trim(initFileName);
		
		if(!blankSignFile){
			var signFileHandel = signFileName.substr(signFileName.length - 4);
			if(signFileHandel != '.jpg' && signFileHandel != '.png' && signFileHandel != 'ists'){
				$('#sign-file-name').addClass("errorMsgInput");
				$errMsg.text('Incorrect signature file type!');
				flag = false;
			}
		}
		
		if(!blankInitFile){
			var initFileHandel = initFileName.substr(initFileName.length - 4);
			if(initFileHandel != '.jpg' && initFileHandel != '.png' && initFileHandel != 'ists'){
				$('#init-file-name').addClass("errorMsgInput");
				$errMsg.text('Incorrect initials file type!');
				flag = false;
			}
		}
		$('#init-file-name').removeClass("errorMsgInput");
		$('#sign-file-name').removeClass("errorMsgInput");
	}
	
	
	
	/*if(userTypeId == 2){
		var count = 0;
		$('.vendor-location-box').each(function(){
			if($(this).is(":checked")){
				count++;
			}
		});
		if(count==0){
			$errMsg.text('No vendor locations chosen!');
			flag = false;
		}
	}*/
	
	return flag;
}

function toggleButton(val){
	if(val=='disable'){
		$('#save-user-vendor-edit').removeClass( "buttonPrimary" ).addClass( "buttonDisabled" );
	}else{
		$('#save-user-vendor-edit').removeClass( "buttonDisabled" ).addClass( "buttonPrimary" );
	}
}

function validateEmailOrUserId(isCreateOrEdit){
	var ssoId = $('#sso-id').val();
	var userId = $('#user-id').val();
	var oldUserName=$('#sso-old-id').val();
	
	var validUserId = isUserIdValid(ssoId);
	
	if(validUserId === false ) {
		$errMsg.text("Selected username contains invalid characters. Please use only letters and numbers");
		$('.error-messages-container').removeClass('displayNone');
	}
	
	if(validUserId && (isCreateOrEdit=='true') || (isCreateOrEdit=='false' && $.trim(ssoId) != $.trim(oldUserName))){
		$errMsg.text("");
		$('.error-messages-container').addClass('displayNone');
		var $isUserNameValidPromise = $.ajax({
			type: "POST",
			url:'./is-username-valid',
			global: true,
			data: {ssoId:ssoId, userId:userId,isCreateOrEdit:isCreateOrEdit}
		});

		$isUserNameValidPromise.fail(function(xhr, ajaxOptions, thrownError){
			var responseText = xhr.responseText;
			if(responseText.indexOf('Error in processing the last request.')>0){
				$errMsg.text("Error in processing the last request.");
				$('.error-messages-container').removeClass('displayNone');
			}
		}).done(function(data){
			$("#returnFlg").val(data.returnFlg);
			if(isCreateOrEdit==='true'){
				$ldapUserinfoModal.find("#ok").hide();
				$ldapUserinfoModal.find("#yes").hide();
			}
			switch(data.returnFlg) {
		    case -1:
		    	toggleButton("");
		        break;
		    case 0:
		    	var errorTxt="An active user already exists with the user name " + ssoId + ".";
				if(isCreateOrEdit ==='true'){
					$ldapUserinfoModal.find("#infoText").text(errorTxt);
					$ldapUserinfoModal.find("#ok").show();
					openModal($ldapUserinfoModal);
		    	}else{
		    		$errMsg.text(errorTxt);
					$('.error-messages-container').removeClass('displayNone');
		    	}
				toggleButton("disable");
		        break;
		    case 1:
		    	var errorTxt="<p>User already exists in the Penske system but has not been added to Supplier Management Center.</p>"+
		    				"<p> Would you like to add them now?</p>";
				
		    	if(isCreateOrEdit ==='true'){
					$ldapUserinfoModal.find("#infoText").html(errorTxt);

					$ldapUserinfoModal.find("#yes").show();
					$ldapUserinfoModal.find("#fnameM").val(data.firstName);
					$ldapUserinfoModal.find("#lnameM").val(data.lastName);
					$ldapUserinfoModal.find("#phoneM").val(data.phone);
					$ldapUserinfoModal.find("#emailM").val(data.email);
					$ldapUserinfoModal.find("#ssoM").val(data.ssoId);
					openModal($ldapUserinfoModal);
		    	}else{
		    		$errMsg.text(errorTxt);
					$('.error-messages-container').removeClass('displayNone');
		    	}
				toggleButton("");
		        break;
		    case 2:
		    	var errorTxt="<p>An Inactive user already exists with the user name " + ssoId + " in LDAP.</p>" +
		    			"<p>Please Contact Support @ "+data.supportNumber+" for further assistance.</p>";
		    	
				if(isCreateOrEdit ==='true'){
					$ldapUserinfoModal.find("#infoText").html(errorTxt);
					$ldapUserinfoModal.find("#ok").show();
					openModal($ldapUserinfoModal);
		    	}else{
		    		$errMsg.text(errorTxt);
					$('.error-messages-container').removeClass('displayNone');
		    	}
				toggleButton("disable");
		        break;
		    case 3:
		    	var errorTxt="<p>Selected username contains invalid characters. Please use letters and numbers only</p>";
				if(isCreateOrEdit ==='true'){
					$ldapUserinfoModal.find("#infoText").html(errorTxt);
					$ldapUserinfoModal.find("#ok").show();
					openModal($ldapUserinfoModal);
		    	}
				toggleButton("disable");
		        break;		        
		    default:
		    	if(isCreateOrEdit ==='true'){
		    		 openModal($ldapUserinfoModal);
		    	}else{
		    		toggleButton("");
		    	}
		} 
			if(!(isCreateOrEdit==='true')){
				if(data.returnFlg==1){					
					$('#email').val(data.email);
					$("#sso-id").val(data.ssoId);
					$("#first-name").val(data.firstName);
					$("#last-name").val(data.lastName);
					$("#phone").val(data.phone);
				}else{
					$("#first-name").val('');
					$("#last-name").val('');
					$("#phone").val('');
				}
			}
		});
	}
}

function isUserIdValid(userId) {
	console.log("Validating Username");
	var alphaNumericRegex = /(^[A-Za-z0-9]+$)/;
	return alphaNumericRegex.test(userId)
}


//# sourceURL=users-form.js