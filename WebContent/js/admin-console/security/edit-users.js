$(document).ready(function() { 
	var $permissionsAccordion = $('.permission-tab-accordions');
	var $templateAccordions = $('.templates-accordion');
	var $editModal = $('#edit-modal');
	var $editUserType = $('#edit-user-type');
	var $editUserRole = $('#edit-user-role');
	var $editSsoId = $('#edit-sso-id');
	var $editEmail = $('#edit-email');
	var $editUserDept = $('#edit-user-dept');
	var $editVendor = $('#edit-vendor');
	var $editLocation = $('#edit-location');
	var $editSsoIdDiv = $('#edit-sso-id-div');
	var $editUsername = $('#edit-username');
	var $templatesDiv = $('#templates');
	var $rolePermissionsDiv = $('#role-permissions');
	var $signFileModal = $('#sign-file-modal'); 
	var $initFileModal = $('#init-file-modal');
	var $signFileHiddenInput = $(".sign-file-hidden-input");
	var $initFileHiddenInput = $(".init-file-hidden-input");

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

	//templates header accordion
	$('#templates-header-div').accordion({
		active: 0,
		clearStyle: true, 
		autoHeight: false,
		collapsible: false           
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

	$editEmail.on('keyup', function(){
		var userType = $editUserType.val();
		var isSupplierUser = (userType == "2");
		var username = $editEmail.val();
		if(isSupplierUser){
			$editUsername.val(username);
		}
	});

	$editSsoId.on('keyup', function(){
		var userType = $editUserType.val();
		var isPenskeUser = (userType == "1");
		var username = $editSsoId.val();
		if(isPenskeUser){
			$editUsername.val(username);
		}
	});

	//changes form based on user type
	$editUserType.change(function(){
		var userType = $editUserType.val();
		var isPenskeUser = (userType == "1");
		var isSupplierUser = (userType == "2");
		var $editUserDeptDiv = $('#edit-user-dept-div');
		var $editVendorDiv = $('#edit-vendor-div');
		var $editLocationDiv = $('#edit-location-div');
		var $editSignFileDiv =$('#edit-signature-file-div');
		var $editInitFileDiv =$('#edit-initials-file-div');


		$editSsoId.val('');
		$editUserDept.val('');
		$editVendor.val('');
		$editLocation.val('');
		$editUserRole.val('');
		$('.vendor-locations').remove();

		if(isSupplierUser){

			$templatesDiv.children().remove();
			$rolePermissionsDiv.children().remove();

			$('#edit-username').val($('#edit-email').val());

			if(!($editSsoIdDiv.hasClass('displayNone'))){
				$editSsoId.removeAttr('name');
				//$editSsoIdDiv.addClass('displayNone');
				$editSsoId.addClass('optional');}

			if(!($editUserDeptDiv.hasClass('displayNone'))){
				$editUserDept.removeAttr('name');
				$editUserDeptDiv.addClass('displayNone');
				$editUserDept.addClass('optional');}

			if(!($editSignFileDiv.hasClass('displayNone'))){
				$editSignFileDiv.addClass('displayNone');}

			if(!($editInitFileDiv.hasClass('displayNone'))){
				$editInitFileDiv.addClass('displayNone');}

			if($editVendorDiv.hasClass('displayNone')){
				$editVendor.attr('name', 'vendor.vendorName');
				$editVendorDiv.removeClass('displayNone');
				$editVendor.removeClass('optional');}

			if($editLocationDiv.hasClass('displayNone')){
				$editLocationDiv.removeClass('displayNone');}
		}

		if(isPenskeUser){

			$rolePermissionsDiv.children().remove();
			$templatesDiv.children().remove();

			$('#edit-username').val($editSsoId.val());

			if(!($editVendorDiv.hasClass('displayNone'))){
				$editVendor.attr('name', 'vendor.vendorName');
				$editVendorDiv.addClass('displayNone');
				$editVendor.addClass('optional');}

			if(!($editLocationDiv.hasClass('displayNone'))){
				$editLocationDiv.addClass('displayNone');}

			if($editSsoIdDiv.hasClass('displayNone')){
				$editSsoId.attr('name', 'ssoId');
				$editSsoIdDiv.removeClass('displayNone');}

			if($editUserDeptDiv.hasClass('displayNone')){
				$editUserDept.attr('name', 'userDept');
				$editUserDeptDiv.removeClass('displayNone');}

			if($editSignFileDiv.hasClass('displayNone')){
				$editSignFileDiv.removeClass('displayNone');
				$editSignFileDiv.removeClass('optional');}

			if($editInitFileDiv.hasClass('displayNone')){
				$editInitFileDiv.removeClass('displayNone');}

		}
	});

	//--------------------------------------------listeners
	//permissions accordions
	$editUserRole.change(function(){
		var roleId = $(this).val();
		var isRoleEmpty = (roleId == '');
		if(isRoleEmpty){
			$('#role-permissions').children().remove();
		} else {
			var $permissionsAccordionPromise = $.get('get-permissions-accordion-content.htm', {roleId:roleId});

			$permissionsAccordionPromise.done(function(data){
				$('#role-permissions').html(data);
			});
		}
	});

	//vendor locations
	$editVendor.change(function(){

		var vendorName = $editVendor.val();
		var isVendorNameEmpty = (vendorName == '');
		$templatesDiv.children().remove();
		if(isVendorNameEmpty){
			$('.vendor-locations').remove();
		} else {
			var $vendorLocationsPromise = $.get('get-vendor-locations-content.htm', {vendorName:vendorName});
			$('.vendor-locations').remove();
			$vendorLocationsPromise.done(function(data){
				var vendorLocationsList = data;

				$.each(vendorLocationsList, function(key, value){
					var location = value;

					$('#edit-location').append('<div class="vendor-locations">'
							+ '<input type="checkbox" id="' + location.vendorId + '" class="vendor-location-box" value="' + location.vendorId + '"/>'
							+ '<label for="' + location.vendorId + '" class="textLabel">' + location.city + ', ' +  location.state + ' - ' + location.vendorNumber + '</label>'
							+ '</div>');
				});
			});
		}
	});

	//edit modal
	$('.save').on("click", function(){
		var $editForm = $('#edit-form');
		var signatureFile = $signFileHiddenInput.get(0).files[0];
		var initialsFile = $initFileHiddenInput.get(0).files[0];
		var vendorIds = [];
		var formData = new FormData();
		
		var isValid = validate($editForm);
		
		if(isValid){
		$('.vendor-location-box').each(function(){
			if($(this).is(":checked")){
				vendorIds.push($(this).val());
			}
		});
		
		$editForm.find('select, input:not(.vendor-location-box, input[type="file"], .file-text-box)').each(function(){
			var $input = $(this);
			var inputName = $input.attr("name");
			var inputValue = $input.val();
			formData.append(inputName, inputValue);
		});

		formData.append("vendorIds", vendorIds);
		formData.append("signatureImage", signatureFile);
		formData.append("initialsImage", initialsFile);

		var $editUserPromise = $.ajax({
			url: 'edit-user',
	        data: formData,
	        processData: false,
	        contentType: false,
	        type: 'POST'
		});
		
		$editUserPromise.done(function(data){
			var userId = $('#edit-user-id').val();
			$('.user-id').each(function(){
				var userIdMatch = $(this).val();
				var isUserIdMatch = (userIdMatch == userId);
				
				if(!isUserIdMatch) return true;
				
				var $userRow = $(this).closest('.user-row');
				var nRow = $userRow[0];

				$('#users-table').dataTable().fnUpdate(data.firstName, nRow, 1);
				$('#users-table').dataTable().fnUpdate(data.lastName, nRow, 2);
				$('#users-table').dataTable().fnUpdate(data.email, nRow, 3);
				$('#users-table').dataTable().fnUpdate(data.phone, nRow, 4);
				$('#users-table').dataTable().fnUpdate(data.userType.userType, nRow, 6);
				$('#users-table').dataTable().fnUpdate(data.role.roleName + "<input class='role-id' type=hidden value='" + data.role.roleId + "'/>", nRow, 7);


			});
			closeModal($editModal);
		});
		}
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
	
	//signature preview
	$('#signature-preview').on('click', function(){
		var userId = $('#user-id').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({ 
		    url : "get-signature-preview.htm?userId=" + userId + '&_=' + currentTimeStamp, 
		    processData : false,
		    success: function(data){
		    $("#sign-file-modal").html('<img src="data:image/png;base64,'+data +'"'+'/>');
		    console.log('<img src="data:image/png;base64,'+data +'/>');
		    }
		});
		openModal($('#sign-file-modal'));
	});

	
	$('#edit-phone').mask("(999)999-9999");
	
	//initials preview
	$('#intials-preview').on('click', function(){
		var userId = $('#edit-user-id').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({ 
		    url : "get-intials-preview.htm?userId=" + userId + '&_=' + currentTimeStamp , 
		    processData : false,
		    success: function(data){
		    $("#init-file-modal").html('<img src="data:image/png;base64,'+data +'"'+'/>');
		    }
		});
		openModal($('#init-file-modal'));
	});

});

function validate($editForm){
	
	if(validateFormTextFields($editForm) == false)
		return false;
	
	var signFileName = $('#sign-file-name').val();
	var initFileName = $('#init-file-name').val();
	
	var blankSignFile = signFileName.length === 0 || !$.trim(signFileName);
	var blankInitFile = initFileName.length === 0 || !$.trim(initFileName);
	
	if(!blankSignFile){
		var signFileHandel = signFileName.substr(signFileName.length - 4);
		if(signFileHandel != '.jpg' && signFileHandel != '.png' && signFileHandel != 'ists'){
			$('#sign-file-name').addClass("errorMsgInput");
			return false;
		}
	}
	
	if(!blankInitFile){
		var initFileHandel = initFileName.substr(initFileName.length - 4);
		if(initFileHandel != '.jpg' && initFileHandel != '.png' && initFileHandel != 'ists'){
			$('#init-file-name').addClass("errorMsgInput");
			return false;
		}
	}
	$('#init-file-name').removeClass("errorMsgInput");
	$('#sign-file-name').removeClass("errorMsgInput");
	
	return true;
}


