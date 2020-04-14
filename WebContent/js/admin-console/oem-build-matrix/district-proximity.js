$(document).ready(function() {
	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-district-proximity");
	
	var $saveProximity = $('.saveProximity');

	$("#tier1").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#list1 li").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});

	$("#tier2").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#list2 li").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});

	$("#tier3").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#list3 li").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});

	//Saves the proximity data
	/*$saveProximity.on("click", function() {
			//AJAX call for updating the T_AND_C frequency setting
			$.ajax({
				type: "POST",
				url: 'insert-district-proximity.htm',
				global: false,
				data: {},
				success: function() {
					
				},
				error: function() {
					
				}
			});
		}); */
	
	setMaxVericalLineHeight();

});

function getContextRoot() {
	return window.sessionStorage.getItem('baseAppUrl');
}

function setMaxVericalLineHeight() {
	var maxVerticalLineHeight = 450; // Default height
	
	if($("#tier1-div") && $("#tier1-div").length > 0 
			&& $("#tier2-div") && $("#tier2-div").length > 0 
			&& $("#tier3-div") && $("#tier3-div").length > 0) {
		maxVerticalLineHeight = Math.max($("#tier1-div").height(), $("#tier2-div").height(), $("#tier3-div").height());
	}
	
	$(".vertical-line").height(maxVerticalLineHeight + "px");	
}
