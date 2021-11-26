<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>Component Management</title>
	
	<%@ include file="../../global/v2/header.jsp"%>

</head>

<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../global/navigation/admin-console/components/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<div class="table_div">

				<table id="component-management-table">
					<thead>
						<tr>
							<th>Group</th>
							<th>Sub-Group</th>
							<th>Sub Component</th>
							<th>Visible</th>
							<th>Allow Duplicates</th>
							<th>Payment Holds</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${componentList}" var="component">
							<c:set var="holdPayments" value="${holdPaymentsByCompId.get(component.componentId)}" />
							<tr class="component-row" data-component-id="${component.componentId}" data-component-group-number="${component.componentGroupNumber}">
								<td>${component.componentGroup}</td>
								<td>${component.subGroup}</td>
								<td>${component.subComponentName}</td>
								<td><input type="checkbox" class="visible-component-check" <c:if test="${component.visible}">checked disabled</c:if>></td>
								<td><input type="checkbox" class="allow-duplicate-check" <c:if test="${component.allowDuplicates}">checked</c:if> <c:if test="${component.visible == false}">disabled</c:if>></td>
								<td><a class="secondaryLink hold-payment-link">Payment Holds<c:if test="${not empty holdPayments}"> (${fn:length(holdPayments)})</c:if></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>

		</div>

		<div id="component-modal" class="component-modal modal"></div>

	</div>


	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/components/component-management.js" type="text/javascript"></script>


</body>

</html>
