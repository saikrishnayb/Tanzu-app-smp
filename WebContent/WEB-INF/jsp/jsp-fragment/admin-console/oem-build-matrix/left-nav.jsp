<div class="leftNav">
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/build-history.htm" id="left-nav-build-history" onclick="javascript:loadProcessImage();">Build History</a></div>
		<tl:penskeOnly>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/attribute-maintenance.htm" id="left-nav-attribute-maintenance" classonclick="javascript:loadProcessImage();">Attribute Maintenance</a></div>
		</tl:penskeOnly>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/business-award-maint.htm" id="left-nav-business-award-maint" onclick="javascript:loadProcessImage();">OEM Mix Maintenance</a></div>
		<div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/maintenance-summary.htm" id="left-nav-maintenance-summary" onclick="javascript:loadProcessImage();">Plant Maintenance</a></div>
		<div class="leftNavGroup"><a id="left-nav-prod-slot-maintenance">Production Slot Maintenance</a></div>
		<!-- <div class="leftNavGroup"><a id="left-nav-slot-utilization" >Slot Utilization</a></div> -->
		<%-- <div class="leftNavGroup"><a href="${baseAppUrl}/admin-console/oem-build-matrix/bodyplant-capabilities.htm" id="left-nav-bodyplant-capabilities" onclick="javascript:loadProcessImage();">Body Plant Capabilities</a></div> --%>
</div>