<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
<head>
<title>OEM Build Matrix</title>
	<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/css/jquery.multiselect.css" rel="stylesheet" type="text/css" />
</head>
`
<body style="overflow-y:visible;">
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<span class="floatRight addRow push-right"> </span>
			<div id="PopupError" style="display:none">
				<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
			</div>
			<h1>Attribute Maintenance</h1>
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
						<tr class="attribute-row">
							<td class="data-view">
								<a href="#" class="edit-attribute">Update</a>
								<span class="display-color">|</span> 
							    <a href="#" class="add-attribute">Add</a>
							    <input type="hidden" class="edit-attribute-id" value="${attribute.attributeId}" />
							</td>
							<td>${attribute.attributeName}</td>
							<td>
								<c:forEach items="${attribute.values}" var="value">
								<a class="buttonPrimary non-selected-attrvalue">${value}</a>	
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div id="edit-attribute-modal" class="edit-attribute modal"></div>
			<div id="add-attribute-modal" class="add-attribute modal"></div>
		</div>
	</div>
	
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/attribute-setup.js" type="text/javascript"></script>
	<script src="${baseUrl}/jQuery/jquery.multiselect.js" type="text/javascript"></script>
	
</body>
</html>