<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<form id="add-delay-reason-form" action="add-delay-reason.htm" method="post">
	<div class="line">
		<label>Delay Reason</label>
		<input type="text" tabindex=1 class="edit-input input alpha alpha-numeric" id="add-delay-reason-name" name="reasonName" value=""/>
	</div>
</form>
<div class="blank"></div>
<div class="buttonLine">
	<div class="hidden errorDiv" id="error-message">
		<img src="${commonStaticUrl}/images/warning.png"></img>
		<span id="message-span" class="errorMsg"></span>
	</div>
	<div class="float-right">
		<a class="cancel" tabindex=2>Cancel</a>
		<a class="buttonPrimary add-delay-reason-save" tabindex=3>Save</a>
	</div>
</div>