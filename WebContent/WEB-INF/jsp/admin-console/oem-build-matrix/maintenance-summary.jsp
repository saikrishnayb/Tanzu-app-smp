<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
<head>
<title>OEM Build Matrix</title>
<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
<link href="${baseUrl}/css/admin-console/oem-build-matrix/maintenence-summary.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix-global.css" rel="stylesheet" type="text/css" />
</head>
<body style="overflow-y:visible;">
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<span class="floatRight addRow push-right"> </span>
			<div id="PopupError" style="display:none">
				<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
			</div>
			<h1>Plant Maintenance</h1>

			<table id="body-plant-maint-table" class="plant-view">
				<thead>
					<tr>
						<th class="actionsheader"></th>
						<th>Manufacturer</th>
						<th>City</th>
						<th>State</th>
						<th>Plant Name</th>
						<th>Offline Dates</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${maintenanceSummary}" var="plant">
						<tr>
							<td class="editable centerAlign">
							<div class="dropdown">
								<a class="bootStrapDropDown dropdown-toggle"
									data-toggle="dropdown"> Actions <span class="caret"></span>
								</a>
								<input type="hidden" class="plant-id" value="${plant.plantId}" />
								<ul class="dropdown-menu">
									<li><a href="bodyplant-capabilities.htm" >Body Plant Exceptions</a></li>
									<li><a onclick="processingImageAndTextHandler('visible','Loading data...');" href="district-proximity.htm?plantId=${plant.plantId}">Proximity Configuration</a></li> 
									<li><a class="set-offline-date" onclick="setOfflineDates(${plant.plantId})" href="#">Plant Off-line Dates</a></li>
								</ul>
							</div>
							</td>
							<td>${plant.plantManufacturer}</td>
							<td>${plant.city}</td>
							<td>${plant.state}</td>
							<td>${plant.plantName}</td>
							<td>
								<c:choose>
									 <c:when test="${fn:length(plant.offlineDates) gt 1}">Multiple Dates</c:when>
								     <c:when test="${fn:length(plant.offlineDates) eq 1}">
									     <c:forEach items="${plant.offlineDates}" var="offlineDate">
									     		<fmt:formatDate pattern = "MM/dd/yyyy" value = "${offlineDate.offlineStartDate}" /> - <fmt:formatDate pattern = "MM/dd/yyyy" value = "${offlineDate.offlineEndDate}" />
									     		
									     </c:forEach>
								      </c:when>
								     <c:otherwise>No Offline Dates</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div id="set-offline-dates-modal" class="modal"></div>
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/maintenance-summary.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/plugins/jquery.mask.min.js"></script>
</body>
</html>