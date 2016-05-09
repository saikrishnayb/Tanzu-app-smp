<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
		<link href="${context}/css/admin-console/security/roles.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/admin-console/security/edit-create-role.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/jquery.multiselect.css"rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${context }/jQuery/jquery.multiselect.js"></script>
		<script src="${commonStaticUrl}/js/jquery.jstree.js" type="text/javascript"></script>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		
		<div id="mainContent" class="borderTop floatLeft">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp" %>
			
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
			
	</body>
	<!-- Scripts -->
	<script src="${context}/js/admin-console/security/create-new-role.js" type="text/javascript"></script>
</html>