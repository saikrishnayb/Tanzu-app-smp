var LoadingUtil = (function() {
  
  var _loadingOverlay = null;
  
  _initialize();
  
  var showLoadingOverlay = function(isAjax, isHighPriority) {
    _loadingOverlay.classList.add(isAjax? 'ajax-loading': 'page-loading');
    if(isHighPriority)
      _loadingOverlay.classList.add('high-priority');
  };
  
  var hideLoadingOverlay = function(highPriority) {
    
    var loadingOverlayIsInHighPriority = _loadingOverlay.classList.contains('high-priority');
    
    if(highPriority === true || !loadingOverlayIsInHighPriority) {
      _loadingOverlay.classList.remove('ajax-loading');
      _loadingOverlay.classList.remove('page-loading');
      _loadingOverlay.classList.remove('high-priority');
    }
      
  };

  //Private Methods ************************************************************
  function _initialize() {
    _loadingOverlay = document.querySelector('#loading-overlay');
    
    if(_loadingOverlay === null)
      throw 'No #loading-overlay was found on the page';
  };

  return {
    showLoadingOverlay: showLoadingOverlay,
    hideLoadingOverlay: hideLoadingOverlay,
  };

})();