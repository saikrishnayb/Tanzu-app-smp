$(document).ready(function() {
	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-maintenance-summary");
	var proximityChangeCnt = 0;
	var proximityUpdateList = [];

	$("#tier1, #tier2, #tier3").on("keyup", function() {
		var tierNumber = $(this).attr("id").split("tier")[1];
		var value = $(this).val().toLowerCase();
		var listId = "#list" + tierNumber + " li";

		$(listId).filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
		$(listId).find('.district-values').show();
		setMaxVericalLineHeight();
	});

	$('.select-deselect-all').on("click", function() {
		$(this).parent().parent().find('.district-checkbox').trigger("change");
	});

	$('.district-checkbox').on("change", function() {
		debugger;
		var plantId = parseInt($('#plantId').val());
		var area = $(this).attr('area');
		var tier = parseInt($(this).attr('tier'));
		var district = $(this).val();
		var proximityId = ($(this).attr('proximityId') != "" && $(this).attr('proximityId') != undefined) ? parseInt($(this).attr('proximityId')) : "";
		var proximityUpdateObj = {};
		proximityUpdateObj['proximityId'] = proximityId;
		proximityUpdateObj['plantId'] = plantId;
		proximityUpdateObj['tier'] = tier;
		proximityUpdateObj['district'] = district;

		if ($(this).is(':checked')) { //user selects a district
			if (proximityId == 0 || proximityId == "") {
				//selected district to be inserted
				proximityUpdateObj["removeDistrict"] = false;
				proximityUpdateList.push(proximityUpdateObj);
				proximityChangeCnt++;
			} else { //in this case,user reverts selection
				proximityUpdateList = $.grep(proximityUpdateList, function(e) {
					var include = true;
					if (e.district == district) {
						if (e.tier == tier)
							include = false;
					}
					return include;
				// return e.district != district && e.tier !=tier; 
				});
				proximityChangeCnt--;
			}
		} else { //user de-selects a  district
			if (proximityId != 0 && proximityId != "") {
				//selected district to be deleted
				proximityUpdateObj["removeDistrict"] = true;
				proximityUpdateList.push(proximityUpdateObj);
				proximityChangeCnt++;
			} else { //in this case,user reverts selection
				proximityUpdateList = $.grep(proximityUpdateList, function(e) {
					var include = true;
					if (e.district == district) {
						if (e.tier == tier)
							include = false;
					}
					return include;
				});
				proximityChangeCnt--;
			}
		}
		
		//enable/disable save button
		if (proximityUpdateList.length != 0) {
			$("#save-proximitybtn").removeClass("buttonDisabled");
		} else {
			$("#save-proximitybtn").addClass("buttonDisabled");
		}
	});
	
	//Saves the proximity data
	$('#save-proximitybtn').on("click",function() {
		if (proximityUpdateList && proximityUpdateList.length != 0) {
			showLoading();
			var $saveProximityPromise = $.ajax({
				type : "POST",
				url : './save-district-proximity.htm',
				global : false,
				data : JSON.stringify(proximityUpdateList),
				contentType : 'application/json'
			});
			$saveProximityPromise.done(function(data) {
				window.location.href = "maintenance-summary.htm";
			});
			$saveProximityPromise.fail(function(xhr, ajaxOptions, thrownError) {
				if (xhr.responseText.indexOf('Error Processing the Save proximity configuration.') > 0) {
					$('.errorMsg').text("Error Processing the Save proximity configuration.");
					$('#PopupError').show();
				}
				hideLoading();
			});
		}
	});
	
	setMaxVericalLineHeight();
});

function getContextRoot() {
	return window.sessionStorage.getItem('baseAppUrl');
}

/**
 * This method is used to identify the height of each tier to modify the vertical line height.
 * This has been invoked on page load and 
 * on-click of expanding filter icon (from jquery.accordion-live-filter.js) at each area level under each tier.
 * 
 * Parameter "obj" is passed while invoking this method on-click of expanding filter icon
 */
function setMaxVericalLineHeight(obj) {
	var defaultVerticalLineHeight = 450;
	var maxVerticalLineHeight = defaultVerticalLineHeight;
	var tier1_div_height, tier2_div_height, tier3_div_height;

	if ($("#tier1-div") && $("#tier1-div").length > 0
		&& $("#tier2-div") && $("#tier2-div").length > 0
		&& $("#tier3-div") && $("#tier3-div").length > 0) {
		tier1_div_height = $("#tier1-header").height() + $("#tier1-filter-div").height();
		tier2_div_height = $("#tier2-header").height() + $("#tier2-filter-div").height();
		tier3_div_height = $("#tier3-header").height() + $("#tier3-filter-div").height();

		maxVerticalLineHeight = Math.max(tier1_div_height, tier2_div_height, tier3_div_height, defaultVerticalLineHeight);
	}

	if(obj != null && obj != undefined) {
		var current_tier_div_height = $(obj).closest("div .tier-filter-div").prev("div .tier-header").height() + $(obj).closest("div .tier-filter-div").height();
		var expanded_accordion_height = $(obj).height() + $(obj).next("ul").height(); //label + ul heights
		
		if($(obj).hasClass("expanded")) {
			var tempVerticalLineHeight = current_tier_div_height + expanded_accordion_height; 
			maxVerticalLineHeight = Math.max(maxVerticalLineHeight, tempVerticalLineHeight);
		} else {
			var tempVerticalLineHeight = current_tier_div_height - expanded_accordion_height;

			var current_tier_div_id =  $(obj).closest("div .tier-div").attr("id");
			var tier_number = current_tier_div_id.split("-div")[0].split("tier")[1];

			switch (tier_number) {
			case "1":
				maxVerticalLineHeight = Math.max(tempVerticalLineHeight, tier2_div_height, tier3_div_height);
				break;
			case "2":
				maxVerticalLineHeight = Math.max(tempVerticalLineHeight, tier1_div_height, tier3_div_height);
				break;
			case "3":
				maxVerticalLineHeight = Math.max(tempVerticalLineHeight, tier1_div_height, tier2_div_height);
				break;
			}
		}
	}

	if (maxVerticalLineHeight > defaultVerticalLineHeight) {
		$(".vertical-line, #tier3-div").height(maxVerticalLineHeight + 20 + "px");
	} else {
		$(".vertical-line, #tier3-div").height(defaultVerticalLineHeight + 20 + "px");
	}
}
