var iframeResizer = (function() {
  
  let _iframeNode = window.parent.document.querySelector('#mainFrame');
  let _iframeWindow = window.parent.window.frames[0];
  let _resizeIframeTimeoutID = null;
  let _observer = null;
  
  let _getElementHeight = function getElementHeight(element) {
    let marginTop = Number.parseInt(window.getComputedStyle(element).marginTop.replace('px', ''));
    let marginBottom = Number.parseInt(window.getComputedStyle(element).marginBottom.replace('px', ''));
    let clientHeight = element.getBoundingClientRect().height;
    
    return clientHeight + marginTop + marginBottom;
  };
  
  let resizeIframe = function resizeIframe() {
    
    parent.window.clearTimeout(_resizeIframeTimeoutID);
    
    _resizeIframeTimeoutID = parent.window.setTimeout(function() {
      
      let pageYOffset = window.parent.pageYOffset;
      
      _iframeNode.style.height = '0px';
      _iframeNode.style.minHeight = '0px';
      
      let iframeBody = _iframeWindow.document.body;
      
      let iframeBodyHeight = iframeBody.scrollHeight;
      let minHeightSubtract =   _getElementHeight(window.parent.document.querySelector('#title'))
                              + _getElementHeight(window.parent.document.querySelector('#footer'))
                              + _getElementHeight(window.parent.document.querySelector('body > div > nav'))
                              + _getElementHeight(window.parent.document.querySelector('.iframe-container'));
      
      _iframeNode.style.minHeight = 'calc(100vh - ' + minHeightSubtract + 'px)';
      _iframeNode.style.height = iframeBodyHeight + 'px';
      
      
      //Not sure why scrollHeight does not give accurate results. Do this for now //TODO
      let newIframeBodyHeight = iframeBody.scrollHeight;
      if(newIframeBodyHeight > 10 + iframeBodyHeight ) _iframeNode.style.height = newIframeBodyHeight + 'px';
      
      window.parent.scrollTo(0, pageYOffset);
      
      console.log('iframe resized');
      
    }, 100);
    
  };
  
  let initateResizeListener = function initateResizeListener() {
    
    if(_observer !== null) throw 'ResizeListener already initialized'; 
    
    let targetNode = _iframeWindow.document.body;
    let config = {attributes: true, characterData: true,childList: true, subtree: true};
    
    _observer = new MutationObserver(function(mutationsList, observer) {
      resizeIframe();
    });
    
    _observer.observe(targetNode, config);
    
    _iframeWindow.addEventListener('unload', function(event) {
      stopResizeListener();
    });
    
    window.parent.addEventListener('resize', resizeIframe, false );
    
  };
  
  let stopResizeListener = function stopResizeListener() {
    
    if(_observer === null) 
      throw 'ResizeListener never initialized';
    
    _observer.disconnect();
    _observer = null;
    
  };
  
  return {
    initateResizeListener: initateResizeListener,
    stopResizeListener: stopResizeListener,
    resizeIframe: resizeIframe
  }
  
})();