<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY_ASSOCIATION">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/components/category-association.htm" id="left-nav-category-association" onclick="javascript:loadProcessImage();">Category Association</a></div>
	</tl:isAuthorized> 
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY">
		<div><a href="${pageContext.request.contextPath}/admin-console/components/category-management.htm" id="left-nav-category-management" onclick="javascript:loadProcessImage();">Category Management</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_COMPONENTS">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/components/component-management.htm" id="left-nav-component-management" onclick="javascript:loadProcessImage();">Component Management</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TEMPLATE"> 
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/components/template.htm" id="left-nav-template" onclick="javascript:loadProcessImage();">Template Management</a></div>
 	</tl:isAuthorized>

 	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_COMPONENT_OVERRIDE"> 
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/components/component-Visibility-Override.htm" id="left-nav-component-visibility-overrides" onclick="javascript:loadProcessImage();">Component&nbsp;Visibility Override</a></div>
	</tl:isAuthorized> 
</div> 