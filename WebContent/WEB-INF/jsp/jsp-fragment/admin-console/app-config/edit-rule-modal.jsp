<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="edit-rule-form">
	<span class="floatLeft clear-left errorMsg">* indicates a required field.</span>
	<input name="dynamicRuleId"  id="editDynamicRuleId"  type="hidden"/>
	
	<label class="floatLeft clear-left width-60">Corp</label>
	<select class="floatLeft width-100" tabindex=1 name="corpCode">
		<option value="">Select...</option>
		<c:forEach var="corpCode" items="${corpCodes}">
		<option value="${corpCode}">${corpCode}</option>
		</c:forEach>
	</select>
	
	<label class="floatLeft clear-left width-60">Make</label>
	<select class="floatLeft width-300" tabindex=2 name="manufacturer">
		<option value="">Select...</option>
		<c:forEach var="make" items="${makes}">
		<option value="${make.abbreviation}">${make.abbreviation} - ${make.makeName}</option>
		</c:forEach>
	</select>
	
	<label class="floatLeft clear-left width-60">Model</label>
	<select class="floatLeft width-300" tabindex=3 name="model">
		<option value="">Select...</option>
		<c:forEach var="model" items="${models}">
		<option value="${model}">${model}</option>
		</c:forEach>
	</select>
	
	<label class="floatLeft clear-left width-60">Status</label>
	<select class="floatLeft width-300" tabindex=4 name="status">
		<option value="A">Active</option>
			<option value="I">InActive</option>
	</select>
	<label class="floatLeft clear-left width-60">Year <span class="errorMsg">*</span></label>
	<input class="floatLeft width-100" type="text" tabindex=5 name="modelYear" maxlength="4" autocomplete="off" />
	
	<label class="floatLeft clear-left width-60">Priority <span class="errorMsg">*</span></label>
	<input class="floatLeft width-100" type="text" tabindex=6 name="priority" autocomplete="off" value="${maxPriority}"/>
	
	<div class="floatRight">
		<a class="secondaryLink floatLeft cancel-modal margin-upper-right" tabindex=7>Cancel</a>
		<a class="buttonPrimary  clear-left save" tabindex=8>Save</a>
       </div>
	<div class="error floatRight margin-upper-right hidden">
		<img src="${commonStaticUrl}/images/warning.png">
		<span class="errorMsg"></span>
	</div>
</form>

<script src="${baseUrl}/js/admin-console/app-config/modals/edit-rule-modal.js" type="text/javascript"></script>