var bodiesOnOrder = $('.build-mix-top').data('bodies-on-order');
var $submitBtn = $('#submit-btn');

$('.attribute-container').each(function(){
	var $container = $(this);
/*	var isReefer = "REEFERMAKE" == $container.data('attribute-key');
	var isRearDoor = "REARDOORMAKE" == $container.data('attribute-key');
	var isLiftgate = "LIFTGATEMAKE" == $container.data('attribute-key');
	
	$container.find('.attribute-value-row').each(function(){
		var $row = $(this);
		var $unitsInput = $row.find('.attribute-units');
		var unitsText = $unitsInput.val();
		unitsText = unitsText.trim() == '' ? 0 : unitsText;
		var units = parseInt(unitsText);
		
		var percentage = calculatePercentage(units, $container, isReefer, isRearDoor, isLiftgate);
		$row.find('.attribute-percentage').val(percentage);
	});*/
	
	calculateTotals($container);
});

$('.attribute-container').on('input', '.attribute-units', function(){
	var $unitsInput = $(this);
	var $attributeValueRow = $unitsInput.closest('.attribute-value-row');
	var $container = $attributeValueRow.closest('.attribute-container');
	var unitsText = $unitsInput.val();
	unitsText = unitsText.trim() == '' ? '0' : unitsText;
	var units = parseInt(unitsText);
	
	var isReefer = "REEFERMAKE" == $container.data('attribute-key');
	var isRearDoor = "REARDOORMAKE" == $container.data('attribute-key');
	var isLiftgate = "LIFTGATEMAKE" == $container.data('attribute-key');
	
	var percentage = calculatePercentage(units, $container, isReefer, isRearDoor, isLiftgate);
	$attributeValueRow.find('.attribute-percentage').val(percentage);
	
	calculateTotals($container);
});

$('.attribute-container').on('input', '.attribute-percentage', function(){
	var $pecentageInput = $(this);
	var $attributeValueRow = $pecentageInput.closest('.attribute-value-row');
	var $container = $attributeValueRow.closest('.attribute-container');
	var percentText = $pecentageInput.val();
	percentText = percentText.trim() == '' ? '0' : percentText;
	var percentage = parseFloat(percentText);
	
	var isReefer = "REEFERMAKE" == $container.data('attribute-key');
	var isRearDoor = "REARDOORMAKE" == $container.data('attribute-key');
	var isLiftgate = "LIFTGATEMAKE" == $container.data('attribute-key');
	
	var units = calculateUnits(percentage, $container, isReefer, isRearDoor, isLiftgate);
	$attributeValueRow.find('.attribute-units').val(units);
	
	calculateTotals($container);
});

$submitBtn.on('click', function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var buildMixForm = $('#build-mix-form').empty();
	
	var buildId = $(this).data('build-id');
	var buildIdInput = document.createElement('input');
	buildIdInput.type = 'hidden';
	buildIdInput.name = 'buildId';
	buildIdInput.value = buildId;
	buildMixForm.append(buildIdInput);
	
	var index = 0;
	
	var $attributeContainers = $('.attribute-container');
	$attributeContainers.each(function(){
		var $container = $(this);
		
		var groupKey = $container.data('group-key');
		
		var $attributeValueRows = $container.find('.attribute-value-row');
		$attributeValueRows.each(function(){
			var $row = $(this);
			
			var attributeValue = $row.data('attribute-value');
			var percentage = parseFloat($row.find('.attribute-percentage').val());
			var unitsText = $row.find('.attribute-units').val();
			unitsText = unitsText.trim() == '' ? 0 : unitsText;
			var units = parseInt(unitsText);
			
			var groupKeyInput = document.createElement('input');
			groupKeyInput.type = 'hidden';
			groupKeyInput.name = 'attributeRows[' + index + '].groupKey'
			groupKeyInput.value = groupKey;
			buildMixForm.append(groupKeyInput);
			
			var awardKeyInput = document.createElement('input');
			awardKeyInput.type = 'hidden';
			awardKeyInput.name = 'attributeRows[' + index + '].awardKey';
			awardKeyInput.value = attributeValue;
			buildMixForm.append(awardKeyInput);
			
			var percentageInput = document.createElement('input');
			percentageInput.type = 'hidden';
			percentageInput.name = 'attributeRows[' + index + '].awardPercentage';
			percentageInput.value = percentage;
			buildMixForm.append(percentageInput);
			
			var unitsInput = document.createElement('input');
			unitsInput.type = 'hidden';
			unitsInput.name = 'attributeRows[' + index + '].awardQuantity';
			unitsInput.value = units;
			buildMixForm.append(unitsInput);
			
			index++;
			
		});
	
	});
	
	$.post(baseBuildMatrixUrl + '/submit-build', buildMixForm.serialize()).done(function(){
		loadProcessImage();
    	window.location.href = baseBuildMatrixUrl + "/build-history";
    })
	
});

