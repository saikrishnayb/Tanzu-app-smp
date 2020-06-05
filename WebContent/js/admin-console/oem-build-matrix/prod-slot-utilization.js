selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-utilization");

var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");


var $slotUtilizationTable = $('#slot-utilization-table');
var $slotUtilizationDataTable = $slotUtilizationTable.DataTable({
	'paging': false,
	'ordering': false,
	'info': false,
	'scrollX': true,
	'scrollY': '60vh',
	'lengthChange': false,
	'searching': false,
	'fixedHeader': true,
	'autoWidth': true,
	'responsive': false,
	'columnDefs': [
		{targets: [0], width: '100px'}
	]
});

$("#vehicletype-drpdwn, #year-drpdwn").on("change", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});
