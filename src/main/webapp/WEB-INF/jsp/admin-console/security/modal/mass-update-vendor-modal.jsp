<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="mass-update-modal" class="modal-content col-xs-12" data-modal-title="Modify Vendor" data-modal-width="520">
	<form id="mass-update-form">
		<span class="floatLeft clear-left errorMsg">* indicates a required field.</span>
		
		<label class="floatLeft clear-left width-250">Excluded from Notifications <span class="errorMsg">*</span></label>
		<select class="floatLeft width-200" name="notificationException">
			<option value="">Select...</option>
			<option value="Y">Yes</option>
			<option value="N">No</option>
		</select>
		
		<label class="floatLeft clear-left width-250">Assigned Vehicle Planning Analyst <span class="errorMsg">*</span></label>
		<select class="floatLeft width-200" name="planningAnalyst.userId">
			<option value="">Select...</option>
			<c:forEach var="analyst" items="${analysts}">
			<option value="${analyst.userId}">${analyst.firstName} ${analyst.lastName}</option>
			</c:forEach>
		</select>
		
		<label class="floatLeft clear-left width-250">Assigned Vehicle Supply Specialist</label>
		<select class="floatLeft width-200" name="supplySpecialist.userId">
			<option value="">Select...</option>
			<c:forEach var="specialist" items="${specialists}">
			<option value="${specialist.userId}">${specialist.firstName} ${specialist.lastName}</option>
			</c:forEach>
		</select>
		
		<div class="floatRight button-div clear-both">
			<a class="buttonPrimary floatRight clear-left save">Save</a>
			<a class="secondaryLink floatRight cancel">Cancel</a>
			<div class="error floatRight hidden">
				<img src="${commonStaticUrl}/images/warning.png">
				<span class="errorMsg"></span>
			</div>
		</div>
	</form>
</div>

<script src="${baseUrl}/js/admin-console/security/mass-update-vendor-modal.js" type="text/javascript"></script>