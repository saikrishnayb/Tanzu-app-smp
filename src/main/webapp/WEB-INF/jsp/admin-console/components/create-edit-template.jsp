<!DOCTYPE html>

<html>  
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<title>SMC Home</title>
	<%@ include file="../../../jsp/global/v1/header.jsp" %>
	<link href="${baseUrl}/css/global/v1/legacy-do-not-use/chosen-do-not-use.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/admin-console/security/org.css"rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/security/create-user.css"rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/components/template-rule.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>


<Script>
	var isCreatePage='${isCreatePage}';
</Script>
	</head>
	
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="borderTop">
		<div id="error-modal" ></div>
		<%@ include file="../../global/navigation/admin-console/components/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../admin-console/components/includes/template-form.jsp"%>
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
	
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script src="${baseUrl}/js/global/v1/legacy-do-not-use/modal-util-do-not-use.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/global/v1/legacy-do-not-use/jquery.dataTables-do-not-use.min.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/global/v1/legacy-do-not-use/chosen.jquery-do-not-use.min.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/components/template-form.js" type="text/javascript"></script>
</body>
</html>