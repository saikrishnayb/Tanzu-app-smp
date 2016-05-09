<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="edit-unit-exception-form" action="edit-unit-exception" method="post">
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
		<label class="wide130">To be Provided By:</label>
		<select id="provided-by-list" class="wide-select input alpha alpha-numeric" name="provider">
			<option value=""></option>
			<c:forEach items="${poGroup}" var="provider">
			<option value="${provider}">${provider}</option>
			</c:forEach>
		</select>
	</div>
	<div class="line">
		<label class="wide130">Provider Sub Group:</label>
		<select id="provided-by-sub" class="wide-select input alpha alpha-numeric optional" name="providerSub">
			<option value="default"></option>
		</select>
	</div>
	<input type="hidden" id="exception-id-modal" value="${exception.exceptionId}"/>
</form>
<div class="line">
	<div class="hidden errorDiv" id="error-message">
		<img src="${commonStaticUrl}/images/warning.png"></img>
		<span id="message-span" class="errorMsg"></span>
	</div>
</div>
<div class="blank"></div>
<div class="blank"></div>
<div class="buttonLine">
	<div class="left-checkbox">
		<input type="checkbox" id="unit-checkbox" value="makeGlobal"/><span class="small-text">Apply to All Future Conflicts</span>
	</div>
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary edit-unit-exception-confirm">Save</a>
	</div>
</div>