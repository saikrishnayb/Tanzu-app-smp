<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"  scope="page" />
<script src="${context}/js/overlay/globaloverlay.js" type="text/javascript"></script>
<nav>
	<ul style="margin-top:4px">
		<li style ="display: table-cell;">
			<ul>
				<li><a id="tab-security" href="${pageContext.request.contextPath}/admin-console/security/users.htm" onclick="javascript:loadProcessImage();">Security</a></li>
				<li><a id="tab-components"  href="${pageContext.request.contextPath}/admin-console/components/category-association.htm" onclick="javascript:loadProcessImage();">Components</a></li>
				<li><a id="tab-app-config" href="${pageContext.request.contextPath}/admin-console/app-config/dynamic-rules.htm" onclick="javascript:loadProcessImage();">App Config</a></li>
			</ul>
		</li>
	</ul>
</nav>
