<!DOCTYPE html>

<html>
	<head> 
	    <title>SMC Loadsheet Rule</title>
	    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	    <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
	</head>
	<body>
	<div id="rulePopupDiv">
		<div class="ruleNameList">
		<input type="hidden" id="ruleCount" value="${fn:length(rulesList)}"/>
		  <table id="rulesTable">
			<thead>
			   <div class="existingRulesHeading"><span><a onClick="addNewRule()" >Existing Rules</a></span>
			   <img src="${commonStaticUrl}/images/add.png" onClick="addNewRule()" id="addNewRule" class="centerImage handCursor adderAlign rightMargin" /></div>
			</thead>
			<tbody>
				<c:forEach items="${rulesList}" var="rule" varStatus="cmpIndex">
				
				<tr class="handCursor currentRow <c:if test='${cmpIndex.index eq 0}'>highlightRule</c:if>" id="rule-${rule.ruleId}" >
					
					<td><img src="${commonStaticUrl}/images/delete.png" id="deleteRule" ruleId="${rule.ruleId}" onclick="deleteRule(${rule.ruleId})"class="imageAlign handCursor rightMargin deleteRule"/></td>
					<td onClick="getRuleDetails(${rule.ruleId});"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></td>
  					<td class="pointer rules" onClick="getRuleDetails(${rule.ruleId});" ruleId="${rule.ruleId}">${rule.ruleName}</td>
	   				<td class="seq priority" onClick="getRuleDetails(${rule.ruleId});">#${rule.priority}</td>
	    		</tr>
	          	</c:forEach> 
          	</tbody>
          </table>
		</div>
		<div id="ruleDetailsPage">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/ruleDetails.jsp"%>
		</div>
	</div>
	</body>
</html>