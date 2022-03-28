$("#values").multiselect({
	minWidth : 150,
	noneSelectedText : "",
});

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