var $rulesTable = $('#rule-table');
var $rulesInactiveTable = $('#rule-inactive-table');
var $editRuleModal = $('#edit-rule-modal');
var $addRuleModal = $('#add-rule-modal');

$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-dynamic-rules");
	
	/* ---------- Table Declaration ----------- */	
	var iDisplayLength = 5;//tableRowLengthCalc();
	
	$rulesTable.dataTable({ 					//All of the below are optional
				"aaSorting": [[ 6, "desc" ]], 	//default sort column
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
				"oLanguage": {"sEmptyTable": "No rules were found."}, //Message displayed when no records are found
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
											
											infoRow.css("display", "none");
										}
	});
	
	
	$rulesInactiveTable.dataTable({ 					//All of the below are optional
		"aaSorting": [[ 6, "desc" ]], 	//default sort column
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
		"oLanguage": {"sEmptyTable": "No rules were found."}, //Message displayed when no records are found
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
									
									infoRow.css("display", "none");
								}
		});
	
	/* ----------- Modal Declarations ----------- */
	$editRuleModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 250,
		resizable: false,
		title: 'Edit Rule',
		closeOnEscape: false
	});
	
	$addRuleModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 250,
		resizable: false,
		title: 'Add Rule',
		closeOnEscape: false
	});
	
	/* ------------ Editing A Rule ------------ */
	$rulesTable.on('click', '.edit-rule', function() {
		editRule($(this).closest('tr'));
	});
	
	$rulesInactiveTable.on('click', '.edit-rule', function() {
		editRule($(this).closest('tr'));
	});
	
	var editRule=function(rowObj){
		var $row = rowObj;
		
		var make = $row.find('.manufacturer').text();
		
		$.post('./get-rule-modal-data',
				{'make': make, 'modalName': 'edit','status':$row.find('.status').text()},
				function(data) {
					$editRuleModal.html(data);
					
					// Populate the modal with the information from the user-selected row.
					$editRuleModal.find('[name="dynamicRuleId"]').val($row.find('[name="dynamicRuleId"]').val());
					$editRuleModal.find('[name="corpCode"]').val($row.find('.corp-code').text());
					$editRuleModal.find('[name="manufacturer"]').val($row.find('.manufacturer').text());
					$editRuleModal.find('[name="model"]').val($row.find('.model').text());
					var statusVal = $row.find('.status').text();
					//$editRuleModal.find('[name="modelYear"]').val($row.find('.model-year').text());
					var modelYear=$row.find('.model-year').text();
					if(modelYear !=null && modelYear !=='0'){
						$editRuleModal.find('[name="modelYear"]').val(modelYear);
					}
					if(statusVal != null){
						statusVal = statusVal.trim();
					if(statusVal == "Inactive"){
						statusVal = "I";
					}
					else{
						statusVal = "A";
						$editRuleModal.find('[name="priority"]').val($row.find('.priority').text());
					}
					}
					$editRuleModal.find('[name="status"]').val(statusVal);
					$editRuleModal.find('.error').hide();
					$('.errorMsgInput').removeClass('errorMsgInput');
					
					$editRuleModal.dialog('open');
				});
	};
	/* ------------- Adding A Rule -------------- */
	$('.add-rule').on('click', function() {
		$.post('./get-rule-modal-data',
				{'make': '', 'modalName': 'add','status':''},
				function(data) {
					$addRuleModal.html(data);
					
					$addRuleModal.find('.error').hide();
					$('.errorMsgInput').removeClass('errorMsgInput');
					
					$addRuleModal.dialog('open');
				});
	});
	
	/* ------------ Deleting A Rule ------------ */
	$rulesTable.on('click', '.delete-rule', function() {
		deleteRule($(this).closest('tr'));
	});
	
	$rulesInactiveTable.on('click', '.delete-rule', function() {
		deleteRule($(this).closest('tr'));
	});
	
	var deleteRule=function(rowObj){
		var $row = rowObj;
		var dynamicRuleId = $row.find('[name="dynamicRuleId"]').val();
		
		// Deactivate the rule in the database.
		$.post('./delete-dynamic-rule', 
				{'dynamicRuleId': dynamicRuleId}, 
				function(data) {
					// Reload the page, because deleting the rule may have affected other rules.
					location.assign('./dynamic-rules');
				}
			);
	};
	
});