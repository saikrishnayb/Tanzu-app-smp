<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
	    <title>SMC Template Management</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/search-template-management.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<table id="templateTable" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Tab</th>
							<th>Search Template</th>
							<th>Display Sequence</th>
							<th>Visibility</th>
							<th>Default For Tab</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="searchTemplate" items="${searchTemplates}">
						<tr class="template-row">
							<td class="editable centerAlign">
								<a class="rightMargin edit-template">Edit</a>
								<input type="hidden" value="${searchTemplate.templateId}" class="template-id" />
							</td>
							<td>
								${searchTemplate.tabName}
								<input type="hidden" value="${searchTemplate.tabId}" class="tab-id" />
							</td>
							<td>${searchTemplate.templateName}</td>
							<td>${searchTemplate.displaySequence}</td>
							<td>
								<c:if test="${searchTemplate.visibilityPenske == 'Y' and searchTemplate.visibilityVendor == 'N'}">PENSKE</c:if>
								<c:if test="${searchTemplate.visibilityPenske == 'N' and searchTemplate.visibilityVendor == 'Y'}">VENDOR</c:if>
								<c:if test="${searchTemplate.visibilityPenske == 'Y' and searchTemplate.visibilityVendor == 'Y'}">BOTH</c:if>
							</td>
							<td>
								<c:if test="${searchTemplate.defaultForTab == 1}">YES</c:if>
								<c:if test="${searchTemplate.defaultForTab == 0}">NO</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div id="edit-template" class="modal">
					
				</div>
			</div>
		</div> 
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/search-template-management.js" type="text/javascript"></script>
	</body>
</html>