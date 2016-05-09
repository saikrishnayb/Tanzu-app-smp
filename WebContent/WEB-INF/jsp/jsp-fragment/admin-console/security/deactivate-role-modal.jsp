<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />

<input type="hidden" name="role-id" value="${role.roleId}" />

<div id="message-text" class="floatLeft clear-left">
	<h3 class="floatLeft clear-left">This operation cannot be undone!</h3>
</div>

<span class="floatLeft clear-left">Role Name:</span>
<ul class="floatLeft clear-left margin-bottom-10">
	<li>${role.roleName}</li>
</ul>
<c:if test="${not empty subRoles }">
	<span class="floatLeft clear-left">Below Child Role also will get deleted</span>
	<ul class="floatLeft clear-both margin-bottom-10">
		<c:forEach items="${subRoles}" var="childRole">
			<c:if test="${role.roleId ne childRole.roleId}">
				<li>${childRole.roleName}</li>
			</c:if>
		</c:forEach>
	</ul>
</c:if>
<div class="floatRight clear-left button-div">
	<a class="buttonPrimary floatRight confirm">Confirm</a>
	<a class="secondaryLink floatRight cancel">Cancel</a>
</div>

<script src="${context}/js/admin-console/security/deactivate-role-modal.js" type="text/javascript"></script>