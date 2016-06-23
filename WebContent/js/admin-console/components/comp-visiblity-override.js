var $componentSearchModal;
var $errMsgContainer = $('.error-messages-container');
var $errMsg = $('.edit-buttons-90').find('.error-messages-container').find('.errorMsg');
$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-component-visibility-overrides");
	
	$('.back').on('click', function(){
		parent.history.back();
		return false;
	});
	
	var $overrideTable = $('#visiblity-override-table');
	
	$componentSearchModal = $('#component-search-modal');
	
	var $confirmDeactivationModal = $('#delete-visiblity-override-modal');
	
	//datatable
	$overrideTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
        "bPaginate": true, //enable pagination
        "bLengthChange": false, //enable change of records per page, not recommended
        "bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
        "bSort": true, //Allow sorting by column header
        "bInfo": true, //Showing 1 to 10 of 11 entries
        "aoColumnDefs": [
                         {"bSortable": false, "aTargets": [ 0 ]} //stops first column from being sortable
                         ],
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
		 var rowCount = this.fnSettings().fnRecordsDisplay();
		 if (rowCount > 0) {
		        infoRow.css("display", "block");
		 } else {
		        infoRow.css("display", "none");
		 }
		        }
		 } );

	$componentSearchModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 750,
		minHeight: 270,
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$confirmDeactivationModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		title: 'Delete Component Visibility Override',
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});

	$('#dependentComponentSel').on("click", function(){
		loadSearchComponentScreen(0);
	});
	
	$('#controllingComponentSel').on("click", function(){
		loadSearchComponentScreen(1);
	});
	$('.create').on("click", function(){
		
		var $overrideForm = $('#visiblity-override-form');
		var isValid = validate($overrideForm);
		
		if(isValid){
			$errMsgContainer.addClass('displayNone');
			var $overrideAddPromise = $.post('./create-comp-visiblity-override.htm', $overrideForm.serialize());
			$overrideAddPromise.done(function(data){
				location.assign('./component-Visibility-Override.htm');
			});
			$overrideAddPromise.fail(function(xhr, ajaxOptions, thrownError) {
				 if(xhr.responseText.indexOf('Error Processing the Component Visibility Override')>0){
					  $errMsg.text('Error Processing the Component Visibility Override.');
					  $errMsgContainer.removeClass('displayNone');
				  }
				 else if(xhr.responseText.indexOf('Component Visibility Override Already exists.')>0){
					  $errMsg.text('Component Visibility Override Already exists..');
					  $errMsgContainer.removeClass('displayNone');
				  }
			});
				
		}
		else {
			$errMsgContainer.removeClass('displayNone');
		}
	});
	
	//edit modal
	$overrideTable.on("click", ".edit-visiblity-override", function(){
		var $this =  $(this);
		var overrideId = $this.closest('.visiblity-override-row').find('.visiblity-override-id').val();
		location.assign('./create-modify-comp-visiblity-override-page.htm?isCreatePage=false&overrideId='+overrideId);
	});

	
	$('.save').on("click", function(){
		
		var $overrideForm = $('#visiblity-override-form');
		var isValid = validate($overrideForm);
		
		if(isValid){
			$errMsgContainer.addClass('displayNone');
			var $overrideAddPromise = $.post('./update-comp-visiblity-override.htm', $overrideForm.serialize());
			$overrideAddPromise.done(function(data){
				location.assign('./component-Visibility-Override.htm');
			});
			$overrideAddPromise.fail(function(xhr, ajaxOptions, thrownError) {
				 if(xhr.responseText.indexOf('Error Processing the Component Visibility Override')>0){
					  $errMsg.text('Error Processing the Component Visibility Override.');
					  $errMsgContainer.removeClass('displayNone');
				  }
				 else if(xhr.responseText.indexOf('Component Visibility Override Already exists.')>0){
					  $errMsg.text('Component Visibility Override Already exists..');
					  $errMsgContainer.removeClass('displayNone');
				  }
			});
				
		}
		else {
			$errMsgContainer.removeClass('displayNone');
		}
	});
	
	//deactivate modal
	$overrideTable.on("click", ".delete-visiblity-override", function(){
		var $this =  $(this);
		var overrideId = $this.closest('.visiblity-override-row').find('.visiblity-override-id').val();
		var $getDeactivateModalContentPromise = $.get('get-deactivate-visiblity-override-modal-content.htm', {overrideId:overrideId});
		$getDeactivateModalContentPromise.done(function(data){
			$confirmDeactivationModal.html(data);
			openModal($confirmDeactivationModal);
			
		});
		openModal($confirmDeactivationModal);
	});
	
	//deactivate execution
	$confirmDeactivationModal.on("click", ".deactivate-confirm", function(){
		var overrideId = $('#override-id').val();
		var $deactivatePromise = $.post('delete-visiblity-override.htm', {overrideId:overrideId});
		
		$deactivatePromise.done(function(data){
			$('.visiblity-override-id').each(function(){
				var overrideIdMatch = $(this).val();
				var isoverrideIdMatchIdMatch = (overrideIdMatch == overrideId);
				if(isoverrideIdMatchIdMatch){
					var $overrideRow = $(this).closest('.visiblity-override-row');
					var nRow = $overrideRow[0];
					
					$('#visiblity-override-table').dataTable().fnDeleteRow(nRow);
				}
			});
			closeModal($confirmDeactivationModal);
		});
	});
	
});

