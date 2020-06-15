<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>OEM Build Matrix</title>
	
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-history.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<div class="row">
        		<div class="col-xs-12">
          			<h1>Build History</h1>
        		</div>
      		</div>
			<div class="row">
        		<div class="col-xs-12">
					<table id="build-history-table" class="plant-view" data-show-start-build-btn="${showStartBuildBtn}">
						<thead>
							<tr>
								<th></th>
								<th class="leftAlign" id="build-number">Build #</th>
								<th class="leftAlign">Unit Qty</th>
								<th class="leftAlign">Status</th>
								<th class="leftAlign">Started By</th>
								<th class="leftAlign">Start Date</th>
								<th class="leftAlign">Submitted By</th>
								<th class="leftAlign">Submitted Date</th>
								<th class="leftAlign">Run Date</th>						
								<th class="rightAlign"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${buildHistoryList}" var="buildHistory">
								<tr class="user-row">
									<td>
										<c:choose>
											<c:when test="${buildHistory.showReworkBtn()}">
												<a class="buttonSecondary rework-btn" id="rework-build" build-id="${buildHistory.buildId}">Rework Build</a>
												<a class="buttonSecondary rework-btn" id="cancel-build" build-id="${buildHistory.buildId}">Cancel Build</a>
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</td>
									<td class="leftAlign">
										<c:choose>
											<c:when test='${buildHistory.buildStatus.code eq "P"}'>
												<a href="${baseAppUrl}/admin-console/oem-build-matrix/order-summary?buildId=${buildHistory.buildId}">${buildHistory.buildId}</a>
											</c:when>
											<c:otherwise>
												${buildHistory.buildId}
											</c:otherwise>
										</c:choose>
									</td>
									<td class="leftAlign">${buildHistory.quantity}</td>
									<td class=""> ${buildHistory.buildStatus.label} </td>
									<td>${buildHistory.startedByName}</td>
									<td>${buildHistory.startedDate}</td>
									<td>${buildHistory.submittedByName}</td>
									<td>${buildHistory.submittedDate}</td>
									<td>${buildHistory.runEndDate}</td>
									<td>
										<c:choose>
											<c:when test="${buildHistory.showViewReportBtn()}">
												<a href="view-slot-results-filter.htm?buildId=${buildHistory.buildId}&selectedFiltersList=A,E,P&checkedFilter=0">View Slot Results</a>
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
			<div class="row">
        		<div class="col-xs-12 status-terms-container">
        			<h2>Status Terms</h2>
					<ul>
					<c:forEach items="${buildStatuses}" var="status">
						<li>${status.label} - ${status.description}</li>
					</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div id="confirmReworkOrDeleteModal" >
		<p id="confirmMessage"></p>
		<input type="hidden" id="build-id">
		<div class="confirm-modal-btn">
			<a href="javascript:void(0)" class="secondaryLink" onclick="closeConfirmDialog();">Cancel</a> 
			<a href="javascript:void(0)" class="buttonPrimary" delete="N" id="reworkOrDeleteConfirm">Rework</a>
		</div>
	</div>
	
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-history.js" type="text/javascript"></script>
</body>
</html>