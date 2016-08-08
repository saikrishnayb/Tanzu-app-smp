<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="line">
		<label>Component Name:</label>${exception.componentName}
	</div>
	<div class="blank"></div>
	<div class="line">
		<div id="list-div">
		<label>PO Group:</label>
		<ul id="po-group-items">
			<li>
				${exception.poGroup}
			</li>
		</ul>
		</div>
	</div>
	<div class="blank"></div>
	<div class="line">
		<label>To be Provided By:</label>${exception.providerPo}
	</div>
	<!-- <div class="blank"></div>
	<div class="line">
		<label class="wide-label">Provider Sub Group:</label>
		<label>${exception.providerPoSub}</label>
	</div> -->
	<input type="hidden" id="exception-id-modal" value="${exception.exceptionId}"/>
<div class="blank"></div>
<div class="buttonLine">
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary delete-global-exception-confirm">Yes, Delete</a>
	</div>
</div>