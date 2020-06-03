ritsu.storeInitialFormValues('#update-build-params-form');

$('.numbers-only').on('input', function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
    if(ritsu.isFormDirty('#update-build-params-form'))
    	$('#update-build-params-btn').removeClass('buttonDisabled');
});

$('#update-build-params-btn').on('click', function(){
	var invalidForm = !ritsu.validate('#update-build-params-form');
	
	if(invalidForm){
		ritsu.markInvalidFields('#update-build-params-form');
		return false;
	}
	
	var buildId = $('#update-build-params-btn').data('buildId');
	var maxWeeksBefore = parseInt($('#update-build-params-form').find('#build-weeks-before').val());
	var maxWeeksAfter = parseInt($('#update-build-params-form').find('#build-weeks-after').val());
	
	var $updateBuildPromise = $.ajax({
		type: "POST",
		url: baseBuildMatrixUrl + '/update-build-params',
		data: {
			buildId: buildId,
			maxWeeksBefore:maxWeeksBefore,
			maxWeeksAfter:maxWeeksAfter
		}
	});
	
	$updateBuildPromise.done(function(){
		$('.max-weeks-before-badge').text(maxWeeksBefore);
		$('.max-weeks-after-badge').text(maxWeeksAfter);
		
	    ModalUtil.closeModal($buildMixModal);
	});
});

//# sourceURL=update-build-params.js