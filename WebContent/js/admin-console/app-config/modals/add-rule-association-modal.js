var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
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
	     //submit the form only if the button is not disabled.
		 if (!$("#saveRuleAssociation").hasClass("buttonDisabled")) {
		 var errorMsg = '';
		 // Validate the form except the scenario where all the rows are deleted while edit.
		 if(!($("#ruleCount").val()!=0 && $("#rules > div").length==0))
		 errorMsg = validateAddRuleAssociationForm();
		
		// If no error message was returned, hide any errors and submit the form data.
		if (errorMsg.length == 0) {
			$error.hide();
			$.ajax({
				  type: "POST",
				  url: "./save-rule-association-modal-data.htm",
				  data: $('#add-rule-association-form').serialize(),
				  success: function(data){
					  var url=window.location.href;
						if (url.indexOf("requestedFrom") >= 0){	// if request came from Back button in create rule
							window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
						}else if(url.indexOf("create-rule.htm") >= 0){//if request came from Save button in create rule
							url=url.replace("create-rule.htm","goBack-createRule.htm");
							window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
						}else{
							//add component id while reloading to scroll to the correct component.
							var url=window.location.href;
							//i frequest comes from loadsheetmanagement page
							if(url.indexOf("get-loadsheet-components.htm") >=0){
								if(url.indexOf("?componentId") >=0){
									window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
								}
								url=url.substring(0,url.indexOf("&componentId", 0));
								if(url==''){
								window.location.href=window.location.href +"&componentId="+$("#componentIdforAddRule").val();
								}else{
									window.location.href=url +"&componentId="+$("#componentIdforAddRule").val();
								} 
								
							}else{//else from create rule back/save button
								window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
							}
						}
						processingImageAndTextHandler('visible','Loading data...');
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
	  }	
		
	});
	
	//To Validate the priority fields with numeric data.
	$addRuleAssociationModal.on('keypress', '.priority' , function(e){
		
		
		//if the letter is not digit don't type anything
	    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	       //display error message
	       return false;
	   }else{
		     //to check duplicate priority values.
			 var inputArr = $("input[class='priority']").map(function(){return $(this).val();}).get();
			 var priorityArray = inputArr.filter(function(v){return v!==" ";});
			 var keynum='';
			 if(window.event) { // IE                    
			      keynum = e.keyCode;
			 } else if(e.which){ // Netscape/Firefox/Opera                   
			      keynum = e.which;
			 }
			 //keyed input number
			 var inputNumber = String.fromCharCode(keynum);
			 
			 // to get priority text box value.
			 var inputValue=$(this).val();
			 inputValue=+inputValue+inputNumber;
			 
			 //if the priority value is '0' don't type anything
			 if(inputValue==0){
			     return false;
			 }
			//if the priority digit is already entered don't type anything
			 if($.inArray(inputNumber,priorityArray)>-1){
			     return false;
			 }
			//if the priority value is already entered don't type anything
			 if($.inArray(inputValue,priorityArray)>-1){
				 return false;
			 }
			//if the priority value is greater than '10' don't type anything
			 if(inputValue>10){
				 return false; 
			 }
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
		disableOptions();
	});
	
	$addRuleAssociationModal.on('keypress', function(e) {
		if (e.which == 13) {
			$addRuleAssociationModal.find('.save').trigger('click');
		}
	});
	
	$addRuleAssociationModal.find('form').submit(function() {
		return false;
	});
	
	$("#addRule").blur(); 	
});

/**
 * function to add new row for rule association.
 */
function addRule() {
	
	var divCount =$("#rules > div").length;	
	   if(divCount<10){
	
	var newDiv='<div id="rule'+rowCount+'" style="width:100%;padding-top: 1%">'
	+'<div class="column-data-left"><input name="rule['+rowCount+'].priority"  maxlength="2" required="required" class="priority" id="ruleProirity'+rowCount+'" style="width:74%;text-align:right;"/></div>' 
	+'<div class="column-data-center"><select  name="rule['+rowCount+'].ruleId" style="width:92%" class="rule" style="width:53%" id="ruleId'+rowCount+'">'
	+'</select></div>'
	+'<div class="column-data-right"><select  name="rule['+rowCount+'].lsOverride" class="lsOverRide" id="rulelsOverride'+rowCount+'" style="width:60%">'
	+'</select>'
	+'<a style="text-decoration:none;padding-left: 22%;" id="deleteRule" onclick="deleteRule('+rowCount+');">'
	+'<img src='+commonStaticUrl+'/images/delete.png class="centerImage handCursor" tabindex=0 alt="delete Row"/></a></div>'
	+'</div>';
	$('#rule-association-modal #rules').append(newDiv);
	var $options = $("#ruleId > option").clone();
	$('#ruleId'+rowCount).append($options);
	$('#ruleId'+rowCount).val('');
	
	$options = $("#rulelsOverride > option").clone();
	$('#rulelsOverride'+rowCount).append($options);
	$('#rulelsOverride'+rowCount).val('');
	var prorityArray = $("input[class='priority']").map(function(){return $(this).val();}).get();
	var maxPriority = Math.max.apply(Math, prorityArray);
	var nextPriority=maxPriority+1;
	if(nextPriority<11)
	$('#ruleProirity'+rowCount).val(nextPriority);
   rowCount++;
   $error.hide();
   }else {
		$error.find('.errorMsg').text("Maximum number of rules allowed 10.");
		$error.show();
	}
    // to enable the save button if it is disabled.
   if ($("#saveRuleAssociation").hasClass("buttonDisabled")) {
	   $("#saveRuleAssociation").removeClass("buttonDisabled").addClass("buttonPrimary"); 
   }
   disableOptions();//function to disable options in rule drop down based on the selection.
}



/**
 * function to delete a rule 
 * 
 */
function deleteRule(count) {
	$('#rule'+count).remove();
	 $error.hide();
	 // to disable the save button when there are no rows.
	 if(($("#ruleCount").val()==0 && $("#rules > div").length==0)){
		 $("#saveRuleAssociation").removeClass("buttonPrimary").addClass("buttonDisabled");
	 }
	 disableOptions();//function to disable options in rule drop down based on the selection.
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
			errorMsg = 'A required field is missing.';
		}else if(val.indexOf("Override") >= 0){
			errorMsg = 'A required field is missing.';
		}else{
			errorMsg = 'A required field is missing.';
		}
		$(this).addClass('errorMsgInput');
	   }
	});

	
	return errorMsg;
}
/* function to disable options in rule drop down based on the selection. */
function disableOptions()
{
    $('.rule option').attr("disabled",false); //enable everything
	//collect the values from selected;
	 var  arr = $.map($('.rule option:selected'), function(n){
	          return n.value;
	     });
	 var newArray = arr.filter(function(v){return v!==" ";});
	//disable elements
	$('.rule option').filter(function()
	{
	    return $.inArray($(this).val(),newArray)>-1; //if value is in the array of selected values
	 }).attr("disabled",true);   
	
}
//# sourceURL=add-rule-association-modal.js