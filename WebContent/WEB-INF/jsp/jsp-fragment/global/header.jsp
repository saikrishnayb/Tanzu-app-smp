<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<%-- TODO: Look into removing this script from here --%>
<script src="${baseUrl}/js/overlay/globaloverlay.js" type="text/javascript"></script>
<nav>
	
	<ul>
		<li style ="display: table-cell;">
			<ul>
			
				<c:set var="hasSecurityTab" value="0"></c:set>
				<tl:isAuthorized tabName="Admin Console" secFunction="PENSKE_USERS">
					<c:set var="hasSecurityTab" value="1"></c:set>
				</tl:isAuthorized>
				<tl:isAuthorized tabName="Admin Console" secFunction="VENDOR_USERS">
					<c:set var="hasSecurityTab" value="1"></c:set>
				</tl:isAuthorized>				
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_ROLES">
					<c:set var="hasSecurityTab" value="1"></c:set>
				</tl:isAuthorized>		
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_VENDORS">
					<c:set var="hasSecurityTab" value="1"></c:set>
				</tl:isAuthorized>		
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_ORG">
					<c:set var="hasSecurityTab" value="1"></c:set>
				</tl:isAuthorized>		
				<c:if test="${hasSecurityTab == '1'}">
					<li><a id="tab-security" href="${baseAppUrl}/admin-console/security/navigate-security.htm" onclick="javascript:loadProcessImage();">Security</a></li>
				</c:if>				
				
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
				<!-- Include Components only if user has access to at least one left nav inside Components -->
				<c:if test="${hasComponents == '1'}">
                  <tl:penskeOnly>
					<li><a id="tab-components"  href="${baseAppUrl}/admin-console/components/navigate-components.htm" onclick="javascript:loadProcessImage();">Components</a></li>
                  </tl:penskeOnly>
				</c:if>
				
				<!-- Check if user has access to at least one left nav inside App Config -->
				<c:set var="hasAppConfig" value="0"></c:set>
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
				<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TC">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized> 
				<tl:isAuthorized tabName="Admin Console" secFunction="UPLOAD_EXCEL">
					<c:set var="hasAppConfig" value="1"></c:set>
				</tl:isAuthorized> 
				<!-- Include app config if user has access to at least one left nav inside app config -->
				<c:if test="${hasAppConfig == '1'}">
                  <tl:penskeOnly>
					<li><a id="tab-app-config" href="${baseAppUrl}/admin-console/app-config/navigate-app-config.htm" onclick="javascript:loadProcessImage();">App Config</a></li>
                  </tl:penskeOnly>
				</c:if>
			<%-- 	<tl:isAuthorized secFunction="OEM_BUILD_MATRIX" tabName="Admin Console"> --%>
					<li><a id="tab-oem-build-matrix" href="${baseAppUrl}/admin-console/oem-build-matrix/navigate-oem-build-matrix.htm" onclick="javascript:loadProcessImage();">Build Matrix</a></li>
				<%-- </tl:isAuthorized> --%>
				
			</ul>
		</li>
	</ul>
</nav>
