$("#oem-name-drpdown").multiselect({
	close : function() {},
	minWidth : 169,
	open: function(){
      $(".ui-multiselect-menu ").css('width','335px');
    },
    noneSelectedText : "",
}).multiselectfilter({width : 250})

$('#oem-name-drpdown .ui-multiselect').css('width', '100%');

$('#po-category-drpdown').on('change', function(){
	$('#oem-name-drpdown').find('option').each(function(){ $(this).remove() });
	if($(this).val()=="") {
		$('#oem-name-drpdown').multiselect('refresh');
	}
	else {
		var poCategoryName = $(this).val();
		
		var $getMfrListPromise = $.ajax({
			type : 'POST',
			url : baseBuildMatrixUrl + '/get-oems-for-po-category',
			data: {poCategoryName: poCategoryName}
		});
		  
		$getMfrListPromise.done(function(list) {
			list.forEach(function(mfr){
				$("#oem-name-drpdown").append('<option value="' + mfr.mfrCode + '" >' + mfr.mfrCode + ' - ' + mfr.mfrName + '</option>');
			});
			
			$('#oem-name-drpdown').multiselect('refresh');
		});
	}
});

//# sourceURL=add-oem.js