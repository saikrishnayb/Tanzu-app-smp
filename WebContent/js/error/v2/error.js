/* Cached Selectors ****************************************************/
var $rowException = $('.row-exception');

/*  Listeners **********************************************************/
$('.show-error').on('click', function() {
	
	$rowException.toggleClass('hidden');
	
	var isHidden = $rowException.hasClass('hidden');
	
	$('.show-error').text(isHidden ? 'Show Error Details' : 'Hide Error Details');
	
});

$('.goto-main-page').on('click', function() {
	if(window.parent) {
		sessionStorage.setItem('ignoreBeforeUnload', "true");
		$('nav a#Home', parent.document).mousedown();
	}
});