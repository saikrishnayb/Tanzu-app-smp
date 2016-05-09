<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<span class="msg">Are you sure you want to delete the following Delay(s)?</span>
<div class="blank"></div>
<div class="blank"></div>
<input type="hidden" class="delay-id" value="${delay.delayId}"/>
<ul id="delete-list-data">
	<li class="delay-date-type"><span class="wide-label"><label>Date Type</label></span>${delay.dateType}</li>
	<li class="delay-po-category"><span class="wide-label"><label>PO Category</label></span>${delay.poCategory}</li>
	<li class="delay-type"><span class="wide-label"><label>Delay Type</label></span>${delay.delayType}</li>
	<li class="delay-reason"><span class="wide-label"><label>Delay Reason</label></span>${delay.delayReason}</li>
</ul>
<div class="blank"></div>
<div class="blank"></div>
<div class="buttonLine">
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary delete-delay">Yes, Delete</a>
	</div>
</div>