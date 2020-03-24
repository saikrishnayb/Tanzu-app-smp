<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<title>OEM Build Matrix</title>
<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix.css" rel="stylesheet" type="text/css" />
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
			<h1>Production Slots Maintenance</h1>

			<table id="body-plant-maint-table" class="plant-view">
				<thead>
					<tr>
						<th></th>
						<th class="">Plant Name</th>
						<th>City</th>
						<th class="">State</th>
						<th class="">Region</th>
						<th class="">Area</th>
						<th class="">Offline Dates</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${maintenanceSummary}" var="slotMaintenance">
						<tr>
							<td class="editable centerAlign ">
							<div class="dropdown">
								<a class="bootStrapDropDown dropdown-toggle"
									data-toggle="dropdown"> Actions <span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a href="bodyplant-capabilities.htm" >Body Plant Exceptions</a></li>
									<li><a href="district-proximity.htm">Proximity Configuration</a></li>
									<li><a href="#">Plant Off-line Dates</a></li>
								</ul>
							</div>
							</td>
							<td>${slotMaintenance.plantName}</td>
							<td>${slotMaintenance.city}</td>
							<td>${slotMaintenance.state}</td>
							<td>${slotMaintenance.region}</td>
							<td>${slotMaintenance.area}</td>
							<td>
							<c:choose>
							 <c:when test="${fn:trim(slotMaintenance.offlineDates) ne '' and slotMaintenance.offlineDates ne null }">${slotMaintenance.offlineDates}</c:when>
						     <c:otherwise>No offline Dates</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/maintenance-summary.js" type="text/javascript"></script>
</body>
</html>