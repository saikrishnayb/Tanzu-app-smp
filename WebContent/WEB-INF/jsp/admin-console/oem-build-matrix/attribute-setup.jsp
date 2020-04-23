<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
<head>
<title>OEM Build Matrix</title>
	<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/attribute-maintenance.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/jquery.multiselect.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix-global.css" rel="stylesheet" type="text/css" />
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
			<div class="row">
        		<div class="col-xs-12">
					<h1>Attribute Maintenance</h1>
				</div>
			</div>
			<div class="row">
        		<div class="col-xs-12 attribute-setup-table-top">
        			<div class='search-div'>
          				<label>Search: </label> <input type="text" id="attribute-search"/>
          			</div>
        		</div>
      		</div>
      		<div class="row">
        		<div class="col-xs-12 business-award-maint-table-container">
					<table id="attribute-table" class="attribute-view">
						<thead>
							<tr>
								<th class="" id="heading-view"></th>
								<th class="">Attribute Name</th>
								<th class="">Possible Values</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${attributeList}" var="attribute">
								<tr class="attribute-row" data-attribute-id="${attribute.attributeId}">
									<td class="data-view">
										<a class="edit-attribute secondaryLink">Update</a>
										<span class="display-color">|</span> 
									    <a class="add-attribute secondaryLink">Add</a>
									    <input type="hidden" class="edit-attribute-id" value="${attribute.attributeId}" />
									</td>
									<td>${attribute.attributeName}</td>
									<td class="value-td">
										<c:forEach items="${attribute.attributeValues}" var="value">
										<span class="badge non-selected-attrvalue" data-attribute-value-id="${value.attributeValueId}">${value.attributeValue}</span>	
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<div id="add-update-attribute-modal" class="modal row"></div>
		</div>
	</div>
	
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/attribute-setup.js" type="text/javascript"></script>
	<script src="${baseUrl}/jQuery/jquery.multiselect.js" type="text/javascript"></script>
	
</body>
</html>