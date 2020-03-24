$(document).ready(function() {
	var times = [
		"Body Plant Exceptions",
		"Proximity Configuration",
		"Proximity Configuration"

	];
	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-maintenance-summary");
	$table = $('#body-plant-maint-table').dataTable({ //All of the below are optional
		"bPaginate" : true, //enable pagination
		"bStateSave" : true, //To retrieve the data on click of back button
		"sPaginationType" : "two_button",
		"aaSorting" : [], //default sort column
		"aoColumnDefs" : [ {
			'bSortable' : false,
			'aTargets' : [ 0 ]
		} ],
		"aocolumn" : [
			{
				"render" : function(d, t, r) {
					var $select = $("<select></select>", {
						"id" : r[0] + "start",
						"value" : d
					});
					$.each(times, function(k, v) {
						var $option = $("<option></option>", {
							"text" : v,
							"value" : v
						});
						if (d === v) {
							$option.attr("selected", "selected")
						}
						$select.append($option);
					});
					return $select.prop("outerHTML");
				}
			}
			, null, null, null, null, null, null
		],
		"bLengthChange" : true, //enable change of records per page, not recommended
		"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bAutoWidth" : false,
		"bSort" : true, //Allow sorting by column header
		"bInfo" : true, //Showing 1 to 10 of 11 entries
		"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength" : 100, //number of records per page for pagination
		"oLanguage" : {
			"sEmptyTable" : "No Results Found"
		},
		//"sScrollY": 246, //Adds a vertical scroll bar if the content exceeds this amount
		//"sScrollXInner": "100%" 
		"fnDrawCallback" : function() { //This will hide the pagination menu if we only have 1 page.
			var paginateRow = $(this).parent().children('div.dataTables_paginate');
			var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

			if (pageCount > 1) {
				paginateRow.css("display", "block");
			} else {
				paginateRow.css("display", "none");
			}
			//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
			var infoRow = $(this).parent().children('div.dataTables_info');
			var rowCount = this.fnSettings().fnRecordsDisplay();
			if (rowCount > 0) {
				infoRow.css("display", "block");
			} else {
				infoRow.css("display", "none");
			}
		}
	});

});