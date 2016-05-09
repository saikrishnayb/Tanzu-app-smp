<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<span class="msg">Are you sure you want to delete the following Delay Type(s)?  This will also delete the associated Delay Reason(s) and any Delays that contain this Type.</span>
<div class="blank"></div>
<div class="blank"></div>
<ul id="delete-list-data">
	<c:forEach items="${type}" var="delayType">
	<input type="hidden" class="delay-type-id" value="${delayType.typeId}"/>
	<li class="delay-type"><span class="wide-label"><label>Delay Type:</label></span>${delayType.delayType}</li>
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