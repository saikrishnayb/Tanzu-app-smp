<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Loadsheet Rule</title>
	    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    	<!-- Scripts -->
		<script src="${context}/js/admin-console/app-config/create-rule.js" type="text/javascript"></script>
		<%-- <script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script> --%>
		<script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<!-- <link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/> -->
		<link href="${context}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
			<form:form method="post" action="create-rule.htm" modelAttribute="ruleMaster" id="create-rule-form">	
				<div>
				<div class="floatLeft"><h1>Loadsheet Rules</h1></div>
				<div class="floatLeft marginLeft" style="margin-top:4px">
					<form:label path="ruleName" for="ruleName">Enter Rule Name</form:label>
					<form:input path="ruleName" id="ruleName"  type="text"/>
				</div>
				<div class="floatLeft marginLeft" style="margin-top:4px">
					<form:label path="description" for="description">Description</form:label>
					<form:input path="description" id="description"  type="text"/>
				</div>
				<div class="floatRight">
					<a class="buttonSecondary" href="loadsheet-rule.htm" onclick="javascript:loadProcessImage();" >Back</a>
				</div>
				</div>
			
			
			<div>
				<fieldset style="width:100%">
					<legend>Rules</legend>
					<div class="displayNone" id="AddCriteriaGroup">
					<div class="floatLeft rightMargin">
						<a href="#" onClick="addNewGroup();">Add Criteria Group<img src="${commonStaticUrl}/images/add.png"id="addRow" class="leftPad centerImage handCursor adder" alt="Add Criteria Group"/></a>
					</div>
					<div class="floatRight leftMargin rightMargin">
						<a class="buttonPrimary" onClick="submitCreateRuleForm();" id="save" href="#">Save</a>
					</div>
					</div>
					
					<table id="createRule-Table" style="width:100%;">
					<thead>
						<tr>
							<th></th> 
							<th>Component</th>
							<th></th>
							<th>Value</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${ruleMaster.ruleDefinitionsList }" var="ruleDefinitionsList" varStatus="loopIndex">
						<c:if test="${ruleDefinitionsList.isGroupHeader}">
							<!-- Seeting row count to 1 -->
							<c:set var = "rowCount" scope = "page" value = "1"/>
							<!-- Creating group header column -->
							<tr id="G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" class="groupHeader group${ruleDefinitionsList.criteriaGroup}">
								<td class="editable centerAlign"><a href="#" class="rightMargin" onClick="copyGroup('${ruleDefinitionsList.criteriaGroup}')">Copy</a><a href="#" onClick="deleteGroup('${ruleDefinitionsList.criteriaGroup}');"><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/></a></td>
								<td>
								<form:select id="componentsDropDown-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" path="ruleDefinitionsList[${loopIndex.count -1}].componentId"  onChange="loadOperands(${ruleDefinitionsList.criteriaGroup},${rowCount})" style="width:100%">
									<form:option value=""></form:option>
									<c:forEach items="${componentsList}" var="component">
				                   		<form:option value="${component.componentId}-${component.componentType}">${component.componentGroup}-${component.subGroup} (ID: ${component.componentId})</form:option>
				                   	</c:forEach>
								</form:select>
								</td>
								<td>
								<form:select path="ruleDefinitionsList[${loopIndex.count -1}].operand"  id="operandsID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" disabled>
								</form:select>
								</td>
								<td><form:input path="ruleDefinitionsList[${loopIndex.count -1}].value"  id="valueID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" type="text"/>
								<form:input type="hidden" class="criteriaGroupVal" path="ruleDefinitionsList[${loopIndex.count -1}].criteriaGroup" value="${ruleDefinitionsList.criteriaGroup}"/><!-- Hidden filed for criteria group -->
								</td>
								<td><a><img src="${commonStaticUrl}/images/add.png"id="addRow" class="centerImage handCursor adder" onclick="addNewRow('${ruleDefinitionsList.criteriaGroup}');" alt="Add Row"/></a></td>
							</tr>
						</c:if>
						<c:if test="${!ruleDefinitionsList.isGroupHeader}">
							<!-- incrementing row count -->
							<c:set var = "rowCount" scope = "page" value = "${rowCount +1}"/>
							<!-- Creating group rows -->
							<tr id="G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" class="group${ruleDefinitionsList.criteriaGroup}">
								<td class="editable centerAlign"></td>
								<td>
								<form:select id="componentsDropDown-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" path="ruleDefinitionsList[${loopIndex.count -1}].componentId"  onChange="loadOperands(${ruleDefinitionsList.criteriaGroup},${rowCount})" style="width:100%">
									<form:option value=""></form:option>
									<c:forEach items="${componentsList}" var="component">
				                   		<form:option value="${component.componentId}-${component.componentType}">${component.componentGroup}-${component.subGroup} (ID: ${component.componentId})</form:option>
				                   	</c:forEach>
								</form:select>
								</td>
								<td>
								<form:select path="ruleDefinitionsList[${loopIndex.count -1}].operand"  id="operandsID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" disabled>
								</form:select>
								</td>
								<td><form:input path="ruleDefinitionsList[${loopIndex.count -1}].value"  id="valueID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" type="text"/>
								<form:input type="hidden" path="ruleDefinitionsList[${loopIndex.count -1}].criteriaGroup" value="${ruleDefinitionsList.criteriaGroup}"/><!-- Hidden filed for criteria group -->
								</td>
								<td><a><img src="${commonStaticUrl}/images/delete.png" id="deleteRow" class="centerImage handCursor " alt="Delete Row"/></a></td>
							</tr>
						</c:if>
						
						
					</c:forEach>
					</tbody>
				</table>
				</fieldset>
			</div>
			</form:form>
			</div>	
		</div> 
		<!-- Hidden Fields -->
		<select class="displayNone" id="componentsDropDown">
							<c:forEach items="${componentsList}" var="component">
		                   		<option value="${component.componentId}-${component.componentType}">${component.componentGroup}-${component.subGroup} (ID: ${component.componentId})</option>
		                   	</c:forEach>
		</select>
		<input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
	</body>
</html>