$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-templates");
	
	//corresponding child checkboxes are checked when parent checkbox(mfr/corp) is checked
	 $("input[type='checkbox']").change(function () {
	        $(this).siblings('ul').find("input[type='checkbox']").prop('checked', this.checked);
	    });
	
	 $('.child-vendor').on("change",function(){
		 var $vendor = $(this);
		 var checked =false;
		
		 $vendor.closest('.corp').find("input[type='checkbox']").each(function(){
			 if($(this).is(':checked')){
				 checked =true;
			 }
			 else
				 checked =false;
		});
		if(checked==true){
			$(this).closest('.corp').find('.parent-corp').prop('checked', true);
		}
			 
	 });
	
	//mfr-corp-vendor_location jstree
	$("#jstree-mfr-div").jstree({
		
		 "core" : {"animation" : 75,
			 "initially_open": ["mfr-corp"] },
		 "themes" : {"theme" : "default",
		   "dots"  : false}		
});
	
	$('.save-template').on("click",function(){	
		
		var $checked = $("input[name='vendor-number']:checked");
		var checked = $checked.length;
		
		if(checked=== 0){
			$('#errorMessage').find('.errorMsg').text("please select vendor");
			$('.add-row-plus').after($('#errorMessage').show());
			return false;
		}
		if(!($('#po-category-accordion-container').html().trim())){
			
			$('#errorMessage').find('.errorMsg').text("please add category");
			$('.save-btn').after($('#errorMessage').show());
			return false;
		}
		
		$checked.each(function(){
				
			var vendorNumber =$(this).val();
			var corpCode =$(this).closest('.corp').find('.parent-corp').val();
					
			$addTemplatePromise = $.post("add-template.htm",{corpCode:corpCode,vendorNumber:vendorNumber});
			$addTemplatePromise.done(function(data){
				var templateId =data;
				addTemplateComponents(templateId);
			});
		});
			window.location.href = './templates.htm';
 });
	
	$('.cancel-back').on("click",function(){	
		window.location.href = './templates.htm';
	});

	});
	function checkVisibileEdiatableRequired (isVisibleEditablerequired){
		if(isVisibleEditablerequired)
			return 1;
		else return 0;
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