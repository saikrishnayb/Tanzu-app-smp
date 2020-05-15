<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>SMC Home</title>
	<%@ include file="../../../jsp/global/v1/header.jsp" %>
	<link href="${baseUrl}/css/global/v1/jquery/jquery.multiselect.css"rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/security/users.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/global/v1/jquery/jquery.multiselect.filter.css"rel="stylesheet" type="text/css" />
</head>

<!-- ******************************edit modal******************************** -->
<div id="edit-modal" class="modal edit-user-modal" title="Edit User Information"></div>

<!-- ***************************deactivate modal********************************** -->
<div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Account Deactivation">
</div>
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
<c:choose>
    <c:when test="${accessVendor eq true}">				
		<!--  ********************************search criteria******************************** -->
			<span id="advancedSearch" class="expandableContent handCursor 
				<c:if test="${hasBeenSearched eq false}">collapsedImage</c:if>
				<c:if test="${hasBeenSearched eq true}">expandedImage</c:if>
				 floatRight margin-bottom"
				onclick="toggleContent('search-content','advancedSearch');">
				<c:if test="${hasBeenSearched eq false}">Show Search Criteria</c:if>
				<c:if test="${hasBeenSearched eq true}">Hide Search Criteria</c:if></span>
			<div id="search-content" 
        class="displayNone tableBorder margin-top clear-both search-content" data-has-been-searched="${hasBeenSearched}" data-vendor-user-search="true">
				<form id="search-vendor-user-form" action="./users-search.htm" method="GET">
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
							<input id="search-last-name" tabindex=2 name="lastName" class="supportingLabel input alpha alpha-name optional" type="text" value="<c:out value="${userSearchForm.lastName}"/>"/>
						</div>
						<div class="clear-float-left">
              <label class="rightAlign">Org</label>
              <select id="search-org" tabindex=4 name="orgIds" class="input numeric numeric-whole optional" multiple>
                <c:forEach items="${orgList}" var="org">
	                <c:if test="${hasBeenSearched}">
	                  <c:set var="orgSelected" value =""/>
	                  <c:forEach items="${userSearchForm.orgIds}" var="searchOrgId">
	                    <c:if test="${org.orgId eq searchOrgId}">
	                      <c:set var="orgSelected" value ="selected"/>
	                    </c:if>
	                  </c:forEach>
	                </c:if>
	                <option value="${org.orgId}" ${orgSelected}>${org.orgName}</option>
                </c:forEach>
              </select>
            </div>
					</div>
				
					<div id="threeColumnContainer third" class="threeColumnContainer third">
						<div class="clear-float-left">
							<label class="rightAlign">Email</label>
							<input id="search-email" tabindex=3 name="email" class="supportingLabel input alpha alpha-email optional" type="text" value="<c:out value="${userSearchForm.email}"/>"/>
						</div>
					</div>
					<input type="hidden" name="vendorSearch" value="true">
					</form>
					<div id="search-buttons-div" class="search-buttons-div">
							<a class="buttonSecondary floatRight vendorSearch" tabindex=4 href="#">Search</a>
							<a class="buttonSecondary floatRight margin-right reset" tabindex=5 href="#">Reset</a>
					</div>
			</div>
			<div class="full-width">
				<span class="floatRight addRow">
					<a href="${baseAppUrl}/admin-console/security/create-vendor-user-page.htm">Create User
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
							<th>Org</th>
							<th class="lastLogin">Last Login</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userList}" var="user">
						<tr class="user-row">
							<td class="editable centerAlign">
								<a class="rightMargin edit-vendor-user">Edit</a>
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin deactivate-vendor-user <c:if test="${user.deactivatible eq true}">deactivate-vendor-user</c:if>"/>
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
							<td>${user.org}</td>
							<c:choose>
                <c:when test= "${user.lastLoginDate eq null}">
                  <td>(Never)</td>
                </c:when>
                <c:otherwise>
                  <td>${user.getFormattedLastLoginDate()}</td>
                </c:otherwise>
              </c:choose>
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
	<input type="hidden" id="tabNavUser" value="left-nav-vendor-users">
	
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script type="text/javascript" src="${baseUrl}/js/global/v1/jquery/jquery.multiselect.js"></script>
	<script type="text/javascript" src="${baseUrl}/js/global/v1/jquery/jquery.multiselect.filter.js"></script>
	<script src="${baseUrl}/js/admin-console/security/users.js" type="text/javascript"></script>
</body>

</html>