var ModalUtil = new function() {
  
  this.initializeModal = function($modalSelector, width, position) {

    width = width ? width : "auto";
    position = position ? position : { my: "center", at: "center", of: window, collision: "fit"};
    
    $modalSelector.dialog({
      autoOpen: false,
      modal: true,
      dialogClass: 'popupModal',
      width: width,
      resizable: false,
      closeOnEscape: true,
      close: function() {
        var emptyContents = !this.querySelector('.modal-content').hasAttribute('data-perserve-contents');
        if(emptyContents)
          $(this).children().detach().remove();
      },
      position: position
    });
    
  };
  
  this.closeModal = function closeModal($modals) {
    
    //Close child modals first
    $modals.filter('.modal-child').each(function() {
      $(this).dialog('close');
    });
    
    $modals.not('.modal-child').each(function() {
      $(this).dialog('close');
    });
    
  };
  
  this.openModal = function openModal($modal) {
    
    var $modalContent      = $modal.find('.modal-content');
    
    var modalTitle         = $modalContent.data('modal-title');
    var modalWidth         = $modalContent.data('modal-width');
    var modalMaxWidth      = $modalContent.data('modal-max-width');
    var modalMinHeight     = $modalContent.data('modal-min-height');
    var modalMaxHeight     = $modalContent.data('modal-max-height');
    
    var hasModalTitle      = modalTitle !== undefined;
    var hasModalWidth      = modalWidth !== undefined;
    var hasModalMaxWidth   = modalMaxWidth !== undefined;
    var hasModalMinHeight  = modalMinHeight !== undefined;
    var hasModalMaxHeight  = modalMaxHeight !== undefined;
    
    var hasDataTable = $modalContent.data('contains-data-table') !== undefined;
    
    if(hasModalTitle) $modal.dialog("option", "title", modalTitle);
    if(hasModalWidth) $modal.dialog("option", "width", modalWidth);
    if(hasModalMaxWidth) $modal.dialog("option", "width", modalMaxWidth); //Dumb hack since maxWidth seems to not work with "auto" width
    if(hasModalMinHeight) $modal.dialog("option", "minHeight", modalMinHeight);
    if(hasModalMaxHeight) $modal.dialog("option", "height", modalMaxHeight);
    
    
    $modal.addClass('loading').find('.modal-content').addClass('content-loading').css('visibility',  'hidden'); 
    
    $modal.dialog('open');
    
    window.setTimeout(function() { //We need to a window timeout with 0 as a hack to make sure any css gets loaded and processed by the browser
      
      $modal.removeClass('loading').find('.modal-content').removeClass('content-loading').css('visibility',  'visible');
      $modal.dialog("option", "position", $modal.dialog( "option", "position"));
      
      if(hasDataTable)
        
        $modal.find('table').DataTable().columns.adjust();
        //$.fn.dataTable.tables({ visible: true, api: true }).columns.adjust();
      
    }, 0);
    
  };
  
  this.openChildModal = function openChildModal($childModal) {

    $globalModal = $globalModal.add($childModal);

    $globalModal.off('click', '.modal-close');
    modalCloseListener();
    
    this.openModal($childModal);
  
  };
  
  this.getTopMostOpenedModal = function() {
    
    var openModals =  [];
    
    $('.modal').each(function() {
      
      var $this = $(this);
      
      var isOpen = $this.dialog("isOpen");
      if(isOpen) openModals.push($this);
      
    });
    
    var noModals = openModals.length === 0;
    if(noModals) return $();
    
    var $lastModal = openModals[0];
    
    for(var i = 1; i < openModals.length; i++) {
      
      var $modal = openModals[i];
      
      var lastZIndex = $lastModal.closest('.ui-dialog')[0].style.zIndex;
      var thisZIndex = $modal.closest('.ui-dialog')[0].style.zIndex;
      
      var isBiggerZIndex = thisZIndex > lastZIndex;
      
      if(isBiggerZIndex) $lastModal = $modal;
      
    }
    
    return $lastModal;
    
  };
};