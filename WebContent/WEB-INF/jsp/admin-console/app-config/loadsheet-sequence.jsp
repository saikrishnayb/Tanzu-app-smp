<!DOCTYPE html>
<html>
<head>
	<title>Loadsheet Sequence</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
	<link href="${context}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>
	<Script>
	var hostUrl='${context}';
	</Script>
</head>

<!-- ***************************deactivate modal********************************** -->
<div id="Sequence-modal" class="Sequence modal" title="Loadsheet Sequence">
</div>
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
		<input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
			<div class="full-width">
			<h1 style="display: inline-block;">Loadsheet Sequencing</h1>
				
				<table id="sequence-table" >
					<thead>
						<tr>
							<th class="viewCol" style="width:9%"></th>
							<th>Loadsheet Sequence Name</th>
							<th>Description</th>
							<th>Category</th>
							<th>Type</th>
							<th>OEM</th>
							<th>Edited By</th>
							<th>Edited Date</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sequences}" var="temp">
						<tr class="sequence-row">
							<td class="editable centerAlign">
								<a class="rightMargin edit-sequence">Edit</a>
								<a class="rightMargin copy-sequence">Copy</a>
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin deactivate"/>
								<input  class="template-id" type=hidden value="${temp.id}"/>
							</td>
							<td>${temp.name}</td>
							<td>${temp.description}</td>
							<td>${temp.category}</td>
							<td>${temp.type}</td>
							<td>${temp.oem}</td>
							<td>${temp.editedBy}</td>
							<td>${temp.fmtEditedDate}</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
<!-- Scripts -->		
<script src="${context}/js/admin-console/app-config/loadsheet-sequence.js"	type="text/javascript"></script>
</html>