<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/security/roles.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
			
				<!-- Advanced Search -->
				<span id="advanced-search" class="expandableContent handCursor collapsedImage floatRight margin-bottom-10"
				onclick="toggleContent('search-content','advanced-search');">Show Search Criteria</span>
				
				<div id="search-content" class="displayNone clear-both">
					<form id="advanced-search-form" class="margin-bottom-10" method="get" action="./roles-advanced-search">
						<fieldset>
							<legend>Advanced Search</legend>
							<label class="floatLeft clear-left width-150">Role Name</label>
							<input class="floatLeft width-200" type="text"  name="roleName" value="<c:out value="${searchedRole.roleName}"/>" maxlength="50" autocomplete="off" />
							
							<label class="floatLeft clear-left width-150">Base Role</label>
							<select class="floatLeft width-200"  name="baseRoleId">
								<option value="">Select...</option>
								<option value="-1" <c:if test="${searchedRole.baseRoleId eq -1}">selected</c:if>>-- No Base Role --</option>
								<c:forEach var="baseRole" items="${baseRoles}">
								<option value="${baseRole.roleId}" <c:if test="${searchedRole.baseRoleId eq baseRole.roleId}">selected</c:if>>${baseRole.roleName}</option>
								</c:forEach>
							</select>
							
						</fieldset>
					</form>
					
					<div class="floatRight clear-left button-div" id="search-buttons-div">
						<a class="buttonSecondary floatRight search" >Search</a>
						<a class="secondaryLink floatRight reset" >Reset</a>
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
						<a href="./create-new-role">Create Role</a>
						<a href="./create-new-role"><img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/></a>
					</span>
				</div>
				
				<!-- Deactivate Role Modal -->
				<div class="modal" id="deactivate-modal"></div>
			</div>
		</div>
	
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/security/roles.js" type="text/javascript"></script>
	</body>
</html>