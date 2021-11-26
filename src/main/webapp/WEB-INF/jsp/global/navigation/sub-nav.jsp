<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>

<!-- ***** Overlays ***** -->
<%-- NOTE: we need this to be first on the page so that, if we have a large page with lots of content, the loading overlay shows while the page is loading --%>
<%-- This loading overlay isn't used on the v1 page template, but that's ok, because its CSS doesn't have the page-loading class, either. --%>
<div id="loading-overlay" class="page-loading"></div>

<nav>
	<ul>
		<li style ="display: table-cell;" class="current">
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
					<li><a id="tab-security" href="${baseAppUrl}/admin-console/security/navigate-security.htm">Security</a></li>
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
					<li><a id="tab-components"  href="${baseAppUrl}/admin-console/components/navigate-components.htm">Components</a></li>
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
					<li><a id="tab-app-config" href="${baseAppUrl}/admin-console/app-config/navigate-app-config.htm">App Config</a></li>
                  </tl:penskeOnly>
				</c:if>
				<c:set var="hasBuildMatrix" value="0"></c:set>
				<tl:isAuthorized secFunction="OEM_BUILD_MATRIX" tabName="Admin Console">
					<c:set var="hasBuildMatrix" value="1"></c:set>
				</tl:isAuthorized>
				<c:if test="${hasBuildMatrix == '1'}">
                  <tl:penskeOnly>
					<li><a id="tab-oem-build-matrix" href="${baseAppUrl}/admin-console/oem-build-matrix/navigate-oem-build-matrix.htm">Build Matrix</a></li>
				</tl:penskeOnly>
				</c:if>
			</ul>
		</li>
	</ul>
</nav>
