var allNodes;
$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-attribute-maintenance");
	var $editAttributeModal =$('#edit-attribute-modal');
	var $addAttributeModal =$('#add-attribute-modal');

	$attributeTable = $('#attribute-table').dataTable({ //All of the below are optional
		"bPaginate" : true, //enable pagination
		"bStateSave" : true, //To retrieve the data on click of back button
		"sPaginationType" : "two_button",
		"aaSorting" : [], //default sort column
		"aoColumnDefs" : [ {
			'bSortable' : false,
			'aTargets' : [ 0 ]
		} ],
		"bLengthChange" : true, //enable change of records per page, not recommended
		"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bAutoWidth" : false,
		"bSort" : true, //Allow sorting by column header
		"bInfo" : true, //Showing 1 to 10 of 11 entries
		"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength" : 100, //number of records per page for pagination
		"oLanguage" : {
			"sEmptyTable" : "No Results Found"
		},
		//"sScrollY": 246, //Adds a vertical scroll bar if the content exceeds this amount
		//"sScrollXInner": "100%" 
		"fnDrawCallback" : function() { //This will hide the pagination menu if we only have 1 page.
			var paginateRow = $(this).parent().children('div.dataTables_paginate');
			var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

			if (pageCount > 1) {
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
	});
	
	$editAttributeModal.dialog({
		autoOpen : false,
		cache : false,
		modal : true,
		dialogClass : 'popupModal',
		width : 400,
		minHeight: 200,
		position : {
			my : "center top",
			at : "center top",
			of : window
		},
		resizable : false,
		title : 'Update Attribute Value',
		closeOnEscape : false,
		open: function(event, ui) { }
	});
	
	$addAttributeModal.dialog({
		autoOpen : false,
		cache : false,
		modal : true,
		dialogClass : 'popupModal',
		width : 400,
		minHeight: 200,
		position : {
			my : "center top",
			at : "center top",
			of : window
		},
		resizable : false,
		title : 'Add Attribute',
		closeOnEscape : false,
		open: function(event, ui) { }
	});
	
	$editAttributeModal.on("click",'#update-attr',function(){
		showLoading();
		var attributeId = $(this).data('save-attr-id');
		var attrValueIds = $editAttributeModal.find('#values').val();
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
//					$('#attribute-table').find('.edit-attribute-id').each(function(){
//						var attributeIdCheck = $(this).val();
//						var attributeIdMatch = (attributeIdCheck ==attributeId) ;
//					
//						if(attributeIdMatch){
//							
//							var $attributeRow = $(this).closest('tr');
//							var nRow = $attributeRow[0];
//							$attributeTable.dataTable().fnUpdate( attrValuesString, nRow, 2, false);
//							
//						}
//					});
			closeModal($editAttributeModal);
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
	
	$addAttributeModal.on("click",'#create-attr',function(){
		showLoading();
		var attributeId = $(this).data('save-attr-id');
		var $form = $('#editAttributeForm[data-save-attr-id="'+attributeId+'"]');
		var attributeData = $form.serialize();
		$form.find('.attributeId').val();
		var attributeName = $form.find('#attributeName').val();
		var attributeValue = $addAttributeModal.find('#attributeValue').val().toUpperCase();
		
		
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
					closeModal($addAttributeModal);
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
	
	$('#attribute-table').on("click",'.edit-attribute',function(){
			
			var $this =$(this);
			var attributeId = $this.closest('.attribute-row').find('.edit-attribute-id').val();
			
			var $getEditAttributeContentPromise =$.get("get-edit-attribute-content.htm",{attributeId:attributeId});
			$('.editAttributeForm[data-save-attr-id="'+attributeId+'"]').remove();
			$getEditAttributeContentPromise.done(function(data){
				$editAttributeModal.html(data);
				openModal($editAttributeModal);
			});
			
		});
	
	$('#attribute-table').on("click",'.add-attribute',function(){
		
		var $this =$(this);
		var attributeId = $this.closest('.attribute-row').find('.edit-attribute-id').val();
		
		var $getAddAttributeContentPromise =$.get("get-add-attribute-content.htm",{attributeId:attributeId});
		$('.editAttributeForm[data-save-attr-id="'+attributeId+'"]').remove();
		$getAddAttributeContentPromise.done(function(data){
			$addAttributeModal.html(data);
			openModal($addAttributeModal);
		});
		
	});
});

function enableUpdate()
{
	var originalValueList=$('.attr-original-values').val().slice(1,-1).split(',');
	var optionSelected = $('#values').val()
	console.log(optionSelected);
	if(originalValueList.length!=optionSelected.length)
		$('#update-attr').removeClass('buttonDisabled');
	else
		$('#update-attr').addClass('buttonDisabled');
	
}
function validate($form){
	var valid = validateFormTextFields($form);
	if(!valid){
		$('.errorMsg').text("please enter valid value");
		$('.error').show();
	}
	var attrOptionGrp =$form.find('#optionGroup').val();
	var attrName=$form.find('#attributeName').val();
	var attrValues=$form.find('#values').val();
	
	if(attrOptionGrp == ''||attrName=='' || attrValues=='')
		{
		$('.errorMsg').text("please enter required fields");
		$('.error').show();
		}

return valid;
}

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
