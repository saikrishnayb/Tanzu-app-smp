//***************************************************** regex************
/*
* Has to match exactly: (xxx)xxx-xxxx
*/
var numericPhoneRegex = /^(\([\d]{3}\))([\d]{3}[-]{1}[\d]{4})$/;
/*
* Any letter/number/underscore/white space/comma/semi colon/single quote/double quote/prob something else at least once
*/
var alphaNumericRegex = /([^\s])/;
/*
* Any letter, whitespace, period, and single quote at least once or more
*/
var alphaNameRegex = /(^[\D\s'.]+$)/;

/*
* Any digit one or more times                                                                                                                                                                                
 */
var numericWholeRegex = /^([\d]+)$/;

/*
 * Email validation
 */
var alphaEmailRegex = /([\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,4})/;

var $ajaxErrorFlg=true;
/*function loadProcessImage(pageName){
	
}*/
$(document).ready(function() {
	
	
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    options.async = true;
	});
	var $document = $(document);
	var $ajaxErrorModal = $('#ajax-error-modal');
	var $spinnerModal = $('#spinner-modal');
	var $roleModal = $('#role-modal');
	var $roleName = $('#role-info');
	var $customizedAjaxErrorModal = $('#customized-ajax-error');
	
	//Turns the AJAX cache off
	$.ajaxSetup({
		cache:false
	});

	$spinnerModal.dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: 10,
			height: 50,
			resizable: false,
			closeOnEscape: false
		});
	
	$spinnerModal.siblings('div.ui-dialog-titlebar').remove();
	
	$roleModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 700,
		minHeight: "auto",
		position: { my: "center top", at: "center top", of: window},
		resizable: false,
		closeOnEscape: false
	});
	
	$customizedAjaxErrorModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'errorModal',
		width: 500,
		minHeight: 0,
		resizable: false,
		closeOnEscape: true
	});
	
	$ajaxErrorModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'errorModal',
		width: 500,
		minHeight: 0,
		resizable: false,
		closeOnEscape: true,
	});
	
	//Global Listeners
	$(".modal").on("click", ".cancel, .close-modal", function(){
		var $this = $(this);
		var $closestModal = $this.closest(".modal");
		
		$closestModal.dialog('close').empty();
	});
	
	//error modal listeners
	
	$document.ajaxError(function(){
		if($ajaxErrorFlg){
			closeModal($('.modal'));
			openModal($ajaxErrorModal);
		}
	});
	
	$('#error-ok').on('click', function(){
		closeModal($ajaxErrorModal);
	});
	
	//spinner modal listener
	$document.ajaxSend(function(){
		openModal($spinnerModal);
	});
	
	$document.ajaxComplete(function(){
		closeModal($spinnerModal);
	});
	
	//role modal listener
	$roleName.on('click', function(){
		var roleId = $('#application-user-role-id').val();
		var roleName = $roleName.text();
		var $roleModalContentPromise = $.get('/suppliermgmt/get-role-modal-content.htm', {roleId:roleId, roleName:roleName});
		
		$roleModalContentPromise.done(function(data){
			$roleModal.html(data);
			openModal($roleModal);
		});
			
	});
	
});

