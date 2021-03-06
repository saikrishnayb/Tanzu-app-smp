<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>OEM Build Matrix</title>

	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/order-summary.css" rel="stylesheet" type="text/css" />
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
	          			<h1>Order Summary</h1>
	        		</div>
	      		</div>
				<div class="row">
	        		<div class="col-xs-12 confirm-selection-table-top">
	        			<div class='search-div'>
	          				<label>Search: </label> <input type="text" id="order-search"/>
	          			</div>
	        			<div class='badge-div'>
	          				<label>Bodies on Order</label> <span id="bodies-on-order" class="badge">0</span>
	          				<label>Chassis Available</label> <span class="badge">${chassisAvailable}</span>
	          				<label>Show Selected Only</label> <input type="checkbox" id="show-selected-checkbox">
	          			</div>
	          			<div class="btn-div floatRight">
	          				<a id="add-to-build" class="buttonSecondary buttonDisabled">Add to Build</a>
	          			</div>
	        		</div>
	      		</div>
	      		<div class="row">
            		<div class="col-xs-12 order-summary-table-container">			
						<table id="order-summary-table">
							<thead>
								<tr>
									<th class=""><input id="mass-select-all" type="checkbox"></th>
									<th class="">Order#</th>
									<th class="">Status</th>
									<th class="">Region</th>
									<th class="">Area</th>
									<th class="">District</th>
									<th class="">District Name</th>
									<th class="">Program Name</th>
									<th class="">Requested Qty</th>
									<th class="">Fulfilled Qty</th>
									<th class=""># of Units to Consider</th>
									<th class="">Requested Delivery Date</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${approvedOrdersByKey}" var="orderEntry">
									<c:set var="orderKey" value="${orderEntry.key}"/>
									<c:set var="order" value="${orderEntry.value}"/>
									<c:set var="unitsToConsider" value="${null}"/>
									<c:if test="${!empty unitsToConsiderByOrderKey}">
										<c:set var="unitsToConsider" value="${unitsToConsiderByOrderKey.get(orderKey)}"/>
									</c:if>
									<c:set var="orderSelected" value="${unitsToConsider ne null}"/>
									<c:choose>
										<c:when test="${orderSelected}">
											<c:set var="rowClass" value="user-row approved-order-row row-selected"/>
										</c:when>
										<c:otherwise>
											<c:set var="rowClass" value="user-row approved-order-row"/>
										</c:otherwise>
									</c:choose>
									<tr class="${rowClass}" data-order-id="${order.orderId}" data-delivery-id="${order.deliveryId}" data-unfulfilled-qty="${order.unfulfilledQty}">
										<td><input class="select-order" onclick="saveCheckedBoxes(this.id)" type='checkbox' <c:if test="${orderSelected}"> checked</c:if>></td>
										<td>${order.orderId}</td>
										<td>${order.approvalStatus.label}</td>
										<td>${order.region}</td>
										<td>${order.area}</td>
										<td>${order.district}</td>
										<td>${order.districtDesc}</td>
										<td>${order.packageName}</td>
										<td class="order-quantity">${order.orderTotalQuantity}</td>
										<td class="order-fulfilled">${order.fulfilledQty}</td>
										<td class="units-to-consider-td">
											<c:choose>
												<c:when test="${orderSelected}">
													<input type="text" class="units-to-consider numbers-only" value="${unitsToConsider}">
												</c:when>
												<c:otherwise>
													<input type="text" class="units-to-consider numbers-only" value="${order.unfulfilledQty}">
												</c:otherwise>
											</c:choose>
										</td>
										<td>${order.formattedDeliveryDate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				
				<div id="edit-attribute-popup" style="display:none;"></div>
				<form id="order-selection-form" name="orderSelectionForm" data-build-id="${buildId}" data-guidance="${guidance}" method="POST" action="${baseAppUrl}/admin-console/oem-build-matrix/add-to-build"></form>
			</div>
		</div>
	</div>
	
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/order-summary.js" type="text/javascript"></script>
	
</body>
</html>