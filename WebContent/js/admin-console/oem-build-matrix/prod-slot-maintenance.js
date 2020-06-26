selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-maintenance");

var $prodSlotMaintenanceModal = $('#prod-slot-maintenance-modal');

ModalUtil.initializeModal($prodSlotMaintenanceModal);

var $slotMaintenanceTable = $('#slot-maintenance-table');
var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");
$slotMaintenanceDataTable = $slotMaintenanceTable.DataTable({
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
		{targets: [0], width: '100px'},
		{targets: ['slot-table-header'], width: '120px'}
	],
	"language": {
	      "emptyTable": "Slots have not been created for the selected year and vehicle type combination. Create slots to continue"
	 }
});

$("#vehicletype-drpdwn, #year-drpdwn").on("change", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});

$('#create-slots-btn').on('click', function(){
	var $getCreateSlotsContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-create-slots-modal'
	});
	$getCreateSlotsContentPromise.done(function(data){
		$prodSlotMaintenanceModal.html(data);
	    ModalUtil.openModal($prodSlotMaintenanceModal);
	});
});

$('#import-btn').on('click', function(){
	var vehicleType = $('#vehicletype-drpdwn').val();
	var year = $('#year-drpdwn').val();
	
	var $getImportContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-import-slot-maintenance',
		data: {
			slotTypeId: vehicleType,
			year: year
		}
	});
	$getImportContentPromise.done(function(data){
		$prodSlotMaintenanceModal.html(data);
	    ModalUtil.openModal($prodSlotMaintenanceModal);
	});
});

$('#export-btn').on('click', function(){
	var vehicleType = $('#vehicletype-drpdwn').val();
	var vehicleTypeDesc = $('#vehicletype-drpdwn').find('option[selected="selected"]').text()
	var year = $('#year-drpdwn').val();
	
	var today = new Date();
	var filename = vehicleTypeDesc + '_' + year + '_Slots_';

	var mm = today.getMonth() + 1;
	if (mm < 10) {
		mm = '0' + mm;
	}
	filename += mm;
	
	var dd = today.getDate();
	if (dd < 10) {
		dd = '0' + dd;
	}
	filename += dd;
	
	var list = [vehicleType, year];

	DownloadUtil.downloadFileAsFormPost(baseBuildMatrixUrl + '/export-slot-maintenance.htm', filename + '.xlsx', 'list', list);
});