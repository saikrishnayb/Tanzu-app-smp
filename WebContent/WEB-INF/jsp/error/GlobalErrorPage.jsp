<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="pragma" content="no-cache" />
	<title>Global Error Page</title>		
</head>
<body onload="hideLoading();">
	<form:form  method="post" modelAttribute="errorModel">
		<span style="float: center;color: #CC0000" >
			<c:if test="${not empty errorModel.message}">
				An Error Occurred.  
			</c:if>
			<c:out value="${errorModel.message}" />
			<br/> 
			Please Contact Support @ ${supportNum} for further assistance.
		</span>
	</form:form>
</body>
</html>