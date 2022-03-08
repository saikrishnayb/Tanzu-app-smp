<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-content col-xs-12" data-modal-title="Vendor Information" data-modal-width="550">
	<label class="floatLeft clear-left width-125">Company</label>
	<span class="floatLeft">${vendor.companyName}</span>
	
	<label class="floatLeft clear-left width-125">Vendor Number</label>
	<span class="floatLeft">${vendor.vendorNumber}</span>
	
	<div class="floatLeft width-full"></div>
	
	<h3 class="section-header floatLeft">Accounts Payable Information</h3>
	
	<c:choose>
		<c:when test="${not empty vendor.vendorName}">
			<label class="floatLeft clear-left width-125">Vendor Name</label>
			<span class="floatLeft">${vendor.vendorName}</span>
			
			<label class="floatLeft clear-left width-125">Vendor Address</label>
			<span class="floatLeft">${vendor.vendorAddress}</span>
			
			<label class="floatLeft clear-left width-125">Attention</label>
			<span class="floatLeft">${vendor.attention}</span>
			
			<label class="floatLeft clear-left width-125">City &amp; State</label>
			<span class="floatLeft">
				<c:choose>
					<c:when test="${not empty vendor.city and not empty vendor.state}">${vendor.city}, ${vendor.state}</c:when>
					<c:otherwise>${vendor.city}${vendor.state}</c:otherwise>
				</c:choose>
			</span>
			
			<label class="floatLeft clear-left width-125">Zip Code</label>
			<span class="floatLeft">${vendor.zipCode}</span>
		</c:when>
		<c:otherwise>
			<span class="no-data-msg floatLeft clear-left width-250">No data on file.</span>
		</c:otherwise>
	</c:choose>
	
	<div class="floatLeft width-full"></div>
	
	<h3 class="section-header floatLeft">Purchasing Information</h3>
	
	<label class="floatLeft clear-left width-125">Payment Terms</label>
	<span class="floatLeft">${vendor.paymentTerms}<c:if test="${not empty vendor.paymentTerms}"> (days)</c:if></span>
	
	<div class="floatLeft spacer width-full"></div>
	
	<label class="floatLeft clear-left width-250">Order Name</label>
	<span class="floatLeft">${vendor.orderName}</span>
	
	<label class="floatLeft clear-left width-250">Purchase Order Mailing Address 1</label>
	<span class="floatLeft">${vendor.mailingAddress1}</span>
	
	<label class="floatLeft clear-left width-250">Purchase Order Mailing Address 2</label>
	<span class="floatLeft">${vendor.mailingAddress2}</span>
	
	<label class="floatLeft clear-left width-250">Purchase Order Mailing City</label>
	<span class="floatLeft">${vendor.mailingCity}</span>
	
	<label class="floatLeft clear-left width-250">Purchase Order Mailing State</label>
	<span class="floatLeft">${vendor.mailingState}</span>
	
	<label class="floatLeft clear-left width-250">Purchase Order Mailing Zip Code</label>
	<span class="floatLeft">${vendor.mailingZipCode}</span>
	
	<div class="floatLeft spacer width-full"></div>
	
	<label class="floatLeft clear-left width-125">Shipping Address 1</label>
	<span class="floatLeft">${vendor.shippingAddress1}</span>
	
	<label class="floatLeft clear-left width-125">Shipping Address 2</label>
	<span class="floatLeft">${vendor.shippingAddress2}</span>
	
	<label class="floatLeft clear-left width-125">Shipping City</label>
	<span class="floatLeft">${vendor.shippingCity}</span>
	
	<label class="floatLeft clear-left width-125">Shipping State</label>
	<span class="floatLeft">${vendor.shippingState}</span>
	
	<label class="floatLeft clear-left width-125">Shipping Zip Code</label>
	<span class="floatLeft">${vendor.shippingZipCode}</span>
	
	<a class="buttonPrimary floatRight clear-left modal-close">Close</a>
</div>
