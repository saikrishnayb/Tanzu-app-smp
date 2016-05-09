$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-notifications");
	
	//Cache selectors for the table and edit notification modal
	var $editModal = $('#edit-notification');
	var $notificationTable = $('#notification-table');
	
	var iDisplayLength = tableRowLengthCalc();
	
	//Sets up the paginated table for the notifications
	$notificationTable.dataTable( { //All of the below are optional
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
		"iDisplayLength": iDisplayLength, //number of records per page for pagination
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
	
	//Prepares the edit notification modal
	$editModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 800,
		minHeight: 200,
		resizable: false,
		closeOnEscape: false,
		position: { my: "center top", at: "center top"}
	});
	
	//Listener for opening the edit notification modal when a user clicks "edit"
	$notificationTable.on("click", ".edit-notification", function() {
		var $this =  $(this);
		var notificationId = $this.closest('.notification-row').find('.notification-id').val();
		
		var $getNotificationModalContentPromise = $.get("get-notification-modal-content.htm", {notificationId:notificationId});
		$getNotificationModalContentPromise.done(function(data) {
			$editModal.html(data);
			openModal($editModal);
		});
	});
});
