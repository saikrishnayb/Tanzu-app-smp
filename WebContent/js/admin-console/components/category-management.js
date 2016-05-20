$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-category-management");
	
	var $categorytables = $('.category-table');
	var $editCategoryModal =$('#edit-category-modal');
	var $addCategoryModal =$('#add-category-modal');
	var $addCategory = $('.add-category');
	var $addSubCategoryModal =$('#add-sub-category-modal');
	var $editSubCategoryModal =$('#edit-sub-category-modal');
	var $addSubCategory =$('.add-sub-category');
	var $poCategoryTable =$('#po-category-table');
	var $subCategoryTable = $('#sub-category-table');
	var commonStaticUrl =$('#common-static-url').val();
	var iDisplayLength = tableRowLengthCalc();
	
	$categorytables.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
        "bPaginate": true, //enable pagination
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
	
	//deleting PO CAT
	$poCategoryTable.on("click",'.delete-category',function(){
		$('#delete-pocategory-modal').empty();
		var $this =  $(this);
		var poCategoryId = $this.closest('.category-row').find('.editable').find('.category-id').val();
		var catName = $this.closest('.category-row').find('.po-cat-name').text();
		
		$('#delete-pocategory-modal').prepend('<p>Are you sure you want to delete this PO Category</p><ul><li>' + catName + '<input type="input" class="displayNone" id="cat-id" value="' + poCategoryId + '"/></li></ul>'
				+ '<p> <h3><u>Note:</u> By Deactivating POCategory, Its Category Association will be deactivated.</h3></p>'
				+'<div style="position:absolute;bottom:3px;right:5px;"><a class="secondaryLink cancel" tabIndex="-1">No, Cancel</a><a class="buttonPrimary delete" tabIndex="-1">Yes, Delete</a></div>');
		
		openModal($('#delete-pocategory-modal'));
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
					$poCategoryTable.dataTable().fnUpdate('Inactive', nRow, 3);
					
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
					$subCategoryTable.dataTable().fnUpdate('Inactive', nRow, 3);
					//$subCategoryTable.dataTable().fnDeleteRow(nRow);
				}
			});
			$('#delete-subcategory-modal').empty();
			closeModal($('#delete-subcategory-modal'));
		});
		
	});
	
	$('#po-category-table').on("click",'.edit-category',function(){
		
		var $this =$(this);
		var poCategoryId = $this.closest('.category-row').find('.category-id').val();
		var $getEditCategoryContentPromise =$.get("get-edit-category-content.htm",{poCategoryId:poCategoryId});
		
		$getEditCategoryContentPromise.done(function(data){
			$editCategoryModal.html(data);
			openModal($editCategoryModal);
		});
		
	});
	
	
	$editCategoryModal.on("click",'.save-category-edited',function(){
	
		var $form = $('#editCategory');
		var categoryData =$form.serialize();
		var categoryName =$form.find('.category-name').val();
		var description = $form.find('.category-description').val();
		
		var $selectedStatus = $('#status').find('option:selected');
		var statusText =$selectedStatus.text();
			
		var categoryId = $form.find('.po-category-id').val();
		var isValid =validate($form);
		
		if(isValid){
			var $updatePoCategoryPromise =$.post("update-po-category.htm",categoryData);
			
			
			$updatePoCategoryPromise.done(function(data){
				
				$poCategoryTable.find('.category-id').each(function(){
					
					var categoryIdCheck = $(this).val();
					var categoryIdMatch = (categoryIdCheck ==categoryId) ;
				
					if(categoryIdMatch){
						
						var $categoryRow = $(this).closest('tr');
						var nRow = $categoryRow[0];
						$poCategoryTable.dataTable().fnUpdate( categoryName, nRow, 1);
						$poCategoryTable.dataTable().fnUpdate( description, nRow, 2);
						$poCategoryTable.dataTable().fnUpdate( statusText, nRow, 3);
						
					}
				});
				
			});
			closeModal($editCategoryModal);
	    }
	});
	
	
	$addCategory.on("click",function(){
		var $addPoCategoryContentPromise =$.get("add-category-modal-content.htm");
		$addPoCategoryContentPromise.done(function(data){
			$addCategoryModal.html(data);
		});
		openModal($addCategoryModal);
		
	});
	
	$addCategoryModal.on("click",'.save-category',function(){
		
		$('#status').removeAttr('disabled');
		var $form = $addCategoryModal.find('#editCategory');
		var categoryData =$form.serialize();
		var categoryName = $form.find('.category-name').val();
		var description = $form.find('.category-description').val();
		
		var $selectedStatus = $('#status').find('option:selected');
		var status= $selectedStatus.val();
		if(status != 'A' && status != 'I')
			return;
		var statusText =$selectedStatus.text();
		var isValid =validate($form);
		
		if(isValid){
			
			var $insertPoCategoryPromise =$.post("insert-po-category.htm",categoryData);
			$insertPoCategoryPromise.done(function(data){
				
				var firstColoumn = "<a class='rightMargin edit-category'>Edit</a>"
					+'<a><img src=' +commonStaticUrl+'/images/delete.png class="centerImage rightMargin delete-category" ></a>'
					+ '<input type ="hidden" class="category-id" value="' +data.categoryId+'"/>';
				
				var rowIndex = $('#po-category-table').dataTable().fnAddData([firstColoumn,categoryName,description,statusText]);
				var newRow = $('#po-category-table').dataTable().fnGetNodes(rowIndex[0]);
				$(newRow).addClass("category-row");
				$(newRow).find("td:first-child").addClass("editable centerAlign");
			});
			closeModal($addCategoryModal);
		}
		
		
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
			
				var $updateSubCategoryPromise =$.post("update-sub-category.htm",categoryData);
				$updateSubCategoryPromise.done(function(data){
					
					$('#sub-category-table').find('.sub-category-id').each(function(){
						var categoryIdCheck = $(this).val();
						var categoryIdMatch = (categoryIdCheck ==categoryId) ;
					
						if(categoryIdMatch){
							
							var $categoryRow = $(this).closest('tr');
							var nRow = $categoryRow[0];
							$subCategoryTable.dataTable().fnUpdate( categoryName, nRow, 1);
							$subCategoryTable.dataTable().fnUpdate( description, nRow, 2);
							$subCategoryTable.dataTable().fnUpdate( statusText, nRow, 3);
							
						}
					});
					
				});
				closeModal($editSubCategoryModal);
		}
		
	});
	
	$addSubCategory.on("click",function(){
		
		var $addSubCategoryContentPromise =$.get("add-sub-category-modal-content.htm");
		$addSubCategoryContentPromise.done(function(data){
			$addSubCategoryModal.html(data);
		});
		openModal($addSubCategoryModal);
		
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
			
	
			var $insertSubCategoryPromise =$.post("insert-sub-category.htm",categoryData);
			$insertSubCategoryPromise.done(function(data){
				
				var subCategoryId =data;
				var firstColoumn = "<a class='rightMargin edit-category'>Edit</a>"
					+'<a><img src=' +commonStaticUrl+'/images/delete.png class="centerImage rightMargin delete-category" ></a>'
					+ '<input type ="hidden" class="sub-category-id" value="' +subCategoryId+'"/>';
				
				var rowIndex = $('#sub-category-table').dataTable().fnAddData([firstColoumn,categoryName,description,statusText]);
				var newRow = $('#sub-category-table').dataTable().fnGetNodes(rowIndex[0]);
				$(newRow).addClass("sub-category-row");
				$(newRow).find("td:first-child").addClass("editable centerAlign");
				
			});
			closeModal($addSubCategoryModal);
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