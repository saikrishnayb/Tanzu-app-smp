selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-utilization");

var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");
var $prodSlotUtilizationModal = $('#prod-slot-utilization');

ModalUtil.initializeModal($prodSlotUtilizationModal);


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

$('.release-units-link').on('click', function(){
	var $this = $(this);
	var $row = $this.closest('tr');
	
	var regionId = $row.data('region-id');
	var dateId = $row.data('date-id');
	var plantId = $row.data('plant-id');
	
	var $getReleaseUnitsContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-release-units-modal',
		data: {
			regionId: regionId,
			dateId: dateId,
			plantId: plantId
		}
	});
	$getReleaseUnitsContentPromise.done(function(data){
		$prodSlotUtilizationModal.html(data);
	    ModalUtil.openModal($prodSlotUtilizationModal);
	});
});