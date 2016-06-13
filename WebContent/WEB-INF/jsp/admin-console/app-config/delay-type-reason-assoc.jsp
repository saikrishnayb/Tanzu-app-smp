<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Delay Reason Codes</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    <!-- Scripts -->
		<script src="${context}/js/admin-console/app-config/delay-type-reason-assoc.js" type="text/javascript"></script>
		<link href="${context}/css/admin-console/app-config/delay-reason-codes.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
				<span class="floatRight addRow push-right">
					<a class="add-delay-reason">
						Add Delay Reason
						<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor adder" alt="Add Row"/>
					</a>
					<!-- Modal -->
					<div class="modal" id="add-delay-type-reason-modal">
						
					</div>
					<div class="modal" id="edit-delay-type-reason-modal">
					
					</div>
					<div class="modal" id="delete-delay-type-reason-modal">

					</div>
				</span>
				<table id="delay-type-reason-table">
					<thead>
						<tr>
							<th></th>
							<th>Delay Type</th>
							<th>Delay Reason</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${delays}" var="delays">
						<tr class="table-row">
							<td class="editable centerAlign width">
								<a class="rightMargin edit-button">Edit</a>
								<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/></a>
								<input type="hidden" class="association-id" value="${delays.delayTypeReasonId}"/>
							</td>
							<td class="delay-type">${delays.delayType}</td>
							<td class="delay-reason">${delays.delayReason}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div> 

		<input type="hidden" id="common-url" value="${commonStaticUrl}"/>
	</body>
</html>