<!DOCTYPE html>

<html>
	<head> 
	    <title>SMC Loadsheet Rule</title>
	    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	    <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
	    <c:set var="context" value="${pageContext.request.contextPath}" scope="page" />
	</head>
	<body>
	<div id="rulePopupDiv">
		<div class="ruleNameList">
		<input type="hidden" id="ruleCount" value="${fn:length(rulesList)}"/>
		  <table id="rulesTable">
			<thead>
			   <div><span class="existingRulesHeading"><a onClick="addNewRule()" >Existing Rules</a></span>
			   <img src="${commonStaticUrl}/images/add.png" onClick="addNewRule()" id="addNewRule" class="centerImage handCursor adderAlign rightMargin" /></div>
			</thead>
			<tbody>
				<c:forEach items="${rulesList}" var="ruleList" varStatus="cmpIndex">
				
				<tr class="currentRow <c:if test='${cmpIndex.index eq 0}'>highlightRule</c:if>" id="rule-${ruleList.ruleId}" >
					<td><img src="${commonStaticUrl}/images/delete.png" id="deleteRule" ruleId="${ruleList.ruleId}" onclick="deleteRule(${ruleList.ruleId})"class="imageAlign handCursor rightMargin deleteRule"/></td>
  					<td class="pointer rules" onClick="getRuleDetails(${ruleList.ruleId});" ruleId="${ruleList.ruleId}">${ruleList.ruleName}</td>
	   				<td class="seq priority" onClick="getRuleDetails(${ruleList.ruleId});">#${cmpIndex.index+1}</td>
	    		</tr>
	          	</c:forEach> 
          	</tbody>
          </table>
		</div>
		<div id="ruleDetailsPage">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/ruleDetails.jsp"%>
		</div>
	</div>
		 <input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
	</body>
</html>