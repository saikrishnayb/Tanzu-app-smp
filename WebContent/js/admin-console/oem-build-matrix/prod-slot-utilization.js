selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-utilization");

var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");
var $prodSlotUtilizationModal = $('#prod-slot-utilization');

ModalUtil.initializeModal($prodSlotUtilizationModal);


var $slotUtilizationTable = $('#slot-utilization-table');
var $slotUtilizationDataTable = $slotUtilizationTable.DataTable({
	'fixedColumns': {
        leftColumns: 2
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
		{targets: [0], width: '100px'},
		{targets: ['slot-table-header'], width: '140px'}
	],
	"language": {
	      "emptyTable": "Slots have not been created for the selected year and vehicle type combination. Create slots to continue"
	 }
});

$("#search-button").on("click", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});

$('.release-units-link').on('click', function(){
	var $this = $(this);
	var $td = $this.closest('td');
	var $row = $this.closest('tr');
	
	var region = $td.data('region');
	var dateId = $row.data('prod-slot-date-id');
	var plantId = $td.data('plant-id');
	var slotId = $td.data('slot-id');
	var slotRegionId = $td.data('slot-region-id');
	var regionDesc = $td.data('region-desc');
	
	var $getReleaseUnitsContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-release-units-modal',
		data: {
			region: region,
			dateId: dateId,
			plantId: plantId,
			slotId: slotId,
			slotRegionId: slotRegionId,
			regionDesc: regionDesc
		}
	});
	$getReleaseUnitsContentPromise.done(function(data){
		$prodSlotUtilizationModal.html(data);
	    ModalUtil.openModal($prodSlotUtilizationModal);
	});
});