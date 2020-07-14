selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-maintenance");

var $prodSlotMaintenanceModal = $('#prod-slot-maintenance-modal');

ModalUtil.initializeModal($prodSlotMaintenanceModal);

ritsu.storeInitialFormValues('#slot-maintenance-form');

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

$('.available-slot-input').on('input', function(){
	
	if(ritsu.isFormDirty('#slot-maintenance-form'))
		$('#save-slots-btn').removeClass('buttonDisabled');
	
})

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
	if($(this).hasClass('buttonDisabled'))
		return false;
	
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
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var vehicleType = $('#vehicletype-drpdwn').val();
	var year = $('#year-drpdwn').val();
	
	var $getExportContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-export-slot-maintenance',
		data: {
			slotTypeId: vehicleType,
			year: year
		}
	});
	$getExportContentPromise.done(function(data){
		$prodSlotMaintenanceModal.html(data);
	    ModalUtil.openModal($prodSlotMaintenanceModal);
	});
});

$('#save-slots-btn').on('click', function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var serializedForm = $('#slot-maintenance-form').serialize();
	
	var $saveSlotsPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/save-slots',
		data: serializedForm
	});
	
	$saveSlotsPromise.done(function(){
		$('#save-slots-btn').addClass('buttonDisabled');
    	ritsu.storeInitialFormValues('#slot-maintenance-form');
	});
})