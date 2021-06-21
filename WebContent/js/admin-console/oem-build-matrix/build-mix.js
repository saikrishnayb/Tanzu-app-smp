var bodiesOnOrder = $('.build-mix-top').data('bodies-on-order');
var $submitBtn = $('#submit-btn');
var $buildMixModal = $('#build-mix-modal');
var $bodySplitWarningModal = $('#body-split-warning-modal');

ModalUtil.initializeModal($buildMixModal);
ModalUtil.initializeModal($bodySplitWarningModal);

selectCurrentNavigation("tab-oem-build-matrix", "");

$('.attribute-container').each(function(){
	var $container = $(this);
	calculateTotals($container);
});

ritsu.storeInitialFormValues('input');

$('.numbers-only').on('input', function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});

$('.attribute-container').on('change', '.attribute-units', function(){
	var $unitsInput =     $(this);
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

$('.attribute-container').on('focus', '.attribute-units.body-splits-exist', function(){
	var $unitsInput = $(this);
	$unitsInput.blur();
	var $attributeValueRow = $unitsInput.closest('.attribute-value-row');
	var make = $attributeValueRow.data('attribute-value');
	ModalUtil.openModal($bodySplitWarningModal);
	$bodySplitWarningModal.find('#confirm-change-units').data('make', make)
});

$bodySplitWarningModal.on('click', '#cancel-change-units', function() {
	ModalUtil.closeModal($bodySplitWarningModal);
});

$bodySplitWarningModal.on('click', '#confirm-change-units', function() {
	var $this = $(this);
	var buildId = $this.data('build-id');
	var make = $this.data('make');
	
	$.post(baseBuildMatrixUrl + '/delete-body-splits', { 
		buildId:buildId,
		make:make
	  }).done(function(){
		  var $row = $('.attribute-value-row[data-attribute-value="' + make + '"]');
		  $row.find('.attribute-units').removeClass('body-splits-exist');
		  ModalUtil.closeModal($bodySplitWarningModal);
	  })
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

$('.attribute-container').on('input', '.excess-attribute-units', function(){
	var $excessInput = $(this);
	var $attributeValueRow = $excessInput.closest('.attribute-value-row');
	var $container = $attributeValueRow.closest('.attribute-container');
	
	calculateTotals($container)
	
});

$('.attribute-container').on('click', '.reset-link', function(){
	var $resetLink = $(this);
	var $container = $resetLink.closest('.attribute-container');
	
	ritsu.resetInitialFormValues($container);
	
	calculateTotals($container);
});

$('.attribute-container').on('click', '.split-by-type-link', function(){
	var buildId = $(this).data('build-id');
	
	var $getSplitByTypeContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-split-by-type',
		data: {buildId: buildId}
	});
	$getSplitByTypeContentPromise.done(function(data){
		$buildMixModal.html(data);
	    ModalUtil.openModal($buildMixModal);
	});
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
	
	var guidance = $('#guidance').prop('checked');
	var guidanceInput = document.createElement('input');
	guidanceInput.type = 'hidden';
	guidanceInput.name = 'guidance';
	guidanceInput.value = guidance;
	buildMixForm.append(guidanceInput);
	
	var index = 0;
	
	var $attributeContainers = $('.attribute-container');
	$attributeContainers.each(function(){
		var $container = $(this);
		
		var groupKey = $container.data('group-key');
		var isBodyMake = $container.is('[data-is-body-make]');
		
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
			
			if(isBodyMake) {
				var excessText = $row.find('.excess-attribute-units').val();
				var excessValue = excessText.trim() == '' ? null : parseInt(excessText);
				
				var excessUnitsInput = document.createElement('input');
				excessUnitsInput.type = 'hidden';
				excessUnitsInput.name = 'attributeRows[' + index + '].awardExcess';
				excessUnitsInput.value = excessValue;
				buildMixForm.append(excessUnitsInput);
			}
			
			index++;
			
		});
	
	});
	
	$.post(baseBuildMatrixUrl + '/submit-build', buildMixForm.serialize()).done(function(){
    	window.location.href = baseBuildMatrixUrl + "/build-history";
    })
	
});

$('#update-build-btn').on('click', function(){
	var buildId = $('#build-mix-form').data('build-id');
	
	var $getUpdateBuildContentPromise = $.ajax({
		type: "GET",
		url: baseBuildMatrixUrl + '/get-update-build-params',
		data: {buildId: buildId}
	});
	$getUpdateBuildContentPromise.done(function(data){
		$buildMixModal.html(data);
	    ModalUtil.openModal($buildMixModal);
	});
})

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
		var isBodyMake = "BODYMAKE" == $container.data('attribute-key');
		
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
			
			if(isBodyMake) {
				var excessText = $row.find('.excess-attribute-units').val();
				if(!excessText.trim() == '') {
					var unitsText = $row.find('.attribute-units').val();
					unitsText = unitsText.trim() == '' ? 0 : unitsText;
					var units = parseInt(unitsText);
					
					var excess = parseInt(excessText);
					if(excess <= units) {
						enableSubmit = false;
						$row.find('.excess-attribute-units').addClass('error-input');
					}
					else {
						$row.find('.excess-attribute-units').removeClass('error-input');
					}
				}
				else
					$row.find('.excess-attribute-units').removeClass('error-input');
			}
				
		});
	});
	
	if(enableSubmit)
		$submitBtn.removeClass('buttonDisabled');
	else
		$submitBtn.addClass('buttonDisabled');
}