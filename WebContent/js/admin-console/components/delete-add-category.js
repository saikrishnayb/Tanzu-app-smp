var $deleteCategoryModal =$('#delete-category');
var $addCategoryModal= $('#add-category-modal');

var $addCategory =$('.add-category');

$deleteCategoryModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		resizable: false,
		title: 'Confirm delete category',
		closeOnEscape: false,
		open: function(event, ui) { }
	});

$addCategoryModal.dialog({
	autoOpen: false,
	modal: true,
	dialogClass: 'popupModal',
	width: 370,
	minHeight:150,
	resizable: false,
	title: 'PoCategory',
	closeOnEscape: false,
	open: function(event, ui) { }
});

var $poCategoryAccordionContainer=$('.po-category-accordion-container');

	$poCategoryAccordionContainer.on("click",'.delete-icon',function(){
		var $this =  $(this);
		var poCategoryId = $this.closest('.category-accordian-header').find('.po-category').val();
		var subCategoryId = $this.closest('.delete-image-container').find('.sub-category').val();
		var poCategory = $this.data("accordian-position");
		var $deleteTemplateCategoryPromise =$.post("delete-category-content",{poCategoryId:poCategoryId,subCategoryId:subCategoryId});
	
	    $deleteTemplateCategoryPromise.done(function(data){
			$deleteCategoryModal.html(data);
			$deleteCategoryModal.data("accordian-position",poCategory);
			openModal($deleteCategoryModal);
	    });
	 });
	
	
		
//func to delete newly added categories from  create template page as well as edit template modal

	$deleteCategoryModal.on("click", ".delete", function(){
		
		var poCategory = $deleteCategoryModal.data("accordian-position");
		$('.added-categories-accordion').each(function(){
			
			$('.added-categories-accordion').find('h3[data-accordian-position="'+ poCategory + '"],div[data-accordian-position="'+ poCategory + '"]').each(function(){;
	        	$(this).remove();
	      
			});
		});
			
		closeModal($deleteCategoryModal);
	});

//func triggers when add category link is clicked on either create template or edit template modal
	$addCategory.on("click",function(){
		
		var $getPoCategoriesPromise =$.get("get-add-po-categories.htm");
		$getPoCategoriesPromise.done(function(data){
			
			$addCategoryModal.html(data);
			openModal($addCategoryModal);
		});
	});

	
	$addCategoryModal.on("change",'#po-category',function(){
		
		var $this =$(this);
		var selected =$this.find('option:selected');
		
		var poCategoryId = selected.val();
		var $getAddPoSubCategoriesPromise =$.get("get-add-po-sub-categories.htm",{poCategoryId:poCategoryId});
		
		$getAddPoSubCategoriesPromise.done(function(data){
			var $poSubCategoryFieldSet = $('#sub-category');
			$poSubCategoryFieldSet.empty();
			$poSubCategoryFieldSet.append("<legend>SubCategory</legend>");
			
			var poSubCategoryList =data;
			
			$.each(poSubCategoryList,function(key,value){
				
				var poSubCategory = value;
				$poSubCategoryFieldSet.append('<div class="sub-categories">'
								+'<input type="checkbox" class="sub-category-chk" value="' + poSubCategory.subCategoryId + '"/>'
								+ '<label  class="textLabel">' + poSubCategory.subCategoryName + '</label>'
								+ '</div>');
				
			});
				
		});
	
	});

	$addCategoryModal.on("click",'.save-category',function(){
		
		var $selectedPoCategory = $('#po-category').find('option:selected');
		var poCategoryId= $selectedPoCategory.val();
		var subCategoryIds =[];
		var valid;
		var flag=true;
		var numOfChecked = 0;
		if(poCategoryId == ''){
			$('#po-category').addClass('errorMsgInput');
			$('.error').find('.errorMsg').text('No PoCategory selected');
			$('.error').show();
			return;
		}
		var $poSubCategoryFieldSet = $('#sub-category');
		var hasSubCategories = $poSubCategoryFieldSet.hasClass("sub-categories");
		
		$('.sub-category-chk').each(function(){
			if($(this).attr('checked'))
				numOfChecked++;
		});
		
		if(numOfChecked == 0){
			$selectedPoCategory.addClass('errorMsgInput');
			$('.error').find('.errorMsg').text('No subcategories selected');
			$('.error').show();
			return;
		}
		
		if(numOfChecked > 0){
			$('.sub-category-chk').each(function(){
				if($(this).attr('checked')){
					subCategoryIds.push($(this).val());
					valid=validate(poCategoryId,$(this).val());
					if(!valid)
						flag=false;
				}
			});			
		}
		if(!flag){
			$('.error').find('.errorMsg').text('selected category already exists');
			$('.error').show();
		}
		else{
			$getCategoryComponentsPromise =$.get("get-add-category-components.htm",{poCategoryId:poCategoryId,subCategoryIds:subCategoryIds});
			$getCategoryComponentsPromise.done(function(data){
				
				//appending categories to create template page
				$('#po-category-accordion-container').append(data);
				
				//appending to edit template modal categories (templates page)
				$('.template-accordion-container').append(data);
				
				closeModal($addCategoryModal);
			});
		  }
	
	});

	$addCategoryModal.on("click",'.cancel',function(){
		
		$('#add-category-modal').dialog('close');
	});

	// func to make sure no duplicate categories are added
	 function validate(poCategoryId,subCategoryId){
		var validate =true;
		var selectedPoCategory =poCategoryId.toString();
		var selectedSubCtaegory=subCategoryId.toString();
		var selectedPoSub =selectedPoCategory+'-'+selectedSubCtaegory;
		var $accordion=$('.po-category-accordion-container').find('.added-categories-accordion');
			
		//looping through newly added categories
		$accordion.each(function(){
				var $accordianHeader =$(this).find('.category-accordian-header');
				poSub=$accordianHeader.data("accordian-position");
				
				if(poSub ===selectedPoSub){
					
					validate=false;
				}
				else{
					return true;
				}
		});
			//looping through edit template modal categories
		var $templateAccordion=$('.template-accordion-container').find('.template-accordion');
		$templateAccordion.each(function(){
			var $accordianHeader =$(this).find('.accordian-header');
			poSub=$accordianHeader.data("accordian-position");
			
			if(poSub ===selectedPoSub){
				
				validate=false;
			}
		});
		return validate;
	}