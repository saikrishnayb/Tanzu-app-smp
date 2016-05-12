<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
*******************************************************************************
*
* @Author 		: 502019110
* @Version 	: 1.0
* @FileName :Global Error.jsp
* @Date Created: Nov 21, 2012
* @Date Modified : 
* @Modified By : 
* @Contact 	:
* @Description : This JSP is used if there is any uncaught exception thrown from the application
* @History		:
*
******************************************************************************
--%>
<html>
<head>
<c:set var="context" scope="request"><c:out value="${pageContext.request.contextPath}" /></c:set>
<jsp:include page="/WEB-INF/jsp/includes/header.jspf"/>			
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragma" content="no-cache" />
<title>Global Error Page</title>		
</head>
<body onload="parent.hideLoading();">
<jsp:include page="/WEB-INF/jsp/includes/navigation.jspf"/>   
<div id="mainContent">
	<form:form  method="post" modelAttribute="errorModel">
						<b>						
									<span style="float: center;color: #CC0000" ><strong>
										<c:out value="${errorModel.message}" />
										<c:if test="${not empty errorModel.message}">
											An Error Occurred.  
										</c:if>
										<c:if test="${not empty USER_NOT_FOUND}">
											${USER_NOT_FOUND }
										</c:if>
										<c:if test="${not empty SEC_FUNCTION_NOT_FOUND}">
											${SEC_FUNCTION_NOT_FOUND }
										</c:if>
										<c:if test="${not empty ASSOCIATED_VENDORS_NOT_FOUND}">
											${ASSOCIATED_VENDORS_NOT_FOUND }
										</c:if>
										
									</strong>
									<br/> 
									Please Contact Support @ ${supportNum} for further assistance.
									</span> 
									<br>
						</b>
	</form:form>
</div>			
</body>
<jsp:include page="/WEB-INF/jsp/includes/footer.jspf"/>

