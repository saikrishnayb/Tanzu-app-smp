<jsp:include page="includes/header.jspf" />
 </head>
 <body style="margin:4px;" >
	

	<%-- <input id="staticUrlHidden" type="hidden" value="${staticurl}"> --%>

	<div class="overlayadmin" style="visibility:hidden;"></div>
  	<div class="processingImg" style="visibility:hidden;">
  	        <div><img src="${commonStaticUrl}/images/spinner-big.gif"/></div>
  	        <div id="processingText"></div>
  	</div>
	
  	

<div><jsp:include page="includes/navigation.jspf"/></div> 
<div style="position:relative;top:-4px;"><iframe id="mainFrame" src="" style='width: 100%; height: 700px;'  id="iframe" onload="resizeIframe(this);"   >Frames Are Not Supported In Your Browser.</iframe></div>
<jsp:include page="includes/footer.jspf"/>

	