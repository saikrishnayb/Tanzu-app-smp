<html>
<head>
	<title>SMC Error!</title>
	<%@ include file="../../jsp/jsp-fragment/global/default-head-block.jsp" %>
</head>
<body>
	<%@ include file="../../jsp/jsp-fragment/global/header.jsp" %>
	<div id="mainContent">
		<%-- Provide a default value for the error message --%>
		<c:if test="${empty errorMessage}">
			<c:set var="errorMessage" value="Hmm, something went wrong.  See if you could try again." />
		</c:if>
		
		<%-- Either include the sidebar or not, depending on whether we're on a page that has one. --%>
		<c:choose>
			<c:when test="${sidebarExists}">
				<c:import url="../../jsp/jsp-fragment${leftNavDirectory}/left-nav.jsp"/>
				<div class="leftNavAdjacentContainer">
					<h1 class="pageTitle">${errorMessage}</h1>
				</div>
			</c:when>
			<c:otherwise>
				<h1 class="pageTitle">${errorMessage}</h1>
			</c:otherwise>
		</c:choose>
	</div>
	<%@ include file="../../jsp/jsp-fragment/global/footer.jsp" %>
</body>
</html>