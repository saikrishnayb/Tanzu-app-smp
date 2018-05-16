<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<p>The Component <strong>${componentName}</strong> is still used in the following Rules:</p><br>
<ul>
<c:forEach items="${ruleMaster}" var="ruleMaster">
<li>
 	${ruleMaster.ruleName} <br>
 </li>
</c:forEach>
</ul>
