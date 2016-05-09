<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<form id="edit-modal-form" action="edit-delay-type.htm">
	<div class="line">
		<span class="textLabel rightMargin">Original Name</span>
		<input type="hidden" id="hidden-type-id" value="${type.typeId}" />
		<input type="text" class="edit-modal-input" disabled="disabled" value="${type.delayType}"/>
	</div>
	<div class="line">
		<label>Edited Name</label>
		<input type="text" class="edit-modal-input input alpha alpha-numeric" id="edit-delay-type-name"/>
	</div>
</form>
<div class="blank"></div>
<div class="buttonLine">
	<div class="hidden errorDiv" id="error-message">
		<img src="${commonStaticUrl}/images/warning.png"></img>
		<span id="message-span" class="errorMsg"></span>
	</div>
	<div class="float-right">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary save-edit">Save</a>
	</div>
</div>