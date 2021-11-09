<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="includes/head-block.jspf"%>
  </head>
  <body id="app-container-body">
    <%@ include file="includes/header.jsp"%>
  
    <div class="overlayadmin" style="visibility: hidden;"></div>
    <div class="processingImg" style="visibility: hidden;">
      <div>
        <img src="${commonStaticUrl}/images/spinner-big.gif" />
      </div>
      <div id="processingText"></div>
    </div>
    
    <div>
      <%@ include file="includes/navigation.jspf"%>
    </div>
    
    <div class="iframe-container" style="position: relative;">
      <iframe id="mainFrame" src="" style='width: 100%; height: 700px;' id="iframe" onload="iframeLoaded();">
        Frames Are Not Supported In Your Browser.
      </iframe>
    </div>
    
    <%-- Status dialog - This is manipulated with PageUtil.showOverlay() and PageUtil.hideOverlay() within the child frame --%>
	<div id="status-dialog" class="dialog ui-helper-hidden text-center">
		<div class="dialog-content" data-dialog-no-close data-dialog-width="300">
			<div class="status-dialog-spinner spinner"></div>
			<div class="update-message text-center"></div>
		</div>
	</div>
	
	<%-- Semi-transparent Ajax loading overlay - Manipulate this with PageUtil.showOverlay() and PageUtil.hideOverlay() within the child frame --%>
	<div id="ajax-loading-overlay" class="overlay spinner ui-helper-hidden"></div>
    
    <%@ include file="includes/footer.jspf"%>
    <script src="${baseUrl}/js/app-container/includes/iframe-resizer.js"></script>
  </body>
</html>