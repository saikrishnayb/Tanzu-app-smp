$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-global-exceptions");
	
	var $exceptionTable = $('#exceptionTable');
	initDataTable($exceptionTable);
	var $editExceptionModal = $("#modal-edit-global-exception");
	var $deleteExceptionModal = $("#modal-delete-global-exception");

	$editExceptionModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 235,
		resizable: false,
		title: 'Edit Global Exception',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$deleteExceptionModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 350,
		minHeight: 235,
		resizable: false,
		title: 'Delete Global Exception',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$exceptionTable.on("click", ".edit-exception", function(){
		
		var $thisRow = $(this).closest("tr");
		var exceptionId = $thisRow.find(".global-exception-id").val();
		var $editGlobalExceptionModalPromise = $.get("get-global-exceptions-edit-modal.htm", {exceptionId:exceptionId});
		$editGlobalExceptionModalPromise.done( function(data){
			
			$editExceptionModal.html(data);
			openModal($editExceptionModal);
		});
	});
	$exceptionTable.on("click", ".delete-exception", function(){
		
		var $thisRow = $(this).closest("tr");
		var exceptionId = $thisRow.find(".global-exception-id").val();
		var $deleteGlobalExceptionModalPromise = $.get("get-global-exceptions-delete-modal.htm", {exceptionId:exceptionId});
		$deleteGlobalExceptionModalPromise.done( function(data){
			
			$deleteExceptionModal.html(data);
			openModal($deleteExceptionModal);
		});
	});
	$editExceptionModal.on("change", "#provided-by-list", function(){
		
		var $dropdown = $('#provided-by-list');
		var $subDropdown = $('#provided-by-sub');
		var selectedOption = $dropdown.val();
		var $getSubGroupPromise = $.get("get-sub-groups.htm", {selectedOption:selectedOption});
		$getSubGroupPromise.done( function(data){
			
			$subDropdown.children().remove();
			var list = data;
			$subDropdown.append("<option value='default'></option>");
			for(var i = 0; i < list.length; i++){
				
				$subDropdown.append("<option value='" + list[i] + "'>" + list[i] + "</option>");
			}
		});
	});
	$editExceptionModal.on("click", ".edit-global-exception-confirm", function(){
		
		var provider = $('#provided-by-list').val();
		var subProvider = $('#provided-by-sub').val();
		var exceptionId = $('#exception-id-modal').val();
		var $globalForm = $('#edit-global-exception-form');
		var $errorDiv = $('#error-message');
		
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($globalForm);
		
		if(validated){
			
			var $getEditGlobalExceptionPromise = $.get("edit-global-exception.htm", {exceptionId:exceptionId, provider:provider, subProvider:subProvider});
			$getEditGlobalExceptionPromise.done( function(){
				
				$exceptionTable.find('.global-exception-id').each(function(){
					
					var $this = $(this);
					var intId = parseInt(exceptionId);
					var exceptionIdMatches = $this.val() == intId;
					if(exceptionIdMatches){
						
						var $row = $this.closest("tr");
						$exceptionTable.fnUpdate( provider, $row[0], 3 );
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
	$deleteExceptionModal.on("click", ".delete-global-exception-confirm", function(){
		
		var exceptionId = $('#exception-id-modal').val();
		var $deleteExceptionPromise = $.get("delete-global-exception.htm", {exceptionId:exceptionId});
		$deleteExceptionPromise.done( function(){
			
			$exceptionTable.find('.global-exception-id').each(function(){
				
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
});

function initDataTable($exceptionTable){
	
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