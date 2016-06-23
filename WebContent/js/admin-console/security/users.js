var $searchFName = $('#search-first-name');
var $searchLName = $('#search-last-name');
var $searchRole = $('#search-role');
var $searchEmail = $('#search-email');
var $searchuserType = $('#search-user-type');

$(document).ready(function() {
	var $tabNavUser=$('#tabNavUser').val();
	selectCurrentNavigation("tab-security",$tabNavUser);
	
	//cache selector
	var $usersTable = $('#users-table');
	var $confirmAccountDeactivationModal = $('#deactivate-modal');
	var $editModal = $('#edit-modal');
	var $searchButtonsContainer = $('#search-buttons-div');
	
	//edit user modal
	$editModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 1198,
		//top:-28,
		my: "center",
		at: "center",
		of: window,
		height: "auto",
		resizable: false,
		closeOnEscape: false
	});
	// $(".ui-dialog-titlebar").hide();
	  //  $(".ui-dialog").addClass("custom_dialog_style");
	//account deactivation modal
	$confirmAccountDeactivationModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});

	var iDisplayLength = 10;//tableRowLengthCalc();
	
	//user summary table
	$usersTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bAutoWidth": false, //cray cray
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"aoColumnDefs": [
		                 {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] }
		                 ],
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": iDisplayLength , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "No Results Found"}, //Message displayed when no records are found
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
	
	//---------------------------------------Listeners----------------------------------------
	//reset search fields
	$searchButtonsContainer.on('click', '.reset', function(){
		$searchEmail.val('');
		$searchFName.val('');
		$searchLName.val('');
		$searchRole.val('');
		$searchuserType.children().removeAttr("selected");
		$searchuserType.find('.default-option').attr('selected', 'selected');
		
		$searchEmail.removeClass('errorMsgInput');
		$searchFName.removeClass('errorMsgInput');
		$searchLName.removeClass('errorMsgInput');
		$searchRole.removeClass('errorMsgInput');;
		$searchuserType.removeClass('errorMsgInput');
		$('.error-messages-container').addClass('displayNone');
	});
	
	//search for user accounts
	$searchButtonsContainer.on('click', '.search', function(){
		var  $searchForm = $('#search-user-form');
		$searchForm.submit();
	});
	
	//search for vendor user accounts
	$searchButtonsContainer.on('click', '.vendorSearch', function(){
		var  $searchForm = $('#search-vendor-user-form');
		$searchForm.submit();
	});
	
	if($("#search-content").is(":visible")){
		if($("#advancedSearch").is(":visible")){
			//Currently Expanded
			$("#advancedSearch").text('Hide Search Criteria');
		}
	}
	else{
		if($("#advancedSearch").is(":hidden")){
			//Currently Collapsed
			$("#advancedSearch").text('Show Search Criteria');
		}
	}
	
	//penske user deactivate modal
	$usersTable.on("click", ".deactivate", function(){
		/*
		var $this =  $(this);
		var email = $this.closest('.user-row').find('.user-email').text();
		var userId = $this.closest('.user-row').find('.user-id').val();
		var $getDeactivateUserModalContentPromise = $.get('get-deactivate-user-modal-content.htm', {email:email, userId:userId});
		
		
		$getDeactivateUserModalContentPromise.done(function(data){
			$confirmAccountDeactivationModal.html(data);
			openModal($confirmAccountDeactivationModal);
			
		});
		
		openModal($confirmAccountDeactivationModal);
		*/
		var $this =  $(this);
		var $isVendorUser=false;
		deactivteUser($this,$isVendorUser,$confirmAccountDeactivationModal);
	});
	
	//vendor user deactivate modal
	$usersTable.on("click", ".deactivate-vendor-user", function(){
		var $this =  $(this);
		var $isVendorUser=true;
		deactivteUser($this,$isVendorUser,$confirmAccountDeactivationModal);
	});
	
	//edit modal
	$usersTable.on("click", ".edit-user", function(){
		
		var $this =  $(this);
		var userId = $this.closest('.user-row').find('.user-id').val();
		var userType = $this.closest('.user-row').find('.user-type').text();
		var roleId = $this.closest('.user-row').find('.role-id').val();
		var $getEditUserModalContentPromise = $.get('get-edit-user-modal-content.htm', {userId:userId, userType:userType, roleId:roleId});
		
		$getEditUserModalContentPromise.done(function(data){
			$editModal.html(data);
			openModal($editModal);
			//   $(".ui-dialog").addClass("custom_dialog_style");
			
		});
	});
	
	//deactivate execution
	$confirmAccountDeactivationModal.on("click", ".deactivate-confirm", function(){
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
			closeModal($confirmAccountDeactivationModal);
		});
	});
	
	//edit modal
	$usersTable.on("click", ".edit-vendor-user", function(){
		
		var $this =  $(this);
		var userId = $this.closest('.user-row').find('.user-id').val();
		var userType = $this.closest('.user-row').find('.user-type').text();
		var roleId = $this.closest('.user-row').find('.role-id').val();
		var $getEditUserModalContentPromise = $.get('get-edit-vendor-user-modal-content.htm', {userId:userId, userType:userType, roleId:roleId});
		
		$getEditUserModalContentPromise.done(function(data){
			$editModal.html(data);
			openModal($editModal);
			$editModal.find("#first-name").focus();
			//   $(".ui-dialog").addClass("custom_dialog_style");
			
		});
	});
});

var $confirmModal = $('#deactivate-confirm');
var $editModal = $('#edit-modal');

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

function deactivteUser($this,$isVendorUser,$confirmAccountDeactivationModal){
	var email = $this.closest('.user-row').find('.user-email').text();
	var userId = $this.closest('.user-row').find('.user-id').val();
	var $getDeactivateUserModalContentPromise = $.get('get-deactivate-user-modal-content.htm', {email:email, userId:userId
		, isVendorUser:$isVendorUser});
	
	
	$getDeactivateUserModalContentPromise.done(function(data){
		$confirmAccountDeactivationModal.html(data);
		openModal($confirmAccountDeactivationModal);
		
	});
	
	openModal($confirmAccountDeactivationModal);
}
//# sourceURL=users.js