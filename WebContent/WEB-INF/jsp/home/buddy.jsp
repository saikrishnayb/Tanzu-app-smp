<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>



<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">	
<style type="text/css">

    div#userSelection ul { margin:0 0 0 20px; padding:0; list-style-type:none; }
    div#userSelection li>div { display:inline; margin-right:5px; cursor:pointer; }

  </style>				
		
<script src="<c:out value='${baseUrl}'/>/js/buddy.js" type="text/javascript"></script>		

<body>
<div id="buddyPopup">
<form id="buddyForm"  method="get">
	<div id="headerArea">
	
		<spring:message code="buddy.description" />
	</div>	
	<div id="userSelection"  style="overflow-y: auto;height: 251px;	width: 100%;padding-top:3%;" >
	<ul style="list-style-type: none;">
		<li>
			<Label><input type="checkbox" id="allCheckBox" value ="allCheckBox" class="allCheckboxClass" onclick="selectAll('all');">All</Label>
			<a id="clearBuddyList" style="float:right;padding-right:10px">Clear All</a>
		</li>
		<li><div></div><Label><input type="checkbox" id="planCheckBox" value="planCheckBox" class="planCheckBoxClass" onclick="selectAll('plan');">All PLANNING</Label>
			<ul style="list-style-type: none;">
			<div id="plannerDiv">	
				<c:forEach items="${planningUsersList}" var="user">
				<c:choose>
					<c:when test="${user.sso == loggedInUserSso}">
						 <li>
							<Label>
								<input type="checkbox" checked="checked" class="plannerCheckBoxValues" disabled="disabled" value="${user.sso}/${user.userDept} " >${user.firstName} ${user.lastName}
							</Label>	
						</li>
					</c:when>
					<c:otherwise>
						 <li>
						 	<Label>
								<input type="checkbox" id="${user.sso}" class="plannerCheckBox plannerCheckBoxValues" value="${user.sso}/${user.userDept} " onclick="unSelectHeader('plan');">${user.firstName} ${user.lastName}
							</Label>
						</li>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</div>
			</ul>
		</li>
		<li><div></div><Label><input type="checkbox" id="buyCheckBox" value="buyCheckBox" class="buyCheckBoxClass" onclick="selectAll('buy');">All BUYERS</Label>
			
			<ul style="list-style-type: none;">
			<div id="buyerDiv">	
				<c:forEach items="${purchasingUsersList}" var="user">
				<c:choose>
					<c:when test="${user.sso == loggedInUserSso}">
						<li>
							<Label>
								<input type="checkbox" checked="checked" class="buyerCheckBoxValues" disabled="disabled" value="${user.sso}/${user.userDept}" >${user.firstName} ${user.lastName}
							</Label>
						</li>				
					</c:when>
					<c:otherwise >
						<li>
							<Label>
								<input type="checkbox" id="${user.sso}" class="buyerCheckBox buyerCheckBoxValues" value="${user.sso}/${user.userDept}" onclick="unSelectHeader('buy');">${user.firstName} ${user.lastName}
							</Label>
						</li>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</div>
			</ul>
			
		</li>
	</ul>
	
	</div>
	<input type="hidden" id="existingBuddyList" value="${existingBuddySsoList}"/>
	<input type="hidden" id="newBuddies" value=""/>
	<div id="buttonDiv" style="float:right;top:0;padding-top:8%;">
		<a onclick="closePopup();return false;"><spring:message code="buddy.cancel" /></a>
		<a class="buttonSecondary" onclick="saveBuddyList();return false;" >
			<spring:message code="buddy.save" />
		</a>
	</div>
</form>
</div>
</body>
</html>