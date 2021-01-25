$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-cost-sheet-adjustment-options");
	
	var $optionTable = $('#optionTable');
	var $optionModal = $('#optionModal');
	
	// Initializes data table
	$optionTable.dataTable({
		"aoColumnDefs": [
			{ 'sWidth':"100px", "aTargets":[0]},
			{ 'bSortable': false, 'aTargets':[0]},
			{ "bSearchable": false, "aTargets": [0]}
		],
		"bAutoWidth": false,
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": false,
		"bStateSave": true,
		"bFilter": true, // Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, // Allow sorting by column header
		"bInfo": true, // Showing 1 to 10 of 11 entries
		"oLanguage": {"sEmptyTable": "No records are found"} // Message displayed when no records are found
	});
	
	// Initializes the option modal
	$optionModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 200,
		resizable: false,
		title: 'Cost Sheet Adjustment Option',
		closeOnEscape: false,
		position: { my: "center", at: "center"}
	});
	
	// Opens the option modal when a user clicks the Edit link
	$optionTable.on('click', '.edit-option', function() {
		var caOptionId = $(this).closest('.option-row').find('.option-id').val();
		var $getOptionPromise = $.get("get-cost-sheet-adjustment-option-modal.htm", {caOptionId: caOptionId});
		$getOptionPromise.done(function(data) {
			$optionModal.html(data);
			$optionModal.dialog("option", "title", 'Edit Cost Sheet Adjustment Option');
			openModal($optionModal);
		});
	});
	
	// Deletes the option when a user clicks the delete button
	$optionTable.on('click', '.delete-button', function() {
		var caOptionId = $(this).closest('.option-row').find('.option-id').val();
		var $deleteOptionPromise = $.post("delete-cost-sheet-adjustment-option.htm", {caOptionId: caOptionId});
		$deleteOptionPromise.done(function(data) {
			location.reload();
		});
	});
	
	// Add Adjustment Option link
	var strHTML='<span style="margin-right: 10px;" class="floatLeft addRow">'+
		'<a href="#" onclick="addOption(); return false;">Add Adjustment Option</a>'+
		'</span>';
	$("#optionTable_wrapper").prepend(strHTML);

	// Checks Order Code not empty
	$optionModal.on('input', '#orderCode', function() {
		var $err = $optionModal.find(".error");
		var value = $(this).val().trim();
		if (value.length > 0) {
			$err.addClass('hidden');
			$("#actionButton").removeClass('buttonDisabled');
		} else {
			$err.removeClass('hidden');
			$("#actionButton").addClass('buttonDisabled');
		}
	});

	// Action on Enter key
	$optionModal.on('keypress', '#orderCode', function(e) {
		if (e.which == 13) {
			$("#actionButton").click();
			return false;
		}
	});
});

// Opens Add option modal
function addOption() {
	var caOptionId = 0;
	var $getOptionPromise = $.get("get-cost-sheet-adjustment-option-modal.htm", {caOptionId: caOptionId});
	$getOptionPromise.done(function(data) {
		var $optionModal = $('#optionModal');
		$optionModal.html(data);
		$optionModal.dialog("option", "title", 'Add Cost Sheet Adjustment Option');
		openModal($optionModal);
	});
}

// Saves the option
function doSave() {
	if ($("#actionButton").hasClass('buttonDisabled')) {
		return false;
	}

	var $optionModal = $('#optionModal');
	var $form = $optionModal.find('#option-form');
	var $updateOptionPromise = $.post("update-cost-sheet-adjustment-option.htm", $form.serialize());
	$updateOptionPromise.done(function(data) {
		location.reload();
	});
}

// Adds the option
function doAdd() {
	if ($("#actionButton").hasClass('buttonDisabled')) {
		return false;
	}

	var $optionModal = $('#optionModal');
	var $form = $optionModal.find('#option-form');
	var $addOptionPromise = $.post("add-cost-sheet-adjustment-option.htm", $form.serialize());
	$addOptionPromise.done(function(data) {
		location.reload();
	});
}
