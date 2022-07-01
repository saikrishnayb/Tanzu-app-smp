function loadProcessImage(pageName){
	processingImageAndTextHandler('visible','Loading data...');
}
function processingImageAndTextHandler(show,processingText){
	
	$(".processingImg", parent.document).css('visibility',show);
	$("#processingText", parent.document).text(processingText);
	$(".overlayadmin", parent.document).css('visibility',show);
}

var displayFlag=true;
$(document).ready(function() {
	var $document = $(document);
	//Turns the AJAX cache off
	$.ajaxSetup({
		cache:false
	});
	
	$('a').click(function() {
		$this=$(this);
		var $aName=$this.text();
		if($aName=='Edit' || $aName=='copy' || $aName=='Search' || $aName.indexOf('Create')>-1 ||$aName.indexOf('View')>-1 || $aName.indexOf('Configure')>-1 ||$aName.indexOf('Set')>-1 || $aName=='Back'){
			if(isInHideList($this)){
				if(displayFlag){
					processingImageAndTextHandler('visible','Loading data...');
				}
			}
		}
	});

	$document.ajaxError(function(){
		processingImageAndTextHandler('hidden');
	});
	
	//spinner modal listener
	$document.ajaxSend(function(){
		if(displayFlag){
			processingImageAndTextHandler('visible','Processing...');
		}
	});

	$document.ajaxComplete(function() {
		processingImageAndTextHandler('hidden');
	});
	
	$document.ajaxStart(function() {
		if(displayFlag){
			processingImageAndTextHandler('visible','Processing...');
		}
	});

	processingImageAndTextHandler('hidden');

});

function getDivStatus(){
	if($('#processingImg').is(':visible') || $("#overlayadmin").is(':visible')){
		return true;
	}else{
		return false;
	}
}


function isInHideList($this){
	// Special Case
	if($this.hasClass('edit-alert')){
		return false;
	}else{
		return true;
	}
}