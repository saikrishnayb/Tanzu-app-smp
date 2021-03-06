<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
	    <title>SMC Home</title>
	    
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
		<link href="${baseUrl}/css/admin-console/security/roles.css" rel="stylesheet" type="text/css"/>
		<link href="${baseUrl}/css/admin-console/security/edit-create-role.css" rel="stylesheet" type="text/css"/>
		
	</head>
	
	<body style="overflow-y: auto;">
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		
		<div id="mainContent" class="borderTop floatLeft">
			<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<div id="column-one" class="floatLeft clear-left">
					<c:choose>
						<c:when test="${editOrCopy=='E'}">
							<h1 class="floatLeft clear-left">Modify Role</h1>
						 </c:when>
						<c:otherwise>
							<h1 class="floatLeft clear-left">Copy Role</h1>
						</c:otherwise>
					</c:choose>
					<form id="edit-role-form">
						<input name="roleId" type="hidden" value="${editRole.roleId}" />
						<input name="editOrCopy" id="editOrCopy" type="hidden" value="${editOrCopy}" />
						<input name="tempBaseRoleId" id="tempBaseRoleId"  type="hidden" value="${editRole.baseRoleId}" />
						<label class="floatLeft clear-left width-100">Role Name<span class=requiredField>*</span></label>
						<c:choose>
						  <c:when test="${editOrCopy=='E'}">
						  			<input class="floatLeft width-200" name="roleName" tabindex=1 id="roleName" value="${editRole.roleName}" type="text" maxlength="20" autocomplete="off" />
						  </c:when>
						  <c:otherwise>
						  			<input class="floatLeft width-200" name="roleName" id="roleName" type="text" maxlength="20" autocomplete="off" />
						  </c:otherwise>
						</c:choose>	
						<label class="floatLeft clear-left width-100">Base Role<span class=requiredField>*</span></label>
						<select class="floatLeft width-300" tabindex=2 name="baseRoleId" id="baseRoleId">
							<option value="">Select...</option>
							<c:forEach var="role" items="${roles}">
							 	<c:if test="${editRole.roleId ne role.roleId}"> 
									<option value="${role.roleId}" <c:if test="${editRole.baseRoleId eq role.roleId}">selected</c:if> >${role.orgName}-${role.baseRoleName}-${role.roleName}</option>
								</c:if> 
							</c:forEach>
						</select>
						
						<label for="orgDescription" class="floatLeft clear-left width-100">Description<span class=requiredField>*</span></label> 
							
						<c:choose>
						  <c:when test="${editOrCopy=='E'}">
						  		<textarea rows="6" cols="35" id="roleDescription" tabindex=3 name="roleDescription" maxlength="50" class="floatLeft width-200">${editRole.roleDescription}</textarea>
						  </c:when>
						  <c:otherwise>
						  		<textarea rows="6" cols="35" id="roleDescription" name="roleDescription" maxlength="255" class="floatLeft width-200"></textarea>
						  </c:otherwise>
						</c:choose>	
					</form> 					
					<h3 class="floatLeft clear-left">Role Hierarchy</h3>
					<div id="role-hierarchy" class="floatLeft clear-left jstree">
						<%@ include file="includes/role-hierarchy.jsp" %>
					</div>
					
					<div id="edit-links" class="floatLeft clear-left width-full">
					</div>
				</div>
				
				<div id="column-two" class="floatLeft margin-bottom-10">
					<h1 class="floatLeft clear-left">Role Permissions</h1>
					<div class=" floatLeft clear-left width-full" id="role-permissions">
						<%@ include file="includes/role-permissions.jsp" %>
					</div>
					
					<div id="modify-role-buttons" class="floatRight button-div">
						<a id="save-role" tabindex=5 class="buttonPrimary floatRight clear-left save">Save</a>
						<a class="secondaryLink floatRight cancel" tabindex=4  id="back">Cancel</a>
						<div class="error floatRight hidden">
							<img src="${commonStaticUrl}/images/warning.png">
							<span class="errorMsg"></span>
						</div>
					</div>
				</div>
				
				<!-- Deactivate Role Modal -->
				<div class="modal" id="deactivate-modal"></div>
			</div>
		</div>

		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${commonStaticUrl}/js/jquery.jstree.js" type="text/javascript"></script>
		<script src="${baseUrl}/js/admin-console/security/modify-role.js" type="text/javascript"></script>			
	</body>
</html>