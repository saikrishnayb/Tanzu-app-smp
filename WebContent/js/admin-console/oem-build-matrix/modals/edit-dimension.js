$('.attibute-values-checkbox').on("change", function() {
	var capabilityUpdatelist = [];
	$('input[type=checkbox]:not(:checked)').each(function() {
		capabilityUpdatelist.push($(this).val());
	});
	if ($(this).is(':checked')) {
		$("#update-capability").removeClass("buttonDisabled");
	} else {
		$("#update-capability").addClass("buttonDisabled");
	}
	if (capabilityUpdatelist.length == 0) {
		$("#update-capability").addClass("buttonDisabled");
	} else {
		$("#update-capability").removeClass("buttonDisabled");
	}

});

$('.disallow-attibute-values-checkbox').on("change", function() {
	var capabilityUpdateslist = [];
	$('input[type=checkbox]:not(:checked)').each(function() {
		capabilityUpdateslist.push($(this).val());
	});
	if ($(this).is(':checked')) {
		$("#update-capability").addClass("buttonDisabled");
	} else {
		$("#update-capability").removeClass("buttonDisabled");
	}
	if (capabilityUpdateslist.length == 0) {
		$("#update-capability").addClass("buttonDisabled");
	} else {
		$("#update-capability").removeClass("buttonDisabled");
	}
});