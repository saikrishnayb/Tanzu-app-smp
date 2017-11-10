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
	<link href="${context}/css/admin-console/components/comp-overrid.css"rel="stylesheet" type="text/css" />


<Script>
	var isCreatePage='${isCreatePage}';
</Script>
	</head>
	
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/comp-visiblity-override-form.jsp"%>
			<div class="edit-buttons-90">
				<span class="errorMsg">* indicates a required field</span>
				<a class="secondaryLink back" tabindex=6>Cancel</a> 
				<c:if test="${isCreatePage eq true}">
					<a id="save-template-create" class="buttonPrimary create" tabindex=7>Save</a>
				</c:if>
				<c:if test="${isCreatePage eq false}">
					<a id="save-template-edit" class="buttonPrimary save" tabindex=7>Save</a>
				</c:if>
				<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class=errorMsg>One or more required fields not filled in correctly.</span>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- Scripts -->
<script src="${context}/js/admin-console/components/comp-visiblity-override.js" type="text/javascript"></script>
</html>