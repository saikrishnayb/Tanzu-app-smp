$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-templates");
	
	var $vendorTable = $('#vendor-table');
	var $deleteTemplateModal =$('#delete-template-modal');
	var $editTemplateModal= $('#edit-template-modal');
	var $deleteTemplateCategoryModal =$('#delete-template-category');
	var $searchForm =$('#searchForm');
	
	//datatable
	$vendorTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
        "bPaginate": true, //enable pagination
        "bLengthChange": false, //enable change of records per page, not recommended
        "bStateSave": true,
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
	$editTemplateModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 450,
		minHeight: 400,
		title:'Edit Template',
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	// delete modal for deleting template from vendor table
	$deleteTemplateModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 150,
		resizable: false,
		title: 'Confirm delete template',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	$deleteTemplateCategoryModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		resizable: false,
		title: 'Confirm delete template Category',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	 $vendorTable.on("click", ".delete-template", function(){
			
		var $this =  $(this);
		var templateId = $this.closest('.template-row').find('.template-id').val();
		var $getDeleteTemplateModalContentPromise = $.get('get-delete-template-modal-content',{templateId:templateId});
		
		$getDeleteTemplateModalContentPromise.done(function(data){
			
			$deleteTemplateModal.html(data);
			openModal($deleteTemplateModal);
		});
	});
		
		//delete template from table
	 $deleteTemplateModal.on("click", ".delete", function(){
		var $template = $deleteTemplateModal.find('#template');
		var templateId = $template.find("input[name='templateId']").val();
		
		var $deleteTableTemplatePromise = $.post('delete-template-table-content',{templateId:templateId});
		$deleteTableTemplatePromise.done(function(data){
			$('.template-id').each(function(){
				var templateIdMatch = $(this).val();
				var isTemplateIdMatch = (templateIdMatch == templateId);
				if(isTemplateIdMatch){
					var $templateRow = $(this).closest('.template-row');
					var nRow = $templateRow[0];
					
					$vendorTable.dataTable().fnDeleteRow(nRow);
				}
			});
			
			closeModal($deleteTemplateModal);
		});
	});
	
	$vendorTable.on("click",'.edit-template',function(){
		
		$this =$(this);
		var corpCode =$this.closest('.template-row').find('.corp-code').val();
		var vendorNumber =$this.closest('.template-row').find('.vendor-number').val();
		var templateId =$this.closest('.template-row').find('.template-id').val();
		var $getVendorTemplatePromise =$.get("get-vendor-template",{vendorNumber:vendorNumber,corpCode:corpCode});
		
		$getVendorTemplatePromise.done(function(data){
			
			$editTemplateModal.html(data);
			$editTemplateModal.data("template-id",templateId);
			openModal($editTemplateModal);
		});
	});
	
	$editTemplateModal.on("click",'.save-edited',function(){
		
		var templateId = $editTemplateModal.data("template-id");
		var visible,editable,required;
		
		$('.template-accordion').each(function(){
	
			var accordianPosition =$(this).find('.accordian-header').data("accordian-position");	
			var $tablediv =$('.template-accordion').find('div[data-accordian-position="'+ accordianPosition + '"]');
			
			$tablediv.find('.component-row').each(function(){
				
				var isVisible = $(this).find($("input[name='component-visible']:checked")).is('[data-visible]');
				visible = checkVisibileEdiatableRequired(isVisible);
				var isEditable = $(this).find($("input[name='component-editable']:checked")).is('[data-editable]');
				editable =checkVisibileEdiatableRequired(isEditable);
				var isRequired = $(this).find($("input[name='component-required']:checked")).is('[data-required]');
				required= checkVisibileEdiatableRequired(isRequired);
				var  tempCompId = $(this).find('.tempcomp-id').val();
				var serializeObject = "visible="+visible+"&editable="+editable+"&required="+required+"&tempCompId="+tempCompId;
				$updateTemplateComponentPromise = 	$.post('./update-template-components',serializeObject);
												
				$updateTemplateComponentPromise.done(function(data){
				});
			});
		});
		addTemplateComponents(templateId);
		closeModal($editTemplateModal);
		window.location.href = './templates';
	});
	//getting modal content for deleting categories from edit modal
	$editTemplateModal.on("click",'.delete-image',function(){
		
		var $this =  $(this);
		var poCategoryId = $this.closest('.accordian-header').find('.po-category').val();
		var subCategoryId = $this.closest('.delete-image-container').find('.sub-category').val();
		var templateId = $this.closest('.delete-image-container').find('.template-id').val();
		var accordianPosition = $this.data("accordian-position");
		var $getDeleteTemplateCategoryModalContentPromise = $.get('delete-category-content-edit',{templateId:templateId,poCategoryId:poCategoryId,subCategoryId:subCategoryId});
		
		$getDeleteTemplateCategoryModalContentPromise.done(function(data){
			$deleteTemplateCategoryModal.html(data);
			$deleteTemplateCategoryModal.data("accordion-position",accordianPosition);
			openModal($deleteTemplateCategoryModal);
	    });
	});
	//deleting newly added categories from edit modal
	$editTemplateModal.on("click",'.delete-icon',function(){
		
		var $this =  $(this);
		var poCategoryId = $this.closest('.category-accordian-header').find('.po-category').val();
		var subCategoryId = $this.closest('.delete-image-container').find('.sub-category').val();
		var accordianPosition = $this.data("accordian-position");
		var $deleteTemplateCategoryPromise =$.post("delete-category-content",{poCategoryId:poCategoryId,subCategoryId:subCategoryId});
		
		$deleteTemplateCategoryPromise.done(function(data){
				$deleteCategoryModal.html(data);
				$deleteCategoryModal.data("accordian-position",accordianPosition);
				openModal($deleteCategoryModal);
		});
	 });
	//deleting categories form edit modal
	$deleteTemplateCategoryModal.on("click",'.delete',function(){
		
		var $category = $deleteTemplateCategoryModal.find('#category');
		var templateId = $category.find('input[name="templateId"]').val();
		var poCategoryId = $category.find("input[name='poCategoryId']").val();
		var subCategoryId = $category.find("input[name='subCategoryId']").val();
		var accordianPosition = $deleteTemplateCategoryModal.data("accordion-position");
		var $deleteAccordionCategoryPromise = $.post('delete-accordion-category',{templateId:templateId,poCategoryId:poCategoryId,subCategoryId:subCategoryId});
		
		$deleteAccordionCategoryPromise.done(function(data){
		
			$('.template-accordion').each(function(){
				
				$('.template-accordion').find('h3[data-accordian-position="'+ accordianPosition + '"],div[data-accordian-position="'+ accordianPosition + '"]').each(function(){;
			        $(this).remove();
			      
			    });
			});
			closeModal($deleteTemplateCategoryModal);
		});
	});
	//submitting advance  search form
	
	$('.search').on("click",function(){
		
		if($('#manufacture').val()==='' ||$('#manufacture').val() == 'select'){
			$('#manufacture').addClass('errorMsgInput');
			$('.requiredFieldsCheck').show();
			return false;
		}
		$searchForm.submit();
	});
});
	$('.reset').on('click', function() {
	
		$('#searchForm').find('select').find('option:first').attr('selected', 'selected');
		$('#searchForm').find('input, select').removeClass('errorMsgInput');
		$('.requiredFieldsCheck').hide();
	});
	var $vendorNumber =$('#searchForm').find('[name="vendorNumber"]');
	var $manufacture =$('#searchForm').find('[name="manufacture"]');
	var $poCategory =$('#searchForm').find('[name="poCategoryId"]');
	var $subCategory =$('#searchForm').find('[name="subCategoryId"]');
	
	if ($manufacture.val() != 'select' || $vendorNumber.val() > 0|| $poCategory.val() > 0 || $subCategory.val() > 0) {
		$('#advancedSearch').trigger('click');
	} 
	//dynamically poulating vendor number drop down
	$('#manufacture').on("change",function(){
		var $this =$(this);
		var manufacture = $this.val();
		var $getVendorNumbers =$.get("get-vendor-numbers-by-mfr",{MFR:manufacture});
		
		$getVendorNumbers.done(function(data){
			var $vendorNumberDropDown = $('#vendorNumber');
			$vendorNumberDropDown.empty().append("<option value="+0+">Select</option>");
			var vendorNumberList =data;
			$.each(vendorNumberList,function(key,value){
				
				var vendorNumber = value;
				
				$vendorNumberDropDown.append('<option value = "'+vendorNumber+'" >'+vendorNumber +'</option>');
			});
		});
	});
	//dynamically populating subcategory dropdown 
	$('#poCategory').on("change",function(){
		var $this =$(this);
		var poCategory = $this.val();
		var $getSubCategories =$.get("get-add-po-sub-categories",{poCategoryId:poCategory});
		
		$getSubCategories.done(function(data){
			var $subCategoryDropDown = $('#subCategory');
			$subCategoryDropDown.empty().append("<option value="+0+">Select</option>");
			var subCategoryList =data;
			$.each(subCategoryList,function(key,value){
				
				var subCategory = value;
		
				$subCategoryDropDown.append('<option value = "'+subCategory.subCategoryId+'" >'+subCategory.subCategoryName +'</option>');
				
			});
		
		});
	
	});
	
