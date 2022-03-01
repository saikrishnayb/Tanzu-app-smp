var $tabNavUser=$('#tabNavUser').val();
selectCurrentNavigation("tab-security",$tabNavUser);

// Selectors
var $vendorUsersTable = $('#vendor-users-table');
var $vendorUsersModal = $('#vendor-user-modal');

// Initializes multiselect - have to initalize here instead of the separate js because we need to wait for the page to fully load 
// before this gets initialized or we'll get a .multiselect is not a function error
$('#search-org').multiselect({
minWidth:220,
noneSelectedText:"",
open: function(){
  $(".ui-multiselect-menu ").css('width','335px');
}
}).multiselectfilter({width : 250});

// Initializes the modal
ModalUtil.initializeModal($vendorUsersModal);

// Initializes the DataTable
var $vendorUsersDataTable = $vendorUsersTable.DataTable( { //All of the below are optional
	"order": [[ 1, "asc" ]], //default sort column
	"paging": true, //enable pagination
	"stateSave": true,
	"autoWidth": false, //cray cray
	"lengthChange": true, //enable change of records per page, not recommended
	"searching": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
	"ordering": true, //Allow sorting by column header
	"info": true, //Showing 1 to 10 of 11 entries
	createdRow: function (row, data) {
		$(row).attr('data-user-id', data.userId);
		$(row).attr('data-role-id', data.role.roleId);
	},
	"columnDefs": [
					 {'visible': false, targets: [0]},
					 {'searchable': false, targets: [0]},
					 {'className': 'user-id-td', targets: [0]},
				     {'className': 'user-email', targets: [4]},
					 {'orderable': false, targets: [ 1 ]}, //stops first column from being sortable
					 { 'width': "100px", targets: [ 1 ] },
					 { 'type': "date", targets: [ "lastLogin" ] },
					 {'searchable': false, targets: [1]}
					 ],
	"columns": [
		{ data: 'userId'},
		{ defaultContent: 
		'<div class="dropdown">' +
			'<a class="bootStrapDropDown dropdown-toggle" data-toggle="dropdown">'+
				'Actions' + 
				'<span class="caret"></span>' +
			'</a>' +
	        '<ul class="dropdown-menu">' +
				'<li>' +
					'<a class="edit-vendor-user">Edit User</a>' +
				'</li>' +
				'<li>' +
					'<a class="resend-email">Re-send Enrollment Email</a>' +
				'</li>' +
				'<li>' +
					'<a class="deactivate-vendor-user">Delete User</a>' +
				'</li>' +
			'</ul>' +
		'</div>'},
        { data: 'firstName' },
        { data: 'lastName' },
		{ data: 'email' },
        { data: 'formattedPhone' },
		{ data: 'role.roleName' },
		{ data: 'org' },
		{ data: 'formattedCreatedDate' },
		{ data: 'formattedLastLoginDate'}
    ],
	"paginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
	"pageLength": 100 , //number of records per page for pagination
	"language": {"emptyTable": "No Results Found"}, //Message displayed when no records are found
	"drawCallback": function() { //This will hide the pagination menu if we only have 1 page.
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
	
		parent.iframeResizer.resizeIframe();
	}
});

$(function() {
	getVendorUserTableContents(null);	
});
//---------------------------------------Listeners----------------------------------------

//vendor user deactivate modal
$vendorUsersTable.on("click", ".deactivate-vendor-user", function(){
	var $this =  $(this);
	var $isVendorUser=true;
	deactivteUser($this,$isVendorUser);
});
	
//deactivate execution
$vendorUsersModal.on("click", ".deactivate-confirm", function(){
	var userId = $('#user-id').val();
	var isVendorUser = $('#is-vendor-user').val();
	var $deactivateUserPromise = $.post('deactivate-user.htm', {userId:userId,isVendorUser:isVendorUser});
	
	$deactivateUserPromise.done(function(data){
		$('.user-id').each(function(){
			var userIdMatch = $(this).val();
			var isUserIdMatch = (userIdMatch == userId);
			if(isUserIdMatch){
				var $userRow = $(this).closest('.user-row');
				var nRow = $userRow[0];
				
				$('#users-table').dataTable().fnDeleteRow(nRow);
			}
		});
		ModalUtil.closeModal($vendorUsersModal);
	});
});

// Cancel
$vendorUsersModal.on('click', '#cancelButton', function() {
	ModalUtil.closeModal($vendorUsersModal);
});
	
