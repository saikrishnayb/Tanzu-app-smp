var $searchFName = $('#search-first-name');
var $searchLName = $('#search-last-name');
var $searchRole = $('#search-role');
var $searchEmail = $('#search-email');
var $searchorgType = $('#search-org-type');

$(document).ready(function() {
	selectCurrentNavigation("tab-security", "left-nav-org");
	
	//cache selector
	var $orgTable = $('#org-table');
	var $confirmOrgDeactivationModal = $('#deactivate-modal');
	var $editModal = $('#edit-modal');
	var $searchButtonsContainer = $('#search-buttons-div');
	//edit org modal
	$editModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		height: "auto",
		width: 1198,
		top:-28,
		left:21,
		resizable: false,
		closeOnEscape: false
	});
	// $(".ui-dialog-titlebar").hide();
	    $(".ui-dialog").addClass("custom_dialog_style");
	//account deactivation modal
	$confirmOrgDeactivationModal.dialog({
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
	
	//org summary table
	$orgTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bAutoWidth": false, //cray cray
		"bStateSave": true,
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
		/*$searchEmail.val('');
		$searchFName.val('');
		$searchLName.val('');
		$searchRole.val('');
		$searchorgType.children().removeAttr("selected");
		$searchorgType.find('.default-option').attr('selected', 'selected');
		
		$searchEmail.removeClass('errorMsgInput');
		$searchFName.removeClass('errorMsgInput');
		$searchLName.removeClass('errorMsgInput');
		$searchRole.removeClass('errorMsgInput');;
		$searchorgType.removeClass('errorMsgInput');*/
		$('#orgName').val('');
		$('#search-org-form').find('select[name="parentOrgId"]').val('');
		$('.error-messages-container').addClass('displayNone');
	});
	
	
	$('#search-org-form').on('keypress', function(e) {
		var  $searchForm = $('#search-org-form');
		if(($searchForm.find('[name="orgName"]').val().length > 0) || ($searchForm.find('[name="parentOrgId"]').val().length > 0) || ($searchForm.find('[name="parentOrgId"]').val() != 'select')){
		if (e.which == 13) {
			$searchButtonsContainer.find('.search').trigger('click');
			event.preventDefault();
		}
		}
		});
	
	//search for org accounts
	$searchButtonsContainer.on('click', '.search', function(){
		search();
	});
	
	function search(){
		var  $searchForm = $('#search-org-form');
		var isValid = validateSearchForm($searchForm);
		
		if(isValid == true){
			$('#search-org-type').removeClass('disabled');
			$searchForm.submit();
		} else if(isValid == false){
			$('.error-messages-container').removeClass('displayNone');
		}
		
	}
	
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
	
	//deactivate modal
	$orgTable.on("click", ".deactivate", function(){
		
		var $this =  $(this);
		var orgName = $this.closest('.org-row').find('.org-name').text();
		var orgId = $this.closest('.org-row').find('.org-id').val();
		var $getDeactivateorgModalContentPromise = $.get('get-deactivate-org-modal-content.htm', {orgName:orgName,orgId:orgId});
		
		
		$getDeactivateorgModalContentPromise.done(function(data){
			$confirmOrgDeactivationModal.html(data);
			openModal($confirmOrgDeactivationModal);
			
		});
		
		openModal($confirmOrgDeactivationModal);
	});
	
	//edit modal
	$orgTable.on("click", ".edit-org", function(){
		var $this =  $(this);
		var orgId = $this.closest('.org-row').find('.org-id').val();
		location.assign('./modify-org.htm?orgId=' + orgId);
	});
	
	//deactivate execution
	$confirmOrgDeactivationModal.on("click", ".deactivate-confirm", function(){
		var orgId = $('#org-id').val();
		var $deactivateorgPromise = $.post('delete-org.htm', {orgId:orgId});
		
		$deactivateorgPromise.done(function(data){
			closeModal($confirmOrgDeactivationModal);
			$('.org-id').each(function(){
				/*var orgIdMatch = $(this).val();
				var isorgIdMatch = (orgIdMatch == orgId);
				if(isorgIdMatch){
					var $orgRow = $(this).closest('.org-row');
					var nRow = $orgRow[0];
					
					$('#org-table').dataTable().fnDeleteRow(nRow);
				}*/
				location.assign('./org.htm');
			});
		});
		$deactivateorgPromise.fail(function(xhr, ajaxOptions, thrownError) {
			 if(xhr.responseText.indexOf('Error in processing the last request.')>0){
				 closeModal($confirmOrgDeactivationModal);
			  }
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