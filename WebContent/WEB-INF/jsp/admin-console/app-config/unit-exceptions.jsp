<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Unit Exceptions</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/unit-exceptions.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<table id="exceptionTable" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Component Name</th>
							<th>Unit</th>
							<th>To be Provided By</th>
							<th>Created By</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${exceptions}" var="exception">
						<tr>
							<td class="editable centerAlign width">
								<a class="rightMargin edit-exception">Edit</a>
								<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-exception"/></a>
								<input type="hidden" class="unit-exception-id" value="${exception.exceptionId}"/>
							</td>
							<td>${exception.componentName}</td>
							<td>${exception.unitNumber}</td>
							<td>${exception.providerPo}</td>
							<td>${exception.createdBy}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="modal" id="modal-edit-unit-exception">
				</div>
				<div class="modal" id="modal-delete-unit-exception">
				</div>
			</div>
		</div> 
		
		<%@ include file="../../../jsp/jsp-fragment/global/footer.jsp" %>
	</body>
	<!-- Scripts -->
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/app-config/unit-exceptions.js" type="text/javascript"></script>
</html>