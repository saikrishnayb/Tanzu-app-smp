$("#release-units").multiselect({
	minWidth : 150,
	noneSelectedText : "",
});

function enableUpdate()
{
	var unitsSelected = $('#release-units').val()
	if(unitsSelected.length>0)
		$('#release-units-btn').removeClass('buttonDisabled');
	else
		$('#release-units-btn').addClass('buttonDisabled');
}