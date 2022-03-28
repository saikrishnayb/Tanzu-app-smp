$(document).ready(function() {
	//Cache selectors
	var editNameLinkHtml = '<a class="edit-name-link">Edit Template Name</a>';
	var saveAndCancelName = '<a class="save-template-name">Save Template Name</a><a class="cancel-name-edit">Cancel</a>';
	var $editModal = $('#edit-template');
	var $templateName = $('.template-name');
	var $templateLinks = $('.edit-template-name');
	var $saveTemplate = $('.save-template');
	var oldTemplateName = '';
	
	//Adds input box for template name when user clicks "Edit Template Name"
	$editModal.on("click", ".edit-name-link", function() {
		oldTemplateName = $templateName.text();
		
		$templateName.empty();
		$templateName.append('<input type="text" class="edit-name" name="newTemplateName" value="' + oldTemplateName + '" />');
		
		$templateLinks.empty();
		$templateLinks.append(saveAndCancelName);
		
		$saveTemplate.css({ 'visibility' : 'hidden' });
	});
	
	//Removes input box when a user clicks Cancel if the Edit Template Name input box is active
	$editModal.on("click", ".cancel-name-edit", function() {
		$templateName.empty();
		$templateName.append(oldTemplateName + '<input type="hidden" id="template-name" name="templateName" value="' + oldTemplateName + '" />');
		
		$templateLinks.empty();
		$templateLinks.append(editNameLinkHtml);
		
		$saveTemplate.css({ 'visibility' : 'visible' });
	});
	
	//Saves the template name the user enters and displays it on the modal - does NOT save to database until user clicks the "Save" button
	$editModal.on("click", ".save-template-name", function() {
		var newTemplateName = $('.edit-name').val();
		
		$templateName.empty();
		$templateName.append(newTemplateName + '<input type="hidden" id="template-name" name="templateName" value="' + newTemplateName + '" />');
		
		$templateLinks.empty();
		$templateLinks.append(editNameLinkHtml);
		
		$saveTemplate.css({ 'visibility' : 'visible' });
	});
	
	$editModal.on('keypress', function(e) {
		if (e.which == 13) {
			$editModal.find('.save-template').trigger('click');
			event.preventDefault();
		}
	});
	
	//Saves the template when the user clicks "Save"
	$('.save-template').on('click', function() {
		var $searchTemplateForm = $('#editForm');
		var $displaySequenceInput = $('.display-sequence-text');
		var displaySequence = $displaySequenceInput.val();
		var errorMessage = $('.error');
		var templateName = $('#template-name').val();
		var templateId = $('#templateId').val();
		
		var $isTemplateNameAvailablePromise = $.ajax({
			type: "POST",
			url: "is-search-template-name-available",
			global: false,
			data: {templateName:templateName, templateId:templateId}
		});
		
		$isTemplateNameAvailablePromise.fail(function(xhr, ajaxOptions, thrownError) {
			var responseText = xhr.responseText;
			var ajaxError = JSON.parse(responseText);
			var errorDescription = ajaxError.modalErrorMessage;
			$('#customized-ajax-error').find('p').html(errorDescription);
			$('#customized-ajax-error').dialog('open');
		});
		
		$isTemplateNameAvailablePromise.done(function() {
			if (displaySequence.length == 0 || isNaN(displaySequence) || displaySequence < 0) {
				errorMessage.css({ 'visibility' : 'visible' });
				$displaySequenceInput.css({ 'background-color' : '#FFB591' });
				
				return;
			}
			else {
				errorMessage.css({ 'visibility' : 'hidden' });
				$displaySequenceInput.css({ 'background-color' : '#FFFFFF' });
			}
		
			var $saveTemplatePromise = $.post('update-search-template', $searchTemplateForm.serialize());
			$saveTemplatePromise.done(function(data) {
				var visibility = '';
				
				if (data.visibilityVendor == 'Y' && data.visibilityPenske == 'Y')
					visibility = 'BOTH';
				else if (data.visibilityVendor == 'Y' && data.visibilityPenske == 'N')
					visibility = 'VENDOR';
				else
					visibility = 'PENSKE';
				
				var rows = $('#templateTable').dataTable().fnGetNodes();
				
				for (var i = 0; i < rows.length; i++) {
					var rowTemplateId = $(rows[i]).find('.template-id').val();
					var isTemplate = (rowTemplateId == data.templateId);
					
					var rowTabId = $(rows[i]).find('.tab-id').val();
					var isTab = (rowTabId == data.tabId);
					
					var $templateRow = $(rows[i]).closest('.template-row');
					var nRow = $templateRow[0];
					
					if (isTab) {
						if (data.defaultForTab == 1 && isTemplate) {
							$('#templateTable').dataTable().fnUpdate('YES', nRow, 5);
						}
						else {
							$('#templateTable').dataTable().fnUpdate('NO', nRow, 5);
						}
					}
					
					if (isTemplate) {
						$('#templateTable').dataTable().fnUpdate(data.templateName, nRow, 2);
						$('#templateTable').dataTable().fnUpdate(data.displaySequence, nRow, 3);
						$('#templateTable').dataTable().fnUpdate(visibility, nRow, 4);
					}
				}
			});
			
			closeModal($editModal);
		});
	});
	
});

//# sourceURL=edit-search-template-modal.js