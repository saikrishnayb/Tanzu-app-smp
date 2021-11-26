<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
	    <link href="${baseUrl}/css/global/v1/jquery/jquery.multiselect.css"rel="stylesheet" type="text/css" />
		<link href="${baseUrl}/css/admin-console/security/roles.css" rel="stylesheet" type="text/css"/>
		<link href="${baseUrl}/css/admin-console/security/edit-create-role.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body style="overflow-y: auto;">
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		
		<div id="mainContent" class="borderTop floatLeft">
			<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<div id="column-one" class="floatLeft clear-left" style="width:40%">
					<h1 class="floatLeft clear-left">Create New Role</h1>
					
					<form id="new-role-form">
						<label class="floatLeft clear-left width-100">Role Name<span class=requiredField>*</span></label>
						<input class="floatLeft width-200" name="roleName" id="roleName" type="text" maxlength="20" autocomplete="off" />
						
						<label class="floatLeft clear-left width-100">Base Role<span class=requiredField>*</span></label>
						<select class="floatLeft width-300" name="baseRoleId" id="baseRoleId">
							<option value="">Select...</option>
							<c:forEach var="role" items="${roles}">
								<option value="${role.roleId}">${role.orgName}-${role.baseRoleName}-${role.roleName}</option>
							</c:forEach>
						</select>
						
						<label for="orgDescription" class="floatLeft clear-left width-100">Description<span class=requiredField>*</span></label> 
							<textarea rows="6" cols="35" id="roleDescription" name="roleDescription" maxlength="50" class="floatLeft width-200"></textarea>
			
					</form>
					
					<h3 class="floatLeft clear-left">Role Hierarchy</h3>
					<div id="role-hierarchy" class="floatLeft clear-left jstree"></div> 
				</div>
				
				<div id="column-two" class="floatLeft margin-bottom-10" style="width:50%">
					<h1 class="floatLeft clear-left">Role Permissions</h1>
					<div class=" floatLeft clear-left width-full" id="role-permissions"></div>
				</div>
				
				<div id="new-role-buttons" class="floatRight button-div">
					<a class="buttonPrimary floatRight clear-left save">Save</a>
					<a class="secondaryLink floatRight cancel" id="back">Cancel</a>
					<div class="error floatRight hidden">
						<img src="${commonStaticUrl}/images/warning.png">
						<span class="errorMsg"></span>
					</div>
				</div>
			</div>
		</div>
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${commonStaticUrl}/js/jquery.jstree.js" type="text/javascript"></script>
		<script type="text/javascript" src="${baseUrl}/js/global/v1/jquery/jquery.multiselect.js"></script>
		<script src="${baseUrl}/js/admin-console/security/create-new-role.js" type="text/javascript"></script>
	</body>
</html>