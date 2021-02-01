$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-cost-sheet-tolerances");
	
	var $toleranceTable = $('#toleranceTable');
	var $toleranceModal = $('#toleranceModal');
	var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
	
	// Initializes data table
	$toleranceTable.dataTable({
		"aoColumnDefs": [
			{ 'sWidth':"100px", "aTargets":[0]},
			{ 'bSortable': false, 'aTargets':[0, 3]},
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
	
	// Initializes the tolerance modal
	$toleranceModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 400,
		minHeight: 250,
		resizable: false,
		title: 'Cost Sheet Tolerance',
		closeOnEscape: false,
		position: { my: "center", at: "center"}
	});
	
	// Opens the tolerance modal when a user clicks the Edit link
	$toleranceTable.on('click', '.edit-tolerance', function() {
		var costToleranceId = $(this).closest('.tolerance-row').find('.tolerance-id').val();
		var $getTolerancePromise = $.get("get-cost-sheet-tolerance-modal.htm", {costToleranceId: costToleranceId});
		$getTolerancePromise.done(function(data) {
			$toleranceModal.html(data);
			$toleranceModal.dialog("option", "title", 'Edit Cost Sheet Tolerance');
			openModal($toleranceModal);
		});
	});
	
	// Deletes the tolerance when a user clicks the delete button
	$toleranceTable.on('click', '.delete-button', function() {
		var costToleranceId = $(this).closest('.tolerance-row').find('.tolerance-id').val();
		var $deleteTolerancePromise = $.post("delete-cost-sheet-tolerance.htm", {costToleranceId: costToleranceId});
		$deleteTolerancePromise.done(function(data) {
			location.reload();
		});
	});
	
	// Add Tolerance link
	var strHTML='<span style="margin-right: 10px;" class="floatLeft addRow">'+
		'<a href="#" onclick="addTolerance(); return false;">Add Tolerance</a>'+
		'<img src='+commonStaticUrl+'/images/add.png class="centerImage handCursor" alt="Add Load sheet Sequence"/>'+
		'</span>';
	$("#toleranceTable_filter").prepend(strHTML);

	// OnChange of PO Category
	$toleranceModal.on('change', '#poCategory', function() {
		rePopulateMakes();
		validate();
	});

	// OnChange of Make
	$toleranceModal.on('change', '#mfrCode', function() {
		validate();
	});

	// Checks Tolerance not empty
	$toleranceModal.on('input', '#tolerance', function() {
		validate();
	});

	// Action on Enter key
	$toleranceModal.on('keypress', '#tolerance', function(e) {
		if (e.which == 13) {
			$("#actionButton").click();
			return false;
		}
	});

});

// Opens Add tolerance modal
function addTolerance() {
	var costToleranceId = 0;
	var $getTolerancePromise = $.get("get-cost-sheet-tolerance-modal.htm", {costToleranceId: costToleranceId});
	$getTolerancePromise.done(function(data) {
		var $toleranceModal = $('#toleranceModal');
		$toleranceModal.html(data);
		$toleranceModal.dialog("option", "title", 'Add Cost Sheet Tolerance');
		openModal($toleranceModal);
		rePopulateMakes();
	});
}

// Saves the tolerance
function doSave() {
	if (!validate()) {
		return false;
	}

	var $toleranceModal = $('#toleranceModal');
	var $form = $toleranceModal.find('#tolerance-form');
	var $updateTolerancePromise = $.post("update-cost-sheet-tolerance.htm", $form.serialize());
	$updateTolerancePromise.done(function(data) {
		location.reload();
	});
}

// Adds the tolerance
function doAdd() {
	if (!validate()) {
		return false;
	}

	var $toleranceModal = $('#toleranceModal');
	var $form = $toleranceModal.find('#tolerance-form');
	var $addTolerancePromise = $.post("add-cost-sheet-tolerance.htm", $form.serialize());
	$addTolerancePromise.done(function(data) {
		location.reload();
	});
}

// Re-populates the makes select
function rePopulateMakes() {
	// Get the selected options
	var selectedCategory = $("#poCategory").val();
	var $make = $("#mfrCode");
	var selectedMake = $make.val();

	// Get available models
	var availableMakes = getAvailableMakes(selectedCategory); 

	// Re-populate the select
	$make.empty();
	$make.append($("<option>").val('').text('Select...'));
	availableMakes.forEach(function(make) {
		var opt = $("<option>").val(make.mfrCode).text(make.mfrCode);
		if (make.mfrCode == selectedMake) {
			opt.attr("selected", "selected");
		}
		$make.append(opt);
	});
}

// Finds makes that are match to selected category
function getAvailableMakes(selectedCategory) {
	var availableMakes = [];

	if (selectedCategory == '') {
		return availableMakes;
	}

	// Iterate over makesObjectArray
	makesObjectArray.forEach(function(make) {
		if (make.poCategoryTypes.some(function(category) {return category == selectedCategory;})) {
			availableMakes.push(make);
		}
	});

	return availableMakes;
}

function validate() {
	var ok = true;

	var $errCategory = $("#errCategory");
	var selectedCategory = $('#poCategory').val();
	if (selectedCategory != '') {
		$errCategory.addClass('hidden');
	} else {
		ok = false;
		$errCategory.removeClass('hidden');
	}

	var $errMake = $("#errMake");
	var selectedMake = $('#mfrCode').val();
	if (selectedMake != '') {
		$errMake.addClass('hidden');
	} else {
		ok = false;
		$errMake.removeClass('hidden');
	}

	var $errTolerance = $("#errTolerance");
	var tolerance = $('#tolerance').val().trim();
	if (tolerance.length > 0 && $.isNumeric(tolerance) && parseFloat(tolerance) >= 0) {
		$errTolerance.addClass('hidden');
	} else {
		ok = false;
		$errTolerance.removeClass('hidden');
	}

	if (ok) {
		$("#actionButton").removeClass('buttonDisabled');
	} else {
		$("#actionButton").addClass('buttonDisabled');
	}

	return ok;
}

