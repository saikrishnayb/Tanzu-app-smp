$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-reason-types");
	
	var $delayTypeTable = $('#delay-type-table');
	var $addDelayTypeModal = $('#add-delay-type-modal');
	var $deleteDelayTypeModal = $('#delete-delay-type-modal');
	var $editDelayTypeModal = $("#edit-delay-type-modal");
	var commonStaticUrl = $('#common-url').val();
	var $customErrorModal = $("#customized-ajax-error");
	// initializers
	initializeDelayTypeTable($delayTypeTable);
	
	$addDelayTypeModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 350,
		minHeight: 100,
		resizable: false,
		title: 'Add Delay Type',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$deleteDelayTypeModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 350,
		minHeight: 200,
		resizable: false,
		title: 'Delete Delay Type',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$editDelayTypeModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 375,
		minHeight: 150,
		resizable: false,
		title: 'Edit Delay Type',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$('.add-delay-type').on("click", function(){
		displayFlag=false;
		var $getAddDelayTypeModalPromise = $.get("get-add-delay-type-modal-page.htm");
		$getAddDelayTypeModalPromise.done( function(data){
			
			$addDelayTypeModal.html(data);
			openModal($addDelayTypeModal);
		});
	});
	$('.modal').on("click", ".save-add", function(){
		
		var $typeField = $('#delay-type');
		var $addDelayTypeForm = $('#add-delay-type-form');
		var $errorDiv = $('#error-message');
		
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($typeField);
		
		if(validated){
			
			var addDelayTypeFormRequestMapping = $addDelayTypeForm.attr('action');
			var $addDelayTypePromise = $.ajax({ 
												url: addDelayTypeFormRequestMapping, 
												data: $addDelayTypeForm.serialize(),
												global: false });
			
			$addDelayTypePromise.done( function(data){
				
				var typeObj = data;
				var typeId = typeObj.typeId;
				var typeName = typeObj.delayType;
				var firstColumn = "<a class='rightMargin edit-button'>Edit</a>" +
									"<a><img src='" + commonStaticUrl + "/images/delete.png' class='centerImage rightMargin delete-button'/></a>" +
									"<input type='hidden' class='delay-type-id' value='" + typeId + "'/>";
				var rowIndex = $delayTypeTable.fnAddData( [ firstColumn, typeName ],false);
			//	var $newRow = $( $delayTypeTable.fnGetNodes( rowIndex[0]) );
			//	$newRow.find("td:first-child").addClass("editable centerAlign width");
			//	$newRow.find("td:nth-child(1)").addClass("delay-type");
				var nTr  = $delayTypeTable.fnSettings().aoData[ rowIndex[0] ].nTr;
				//var $column = $($newRow).find('td:first').addClass("editable centerAlign width");
				$('td', nTr)[0].setAttribute( 'class', 'editable centerAlign width' );
				$('td', nTr)[1].setAttribute( 'class', 'delay-type' );
				$addDelayTypeModal.dialog('close').empty();
				
			}).fail( function(jqXHR, ajaxOptions, thrownError){
				/*
				var responseText = (xhr.responseText);
				var ajaxError = JSON.parse(responseText);
				var errorDescription = ajaxError.errorDescription;
				$customErrorModal.find("p").html(errorDescription);
				openModal( $customErrorModal );*/
				
				$ajaxErrorFlg=false;
			     if(jqXHR.responseText.indexOf('A Delay Type with this name already exists.')>0){
			    	var $error= $addDelayTypeModal.find("#error-message"); 
			    	$error.removeClass('hidden');
			    	$error.find(".errorMsg").html('A Delay Type with this name already exists.');
				  }
			});
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $typeField.hasClass('errorMsgInput') ){
				
				$errorSpan.text("Field cannot be left blank");
				$errorDiv.removeClass('hidden');
			}
		}
	});
	$delayTypeTable.on("click", ".delete-button", function(){
		
		var $this = $(this);
		var delayId = $this.closest("tr").find('.delay-type-id').val();
		var delayType = $this.closest("tr").find('.delay-type').text();
		displayFlag=false;
		var $getDeleteDelayTypeModalPromise = $.get("get-delete-delay-type-modal-page.htm", {typeId:delayId, delayType:delayType});
		$getDeleteDelayTypeModalPromise.done( function(data){
			
			$deleteDelayTypeModal.html(data);
			openModal($deleteDelayTypeModal);
		});
	});
	$('.modal').on("click", ".delete-delay", function(){
		
		// get id value in hidden input field
		var typeId = $deleteDelayTypeModal.find('.delay-type-id').val();
		var intId = parseInt(typeId);
		displayFlag=false;
		// access controller and delete delay type from database
		var $getDeleteDelayTypePromise = $.get("delete-delay-type.htm", {typeId:intId});
		$getDeleteDelayTypePromise.done( function(){
			
			$delayTypeTable.find('.delay-type-id').each(function(){
				var $this = $(this);
				var delayTypeIdMatches = $this.val() == intId;
				if(delayTypeIdMatches){
					
					var $row = $this.closest("tr");
					$delayTypeTable.fnDeleteRow( $row[0] );
				}
			});
			$deleteDelayTypeModal.dialog('close').empty();
		});
	});
	$delayTypeTable.on("click", ".edit-button", function(){
		
		var $tr = $(this).closest("tr");
		var $delayId = $tr.find('.delay-type-id').val();
		var $delayName = $tr.find('.delay-type').text();
		var $getEditDelayTypeModalPromise = $.get("get-edit-delay-type-modal-page.htm", {typeId:$delayId, delayType:$delayName});
		$getEditDelayTypeModalPromise.done( function(data){
			
			$editDelayTypeModal.html(data);
			openModal($editDelayTypeModal);
		});
	});
	$('.modal').on("click", ".save-edit", function(){
		
		var $errorDiv = $('#error-message');
		var $nameField = $('#edit-delay-type-name');
		var changedName = $nameField.val();
		var $id = $('#hidden-type-id').val();
		var $oldVal =$('#hidden-type-val').val();
		var intId = parseInt($id);
		if($oldVal == null || $oldVal==undefined){
			$oldVal="";
		}
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($nameField);
		
		if(validated){
			
			var $editForm = $('#edit-modal-form');
			var editDelayTypeFormMapping = $editForm.attr('action');
			var $editDelayTypePromise = $.ajax({ 
											url: editDelayTypeFormMapping, 
											data: {typeId:$id,oldVal:$oldVal, delayType:changedName},
											global: false} );
			
			$editDelayTypePromise.done( function(data){
				
				// iterate through each table row and switch the name of the correct matching ID
				$delayTypeTable.find('.delay-type-id').each(function(){
					
					var $this = $(this);
					var delayTypeIdMatches = $this.val() == intId;
					if(delayTypeIdMatches){
						
						var $row = $this.closest("tr");
						$delayTypeTable.fnUpdate( changedName, $row[0], 1 , false); // 1 is the second column which stores the name
					}
				});
				$editDelayTypeModal.dialog('close').empty();
				
			}).fail( function(jqXHR, ajaxOptions, thrownError){
				/*
				var responseText = (xhr.responseText);
				var ajaxError = JSON.parse(responseText);
				var errorDescription = ajaxError.errorDescription;
				$customErrorModal.find("p").html(errorDescription);
				openModal( $customErrorModal );*/
				
				$ajaxErrorFlg=false;
			     if(jqXHR.responseText.indexOf('A Delay Type with this name already exists.')>0){
			    	var $error= $editDelayTypeModal.find("#error-message"); 
			    	$error.removeClass('hidden');
			    	$error.find(".errorMsg").html('A Delay Type with this name already exists.');
				  }
			});
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $nameField.hasClass('errorMsgInput') ){
				
				$errorSpan.text("Field cannot be left blank");
				$errorDiv.removeClass('hidden');
			}
		}
	});
});

function initializeDelayTypeTable($delayTypeTable){
	
	$delayTypeTable.dataTable({ //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
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