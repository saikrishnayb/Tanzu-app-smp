$(document).ready(function() {
	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-maintenance");

	var $slotMaintenanceTable = $('#slot-maintenance-table');
	var $vehicleTypeDrpdwn =$("#vehicletype-drpdwn");
	var $yearDrpdwn =$("#year-drpdwn");
	$slotMaintenanceDataTable = $slotMaintenanceTable.DataTable({
		"bPaginate" : true, //enable pagination
		"bStateSave" : true, //To retrieve the data on click of back button
		"sScrollY"  : "271px",//enable scroll 
		"sScrollX"  : "100%",
		"sPaginationType" : "two_button",
		"bLengthChange" : true, //enable change of records per page, not recommended
		"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bAutoWidth" : false,
		"bSort" : true, //Allow sorting by column header
		"bInfo" : true, //Showing 1 to 10 of 11 entries
		"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength" : 100, //number of records per page for pagination
		"aoColumnDefs" : [ {
			'bSortable' : true,
			'aTargets' : [ 0 ]
		} ],
		"responsive":false,
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
			//This change would allow the data table 
	    	//to adjust with dynamically after the page has been loaded
	    	$(".dataTables_scrollHead").css("width","99.8%");
	    	$(".dataTables_scrollHeadInner").css("width","98.5%");
	    	$(".dataTable").css("width","99.8%");
	    	$(".dataTables_scrollBody").css("width","100%"); 
		}
	});

	$vehicleTypeDrpdwn.on("change", function() {
		var slotTypeId=$(this).val();
		var selectedYear=$('#year-drpdwn').val();
		$.ajax({
			type : "POST",
			url : './prod-slot-maintenance.htm',
			data: {slotType: slotTypeId,year:selectedYear},
			 success: function(data){
				 window.location.reload(true);
			 }
		});
		
	});

	$yearDrpdwn.on("change", function() {
		var slotTypeId=$('#vehicletype-drpdwn').val();
		var selectedYear=$(this).val();
		$.ajax({
			type : "POST",
			url : './prod-slot-maintenance.htm',
			data: {slotType: slotTypeId,year:selectedYear}
		});
	});
});