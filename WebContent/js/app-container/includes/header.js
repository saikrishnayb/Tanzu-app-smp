$(document).ready(function() {
  
  var $utlityModal = $('.modal-utility');
  var $utilityList = $('#utility .utility-list');
  
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
    
    var $getOrgAssociationModalContentPromise = $.get(getContextRoot() + "/userController/get-vendor-filter-modal-content.htm");
    
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
    
    $toggleVendorFilterPromise.done(function(){
      $(".vendor-filter-toggle-container").toggleClass('on');
      document.getElementById('mainFrame').contentWindow.location.reload(true);
    }).error(function(){
      hideLoading();
    })
    
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
	var url= getContextRoot()+"/userController/getUsersList.htm";

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
	var url= getContextRoot()+"/navigation/getHowTo.htm";

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
	var url= getContextRoot()+"/navigation/getHelp.htm";

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

 
 