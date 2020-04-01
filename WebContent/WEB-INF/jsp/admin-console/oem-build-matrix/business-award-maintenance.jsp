<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
<head>
<title>OEM Build Matrix</title>
<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>

<script src="${baseUrl}/js/admin-console/oem-build-matrix/business-award-maintenance.js" type="text/javascript"></script>
<script src="${baseUrl}/js/v2/dataTables.rowGroup_1.1.1.min.js" type="text/javascript"></script> 
<script src="${baseUrl}/jQuery/jquery.multiselect.js" type="text/javascript"></script>
<script src="${baseUrl}/jQuery/jquery.multiselect.filter.js" type="text/javascript"></script>
<script src="${baseUrl}/js/plugins/autoNumeric.min-1.9.15.js" type="text/javascript"></script>

<link href="${baseUrl}/css/jquery.multiselect.filter.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/admin-console/oem-build-matrix/business-award-maintenance.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/home/home.css" rel="stylesheet" type="text/css" />
</head>

<body style="overflow-y:visible;">
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<span class="floatRight addRow push-right"> </span>
			<div id="PopupError" style="display:none">
				<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
			</div>
			<div id="bottom-view">
				<table id="maint-table-view">
				<tbody>
					<tr id="maint-table-view">
        				<td id="table-data1"><h1>OEM Mix Maintenance</h1></td>
       					<td id="table-data2">
       						<div id="save-div">
								<a href="#" id="save-oem" class="buttonPrimary round-corner-btn-cls">Save</a>
           					 </div>
       					 </td>
        			</tr>
				</tbody>
				</table>
			</div>
			<table id="business-award-maint-table" class="plant-view">
				<thead>
					<tr>
						<th>PO CATEGORY</th>
						<th class="leftAlign" id="table-head1"><input id="mass_select_all" type="checkbox"></th>
						<th class="leftAlign" id="table-head2">OEM Name</th>
						<th class="leftAlign">% Value</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${oemMaintenanceList}" var="oem">
						<tr class="user-row">
							<td>${oem.poCategory}</td>
							<td class="leftAlign">
							<input type="checkbox" id="select-oem" onclick="saveCheckedBoxes(this.id)" > 
							<a class="buttonSecondary round-corner-btn-cls" id="delete-display">DELETE</a>
							</td>
							<td class="leftAlign">${oem.oemName}</td>
							<td class="leftAlign">
								<input class="oem-value" type="text" value="${oem.value}" group-name="${oem.poCategory}" oem-id="${oem.oemId}" oninput="calculateAverage(${oem.oemId},'${oem.poCategory}')">%
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="add-oem-popup" class="modal"></div>
		</div>
	</div>
</body>
</html>