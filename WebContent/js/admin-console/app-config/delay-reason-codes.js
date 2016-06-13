$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-reason-codes");
	
	var $delayReasonTable = $('#delay-reason-table');
	initializeDataTable($delayReasonTable);
	var $deleteDelayReasonModal = $('#delete-delay-reason-modal');
	var $editDelayReasonModal = $('#edit-delay-reason-modal');
	var $addDelayReasonModal = $('#add-delay-reason-modal');
	var $customErrorModal = $('#customized-ajax-error');
	var commonStaticUrl = $('#common-url').val();
	
	$addDelayReasonModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight: 150,
		resizable: false,
		title: 'Add Delay Reason',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$deleteDelayReasonModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 335,
		minHeight: 175,
		resizable: false,
		title: 'Delete Delay Reason',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$editDelayReasonModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight: 150,
		resizable: false,
		title: 'Edit Delay Reason',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$delayReasonTable.on("click", ".delete-button", function(){
		
		var $row = $(this).closest("tr");
		var typeId = $row.find('.type-id').val();
		var delayId = $row.find('.association-id').val();
		var delayReason = $row.find('.delay-reason').text();
		displayFlag=false;
		var $getDeleteDelayReasonModalPromise = $.get("get-delete-delay-reason-modal-page.htm", {typeId:typeId, reasonId:delayId, delayReason:delayReason});
		$getDeleteDelayReasonModalPromise.done( function(data){
			
			$deleteDelayReasonModal.html(data);
			openModal($deleteDelayReasonModal);
		});
	});
	
	$deleteDelayReasonModal.on("click", ".delete-delay", function(){
		
		// get id value in hidden input field
		var reasonId = $('.delay-reason-id').val();
		//var typeId = $('.delay-type-id').val();
		var intId = parseInt(reasonId);
		displayFlag=false;
		// access controller and delete delay type from database
		var $getDeleteDelayReasonPromise = $.get("delete-delay-reason.htm", {reasonId:intId});
		$getDeleteDelayReasonPromise.done( function(){
			
			$delayReasonTable.find('.association-id').each(function(){
				var $this = $(this);
				var delayReasonIdMatches = $this.val() == intId;
				if(delayReasonIdMatches){
					
					var $row = $this.closest("tr");
					$delayReasonTable.fnDeleteRow( $row[0] );
				}
			});
			$deleteDelayReasonModal.dialog('close').empty();
		});
	});
	$delayReasonTable.on("click", ".edit-button", function(){
		
		var $row = $(this).closest("tr");
		var typeId = $row.find('.type-id').val();
		var delayId = $row.find('.association-id').val();
		var delayReason = $row.find('.delay-reason').text();
		displayFlag=false;
		var $getEditDelayReasonModalPromise = $.get("get-edit-delay-reason-modal-page.htm", {reasonId:delayId, delayReason:delayReason});
		$getEditDelayReasonModalPromise.done( function(data){
				
			$editDelayReasonModal.html(data);
			openModal($editDelayReasonModal);
		});
	});
	
	
	$editDelayReasonModal.on("click", ".edit-delay-reason", function(){
		
		var reasonId = $('#hidden-reason-id').val();
		var oldReasonName = $('#hidden-reason-name').val();
		var newReasonName = $('#edit-delay-reason-name').val();
		var $form = $('#edit-modal-form');
		var $errorDiv = $('#error-message');
		
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($form);
		
		if(validated){
		
			var $getEditDelayReasonPromise = $.ajax({
												url: "edit-delay-reason.htm", 
												data: {reasonId:reasonId, oldReasonName:oldReasonName, newReasonName:newReasonName},
												global: false});
			
			$getEditDelayReasonPromise.done( function(){
				
				$delayReasonTable.find('.association-id').each(function(){
					
					var $this = $(this);
					var intId = parseInt(reasonId);
					var delayReasonIdMatches = $this.val() == intId;
					if(delayReasonIdMatches){
						
						var $row = $this.closest("tr");
						$delayReasonTable.fnUpdate( newReasonName, $row[0], 1 );
						$row.find("td:first-child").find(".association-id").val(reasonId);
					}
				});
				$editDelayReasonModal.dialog('close').empty();
				
			}).fail( function(jqXHR, ajaxOptions, thrownError){
				$ajaxErrorFlg=false;
			     if(jqXHR.responseText.indexOf('Delay Reason with this name already exists.')>0){
			    	var $error= $editDelayReasonModal.find("#error-message"); 
			    	$error.removeClass('hidden');
			    	$error.find(".errorMsg").html('Delay Reason with this name already exists.');
				  }
			});
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $('#delay-type-of-reason').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Delay Type");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#edit-delay-reason-name').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Field cannot be left blank");
				$errorDiv.removeClass('hidden');
			}
		}
	});
	
	$('.add-delay-reason').on("click", function(){
		displayFlag=false;
		var $getAddDelayReasonModalPromise = $.get("get-add-delay-reason-modal-page.htm");
		$getAddDelayReasonModalPromise.done( function(data){
			
			$addDelayReasonModal.html(data);
			openModal($addDelayReasonModal);
		});
	});
	
	$addDelayReasonModal.on("click", ".add-delay-reason-save", function(){
		
		var $addDelayReasonForm = $('#add-delay-reason-form');
		var $errorDiv = $('#error-message');
		
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($addDelayReasonForm);
		
		if(validated){
			
			var addDelayReasonFormRequestMapping = $addDelayReasonForm.attr('action');
			displayFlag=false;
			var $addDelayReasonPromise = $.post( addDelayReasonFormRequestMapping, $addDelayReasonForm.serialize() );
			$addDelayReasonPromise.done( function(data){
				
				var reasonObj = data;
				var reasonId = reasonObj.reasonId;
				var typeId = reasonObj.typeId;
				var firstColumn = "<a class='rightMargin edit-button'>Edit</a>" +
						"<a><img src='" + commonStaticUrl + "/images/delete.png' class='centerImage rightMargin delete-button'/></a>" +
								"<input type='hidden' class='association-id' value='" + reasonId + "'/>";
				var rowIndex = $delayReasonTable.fnAddData( [ firstColumn, $('#add-delay-reason-name').val() ]);
				//var $newRow = $( $delayReasonTable.fnGetNodes(rowIndex[0]) );
				//$newRow.find("td:first-child").addClass("editable centerAlign width");
				//$newRow.find("td:nth-child(1)").addClass("delay-type");
				var nTr  = $delayReasonTable.fnSettings().aoData[ rowIndex[0] ].nTr;
				//var $column = $($newRow).find('td:first').addClass("editable centerAlign width");
				$('td', nTr)[0].setAttribute( 'class', 'editable centerAlign width' );
				$('td', nTr)[1].setAttribute( 'class', 'delay-reason' );
				
				
				$addDelayReasonModal.dialog('close').empty();
			});
			$addDelayReasonPromise.fail(function (jqXHR, ajaxOptions, thrownError) {
				$ajaxErrorFlg=false;
			     if(jqXHR.responseText.indexOf('Error in processing the last request.')>0){
			    	var $error= $addDelayReasonModal.find("#error-message"); 
			    	$error.removeClass('hidden');
			    	$error.find(".errorMsg").html('Delay Reason with this name already exists.');
				  }
			});
			
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $('#delay-type-of-reason').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Delay Type");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#add-delay-reason-name').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Field cannot be left blank");
				$errorDiv.removeClass('hidden');
			}
		}
	});
});

// data table initialization
function initializeDataTable($delayReasonTable){
	
	$delayReasonTable.dataTable({ //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": 10 , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
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