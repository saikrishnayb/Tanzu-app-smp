<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>OEM Build Matrix</title>

	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-mix.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	
	<div id="mainContent">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<div class="container-fluid">
				<%@ include file="../../global/v2/page-error-container.jsp"%>
				<div class="row">
	        		<div class="col-xs-12">
	          			<h1>OEM Mix</h1>
	        		</div>
	      		</div>
	      		<div class="row">
	        		<div class="col-xs-12 build-mix-top" data-bodies-on-order="${bodiesOnOrder}" >
		        		<div class="row">
		        			<div class='badge-div col-lg-4' >
		          				<label>Bodies on Order</label> <span class="badge">${bodiesOnOrder}</span>
		          				<label>Chassis Available</label> <span class="badge">${chassisAvailable}</span>
		          			</div>
		          			<div class='weeks-badge-div col-lg-4' >
		          				<label>Build Weeks Before</label> <span class="badge max-weeks-before-badge">${summary.maxWeeksBefore}</span>
		          				<label>Build Weeks After</label> <span class="badge max-weeks-after-badge">${summary.maxWeeksAfter}</span>
		          			</div>
		          			<div class="btn-div col-lg-4">
		          				<div class="floatRight">
			          				<a id="update-build-btn" class="secondaryLink">Update Build Parameters</a>
			          				<a id="back-btn" href="${baseAppUrl}/admin-console/oem-build-matrix/available-chassis-summary?buildId=${summary.buildId}" class="buttonSecondary">Back</a>
			          				<a id="submit-btn" class="buttonPrimary buttonDisabled" data-build-id="${summary.buildId}">Submit</a>
			          			</div>
		          			</div>
		        		</div>
	        		</div>
	      		</div>
	      		<div class="row">
	      			<div class="col-xs-12">
	      				<div class="floatRight">
	      					<label for="guidance">Guidance</label>
	      					<div class="slider-container">
							  <label class="switch" for="guidance">
							    <input type="checkbox" id="guidance" name="guidance" <c:if test='${summary.guidanceMode eq "Y"}'> checked</c:if> />
							    <div class="slider round"></div>
							  </label>
							</div>
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
	      				<c:set var="isBodyMake" value="${false}" />
	      				<c:set var="awardByAttributeKey" value="${buildMixMap.get(attribute.groupKey)}" />
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
							<c:when test='${attribute.attributeKey eq "BODYMAKE"}'>
	      						<c:set var="isBodyMake" value="${true}" />
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
		      					<c:if test="${isBodyMake}">data-is-body-make</c:if>
		      					>
		      					<div class="row">
		      						<div class="col-xs-12 attribute-header">
		      							<h2>${attribute.attributeName}</h2>
		      							<div class="reset-div floatRight">
		      								<c:if test="${isBodyMake}">
		      									<a class="secondaryLink split-by-type-link" data-build-id="${summary.buildId}">Split By Type</a>
		      								</c:if>
		      								<a class="secondaryLink reset-link">Reset</a>
		      							</div>
		      						</div>
		      					</div>
		      					<div class="row">
		      						<div class="col-xs-12 attribute-table-container">
		      							<table class="attribute-table">
		      								<c:if test="${isBodyMake}">
			      								<thead>
			      									<tr>
			      										<th></th>
			      										<th></th>
			      										<th>Ideal</th>
			      										<th>Excess</th>
			      									</tr>
			      								</thead>
		      								</c:if>
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
		      										<c:set var="existingAward" value="${awardByAttributeKey.get(attributeValue.attributeValue)}"/>
		      										<c:choose>
		      											<c:when test="${!empty existingAward}">
		      												<c:set var="percentValue" value="${existingAward.percentage}"/>
		      											</c:when>
		      											<c:otherwise>
		      												<c:set var="percentValue" value="${attributeValue.defaultPercentage}"/>
		      											</c:otherwise>
		      										</c:choose>
		      										<tr class="attribute-value-row ${rowClass}" data-attribute-value="${attributeValue.attributeValue}">
		      											<td class="attribute-value-td">${attributeValue.attributeValue}</td>
		      											<td class="attribute-percentage-td unit-percent-input">
		      												<input type="text" class="attribute-percentage text-align-right numbers-only" value="${percentValue}" />
		      												<span class="percent-sign">%</span>
		      											</td>
		      											<!-- <td>%<td> -->
		      											<td class="attribute-units-td unit-percent-input">
			      											<c:choose>
			      												<c:when test="${!empty existingAward}">
			      													<c:set var="unitsValue" value="${existingAward.quantity}"/>
			      												</c:when>
			      												<c:otherwise>
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
					      										</c:otherwise>
					      									</c:choose>
			      											<input type="text" class="attribute-units text-align-right numbers-only" value="${unitsValue}"/>
			      											<c:if test="${!isBodyMake}"><span class="units-label">Units</span></c:if>
			      										</td>
		      											<c:if test="${isBodyMake}">
				      										<td class="excess-attribute-units-td">
				      											<c:choose>
				      												<c:when test="${!empty existingAward}">
				      													<c:set var="excessUnitsValue" value="${existingAward.awardExcess}"/>
				      												</c:when>
				      												<c:otherwise>
					      												<c:set var="excessUnitsValue" value=""/>
						      										</c:otherwise>
						      									</c:choose>
				      											<input type="text" class="excess-attribute-units text-align-right numbers-only" value="${excessUnitsValue}"/>
				      											<span class="units-label">Units</span>
				      										</td>
				      									</c:if>
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
		      										<c:when test="${isBodyMake}">
			      										<tr class="attribute-total-row">
				      										<td >Total</td>
				      										<td class="total-percentage-td unit-percent-input">
				      											<span class="total-percentage text-align-right">0</span>
				      											<span class="percent-sign">%</span>
				      										</td>
				      										<!-- <td>%<td> -->
				      										<td class="total-units-td unit-percent-input" colspan="2">
				      											<span class="total-units text-align-right">0</span>
				      											<span class="units-label">of ${bodiesOnOrder} Units</span>
				      										</td>
				      										<!-- <td>Units</td> -->
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
				<form id="build-mix-form" name="buildMixForm" data-build-id="${summary.buildId}"></form>
				
				<div id="build-mix-modal" class="modal"></div>
				<div class="modal row" id="body-split-warning-modal">
			      <div class="modal-content col-xs-12" 
			      	data-modal-title="Existing Body Splits" 
			      	data-modal-max-width="450" 
			      	data-keep-contents="true">
			        <div class="row">
			          <div class="col-xs-12">
			            You already have Body Splits for this make. If you want to make changes, the Body Splits will need to be deleted. Do you want to delete the Body Splits?
			          </div>
			          
			          <div class="clear-confrim-button-row col-xs-12">
			            <div class="pull-right">
			              <a id="cancel-change-units" class="buttonSecondary btn">No</a>
			              <a id="confirm-change-units" class="buttonPrimary btn" data-build-id="${summary.buildId}" data-make>Yes</a>
			            </div>
			          </div>
			        </div>
			      </div>
			    </div>
				
			</div>
		</div>
	</div>
	
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/build-mix.js" type="text/javascript"></script>
</body>
</html>