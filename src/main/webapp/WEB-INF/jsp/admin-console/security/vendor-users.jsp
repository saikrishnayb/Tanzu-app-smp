<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>SMC Home</title>
	<%@ include file="../../../jsp/global/v2/header.jsp" %>
	<link href="${baseUrl}/css/admin-console/security/vendor-users.css" rel="stylesheet" type="text/css"/>
</head>

<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<c:choose>
			    <c:when test="${accessVendor eq true}">
			    	<div class="row">
			    		<%@ include file="../security/includes/advanced-user-search.jsp"%>
			    	</div>
			    	<div class="row">			
						<div class="col-xs-12">
							<span class="addRow pull-right">
								<a href="${baseAppUrl}/admin-console/security/create-vendor-user-page.htm">Create User
									<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
								</a>
							</span>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 vendor-users-table-container">
							<table id="vendor-users-table" >
								<thead>
									<tr>
										<th class="viewCol"></th>
										<th>First Name</th>
										<th>Last Name</th>
										<th>Email</th>
										<th>Phone</th>
										<th>Role</th>
										<th>Org</th>
										<th>Created Date</th>
										<th class="lastLogin">Last Login</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${userList}" var="user" varStatus="userLoop">
									<tr class="user-row">
										<td class="editable centerAlign">
											<div class="dropdown">
									            <a class="bootStrapDropDown dropdown-toggle" data-toggle="dropdown">
									              Actions
									              <span class="caret"></span>
									            </a>
									            <ul class="dropdown-menu">
													<li>
														<a class="edit-vendor-user">Edit User</a>
													</li>
													<li>
														<a class="resend-email">Re-send Enrollment Email</a>
													</li>
													<li>
														<a class="deactivate-vendor-user">Delete User</a>
													</li>
												</ul>
											</div>
											<input class="user-id" type=hidden value="${user.userId}"/>
										</td>
										<td>${user.firstName}</td>
										<td>${user.lastName}</td>
										<td class="user-email">${user.email}</td>
										<td>${user.phone} <c:if test="${(user.extension ne '') || (not empty user.extension)}">ext. ${user.extension}</c:if></td>
										<td>${user.role.roleName} <input class="role-id" type=hidden value="${user.role.roleId}"/></td>
										<td>${user.org}</td>
										<td>${user.formattedCreatedDate}</td>
										<td>
											<c:choose>
				                				<c:when test= "${user.lastLoginDate eq null}">
			                  						(Never)
			                					</c:when>
				                				<c:otherwise>
				                  					${user.getFormattedLastLoginDate()}
			                					</c:otherwise>
			              					</c:choose>
			              				</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row">
			        	<div class="col-xs-12">
				       		<span style="float: center;color: #CC0000" >You are not authorized to see the manage user page. Please Contact Support for further assistance.</span>
						</div>
					</div> 
		 		</c:otherwise>
			</c:choose>	
		</div>
		
		<!-- Modals -->
		<div id="user-modal" class="modal row"></div>
		
	</div>
	<input type="hidden" id="tabNavUser" value="left-nav-vendor-users-v2">
	
	<%@ include file="../../../jsp/global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/security/vendor-users.js" type="text/javascript"></script>
</body>

</html>