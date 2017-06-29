<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Loadsheet Rule</title>
	    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    	<!-- Scripts -->
		<script src="${context}/js/admin-console/app-config/create-rule.js" type="text/javascript"></script>
		<script src="${context}/js/v2/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/admin-console/app-config/delay-management.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
			<form:form method="post"  modelAttribute="ruleMaster" id="create-rule-form">	
				<div>
				<div class="floatLeft"><h1>Loadsheet Rules</h1></div>
				<div class="floatLeft marginLeft" style="margin-top:4px;margin-right:5%;">
					<form:label path="ruleName" for="ruleName">Enter Rule Name</form:label>
					<form:input path="ruleName" id="ruleName" maxlength="50"  type="text"/>
					<form:input path="ruleId" id="ruleId"  type="hidden" value="${ruleMaster.ruleId }"/>
				</div>
				<div class="floatLeft marginLeft" style="margin-top:4px">
					<form:label path="description" for="description">Description</form:label>
					<form:input path="description" id="description" maxlength="100"  type="text"/>
				</div>
				<div class="floatRight">
					<a class="buttonSecondary" href="goBack-createRule.htm?requestedFrom=${requestedFrom}" onclick="javascript:loadProcessImage();" >Back</a>
				</div>
				</div>
				<div id="ErrorMsg" style="clear:both" class="floatLeft error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class="errorMsg"></span>
				</div>
			
			<div>
				<fieldset style="width:100%">
					<legend>Rules</legend>
					<div class="displayNone" id="AddCriteriaGroup">
					<div class="floatLeft rightMargin">
						<a href="javascript:void(0)" onClick="addNewGroup();">Add Criteria Group<img src="${commonStaticUrl}/images/add.png"id="addRow" class="leftPad centerImage handCursor adder rightMargin" alt="Add Criteria Group"/></a>
					</div>
					<div class="floatRight leftMargin">
						<a class="buttonPrimary" onClick="submitCreateRuleForm();" id="save" href="javascript:void(0)">Save</a>
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
							<!-- Setting row count to 1 -->
							<c:set var = "rowCount" scope = "page" value = "1"/>
							<!-- Creating group header column -->
							<tr  class="groupHeader group${ruleDefinitionsList.criteriaGroup}" id="G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}">
								<td class="editable centerAlign"><a href="javascript:void(0)" class="rightMargin" onClick="copyGroup('${ruleDefinitionsList.criteriaGroup}')">Copy</a><a href="javascript:void(0)" onClick="deleteGroup('${ruleDefinitionsList.criteriaGroup}');"><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/></a></td>
								<td>
								<form:select id="componentsDropDown-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" path="ruleDefinitionsList[${loopIndex.count -1}].componentId"  onChange="loadOperands(${ruleDefinitionsList.criteriaGroup},${rowCount})" style="width:100%">
									<form:option value=""></form:option>
									<c:forEach items="${componentsList}" var="component">
				                   		<form:option value="${component.componentId}-${component.componentType}">${component.componentGroup}<c:if test="${not empty  component.subGroup}"> ${component.subGroup}</c:if> - ${component.componentName} (ID: ${component.componentId})</form:option>
				                   	</c:forEach>
								</form:select>
								</td>
								<td>
								<c:if test="${fn:length(ruleDefinitionsList.operandsList) eq 0}"><c:set var="disabledFlag" value="true" scope="page"></c:set></c:if>
								<form:select path="ruleDefinitionsList[${loopIndex.count -1}].operand"  id="operandsID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}"  disabled="${disabledFlag}">
								<c:forEach items="${ruleDefinitionsList.operandsList}" var="operands">
				                   		<form:option value="${operands}">${operands}</form:option>
				                 </c:forEach>
				                </form:select>
				                </td>
								<td><form:input path="ruleDefinitionsList[${loopIndex.count -1}].value"  id="valueID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" maxlength="30" type="text"/>
								<form:input type="hidden" id="ruleDefId" path="ruleDefinitionsList[${loopIndex.count -1}].ruleDefId" value="${ruleDefinitionsList.ruleDefId}"/><!-- Hidden field for ruleDefId -->
								<form:input type="hidden" class="criteriaGroupVal" path="ruleDefinitionsList[${loopIndex.count -1}].criteriaGroup" value="${ruleDefinitionsList.criteriaGroup}"/><!-- Hidden field for criteria group -->
								</td>
								<td><a><img src="${commonStaticUrl}/images/add.png"id="addRow" class="centerImage handCursor adder" onclick="addNewRow('${ruleDefinitionsList.criteriaGroup}');" alt="Add Row"/></a></td>
							</tr>
						</c:if>
						<c:if test="${!ruleDefinitionsList.isGroupHeader}">
							<!-- incrementing row count -->
							<c:set var = "rowCount" scope = "page" value = "${rowCount +1}"/>
							<!-- Creating group rows -->
							<tr  class="group${ruleDefinitionsList.criteriaGroup}" id="G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}">
								<td class="editable centerAlign"></td>
								<td>
								<form:select id="componentsDropDown-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" path="ruleDefinitionsList[${loopIndex.count -1}].componentId"  onChange="loadOperands(${ruleDefinitionsList.criteriaGroup},${rowCount})" style="width:100%">
									<form:option value=""></form:option>
									<c:forEach items="${componentsList}" var="component">
				                   		<form:option value="${component.componentId}-${component.componentType}">${component.componentGroup}<c:if test="${not empty  component.subGroup}"> ${component.subGroup}</c:if> - ${component.componentName} (ID: ${component.componentId})</form:option>
				                   	</c:forEach>
								</form:select>
								</td>
								<td>
								<form:select path="ruleDefinitionsList[${loopIndex.count -1}].operand"  id="operandsID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" >
								<c:forEach items="${ruleDefinitionsList.operandsList}" var="operands">
				                   		<form:option value="${operands}">${operands}</form:option>
				                 </c:forEach>
								</form:select>
								</td>
								<td><form:input path="ruleDefinitionsList[${loopIndex.count -1}].value"  id="valueID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" maxlength="30" type="text"/>
								<form:input type="hidden" id="ruleDefId" path="ruleDefinitionsList[${loopIndex.count -1}].ruleDefId" value="${ruleDefinitionsList.ruleDefId}"/><!-- Hidden field for ruleDefId -->
								<form:input type="hidden" path="ruleDefinitionsList[${loopIndex.count -1}].criteriaGroup" value="${ruleDefinitionsList.criteriaGroup}"/><!-- Hidden field for criteria group -->
								</td>
								<td><a><img src="${commonStaticUrl}/images/delete.png" id="deleteRow" class="centerImage handCursor " alt="Delete Row"/></a></td>
							</tr>
						</c:if>
						
						
					</c:forEach>
					</tbody>
				</table>
				</fieldset>
				<c:if test="${ fn:length(loadSheetManagementList) gt 0 }">
				<fieldset style="width:100%;margin-top:10px;">
					<legend>Loadsheet Category Assigned</legend>
					<div class="displayNone" id="AssignedCount">
					<div class="floatLeft">
						<p>Assigned #: ${ fn:length(loadSheetManagementList)} </p>
					</div>
					</div>
					<table id="Assigned-Table" style="width:100%;">
						<thead>
							<tr>
								<th></th> 
								<th>Category</th>
								<th>Sub Category</th>
								<th>Component Group</th>
								<th>Sub-Group</th>
								<th>Component</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${loadSheetManagementList }" var="loadSheetDetails">
							<tr>
								<td class="editable centerAlign"><a onclick="javascript:loadProcessImage();" href="get-loadsheet-components.htm?categoryId=${loadSheetDetails.catTypeId}&category=${loadSheetDetails.category} &type=${loadSheetDetails.type} &viewMode=${loadSheetDetails.usesDefault}&compRqstdFrom=EDIT_RULE&componentId=${loadSheetDetails.componentId}">Go To</a></td>
								<td>${loadSheetDetails.category }</td>
								<td>${loadSheetDetails.type }</td>
								<td>${loadSheetDetails.componentGroup }</td>
								<td>${loadSheetDetails.subGroup }</td>
								<td>${loadSheetDetails.component }</td>
							</tr>
							</c:forEach>	
						</tbody>
					</table>
				</fieldset>
				</c:if>
			</div>
			<input type="hidden" name="requestedFrom" value="${requestedFrom}"/>
			</form:form>
			</div>	
		</div> 
		<!-- Hidden Fields -->
		<input type="hidden" id="pageAction" value="${pageAction}"/>
		<input type="hidden" id="numberOfRows" value="${fn:length(ruleMaster.ruleDefinitionsList)}"/>
		<select class="displayNone" id="componentsDropDown">
							<c:forEach items="${componentsList}" var="component">
		                   		<option value="${component.componentId}-${component.componentType}">${component.componentGroup}<c:if test="${not empty  component.subGroup}"> ${component.subGroup}</c:if> - ${component.componentName} (ID: ${component.componentId})</option>
		                   	</c:forEach>
		</select>
		<input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
	</body>
</html>