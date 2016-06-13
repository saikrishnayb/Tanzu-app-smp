<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<form id="edit-modal-form" action="edit-delay-reason.htm">
	<div class="line">
		<span class="textLabel rightMargin">Original Reason</span>
		<input type="hidden" id="hidden-reason-id" value="${reason.reasonId}" />
		<input type="hidden" id="hidden-reason-name" value="${reason.delayReason}"/>
		<input type="text" class="edit-modal-input" disabled="disabled" value="${reason.delayReason}"/>
	</div>
	<div class="line">
		<label>Edited Reason</label>
		<input type="text" class="edit-modal-input alpha alpha-numeric input" id="edit-delay-reason-name" value=""/>
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
		<a class="buttonPrimary edit-delay-reason">Save</a>
	</div>
</div>