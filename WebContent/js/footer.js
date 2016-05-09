$(document).ready(function() 
{
	$('#termsAndConditionPopup').dialog({
	autoOpen		: false,
	modal			: true,
	dialogClass		: 'popupModal',
	width			: 650,
	minHeight		: 220,
	position		: { my: "center top", at: "center top", of: window },
	resizable		: false,
	title			: 'Terms and Condition',
	closeOnEscape	: true
	});
	

	
});

function showTermsAndCondition(){
	showLoading();
	var url= getContextRoot()+"/userController/getTermsAndCondition.htm";

	 $.ajax({
	            url : url,
	            success : function(data) 
	            {
	            		$("#getTermsAndCondition").empty();
		            	$("#getTermsAndCondition").append(data);
		            	$("#termsAndConditionPopup").dialog('open');
		            	hideLoading();
	            },
	            error: function(jqXHR, textStatus, errorThrown) 
	            {
	            	if(jqXHR.status == 500){
	    				
	    				alertModal("info","Error in opening Terms and Conditions.");
	    			}
	    			else if(textStatus=="timeout"){															 
	    				
	    				alertModal("info","Sorry Timed out.Please Try again.");
	    			}
						/*alertModal("info","Error");
	                  $(".commonException").show();*/
	                  hideLoading();
	                  return;
	            }
	      });
	
}
