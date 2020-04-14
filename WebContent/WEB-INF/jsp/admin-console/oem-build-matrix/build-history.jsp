<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<title>OEM Build Matrix</title>
		
		<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
		<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-history.css" rel="stylesheet" type="text/css" />
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
	          			<h1>Build History</h1>
	        		</div>
	      		</div>
				<div class="row">
	        		<div class="col-xs-12">
						<table id="build-history-table" class="plant-view" data-show-start-build-btn="${showStartBuildBtn}">
							<thead>
								<tr>
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
										<td class="leftAlign">
											<c:choose>
												<c:when test='${buildHistory.buildStatus.code eq "P"}'>
													<a href="${baseAppUrl}/admin-console/oem-build-matrix/order-summary?buildId=${buildHistory.buildId}" onclick="javascript:loadProcessImage();">${buildHistory.buildId}</a>
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
												<a class=""> View Report</a> 
												</c:when>
												<c:otherwise>
												</c:otherwise>
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
		
		<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
		<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-history.js" type="text/javascript"></script>
	</body>
</html>