function loadSearchComponentScreen(val){
	var $getSearchComponent = $.get('get-component-search-page.htm', {val:val});
	$getSearchComponent.done(function(data){
		$componentSearchModal.html(data);
		openModal($componentSearchModal);
	});
}
function initSearchPage(){
	var $searchComponenttable=$('#component-search-modal #search-Component-table');
	$searchComponenttable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bAutoWidth": false, //cray cray
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"sRowSelect": "multi",
		"aoColumnDefs": [
		                 {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] }
		                 ],
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": 10 , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "No Results Found"}, //Message displayed when no records are found
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
	} );
	
	$('#select-component').on("click", function(){
		var compCheckedId=$('input:radio[name=componentId]:checked').attr('id');
		if(compCheckedId== null || compCheckedId==undefined){
			$componentSearchModal.find('.error-messages-container').removeClass('displayNone');
			//$errMsg.removeClass('displayNone');
		}else{
			$componentSearchModal.find('.error-messages-container').addClass('displayNone');
			var compId=$('#'+compCheckedId).val();
			var compName=$('#compName_'+compCheckedId).text();
			var val=$('#val').val();
			if(val==0){
				$('#dependentComponentName').val(compName);
				$('#dependentComponentId').val(compId);
			}else{
				$('#controllingComponentName').val(compName);
				$('#controllingComponentId').val(compId);
			}
			closeModal($componentSearchModal);
		}
		//console.log('[compId] '+compId+"[ compName ]"+compName);
	});
}


function validate($editForm){

	var flag = true;
	if(validateFormTextFields($editForm) == false){
		if($('#overrideType').hasClass('errorMsgInput')){
			$errMsg.text('Error Override Types invalid!');
		}
		if($('#dependentComponentId').hasClass('errorMsgInput')){
			$errMsg.text('Error Dependent Component invalid!');
		}
		if($('#dependentComponentName').hasClass('errorMsgInput')){
			$errMsg.text('Error Dependent Component invalid!');
		}
		if($('#poCategoryAssocId').hasClass('errorMsgInput')){
			$errMsg.text('Error PO Category invalid!');
		}
		flag = false;
	}
	
	var controllingValue=$('#controllingComponentId').val();
	if(controllingValue !=null && controllingValue.trim().length>0){
		if(parseInt(controllingValue)>0){
			if($('#controllingValue').val() !=null && $('#controllingValue').val().length==0){
				$errMsg.text('Error Controlling Value invalid!');
				flag = false;
			}
		}
	/*	if($('#controllingComponentId').hasClass('errorMsgInput')){
			$errMsg.text('Error Controlling Component invalid!');
		}
		if($('#controllingComponentName').hasClass('errorMsgInput')){
			$errMsg.text('Error Controlling Component invalid!');
		}
	*/
	}
	return flag;
}
