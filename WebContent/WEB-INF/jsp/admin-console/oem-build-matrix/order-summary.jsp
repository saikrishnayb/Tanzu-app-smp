<!DOCTYPE html>

<html>
	<head>
	<title>OEM Build Matrix</title>
		<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
		<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix.css" rel="stylesheet" type="text/css" />
		<link href="${baseUrl}/css/admin-console/oem-build-matrix/order-summary.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
		
		<div id="mainContent">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
				<div class="container-fluid">
					<div id="PopupError" style="display:none">
						<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
					</div>
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
		          			</div>
		          			<div class="btn-div floatRight">
		          				<a id="add-to-build" class="buttonSecondary">Add to Build</a>
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
										<th class="">Quantity</th>
										<th class="">Requested Delivery Date</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${approvedOrdersByKey}" var="orderEntry">
										<c:set var="orderKey" value="${orderEntry.key}"/>
										<c:set var="order" value="${orderEntry.value}"/>
										<tr class="user-row approved-order-row" data-order-id="${order.orderId}" data-delivery-id="${order.deliveryId}">
											<td><input class="select-order" onclick="saveCheckedBoxes(this.id)" type='checkbox' <c:if test="${selectedOrderKeys.contains(orderKey)}"> checked</c:if>></td>
											<td><a>${order.orderId}</a></td>
											<td>${order.approvalStatus.label}</td>
											<td>${order.region}</td>
											<td>${order.area}</td>
											<td>${order.district}</td>
											<td>${order.districtDesc}</td>
											<td>${order.packageName}</td>
											<td class="order-quantity">${order.orderTotalQuantity}</td>
											<td>${order.deliveryDate}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div id="edit-attribute-popup" style="display:none;"></div>
					<form id="order-selection-form" name="orderSelectionForm" data-build-id="${buildId}" method="POST" action="${baseAppUrl}/admin-console/oem-build-matrix/add-to-build"></form>
				</div>
			</div>
		</div>
		
		<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
		<script src="${baseUrl}/js/admin-console/oem-build-matrix/order-summary.js" type="text/javascript"></script>
		
	</body>
</html>