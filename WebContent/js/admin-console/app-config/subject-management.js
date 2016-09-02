var $subjectTable = $('#subject-table');
var $editSubjectModal = $('#edit-subject-modal');
var $addSubjectModal = $('#add-subject-modal');

$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-subject-management");
	
	/* ----------- Datatable Declaration ----------- */
	var iDisplayLength = 10;//tableRowLengthCalc();
	
	$subjectTable.dataTable({ 					//All of the below are optional
				"aaSorting": [[ 1, "asc" ]], 	//default sort column
				"bPaginate": true, 				//enable pagination
				"bStateSave": true,
				"bLengthChange": false, 		//enable change of records per page, not recommended
				"bFilter": false, 				//Allows dynamic filtering of results, do not enable if using ajax for pagination
				"bSort": true, 					//Allow sorting by column header
				"bInfo": true, 					//Showing 1 to 10 of 11 entries
				"bAutoWidth": false,
				"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0 ]},
				                 {"sWidth": "70px", "aTargets": [ 0 ]}],
				"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
				"iDisplayLength": iDisplayLength , 			//number of records per page for pagination
				"oLanguage": {"sEmptyTable": "No subjects were found."}, //Message displayed when no records are found
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
	$editSubjectModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 450,
		minHeight: 200,
		resizable: false,
		title: 'Edit Subject',
		closeOnEscape: false
	});
	
	$addSubjectModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 450,
		minHeight: 200,
		resizable: false,
		title: 'Add Subject',
		closeOnEscape: false
	});
	
	/* ----------- Editing A Subject ----------- */
	//$('.edit-subject').on('click', function() {
	$subjectTable.on("click", ".edit-subject", function(){
		var $row = $(this).closest('tr');

		// Populate the modal with the values of the subject selected by the user from the table.
		$editSubjectModal.find('[name="subjectId"]').val($row.find('[name="subjectId"]').val());
		$editSubjectModal.find('[name="subjectName"]').val($row.find('#subject-name').text());
		$editSubjectModal.find('[name="department"]').val($row.find('[name="department"]').val());
		$editSubjectModal.find('[name="type"]').val($row.find('[name="type"]').val());
		
		// Hide any error messages.
		$editSubjectModal.find('.error').hide();
		$('.errorMsgInput').removeClass('errorMsgInput');
		
		$editSubjectModal.dialog('open');
	});
	
	/* ------------ Adding A Subject ------------ */
	$('.add-subject').on('click', function() {
		// Reset the values in the modal.
		$addSubjectModal.find('[name="subjectName"]').val('');
		$addSubjectModal.find('[name="department"]').val('');
		$addSubjectModal.find('[name="type"]').val('');
		
		// Hide any error messages.
		$addSubjectModal.find('.error').hide();
		$('.errorMsgInput').removeClass('errorMsgInput');
		
		$addSubjectModal.dialog('open');
	});
	
	/* ---------- Deleting A Subject ---------- */
//	$('.delete-subject').on('click', function() {
	$subjectTable.on("click", ".delete-subject", function(){	
		var $row = $(this).closest('tr');
		var subjectId = $row.find('[name="subjectId"]').val();

		// Deactivate the subject in the database.
		$.post('./modify-subject-status.htm', 
				{'subjectId': subjectId}, 
				function(data) {
					var nRow = $row[0];
					
					// Remove the row from the datatable.
					$subjectTable.dataTable().fnDeleteRow(nRow);
				});
	});
});