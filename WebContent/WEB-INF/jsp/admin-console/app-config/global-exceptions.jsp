<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Global Exceptions</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    	<!-- CSS -->
	    <link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/admin-console/app-config/global-exceptions.css" rel="stylesheet" type="text/css"/>
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
							<th>PO Group</th>
							<th>To be Provided By</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${exceptions}" var="exception">
						<tr>
							<td class="editable centerAlign width">
								<a class="rightMargin edit-exception">Edit</a>
								<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-exception"/></a>
								<input type="hidden" class="global-exception-id" value="${exception.exceptionId}"/>
							</td>
							<td class="component-name">${exception.componentName}</td>
							<td class="po-group">${exception.poGroup}</td>
							<td class="provided-by">
							${exception.providerPo}
							<input type="hidden" class="provided-by-po-sub" value="${exception.providerPoSub}"/>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="modal" id="modal-edit-global-exception">
				</div>
				<div class="modal" id="modal-delete-global-exception">
				</div>
			</div>
		</div> 
		
	</body>
	<!-- Scripts -->
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="${context}/js/admin-console/app-config/global-exceptions.js" type="text/javascript"></script>
</html>