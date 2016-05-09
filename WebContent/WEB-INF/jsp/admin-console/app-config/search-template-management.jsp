<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Template Management</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
		<link href="${context}/css/admin-console/app-config/search-template-management.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			
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
								<c:if test="${searchTemplate.visibilityPenske == 1 and searchTemplate.visibilityVendor == 0}">PENSKE</c:if>
								<c:if test="${searchTemplate.visibilityPenske == 0 and searchTemplate.visibilityVendor == 1}">VENDOR</c:if>
								<c:if test="${searchTemplate.visibilityPenske == 1 and searchTemplate.visibilityVendor == 1}">BOTH</c:if>
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
		
		<%@ include file="../../../jsp/jsp-fragment/global/footer.jsp" %>
	</body>
	
	<!-- Scripts -->
	<script src="${context}/js/admin-console/app-config/search-template-management.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery.fixedMenu.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/common.js" type="text/javascript"></script>
</html>