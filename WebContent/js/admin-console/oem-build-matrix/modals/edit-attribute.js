$("#values").multiselect({
	close : function() {},
	minWidth : 150,
	noneSelectedText : "",
	open : function() {
		//Code added to increase the width of drop down content
		$(".ui-multiselect-menu ").css('width', '169px');
	}
});

$(".ui-multiselect-menu ").css('width', '169px');

function validateAttributeValue($form){
	var valid = validateFormTextFields($form);
	var attributeValue = $('#add-attribute-modal').find('#attributeValue').val();
	
	if(attributeValue == '')
		{
		valid = false;
		$("#ErrorMsg span").text("Attribute value must be provided");
		$("#ErrorMsg").show();
		$("#attributeValue").addClass("errorMsgInput");
		}
return valid;
}

function enableUpdate()
{
	var originalValueList=$('.attr-original-values').val().slice(1,-1).split(',');
	var optionSelected = $('#values').val()
	if(originalValueList.length!=optionSelected.length)
		$('#update-attr').removeClass('buttonDisabled');
	else
		$('#update-attr').addClass('buttonDisabled');
	
}

//# sourceURL=edit-attribute.js