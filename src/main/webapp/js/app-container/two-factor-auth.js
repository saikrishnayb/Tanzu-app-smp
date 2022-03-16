// Selectors
var $twoFactorAuthModal = $('#two-factor-auth-modal');

// Initializes the modal
ModalUtil.initializeModal($twoFactorAuthModal);

ModalUtil.openModal($twoFactorAuthModal);

$twoFactorAuthModal.on('paste, input', '#access-code', function(){
	var $accessCodeInput = $('#access-code');
	var $submitAccessCodeBtn = $('#submit-access-code-btn');
	var accessCode = $accessCodeInput.val().trim();
	
	if(!isNaN(accessCode) && accessCode.length === 6)
		$submitAccessCodeBtn.removeClass('buttonDisabled');
	else
		$submitAccessCodeBtn.addClass('buttonDisabled');
});

$twoFactorAuthModal.on('click', '#resend-access-code-btn', function(){
	$('.access-code-resent-row').hide();
	
	var $resendAccessCodeBtn = $('#resend-access-code-btn');
	var userId = $resendAccessCodeBtn.data('user-id');
	
	var $checkAccessCodePromise = $.ajax( {
		type: 'POST',
		url: 'resend-access-code', 
		data: {userId: userId},
		global: false,
		beforeSend: function() {
		  LoadingUtil.showLoadingOverlay(true);
		}
  	});
	
	$checkAccessCodePromise.done(function(){
		$('.access-code-resent-row').show();
	});
	
});

$twoFactorAuthModal.on('click', '#submit-access-code-btn', function(){
	ModalUtil.clearAndHideErrorMessages();
	$('.access-code-resent-row').hide();
	
	var $accessCodeInput = $('#access-code');
	var $submitAccessCodeBtn = $('#submit-access-code-btn');
	
	var accessCode = $accessCodeInput.val().trim();
	var userId = $submitAccessCodeBtn.data('user-id');
	
	if($submitAccessCodeBtn.hasClass('buttonDisabled'))
		return false;
	
	if(isNaN(accessCode)) {
		ModalUtil.displayErrorMessages("Access Code is not a number", true);
		return false;
	}
	
	var $checkAccessCodePromise = $.ajax( {
		type: 'POST',
		url: 'check-access-code', 
		data: {userId: userId, accessCode: accessCode},
		global: false,
		beforeSend: function() {
		  LoadingUtil.showLoadingOverlay(true);
		}
  	});
	
	$checkAccessCodePromise.done(function(accessCodeResult){
		var accessCodeMatched = accessCodeResult.accessCodeMatched;
		var accessCodeGeneratedRecently = accessCodeResult.codeGeneratedRecently;
		
		var isValid = accessCodeMatched && accessCodeGeneratedRecently;
		if(isValid){
			window.location.href = '/two-factor-auth-passed?' + $.param({userId : userId});
		}
		else {
			if(!accessCodeGeneratedRecently)
				ModalUtil.displayErrorMessages("Your access code has expired, and a new code has been sent to the email on file. Please enter the new access code.", true);
			else
				ModalUtil.displayErrorMessages("Access Code invalid, please try again.", true);
		}
	});
});
