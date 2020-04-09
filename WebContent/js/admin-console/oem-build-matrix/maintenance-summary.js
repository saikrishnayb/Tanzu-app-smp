$(document).ready(function() {
	var $setOfflineDatesModal= $('#set-offline-dates-modal');
	
	selectCurrentNavigation("tab-app-config", "left-nav-maintenance-summary");
	
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
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 312,
		minHeight: 230,
		resizable: false,
		title: 'Set Plant Offline Date',
		closeOnEscape: false
	});

	$setOfflineDatesModal.on("click",'#save-offline-dates',function(){
		var $form = $('#set-offline-date-form');
		var plantId = $form.find('.plantId').val();
		var offlineStartDate= $form.find('#start-date').val();
		var offlineEndDate= $form.find('#end-date').val();
		showLoading();
		var input = {};
		input['plantId'] = $form.find('.plantId').val();
		input['offlineStartDate'] =($form.find('#start-date').val()!='')? (new Date($form.find('#start-date').val())) : null;
		input['offlineEndDate'] = ($form.find('#end-date').val()!='')? (new Date($form.find('#end-date').val())) : null;
		
		var errorMsg = '';
		
		// Validate the form.
		errorMsg = validateOfflineDateForm($form);
		
		// If no error message was returned, hide any errors and submit the form data.
		if (errorMsg.length == 0) {
		var $saveOfflineDatesPromise = $.ajax({
			type: "POST",
			url:'./save-offline-dates.htm',
			global: false,
			data: JSON.stringify(input),
			contentType: 'application/json',
			success: function(data){
				$('#body-plant-maint-table').find('.plant-id').each(function(){
					var plantIdCheck = $(this).val();
					var plantIdMatch = (plantIdCheck ==plantId) ;
				
					if(plantIdMatch){
						var stringToAppend = (offlineStartDate== '')?"No Offline Dates" :( offlineStartDate+' - '+offlineEndDate);
						var $bodyPlantRow = $(this).closest('tr');
						var nRow = $bodyPlantRow[0];
						$bodyPlantTable.dataTable().fnUpdate(stringToAppend , nRow, 5, false);
						
					}
				});
				closeModal($setOfflineDatesModal);
				hideLoading();
			  },
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				  if(XMLHttpRequest.responseText.indexOf('Error Processing the Setting Offline dates')>0){
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
		
	});
});
 
function validateOfflineDateForm($form) {
	var errorMsg = '';
	var offlineStartDate= $form.find('#start-date').val();
	var offlineEndDate= $form.find('#end-date').val();
	if(offlineStartDate.length!=0 && offlineEndDate.length ==0)
		{
			errorMsg = "Offline End date is required.";
		}
	else if(offlineStartDate.length ==0 && offlineEndDate.length !=0)
		{
			errorMsg = "Offline Start date is required.";
		}
	else if(offlineStartDate.length !=0 && offlineEndDate.length !=0)
		{
			if(!validateDate(offlineStartDate))
				errorMsg = "Invalid Start date.";
			
			else if(!validateDate(offlineEndDate))
				errorMsg = "Invalid End date.";
			
			else 
			{
				if(new Date(offlineStartDate) > new Date(offlineEndDate) )
				errorMsg = "Start date is greater than End date. ";
			}
		}
	return errorMsg;
}
function setOfflineDates(plantId)
{
	$.post('./get-offline-date-setup-modal.htm',
			{'plantId':plantId},
			function(data) {
				$('#set-offline-dates-modal').html(data);
				
				$('#set-offline-dates-modal').find('.error').hide();
				$('.errorMsgInput').removeClass('errorMsgInput');
				
				$('#set-offline-dates-modal').dialog('open');
			});
}

function validateDate(input){
	
	if(input=="")
		{
			//if date is empty
			return true;
		}
	else
		{
			//Detailed check for valid date ranges
			var monthfield=input.split("/")[0];
			var dayfield=input.split("/")[1];
			var yearfield=input.split("/")[2];
			var dayobj = new Date(yearfield, monthfield-1, dayfield);
		
				if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield)){
					returnVal= false;
				}
				else{
					returnVal=true;
					return returnVal;
				}
		}
		
	
}