var allNodes;
$(document).ready(function() {
	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-attribute-maintenance");
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
		title : 'Add Attribute Value',
		closeOnEscape : false,
		open: function(event, ui) { }
	});
	
	$editAttributeModal.on("click",'#update-attr',function(){
		
		var $form = $('#editAttributeForm');
		var attributeData =$form.serialize();
		var attributeId = $form.find('.attributeId').val();
		var attributeOptionGrp =$form.find('#optionGroup').val();
		var attributeName = $form.find('#attributeName').val();
		var attrValues = $form.find('#values').find('option:selected');
		var attrValuesString= '';
		$(attrValues).each(function(){
			attrValuesString += ' <a class="buttonPrimary non-selected-attrvalue">'+$(this).val()+'</a>';
		});
		
	//	var isValid =validate($form);
		
			var $updateAttributePromise = $.ajax({
				type: "POST",
				url:'./update-attribute.htm',
				global: false,
				data: attributeData
			});
			$updateAttributePromise.done(function(data){
					
					$('#attribute-table').find('.edit-attribute-id').each(function(){
						var attributeIdCheck = $(this).val();
						var attributeIdMatch = (attributeIdCheck ==attributeId) ;
					
						if(attributeIdMatch){
							
							var $attributeRow = $(this).closest('tr');
							var nRow = $attributeRow[0];
							$attributeTable.dataTable().fnUpdate( attrValuesString, nRow, 2, false);
							
						}
					});
					closeModal($editAttributeModal);
				});
		    $updateAttributePromise.fail(function(xhr, ajaxOptions, thrownError) {
					  if(xhr.responseText.indexOf('Error Processing the updating Attribute')>0){
						  $('.errorMsg').text("Error Processing the updating Attribute.");
							 $('.error').show();
					  }
				});
		
		
	});
	
	$addAttributeModal.on("click",'.create-attr',function(){
		
		var $form = $('#editAttributeForm');
		var attributeData =$form.serialize();
		var attributeId = $form.find('.attributeId').val();
		var attributeName = $form.find('#attributeName').val();
		var attributeValue = $addAttributeModal.find('#attributeValue').val();
		
			var $addAttributePromise = $.ajax({
				type: "POST",
				url:'./add-attribute.htm',
				global: false,
				data: {attributeId : attributeId, attributeValue: attributeValue}
			});
			$addAttributePromise.done(function(data){
				 closeModal($editAttributeModal);
			});
		    $addAttributePromise.fail(function(xhr, ajaxOptions, thrownError) {
					  if(xhr.responseText.indexOf('Error Processing the saving Attribute')>0){
						  $('.errorMsg').text("Error Processing the saving Attribute.");
							 $('.error').show();
					  }
				});
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
			$getEditAttributeContentPromise.done(function(data){
				$editAttributeModal.html(data);
				openModal($editAttributeModal);
			});
			
		});
	
	$('#attribute-table').on("click",'.add-attribute',function(){
		
		var $this =$(this);
		var attributeId = $this.closest('.attribute-row').find('.edit-attribute-id').val();
		
		var $getAddAttributeContentPromise =$.get("get-add-attribute-content.htm",{attributeId:attributeId});
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
