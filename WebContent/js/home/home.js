$(document).ready(function() {
	selectCurrentNavigation("tab-home");
	
	initializePage();
	$("#timeoutMessage").hide();
	$("#statusError").hide();
	// When a tab is clicked, hide all Alert Headers and Alerts except for those pertaining to the clicked tab,
	// and show the clicked tab as selected.
	$('.tab').on('click', function() {
		var $tab = $(this);
		var tabKey = $tab.attr('id');
		// Show the clicked tab image as selected. (The changes in padding are to prevent the image from "moving" when the border is added.)
		$('.tab-image-display').removeClass('selected-tab');
		$tab.find('.tab-image-display').addClass('selected-tab');
		getAlertCount(tabKey);
	});
});

function initializePage() {
	// Show the first tab as selected.
	$('.tab:first-child').find('.tab-image-display').addClass('selected-tab');
	
	// Add count to the first Alert Header of each tab.
	$('.header:first-child').find('table thead tr th:nth-child(2)').text('Count');

	// Alternate the row colors for the alerts (doing this here because using nth:child() for this in IE does not work in earlier versions of IE)
	displayTable();
	
	// Bold the first word of each tab name.
	$('.tab-name').each(function() {
		var $tabName = $(this);
		var currentTabName = $tabName.text().split(" ");
		var newTabName = "";
		
		for (var i = 0; i < currentTabName.length; i ++) {
			if (i == 0) {
				newTabName = newTabName + '<span class="bold">' + currentTabName[i] + '</span>';
			}
			else {
				newTabName = newTabName + ' ' + currentTabName[i];
			}
		}
		
		$tabName.html(newTabName);
	});
}

function displayTable(){
	// Add count to the first Alert Header of each tab.
	$('.header:first-child').find('table thead tr th:nth-child(2)').text('Count');	
	$('.header table').each(function() {
		var $table = $(this);
		var odd = true;
		
		// Alternate between the even and odd classes for the rows.
		$table.find('tbody tr').each(function() {
			var $row = $(this);
			
			if (odd) {
				$row.addClass('odd');
				odd = false;
			}
			else {
				$row.addClass('even');
				odd = true;
			}
		});
	});
	var isFlag = false;
	$(".flags").each(function(){
		var flagVal = $(this).val();
		if(flagVal == 'Y'){
			isFlag = true;
		}
	});
	if(isFlag){
		$("#flagMessage").show();
	}
	else 
		$("#flagMessage").hide();
}

/**
 * this method is used to call a selected template ID On click of the count in the Dash board
 * 
 */
function redirectToTemplate(tabKey,templateKey){
	parent.redirectToTemplate(tabKey,templateKey);	//Calling parent js for redirecting
}
/**
 * this method is used to call a selected template On click of the count of out of compliance,data conflict and 
 upstream vendor items in the Dash board
 * 
 */
function redirectToTab(subTabName){
	parent.redirectToTab(subTabName);
}


function getContextRoot() {
	return $("#contextRoot").val();
}

function showLoading()
{
	//showLoadingForPage();//ForChildPage();
	  
	parent.showLoadingForChildPage();
}

function hideLoading(){
	parent.hideLoading();
	
}
function getAlertCount(tabKey){
	var url=getContextRoot()+"/home/getAlerts.htm?tabKey="+tabKey;
	$("#statusError").hide();
	$("#timeoutMessage").hide();
	//showLoading();
	$.ajax({
		url     :  url,
		type    : "GET",
		timeout :  50000,
		contentType: false,
		processData :false,
		success : function(response) {
			var alertMessage = $(response).find('#alertTable').html();
			$("#alertTable").html(alertMessage); 
			displayTable();
			hideLoading();
		},error: function(jqXHR, textStatus, errorThrown) {
			hideLoading();
			if(jqXHR.status == 500){
				$("#statusError").show();  // Error message will be shown 	
			}
			else if(textStatus=="timeout"){								
				$("#timeoutMessage").show();
			}
			return;
		}
	});
}
