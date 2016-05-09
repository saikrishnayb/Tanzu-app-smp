<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"  scope="page" />

<c:if test="${not empty role}">
<ul class="group">
	<li id="role-hierarchy-tree">
	<!-- selected-role -- Add this style to span to highlight the node -->
		<a><span class="role " id="${role.roleId}">${role.roleName}</span></a>
		
		<c:forEach var="role" items="${role.subRoles}">
			
			<c:set var="role" value="${role}" scope="request" />
			
			<jsp:include page="role-hierarchy.jsp" />
		</c:forEach>
	</li>
</ul>
</c:if>

<script src="${context}/js/admin-console/security/role-hierarchy.js" type="text/javascript"></script>