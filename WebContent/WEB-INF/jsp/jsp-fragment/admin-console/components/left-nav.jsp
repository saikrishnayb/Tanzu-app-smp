<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
	<!-- <div><a href="${pageContext.request.contextPath}/admin-console/components/visibility-by-category.htm" id="left-nav-visibility-by-category">Visibility By Category</a></div>
	 
	<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/components/templates.htm" id="left-nav-templates">Templates</a></div>
	-->
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY_ASSOCIATION">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/components/category-association.htm" id="left-nav-category-association" onclick="javascript:loadProcessImage();">Category Association</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_CATEGORY">
		<div><a href="${pageContext.request.contextPath}/admin-console/components/category-management.htm" id="left-nav-category-management" onclick="javascript:loadProcessImage();">Category Management</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TEMPLATE"> 
	<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/components/template.htm" id="left-nav-template" onclick="javascript:loadProcessImage();">Template Management</a></div> 
 </tl:isAuthorized>
</div>