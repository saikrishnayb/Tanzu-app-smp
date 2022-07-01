$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-excel-uploads");
	
	//if one of the radio buttons is selected hide the error msg.
	$('input[name="uploadSelect"]').on('click', function(){
		$("#radioBttnRequiredMsg").hide();
	});
	
	//file submit button
	$("#fileUploadSubmit").on("click", function(){
		
		var noRadioButtonsChecked = true; //default is that no selection has been made
		
		$('input[name="uploadSelect"]').each(function(){
			if($(this).is(':checked'))
			{
				noRadioButtonsChecked = false; //look for a selection
			}
		});
		
		//if the button is disabled don't go any further.
		if($("#fileUploadSubmit").hasClass('buttonDisabled')){
			return false;
		}
		else if(noRadioButtonsChecked)
		{
			//if the user didn't make a selection show an error msg
			$("#radioBttnRequiredMsg").show();
			
			return false;
		}
		else
		{
			//submit form
			$("#fileUploadSubmit").addClass("buttonDisabled");
			$("#uploadForm").submit();
		}
	});
});