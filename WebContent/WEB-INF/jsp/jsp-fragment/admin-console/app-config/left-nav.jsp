<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>

	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_SUBJECTS">
	 <div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/subject-management.htm" id="left-nav-subject-management" onclick="javascript:loadProcessImage();">Subject Management</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_MANAGEMENT">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/loadsheet-management.htm" id="left-nav-loadsheet-management" onclick="javascript:loadProcessImage();">Loadsheet Management</a></div>
	</tl:isAuthorized>
	
	<!-- This link will be removed once load sheet mangement page is ready -->
	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_RULES">
		<div ><a href="${pageContext.request.contextPath}/admin-console/app-config/loadsheet-rule.htm" id="left-nav-loadsheet-rules" onclick="javascript:loadProcessImage();">Loadsheet Rules</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_SEQUENCES">
		<div ><a href="${pageContext.request.contextPath}/admin-console/app-config/loadsheet-sequence.htm" id="left-nav-loadsheet-sequence" onclick="javascript:loadProcessImage();">Loadsheet Sequences</a></div>
	</tl:isAuthorized>	
	
	<tl:isAuthorized tabName="Admin Console" secFunction="DYNAMIC_RULES_MANAGEMENT">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/dynamic-rules.htm" id="left-nav-dynamic-rules" onclick="javascript:loadProcessImage();">Dynamic Rules</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="SEARCH_TEMPLATES">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/search-template-management.htm" id="left-nav-search-templates" onclick="javascript:loadProcessImage();">Search Templates</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="ALERT_MANAGEMENT">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/alerts.htm" id="left-nav-alerts" onclick="javascript:loadProcessImage();">Alerts</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="GLOBAL_EXCEPTIONS_MANAGEMENT">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/global-exceptions.htm" id="left-nav-global-exceptions" onclick="javascript:loadProcessImage();">Global Exceptions</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_DELAY_REASONS">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/delay-reason-codes.htm" id="left-nav-reason-codes" onclick="javascript:loadProcessImage();">Delay Reason Codes</a></div>
	</tl:isAuthorized>

	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TC">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/terms-and-conditions.htm" id="left-nav-terms-and-conditions" onclick="javascript:loadProcessImage();">T &#38; C Management</a></div>
	</tl:isAuthorized> 
	<tl:isAuthorized tabName="Admin Console" secFunction="UPLOAD_EXCEL">
		<div class="leftNavGroup"><a href="${pageContext.request.contextPath}/admin-console/app-config/excelUploads.htm" id="left-nav-excel-uploads" onclick="javascript:loadProcessImage();">Excel Uploads</a></div>
	</tl:isAuthorized>	
	
</div>