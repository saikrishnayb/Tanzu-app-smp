<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Dynamic Rules</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/dynamic-rules.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>

			<div class="leftNavAdjacentContainer">
<c:choose>
    <c:when test="${access eq true}">
				<span id="noteSpan" class="bold">Note: These Rules will be used for Get Next Deal Process. Buyers will receive Deals matching against Priority Order.</span>
				
				<!-- Rule Table -->
				<table id="rule-table" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Corp</th>
							<th>Manufacturer</th>
							<th>Model</th>
							<th>Status</th>
							<th>Model Year</th>
							<th>Priority</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="rule" items="${activeDynamicRules}">
						<tr>
							<td class="editable centerAlign">
								<a class="rightMargin edit-rule">Edit</a>
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin handCursor delete-rule"/>
								<input name="dynamicRuleId" type="hidden" value="${rule.dynamicRuleId}" />
							</td>
							<td class="corp-code">${rule.corpCode}</td>
							<td class="manufacturer">${rule.manufacturer}</td>
							<td class="model">${rule.model}</td>
							<td class="status"><c:choose>
											<c:when test="${rule.status eq 'A'}">Active</c:when>
											<c:otherwise>Inactive</c:otherwise>
										</c:choose></td>
							<td class="model-year"><c:if test="${rule.modelYear gt 0}">${rule.modelYear}</c:if></td>
							<td class="priority">${rule.priority}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<span class="floatRight addRow">
					<a class="add-rule">Add Rule</a>
					<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor add-rule" alt="Add Rule"/>
				</span>
				
				
				<table id="rule-inactive-table" >
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Corp</th>
							<th>Manufacturer</th>
							<th>Model</th>
							<th>Status</th>
							<th>Model Year</th>
							<!--  <th>Priority</th>-->
						</tr>
					</thead>
					<tbody>
						<c:forEach var="rule" items="${inactiveDynamicRules}">
						<tr>
							<td class="editable centerAlign">
								<a class="rightMargin edit-rule">Edit</a>
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin handCursor delete-rule"/>
								<input name="dynamicRuleId" type="hidden" value="${rule.dynamicRuleId}" />
							</td>
							<td class="corp-code">${rule.corpCode}</td>
							<td class="manufacturer">${rule.manufacturer}</td>
							<td class="model">${rule.model}</td>
							<td class="status"><c:choose>
											<c:when test="${rule.status eq 'A'}">Active</c:when>
											<c:otherwise>Inactive</c:otherwise>
										</c:choose></td>
							<td class="model-year"><c:if test="${rule.modelYear gt 0}">${rule.modelYear}</c:if></td>
							<!-- <td class="priority">${rule.priority}</td>-->
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<!-- Edit Rule Modal -->
				<div id="edit-rule-modal" class="modal"></div>
				
				<!-- Add Rule Modal -->
				<div id="add-rule-modal" class="modal"></div>
</c:when>
	<c:otherwise>
	       <span style="float: center;color: #CC0000" >
				You are not authorized to see the dynamic rule page. Please Contact Support for further assistance.
			</span> 
	 </c:otherwise>
</c:choose>				
			</div>
		</div> 
		
	</body>
	<!-- Scripts -->
	<script src="${baseUrl}/js/admin-console/app-config/dynamic-rules.js" type="text/javascript"></script>
</html>