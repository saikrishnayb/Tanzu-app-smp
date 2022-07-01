"use strict";

/**
 * NOTE: this file was mostly copied from SMCOF's global.js around 2020-05-11. Not every module was copied, though, since they aren't all needed in suppliermgmt at this time.
 * If we have need of other *Util objects from the global.js later on, they should be brought over as separate files (ex: modal-uitl.js, number-util.js).
 * Also, the handleAjaxError() function was changed (again as of 2020-05-11) to be more like that in leasedealmgmt, since that one is cleaner. This affects the structure of
 * 	Java classes like AjaxError and ValidationError
 */

/*************** Cached Selectors ***************/
var $globalModal = $('#modal-global');
var $errorModal = $('#modal-error');
var $stackTraceModal = $('#modal-stack-trace')
var $pageErrorContainer = $('#page-error-container');
var $parentWindow = $(window.parent);

var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
var baseAppUrl = sessionStorage.getItem('baseAppUrl');
var baseAdminConsoleUrl = sessionStorage.getItem('baseAdminConsoleUrl');
var baseBuildMatrixUrl = sessionStorage.getItem('baseBuildMatrixUrl');

//Timer used to track when the fade-out should occur on the success message for copying an exception stack trace to the clipboard
var copySuccessTimer;

/*************** AJAX Setup ***************/
//Most of our AJAX requests in this application are dynamic, and hence should never be cached.
$.ajaxSetup({
	cache: false
});

$(document).ajaxError(function(event, jqXHR, settings, thrownError) {

	var ajaxErrorObject = null;

	try {
		ajaxErrorObject = jQuery.parseJSON(jqXHR.responseText);
	}catch(exception) {}

	LoadingUtil.hideLoadingOverlay(true);	//Make sure to always close the spinner if something blows up
	
	handleAjaxError(ajaxErrorObject, jqXHR.status);
});

$.ajaxPrefilter(function(options, originalOptions, jqXHR) {

	var ajaxIsNotPost = options.type.toUpperCase() !== 'POST';
	var ignoreAjaxPreFilter = options.ignorePreFilter;

	//Only clear the error and warning labels if the request is a POST and the caller didn't tell us not to
	if(ajaxIsNotPost || ignoreAjaxPreFilter) return true;

	clearErrorAndWarningLabels();
});

$(document).ajaxStart(function() {
	LoadingUtil.showLoadingOverlay(true);
});

$(document).ajaxStop(function() {
	LoadingUtil.hideLoadingOverlay(true);
});

$(window).on('beforeunload', function(event){
	var ignoreBeforeUnload = sessionStorage.getItem('ignoreBeforeUnload') === "true";
	if(ignoreBeforeUnload) {
		sessionStorage.setItem('ignoreBeforeUnload', "false");
		return;
	}
	
	LoadingUtil.showLoadingOverlay(true);
});

/*************** Initialization ***************/
//Setup the modals that are available on every page
ModalUtil.initializeModal($globalModal);
ModalUtil.initializeModal($errorModal);
ModalUtil.initializeModal($stackTraceModal);

//Setup ritsu (validation utility), and tell it we're using bootstrap styling
ritsu.initialize({
	useBootstrap3Stlying: true
});

//DataTable Defaults
$.extend($.fn.dataTable.defaults, {
	responsive: true,
	stateSave: true,
	autoWidth: false
});

