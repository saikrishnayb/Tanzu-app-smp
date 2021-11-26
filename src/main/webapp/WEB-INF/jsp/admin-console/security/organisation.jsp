<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>SMC Home</title>
	<%@ include file="../../../jsp/global/v1/header.jsp" %>
	
	<link href="${baseUrl}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>
</head>
<!-- ******************************edit modal******************************** -->
<div id="edit-modal" class="modal edit-org-modal" title="Edit org Information"></div>

<!-- ***************************deactivate modal********************************** -->
<div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Organisation Deactivation">
</div>
<!-- *******************************org account table************************** -->
<body>
	<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			
		<!--  ********************************search criteria******************************** -->
			<span id="advancedSearch" class="expandableContent handCursor 
				<c:if test="${hasBeenSearched eq false}">collapsedImage</c:if>
				<c:if test="${hasBeenSearched eq true}">expandedImage</c:if>
				 floatRight margin-bottom"
				onclick="toggleContent('search-content','advancedSearch');">Show Search Criteria</span>
				
			<div id="search-content" 
				class="<c:if test="${hasBeenSearched eq false}">displayNone</c:if> 
				<c:if test="${hasBeenSearched eq true}">displayBlock</c:if> clear-both">
				<form id="search-org-form" action="./org-search.htm" class="margin-bottom-10" method="GET">
				<fieldset>
						<legend>Advanced Search</legend>	
							<label class="floatLeft clear-left width-150">Org Name</label>
							<input class="floatLeft width-200"  name="orgName" tabindex=1  id="orgName" class="supportingLabel" type="text" value="${userSearchForm.firstName}"/>
							<label class="floatLeft clear-left width-150">Parent Org</label>
							<select class="floatLeft width-200" tabindex=2 name="parentOrgId" id="parentOrgId">
								<option value="0">Select...</option>
								<c:forEach items="${orgListDrop}" var="orgDrop">
									<option value="${orgDrop.orgId}">${orgDrop.orgName}</option>
								</c:forEach>
							</select>
				</fieldset>
				
					</form>
					<div id="search-buttons-div" class="search-buttons-div">
							<a class="buttonSecondary floatRight search" tabindex=4 href="#">Search</a>
							<a class="buttonSecondary floatRight margin-right reset" tabindex=3 href="#">Reset</a>
							<div class="error-messages-container displayNone">
								<img src="${commonStaticUrl}/images/warning.png"></img>
								<span class=errorMsg></span>
							</div>
					</div>
			</div>
			<div class="full-width">
				<span class="floatRight addRow">
					<a href="${baseAppUrl}/admin-console/security/create-org-page.htm">Create Org
						<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
					</a>
				</span>
				<table id="org-table" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Org Name</th>
							<th>Parent Org</th>
							<th>Description</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orgList}" var="org">
						<tr class="org-row">
							
							<td class="editable centerAlign">
							<c:if test="${(org.orgId != myOrg)}">
								<c:if test="${(org.parentOrgId ne 0)}">
									<a class="rightMargin edit-org">Edit</a>
									<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin deactivate"/>
									<input  class="org-id" type=hidden value="${org.orgId}"/>
								</c:if>
							</c:if>
							</td>
							
							<td class="org-name" >${org.orgName}</td>
							<td >${org.parentOrgName}</td>
							<td >${org.orgDescription}</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<script>
		var isCreatePage='${isCreatePage}';
	</script>
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/security/org-form.js"	type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/security/organisation.js" type="text/javascript"></script>
</body>
</html>