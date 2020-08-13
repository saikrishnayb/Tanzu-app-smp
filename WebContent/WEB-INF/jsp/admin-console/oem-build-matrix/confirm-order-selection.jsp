<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>OEM Build Matrix</title>

	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/confirm-order-selection.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<c:set var="tooManyBodies" value="${bodiesOnOrder > chassisAvailable}"/>
			<div class="container-fluid">
				<%@ include file="../../global/v2/page-error-container.jsp"%>
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
			          				There are not enough available chassis to match the Body Orders selected. Please modify your body selection or unexclude chassis on the next screen.
		          			</div>
	          			</c:if>
	          			<div class="btn-div floatRight">
	          				<a id="back-btn" href="${baseAppUrl}/admin-console/oem-build-matrix/order-summary?buildId=${buildId}" class="buttonSecondary" >Back</a>
	          				<a id="continue" href="${baseAppUrl}/admin-console/oem-build-matrix/available-chassis-summary?buildId=${buildId}" class="buttonSecondary">Continue</a>
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
								<c:forEach items="${orderMap}" var="entry">
									<c:set var="orderKey" value="${entry.key}" />
									<c:set var="pair" value="${entry.value}" />
									<c:set var="order" value="${pair.left}" />
									<c:set var="croBuildRequest" value="${pair.right}" />
									<tr class="user-row approved-order-row" data-order-id="${order.orderId}">
										<td>${order.orderId}</td>
										<td>${order.approvalStatus.label}</td>
										<td>${order.region}</td>
										<td>${order.area}</td>
										<td>${order.district}</td>
										<td>${order.districtDesc}</td>
										<td>${order.packageName}</td>
										<td>${croBuildRequest.requestedQty}</td>
										<td>${order.formattedDeliveryDate}</td>
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
	
	<%@ include file="../../global/v2/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/confirm-order-selection.js" type="text/javascript"></script>
	
</body>
</html>