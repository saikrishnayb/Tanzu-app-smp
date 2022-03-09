<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="purchasing-details-modal" class="modal-content col-xs-12" data-modal-title="Purchasing Details" data-modal-width="550">
	<%@ include file="../../../global/v2/modal-error-container.jsp"%>
	<div class="row modal-body">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<h2>Vendor Information</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-5">
					<label>Vendor Number</label>
				</div>
				<div class="col-xs-7">
					${vendor.vendorNumber}
				</div>
			</div>
			<div class="row">
				<div class="col-xs-5">
					<label>Vendor Name</label>
				</div>
				<div class="col-xs-7">
					${vendor.vendorName}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<hr>
		</div>
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<h2>Purchasing Summary</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-5">
					<label>POs Issued (Last 3 Years)</label>
				</div>
				<div class="col-xs-7">
					${purchasingSummary.posIssuedInLast3Years}
				</div>
			</div>
			<div class="row">
				<div class="col-xs-5">
					<label>Date of Last PO Issued</label>
				</div>
				<div class="col-xs-7">
					${purchasingSummary.formattedLastPoDate}
				</div>
			</div>
		</div>
	</div>
	
	<div class="row modal-footer">
		<div class="col-xs-12">
			<a class="buttonPrimary modal-close">Close</a>
		</div>
	</div>
</div>
