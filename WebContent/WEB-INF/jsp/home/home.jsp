<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    <link href="${context}/css/home/home.css" rel="stylesheet" type="text/css" />
		<script src="${commonStaticUrl}/js/jquery.tooltip.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.tooltip.css" rel="stylesheet" type="text/css"/>
		<script src="<c:out value='${context}'/>/jQuery/jquery.blockUI.js" type="text/javascript"></script>
		<script src="${context}/js/home/home.js" type="text/javascript"></script>
	</head>
	<body style="overflow-y:hidden;">
		<%@ include file="../../jsp/jsp-fragment/global/header.jsp" %>
		<input class = "hidden" value = "${commonStaticUrl}" id = "commonStaticUrl"/>
		<input type="hidden" name="contextRoot" id="contextRoot" value="${context }"/>
		<c:set var = "flag" value = "Y"></c:set>
		<div id="homePage"></div> <!-- div for identifying the page while unblocking the screen -->
		<div id="mainContent" class="borderTop">
		<div> <h1 class="pageTitle" style="color: red;text-align:center;">Action Items</h1> </div>
			<ul id="tab-display">
				<c:forEach var="tab" items="${tabs}">
					<li class="tab floatLeft handCursor" id="${tab.tabKey}">
						<div class="tab-name-display">
							<span class="tab-name">${tab.tabName}</span>
						</div>
						<div class="tab-image-display"><img class="tab-image" src="${context}/${tab.imageUrl}" /></div>
					</li>
				</c:forEach>
			</ul>
			
			<div id="statusError" style="display:none;padding-bottom:1%;text-align:center;">
				<span class="errorMsg">
					<spring:message code="alert.failure" /></span>
			</div>
			<div id="timeoutMessage" style="display:none;text-align:center;padding-bottom:1%;">
		        <span class="errorMsg">
		            <spring:message code="alert.ajax.timeout" />
		       </span>
	        </div>
			<div id ="alertTable" >
				<div class="tab-header" id="${TabKey}">
				<c:forEach var="alertHeader" items="${alertHeaders}">
					<div class="header width-full">
						<table class="width-full">
							<thead>
								<tr>
									<th>
										<span>${alertHeader.headerName}</span>
										<c:if test="${not empty alertHeader.helpText}">
										<img rel="tooltip" src="${commonStaticUrl}/images/help.png" class="centerImage help-text-image handCursor" title="${alertHeader.helpText}"/>
										</c:if>
									</th>

									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="alert" items="${alertHeader.alerts}">
								<tr>
									<td>
										<span>${alert.alertName}</span>
										<c:if test="${not empty alert.helpText}">
										<img rel="tooltip" src="${commonStaticUrl}/images/help.png" class="centerImage help-text-image handCursor" title="${alert.helpText}"/>
										</c:if>
									</td>

									<td>
									<c:choose>
										<c:when test="${alert.actionable eq 1 and not empty alert.link and alert.count != 0}">
											<c:choose>
												<c:when test="${alert.alertKey eq 'ALRT_PROD_UP_VEND_DATE_CHG'}">
													<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTab('upStreamVendor');return false">${alert.count}</a>
												</c:when>
												<c:when test="${alert.alertKey eq 'ALRT_PROD_DATA_CONFLICT'}">
													<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTab('dataConflict');return false">${alert.count}</a>
												</c:when>
												<c:when test="${alert.alertKey eq 'ALRT_PROD_DELAY_COMM_REQ'}">
													<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTab('dateValidation');return false">${alert.count}</a>
												</c:when>
												<c:otherwise>
													<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTemplate('${TabKey}','${alert.templateKey}');return false">${alert.count}</a>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>0</c:otherwise>
									</c:choose>
										</td>
										<td>
										<c:if test="${alert.flag == 'Y'}">
											<input type="hidden" class = "flags" value="Y"/>
											<img class="centerImage" src="${commonStaticUrl}/images/warning.png"/>
										</c:if>
									</td>
								</tr>
								<!-- hiddenTemplateIds -->
							<input class=templateKeys type="hidden" value="${alert.templateKey }">
							</c:forEach>
							</tbody>
						</table>
						
					</div>
				</c:forEach>
				</div>
				<div id = "flagMessage" class="error floatLeft" style ="display: none; text-align:center; padding-top:1%;">
					<img src="${commonStaticUrl}/images/warning.png">
					<span>Indicates items are out of compliance</span>
				</div> 
			</div>
		</div> 
		
		
		
	
	</body>
	

</html>