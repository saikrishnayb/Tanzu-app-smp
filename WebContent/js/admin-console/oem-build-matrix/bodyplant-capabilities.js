var $EditDimensionModal = $('#edit-dimension-popup-modal');

ModalUtil.initializeModal($EditDimensionModal);

selectCurrentNavigation("tab-oem-build-matrix", "left-nav-maintenance-summary");
$table = $('#plant-capablity-table').DataTable({ //All of the below are optional
	"bPaginate" : true, //enable pagination
	"bStateSave" : true, //To retrieve the data on click of back button
	"sPaginationType" : "two_button",
	"aaSorting" : [], //default sort column
	"aoColumnDefs" : [ {
		'bSortable' : false,
		'aTargets' : [ 0 ]
	} ],
	"bLengthChange" : true, //enable change of records per page, not recommended
	"bFilter" : false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
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

var strHTML = '<div class="button-div-bottom floatRight">' +
			  '<a href="' + baseBuildMatrixUrl + '/maintenance-summary" class="buttonSecondary floatRight">Back</a>' +
			  '</div>';

$("#plant-capablity-table_wrapper").prepend(strHTML);

$('#plant-capablity-table').on("click", '#edit-capability', function() {
	var row = $(this).closest('tr');
	var attributeId = row.find('.edit-attribute-id').val();
	var plantId = row.find('.edit-plant-id').val();
	var attributeKey = row.find('.edit-attribute-key').val();
	var attributeName = row.find('.edit-attribute-name').val();

	var $getEditDimensionPromise = $.get("load-edit-dimension-popup-modal", {
		attributeId : attributeId,
		plantId : plantId,
		key : attributeKey,
		attributeName : attributeName
	});
	$getEditDimensionPromise.done(function(data) {
		$EditDimensionModal.html(data);
		ModalUtil.openModal($EditDimensionModal);
	});

});

$EditDimensionModal.on("click", '#update-capability', function() {

	var capabilityUpdatelist = [];
	$('input[type=checkbox]:not(:checked)').each(function() {
		capabilityUpdatelist.push($(this).val());
	});

	if (capabilityUpdatelist == 0) {
		capabilityUpdatelist.push('');
	}

	var capabilityNotUpdatelist = [];
	$('#attribute-values-div input:checked').each(function() {
		capabilityNotUpdatelist.push($(this).val());
	});

	if (capabilityUpdatelist && capabilityUpdatelist.length != 0) {
		var plantId = $EditDimensionModal.find('.plantId').val();
		var attributeKey = $EditDimensionModal.find('.attributeKey').val();
		var attributeId = $EditDimensionModal.find('.attributeId').val();

		var $updateCapabilityPromise = $.ajax({
			type : "POST",
			url : './update-capability.htm',
			data : {
				plantId : plantId,
				attributeKey : attributeKey,
				capabilityUpdatelist : capabilityUpdatelist
			}
		});
		$updateCapabilityPromise.done(function(data) {
			var $editAttribute = $('#plant-capablity-table').find('.user-row[data-attribute-id="' + attributeId + '"]');
			capabilityUpdatelist.forEach(function(id) {
				$editAttribute.find('.selected-attrvalue[data-attribute-value-id="' + id + '"]').addClass('selected-attrvalue badge-danger');
				$editAttribute.find('.non-selected-attrvalue[data-attribute-value-id="' + id + '"]').addClass('selected-attrvalue badge-danger');
			});
			capabilityNotUpdatelist.forEach(function(id) {
				$editAttribute.find('.selected-attrvalue[data-attribute-value-id="' + id + '"]').addClass('non-selected-attrvalue badge');
				$editAttribute.find('.selected-attrvalue[data-attribute-value-id="' + id + '"]').removeClass('selected-attrvalue badge-danger');
				$editAttribute.find('.selected-attrvalue[data-attribute-value-id="' + id + '"]').addClass('non-selected-attrvalue badge');
			});

			ModalUtil.closeModal($EditDimensionModal);

		});
	} else {
		addErrorMessage("Error Processing the Body Plant Exception update.");
	}
});