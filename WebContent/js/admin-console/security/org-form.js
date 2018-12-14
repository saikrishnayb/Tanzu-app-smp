var $errMsg = $('.edit-buttons').find('.error-messages-container').find('.errorMsg');
var $vendorFilterModal;
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
	$vendorFilterModal = $('#vendor-filter-model'); 
	var $createvendorHierarchy = $('#vendor-hierarchy');
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

	$vendorFilterModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 470,
		minHeight: 150,
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
		"bStateSave": true,
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
	
	//create Org
	$('.create').on("click", function(){
		var $orgForm = $('#org-form');
		var checked_ids =getSelectedVal('create');
		$('.error-messages-container').addClass('displayNone');
		
		var isNotValid = !validate($orgForm,checked_ids);
		
		if(isNotValid){
			$('.error-messages-container').removeClass('displayNone');
			return false;
		}else{
			var input = $("<input>")
	        .attr("type", "hidden")
	        .attr("name", "vendorStr").val(checked_ids);
			$orgForm.append($(input));
			//$orgForm.submit();
			//console.log($orgForm.serialize());
			var $createOrgPromise = $.post('./create-org.htm', $orgForm.serialize());
			$createOrgPromise.done(function() {
				location.assign('./org.htm');
			});
			$createOrgPromise.fail(function(xhr, ajaxOptions, thrownError) {
				 if(xhr.responseText.indexOf('Error Processing the Org')>0){
					  $errMsg.text('Error occured while processing the Org.');
					  $('.error-messages-container').removeClass('displayNone');
				  }
			});
		}
	});

	//saveVendor();
	//edit Org
	$('.save').on("click", function(){
		var orgId = $('#org-id').val();
		var checked_ids =getSelectedVal('edit');		
		var $orgForm = $('#org-form');
		
		$('.error-messages-container').addClass('displayNone');
		
		var isValid = validate($orgForm,checked_ids);
		
		if(isValid){
			var input = $("<input>")
	        .attr("type", "hidden")
	        .attr("name", "vendorStr").val(checked_ids);
			$orgForm.append($(input));
			//$orgForm.submit();
			var $updateOrgPromise =$.post('./update-org.htm', $orgForm.serialize());
			$updateOrgPromise.done(function() {
				location.assign('./org.htm');
			});
			$updateOrgPromise.fail(function(xhr, ajaxOptions, thrownError) {
				 if(xhr.responseText.indexOf('Error Processing the Org')>0){
					  $errMsg.text('Error occured while processing the Org.');
					  $('.error-messages-container').removeClass('displayNone');
				  }
			});
			
		} else {
			$('.error-messages-container').removeClass('displayNone');
			return false;
		}
		
	});
	// refresh SSO Data
	$('#refreshSSODetails').on("click", function(){
		
		var userId = $('#user-id').val();
		var userType = $('#user-type').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({
			  type: "GET",
			  url: "./sso-user-lookup-refresh.htm?userId="+ userId + '&userType='+userType+ '&_=' + currentTimeStamp, 
			
			  success: function(data){
				 
					  $("#sso-updated-information").html(data);
				 
				
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown) {
				  console.log(XMLHttpRequest.responseText,$errMsg);
				  if(XMLHttpRequest.responseText.indexOf('SSO')>0){
					  $errMsg.text('SSO doesnot exist.');
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
		    url : "get-signature-preview.htm?userId=" + userId + '&_=' + currentTimeStamp, 
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
		    url : "get-intials-preview.htm?userId=" + userId + '&_=' + currentTimeStamp , 
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
		    url : "delete-intials.htm?userId=" + userId + '&ssoId='+ssoId + '&_=' + currentTimeStamp , 
		    processData : false,
		    success: function(){
		   
		    }
		});
	});
	
	
	//signature delete
	$('#signature-delete').on('click', function(){
		var userId = $('#user-id').val();
		var ssoId = $('#sso-id').val();
		var currentTimeStamp = new Date().getTime();
		$.ajax({ 
		    url : "delete-signature.htm?userId=" + userId + '&ssoId='+ssoId + '&_=' + currentTimeStamp , 
		    processData : false,
		    success: function(){
		  
		    }
		});
	});
	
	$('.ssolookup').on("click", function(){
		var ssoId = $('#sso-id').val();
		$.ajax({
			  type: "GET",
			  url: "./sso-user-lookup.htm",
			  data: {ssoId: ssoId},
			  success: function(data){
				  var $userForm = $('.user-form-container');
					var vendorIds = [];
					
					$('.error-messages-container').addClass('displayNone');
					 $('#sso-id').val(data["ssoId"]);
					  $('#first-name').val(data["firstName"]);
					  $('#last-name').val(data["lastName"]);
					  $('#phone').val(data["phone"]);
			          $("#email").val(data["email"]);
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown) {
				  if(XMLHttpRequest.responseText.indexOf('SSO')>0){
					  $errMsg.text('SSO doesnot exist.');
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
			url: 'refresh-user-with-sso-data.htm?userId='+ userId + '&userType='+userType+ '&_=' + currentTimeStamp,
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
				closeModal($ssoRefreshModal);
				closeModal($editModal);
			});
		});
	});

	$('.vendor-filter').on("click",function(e){
	//	$('#corp').val('');
	//	$('#vendor-name').val('');
		$('#resetlbl').hide();
		openModal($vendorFilterModal);
	});	
	$('#back-id').on("click",function(){
		$vendorFilterModal.dialog('close');
	});	
	$(".filter-vendor-Btn").on("click",function(){
		var vendor = $('#vendor-name').val();
		var corp = $('#corp').val();
		var parentOrg=$('#parent-org').val();
		//if (parentOrg.length == 0 || parentOrg ==0) {
		//	parentOrg='';
		//}
		filterVendor(corp, vendor, parentOrg,false);
	});	
	
	$(".vendor-filter-reset").on("click",function(){
		var parentOrg=$('#parent-org').val();
		//if (parentOrg.length == 0 || parentOrg ==0) {
		//	parentOrg='';
		//}
		filterVendor('', '', parentOrg,true);
	});	
//Call after edit is loaded
	if(isCreatePage=='false'){
		populateEditVendorTree();
		showSelected(jeditVendor.jstree(true), 'edit-vendor-hierarchy');
		toggelStyle('hide', 'show',false);
	}else{
		 toggelStyle('show','hide',false);
	}
	
	$('[id="parent-org"]').on('change', function() {
		var parentOrg = $(this).val();
		$createvendorHierarchy.empty();		
		$("#loading").show();
		if (parentOrg.length > 0 && parentOrg >0) {
					
			var $createUserPromise =$.ajax({
				url: './get-vendor-hierarchy-page.htm',
				data: {parentOrg: parentOrg},
		        type: 'POST'
			});
			$createUserPromise.done(function(data){
				var htmlDt='';
				var $divId='vendor-hierarchy';
				var noRecord=false; 
				var $containerDiv=$("#create-vendor-hierarchy-container");
				if(isCreatePage=='false'){
					$divId='edit-vendor-hierarchy';
					$containerDiv=$("#edit-vendor-hierarchy-container");
				}
				if(data.indexOf('No record found')>0){
					 htmlDt=data;
					 noRecord=true;
				}else{
					 htmlDt='<div id="'+$divId+'" class="floatLeft clear-left jstree">'+data+'</div>';
				}
				$containerDiv.html(htmlDt);
				//closeModal($vendorFilterModal);
				if(isCreatePage=='false' && !noRecord){
					populateEditVendorTree();
				}else{
					createVendorTree();
				}
			});
			
		}else{
			var $containerDiv=$("#create-vendor-hierarchy-container").find('#vendor-hierarchy');
			if(isCreatePage=='false'){
				$containerDiv=$("#edit-vendor-hierarchy-container").find('#edit-vendor-hierarchy');
			}
			$containerDiv.html('<h3>Select Parent Org to display vendors.</h3>');
			$("#loading").hide();
		}
	});
	$('#hide').click( function() {
		
		if(isCreatePage=='false'){
			showSelected(jeditVendor.jstree(true), 'edit-vendor-hierarchy');
		}else{
			showSelected(jroleHierarchy.jstree(true), 'vendor-hierarchy');
		}
		 toggelStyle('hide', 'show',false);
	});
	$('#show').click( function() {
		if(isCreatePage=='false'){
			showAll('edit-vendor-hierarchy');
		}else{
			showAll('vendor-hierarchy');
		}
		 toggelStyle('show','hide',false);
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

function populateEditVendorTree(){
	editVendorTree();
	//saveVendor();
	var vendorId=$("#selVendorList").val();
	if(vendorId !== ''){
		var vendorArray = vendorId.split(",");
		if(vendorArray !== null){
			var checked_ids= [];
			$.each(vendorArray,function(i){
				var tempVal='sub-role-hierarchy-trees-'+vendorArray[i];
				checked_ids.push(tempVal);
				});
			if (checked_ids!=null && checked_ids.length > 0 ) {
				jeditVendor.jstree(true).select_node(checked_ids);
			}
		}
	}
}
function saveVendor(){
	
}
function getSelectedVal(val){
	var checked_ids = [];
	var obj;
	if(val==='create'){
		if(jroleHierarchy != null && jroleHierarchy !='undefined'){
			obj=jroleHierarchy.jstree(true).get_selected();
		}
	}else{
		if(jeditVendor != null && jeditVendor !='undefined'){
			obj=jeditVendor.jstree(true).get_selected();
		}
	}
	if(obj !=null){
		$.each( obj, function( key, value ) {	
				var str =value.split("-").pop();
				if(!isNaN(str)){
					checked_ids.push(str);
				}
			});
	}
	return checked_ids;
}

function validate($editForm,checked_ids){

	var flag = true;
	
	if(validateFormTextFields($editForm) == false){
		if($('#orgName').hasClass('errorMsgInput')){
			$errMsg.text('Error org Name invalid!');
		}
		if($('#parent-org').hasClass('errorMsgInput')){
			$errMsg.text('Error parent org invalid!');
		}
		if($('#orgDescription').hasClass('errorMsgInput')){
			$errMsg.text('Error org Description invalid!');
		}
		
		flag = false;
	}
	else if(checked_ids.length === 0){
		$errMsg.text('Error Associated Vendor invalid!');
		flag = false;
	}
	
	return flag;
}

function filterVendor(corp,vendor,parentOrg,isReset){
	var currentTimeStamp = new Date().getTime();
	if (parentOrg.length > 0 && parentOrg >0) {
		$('.filter-edit-buttons').find('.error-messages-container').addClass('displayNone');
		var $editUserPromise = $.ajax({
			url: 'filter-vendor-list.htm?_=' + currentTimeStamp,
			data: {corp: corp,vendor:vendor,parentOrg:parentOrg},
	        /*processData: false,
	        contentType: false,*/
	        type: 'POST'
		});
		
		$editUserPromise.done(function(data){
			var htmlDt='';
			var $divId='vendor-hierarchy';
			var $containerDiv=$("#create-vendor-hierarchy-container");
			var noRecord=false; 
			if(isCreatePage=='false'){
				$divId='edit-vendor-hierarchy';
				$containerDiv=$("#edit-vendor-hierarchy-container");
			}
			if(data.indexOf('No record found')>0){
				 htmlDt=data;
				 noRecord=true;
			}else{
				 htmlDt='<div id="'+$divId+'" class="floatLeft clear-left jstree">'+data+'</div>';
			}
			$containerDiv.html(htmlDt);
			closeModal($vendorFilterModal);
			if(isCreatePage=='false' && !noRecord){
				populateEditVendorTree();
			}else{
				createVendorTree();
			}
			$('#corp').val('');
			$('#vendor-name').val('');
			if(!isReset){
				$('#resetlbl').show();
				$('#filterlbl').hide();
			}else{
				$('#resetlbl').hide();
				$('#filterlbl').show();
			}
			 toggelStyle('show','hide',true);
		});
	}else{
		var $errMsgs =  $('.filter-edit-buttons').find('.error-messages-container').find('.errorMsg');
		$errMsgs.text('Please select prarent org before appling filter.');
		$('.filter-edit-buttons').find('.error-messages-container').removeClass('displayNone');
	}
}

function showSelected($treeObj,treeId){
	var undeterminedNode=[];
	 $('#'+treeId).find('li[id^=role-hierarchy-trees').each( function(){
		 var id=this.id;
		//  if($(this).find('ul').find('li').find('div').hasClass('jstree-wholerow-clicked')){
		 if($(this).find('ul > li > div').hasClass('jstree-wholerow-clicked')){
			   undeterminedNode.push(id);
		   }
	});
	 
	 var $tree = $treeObj,
	 nodesSelected = $tree.get_selected();
	 undeterminedNode.forEach( function(node) {
		 nodesSelected.push(node);
	 });
	  $('#'+treeId).find('li').each( function(){
	     if ( nodesSelected.indexOf(this.id) === -1 ) {
	         $(this).hide();
	     }
	 });
	 

/*	var $tree = $treeObj,
	nodesSelected = $tree.get_selected(),
    nodeIdsToStay = [];

	nodesSelected.forEach( function(node) {
	  var path = getPath(node, false, true);
	  path.forEach(function(n) {
	     if (nodeIdsToStay.indexOf(n)===-1) {
	         nodeIdsToStay.push(n);
	     }
	  });
	});
	 $('#'+treeId).find('li').each( function(){
	        if ( nodeIdsToStay.indexOf(this.id) === -1 ) {
	            $(this).hide();
	        }
	    });
	 */
}

function showAll(treeId){
	$('#'+treeId).find('li').each( function(){
	         $(this).show();
	 });
}

function toggelStyle(curr,other,reset){
	if(!reset){
		 $('#'+curr).css("color", "blue");
		 $('#'+curr).css("font-weight", "bold");
		 $('#'+other).css("color", "");
		 $('#'+other).css("font-weight", "normal");
	}else{
		 $('#'+curr).css("color", "");
		 $('#'+curr).css("font-weight", "normal");
		 $('#'+other).css("color", "");
		 $('#'+other).css("font-weight", "normal");
	}
}