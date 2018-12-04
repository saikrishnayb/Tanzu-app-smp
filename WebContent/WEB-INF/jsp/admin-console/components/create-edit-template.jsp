<!DOCTYPE html>

<html>  
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<title>SMC Home</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/admin-console/security/org.css"rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/security/create-user.css"rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/chosen.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/admin-console/components/template-rule.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>


<Script>
	var isCreatePage='${isCreatePage}';
</Script>
	</head>
	
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<div id="error-modal" ></div>
		<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/template-form.jsp"%>
			<div class="edit-buttons">
				<span class="errorMsg">* indicates a required field</span>
				<a class="secondaryLink back" tabIndex="3">Cancel</a> 
				<c:if test="${isCreatePage eq true}">
					<a id="save-template-create" class="buttonPrimary create" tabIndex="4">Save</a>
				</c:if>
				<c:if test="${isCreatePage eq false}">
					<a id="save-template-edit" class="buttonPrimary save" tabIndex="4">Save</a>
				</c:if>
                <div id="unsaved-changes-modal" class="modal" title="Unsaved Changes">
                  <p>You have unsaved changes on this page, would you like to continue?</p>
                  
                  <div class="floatRight modal-buttons">
                    <a class="unsaved-modal-close">Cancel</a>
                    <a class="buttonPrimary unsaved-modal-continue">Continue</a>
                  </div>
                  
                </div>
			</div>
		</div>
	</div>
	<input type="hidden" id="tempCompId" value="${tempCompId}"/>
	<input type="hidden" id="toggleSelection" value="${toggleSelection}"/>
</body>
<!-- Scripts -->
<script src="${baseUrl}/js/global/modal-util.js" type="text/javascript"></script>
<script src="${baseUrl}/js/v2/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
<script src="${baseUrl}/jQuery/chosen.jquery.min.js" type="text/javascript"></script>
<script src="${baseUrl}/js/admin-console/components/template-form.js" type="text/javascript"></script>
</html>