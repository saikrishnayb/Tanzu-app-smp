<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<head>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/modal/view-guidance.css" rel="stylesheet" type="text/css" />
</head>
<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="View Guidance - Build ${buildId}" data-modal-width="650">
	<div class="row modal-body">
		<div class="col-xs-12">
			<table id="view-guidance-table">
				<thead>
					<tr>
						<th>Attribute</th>
						<th>Qty. Requested</th>
						<th>Qty. Excess</th>
						<th>Qty. Allotted</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${guidanceSummariesByGroupKey}" var="entry" varStatus="mapLoop">
						<c:set var="groupKey" value="${entry.key}"/>
						<c:set var="guidanceSummaries" value="${entry.value}"/>
						<tr class="group-key-row">
							<td class="group-key-td" colspan=4>${groupKey}</td>
						<tr>
						<c:forEach items="${guidanceSummaries}" var="guidanceSummary" varStatus="guidanceSummariesLoop">
							<c:choose>
								<c:when test="${guidanceSummariesLoop.count % 2 eq 0}">
									<c:set var="rowClass" value="even" />
								</c:when>
								<c:otherwise>
									<c:set var="rowClass" value="odd" />
								</c:otherwise>
							</c:choose>
							<tr class="guidance-summary-row ${rowClass}">
								<td class="guidance-summary-td">${guidanceSummary.awardKey}</td>
								<td class="guidance-summary-td">${guidanceSummary.guidanceTarget}</td>
								<td class="guidance-summary-td">${guidanceSummary.guidanceExcess}</td>
								<td class="guidance-summary-td">${guidanceSummary.guidanceAllocation}</td>
							<tr>
						</c:forEach>	
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
