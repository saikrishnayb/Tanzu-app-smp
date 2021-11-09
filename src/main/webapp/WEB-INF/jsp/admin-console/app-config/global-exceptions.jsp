<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
	    <title>SMC Global Exceptions</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/global-exceptions.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				 <form id="search-exception-form" action="exception-search.htm" method="GET">
					<div class="customSearch">
						<label class="labelName CustomLabel" >Component Name:</label>
						<input name="componentNameSearch"  class="fuzzySearch" id="componentNameSearch" value="<c:out value="${searchedData.componentNameSearch}"/>" tabindex="1"></input>
						<label class="labelName CustomLabel" >Vendor Name:</label>
						<input name="vendorNameSearch" class="fuzzySearch" id="vendorNameSearch" value="<c:out value="${searchedData.vendorNameSearch}"/>"  tabindex="2"></input>
						<label class="labelName  CustomLabel">Unit #: </label>
						<input name="unitNumberSearch" maxlength="10" class="querySearch" id="unitNumSearch" value="<c:out value="${searchedData.unitNumberSearch}"/>" tabindex="3"></input>
						<label class="labelName CustomLabel">PO #:</label>
						<input  name="poNumberSearch" class="querySearch" maxlength="7" id="poSearch" 
						<c:if test="${searchedData.poNumberSearch ne 0}">	
						value="<c:out value="${searchedData.poNumberSearch}"/>"
						</c:if> tabindex="4"/>
						<a class="buttonSecondary  reset buttonSize buttonDisabled" href="#" tabindex="5">Clear</a>&nbsp;
						<a class="buttonPrimary edit-alert buttonSize querySearch" unitNumSearch="${searchedData.unitNumberSearch}" poSearch= "${searchedData.poNumberSearch}" id="searchException" tabindex="6">Search</a>
					</div>
					 <div class="basicValidation displayNone floatRight errorText errorDisplay">
						<img src="${commonStaticUrl}/images/warning.png"></img>
						<span class=errorMsg>Please enter Unit or PO number</span>
					 </div>
					 <div class="basicPoNum displayNone floatRight errorDisplay">
						<img src="${commonStaticUrl}/images/warning.png"></img>
						<span class=errorMsg>Enter a valid PO number</span>
					 </div>
				</form>
				<table id="exceptionTable">
					<thead>
						<tr>
							<th></th>
							<th>Component Name</th>
							<th>PO &#38; Vendor Association</th>
							<th>To be Provided By</th>
							<th class="dateHeader">Date Modified</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${exceptions}" var="exception">
						<tr>
							<td class="editable centerAlign width">
								<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-exception"/></a>
								<a class="rightMargin edit-exception">Edit</a>
								<input type="hidden" class="global-exception-id" value="${exception.exceptionId}"/>
							</td>
							<td class="component-name">${exception.componentName}</td>
							<td class="po-group">
                              <c:forEach items="${exception.poCategoryGroups}" var="poCategoryGroup">
                                <label>${poCategoryGroup.poCategorySubcategory} -</label>
                                <span class="vendor-name">${poCategoryGroup.vendor.vendorName},</span>
                                <span>${poCategoryGroup.vendor.city},</span>
                                <span>${poCategoryGroup.vendor.state}</span>
                                <span>${poCategoryGroup.vendor.zipCode}</span>
                                <br>
                              </c:forEach>
                            </td>
							<td class="provided-by">
							${exception.providerVendor.vendorName}
							</td>
							<td class="modified-date">
								<span value="${exception.modifiedDate}">${exception.modifiedDate}</span>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				<div class="modal" id="modal-edit-global-exception">
				</div>
				<div class="modal" id="modal-delete-global-exception">
				</div>
			</div>
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/global-exceptions.js" type="text/javascript"></script>
	</body>
</html>