//Datepicker Defaults
$.datepicker.setDefaults({

	//Allow the user to change months and years
	changeMonth:true,
	changeYear:true,

	//Display dates in other months at the start and end of the current month, so we are always showing full weeks. 
	showOtherMonths: true,

	//Allow the datepicker to open either when the button is clicked or when the control receives focus. 
	showOn: "both",
	buttonImage: commonStaticUrl +  "/images/calendar.png",
	buttonImageOnly: true,
	showButtonPanel: false,

	//Use "Sun", "Mon", "Tue" instead of "Su", "Mo", "Tu" in the popup 
	dayNamesMin: $.datepicker._defaults.dayNamesShort,

	//Change the button image when opening and closing the datepicker
	onClose: function(dateText, inst) {
		$(this).datepicker('option','buttonImage', commonStaticUrl + '/images/calendar.png');
	},
	beforeShow: function(input, inst) {
		$(this).datepicker('option','buttonImage', commonStaticUrl + '/images/calendar_selected.png');
	},

	//If dates in the past are not allowed (denoted by the 'data-no-past-date' attribute), don't allow the user to select them.
	// This same attribute is used by form validation, too, so the behavior is consistent.
	beforeShowDay: function(date) {
		var pastDatesValid = !this.hasAttribute('data-no-past-date');
		var dateIsNotPast = date.getTime() >= new Date().getTime();
		return [pastDatesValid || dateIsNotPast,""];
	}
});

$(function() {

	//Force the parent frame to calculate the correct sizing for the inner frame data (this ties into windowResizeListener())
	var hasIframe = $(parent.document).find('iframe').length > 0;
	if(hasIframe) $parentWindow.trigger('resize');

	//Remove any navigation spinner that might still be out there
	LoadingUtil.hideLoadingOverlay();
	
	//***** VERSION 1 PAGE TEMPLATE HELPER *****//
	// NOTE: this block of statements is only here to hide the spinner when navigating FROM a v1 page template TO a v2 page template,.
	// since the v1 spinner resides in the parent window, and hence it will still be showing when the page loads.
	// Once all pages are moved off of the v1 template, these statements should be removed.
	$("#processingText", parent.document).text('');
	$(".processingImg, .overlayadmin", parent.document).css('visibility', 'hidden');
	//***** END VERSION 1 PAGE TEMPLATE HELPER *****//

	//If we had any table containers that were hidden while the table data loaded, then show the table now that all data has loaded
	$('.table-container').removeClass('invisible');

});

/*************** Listeners ***************/
//Upon resizing the parent window, re-adjust data table columns and reset the position of open modals, to keep things visible as the window size changes
var windowResizeTimer;
function windowResizeListener(event) {

	clearTimeout(windowResizeTimer);
	windowResizeTimer = setTimeout(function() {

		var $openedModal = ModalUtil.getTopMostOpenedModal();

		var position = $openedModal.dialog( "option", "position");
		$openedModal.dialog( "option", "position", position);

		var hasChildModal = $openedModal.find('.modal-content').data('contains-child-modal') !== undefined;
		if(hasChildModal) $('.modal-child').dialog( "option", "position", position);

		$.fn.dataTable.tables( { visible: true, api: true } ).columns.adjust();

		var hasIframe = $(parent.document).find('iframe').length > 0;
		if(hasIframe) {
			$parentWindow.off('resize', windowResizeListener);
			$parentWindow.on('resize', windowResizeListener);
		}

	}, 0);
}
$parentWindow.on('resize', windowResizeListener);

//Show the stack trace modal when we're in the error modal if the user clicks "Show Details"
$errorModal.on('click', '.show-stack-trace', function() {
	
	var exceptionCause = $errorModal.data('exceptionCause');
	var exceptionMessage = $errorModal.data('exceptionMessage');
	var exceptionStackTrace = $errorModal.data('exceptionStackTrace');
	
	$stackTraceModal.find('.exception-message').html(exceptionMessage);
	$stackTraceModal.find('.exception-stack-trace').html(exceptionStackTrace);
	
	ModalUtil.openModal($stackTraceModal);
});

