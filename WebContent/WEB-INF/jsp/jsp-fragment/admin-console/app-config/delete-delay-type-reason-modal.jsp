<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<span class="msg">Are you sure you want to delete the following Delay Association(s)?  This will also delete any Delays that contain this Association.</span>
<div class="blank"></div>
<div class="blank"></div>
<input type="hidden" class="delay-type-reason-id" value="${typeReasonId}"/>

<ul id="delete-list-data">
	<li class="delay-type"><span class="wide-label"><label>Delay Type&nbsp;&nbsp;&nbsp;&nbsp;:</label></span>${type}</li>
	<li class="delay-reason"><span class="wide-label"><label>Delay Reason:</label></span>${reason}</li>
</ul>

<div class="blank"></div>
<div class="blank"></div>
<div class="buttonLine">
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary delete-delayTypeReson">Delete</a>
	</div>
</div>