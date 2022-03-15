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

$twoFactorAuthModal.on('click', '#submit-access-code-btn', function(){
	ModalUtil.clearAndHideErrorMessages();
	
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
	
	$checkAccessCodePromise.done(function(pair){
		var accessCodeMatched = pair.left;
		var accessCodeExpired = pair.right;
		
		var isValid = accessCodeMatched && !accessCodeExpired;
		if(isValid){
			window.location.href = '/two-factor-auth-passed?' + $.param({userId : userId});
		}
		else {
			if(accessCodeExpired)
				ModalUtil.displayErrorMessages("Your access code has expired, and a new code has been sent to the email on file. Please enter the new access code.", true);
			else
				ModalUtil.displayErrorMessages("Access Code invalid, please try again.", true);
		}
	});
});
