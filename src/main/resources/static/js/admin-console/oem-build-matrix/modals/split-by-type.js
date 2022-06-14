$('.body-split-container').on('input', '.numbers-only', function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
    
    checkSelectsAndTotals();
});

$('.body-split-container').on('click', '.add-body-split-row', function(){
	var $link = $(this);
	var $row = $link.closest('.body-split-input-row');
	
	var $inputRows = $('.body-split-container').find('.body-split-input-row');
	
	var $mfrs = $($inputRows[0]).find('option');
	
	$('.body-split-container').append(
			'<div class="row body-split-input-row">' +
				'<div class="col-xs-2">' +
					'<select class="mfr-select">' +
					'</select>' +
				'</div>' +
				'<div class="col-xs-2">' +
					'<input type="text" class="total" disabled>' +
				'</div>' +
				'<div class="col-xs-2">' +
					'<input type="text" class="van-qty split-unit-input numbers-only" value="">' +
				'</div>' +
				'<div class="col-xs-2">' +
					'<input type="text" class="reefer-qty split-unit-input numbers-only" value="">' +
				'</div>' +
				'<div class="col-xs-2">' +
					'<input type="text" class="flatbed-qty split-unit-input numbers-only" value="">' +
				'</div>' +
				'<div class="col-xs-2">' +
					'<a class="delete-body-split-row hidden">' +
						'<i class="fa fa-trash"></i>' +
					'</a>' +
					'<a class="add-body-split-row">' +
						'<i class="fa fa-plus-circle"></i>' +
					'</a>' +
				'</div>' +
			'</div>'
	);
	
	var $addedRow = $('.body-split-container').find('.body-split-input-row:last');
	$mfrs.each(function(){
		$addedRow.find('.mfr-select').append($(this).clone());
	});
	
	$row.find('.add-body-split-row').addClass('hidden');
	$row.find('.delete-body-split-row').removeClass('hidden');
	
	checkSelectsAndTotals();
});

$('.body-split-container').on('click', '.delete-body-split-row', function(){
	var $link = $(this);
	var $row = $link.closest('.body-split-input-row');
	
	$row.remove();
	var $lastRow = $('.body-split-container').find('.body-split-input-row:last');
	
	$lastRow.find('.add-body-split-row').removeClass('hidden');
	$lastRow.find('.delete-body-split-row').addClass('hidden');
	
	checkSelectsAndTotals();
});

$('.body-split-container').on('change', '.mfr-select', function(){
	var $mfrSelect = $(this);
	var mfrValue = $mfrSelect.val();
	var $row = $mfrSelect.closest('.body-split-input-row');
	
	if(mfrValue != '') {
		var $bodyMakeContainer = $('.attribute-container[data-attribute-key="BODYMAKE"]');
		var $mfrRow = $bodyMakeContainer.find('.attribute-value-row[data-attribute-value="' + mfrValue +'"]');
		var mfrTotal = $mfrRow.find('.attribute-units').val();
		$row.find('.total').val(mfrTotal);
	}
	else
		$row.find('.total').val('');
	
	checkSelectsAndTotals();
	
});

