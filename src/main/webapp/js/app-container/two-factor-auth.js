// Selectors
var $twoFactorAuthModal = $('#two-factor-auth-modal');

// Initializes the modal
ModalUtil.initializeModal($twoFactorAuthModal);

ModalUtil.openModal($twoFactorAuthModal);

$twoFactorAuthModal.on('paste, input', function(){
	var $accessCodeInput = $('#access-code')
	var $submitAccessCodeBtn = $('#submit-access-code-btn')
	var accessCode = $accessCodeInput.val().trim();
	
	if(!isNaN(accessCode) && accessCode.length === 6)
		$submitAccessCodeBtn.removeClass('buttonDisabled');
	else
		$submitAccessCodeBtn.addClass('buttonDisabled');
})