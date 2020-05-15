$('.attibute-values-checkbox').on("change", function() {

	if ($(this).is(':checked')) {
		$("#update-capability").removeClass("buttonDisabled");
	} else {
		$("#update-capability").addClass("buttonDisabled");
	}
});

$('.disallow-attibute-values-checkbox').on("change", function() {

	if ($(this).is(':checked')) {
		$("#update-capability").addClass("buttonDisabled");
	} else {
		$("#update-capability").removeClass("buttonDisabled");
	}
});