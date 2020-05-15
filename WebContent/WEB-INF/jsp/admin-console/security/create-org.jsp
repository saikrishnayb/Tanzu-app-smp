<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>  
<head>

	<title>SMC Home</title>
	<%@ include file="../../../jsp/global/v1/header.jsp" %>
	
	<link href="${baseUrl}/css/global/v1/legacy-do-not-use/jstree-do-not-use/style-do-not-use.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/admin-console/security/org.css"rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/security/create-user.css"rel="stylesheet" type="text/css" />

	</head>
	
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../../jsp/admin-console/security/includes/org-form.jsp"%>
			<div class="edit-buttons">
				<a class="secondaryLink back" tabIndex="5">Cancel</a> 
				<c:if test="${isCreatePage eq true}">
					<a id="save-user-edit" class="buttonPrimary create" tabIndex="6">Save</a>
				</c:if>
				<c:if test="${isCreatePage eq false}">
					<a id="save-user-edit" class="buttonPrimary save" tabIndex="6">Save</a>
				</c:if>
				<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class=errorMsg></span>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		var isCreatePage='${isCreatePage}';
	</script>
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script src="${baseUrl}/js/global/v1/legacy-do-not-use/jquery-do-not-use-3.4.1.min.js"></script>
	<script>
		var j = jQuery.noConflict();
	</script>
	<script src="${baseUrl}/js/global/v1/legacy-do-not-use/jstree-do-not-use.min.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/security/create-org.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/security/vendor-hierarchy.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/security/org-form.js" type="text/javascript"></script>
</body>
</html>