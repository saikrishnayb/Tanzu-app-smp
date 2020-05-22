<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="modalName" value='${editPopup eq true ? "Update Attribute Value" : "Add Attribute"}' />

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title='Payment Holds -
	<c:if test="${not empty component.componentGroup}"> ${component.componentGroup}</c:if>
	<c:if test="${not empty component.subGroup}"> ${component.subGroup}</c:if>
	<c:if test="${not empty component.subComponentName}"> ${component.subComponentName}</c:if>' 
	data-modal-width="775">
	<div class="row modal-body">
		<form id="hold-payment-form" class="hold-payment-form form-horizontal">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="col-xs-1">
						<label>Vendors:</label>
					</div>
					<div class="col-xs-11">
						<select id="vendors" name="vendors" style="width:175px;" multiple required>
							<c:forEach items="${vendors}" var="vendor">
								<c:set var="holdPayment" value="${holdpaymentsByVendorId.get(vendor.vendorId)}" />
								<c:set var="corp" value="${corpByCorpCode.get(vendor.corpCode)}" />
								<option value="${vendor.vendorId}" <c:if test="${not empty holdPayment}">selected</c:if>>
									${vendor.vendorNumber} (${corp.description}) - ${vendor.vendorName} - ${vendor.city}, ${vendor.state}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="save-hold-payments">
			<a class="buttonPrimary floatRight"
				id="save-hold-payments-btn" data-component-id="${component.componentId}">Save</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/components/modals/hold-payment-modal.js" type="text/javascript"></script>