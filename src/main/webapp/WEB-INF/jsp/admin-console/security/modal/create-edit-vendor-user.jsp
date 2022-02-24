<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal-content col-xs-12" data-modal-title='${isCreatePage ? "Create User" : "Edit User" }' data-modal-width="1198">
	<div class="row modal-body">
		<div class="col-xs-12">
			<div class="row">
				<%@ include file="../includes/vendor-user-form.jsp"%>
			</div>
		</div>
	</div>
	<div class="row modal-footer" style="margin-top:20px;">
		<div class="col-xs-12">
			<div class="edit-buttons">
				<a id="cancelButton" class="secondaryLink cancel" tabIndex="10">Cancel</a>
				<a id="save-user-vendor-edit" class="buttonPrimary saveVendor" tabIndex="11">Save</a>
				<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class=errorMsg>One or more required fields not filled in correctly.</span>
				</div>
			</div>
		</div>
	</div>
	<script src="${baseUrl}/js/admin-console/security/modal/create-edit-vendor-user.js" type="text/javascript"></script>
	
	<!-- Modals -->
	<div id="create-edit-vendor-user-modal" class="modal row"></div>
	
	<div id="ldap-userinfo-modal" class="modal row">
		<div class="modal-content ldap-user-info-modal-content col-xs-12" data-modal-title="LDAP User Info" data-modal-max-width="350" data-keep-contents="true">
			<div class="row">
				<p id="infoText"></p>
				<div class="deactivate-buttons-div-ok" style="display: none;" id="ok">
					<br/>
					<a class="secondaryLink cancelLdap" style="float: right; vertical-align:bottom;" tabIndex="-1">Ok</a>
				</div>
				
				<div class="deactivate-buttons-div" style="display: none" id="yes">
					<input type="hidden" id="fnameM">
					<input type="hidden" id="lnameM">
					<input type="hidden" id="phoneM">
					<input type="hidden" id="ssoM">
					<input type="hidden" id="emailM">
					<a class="secondaryLink cancelLdap" tabIndex="-1">No, Cancel</a> 
					<a class="buttonPrimary goLDAP" tabIndex="-1">Yes, Continue</a>
				</div>
			</div>
		</div>
	</div> 
</div>