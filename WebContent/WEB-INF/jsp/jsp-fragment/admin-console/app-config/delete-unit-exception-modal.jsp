<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="line">
		<label class="wide-label">Component Name:</label>
		<label>${exception.componentName}</label>
	</div>
	<div class="blank"></div>
	<div class="line">
		<label class="wide-label">Unit #</label>
		<label>${exception.unitNumber}</label>
	</div>
	<div class="blank"></div>
	<div class="line">
		<div id="list-div">
		<label class="wide-label">PO Group</label>
		<ul id="po-group-items">
			<c:forEach items="${poGroup}" var="poGroup">
			<li>
				<label>${poGroup}</label>
			</li>
			</c:forEach>
		</ul>
		</div>
	</div>
	<div class="blank"></div>
	<div class="line">
		<label class="wide-label">To be Provided By:</label>
		<label>${exception.providerPo}</label>
	</div>
	<c:if test="${exception.providerSubPo != ''}">
		<div class="blank"></div>
		<div class="line">
			<label class="wide-label">Provider Sub Group:</label>
			<label>${exception.providerSubPo}</label>
		</div>
	</c:if>
	<input type="hidden" id="exception-id-modal" value="${exception.exceptionId}"/>
<div class="blank"></div>
<div class="buttonLine">
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary delete-unit-exception-confirm">Yes, Delete</a>
	</div>
</div>