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
								<a id="create-vendor-user">Create User
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
										<th></th>
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
									<!-- Uses Javascript implementation -->
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
		<div id="vendor-user-modal" class="modal row"></div>
		
	</div>
	<input type="hidden" id="tabNavUser" value="left-nav-vendor-users-v2">
	
	<%@ include file="../../../jsp/global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/security/vendor-users.js" type="text/javascript"></script>
</body>

</html>