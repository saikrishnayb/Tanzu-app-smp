<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
	    <title>SMC Notifications</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    <!--  CSS -->
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/fixedMenu_style1.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="${baseUrl}/css/admin-console/app-config/notifications.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
			
				<!-- Holds notification content in the table. Displays by escalation group. -->
				<table id="notification-table">
					<thead>
						<tr>
							<th></th>
							<th>Alert Metric</th>
							<th>Escalation Group</th>
							<th>Compliance Type</th>
							<th>Compliance Count</th>
							<th>Escalation 1 Days</th>
							<th>Escalation 1 Contacts</th>
							<th>Escalation 2 Days</th>
							<th>Escalation 2 Contacts</th>
							<th>Escalation 3 Days</th>
							<th>Escalation 3 Contacts</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${notificationsList}" var="notification">
							<c:if test="${notification.visibilityVendor == 1}">
								<tr class="notification-row">
									<td class="editable centerAlign">
										<a class="edit-notification">Edit</a>
										<input type="hidden" value="${notification.notificationId}" class="notification-id" />
									</td>
									<td class="alert-metric">${notification.alertMetric}</td>
									<td class="escalation-group">
										<input type="hidden" class="visibility" value="VENDOR" />
										Vendor
									</td>
									<td class="compliance-type">${notification.complianceType}</td>
									<td class="compliance-count">${notification.complianceCount}</td>
									<td class="escalation-1-days">${notification.escalationOneDays}</td>
									<td>
										[
										<c:forEach items="${notification.vendorEsc1Parties}" var="escOneContact" varStatus="status">
											${escOneContact.contact}
											<c:if test="${!status.last}">
												,
											</c:if> 
										</c:forEach>
										]
									</td>
									<td class="escalation-2-days">${notification.escalationTwoDays}</td>
									<td>
										[
										<c:forEach items="${notification.vendorEsc2Parties}" var="escTwoContact" varStatus="status">
											${escTwoContact.contact}
											<c:if test="${!status.last}">
											,
											</c:if> 
										</c:forEach>
										]
									</td>
									<td class="escalation-3-days">${notification.escalationThreeDays}</td>
									<td>
										[
										<c:forEach items="${notification.vendorEsc3Parties}" var="escThreeContact" varStatus="status">
											${escThreeContact.contact}
											<c:if test="${!status.last}">
												,
											</c:if> 
										</c:forEach>
										]
									</td>
								</tr>
							</c:if>
							
							<c:if test="${notification.visibilityPurchasing == 1}">
								<tr class="notification-row">
									<td class="editable centerAlign">
										<a class="edit-notification">Edit</a>
										<input type="hidden" value="${notification.notificationId}" class="notification-id" />
									</td>
									<td class="alert-metric">${notification.alertMetric}</td>
									<td class="escalation-group">
										<input type="hidden" class="visibility" value="PURCHASING" />
										Purchasing
									</td>
									<td class="compliance-type">${notification.complianceType}</td>
									<td class="compliance-count">${notification.complianceCount}</td>
									<td class="escalation-1-days">${notification.escalationOneDays}</td>
									<td>
										[
										<c:forEach items="${notification.purchasingEsc1Parties}" var="escOneContact" varStatus="status">
											${escOneContact.contact}
											<c:if test="${!status.last}">
												,
											</c:if> 
										</c:forEach>
										]
									</td>
									<td class="escalation-2-days">${notification.escalationTwoDays}</td>
									<td>
										[
										<c:forEach items="${notification.purchasingEsc2Parties}" var="escTwoContact" varStatus="status">
											${escTwoContact.contact}
											<c:if test="${!status.last}">
											,
											</c:if> 
										</c:forEach>
										]
									</td>
									<td class="escalation-3-days">${notification.escalationThreeDays}</td>
									<td>
										[
										<c:forEach items="${notification.purchasingEsc3Parties}" var="escThreeContact" varStatus="status">
											${escThreeContact.contact}
											<c:if test="${!status.last}">
												,
											</c:if> 
										</c:forEach>
										]
									</td>
								</tr>
							</c:if>
							
							
							<c:if test="${notification.visibilityPlanning == 1}">
								<tr class="notification-row">
									<td class="editable centerAlign">
										<a class="edit-notification">Edit</a>
										<input type="hidden" value="${notification.notificationId}" class="notification-id" />
									</td>
									<td class="alert-metric">${notification.alertMetric}</td>
									<td class="escalation-group">
										<input type="hidden" class="visibility" value="PLANNING" />
										Planning
									</td>
									<td class="compliance-type">${notification.complianceType}</td>
									<td class="compliance-count">${notification.complianceCount}</td>
									<td class="escalation-1-days">${notification.escalationOneDays}</td>
									<td>
										[
										<c:forEach items="${notification.planningEsc1Parties}" var="escOneContact" varStatus="status">
											${escOneContact.contact}
											<c:if test="${!status.last}">
												,
											</c:if> 
										</c:forEach>
										]
									</td>
									<td class="escalation-2-days">${notification.escalationTwoDays}</td>
									<td>
										[
										<c:forEach items="${notification.planningEsc2Parties}" var="escTwoContact" varStatus="status">
											${escTwoContact.contact}
											<c:if test="${!status.last}">
											,
											</c:if> 
										</c:forEach>
										]
									</td>
									<td class="escalation-3-days">${notification.escalationThreeDays}</td>
									<td>
										[
										<c:forEach items="${notification.planningEsc3Parties}" var="escThreeContact" varStatus="status">
											${escThreeContact.contact}
											<c:if test="${!status.last}">
												,
											</c:if> 
										</c:forEach>
										]
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		
		<!-- Notification Modal -->
		<div id="edit-notification" class="modal edit-notification-modal" title='Notification Setup'>
			
		</div>
		
		<%@ include file="../../../jsp/jsp-fragment/global/footer.jsp" %>
	</body>
	
	<!-- Javascript Files -->
	<script src="${baseUrl}/js/admin-console/app-config/notifications.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery.fixedMenu.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/common.js" type="text/javascript"></script>
</html>