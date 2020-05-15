<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>  
<head>

	<title>SMC Home</title>
	
	<%@ include file="../../../jsp/global/v1/header.jsp" %>
	<link href="${baseUrl}/css/admin-console/security/users.css"rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/security/create-user.css"rel="stylesheet" type="text/css" />

</head>
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<div class="edit-buttons">
				<a class="secondaryLink back" tabIndex="-1">Cancel</a> 
				<a id="save-user-edit" class="buttonPrimary create" tabIndex="-1">Add To SMC</a>
				<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class=errorMsg></span>
				</div>
			</div>
			<%@ include file="includes/user-form.jsp"%>
		</div>
	</div>
	<input type="hidden" id="tabNavUser" value="left-nav-users">
	
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/security/users-form.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/security/create-users.js" type="text/javascript"></script>
</body>
</html>