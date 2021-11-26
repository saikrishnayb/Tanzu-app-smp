<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/import-region-slots-confirmation.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<c:set var="noRows" value="${empty results.summary.rows}" />
		<c:set var="invalidSlots" value="${!empty results.getInvalidSlots()}" />
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<div class="row">
        		<div class="col-xs-12">
          			<h1>Import Region Slot Confirmation</h1>
        		</div>
      		</div>
      		<div class="row">
      			<div class="col-xs-12 import-slots-table-top">
	      			<div class="importing-details">
	      				<label>Importing:</label>
	      				<span>${region} - ${regionDesc}</span>
						<span>${slotType.slotTypeDesc}</span>
						<span>${year}</span>
	      			</div>
		      		<div class="btn-div floatRight">
		  				<a id="back-btn" class="buttonSecondary" href="${backUrl}">Back</a>
		  				<a id="import-confirm-btn" class="buttonPrimary<c:if test="${noRows or invalidSlots}"> buttonDisabled</c:if>">Import</a>
		  			</div>
		  		</div>
      		</div>
      		<div class="row invalid-plants-row<c:if test="${empty results.plantsNotFound}"> hidden</c:if>" >
      			<div class="col-xs-12">
	      			<div class="alert alert-warning">
	      				The following plants were not found or are not part of this region:
	      				<ul>
		      				<c:forEach items="${results.plantsNotFound}" var="plant">
								<li>${plant}</li>
							</c:forEach>
						</ul>
	      			</div>
	      		</div>
      		</div>
      		<div class="row invalid-slots-row<c:if test="${!invalidSlots}"> hidden</c:if>" >
      			<div class="col-xs-12">
	      			<div class="alert alert-danger">
	      				The following slots are invalid because the new available region slots is less than the allocated region slots 
	      				or the new available region slots plus the other allocated region slots is greater than the overall slots:
	      				<ul>
		      				<c:forEach items="${results.plantsNotFound}" var="plant">
								<li>${plant}</li>
							</c:forEach>
							<c:forEach items="${results.invalidSlots}" var="invalidSlot">
		      					<c:set var="slotDate" value="${invalidSlot.left}"/>
		      					<c:set var="cell" value="${invalidSlot.right}"/>
								<li>
									${slotDate.formattedSlotDate} - ${cell.bodyPlant.plantManufacturer} - ${cell.bodyPlant.city}, ${cell.bodyPlant.state}: New Available Slots: ${cell.regionAvailability.slotAvailable} 
									&emsp; Used Region Slots: ${cell.regionAvailability.slotReserved + cell.regionAvailability.slotAccepted}
									&emsp; Overall Slots: ${cell.slot.availableSlots}
								</li>
							</c:forEach>
						</ul>
	      			</div>
	      		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12">
        			<form id="import-region-slots-confirm-form" action="./save-region-slots" method="POST">
	        			<input type="hidden" name="slotTypeId" value="${slotType.slotTypeId}">
						<input type="hidden" name="year" value="${year}">
						<input type="hidden" name="region" value="${region}">
						<table id="import-slots-table">
							<thead>
									<tr>
										<th class="centerAlign import-slots-table-header" id="prod-date">Production Date</th>
										<c:forEach items="${results.bodyPlantList}" var="plantData">
											<th class="centerAlign import-slots-table-header no-sort" id="${plantData.plantId}"> ${plantData.plantManufacturer} <br> ${plantData.city}, ${plantData.state}</th>	
										</c:forEach>
									</tr>
							</thead>
							<tbody id="import-slots-tablebody">
								<c:set var="slotIndex" value="0" />
								<c:forEach items="${results.summary.rows}" var="row">
									<tr>
										<td class="centerAlign import-slots-table-header" headers="prod-date">${row.slotDate.formattedSlotDate}</td>
										<c:forEach items="${row.cells}" var="cell" varStatus="innerLoop">
											<c:set var="invalidSlot" value="${cell.regionAvailability.invalidSlot}" />
											<td class="centerAlign slot-table-header<c:if test="${invalidSlot}"> invalid-slot-td</c:if>" headers="${cell.bodyPlant.plantId}">
												${cell.regionAvailability.slotAvailable}
											</td>
											<input type="hidden" name="regionSlotInfos[${slotIndex}].slotRegionId" value="${cell.regionAvailability.slotRegionId}" />
											<input type="hidden" name="regionSlotInfos[${slotIndex}].slotAvailable" value="${cell.regionAvailability.slotAvailable}" />
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
		
	</div>
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/import-region-slots-confirmation.js" type="text/javascript"></script>
</body>
</html>