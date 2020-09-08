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
		<c:set var="noRows" value="${empty summary.rows}" />
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
		          					<c:if test="${vehicleselected}">
		          						<c:set var="slotType" scope="page" value="${type}" />
		          					</c:if>
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
          				<div class="region-div">
          					<label>Region</label>
	          				<select id="region-drpdwn" name="region">
	          					<c:forEach items="${regionMap}" var="entry">
	          						<c:set var="regionSelected">${entry.key eq selectedRegion}</c:set>
	          						<c:if test="${regionSelected}">
		          						<c:set var="regionInfo" scope="page" value="${entry.value}" />
		          					</c:if>
		          					<option value="${entry.key}" ${regionSelected?'selected="selected"':'' }>${entry.value.region} - ${entry.value.regionDesc}</option>
		          				</c:forEach>
	          				</select>
          				</div>
          				<a class="buttonSecondary" id="search-button">Search</a>
          				</form>
          			</div>
          			<div class="btn-div floatRight">
          				<!-- <a  class="buttonPrimary">Save</a> -->
          				<!-- <a  class="buttonSecondary">Export</a> -->
          			</div>
        		</div>
      		</div>
      		<div class="row">
      			<div class="col-xs-12 now-viewing-row">
      				<label>Now Viewing:</label>
					<span>${slotType.slotTypeDesc}</span>
					<span>${selectedYear}</span>
					<span>${regionInfo.region} - ${regionInfo.regionDesc}</span>
      			</div>
        		<div class="col-xs-12">
        			<div class="slot-utilization-table-container">
        				<c:choose>
	        				<c:when test="${empty summary.bodyPlantById}">
	        					<div class="no-plants-message">
	        						There are no plants associated with this region. Please add plant associations to continue
	        					</div>
	        				</c:when>
	        				<c:when test="${noRows}">
	        					<div class="no-plants-message">
	        						Slots have not been created for the selected year and vehicle type combination. Create slots to continue
	        					</div>
	        				</c:when>
	        				<c:otherwise>
								<table id="slot-utilization-table">
									<thead>
										<%-- <tr class="region-row">
											<th class="first-col"></th>
											<c:forEach items="${summary.plantAssociationsByRegion}" var="entry">
												<c:set var="region" value="${entry.key}" />
												<c:set var="associationList" value="${entry.value}" />
												<c:set var="associationListSize" value="${fn:length(associationList)}" />
												<c:set var="exampleBodyPlant" value="${associationList.get(0)}" />
												<th colspan="${associationListSize * 3}">${exampleBodyPlant.region} - ${exampleBodyPlant.regionDesc}</th>
											</c:forEach>
										</tr> --%>
										<tr class="plant-header-row">
											<th class=""></th>
											<th class="first-col">Production Date</th>
											<c:forEach items="${bodyPlantList}" var="bodyPlant">
												<th colspan="3" id="${bodyPlant.plantId}"> ${bodyPlant.plantManufacturer} <br> ${bodyPlant.city}, ${bodyPlant.state}</th>
											</c:forEach>
										</tr>
										<tr class="badge-row">
											<th class=""></th>
											<th class="first-col"></th>
											<c:forEach items="${bodyPlantList}" var="bodyPlant">
												<th id="a-${bodyPlant.plantId}"><span class="badge available-badge">Available</span></th>
												<th id="r-${bodyPlant.plantId}"><span class="badge reserved-badge">Reserved</span></th>
												<th id="i-${bodyPlant.plantId}"><span class="badge issued-badge">Issued</span></th>
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
												<td class="prod-date-week">WK ${row.slotDate.weekOfYear}</td>
												<td class="first-col prod-date">${row.slotDate.formattedSlotDate}</td>
												<c:forEach items="${row.cells}" var="cell">
													<c:set var="ra" value="${cell.regionAvailability}"/>
													<c:set var="slot" value="${cell.slot}"/>
													<c:choose>
														<c:when test="${empty ra}">
															<c:set var="slotRegionId" value="${-1}"/>
															<c:set var="slotAvailable" value="${0}"/>
															<c:set var="slotReserved" value="${0}"/>
															<c:set var="slotAccepted" value="${0}"/>
														</c:when>
														<c:otherwise>
															<c:set var="slotRegionId" value="${ra.slotRegionId}"/>
															<c:set var="slotAvailable" value="${ra.slotAvailable}"/>
															<c:set var="slotReserved" value="${ra.slotReserved}"/>
															<c:set var="slotAccepted" value="${ra.slotAccepted}"/>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${empty slot}">
															<c:set var="slotId" value="${-1}"/>
														</c:when>
														<c:otherwise>
															<c:set var="slotId" value="${slot.slotId}"/>
														</c:otherwise>
													</c:choose>
													<td class="available-units" headers="a-${cell.bodyPlant.plantId}">${slotAvailable}</td>
													<c:choose>
														<c:when test="${slotReserved gt 0}">
															<td class="reserved-units" headers="r-${cell.bodyPlant.plantId}"
																data-plant-id="${cell.bodyPlant.plantId}"
																data-region="${regionInfo.region}" 
																data-slot-id="${slotId}" 
																data-slot-region-id="${slotRegionId}" 
																data-region-desc="${regionInfo.regionDesc}" >
																	<a class="secondaryLink release-units-link">${slotReserved}</a>
															</td>
														</c:when>
														<c:otherwise>
															<td class="reserved-units" headers="r-${cell.bodyPlant.plantId}">${slotReserved}</td>
														</c:otherwise>
													</c:choose>
													<td class="issued-units" headers="i-${cell.bodyPlant.plantId}">${slotAccepted}</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:otherwise>
						</c:choose>
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