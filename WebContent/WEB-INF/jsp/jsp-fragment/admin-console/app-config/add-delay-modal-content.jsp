<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
	<form id="add-delay-form" action="add-new-delay.htm">
		<div class="line">
		<label>Date Type</label>
		<select name="dateTypeId" id="date-type" class="input alpha alpha-name">
			<option value="default">Date Types...</option>
			<c:forEach items="${dateTypes}" var="dateTypes">
			<option value="${dateTypes}">${dateTypes}</option>
			</c:forEach>
		</select>
		</div>
		<div class="line">
		<label>PO Category</label>
		<select name="poCategoryId" id="po-category" class="input numeric numeric-whole">
			<option value="default">PO Categories...</option>
			<c:forEach items="${POs}" var="PO">
			<option value="${PO.categoryId}">${PO.categoryName}</option>
			</c:forEach>
		</select>
		</div>
		<div class="line">
		<label>Delay Type</label>
		<select name="delayTypeId" id="delay-type" class="input numeric numeric-whole">
			<option value="default">Delay Types...</option>
			<c:forEach items="${types}" var="type">
			<option value="${type.typeId}">${type.delayType}</option>
			</c:forEach>
		</select>
		</div>
		<div class="line">
		<label>Delay Reason</label>
		<select name="delayReasonId" id="delay-reason" class="input numeric numeric-whole">
			<option value="default">select a Delay Type...</option>
		</select>
		<img id="spinnerSmall" src="${commonStaticUrl}/images/spinner.gif" class="hidden"/>
		</div>
	</form>
	<div class="blank"></div>
<div class="buttonLine">
	<div class="hidden errorDiv" id="error-message">
		<img src="${commonStaticUrl}/images/warning.png"></img>
		<span id="message-span" class="errorMsg"></span>
	</div>
	<div class="float-right">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary save">Save</a>
	</div>
</div>