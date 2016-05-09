<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />

<form id="edit-header-form">
	<span class="floatLeft clear-left errorMsg">* indicates a required field.</span>

	<input name="alertId" type="hidden" />
	<input name="alertType" type="hidden" />

	<label class="floatLeft clear-left width-150">Tab</label>
	<span id="tab-name" class="floatLeft"></span>
	
	<label class="floatLeft clear-left width-150">Header Name <span class="errorMsg">*</span></label>
	<span id="header-name" class="floatLeft header-change"></span>
	<input id="header-name-change" class="floatLeft header-change edit-field width-300 margin-left-fix" name="headerName" maxLength="50" autocomplete="off" />
	<a id="edit-header-name" class="bold floatLeft secondaryLink header-change">Edit Header Name</a>
	<a id="save-header-name" class="bold floatLeft secondaryLink header-change hidden">Save Name</a>
	<a id="cancel-edit-header-name" class="bold floatLeft secondaryLink header-change hidden">Cancel</a>
	
	<label class="floatLeft clear-left width-150">Hover Over Help</label>
	<input class="floatLeft width-300 margin-left-fix width-100" name="helpText" type="text" maxLength="100" autocomplete="off" />
	
	<label class="floatLeft clear-left width-150">Display Sequence  <span class="errorMsg">*</span></label>
	<input class="floatLeft margin-left-fix width-100" name="displaySequence" type="text" autocomplete="off" />
</form>

<a class="buttonPrimary floatRight clear-left save">Save</a>
<a class="secondaryLink floatRight cancel">Cancel</a>
<div class="error floatRight hidden">
	<img src="${commonStaticUrl}/images/warning.png">
	<span class="errorMsg"></span>
</div>

<script src="${context}/js/admin-console/app-config/modals/edit-alert-header-modal.js" type="text/javascript"></script>