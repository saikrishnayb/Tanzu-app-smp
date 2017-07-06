<!DOCTYPE html>
<html>
<head>
	<title>Loadsheet Components</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
    <script src="${context}/js/admin-console/app-config/loadsheet-components.js"	type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
	<link href="${context}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>
	<link href="${context}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<Script>
	var hostUrl='${context}';
	</Script>
</head>

<!-- ***************************deactivate modal********************************** -->
<div id="Component-modal" class="comonent-modal modal" title="Component Rules">
</div>
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
		<input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
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
							<a class="add-rule-association" id="${component.componentId}-${component.componentVisibilityId}-${viewMode}"><c:choose>
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
			<fieldset style="width:50%;height:15px;">
			<pre>   R=Required Component     A=Allowed Component     N=Not Allowed     D=Display VEHTVINR even if not entered    Blank=Use Default</pre>
            </fieldset>
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

</body>
</html>