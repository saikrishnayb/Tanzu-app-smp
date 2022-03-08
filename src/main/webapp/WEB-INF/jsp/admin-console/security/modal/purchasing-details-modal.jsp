<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-content col-xs-12" data-modal-title="Purchasing Details" data-modal-width="550">
	<%@ include file="../../../global/v2/modal-error-container.jsp"%>
	<div class="row modal-body">
		<div class="col-xs-12">
			<h2 class="row">Vendor Information</h2>
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
		<div class="row col-xs-12">
			<hr>
		</div>
		<div class="col-xs-12">
			<h2 class="row">Purchasing Summary</h2>
			<div class="row">
				<div class="col-xs-5">
					<label>POs Issued (Last 3 Years)</label>
				</div>
				<div class="col-xs-7">
					${purchasingSummary.poCount}
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
