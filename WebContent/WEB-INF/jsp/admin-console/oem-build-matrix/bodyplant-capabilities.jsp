<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
<head>
<title>OEM Build Matrix</title>
	<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/bodyplant-capabilities.css" rel="stylesheet" type="text/css" />
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
					<h1>Body Plant Exceptions - ${plantData.plantName} -
						${plantData.city}, ${plantData.state}</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<table id="plant-capablity-table" class="plant-view">
						<thead>
							<tr>
								<th class="editHeader"></th>
								<th class="">Attribute Name</th>
								<th class="">Attribute Values</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${bodyPlantCapability}" var="attribute">
								<tr class="user-row" data-attribute-id="${attribute.attributeId}">
									<td class="editable centerAlign">
										<a onclick="loadEditDimensionForm(${attribute.attributeId})"
											class="rightMargin edit-button" id="edit-capability"> Edit
										</a>
									</td>
									<td>${attribute.attributeName}</td>
									<td class="value-td">
										<c:forEach items="${attribute.attributeValuesMap}" var="attributeValue">
											<c:if test="${attributeValue.value}">
												<span class="badge selected-attrvalue">${attributeValue.key}</span>
											</c:if>
											<c:if test="${!attributeValue.value}">
												<span class="badge non-selected-attrvalue">${attributeValue.key}</span>
											</c:if>
										</c:forEach></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div id="edit-dimension-popup-modal" class="modal row"></div>
		</div>
	</div>

	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/bodyplant-capabilities.js" type="text/javascript"></script>
	<script src="${baseUrl}/jQuery/jquery.multiselect.js" type="text/javascript"></script>

</body>
</html>