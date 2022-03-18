<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This page is only for two factor authentication since it should not be part of the main container --%>
<html>
	<head>
		<%@ include file="../../jsp/global/v2/header.jsp" %>
		<link href="${baseUrl}/css/app-container/two-factor-auth.css" rel="stylesheet" type="text/css"/>
	</head>
	<body style="margin:4px;" >
		<%@ include file="includes/header.jsp"%>
		<%@ include file="includes/navigation.jspf"%>
		
		<div id="loading-overlay" class="page-loading"></div>
		   
		<div id="mainContent">
			&emsp;
			<!-- Modals -->
			<div id="two-factor-auth-modal" class="modal">
				<%@ include file="../../jsp/global/v2/modal-error-container.jsp"%>
				<div class="modal-content col-xs-12" 
					data-modal-title="Verification Required" 
					data-modal-max-width="500" 
					data-keep-contents="true" 
					data-modal-no-close>
					<div class="row modal-body">
						<div class="col-xs-12 text-center">
							<div class="row">
								<div class="col-xs-12">
									An access code has been sent to the email associated with this account.
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<label>Email Address: </label>${user.emailAddress}
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									Please enter the code in the field below to continue.
								</div>
							</div>
							<div class="row access-code-row">
								<div class="col-xs-12">
									<label for="access-code">Access Code: </label><input id="access-code" name="access-code" class="numeric" maxLength="6" />
								</div>
							</div>
							<div class="row access-code-resent-row ui-helper-hidden">
								<div class="col-xs-12 alert alert-info">
									A new access code has been sent to the email on file.<br>
									Please enter the new access code.
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="button-div floatRight">
							<a id="resend-access-code-btn" 
								class="linkSecondary "
								data-user-id="${user.userId}">
									Resend Access Code
							</a>
							<a id="submit-access-code-btn" 
								class="buttonSecondary buttonDisabled"
								data-user-id="${user.userId}">
									Continue
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<%@ include file="includes/footer.jspf"%>		
	</body>
	<%@ include file="../../jsp/global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/app-container/includes/header.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/app-container/two-factor-auth.js" type="text/javascript"></script>
</html>
