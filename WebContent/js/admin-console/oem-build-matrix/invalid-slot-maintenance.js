selectCurrentNavigation("tab-oem-build-matrix", "left-nav-invalid-slots");

var $mfrDrpdwn = $("#manufacturer-drpdwn");
var $plantDrpdwn = $("#plant-drpdwn");
var $slotTypeDrpdwn = $("#slot-type-drpdwn");
var $invalidSlotSaveModal = $('#modal-invalid-slot-save');

ModalUtil.initializeModal($invalidSlotSaveModal);

ritsu.storeInitialFormValues('#invalid-slots-form');

var $invalidSlotTable = $('#invalid-slots-table');
var $invalidSlotDataTable = $invalidSlotTable.DataTable({
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
	      "emptyTable": "There are no more invalid slots for this plant."
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
	var $row = $(this).closest('.date-unit-row');
	var overallSlots =  $row.data('available-slots')
	
	if(ritsu.isFormDirty('#invalid-slots-form'))
		$('#save-invalid-slots-btn').removeClass('buttonDisabled');
	
	var rowSlots = 0;
	var errorsExist = false;
	$row.find('.available-slot-input').each(function(){
		var $td = $(this).closest('.available-units-td');
		this.value = this.value.replace(/[^0-9]/g,'');
		var inputValue = this.value;
		var allocatedRegionSlots = parseInt(this.getAttribute('data-region-allocated-slots'));
		
		if(inputValue == ''){
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
			$(this).addClass('errorMsgInput');
			$td.find('.unallocated-region-slots').addClass('errorMsg');
			errorsExist = true;
		}
		rowSlots += value;
	});
	
	var invalidRow = false;
	if(rowSlots > overallSlots) {
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

$('#save-invalid-slots-btn').on('click', function() {
  ModalUtil.openModal($invalidSlotSaveModal);
});

$('#cancel-save-btn').on('click', function() {
  ModalUtil.closeModal($invalidSlotSaveModal);
});

$('#save-invalid-slots-confirm-btn').on('click', function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var slotIndex = 0;
	var rowIndexes = [];
	$('#invalid-slots-form').find('.date-unit-row').each(function(index) {
		var $row = $(this);
		var invalidRow = false;
		$row.find('.available-slot-input').each(function(){
			if($(this).hasClass('errorMsgInput')) {
				invalidRow = true
			}
		});
		
		if(invalidRow) {
			$row.find('.available-units-td').each(function(innerIndex) {
				var $td = $(this);
				$td.find(':input').each(function(innerIndex){
					$(this).attr("disabled", "disabled");
				});
			});
		}
		else {
			$row.find('.available-units-td').each(function(innerIndex) {
				var $td = $(this);
				$td.find(':input').each(function(innerIndex){
					this.name = this.name.replace('XXX', slotIndex);
				});
				slotIndex++;
			});
			rowIndexes.push($row.index());
		}
	});
	
	var serializedForm = $('#invalid-slots-form').serialize();
	
	$('#invalid-slots-form').find('.available-slot-input').each(function(index) {
		var $td = $(this).closest('.available-units-td');
		$td.find(':input').each(function() {
			this.name = this.name.replace(/\[[0-9]*\]/, '[XXX]');
		});
	})
	
	var $saveSlotsPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/save-invalid-slots',
		data: serializedForm
	});
	
	$saveSlotsPromise.done(function(){
		$('#save-invalid-slots-btn').addClass('buttonDisabled');
		$('#invalid-slots-form').find(':input:disabled').removeAttr("disabled");
    	ritsu.storeInitialFormValues('#invalid-slots-form');
    	rowIndexes.forEach(function(rowIndex){
    		$invalidSlotDataTable.row(rowIndex).remove();
    	});
    	$invalidSlotDataTable.draw();
    	ModalUtil.closeModal($invalidSlotSaveModal);
	});
});

$('#manufacturer-drpdwn').on('change', function(){
	var mfrCode = $(this).val();
	
	var $getInvalidPlantsAndSlotTypesPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-invalid-plants-and-slot-types',
		data: {
			mfrCode:mfrCode
		}
	});
	
	$getInvalidPlantsAndSlotTypesPromise.done(function(data){
		var plants = data.invalidBodyPlants;
		var slotTypes = data.invalidSlotTypes;
		
		$('#plant-drpdwn').find('option').remove();
		plants.forEach(function(plant){
			$('#plant-drpdwn').append('<option value="' + plant.plantId + '">' + 
					plant.plantManufacturer + ' - ' + plant.city + ', ' + plant.state + 
					'</option>');
		});
		
		$('#slot-type-drpdwn').find('option').remove();
		slotTypes.forEach(function(slotType){
			$('#slot-type-drpdwn').append('<option value="' + slotType.slotTypeId + '">' +
					slotType.slotTypeDesc + '</option>');
		});
	});
});

$('#plant-drpdwn').on('change', function(){
	var plantId = $(this).val();
	
	var $getInvalidSlotTypesPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-invalid-slot-types',
		data: {
			plantId:plantId
		}
	});
	
	$getInvalidSlotTypesPromise.done(function(data){
		var slotTypes = data;
		
		$('#slot-type-drpdwn').find('option').remove();
		slotTypes.forEach(function(slotType){
			$('#slot-type-drpdwn').append('<option value="' + slotType.slotTypeId + '">' +
					slotType.slotTypeDesc + '</option>');
		});
	});
});

$("#search-button").on("click", function() {
	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});
