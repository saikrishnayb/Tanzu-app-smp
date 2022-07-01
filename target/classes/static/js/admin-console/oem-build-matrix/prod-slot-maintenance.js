selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-maintenance");

var $prodSlotMaintenanceModal = $('#prod-slot-maintenance-modal');

ModalUtil.initializeModal($prodSlotMaintenanceModal);

ritsu.storeInitialFormValues('#slot-maintenance-form');

var $slotMaintenanceTable = $('#slot-maintenance-table');
var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");
$slotMaintenanceDataTable = $slotMaintenanceTable.DataTable({
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
		{targets: ['slot-table-header'], width: '120px'}
	],
	"language": {
	      "emptyTable": "Slots have not been created for the selected year and vehicle type combination. Create slots to continue"
	 }
});

$("#search-button").on("click", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});

$('.available-slot-input').on('focusin', function(){
	var $td = $(this).closest('.available-units-td');
	$td.find('.allocated-region-slots').removeClass('hidden');
});

$('.available-slot-input').on('focusout', function(){
	var $td = $(this).closest('.available-units-td');
	$td.find('.allocated-region-slots').addClass('hidden');
});

$('.available-slot-input').on('input', function(){
	this.value = this.value.replace(/[^0-9]/g,'');
	var inputValue = this.value;
	var $td = $(this).closest('.available-units-td');
	var allocatedSlots =  parseInt(this.getAttribute('data-allocated-slots'));
	
	if(inputValue == ''){
		$('#save-region-slots-btn').addClass('buttonDisabled');
		$(this).addClass('errorMsgInput');
		return false;
	}
	else{
		$(this).removeClass('errorMsgInput');
	}
	if(ritsu.isFormDirty('#slot-maintenance-form'))
		$('#save-slots-btn').removeClass('buttonDisabled');
	
	var value = parseInt(inputValue);
	
	if(value < allocatedSlots) {
		$('#save-slots-btn').addClass('buttonDisabled');
		$(this).addClass('errorMsgInput');
		$td.find('.allocated-region-slots').addClass('errorMsg');
	}
	else {
		$('#save-slots-btn').removeClass('buttonDisabled');
		$(this).removeClass('errorMsgInput');
		$td.find('.allocated-region-slots').removeClass('errorMsg');
	}
	
	$('.available-slot-input').each(function(){
		if($(this).hasClass('errorMsgInput'))
			$('#save-slots-btn').addClass('buttonDisabled');
	});
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
	var slotIndex = 0;
	$('#slot-maintenance-form').find('.available-slot-input').each(function(index) {
		var initialValue = this.getAttribute('data-initial-value');
		var newValue = parseInt(this.value);
		var $td = $(this).closest('.available-units-td');
		if(initialValue == newValue) {
			$td.find(':input').each(function(innerIndex){
				$(this).attr("disabled", "disabled");
			});
		}
		else{
			$td.find(':input').each(function(innerIndex) {
		      this.name = this.name.replace('XXX', slotIndex);
		    });
			slotIndex++;
		}
	});
	
	var serializedForm = $('#slot-maintenance-form').serialize();
	
	$('#slot-maintenance-form').find('.available-slot-input').each(function(index) {
		var $td = $(this).closest('.available-units-td');
		$td.find(':input').each(function() {
			this.name = this.name.replace(/\[[0-9]*\]/, '[XXX]');
		});
	})
	
	var $saveSlotsPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/save-slots',
		data: serializedForm
	});
	
	$saveSlotsPromise.done(function(){
		$('#save-slots-btn').addClass('buttonDisabled');
		$('#slot-maintenance-form').find(':input:disabled').removeAttr("disabled");
    	ritsu.storeInitialFormValues('#slot-maintenance-form');
	});
})