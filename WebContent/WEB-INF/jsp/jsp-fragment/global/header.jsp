<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<c:set var="context" value="${pageContext.request.contextPath}"  scope="page" />
<script src="${context}/js/overlay/globaloverlay.js" type="text/javascript"></script>
<nav>
	
	<ul style="margin-top:4px">
		<li style ="display: table-cell;">
			<ul>
				<li><a id="tab-security" href="${pageContext.request.contextPath}/admin-console/security/navigate-security.htm" onclick="javascript:loadProcessImage();">Security</a></li>
				
				<!--  Check if user has access to at least one lef nav inside Components -->
				<c:set var="hasComponents" value="0"></c:set>
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY_ASSOCIATION">
					<c:set var="hasComponents" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY">
					<c:set var="hasComponents" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TEMPLATE"> 
					<c:set var="hasComponents" value="1"></c:set> 
			 	</tl:isAuthorized>
			 	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_COMPONENT_OVERRIDE"> 
					<c:set var="hasComponents" value="1"></c:set>
				</tl:isAuthorized> 
				<!-- Include Components only if user has access to at least one left nav inside Components -->
				<c:if test="${hasComponents == '1'}">
                  <tl:penskeOnly>
					<li><a id="tab-components"  href="${pageContext.request.contextPath}/admin-console/components/navigate-components.htm" onclick="javascript:loadProcessImage();">Components</a></li>
                  </tl:penskeOnly>
				</c:if>
				
				<!-- Check if user has access to at least one left nav inside App Config -->
				<c:set var="hasAppConfig" value="0"></c:set>
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_SUBJECTS">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="DYNAMIC_RULES_MANAGEMENT">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="SEARCH_TEMPLATES">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="ALERT_MANAGEMENT">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="GLOBAL_EXCEPTIONS_MANAGEMENT">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="UNIT_EXCEPTIONS_MANAGEMENT">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_DELAY">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="DELAY_ASSOCIATION">
					<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_DELAY_TYPE">
						<c:set var="hasAppConfig" value="1"></c:set>
					</tl:isAuthorized>
					<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_DELAY_REASONS">
						<c:set var="hasAppConfig" value="1"></c:set>
					</tl:isAuthorized>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TC">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized> 
				<tl:isAuthorized tabName="Admin Console" secFunction="UPLOAD_EXCEL">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized> 
				<!-- Include app config if user has access to at least one left nav inside app config -->
				<c:if test="${hasAppConfig == '1'}">
                  <tl:penskeOnly>
					<li><a id="tab-app-config" href="${pageContext.request.contextPath}/admin-console/app-config/navigate-app-config.htm" onclick="javascript:loadProcessImage();">App Config</a></li>
                  </tl:penskeOnly>
				</c:if>
			</ul>
		</li>
	</ul>
</nav>
