var jroleHierarchy;
var jeditVendor;
function createVendorTree(){
	jroleHierarchy=j('#vendor-hierarchy');
	jroleHierarchy.jstree({
		  "core" : {"animation" : 75},
			  "themes" : {"theme" : false,"dots"  : false, "icons":false},
			  "plugins" : [  "wholerow", "checkbox"],
			  "checkbox" : {
			    "keep_selected_style" : false,
			    "three_state": true 
			  }
			
	  });
	$("#loading").hide();
}
function editVendorTree(){
	jeditVendor = j('#edit-vendor-hierarchy');
	jeditVendor.jstree({
		  "core" : {"animation" : 75
		  },
			  "themes" : {"theme" : false,"dots"  : false, "icons":false},
			  "plugins" : [  "wholerow", "checkbox"],
			  "checkbox" : {
			    "keep_selected_style" : false,
			    "three_state": true 
			  }
			
	  });
	$("#loading").hide();
}

