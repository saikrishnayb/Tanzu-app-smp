var allNodes;
var $addUpdateAttributeModal = $('#add-update-attribute-modal');

ModalUtil.initializeModal($addUpdateAttributeModal);

$(document).ready(function() {
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
	
	/*$("#values").multiselect({
		close : function() {},
		minWidth : 150,
		noneSelectedText : "",
		open : function() {
			//Code added to increase the width of drop down content
			$(".ui-multiselect-menu ").css('width', '169px');
		}
	});
	
	$(".ui-multiselect-menu ").css('width', '169px');*/
	
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
});

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

//# sourceURL=edit-attribute.js