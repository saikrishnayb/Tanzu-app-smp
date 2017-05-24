/**
 * 
 */

$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-management");
	var $loadsheetTable = $('#loadsheet-table');
	initializeloadSheetTable($loadsheetTable);
});

function initializeloadSheetTable($loadsheetTable){
	
	$loadsheetTable.dataTable({ //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [0] } ],//disable sorting for specific column indexes
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": 10 , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		"search": {
			type: "text",
			bRegex: true,
			bSmart: true
			 },
		"fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
			
	var paginateRow = $(this).parent().children('div.dataTables_paginate');
	var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

			if (pageCount > 1){
				paginateRow.css("display", "block");
			} 
			else{
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
}
