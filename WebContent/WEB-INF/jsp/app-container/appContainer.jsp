<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<%@ include file="includes/head-block.jspf"%>
	</head>
</head>
<body style="margin:4px;" >
	<%@ include file="includes/header.jsp"%>
	
	<div class="overlayadmin" style="visibility:hidden;"></div>
  	<div class="processingImg" style="visibility:hidden;">
  	    <div><img src="${commonStaticUrl}/images/spinner-big.gif"/></div>
  	    <div id="processingText"></div>
  	</div>
	<div>
		<%@ include file="includes/navigation.jspf"%>
	</div> 
	<div style="position:relative;top:-4px;">
		<iframe id="mainFrame" src="" style='width: 100%; height: 700px;'  id="iframe" onload="resizeIframe(this);"   >Frames Are Not Supported In Your Browser.</iframe>
	</div>
	<%@ include file="includes/footer.jspf"%>
</body>
</html>