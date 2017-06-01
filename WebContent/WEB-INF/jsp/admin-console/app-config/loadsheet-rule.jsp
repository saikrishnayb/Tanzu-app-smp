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
							<a class="rightMargin edit-button" onCLick="processingImageAndTextHandler('visible','Loading data...');" href="edit-rule.htm?ruleId=${loadsheetRule.ruleId}&requestedFrom=CREATE_RULE">Edit</a>
							<a href="javascript:void(0)" ><img src="${commonStaticUrl}/images/delete.png" id="deleteRule" class="centerImage rightMargin delete-button"/></a>
							<input type="hidden" id="ruleId" value="${loadsheetRule.ruleId}"/>
							<input type="hidden" id="ruleName" value="${loadsheetRule.ruleName}"/>
							<input type="hidden" id="timesUsed" value="${loadsheetRule.timesUsed}"/>
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
			<div class="floatRight" style="margin-top:20px;">
				<a class="buttonPrimary " href="load-create-rule.htm?requestedFrom=CREATE_RULE" >Create Rule</a>
			</div>		
		</div> 
		<input type="hidden" id="common-url" value="${commonStaticUrl}"/>
		<!-- Delete Rule Confirmation Popup -->
		<div id="confirmDeleteModal">
			<p id="deleteMessage"></p>
	
			<div style="position:absolute;bottom:3px;right:5px;">
				<a href="javascript:void(0)" class="secondaryLink" onclick="closeConfirmDialog();"tabIndex="-1">No</a> 
				<a href="javascript:void(0)" class="buttonPrimary" onclick="confirmDeleteRule()" tabIndex="-1">Yes</a>
			</div>
		</div>
</body>
</html>