<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="modalName" value='${adjustmentOption.optionId gt 0 ? "Edit Cost Sheet Adjustment Option" : "Add Cost Sheet Adjustment Option"}' />

<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="400">
	<%@ include file="../../../global/v2/modal-error-container.jsp"%>
	<div class="row modal-body">
		<form id="option-form" class="form-horizontal">
			<input name="optionId" type="hidden" value="${adjustmentOption.optionId}"/>
			<div class="col-xs-12">

				<div class="form-group">
					<div class="col-xs-4">
						<label for="orderCode">Order Code</label>
					</div>
					<div class="col-xs-8">
						<input id="orderCode" name="orderCode" type="text" maxLength="20" autocomplete="off" value="${adjustmentOption.orderCode}"/>
					</div>
					<div class="error hidden col-xs-12">
						<img src="${commonStaticUrl}/images/warning.png">
						<span class="errorMsg">Please enter Order Code</span>
					</div>
				</div>

			</div>
		</form>
	</div>

	<div class="modal-footer">
		<a href="#" id="cancelButton" class="secondaryLink">Cancel</a>
		<c:if test="${adjustmentOption.optionId gt 0}">
			<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doSave(); return false;">Save</a>
		</c:if>
		<c:if test="${adjustmentOption.optionId eq 0}">
			<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doAdd(); return false;">Add</a>
		</c:if>
	</div>
</div>
