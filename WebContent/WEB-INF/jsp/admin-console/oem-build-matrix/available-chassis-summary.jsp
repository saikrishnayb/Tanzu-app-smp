<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
	<title>OEM Build Matrix</title>
	
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/available-chassis-summary.css" rel="stylesheet" type="text/css" />
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
	          			<h1>Available Chassis Summary</h1>
	        		</div>
	      		</div>
	      		<div class="row">
	        		<div class="col-xs-12 available-units-table-top" data-build-id="${buildId}">
	        			<div class='badge-div'>
	          				<label>Bodies on Order</label> <span class="badge bodies-on-order-badge">${bodiesOnOrder}</span>
	          				<label>Chassis Available</label> <span class="badge chassis-available-badge" data-chassis-available="${chassisAvailable}">${chassisAvailable}</span>
	          			</div>
	          			<div class='too-many-bodies-div hidden'>
	          				There are not enough available Chassis to match the Body Orders selected. Please modify your body selection or unexclude chassis to continue.
	          			</div>
	          			<div class="btn-div floatRight">
	          				<a id="back-btn" href="${baseAppUrl}/admin-console/oem-build-matrix/back-to-confirm?buildId=${buildId}" class="buttonSecondary">Back</a>
	          				<a id="continue" href="${baseAppUrl}/admin-console/oem-build-matrix/build-mix?buildId=${buildId}" class="buttonSecondary <c:if test="${tooManyBodies}"> buttonDisabled</c:if>">Continue</a>
	          			</div>
	        		</div>
	      		</div>
	      		      <div class="row">
				        <div class="col-xs-3">
				          <div class="pull-left">
				            <label>Unit Search:</label> <input type="text" id="unit-search" />
				          </div>
				        </div>
				        <div class="col-xs-3">
				          <div class="pull-right">
				            <a id="multi-exclude-btn" class="buttonSecondary btn buttonDisabled">Exclude</a>
				          </div>
				        </div>
				        <div class="col-xs-6">
				          <div class="pull-right">
				            <a id="clear-excluded-units-btn"class="buttonSecondary btn <c:if test="${empty summaryModel.groupedExcludedUnits}">buttonDisabled</c:if>">Clear</a>
				          </div>
				        </div>
				      </div>
				      <div class="row available-units-table-container">
				        <div class="col-xs-6 eligible-unit-table-conatiner">
				          <div>
				            <table id="eligible-unit-table">
				              <thead>
				                <tr>
				                  <th><input type="checkbox" id="select-all-unit-ranges" /></th>
				                  <th>Unit Range</th>
				                  <th>Corp</th>
				                  <th>Location</th>
				                  <th>Unit Quantity</th>
				                  <th>Select Unit Quantity</th>
				                  <th></th>
				                </tr>
				              </thead>
				              <tbody>
				                <c:forEach var="unitRange" items="${summaryModel.groupedAvailableUnits}" varStatus="outerLoop">
				                  <c:set var="rangeIsOneUnit" value="${unitRange.get(0).unitNumber==unitRange.get(unitRange.size() - 1).unitNumber}" />
				                  <c:set var="sampleUnit" value="${unitRange.get(0)}" />
				                  <c:set var ="unitArray" value=""/>
				                  <c:forEach var="unit" items="${unitRange}" varStatus="loopStatus">
				                    <c:set var="seperator" value=""/>
				                    <c:if test="${loopStatus.count != unitRange.size()}">
				                      <c:set var="seperator" value =","/>
				                    </c:if>
				                    <c:set var="unitArray" value ='${unitArray}${fn:trim(unit.unitNumber)}${seperator}'/>
				                  </c:forEach>
				                  <tr	
				                    data-unit-array='[${unitArray}]' data-row-id="${outerLoop.count}" data-original-unit-array='[${unitArray}]'>
				                    <td 
				                      data-start-unit="${fn:trim(unitRange.get(0).unitNumber)}" 
				                      data-end-unit="${fn:trim(unitRange.get(unitRange.size() - 1).unitNumber)}">
				                      <input type="checkbox" class="select-unit-range" />
				                    </td>
				                    <td>
				                      <c:choose>
				                        <c:when test="${rangeIsOneUnit}">
				                          ${sampleUnit.unitNumber}
				                        </c:when>
				                        <c:otherwise>
				                          ${sampleUnit.unitNumber} - ${unitRange.get(unitRange.size() - 1).unitNumber}
				                        </c:otherwise>
				                      </c:choose>
				                    </td>
				                    <td>${sampleUnit.corp}</td>
				                    <td>${sampleUnit.districtNumber}</td>
				                    <td>${unitRange.size()}</td>
				                    <td>
				                      <input type="text" min=0 max="${unitRange.size()}" id="select-unit-quantity" class="unit-quantity-textbox" pattern="\d+" <c:if test="${rangeIsOneUnit}">disabled="disabled"</c:if>>
				                    </td>
				                    <td><a class="buttonSecondary btn exclude-btn">EXCLUDE</a></td>
				                  </tr>
				                </c:forEach>
				              </tbody>
				            </table>
				          </div>
				        </div>
				        <div class="col-xs-6">
				          <div>
				            <table id="excluded-units-table">
				              <thead>
				                <tr>
				                  <th></th>
				                  <th>Unit Range</th>
				                  <th>Corp</th>
				                  <th>Location</th>
				                  <th>Unit Quantity</th>
				                  <th>Identify As</th>
				                </tr>
				              </thead>
				              <tbody>
				              	<c:set var="rowId" value = "9999"/>
				              	<c:forEach var="unitRange" items="${summaryModel.groupedExcludedUnits}" varStatus="outerLoop">
				                  <c:set var="rangeIsOneUnit" value="${unitRange.get(0).unitNumber==unitRange.get(unitRange.size() - 1).unitNumber}" />
				                  <c:set var="sampleUnit" value="${unitRange.get(0)}" />
				                  <c:set var ="unitArray" value=""/>
				                  <c:forEach var="unit" items="${unitRange}" varStatus="loopStatus">
				                    <c:set var="seperator" value=""/>
				                    <c:if test="${loopStatus.count != unitRange.size()}">
				                      <c:set var="seperator" value =","/>
				                    </c:if>
				                    <c:set var="unitArray" value ='${unitArray}${fn:trim(unit.unitNumber)}${seperator}'/>
				                  </c:forEach>
				                  <tr class="row-normal"	
				                    data-unit-array='[${unitArray}]' data-row-id="${rowId}">
				                    <td>
				                      <i class="fa fa-trash delete-exclude"></i>
				                    </td>
				                    <td>
				                      <span class="row-excluded-range">
				                      <c:choose>
				                        <c:when test="${rangeIsOneUnit}">
				                          ${sampleUnit.unitNumber}
				                        </c:when>
				                        <c:otherwise>
				                          ${sampleUnit.unitNumber} - ${unitRange.get(unitRange.size() - 1).unitNumber}
				                        </c:otherwise>
				                      </c:choose>
				                      </span>
				                    </td>
				                    <td>${sampleUnit.corp}</td>
				                    <td>${sampleUnit.districtNumber}</td>
				                    <td>
				                      <span class="row-excluded-quantity">${unitRange.size()}</span>
				                    </td>
				                    <td class="select-excluded-unit-quantity-cell">
				                      <a class="buttonSecondary btn excluded-btn">EXCLUDED</a>
				                    </td>
				                  </tr>
				                  <c:set var="rowId" value = "${rowId-1}"/>
				                </c:forEach>
				              </tbody>
				            </table>
				          </div>
				        </div>
				      </div>
				      <div id="quantity-row" class="row">
				        <div class="col-xs-6">
				          <div class="pull-right">
				            <label>Total Selected Units: <span id="selected-unit-quantity">0</span></label>
				          </div>
				        </div>
				        <div class="col-xs-6">
				          <div class="pull-right">
				            <label>Total Units Excluded as RENTAL: <span id="excluded-unit-quantity">0</span></label>
				          </div>
				        </div>
				      </div>
				
				<div id="edit-attribute-popup" style="display:none;"></div>
			</div>
		</div>
	</div>
	
	<div class="modal row" id="modal-clear-exclude-confirmation">
      <div class="modal-content col-xs-12" data-modal-title="Clear Units Confirmation" data-modal-max-width="350" data-keep-contents="true">
        <div class="row">
          <div class="col-xs-12">
            Are you sure you wish to clear all of your excluded rows?
          </div>
          
          <div class="clear-confrim-button-row col-xs-12">
            <div class="pull-right">
              <a id="cancel-clear-btn" class="buttonSecondary btn">Cancel</a>
              <a id="clear-excluded-units-confirm-btn"class="buttonPrimary btn">Clear</a>
            </div>
          </div>
        </div>
      </div>
    </div>
	
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/available-chassis-summary.js" type="text/javascript"></script>
	
</body>
</html>