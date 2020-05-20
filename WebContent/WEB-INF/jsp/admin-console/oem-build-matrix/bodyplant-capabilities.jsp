<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>OEM Build Matrix</title>
	
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/bodyplant-capabilities.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<span class="floatRight addRow push-right"> </span>
			<div class="row">
				<div class="col-xs-12">
					<h1>Body Plant Exceptions - ${plantData.plantManufacturer} - ${plantData.city}, ${plantData.state}</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<table id="plant-capablity-table" class="plant-view">
						<thead>
							<tr>
								<th class="editHeader"></th>
								<th class="">Attribute</th>
								<th class="">Attribute Values</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${bodyPlantCapability}" var="attribute">
								<tr class="user-row" data-attribute-id="${attribute.attributeId}">
									<td class="editable centerAlign">
										<a onclick="loadEditDimensionForm(${attribute.attributeId}, ${plantData.plantId}, '${attribute.attributeKey}', '${attribute.attributeName}')"
											class="rightMargin edit-button" id="edit-capability"> Edit
										</a>
									</td>
									<td>${attribute.attributeName}</td>
									<td class="value-td">
										<c:forEach items="${attribute.attributeValuesMap}" var="attributeValue">
											<c:if test="${attributeValue.value}">
												<span class="badge selected-attrvalue" data-attribute-value-id="${attributeValue.key}" value="${attributeValue.key}">${attributeValue.key}</span>
											</c:if>
											<c:if test="${!attributeValue.value}">
												<span class="badge non-selected-attrvalue" data-attribute-value-id="${attributeValue.key}" value="${attributeValue.key}">${attributeValue.key}</span>
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

	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/bodyplant-capabilities.js" type="text/javascript"></script>
</body>
</html>