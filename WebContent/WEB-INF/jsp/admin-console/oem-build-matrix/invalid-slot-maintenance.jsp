<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/invalid-slot-maintenance.css" rel="stylesheet" type="text/css" />
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
          			<h1>Invalid Slot Maintenance</h1>
        		</div>
      		</div>
      		<c:choose>
				<c:when test="${!invalidSlotsExist}">
					<div class="row">
        				<div class="col-xs-12 no-invalid-slots-message">
							There are no invalid slots
						</div>
					</div>
				</c:when>
				<c:otherwise>
		      		<div class="row">
		        		<div class="col-xs-12 invalid-slots-table-top">
		        			<div class='search-div'>
		        				<form id="filter-slots-form" action="./invalid-slots.htm" method="GET">
		        				<div class="manufacturer-div">
		        					<label>Manufacturer</label>
			          				<select id="manufacturer-drpdwn">
				          				<c:forEach items="${mfrMap}" var="mfr">
				          					<c:set var="mSelected">${mfr.key eq selectedMfr}</c:set>
				          					<c:if test="${mSelected}">
				          						<c:set var="mfrSelected" scope="page" value="${mfr.key}" />
				          					</c:if>
				          					<option value="${mfr.key}" ${mSelected?'selected="selected"':'' } >${mfr.value}</option>
				          				</c:forEach>
			          				</select>
		        				</div>
		          				<div class="plant-div">
		          					<label>Plant</label>
			          				<select id="plant-drpdwn" name="plantId">
			          					<c:forEach items="${plantList}" var="plant">
				          					<c:set var="pSelected">${plant.plantId eq selectedPlant}</c:set>
				          					<c:if test="${pSelected}">
				          						<c:set var="plantSelected" scope="page" value="${plant}" />
				          					</c:if>
				          					<option value="${plant.plantId}" ${pSelected?'selected="selected"':'' } >${plant.plantManufacturer} - ${plant.city}, ${plant.state}</option>
				          				</c:forEach>
			          				</select>
		          				</div>
		          				<div class="slot-type-div">
		          					<label>Slot Type</label>
			          				<select id="slot-type-drpdwn" name="slotTypeId">
			          					<c:forEach items="${slotTypeList}" var="slotType">
			          						<c:set var="sSelected">${selectedSlotType eq slotType.slotTypeId}</c:set>
			          						<c:if test="${sSelected}">
				          						<c:set var="slotTypeSelected" scope="page" value="${slotType}" />
				          					</c:if>
				          					<option value="${slotType.slotTypeId}" ${sSelected?'selected="selected"':'' }>${slotType.slotTypeDesc}</option>
				          				</c:forEach>
			          				</select>
		          				</div>
		          				<a class="buttonSecondary" id="search-button">Search</a>
		          				</form>
		          			</div>
		          			<div class="btn-div floatRight">
		          				<a id="save-invalid-slots-btn" class="buttonPrimary buttonDisabled">Save</a>
		          			</div>
		        		</div>
		      		</div>
		      		<div class="row">
		      			<div class="col-xs-12 now-viewing-row">
		      				<label>Now Viewing:</label>
							<span id="viewing-plant" data-slot-type-id="${plantSelected.plantId}">${plantSelected.plantManufacturer} - ${plantSelected.city}, ${plantSelected.state}</span>
							<span id="viewing-slot-type" data-slot-type-id="${slotTypeSelected.slotTypeId}">${slotTypeSelected.slotTypeDesc}</span>
		      			</div>
		        		<div class="col-xs-12">
		        			<div class="invalid-slots-table-container">
			        			
	        					<form id="invalid-slots-form">
				        			<input type="hidden" name="plantId" value="${plantSelected.plantId}">
									<input type="hidden" name="slotTypeId" value="${slotTypeSelected.slotTypeId}">
									<table id="invalid-slots-table">
										<thead>
											<tr class="region-header-row">
												<th id="prod-date" class="first-col slot-table-header">Production Date</th>
												<c:forEach items="${invalidSlotsSummary.regionPlantAssociations}" var="rpa">
													<th id="${rpa.region}" class="slot-table-header">
														${rpa.region}
														<br>
														${rpa.regionDesc}
													</th>
												</c:forEach>	
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${invalidSlotsSummary.rows}" var="row" varStatus="loop">
												<c:choose>
													<c:when test="${loop.count % 2 eq 0}">
														<c:set var="rowClass" value="even" />
													</c:when>
													<c:otherwise>
														<c:set var="rowClass" value="odd" />
													</c:otherwise>
												</c:choose>
												<c:set var="slotDateId" value="${row.slotDate.slotDateId}" />
												<tr class="date-unit-row ${rowClass}" 
													data-prod-slot-date-id="${slotDateId}" 
													data-slot-id="${row.slot.slotId}" 
													data-available-slots="${row.slot.availableSlots}"
													data-row-index="${loop.index}">
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
															</c:when>
															<c:otherwise>
																<c:set var="slotRegionId" value="${ra.slotRegionId}"/>
																<c:set var="slotAvailable" value="${ra.slotAvailable}"/>
																<c:set var="slotReserved" value="${ra.slotReserved}"/>
																<c:set var="slotAccepted" value="${ra.slotAccepted}"/>
																<c:set var="invalidSlot" value="${ra.invalidSlot}"/>
																<c:set var="allocatedSlots" value="${ra.allocatedSlots}"/>
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
														<td class="available-units-td" headers="${cell.regionPlantAssociation.region}">
															<input type="hidden" name="invalidSlotInfos[XXX].slotRegionId" value="${slotRegionId}" />
															<input type="hidden" name="invalidSlotInfos[XXX].slotId" value="${slotId}" />
															<input type="hidden" name="invalidSlotInfos[XXX].plantId" value="${cell.bodyPlant.plantId}" />
															<input type="hidden" name="invalidSlotInfos[XXX].slotDateId" value="${row.slotDate.slotDateId}" />
															<input type="hidden" name="invalidSlotInfos[XXX].region" value="${cell.regionPlantAssociation.region}" />
															<input type="text" class="available-slot-input<c:if test="${invalidSlot}"> errorMsgInput</c:if>" name="invalidSlotInfos[XXX].slotAvailable" value="${slotAvailable}" 
																data-overall-slots="${availableSlots}" 
																data-allocated-slots="${allocatedRegionSlots - slotAvailable}"
																data-region-allocated-slots="${allocatedSlots}"/>
															<br>
															<div class="unallocated-region-slots-div">
																<span class="unallocated-region-slots hidden<c:if test="${invalidSlot}"> errorMsg</c:if>">
																	Available: <span class="unallocated-slots">${unallocatedSlots}</span>
																	&emsp;Reserved: ${allocatedSlots}</span>
																</span>
															</div>
														</td>
														<c:set var="slotIndex" value="${slotIndex+1}" />
													</c:forEach>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="modal row" id="modal-invalid-slot-save">
		      <div class="modal-content col-xs-12" data-modal-title="Save Invalid Units" data-modal-max-width="350" data-keep-contents="true">
		        <div class="row">
		          <div class="col-xs-12">
		            Are you sure you want to save? Any rows with invalid entries will not be saved.
		          </div>
		          
		          <div class="save-confrim-button-row col-xs-12">
		            <div class="pull-right">
		              <a id="cancel-save-btn" class="buttonSecondary btn">Cancel</a>
		              <a id="save-invalid-slots-confirm-btn"class="buttonPrimary btn">Save</a>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
		</div>
		
	</div>
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/invalid-slot-maintenance.js" type="text/javascript"></script>
</body>
</html>