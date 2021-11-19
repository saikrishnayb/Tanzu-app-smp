var $vendorTable = $('#vendor-table');
var $editVendorModal = $('#edit-vendor-modal');
var $viewVendorModal = $('#view-vendor-modal');
var $massUpdateModal = $('#mass-update-modal');
var $searchButtonsContainer = $('#search-buttons-div');

$(document).ready(function() {
	selectCurrentNavigation("tab-security", "left-nav-vendors");
	
	/* ----------- Datatable Declaration ----------- */
	var iDisplayLength = 100;//tableRowLengthCalc();
	
	var $vendorDataTable = $vendorTable.dataTable({ 					//All of the below are optional
				"aaSorting": [[ 2, "asc" ]], 	//default sort column
				"bPaginate": true, 				//enable pagination
				"bLengthChange": true, 		//enable change of records per page, not recommended
				"bStateSave": true,
				"bFilter": true, 				//Allows dynamic filtering of results, do not enable if using ajax for pagination
				"bSort": true, 				//Allow sorting by column header
				"bInfo": true, 					//Showing 1 to 10 of 11 entries
				"bAutoWidth": false,
				"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0 ]},
				                 {"sWidth": "100px", "aTargets": [ 0 ]},
				                 {"bSearchable": false, "aTargets": [0]}],
				"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
				"iDisplayLength": 100 , 			//number of records per page for pagination
				"oLanguage": {"sEmptyTable": "No vendors were found."}, //Message displayed when no records are found
				"sDom" : '<"vendor-table-top"l<"expand-collapse-div"">"f>tipr',
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
											var rowCount = this.fnSettings().fnRecordsDisplay();
											if (rowCount > 0){
												infoRow.css("display", "block");
											} 
											else{
												infoRow.css("display", "none");
											}
											//infoRow.css("display", "none");
											
										}
	});
	
	$('.expand-collapse-div').html('<a id="expand-collapse-link" class="expand">Expand All</a>')
	
	/* ----------- Modal Declarations ----------- */
	$editVendorModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 550,
		minHeight: 500,
		resizable: false,
		title: 'Modify Vendor',
		closeOnEscape: false
	});
	
	$viewVendorModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 550,
		minHeight: 500,
		resizable: false,
		title: 'Vendor Information',
		closeOnEscape: false
	});
	
	$massUpdateModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 486,
		minHeight: 200,
		resizable: false,
		title: 'Modify Vendor',
		closeOnEscape: false
	});
	
	$vendorTable.on('click', '.vendor-address-icon', function() {
		var $this = $(this);
		var collapsed = $this.hasClass('va-collapsed');
		var $td = $this.closest('td');
		
		if(collapsed){
			$td.find('.vendor-address').show();
			$this.attr("src", "https://staticdev.penske.com/common/images/expanded.png");
			$this.removeClass('va-collapsed');
		}
		else {
			$td.find('.vendor-address').hide();
			$this.attr("src", "https://staticdev.penske.com/common/images/collapsed.png");
			$this.addClass('va-collapsed');
		}
		
	});
	
	$('#expand-collapse-link').on('click',function(){
		var $this = $(this);
		var expand = $this.hasClass('expand');
		
		if(expand){
			$vendorTable.find('tbody tr').each(function(){
				var $row = $(this);
				var $td = $row.find('.vendor-name');
				var $vendorIcon = $td.find('.vendor-address-icon')
				
				$td.find('.vendor-address').show();
				$vendorIcon.attr("src", "https://staticdev.penske.com/common/images/expanded.png");
				$vendorIcon.removeClass('va-collapsed');
			});
			
			$this.text('Collapse All')
			$this.removeClass('expand');
		}
		else {
			$vendorTable.find('tbody tr').each(function(){
				var $row = $(this);
				var $td = $row.find('.vendor-name');
				var $vendorIcon = $td.find('.vendor-address-icon')
				
				$td.find('.vendor-address').hide();
				$vendorIcon.attr("src", "https://staticdev.penske.com/common/images/collapsed.png");
				$vendorIcon.addClass('va-collapsed');
			});
			
			$this.text('Expand All')
			$this.addClass('expand');
		}
	});
	
	/* ------------ Advanced Search ------------ */
	var $advancedForm = $('#advanced-search-form');
	$searchButtonsContainer.on('click', '.search', function(){
	
	/*$('#search-content .search').on('click', function() {*/
		submitAdvancedSearch();
	});
	
	// Allow user to submit form by using the enter key.
	$('#advanced-search-form input, #advanced-search-form select').keypress(function(key) {
		if (key.which == 13) {
			/*submitAdvancedSearch();*/
			$searchButtonsContainer.find('.search').trigger('click');
			event.preventDefault();
		}
	});
	
	if($("#search-content").is(":visible")){
		if($("#advanced-search").is(":visible")){
			//Currently Expanded
			$("#advanced-search").text('Hide Search Criteria');
		}
	}
	else{
		if($("#advanced-search").is(":hidden")){
			//Currently Collapsed
			$("#advanced-search").text('Show Search Criteria');
		}
	}
	
	$advancedForm.find('.reset').on('click', function() {
		$advancedForm.find('input').val('');
		$advancedForm.find('select').find('option:first').attr('selected', 'selected');
		
		$advancedForm.find('input, select').removeClass('errorMsgInput');
		$advancedForm.find('.error').hide();
	});
	
	$("#vendorNumber").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	     return false;
	    }
	   });

	// Allow the user to see their advanced search terms if they just performed a search.
