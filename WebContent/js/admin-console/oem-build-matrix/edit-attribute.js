$("#values").multiselect({
	close : function() {},
	minWidth : 150,
	noneSelectedText : "",
	open : function() {
		//Code added to increase the width of drop down content
		$(".ui-multiselect-menu ").css('width', '169px');
	}
});
$(".ui-multiselect-menu ").css('width', '169px');