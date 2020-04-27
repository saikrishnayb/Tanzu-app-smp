<!DOCTYPE html>

<html>
	<head>
	<title>OEM Build Matrix</title>
		<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
		<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-mix.css" rel="stylesheet" type="text/css" />
		<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix-global.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
		
		<div id="mainContent">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/oem-build-matrix/left-nav.jsp"%>
			<div class="leftNavAdjacentContainer">
				<div class="container-fluid">
					<div id="PopupError" style="display:none">
						<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
					</div>
					<div class="row">
		        		<div class="col-xs-12">
		          			<h1>OEM Mix</h1>
		        		</div>
		      		</div>
		      		<div class="row">
		        		<div class="col-xs-12 build-mix-top" data-bodies-on-order="${bodiesOnOrder}" >
		        			<div class='badge-div' >
		          				<label>Bodies on Order</label> <span class="badge">${bodiesOnOrder}</span>
		          				<label>Chassis Available</label> <span class="badge">${chassisAvailable}</span>
		          			</div>
		          			<div class="btn-div floatRight">
		          				<a id="back-btn" href="${baseAppUrl}/admin-console/oem-build-matrix/available-chassis-summary?buildId=${buildId}" onclick="javascript:loadProcessImage();" class="buttonSecondary">Back</a>
		          				<a id="submit-btn" class="buttonPrimary buttonDisabled" data-build-id="${buildId}">Submit</a>
		          			</div>
		        		</div>
		      		</div>
		      		<div class="row">
		      			<div class="col-xs-12 attributes-section">
		      			<c:forEach items="${attributes}" var="attribute" varStatus="outerLoop">
		      				<c:if test="${outerLoop.count eq 1 or (outerLoop.count-1) % 3 eq 0}"><div class="row attribute-row"></c:if>
		      				<c:set var="isLiftgate" value="${false}" />
		      				<c:set var="isRearDoor" value="${false}" />
		      				<c:set var="isReefer" value="${false}" />
		      				<c:choose>
								<c:when test='${attribute.attributeKey eq "LIFTGATEMAKE"}'>
									<c:set var="isLiftgate" value="${true}" />
								</c:when>
								<c:when test='${attribute.attributeKey eq "REARDOORMAKE"}'>
		      						<c:set var="isRearDoor" value="${true}" />
								</c:when>
								<c:when test='${attribute.attributeKey eq "REEFERMAKE"}'>
		      						<c:set var="isReefer" value="${true}" />
								</c:when>
							</c:choose>
		      				<div class="col-lg-4">
			      				<diV class="attribute-container" 
			      					data-attribute-id="${attribute.attributeId}" 
			      					data-attribute-key="${attribute.attributeKey}" 
			      					data-group-key="${attribute.groupKey}" 
			      					<c:if test="${isLiftgate}">data-liftgate-units="${liftgateUnits}"</c:if>
			      					<c:if test="${isRearDoor}">data-reardoor-units="${reardoorUnits}"</c:if>
			      					<c:if test="${isReefer}">data-reefer-units="${reeferUnits}"</c:if>
			      					>
			      					<div class="row">
			      						<div class="col-xs-12 attribute-header">
			      							<h2>${attribute.attributeName}</h2>
			      							<div class="reset-div floatRight">
			      								<a class="secondaryLink reset-link">Reset</a>
			      							</div>
			      						</div>
			      					</div>
			      					<div class="row">
			      						<div class="col-xs-12 attribute-table-container">
			      							<table class="attribute-table">
			      								<tbody>
			      									<c:forEach items="${attribute.attributeValues}" var="attributeValue" varStatus="innerLoop">
			      										<c:choose>
			      											<c:when test="${innerLoop.count % 2 == 0}">
			      												<c:set var="rowClass" value="odd"/>
			      											</c:when>
			      											<c:otherwise>
			      												<c:set var="rowClass" value="even"/>
			      											</c:otherwise>
			      										</c:choose>
			      										<tr class="attribute-value-row ${rowClass}" data-attribute-value="${attributeValue.attributeValue}">
			      											<td class="attribute-value-td">${attributeValue.attributeValue}</td>
			      											<td class="attribute-percentage-td unit-percent-input">
			      												<input type="text" class="attribute-percentage text-align-right numbers-only" value="0" />
			      												<span class="percent-sign">%</span>
			      											</td>
			      											<!-- <td>%<td> -->
			      											<td class="attribute-units-td unit-percent-input">
				      											<c:choose>
				      												<c:when test="${isReefer}">
				      													<c:set var="unitsValue" value="${attributeValue.getUnitsByPercentage(reeferUnits)}"/>
				      												</c:when>
				      												<c:when test="${isRearDoor}">
				      													<c:set var="unitsValue" value="${attributeValue.getUnitsByPercentage(reardoorUnits)}"/>
				      												</c:when>
				      												<c:when test="${isLiftgate}">
				      													<c:set var="unitsValue" value="${attributeValue.getUnitsByPercentage(liftgateUnits)}"/>
				      												</c:when>
				      												<c:otherwise>
				      													<c:set var="unitsValue" value="${attributeValue.getUnitsByPercentage(bodiesOnOrder)}"/>
				      												</c:otherwise>
				      											</c:choose>
				      											<input type="text" class="attribute-units text-align-right numbers-only" value="${unitsValue}"/>
				      											<span class="units-label">Units</span>
				      										</td>
			      											<!-- <td>Units</td> -->
			      										</tr>
			      									</c:forEach>
			      									<c:choose>
			      										<c:when test='${isReefer or isRearDoor or isLiftgate}'>
			      											<c:choose>
			      												<c:when test="${isReefer}">
			      													<c:set var="totalUnits" value="${reeferUnits}" />
			      												</c:when>
			      												<c:when test="${isRearDoor}">
			      													<c:set var="totalUnits" value="${reardoorUnits}" />
			      												</c:when>
			      												<c:when test="${isLiftgate}">
			      													<c:set var="totalUnits" value="${liftgateUnits}" />
			      												</c:when>
			      											</c:choose>
			      											<tr class="attribute-total-row">
					      										<td >Total</td>
					      										<td class="total-percentage-td unit-percent-input">
					      											<span class="total-percentage text-align-right">0</span>
					      											<span class="percent-sign">%</span>
					      										</td>
					      										<!-- <td>%<td> -->
					      										<td class="total-units-td unit-percent-input">
					      											<span class="total-units text-align-right">0</span>
					      											<span class="units-label">of ${totalUnits} Units</span>
					      										</td>
					      										<%-- <td>of ${totalUnits} Units</td> --%>
					      									</tr>
			      										</c:when>
			      										<c:otherwise>
					      									<tr class="attribute-total-row">
					      										<td >Total</td>
					      										<td class="total-percentage-td unit-percent-input">
					      											<span class="total-percentage text-align-right">0</span>
					      											<span class="percent-sign">%</span>
					      										</td>
					      										<!-- <td>%<td> -->
					      										<td class="total-units-td unit-percent-input">
					      											<span class="total-units text-align-right">0</span>
					      											<span class="units-label">of ${bodiesOnOrder} Units</span>
					      										</td>
					      										<!-- <td>Units</td> -->
					      									</tr>
			      										</c:otherwise>
			      									</c:choose>
			      								</tbody>
			      							</table>
			      						</div>
		      						</div>
			      				</div>
			      				</div>
		      				<c:if test="${outerLoop.count % 3 eq 0}"></div></c:if>
		      			</c:forEach>
		      			</div> 			
		      		</div>
					<div id="oem-mix-modal" style="display:none;"></div>
					<form id="build-mix-form" name="buildMixForm" data-build-id="${buildId}"></form>
				</div>
			</div>
		</div>
		
		<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
		<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-mix.js" type="text/javascript"></script>
		<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-matrix-global.js" type="text/javascript"></script>
	</body>
</html>