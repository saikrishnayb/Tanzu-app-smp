<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/prod-slot-maintenance.css" rel="stylesheet" type="text/css" />
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
          			<h1>Slot Availability Maintenance</h1>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12 slot-maintenance-table-top">
        			<div class='search-div'>
        				<form id="filter-slots-form" action="./prod-slot-maintenance.htm" method="GET">
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
          			<div class="btn-div floatRight">
          				<a id="create-slots-btn" class="buttonSecondary">Create Slots</a>
          				<a id="import-btn" class="buttonSecondary<c:if test="${noRows}"> buttonDisabled</c:if>">Import</a>
          				<a id="export-btn" class="buttonSecondary<c:if test="${noRows}"> buttonDisabled</c:if>">Export</a>
          				<a id="save-slots-btn" class="buttonPrimary buttonDisabled">Save</a>
          			</div>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12">
        			<form id="slot-maintenance-form">
	        			<input type="hidden" name="slotTypeId" value="${slotTypeId}">
						<input type="hidden" name="year" value="${year}">
						<table id="slot-maintenance-table">
							<thead>
									<tr>
									<th class="centerAlign slot-table-header" id="prod-date">Production Date</th>
									<c:forEach items="${bodyplantList}" var="plantData">
										<th class="centerAlign slot-table-header no-sort" id="${plantData.plantId}"> ${plantData.plantManufacturer} <br> ${plantData.city}, ${plantData.state}</th>	
									</c:forEach>
									</tr>
							</thead>
							<tbody id="slot-maintenance-tablebody">
								<c:set var="slotIndex" value="0" />
								<c:forEach items="${summary.rows}" var="row">
									<tr>
										<td class="centerAlign slot-table-header" headers="prod-date">${row.slotDate.formattedSlotDate}</td>
										<c:forEach items="${row.cells}" var="cell">
											<td class="centerAlign slot-table-header" headers="${cell.bodyPlant.plantId}">
												<input type="hidden" name="slotInfos[${slotIndex}].slotId" value="${cell.slot.slotId}" />
												<input class ="available-slot-input" name="slotInfos[${slotIndex}].availableSlots" type="number" value="${cell.slot.availableSlots}"/>
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
			<div id="prod-slot-maintenance-modal" class="modal"></div>	
		</div>
		
	</div>
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/prod-slot-maintenance.js" type="text/javascript"></script>
</body>
</html>