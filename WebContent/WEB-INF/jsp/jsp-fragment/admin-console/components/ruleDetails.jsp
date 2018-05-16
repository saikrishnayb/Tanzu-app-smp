<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />
<script src="${context}/js/admin-console/components/template-rule.js" type="text/javascript"></script>

<div class="ruleDescription">
	<form:form method="post"  modelAttribute="ruleMaster" id="create-rule-form">	
		<form:input path="templateComponentId" id="templateComponentId" type="hidden" value="${templateComponentId}" />
		<form:input path="ruleType" id="ruleType" type="hidden" value="U"/>
		<form:input path="templateId" id="templateId" type="hidden" value="${templateId}"/>
		<form:input path="ruleId" id="ruleId" type="hidden" value="${ruleMaster.ruleId}"/>
		<form:input path="priority" id="priority" type="hidden" value=""/>
		<fieldset class="rulesFieldsetDisplay" >
			<div class="floatLeft marginLeft ruleName">
				<form:label path="ruleName" for="ruleName">Enter Rule Name</form:label>
				<form:input path="ruleName" id="ruleName" maxlength="50"  type="text"/>					
			</div>
			<div class="floatLeft marginLeft ruleDesc">
				<form:label path="description" for="description">Description</form:label>
				<form:input path="description" id="description" maxlength="100"  type="text"/>
			</div>	
			<div id="ErrorMsg" style="clear:both" class="floatLeft error-messages-container displayNone">
				<img src="${commonStaticUrl}/images/warning.png"></img>
				<span class="errorMsg"></span>
			</div>
				
			<table id="createRule-Table" >
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
								<form:select  class="searchSelect" id="componentsDropDown-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" path="ruleDefinitionsList[${loopIndex.count -1}].componentId"  onChange="loadOperands(${ruleDefinitionsList.criteriaGroup},${rowCount})">
									<form:option value=""></form:option>
									<c:forEach items="${componentsList}" var="component">
				                   		<form:option value="${component.componentId}-${component.componentType}">${component.componentName} (ID: ${component.componentId})</form:option>
				                   	</c:forEach>
								</form:select>
								</td>
								<td>
								<c:if test="${fn:length(ruleDefinitionsList.operandsList) eq 0}"><c:set var="disabledFlag" value="true" scope="page"></c:set></c:if>
								<form:select path="ruleDefinitionsList[${loopIndex.count -1}].operand" class="operandsDropDown" id="operandsID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" disabled="${disabledFlag}" onChange="disableComponentValue(${ruleDefinitionsList.criteriaGroup},${rowCount})">
								<c:forEach items="${ruleDefinitionsList.operandsList}" var="operands">
								<c:choose>
									<c:when test="${operands eq 'E'}">
										<form:option value="E">Exists On PO</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${operands}">${operands}</form:option>
									</c:otherwise>
								</c:choose>
				                 </c:forEach>
				                </form:select>
				                </td>
				                <c:if test="${ruleDefinitionsList.operand eq 'E'}"><c:set var="valuedisabledFlag" value="true" scope="page"></c:set></c:if>
				                <c:if test="${ruleDefinitionsList.operand ne 'E'}"><c:set var="valuedisabledFlag" value="false" scope="page"></c:set></c:if>
								<td><form:input path="ruleDefinitionsList[${loopIndex.count -1}].value" class="componentValue" id="valueID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" disabled="${valuedisabledFlag}" maxlength="30" type="text"/>
								<form:input type="hidden" id="ruleDefId" path="ruleDefinitionsList[${loopIndex.count -1}].ruleDefId" value="${ruleDefinitionsList.ruleDefId}"/><!-- Hidden field for ruleDefId -->
								<form:input type="hidden" class="criteriaGroupVal" path="ruleDefinitionsList[${loopIndex.count -1}].criteriaGroup" value="${ruleDefinitionsList.criteriaGroup}"/><!-- Hidden field for criteria group -->
								</td>
								<td><a><img src="${commonStaticUrl}/images/add.png"id="addRow" tabindex=0 class="centerImage handCursor adder" onclick="addNewRow('${ruleDefinitionsList.criteriaGroup}');" alt="Add Row"/></a></td>
							</tr>
						</c:if>
						<c:if test="${!ruleDefinitionsList.isGroupHeader}">
							<!-- incrementing row count -->
							<c:set var = "rowCount" scope = "page" value = "${rowCount +1}"/>
							<!-- Creating group rows -->
							<tr  class="group${ruleDefinitionsList.criteriaGroup}" id="G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}">
								<td class="editable centerAlign"></td>
								<td>
								<form:select class="searchSelect" id="componentsDropDown-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" path="ruleDefinitionsList[${loopIndex.count -1}].componentId"  onChange="loadOperands(${ruleDefinitionsList.criteriaGroup},${rowCount})"><%-- style="width:400px" --%>
									<form:option value=""></form:option>
									<c:forEach items="${componentsList}" var="component">
				                   		<form:option value="${component.componentId}-${component.componentType}">${component.componentName} (ID: ${component.componentId})</form:option><!--  -->
				                   	</c:forEach>
								</form:select>
								</td>
								<td>
								<c:if test="${fn:length(ruleDefinitionsList.operandsList) eq 0}"><c:set var="disabledFlag" value="true" scope="page"></c:set></c:if>
								<form:select path="ruleDefinitionsList[${loopIndex.count -1}].operand"  class="operandsDropDown" id="operandsID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" disabled="${disabledFlag}" onChange="disableComponentValue(${ruleDefinitionsList.criteriaGroup},${rowCount})">
								<c:forEach items="${ruleDefinitionsList.operandsList}" var="operands">
								<c:choose>
									<c:when test="${operands eq 'E'}">
										<form:option value="E">Exists On PO</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${operands}">${operands}</form:option>
									</c:otherwise>
								</c:choose>
								</c:forEach>
								</form:select>
								</td>
								<c:if test="${ruleDefinitionsList.operand eq 'E'}"><c:set var="valuedisabledFlag" value="true" scope="page"></c:set></c:if>
				                <c:if test="${ruleDefinitionsList.operand ne 'E'}"><c:set var="valuedisabledFlag" value="false" scope="page"></c:set></c:if>
								<td><form:input path="ruleDefinitionsList[${loopIndex.count -1}].value" class="componentValue" id="valueID-G_${ruleDefinitionsList.criteriaGroup}-R_${rowCount}" disabled="${valuedisabledFlag}" maxlength="30" type="text" />
								<form:input type="hidden" id="ruleDefId" path="ruleDefinitionsList[${loopIndex.count -1}].ruleDefId" value="${ruleDefinitionsList.ruleDefId}"/><!-- Hidden field for ruleDefId -->
								<form:input type="hidden" path="ruleDefinitionsList[${loopIndex.count -1}].criteriaGroup" value="${ruleDefinitionsList.criteriaGroup}"/><!-- Hidden field for criteria group -->
								</td>
								<td><a><img src="${commonStaticUrl}/images/delete.png" id="deleteRow" class="centerImage handCursor " alt="Delete Row"/></a></td>
							</tr>
						</c:if>					
					</c:forEach>
				</tbody>
				</table>
				<select class="displayNone"  id="componentsDropDown" >
				<c:forEach items="${componentsList}" var="component">
	           		<option value="${component.componentId}-${component.componentType}">${component.componentName} (ID: ${component.componentId})</option>
	  			</c:forEach>
				</select>
			</fieldset>
			<div class="floatLeft checkBoxDiv">
				<label>Outcome:</label>
					<label class="checkBoxPadding">Not Visible</label>
					<form:checkbox path="forRules" id="forRulesCheckBox"  value="${ruleMaster.forRules}" disabled="${ruleMaster.viewable}"/>
					
					<label>Visible</label>
					<form:checkbox path="viewable" id="visibleCheckBox" value="${ruleMaster.viewable}"
						disabled="${ruleMaster.required || ruleMaster.editable }"/>
					 
					<label class="checkBoxPadding">Editable</label>
					<form:checkbox path="editable" id="editCheckBox" value="${ruleMaster.editable}"
						disabled="${ruleMaster.required}"/>
					
					<label class="checkBoxPadding">Required</label>
					<form:checkbox path="required" id="reqdCheckBox" value="${ruleMaster.required}"/>
					
			</div>
			<div class="floatLeft addCriteriaGroup">
					<a href="javascript:void(0)" onClick="addNewGroup();">Add Criteria Group<img src="${commonStaticUrl}/images/add.png"id="addRow" class="leftPad centerImage handCursor  rightMargin" alt="Add Criteria Group"/></a>
			</div>
			<div class="floatRight  buttonSave">
					<a class="buttonPrimary" onClick="submitCreateRuleForm();" id="save" href="javascript:void(0)">Save</a>
			</div>
			<input type="hidden" id="pageAction" value="${pageAction}"/>
			<input type="hidden" id="numberOfRows" value="${fn:length(ruleMaster.ruleDefinitionsList)}"/>
	</form:form>
</div>