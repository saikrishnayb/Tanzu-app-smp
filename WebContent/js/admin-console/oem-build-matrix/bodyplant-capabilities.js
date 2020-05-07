var allNodes;
$(document).ready(function() {

	$table = $('#plant-capablity-table').dataTable({ //All of the below are optional
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
		'<a href="' + baseBuildMatrixUrl + '/maintenance-summary" onclick="javascript:loadProcessImage();" class="buttonSecondary floatRight">Back</a>' +
		'</div>';

	$("#plant-capablity-table_wrapper").prepend(strHTML);

	$('#edit-dimension-popup-modal').dialog({
		autoOpen : false,
		cache : false,
		modal : true,
		dialogClass : 'popupModal',
		width : 400,
		position : {
			my : "center top",
			at : "center top",
			of : window
		},
		resizable : false,
		title : 'Edit Dimension- Body Length',
		closeOnEscape : false
	});
});

function getContextRoot() {
	return window.sessionStorage.getItem('baseAppUrl');
}

function loadEditDimensionForm(attributeId) {
	var url = getContextRoot() + "/admin-console/oem-build-matrix/load-edit-dimension-popup-modal.htm";
	$.ajax({
		url : url,
		cache : false,
		type : "POST",
		data : {
			attributeId : attributeId
		},
		success : function(data) {
			$("#edit-dimension-popup-modal").html(data);
			$('#edit-dimension-popup-modal').dialog('open');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 500) {
			} else if (textStatus == "timeout") {
			}
			return;
		}
	});
}