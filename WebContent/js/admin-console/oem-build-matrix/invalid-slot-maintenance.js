selectCurrentNavigation("tab-oem-build-matrix", "left-nav-invalid-slots");

var $mfrDrpdwn = $("#manufacturer-drpdwn");
var $plantDrpdwn = $("#plant-drpdwn");
var $slotTypeDrpdwn = $("#slot-type-drpdwn");
var $invalidSlotModal = $('#invalid-slot-modal');

ModalUtil.initializeModal($invalidSlotModal);

ritsu.storeInitialFormValues('#invalid-slots-form');

var $invalidSlotTable = $('#invalid-slots-table');
var $invalidSlotDataTable = $invalidSlotTable.DataTable({
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
		{targets: ['slot-table-header'], width: '140px'}
	],
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
	var $row = $(this).closest('.date-unit-row');
	var overallSlots =  $row.data('available-slots')
	
	if(ritsu.isFormDirty('#region-slot-maintenance-form'))
		$('#save-invalid-slots-btn').removeClass('buttonDisabled');
	
	var rowSlots = 0;
	var errorsExist = false;
	$row.find('.available-slot-input').each(function(){
		var $td = $(this).closest('.available-units-td');
		this.value = this.value.replace(/[^0-9]/g,'');
		var inputValue = this.value;
		var allocatedRegionSlots = parseInt(this.getAttribute('data-region-allocated-slots'));
		
		if(inputValue == ''){
			$('#save-invalid-slots-btn').addClass('buttonDisabled');
			$(this).addClass('errorMsgInput');
			$td.find('.unallocated-region-slots').addClass('errorMsg');
			errorsExist = true;
		}
		else{
			$(this).removeClass('errorMsgInput');
			$td.find('.unallocated-region-slots').removeClass('errorMsg');
		}
		
		if(errorsExist)
			return;
		
		var value = parseInt(inputValue);
		
		if(value < allocatedRegionSlots) {
			$('#save-invalid-slots-btn').addClass('buttonDisabled');
			$(this).addClass('errorMsgInput');
			$td.find('.unallocated-region-slots').addClass('errorMsg');
			errorsExist = true;
		}
		rowSlots += value;
	});
	
	var invalidRow = false;
	if(rowSlots > overallSlots) {
		$('#save-invalid-slots-btn').addClass('buttonDisabled');
		invalidRow = true;
	}
	
	$row.find('.available-slot-input').each(function(){
		var $td = $(this).closest('.available-units-td');
		$td.find('.unallocated-slots').text(overallSlots-rowSlots);
		if(invalidRow) {
			$(this).addClass('errorMsgInput');
			$td.find('.unallocated-region-slots').addClass('errorMsg');
		}
	});
	
	
});

$('#save-invalid-slots-btn').on('click', function(){
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
		$('#save-invalid-slots-btn').addClass('buttonDisabled');
		$('#region-slot-maintenance-form').find(':input:disabled').removeAttr("disabled");
    	ritsu.storeInitialFormValues('#region-slot-maintenance-form');
	});
});

$("#search-button").on("click", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});
