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
	<link href="${context}/css/admin-console/security/org.css"rel="stylesheet" type="text/css" />
	<link href="${context}/css/admin-console/security/create-user.css"rel="stylesheet" type="text/css" />

<Script>
	var isCreatePage='${isCreatePage}';
</Script>
	</head>
	
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/security/org-form.jsp"%>
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
</body>
<!-- Scripts -->
<script src="${commonStaticUrl}/js/jquery.dataTables.min.js"type="text/javascript"></script>
<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
<script src="${context}/js/admin-console/security/create-org.js" type="text/javascript"></script>
<script src="${context}/js/jstree/jquery-1.10.2.min.js" type="text/javascript"></script>
<Script>
var j = jQuery.noConflict();
</Script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.js"></script>
<link href="${context}/js/jstree/css/style.min.css" rel="stylesheet" type="text/css"/>
<script src="${context}/js/jstree/jstree.min.js" type="text/javascript"></script>
<script src="${context}/js/admin-console/security/vendor-hierarchy.js" type="text/javascript"></script>
<script src="${context}/js/admin-console/security/org-form.js" type="text/javascript"></script>
</html>