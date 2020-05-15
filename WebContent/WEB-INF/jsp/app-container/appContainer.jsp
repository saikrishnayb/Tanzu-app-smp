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
    
    <%@ include file="includes/footer.jspf"%>
    <script src="${baseUrl}/js/app-container/includes/iframe-resizer.js"></script>
  </body>
</html>