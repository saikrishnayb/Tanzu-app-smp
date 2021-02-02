<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="option-form">
	<input name="optionId" type="hidden" value="${adjustmentOption.optionId}"/>

	<label for="orderCode">Order Code</label>
	<input id="orderCode" name="orderCode" type="text" maxLength="20" autocomplete="off" value="${adjustmentOption.orderCode}"/>

	<div class="error hidden">
		<img src="${commonStaticUrl}/images/warning.png">
		<span class="errorMsg">Please enter Order Code</span>
	</div>
</form>

<div class="blank"></div>

<div class="floatRight">
	<a href="#" class="secondaryLink floatLeft cancel">Cancel</a>
	<c:if test="${adjustmentOption.optionId gt 0}">
		<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doSave(); return false;">Save</a>
	</c:if>
	<c:if test="${adjustmentOption.optionId eq 0}">
		<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doAdd(); return false;">Add</a>
	</c:if>
</div>
