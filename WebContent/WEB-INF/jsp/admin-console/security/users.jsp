<!DOCTYPE html>
<html>
<head>
	<title>SMC Home</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>

	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
	<link href="${context}/css/admin-console/security/users.css" rel="stylesheet" type="text/css"/>
</head>
<!-- ******************************edit modal******************************** -->
<div id="edit-modal" class="modal edit-user-modal" title="Edit User Information"></div>

<!-- ***************************deactivate modal********************************** -->
<div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Account Deactivation">
</div>
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
<c:choose>
    <c:when test="${access eq true}">			
		<!--  ********************************search criteria******************************** -->
			<span id="advancedSearch" class="expandableContent handCursor 
				<c:if test="${hasBeenSearched eq false}">collapsedImage</c:if>
				<c:if test="${hasBeenSearched eq true}">expandedImage</c:if>
				 floatRight margin-bottom"
				onclick="toggleContent('search-content','advancedSearch');">Show Search Criteria</span>
				
			<div id="search-content" 
				class="<c:if test="${hasBeenSearched eq false}">displayNone</c:if> 
				<c:if test="${hasBeenSearched eq true}">displayBlock</c:if> 
				tableBorder margin-top clear-both search-content">
				<form id="search-user-form" action="./users-search.htm" method="GET">
					<div class="threeColumnContainer">
						<div class="clear-float-left">	
							<label class="rightAlign">First Name</label>
							<input id="search-first-name" tabindex=1 name="firstName" class="supportingLabel input alpha alpha-name optional" type="text" value="<c:out value="${userSearchForm.firstName}"/>"/>
						</div>
						<div class="clear-float-left">
							<label class="rightAlign">Role</label>
							<select id="search-role" tabindex=4 name="roleId" class="input numeric numeric-whole optional">
								<option></option>
								<c:forEach items="${roleList}" var="role">
									<option value="${role.roleId}" <c:if test="${userSearchForm.roleId eq role.roleId}"> selected </c:if>>${role.roleName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				
					<div class="threeColumnContainer">
						<div class="clear-float-left">
							<label class="rightAlign">Last Name</label>
							<input id="search-last-name" tabindex=2name="lastName" class="supportingLabel input alpha alpha-name optional" type="text" value="<c:out value="${userSearchForm.lastName}"/>"/>
						</div>
					</div>
				
					<div id="threeColumnContainer third" class="threeColumnContainer third">
						<div class="clear-float-left">
							<label class="rightAlign">Email</label>
							<input id="search-email" tabindex=3 name="email" class="supportingLabel input alpha alpha-email optional" type="text" value="<c:out value="${userSearchForm.email}"/>"/>
						</div>
					</div>
					<input type="hidden" name="vendorSearch" value="false">
					</form>
					<div id="search-buttons-div" class="search-buttons-div">
							<a class="buttonSecondary floatRight search" tabindex=6 href="#">Search</a>
							<a class="buttonSecondary floatRight margin-right reset" tabindex=5 href="#">Reset</a>
							<div class="error-messages-container displayNone">
								<img src="${commonStaticUrl}/images/warning.png"></img>
								<span class=errorMsg></span>
							</div>
					</div>
			</div>
			<div class="full-width">
				<span class="floatRight addRow">
					<a href="${pageContext.request.contextPath}/admin-console/security/create-user-page.htm">Create User
						<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
					</a>
				</span>
				<table id="users-table" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Email</th>
							<th>Phone</th>
							<th>Status</th>
							<th>Type</th>
							<th>Role</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userList}" var="user">
						<tr class="user-row">
							<td class="editable centerAlign">
								<a class="rightMargin edit-user">Edit</a>
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin <c:if test="${user.deactivatible eq true}">deactivate</c:if>"/>
								<input class="user-id" type=hidden value="${user.userId}"/>
							</td>
							<td><c:out value="${user.firstName}" /></td>
							<td><c:out value="${user.lastName}" /></td>
							<td class="user-email"><c:out value="${user.email}" /></td>
							<td>${user.phone} <c:if test="${(user.extension ne '') || (not empty user.extension)}">ext. ${user.extension}</c:if></td>
							<td>
							 	<c:if test="${user.status eq 'A'}">Active</c:if>
								<c:if test="${user.status eq 'I'}">Inactive</c:if>
							</td>
							<td class="user-type">${user.userType.userType}</td>
							<td>${user.role.roleName} <input class="role-id" type=hidden value="${user.role.roleId}"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</c:when>
	<c:otherwise>
	       <span style="float: center;color: #CC0000" >
				You are not authorized to see the manage user page. Please Contact Support for further assistance.
			</span> 
	 </c:otherwise>
</c:choose>
		</div>
	</div>
	<input type="hidden" id="tabNavUser" value="left-nav-users">
</body>
<!-- Scripts -->
<script src="${context}/js/admin-console/security/users.js" type="text/javascript"></script>
</html>