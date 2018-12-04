<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="editForm">
	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Tab:</label></span>
		<span class="info-span">${searchTemplate.tabName}</span>
	</div>
	
	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Template Name:</label></span>
		<span class="info-span">${searchTemplate.templateName}</span>
		<span class="edit-template-name"><a>Edit Template Name</a></span>
	</div>
	
	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Default For This Tab:</label></span>
		<span class="info-span">
			<select name="defaultForTab">
				<option value="YES">YES</option>
				<option value="NO">NO</option>
			</select>
		</span>
	</div>
	
	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Display Sequence:</label></span>
		<span class="info-span"><input type="text" name="displaySequence" value="${searchTemplate.displaySequence}"/></span>
	</div>
	
	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Visibility:</label></span>
		<span class="info-span">
			<select name="visibility">
				<option value="BOTH">BOTH</option>
				<option value="PENSKE">PENSKE</option>
				<option value="VENDOR">VENDOR</option>
			</select>
		</span>
	</div>
	
	<span class="primary-functions">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary save-template">Save</a>
	</span>
	
	<input type="hidden" name="templateId" value="${templateId}" />
	
</form>


<!-- Scripts -->
<script src="${baseUrl}/js/admin-console/app-config/modals/edit-template-modal.js" type="text/javascript"></script>