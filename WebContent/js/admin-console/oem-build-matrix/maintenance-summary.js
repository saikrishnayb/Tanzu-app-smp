$(document).ready(function() {
	var $setOfflineDatesModal = $('#set-offline-dates-modal');
	var $addNewRow = $('#add-new-row');
	var commonStaticUrl = window.sessionStorage.getItem('commonStaticContainerUrl');
	var removeOfflineDate = [];
	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-maintenance-summary");

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

	$setOfflineDatesModal.dialog({
		autoOpen : false,
		modal : true,
		dialogClass : 'popupModal',
		width : 470,
		minHeight : 230,
		resizable : false,
		title : 'Offline Dates',
		closeOnEscape : false
	});

	$setOfflineDatesModal.on("click", '#save-offline-dates', function() {
		showLoading();
		var $form = $('#set-offline-date-form');
		var plantId = $form.find('.plantId').val();
		var rows = $form.find('#offline-dates-table .row')
		var plantOfflineDate = {};
		var plantOfflineDateList = [];
		var startDate;
		var endDate;
		$.each(rows, function(index, row) {
			var offlineDateId = $(row).attr('offlineDateId');
			startDate = $(row).find('.start-date').val();
			endDate = $(row).find('.end-date').val();
			if (startDate != '' && endDate != '') {

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
		if (plantOfflineDateList.length != 0 || removeOfflineDate.length != 0) {
			var errorMsg = '';

			// Validate the form.
			errorMsg = validateOfflineDateForm($form);

			// If no error message was returned, hide any errors and submit the form data.
			if (errorMsg.length == 0) {
				var $saveOfflineDatesPromise = $.ajax({
					type : "POST",
					url : './save-offline-dates.htm',
					global : false,
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
										stringToAppend = "No Offline Dates";
									else
										stringToAppend = startDate + ' - ' + endDate;

								} else if (plantOfflineDateList.length > 0)
									stringToAppend = "Multiple Dates";
								else
									stringToAppend = "No Offline Dates";

								var $bodyPlantRow = $(this).closest('tr');
								var nRow = $bodyPlantRow[0];
								$bodyPlantTable.dataTable().fnUpdate(stringToAppend, nRow, 5, false);

							}
						});
						closeModal($setOfflineDatesModal);
						hideLoading();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						if (XMLHttpRequest.responseText.indexOf('Error Processing the Setting Offline dates') > 0) {
							$('.errorMsg').text("Error Processing the Setting Offline dates.");
							$('.error').show();

						}
						hideLoading();

					}
				});

			}
			// If an error was found, display it to the user and do not submit the form data.
			else {

				$('.errorMsg').text(errorMsg);
				$('.error').show();
				hideLoading();
			}

		}

	});

	$setOfflineDatesModal.on("click", '#add-new-row', function() {
		debugger;
		var newRow = "<tr class='row'><td class='col-xs-3'><span class='dateLbl'>Date</span></td>" +
			"<td class='col-xs-7'>" +
			"<input name='startDate' class='start-date' class='common-form-control date-picker numeric numeric-jquery-date advanced-date' type='text' />" +
			"<input  name='offlineStartDate' type='hidden' class='datepickerStartHidden'/> - " +
			"<input name='endDate' class='end-date' class='common-form-control date-picker numeric numeric-jquery-date advanced-date' type='text' />" +
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
		if (offlineDateId != '' && offlineDateId != 0)
			removeOfflineDate.push(offlineDateId);
		$(this).parents("tr").remove();
	});

});

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
			$('#set-offline-dates-modal').html(data);

			$('#set-offline-dates-modal').find('.error').hide();
			$('.errorMsgInput').removeClass('errorMsgInput');

			$('#set-offline-dates-modal').dialog('open');
		});
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