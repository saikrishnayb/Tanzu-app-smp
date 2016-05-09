<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />

<div id="edit-subject-modal" class="modal">
	<form id="edit-subject-form">
		<input class="floatLeft" name="subjectId" type="hidden"/>
		<span class="floatLeft clear-left errorMsg">* indicates a required field</span>
		
		<label class="floatLeft clear-left width-100">Subject <span class="errorMsg">*</span></label>
		<input class="floatLeft width-300" name="subjectName" type="text" autocomplete="off" maxlength="100" />
		
		<label class="floatLeft clear-left width-100">Department <span class="errorMsg">*</span></label>
		<select class="floatLeft width-300" name="department">
			<option value="-1">Select...</option>
			<option value="0">Vehicle Planning</option>
			<option value="1">Vehicle Supply</option>
		</select>
		
		<label class="floatLeft clear-left width-100">Type <span class="errorMsg">*</span></label>
		<select class="floatLeft width-300" name="type">
			<option value="-1">Select...</option>
			<option value="0">Info Request</option>
			<option value="1">Change Request</option>
		</select>
		
		<a class="buttonPrimary floatRight clear-left save">Save</a>
		<a class="secondaryLink floatRight cancel-modal">Cancel</a>
		<div class="error floatRight hidden">
			<img src="${commonStaticUrl}/images/warning.png">
			<span class="errorMsg"></span>
		</div>
	</form>
</div>

<script src="${context}/js/admin-console/app-config/modals/edit-subject-modal.js" type="text/javascript"></script>