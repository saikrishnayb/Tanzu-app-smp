<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
      		<input type="hidden" id="buildId" value="${buildId}">
			<div class="row">
        		<div class="col-xs-12 slot-results-table-top">
        			<div class='search-div'>
          				<label>Search: </label> <input type="text" id="slot-search"/>
          			</div>
          			<div class="btn-div floatRight">
          				<a href="${baseAppUrl}/admin-console/oem-build-matrix/build-history.htm" class="buttonSecondary">Back</a>
          			<c:if test="${fn:length(slotResults) ne 0}">
          				<a id="export-slot-results" class="buttonPrimary" onclick="exportSlotResults();return false;">Accept and Export</a>
          			</c:if>
          			</div>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12">
					<table id="slot-results-table">
						<thead>
							<tr>
								<th>Order #</th>
								<th class="leftAlign">Unit</th>
								<th class="leftAlign">Program Name</th>
								<th class="leftAlign">Region</th>
								<th class="leftAlign">Area</th>
								<th class="leftAlign">District</th>
								<th class="leftAlign">District Name</th>
								<th class="leftAlign">Requested Delivery Date</th>
								<th class="leftAlign">Production Slot</th>
								<th class="leftAlign">Production Date</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${slotResults}" var="unit">
								<tr class="result">
										<td><a class="secondaryLink">${unit.orderId}</a></td>
										<td class="leftAlign">${unit.unitNumber}</td>
										<td class="leftAlign">${unit.programName}</td>
										<td class="leftAlign">${unit.region}</td>
										<td class="leftAlign">${unit.area}</td>
										<td class="leftAlign">${unit.districtNumber}</td>
										<td class="leftAlign">${unit.districtName}</td>
										<td class="leftAlign"><fmt:formatDate pattern = "MM/dd/yyyy" value = "${unit.requestedDeliveryDate}" /></td>
										<td class="leftAlign">${unit.productionSlot}</td>
										<td class="leftAlign"><fmt:parseDate pattern="MM/dd/yyyy" value="${unit.productionDate}" var="parsedProductionDate" />
										<fmt:formatDate pattern="MM/dd/yyyy" value="${parsedProductionDate}"  />
										</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>	
		</div>
		
	</div>
	
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/file-download-helper.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/production-slot-results.js" type="text/javascript"></script>
</body>
</html>