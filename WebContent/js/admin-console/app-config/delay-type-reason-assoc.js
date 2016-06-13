$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-delay-association");
	
	var $delayTypeReasonTable = $('#delay-type-reason-table');
	initializeDataTable($delayTypeReasonTable);
	var $deleteDelayTypeReasonModal = $('#delete-delay-type-reason-modal');
	var $editDelayTypeReasonModal = $('#edit-delay-type-reason-modal');
	var $addDelayTypeReasonModal = $('#add-delay-type-reason-modal');
	var $customErrorModal = $('#customized-ajax-error');
	var commonStaticUrl = $('#common-url').val();
	
	$addDelayTypeReasonModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight: 150,
		resizable: false,
		title: 'Configure Association',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$deleteDelayTypeReasonModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 335,
		minHeight: 175,
		resizable: false,
		title: 'Delete Association',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$editDelayTypeReasonModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight: 150,
		resizable: false,
		title: 'Edit Association',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	

		 
	 $('.add-delay-reason').on("click", function(){
			displayFlag=false;
			var $getAddDelayReasonModalPromise = $.get("get-add-delay-type-reason-page.htm");
			$getAddDelayReasonModalPromise.done( function(data){
				
				$addDelayTypeReasonModal.html(data);
				openModal($addDelayTypeReasonModal);
			});
		});
	 
	 $delayTypeReasonTable.on("click", ".edit-button", function(){
			
			var $row = $(this).closest("tr");
			var delayAssocId = $row.find('.association-id').val();
			displayFlag=false;
			var $getEditDelayReasonModalPromise = $.get("get-edit-delay-type-reason-page.htm", {typeReasonId:delayAssocId});
			$getEditDelayReasonModalPromise.done( function(data){
					
				$editDelayTypeReasonModal.html(data);
				openModal($editDelayTypeReasonModal);
			});
		});
	 
	 $delayTypeReasonTable.on("click", ".delete-button", function(){
			
			var $row = $(this).closest("tr");
			var typeVal = $row.find('.delay-type').text();
			var delayAssocId = $row.find('.association-id').val();
			var delayReason = $row.find('.delay-reason').text();
			displayFlag=false;
			var $getDeleteDelayReasonModalPromise = $.get("get-delete-delay-type-reason-page.htm", {reasonVal:delayReason,typeVal:typeVal,typeReasonId:delayAssocId});
			$getDeleteDelayReasonModalPromise.done( function(data){
				
				$deleteDelayTypeReasonModal.html(data);
				openModal($deleteDelayTypeReasonModal);
			});
		});
	 
	 $addDelayTypeReasonModal.on("click", ".add-delay-reason-save", function(){
			
			var $addDelayReasonForm = $('#add-delay-type-reason-form');
			var $errorDiv = $('#error-message');
			
			$errorDiv.addClass("hidden");
			var validated = validateFormTextFields($addDelayReasonForm);
			var typeId=$('#delay-type-of-reason').val();
			var reasonId=$('#add-delay-reason-name').val();
			var currentTimeStamp = new Date().getTime();
			if(validated){
				
				var addDelayReasonFormRequestMapping = $addDelayReasonForm.attr('action');
				displayFlag=false;
				var $addDelayReasonPromise = $.ajax({
					url: addDelayReasonFormRequestMapping+'?typeId='+ typeId + '&reasonId='+reasonId+ '&_=' + currentTimeStamp,
			        processData: false,
			        contentType: false,
			        type: 'POST'
				});
				$addDelayReasonPromise.done( function(data){
					if(data!=null){
						var reasonObj = data;
						var reasonId = reasonObj.reasonId;
						var typeId = reasonObj.typeId;
						var delayAssocid = reasonObj.delayAssocid;
						var reasonText=reasonObj.reasonVal;
						var typeText=reasonObj.typeVal;
						if(delayAssocid !=undefined){
							var firstColumn = "<a class='rightMargin edit-button'>Edit</a>" +
									"<a><img src='" + commonStaticUrl + "/images/delete.png' class='centerImage rightMargin delete-button'/></a>" +
											"<input type='hidden' class='association-id' value='" + delayAssocid + "'/>";
							var rowIndex = $delayTypeReasonTable.fnAddData( [ firstColumn,typeText,reasonText ]);
							var nTr  = $delayTypeReasonTable.fnSettings().aoData[ rowIndex[0] ].nTr;
							$('td', nTr)[0].setAttribute( 'class', 'editable centerAlign width' );
							$('td', nTr)[1].setAttribute( 'class', 'delay-type' );
							$('td', nTr)[2].setAttribute( 'class', 'delay-reason' );
						}
					}					
					$addDelayTypeReasonModal.dialog('close').empty();
				});
				$addDelayReasonPromise.fail(function (jqXHR, ajaxOptions, thrownError) {
					$ajaxErrorFlg=false;
				     if(jqXHR.responseText.indexOf('Error in processing the last request.')>0){
				    	var $error= $addDelayTypeReasonModal.find("#error-message"); 
				    	$error.removeClass('hidden');
				    	$error.find(".errorMsg").html('Delay Type-Reason Association already exists.');
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
					
					$errorSpan.text("Please select a Delay Reason");
					$errorDiv.removeClass('hidden');
				}
			}
		});

	 $editDelayTypeReasonModal.on("click", ".edit-delay-reason", function(){
			
			var oldTypeId = $('#hidden-type-id').val();
			var oldReasonId = $('#hidden-reason-id').val();
			var newTypeId = $('#delay-type-of-reason').val();
			var newReasonId = $('#edit-delay-reason-name').val();
			var assocId=$('#hidden-assoc-id').val();
			
			/*var newTypeName = $('#delay-type-of-reason option').filter(":selected").text();
			var oldReasonName = $('#hidden-reason-name').val();
			var newReasonName = $('#edit-delay-reason-name').val();*/
			var $form = $('#edit-modal-form');
			var $errorDiv = $('#error-message');
			
			$errorDiv.addClass("hidden");
			var validated = validateFormTextFields($form);
			
			if(validated){
			
				var $getEditDelayReasonPromise = $.ajax({
													url: "edit-delay-type-reason.htm", 
													data: {oldTypeId:oldTypeId, newTypeId:newTypeId,
														oldReasonId:oldReasonId, newReasonId:newReasonId,
														assocId:assocId},
													global: false});
				
				$getEditDelayReasonPromise.done(function(data){
					
					if(data!=null){
						var reasonObj = data;
						var reasonId = reasonObj.reasonId;
						var typeId = reasonObj.typeId;
						var delayAssocid = reasonObj.delayAssocid;
						var reasonText=reasonObj.reasonVal;
						var typeText=reasonObj.typeVal;
						if(delayAssocid !=undefined){
							$delayTypeReasonTable.find('.association-id').each(function(){
								
								var $this = $(this);
								var intId = parseInt(assocId);
								var delayReasonIdMatches = $this.val() == intId;
								if(delayReasonIdMatches){
									
									var $row = $this.closest("tr");
									$delayTypeReasonTable.fnUpdate( typeText, $row[0], 1 );
									$delayTypeReasonTable.fnUpdate( reasonText, $row[0], 2 );
									$row.find("td:first-child").find(".association-id").val(assocId);
								}
							});
						}
					}
					$editDelayTypeReasonModal.dialog('close').empty();
					
				}).fail( function(jqXHR, ajaxOptions, thrownError){
					$ajaxErrorFlg=false;
				     if(jqXHR.responseText.indexOf('Error in processing the last request.')>0){
				    	var $error= $editDelayTypeReasonModal.find("#error-message"); 
				    	$error.removeClass('hidden');
				    	$error.find(".errorMsg").html('Delay Type-Reason Association already exists.');
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
					
					$errorSpan.text("Please select a Delay Reason");
					$errorDiv.removeClass('hidden');
				}
			}
		});
		
	 $deleteDelayTypeReasonModal.on("click", ".delete-delayTypeReson", function(){
			
			// get id value in hidden input field
			var assocId = $('.delay-type-reason-id').val();
			var intId = parseInt(assocId);
			displayFlag=false;
			// access controller and delete delay type from database
			var $getDeleteDelayReasonPromise = $.get("delete-delay-type-reason.htm", {assocId:assocId});
			$getDeleteDelayReasonPromise.done( function(){
				
				$delayTypeReasonTable.find('.association-id').each(function(){
					var $this = $(this);
					var delayReasonIdMatches = $this.val() == intId;
					if(delayReasonIdMatches){
						
						var $row = $this.closest("tr");
						$delayTypeReasonTable.fnDeleteRow( $row[0] );
					}
				});
				$deleteDelayTypeReasonModal.dialog('close').empty();
			});
		});
});


// data table initialization
function initializeDataTable($delayTypeReasonTable){
	
	$delayTypeReasonTable.dataTable({ //All of the below are optional
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