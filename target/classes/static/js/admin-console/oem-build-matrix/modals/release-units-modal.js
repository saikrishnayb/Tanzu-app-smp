$("#release-units").multiselect({
	minWidth : 150,
	noneSelectedText : "",
});

$("#release-units-btn").on('click', function(){
	var $this  = $(this);
	
	if($this.hasClass('buttonDisabled'))
		return false;
	
	var slotReservations = $('#release-units').find('option').length;
	var slotReservationIds = $('#release-units').val();
	
	var $releaseUnitsPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/release-units',
		data: {
			slotReservationIds: slotReservationIds
		}
	});
	
	$releaseUnitsPromise.done(function(){
		var region = $this.data('region');
		var plantId = $this.data('plant-id');
		var slotDateId = $this.data('slot-date-id')
		var $row = $('.date-unit-row[data-prod-slot-date-id=' + slotDateId + ']');
		var $reservedUnitTd = $row.find('td[data-plant-id=' + plantId + ']');
		var remainingReservations = slotReservations - slotReservationIds.length;
		
		if(remainingReservations > 0){
			$reservedUnitTd.html('<a class="secondaryLink release-units-link">' + remainingReservations + '</a>');
		}
		else {
			$reservedUnitTd.html('0');
		}
		
	    ModalUtil.closeModal($prodSlotUtilizationModal);
	});
	
});

function enableUpdate()
{
	var unitsSelected = $('#release-units').val()
	if(unitsSelected.length>0)
		$('#release-units-btn').removeClass('buttonDisabled');
	else
		$('#release-units-btn').addClass('buttonDisabled');
}