function calculateUnits(percentage, $container, isReefer, isRearDoor, isLiftgate){
	if(isReefer) {
		var reeferUnits = $container.data('reefer-units');
		if(reeferUnits == 0)
			return 0.00;
		else
			return Math.floor(reeferUnits * (percentage/100) + 0.5);
	}
	else if(isRearDoor) {
		var rearDoorUnits = $container.data('reardoor-units');
		if(rearDoorUnits == 0)
			return 0.00;
		else
			return Math.floor(rearDoorUnits * (percentage/100)  + 0.5);
	}
	else if(isLiftgate) {
		var liftgateUnits = $container.data('liftgate-units');
		if(liftgateUnits == 0)
			return 0.00;
		else
			return Math.floor(liftgateUnits * (percentage/100)  + 0.5);
	}
	else{
		return Math.floor(bodiesOnOrder * (percentage/100)  + 0.5);
	}
}

function calculatePercentage(units, $container, isReefer, isRearDoor, isLiftgate){
	if(isReefer) {
		var reeferUnits = $container.data('reefer-units');
		if(reeferUnits == 0)
			return 0.00;
		else
			return ((units/reeferUnits) * 100).toFixed(2);
	}
	else if(isRearDoor) {
		var rearDoorUnits = $container.data('reardoor-units');
		if(rearDoorUnits == 0)
			return 0.00;
		else
			return ((units/rearDoorUnits) * 100).toFixed(2);
	}
	else if(isLiftgate) {
		var liftgateUnits = $container.data('liftgate-units');
		if(liftgateUnits == 0)
			return 0.00;
		else
			return ((units/liftgateUnits) * 100).toFixed(2);
	}
	else{
		return ((units/bodiesOnOrder) * 100).toFixed(2);
	}
}

function calculateTotals($container) {
	var totalUnits = 0;
	var totalPercentage = 0;
	
	var $attributeValueRows = $container.find('.attribute-value-row');
	$attributeValueRows.each(function(){
		var $row = $(this);
		var percentageText = $row.find('.attribute-percentage').val();
		percentageText = percentageText.trim() == '' ? '0' : percentageText;
		var percentage = parseFloat(percentageText);
		totalPercentage += percentage;
		var unitsText = $row.find('.attribute-units').val();
		unitsText = unitsText.trim() == '' ? '0' : unitsText;
		var units = parseInt(unitsText);
		totalUnits += units;
	});
	
	var $totalRow = $container.find('.attribute-total-row');
	$totalRow.find('.total-units').text(totalUnits);
	$totalRow.find('.total-percentage').text(totalPercentage.toFixed(2));
	checkTotals();
}

function checkTotals(){
	var $attributeContainers = $('.attribute-container');
	var enableSubmit = true;
	$attributeContainers.each(function(){
		var $container = $(this);
		
		var isReefer = "REEFERMAKE" == $container.data('attribute-key');
		var isRearDoor = "REARDOORMAKE" == $container.data('attribute-key');
		var isLiftgate = "LIFTGATEMAKE" == $container.data('attribute-key');
		
		var $totalRow = $container.find('.attribute-total-row');
		var totalUnits = parseInt($totalRow.find('.total-units').text());
		
		var unitsToCompare;
		if(isReefer)
			unitsToCompare = $container.data('reefer-units');
		else if(isRearDoor)
			unitsToCompare = $container.data('reardoor-units');
		else if(isLiftgate)
			unitsToCompare = $container.data('liftgate-units');
		else
			unitsToCompare = bodiesOnOrder;
			
		if(totalUnits != unitsToCompare) {
			$totalRow.addClass('text-danger');
			enableSubmit = false;
		}
		else {
			$totalRow.find('.total-percentage').text("100");
			$totalRow.removeClass('text-danger');
		}
		
		var $attributeValueRows = $container.find('.attribute-value-row');
		$attributeValueRows.each(function(){
			var $row = $(this);
			var unitsText = $row.find('.attribute-units').val();
			if(unitsText.trim() == ''){
				enableSubmit = false;
				$row.find('.attribute-units').addClass('error-input');
			}
			else
				$row.find('.attribute-units').removeClass('error-input');
		});
	});
	
	if(enableSubmit)
		$submitBtn.removeClass('buttonDisabled');
	else
		$submitBtn.addClass('buttonDisabled');
}