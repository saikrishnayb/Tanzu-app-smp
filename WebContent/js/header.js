$(document).ready(function() 
{
	$('#buddyPopup').dialog({
	autoOpen		: false,
	modal			: true,
	dialogClass		: 'popupModal',
	width			: 350,
	minHeight		: 420,
	position		: { my: "center top", at: "center top", of: window },
	resizable		: false,
	title			: 'Buddy System',
	closeOnEscape	: false
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