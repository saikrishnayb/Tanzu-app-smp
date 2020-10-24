<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/production-slot-results.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<div class="row">
				<div class="col-xs-12">
					<h1>Production Slot Results</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 slot-results-table-top">
					<div class='search-div'>
						<label>Search: </label> <input type="text" id="slot-search" />
					</div>
					<div class="Filter-div" id="filter-checkbox">
						<form id="filter-slots-form" action="./view-slot-results-filter.htm" method="GET">
							<input type="hidden" id="buildId" value="${buildId}" name="buildId"> 
							<input type="hidden" id="selectedFiltersList" value="${selectedFiltersList}" name="selectedFiltersList"> 
							<input type="hidden" id="checkedFilter" value="1" name="checkedFilter">
							<input type="hidden" id="approvedBuild" value="${approvedBuild}">
							<span> <label>Filter Results</label> 
								<c:choose>
									<c:when test= "${checkedFilter eq true}">
										<span><input type="checkbox" id="Matched" value="A" name="filters" checked <c:if test="${approvedBuild eq true}">disabled</c:if>/><label>Slot Found</label></span> 
										<span><input type="checkbox" id="Exceptions" value="E" name="filters" checked <c:if test="${approvedBuild eq true}">disabled</c:if>/><label>No Date Found</label></span>
										<span><input type="checkbox" id="Unmatched" value="U" name="filters" checked <c:if test="${approvedBuild eq true}">disabled</c:if>/><label>No Suitable Match Found</label></span>
										<span><input type="checkbox" id="PlantMissing" value="P" name="filters" checked <c:if test="${approvedBuild eq true}">disabled</c:if>/><label>Chassis Found, Body Required</label></span>
									</c:when>
									<c:otherwise>
										<span><input type="checkbox" id="Matched" value="A" name="filters"/><label>Slot Found</label></span> 
										<span><input type="checkbox" id="Exceptions" value="E" name="filters"/><label>No Date Found</label></span>
										<span><input type="checkbox" id="Unmatched" value="U" name="filters"/><label>No Suitable Match Found</label></span>
										<span><input type="checkbox" id="PlantMissing" value="P" name="filters"/><label>Chassis Found, Body Required</label></span>
									</c:otherwise>
								</c:choose>
							</span>
						</form>
					</div>
					<div class="btn-div floatRight">
						<a href="${baseAppUrl}/admin-console/oem-build-matrix/build-history.htm" class="buttonSecondary">Back</a>
						<div id="actions-dpdown" class="buttonSecondary dropdown buttonDisabled">
								<a class="bootStrapDropDown dropdown-toggle production-slot-actions"
									data-toggle="dropdown"> Actions <span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a id="delete-reservation">Delete Selected Reservations</a></li>
									<li><a id="update-reservation">Update Selected Slot Reservations</a></li>
									<tl:isAuthorized tabName="Admin Console" secFunction="OEM_BUILD_MATRIX_DEBUG">
									<li><a id="view-diagnostic-info">View Diagnostic Info</a></li>
									</tl:isAuthorized>
									
								</ul>
							</div>
						<c:if test="${fn:length(slotResults) ne 0}">
							<a id="export-slot-results" class="buttonPrimary" onclick="exportSlotResults();return false;">Export</a>
						</c:if>
						<a id="accept-slot-results" class="buttonSecondary  <c:if test="${!showAcceptBtn or fn:length(slotResults) eq 0 or (checkedFilter eq true and approvedBuild eq true)}"> buttonDisabled</c:if>">Accept</a>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 slot-results-table-top">
					<label>Show Selected Only</label> <input type="checkbox" id="show-selected-checkbox">
					<a id="clear-selections-link">Clear Selections</a>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<table id="slot-results-table">
						<thead>
							<tr>
								<th id="order-column">Order #</th>
								<th class="leftAlign">Unit</th>
								<th class="leftAlign">Program Name</th>
								<th class="leftAlign">Region</th>
								<th class="leftAlign">Area</th>
								<th class="leftAlign">District</th>
								<th class="leftAlign">District Name</th>
								<th class="leftAlign">Requested Delivery Date</th>
								<th class="leftAlign width-200">Production Slot</th>
								<th class="leftAlign width-100">Production Date</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${slotResults}" var="unit">
								<tr class="result">
									<td>
										<input class="unit-selection" type="checkbox" data-attribute-id="${unit.slotReservationId}" reservation-status="${unit.reservationStatus}" 
											order-id="${unit.orderId}" unit-number="${unit.unitNumber}" plant-id="${unit.plantId}" vehicle-type="${unit.vehicleType}"/>
										${unit.orderId}
									</td>
									<td class="leftAlign">
										${unit.unitNumber}
										<c:if test="${unit.changeRequired}">
											<i class="fa fa-exclamation-triangle change-required" title="Change Required"></i>
										</c:if>
									</td>
									<td class="leftAlign">${unit.programName}</td>
									<td class="leftAlign">${unit.region}</td>
									<td class="leftAlign">${unit.area}</td>
									<td class="leftAlign">${unit.districtNumber}</td>
									<td class="leftAlign">${unit.districtName}</td>
									<td class="leftAlign"><fmt:formatDate pattern="MM/dd/yyyy" value="${unit.requestedDeliveryDate}" /></td>
									<td class="leftAlign">${unit.productionSlot}
										<input type="hidden" id="production-slot" value="${unit.productionSlot}" >
									</td>
									<td class="row leftAlign">${unit.formattedProductionDate}
										<input type="hidden" id="production-date" value="${unit.formattedProductionDate}" >
								   </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal row" id="confirm-delete-reservation-modal">
      <div class="modal-content confirm-modal-content col-xs-12" data-modal-title="Confirm" data-modal-max-width="350" data-keep-contents="true">
        <div class="row">
          <div id="confirmMessage" class="col-xs-12">
          </div>
          <div class="confrim-button-row col-xs-12">
            <div class="pull-right">
              <a id="cancel-confirm" class="secondaryLink">Cancel</a>
              <a id="confirm-btn"  confirm-action="delete" class="buttonPrimary btn">Confirm</a>
            </div>
          </div>
        </div>
      </div>
    </div>
	<div id="update-reservation-popup-modal" class="modal row"></div>
	<div id="view-diagnostic-info-popup-modal" class="modal row"></div>
	<%@ include file="../../global/v2/footer.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/file-download-helper.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/production-slot-results.js" type="text/javascript"></script>
</body>
</html>