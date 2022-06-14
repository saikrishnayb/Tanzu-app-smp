$("#plants").multiselect({
	minWidth : 150,
	noneSelectedText : "",
	open : function() {
		$(".ui-multiselect-menu ").css('width', '300px');
	}
});

$('#manufacturer').on('change', function(){
	var $this = $(this);
	var mfrCode = $this.val();
	
	if(mfrCode == ''){
		$('#plants').empty();
		$('#plants').append('<option value= ""></option>').multiselect('refresh');
		$('#plants').multiselect("disable");
		$('#export-slots-btn').addClass('buttonDisabled');
	}
	else {
		var $getBodyPlantsForMfrPromise = $.ajax({
			type: "GET",
			url: baseBuildMatrixUrl + '/get-body-plants-by-mfr-code',
			data: {
				mfrCode: mfrCode
			}
		});
		$getBodyPlantsForMfrPromise.done(function(plantList){
			$('#plants').empty();
			plantList.forEach(function(bodyPlant, index) {
				$('#plants').append('<option value="' + bodyPlant.plantId + '">' + bodyPlant.plantName + ' - ' + 
						bodyPlant.city + ', ' + bodyPlant.state +  '</option>');
			});
			$('#plants').multiselect("enable");
			$('#plants').multiselect('refresh');
		});
		
	}
	
	
});

$('#export-slots-btn').on('click', function(){
	var $this = $(this);
	var slotTypeId = $this.data('slot-type-id');
	var vehicleTypeDesc = $('#vehicle-desc').text().trim().replace(' ', '_');
	var manufacturer = $('#manufacturer').find('option:selected').text().trim().replace(' ', '_');
	var year = $this.data('year');;
	var plantIds = $('#plants').val();
	
	var today = new Date();
	var filename = vehicleTypeDesc + '_' + year + '_' + manufacturer  + '_Slot_Maintenance_';

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
	
	var params = 'slotTypeId=' + slotTypeId + '&year=' + year + '&plantIds[]=' + plantIds;

	DownloadUtil.downloadFileAsFormPostMultiParams(baseBuildMatrixUrl + '/export-slot-maintenance', filename + '.xlsx', params);
	
	ModalUtil.closeModal($prodSlotMaintenanceModal);
});

function enableUpdate()
{
	var optionSelected = $('#plants').val()
	if(optionSelected.length > 0)
		$('#export-slots-btn').removeClass('buttonDisabled');
	else
		$('#export-slots-btn').addClass('buttonDisabled');
}

//# sourceURL=export-slot-maintenance.js