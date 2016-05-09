users = [];
	

$(document).ready(function() {
	//Cached selectors
	var $editModal = $('#edit-notification');
	var $addUserButton = $('.add-button');
	var $addUserCancel = $('.cancel-addl-users-link');
	var $addlUsersSelect = $('.addl-users-select');
	var users = [];
	var $additionalUserCheckBoxes = $('.addl-users-checkbox');
	
	//Sets up vendor contacts accordion
	$('#vendor-contacts').accordion({
		active: false,
		collapsible: true,
		autoHeight: false
	});
	
	//Sets up purchasing contacts accordion
	$('#purchasing-contacts').accordion({
		active: false,
		collapsible: true,
		autoHeight: false
	});
	
	//Sets up planning contacts accordion
	$('#planning-contacts').accordion({
		active: false,
		collapsible: true,
		autoHeight: false
	});
	
	//Used for checking off Add SMC User(s) checkbox on modal load
	$additionalUserCheckBoxes.each(function() {
		var $currentCheckBox = $(this);
		
		if ($currentCheckBox.is(':checked')) {
			var $addUsersButton = $currentCheckBox.closest('.escalation-column').find('.add-button-span');
			$addUsersButton.css({'visibility' : 'visible'});
		}
	});
	
	//When a user clicks the cancel link when adding an additional user, hides the text box and links
	$addUserCancel.on("click", function() {
		var $cancelLink = $(this);
		
		cancelAddUser($cancelLink);
	});
	
	//Used for retrieving a list of e-mails for the additional users dropdown
	var $getEmailsPromise = $.get('get-user-emails.htm');
	$getEmailsPromise.done(function(data) {
		users = data.split(';');
	});
	
	//Displays the additional users dropdown and cancel link when the Add button is clicked
	$addUserButton.on("click", function() {
		var $addButton = $(this);
		
		var $deleteLinks = $addButton.closest('.escalation-column').find('.delete-user-link');
		$deleteLinks.bind('click', false);
		addSMCUser($addButton, users);
	});
	
	//Displays the add button when the additional users checkbox is checked off
	$additionalUserCheckBoxes.on("click", function() {
		var $currentCheckBox = $(this);
		
		if ($currentCheckBox.is(':checked')) {
			var $addUsersButton = $currentCheckBox.closest('.escalation-column').find('.add-button-span');
			$addUsersButton.css({'visibility' : 'visible'});
		}
		else {
			var $addUsersButton = $currentCheckBox.closest('.escalation-column').find('.add-button-span');
			$addUsersButton.css({'visibility' : 'hidden'});
		}
	});
	
	//When a user selects an option from the dropdown, adds it to the list
	$addlUsersSelect.on("change", function() {
		var $addlUsersDropdown = $(this);
		var commonUrl = $('#common-url').val();
		
		var $addlUsersSpan = $addlUsersDropdown.closest('.escalation-column').find('.addl-smc-users');
		var np = $addlUsersSpan.attr("id");
		
		var $addlUserValue = $addlUsersDropdown.val();
		
		if (np == 'esc1Vendor')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="vendorEsc1Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc2Vendor')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="vendorEsc2Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc3Vendor')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="vendorEsc3Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc1Purchasing')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="purchasingEsc1Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc2Purchasing')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="purchasingEsc2Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc3Purchasing')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="purchasingEsc3Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc1Planning')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' +  $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="planningEsc1Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc2Planning')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="planningEsc2Additional" value="' 
					+ $addlUserValue + '" /></div>');
		
		if (np == 'esc3Planning')
			$addlUsersSpan.append('<div class="addl-user"><a class="delete-user-link"><img src="' + commonUrl + '/images/delete.png" class="delete-user-image"/></a>' 
					+ '<span class="addl-user-email">' + $addlUserValue + '</span>' + '<input type="hidden" class="hidden-email" name="planningEsc3Additional" value="' 
					+ $addlUserValue + '" /></div>');
			
		var $addUserDropdownSpan = $addlUsersDropdown.closest('.escalation-column').find('.additional-users-input');
		$addUserDropdownSpan.css({'visibility' : 'hidden'});
		
		var $cancelAddUserLinkSpan = $addlUsersDropdown.closest('.escalation-column').find('.cancel-addl-users-link-span');
		$cancelAddUserLinkSpan.css({'visibility' : 'hidden'});
		
		var $deleteLinks = $addlUsersDropdown.closest('.escalation-column').find('.delete-user-link');
		$deleteLinks.unbind('click', false);
	});
	
	//Removes an email from the list when a user clicks the delete link
	$editModal.on("click", ".delete-user-link", function() {
		var $this = $(this);
		$UsersDiv = $this.closest('.addl-user');
		$UsersDiv.remove();
	});
	
	//Closes the modal and updates the database
	$('#edit-notification-save').on("click", function() {
		var $notificationForm = $('#notification-form');
		var $complianceCount = $notificationForm.find('#compliance-count');
		var complianceValue = $complianceCount.val();
		var $esc1 = $notificationForm.find('#escalation1-count');
		var escalation1Count = $esc1.val();
		var $esc2 = $notificationForm.find('#escalation2-count');
		var escalation2Count = $esc2.val();
		var $esc3 = $notificationForm.find('#escalation3-count');
		var escalation3Count = $esc3.val();
		
		//Validates compliance value
		if (complianceValue.length == 0 || isNaN(complianceValue) || complianceValue < 0) {
			var $complianceError = $notificationForm.find('.compliance-error');
			
			$complianceCount.css({ 'background-color' : '#FFB591' });
			$complianceError.css({'visibility' : 'visible'});
			return;
		}
		else {
			var $complianceError = $notificationForm.find('.compliance-error');
			
			$complianceError.css({'visibility' : 'hidden'});
			$complianceCount.css({ 'background-color' : '#FFFFFF' });
		}
		
		//Validates value for escalation 1 days
		if (escalation1Count.length == 0 || isNaN(escalation1Count) || escalation1Count < 0) {
			var $escalation1Error = $notificationForm.find('.esc1-error');
			
			$esc1.css({ 'background-color' : '#FFB591' });
			$escalation1Error.css({'visibility' : 'visible'});
			return;
		}
		else {
			var $escalation1Error = $notificationForm.find('.esc1-error');
			
			$escalation1Error.css({'visibility' : 'hidden'});
			$esc1.css({ 'background-color' : '#FFFFFF' });
		}
		
		//Validates value for escalation 2 days
		if (escalation2Count.length == 0 || isNaN(escalation2Count) || escalation2Count < 0) {
			var $escalation2Error = $notificationForm.find('.esc2-error');
			var $escalation2ErrorMessage = $notificationForm.find('.esc2-error-message');
			
			$esc2.css({ 'background-color' : '#FFB591' });
			$escalation2ErrorMessage.text('Escalation Days Must Be a Positive Number');
			$escalation2Error.css({'visibility' : 'visible'});
			return;
		}
		else if (escalation2Count < escalation1Count) {
			var $escalation2Error = $notificationForm.find('.esc2-error');
			var $escalation2ErrorMessage = $notificationForm.find('.esc2-error-message');
			
			$esc2.css({ 'background-color' : '#FFB591' });
			$escalation2ErrorMessage.text('Value must be greater than that of escalation 1.');
			$escalation2Error.css({'visibility' : 'visible'});
			return;
		}
		else {
			var $escalation2Error = $notificationForm.find('.esc2-error');
			
			$escalation2Error.css({'visibility' : 'hidden'});
			$esc2.css({ 'background-color' : '#FFFFFF' });
		}
		
		//Validates value for escalation 3 days
		if (escalation3Count.length == 0 || isNaN(escalation3Count) || escalation3Count < 0) {
			var $escalation3Error = $notificationForm.find('.esc3-error');
			var $escalation3ErrorMessage = $notificationForm.find('.esc3-error-message');
			
			$esc3.css({ 'background-color' : '#FFB591' });
			$escalation3ErrorMessage.text('Escalation Days Must Be a Positive Number');
			$escalation3Error.css({'visibility' : 'visible'});
			return;
		}
		else if (escalation3Count < escalation2Count) {
			var $escalation3Error = $notificationForm.find('.esc3-error');
			var $escalation3ErrorMessage = $notificationForm.find('.esc3-error-message');
			
			$esc3.css({ 'background-color' : '#FFB591' });
			$escalation3ErrorMessage.text('Value must be greater than that of escalation 2.');
			$escalation3Error.css({'visibility' : 'visible'});
			return;
		}
		else {
			var $escalation3Error = $notificationForm.find('.esc3-error');
			
			$escalation3Error.css({'visibility' : 'hidden'});
			$esc3.css({ 'background-color' : '#FFFFFF' });
		}
		
		//Submits form for processing
		var $saveNotificationPromise = $.post('update-notification.htm', $notificationForm.serialize());
		$saveNotificationPromise.done(function(data) {
			var notificationId = $('.notification-id-modal').val();
			
			//Redraws the table with any new information
			var rows = $('#notification-table').dataTable().fnGetNodes();
			
			for (var j = 0; j < rows.length; j++) {
				var rowNotificationId = $(rows[j]).find('.notification-id').val();
				var isMatch = (notificationId == rowNotificationId);
				var $notificationTable = $('#notification-table');
				
				if (isMatch) {
					var visibility = $(rows[j]).find('.visibility').val();
					
					//Redraws a line for VENDOR
					if (visibility == 'VENDOR') {
						var $notificationRow = $(rows[j]).closest('.notification-row');
						var nRow = $notificationRow[0];
						
						$notificationTable.dataTable().fnUpdate(data.complianceType, nRow, 3);
						$notificationTable.dataTable().fnUpdate(data.complianceCount, nRow, 4);
						$notificationTable.dataTable().fnUpdate(data.escalationOneDays, nRow, 5);
						
						
						//Update vendor escalation 1 contacts
						var i = 0;
						var vendorEsc1Contacts = '[ ';
						
						if (data.vendorEsc1Parties != null) {
							for (i = 0; i < data.vendorEsc1Parties.length; i++) {
								vendorEsc1Contacts = vendorEsc1Contacts + data.vendorEsc1Parties[i].contact;
								if (i != (data.vendorEsc1Parties.length - 1)) {
									vendorEsc1Contacts = vendorEsc1Contacts + ', ';
								}
							}
						}
						vendorEsc1Contacts = vendorEsc1Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(vendorEsc1Contacts, nRow, 6);
						
						$notificationTable.dataTable().fnUpdate(data.escalationTwoDays, nRow, 7);
						
						var vendorEsc2Contacts = '[ ';
						if (data.vendorEsc2Parties != null) {
							for (i = 0; i < data.vendorEsc2Parties.length; i++) {
								vendorEsc2Contacts = vendorEsc2Contacts + data.vendorEsc2Parties[i].contact;
								if (i != (data.vendorEsc2Parties.length - 1)) {
									vendorEsc2Contacts = vendorEsc2Contacts + ', ';
								}
							}
						}
						vendorEsc2Contacts = vendorEsc2Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(vendorEsc2Contacts, nRow, 8);
						
						$notificationTable.dataTable().fnUpdate(data.escalationThreeDays, nRow, 9);
						
						var vendorEsc3Contacts = '[ ';
						if (data.vendorEsc3Parties != null) {
							for (i = 0; i < data.vendorEsc3Parties.length; i++) {
								vendorEsc3Contacts = vendorEsc3Contacts + data.vendorEsc3Parties[i].contact;
								if (i != (data.vendorEsc3Parties.length - 1)) {
									vendorEsc3Contacts = vendorEsc3Contacts + ', ';
								}
							}
						}
						vendorEsc3Contacts = vendorEsc3Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(vendorEsc3Contacts, nRow, 10);
					}
					
					//Redraws a line for PURCHASING
					if (visibility == 'PURCHASING') {
						var $notificationRow = $(rows[j]).closest('.notification-row');
						var nRow = $notificationRow[0];
						
						$notificationTable.dataTable().fnUpdate(data.complianceType, nRow, 3);
						$notificationTable.dataTable().fnUpdate(data.complianceCount, nRow, 4);
						$notificationTable.dataTable().fnUpdate(data.escalationOneDays, nRow, 5);
						
						//Update vendor escalation 1 contacts
						var i = 0;
						var purchasingEsc1Contacts = '[ ';
						
						if (data.purchasingEsc1Parties != null) {
							for (i = 0; i < data.purchasingEsc1Parties.length; i++) {
								purchasingEsc1Contacts = purchasingEsc1Contacts + data.purchasingEsc1Parties[i].contact;
								if (i != (data.purchasingEsc1Parties.length - 1)) {
									purchasingEsc1Contacts = purchasingEsc1Contacts + ', ';
								}
							}
						}
						purchasingEsc1Contacts = purchasingEsc1Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(purchasingEsc1Contacts, nRow, 6);
						
						$notificationTable.dataTable().fnUpdate(data.escalationTwoDays, nRow, 7);
						
						var purchasingEsc2Contacts = '[ ';
						if (data.purchasingEsc2Parties != null) {
							for (i = 0; i < data.purchasingEsc2Parties.length; i++) {
								purchasingEsc2Contacts = purchasingEsc2Contacts + data.purchasingEsc2Parties[i].contact;
								if (i != (data.purchasingEsc2Parties.length - 1)) {
									purchasingEsc2Contacts = purchasingEsc2Contacts + ', ';
								}
							}
						}
						purchasingEsc2Contacts = purchasingEsc2Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(purchasingEsc2Contacts, nRow, 8);
						
						$notificationTable.dataTable().fnUpdate(data.escalationThreeDays, nRow, 9);
						
						var purchasingEsc3Contacts = '[ ';
						if (data.purchasingEsc3Parties != null) {
							for (i = 0; i < data.purchasingEsc3Parties.length; i++) {
								purchasingEsc3Contacts = purchasingEsc3Contacts + data.purchasingEsc3Parties[i].contact;
								if (i != (data.purchasingEsc3Parties.length - 1)) {
									purchasingEsc3Contacts = purchasingEsc3Contacts + ', ';
								}
							}
						}
						purchasingEsc3Contacts = purchasingEsc3Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(purchasingEsc3Contacts, nRow, 10);
					}
					
					//Redraws a line for PLANNING
					if (visibility == 'PLANNING') {
						var $notificationRow = $(rows[j]).closest('.notification-row');
						var nRow = $notificationRow[0];
						
						$notificationTable.dataTable().fnUpdate(data.complianceType, nRow, 3);
						$notificationTable.dataTable().fnUpdate(data.complianceCount, nRow, 4);
						$notificationTable.dataTable().fnUpdate(data.escalationOneDays, nRow, 5);
						
						//Update vendor escalation 1 contacts
						var i = 0;
						var planningEsc1Contacts = '[ ';
						if (data.planningEsc1Parties != null) {
							for (i = 0; i < data.planningEsc1Parties.length; i++) {
								planningEsc1Contacts = planningEsc1Contacts + data.planningEsc1Parties[i].contact;
								if (i != (data.planningEsc1Parties.length - 1)) {
									planningEsc1Contacts = planningEsc1Contacts + ', ';
								}
							}
						}
						planningEsc1Contacts = planningEsc1Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(planningEsc1Contacts, nRow, 6);
						
						$notificationTable.dataTable().fnUpdate(data.escalationTwoDays, nRow, 7);
						
						var planningEsc2Contacts = '[ ';
						if (data.planningEsc2Parties != null) {
							for (i = 0; i < data.planningEsc2Parties.length; i++) {
								planningEsc2Contacts = planningEsc2Contacts + data.planningEsc2Parties[i].contact;
								if (i != (data.planningEsc2Parties.length - 1)) {
									planningEsc2Contacts = planningEsc2Contacts + ', ';
								}
							}
						}
						planningEsc2Contacts = planningEsc2Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(planningEsc2Contacts, nRow, 8);
						
						$notificationTable.dataTable().fnUpdate(data.escalationThreeDays, nRow, 9);
						
						var planningEsc3Contacts = '[ ';
						if (data.planningEsc3Parties != null) {
							for (i = 0; i < data.planningEsc3Parties.length; i++) {
								planningEsc3Contacts = planningEsc3Contacts + data.planningEsc3Parties[i].contact;
								if (i != (data.planningEsc3Parties.length - 1)) {
									planningEsc3Contacts = planningEsc3Contacts + ', ';
								}
							}
						}
						planningEsc3Contacts = planningEsc3Contacts + ' ]';
						$notificationTable.dataTable().fnUpdate(planningEsc3Contacts, nRow, 10);
					}
				}
			}
			
			closeModal($editModal);
		});
	});
});


