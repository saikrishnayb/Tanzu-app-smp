<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ include file="../includes/user-form-vendor.jsp"%>
<div class="edit-buttons">
	<a class="secondaryLink cancel" >Cancel</a>
	<a id="save-user-vendor-edit" class="buttonPrimary saveVendor">Save</a>
	<div class="error-messages-container displayNone">
		<img src="${commonStaticUrl}/images/warning.png"></img>
		<span class=errorMsg>One or more required fields not filled in correctly.</span>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/security/users-form.js"
	type="text/javascript"></script>