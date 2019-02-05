<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%-- This page is only for display if there is an error when displaying the outer frame. Typically, this is only at login time. --%>
<html>
	<head>
		<%@ include file="includes/head-block.jspf"%>
	</head>
</head>
<body onload="hideLoading();" style="margin:4px;" >
	<%@ include file="includes/header.jsp"%>
	<%@ include file="includes/navigation.jspf"%>   
	<div id="mainContent">
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
	</div>			
</body>
<%@ include file="includes/footer.jspf"%>
</html>
