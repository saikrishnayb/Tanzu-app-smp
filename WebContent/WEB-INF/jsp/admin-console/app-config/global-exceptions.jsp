<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Global Exceptions</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    	<!-- CSS -->
	    <link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/admin-console/app-config/global-exceptions.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
			<form id="search-exception-form" action="exception-search.htm" method="GET">
				<div class="customSearch" style="margin-bottom: 5px">
					<span id="advancedSearch" class="expandableContent handCursor collapsedImage
						 floatRight margin-bottom unitPoSearch"
						onclick="toggleContent('search-content','advancedSearch');">Show Advanced Search
					</span>
					<div class="searchAlignText">
						<label>Vendor Name:</label>
						<input name="vendorNameSearch" id="vendorNameSearch" value="<c:out value="${searchedData.vendorNameSearch}"/>"  tabindex="2"></input>
					</div>
					<div class="searchAlignText">
						<label>Component Name:</label>
						<input name="componentNameSearch" id="componentNameSearch" value="<c:out value="${searchedData.componentNameSearch}"/>" tabindex="1"></input>
					</div>
				</div>
				<div id="search-content" class="customSearch  displayNone" >
					<div  class="searchAlignNumber">
						<a class="buttonSecondary  reset"  href="#" tabindex="5">Clear</a>
						<a class="buttonSecondary edit-alert" id="searchException" tabindex="6">Search</a>
						<div class="basicValidation displayNone">
							<img src="${commonStaticUrl}/images/warning.png"></img>
							<span class=errorMsg>Please enter any one/valid search criteria</span>
						</div>
					</div>
					<div class="searchPONumber" >
						<label>PO #:</label>
						<input  name="poNumberSearch" id="poSearch" 
						<c:if test="${searchedData.poNumberSearch ne 0}">	
						value="<c:out value="${searchedData.poNumberSearch}"/>"
						</c:if> tabindex="4"/>
							<div class="basicPoNum displayNone">
							<img src="${commonStaticUrl}/images/warning.png"></img>
							<span class=errorMsg>Please enter numbers</span>
						</div>
					</div>
					<div class="searchUnitNumber" >
						<label>Unit #: </label>
						<input name="unitNumberSearch"  id="unitNumSearch" value="<c:out value="${searchedData.unitNumberSearch}"/>" tabindex="3"></input>
					</div>
				</div>
				</form>
				<table id="exceptionTable" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th class="componentNameHeader">Component Name</th>
							<th class="vendorHeader">PO &#38; Vendor Association</th>
							<th class="providedByHeader">To be Provided By</th>
							<th class="dateModifiedHeader">Date Created</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${exceptions}" var="exception">
						<tr>
							<td class="editable centerAlign width">
								<a class="rightMargin edit-exception">Edit</a>
								<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-exception"/></a>
								<input type="hidden" class="global-exception-id" value="${exception.exceptionId}"/>
							</td>
							<td class="component-name">${exception.componentName}</td>
							<td class="po-group">
                              <c:forEach items="${exception.poCategoryGroups}" var="poCategoryGroup">
                                <label>${poCategoryGroup.poCategorySubcategory} -</label>
                                <span class="vendor-name">${poCategoryGroup.vendor.vendorName}</span>
                                <span>${poCategoryGroup.vendor.city},</span>
                                <span>${poCategoryGroup.vendor.state}</span>
                                <span>${poCategoryGroup.vendor.zipCode}</span>
                                <br>
                              </c:forEach>
                            </td>
							<td class="provided-by">
							${exception.providerVendor.vendorName}
							</td>
							<td>
								<span value="${exception.createdDate}">${exception.createdDate}</span>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="modal" id="modal-edit-global-exception">
				</div>
				<div class="modal" id="modal-delete-global-exception">
				</div>
			</div>
		</div> 
		
	</body>
	<!-- Scripts -->
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="${context}/js/admin-console/app-config/global-exceptions.js" type="text/javascript"></script>
</html>