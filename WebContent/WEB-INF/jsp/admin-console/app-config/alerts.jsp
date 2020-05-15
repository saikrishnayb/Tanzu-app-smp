<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
	    <title>SMC Alerts</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/alerts.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<!-- Alert/Alert Header Datatable -->
				<table id="alert-table" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Type</th>
							<th>Tab</th>
							<th>Display Seq</th>
							<th>Header Name</th>
							<th>Detail (Alert) Name</th>
							<th>Visibility</th>
							<th>Associated Search Template</th>
							<th>Hover Over Help Text</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="alert" items="${alerts}">
						<tr>
							<td class="editable centerAlign">
								<a class="rightMargin edit-alert">Edit</a>
								<input name="alertId" type="hidden" value="${alert.alertId}" />
							</td>
							<td class="alert-type">${alert.alertType}</td>
							<td class="tab-name">${alert.tabName}</td>
							<td class="display-sequence">${alert.displaySequence}</td>
							<td class="header-name">${alert.headerName}</td>
							<td class="alert-name">${alert.alertName}</td>
							<td class="visibility">
								<input name="visibilityPenske" type="hidden" value="${alert.visibilityPenske}" />
								<input name="visibilityVendor" type="hidden" value="${alert.visibilityVendor}" />
								<c:if test="${alert.visibilityPenske eq 1 and alert.visibilityVendor eq 0}">Penske</c:if>
								<c:if test="${alert.visibilityPenske eq 0 and alert.visibilityVendor eq 1}">Vendor</c:if>
								<c:if test="${alert.visibilityPenske eq 1 and alert.visibilityVendor eq 1}">Both</c:if>
							</td>
							<td class="template-name">
								<input type="hidden" name="templateId" value="${alert.templateId}" />
								<span>${alert.templateName}</span>
							</td>
							<td class="help-text">${alert.helpText}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				
				<!-- Alert Header Modal -->
				<div id="edit-header-modal" class="modal">
				<%@ include file="../../../jsp/admin-console/app-config/modal/edit-alert-header-modal.jsp" %></div>
				
				<!-- Alert Detail Modal -->
				<div id="edit-detail-modal" class="modal"></div>
			</div>
		</div> 
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/alerts.js" type="text/javascript"></script>
	</body>
</html>