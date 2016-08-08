<jsp:include page="includes/header.jspf" />
 </head>
 <body style="margin:4px;" >
	

	<%-- <input id="staticUrlHidden" type="hidden" value="${staticurl}"> --%>
  
  <!-- 	<div id="dealMgmtFunctionalityHeader">
		<div id="functionalityHeader"></div>
	  	<div id="demo1" class="demo">
	  				<ul><li id="phtml_1">
	  				   <a href="#"><div id="functionalityContent" style="width:98%;position:absolute;margin-top: -15px;"></div><input type="hidden" id="titleHiddenId"/><input type="hidden" id="titleIdHidden"/><input type="hidden" id="pageNameHiddenId"/><input type="hidden" id="dealSelectedLenId" value="0"/><input type="hidden" id="dealItemSelectedLenId" value="0"/><input type="hidden" id="dealHasSpecId"/><input type="hidden" id="dealHasRateId"/><input type="hidden" id="migratedDealId" value="No"/><input id="dealValiStsId" type="hidden"/><input id="dealItmsForValidationId" type="hidden"/><input id="mainContactForValidationId" type="hidden"/><input type="hidden" id="cntryCdId"/><input type="hidden" id="prodLineId"/><input type="hidden" id="cdVLSAVMAId"/><input type="hidden" id="incumId"/><input type="hidden" id="pageNumDealTabId" value="0"/><input type="hidden" id="stageDealTabId"/></a>
		  				<ul id="dealItemHeaderContainer">
						  	<li id="phtml_2"><a href="#"></a></li>
		  	            </ul>
		  	          </li></ul>
		</div>
		<div style="padding-top: 0.8%"><hr noshade="noshade"/></div> -->
	<!-- </div>	 -->
	<div class="overlayadmin" style="visibility:hidden;"></div>
  	<div class="processingImg" style="visibility:hidden;">
  	        <div><img src="${commonStaticUrl}/images/spinner-big.gif"/></div>
  	        <div id="processingText"></div>
  	</div>
	
  	

<div><jsp:include page="includes/navigation.jspf"/></div> 
<div style="position:relative;top:-4px;"><iframe id="mainFrame" src="" style='width: 100%; height: 700px;'  id="iframe" onload="resizeIframe(this);"   >Frames Are Not Supported In Your Browser.</iframe></div>
<jsp:include page="includes/footer.jspf"/>

	