<html>
<head>
	<title>SMC Error!</title>
	<%@ include file="../../global/v1/header.jsp" %>
</head>
<body>
	<%@ include file="../../global/navigation/sub-nav.jsp" %>
	<div id="mainContent">
		<%-- Provide a default value for the error message --%>
		<c:if test="${empty errorMessage}">
			<c:set var="errorMessage" value="Hmm, something went wrong.  See if you could try again." />
		</c:if>
		
		<h1 class="pageTitle">${errorMessage}</h1>
		If the problem persists, please contact support @ ${supportNum} for further assistance.
	</div>
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
</body>
</html>