// edit modal
$vendorUsersTable.on("click", ".edit-vendor-user", function(){
	
	var $this =  $(this);
	var userId = $this.closest('tr').data('user-id');
	var userType = "VENDOR";
	var roleId = $this.closest('tr').data('role-id');
	var $getEditUserModalContentPromise = $.get('get-create-edit-vendor-user', {isCreate:false, userId:userId, userType:userType, roleId:roleId});
	
	$getEditUserModalContentPromise.done(function(data){
		$vendorUsersModal.html(data);
		ModalUtil.openModal($vendorUsersModal);
		$vendorUsersModal.find("#first-name").focus();
		
	});
});

// Create
$('#create-vendor-user').on('click', function(){
	var $getCreateVendorUserModalContentPromise = $.get('get-create-edit-vendor-user', {isCreate:true, userId:null, userType:null, roleId:null});
	
	$getCreateVendorUserModalContentPromise.done(function(data){
		$vendorUsersModal.html(data);
		ModalUtil.openModal($vendorUsersModal);
	});
});

$vendorUsersTable.on("click", ".resend-email", function(){
	var $this =  $(this);
	var userId = $this.closest('tr').data('user-id');
	
	var $getResendModalContentPromise = $.get('get-resend-email-modal-content.htm', {userId:userId});
	
	$getResendModalContentPromise.done(function(data){
		$vendorUsersModal.html(data);
		ModalUtil.openModal($vendorUsersModal);
		
	});
});

$vendorUsersModal.on('click', '#resend-confirm', function() {
	var userId = $('#resend-confirm').data('user-id');
	
	var $resendEmailContentPromise = $.get('resend-email.htm', {userId:userId});
	
	$resendEmailContentPromise.done(function(){
		ModalUtil.closeModal($vendorUsersModal);
	});
});
	
if($('#search-content').data('has-been-searched')==true && $('#search-content').data('vendor-user-search')==true){
	$("#search-content").removeClass("displayNone").addClass("displayBlock");
}

//--------------------------open and close search criteria div-----------------

function toggleContent(contentId,spanId){
	
	if($("#" + contentId).is(":visible")){
		//Currently Expanded
	  $("#" + spanId).removeClass('expandedImage').addClass('collapsedImage');
    $("#" + contentId).removeClass("displayBlock").addClass("displayNone");
    $("#" + spanId).text('Show Search Criteria');
	}
	else{
		//Currently Collapsed
	  $("#" + spanId).removeClass('collapsedImage').addClass('expandedImage');
    $("#" + contentId).removeClass("displayNone").addClass("displayBlock");
    $("#" + spanId).text('Hide Search Criteria');
	}
	
}

function validateSearchForm($searchForm){
	var $errorMessage = $('.error-messages-container').find('.errorMsg');
	var count = 0;
	$searchForm.find('.input').each(function(){
		if($(this).val() == ''){
			count++;
		}
	});
	
	
	
	var allBlank = (count >= 5);
	
	if(allBlank){
		return null;
	}
	
	validateFormTextFields($searchForm);
	
	if($searchFName.hasClass("errorMsgInput")){
		$errorMessage.text('Error Invalid First Name');
		return false;
	}
	
	if($searchLName.hasClass("errorMsgInput")){
		$errorMessage.text('Error Invalid Last Name');
		return false;
	}
	
	if($searchEmail.hasClass("errorMsgInput")){
		$errorMessage.text('ErrorInvalid Email');
		return false;
	}
	
	return true;
}

function deactivteUser($this,$isVendorUser){
	var email = $this.closest('tr').find('.user-email').text();
	var userId = $this.closest('tr').data('user-id')
	var $getDeactivateUserModalContentPromise = $.get('get-deactivate-user-modal-content.htm', {email:email, userId:userId
		, isVendorUser:$isVendorUser, isV2: true});
	
	
	$getDeactivateUserModalContentPromise.done(function(data){
		$vendorUsersModal.html(data);
		ModalUtil.openModal($vendorUsersModal);
	});
}

function getVendorUserTableContents(userSearchForm) {
	parent.showLoading();	
	$getVendorUserTableContents = $.ajax( {
	    type: 'GET',
	    url: 'get-vendor-user-table-contents', 
	    data: userSearchForm,
		global: false,
		beforeSend: function() {
	      LoadingUtil.showLoadingOverlay(true);
	    }
	  });
	
	$getVendorUserTableContents.done(function(data){
		
		$vendorUsersDataTable.clear();
    	$vendorUsersDataTable.rows.add(data);
    	$vendorUsersDataTable.draw();
		LoadingUtil.hideLoadingOverlay();
		parent.hideLoading();	
	});
	
}
//# sourceURL=vendor-users.js