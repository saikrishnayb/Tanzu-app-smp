<html>
<head>
	<title>SMC Error!</title>
	<%@ include file="../../jsp/jsp-fragment/global/default-head-block.jsp" %>
</head>
<body>
	<%@ include file="../../jsp/jsp-fragment/global/header.jsp" %>
	<div id="mainContent">
	<c:if test="${sidebarExists}">
		<c:import url="../../jsp/jsp-fragment${leftNavDirectory}/left-nav.jsp"/>
		<div class="leftNavAdjacentContainer">
	</c:if>
		<c:choose>
			<c:when test="${empty errorMessage}">
				<h1 class="pageTitle">Hmm, something went wrong.  See if you could try again.</h1>
			</c:when>
			<c:otherwise>
				<h1 class="pageTitle">${errorMessage}</h1>
			</c:otherwise>
		</c:choose>
	<c:if test="${sidebarExists}">
	</div>
	</c:if>
	</div>
	<%@ include file="../../jsp/jsp-fragment/global/footer.jsp" %>
</body>
</html>