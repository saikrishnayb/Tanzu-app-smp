<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/prod-slot-utilization.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<div class="row">
        		<div class="col-xs-12">
          			<h1>Production Slot Utilization</h1>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12 slot-utilization-table-top">
        			<div class='search-div'>
        				<form id="filter-slots-form" action="./prod-slot-utilization.htm" method="GET">
        				<div class="vehicletype-div">
        					<label>Vehicle Type</label>
	          				<select id="vehicletype-drpdwn" name="slotType">
		          				<c:forEach items="${vehicleTypes}" var="type">
		          					<c:set var="vehicleselected">${type.slotTypeId eq slotTypeId}</c:set>
		          					<option value="${type.slotTypeId}" ${vehicleselected?'selected="selected"':'' } >${type.slotTypeDesc }</option>
		          				</c:forEach>
	          				</select>
        				</div>
          				<div class="year-div">
          					<label>Year</label>
	          				<select id="year-drpdwn" name="year">
	          					<c:forEach items="${years}" var="year">
	          						<c:set var="yearselected">${year eq selectedYear}</c:set>
		          					<option value="${year}" ${yearselected?'selected="selected"':'' }>${year}</option>
		          				</c:forEach>
	          				</select>
          				</div>
          				</form>
          				
          			</div>
          			<!-- <div class="btn-div floatRight">
          				<a  class="buttonSecondary">Import</a>
          				<a  class="buttonSecondary">Export</a>
          			</div> -->
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12">
        			<div class="slot-utilization-table-container">
						<table id="slot-utilization-table">
							<thead>
								<tr class="region-row">
									<th class="first-col"></th>
									<th colspan="12">Northeast Region</th>
									<th colspan="12">Southeast Region</th>
									<th colspan="12">Northwest Region</th>
								</tr>
								<tr class="plant-header-row">
									<th class="first-col">Production Date</th>
									<c:forEach items="${bodyplantList}" var="plantData">
										<th colspan="3" id="${plantData.plantId}"> ${plantData.plantManufacturer} <br> ${plantData.city}, ${plantData.state}</th>	
									</c:forEach>
								</tr>
								<tr class="badge-row">
									<th class="first-col"></th>
									<c:forEach items="${bodyplantList}" var="plantData">
										<th><span class="badge available-badge">Available</span></th>
										<th><span class="badge reserved-badge">Reserved</span></th>
										<th><span class="badge issued-badge">Issued</span></th>
									</c:forEach>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${slotMaintenanceSummary}" var="productionSlot" varStatus="loop">
									<c:choose>
										<c:when test="${loop.count % 2 eq 0}">
											<c:set var="rowClass" value="even" />
										</c:when>
										<c:otherwise>
											<c:set var="rowClass" value="odd" />
										</c:otherwise>
									</c:choose>
									<tr class="date-unit-row ${rowClass}" 
										data-prod-slot-id="${slotDateId}"
										data-region-id=""
										data-date-id=""
										data-plant-id="">
										<td class="first-col prod-date">${productionSlot.formattedSlotDate}</td>
										<c:forEach items="${productionSlot.buildSlots}" var="slotForplant">
											<td class="available-units">5</td>
											<td class="reserved-units"><a class="secondaryLink release-units-link">5</a></td>
											<td class="issued-units">5</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div id="prod-slot-utilization" class="modal"></div>	
		</div>
		
	</div>
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/prod-slot-utilization.js" type="text/javascript"></script>
</body>
</html>