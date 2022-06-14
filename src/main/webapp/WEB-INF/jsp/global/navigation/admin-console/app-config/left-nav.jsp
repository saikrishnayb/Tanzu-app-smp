<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>

	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/loadsheet-management" id="left-nav-loadsheet-management">Loadsheet Management</a></div>
	</tl:isAuthorized>
	
	<!-- This link will be removed once load sheet mangement page is ready -->
	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_RULES">
		<div ><a href="${baseAppUrl}/admin-console/app-config/loadsheet-rule" id="left-nav-loadsheet-rules">Loadsheet Rules</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_SEQUENCES">
		<div ><a href="${baseAppUrl}/admin-console/app-config/loadsheet-sequence" id="left-nav-loadsheet-sequence">Loadsheet Sequences</a></div>
	</tl:isAuthorized>	
	
	<tl:isAuthorized tabName="Admin Console" secFunction="DYNAMIC_RULES_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/dynamic-rules" id="left-nav-dynamic-rules">Dynamic Rules</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="SEARCH_TEMPLATES">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/search-template-management" id="left-nav-search-templates">Search Templates</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="ALERT_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/alerts" id="left-nav-alerts">Alerts</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="GLOBAL_EXCEPTIONS_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/global-exceptions" id="left-nav-global-exceptions">Global Exceptions</a></div>
	</tl:isAuthorized>

	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TC">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/terms-and-conditions" id="left-nav-terms-and-conditions">T &#38; C Management</a></div>
	</tl:isAuthorized> 
	<tl:isAuthorized tabName="Admin Console" secFunction="UPLOAD_EXCEL">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/excelUploads" id="left-nav-excel-uploads">Excel Uploads</a></div>
	</tl:isAuthorized>	

	<div class="leftNavGroup">Cost Sheet Management</div>
		<tl:isAuthorized tabName="Admin Console" secFunction="COST_SHEET_ADJUSTMENT_OPTIONS">
			<div><a href="${baseAppUrl}/admin-console/app-config/cost-sheet-adjustment-options" id="left-nav-cost-sheet-adjustment-options">Cost Sheet Adjustment Options</a></div>
		</tl:isAuthorized>
		<tl:isAuthorized tabName="Admin Console" secFunction="COST_SHEET_TOLERANCES">
			<div><a href="${baseAppUrl}/admin-console/app-config/cost-sheet-tolerances" id="left-nav-cost-sheet-tolerances">Cost Sheet Tolerances</a></div>
		</tl:isAuthorized>
</div>