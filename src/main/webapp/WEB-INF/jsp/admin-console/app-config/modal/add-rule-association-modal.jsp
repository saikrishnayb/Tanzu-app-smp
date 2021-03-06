<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="${baseUrl}/css/admin-console/app-config/add-rule-association.css" rel="stylesheet" type="text/css"/>

<form:form id="add-rule-association-form" name="add-rule-association-form" modelAttribute="componentRule" method="POST">
		
	<form:hidden path="componentVisibilityId" id="componentVisibilityId"></form:hidden>
	<input id="ruleCount" type="hidden" name="ruleCount"  value="${fn:length(componentRule.rule)}"/>
	<!-- hidden fields to populate option values for added select boxes -->
	<select   id="ruleId" style="display: none">
	         <option value=" "></option>
			<c:forEach var="temp" items="${rules}">
			<option value="${temp.ruleId}">${temp.ruleName}</option>
			</c:forEach>
	</select>
	<select   id="rulelsOverride" style="display: none">
			<option value=" "></option>
			<option value="R">R</option>
			<option value="A">A</option>
			<option value="N">N</option>
	</select>
	<h2 style="display: inline-block;">${componentRule.displayName}</h2>
	<div id="label" style="width:100%">
	<div class="column-left"><label>Priority <span class="errorMsg">*</span></label></div>
	<div class="column-center"><label>Rule <span class="errorMsg">*</span></label></div>
	<div class="column-right"><label>LS Override <span class="errorMsg">*</span></label></div>
	<div class="column-right-most">
	<c:if test="${viewMode ne 'Y'}">
	<a style="padding-left: 20%;" id="addRule"  onclick="addRule();"><img class="addRow"
			 src="<c:out value='${commonStaticUrl}'/>/images/add.png"/></a>
	</c:if>
	</div>
	</div>
	<div id="rules" style="width:100%">
	<c:choose>
	<c:when test="${viewMode eq 'Y'}">
			<c:set var="disableInput" value="true" />
			<c:set var="disableSelecte" value="${1 == 1 }" />
			</c:when>
			<c:otherwise>
			<c:set var="disableInput" value="false" />
			<c:set var="disableSelecte" value="${1 == 2 }" />
			</c:otherwise>
	</c:choose>
	<c:choose>
			<c:when test="${fn:length(componentRule.rule) gt 0}"> 
			    <!-- if the component already has associated rules -->
				<c:forEach items="${componentRule.rule}" varStatus="indexCount">
					<div id="rule${indexCount.index }" style="width:100%;padding-top: 1%">
						<div class="column-data-left"><form:input maxlength="2" readonly="${disableInput}"  path="rule[${indexCount.index }].priority" class="priority" id="ruleProirity${indexCount.index }" style="width:74%;text-align:right;"/></div> 
						
						<div class="column-data-center"><form:select  disabled="${disableSelecte}" class="rule" path="rule[${indexCount.index }].ruleId" style="width:92%" id="ruleId${indexCount.index }">
									<form:option value=" "></form:option>
									<c:forEach var="temp" items="${rules}">
										<form:option value="${temp.ruleId}">${temp.ruleName}</form:option>
									</c:forEach>
						</form:select></div>
					    <div class="column-data-right"><form:select  disabled="${disableSelecte}" class="lsOverRide" path="rule[${indexCount.index }].lsOverride"  id="rulelsOverride${indexCount.index }" style="width:60%">
										<form:option value=" "></form:option>
										<form:option value="R">R</form:option>
										<form:option value="A">A</form:option>
										<form:option value="N">N</form:option>
					    </form:select>
					     <c:if test="${viewMode ne 'Y'}">
					     <a style="text-decoration:none;padding-left: 20%;" id="deleteRule" tabindex=0 onclick="deleteRule(${indexCount.index});"><img class="addRow"
						           src="<c:out value='${commonStaticUrl}'/>/images/delete.png"/></a>
						</c:if>
						</div>
					</div>
	           </c:forEach>
			
		    </c:when>
			<c:otherwise>
			 <!-- if the component doesn't have any associated rules -->
			 <c:if test="${viewMode ne 'Y'}">
			    <div id="rule0" style="width:100%;padding-top: 1%">
						<div class="column-data-left"><form:input maxlength="2" path="rule[0].priority" required="required" class="priority" value="1" id="ruleProirity${indexCount.index }" style="width:74%;text-align:right;"/></div> 
						
						<div class="column-data-center"><form:select  path="rule[0].ruleId" class="rule" style="width:92%" id="ruleId0">
						<form:option value=" "></form:option>
									<c:forEach var="temp" items="${rules}">
										<form:option value="${temp.ruleId}">${temp.ruleName}</form:option>
									</c:forEach>
						</form:select></div>
					    <div class="column-data-right"><form:select  path="rule[0].lsOverride" class="lsOverRide" style="width:60%" id="rulelsOverride0">
					                    <form:option value=" "></form:option>
										<form:option value="R">R</form:option>
										<form:option value="A">A</form:option>
										<form:option value="N">N</form:option>
					    </form:select>
					  <a style="text-decoration:none;padding-left: 20%;" id="deleteRule" tabindex=0 href="#"onclick="deleteRule(0);"><img class="addRow"
						           src="<c:out value='${commonStaticUrl}'/>/images/delete.png"/></a>
				</div>
				</div>
			   </c:if>
			   
			</c:otherwise>
		</c:choose>
	
	
	
	</div>
	<%-- <input class="floatLeft width-100" type="text" name="priority" value="${maxPriority}" autocomplete="off" /> --%>
	 <c:if test="${viewMode ne 'Y'}">
		<div style="width: 100%;display: inline-block;margin-top:10px">
		     <a id="saveRuleAssociation" class="buttonPrimary floatRight  save" tabindex=0>Save</a>
		     <div class="error floatRight hidden margin-upper-right">
			   <img src="${commonStaticUrl}/images/warning.png">
			   <span class="errorMsg"></span>
		     </div>
		</div>
	 </c:if>
	
</form:form>

<script src="${baseUrl}/js/admin-console/app-config/modal/add-rule-association-modal.js" type="text/javascript"></script>