var $errMsg = $('.edit-buttons').find('.error-messages-container').find('.errorMsg');
$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-category-management");
	
	//var $categorytables = $('.category-table');
	var $editCategoryModal =$('#edit-category-modal');
	var $addCategoryModal =$('#add-category-modal');
	var $addSubCategoryModal =$('#add-sub-category-modal');
	var $editSubCategoryModal =$('#edit-sub-category-modal');
	var $addSubCategory =$('.add-sub-category');
	var $poCategoryTable =$('#po-category-table');
	var $subCategoryTable = $('#sub-category-table');
	var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
	var iDisplayLength = 10;//tableRowLengthCalc();
	
	$poCategoryTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
        "bPaginate": true, //enable pagination
        "bStateSave": true,
        "bAutoWidth": false, //cray cray
        "bLengthChange": false, //enable change of records per page, not recommended
        "bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
        "bSort": true, //Allow sorting by column header
        "bInfo": true, //Showing 1 to 10 of 11 entries
        "aoColumnDefs": [
                         {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] }
                         ],
        "sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
        "iDisplayLength": iDisplayLength , //number of records per page for pagination
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
	$subCategoryTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
        "bPaginate": true, //enable pagination
        "bStateSave": true,
        "bAutoWidth": false, //cray cray
        "bLengthChange": false, //enable change of records per page, not recommended
        "bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
        "bSort": true, //Allow sorting by column header
        "bInfo": true, //Showing 1 to 10 of 11 entries
        "aoColumnDefs": [
                         {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] }
                         ],
        "sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
        "iDisplayLength": iDisplayLength , //number of records per page for pagination
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
	
	$('#delete-pocategory-modal').dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 150,
		resizable: false,
		title: 'PO Category Deactivate Confirmation',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$('#delete-subcategory-modal').dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 500,
		minHeight: 150,
		resizable: false,
		title: 'Sub Category Deactivate Confirmation',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$editCategoryModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 'auto',
		minHeight: 200,
		title:'Edit Category',
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$addCategoryModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 'auto',
		minHeight: 200,
		title:'Add category',
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$editSubCategoryModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 'auto',
		minHeight: 200,
		title:'Edit Category',
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$addSubCategoryModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 'auto',
		minHeight: 200,
		title:'Add category',
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	//deleting PO CAT MODAL
	$('#delete-pocategory-modal').on("click",'.delete',function(){
		var poCatId = $(this).closest('.modal').find('#cat-id').val();
		var $deletePOCategoryPromise = $.post('delete-po-category.htm', {poCatId:poCatId});
		
		$deletePOCategoryPromise.done(function(data){
			$('.category-id').each(function(){
				var $this = $(this);
				
				if($(this).val() == poCatId){
					var $catRow = $this.closest('.category-row');
					var nRow = $catRow[0];
					$poCategoryTable.dataTable().fnUpdate('Inactive', nRow, 3, false);
					
					//$poCategoryTable.dataTable().fnDeleteRow(nRow);
				}
			});
			$('#delete-pocategory-modal').empty();
			closeModal($('#delete-pocategory-modal'));
		});
		
	});
	
	//deleting SUB CAT
	$subCategoryTable.on("click",'.delete-category',function(){
		$('#delete-subcategory-modal').empty();
		var $this =  $(this);
		var subCategoryId = $this.closest('.sub-category-row').find('.editable').find('.sub-category-id').val();
		var catName = $this.closest('.sub-category-row').find('.sub-cat-name').text();
		
		$('#delete-subcategory-modal').prepend('<p>Are you sure you want to delete this Sub Category</p><ul><li>' + catName + '<input type="input" class="displayNone" id="sub-cat-id" value="' + subCategoryId + '"/></li></ul>'
				+'<p> <h3><u>Note:</u> By Deactivating Sub Category, Its Category Association will be deactivated.</h3></p>'
				+'<div style="position:absolute;bottom:3px;right:5px;"><a class="secondaryLink cancel" tabIndex="-1">No, Cancel</a><a class="buttonPrimary delete" tabIndex="-1">Yes, Delete</a></div>');
		
		openModal($('#delete-subcategory-modal'));
	 });
	
	//deleting SUB CAT MODAL
	$('#delete-subcategory-modal').on("click",'.delete',function(){
		var subCatId = $(this).closest('.modal').find('#sub-cat-id').val();
		var $deleteSUBCategoryPromise = $.post('delete-sub-category.htm', {subCatId:subCatId});
		
		$deleteSUBCategoryPromise.done(function(data){
			$('.sub-category-id').each(function(){
				var $this = $(this);
				
				if($(this).val() == subCatId){
					var $catRow = $this.closest('.sub-category-row');
					var nRow = $catRow[0];
					$subCategoryTable.dataTable().fnUpdate('Inactive', nRow, 3, false);
					//$subCategoryTable.dataTable().fnDeleteRow(nRow);
				}
			});
			$('#delete-subcategory-modal').empty();
			closeModal($('#delete-subcategory-modal'));
		});
		
	});
	
	
	$('#sub-category-table').on("click",'.edit-category',function(){
		
		var $this =$(this);
		var subCategoryId = $this.closest('.sub-category-row').find('.sub-category-id').val();
		
		var $getEditSubCategoryContentPromise =$.get("get-edit-sub-category-content.htm",{subCategoryId:subCategoryId});
		$getEditSubCategoryContentPromise.done(function(data){
			$editSubCategoryModal.html(data);
			openModal($editSubCategoryModal);
		});
		
	});
	$editSubCategoryModal.on('keypress', function(e) {
		if (e.which == 13) {
			$editSubCategoryModal.find('.save-category-edited').trigger('click');
			event.preventDefault();
		}
	});
	
	$editSubCategoryModal.on("click",'.save-category-edited',function(){
		
		var $form = $('#editSubCategory');
		var categoryData =$form.serialize();
		var categoryName =$form.find('.category-name').val();
		var categoryId = $form.find('.category-id').val();
		var description = $form.find('.category-description').val();
		var $selectedStatus = $('#status').find('option:selected');
		var status= $selectedStatus.val();
		var statusText =$selectedStatus.text();
		
		var isValid =validate($form);
		
		if(isValid){
			
				//var $updateSubCategoryPromise =$.post("update-sub-category.htm",categoryData);
			var $updateSubCategoryPromise = $.ajax({
				type: "POST",
				url:'./update-sub-category.htm',
				global: false,
				data: categoryData
			});
				$updateSubCategoryPromise.done(function(data){
					
					$('#sub-category-table').find('.sub-category-id').each(function(){
						var categoryIdCheck = $(this).val();
						var categoryIdMatch = (categoryIdCheck ==categoryId) ;
					
						if(categoryIdMatch){
							
							var $categoryRow = $(this).closest('tr');
							var nRow = $categoryRow[0];
							$subCategoryTable.dataTable().fnUpdate( categoryName, nRow, 1, false);
							$subCategoryTable.dataTable().fnUpdate( description, nRow, 2, false);
							$subCategoryTable.dataTable().fnUpdate( statusText, nRow, 3, false);
							
						}
					});
					closeModal($editSubCategoryModal);
				});
				$updateSubCategoryPromise.fail(function(xhr, ajaxOptions, thrownError) {
					 if(xhr.responseText.indexOf('Sub-Category Already exists.')>0){
						  $('.errorMsg').text("Sub-Category Already exists");
							 $('.error').show();
					  }
					 else  if(xhr.responseText.indexOf('Error Processing the updating Sub-Category')>0){
						  $('.errorMsg').text("Error Processing the updating Sub-Category.");
							 $('.error').show();
					  }
				});
		}
		
	});
	
	$addSubCategory.on("click",function(){
		
		var $addSubCategoryContentPromise =$.get("add-sub-category-modal-content.htm");
		$addSubCategoryContentPromise.done(function(data){
			$addSubCategoryModal.html(data);
		});
		openModal($addSubCategoryModal);
		
	});
	
	$addSubCategoryModal.on('keypress', function(e) {
		if (e.which == 13) {
			$addSubCategoryModal.find('.save-category').trigger('click');
			event.preventDefault();
		}
	});
	
	$addSubCategoryModal.on("click",'.save-category',function(){
		
		var $form = $addSubCategoryModal.find('#editSubCategory');
		var categoryData =$form.serialize();
		var categoryName = $form.find('.category-name').val();
		var description = $form.find('.category-description').val();
		var $selectedStatus = $('#status').find('option:selected');
		var status= $selectedStatus.val();
		var statusText =$selectedStatus.text();
		var isValid =validate($form);
		
		if(isValid){
			
	
			//var $insertSubCategoryPromise =$.post("insert-sub-category.htm",categoryData);
			var $insertSubCategoryPromise = $.ajax({
				type: "POST",
				url:'./insert-sub-category.htm',
				global: false,
				data: categoryData
			});
			$insertSubCategoryPromise.done(function(data){
				
				var subCategoryId =data;
				var firstColoumn = "<a class='rightMargin edit-category'>Edit</a>"
					+ '<input type ="hidden" class="sub-category-id" value="' +subCategoryId+'"/>';
				
				var rowIndex = $('#sub-category-table').dataTable().fnAddData([firstColoumn,categoryName,description,statusText],false);
				var newRow = $('#sub-category-table').dataTable().fnGetNodes(rowIndex[0]);
				$(newRow).addClass("sub-category-row");
				$(newRow).find("td:first-child").addClass("editable centerAlign");
				closeModal($addSubCategoryModal);
			});
		$insertSubCategoryPromise.fail(function(xhr, ajaxOptions, thrownError) {
			 if(xhr.responseText.indexOf('Sub-Category Already exists.')>0){
				  $('.errorMsg').text("Sub-Category Already exists.");
					 $('.error').show();
			  }
			 else  if(xhr.responseText.indexOf('Error Processing the Insert Sub-Category')>0){
				  $('.errorMsg').text("Error Processing the Insert Sub-Category.");
					 $('.error').show();
			  }
		});
		}
		
		
	});
	
	
});



function validate($form){
	
	
		var valid = validateFormTextFields($form);
		if(!valid){
			$('.errorMsg').text("please enter valid value");
			$('.error').show();
		}
		var categoryName =$form.find('.category-name').val();
		var categoryDesc=$form.find('.category-description').val();
		
		if(categoryName == ''||categoryDesc=='' )
			{
			$('.errorMsg').text("please enter required fields");
			$('.error').show();
			}
	
	return valid;
}