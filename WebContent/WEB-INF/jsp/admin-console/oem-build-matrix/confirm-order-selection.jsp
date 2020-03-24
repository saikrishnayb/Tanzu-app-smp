<!DOCTYPE html>

<html>
	<head>
	<title>OEM Build Matrix</title>
		<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
		<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix.css" rel="stylesheet" type="text/css" />
		<link href="${baseUrl}/css/admin-console/oem-build-matrix/confirm-order-selection.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
		
		<div id="mainContent">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/oem-build-matrix/left-nav.jsp"%>
			<div class="leftNavAdjacentContainer">
				<c:set var="tooManyBodies" value="${bodiesOnOrder > chassisAvailable}"/>
				<div class="container-fluid">
					<div id="PopupError" style="display:none">
						<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
					</div>
					<div class="row">
		        		<div class="col-xs-12">
		          			<h1>Confirm Selection</h1>
		        		</div>
		      		</div>
		      		<div class="row">
		        		<div class="col-xs-12 confirm-selection-table-top">
		        			<div class='badge-div'>
		          				<label>Bodies on Order</label> <span class="badge">${bodiesOnOrder}</span>
		          				<label>Chassis Available</label> <span class="badge">${chassisAvailable}</span>
		          			</div>
		          			<c:if test="${tooManyBodies}">
			          			<div class='too-many-bodies-div'>
			          				There are not enough available Chassis to match the Body Orders selected. Please modify your selection and try again.
			          			</div>
		          			</c:if>
		          			<div class="btn-div floatRight">
		          				<a id="back-btn" href="${baseAppUrl}/admin-console/oem-build-matrix/order-summary?buildId=${buildId}" class="buttonSecondary round-corner-btn-cls">Back</a>
		          				<a id="continue" class="buttonSecondary round-corner-btn-cls <c:if test="${tooManyBodies}"> buttonDisabled</c:if>">Continue</a>
		          			</div>
		        		</div>
		      		</div>
		      		<div class="row">
	            		<div class="col-xs-12 confirm-selection-table-container">			
							<table id="confirm-selection-table">
								<thead>
									<tr>
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
									<c:forEach items="${selectedOrders}" var="order">
										<tr class="user-row approved-order-row" data-order-id="${order.orderId}">
											<td>${order.orderId}</td>
											<td>${order.approvalStatus.label}</td>
											<td>${order.region}</td>
											<td>${order.area}</td>
											<td>${order.district}</td>
											<td>${order.districtDesc}</td>
											<td>${order.packageName}</td>
											<td>${order.orderTotalQuantity}</td>
											<td>${order.deliveryDate}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div id="edit-attribute-popup" style="display:none;"></div>
				</div>
			</div>
		</div>
		
		<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
		<script src="${baseUrl}/js/admin-console/oem-build-matrix/confirm-order-selection.js" type="text/javascript"></script>
		
	</body>
</html>