//Attempt to copy the stack trace to the clipboard for easy pasting
$stackTraceModal.on('click', '.btn-copy-stack', function() {
	
	var textToCopy = $stackTraceModal.find('.exception-message')[0].textContent + "\n" + $stackTraceModal.find('.exception-stack-trace')[0].textContent;
	
	var textArea = document.createElement("textarea");
	textArea.style.zIndex = "50";
	textArea.style.width = "1px";
	textArea.style.width = "1px";
	$stackTraceModal.append(textArea);
	 
	textArea.textContent = textToCopy;
	textArea.focus();
	textArea.setSelectionRange(0, textArea.value.length);
	 
	var commandSupported = document.execCommand("copy");
	$(textArea).remove(); 
	 
	
	if(commandSupported) {
		
		window.clearTimeout(copySuccessTimer);
		
		$stackTraceModal.find('.successful-copy').fadeIn(1000);
	
		copySuccessTimer = window.setTimeout(function() {
			$stackTraceModal.find('.successful-copy').fadeOut(1000);
		}, 3000);
	} 
	else
		alert("Browser does not support clipboard copy");
});

//Add a close handler for the error container at the top of each page
$pageErrorContainer.on('click', '.close', function() {
  $pageErrorContainer.hide().find('ul').empty();
});

/*************** Helper Functions ***************/
function handleAjaxError(ajaxError, status) {
	
	if(ajaxError == null || ajaxError.length === 0) {
		var supportNum = sessionStorage.getItem('supportPhoneNum');
		ajaxError = {
			modalErrorMessage: 'Something went wrong with the request. Please try again.',
			modalTitle: 'Error'
		}
	}
	
	var errorText = ajaxError.modalErrorMessage;
	var errorTitle = ajaxError.modalTitle;
	var exceptionMessage = ajaxError.exceptionMessage;
	var exceptionStackTrace = ajaxError.exceptionStackTrace;
	var exceptionTime = ajaxError.currentTimeFormatted;
	var critical = ajaxError.critical;
	
	var isValidationError = status === 400 && ajaxError.validationErrors && ajaxError.validationErrors.length > 0;
	var isNonCritical = critical === false;
	
	//Identify the error container in which to put validation error messages
	// If there is no open lightbox, the page-level error container will be selected.
	var topmostModal = ModalUtil.getTopMostOpenedModal();
	//The topmostModal might not exist (if no modal is open), so we might be passing an empty jQuery here. That's ok.
	var errorContainer = getErrorContainer(topmostModal.find('.error-container'));
	var modalIsOpen = topmostModal.length !== 0;
	
	if((isValidationError || isNonCritical) && errorContainer && errorContainer.length > 0) {
		
		//Validation errors (and non-critical errors) go near the element they pertain to (based on their "path" property)
		// If that can't be located, then put them in a section at the top of the page (or the top-most lightbox)
		// If there isn't any error container on the page, then fall back to showing them in a lightbox
		
		var messagesToDisplay ;
		if(isValidationError)
			messagesToDisplay = ajaxError.validationErrors;
		else
			messagesToDisplay = [{message: errorText}];
		
		//This is the list element that error messages that don't get attached to a specific element will go in
		var errorList = errorContainer.find('ul.error-list');
		//We only print each specific message once in the general error list, so we keep track of the ones we've already printed
		var uniqueErrorTexts = {};
		
		/*
		 * This JS method is set up to handle more complex validation errors than suppliermgmt has (as of 2020-05-12), since it was taken from SMCOF.
		 * If we ever implement more sophisticated validation, as in SMCOF, then this method is already ready to handle that validation, perhaps with a couple of tweaks.
		 * Properties on the ValidationError objecct that this block can handle are:
		 *   * message (Required) - the message to render to the user
		 *   * path - the "name" property of the element on the page that the error message is associated with
		 *   * severity - an enum with things like ERROR and WARNING. If provided, different styling will be used on messages
		 *   * extraInfo - if provided, this should be appended to the end of the "message" property when displaying to the user
		 */
		$.each(messagesToDisplay, function() {
			
			var message = this.message + (this.extraInfo ? ' ' + this.extraInfo : '');
			
			var isWarning = this.severity === 'WARNING';
			var labelClass = (isWarning) ? 'warning-label' : 'error-label';
			
			//If the error has a "path" property, try to match it to an element with that name on the page, and locate the message as close to that element as possible
			//First, escape some characters from the path (left-bracket, right-bracket, and dot) that could give jQuery trouble
			var path = this.path == null ? '' : this.path.replace(/([.[\]])/g, "\\$1");
			var $element = !path ? $() : modalIsOpen? topmostModal.find(':input[name=' + path + ']') : $(':input[name=' + path + ']');
			
			//If there isn't an error with the given path, or if no path was given, then the error will get shown in the nearest general error container
			var elementFound = $element.length > 0 && $element.is(":visible");
			if(elementFound) {
				//If we found an element with the path, try to locate an error container nearby that element to put the error label in
				var id = $element.attr('id');
				var $labelParent = $element.closest('.error-label-container');
				
				//If there isn't an error label container, just stick the error in the element's parent container
				if($labelParent.length == 0) $labelParent = $element.parent();
				
				//Add the error as a label
				$labelParent.append('<label class="' + labelClass + '"' + (id ? ' for="' + id + '"' : '') + '>' + message + '</label>');
			} else {
				var listItem = '<li class="' + labelClass + '">' + message + '<//li>';
				
				//Only show each message once
				if(uniqueErrorTexts[message] !== true) {
					uniqueErrorTexts[message] = true;
					addErrorMessage(message, errorContainer, labelClass);
				}
			}
		});
	} else {
		
		//If the page doesn't have an error container in the header, or if it's a severe enough message, then show it in a modal dialog
		$errorModal.find('.error-text').text(errorText);
		$errorModal.dialog("option", "title", errorTitle);
		
		//If the error has exception information attached to it (ex: a stack trace), then add a link to show those details.
		var hasExceptionInfo = exceptionStackTrace || exceptionMessage;
		if(hasExceptionInfo) $errorModal.data('exceptionMessage', exceptionMessage)
			                            .data('exceptionStackTrace', exceptionStackTrace).find('.error-text')
			                            .append('<br><br><p>For more information click <a class="show-stack-trace">here.</a></p>'); //Just use <br>s to space out, i know i know...
		
		ModalUtil.openModal($errorModal);
	}
}

