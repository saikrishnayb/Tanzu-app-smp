<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Delay Management</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    	<!-- Scripts -->
		<script src="${context}/js/admin-console/app-config/delay-management.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
				<span class="floatRight addRow push-right">
					<a href="#" class="addDelay" id="add-new-delay">
						Add Delay
						<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor adder" alt="Add Row"/>
					</a>
				</span>
				<table id="delay-table" style="width:100%;">
					<thead>
						<tr>
							<th></th>
							<th>Date Type</th>
							<th>PO Category</th>
							<th>Delay Type</th>
							<th>Delay Reason</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${delays}" var="delay">
						<tr>
							<td class="editable centerAlign width">
								
								<a class="rightMargin edit-button">Edit</a>
								<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/></a>
								<input type="hidden" class="delay-id" value="${delay.delayId}"/>
							</td>
							<td class="date-type">${delay.dateType}</td>
							<td class="po-category">${delay.poCategory}</td>
							<td class="delay-type">${delay.delayType}</td>
							<td class="delay-reason">${delay.delayReason}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				
				<!-- Modal -->
					<div class="modal modal-delay" id="add-delay-modal">
						
					</div>
					<div class="modal modal-delay" id="edit-delay-modal">
					
					</div>
					<div class="modal modal-delay" id="delete-delay-modal">

					</div>
			</div>		
		</div> 
		<%@ include file="../../../jsp/jsp-fragment/global/footer.jsp" %>
		<input type="hidden" id="common-url" value="${commonStaticUrl}"/>
	</body>
</html>