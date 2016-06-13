<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<form id="edit-modal-form" action="edit-delay-type-reason.htm">
	<div class="line">
		<label>Delay Type</label>
		<input type="hidden" id="hidden-type-id" value ="${delayReasonModel.typeId}"/>
		<input type="hidden" id="hidden-reason-id" value="${delayReasonModel.reasonId}" />
		<input type="hidden" id="hidden-assoc-id" value="${delayReasonModel.delayAssocid}" />
		<select id="delay-type-of-reason" class="edit-input input numeric numeric-whole">
			<option value=""></option>
			<c:forEach items="${types}" var="types">
			<option value="${types.typeId}" <c:if test="${delayReasonModel.typeId == types.typeId}"> selected </c:if>>${types.delayType}</option>
			</c:forEach>
		</select>
	</div>
	<div class="line">
		<label>Delay Reason</label>
		<select id="edit-delay-reason-name" class="edit-input input numeric numeric-whole">
			<option value=""></option>
			<c:forEach items="${reason}" var="reason">
			<option value="${reason.reasonId}" <c:if test="${delayReasonModel.reasonId == reason.reasonId}"> selected </c:if>>${reason.delayReason}</option>
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
		<a class="buttonPrimary edit-delay-reason">Save</a>
	</div>
</div>