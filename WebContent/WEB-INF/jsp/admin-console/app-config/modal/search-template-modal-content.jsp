<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="editForm">

	<c:if test="${not empty searchTemplate.tabName}">
		<div class="edit-section">
			<span class="label-span"><label class="edit-label">Tab:</label></span>
			<span class="info-span">${searchTemplate.tabName}<input type="hidden" name="tabId" value="${searchTemplate.tabId}" /></span>
		</div>
	</c:if>

	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Template Name:</label></span>
		<span class="info-span template-name">${searchTemplate.templateName}<input type="hidden" id="template-name" name="templateName" value="${searchTemplate.templateName}" /></span>
		<span class="edit-template-name"><a class="edit-name-link">Edit Template Name</a></span>
	</div>
	
	<c:if test="${not empty searchTemplate.tabName}">
		<div class="edit-section">
			<span class="label-span"><label class="edit-label">Default For Tab*:</label></span>
			<span class="info-span">
				<select name="defaultForTab" tabindex=1 <c:if test="${searchTemplate.defaultForTab == 1}">disabled</c:if>>
					<option value="NO" <c:if test="${searchTemplate.defaultForTab == 0}">selected</c:if>>NO</option>
					<option value="YES" <c:if test="${searchTemplate.defaultForTab == 1}">selected</c:if>>YES</option>
				</select>
			</span>
		</div>
	</c:if>
	
	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Display Sequence:</label></span>
		<span class="info-span"><input type="text" class="display-sequence-text" tabindex=2 name="displaySequence" value="${searchTemplate.displaySequence}"/></span>
		<span class="error">
			<img class="error-img" src="${commonStaticUrl}/images/warning.png" />
			<span class="errorMsg">Must Be a Positive Number</span>
		</span>
	</div>
	
	<div class="edit-section">
		<span class="label-span"><label class="edit-label">Visibility:</label></span>
		<span class="info-span">
			<select name="visibility" tabindex=3>
				<option value="BOTH" <c:if test="${searchTemplate.visibilityPenske == 'Y' and searchTemplate.visibilityVendor == 'Y'}">selected</c:if>>Both</option>
				<option value="PENSKE" <c:if test="${searchTemplate.visibilityPenske == 'Y' and searchTemplate.visibilityVendor == 'N'}">selected</c:if>>Penske</option>
				<option value="VENDOR" <c:if test="${searchTemplate.visibilityPenske == 'N' and searchTemplate.visibilityVendor == 'Y'}">selected</c:if>>Vendor</option>
			</select>
		</span>
	</div>
	
	<c:if test="${not empty searchTemplate.tabName}">
		<div>
			<c:if test="${searchTemplate.defaultForTab == 1}">
				<span>*A search template may not be removed as the default if it is currently the default. This dropdown is disabled.</span>
			</c:if>
			<c:if test="${searchTemplate.defaultForTab == 0}">
				<span>*Setting a new default for this tab will remove the old default template.</span>
			</c:if>
		</div>
	</c:if>
	
	<span class="primary-functions">
		<a class="cancel" tabindex=4>Cancel</a>
		<a class="buttonPrimary save-template" tabindex=5>Save</a>
	</span>
	
	<input type="hidden" id="templateId" name="templateId" value="${searchTemplate.templateId}" />
	
</form>


<!-- Scripts -->
<script src="${baseUrl}/js/admin-console/app-config/modal/edit-search-template-modal.js" type="text/javascript"></script>