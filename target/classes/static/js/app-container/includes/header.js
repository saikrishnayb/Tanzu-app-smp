var $statusDialog;

$(document).ready(function() {
  
  var $utlityModal = $('.modal-utility');
  var $utilityList = $('#utility .utility-list');
  $statusDialog = $('#status-dialog');
  
  //Initialization ******************************************************
  ModalUtil.initializeModal($utlityModal);
  
  $('#buddyPopup').dialog({
    autoOpen    : false,
    modal     : true,
    dialogClass   : 'popupModal',
    width     : 350,
    minHeight   : 420,
      position: ['center',75],
    resizable   : false,
    title     : 'Buddy System',
    closeOnEscape : false
  });
   
  $('#helpPopup').dialog({
    autoOpen    : false,
    modal     : true,
    dialogClass   : 'popupModal',
    width     : 750,
    maxHeight   : 420,
        height      : 'auto',
        position: ['center',75],
    resizable   : false,
    title     : 'Help',
    closeOnEscape : false,
    close     : function (event, ui) {
      $("#helpPopup").empty();
    }
  }); 
  
  $('#helpSelector').dialog({
    autoOpen    : false,
    modal     : true,
    dialogClass   : 'popupModal',
    width     : 750,
    maxHeight   : 420,
        height      : 'auto',
        position: ['center',75],
    resizable   : false,
    title     : 'Help',
    closeOnEscape : false
  });
  
  $statusDialog.dialog({
		autoOpen: false,
		modal: true,
		width: $statusDialog.find('.dialog-content').data('dialog-width'),
		minHeight: 0,
		resizable: false,
		closeOnEscape: false,
		close: function() {
			$(this).find('.update-message').empty();
		},
		dialogClass: 'no-titlebar'
	});
  
  //Listeners **********************************************************
    
  $('#helpLink').on("click", function () {
    openHelpSelector();
  });
  
  $('#frequentlyAsked').on("click", function () {
    openHelp();
  }); 
  
  $('#howToVideos').on("click", function () {
    openHowToVideo();
  });    
  
  $utilityList.on('click', '.vendor-filter', function() {
    
    showLoading();
    
    var $getOrgAssociationModalContentPromise = $.get(getContextRoot() + "/userController/get-vendor-filter-modal-content");
    
    $getOrgAssociationModalContentPromise.done(function(modalContent) {
      
      $utlityModal.html(modalContent);
      ModalUtil.openModal($utlityModal);
      
    }).always(function() {
      hideLoading();
    });
    
  });
  
$utilityList.on('click', '.vendor-filter-toggle', function() {
	showLoading();
	var $toggleVendorFilterPromise = $.post(getContextRoot() + "/userController/toggle-vendor-filter");
	
	$toggleVendorFilterPromise.done(function() {
		$(".vendor-filter-toggle-container").toggleClass('on');
		var currentTab = $('nav ul li.current a', parent.document).attr('id');
		if (currentTab == 'Home') {
			$('#mainFrame').contents().find('div.selected-tab').click();
		} else {
			document.getElementById('mainFrame').contentWindow.location.reload(true);
		}
	}).error(function() {
		hideLoading();
	})
});

  $('nav ul li a').on('mousedown', function() {
	closeValidationWindow();
  });
  
});

// Functions ************************************************************

function refreshFrameWithoutWarnings(dontShowLoading){
  
  if(dontShowLoading === undefined) showLoading();
  document.querySelector('#mainFrame').contentWindow.location.href += ''; 
}
function openHelpSelector()
{
	$("#helpSelector").show();
	$("#helpSelector").dialog('open'); 
}

function openBuddyPopup()
{
	showLoading();
	var url= getContextRoot()+"/userController/getUsersList";

	 $.ajax({
	            url 			: url,
	            cache			:false,
	            success : function(data) 
	            {
	            	
	            	$("#buddyPopup").show();
	            	$("#buddyPopup").html(data);
	            	$("#buddyPopup").dialog('open');   
	            	hideLoading();
	            
	            },
	            error: function(jqXHR, textStatus, errorThrown) 
	            {
						alertModal("info","Error");
	                  $(".commonException").show();
	                  hideLoading();
	                  return;
	            }
	      });
}

function openHowToVideo()
{
	showLoading();
	var url= getContextRoot()+"/navigation/getHowTo";

	 $.ajax({
	            url 			: url,
	            cache			:false,  
	            success : function(data) 
	            {
	            	
	            	$("#helpPopup").show();
	            	$("#helpPopup").html($.trim(data));
	            	$("#helpPopup").dialog('open');
	            	hideLoading();
	            
	            },
	            error: function(jqXHR, textStatus, errorThrown) 
	            {
						alertModal("info","Error");
	                  $(".commonException").show();
	                  hideLoading();
	                  return;
	            }
	      });

}


function openHelp()
{
	showLoading();
	var url= getContextRoot()+"/navigation/getHelp";

	 $.ajax({
	            url 			: url,
	            cache			:false,  
	            success : function(data) 
	            {
	            	
	            	$("#helpPopup").show();
	            	$("#helpPopup").html($.trim(data));
	            	$("#helpPopup").dialog('open');
	            	hideLoading();
	            
	            },
	            error: function(jqXHR, textStatus, errorThrown) 
	            {
						alertModal("info","Error");
	                  $(".commonException").show();
	                  hideLoading();
	                  return;
	            }
	      });
}

//***** Loading Overlay - Accessed by JS in Child Frame *****//
function showStatusDialog() {
	$statusDialog.dialog('open');
}

function hideStatusDialog() {
	$statusDialog.dialog('close');
}

///////////////////////////
var validationWindow;
function openValidationWindow(url) {
	validationWindow = window.open(url,"validationWindow","toolbar=0,resizable=1,scrollbars=1");
	return validationWindow;
}

function closeValidationWindow() {
	if(validationWindow !== undefined)
		validationWindow.close();
	
	validationWindow = undefined;
}

function getValidationWindow(){
	return validationWindow;
}

$(window).on('beforeunload', function (){
	closeValidationWindow();
});