<!DOCTYPE html>
<html>
<head>
	<title>SMC Home</title>
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
<div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Template Deactivation">
</div>
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<div class="full-width">
				<span class="floatRight addRow">
					<a href="${pageContext.request.contextPath}/admin-console/security/create-modify-template-page.htm?isCreatePage=true &templateId=0">Create Template</a>
					<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
				</span>
				<table id="template-table" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Template ID</th>
							<th>Description</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${templateList}" var="temp">
						<tr class="template-row">
							<td class="editable centerAlign">
								<a class="rightMargin edit-template">Edit</a>
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin deactivate"/>
								<input  class="template-id" type=hidden value="${temp.templateID}"/>
							</td>
							<td>${temp.templateID}</td>
							<td class="template-name">${temp.templateDesc}</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
<!-- Scripts -->		
<script src="${context}/js/admin-console/security/template-form.js"	type="text/javascript"></script>
</html>