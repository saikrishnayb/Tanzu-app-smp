selectCurrentNavigation("tab-security", "left-nav-vendors");

var $vendorTable = $('#vendor-table');
var $searchButtonsContainer = $('#search-buttons-div');

// Initializes the DataTable
var $vendorDataTable = $vendorTable.DataTable({ //All of the below are optional
	"order": [[ 2, "asc" ]], //default sort column
	"paging": true, //enable pagination
	"stateSave": true,
	"lengthChange": true, //enable change of records per page, not recommended
	"searching": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
	"ordering": true, //Allow sorting by column header
	"info": true, //Showing 1 to 10 of 11 entries
	"autoWidth": false,
	"columnDefs": [
		{"orderable": false, "targets": 0},
		{"width": "100px", "targets": 0},
		{"searchable": false, "targets": 0},
		{'className': 'actions', "targets": 0},
		{'className': 'corp-code', "targets": 1},
		{'className': 'vendor-name', "targets": 2},
		{'className': 'vendor-number', "targets": 3},
		{'className': 'mfr-code', "targets": 4},
		{'className': 'annual-agreement', "targets": 5},
		{'className': 'primary-contact', "targets": 6},
		{'className': 'contact-phone', "targets": 7},
		{'className': 'planning-analyst', "targets": 8}
	],
	"columns": [
		{ render: function(colData, type, rowData, meta) {
				var rowHtml = '<input class="update-checkbox" type="checkbox" />'
					+ '<div class="dropdown">'
					+ '<a class="bootStrapDropDown dropdown-toggle" data-toggle="dropdown">Actions<span class="caret"></span></a>'
					+ '<ul class="dropdown-menu">'
					+ '<li><a class="view-vendor">Vendor Information</a></li>'
					+ '<li><a class="edit-vendor">Modify Vendor</a></li>'
					+ '<li><a class="purchasing-details">Purchasing Details</a></li>'
					+ '</ul>'
					+ '</div>'
					+ '<input type="hidden" name="vendorId"';
				if (rowData.vendorId) {
					rowHtml += ' value= "' + rowData.vendorId + '" />';
				} else {
					rowHtml += '/>';
				}
				rowHtml += '<input type="hidden" name="notificationException"';
				if (rowData.notificationException) {
					rowHtml += ' value="'+ rowData.notificationException + '" />';
				} else {
					rowHtml += '/>';
				}
				rowHtml += '<input type="hidden" name="supplySpecialist"';
				if (rowData.supplySpecialist && rowData.supplySpecialist.userId) {
					rowHtml += ' value="' + rowData.supplySpecialist.userId + '" />';
				} else {
					rowHtml += '/>';
				}
				return rowHtml;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				return rowData.corpCode;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				var rowHtml = '<span class="vendor-address-expand-collapse">'
					+ '<img src="https://staticdev.penske.com/common/images/collapsed.png"'
					+ ' data-collapsed-src="https://staticdev.penske.com/common/images/collapsed.png"'
					+ ' data-expanded-src="https://staticdev.penske.com/common/images/expanded.png"'
					+ ' class="vendor-address-icon va-collapsed" />'
					+ rowData.vendorName
					+ '</span><br>'
					+ '<div class="vendor-address ui-helper-hidden">'
					+ '<label>Shipping Address</label><br>'
					+ rowData.shippingAddress1 + '<br>'
					+ rowData.shippingAddress2 + '<br>'
					+ rowData.shippingCity + ', ' + rowData.shippingState + ', ' + rowData.shippingZipCode
					+ '</div>';
				return rowHtml;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				return rowData.vendorNumber;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				var rowHtml = '';
				if (rowData.mfrCodes) {
					for (var i = 0; i < rowData.mfrCodes.length; i++) {
						rowHtml += rowData.mfrCodes[i];
						if (i != rowData.mfrCodes.length - 1) rowHtml += '<br>';
					}
				}
				return rowHtml;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				var rowHtml = '';
				if (rowData.annualAgreement == 'Y') rowHtml = 'Yes';
				if (rowData.annualAgreement == 'N') rowHtml = 'No';
				return rowHtml;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				var rowHtml = '';
				if (rowData.primaryContact) rowHtml = rowData.primaryContact.firstName + ' ' + rowData.primaryContact.lastName;
				return rowHtml;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				var rowHtml = '';
				if (rowData.primaryContact) rowHtml = rowData.primaryContact.phoneNumber;
				return rowHtml;
			}
		},
		{ render: function(colData, type, rowData, meta) {
				var rowHtml = '<input name="planningAnalyst" type="hidden" value="';
				if (rowData.planningAnalyst) rowHtml += rowData.planningAnalyst.userId;
				rowHtml += '" />';
				rowHtml += '<span>';
				if (rowData.planningAnalyst) rowHtml += rowData.planningAnalyst.firstName + ' ' + rowData.planningAnalyst.lastName;
				rowHtml += '</span>';
				return rowHtml;
			}
		}
	],
	"paginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
	"pageLength": 100 , //number of records per page for pagination
	"language": {"emptyTable": "No vendors were found."}, //Message displayed when no records are found
	"dom" : '<"vendor-table-top"l<"expand-collapse-div"">"f>tipr',
	"drawCallback": function() { //This will hide the pagination menu if we only have 1 page.
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
		if (rowCount > 0) {
			infoRow.css("display", "block");
		} else {
			 
			infoRow.css("display", "none");
		}
		parent.iframeResizer.resizeIframe();
	}
});

