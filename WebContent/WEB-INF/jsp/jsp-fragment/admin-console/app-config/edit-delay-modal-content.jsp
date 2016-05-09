<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
	<form method="post" action="edit-delay.htm" id="edit-delay-form">
	<input type="hidden" id="delay-id" name="delayId" value="${delay.delayId}"/>
		<div class="line">
			<label>Date Type</label>
			<select id="date-type" name="dateTypeId" class="input alpha alpha-name">
				<c:forEach items="${dateTypes}" var="dateTypeId">
				<option value="${dateTypeId}"
						<c:if test="${dateTypeId eq delay.dateType}"> selected</c:if>>${dateTypeId}</option>
				</c:forEach>
			</select>
		</div>
		<div class="line">
			<label>PO Category</label>
			<select id="po-category" name="poCategoryId" class="input numeric numeric-whole">
				<c:forEach items="${POs}" var="PO">
				<option value="${PO.categoryId}"
						<c:if test="${PO.categoryName eq delay.poCategory}"> selected</c:if>>${PO.categoryName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="line">
			<label>Delay Type</label>
			<select id="delay-type" name="delayTypeId" class="input numeric numeric-whole">
				<c:forEach items="${types}" var="type">
				<option value="${type.typeId}"
						<c:if test="${type.delayType eq delay.delayType}"> selected</c:if>>${type.delayType}</option>
				</c:forEach>
			</select>
		</div>
		<div class="line">
			<label>Delay Reason</label>
			<select id="delay-reason" name="delayReasonId" class="input numeric numeric-whole">
				<c:forEach items="${reasons}" var="reason">
					<option value="${reason.reasonId}"
					<c:if test="${reason.delayReason eq delay.delayReason}"> selected</c:if>>${reason.delayReason}</option>
				</c:forEach>
			</select>
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
		<a class="buttonPrimary save-edit">Save</a>
	</div>
</div>