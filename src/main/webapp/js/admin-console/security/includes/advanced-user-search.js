var $searchButtonsContainer = $('#search-buttons-div');
// Advanced Search Fields
var $searchFName = $('#search-first-name');
var $searchLName = $('#search-last-name');
var $searchRole = $('#search-role');
var $searchEmail = $('#search-email');
var $searchuserType = $('#search-user-type');
var $searchUserForm = $('#search-user-form');
var $inputs = $('#search-first-name','#search-last-name','#search-role','#search-email');

$searchButtonsContainer.on('click', '.reset', function(){
	$searchEmail.val('');
	$searchFName.val('');
	$searchLName.val('');
	$searchRole.val('');
	$searchuserType.children().removeAttr("selected");
	$searchuserType.find('.default-option').attr('selected', 'selected');
	
	$searchEmail.removeClass('errorMsgInput');
	$searchFName.removeClass('errorMsgInput');
	$searchLName.removeClass('errorMsgInput');
	$searchRole.removeClass('errorMsgInput');
	$searchuserType.removeClass('errorMsgInput');
	$('.error-messages-container').addClass('displayNone');
	$('#search-org').multiselect("widget").find(':checkbox').removeAttr('checked');
	$('#search-org').val([]);
	$('#search-org').multiselect("widget").find(':checkbox').removeAttr('aria-selected');
	$('#search-org').multiselect('update');
});
	
//search for user accounts
$searchButtonsContainer.on('click', '.usersSearch', function(){
	var  $searchForm = $('#search-user-form');
	$searchForm.submit();
});
	
$('#search-user-form').on('keypress', function(e) {
	var $searchUserForm = $('#search-user-form');
	//Show the advanced search form if the user had just used it.
	if( ($searchUserForm.find('[name="firstName"]').val().length > 0) || ($searchUserForm.find('[name="lastName"]').val().length > 0) ||
	($searchUserForm.find('[name="email"]').val().length > 0) || ($searchUserForm.find('[name="roleId"]').val().length > 0)){
			if (e.which == 13) {
				$searchButtonsContainer.find('.search').trigger('click');
				event.preventDefault();
			}
	}
});
		
//search for vendor user accounts
$searchButtonsContainer.on('click', '.vendorUsersSearch', function(){
	var  $searchForm = $('#search-vendor-user-form');
	getVendorUserTableContents($searchForm.serialize())
});
	

$('#search-vendor-user-form').on('keypress', function(e) {
	var $searchUserForm = $('#search-vendor-user-form');
	//Show the advanced search form if the user had just used it.
	if( ($searchUserForm.find('[name="firstName"]').val().length > 0) || ($searchUserForm.find('[name="lastName"]').val().length > 0) ||
	($searchUserForm.find('[name="email"]').val().length > 0) || ($searchUserForm.find('[name="roleId"]').val().length > 0) ||
	($('#search-vendor-user-form').find('[name=orgIds]').multiselect("widget").find(':checkbox:checked').length > 0)){
			if (e.which == 13) {
				$searchButtonsContainer.find('.vendorUsersSearch').trigger('click');
				event.preventDefault();
			}
		
	}
});
	
if($("#search-content").is(":visible")){
	if($("#advancedSearch").is(":visible")){
		//Currently Expanded
		$("#advancedSearch").text('Hide Search Criteria');
	}
}
else{
	if($("#advancedSearch").is(":hidden")){
		//Currently Collapsed
		$("#advancedSearch").text('Show Search Criteria');
	}
}

//# sourceURL=advanced-user-search.js