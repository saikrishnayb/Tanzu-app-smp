<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />

<div id="add-subject-modal" class="modal">
	<form id="add-subject-form">
		<span class="floatLeft clear-left errorMsg">* indicates a required field</span>
	
		<label class="floatLeft clear-left width-100">Subject <span class="errorMsg">*</span></label>
		<input class="floatLeft width-300" name="subjectName" tabindex=1 type="text" autocomplete="off" maxlength="100" />
		
		<label class="floatLeft clear-left width-100">Department <span class="errorMsg">*</span></label>
		<select class="floatLeft width-300" tabindex=2 name="department">
			<option value="-1">Select...</option>
			<option value="0">Vehicle Planning</option>
			<option value="1">Vehicle Supply</option>
		</select>
		
		<label class="floatLeft clear-left width-100">Type <span class="errorMsg">*</span></label>
		<select class="floatLeft width-300" tabindex=3 name="type">
			<option value="-1">Select...</option>
			<option value="0">Info Request</option>
			<option value="1">Change Request</option>
		</select>
		
		<!-- <a  class="buttonPrimary floatRight clear-left save" tabindex=4>Save</a>
		<a class="secondaryLink floatRight cancel-modal" tabindex=5>Cancel</a> -->
		<div class="floatRight">
		<a class="secondaryLink floatLeft cancel-modal" tabindex="4" ">Cancel</a>
		<a class="buttonPrimary  clear-left save" tabindex="5">Save</a>
       </div>
		<div class="error floatRight hidden">
			<img src="${commonStaticUrl}/images/warning.png">
			<span class="errorMsg"></span>
		</div>
	</form>
</div>

<script src="${context}/js/admin-console/app-config/modals/add-subject-modal.js" type="text/javascript"></script>