//Helper function for showing text box and links for additional user
function addSMCUser(additionalUsersButton, users) {
	var $addUserSpan = additionalUsersButton.closest('.escalation-column').find('.additional-users-input');
	var $addUserDropdown = additionalUsersButton.closest('.escalation-column').find('.addl-users-select');
	var presentEmails = [];
	var isEmailPresent = false;
		
	$addUserSpan.closest('.escalation-column').find('.hidden-email').each(function() {
		presentEmails.push($(this).val());
	});
	
	$addUserDropdown.children().remove();
	$addUserDropdown.append('<option value=""></option>');
	
	for (var i = 0; i < users.length; i++) {
		isEmailPresent = false;
		if (users[i] != "") {
			for (var j = 0; j < presentEmails.length; j++) {
				if (presentEmails[j] == users[i])
					isEmailPresent = true;
			}
			
			if (!isEmailPresent)
				$addUserDropdown.append('<option value=' + users[i] + '>' + users[i] + '</option>');
		}
	}
	
	$addUserSpan.css({'visibility' : 'visible'});
	
	var $cancelAddlUserLink = additionalUsersButton.closest('.escalation-column').find('.cancel-addl-users-link-span');
	$cancelAddlUserLink.css({'visibility' : 'visible'});
}


//Helper function for hiding text box and links for additional user
function cancelAddUser(cancelLink) {
	var $addUserDropdown = cancelLink.closest('.escalation-column').find('.additional-users-input');
	$addUserDropdown.css({'visibility' : 'hidden'});
	
	var $cancelAddUserLink = cancelLink.closest('.escalation-column').find('.cancel-addl-users-link-span');
	$cancelAddUserLink.css({'visibility' : 'hidden'});
	
	var $deleteLinks = cancelLink.closest('.escalation-column').find('.delete-user-link');
	$deleteLinks.unbind('click', false);
}


//Helper function for hiding text box and links for additional user
function saveAddUser(saveLink) {
	var $addUserTextBox = saveLink.closest('.escalation-column').find('.additional-users-input');
	$addUserTextBox.css({'visibility' : 'hidden'});
	
	var $addUserLinks = saveLink.closest('.escalation-column').find('.additional-users-links');
	$addUserLinks.css({'visibility' : 'hidden'});
}