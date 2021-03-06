<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Loadsheet Components</title>
	
	<%@ include file="../../../jsp/global/v1/header.jsp" %>
	
	<link href="${baseUrl}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>
</head>

<!-- ***************************deactivate modal********************************** -->
<div id="Component-modal" class="comonent-modal modal" title="Component Rules">
</div>
<body>
	<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>
		<div class="leftNavAdjacentContainer">
		<h1 style="display: inline-block;">Loadsheet Management - ${category} - ${type}</h1>
			<div class="full-width">
				<table id="component-table">
					<thead>
						<tr>
							<th>Component Group </th>
							<th>Sub-Group</th>
							<th>Component</th>
							<th>LoadSheet</th>
							<th>2B</th>
							<th>Rule</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${components}" var="component">
						<tr class="component-row" id="${component.componentId}">
							<td>${component.group}</td>
							<td>${component.subGroup}</td>
							<td class="editable">${component.componentName}</td>
							<td>${component.lsOverride}</td>
							<td>${component.screen2b}</td>
							<td>
							<a class="add-rule-association"  data-value="${component.group}${not empty component.subGroup ?' - ':''}${component.subGroup}${not empty component.componentName ?' - ':''}${component.componentName}" id="${component.componentId}-${component.componentVisibilityId}-${viewMode}"><c:choose>
							        <c:when test="${viewMode eq 'Y' }">
							         <c:if test="${component.ruleCount gt 0 }">
							         View Rule(${component.ruleCount})
							        </c:if>
							        </c:when>
								    <c:when test="${component.ruleCount gt 0 }">Add / Modify Existing Rules(${component.ruleCount})</c:when>
									<c:otherwise>Add Rule</c:otherwise>
								</c:choose></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="notes" style="padding-top: 1%">
				<%@ include file="/WEB-INF/jsp/admin-console/app-config/loadSheet-footer.jspf" %>
			</div>
		</div>
		
	<!-- popup for Rule Association -->
	<div id="rule-association-modal" class="modal"></div>
	</div>
	<!-- Hidden fields to auto open the rule Association popup -->
	<input type="hidden" id="componentId" value="${componentId }"/>
	<input type="hidden" id="componentIdforAddRule" value=""/>
	<input type="hidden" id="visibilityId" value="${visibilityId}"/>
	<input type="hidden" id="viewMode" value="${viewMode}"/>
	
	<input type="hidden" id="selectedComponentId" value="${selectedComponentId}"/>
	<input type="hidden" id="compRequestedFrom" value="${compRequestedFrom}"/>
	
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/app-config/loadsheet-components.js"	type="text/javascript"></script>
</body>
</html>