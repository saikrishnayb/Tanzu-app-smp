selectCurrentNavigation("tab-oem-build-matrix", "left-nav-prod-slot-region-maintenance");

var $vehicleTypeDrpdwn = $("#vehicletype-drpdwn");
var $yearDrpdwn = $("#year-drpdwn");
var $prodSlotRegionMaintenanceModal = $('#prod-slot-region-maintenance-modal');

ModalUtil.initializeModal($prodSlotRegionMaintenanceModal);

ritsu.storeInitialFormValues('#region-slot-maintenance-form');

var $slotRegionMaintenanceTable = $('#slot-region-maintenance-table');
var $slotRegionMaintenanceDataTable = $slotRegionMaintenanceTable.DataTable({
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
	var allocatedRegionSlots = parseInt(this.getAttribute('data-region-allocated-slots'));
	
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
	else if(value < allocatedRegionSlots) {
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
	
	var slotIndex = 0;
	$('#region-slot-maintenance-form').find('.available-slot-input').each(function(index) {
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
	
	var serializedForm = $('#region-slot-maintenance-form').serialize();
	
	$('#region-slot-maintenance-form').find('.available-slot-input').each(function(index) {
		var $td = $(this).closest('.available-units-td');
		$td.find(':input').each(function() {
			this.name = this.name.replace(/\[[0-9]*\]/, '[XXX]');
		});
	})
	
	var $saveSlotsPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/save-region-slots',
		data: serializedForm
	});
	
	$saveSlotsPromise.done(function(){
		$('#save-region-slots-btn').addClass('buttonDisabled');
		$('#region-slot-maintenance-form').find(':input:disabled').removeAttr("disabled");
    	ritsu.storeInitialFormValues('#region-slot-maintenance-form');
	});
});

$("#search-button").on("click", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});

$('#import-btn').on('click', function(){
	var $this = $(this);
	if($this.hasClass('buttonDisabled'))
		return false;
	
	var slotTypeId = $this.data('slot-type-id');
	var year = $this.data('year');
	var region = $this.data('region');
	var regionDesc = $this.data('region-desc');
	
	var $getImportContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-import-region-slot-maintenance',
		data: {
			slotTypeId: slotTypeId,
			year: year,
			region: region,
			regionDesc, regionDesc
		}
	});
	$getImportContentPromise.done(function(data){
		$prodSlotRegionMaintenanceModal.html(data);
	    ModalUtil.openModal($prodSlotRegionMaintenanceModal);
	});
});

$('#export-region-slots-btn').on('click', function(){
	var $this = $(this);
	if($this.hasClass('buttonDisabled'))
		return false;
	
	var slotTypeId = $this.data('slot-type-id');
	var vehicleTypeDesc = $this.data('vehicle-desc').trim().replace(' ', '_');
	var year = $this.data('year');
	var region = $this.data('region');
	
	var today = new Date();
	var filename = 'Region_' + region + '_' + vehicleTypeDesc + '_' + year  + '_Slot_Maintenance_';

	var mm = today.getMonth() + 1;
	if (mm < 10) {
		mm = '0' + mm;
	}
	filename += mm;
	
	var dd = today.getDate();
	if (dd < 10) {
		dd = '0' + dd;
	}
	filename += '_' + dd;
	
	var params = 'slotTypeId=' + slotTypeId + '&year=' + year + '&region=' + region;

	DownloadUtil.downloadFileAsFormPostMultiParams(baseBuildMatrixUrl + '/export-region-slot-maintenance', filename + '.xlsx', params);
});