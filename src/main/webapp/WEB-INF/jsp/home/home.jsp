<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../jsp/global/v1/header.jsp" %>
	    
	    <link href="${baseUrl}/css/home/home.css" rel="stylesheet" type="text/css" />
		<link href="${commonStaticUrl}/css/jquery.tooltip.css" rel="stylesheet" type="text/css"/>
	</head>
	<body style="overflow-y:hidden;">
		<c:set var = "flag" value = "Y"></c:set>
		<div id="homePage"></div> <!-- div for identifying the page while unblocking the screen -->
		<div id="mainContent" class="borderTop">		
			<c:if test="${empty tabs}">	
				<div><span style="float: center;color: #CC0000">Your Role doesn't have any active tabs and dashboards assigned. <br/> 
									Please Contact Support @ ${supportNum} for further assistance.
									</span></div>
			</c:if>			
			<div>
			<c:set var="tabKey"/>	
			<c:if test="${not empty tabs}">			
			<div> <h1 class="pageTitle" style="color: red;text-align:center;">Action Items</h1> </div>
				<ul id="tab-display">
					<c:forEach var="tab" items="${tabs}">
						<c:set var="isValidTab" value="N"/>	
						<c:choose>
							<c:when test="${tab.tabKey eq 'TAB_OF'}">	
								<tl:isAuthorized tabName="Order Fulfillment" secFunction="ORDER_FULFILLMENT_TAB">
                                  <tl:penskeOnly>
									<c:set var="isValidTab" value="Y"/>	
                                  </tl:penskeOnly>
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
							<c:otherwise>
								<c:set var="isValidTab" value="Y"/>	
							</c:otherwise>
						</c:choose>
						<c:if test="${isValidTab eq 'Y'}">
						<li class="tab floatLeft handCursor" id="${tab.tabKey}">
							<div class="tab-name-display">
								<span class="tab-name">${tab.tabName}</span>
							</div>
							<div class="tab-image-display" id="link${tab.tabKey}"><img class="tab-image" src="${baseUrl}/${tab.imageUrl}" /></div>
						</li>
						</c:if>
					</c:forEach>
				</ul>
				</c:if>			
				<div id="statusError" style="display:none;padding-bottom:1%;text-align:center;">
					<span class="errorMsg">
						<spring:message code="alert.failure" /></span>
				</div>
				<div id="timeoutMessage" style="display:none;text-align:center;padding-bottom:1%;">
			        <span class="errorMsg">
			            <spring:message code="alert.ajax.timeout" />
			       </span>
		        </div>
                
				<div id ="alertTable"  <c:if test="${hideTable}">style="display:none"</c:if> >
					<div class="tab-header" id="${TabKey}">
					<c:forEach var="alertHeader" items="${alertHeaders}">
						<c:set var="alerts" value="${alertsByHeaderId.get(alertHeader.headerId)}" />
						<c:if test="${!empty alerts}">
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
								<c:forEach var="alertView" items="${alerts}">
									<c:set var="alert" value="${alertView.alert}" />
									<c:set var="alertCount" value="${alertView.alertCount}" />
									<c:set var="alertLink" value="${alertView.alertLink}" />
									<c:choose>
											<c:when test="${alert.alertType.alertKey eq 'ALRT_PROD_DATA_CONFLICT'}">		
												<c:set var="isValidAlert" value="N"/>						
												<tl:isAuthorized tabName="Production" secFunction="DATA_CONFLICT_MENU">
													<c:set var="isValidAlert" value="Y"/>
												</tl:isAuthorized>
											</c:when>
											<c:when test="${alert.alertType.alertKey eq 'ALRT_ALL_MISSING_INFO'}">
												<c:set var="isValidAlert" value="N"/>						
												<tl:isAuthorized tabName="Production" secFunction="PROVIDE_MISSING_INFORMATION">
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
											<c:when test="${alert.actionable and not empty alertLink and alertCount != 0}">
												<c:choose>
													<c:when test="${alert.alertType.alertKey eq 'ALRT_PROD_DATA_CONFLICT'}">
														<tl:isAuthorized tabName="Production" secFunction="DATA_CONFLICT_MENU">
														<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTab('dataConflict');return false">${alertCount}</a>
														</tl:isAuthorized>
													</c:when>
													<c:when test="${(alert.alertType.alertKey eq 'ALRT_OF_VEND_ANLYST_ASSG_REQ') || (alert.alertType.alertKey eq 'ALRT_OF_NEW_VEND_SETUP_REQ') || (alert.alertType.alertKey eq 'ALRT_OF_VEND_USER_SETUP_REQ')}">
														<a id="CountId-${TabKey}-${alert.alertType.alertKey }" onClick="redirectToTemplate('${TabKey}','${alert.alertType.alertKey}');return false">${alertCount}</a>
													</c:when>
													<c:otherwise>
														<a id="CountId-${TabKey}-${alert.templateKey }" onClick="redirectToTemplate('${TabKey}','${alert.templateKey}');return false">${alertCount}</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>0</c:otherwise>
										</c:choose>
											</td>
											<td>
										</td>
									</tr>
									</c:if>
									<!-- hiddenTemplateIds -->
								<input class=templateKeys type="hidden" value="${alert.templateKey }">
								</c:forEach>
								</tbody>
							</table>
							
						</div>
						</c:if>
					</c:forEach>
					</div>
					<div id = "flagMessage" class="error floatLeft" style ="display: none; text-align:center; padding-top:1%;">
						<img src="${commonStaticUrl}/images/warning.png">
						<span>Indicates items are out of compliance</span>
					</div> 
				</div>
				<input type="hidden" value="${tabKey}" id="hiddenTabId"/>
				</div>			
		</div> 
		
		<%@ include file="../../jsp/global/v1/footer.jsp" %>
		<script src="${commonStaticUrl}/js/jquery.tooltip.js" type="text/javascript"></script>
		<script src="${baseUrl}/js/global/v1/jquery/jquery.blockUI.js" type="text/javascript"></script>
		<script src="${baseUrl}/js/home/home.js" type="text/javascript"></script>
  </body>
</html>