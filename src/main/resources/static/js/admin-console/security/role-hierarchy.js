$(document).ready(function() {
	$roleHierarchy.unbind();
	
	/* --------------- Declare the jstree. --------------- */
	$roleHierarchy.jstree({"core"   : { "animation" : 0,
										"initially_open" : [ "role-hierarchy-tree" ]},
						   "themes" : { "dots"  : false  }});
	
	$('span.role').each(function() {
		var $span = $(this);
		var roleId = $span.attr('id');
		
		if ($('span.role').length > 1) {
			if (roleId != $('[name="base-role-id"]').val() && roleId != $('[name="roleId"]').val()) {
				$span.removeClass('selected-role');
			}
		}
	});
});

//# sourceURL=role-hierarchy.js