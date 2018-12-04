<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
		<link href="${baseUrl}/css/admin-console/security/roles.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		
		<div id="mainContent" class="borderTop floatLeft">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp"%>
			
				<!-- Advanced Search -->
				<span id="advanced-search" class="expandableContent handCursor collapsedImage floatRight margin-bottom-10"
				onclick="toggleContent('search-content','advanced-search');">Show Search Criteria</span>
				
				<div id="search-content" class="displayNone clear-both">
					<form id="advanced-search-form" class="margin-bottom-10" method="get" action="./roles-advanced-search.htm">
						<fieldset>
							<legend>Advanced Search</legend>
							<label class="floatLeft clear-left width-150">Role Name</label>
							<input class="floatLeft width-200" type="text" tabindex=1 name="roleName" value="<c:out value="${searchedRole.roleName}"/>" maxlength="50" autocomplete="off" />
							
							<label class="floatLeft clear-left width-150">Base Role</label>
							<select class="floatLeft width-200" tabindex=2 name="baseRoleId">
								<option value="">Select...</option>
								<option value="-1" <c:if test="${searchedRole.baseRoleId eq -1}">selected</c:if>>-- No Base Role --</option>
								<c:forEach var="baseRole" items="${baseRoles}">
								<option value="${baseRole.roleId}" <c:if test="${searchedRole.baseRoleId eq baseRole.roleId}">selected</c:if>>${baseRole.roleName}</option>
								</c:forEach>
							</select>
							
						</fieldset>
					</form>
					
					<div class="floatRight clear-left button-div" id="search-buttons-div">
						<a class="buttonSecondary floatRight search" tabindex=4>Search</a>
						<a class="secondaryLink floatRight reset" tabindex=3>Reset</a>
					</div>
				</div>
				
				<!-- Roles Table -->
				<div class="width-full">
					<table id="roles-table" class="margin-bottom-10">
						<thead>
							<tr>
								<th class="viewCol"></th>
								<th>Role Name</th>
								<th>Description</th>
								<th>Base Role</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="role" items="${roles}">
							<tr class="role-row">
								<td class="editable centerAlign">
								<c:if test="${(role.roleId != myRole)}">
									<c:if test="${(role.baseRoleId != 0)}">
										<a class="rightMargin edit-role">Edit</a>
										<a class="rightMargin copy-role">copy</a>
										<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin handCursor deactivate-role"/>
										<input type="hidden" name="roleId" value="${role.roleId}" />
									</c:if>
								</c:if>
								</td>
								<td class="role-name">${role.roleName}</td>
								<td class="role-name">${role.roleDescription}</td>
								<td class="base-role-name">${role.baseRoleName}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<span class="floatRight">
						<a href="./create-new-role.htm">Create Role</a>
						<a href="./create-new-role.htm"><img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/></a>
					</span>
				</div>
				
				<!-- Deactivate Role Modal -->
				<div class="modal" id="deactivate-modal"></div>
			</div>
		</div>
			
	</body>
	<!-- Scripts -->
	<script src="${baseUrl}/js/admin-console/security/roles.js" type="text/javascript"></script>
</html>