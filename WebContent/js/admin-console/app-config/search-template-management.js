$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-search-templates");
	
	var $editModal = $('#edit-template');
	
	//Initializes data table
	$('#templateTable').dataTable({ //All of the below are optional
		"aoColumnDefs": [
		                 { 'sWidth':"100px", "aTargets":[0]},
		                 { 'bSortable': false, 'aTargets':[0]}
		                ],
		"bAutoWidth": false,
		"aaSorting": [[ 1, "desc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": false, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": 10 , //number of records per page for pagination
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
	});
	
	//Initializes the edit search template modal
	$('#edit-template').dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 700,
		minHeight: 250,
		resizable: false,
		title: 'Template Management',
		closeOnEscape: false,
		position: { my: "center top", at: "center top"}
	});
	
	//Opens the edit search template modal when a user clicks "Edit"
	$('#templateTable').on('click', '.edit-template', function() {
		var $this = $(this);
		var templateId = $this.closest('.template-row').find('.template-id').val();
		
		var $getTemplateModalContentPromise = $.get("get-search-template-modal-content.htm", {templateId:templateId});
		$getTemplateModalContentPromise.done(function(data) {
			$editModal.html(data);
			openModal($editModal);
		});
	});
});