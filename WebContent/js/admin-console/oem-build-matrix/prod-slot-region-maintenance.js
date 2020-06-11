selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-region-maintenance");

var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");
var $prodSlotRegionMaintenanceModal = $('#prod-slot-region-maintenance-modal');

ModalUtil.initializeModal($prodSlotRegionMaintenanceModal);


var $slotRegionMaintenanceTable = $('#slot-region-maintenance-table');
var $slotRegionMaintenanceDataTable = $slotRegionMaintenanceTable.DataTable({
	'fixedColumns': {
        leftColumns: 1
    },
	'paging': false,
	'ordering': false,
	'info': false,
	'scrollX': true,
	'scrollY': '500px',
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