/*	if ($advancedForm.find('input:text[value!=""]').length > 0) {
	//	$('#advanced-search').trigger('click');
	}
	else {
		// The dropdowns have different default values (because for some, 0 = "No"), so they need to be checked individually.
		var $notificationException = $advancedForm.find('[name="notificationException"]');
		var $annualAgreement = $advancedForm.find('[name="annualAgreement"]');
		var $planningAnalyst = $advancedForm.find('[name="planningAnalyst.userId"]');
		var $supplySpecialist = $advancedForm.find('[name="supplySpecialist.userId"]');

		if ($notificationException.val() != -1 || $annualAgreement.val() != -1 || $planningAnalyst.val() > 0 || $supplySpecialist.val() > 0) {
		//	$('#advanced-search').trigger('click');
		}
	}
*/	
	/* ------------ Editing A Vendor ------------ */
	$vendorTable.on('click', '.edit-vendor', function() {
		var $row = $(this).closest('tr');

		var vendorId = $row.find('[name="vendorId"]').val();
		var analystId = $row.find('[name="planningAnalyst"]').val();
		var specialistId = $row.find('[name="supplySpecialist"]').val();
		//for pop up positioning 
		var x = ($(window).width() - 415) / 2;
		var y = $(this).offset().top + $(this).height();
		
		$.post('./edit-vendor.htm',
				{'vendorId': vendorId},
				function(data) {
					var analystFound = false;
					var specialistFound = false;
					
					$editVendorModal.html(data);
					//$editVendorModal.dialog('open');
					$editVendorModal.dialog("option", "position", [x,y]).dialog("open");
					
					/*$editVendorModal.find('[name="planningAnalyst.userId"] option').each(function() {
						if (!analystFound && $(this).val() == analystId) {
							$(this).attr('selected', 'selected');
							analystFound = true;
						}
					});
					
					$editVendorModal.find('[name="supplySpecialist.userId"] option').each(function() {
						if (!specialistFound && $(this).val() == specialistId) {
							$(this).attr('selected', 'selected');
							specialistFound = true;
						}
					});*/
				});
	});
	
	/* ------------ Viewing A Vendor ------------ */
	$vendorTable.on('click', '.view-vendor', function() {
		var $row = $(this).closest('tr');
		var vendorId = $row.find('[name="vendorId"]').val();
		var x = ($(window).width() - 415) / 2;
		var y = $(this).offset().top + $(this).height();
		$.get('./view-vendor.htm',
				{'vendorId': vendorId},
				function(data) {
					$viewVendorModal.html(data);
					//$viewVendorModal.dialog('open');
					$viewVendorModal.dialog("option", "position", [x,y]).dialog("open");
				});
	});
	
	/* --------- Mass Updating Vendors --------- */
	$('#mass-update').on('click', function() {
		var $error = $(this).closest('.button-div').find('.error');

		var vendorChecked = false;
		// The following variables are for filling the information in the modal, but only if the information
		// is the same across all checked vendors, otherwise the modal will use the default setting.
		var notificationException = '';
		var planningAnalyst = '';
		var supplySpecialist = '';
		
		// Get the vendor IDs for the checked vendors.
		$vendorTable.find('tr').each(function() {
			var curNotificationException = $.trim($(this).find('[name="notificationException"]').val());
			var curPlanningAnalyst = $(this).find('[name="planningAnalyst"]').val();
			var curSupplySpecialist = $(this).find('[name="supplySpecialist"]').val();
			
			var $checkbox = $(this).find('.update-checkbox');
			
			// The initial checked vendor.
			if ($checkbox.is(':checked')) {
				if (vendorChecked) {
					if (curNotificationException != notificationException) {
						notificationException = '';
					}
					
					if (curPlanningAnalyst != planningAnalyst) {
						planningAnalyst = '';
					}
					
					if (curSupplySpecialist != supplySpecialist) {
						supplySpecialist = '';
					}
					
					return;
				}
				
				vendorChecked = true;
				
				// Set all of the data to the initial checked vendor's data.
				notificationException = curNotificationException;
				planningAnalyst = curPlanningAnalyst;
				supplySpecialist = curSupplySpecialist;
			}
		});
		
		if (vendorChecked) {
			$error.hide();
			
			$.post('./get-analysts-and-specialists.htm',
					function(data) {
						$massUpdateModal.html(data);

						$massUpdateModal.find('[name="notificationException"]').val(notificationException);
						$massUpdateModal.find('[name="planningAnalyst.userId"]').val(planningAnalyst);
						$massUpdateModal.find('[name="supplySpecialist.userId"]').val(supplySpecialist);
	
						$massUpdateModal.dialog('open');
					});
		}
		else {
			$error.find('.errorMsg').text('No vendors are selected.');
			$error.show();
		}
	});
});

