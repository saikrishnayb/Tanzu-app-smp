// URLs
var baseUrl = sessionStorage.getItem("baseUrl");

// Selectors
var $twoFactorAuthModal = $('#two-factor-auth-modal');

// Initializes the modal
ModalUtil.initializeModal($twoFactorAuthModal);

// For this page, we want to open modal right away to block the rest of page.
// Even if the modal were somehow closed, none of the other links on the page work.
ModalUtil.openModal($twoFactorAuthModal);

// Only enable button if the user submits a 6 digit access code
$twoFactorAuthModal.on('paste, input', '#access-code', function(){
	var $accessCodeInput = $('#access-code');
	var $submitAccessCodeBtn = $('#submit-access-code-btn');
	var accessCode = $accessCodeInput.val().trim();
	
	if(!isNaN(accessCode) && accessCode.length === 6)
		$submitAccessCodeBtn.removeClass('buttonDisabled');
	else
		$submitAccessCodeBtn.addClass('buttonDisabled');
});

// Submit on enter
$twoFactorAuthModal.on('keypress', '#access-code', function(e) {
	if (e.which == 13) {
		$('#submit-access-code-btn').click();
	}
});

// Resend access code
$twoFactorAuthModal.on('click', '#resend-access-code-btn', function(){
	$('.access-code-resent-row').hide();
	
	var $resendAccessCodeBtn = $('#resend-access-code-btn');
	var userId = $resendAccessCodeBtn.data('user-id');
	
	var $checkAccessCodePromise = $.ajax( {
		type: 'POST',
		url: baseUrl + '/app/login/resend-access-code', 
		data: {userId: userId},
  	});
	
	$checkAccessCodePromise.done(function(){
		$('.access-code-resent-row').show();
	});
	
});

// Submit the access code to be checked against the database
$twoFactorAuthModal.on('click', '#submit-access-code-btn', function(){
	if($submitAccessCodeBtn.hasClass('buttonDisabled'))
		return false;
	
	ModalUtil.clearAndHideErrorMessages();
	$('.access-code-resent-row').hide();
	
	var $accessCodeInput = $('#access-code');
	var $submitAccessCodeBtn = $('#submit-access-code-btn');
	
	var accessCode = $accessCodeInput.val().trim();
	var userId = $submitAccessCodeBtn.data('user-id');
	
	// Should never hit this but doesn't hurt to check
	if(isNaN(accessCode)) {
		ModalUtil.displayErrorMessages("Access Code is not a number", true);
		return false;
	}
	
	var $checkAccessCodePromise = $.ajax( {
		type: 'POST',
		url: baseUrl + '/app/login/check-access-code', 
		data: {userId: userId, accessCode: accessCode},
		// Using global: false here because we want the loading overlay to persist while it is loading the home page
		// when the user passes 2FA
		global: false,
		beforeSend: function() {
	      LoadingUtil.showLoadingOverlay(true);
	    }
  	});
	
	$checkAccessCodePromise.done(function(accessCodeResult){
		var accessCodeMatched = accessCodeResult.accessCodeMatched;
		var accessCodeGeneratedRecently = accessCodeResult.codeGeneratedRecently;
		
		// If it's valid. send them back though the validate controller method. This time they shouldn't need to enter 2FA
		var isValid = accessCodeMatched && accessCodeGeneratedRecently;
		if(isValid){
			window.location.replace(baseUrl + '/app/login/validate');
		}
		// If it's not valid, display correct message to user
		else {
			if(!accessCodeGeneratedRecently)
				ModalUtil.displayErrorMessages("Your access code has expired, and a new code has been sent to the email on file. Please enter the new access code.", true);
			else
				ModalUtil.displayErrorMessages("Access Code invalid, please try again.", true);
			
			LoadingUtil.hideLoadingOverlay();
		}
	});
});
