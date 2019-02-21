/**
 * 
 */
var $selectedRow;
var $loadsheetRuleTable;
$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-rules");
	$loadsheetRuleTable = $('#loadsheet-rule-table');
	initializeRuleTable($loadsheetRuleTable);
	
	$('#confirmDeleteModal').dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		resizable: false,
		title: 'Confirm',
		closeOnEscape: false,
		open: function(event, ui) {$(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();}
	});
	
	
	$('#loadsheet-rule-table').on( 'click', '#deleteRule', function () {
		
		$selectedRow = $(this);
		var ruleName=$selectedRow.closest('tr').find('#ruleName').val();
		var timesUsed=$selectedRow.closest('tr').find('#timesUsed').val();
		openConfirmModal(ruleName,timesUsed);
		
	});
	
});

function initializeRuleTable($loadsheetRuleTable){
	
	$loadsheetRuleTable.dataTable({ //All of the below are optional
		"aaSorting"			: [[ 3, "desc" ]], //default sort column
		"bPaginate"			: true, //enable pagination
		"bStateSave"		: true,	//To retrieve the data on click of back button
		"bLengthChange"		: true, //enable change of records per page, not recommended
		"bFilter"			: true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort"				: true, //Allow sorting by column header
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [0] }, 
		                    {"bSearchable": false, "aTargets": [0]} ],//disable sorting for specific column indexes
		"bInfo"				: true, //Showing 1 to 10 of 11 entries
		"sPaginationType"	: "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength"	: -1 , //number of records per page for pagination
		"aLengthMenu"		: [[10, 25, 50,100,-1], [10, 25, 50,100,"All"]],//number of records per page for pagination
		"oLanguage"			: {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		"search"			: {
			type: "text",
			bRegex: true,
			bSmart: true
			 },
		"fnDrawCallback"	: function() { //This will hide the pagination menu if we only have 1 page.
			
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
		},
		"fnStateSave": function (oSettings, oData) {
            localStorage.setItem( 'loadSheetRuleTable', JSON.stringify(oData) );
        },
        "fnStateLoad": function (oSettings) {
            return JSON.parse( localStorage.getItem('loadSheetRuleTable') );
        }
});
	
	//To resize iframe on datatable search
	$("div.dataTables_filter input").keyup( function (e) {
		parent.resizeAfterPaginationChange();
	} );	

	
	//To resize iframe on change of page size and on click of page numbers
	$('select[name=loadsheet-rule-table_length]').change(function(){
		parent.resizeAfterPaginationChange();
	
	});
	$(document).on("click", "a.paginate_button", function () {
		parent.resizeAfterPaginationChange();
	});
}

function openConfirmModal(ruleName,timesUsed){
	$('#confirmDeleteModal').dialog('open');
	if(timesUsed > 0){
		$('#deleteMessage').text("Rule '"+ruleName+"' is used "+timesUsed+" time(s), Do you really want to delete ?");
	}else{
		$('#deleteMessage').text("Do you really want to delete the Rule: "+ruleName +" ?");
	}
}

function confirmDeleteRule(){
	
	
	var ruleId=$selectedRow.closest('tr').find('#ruleId').val();
	
	$.ajax({
		  type: "POST",
		  url: "./delete-rule.htm",
		  data: {ruleId : ruleId},
		  success: function(data){
			  
			 var $row = $selectedRow.closest("tr");
			$loadsheetRuleTable.dataTable().fnDeleteRow($row[0]);
			closeConfirmDialog();
		  }
		});
	
}


function closeConfirmDialog(){
	$('#selectedRow').remove();
	$('#confirmDeleteModal').dialog('close');
}


