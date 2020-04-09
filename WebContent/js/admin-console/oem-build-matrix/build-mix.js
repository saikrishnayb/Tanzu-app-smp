var bodiesOnOrder = $('.build-mix-top').data('bodies-on-order');
var $submitBtn = $('#submit-btn');

$('.attribute-container').each(function(){
	var $container = $(this);
	$container.find('.attribute-value-row').each(function(){
		var $row = $(this);
		var $unitsInput = $row.find('.attribute-units');
		var units = parseInt($unitsInput.val());
		
		var percentage = calculatePercentage(units);
		$row.find('.attribute-percentage').val(percentage);
	});
	
	calculateTotals($container);
});

$('.attribute-container').on('input', '.attribute-units', function(){
	var $unitsInput = $(this);
	var $attributeValueRow = $unitsInput.closest('.attribute-value-row');
	var $container = $attributeValueRow.closest('.attribute-container');
	var units = parseInt($unitsInput.val());
	
	var percentage = calculatePercentage(units);
	$attributeValueRow.find('.attribute-percentage').val(percentage);
	
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
			var units = parseInt($row.find('.attribute-units').val());
			
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
    	window.location.href = baseBuildMatrixUrl + "/build-history";
    })
	
});

function calculatePercentage(units){
	return ((units/bodiesOnOrder) * 100).toFixed(2)
}

function calculateTotals($container){
	var totalUnits = 0;
	var totalPercentage = 0;
	
	var $attributeValueRows = $container.find('.attribute-value-row');
	$attributeValueRows.each(function(){
		var $row = $(this);
		var percentage = parseFloat($row.find('.attribute-percentage').val());
		totalPercentage += percentage;
		var units = parseInt($row.find('.attribute-units').val());
		totalUnits += units;
	});
	
	var $totalRow = $container.find('.attribute-total-row');
	$totalRow.find('.total-units').text(totalUnits);
	$totalRow.find('.total-percentage').text(totalPercentage);
	checkTotals();
}

function checkTotals(){
	var $attributeContainers = $('.attribute-container');
	var enableSubmit = true;
	$attributeContainers.each(function(){
		var $container = $(this);
		var $totalRow = $container.find('.attribute-total-row');
		var totalUnits = parseInt($totalRow.find('.total-units').text());
		
		if(totalUnits > bodiesOnOrder) {
			$totalRow.addClass('text-danger');
			enableSubmit = false;
		}
		else {
			
			if(totalUnits === bodiesOnOrder)
				$totalRow.find('.total-percentage').text("100");
			else
				enableSubmit = false
				
			$totalRow.removeClass('text-danger');
		}
	});
	
	if(enableSubmit)
		$submitBtn.removeClass('buttonDisabled');
	else
		$submitBtn.addClass('buttonDisabled');
}