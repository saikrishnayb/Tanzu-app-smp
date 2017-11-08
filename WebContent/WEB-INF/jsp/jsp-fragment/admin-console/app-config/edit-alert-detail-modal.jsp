<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />

<form id="edit-detail-form">
	<span class="floatLeft clear-left errorMsg">* indicates a required field.</span>
	
	<input name="alertId" id="alert-id" type="hidden" />
	<input name="alertType" type="hidden" />

	<label class="floatLeft clear-left width-150">Tab</label>
	<span id="tab-name" class="floatLeft"></span>
	
	<label class="floatLeft clear-left width-150">Header Name</label>
	<span id="header-name" class="floatLeft header-name"></span>
	
	<label class="floatLeft clear-left width-150">Alert Name <span class="errorMsg">*</span></label>
	<span id="alert-name" class="floatLeft alert-change"></span>
	<input id="alert-name-change" class="floatLeft alert-change edit-field width-300 margin-left-fix" name="alertName" maxLength="100" autocomplete="off" />
	<a id="edit-alert-name" class="bold floatLeft secondaryLink alert-change">Edit Alert Name</a>
	<a id="save-alert-name" class="bold floatLeft secondaryLink alert-change hidden">Save Name</a>
	<a id="cancel-edit-alert-name" class="bold floatLeft secondaryLink alert-change hidden">Cancel</a>
	
	<label class="floatLeft clear-left width-150">Associated Search Template <span class="errorMsg">*</span></label>
	<span id="template-name" class="floatLeft template-change"></span>
	<select id="template-name-change" class="floatLeft template-change edit-field margin-left-fix" name="templateId">
		<option value=""></option>
		<c:forEach var="template" items="${templates}">
			<option value="${template.templateId}">${template.templateName}</option>
		</c:forEach>
	</select>
	<a id="edit-template-name" class="bold floatLeft secondaryLink template-change">Edit Association</a>
	<a id="save-template-name" class="bold floatLeft secondaryLink template-change hidden">Save Association</a>
	<a id="cancel-edit-template-name" class="bold floatLeft secondaryLink template-change hidden">Cancel</a>
	
	<label class="floatLeft clear-left width-150">Hover Over Help</label>
	<input class="floatLeft width-300 margin-left-fix" tabindex=1 name="helpText" type="text" maxLength="100" autocomplete="off" />
	
	<label class="floatLeft clear-left width-150">Display Sequence <span class="errorMsg">*</span></label>
	<input class="floatLeft margin-left-fix width-100" tabindex=2 name="displaySequence" type="text" autocomplete="off" />
	
	<label class="floatLeft clear-left width-150">Visibility <span class="errorMsg">*</span></label>
	<select class="floatLeft margin-left-fix width-100" tabindex=3 name="visibility">
		<option value="">Select...</option>
		<option value="1">Vendor</option>
		<option value="2">Penske</option>
		<option value="3">Both</option>
	</select>
</form>

    <div class="floatRight">
	  <a class="secondaryLink floatLeft cancel margin-upper-right" tabindex=3>Cancel</a>
	   <a class="buttonPrimary  clear-left save" tabindex=4>Save</a>
     </div>
<div class="error floatRight hidden">
	<img src="${commonStaticUrl}/images/warning.png">
	<span class="errorMsg"></span>
</div>

<script src="${context}/js/admin-console/app-config/modals/edit-alert-detail-modal.js" type="text/javascript"></script>