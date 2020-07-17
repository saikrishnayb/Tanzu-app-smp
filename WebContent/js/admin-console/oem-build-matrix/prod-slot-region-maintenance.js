selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-region-maintenance");

var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");
var $prodSlotRegionMaintenanceModal = $('#prod-slot-region-maintenance-modal');

ModalUtil.initializeModal($prodSlotRegionMaintenanceModal);

ritsu.storeInitialFormValues('#region-slot-maintenance-form');

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
		{targets: [0], width: '100px'},
		{targets: ['slot-table-header'], width: '120px'}
	],
	"language": {
	      "emptyTable": "Slots have not been created for the selected year and vehicle type combination. Create slots to continue"
	 }
});

$('.available-slot-input').on('focusin', function(){
	var $td = $(this).closest('.available-units-td');
	$td.find('.unallocated-region-slots').removeClass('hidden');
});

$('.available-slot-input').on('focusout', function(){
	var $td = $(this).closest('.available-units-td');
	$td.find('.unallocated-region-slots').addClass('hidden');
});

$('.available-slot-input').on('input', function(){
	this.value = this.value.replace(/[^0-9]/g,'');
	var inputValue = this.value;
	var $td = $(this).closest('.available-units-td');
	var allocatedSlots =  parseInt(this.getAttribute('data-allocated-slots'));
	var overallSlots =  parseInt(this.getAttribute('data-overall-slots'));
	
	if(inputValue == ''){
		$('#save-region-slots-btn').addClass('buttonDisabled');
		$(this).addClass('errorMsgInput');
		return false;
	}
	else{
		$(this).removeClass('errorMsgInput');
	}
	if(ritsu.isFormDirty('#region-slot-maintenance-form'))
		$('#save-region-slots-btn').removeClass('buttonDisabled');
	
	var value = parseInt(inputValue);
	$td.find('.unallocated-slots').text(overallSlots - value - allocatedSlots);
	
	if(value + allocatedSlots > overallSlots) {
		$('#save-region-slots-btn').addClass('buttonDisabled');
		$(this).addClass('errorMsgInput');
		$td.find('.unallocated-region-slots').addClass('errorMsg');
	}
	else {
		$('#save-region-slots-btn').removeClass('buttonDisabled');
		$(this).removeClass('errorMsgInput');
		$td.find('.unallocated-region-slots').removeClass('errorMsg');
	}
	
	$('.available-slot-input').each(function(){
		if($(this).hasClass('errorMsgInput'))
			$('#save-region-slots-btn').addClass('buttonDisabled');
	});
	
});

$('#save-region-slots-btn').on('click', function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var serializedForm = $('#region-slot-maintenance-form').serialize();
	
	var $saveSlotsPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/save-region-slots',
		data: serializedForm
	});
	
	$saveSlotsPromise.done(function(){
		$('#save-region-slots-btn').addClass('buttonDisabled');
    	ritsu.storeInitialFormValues('#region-slot-maintenance-form');
	});
});

$("#search-button").on("click", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});