<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<div class="leftNav">
<tl:isAuthorized tabName="Admin Console" secFunction="USERS_MANAGEMENT">
		<div class="leftNavGroup" style="color: #666666;font-weight: normal;">User Management</div>
		<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_USERS">
			<div><a href="${pageContext.request.contextPath}/admin-console/security/users.htm" id="left-nav-users" onclick="javascript:loadProcessImage();">Penske Users</a></div>
		</tl:isAuthorized>
		<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_VENDOR_USERS">
			<div><a href="${pageContext.request.contextPath}/admin-console/security/vendorUsers.htm" id="left-nav-vendor-users" onclick="javascript:loadProcessImage();">Vendor Users</a></div>
		</tl:isAuthorized>
</tl:isAuthorized>
<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_ROLES">
 	<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/security/roles.htm" id="left-nav-roles" onclick="javascript:loadProcessImage();">Roles</a></div>
 </tl:isAuthorized>
	
<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_VENDORS">
<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/security/vendors.htm" id="left-nav-vendors" onclick="javascript:loadProcessImage();">Vendors</a></div> 
 </tl:isAuthorized>

 <tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_ORG"> 
	<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/security/org.htm" id="left-nav-org" onclick="javascript:loadProcessImage();">Org Management</a></div> 
 </tl:isAuthorized>

</div>