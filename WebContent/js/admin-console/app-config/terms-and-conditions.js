$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-terms-and-conditions");
	
	//Cache selectors
	var $tandcTable = $('#t-and-c-table');
	var $startDates = $('.start-date');
	var $endDates = $('.end-date');
	var $saveFrequency = $('.save-frequency');
	var $viewModal = $('#tandc-view-modal');
	var $okButton = $('.okay');
	
	//Sets up the paginated table for terms and conditions
	$tandcTable.dataTable( { //All of the below are optional
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
		"iDisplayLength": 5, //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		"aoColumnDefs": [
		                 {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] }
		                 ],
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
	
	//Set up modal
	$viewModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 600,
		height: 620,
		resizable: false,
		closeOnEscape: false,
		position: { my: "center top", at: "center top"}
	});
	
	//Formats the dates in the table
	formatMillisToLocalizedTime($startDates);
	formatMillisToLocalizedTime($endDates);
	
	//Saves the frequency to which users should be prompted to confirm the T&C
	$saveFrequency.on("click", function() {
		var $frequency = $('#t-and-c-frequency');
		var frequency = $frequency.val();
		var $frequencyError = $('.frequency-error');
		
		if (frequency.length == 0 || isNaN(frequency) || frequency < 0) {
			$frequency.css({ 'background-color' : '#FFB591'});
			$frequencyError.css({ 'visibility' : 'visible' });
			return;
		}
		else {
			$frequency.css({ 'background-color' : '#FFFFFF'});
			$frequencyError.css({ 'visibility' : 'hidden' });
		}
		
		//AJAX call for updating the T_AND_C frequency setting
		$.ajax({
			type: "POST",
			url: 'update-t-and-c-frequency.htm',
			global: false,
			data: {frequencyDays:frequency},
			success: function() {
				var $success = $('.status-alerts').children('.alert-success').clone();
				var $status = $('.form-submit-status-container');
				
				$status.append($success);
				
				var $successAlert = $status.children('.alert-success');
				$successAlert.removeClass('.hidden');
				$successAlert.fadeIn(1000).delay(1000).fadeOut(1000, function() {
					$(this).remove();
				});
			},
			error: function() {
				var $failure = $('.status-alerts').children('.alert-danger').clone();
				var $status = $('.form-submit-status-container');
				
				$status.append($failure);
				
				var $failureAlert = $status.children('.alert-danger');
				$failureAlert.removeClass('.hidden');
				$failureAlert.fadeIn(1000).delay(1000).fadeOut(1000, function() {
					$(this).remove();
				});
			}
		});
	});
	
	
	//Opens view modal with that version's terms and conditions
	$tandcTable.on("click", ".view-t-and-c", function() {
		var $this = $(this);
		var versionNumber = $this.closest('.t-and-c-row').find('.tandc-version-number').val();
		
		var $getTermsAndConditionsPromise = $.get("view-t-and-c.htm", {versionNumber:versionNumber});
		$getTermsAndConditionsPromise.done(function(data) {
			//$viewModal.html(data);
			
			$tandcDiv = $viewModal.find('.tandc');
			$tandcDiv.empty();
			$tandcDiv.html(data);
			$tandcDiv.animate({scrollTop:0}, "fast");
			openModal($viewModal);
		});
	});
	
	//Global Listeners
	$("#close-modal-tc").on("click",  function(){
		var $this = $(this);
		var $closestModal = $this.closest(".modal");
		
		$closestModal.dialog('close');
	});
	
	
});