/**
 * Adds an error message to a message container on the page, if one exists on the page.
 * @param message {String} The text of the message to add to the error container.
 * @param errorContainer Optional {jQuery} the error container to add the message to.
 * 	If not provided, the error container in the topmost open modal will be used.
 * 	If that isn't found, then the error container in the page will be used.
 * @param labelClass Optional {String} the class that should be added to the error message when it is displayed
 */
function addErrorMessage(message, errorContainer, labelClass) {
	if(!errorContainer || errorContainer.length === 0) {
		var topmostModal = ModalUtil.getTopMostOpenedModal();
		errorContainer = getErrorContainer(topmostModal.find('.error-container'));
	}
	
	labelClass = labelClass || 'error-label';
	
	var errorList = errorContainer.find('ul.error-list');
	errorList.append('<li class="' + labelClass + '">' + message + '</li>');
	
	if(errorList.children('li').length > 0)
		errorContainer.show();
}

function clearErrorAndWarningLabels() {
	
	$('.error-label, .warning-label').not('.ajax-persistent').remove();

	$('.error-container').each(function() {
		var noMoreErrors = $(this).find('ul.error-list').children('li').length === 0;
		if(noMoreErrors)
			$(this).hide();
	});
}

/**
 * Retrieves the closest error container to the given element, or the page-level error container if no element is given.
 * @param selector {jQuery|String} Optional. The method will find the closest error container to this argument.
 */
function getErrorContainer(selector) {
	var errorContainer = undefined;
	if(selector) {
		if($(selector).is(".error-container"))
			errorContainer = $(selector);
		else
			errorContainer = $(selector).closest('.error-container');
	}
	if(!errorContainer || errorContainer.length === 0)
		errorContainer = $pageErrorContainer;
	
	return errorContainer;
};