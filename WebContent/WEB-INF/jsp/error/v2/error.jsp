<!DOCTYPE html>
<html lang="en">
<head>
	<title>Error</title>
	
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/error/v2/error.css" rel="stylesheet">
</head>
<body style="overflow-y:visible;">
	<%@ include file="../../global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="container-fluid">
		<%-- Provide a default value for the error message --%>
		<c:if test="${empty errorMessage}">
			<c:set var="errorMessage">
				Uh oh. Looks like something went wrong back here. Try going back to the <a class="goto-main-page" href="javascript:void(0);">main page</a> 
	        	and trying again. If this keeps happening, please contact support @ ${supportNum}. Sorry for letting you down.
			</c:set>
		</c:if>
		
		<c:set var="errorContent">
			<div class="row row-error">
				<div class="col-xs-4 col-info">
					<h2>
						User: <span class="label label-primary">${currentUser.firstName} ${currentUser.lastName} &#40;${currentUser.sso}&#41;</span>
					</h2>
					<h2>
						Time: <span class="label label-primary label-time">${currentTimeFormatted}</span>
					</h2>
				</div>
	
				<div class="col-xs-7">
					<div class="error-text">
						${errorMessage}
						<c:if test="${not empty validationErrors}">
							<ul>
								<c:forEach items="${validationErrors}" var="validationError">
									<li>${validationError.message}</li>
								</c:forEach>
							</ul>
						</c:if>
						<c:if test="${not empty causeString or not empty stackTraceString}">
							<br/>
							<a class="show-error">Show Error Details</a>
						</c:if>
					</div>
				</div>
			</div>
	
			<div class="row row-exception hidden">
				<div class="col-xs-12 col-exception">
					<div class="alert alert-danger">
						${causeString}<br>
						<div class="exception-string">${stackTraceString}</div>
					</div>
				</div>
			</div>
		</c:set>
		
		<%-- Either include the sidebar or not, depending on whether we're on a page that has one. --%>
		<c:choose>
			<c:when test="${sidebarExists}">
				<c:import url="../../global/navigation${leftNavDirectory}/left-nav.jsp"/>
				<div class="leftNavAdjacentContainer">
					${errorContent}
				</div>
			</c:when>
			<c:otherwise>
				${errorContent}
			</c:otherwise>
		</c:choose>
	</div>

	<%@ include file="../../global/v2/footer.jsp"%>
	<script src="${baseUrl}/js/error/v2/error.js"></script>
</body>
</html>
