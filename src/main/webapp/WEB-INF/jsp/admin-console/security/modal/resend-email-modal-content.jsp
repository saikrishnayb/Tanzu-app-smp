<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal-content col-xs-12" data-modal-title="Resend Enrollment E-mail" data-modal-width="350">
	<div class="row modal-body">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					Clicking "Continue" sends the enrollment password to the user's e-mail address below:
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
				<div class="col-xs-12">
					${editableUser.email}
				</div>
			</div>
		</div>
	</div>
	<div class="row modal-footer" style="margin-top:20px;">
		<div class="col-xs-12">
			<div class="edit-buttons">
				<a id="cancelButton" class="secondaryLink cancel">Cancel</a>
				<a id="resend-confirm" class="buttonPrimary resend-confirm" data-user-id="${editableUser.userId}">Continue</a>
			</div>
		</div>
	</div>
</div>