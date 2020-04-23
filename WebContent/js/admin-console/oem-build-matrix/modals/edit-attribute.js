var $addUpdateAttributeModal = $('#add-update-attribute-modal');

$("#values").multiselect({
	close : function() {},
	minWidth : 150,
	noneSelectedText : "",
	open : function() {
		//Code added to increase the width of drop down content
		$(".ui-multiselect-menu ").css('width', '169px');
	}
});

$(".ui-multiselect-menu ").css('width', '169px');

$addUpdateAttributeModal.on("click",'#update-attr',function(){
	showLoading();
	var attributeId = $(this).data('save-attr-id');
	var attrValueIds = $addUpdateAttributeModal.find('#values').val();
	var notSelectedIds = [];
	$('#values').find('option').not(':selected').each(function() {
		notSelectedIds.push($(this).val());
	});
	
	var $updateAttributePromise = $.ajax({
			type: "POST",
			url:'./update-attribute.htm',
			global: false,
			data: {attributeId: attributeId, attrValueIds: attrValueIds}
		});
	$updateAttributePromise.done(function(data){
		notSelectedIds.forEach(function(id){
			$('#attribute-table').find('.non-selected-attrvalue[data-attribute-value-id="' + id + '"]').remove();;
		})
//				$('#attribute-table').find('.edit-attribute-id').each(function(){
//					var attributeIdCheck = $(this).val();
//					var attributeIdMatch = (attributeIdCheck ==attributeId) ;
//				
//					if(attributeIdMatch){
//						
//						var $attributeRow = $(this).closest('tr');
//						var nRow = $attributeRow[0];
//						$attributeTable.dataTable().fnUpdate( attrValuesString, nRow, 2, false);
//						
//					}
//				});
		closeModal($addUpdateAttributeModal);
		hideLoading();
	});
	$updateAttributePromise.fail(function(xhr, ajaxOptions, thrownError) {
				  if(xhr.responseText.indexOf('Error Processing the updating Attribute')>0){
					  $('.errorMsg').text("Error Processing the updating Attribute.");
						 $('.error').show();
				  }
				  hideLoading();
	});
	
	
});

$addUpdateAttributeModal.on("click",'#create-attr',function(){
	showLoading();
	var attributeId = $(this).data('save-attr-id');
	var $form = $('#editAttributeForm[data-save-attr-id="'+attributeId+'"]');
	var attributeData = $form.serialize();
	$form.find('.attributeId').val();
	var attributeName = $form.find('#attributeName').val();
	var attributeValue = $addUpdateAttributeModal.find('#attributeValue').val().toUpperCase();
	
	
	var isValid = validateAttributeValue($form);
	if(isValid){
	$.ajax({
		  type: "POST",
		  url: "./check-unique-attribute-value.htm",
		  cache:false,
		  data: {attributeId : attributeId, attributeValue: attributeValue},
		  success: function(isUnique){
			  if(isUnique){
				  	showLoading();
				  	var attrValues=$form.find('.attr-original-values').val().slice(1,-1).split(',').map(function(item) {return item.trim();});
				  	attrValues.push(attributeValue);
				  	var attrValuesString= '';
				  	 for (i = 0; i < attrValues.length; i++) {
				  		if(attrValues[i]!='') 
						attrValuesString += ' <a class="buttonPrimary non-selected-attrvalue">'+attrValues[i]+'</a>';
					};
					
					url='./add-attribute.htm';
				$.ajax({
					url : url,
					type: "POST",  
					data: {attributeId : attributeId, attributeValue: attributeValue},
					success : function(attrValue) {
							showLoading();
							var $attributeRow = $('#attribute-table').find('.attribute-row[data-attribute-id="' + attributeId + '"]');
							$attributeRow.find('.value-td').append('<span class="badge non-selected-attrvalue" data-attribute-value-id="' + attrValue.attributeValueId + '">' + attrValue.attributeValue + '</span>');
					},
				}); 
				closeModal($addUpdateAttributeModal);
				hideLoading();
			  }else{
				hideLoading();
				$("#ErrorMsg span").text("Provided attribute value already exists, check the data and try again");
				$("#ErrorMsg").show();
				$("#attributeValue").addClass("errorMsgInput");
				}
		  },
		});
}
	else{
		hideLoading();
		}
});

function validateAttributeValue($form){
	var valid = validateFormTextFields($form);
	var attributeValue = $('#add-attribute-modal').find('#attributeValue').val();
	
	if(attributeValue == '')
		{
		valid = false;
		$("#ErrorMsg span").text("Attribute value must be provided");
		$("#ErrorMsg").show();
		$("#attributeValue").addClass("errorMsgInput");
		}
return valid;
}

function enableUpdate()
{
	var originalValueList=$('.attr-original-values').val().slice(1,-1).split(',');
	var optionSelected = $('#values').val()
	if(originalValueList.length!=optionSelected.length)
		$('#update-attr').removeClass('buttonDisabled');
	else
		$('#update-attr').addClass('buttonDisabled');
	
}