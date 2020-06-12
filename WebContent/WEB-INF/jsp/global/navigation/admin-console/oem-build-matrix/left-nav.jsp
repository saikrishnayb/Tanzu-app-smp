<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/build-history.htm" id="left-nav-build-history">Build History</a></div>
		<tl:penskeOnly>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/attribute-maintenance.htm" id="left-nav-attribute-maintenance">Attribute Maintenance</a></div>
		</tl:penskeOnly>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/business-award-maint.htm" id="left-nav-business-award-maint">OEM Mix Maintenance</a></div>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/maintenance-summary.htm" id="left-nav-maintenance-summary">Plant Maintenance</a></div>
		<tl:penskeOnly>
		<div class="leftNavGroup">Production Slot</div>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/prod-slot-maintenance.htm?slotType=0&year=0" id="left-nav-prod-slot-maintenance" >Maintenance</a></div>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/prod-slot-region-maintenance.htm?slotType=0&year=0&region=0" id="left-nav-prod-slot-region-maintenance" >Region Maintenance</a></div>
		<div><a href="${baseAppUrl}/admin-console/oem-build-matrix/prod-slot-utilization.htm?slotType=0&year=0&region=0" id="left-nav-prod-slot-utilization" >Utilization</a></div>
		</tl:penskeOnly>
		<!-- <div class="leftNavGroup"><a id="left-nav-slot-utilization" >Slot Utilization</a></div> -->
		<%-- <div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/bodyplant-capabilities.htm" id="left-nav-bodyplant-capabilities">Body Plant Capabilities</a></div> --%>
</div>