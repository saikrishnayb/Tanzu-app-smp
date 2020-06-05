var $setOfflineDatesModal = $('#set-offline-dates-modal');
var $regionAssociationModal =$('#region-association-modal');
var $addNewRow = $('#add-new-row');
var commonStaticUrl = window.sessionStorage.getItem('commonStaticContainerUrl');
var removeOfflineDate = [];
var $saveRegionAssociation=$("#save-region-association");
var regionChangeCnt = 0;
var regionAssociationUpdateList = [];

selectCurrentNavigation("tab-oem-build-matrix", "left-nav-maintenance-summary");

ModalUtil.initializeModal($setOfflineDatesModal);
ModalUtil.initializeModal($regionAssociationModal);
$bodyPlantTable = $('#body-plant-maint-table').dataTable({ //All of the below are optional
	"bPaginate" : true, //enable pagination
	"bStateSave" : true, //To retrieve the data on click of back button
	"sPaginationType" : "two_button",
	"aaSorting" : [], //default sort column
	"aoColumnDefs" : [ {
		'bSortable' : false,
		'aTargets' : [ 0 ]
	} ],
	"bLengthChange" : true, //enable change of records per page, not recommended
	"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
	"bAutoWidth" : false,
	"bSort" : true, //Allow sorting by column header
	"bInfo" : true, //Showing 1 to 10 of 11 entries
	"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
	"iDisplayLength" : 100, //number of records per page for pagination
	"oLanguage" : {
		"sEmptyTable" : "No Results Found"
	},
	//"sScrollY": 246, //Adds a vertical scroll bar if the content exceeds this amount
	//"sScrollXInner": "100%" 
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

$setOfflineDatesModal.on("click", '#save-offline-dates', function() {
	var $form = $('#set-offline-date-form');
	var plantId = $form.find('.plantId').val();
	var rows = $form.find('#offline-dates-table .row')
	var plantOfflineDate = {};
	var plantOfflineDateList = [];
	var startDate;
	var endDate;

	$.each(rows, function(index, row) {
		var offlineDateId = $(row).attr('offlineDateId');
		if ( $(row).find('.start-date').val() != '' &&  $(row).find('.end-date').val() != '') {
			startDate = $(row).find('.start-date').val();
			endDate = $(row).find('.end-date').val();
			plantOfflineDate = {};
			plantOfflineDate['offlineDateId'] = offlineDateId;
			plantOfflineDate['plantId'] = plantId;
			plantOfflineDate['offlineStartDate'] = new Date(startDate);
			plantOfflineDate['offlineEndDate'] = new Date(endDate);
			plantOfflineDateList.push(plantOfflineDate);
		} else if (offlineDateId != undefined && offlineDateId != "") {
			removeOfflineDate.push(offlineDateId);
		}

	});

	var input = {};
	input['plantId'] = $form.find('.plantId').val();
	input['offlineDates'] = plantOfflineDateList;
	input['offlineDateToRemove'] = removeOfflineDate;
	var errorMsg = '';
	// Validate the form.
	errorMsg = validateOfflineDateForm($form);
	// If no error message was returned, hide any errors and submit the form data.
	if (errorMsg.length == 0) {
		if (plantOfflineDateList.length != 0 || removeOfflineDate.length != 0) {
			var $saveOfflineDatesPromise = $.ajax({
				type : "POST",
				url : './save-offline-dates.htm',
				data : JSON.stringify(input),
				contentType : 'application/json',
				success : function(data) {
					$('#body-plant-maint-table').find('.plant-id').each(function() {
						var plantIdCheck = $(this).val();
						var plantIdMatch = (plantIdCheck == plantId);

						if (plantIdMatch) {
							var stringToAppend = "";
							if (plantOfflineDateList.length == 1) {
								if (plantOfflineDate['offlineStartDate'] == null || plantOfflineDate['offlineStartDate'] == "")
									stringToAppend = "";
								else
									stringToAppend = startDate + ' - ' + endDate;

							} else if (plantOfflineDateList.length > 0)
								stringToAppend = "Multiple Dates";
							else
								stringToAppend = "";

							var $bodyPlantRow = $(this).closest('tr');
							var nRow = $bodyPlantRow[0];
							$bodyPlantTable.dataTable().fnUpdate(stringToAppend, nRow, 5, false);

						}
					});
					ModalUtil.closeModal($setOfflineDatesModal);
				}
			});
		}
	} else {
		// If an error was found, display it to the user and do not submit the form data.
		clearErrorAndWarningLabels();
		addErrorMessage(errorMsg);
	}
});

$setOfflineDatesModal.on("click", '#add-new-row', function() {
	var newRow = "<tr class='row'><td class='col-xs-3'><span class='dateLbl'>Date</span></td>" +
	"<td class='col-xs-7'>" +
	"<input name='startDate' class='start-date offline-date common-form-control date-picker numeric numeric-jquery-date advanced-date' required type='text'/>" +
	"<input  name='offlineStartDate' type='hidden' class='datepickerStartHidden'/> <span class='dateLbl'> - </span> " +
	"<input name='endDate' class='end-date offline-date common-form-control date-picker numeric numeric-jquery-date advanced-date' required type='text'/>" +
	"<input  name='offlineEndDate' type='hidden' class='datepickerEndHidden' />" +
	"</td>" +
	"<td class='col-xs-2'>" +
	"<a class='deleteRow' ><img src='" + commonStaticUrl + "/images/delete.png'" +
	" class='centerImage rightMargin delete-button'/></a>" +
	"</td>" +
	"</tr>";
	$("#offline-dates-table tbody").append(newRow);
	initializeDatePicker();
});

$setOfflineDatesModal.on("click", '.deleteRow', function() {
	var $offlineDaterRow = $(this).parents("tr");
	var offlineDateId = $offlineDaterRow.attr('offlineDateId');

	if (offlineDateId != '' && offlineDateId != undefined) {
		removeOfflineDate.push(offlineDateId);
		$('#save-offline-dates').removeClass("buttonDisabled");
	}

	$(this).parents("tr").remove();
});

$setOfflineDatesModal.on("click", '#clear-row', function() {
	var $offlineDaterRow = $(this).parents("tr");
	if( $offlineDaterRow.find('.start-date').val() != '' || $offlineDaterRow.find('.end-date').val() != '')
	{
		$('#save-offline-dates').removeClass("buttonDisabled");
	}
	$offlineDaterRow.find('.start-date').val("");
	$offlineDaterRow.find('.end-date').val("");
	
});

$('#set-offline-dates-modal').on('input', '.offline-date', function(){
	$('#save-offline-dates').removeClass("buttonDisabled");
})

$('#set-offline-dates-modal').on('change', '.offline-date', function(){
	$('#save-offline-dates').removeClass("buttonDisabled");
})

function validateOfflineDateForm($form) {
	var errorMsg = '';
	var rows = $form.find('#offline-dates-table .row');
	$.each(rows, function(index, row) {
		if (errorMsg != '')
			return errorMsg;

		var offlineStartDate = $(row).find('.start-date').val();
		var offlineEndDate = $(row).find('.end-date').val();

		if (offlineStartDate.length != 0 && offlineEndDate.length == 0) {
			errorMsg = "Row -" + (parseInt(index) + 1) + ": Offline End date is required.";
		} else if (offlineStartDate.length == 0 && offlineEndDate.length != 0) {
			errorMsg = "Row -" + (parseInt(index) + 1) + ": Offline Start date is required.";
		} else if (offlineStartDate.length != 0 && offlineEndDate.length != 0) {
			if (!validateDate(offlineStartDate))
				errorMsg = "Row -" + (parseInt(index) + 1) + ": Invalid Start date.";
			else if (!validateDate(offlineEndDate))
				errorMsg = "Row -" + (parseInt(index) + 1) + ": Invalid End date.";else {
					if (new Date(offlineStartDate) > new Date(offlineEndDate))
						errorMsg = "Row -" + (parseInt(index) + 1) + ": Start date is greater than End date. ";
				}
		}
	});

	return errorMsg;
}

function setOfflineDates(plantId) {
	$.post('./get-offline-date-setup-modal.htm',
			{
		'plantId' : plantId
			},
			function(data) {
				$setOfflineDatesModal.html(data);
				$setOfflineDatesModal.find('.error').hide();
				$('.errorMsgInput').removeClass('errorMsgInput');
				ModalUtil.openModal($setOfflineDatesModal);
			}
	);
}

function validateDate(input) {
	if (input == "") {
		//if date is empty
		return true;
	} else {
		//Detailed check for valid date ranges
		var monthfield = input.split("/")[0];
		var dayfield = input.split("/")[1];
		var yearfield = input.split("/")[2];
		var dayobj = new Date(yearfield, monthfield - 1, dayfield);

		if ((dayobj.getMonth() + 1 != monthfield) || (dayobj.getDate() != dayfield) || (dayobj.getFullYear() != yearfield)) {
			returnVal = false;
		} else {
			returnVal = true;
			return returnVal;
		}
	}
}

$('.region-association').on("click", function() {
	var plantId = $(this).attr('plantId');
	$.ajax({
		type : "POST",
		url : "./get-region-association-modal.htm",
		cache : false,
		data : {
			plantId : plantId
		},
		success : function(data) {
			$regionAssociationModal.html(data);
			var regionData = $('.regionData').val();
			if (regionData.length > 2) {
				$regionAssociationModal.find('.error').hide();
				$('.errorMsgInput').removeClass('errorMsgInput');
				ModalUtil.openModal($regionAssociationModal);
				regionAssociationUpdateList = [];
			} else {
				addErrorMessage("No region available for the plant, check the data and try again");
			}
		},
	});
});

$regionAssociationModal.on("change", '.region-value-input', function() {
	var plantId = parseInt($('.plantId').val());
	var region = $(this).val();
	var regionAssociationId = ($(this).attr('region-association-id') != "" && $(this).attr('region-association-id') != undefined) ? parseInt($(this).attr('region-association-id')) : "";
	var regionPlantAssociation = {};
	regionPlantAssociation['plantId'] = plantId;
	regionPlantAssociation['region'] = region;
	regionPlantAssociation['regionDesc'] = $(this).attr('region-desc');

	if ($(this).is(':checked')) //user selects a region
	{
		if (regionAssociationId == 0 || regionAssociationId == "") {
			//selected region needs to be inserted
			regionPlantAssociation['isAssociated'] = 'Y';
			regionAssociationUpdateList.push(regionPlantAssociation);
			regionChangeCnt++;
		} else { //in this case,user reverts selection
			regionAssociationUpdateList = $.grep(regionAssociationUpdateList, function(e) {
				return e.region != region;
			});
			regionChangeCnt--;
		}
	} else { //user de-selects a  region
		if (regionAssociationId != 0 || regionAssociationId != "") {
			//selected region needs to be deleted
			regionPlantAssociation['isAssociated'] = 'N';
			regionAssociationUpdateList.push(regionPlantAssociation);
			regionChangeCnt++;
		} else { //in this case,user reverts selection
			regionAssociationUpdateList = $.grep(regionAssociationUpdateList, function(e) {
				return e.region != region;
			});
			regionChangeCnt--;
		}
	}
	//enable/disable save button
	if (regionAssociationUpdateList.length != 0) {
		$("#save-region-association").removeClass("buttonDisabled");
	} else {
		$("#save-region-association").addClass("buttonDisabled");
	}

});

$regionAssociationModal.on("click", "#save-region-association", function() {
	if (regionAssociationUpdateList && regionAssociationUpdateList.length != 0) {
		var $saveRegionPromise = $.ajax({
			type : "POST",
			url : './save-region-association.htm',
			data : JSON.stringify(regionAssociationUpdateList),
			contentType : 'application/json'
		});
		$saveRegionPromise.done(function(data) {
			ModalUtil.closeModal($regionAssociationModal);
		});
	}

});

$regionAssociationModal.on("click", "#cancel-btn", function() {
	ModalUtil.closeModal($regionAssociationModal);
});

//# sourceURL=maintenance-summary.js