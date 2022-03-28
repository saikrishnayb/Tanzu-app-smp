<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tl" uri="/WEB-INF/tld/taglib.tld"%>

<div class="leftNav">
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_USERS">
		<c:set var="allowPenskeUserManagement" value="${true}" />
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_VENDOR_USERS">
		<c:set var="allowVendorUserManagement" value="${true}" />
	</tl:isAuthorized>
	
	<c:if test="${allowPenskeUserManagement or allowVendorUserManagement}">
		<div class="leftNavGroup" style="color: #666666; font-weight: normal;">User Management</div>
		<c:if test="${allowPenskeUserManagement}">
			<tl:penskeOnly>
				<div>
					<a href="${baseAppUrl}/admin-console/security/users" id="left-nav-users">Penske Users</a>
				</div>
			</tl:penskeOnly>
		</c:if>
		
		<c:if test="${allowVendorUserManagement}">
			<div>
				<a href="${baseAppUrl}/admin-console/security/vendor-users" id="left-nav-vendor-users-v2">Vendor Users</a>
			</div>
		</c:if>
		
	</c:if>
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_ROLES">
		<div class="leftNavGroup">
			<a href="${baseAppUrl}/admin-console/security/roles" id="left-nav-roles">Roles</a>
		</div>
	</tl:isAuthorized>

	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_VENDORS">
		<tl:penskeOnly>
			<div class="leftNavGroup">
				<a href="${baseAppUrl}/admin-console/security/vendors" id="left-nav-vendors">Vendors</a>
			</div>
		</tl:penskeOnly>
	</tl:isAuthorized>

	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_ORG">
		<div class="leftNavGroup">
			<a href="${baseAppUrl}/admin-console/security/org" id="left-nav-org">Org Management</a>
		</div>
	</tl:isAuthorized>
</div>