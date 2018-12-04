$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-delay-management");
	// cache selector
	var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
	var $addDelayModal = $('#add-delay-modal');
	var $editDelayModal = $('#edit-delay-modal');
	var $deleteDelayModal = $('#delete-delay-modal');
	var $delayTable = $('#delay-table');
	var $customErrorModal = $('#customized-ajax-error');
	// initializers
	initializeDelayTable($delayTable);

	// modals
	$addDelayModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 200,
		resizable: false,
		title: 'Add Delay',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$editDelayModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight:200,
		resizable: false,
		title: 'Edit Delay',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$deleteDelayModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight:200,
		resizable: false,
		title: 'Delete Delay',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	
	// ADD DELAY
	$('#add-new-delay').on("click", function(){
		displayFlag=false;
		var $getAddDelayModalContentPromise = $.get("get-add-delay-modal-content.htm");
		$getAddDelayModalContentPromise.done( function(data){
			
			$addDelayModal.html(data);
			$addDelayModal.dialog('open');
		});
	});
	$(".modal-delay").on("change", "#delay-type", function(){
		
		var $reasonSelect = $('#delay-reason');
		var typeId = $('#delay-type').val();
		displayFlag=false;
		var $getReasonsAssociatedWithTypePromise = $.get("get-associated-reasons.htm", {typeId:typeId});
		$getReasonsAssociatedWithTypePromise.done( function(data){
			
			$reasonSelect.children().remove();
			var reasonList = data;
			// populate Delay Reason dropdown with data
			$reasonSelect.append("<option value='default'></option>");
			if(reasonList !=null){
				for(var i = 0; i < reasonList.length; i++){
					if(reasonList[i] !=null){
							$reasonSelect.append("<option value='" + reasonList[i].reasonId + "'>" + reasonList[i].delayReason + "</option>");
					}
				}
			}
		});
	});
	$addDelayModal.on("click", ".save", function(){
		
		var $addDelayForm = $('#add-delay-form');
		var $errorDiv = $('#error-message');
		
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($addDelayForm);
		
		if(validated){
			
			var addDelayFormRequestMapping = $addDelayForm.attr('action');
			var $addDelayPromise = $.ajax({
									url: addDelayFormRequestMapping, 
									data: $addDelayForm.serialize(),
									global:false});
			
			$addDelayPromise.done( function(data){
				
				var delayId = data;
				var firstColumn = "<a class='rightMargin edit-button'>Edit</a>" +
				"<a><img src='" + commonStaticUrl + "/images/delete.png' class='centerImage rightMargin delete-button'/></a>" +
				"<input type='hidden' class='delay-id' value='" + delayId + "'/>";
				var addDataReturn = $delayTable.fnAddData( [ firstColumn, 
				                         $('#date-type option').filter(":selected").text(), 
				                         $('#po-category option').filter(":selected").text(), 
				                         $('#delay-type option').filter(":selected").text(), 
				                         $('#delay-reason option').filter(":selected").text() ],false);
				var nTr  = $delayTable.fnSettings().aoData[ addDataReturn[0] ].nTr;
				//var $column = $($newRow).find('td:first').addClass("editable centerAlign width");
				$('td', nTr)[0].setAttribute( 'class', 'editable centerAlign width' );
				$('td', nTr)[1].setAttribute( 'class', 'date-type' );
				$('td', nTr)[2].setAttribute( 'class', 'po-category' );
				$('td', nTr)[3].setAttribute( 'class', 'delay-type ' );
				$('td', nTr)[4].setAttribute( 'class', 'delay-reason' );
				$addDelayModal.dialog('close').empty();
				
			}).fail( function(jqXHR, ajaxOptions, thrownError){
				/*
				var responseText = (xhr.responseText);
				var ajaxError = JSON.parse(responseText);
				var errorDescription = ajaxError.errorDescription;
				$customErrorModal.find("p").html(errorDescription);
				openModal( $customErrorModal );*/
				$ajaxErrorFlg=false;
			     if(jqXHR.responseText.indexOf('Delay with these credentials already exists.')>0){
			    	var $error= $addDelayModal.find("#error-message"); 
			    	$error.removeClass('hidden');
			    	$error.find(".errorMsg").html('Delay with these credentials already exists.');
				  }
			});
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $('#date-type').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Date Type");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#po-category').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a PO Category");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#delay-type').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Delay Type");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#delay-reason').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Delay Reason");
				$errorDiv.removeClass('hidden');
			}
		}
	});
	
	
	// EDIT DELAY
	$delayTable.on("click", ".edit-button", function(){
		
		var $this = $(this);
		var delayId = $this.closest("tr").find('.delay-id').val();
		var dateType = $this.closest("tr").find('.date-type').text();
		var poCategory = $this.closest("tr").find('.po-category').text();
		var delayType = $this.closest("tr").find('.delay-type').text();
		var delayReason = $this.closest("tr").find('.delay-reason').text();
		displayFlag=false;
		var $getEditDelayModalContentPromise = $.get("get-edit-delay-modal-content.htm",
													{
														delayId:delayId,
														dateType:dateType,
														poCategory:poCategory,
														delayType:delayType,
														delayReason:delayReason
													});
		$getEditDelayModalContentPromise.done( function(data){
			
			$editDelayModal.html(data);
			$editDelayModal.dialog('open');
		});
	});
	$editDelayModal.on("click", ".save-edit", function(){
		
		var $editDelayForm = $("#edit-delay-form");
		var delayId = $("#delay-id").val();
		var dateType = $("#date-type option").filter(":selected").text();
		var poCategory = $("#po-category option").filter(":selected").text();
		var delayType = $("#delay-type option").filter(":selected").text();
		var delayReason = $("#delay-reason option").filter(":selected").text();
		var $errorDiv = $('#error-message');
		
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($editDelayForm);
		
		if(validated){
			
			var editDelayFormRequestMapping = $editDelayForm.attr('action');
			var $editDelayPromise = $.ajax({ 
										url: editDelayFormRequestMapping, 
										data: $editDelayForm.serialize(),
										global: false });
			
			$editDelayPromise.done( function(){
				
				$delayTable.find('.delay-id').each(function(){
					
					var $this = $(this);
					var intId = parseInt(delayId);
					var delayReasonIdMatches = $this.val() == intId;
					if(delayReasonIdMatches){
						
						var $row = $this.closest("tr");
						$delayTable.fnUpdate( dateType, $row[0], 1 , false);
						$delayTable.fnUpdate( poCategory, $row[0], 2  , false);
						$delayTable.fnUpdate( delayType, $row[0], 3 , false);
						$delayTable.fnUpdate( delayReason, $row[0], 4 , false);
					}
				});
				$editDelayModal.dialog('close').empty();
				
			}).fail( function(jqXHR, ajaxOptions, thrownError){
				/*
				var responseText = (xhr.responseText);
				var ajaxError = JSON.parse(responseText);
				var errorDescription = ajaxError.errorDescription;
				$customErrorModal.find("p").html(errorDescription);
				openModal( $customErrorModal );
				*/
				$ajaxErrorFlg=false;
			     if(jqXHR.responseText.indexOf('Delay with these credentials already exists.')>0){
			    	var $error= $editDelayModal.find("#error-message"); 
			    	$error.removeClass('hidden');
			    	$error.find(".errorMsg").html('Delay with these credentials already exists.');
				  }
			     else if(jqXHR.responseText.indexOf('Error in processing the last request.')>0){
				    	var $error= $addDelayTypeReasonModal.find("#error-message"); 
				    	$error.removeClass('hidden');
				    	$error.find(".errorMsg").html('Failed Delay Creation.');
					  }
			});
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $('#date-type').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Date Type");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#po-category').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a PO Category");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#delay-type').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Delay Type");
				$errorDiv.removeClass('hidden');
			}
			else if( $('#delay-reason').hasClass('errorMsgInput') ){
				
				$errorSpan.text("Please select a Delay Reason");
				$errorDiv.removeClass('hidden');
			}
		}
	});
	$delayTable.on("click", ".delete-button", function(){
		
		var $thisRow = $(this).closest("tr");
		var delayId = $thisRow.find('.delay-id').val();
		var dateType = $thisRow.find('.date-type').text();
		var poCategory = $thisRow.find('.po-category').text();
		var delayType = $thisRow.find('.delay-type').text();
		var delayReason = $thisRow.find('.delay-reason').text();
		
		var $getDelayModalContentPromise = $.get("get-delete-delay-modal-content.htm", {
																					delayId:delayId,
																					dateType:dateType,
																					poCategory:poCategory,
																					delayType:delayType,
																					delayReason:delayReason
																					});
		
		$getDelayModalContentPromise.done( function(data){
			
			$deleteDelayModal.html(data);
			openModal($deleteDelayModal);
		});
		
	});
	$('.modal').on("click", ".delete-delay", function(){
		
		// delete the row from the table on screen for the user
		var delayId = $deleteDelayModal.find('.delay-id').val();
		var intId = parseInt(delayId);
		
		var $deleteDataPromise = $.get("delete-delay.htm", {delayId:delayId});
		$deleteDataPromise.done( function(){
			
			$delayTable.find('.delay-id').each(function(){
				var $this = $(this);
				var delayIdMatches = $this.val() == intId;
				if(delayIdMatches){
					
					var $row = $this.closest("tr");
					$delayTable.fnDeleteRow( $row[0] );
				}
			});
			$deleteDelayModal.dialog('close').empty();
		});
	});
});

function initializeDelayTable($delayTable){
	
		$delayTable.dataTable({ //All of the below are optional
			"aaSorting": [[ 4, "desc" ]], //default sort column
			"bPaginate": true, //enable pagination
			"bStateSave": true,
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
	