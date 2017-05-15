var commonStaticUrl =$('#common-static-url').val();
$addRuleAssociationModal=$('#rule-association-modal');
var $error = $addRuleAssociationModal.find('.error');
var rowCount=1;
// get the number of rows
$(document).ready(function() {
	//to get the row count
	rowCount = $("#ruleCount").val();
	if(rowCount==0 || rowCount==1){
		rowCount=1;
	}
	//$addRuleAssociationModal.unbind();
	$addRuleAssociationModal.on('click', '.save', function() {
		var errorMsg = '';
		// Validate the form.
		errorMsg = validateAddRuleAssociationForm();
		
		// If no error message was returned, hide any errors and submit the form data.
		if (errorMsg.length == 0) {
			$error.hide();
			$.ajax({
				  type: "POST",
				  url: "./save-rule-association-modal-data.htm",
				  data: $('#add-rule-association-form').serialize(),
				  success: function(data){
					 // $('#rule-association-modal').dialog('close');
					  location.reload();
				  },
				  error: function(XMLHttpRequest, textStatus, errorThrown) {
					  $error.find('.errorMsg').text(XMLHttpRequest.responseText);
					  $error.show(); 
					
				  }
				});
		}
		// If an error was found, display it to the user and do not submit the form data.
		else {
			$error.find('.errorMsg').text(errorMsg);
			$error.show();
		}
		
		
	});
	
	//To Validate the priority fields with numeric data.
	$addRuleAssociationModal.on('keypress', '.priority' , function(e){
	    //if the letter is not digit don't type anything
	    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	       //display error message
	       return false;
	   }
	    $(this).removeClass('errorMsgInput');
	    $error.hide();
	  });	
	
	$addRuleAssociationModal.on('change','.lsOverRide', function() {
		if($(this).val() != " "){
			$(this).removeClass('errorMsgInput');
			 $error.hide();
		}
	});
	
	$addRuleAssociationModal.on('change','.rule', function() {
		if($(this).val() != " "){
			$(this).removeClass('errorMsgInput');
			 $error.hide();
		}
	});
	
	$addRuleAssociationModal.on('keypress', function(e) {
		if (e.which == 13) {
			$addRuleAssociationModal.find('.save').trigger('click');
		}
	});
	
	$addRuleAssociationModal.find('form').submit(function() {
		return false;
	});
	
	
});

/**
 * function to add new row for rule association.
 */
function addRule() {
	
	var newDiv='<div id="rule'+rowCount+'" style="width:100%;padding-top: 1%">'
	+'<div class="column-data-left"><input name="rule['+rowCount+'].priority" maxlength="2" required="required" class="priority" id="ruleProirity'+rowCount+'" style="width:74%"/></div>' 
	+'<div class="column-data-center"><select  name="rule['+rowCount+'].ruleId" style="width:92%" class="rule" style="width:53%" id="ruleId'+rowCount+'">'
	+'</select></div>'
	+'<div class="column-data-right"><select  name="rule['+rowCount+'].lsOverride" class="lsOverRide" id="rulelsOverride'+rowCount+'" style="width:60%">'
	+'</select>'
	+'<a style="text-decoration:none;padding-left: 22%;" id="addRule" href="#"onclick="deleteRule('+rowCount+');">'
	+'<img src='+commonStaticUrl+'/images/delete.png class="centerImage handCursor" alt="delete Row"/></a></div>'
	+'</div>';
	$('#rule-association-modal #rules').append(newDiv);
	var $options = $("#ruleId0 > option").clone();
	$('#ruleId'+rowCount).append($options);
	$('#ruleId'+rowCount).val('');
	
	$options = $("#rulelsOverride0 > option").clone();
	$('#rulelsOverride'+rowCount).append($options);
	$('#rulelsOverride'+rowCount).val('');
   rowCount++;
   $error.hide();
}



/**
 * function to delete a rule 
 * 
 */
function deleteRule(count) {
	$('#rule'+count).remove();
	 $error.hide();
}


/**
 * This function validates the form to make sure that the data is valid. If invalid data is found, then
 * an error message is sent back to be displayed to the user.
 * 
 * @returns		- the error message
 */
function validateAddRuleAssociationForm() {
	errorMsg = '';
	$("#rules :input").each(function() {
	   if($(this).val().trim() === ""){
		var val = $(this).attr('id');
		if (val.indexOf("Proirity") >= 0){
			errorMsg = 'Please enter Priority. ';
		}else if(val.indexOf("Override") >= 0){
			errorMsg = 'Please select LS Override. ';
		}else{
			errorMsg = 'Please select Rule. ';
		}
		$(this).addClass('errorMsgInput');
	   }
	});

	
	return errorMsg;
}

//# sourceURL=add-rule-association-modal.js