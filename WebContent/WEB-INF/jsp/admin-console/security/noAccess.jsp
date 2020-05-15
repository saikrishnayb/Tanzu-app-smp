<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>SMC Home</title>
	<%@ include file="../../../jsp/global/v1/header.jsp" %>
	<link href="${baseUrl}/css/admin-console/security/users.css" rel="stylesheet" type="text/css"/>
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
	       <span style="float: center;color: #CC0000" >
				You are not authorized to see this page. Please Contact Support for further assistance.
			</span> 
		</div>
	</div>
	<input type="hidden" id="tabNavUser" value="left-nav-vendor-users">
	
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/security/users.js" type="text/javascript"></script>
</body>
</html>