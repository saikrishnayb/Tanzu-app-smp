/**
 * 
 */

/*Global Variables for Session Management*/
var sessionExpiryTime;
var currentTime;
var popUpInterval=300;							//Time in seconds before which session timeout pop up comes
var count=popUpInterval;									
var sessionTimeout=240;							//Time in Minutes Session Timeout value
var tabId="0";
var homeLink=0;


$(document).ready(function(){ 
	
	//redirect("orderconfirmation");
	
	
	/*redirecting to the tab which is present in first in navigation*/
	$('#sessionTimeoutPopup').dialog({
		autoOpen: false,
		modal: true,
		dialogClass		: 'popupModal',
		width			: 370,
		minHeight		: 170,
		position		: { my: "center top", at: "center top", of: window },
		resizable: false,
		title: 'Session Expiration',
		closeOnEscape: false,
		open: function(event, ui) {$(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();}
	});
	
	var tabKey=$('nav ul li a',parent.document).first().attr('id');
	redirect(tabKey,null);
	
	
	
	calcOffset();				//To calculate expiry time when the page loads
	checkSession();				//To determine whether the session is alive
	
});

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        console.log('value of cookie: ' + c.substring(name.length, c.length));
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

/**
 * Method for displaying timer for session timeout
 * @returns
 */
var displayCountdown = function() {
    var countdown = function() {
        var cd = new Date(count *1000),                             // Returns milliseconds since 01/01/70
            minutes = cd.getUTCMinutes(),                            // Grab the minutes
            seconds = cd.getUTCSeconds();                            // Grab the seconds

        document.title = 'Expire in ' + minutes + ':' + seconds;     // Update the HTML title
        $('#sessionExpiryTime').html("Your Session is about to expire in " +minutes + ":" + seconds+". Do you want to continue?");            // Update the countdown display
        if (count === 0) {                                           // If we reached zero,
            document.title = 'Session Expired';                      // update the HTML title
            logOut();                                            // and end the session
        }
        count--;
    };
    countdown();                                                      // Call the function once
    displayCountdownIntervalId = window.setInterval(countdown, 1000); // Call the function every second
};

/*-----------------------------------------------*/
/**
 * Session Timeout
 * Method to calculate offset between server and client time
 * 
*/
function calcOffset() {
    sessionExpiryTime=(new Date()).getTime()+(sessionTimeout*60*1000);	//Calculates Session Expiry Time
    currentTime=(new Date()).getTime();									//Calculates Current Time.
}
/**
 * Session Timeout
 * Method to  check whether session has timed-out
 * 
*/

function checkSession() {
	currentTime=(new Date()).getTime();	
	if(currentTime>sessionExpiryTime)									//Check if session expired
		logOut();														//Logout 
	
	else if(currentTime>sessionExpiryTime-(count*1000))					//Check for a specific time-limit before session timeout
		{
			openConfirmModal();											//Open the pop up
			displayCountdown();											//Call the timer function for session timeout
		}
		
	else
		setTimeout('checkSession()', 2000);								//Check whether the sesssion is alive for every 2 seconds
}


	function openConfirmModal(){
		$('#sessionTimeoutPopup').dialog('open');
		$('#sessionTimeoutPopup').show();
	}
	
	function closeConfirmDialog(){
		$('#sessionTimeoutPopup').dialog('close');
		$('#sessionTimeoutPopup').hide();
	}
/**
 * Session Timeout
 * Method to validate the session
 */
	function validateSession()
	{
		closeConfirmDialog();
		currentTime=(new Date()).getTime();
		
				calcOffset();													//Calculate expiry time 		
				checkSession();													//Check the session timeout
				window.clearInterval(displayCountdownIntervalId); 				//Clear the timer for session timeout 
				count=popUpInterval;														//Reset the value 
				document.title="Supplier Management Center ";
				
				var url= getContextRoot()+"/login/validateSession.htm";			//Call the method to make session-alive
				$.ajax({
					  url		: url,
					  type		: 'POST',
					  timeout   :30000,
					  data		: {}	,
		    success	:function(data) {},
		    error:function(){}
				});
				
				var smcofurl= "/smcof/validateSession.htm";						//Call the method to make session-alive
				$.ajax({
					  url		: smcofurl,
					  type		: 'POST',
					  timeout   :30000,
					  data		: {}	,
		    success	:function(data) {},
		    error:function(){}
				});
				
				var smcopurl= "/smcop/validateSession.htm";						//Call the method to make session-alive
				$.ajax({
					  url		: smcopurl,
					  type		: 'POST',
					  timeout   :30000,
					  data		: {}	,
		    success	:function(data) {},
		    error:function(){}
				});
				
			
	}
	
/*-----------------------------------------------*/
function blockElement(elementId){
	calcOffset();
    $("#"+elementId).block({
                    message: '<img src="../images/spinner-big.gif" />' 
    }); 
}

function unBlockPopupModal(elementId){
	$("."+elementId).unblock(); 
	}
function blockPopupModal(elementId){
	calcOffset();
    $("."+elementId).block({
                    message: '<img src="../images/spinner-big.gif" />' 
    }); 
}

function unBlockPopupModal(elementId){
	$("."+elementId).unblock(); 
	}

/*Reload the page When buddy list is selected*/
function reloadPage(){
	calcOffset();
	var currentTab=$('nav ul li.current a',parent.document).attr('id');
	redirect(currentTab);
}
/**
 * redirecting on click of page Onclick of Tab
 * @param tabName
 */
function redirectToTab(tabName){
	redirect(tabName,0);
}

function redirect(tabName,templateKey){
	
	showLoadingForPage();
	var path = "";
	var controllerName="";
	if(tabName == 'orderfulfillment'){
		path = "smcof";
	}else if(tabName == 'orderconfirmation'){
		path = "smcop";
		controllerName="oc";
	}else if(tabName == 'production'){
		path = "smcop";
		controllerName="prod";
	}else if(tabName == 'adminconsole'){
		path = "adminconsole";
	}else if(tabName == 'edi870Error'){
		path = "smcop";
		controllerName="edi870";
	}else if(tabName == "productionStatus"){
		path = "smcop";
		controllerName="prod";
	}else if(tabName == "dataConflict"){
		path = "smcop";
		controllerName="dataConflict";
	}else if(tabName=="dateValidation"){
		path = "smcop";
		controllerName="dateValidation";
		
		if(homeLink==1){
			templateKey=1;	
		}else{
			templateKey=2;	
		}
		
	}else if(tabName=="massUpload"){
		path = "smcop";
		controllerName="massUpload";
	}
	else if(tabName=="Home")
	{
		path = "Home";
		controllerName=tabId;
	}
	else if (tabName=="changeOrders"){
		path = "smcof";
		controllerName="change-orders";
	}
	else if (tabName=="cancellations"){
		path = "smcof";
		controllerName="cancellations";
	}	
	else if (tabName=="massComponentUpdate"){
		path = "smcof";
		controllerName="mass-component-update";
	}		
	else{
		path = "Home";
		controllerName=tabId;
	}
	if(path != ""){
		 
		$('nav ul li.current',parent.document).removeClass('current');
		$('a[id$="' + tabName + '"]',parent.document).parent('li').addClass('current');
		$('a[id="' + tabName + '"]').parent('li').addClass('current').closest('nav > ul > li').addClass('current');
		//selectCurrentNavigation(tabName);
		 $.ajax({
			  type: 'POST',
			  url:  getContextRoot()+'/navigation/navigate.htm',
			  data: { path: path,controllerName:controllerName,templateKey:templateKey},
			  cache	: false,
	    	  success:function(data) {
	    		  $('#mainFrame',parent.document).attr('src', data);
	    		  $(".processing",parent.document).css('visibility','hidden');
			  }
		 });
	}
	
}
/**
 * this method is used to call a selected template ID On click of the count in the Dash board
 * 
 */
function redirectToTemplate(tabKey,templateKey){
	if(tabKey=='TAB_OF'){	//to redirect to fulfillment Tab
		//Redirecting to admin console for vendor alert items
		if(templateKey == 'ALRT_OF_VEND_ANLYST_ASSG_REQ' || templateKey == 'ALRT_OF_NEW_VEND_SETUP_REQ' || templateKey == 'ALRT_OF_VEND_USER_SETUP_REQ')
			{
			redirect("adminconsole",templateKey);
			
			}else{
				
				redirect("orderfulfillment",templateKey);
			}
	}
	else if(tabKey=='TAB_OC'){	//to redirect to Confirmation Tab
		redirect("orderconfirmation",templateKey);
	}
	else if(tabKey=='TAB_PROD'){	//to redirect to Production Tab
		redirect("production",templateKey);
	}
}


/*Resize page if Pagination count Changed*/
function resizeAfterPaginationChange() {
  
  if(window.parent.iframeResizer !== undefined)
    window.parent.iframeResizer.resizeIframe();
}

function iframeLoaded() {
  
  if(window.parent.iframeResizer !== undefined) {
    window.parent.iframeResizer.resizeIframe();
    window.parent.iframeResizer.initateResizeListener();
  }
  
  hideLoadingOnIframeLoad();
}

/*Function to block whole screen when we click on tabs*/
function showLoadingForPage()
{
	calcOffset();
    $.blockUI.defaults.css = {
                  width:      '30%',
                  top:        '40%',
                  left:       '35%',
                  textAlign:  'center',
                  cursor:     'wait'
                };

    $.blockUI({ message: '<img src="../images/spinner-big.gif" />' });

}
/*this function is used to block screen when child page operations are done*/
function showLoadingForChildPage()
{
	calcOffset();
	 $.blockUI.defaults.css = {
             width:      '30%',
             top:        '40%',
             left:       '35%',
             textAlign:  'center',
             cursor:     'wait'
           };
	  $.blockUI({ message: '<img src="../images/spinner-big.gif" />' });

}


function showLoadingWithMsg(){
	 
	 $.blockUI({ message: '<div id="messageDiv" style="width:100%;">'+
			'<h2><div style="float:left;width:10%"><img src="../images/spinner-big.gif" />'+
			'</div><div style="float:right;width:90%;text-align:left">'+
			'Performing Component Validation. <br/>This Process may take a minute to complete.'+
			'</div></h2></div>' ,
			
		 		css: {padding:        0, 
		 	        margin:         0, 
		 	        width:          '30%', 
		 	        top:            '40%', 
		 	        left:           '35%', 
		 	        textAlign:      'left', 
		 	        color:          '#000', 
		 	        border:         '3px solid #aaa', 
		 	        backgroundColor:'#fff', 
		 	        cursor:         'wait'  }
	 }); 
}

function showLoading()
{
	calcOffset();
	  $.blockUI.defaults.css = {
                    width:      '30%',
                    top:        '40%',
                    left:       '35%',
                    textAlign:  'center',
                    cursor:     'wait'
                  };
	  
	  $(document).ajaxStart($.blockUI({ message: '<img src="../images/spinner-big.gif" />' }));
	  
	  /*un block code for popup is given after the popup is opened*/
      
     /* $(document).ajaxStart($.blockUI({ message: '<img src="../images/spinner-big.gif" />' })).ajaxStop($.unblockUI);*/
}

function hideLoading(){

	$.unblockUI();
	
}

/**
 * This method will hide the loading symbol on page load
 * for specific pages based on div id in the page we can remove hide loading
 *  
 */
function hideLoadingOnIframeLoad(){
	       	//For unit detail page and updatedate page we'll not unblock the screen because on load we are making an ajx call and gettig the complinace count
        	var unitDetailPage=$("#mainFrame").contents().find("#unitDetailPageAfterSubmission");
        	var updateDatePage=$("#mainFrame").contents().find("#updateDateSuccessDiv"); 
        	if(unitDetailPage.length==0 && updateDatePage.length==0){
        		hideLoading();
        	}
	
}

function getContextRoot() {
	//This gets the base url for the SMC container app, not the individual app running inside the iframe
	return window.sessionStorage.getItem('baseContainerAppUrl');
}

var commonStaticUrl = window.sessionStorage.getItem('commonStaticContainerUrl');

function getDateFormat(inputDate)
{
	var dateMnth=inputDate.getMonth()+1;
	var dateYr =inputDate.getFullYear();
    dateMnth=dateMnth < 10 ? '0' + dateMnth : dateMnth;
    var dateDay=inputDate.getDate() < 10 ? '0' + inputDate.getDate() : inputDate.getDate();
    
    return dateMnth+"/"+dateDay+"/"+dateYr;

}

function validateAlphaNumeric(inputValue)
{
	var alphaNum = new RegExp("^[a-zA-Z0-9_]*$");
	if(alphaNum.test(inputValue))
		{
			return true ;
		}
	else
		{
			return false;
		}

}

function validateNumbers(inputValue)
{
	var numbers = new RegExp("^[0-9]*$");
	if(numbers.test(inputValue))
		{
			return true ;
		}
	else
		{
			return false;
		}


}
/**
 * Method to validate date
 * @param input
 * @returns
 */
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

//logg off code
function logOut(){
	
	var baseUrl = sessionStorage.getItem('baseContainerUrl');
	location.href = baseUrl+"/SMCLogOff";
}
//for diaplaying the alert message while page is reloading
function displayAlertModal(successMsg){
	
	alertModal("info",successMsg);
}


/*
 * Method to refresh the iframe source 
 */

function refreshPage(){
	showLoadingForPage();
	//document.getElementById('mainFrame').contentWindow.location.href = document.getElementById('mainFrame').contentWindow.location.href;
	document.getElementById('mainFrame').contentWindow.location.reload(true);
}
	

function changeCurrentTab(value){
	if(value=="update"){
		selectCurrentNavigation("dateValidation",'');
	}
	/*if(value=="dateValidation"){
		selectCurrentNavigation("dateValidationSubTab",'');
	}*/
	if(value=="production"){
		selectCurrentNavigation("productionStatus",'');
	}
	if(value=="massUpload"){
		selectCurrentNavigation("massUpload",'');
	}
	if(value=="edi870"){
		selectCurrentNavigation("edi870Error",'');
	}
	if(value=="dataconflict"){
		selectCurrentNavigation("dataConflict",'');
	}
	
}


function setTabId(tab) {
	tabId=tab;
}

function linkRequest(request){
	if(request=='home'){
		homeLink=2;
	}	
	if(request=='button'){
		homeLink=1;	
	}
}