$('.expand-collapse-div').html('<a id="expand-link" class="expand">Expand All</a> / <a id="collapse-link" class="collapse-all">Collapse All</a>')

$vendorTable.on('click', '.vendor-address-expand-collapse', function() {
	toggleVendorAddress(this);
});

$('#expand-link').on('click', function() {
	$.each($vendorTable.dataTable().fnGetNodes(), function() {
		var $row = $(this);
		var $vendorLink = $row.find('.vendor-address-expand-collapse');
		var $vendorIcon = $vendorLink.find('.vendor-address-icon');
		
		//Expand all the ones that are currently collapsed
		if($vendorIcon.hasClass('va-collapsed'))
			toggleVendorAddress($vendorLink);
	})
});

$('#collapse-link').on('click', function() {
	$.each($vendorTable.dataTable().fnGetNodes(), function() {
		var $row = $(this);
		var $vendorLink = $row.find('.vendor-address-expand-collapse');
		var $vendorIcon = $vendorLink.find('.vendor-address-icon');
		
		//Collapse all the ones that aren't currently collapsed
		if(!$vendorIcon.hasClass('va-collapsed'))
			toggleVendorAddress($vendorLink);
	});
});

/* ------------ Advanced Search ------------ */
$searchButtonsContainer.on('click', '.search', function(){
	submitAdvancedSearch();
});

$searchButtonsContainer.on('click', '.reset', function() {
	var $form = $('#advanced-search-form');
	$form.find('input').val('');
	$form.find('select').find('option:first').prop('selected', true);
	
	$form.find('input, select').removeClass('errorMsgInput');
	$form.find('.error').hide();
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

$("#vendorNumber").keypress(function (e) {
     //if the letter is not digit then display error and don't type anything
     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
     return false;
    }
   });

/* ------------ Editing A Vendor ------------ */
$vendorTable.on('click', '.edit-vendor', function() {
	var $row = $(this).closest('tr');
	var vendorId = $row.find('[name="vendorId"]').val();
	
	$.post('./edit-vendor',
			{'vendorId': vendorId},
			function(data) {
				$globalModal.append(data);
				ModalUtil.openModal($globalModal);
			});
});

/* ------------ Viewing A Vendor ------------ */
$vendorTable.on('click', '.view-vendor', function() {
	var $row = $(this).closest('tr');
	var vendorId = $row.find('[name="vendorId"]').val();
	$.get('./view-vendor',
			{'vendorId': vendorId},
			function(data) {
				$globalModal.append(data);
				ModalUtil.openModal($globalModal);
			});
});

/* ------------ Purchasing Details ------------ */
$vendorTable.on('click', '.purchasing-details', function() {
	var $row = $(this).closest('tr');
	var vendorId = $row.find('[name="vendorId"]').val();
	$.get('./purchasing-details',
			{'vendorId': vendorId},
			function(data) {
				$globalModal.append(data);
				ModalUtil.openModal($globalModal);
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
		
		$.post('./get-analysts-and-specialists',
				function(data) {
					$globalModal.append(data);

					$globalModal.find('[name="notificationException"]').val(notificationException);
					$globalModal.find('[name="planningAnalyst.userId"]').val(planningAnalyst);
					$globalModal.find('[name="supplySpecialist.userId"]').val(supplySpecialist);

					ModalUtil.openModal($globalModal);
				});
	}
	else {
		$error.find('.errorMsg').text('No vendors are selected.');
		$error.show();
	}
});

$('#export-vendor-activity').on('click', function(){
	var today = new Date();
	var filename = 'Vendor_Activity_';

	var mm = today.getMonth() + 1;
	if (mm < 10) {
		mm = '0' + mm;
	}
	filename += mm;
	
	var dd = today.getDate();
	if (dd < 10) {
		dd = '0' + dd;
	}
	filename += '_' + dd;
	
	filename += '.xlsx';
	
	DownloadUtil.downloadFileAsFormPost('export-vendor-activity', filename, undefined, undefined, true);
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
		
		$form.submit();
	}
}

function toggleVendorAddress(expandCollapseLink) {
	var $icon = $(expandCollapseLink).find('.vendor-address-icon')
	var collapsed = $icon.hasClass('va-collapsed');
	var $td = $icon.closest('td');
	
	if(collapsed){
		$td.find('.vendor-address').removeClass('ui-helper-hidden');
		$icon.attr("src", $icon.data('expanded-src'));
		$icon.removeClass('va-collapsed');
	}
	else {
		$td.find('.vendor-address').addClass('ui-helper-hidden');
		$icon.attr("src", $icon.data('collapsed-src'));
		$icon.addClass('va-collapsed');
	}
}

function getVendorTableContents(url) {
	parent.showLoading();

	$getVendorTableContents = $.ajax({
		type: 'GET',
		url: url,
		data: $('#advanced-search-form').serialize(),
		global: false,
		beforeSend: function() {
			LoadingUtil.showLoadingOverlay(true);
		}
	});

	$getVendorTableContents.done(function(data) {
		$vendorDataTable.clear();
		$vendorDataTable.rows.add(data);
		$vendorDataTable.draw();
		LoadingUtil.hideLoadingOverlay();
		parent.hideLoading();
	});
}

$(function() {
	var url = $('#search-content').hasClass('displayBlock') ? 'get-vendor-table-contents-advanced-search' : 'get-vendor-table-contents';
	getVendorTableContents(url);
});

//Comment to assist Chrome debugger tools
//# sourceURL=vendors.js