//validation
function validateFormTextFields($selector){

	//Set the form field validity to true to start off with, cause it only takes one apple to ruin this entire thing
	var isValid = true;

	var isForm = $selector.is('form');
	
	if(isForm) $selector = $selector.find('.input');
	
	//Loops through all the jQuery inputs
	$.each($selector, function(){

		var $field = $(this);
		
		var isAlpha = $field.hasClass("alpha");
		var isNumeric = $field.hasClass("numeric");
		var isOptional = $field.hasClass("optional");
		var fieldValue = $field.val();

		if(isAlpha){

			var notValidAlphaNumeric = false;

			/* Here we check what type of alpha field it is */
			var isAlphaNumeric = $field.hasClass("alpha-numeric");
			var isAlphaName = $field.hasClass("alpha-name");
			var isAlphaEmail = $field.hasClass("alpha-email");

			if(isAlphaNumeric){
				notValidAlphaNumeric = !alphaNumericRegex.test(fieldValue);
			}else if(isAlphaName){
				notValidAlphaNumeric = !alphaNameRegex.test(fieldValue);
			} else if(isAlphaEmail){
				notValidAlphaNumeric = !alphaEmailRegex.test(fieldValue);
			}

			/* Check to see if the value is blank*/
			var isBlank = $.trim(fieldValue).length === 0;

			if(!(isBlank && isOptional)){
				notValidAlphaNumeric ? $(this).addClass("errorMsgInput") : $(this).removeClass("errorMsgInput");
				//Sets the entire form to false, just because the was at least 1 invalid field
				if(notValidAlphaNumeric){	
					isValid = false;
				}
			}
			}else if(isNumeric){
				
				var notValidNumericValue = false;
				
				/* Here we check what type of numeric field it is */
				var isNumericPhone = $field.hasClass('numeric-phone');
				var isNumericExtension = $field.hasClass('numeric-extension');
				var isNumericWhole = $field.hasClass('numeric-whole');
				
				/* Check to see if the value is blank */
				var isBlank = $.trim(fieldValue).length === 0;
				
				/* Perform the proper regex test */
				if(isNumericPhone){
					notValidNumericValue = !numericPhoneRegex.test(fieldValue) || isBlank;
				} else if(isNumericExtension){
					//notValidNumericValue = (!(fieldValue.length >5 && !numericPhoneRegex.test(fieldValue)) || isBlank);
					notValidNumericValue = (fieldValue.length >5);
				} else if(isNumericWhole){
					notValidNumericValue = !numericWholeRegex.test(fieldValue) || isBlank;
				}
				
				/* If the field is not blank, validate it. If it is blank, but optional, do not 
				 * validate it. Otherwise validate it */
				if(!(isBlank && isOptional)){
					notValidNumericValue ? $(this).addClass("errorMsgInput") : $(this).removeClass("errorMsgInput");
					
					/* Sets the entire form to false, just because there was at least 1 invalid field
					 * The loops however still continues to add the errorMsgInput class to the rest of
					 * the potentialli invalid inputs
					 */
					if(notValidNumericValue){
						isValid = false;
					}
				}else{
				$(this).removeClass("errorMsgInput");
				}
			}
		});

	return isValid;
}




function openModal($selector){
	$selector.dialog("open");
}

function closeModal($selector){
	$selector.dialog("close");
}


/**
 * Function that takes in a selector and iterates through each one to format the time.
 * 
 * @param $selector
 */
function formatMillisToLocalizedTime($selector) {
	$selector.each(function(){
		var $this = $(this);
		
		var unixTime = $this.html();
		var localizedDate = convertUnixEpochToLocalizedDate(unixTime);
		
		$this.html(localizedDate);
	});
}

/**
 * Helper method that takes in a UNIX epoch time and converts it to the users localized date.
 * 
 * @param epochTime
 * @returns Localized String date
 */
function convertUnixEpochToLocalizedDate(epochTimeMilliseconds){
	var options = { month:'2-digit', day:'2-digit', year:'numeric' };
	var date = new Date(0);
	date.setUTCMilliseconds(parseInt(epochTimeMilliseconds));
	
	var localizedDate;
	
	var isValidDate = !isNaN(date.getTime());
	
	isValidDate ? localizedDate = date.toLocaleString('en-US', options) : localizedDate = null;
	
	return localizedDate;
}

function tableRowLengthCalc() {
	var tableLengthSize;
	var userScreenHeight = $(window).innerHeight();
	
	if (userScreenHeight >= 924) {
		tableLengthSize = 15;
	}
	else if (userScreenHeight >= 774) {
		tableLengthSize = 11;
	}
	else if (userScreenHeight >= 734) {
		tableLengthSize = 8;
	}
	else {
		tableLengthSize = 5;
	}
	
	return tableLengthSize;
}