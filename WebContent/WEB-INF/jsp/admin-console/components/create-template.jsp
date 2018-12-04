<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    <link href="${baseUrl}/css/admin-console/components/create-template.css" rel="stylesheet" type="text/css"/> 
	 	 <script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		 <link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
				 	<div id="jstree-mfr-div">
					 	<c:forEach items="${manufacture}" var="vendor">
						 	<ul >
						 	<li id="mfr-corp" class="mfr">
								<input type="checkbox" class="parent-mfr" value="${vendor.manufacture}"><a>	${vendor.manufacture}</a>
							<ul>
							  <c:forEach items="${vendor.corpCodes}"  var="corp">
									<li id="mfr-corp" class="corp">
									  <input type="checkbox" class="parent-corp" value="${corp.corpCode}"><a>	${corp.corpCode}</a>
										<ul>
										<c:forEach items="${corp.vendorLocation }" var="location">
										
											<li id="phtml_2" class="vendor">
											<input type="checkbox" class="child-vendor" value="${location.vendorNumber}" name="vendor-number"><a>		${location.manufacturer}-${location.state}Area-${location.vendorNumber}</a>
											</li>
										</c:forEach>
										</ul>
									</li>
								</c:forEach>
							</ul>
							</li>
							</ul>
						</c:forEach>
				</div>
				<div id="add-category-link">
					<span class="floatRight addRow add-category">
							<a >Add Category</a>
							<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor add-row-plus" alt="Add Row"/>
						</span>
					</div>
					<div class="po-category-accordion-container" id="po-category-accordion-container"></div> 
					 <div class="save-btn">
					 	<a href="#" class="secondaryLink cancel-back" tabIndex="-1">No, Cancel</a>
						<a class="buttonPrimary save-template" href="#">Save</a>
					</div> 
					<div class="checkVendor" id="errorMessage">
						<img src="${commonStaticUrl}/images/warning.png" /> <span
							class="errorMsg"></span>
					</div>
					<%@ include file="../../../jsp/jsp-fragment/admin-console/components/delete-add-category-modal.jsp" %>
					</div>
			</div>
	</body>
  <!-- Scripts -->
  <script src="${commonStaticUrl}/js/jquery.jstree.js" type="text/javascript"></script>
  <script src="${baseUrl}/js/admin-console/components/create-template.js" type="text/javascript"></script>
</html>