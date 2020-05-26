var selectedAttribtelist = [];
$('#attribute-values-div input:checked').each(function() {
	selectedAttribtelist.push($(this).val());
});
var nonSelectedAttribtelist = [];
$('input[type=checkbox]:not(:checked)').each(function() {
	nonSelectedAttribtelist.push($(this).val());
});

$('.attibute-values-checkbox').on("change", function() {
	if (selectedAttribtelist.length > 0 || nonSelectedAttribtelist.length > 0) {
		$("#update-capability").removeClass("buttonDisabled");
	} else if ($(this).is(':checked')) {
		$("#update-capability").removeClass("buttonDisabled");
	} else {
		$("#update-capability").addClass("buttonDisabled");
	}
});

$('.disallow-attibute-values-checkbox').on("change", function() {
	if (nonSelectedAttribtelist.length > 0) {
		$("#update-capability").removeClass("buttonDisabled");
	} else if ($(this).is(':checked')) {
		$("#update-capability").removeClass("buttonDisabled");
	} else {
		$("#update-capability").addClass("buttonDisabled");
	}
});