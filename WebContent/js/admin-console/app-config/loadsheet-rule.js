/**
 * 
 */

$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-rules");
	var $loadsheetRuleTable = $('#loadsheet-rule-table');
	initializeRuleTable($loadsheetRuleTable);
	
	$('#loadsheet-rule-table tbody tr').on( 'click', '#deleteRule', function () {
		
		
		var $this = $(this);
		var ruleId=$this.closest('tr').find('#ruleId').val();
		
		
		$.ajax({
			  type: "POST",
			  url: "./delete-rule.htm",
			  data: {ruleId : ruleId},
			  success: function(data){
				  
				 var $row = $this.closest("tr");
				$loadsheetRuleTable.dataTable().fnDeleteRow($row[0]);
			  }
			});
		
		
		
		
		
	});
	
});

function initializeRuleTable($loadsheetRuleTable){
	
	$loadsheetRuleTable.dataTable({ //All of the below are optional
		"aaSorting": [[ 3, "desc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [0] } ],//disable sorting for specific column indexes
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


