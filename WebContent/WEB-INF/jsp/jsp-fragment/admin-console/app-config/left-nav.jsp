<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>

	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/loadsheet-management.htm" id="left-nav-loadsheet-management" onclick="javascript:loadProcessImage();">Loadsheet Management</a></div>
	</tl:isAuthorized>
	
	<!-- This link will be removed once load sheet mangement page is ready -->
	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_RULES">
		<div ><a href="${baseAppUrl}/admin-console/app-config/loadsheet-rule.htm" id="left-nav-loadsheet-rules" onclick="javascript:loadProcessImage();">Loadsheet Rules</a></div>
	</tl:isAuthorized>
	
	<tl:isAuthorized tabName="Admin Console" secFunction="LOADSHEET_SEQUENCES">
		<div ><a href="${baseAppUrl}/admin-console/app-config/loadsheet-sequence.htm" id="left-nav-loadsheet-sequence" onclick="javascript:loadProcessImage();">Loadsheet Sequences</a></div>
	</tl:isAuthorized>	
	
	<tl:isAuthorized tabName="Admin Console" secFunction="DYNAMIC_RULES_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/dynamic-rules.htm" id="left-nav-dynamic-rules" onclick="javascript:loadProcessImage();">Dynamic Rules</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="SEARCH_TEMPLATES">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/search-template-management.htm" id="left-nav-search-templates" onclick="javascript:loadProcessImage();">Search Templates</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="ALERT_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/alerts.htm" id="left-nav-alerts" onclick="javascript:loadProcessImage();">Alerts</a></div>
	</tl:isAuthorized>
	<tl:isAuthorized tabName="Admin Console" secFunction="GLOBAL_EXCEPTIONS_MANAGEMENT">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/global-exceptions.htm" id="left-nav-global-exceptions" onclick="javascript:loadProcessImage();">Global Exceptions</a></div>
	</tl:isAuthorized>

	<tl:isAuthorized tabName="Admin Console" secFunction="MANAGE_TC">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/terms-and-conditions.htm" id="left-nav-terms-and-conditions" onclick="javascript:loadProcessImage();">T &#38; C Management</a></div>
	</tl:isAuthorized> 
	<tl:isAuthorized tabName="Admin Console" secFunction="UPLOAD_EXCEL">
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/excelUploads.htm" id="left-nav-excel-uploads" onclick="javascript:loadProcessImage();">Excel Uploads</a></div>
	</tl:isAuthorized>	
  
  <tl:isAuthorized tabName="Admin Console" secFunction="SHIP_THRU_LEAD_TIME">
    <tl:penskeOnly>
      <div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/app-config/ship-thru-lead-time" id="left-nav-ship-thru-lead-time" onclick="javascript:loadProcessImage();">Lead Time Management</a></div>
    </tl:penskeOnly>
  </tl:isAuthorized>  
  <!-- OEM BUILD MATRIX links -->
	<tl:isAuthorized secFunction="OEM_BUILD_MATRIX" tabName="Admin Console">
		<div class="leftNavGroup build-matrix-left-nav-lbl">OEM Build Matrix</div>
		<tl:penskeOnly>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/attribute-maintenance.htm" id="left-nav-attribute-maintenance" onclick="javascript:loadProcessImage();">Attribute Maintenance</a></div>
		</tl:penskeOnly>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/business-award-maint.htm" id="left-nav-business-award-maint" onclick="javascript:loadProcessImage();">OEM Mix Maintenance</a></div>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/maintenance-summary.htm" id="left-nav-maintenance-summary" onclick="javascript:loadProcessImage();">Plant Maintenance</a></div>
		<div><a  href="#"  id="left-nav-prod-slot-maintenance">Production Slot Maintenance</a></div>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/build-history.htm" id="left-nav-build-history" onclick="javascript:loadProcessImage();">Build History</a></div>
		<div><a href="#" id="left-nav-slot-utilization" >Slot Utilization</a></div>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/bodyplant-capabilities.htm" id="left-nav-bodyplant-capabilities" onclick="javascript:loadProcessImage();">Body Plant Capabilities</a></div>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/district-proximity.htm" id="left-nav-district-proximity" onclick="javascript:loadProcessImage();">Body Plant District Proximity</a></div>
		
	</tl:isAuthorized>
</div>