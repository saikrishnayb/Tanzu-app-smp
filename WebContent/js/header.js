$(document).ready(function() 
{
	$('#buddyPopup').dialog({
	autoOpen		: false,
	modal			: true,
	dialogClass		: 'popupModal',
	width			: 350,
	minHeight		: 420,
    position: ['center',75],
	resizable		: false,
	title			: 'Buddy System',
	closeOnEscape	: false
	});
	 
	$('#helpPopup').dialog({
		autoOpen		: false,
		modal			: true,
		dialogClass		: 'popupModal',
		width			: 750,
		maxHeight		: 420,
        height  		: 'auto',
        position: ['center',75],
		resizable		: false,
		title			: 'Help',
		closeOnEscape	: false
	});	
	
	$('#helpLink').on("click", function () {
		openHelpPopup();
	});
	
});

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


function openHelpPopup()
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