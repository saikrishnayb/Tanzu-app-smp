selectCurrentNavigation("tab-oem-build-matrix", "left-nav-build-history");

var $slotResultsTable = $('#slot-results-table');
var orderSelectionCnt = 0;
var orderSelectionList = [];
initializeDatePicker();
var $confirmAcceptSlotModal = $('#confirm-accept-slot-modal');
var $confirmReservationModal = $('#confirm-delete-reservation-modal');

ModalUtil.initializeModal($confirmAcceptSlotModal);
ModalUtil.initializeModal($confirmReservationModal);

$slotResultsDataTable = $slotResultsTable.DataTable({
	"bPaginate" : true, //enable pagination
	"bStateSave" : true, //To retrieve the data on click of back button
	"sPaginationType" : "two_button",
	"bLengthChange" : true, //enable change of records per page, not recommended
	"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
	"bAutoWidth" : false,
	"bSort" : true, //Allow sorting by column header
	"bInfo" : true, //Showing 1 to 10 of 11 entries
	"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
	"iDisplayLength" : 100, //number of records per page for pagination
	"aoColumnDefs" : [ {
		'bSortable' : false,
		'aTargets' : [ 0 ]
	} ],
	"dom" : "t",
	"fnDrawCallback" : function() { //This will hide the pagination menu if we only have 1 page.
		var paginateRow = $(this).parent().children('div.dataTables_paginate');
		var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

		if (pageCount > 1) {
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
	}
});

$('#slot-search').on('keyup', function() {
	$slotResultsDataTable.search($(this).val()).draw() ;
});

function exportSlotResults() {
	var buildId = parseInt($('#buildId').val());
	var today = new Date();
	var filename = 'Run_' + buildId + '_Results_' + today.getFullYear();

	var mm = today.getMonth() + 1;
	if (mm < 10) {
		mm = '0' + mm;
	}
	filename += mm;
	
	var dd = today.getDate();
	if (dd < 10) {
		dd = '0' + dd;
	}
	filename += dd;

	DownloadUtil.downloadFileAsFormPost(baseAppUrl + '/admin-console/oem-build-matrix/exportToExcel.htm', filename + '.xlsx', 'buildId', buildId);

}

$('.production-slot').multiselect({
	minWidth : 150,
	header : [ '' ],
	selectedList : 1,
	open : function() {
		$(".ui-multiselect-menu ").css('width', '210px');
	}
}).multiselectfilter({
	width : 130
});

/* Added this line of code to make the Multi-select box values in normal font instead of bold (Default)*/
$(".ui-multiselect-checkboxes label").removeAttr('font-weight').css("font-weight", "normal");


$(document).ready(initializeDatePicker);

function initializeDatePicker() {
	$(".production-date").datepicker({
		dateFormat : 'mm/dd/yy',
		changeMonth : true,
		changeYear : true,
		showOn : "button",
		buttonImage : "../../../images/calendar.png",
		buttonImageOnly : true,
		monthNamesShort : $.datepicker._defaults.monthNames,
		showButtonPanel : true,
		buttonText : "Open Calendar",
		altField : ".datepickerStartHidden",
		altFormat : "mm/dd/yy",
		onSelect : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
		},
		onClose : function(dateText, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar.png');
		},
		beforeShow : function(input, inst) {
			$(this).datepicker('option', 'buttonImage', '../../../images/calendar_selected.png');
		},
		//Override for beforeShowDay, since all monday only should enable
		beforeShowDay : function(date) {
			return [ date.getDay() == 1, "" ]
		}
	});
}

$(function() {
	if (localStorage && localStorage["checked"]) {
		var localStoredData = JSON.parse(localStorage["checked"]);
		var checkboxes = $("input[name='filters']");
		for (var i = 0; i < checkboxes.length; i++) {
			for (var j = 0; j < localStoredData.length; j++) {
				if (checkboxes[i].value == localStoredData[j]) {
					checkboxes[i].checked = true;
				}
			}
		}
		localStorage.removeItem('checked');
	}
});

