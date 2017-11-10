<!DOCTYPE html>

<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />
<title>SMC Home</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css"rel="stylesheet" type="text/css" />
	<link href="${context}/css/admin-console/security/users.css"rel="stylesheet" type="text/css" />
	<link href="${context}/css/admin-console/security/create-user.css"rel="stylesheet" type="text/css" />

</head>
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<div class="edit-buttons">
				<a class="secondaryLink back" tabIndex="11">Cancel</a> 
				<a id="save-user-vendor-edit" class="buttonPrimary createVendorUser" tabIndex="12">Add To SMC</a>
				<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class=errorMsg></span>  
				</div>
			</div>
			<%@ include file="../../../jsp/jsp-fragment/admin-console/security/user-form-vendor.jsp"%>
		</div>
	</div>
	<input type="hidden" id="tabNavUser" value="left-nav-vendor-users">
</body>
<!-- Scripts -->
<script src="${commonStaticUrl}/js/jquery.dataTables.min.js"type="text/javascript"></script>
<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
<script src="${context}/js/admin-console/security/users-form.js" type="text/javascript"></script>
<script src="${context}/js/admin-console/security/create-users.js" type="text/javascript"></script>
</html>