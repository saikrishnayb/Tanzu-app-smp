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
					<table id="build-history-table" data-show-start-build-btn="${showStartBuildBtn}">
						<thead>
							<tr>
								<th class="actionsheader"></th>
								<th class="leftAlign">Build #</th>
								<th class="leftAlign">Requested Qty</th>
								<th class="leftAlign">Fulfilled Qty</th>
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
								<tr>
									<td class="editable centerAlign">
										<c:choose>
											<c:when test="${buildHistory.showReworkBtn()}">
												<div class="dropdown rework-delete-build">
													<a class="bootStrapDropDown dropdown-toggle" data-toggle="dropdown"> Actions <span class="caret"></span>
													</a>
													<ul class="dropdown-menu">
														<li><a id ="rework-btn" data-build-id="${buildHistory.buildId}">Rework</a></li>
														<li><a id ="cancel-btn" data-build-id="${buildHistory.buildId}">Delete</a></li>
													</ul>
												</div>
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
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
									<td class="leftAlign">${buildHistory.reqQty}</td>
									<td class="leftAlign">${buildHistory.fulfilledQty }</td>
									<td class=""> ${buildHistory.buildStatus.label} </td>
									<td>${buildHistory.startedByName}</td>
									<td>${buildHistory.formattedStartDate}</td>
									<td>${buildHistory.submittedByName}</td>
									<td>${buildHistory.formattedSubmittedDate}</td>
									<td>${buildHistory.formattedRunEndDate}</td>
									<td>
										<c:choose>
											<c:when test="${buildHistory.showViewReportBtn()}">
												<c:if test='${buildHistory.buildStatus.code eq "A"}'>
													<a
														href="view-slot-results-filter.htm?buildId=${buildHistory.buildId}&selectedFiltersList=A&checkedFilter=0">View
														Slot Results</a>
												</c:if>
												<c:if test='${buildHistory.buildStatus.code ne "A"}'>
													<a
														href="view-slot-results-filter.htm?buildId=${buildHistory.buildId}&selectedFiltersList=A,E,P&checkedFilter=0">View
														Slot Results</a>
												</c:if>
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
	<div class="modal row" id="confirmReworkOrDeleteModal">
      <div class="modal-content confirm-modal-content col-xs-12" data-modal-title="Confirm" data-modal-max-width="350" data-keep-contents="true">
        <div class="row">
          <input type="hidden" id="build-id">
          <div id="confirmMessage" class="col-xs-12">
          </div>
          <div class="confrim-button-row col-xs-12">
            <div class="pull-right">
              <a onclick="closeConfirmDialog();" class="secondaryLink">Cancel</a>
              <a id="reworkOrDeleteConfirm"  delete="N" class="buttonPrimary btn">Confirm</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-history.js" type="text/javascript"></script>
</body>
</html>