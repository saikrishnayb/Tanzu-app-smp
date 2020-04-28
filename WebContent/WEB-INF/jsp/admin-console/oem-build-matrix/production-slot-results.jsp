<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/production-slot-results.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix-global.css" rel="stylesheet" type="text/css" />
</head>

<body style="overflow-y:visible;">
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<div id="PopupError" style="display:none">
				<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
			</div>
			<div class="row">
        		<div class="col-xs-12">
          			<h1>Production Slot Results</h1>
        		</div>
      		</div>
			<div class="row">
        		<div class="col-xs-12 slot-results-table-top">
        			<div class='search-div'>
          				<label>Search: </label> <input type="text" id="slot-search"/>
          			</div>
          			<div class="btn-div floatRight">
          				<a href="${baseAppUrl}/admin-console/oem-build-matrix/build-history.htm"
							onclick="javascript:loadProcessImage();" class="buttonSecondary">Back</a>
          			<c:if test="${fn:length(slotResults) ne 0}">
          				<a id="export-slot-results" class="buttonPrimary">Accept and Export</a>
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
										<td class="leftAlign">${unit.productionDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>	
		</div>
		
	</div>
	
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/production-slot-results.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-matrix-global.js" type="text/javascript"></script>
</body>
</html>