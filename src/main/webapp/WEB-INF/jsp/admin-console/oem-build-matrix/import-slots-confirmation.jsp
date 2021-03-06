<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/import-slots-confirmation.css" rel="stylesheet" type="text/css" />
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
          			<h1>Import Slot Confirmation</h1>
        		</div>
      		</div>
      		<div class="row">
      			<div class="col-xs-12 import-slots-table-top">
	      			<div class="importing-details">
	      				<label>Importing:</label>
						<span>${slotType.slotTypeDesc}</span>
						<span>${year}</span>
	      			</div>
		      		<div class="btn-div floatRight">
		  				<a id="back-btn" class="buttonSecondary" href="${backUrl}">Back</a>
		  				<a id="import-confirm-btn" class="buttonPrimary<c:if test="${noRows}"> buttonDisabled</c:if>">Import</a>
		  			</div>
		  		</div>
      		</div>
      		<div class="row invalid-plants-row<c:if test="${empty results.plantsNotFound}"> hidden</c:if>" >
      			<div class="col-xs-12">
	      			<div class="alert alert-warning">
	      				The following plants were not found:
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
	      			<div class="alert alert-warning">
	      				The following slots are invalid because the new available slots is less than the allocated region slots:
	      				<ul>
		      				<c:forEach items="${results.plantsNotFound}" var="plant">
								<li>${plant}</li>
							</c:forEach>
							<c:forEach items="${results.invalidSlots}" var="invalidSlot">
		      					<c:set var="slotDate" value="${invalidSlot.left}"/>
		      					<c:set var="cell" value="${invalidSlot.right}"/>
								<li>
									${slotDate.formattedSlotDate} - ${cell.bodyPlant.plantManufacturer} - ${cell.bodyPlant.city}, ${cell.bodyPlant.state}: New Available Slots: ${cell.slot.availableSlots} &emsp; Allocated Region Slots: ${cell.slot.allocatedRegionSlots}
								</li>
							</c:forEach>
						</ul>
						You can continue but this will mark this slot and any associated region slots invalid and you will be unable to start/continue a build.
	      			</div>
	      		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12">
        			<form id="import-slots-confirm-form" action="./save-slots" method="POST">
	        			<input type="hidden" name="slotTypeId" value="${slotType.slotTypeId}">
						<input type="hidden" name="year" value="${year}">
						<table id="import-slots-table">
							<thead>
									<tr>
										<th class="centerAlign import-slots-table-header" id="prod-date">Production Date</th>
										<c:forEach items="${results.bodyPlantList}" var="plantData">
											<th class="centerAlign slot-table-header no-sort" id="${plantData.plantId}"> ${plantData.plantManufacturer} <br> ${plantData.city}, ${plantData.state}</th>	
										</c:forEach>
									</tr>
							</thead>
							<tbody id="import-slots-tablebody">
								<c:set var="slotIndex" value="0" />
								<c:forEach items="${results.summary.rows}" var="row">
									<tr>
										<td class="centerAlign import-slots-table-header" headers="prod-date">${row.slotDate.formattedSlotDate}</td>
										<c:forEach items="${row.cells}" var="cell" varStatus="innerLoop">
											<c:set var="invalidSlot" value="${cell.slot.isInvalidSlot()}" />
											<td class="centerAlign import-slots-table-header<c:if test="${invalidSlot}"> invalid-slot-td</c:if>" headers="${cell.bodyPlant.plantId}">
												${cell.slot.availableSlots}
											</td>
											<input type="hidden" name="slotInfos[${slotIndex}].slotId" value="${cell.slot.slotId}" />
											<input type="hidden" name="slotInfos[${slotIndex}].availableSlots" value="${cell.slot.availableSlots}" />
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
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/import-slots-confirmation.js" type="text/javascript"></script>
</body>
</html>