<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Template Management</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
		<link href="${context}/css/admin-console/app-config/template-management.css" rel="stylesheet" type="text/css"/>
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
							<th>Default for Tab</th>
							<th>Visibility</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="template" items="${templates}">
						<tr class="template-row">
							<td class="editable centerAlign">
								<a class="rightMargin edit-template">Edit</a>
								<input type="hidden" value="${template.templateId}" class="template-id" />
							</td>
							<td>${template.tabName}</td>
							<td>${template.templateName}</td>
							<td>${template.displaySequence}</td>
							<td>
								<c:if test="${template.defaultForTab}">YES</c:if>
								<c:if test="${not template.defaultForTab}">NO</c:if>
							</td>
							<td>
								<c:if test="${template.visibilityPenske and not template.visibilityVendor}">PENSKE</c:if>
								<c:if test="${not template.visibilityPenske and template.visibilityVendor}">VENDOR</c:if>
								<c:if test="${template.visibilityPenske and template.visibilityVendor}">BOTH</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div id="edit-template">
					
				</div>
			</div>
		</div> 
		
		<%@ include file="../../../jsp/jsp-fragment/global/footer.jsp" %>
	</body>
	
	<!-- Scripts -->
	<script src="${context}/js/admin-console/app-config/template-management.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery.fixedMenu.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/common.js" type="text/javascript"></script>
</html>