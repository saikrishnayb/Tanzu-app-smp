<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/prod-slot-region-maintenance.css" rel="stylesheet" type="text/css" />
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
          			<h1>Production Slot Region Maintenance</h1>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12 slot-region-maintenance-table-top">
        			<div class='search-div'>
        				<form id="filter-slots-form" action="./prod-slot-region-maintenance" method="GET">
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
          				<a id="import-btn" class="buttonSecondary<c:if test="${noRows}"> buttonDisabled</c:if>" 
          					data-slot-type-id="${slotType.slotTypeId}" 
          					data-region-desc="${regionInfo.regionDesc}" 
          					data-year="${selectedYear}" 
          					data-region="${regionInfo.region}">Import</a>
          				<a id="export-region-slots-btn" class="buttonSecondary<c:if test="${noRows}"> buttonDisabled</c:if>" 
          					data-slot-type-id="${slotType.slotTypeId}" 
          					data-vehicle-desc="${slotType.slotTypeDesc}" 
          					data-year="${selectedYear}" 
          					data-region="${regionInfo.region}">Export</a>
          				<a id="save-region-slots-btn" class="buttonPrimary buttonDisabled">Save</a>
          			</div>
        		</div>
      		</div>
      		<div class="row">
      			<div class="col-xs-12 now-viewing-row">
      				<label>Now Viewing:</label>
					<span id="vehicle-desc">${slotType.slotTypeDesc}</span>
					<span>${selectedYear}</span>
					<span>${regionInfo.region} - ${regionInfo.regionDesc}</span>
      			</div>
        		<div class="col-xs-12">
        			<div class="slot-region-maintenance-table-container">
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
	        					<form id="region-slot-maintenance-form">
				        			<input type="hidden" name="slotTypeId" value="${slotTypeId}">
									<input type="hidden" name="year" value="${selectedYear}">
									<input type="hidden" name="region" value="${selectedRegion}">
									<table id="slot-region-maintenance-table">
										<thead>
											<%-- <tr class="region-row">
												<th class="first-col"></th>
												<c:forEach items="${summary.plantAssociationsByRegion}" var="entry">
													<c:set var="region" value="${entry.key}" />
													<c:set var="associationList" value="${entry.value}" />
													<c:set var="associationListSize" value="${fn:length(associationList)}" />
													<c:set var="exampleBodyPlant" value="${associationList.get(0)}" />
													<th colspan="${associationListSize}">${exampleBodyPlant.region} - ${exampleBodyPlant.regionDesc}</th>
												</c:forEach>
											</tr> --%>
											<tr class="plant-header-row">
												<th class="prod-date-week-col"></th>
												<th id="prod-date" class="first-col slot-table-header">Production Date</th>
												<c:forEach items="${bodyPlantList}" var="bodyPlant">
													<th id="${bodyPlant.plantId}" class="slot-table-header">
																${bodyPlant.plantManufacturer}
														<br>
														${bodyPlant.city}, ${bodyPlant.state}
													</th>
												</c:forEach>	
											</tr>
										</thead>
										<tbody>
											<c:set var="slotIndex" value="0" />
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
													<td class="prod-date-week-col">WK ${row.slotDate.weekOfYear}</td>
													<td class="first-col prod-date centerAlign slot-table-header" headers="prod-date">${row.slotDate.formattedSlotDate}</td>
													<c:forEach items="${row.cells}" var="cell">
														<c:set var="ra" value="${cell.regionAvailability}"/>
														<c:set var="slot" value="${cell.slot}"/>
														<c:choose>
															<c:when test="${empty ra}">
																<c:set var="slotRegionId" value="${-1}"/>
																<c:set var="slotAvailable" value="${0}"/>
																<c:set var="slotReserved" value="${0}"/>
																<c:set var="slotAccepted" value="${0}"/>
																<c:set var="invalidSlot" value="${false}"/>
																<c:set var="allocatedSlots" value="${0}"/>
																<c:set var="nonAcceptedSlots" value="${0}"/>
															</c:when>
															<c:otherwise>
																<c:set var="slotRegionId" value="${ra.slotRegionId}"/>
																<c:set var="slotAvailable" value="${ra.slotAvailable}"/>
																<c:set var="slotReserved" value="${ra.slotReserved}"/>
																<c:set var="slotAccepted" value="${ra.slotAccepted}"/>
																<c:set var="invalidSlot" value="${ra.invalidSlot}"/>
																<c:set var="allocatedSlots" value="${ra.allocatedSlots}"/>
																<c:set var="nonAcceptedSlots" value="${ra.nonAcceptedSlots}"/>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${empty slot}">
																<c:set var="slotId" value="${-1}"/>
																<c:set var="availableSlots" value="${0}"/>
																<c:set var="allocatedRegionSlots" value="${0}"/>
																<c:set var="unallocatedSlots" value="${0}"/>
															</c:when>
															<c:otherwise>
																<c:set var="slotId" value="${slot.slotId}"/>
																<c:set var="availableSlots" value="${slot.availableSlots}"/>
																<c:set var="allocatedRegionSlots" value="${slot.allocatedRegionSlots}"/>
																<c:set var="unallocatedSlots" value="${slot.unallocatedSlots}"/>
															</c:otherwise>
														</c:choose>
														<td class="available-units-td" headers="${cell.bodyPlant.plantId}">
															<input type="hidden" name="regionSlotInfos[XXX].slotRegionId" value="${slotRegionId}" />
															<input type="hidden" name="regionSlotInfos[XXX].slotId" value="${slotId}" />
															<input type="hidden" name="regionSlotInfos[XXX].plantId" value="${cell.bodyPlant.plantId}" />
															<input type="hidden" name="regionSlotInfos[XXX].slotDateId" value="${row.slotDate.slotDateId}" />
															<input type="text" class="available-slot-input<c:if test="${invalidSlot}"> errorMsgInput</c:if>" name="regionSlotInfos[XXX].slotAvailable" value="${slotAvailable}" 
																data-overall-slots="${availableSlots}" 
																data-allocated-slots="${allocatedRegionSlots - slotAvailable}"
																data-region-allocated-slots="${allocatedSlots}"/>
															<br>
															<div class="unallocated-region-slots-div">
																<span class="unallocated-region-slots hidden<c:if test="${invalidSlot}"> errorMsg</c:if>">
																	Available: <span class="unallocated-slots">${unallocatedSlots}</span>
																	&emsp;Reserved: ${allocatedSlots}</span>
															</div>
														</td>
														<c:set var="slotIndex" value="${slotIndex+1}" />
													</c:forEach>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div id="prod-slot-region-maintenance-modal" class="modal"></div>	
		</div>
		
	</div>
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/prod-slot-region-maintenance.js" type="text/javascript"></script>
</body>
</html>