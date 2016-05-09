<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />

<form id="edit-rule-form">
	<span class="floatLeft clear-left errorMsg">* indicates a required field.</span>
	<input name="dynamicRuleId"  id="editDynamicRuleId"  type="hidden"/>
	
	<label class="floatLeft clear-left width-60">Corp</label>
	<select class="floatLeft width-100" name="corpCode">
		<option value="">Select...</option>
		<c:forEach var="corpCode" items="${corpCodes}">
		<option value="${corpCode}">${corpCode}</option>
		</c:forEach>
	</select>
	
	<label class="floatLeft clear-left width-60">Make</label>
	<select class="floatLeft width-300" name="manufacturer">
		<option value="">Select...</option>
		<c:forEach var="make" items="${makes}">
		<option value="${make.abbreviation}">${make.abbreviation} - ${make.makeName}</option>
		</c:forEach>
	</select>
	
	<label class="floatLeft clear-left width-60">Model</label>
	<select class="floatLeft width-300" name="model">
		<option value="">Select...</option>
		<c:forEach var="model" items="${models}">
		<option value="${model}">${model}</option>
		</c:forEach>
	</select>
	
	<label class="floatLeft clear-left width-60">Status</label>
	<select class="floatLeft width-300" name="status">
		<option value="A">Active</option>
			<option value="I">InActive</option>
	</select>
	<label class="floatLeft clear-left width-60">Year <span class="errorMsg">*</span></label>
	<input class="floatLeft width-100" type="text" name="modelYear" maxlength="4" autocomplete="off" />
	
	<label class="floatLeft clear-left width-60">Priority <span class="errorMsg">*</span></label>
	<input class="floatLeft width-100" type="text" name="priority" autocomplete="off" value="${maxPriority}"/>
	
	<a class="buttonPrimary floatRight clear-left save">Save</a>
	<a class="secondaryLink floatRight cancel-modal margin-upper-right">Cancel</a>
	<div class="error floatRight margin-upper-right hidden">
		<img src="${commonStaticUrl}/images/warning.png">
		<span class="errorMsg"></span>
	</div>
</form>

<script src="${context}/js/admin-console/app-config/modals/edit-rule-modal.js" type="text/javascript"></script>