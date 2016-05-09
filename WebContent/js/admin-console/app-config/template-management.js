$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-template-management");
	
	var $templateTable = $('#templateTable');
	var $editModal = $('#edit-template');
	
	$templateTable.dataTable({ //All of the below are optional
				"aaSorting": [[ 4, "desc" ]], //default sort column
				"bPaginate": true, //enable pagination
				"bLengthChange": false, //enable change of records per page, not recommended
				"bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
				"bSort": false, //Allow sorting by column header
				"bInfo": true, //Showing 1 to 10 of 11 entries
				"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0 ]}],
				"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
				"iDisplayLength": 10 , //number of records per page for pagination
				"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
				"fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
											var paginateRow = $(this).parent().children('div.dataTables_paginate');
											var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);
											 
											if (pageCount > 1)  {
												paginateRow.css("display", "block");
											} else {
												paginateRow.css("display", "none");
											}
											
											//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
											var infoRow = $(this).parent().children('div.dataTables_info');
											
											infoRow.css("display", "none");
										}
	});
	
	$('#edit-template').dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 800,
		minHeight: 250,
		resizable: false,
		title: 'Template Management',
		closeOnEscape: false
	});
	
	$('#templateTable').on('click', '.edit-template', function() {
		var $this = $(this);
		var templateId = $this.closest('.template-row').find('.template-id').val();
		
		var $getTemplateModalContentPromise = $.get("get-template-modal-content", {templateId:templateId});
		$getTemplateModalContentPromise.done(function(data) {
			$editModal.html(data);
			openModal($editModal);
		});
	});
	
	
	//Edit template name stuff
	/*$('#editTemplateName').on('click', function() {
		var currentTemplateName = $('#templateName').text();
		
		$.post('./get-templates-for-template-modal', function(data) {
			var templateNames = data.split("||");
			
			for (var i = 0; i < templateNames.length; i++) {
				if (templateNames[i] == currentTemplateName) {
					$('#templateNameDropdown').append("<option val='" + templateNames[i] + "' selected>" + templateNames[i] + "</option>");
				}
				else {
					$('#templateNameDropdown').append("<option val='" + templateNames[i] + "'>" + templateNames[i] + "</option>");
				}
			}
			
			$('#editTemplateName, #templateName, #saveTemplateName, #cancelEditTemplateName, #templateNameDropdown').toggle();
		});
	});
	
	$('#cancelEditTemplateName').on('click', function() {
		$('#editTemplateName, #templateName, #saveTemplateName, #cancelEditTemplateName, #templateNameDropdown').toggle();
	});
	
	$('#saveTemplateName').on('click', function() {
		if ($('#templateNameDropdown').val() != "") {
			$('#templateName').text($('#templateNameDropdown').val());
			
			$('#editTemplateName, #templateName, #saveTemplateName, #cancelEditTemplateName, #templateNameDropdown').toggle();
		}
	});*/
	
	$('#cancelEditTemplate').on('click', function() {
		$('#editModal').dialog('close');
	});
	
	$('#saveTemplate').on('click', function() {
		var $error = $('#editError');
		var errorMsg = editFormValid();
		
		if (errorMsg.length == 0) {
			$error.addClass('hidden');
			// Submit Form Data and Close Modal / Refresh the Page.
			var $saveTemplatePromise = $.post('update-template');
			$saveTemplatePromise.done(function() {
				closeModal($('#editModal'));
			});
		}
		else {
			$error.find('.errorMsg').text('');
			$error.find('.errorMsg').append(errorMsg);
			$error.removeClass('hidden');
		}
	});
});

function editFormValid() {
	if ($('#templateNameDropdown').is(':visible')) {
		return 'Save the Template Name.';
	}
	
	if ($('#defaultForTab').val() == '') {
		return 'Default not chosen.';
	}
	
	var dispSeq = $('#displaySequence').val();
	
	if (dispSeq.length == 0) {
		return 'Display Sequence required.';
	}
	else if (!$.isNumeric(dispSeq)) {
		return 'Display Sequence must be a number.';
	}
	
	if ($('#visibility').val() == "") {
		return 'Visibility not chosen.';
	}
	
	return '';
}