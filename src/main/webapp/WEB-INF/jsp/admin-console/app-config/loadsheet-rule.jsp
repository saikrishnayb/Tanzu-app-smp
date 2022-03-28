<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Loadsheet Rule</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>
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
							<th>Associated With</th>
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
							<a class="rightMargin edit-button" onCLick="processingImageAndTextHandler('visible','Loading data...');" href="edit-rule?ruleId=${loadsheetRule.ruleId}&requestedFrom=CREATE_RULE">Edit</a>
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
				<a class="buttonPrimary " href="load-create-rule?requestedFrom=CREATE_RULE" >Create Rule</a>
			</div>		
		</div> 
		<!-- Delete Rule Confirmation Popup -->
		<div id="confirmDeleteModal">
			<p id="deleteMessage"></p>
	
			<div style="position:absolute;bottom:3px;right:5px;">
				<a href="javascript:void(0)" class="secondaryLink" onclick="closeConfirmDialog();"tabIndex="-1">No</a> 
				<a href="javascript:void(0)" class="buttonPrimary" onclick="confirmDeleteRule()" tabIndex="-1">Yes</a>
			</div>
		</div>
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/loadsheet-rule.js" type="text/javascript"></script>
</body>
</html>