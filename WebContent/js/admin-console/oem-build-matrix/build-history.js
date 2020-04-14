selectCurrentNavigation("tab-oem-build-matrix", "left-nav-build-history");

var $buildHistoryTable = $('#build-history-table');

$buildHistoryDataTable = $buildHistoryTable.DataTable({ //All of the below are optional
	"bPaginate" : true, //enable pagination
	"bStateSave" : true, //To retrieve the data on click of back button
	"sPaginationType" : "two_button",
	"aaSorting" : [], //default sort column
	"aoColumnDefs" : [ {
		'bSortable' : false,
		'aTargets' : [ 8 ]
	} ],
	"dom": '<"build-history-table-top"l>tipr',
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
	"initComplete": function() {
		var showStartBuildBtn = $buildHistoryTable.data('show-start-build-btn');
		if(showStartBuildBtn){
			$('.build-history-table-top').append('<a href="' + baseBuildMatrixUrl + '/order-summary" onclick="javascript:loadProcessImage();" class="buttonSecondary floatRight">Start Build</a>');
		}
	}
});

