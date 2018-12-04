<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="edit-vendor-form">
	<input name="vendorId" type="hidden" value="${vendor.vendorId}" />
	
	<div class="column firstColumn floatLeft clear-left width-250">
		<label class="floatLeft width-125">Corp Code:</label>
		<span class="floatLeft" id="corpCode">${vendor.corpCode}</span>
		
		<label class="floatLeft width-125">MFR Code:</label>
		<span class="floatLeft" id="manufacturerCode">${vendor.manufacturerCode}</span>
	</div>
	
	<div class="column secondColumn floatLeft width-250">
		<label class="floatLeft width-125">Vendor Name:</label>
		<span class="floatLeft" id="vendorName">${vendor.vendorName}</span>
		
		<label class="floatLeft width-125">Vendor Number:</label>
		<span class="floatLeft" id="vendorNumber">${vendor.vendorNumber}</span>
	</div>
	
	<div class="floatLeft spacer width-full"></div>
	
	<span class="floatLeft clear-left errorMsg">* indicates a required field.</span>
	
	<label class="floatLeft width-250 clear-left">Vendor Notification Exception <span class="errorMsg">*</span></label>
	<select tabindex=1 class="floatLeft width-200" name="notificationException">
		<option value="">Select...</option>
		<option value="Y" <c:if test="${vendor.notificationException eq 'Y'}">selected</c:if>>Yes</option>
		<option value="N" <c:if test="${vendor.notificationException eq 'N'}">selected</c:if>>No</option>
	</select>
	
	<h3 class="section-header floatLeft">Primary Contact Information</h3>
	
	<input class="primary-contact" type="hidden" name="primaryContact.contactType" value="PRIMARY" />
	<input class="primary-contact" type="hidden" name="primaryContact.vendorId" value="${vendor.vendorId}" />
	
	<label class="floatLeft width-250 clear-left">Contact First Name</label>
	<input class="floatLeft width-200 primary-contact" tabindex=2 name="primaryContact.firstName" value="${vendor.primaryContact.firstName}" maxlength="50" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Last Name</label>
	<input class="floatLeft width-200 primary-contact" tabindex=3 name="primaryContact.lastName" value="${vendor.primaryContact.lastName}" maxlength="50" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Phone Number</label>
	<input class="floatLeft width-200 primary-contact" tabindex=4 name="primaryContact.phoneNumber" value="${vendor.primaryContact.phoneNumber}" maxlength="10" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Email</label>
	<input class="floatLeft width-200 primary-contact"  tabindex=5 name="primaryContact.email" value="${vendor.primaryContact.email}" maxlength="100" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Responsibility</label>
	<select class="floatLeft width-200 primary-contact"  tabindex=6  name="primaryContact.responsibility">
		<option value="">Select...</option>
		<option value="1" <c:if test="${vendor.primaryContact.responsibility eq 1}">selected</c:if>>Purchasing</option>
		<option value="2" <c:if test="${vendor.primaryContact.responsibility eq 2}">selected</c:if>>Production</option>
		<option value="3" <c:if test="${vendor.primaryContact.responsibility eq 3}">selected</c:if>>Purchasing &amp; Production</option>
	</select>
	
	<h3 class="section-header floatLeft">Secondary Contact Information</h3>
	
	<input class="secondary-contact" type="hidden" name="secondaryContact.contactType" value="SECONDARY" />
	<input class="secondary-contact" type="hidden" name="secondaryContact.vendorId" value="${vendor.vendorId}" />
	
	<label class="floatLeft width-250 clear-left">Contact First Name</label>
	<input class="floatLeft width-200 secondary-contact" tabindex=7 name="secondaryContact.firstName" value="${vendor.secondaryContact.firstName}" maxlength="50" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Last Name</label>
	<input class="floatLeft width-200 secondary-contact"  tabindex=8 name="secondaryContact.lastName" value="${vendor.secondaryContact.lastName}" maxlength="50" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Phone Number</label>
	<input class="floatLeft width-200 secondary-contact" tabindex=9  name="secondaryContact.phoneNumber" value="${vendor.secondaryContact.phoneNumber}" maxlength="10" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Email</label>
	<input class="floatLeft width-200 secondary-contact" tabindex=10 name="secondaryContact.email" value="${vendor.secondaryContact.email}" maxlength="100" autocomplete="off" />
	
	<label class="floatLeft width-250 clear-left">Contact Responsibility</label>
	<select class="floatLeft width-200 secondary-contact" tabindex=11 name="secondaryContact.responsibility">
		<option value="">Select...</option>
		<option value="1" <c:if test="${vendor.secondaryContact.responsibility eq 1}">selected</c:if>>Purchasing</option>
		<option value="2" <c:if test="${vendor.secondaryContact.responsibility eq 2}">selected</c:if>>Production</option>
		<option value="3" <c:if test="${vendor.secondaryContact.responsibility eq 3}">selected</c:if>>Purchasing &amp; Production</option>
	</select>
	
	<div class="floatLeft spacer width-full"></div>
	
	<label class="floatLeft width-250 clear-left">Annual Agreement <span class="errorMsg">*</span></label>
	<select class="floatLeft width-200" tabindex=12 name="annualAgreement">
		<option value="">Select...</option>
		<option value="Y" <c:if test="${vendor.annualAgreement eq 'Y'}">selected</c:if>>Yes</option>
		<option value="N" <c:if test="${vendor.annualAgreement eq 'N'}">selected</c:if>>No</option>
	</select>
	
	<label class="floatLeft width-250 clear-left">Assigned Vehicle Planning Analyst <span class="errorMsg">*</span></label>
	<select class="floatLeft width-200" tabindex=13 name="planningAnalyst.userId">
		<option value="">Select...</option>
		<c:forEach var="analyst" items="${analysts}">
		<option value="${analyst.userId}" <c:if test="${vendor.planningAnalyst.userId eq analyst.userId}">selected</c:if>>${analyst.firstName} ${analyst.lastName}</option>
		</c:forEach>
	</select>
	
	<label class="floatLeft width-250 clear-left">Assigned Vehicle Supply Specialist</label>
	<select class="floatLeft width-200" tabindex=14 name="supplySpecialist.userId">
		<option value="">Select...</option>
		<c:forEach var="specialist" items="${specialists}">
		<option value="${specialist.userId}" 
		<c:if test="${specialist.userId eq vendor.supplySpecialist.userId }">selected</c:if>>${specialist.firstName} ${specialist.lastName}</option>
		</c:forEach>
	</select>
	
	<div class="floatRight button-div clear-both">
		<a tabindex=15 class="secondaryLink  cancel">Cancel</a>
		<a tabindex=16 class="buttonPrimary  save">Save</a>
		<div class="error floatRight hidden">
			<img src="${commonStaticUrl}/images/warning.png">
			<span class="errorMsg"></span>
		</div>
	</div>
</form>

<script src="${baseUrl}/js/admin-console/security/edit-vendor-modal.js" type="text/javascript"></script>