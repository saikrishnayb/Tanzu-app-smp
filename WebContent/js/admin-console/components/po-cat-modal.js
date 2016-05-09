$(document).ready(function(){
	
	
	$('.cancel').click(function(){
		var $this = $(this);
		var $closestModal = $this.closest(".modal");
		$closestModal.dialog('close').empty();
	});
});