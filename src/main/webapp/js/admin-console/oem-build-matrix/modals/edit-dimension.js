var selectedAttributelist = [];
$('#attribute-values-div input:checked').each(function() {
	selectedAttributelist.push($(this).val());
});

var nonselectedAttributelist = [];
$('input[type=checkbox]:not(:checked)').each(function() {
	nonselectedAttributelist.push($(this).val());
});

$('.attibute-values-checkbox').on("change", function() {
	if (selectedAttributelist.length > 0 || nonselectedAttributelist.length > 0) {
		$("#update-capability").removeClass("buttonDisabled");
	} else if ($(this).is(':checked')) {
		$("#update-capability").removeClass("buttonDisabled");
	} else {
		$("#update-capability").addClass("buttonDisabled");
	}
});

$('.disallow-attibute-values-checkbox').on("change", function() {
	if (nonselectedAttributelist.length > 0) {
		$("#update-capability").removeClass("buttonDisabled");
	} else if ($(this).is(':checked')) {
		$("#update-capability").removeClass("buttonDisabled");
	} else {
		$("#update-capability").addClass("buttonDisabled");
	}
});