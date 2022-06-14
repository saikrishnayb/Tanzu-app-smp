var $commonTreeContainer = $('.common-tree-container');


// Listeners *************
$commonTreeContainer.on('click', '.common-tree-header .caret', function() {
  
  this.classList.toggle('flip-right');
  
  var $treeContainer = $(this).closest('.common-tree-container')
  var $treeHeader = $treeContainer.find('.common-tree-header');
  var $treeContent = $treeContainer.find('.common-tree-content');
  
  $treeHeader.toggleClass('closed');
  
  var isClosed = $treeHeader.hasClass('closed');
  if(isClosed) return true;
  
  var alreadyLoaded = $treeContent.hasClass('loaded');
  if(alreadyLoaded) return true;
  
  var organizationId = $treeContainer.data('organization-id');
  
  loadTreeContentData($treeContent, organizationId);
 
});

$commonTreeContainer.on('click', '.org-input', function() {
  
  var isChecked = this.checked;
  
  var $commonTreeContent = $(this).closest(".common-tree-container").find('.common-tree-content');
  var isNotLoaded = !$commonTreeContent.hasClass('loaded');
  
  var selectAllChildren = function ($commonTreeContent) {
    $commonTreeContent.find('.vendor-input').prop('checked', isChecked);
  }
  
  if(isNotLoaded) 
    loadTreeContentData($commonTreeContent, this.value, selectAllChildren);
  else
    selectAllChildren($commonTreeContent);
  
});

$commonTreeContainer.on('click', '.vendor-input', function() {
  
  var $closestTreeContent = $(this).closest(".common-tree-content");
  
  var allChecked = true;
  
  $closestTreeContent.find('.vendor-input').each(function() {
    
    var isNotChecked = !this.checked;
    
    if(isNotChecked) {
      allChecked = false;
      return false;
    }
    
  });
  
  $closestTreeContent.prev('.common-tree-header').find('.org-input').prop('checked', allChecked);
  
});

$('.btn-save-vendor-filter').on('click', function() {
  
  var selectedVendorIds = []
  
  $commonTreeContainer.find('.vendor-input:checked').each(function() {
    selectedVendorIds.push(this.value);
  });
  
  showLoading();
  
  var $saveUserVendorSelectionsPromise = $.post(getContextRoot() + "/userController/save-user-vendor-selections", {vendorIds: selectedVendorIds.join()});
  
  $saveUserVendorSelectionsPromise.done(function(data) {
	  //commented out so that the loading spinner stays visible until page refresh
	  //hideLoading();
    ModalUtil.closeModal($('.modal-utility'));
    
    if(data === true)
      $(".vendor-filter-toggle-container").removeClass('hidden off').addClass('on');
    else
      $(".vendor-filter-toggle-container").addClass('hidden');
    
	var currentTab = $('nav ul li.current a', parent.document).attr('id');
	if (currentTab == 'Home') {
		hideLoading();
		$('#mainFrame').contents().find('div.selected-tab').click();
	} else {
		//refreshes the page, without "resend data" warning
		document.querySelector('#mainFrame').contentWindow.location.href = document.querySelector('#mainFrame').contentWindow.location.href 
	}
  });
});

$('.btn-clear-vendors').on('click', function() {
  
  $commonTreeContainer.find('.vendor-input, .org-input').prop('checked', false);
  
});

$('#vendor-filter-fuzzy-search').on('keyup', function(){
	var $searchBox = $(this);
	var query = $searchBox.val().trim().toUpperCase();
	var queryArray = query.split(' ');
	
	$('.common-tree-container').each(function() {
		var $container = $(this);
		
		if(query == '') {
			$container.show();
		}
		else {
			var orgName = $container.find('label .org-name').text().trim();
			var orgNameArray = orgName.split(' ');
			
			var matches = true;
			queryArray.forEach(function(queryItem){
				if(!orgNameArray.includes(queryItem))
					matches = false;
			});
			
			if(matches)
				$container.show();
			else
				$container.hide();
		}
	});
	
});

$('#vendor-filter-fuzzy-search-clear').on('click', function(){
	$('#vendor-filter-fuzzy-search').val('');
	$('#vendor-filter-fuzzy-search').trigger('keyup');
});


// Helper Function ************************************************************/

/**
 * Kinda weird, but take the gicing tree content container and loads it with the vendors from the organization id passed in.
 * The weird part is it takes an optional funtion that if provided, will be be executed after the ajax completes with the 
 * tree content container passed in.
 */
function loadTreeContentData($treeContent, organizationId, optionalFunction) {
  
  showLoading();
  
  var $getOrganizationVendorsPromise = $.get(getContextRoot() + "/userController/get-organization-vendor-filters", {organizationId: organizationId});
  
  $getOrganizationVendorsPromise.done(function(vendorFilters) {
    
    $.each(vendorFilters, function() {
  
      var labelMarkup = '<label> <input type="checkbox" class="vendor-input" value="' + this.vendorId + '"/> ' +
                            this.vendorNumber + ' - ' +  this.vendorCorp + this.formattedAddress +
                        '</label>';
      
      $treeContent.append(labelMarkup);
      
    });
    
    $treeContent.addClass('loaded');
    
    if(optionalFunction != undefined) optionalFunction($treeContent);
    
  }).always(function() {
    hideLoading();
  });
}

//# sourceURL=vendor-filter.js