$('#save-split-by-type-btn').on('click', function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var splitByTypeForm = $('#split-by-type-form').empty();
	var mfrsSelected = []
	
	var buildId = $(this).data('build-id');
	var buildIdInput = document.createElement('input');
	buildIdInput.type = 'hidden';
	buildIdInput.name = 'buildId';
	buildIdInput.value = buildId;
	splitByTypeForm.append(buildIdInput);
	
	var index = 0;
	
	var $inputRows = $('.body-split-container').find('.body-split-input-row');
	$inputRows.each(function(){
		var $row = $(this);
		
		var make = $row.find('.mfr-select').val();
		mfrsSelected.push(make);
		
		var vanQtyText = $row.find('.van-qty').val();
		vanQtyText = vanQtyText.trim() == '' ? 0 : vanQtyText;
		var vanQty = parseInt(vanQtyText);
		
		var reeferQtyText = $row.find('.reefer-qty').val();
		reeferQtyText = reeferQtyText.trim() == '' ? 0 : reeferQtyText;
		var reeferQty = parseInt(reeferQtyText);
		
		var flatbedQtyText = $row.find('.flatbed-qty').val();
		flatbedQtyText = flatbedQtyText.trim() == '' ? 0 : flatbedQtyText;
		var flatbedQty = parseInt(flatbedQtyText);
		
		var makeInput = document.createElement('input');
		makeInput.type = 'hidden';
		makeInput.name = 'bodySplitRows[' + index + '].make'
		makeInput.value = make;
		splitByTypeForm.append(makeInput);
		
		var vanQtyInput = document.createElement('input');
		vanQtyInput.type = 'hidden';
		vanQtyInput.name = 'bodySplitRows[' + index + '].vanQty'
		vanQtyInput.value = vanQty;
		splitByTypeForm.append(vanQtyInput);
		
		var reeferQtyInput = document.createElement('input');
		reeferQtyInput.type = 'hidden';
		reeferQtyInput.name = 'bodySplitRows[' + index + '].reeferQty'
		reeferQtyInput.value = reeferQty;
		splitByTypeForm.append(reeferQtyInput);
		
		var flatbedQtyInput = document.createElement('input');
		flatbedQtyInput.type = 'hidden';
		flatbedQtyInput.name = 'bodySplitRows[' + index + '].flatbedQty'
		flatbedQtyInput.value = flatbedQty;
		splitByTypeForm.append(flatbedQtyInput);
		
		index++;
	});
	
	$.post(baseBuildMatrixUrl + '/split-by-type', splitByTypeForm.serialize()).done(function(){
		var $bodyMakeContainer = $('.attribute-container[data-attribute-key="BODYMAKE"]');
		
		$bodyMakeContainer.find('.attribute-value-row').each(function(){
			var $row = $(this);
			var rowMfr = $row.data('attribute-value');
			var $rowUnits = $row.find('.attribute-units');
			
			if(mfrsSelected.includes(rowMfr)) {
				$rowUnits.addClass('body-splits-exist');
			}
			else {
				$rowUnits.removeClass('body-splits-exist');
			}
		});
		
		ModalUtil.closeModal($buildMixModal);
    })
});

function checkSelectsAndTotals(){
	var mfrsSelected = [];
	var mfrsSelectedMutiple = [];
	var mfrError = false;
	var enableSave = true;
	
	$('.body-split-container').find('.body-split-input-row').each(function(){
		var $row = $(this);
		var $mfrSelect = $row.find('.mfr-select');
		var totalText = $row.find('.total').val().trim();
		
		var vanQtyText = $row.find('.van-qty').val().trim();
		vanQtyText = vanQtyText == '' ? 0 : vanQtyText;
		
		var reeferQtyText = $row.find('.reefer-qty').val().trim();
		reeferQtyText = reeferQtyText == '' ? 0 : reeferQtyText;
		
		var flatbedQtyText = $row.find('.flatbed-qty').val().trim();
		flatbedQtyText = flatbedQtyText == '' ? 0 : flatbedQtyText;
		
		var mfrValue = $mfrSelect.val();
		if(mfrValue != '') {
			if(jQuery.inArray(mfrValue, mfrsSelected) != -1) {
				if(jQuery.inArray(mfrValue, mfrsSelectedMutiple) == -1) {
					mfrsSelectedMutiple.push(mfrValue);
				}
				mfrError = true;
			    enableSave = false;
			}
			else
				mfrsSelected.push(mfrValue)
			if(vanQtyText != '' || reeferQtyText != '' || flatbedQtyText != '') {
				var total = parseInt(totalText);
				var vanQty = parseInt(vanQtyText);
				var reeferQty = parseInt(reeferQtyText);
				var flatbedQty = parseInt(flatbedQtyText);	
					
				var sum = vanQty + reeferQty + flatbedQty;
				if(total != sum) {
					$row.find('.split-unit-input').addClass('error-input');
					enableSave = false
				}
				else
					$row.find('.split-unit-input').removeClass('error-input');
			}
			else
				enableSave = false;
		}
		else {
			if(vanQtyText != '' || reeferQtyText != '' || flatbedQtyText != '') {
				$row.find('.split-unit-input').addClass('error-input');
			}
			else {
				$row.find('.split-unit-input').removeClass('error-input');
			}
			enableSave = false;	
		}
	});
	
	if(mfrError) {
		$('.body-split-container').find('.body-split-input-row').each(function(){
			var $row = $(this);
			var $mfrSelect = $row.find('.mfr-select');
			
			if(mfrsSelectedMutiple.includes($mfrSelect.val()))
				$mfrSelect.addClass('error-input');
			else
				$mfrSelect.removeClass('error-input');
		});
	}
	else {
		$('.mfr-select').removeClass('error-input');
	}
	
	if(enableSave)
		$('#save-split-by-type-btn').removeClass('buttonDisabled');
	else
		$('#save-split-by-type-btn').addClass('buttonDisabled');
}

//# sourceURL=split-by-type.js