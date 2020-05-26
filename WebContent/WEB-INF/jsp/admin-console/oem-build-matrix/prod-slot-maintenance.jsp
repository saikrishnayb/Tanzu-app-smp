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
          				<a  class="buttonSecondary">Import</a>
          				<a  class="buttonSecondary">Export</a>
          			</div>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12">
					<table id="slot-maintenance-table">
						<thead>
								<tr>
								<th class="centerAlign slot-table-header" id="prod-date">Production Date</th>
								<c:forEach items="${bodyplantList}" var="plantData">
									<th class="centerAlign slot-table-header no-sort" id="${plantData.plantId}"> ${plantData.plantManufacturer} - ${plantData.city}, ${plantData.state}</th>	
								</c:forEach>
								</tr>
						</thead>
						<tbody id="slot-maintenance-tablebody">
							<c:forEach items="${slotMaintenanceSummary}" var="productionSlot">
							<c:choose>
								<c:when test="${fn:length(productionSlot.buildSlots) ne 0}">
									<tr>
										<td class="centerAlign slot-table-header" headers="prod-date">${productionSlot.formattedSlotDate}</td>
										<c:forEach items="${productionSlot.buildSlots}" var="slotForplant">
											<td class="centerAlign slot-table-header" headers="${slotForplant.plantId}"><input class ="available-slot-input" type="number" value="${slotForplant.availableSlots}"/></td>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td class="centerAlign slot-table-header" headers="prod-date">${productionSlot.formattedSlotDate}</td>
										<c:forEach items="${bodyplantList}" var="plantData">
											<td class="centerAlign slot-table-header" headers="${plantData.plantId}"><input class ="available-slot-input" type="number" value="0" /></td>
										</c:forEach>
									</tr>
								</c:otherwise>
							</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>	
		</div>
		
	</div>
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/prod-slot-maintenance.js" type="text/javascript"></script>
</body>
</html>