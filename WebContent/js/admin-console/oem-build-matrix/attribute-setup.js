var $addUpdateAttributeModal = $('#add-update-attribute-modal');

ModalUtil.initializeModal($addUpdateAttributeModal);

selectCurrentNavigation("tab-oem-build-matrix", "left-nav-attribute-maintenance");

$attributeTable = $('#attribute-table').DataTable({ //All of the below are optional
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
	"dom": "t",
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

$('#attribute-table').on("click",'.edit-attribute',function(){
		
		var $this =$(this);
		var attributeId = $this.closest('.attribute-row').find('.edit-attribute-id').val();
		
		var $getEditAttributeContentPromise =$.get("get-edit-attribute-content.htm",{attributeId:attributeId});
		$('.editAttributeForm[data-save-attr-id="'+attributeId+'"]').remove();
		$getEditAttributeContentPromise.done(function(data){
			$addUpdateAttributeModal.html(data);
		    ModalUtil.openModal($addUpdateAttributeModal);
		});
		
	});

$('#attribute-table').on("click",'.add-attribute',function(){
	
	var $this =$(this);
	var attributeId = $this.closest('.attribute-row').find('.edit-attribute-id').val();
	
	var $getAddAttributeContentPromise =$.get("get-add-attribute-content.htm",{attributeId:attributeId});
	$('.editAttributeForm[data-save-attr-id="'+attributeId+'"]').remove();
	$getAddAttributeContentPromise.done(function(data){
		$addUpdateAttributeModal.html(data);
	    ModalUtil.openModal($addUpdateAttributeModal);
	});
	
});

$('#attribute-search').on("input", function(){
	$attributeTable.search($(this).val()).draw() ;
});

$addUpdateAttributeModal.on("click",'#update-attr',function(){
	var attributeId = $(this).data('save-attr-id');
	var $form = $('#editAttributeForm[data-save-attr-id="'+attributeId+'"]');
	var attrValueIds = $addUpdateAttributeModal.find('#values').val();
	var notSelectedIds = [];
	$('#values').find('option').not(':selected').each(function() {
		notSelectedIds.push($(this).val());
	});
	
	var isValid = ritsu.validate($form);
	if(!isValid) {
		//We need to show the error messages explicitly here since we're using a jQuery multiselect, which hides the element ritsu would normally highlight
		ritsu.showErrorMessages($form);
		return;
	}
	
	var $updateAttributePromise = $.ajax({
			type: "POST",
			url:'./update-attribute.htm',
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
		ModalUtil.closeModal($addUpdateAttributeModal);
	});
});

$addUpdateAttributeModal.on("click",'#create-attr',function(){
	var attributeId = $(this).data('save-attr-id');
	var $form = $('#editAttributeForm[data-save-attr-id="'+attributeId+'"]');
	var attributeData = $form.serialize();
	$form.find('.attributeId').val();
	var attributeName = $form.find('#attributeName').val();
	var attributeValue = $addUpdateAttributeModal.find('#attributeValue').val().toUpperCase();
	
	var isValid = ritsu.validate($form);
	if(isValid){
	$.ajax({
		  type: "POST",
		  url: "./check-unique-attribute-value.htm",
		  cache:false,
		  data: {attributeId : attributeId, attributeValue: attributeValue},
		  success: function(isUnique){
			  if(isUnique){
				$.ajax({
					url : './add-attribute.htm',
					type: "POST",  
					data: {attributeId : attributeId, attributeValue: attributeValue},
					success : function(attrValue) {
							var $attributeRow = $('#attribute-table').find('.attribute-row[data-attribute-id="' + attributeId + '"]');
							$attributeRow.find('.value-td').append('<span class="badge non-selected-attrvalue" data-attribute-value-id="' + attrValue.attributeValueId + '">' + attrValue.attributeValue + '</span>');
							ModalUtil.closeModal($addUpdateAttributeModal);
					},
				}); 
				
			  }else{
				addErrorMessage("Provided attribute value already exists, check the data and try again");
				$("#attributeValue").closest('form-group').addClass('has-error');
			  }
		  },
		});
	}
});

//# sourceURL=attribute-setup.js