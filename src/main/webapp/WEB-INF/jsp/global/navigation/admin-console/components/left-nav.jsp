<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY_ASSOCIATION">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/components/category-association" id="left-nav-category-association" >Category Association</a></div>
	</tl:isAuthorized> 
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY">
		<div><a href="${baseAppUrl}/admin-console/components/category-management" id="left-nav-category-management" >Category Management</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_COMPONENTS">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/components/component-management" id="left-nav-component-management" >Component Management</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TEMPLATE"> 
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/components/template" id="left-nav-template" >Template Management</a></div>
 	</tl:isAuthorized>
</div> 