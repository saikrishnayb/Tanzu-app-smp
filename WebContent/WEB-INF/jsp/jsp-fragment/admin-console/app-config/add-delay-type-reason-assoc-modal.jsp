<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="blank"></div>
<form id="add-delay-type-reason-form" action="add-delay-type-reason.htm" method="post">
	<div class="line">
		<label>Delay Type&nbsp;&nbsp;&nbsp;&nbsp;</label>
		<select id="delay-type-of-reason" class="edit-input input numeric numeric-whole" name="typeId">
			<option value=""></option>
			<c:forEach items="${types}" var="types">
			<option value="${types.typeId}">${types.delayType}</option>
			</c:forEach>
		</select>
	</div>
	<div class="line">
		<label>Delay Reason</label>
		<select id="add-delay-reason-name" class="edit-input input numeric numeric-whole" name="reasonName">
			<option value=""></option>
			<c:forEach items="${reasons}" var="reason">
			<option value="${reason.reasonId}">${reason.delayReason}</option>
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
		<a class="buttonPrimary add-delay-reason-save">Save</a>
	</div>
</div>