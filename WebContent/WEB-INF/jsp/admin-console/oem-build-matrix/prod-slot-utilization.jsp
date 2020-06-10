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
									<c:forEach items="${summary.plantAssociationsByRegion}" var="entry">
										<c:set var="region" value="${entry.key}" />
										<c:set var="associationList" value="${entry.value}" />
										<c:set var="associationListSize" value="${fn:length(associationList)}" />
										<c:set var="exampleBodyPlant" value="${associationList.get(0)}" />
										<th colspan="${associationListSize * 3}">${exampleBodyPlant.region} - ${exampleBodyPlant.regionDesc}</th>
									</c:forEach>
								</tr>
								<tr class="plant-header-row">
									<th class="first-col">Production Date</th>
									<c:forEach items="${summary.plantAssociationsByRegion}" var="entry">
										<c:set var="region" value="${entry.key}" />
										<c:set var="associationList" value="${entry.value}" />
										<c:forEach items="${associationList}" var="association">
											<c:set var="bodyPlant" value="${summary.bodyPlantById.get(association.plantId)}" />
											<th colspan="3" id="${bodyPlant.plantId}"> ${bodyPlant.plantManufacturer} <br> ${bodyPlant.city}, ${bodyPlant.state}</th>
										</c:forEach>	
									</c:forEach>
								</tr>
								<tr class="badge-row">
									<th class="first-col"></th>
									<c:forEach items="${summary.plantAssociationsByRegion}" var="entry">
										<c:set var="region" value="${entry.key}" />
										<c:set var="associationList" value="${entry.value}" />
										<c:forEach items="${associationList}" var="association">
											<th><span class="badge available-badge">Available</span></th>
											<th><span class="badge reserved-badge">Reserved</span></th>
											<th><span class="badge issued-badge">Issued</span></th>
										</c:forEach>
									</c:forEach>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${summary.rows}" var="row" varStatus="loop">
									<c:choose>
										<c:when test="${loop.count % 2 eq 0}">
											<c:set var="rowClass" value="even" />
										</c:when>
										<c:otherwise>
											<c:set var="rowClass" value="odd" />
										</c:otherwise>
									</c:choose>
									<c:set var="slotDateId" value="${row.slotDate.slotDateId}" />
									<tr class="date-unit-row ${rowClass}" data-prod-slot-date-id="${slotDateId}">
										<td class="first-col prod-date">${row.slotDate.formattedSlotDate}</td>
										<c:forEach items="${row.cells}" var="cell">
											<td class="available-units">${cell.regionAvailability.slotAvailable}</td>
											<c:choose>
												<c:when test="${cell.regionAvailability.slotReserved gt 0}">
													<td class="reserved-units"
														data-plant-id="${cell.bodyPlant.plantId}"
														data-region="${cell.regionAvailability.region}" 
														data-slot-id="${cell.slot.slotId}" 
														data-slot-region-id="${cell.regionAvailability.slotRegionId}" 
														data-region-desc="${cell.regionPlantAssociation.regionDesc}" >
															<a class="secondaryLink release-units-link">${cell.regionAvailability.slotReserved}</a>
													</td>
												</c:when>
												<c:otherwise>
													<td class="reserved-units">${cell.regionAvailability.slotReserved}</td>
												</c:otherwise>
											</c:choose>
											<td class="issued-units">${cell.regionAvailability.slotAccepted}</td>
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