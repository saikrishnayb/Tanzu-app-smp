$(document).ready(function() {
	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-maintenance-summary");
	
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

/**
 * This method is used to identify the height of each tier to modify the vertical line height.
 * This has been invoked on page load and on-click of expanding icon at each area level under each tier.
 */
function setMaxVericalLineHeight() {
	var maxVerticalLineHeight = 450; // Default height
	
	if($("#tier1-div") && $("#tier1-div").length > 0 
			&& $("#tier2-div") && $("#tier2-div").length > 0 
			&& $("#tier3-div") && $("#tier3-div").length > 0) {
		
		var tier1_div_height = $("#tier1-header").height() + $("#tier1-filter-div");
		var tier2_div_height = $("#tier2-header").height() + $("#tier2-filter-div");
		var tier3_div_height = $("#tier3-header").height() + $("#tier3-filter-div");
		
		maxVerticalLineHeight = Math.max(tier1_div_height, tier2_div_height, tier3_div_height);
	}
	
	$(".vertical-line").height(maxVerticalLineHeight + "px");	
}
