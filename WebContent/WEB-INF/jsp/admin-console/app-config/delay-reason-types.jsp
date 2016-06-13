<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Delay Reason Types</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	   	<!-- Scripts -->
		<script src="${context}/js/admin-console/app-config/delay-reason-types.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/admin-console/app-config/delay-reason-types.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
				<span class="floatRight addRow push-right">
					<a class="add-delay-type">
						Add Delay Type
						<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor adder" alt="Add Row"/>
					</a>
					<!-- Modal -->
					<div class="modal" id="add-delay-type-modal">
						
					</div>
					<div class="modal" id="edit-delay-type-modal">
					
					</div>
					<div class="modal" id="delete-delay-type-modal">

					</div>
				</span>
				<table id="delay-type-table">
					<thead>
						<tr>
							<th></th>
							<th>Delay Type</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${types}" var="types">
						<tr class="table-row">
							<td class="editable centerAlign width">
								<a class="rightMargin edit-button">Edit</a>
								<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/></a>
								<input type="hidden" class="delay-type-id" value="${types.typeId}"/>
							</td>
							<td class="delay-type">${types.delayType}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div> 
		<input type="hidden" id="common-url" value="${commonStaticUrl}"/>
	</body>
</html>