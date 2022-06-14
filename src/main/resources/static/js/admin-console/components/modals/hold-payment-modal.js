$('#vendors').multiselect({
	minWidth : 150,
	noneSelectedText : "",
	beforeopen: function(event, ui) {
	    
	    $("#vendors").find('option:selected').each(function () {
	      $(this).prependTo($("#vendors"));
	    });
	    
	    $("#vendors").multiselect("refresh");
	  }
}).multiselectfilter();

$('#save-hold-payments-btn').on('click', function(){
	var componentId = $(this).data('component-id');
	var vendorIds = $('#hold-payment-form').find('#vendors').val();
	
	var $getHoldPaymentContentPromise = $.post("save-hold-payment", {componentId:componentId, vendorIds:vendorIds});
	$getHoldPaymentContentPromise.done(function(data){
		var $componentRow = $('.component-row[data-component-id="' + componentId + '"]');
		var vendorSize = vendorIds.length;
		if(vendorSize == 0){
			$componentRow.find('.hold-payment-link').text("Payment Holds");
		}
		else{
			$componentRow.find('.hold-payment-link').text("Payment Holds (" + vendorSize + ")");
		}
		
		ModalUtil.closeModal($componentModal);
	});
});

//# sourceURL=hold-payment.js