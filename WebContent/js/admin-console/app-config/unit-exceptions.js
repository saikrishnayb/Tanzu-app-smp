$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-unit-exceptions");
	
	var $exceptionTable = $('#exceptionTable');
	initializeTable($exceptionTable);
	var $editExceptionModal = $("#modal-edit-unit-exception");
	var $deleteExceptionModal = $("#modal-delete-unit-exception");
	
	$editExceptionModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 235,
		resizable: false,
		title: 'Resolve Data Responsibility Conflicts',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$deleteExceptionModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight: 235,
		resizable: false,
		title: 'Delete Unit Exception',
		closeOnEscape: false,
		open: function(event, ui) { }
	});

	$exceptionTable.on("click", ".delete-exception", function(){
		
		var $this = $(this);
		var exceptionId = $this.closest("tr").find(".unit-exception-id").val();
		
		var $deleteUnitExceptionModalPromise = $.get("get-delete-unit-exception-modal.htm", {exceptionId:exceptionId});
		$deleteUnitExceptionModalPromise.done( function(data){
			
			$deleteExceptionModal.html(data);
			openModal($deleteExceptionModal);
		});
	});
	$deleteExceptionModal.on("click", ".delete-unit-exception-confirm", function(){
		
		var exceptionId = $("#exception-id-modal").val();
		
		var $deleteExceptionPromise = $.get( "delete-unit-exception.htm", {exceptionId:exceptionId});
		$deleteExceptionPromise.done( function(){
			
			$exceptionTable.find('.unit-exception-id').each(function(){
				
				var intId = parseInt(exceptionId);
				var $this = $(this);
				var exceptionIdMatches = $this.val() == intId;
				if(exceptionIdMatches){
					
					var $row = $this.closest("tr");
					$exceptionTable.fnDeleteRow( $row[0] );
				}
			});
			$deleteExceptionModal.dialog('close').empty();
		});
	});
	
	$exceptionTable.on("click", ".edit-exception", function(){
		
		var $thisRow = $(this).closest("tr");
		var exceptionId = $thisRow.find(".unit-exception-id").val();
		var $editUnitExceptionModalPromise = $.get("get-unit-exceptions-edit-modal.htm", {exceptionId:exceptionId});
		$editUnitExceptionModalPromise.done( function(data){
			
			$editExceptionModal.html(data);
			openModal($editExceptionModal);
		});
	});
	$editExceptionModal.on("change", "#provided-by-list", function(){
		
		var $dropdown = $('#provided-by-list');
		var $subDropdown = $('#provided-by-sub');
		var selectedOption = $dropdown.val();
		var $getSubGroupPromise = $.get("get-sub-groups", {selectedOption:selectedOption});
		$getSubGroupPromise.done( function(data){
			
			$subDropdown.children().remove();
			var list = data;
			$subDropdown.append("<option value=''></option>");
			for(var i = 0; i < list.length; i++){
				
				$subDropdown.append("<option value='" + list[i] + "'>" + list[i] + "</option>");
			}
		});
	});
	$editExceptionModal.on("click", ".edit-unit-exception-confirm", function(){
		
		var provider = $('#provided-by-list').val();
		var subProvider = $('#provided-by-sub').val();
		var exceptionId = $('#exception-id-modal').val();
		var $checkbox = $("#unit-checkbox");
		var globalFlag = false;
		var $unitForm = $('#edit-unit-exception-form');
		var $errorDiv = $('#error-message');
		
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($unitForm);
		
		if(validated){
			

			if($checkbox.is(":checked")){
				
				globalFlag = true;
			}
			var $getEditUnitExceptionPromise = $.get("edit-unit-exception", {exceptionId:exceptionId, provider:provider, subProvider:subProvider, globalFlag:globalFlag});
			
			$getEditUnitExceptionPromise.done( function(){
				
				$exceptionTable.find('.unit-exception-id').each(function(){
					
					var $this = $(this);
					var intId = parseInt(exceptionId);
					var exceptionIdMatches = $this.val() == intId;
					if(exceptionIdMatches){
						
						var $row = $this.closest("tr");
						
						if(globalFlag){
							
							// delete the row if globalFlag is true, because we're deleting this
							// Unit Exception and creating a Global Exception
							$exceptionTable.fnDeleteRow( $row[0] );
						}
						else{
							
							// if globalFlag is false, then just update the Unit Exception row with
							// the new provider
							$exceptionTable.fnUpdate( provider, $row[0], 3 );
						}
					}
				});
				$editExceptionModal.dialog('close').empty();
			});
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $('#provided-by-list').hasClass('errorMsgInput') ){
				
				$errorSpan.text("To be Provided By cannot be blank");
				$errorDiv.removeClass('hidden');
			}
		}
	});
});

function initializeTable($exceptionTable){
	
	$exceptionTable.dataTable({ //All of the below are optional
		"aaSorting": [[ 4, "desc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": false, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0 ]}],
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
									
									infoRow.css("display", "none");
								}
	});
}