function toggleContent(contentId,spanId){
	if($("#" + contentId).is(":visible")){
		//Currently Expanded
		$("#" + spanId).removeClass('expandedImage').addClass('collapsedImage');
		$("#" + contentId).removeClass("displayBlock").addClass("displayNone");
		$("#" + spanId).text('Show Search Criteria');
	}
	else{
		//Currently Collapsed
	   $("#" + spanId).removeClass('collapsedImage').addClass('expandedImage');
	   $("#" + contentId).removeClass("displayNone").addClass("displayBlock");
	   $('#advanced-search-form').find('[name="vendorName"]').focus();
	   $("#" + spanId).text('Hide Search Criteria');
	}
	
}

function submitAdvancedSearch() {
	var $form = $('#advanced-search-form');
	var $error = $form.find('.error');
	var $vendorNumber = $form.find('[name="vendorNumber"]');
	
	// Validate form.
	if ($vendorNumber.val().length > 0 && !$.isNumeric($vendorNumber.val())) {
		$vendorNumber.addClass('errorMsgInput');
		$error.find('.errorMsg').text('Vendor Number must be a number.');
		$error.show();
	}
	else {
		$vendorNumber.removeClass('errorMsgInput');
		$error.hide();
		
		// Remove unused fields from submitting.
		$form.find('input:text[value=""], select[value=""]').prop('disabled', true);
		
		$form.submit();
	}
}