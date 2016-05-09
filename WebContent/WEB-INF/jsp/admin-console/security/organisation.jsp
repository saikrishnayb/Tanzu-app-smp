<!DOCTYPE html>
<html>
<head>
	<title>SMC Home</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
	<link href="${context}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>
	<Script>
	var isCreatePage='${isCreatePage}';
	</Script>
</head>
<!-- ******************************edit modal******************************** -->
<div id="edit-modal" class="modal edit-org-modal" title="Edit org Information"></div>

<!-- ***************************deactivate modal********************************** -->
<div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Organisation Deactivation">
</div>
<!-- *******************************org account table************************** -->
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp"%>
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
							<input class="floatLeft width-200"  name="orgName" id="orgName" class="supportingLabel" type="text" value="${userSearchForm.firstName}"/>
							<label class="floatLeft clear-left width-150">Parent Org</label>
							<select class="floatLeft width-200" name="parentOrgId" id="parentOrgId">
								<option value="0">Select...</option>
								<c:forEach items="${orgListDrop}" var="orgDrop">
									<option value="${orgDrop.orgId}">${orgDrop.orgName}</option>
								</c:forEach>
							</select>
				</fieldset>
				
					</form>
					<div id="search-buttons-div" class="search-buttons-div">
							<a class="buttonSecondary floatRight search" href="#">Search</a>
							<a class="buttonSecondary floatRight margin-right reset" href="#">Reset</a>
							<div class="error-messages-container displayNone">
								<img src="${commonStaticUrl}/images/warning.png"></img>
								<span class=errorMsg></span>
							</div>
					</div>
			</div>
			<div class="full-width">
				<span class="floatRight addRow">
					<a href="${pageContext.request.contextPath}/admin-console/security/create-org-page.htm">Create Org</a>
					<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
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
</body>
<!-- Scripts -->
<script src="${context}/js/jstree/jquery-1.10.2.min.js" type="text/javascript"></script>
<Script>
var j = jQuery.noConflict();
</Script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.js"></script>
<link href="${context}/js/jstree/css/style.min.css" rel="stylesheet" type="text/css"/>
<script src="${context}/js/jstree/jstree.min.js" type="text/javascript"></script>
<script src="${context}/js/admin-console/security/vendor-hierarchy.js" type="text/javascript"></script>				
<script src="${context}/js/admin-console/security/org-form.js"	type="text/javascript"></script>
<script src="${context}/js/admin-console/security/organisation.js" type="text/javascript"></script>
</html>