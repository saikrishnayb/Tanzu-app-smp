<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<span class="msg">Are you sure you want to delete the following Delay Reason(s)?  This will also delete any Delays that contain this Reason.</span>
<div class="blank"></div>
<div class="blank"></div>
<ul id="delete-list-data">
	<c:forEach items="${reason}" var="delayReason">
	<input type="hidden" class="delay-type-id" value="${delayReason.typeId}"/>
	<input type="hidden" class="delay-reason-id" value="${delayReason.reasonId}"/>
	<li class="delay-reason"><span class="wide-label"><label>Delay Reason:</label></span>${delayReason.delayReason}</li>
	</c:forEach>
</ul>
<div class="blank"></div>
<div class="blank"></div>
<div class="buttonLine">
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary delete-delay">Yes, Delete</a>
	</div>
</div>