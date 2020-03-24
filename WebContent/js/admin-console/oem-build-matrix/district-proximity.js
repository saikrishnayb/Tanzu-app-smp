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

});

function getContextRoot() {
	return window.sessionStorage.getItem('baseAppUrl');
}

