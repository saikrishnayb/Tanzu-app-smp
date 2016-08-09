<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
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
		<input class = "hidden" value = "${commonStaticUrl}" id = "commonStaticUrl"/>
		<input type="hidden" name="contextRoot" id="contextRoot" value="${context }"/>
		<c:set var = "flag" value = "Y"></c:set>
		<div id="homePage"></div> <!-- div for identifying the page while unblocking the screen -->
		<div id="mainContent" class="borderTop">
		<c:set var="tabKey"/>
		<div> <h1 class="pageTitle" style="color: red;text-align:center;">Action Items</h1> </div>
			<ul id="tab-display">
				<c:forEach var="tab" items="${tabs}">
					<c:set var="isValidTab" value="N"/>	
					<c:choose>
						<c:when test="${tab.tabKey eq 'TAB_OF'}">	
							<tl:isAuthorized tabName="Order Fulfillment" secFunction="ORDER_FULFILLMENT_TAB">
								<c:set var="isValidTab" value="Y"/>	
							</tl:isAuthorized>	
						</c:when>
						<c:when test="${tab.tabKey eq 'TAB_OC'}">	
							<tl:isAuthorized tabName="Order Confirmation" secFunction="ORDER_CONFIRMATION_TAB">
								<c:set var="isValidTab" value="Y"/>	
							</tl:isAuthorized>		
						</c:when>
						<c:when test="${tab.tabKey eq 'TAB_PROD'}">	
							<tl:isAuthorized tabName="Production" secFunction="PRODUCTION_TAB">
								<c:set var="isValidTab" value="Y"/>	
							</tl:isAuthorized>		
						</c:when>
						<c:when test="${tab.tabKey eq 'TAB_COMM'}">		
							<tl:isAuthorized tabName="Communications" secFunction="COMMUNICATIONS_TAB">
								<c:set var="isValidTab" value="Y"/>	
							</tl:isAuthorized>	
						</c:when>
						<c:otherwise>
							<c:set var="isValidTab" value="Y"/>	
						</c:otherwise>
					</c:choose>
					<c:if test="${isValidTab eq 'Y'}">
					<li class="tab floatLeft handCursor" id="${tab.tabKey}">
						<div class="tab-name-display">
							<span class="tab-name">${tab.tabName}</span>
						</div>
						<div class="tab-image-display" id="link${tab.tabKey}"><img class="tab-image" src="${context}/${tab.imageUrl}" /></div>
					</li>
					</c:if>
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
					<c:set var="tabKey" value="${TabKey}"/>
					<div class="header width-full">
						<table class="width-full">
							<thead>
								<tr>
									<th>
										<span>${alertHeader.headerName}</span>
										<c:if test="${not empty alertHeader.helpText}">
										<img rel="tooltip" src="${commonStaticUrl}/images/information.png" class="centerImage help-text-image handCursor" title="${alertHeader.helpText}"/>
										</c:if>
									</th>

									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="alert" items="${alertHeader.alerts}">
								<c:choose>
										<c:when test="${alert.alertKey eq 'ALRT_PROD_DATA_CONFLICT'}">		
											<c:set var="isValidAlert" value="N"/>						
											<tl:isAuthorized tabName="Production" secFunction="DATA_CONFLICT_MENU">
												<c:set var="isValidAlert" value="Y"/>
											</tl:isAuthorized>
										</c:when>
										<c:when test="${alert.alertKey eq 'ALRT_PROD_DELAY_COMM_REQ'}">		
											<c:set var="isValidAlert" value="N"/>						
											<tl:isAuthorized tabName="Production" secFunction="UPDATE_DATES">
												<c:set var="isValidAlert" value="Y"/>
											</tl:isAuthorized>
										</c:when>
										<c:otherwise>
											<c:set var="isValidAlert" value="Y"/>
										</c:otherwise>
								</c:choose>
								<c:if test="${isValidAlert eq 'Y'}">
								<tr>
									<td>
										<span>${alert.alertName}</span>
										<c:if test="${not empty alert.helpText}">
										<img rel="tooltip" src="${commonStaticUrl}/images/information.png" class="centerImage help-text-image handCursor" title="${alert.helpText}"/>
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
													<tl:isAuthorized tabName="Production" secFunction="DATA_CONFLICT_MENU">
													<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTab('dataConflict');return false">${alert.count}</a>
													</tl:isAuthorized>
												</c:when>
												<c:when test="${alert.alertKey eq 'ALRT_PROD_DELAY_COMM_REQ'}">
													<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTab('dateValidation');return false">${alert.count}</a>
												</c:when>
												<c:when test="${(alert.alertKey eq 'ALRT_OF_VEND_ANLYST_ASSG_REQ') || (alert.alertKey eq 'ALRT_OF_NEW_VEND_SETUP_REQ') || (alert.alertKey eq 'ALRT_OF_VEND_USER_SETUP_REQ')}">
													<a id="CountId-${TabKey}-${alert.alertKey }" onClick="redirectToTemplate('${TabKey}','${alert.alertKey}');return false">${alert.count}</a>
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
								</c:if>
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
			<input type="hidden" value="${tabKey}" id="hiddenTabId"/>
		</div> 
		
		
		
	
	</body>
	

</html>