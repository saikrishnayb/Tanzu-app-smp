<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
<head>
	<title>OEM Build Matrix</title>

	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/maintenence-summary.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<span class="floatRight addRow push-right"> </span>
			<h1>Plant Maintenance</h1>

			<table id="body-plant-maint-table" class="plant-view">
				<thead>
					<tr>
						<th class="actionsheader"></th>
						<th>Manufacturer</th>
						<th>City</th>
						<th>State</th>
						<th>Plant Name</th>
						<th>Offline Dates</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${maintenanceSummary}" var="plant">
						<tr>
							<td class="editable centerAlign">
							<div class="dropdown">
								<a class="bootStrapDropDown dropdown-toggle"
									data-toggle="dropdown"> Actions <span class="caret"></span>
								</a>
								<input type="hidden" class="plant-id" value="${plant.plantId}" />
								<ul class="dropdown-menu">
									<li><a href="bodyplant-capabilities?plantId=${plant.plantId}">Body Plant Exceptions</a></li>
									<li><a class="set-offline-date" onclick="setOfflineDates(${plant.plantId})" href="#">Plant Off-line Dates</a></li>
									<li class="dropdown-header">Geographical Configuration</li>
									<li><a class="region-association" plantId="${plant.plantId}">&emsp;Region Association</a></li>
									<li><a href="district-proximity?plantId=${plant.plantId}">&emsp;Proximity Configuration</a></li>
								</ul>
							</div>
							</td>
							<td>${plant.plantManufacturer}</td>
							<td>${plant.city}</td>
							<td>${plant.state}</td>
							<td>${plant.plantName}</td>
							<td>
								<c:choose>
									 <c:when test="${fn:length(plant.offlineDates) gt 1}">Multiple Dates</c:when>
								     <c:when test="${fn:length(plant.offlineDates) eq 1}">
									     <c:forEach items="${plant.offlineDates}" var="offlineDate">
									     		<fmt:formatDate pattern = "MM/dd/yyyy" value = "${offlineDate.offlineStartDate}" /> - <fmt:formatDate pattern = "MM/dd/yyyy" value = "${offlineDate.offlineEndDate}" />
									     		
									     </c:forEach>
								      </c:when>
								     <c:otherwise></c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div id="set-offline-dates-modal" class="modal row"></div>
	<div id="region-association-modal" class="modal row"></div>
	<div class="modal row" id="confirm-delete-proximity-modal">
      <div class="modal-content confirm-modal-content col-xs-12" data-modal-title="Confirm" data-modal-max-width="350" data-keep-contents="true">
        <div class="row">
          <div class="col-xs-12">
          Associated proximity data will get delete for the region and cannot be undone. Do you want to continue?
          </div>
          <div class="confrim-button-row col-xs-12">
            <div class="pull-right">
              <a id="cancel-confirm" class="secondaryLink">Cancel</a>
              <a id="confirm-btn"  class="buttonPrimary btn">Confirm</a>
            </div>
          </div>
        </div>
      </div>
    </div>
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/maintenance-summary.js" type="text/javascript"></script>
</body>
</html>