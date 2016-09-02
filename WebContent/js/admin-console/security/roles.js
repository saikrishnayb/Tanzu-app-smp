$(document).ready(function() {
	/* ------------ Initialize Page ------------ */
	selectCurrentNavigation("tab-security", "left-nav-roles");
	
	sortBaseRoles($('select[name="baseRoleId"]'));
	setAdvancedFormStatus();
	
	var $advancedForm = $('#advanced-search-form');
	
	/* ----------- Datatable Declaration ----------- */
	var $roleTable = $('#roles-table');
	var iDisplayLength = 10;//tableRowLengthCalc();
	
	$roleTable.dataTable({ 						//All of the below are optional
				"aaSorting": [[ 1, "asc" ]], 	//default sort column
				"bPaginate": true, 				//enable pagination
				"bLengthChange": false, 		//enable change of records per page, not recommended
				"bStateSave": true,
				"bFilter": false, 				//Allows dynamic filtering of results, do not enable if using ajax for pagination
				"bSort": true, 					//Allow sorting by column header
				"bInfo": true, 					//Showing 1 to 10 of 11 entries
				"bAutoWidth": false,
				"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0 ]},
				                 {"sWidth": "70px", "aTargets": [ 0 ]}],
				"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
				"iDisplayLength": iDisplayLength , 			//number of records per page for pagination
				"oLanguage": {"sEmptyTable": "No roles were found."}, //Message displayed when no records are found
				"fnDrawCallback": function() { 	//This will hide the pagination menu if we only have 1 page.
											var paginateRow = $(this).parent().children('div.dataTables_paginate');
											var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);
											 
											if (pageCount > 1)  {
												paginateRow.css("display", "block");
											} else {
												paginateRow.css("display", "none");
											}
											
											//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
											var infoRow = $(this).parent().children('div.dataTables_info');
											
											//infoRow.css("display", "none");
											var rowCount = this.fnSettings().fnRecordsDisplay();
											if (rowCount > 0){
												infoRow.css("display", "block");
											} 
											else{
												infoRow.css("display", "none");
											}
										}
	});
	
	/* ----------- Modal Declarations ----------- */
	var $deactivateModal = $('#deactivate-modal');
	
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
	
	// Show the advanced search form if the user had just used it.
	if ($advancedForm.find('[name="roleName"]').val().length > 0 || $advancedForm.find('[name="baseRoleId"]').val().length > 0) {
		$('#advanced-search').trigger('click');
	}
	
	/* -------- Advanced Form Controls -------- */
	$('input[name="status-check"]').on('click', function() {
		setAdvancedFormStatus();
	});
	
	$('.search').on('click', function() {
		// Disable all inputs that are unused so they do not get passed to the controller.
		if ($advancedForm.find('input[name="roleName"]').val().length == 0) {
			$advancedForm.find('input[name="roleName"]').prop('disabled', true);
		}
		
		if ($advancedForm.find('select[name="baseRoleId"]').val().length == 0) {
			$advancedForm.find('select[name="baseRoleId"]').prop('disabled', true);
		}
		
		$advancedForm.find('input[type="checkbox"]').prop('disabled', true);
		
		$advancedForm.submit();
	});
	
	$('.reset').on('click', function() {
		$advancedForm.find('input[name="roleName"]').val('');
		$advancedForm.find('select[name="baseRoleId"]').val('');
		$advancedForm.find('input[name="statusCheck"]').attr('checked', false);
		$advancedForm.find('input[name="status"]').val('0');
	});
	
	/* ------------ Table Controls ------------ */
	$roleTable.on('click', '.edit-role', function() {
		var roleId = $(this).closest('td').find('input[name="roleId"]').val();
		
		location.assign('./modify-role.htm?roleId=' + roleId+'&editOrCopy=E');
	});
	
	$roleTable.on('click', '.copy-role', function() {
		var roleId = $(this).closest('td').find('input[name="roleId"]').val();
		
		location.assign('./modify-role.htm?roleId=' + roleId+'&editOrCopy=C');
	});
	
	$roleTable.on('click', '.deactivate-role', function() {
		var roleId = $(this).closest('td').find('input[name="roleId"]').val();
		//var status = $(this).closest('tr').find('input[name="status"]').val();
		//status-- removed from the role display table as per new change
		//if (status == 'A') {
			$.get('./deactivate-role.htm',
					{'roleId': roleId},
					function(data) {
						$deactivateModal.html(data);
						$deactivateModal.dialog('open');
					});
		//}else{
			
		//}
	});
});

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
	   $('#advanced-search-form').find('[name="roleName"]').focus();
	   $("#" + spanId).text('Hide Search Criteria');
	}
}

function sortBaseRoles($dropdown) {
	var $options = $dropdown.find('option:nth-child(n+3)');
	
	$options.sort(function (a, b) {
	    return a.text.toUpperCase() == b.text.toUpperCase() ? 0 : a.text.toUpperCase() < b.text.toUpperCase() ? -1 : 1;
	});
	
	$options.remove();
	
	$dropdown.append($options);
}

function setAdvancedFormStatus() {
	var checkedVal = $('input[name="status-check"]').is(':checked') ? 'A' : '';
	
	$('#advanced-search-form input[name="status"]').val(checkedVal);
}