$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-visibility-by-category");
	
	
	var $addVisibility  =$('.add-visibility');
	var $visibilityModal =$('#visibility-modal');
	var $componentTable = $('#component-table');
	var $deleteComponentModal = $('#delete-component-modal');
	var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
	var iDisplayLength = tableRowLengthCalc();
	    
		//datatable
	$componentTable.dataTable( { //All of the below are optional
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

		
		//addCategory modal
		
		$visibilityModal.dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: 'auto',
			minHeight: 180,
			title:'Add Visibility',
			resizable: false,
			closeOnEscape: false,
			open: function(event, ui) { }
		});
		
		
		$deleteComponentModal.dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: 370,
			minHeight: 150,
			resizable: false,
			title: 'Confirm delete Visibility',
			closeOnEscape: false,
			open: function(event, ui) { }
		});
		
		//listeners
		
		$addVisibility.on("click",function(){
			
			var $getAddVisibilityModalContentPromise =	$.get("get-add-visibility-modal-content.htm");
			
			$getAddVisibilityModalContentPromise.done(function(data) {
					
					$visibilityModal.html(data)	;
					$visibilityModal.dialog('open');
				});
		});
		
		
	
		$visibilityModal.on("click",'.save-visibility',function(){
			
			var $addVisibilityForm = $("#add-visibility-form");
			var $selectedComponent = $('#component-name').find('option:selected');
			var componentId= $selectedComponent.val();
			var component = $selectedComponent.text();
			
			var $selectedPoCategory = $('#po-category').find('option:selected');
			var poCategoryId= $selectedPoCategory.val();
			var isComponentVehicle=	$selectedPoCategory.data('component');
			var poCategory =$selectedPoCategory.text();
			
			var $selectedSubCategory = $('#sub-category').find('option:selected');
			var subCategoryId= $selectedSubCategory.val();
			var subCategory= $selectedSubCategory.text();
			
			var isValid = validateAddVisibilityDropDowns($addVisibilityForm);
			
			if(isValid){
				 var rowCount = $componentTable.fnSettings().fnRecordsDisplay();
				 var currentCount=rowCount+1;
				
				
				 var firstColoumn = '<a><img src=' +commonStaticUrl+'/images/delete.png class="centerImage rightMargin delete-visibility" data-component-row='+currentCount+'></a>'
			    				 + '<input type ="hidden" class="component-id" value="' +componentId+'"/>'
			    				 + '<input type ="hidden" class="is-component-vehicle" value="' +isComponentVehicle+'"/>'
								 + '<input type ="hidden" class="component-category" value="' +poCategoryId+'"/>'
								 + '<input type ="hidden" class="component-sub-category" value="' +subCategoryId+'"/>';
				
				var $postSaveVisibilityPromise =	$.post("post-save-visibility.htm",{isComponentVehicle:isComponentVehicle,componentId:componentId,poCategoryId:poCategoryId,subCategoryId:subCategoryId});
				
				$postSaveVisibilityPromise.done(function(data) {
					
					var rowIndex = $componentTable.fnAddData([firstColoumn,component,poCategory,subCategory,"Yes"]);
					var newRow = $componentTable.fnGetNodes(rowIndex[0]);
					$(newRow).addClass("component-row");
					closeModal($visibilityModal);
				});
			}
		});
		
		//listener 
		//component on change
		$visibilityModal.on("change",'#component-name',function(){
			
			var $this =$(this);
			var selected =$this.find('option:selected');
			var isComponentVehicle = selected.data('is-component-vehicle');
			var componentId = $this.val();
			var $getPoCategoriesPromise =$.get("get-po-categories.htm",{isComponentVehicle:isComponentVehicle,componentId:componentId});
			
			$getPoCategoriesPromise.done(function(data){
				var $poCategoryDropDown = $('#po-category');
				$poCategoryDropDown.empty().append("<option value="+''+">Select</option>");
				var poCategoryList =data;
				$.each(poCategoryList,function(key,value){
					
					var poCategory = value;
					$poCategoryDropDown.append('<option value = "'+poCategory.category.categoryId+'"data-id="'+poCategory.componentId+'"data-component="'+poCategory.isComponentVehicle+'" >'+poCategory.category.categoryName +'</option>');
					
				});
				
			});
			
		});
		
		
		//po-category on change
		
		$visibilityModal.on("change",'#po-category',function(){
			var $this =$(this);
			var selected =$this.find('option:selected');
			var isComponentVehicle = selected.data('component');
			var poCategoryName = selected.data('name');
			var poCategoryId = $this.val();
			var $getPoSubCategoriesPromise =$.get("get-po-sub-categories.htm",{poCategoryId:poCategoryId});
			
			$getPoSubCategoriesPromise.done(function(data){
				
				var $poSubCategoryDropDown = $('#sub-category');
				$poSubCategoryDropDown.empty().append("<option value="+''+">Select</option>");
				var poSubCategoryList =data;
				
				$.each(poSubCategoryList,function(key,value){
					
					var poSubCategory = value;
					$poSubCategoryDropDown.append('<option value = "'+poSubCategory.subCategoryId+'">'+poSubCategory.subCategoryName +'</option>');
					
					
				});
			});
			
		});
		
	
		$componentTable.on("click", ".delete-visibility", function(){
			
			var $this =  $(this);
			var componentId = $this.closest('.component-row').find('.component-id').val();
			var isComponentVehicle = $this.closest('.component-row').find('.is-component-vehicle').val();
			var category = $this.closest('.component-row').find('.component-category').val();
			var subCategory = $this.closest('.component-row').find('.component-sub-category').val(); 
			var rowIndex = $this.data('component-row');
			var $getDeleteModalContentPromise = $.get('get-delete-modal-content.htm',{componentId:componentId,isComponentVehicle:isComponentVehicle,category:category,subCategory:subCategory});
			
			$getDeleteModalContentPromise.done(function(data){
				$deleteComponentModal.html(data);
				$deleteComponentModal.data("row-index",rowIndex);
				openModal($deleteComponentModal);
			});
			
		});
		
		
		$deleteComponentModal.on("click", ".delete", function(id){
			var rowIndex = $deleteComponentModal.data('row-index');
			var row = $componentTable.find('.delete-visibility[data-component-row="'+rowIndex+'"]').closest('.component-row');
			var componentId = $('#delete-componentId').val();
			var isComponentVehicle = $('#delete-isComponentVehicle').val();
			var category = $('#delete-categoryId').val();
			var subCategory = $('#delete-subCategoryId').val(); 
			var $deleteTableComponentPromise = $.post('delete-table-component.htm',{componentId:componentId,isComponentVehicle:isComponentVehicle,category:category,subCategory:subCategory});
			
			$deleteTableComponentPromise.done(function(data){
				var nRow = row[0]
				$componentTable.fnDeleteRow(nRow);
				closeModal($deleteComponentModal);
			});
			
		});
});

function validateAddVisibilityDropDowns($addVisibilityForm){
	var isValidate =true;
	
	var $selectedComponent = $addVisibilityForm.find('#component-name').find('option:selected');
	var $selectedPoCategory = $addVisibilityForm.find('#po-category').find('option:selected');
	var $selectedSubCategory = $addVisibilityForm.find('#sub-category').find('option:selected');
	
	if($selectedComponent.val()==''||$selectedPoCategory.val()==''||$selectedSubCategory.val()==''){
		isValidate =false;
		$('#component-name').addClass('errorMsgInput');
		$('#po-category').addClass('errorMsgInput');
		$('#sub-category').addClass('errorMsgInput');
		$addVisibilityForm.find('.requiredFieldsCheck').show();
	}
	return isValidate;
}
	
		
		
	

	
	
	
