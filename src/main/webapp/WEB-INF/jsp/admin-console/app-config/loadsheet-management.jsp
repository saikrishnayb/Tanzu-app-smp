<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
	    <title>SMC Loadsheet Management</title>
	    
	   <%@ include file="../../global/v2/header.jsp"%>
	   
		<link href="${baseUrl}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../global/navigation/sub-nav.jsp"%>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../global/navigation/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
				<%@ include file="../../global/v2/page-error-container.jsp"%>
				<span class="floatRight addRow push-right">
				</span>
				<div><h1>Loadsheet Management</h1></div>
				<table id="loadsheet-table" style="width:100%;">
					<thead>
						<tr>
							<th></th> 
							<th>Category</th>
							<th>Type</th>
							<th>Uses Default</th>
							<th>Edit by</th>
							<th>Edit Date</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${loadsheets}" var="loadsheet">
						<tr>
							<td class="editable centerAlign width">
							 <div class="dropdown">
					            <a class="bootStrapDropDown dropdown-toggle" data-toggle="dropdown">
					              Actions
					              <span class="caret"></span>
					            </a>
					            <ul class="dropdown-menu">
					            <c:choose>
					              <c:when test="${loadsheet.usesDefault eq 'Y'}">
					                <li><a id="componentRulesLabel" href="get-loadsheet-components.htm?categoryId=${loadsheet.catTypeId}&category=${loadsheet.category} &type=${loadsheet.type} &viewMode=Y&compRqstdFrom=LOADSHEET_MANAGEMENT">View Component Rules</a></li>
					                <li><a href="get-loadsheet-sequence.htm?categoryId=${loadsheet.catTypeId}&category=${loadsheet.category} &type=${loadsheet.type} &viewMode=Y">View Loadsheet Sequence</a></li>
					              </c:when>
					              <c:otherwise>
					                <li><a id="componentRulesLabel" href="get-loadsheet-components.htm?categoryId=${loadsheet.catTypeId}&category=${loadsheet.category} &type=${loadsheet.type} &viewMode=N&compRqstdFrom=LOADSHEET_MANAGEMENT" >Configure Component Rules</a></li>
					                <li><a href="get-loadsheet-sequence.htm?categoryId=${loadsheet.catTypeId}&category=${loadsheet.category} &type=${loadsheet.type} &viewMode=N">Set Loadsheet Sequence</a></li>
					              </c:otherwise>
					            </c:choose>
					            </ul>
					          </div>
							</td>
							<td>${loadsheet.category}</td>
							<td>${loadsheet.type}</td>
							<td>
							<c:choose>
							 <c:when test="${loadsheet.usesDefault eq 'Y' }">Y</c:when>
						     <c:otherwise>N</c:otherwise>
							</c:choose>
							</td>
							<td>${loadsheet.editedBy}</td>
							<td>${loadsheet.fmtEditedDate}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>		
		</div>
		
		<%@ include file="../../global/v2/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/loadsheet-management.js" type="text/javascript"></script>
	</body>
</html>