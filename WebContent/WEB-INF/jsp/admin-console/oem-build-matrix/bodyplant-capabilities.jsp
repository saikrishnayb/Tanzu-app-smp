<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
<head>
<title>OEM Build Matrix</title>
<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
<link href="${baseUrl}/css/admin-console/oem-build-matrix/bodyplant-capabilities.css" rel="stylesheet" type="text/css" />
</head>

<body style="overflow-y:visible;">
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
		<div class="leftNavAdjacentContainer">
			<span class="floatRight addRow push-right"> </span>
			<div id="PopupError" style="display:none">
				<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
			</div>
			<h1>Body Plant Exceptions - MORGAN - JANESVILLE,WI</h1>
			<table id="plant-capablity-table"  class="plant-view">
				<thead>
					<tr>
						<th class="allow">Allow</th>
						<th></th>
						<th class="">Vehicle Category</th>
						<th class="">Option Group</th>
						<th class="">Selected Options</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${capabilityList}" var="capability">
						<tr class="user-row">
							<td><input type="checkbox" value="" /></td>
							<td>
									<a href="#" onclick="loadEditDimensionForm(${capability.capabilityId})" class="buttonSecondary floatLeft edit-button" 
									id="edit-capability">
									Edit
								</a>
							</td>
							<td class="">${capability.vehicleCategory}</td>
							<td class="">${capability.optionGroup}</td>
							<td class="">
							<c:forEach items="${capability.dimensionValues}" var="dimension">
								<c:set var="contains" value="false" />
									<c:forEach var="item" items="${capability.selectedDimensions}">
										<c:if test="${item eq dimension}">
											<c:set var="contains" value="true" />
										</c:if>
									</c:forEach>

									<a class="buttonPrimary 
										<c:choose>
											<c:when test="${contains eq true}" >selected-dimension</c:when>
											<c:otherwise>non-selected-dimension</c:otherwise>
										</c:choose>">
										${dimension}
									</a>
							</c:forEach>
							</td>
						</tr>
					</c:forEach>
				
				</tbody>
			</table>
			<div id="edit-dimension-popup" class="modal" ></div>
		</div>
	</div>
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/bodyplant-capabilities.js" type="text/javascript"></script>
	
</body>
</html>