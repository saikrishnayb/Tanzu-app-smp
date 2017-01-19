var $commonTreeContainer = $('.common-tree-container');


// Listeners *************
$commonTreeContainer.on('click', '.common-tree-header .caret', function() {
  
  this.classList.toggle('flip-right');
  
  var $treeContainer = $(this).closest('.common-tree-container')
  var $treeContent = $treeContainer.find('.common-tree-content');
  
  var isClosed = this.classList.contains('flip-right');
  if(isClosed) {
    $treeContent.addClass('hidden');
    return true;
  }
  
  $treeContent.removeClass('hidden');
  
  var alreadyLoaded = $treeContent.hasClass('loaded');
  if(alreadyLoaded) return true;
  
  var organizationId = $treeContainer.data('organization-id');
  
  showLoading();
  
  var $getOrganizationVendorsPromise = $.get(getContextRoot() + "/userController/get-organization-vendor-filters.htm", {organizationId: organizationId});
  
  $getOrganizationVendorsPromise.done(function(vendorFilters) {
    
    $.each(vendorFilters, function() {

      var labelMarkup = '<label> <input type="checkbox" class="vendor-input" value="' + this.vendorId + '"/> ' +
                            this.vendorNumber + ' - ' +  this.vendorCorp + this.formattedAddress +
                        '</label>';
      
      $treeContent.append(labelMarkup);
      
    });
    
    $treeContent.addClass('loaded');
    
  }).always(function() {
    hideLoading();
  });
  
  
});

$commonTreeContainer.on('click', '.org-input', function() {
  var isChecked = this.checked;
  $(this).closest(".common-tree-container").find('.vendor-input').prop('checked', isChecked);
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
  
  var $saveUserVendorSelectionsPromise = $.post(getContextRoot() + "/userController/save-user-vendor-selections.htm", {vendorIds: selectedVendorIds.join()});
  
  $saveUserVendorSelectionsPromise.done(function() {
    hideLoading();
    ModalUtil.closeModal($('.modal-utility'))
  });
  
});

//# sourceURL=vendor-filter.js