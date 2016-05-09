var $alertTable = $('#alert-table');
var $editHeaderModal = $('#edit-header-modal');
var $editDetailModal = $('#edit-detail-modal');

$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-alerts");
	
	/* ----------- Datatable Declaration ----------- */
	var iDisplayLength = tableRowLengthCalc();
	
	$alertTable.dataTable({ 					//All of the below are optional
				"aaSorting": [[ 2, "asc" ]], 	//default sort column
				"bPaginate": true, 				//enable pagination
				"bLengthChange": false, 		//enable change of records per page, not recommended
				"bFilter": false, 				//Allows dynamic filtering of results, do not enable if using ajax for pagination
				"bSort": false, 				//Allow sorting by column header
				"bInfo": true, 					//Showing 1 to 10 of 11 entries
				"bAutoWidth": false,
				"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0 ]},
				                 {"sWidth": "50px", "aTargets": [ 0 ]}],
				"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
				"iDisplayLength": iDisplayLength , 			//number of records per page for pagination
				"oLanguage": {"sEmptyTable": "No alerts or alert headers were found."}, //Message displayed when no records are found
				"fnDrawCallback": function() { 	//This will hide the pagination menu if we only have 1 page.
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
	
	/* ----------- Modal Declarations ----------- */
	$editHeaderModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 650,
		minHeight: 200,
		resizable: false,
		title: 'Alert Management',
		closeOnEscape: false
	});
	
	$editDetailModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 700,
		minHeight: 300,
		resizable: false,
		title: 'Alert Management',
		closeOnEscape: false
	});
	
	/* ----------- Alert Table ----------- */
	$alertTable.on('click', '.edit-alert', function() {
		var $row = $(this).closest('tr');
		var alertType = $row.find('.alert-type').text();
		
		// If the Type is HEADER, then open the Header Pop-up Modal.
		if (alertType == 'HEADER') {
			openPopupHeaderModal($row);
		}
		// If the Type is DETAIL, then open the Detail Pop-up Modal.
		else if (alertType == 'DETAIL') {
			openPopupDetailModal($row);
		}
	});
});

/**
 * This method populates the values in the HEADER modal and opens the pop-up for display to the user.
 * 
 * @param $row	- the row element that was selected by the user.
 */
function openPopupHeaderModal($row) {
	var alertId = $row.find('[name="alertId"]').val();
	var alertType = $row.find('.alert-type').text();
	var tabName = $row.find('.tab-name').text();
	var headerName = $row.find('.header-name').text();
	var helpText = $row.find('.help-text').text();
	var displaySequence = $row.find('.display-sequence').text();

	// Set the values in the modal's form based on information from the selected datatable row.
	$editHeaderModal.find('[name="alertId"]').val(alertId);
	$editHeaderModal.find('[name="alertType"]').val(alertType);
	$editHeaderModal.find('#tab-name').text(tabName);
	$editHeaderModal.find('#header-name').text(headerName);
	$editHeaderModal.find('[name="headerName"]').val(headerName);
	$editHeaderModal.find('[name="helpText"]').val(helpText);
	$editHeaderModal.find('[name="displaySequence"]').val(displaySequence);
	
	// Display only the elements that are needed upon the pop-up opening.
	resetModalElementVisibility($editHeaderModal);

	// Open the pop-up modal.
	$editHeaderModal.dialog('open');
}

/**
 * This method populates the values in the DETAIL modal and opens the pop-up for display to the user.
 * 
 * @param $row	- the row element that was selected by the user.
 */
function openPopupDetailModal($row) {
	var alertId = $row.find('[name="alertId"]').val();
	var alertType = $row.find('.alert-type').text();
	var tabName = $row.find('.tab-name').text();
	var headerName = $row.find('.header-name').text();
	var alertName = $row.find('.alert-name').text();
	var templateName = $row.find('.template-name span').text();
	var templateId = $row.find('[name="templateId"]').val();
	var helpText = $row.find('.help-text').text();
	var displaySequence = $row.find('.display-sequence').text();
	var visibilityPenske = $row.find('[name="visibilityPenske"]').val();
	var visibilityVendor = $row.find('[name="visibilityVendor"]').val();
	var visibility = '';
	
	if (visibilityPenske == '0' && visibilityVendor == '1') {
		visibility = '1';
	}
	else if (visibilityPenske == '1' && visibilityVendor == '0') {
		visibility = '2';
	}
	else if (visibilityPenske == '1' && visibilityVendor == '1') {
		visibility = '3';
	}
	
	$.post('./get-search-templates.htm',
			function(data) {
				$editDetailModal.html(data);
				var $templateDropdown = $editDetailModal.find('#template-name-change');
				
				// Set the values in the modal's form based on information from the selected datatable row.
				$editDetailModal.find('[name="alertId"]').val(alertId);
				$editDetailModal.find('[name="alertType"]').val(alertType);
				$editDetailModal.find('#tab-name').text(tabName);
				$editDetailModal.find('#header-name').text(headerName);
				$editDetailModal.find('#alert-name').text(alertName);
				$editDetailModal.find('[name="alertName"]').val(alertName);
				$editDetailModal.find('#template-name').text(templateName);
				$editDetailModal.find('[name="helpText"]').val(helpText);
				$editDetailModal.find('[name="displaySequence"]').val(displaySequence);
				$editDetailModal.find('[name="visibility"]').val(visibility);

				// Set the original template to the selected value in the dropdown.
				$templateDropdown.find('option').each(function() {
					var $option = $(this);
					
					if (templateId == $option.val()) {
						$option.prop('selected', true);
					}
				});
				
				// Display only the elements that are needed upon the pop-up opening.
				resetModalElementVisibility($editDetailModal);
				
				// Open the pop-up modal.
				$editDetailModal.dialog('open');
			});
}

/**
 * This method shows/hides all of the elements in the modal, essentially resetting its appearance to where
 * nothing appears like it is in the middle of being edited.
 * 
 * @param $modal	- the modal element
 */
function resetModalElementVisibility($modal) {
	$modal.find('#header-name-change').hide();
	$modal.find('#save-header-name').hide();
	$modal.find('#cancel-edit-header-name').hide();
	$modal.find('#header-name').show();
	$modal.find('#edit-header-name').show();

	$modal.find('#alert-name-change').hide();
	$modal.find('#save-alert-name').hide();
	$modal.find('#cancel-edit-alert-name').hide();
	$modal.find('#alert-name').show();
	$modal.find('#edit-alert-name').show();
	
	$modal.find('#template-name-change').hide();
	$modal.find('#save-template-name').hide();
	$modal.find('#cancel-edit-template-name').hide();
	$modal.find('#template-name').show();
	$modal.find('#edit-template-name').show();

	$modal.find('.error').hide();
	$('.errorMsgInput').removeClass('errorMsgInput');
}