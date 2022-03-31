// Selectors
var $errMsg = $('.edit-buttons').find('.error-messages-container').find('.errorMsg');
var $permissionsAccordion = $('.permission-tab-accordions');
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
var $signFileHiddenInput = $(".sign-file-hidden-input");
var $initFileHiddenInput = $(".init-file-hidden-input");

var $createEditVendorUserModal = $('#create-edit-vendor-user-modal'); 
var $ldapUserinfoModal=$("#ldap-userinfo-modal");

//Initialize ritsu
ritsu.initialize({
	useBootstrap3Styling: true
});

// Initializes the modals
ModalUtil.initializeModal($createEditVendorUserModal);
ModalUtil.initializeModal($ldapUserinfoModal);

//all permissions accordions
$permissionsAccordion.accordion({
	heightStyle: "content",
	collapsible: true            
});
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
		var $getPenskeUserRolesPromise = $.get('get-role-list.htm', {userTypeId:userTypeId});
		
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
		var $permissionsAccordionPromise = $.get('get-permissions-accordions', {roleId:roleId});

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
		var $vendorLocationsPromise = $.get('get-vendor-locations-content.htm', {vendorName:vendorName});
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

// refresh SSO Data
$('#refreshSSODetails').on("click", function(){
	
	var userId = $('#user-id').val();
	var userType = $('#user-type').val();
	var currentTimeStamp = new Date().getTime();
	//var $ssoUserLookup = $.get('sso-user-lookup.htm', {ssoId: ssoId});
	$.ajax({
		  type: "GET",
		  url: "./sso-user-lookup-refresh.htm?userId="+ userId + '&userType='+userType+ '&_=' + currentTimeStamp + '&isV2=' + true, 
		  global: false,
		  success: function(data){
				  $createEditVendorUserModal.html(data);
				  ModalUtil.openModal($createEditVendorUserModal);
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
			  
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
	
$('.ssolookup').on("click", function(){
	var ssoId = $('#sso-id').val();
	//var $ssoUserLookup = $.get('sso-user-lookup.htm', {ssoId: ssoId});
	$.ajax({
		  type: "GET",
		  url: "./sso-user-lookup.htm",
		  data: {ssoId: ssoId},
		  global: false,
		  success: function(data){
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
	
$createEditVendorUserModal.on("click", ".cancel, .close-modal", function(){
	var $this = $(this);
	var $closestModal = $this.closest(".modal");
	
	$closestModal.dialog('close').empty();
});
	
$createEditVendorUserModal.on("click",'.refresh-confirm',function(){
	var userId = $('#user-id').val();
	var userType = $('#user-type').val();
	var currentTimeStamp = new Date().getTime();
	var $editUserPromise = $.ajax({
		url: 'refresh-user-with-sso-data.htm?userId='+ userId + '&userType='+userType+ '&_=' + currentTimeStamp,
        processData: false,
        contentType: false,
        type: 'POST'
	});
		
	$editUserPromise.done(function(data){
		var userId = $('#user-id').val();
						
		var $userRow = $('tr[data-user-id="' + userId + '"]');
		var $userRowDT = $vendorUsersDataTable.row($userRow);

		$userRow.find('td:eq(1)').html( data.firstName );
		$userRow.find('td:eq(2)').html( data.lastName );
		$userRow.find('td:eq(3)').html( data.email );
		$userRow.find('td:eq(4)').html( data.phone );
		$userRow.find('td:eq(5)').html( data.role.roleName );
		$userRow.find('td:eq(6)').html( data.org );

		$userRow.attr('data-role-id', data.role.roleId);
		
		$userRowDT.invalidate('dom');
		$vendorUsersDataTable.draw();
		
		ModalUtil.closeModal($vendorUsersModal);
	});
});
	
//create vendor user
$('.createVendorUser').on("click", function(){
	
	if($(this).hasClass( "buttonPrimary")){
		var invalidForm  = !ritsu.validate('#user-form-vendor');
		
		if(invalidForm){
			ritsu.showErrorMessages();
			return false;
		}
		
		var ssoId = $('#sso-id').val();
		var userId = 0;
		
		var $isUserNameValidPromise = $.ajax({
											type: "POST",
											url:'./is-username-available.htm',
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
				
				
				var $createUserPromise = $.ajax( {
				    type: 'POST',
				    url: 'create-vendor-user', 
				    data: $userForm.serialize(),
				  });
				$createUserPromise.done(function() {
					var  $searchForm = $('#search-vendor-user-form');
					getVendorUserTableContents($searchForm.serialize());
					ModalUtil.closeModal($vendorUsersModal);
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
	ModalUtil.closeModal($ldapUserinfoModal);
});
	
$('.goLDAP').on("click",function(){
	$("#first-name").val($("#fnameM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
	$("#last-name").val($("#lnameM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
	$("#phone").val($("#phoneM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
	$("#email").val($("#emailM").val()).prop({'class': 'borderless', 'readonly': 'readonly'});
	ModalUtil.closeModal($ldapUserinfoModal);
});
	
//edit vendor user
$('.saveVendor').on("click", function(){
	if($(this).hasClass( "buttonPrimary")){
		var invalidForm  = !ritsu.validate('#user-form-vendor');
		
		if(invalidForm){
			ritsu.showErrorMessages();
			return false;
		}		
		
		var ssoId = $('#sso-id').val();
		var userId = $('#user-id').val();
		
		var $isUserNameValidPromise = $.ajax({
			type: "POST",
			url:'./is-username-available.htm',
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
			var $editUserPromise = $.post('./edit-vendor-user-static.htm', $userForm.serialize());
								
			$editUserPromise.done(function(data){
				var userId = $('#user-id').val();
				
				var $userRow = $('tr[data-user-id="' + userId + '"]');
				var $userRowDT = $vendorUsersDataTable.row($userRow);

				$userRow.find('td:eq(1)').html( data.firstName );
				$userRow.find('td:eq(2)').html( data.lastName );
				$userRow.find('td:eq(3)').html( data.email );
				$userRow.find('td:eq(4)').html( data.phone );
				$userRow.find('td:eq(5)').html( data.role.roleName );
				$userRow.find('td:eq(6)').html( data.org );

				$userRow.attr('data-role-id', data.role.roleId);
				
				$userRowDT.invalidate('dom');
				$vendorUsersDataTable.draw();
				
				ModalUtil.closeModal($vendorUsersModal);
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

		});
	}
});
	
function msieversion() {

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))     // If Internet Explorer, return version number
       return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
    else                 // If another browser, return 0
        return 0;
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
	
	var validUserId = ritsu.validate($('#user-id'));
	
	if(validUserId === false ) {
		$errMsg.text("Selected username contains invalid characters. Please use only letters and numbers");
		$('.error-messages-container').removeClass('displayNone');
	}
	
	if(validUserId && (isCreateOrEdit=='true') || (isCreateOrEdit=='false' && $.trim(ssoId) != $.trim(oldUserName))){
		$errMsg.text("");
		$('.error-messages-container').addClass('displayNone');
		var $isUserNameValidPromise = $.ajax({
			type: "POST",
			url:'./is-username-valid.htm',
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
					ModalUtil.openModal($ldapUserinfoModal);
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
					ModalUtil.openModal($ldapUserinfoModal);
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
					ModalUtil.openModal($ldapUserinfoModal);
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
					ModalUtil.openModal($ldapUserinfoModal);
		    	}
				toggleButton("disable");
		        break;		        
		    default:
		    	if(isCreateOrEdit ==='true'){
		    		 ModalUtil.openModal($ldapUserinfoModal);
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


//# sourceURL=create-edit-vendor-user.js