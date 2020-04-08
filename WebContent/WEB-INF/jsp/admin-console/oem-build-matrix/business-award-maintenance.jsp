<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
<head>
	<title>OEM Build Matrix</title>
	<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/business-award-maintenance.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix-global.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/global/v2/jquery.multiselect.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/jquery.multiselect.filter.css"rel="stylesheet" type="text/css" />
</head>

<body style="overflow-y:visible;">
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
		<div class="leftNavAdjacentContainer">
			<div id="PopupError" style="display:none">
				<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
			</div>
			<div class="row">
        		<div class="col-xs-12">
          			<h1>OEM Mix Maintenance</h1>
        		</div>
      		</div>
			<div class="row">
        		<div class="col-xs-12 business-award-maint-table-top">
        			<div class='search-div'>
          				<label>Search: </label> <input type="text" id="mix-search"/>
          			</div>
          			<div class="btn-div floatRight">
          				<!-- <a id="delete-mfr" class="buttonSecondary">Delete</a>
          				<a id="add-mfr" class="buttonSecondary">Add</a> -->
          				<a id="save-oem-mix" class="buttonPrimary buttonDisabled">Save</a>
          			</div>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12 business-award-maint-table-container">
					<table id="business-award-maint-table" class="plant-view">
						<thead>
							<tr>
								<th>ATTRIBUTE GROUP</th>
								<!-- <th class="leftAlign" id="table-head1"><input id="mass_select_all" type="checkbox"></th> -->
								<th class="leftAlign" id="table-head2">Attribute Value</th>
								<th class="leftAlign">% Value</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${attributes}" var="attribute">
								<c:forEach items="${attribute.attributeValues}" var="attributeValue">
									<tr class="attribute-row" data-attribute-id="${attribute.attributeId}" data-group-id="${attribute.groupId}" data-attribute-value-id="${attributeValue.attributeValueId}">
										<td class="attribute-name">${attribute.attributeName}</td>
										<!-- <td class="leftAlign">
											<input type="checkbox" id="select-oem" onclick="saveCheckedBoxes(this.id)" > 
											<a class="buttonSecondary" class="delete-row">DELETE</a>
										</td> -->
										<td class="leftAlign">${attributeValue.attributeValue}</td>
										<td class="leftAlign">
											<input class="attribute-percentage numbers-only" type="text" value="${attributeValue.defaultPercentage}">%
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>	
			<div id="oem-mix-modal" class="modal"></div>
			<form id="business-award-form" name="businessAwardForm"></form>
		</div>
		
	</div>
	
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/business-award-maintenance.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-matrix-global.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/v2/jquery.multiselect.js" type="text/javascript"></script>
	<script type="text/javascript" src="${baseUrl}/jQuery/jquery.multiselect.filter.js"></script>
</body>
</html>