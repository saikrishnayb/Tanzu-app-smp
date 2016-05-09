<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />

<div id="message-text" class="floatLeft clear-left">
	<h3 class="floatLeft clear-left errorMsg">This role cannot be deactivated.</h3>
	<span class="floatLeft clear-left">There are users assigned to this role or one of its sub-roles.</span>
</div>

<span class="floatLeft clear-left">Role Name:</span>
<ul class="floatLeft clear-both margin-bottom-10">
	<li>${role.roleName}</li>
</ul>

<div class="floatRight clearLeft button-div">
	<a class="buttonPrimary cancel">Close</a>
</div>