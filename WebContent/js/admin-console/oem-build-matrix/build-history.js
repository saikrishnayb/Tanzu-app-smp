selectCurrentNavigation("tab-oem-build-matrix", "left-nav-build-history");

var $buildHistoryTable = $('#build-history-table');
var $confirmReworkOrDeleteModal = $('#confirmReworkOrDeleteModal')
ModalUtil.initializeModal($confirmReworkOrDeleteModal);

$buildHistoryDataTable = $buildHistoryTable.DataTable({ //All of the below are optional
	"bPaginate" : true, //enable pagination
	"bStateSave" : false, //To retrieve the data on click of back button
	"sPaginationType" : "two_button",
	"aaSorting" : [], //default sort column
	"aoColumnDefs" : [
		{
			'bSortable' : false,
			'aTargets' : [ 0, 8 ]
		},
		{
			'width' : 210,
			'aTargets' : [ 0 ]
		} ],
	"dom" : '<"build-history-table-top"l>tipr',
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
	},
	"initComplete" : function() {
		var showStartBuildBtn = $buildHistoryTable.data('show-start-build-btn');
		if (showStartBuildBtn) {
			$('.build-history-table-top').append('<a href="' + baseBuildMatrixUrl + '/order-summary" class="buttonSecondary floatRight">Start Build</a>');
		}
		else {
			$('#cancel-btn').remove();
			$('#rework-btn').remove();
		}
	}
});

$buildHistoryTable.on('click', '#rework-btn', function() {
	openConfirmModal(false, $(this).data('build-id'));
});

$buildHistoryTable.on('click', '#cancel-btn', function() {
	openConfirmModal(true, $(this).data('build-id'));

});

function openConfirmModal(isDeleteConfirmModal, buildId) {
	if (isDeleteConfirmModal) {
		$("#reworkOrDeleteConfirm").html('Confirm');
		$("#reworkOrDeleteConfirm").attr("delete", "Y");
		$('#confirmMessage').text("Cancelling this build will delete all results and cannot be undone. Do you want to continue?");
	} else {
		$("#reworkOrDeleteConfirm").html('Rework');
		$('#confirmMessage').text("Reworking this build will clear previous match results and cannot be undone. Do you want to continue?");
	}
	$("#build-id").val(buildId);
	ModalUtil.openModal($confirmReworkOrDeleteModal);
}

$confirmReworkOrDeleteModal.on("click", '#reworkOrDeleteConfirm', function() {
	var isDeleteBuild = $("#reworkOrDeleteConfirm").attr("delete");
	var buildId = parseInt($("#build-id").val());
	if (isDeleteBuild == "Y") //delete Build flow
	{
		$.ajax({
			type : "POST",
			url : "./delete-build.htm",
			cache : false,
			data : {
				buildId : buildId
			},
			success : function(data) {
				ModalUtil.closeModal($confirmReworkOrDeleteModal);
				location.assign('./build-history.htm');
				
			},
		});
	} else //Rework Build flow
	{
		$.ajax({
			type : "POST",
			url : "./rework-build.htm",
			cache : false,
			data : {
				buildId : buildId
			},
			success : function(data) {
				ModalUtil.closeModal($confirmReworkOrDeleteModal);
				location.assign('./build-history.htm');
			},
		});
	}
});


function closeConfirmDialog() {
	ModalUtil.closeModal($confirmReworkOrDeleteModal);
}