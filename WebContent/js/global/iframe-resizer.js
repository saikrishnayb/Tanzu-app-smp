var iframeResizer = (function() {
  
  //Polyfill for <= ie11
  if (Number.parseInt === undefined) Number.parseInt = window.parseInt;
  
  let _iframeNode = window.parent.document.querySelector('#mainFrame');
  let _iframeWindow = window.parent.window.frames[0];
  let _resizeIframeTimeoutID = null;
  let _problemClassRegex = /\s?ui-state-hover/gm;
  let _problemTargetIds = ['createRule-Table_wrapper'];
  let _problemPreviousSiblings = ['missing-info-table'];
  let _observer = null;
  
  /**
   * Gets the height of the given element by adding up the top and bottom margins as well as the element hieght itself
   */
  let _getElementHeight = function getElementHeight(element) {
    let marginTop = Number.parseInt(window.getComputedStyle(element).marginTop.replace('px', ''));
    let marginBottom = Number.parseInt(window.getComputedStyle(element).marginBottom.replace('px', ''));
    let clientHeight = element.getBoundingClientRect().height;
    
    return clientHeight + marginTop + marginBottom;
  };
  
  /**
   * Changes the size of the parent iframe's minHeight and height to at minimum match the users screen height 
   * and if the content is taller than the minimum to match that instead.
   */
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
      
      //console.log('iframe resized');
      
    }, 100);
    
  };
  
  /**
   * Starts the mutation observer listener and the window resize listeners to call the
   * resizeIframe function in an event of a browser resize change, or a page content change. If a user navigates to a
   * different page, it calls the stopResizeListener function.
   * 
   * If the mutation observer is already started, it throws an exception.
   */
  let initateResizeListener = function initateResizeListener() {
    
    if(_observer !== null) throw 'ResizeListener already initialized'; 
    
    let targetNode = _iframeWindow.document.body;
    let config = {
      attributeFilter : ["class"],
      attributeOldValue : true,
      attributes : true,
      characterData : true,
      childList : true,
      subtree : true
    };
    
    _observer = new MutationObserver(function(mutationsList, observer) {
      
      //Guilty till proven innocent
      let isAllProblemChildClasses = true; 
      let isAllProblemChildTargets = true;
      
      mutationsList.every(function(mutationRecord) {
        
        //For jquery ui hover
        if(mutationRecord.attributeName === 'class') {
          
          let oldClassList = mutationRecord.oldValue === null? '' : mutationRecord.oldValue;
          let newClassList = mutationRecord.target.classList.toString();
          
          let notJustUiStateHoverChange = oldClassList.replace(_problemClassRegex, '') !==
                                          newClassList.replace(_problemClassRegex, '');
          
          if(notJustUiStateHoverChange) 
            isAllProblemChildClasses = false;
        } 
        //For wonky Loadsheet Rules page TODO please fix and remove
        else if(mutationRecord.type === 'childList') {
          
          let targetId = mutationsList[0].target.id;
          let previousSiblingId = mutationsList[0].previousSibling.id;
          
          let noProblemTargets = _problemTargetIds.indexOf(targetId) === -1;
          let noProblemPrevSiblings = _problemPreviousSiblings.indexOf(previousSiblingId) === -1;
          
          if(noProblemTargets && noProblemPrevSiblings) 
            isAllProblemChildTargets = false;
          
        } else{
          isAllProblemChildClasses = false;
          isAllProblemChildTargets = false;
        } 
        
      });
      
      if(!isAllProblemChildClasses || !isAllProblemChildTargets) resizeIframe();
      
    });
    
    _observer.observe(targetNode, config);
    
    _iframeWindow.addEventListener('unload', function(event) {
      stopResizeListener();
    });
    
    window.parent.addEventListener('resize', resizeIframe, false );
    
  };
  
  /**
   * Function that halts the mutation observer to prevent any further events from being captured. If no mutation
   * observer is started, it throws an exception
   */
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