$('.Filter-div').on("change", function() {

	var selectedFiltersList = [];
	$('#filter-checkbox input:checked').each(function() {
		selectedFiltersList.push($(this).val());
	});

	var data = $("input[name='filters']:checked").map(function() {
		return this.value;
	}).get();
	localStorage['checked'] = JSON.stringify(data);
	document.getElementById("selectedFiltersList").value = selectedFiltersList;

	var $filterSlotsForm = $('#filter-slots-form');
	$filterSlotsForm.submit();
});

$('.unit-selection').on("change", function() {
	var orderObj = {};
	var slotReservationId = $(this).attr('data-attribute-id');
	var slotReservationStatus = $(this).attr('reservation-status');
	orderObj['slotReservationId'] = slotReservationId;
	orderObj['orderId'] = $(this).attr('order-id');
	orderObj['runId'] = $('#buildId').val();
	orderObj['reservationStatus'] = slotReservationStatus;
	var approvedBuild = $('#approvedBuild').val();


	if ($(this).is(':checked')) {
		orderSelectionList.push(orderObj);
	} else {
		orderSelectionList = $.grep(orderSelectionList, function(e) {
			return !(e.slotReservationId == slotReservationId)
		});
	}
	if (orderSelectionList.length > 0 && approvedBuild != 'true') {
		if (!showUpdateButton(orderSelectionList))
			$('#update-reservation').addClass('hideOption');
		else
			$('#update-reservation').removeClass('hideOption');
		$("#actions-dpdown").removeClass("buttonDisabled");
	}
	else
		$("#actions-dpdown").addClass("buttonDisabled");
});

$("#delete-reservation").on("click", function() {
	ModalUtil.openModal($confirmReservationModal);
});

$("#cancel-confirm").on("click", function() {
	ModalUtil.closeModal($confirmReservationModal);
});

$('#confirm-btn').on("click", function() {
	$.ajax({
		type : "POST",
		url : "./delete-reservation-data.htm",
		cache : false,
		data : JSON.stringify(orderSelectionList),
		contentType : 'application/json',
		success : function(data) {
			ModalUtil.closeModal($confirmReservationModal);
			location.assign('view-slot-results-filter.htm?buildId=' + $('#buildId').val() + '&selectedFiltersList=A,E,P&checkedFilter=0');
		},
	});
});

function showUpdateButton(orderSelectionList) {
	var showUpdateAction = true;
	for (var i = 0; i < orderSelectionList.length; i++) {
		if (orderSelectionList[i].reservationStatus != 'A' && orderSelectionList[i].reservationStatus != 'E')
			showUpdateAction = false;
	}
	return showUpdateAction;
}

$('#accept-slot-results').on("click", function() {
	ModalUtil.openModal($confirmAcceptSlotModal);
});

$("#cancel-accept").on("click", function() {
	ModalUtil.closeModal($confirmAcceptSlotModal);
});

$('#confirm-accept').on("click", function() {
	var buildId = parseInt($('#buildId').val());

	$.ajax({
		type : "POST",
		url : "./show-accept-button.htm",
		cache : false,
		data : {
			buildId : buildId
		},
		success : function(showAcceptButton) {
			if (showAcceptButton) {
				$.ajax({
					url : './update-run-status.htm',
					type : "GET",
					data : {
						buildId : buildId
					},
					success : function() {
						ModalUtil.closeModal($confirmAcceptSlotModal);
						$("#actions-dpdown").addClass("buttonDisabled");
						$('#accept-slot-results').addClass("buttonDisabled");
						document.getElementById("Matched").disabled = true;
						document.getElementById("Exceptions").disabled = true;
						document.getElementById("Unmatched").disabled = true;
						document.getElementById("approvedBuild").value = true;
					},
				});

			} else {
				addErrorMessage("Slot reservation records should be in Matched status to Accept the build, check the data and try again");
			}
		},
	});

	ModalUtil.closeModal($confirmAcceptSlotModal);
});