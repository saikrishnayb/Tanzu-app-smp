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
	
});

function getContextRoot() {
	return window.sessionStorage.getItem('baseAppUrl');
}

/**
 * This method is used to identify the height of each tier to modify the vertical line height.
 * This has been invoked on-click of expanding icon at each area level under each tier.
 * 
 * Parameter "obj" is passed while invoking this method on-click of expanding icon
 */
function setMaxVericalLineHeight(obj) {
	var maxVerticalLineHeight;
	var tier1_div_height,tier2_div_height,tier3_div_height; 
	
	if($("#tier1-div") && $("#tier1-div").length > 0 
			&& $("#tier2-div") && $("#tier2-div").length > 0 
			&& $("#tier3-div") && $("#tier3-div").length > 0) {
		tier1_div_height = $("#tier1-header").height() + $("#tier1-filter-div").height();
		tier2_div_height = $("#tier2-header").height() + $("#tier2-filter-div").height();
		tier3_div_height = $("#tier3-header").height() + $("#tier3-filter-div").height();
		
		maxVerticalLineHeight = Math.max(tier1_div_height, tier2_div_height, tier3_div_height);
	}
	
	if(obj != null && obj != undefined) {
		var current_tier_div_height = $(obj).closest("div .tier-filter-div").prev("div .tier-header").height() + $(obj).closest("div .tier-filter-div").height();
		var expanded_accordion_height = $(obj).height() + $(obj).next("ul").height(); //label + ul heights
		
		if($(obj).hasClass("expanded")) {
			var tempVerticalLineHeight = current_tier_div_height + expanded_accordion_height; 
			maxVerticalLineHeight = Math.max(maxVerticalLineHeight, tempVerticalLineHeight);

			$(".vertical-line, #tier3-div").height(maxVerticalLineHeight + "px");
		}
		else {
			tempVerticalLineHeight = current_tier_div_height - expanded_accordion_height;

			var current_tier_div_id =  $(obj).closest("div .tier-div").attr("id");
			var tier_number = current_tier_div_id.split("-div")[0].split("tier")[1];
			
			switch(tier_number) {
				case "1": 
					console.log("1");
					maxVerticalLineHeight = Math.max(tempVerticalLineHeight,  tier2_div_height, tier3_div_height); 
					break;
				case "2":
					console.log("2");
					maxVerticalLineHeight = Math.max(tempVerticalLineHeight,  tier1_div_height, tier3_div_height); 
					break;
				case "3": 
					console.log("3");
					maxVerticalLineHeight = Math.max(tempVerticalLineHeight,  tier1_div_height, tier2_div_height); 
					break;
			}

			$(".vertical-line, #tier3-div").height(maxVerticalLineHeight + 20 + "px");

		} 
	} 
}
