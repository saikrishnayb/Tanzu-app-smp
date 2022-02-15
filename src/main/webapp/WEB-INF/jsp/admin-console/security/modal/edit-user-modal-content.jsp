<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal-content col-xs-12" data-modal-title="Edit User" data-modal-width="1198">
	<div class="row modal-body">
		<div class="col-xs-12">
			<%@ include file="../includes/user-form.jsp"%>
		</div>
	</div>
	<div class="row modal-footer" style="margin-top:20px;">
		<div class="col-xs-12">
			<div class="edit-buttons">
				<a id="cancelButton" class="secondaryLink cancel" tabIndex="5">Cancel</a>
				<a id="save-user-edit" class="buttonPrimary save" tabIndex="6">Save</a>
				<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class=errorMsg>One or more required fields not filled in correctly.</span>
				</div>
			</div>
		</div>
	</div>
	
	<script src="${baseUrl}/js/admin-console/security/users-form.js" type="text/javascript"></script>
</div>

