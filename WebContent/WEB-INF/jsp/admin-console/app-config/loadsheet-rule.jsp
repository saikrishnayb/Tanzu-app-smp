<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Loadsheet Rule</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    	<!-- Scripts -->
		<script src="${context}/js/admin-console/app-config/loadsheet-rule.js" type="text/javascript"></script>
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
				</span> 
				<div><h1>Loadsheet Rules</h1></div>
				<table id="loadsheet-rule-table" style="width:100%;">
					<thead>
						<tr>
							<th></th>
							<th>Rule</th>
							<th>Description</th>
							<th>Times Used</th>
							<th>Created by</th>
							<th>Created Date</th>
							<th>Edit by</th>
							<th>Edit Date</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${loadsheetRules}" var="loadsheetRule">
						<tr>
							<td class="editable centerAlign width">
							<a class="rightMargin edit-button" href="edit-rule.htm?ruleId=${loadsheetRule.ruleId}">Edit</a>
							<a><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/></a>
							</td>
							<td>${loadsheetRule.ruleName}</td>
							<td>${loadsheetRule.description}</td>
							<td>${loadsheetRule.timesUsed}</td>
							<td>${loadsheetRule.createdBy}</td>
							<td>${loadsheetRule.fmtCreatedDate}</td>
							<td>${loadsheetRule.editBy}</td>
							<td>${loadsheetRule.fmtEditedDate}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="floatRight">
				<a class="buttonPrimary" href="load-create-rule.htm" >Create Rule</a>
			</div>		
		</div> 
		<input type="hidden" id="common-url" value="${commonStaticUrl}"/>
	</body>
</html>