function checkVisibileEdiatableRequired (isVisibleEditablerequired){
	if(isVisibleEditablerequired)
		return 1;
	else return 0;
}
function toggleContent(contentId,spanId){
	
	if($("#" + contentId).is(":visible")){
		//Currently Expanded
		$("#" + spanId).removeClass('expandedImage').addClass('collapsedImage');
		$("#" + contentId).removeClass("displayBlock").addClass("displayNone");
	}
	else{
		//Currently Collapsed
	   $("#" + spanId).removeClass('collapsedImage').addClass('expandedImage');
	   $("#" + contentId).removeClass("displayNone").addClass("displayBlock");
	}
}

function addTemplateComponents (templateId){
	
	var visible,editable,required,poCategoryId,subCategoryId,datatype;

	$('.added-categories-accordion').each(function(){
	
	var accordianPosition =$(this).find('.category-accordian-header').data("accordian-position");
	var $tablediv =$('.added-categories-accordion').find('div[data-accordian-position="'+ accordianPosition + '"]');

	$tablediv.find('.component-category-row').each(function(){
		
		
		var isVisible = $(this).find($("input[name='component-visible']:checked")).is('[data-visible]');
		visible = checkVisibileEdiatableRequired(isVisible);
		
		var isEditable = $(this).find($("input[name='component-editable']:checked")).is('[data-editable]');
		editable =checkVisibileEdiatableRequired(isEditable);
		
		var isRequired = $(this).find($("input[name='component-required']:checked")).is('[data-required]');
		required= checkVisibileEdiatableRequired(isRequired);
		
		componentId =$(this).find('.component-id').val();
		datatype=	$(this).find('.data-type').val();
		
		var $header =$('.added-categories-accordion').find('h3[data-accordian-position="'+ accordianPosition + '"]');
		poCategoryId= $header.find('.po-category').val();
		subCategoryId =$header.find('.sub-category').val();
		var vendorTemplateId=templateId;
	    var serializeObject = "visible="+visible+"&editable="+editable+"&required="+required+"&componentId="+componentId+"&templateId="+vendorTemplateId+"&dataType="+datatype;
		 
	    serializeObject=serializeObject+"&poCategoryId="+poCategoryId;
	    serializeObject=serializeObject+"&subCategoryId="+subCategoryId;
			
		$addTemplateComponentPromise = 	$.post('./add-template-components.htm',serializeObject);
												       
		$addTemplateComponentPromise.done(function(data){
		});
	});
	});
}	
