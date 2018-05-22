<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<p>The component <strong>${componentName}</strong> is still used in the following rules:</p><br>
<ul>
<c:forEach items="${ruleList}" var="rule">
<li>
 	${rule}<br>
 </li>
</c:forEach>
</ul>
