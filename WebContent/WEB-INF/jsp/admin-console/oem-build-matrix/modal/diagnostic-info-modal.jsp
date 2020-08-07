<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="modalName" value="Debug Information" />
<div class="modal-content" data-modal-title="${modalName}" data-modal-width="550" data-modal-max-height="400">
	<div class="modal-content-body">
		<c:choose>
			<c:when test="${fn:length(debugInformation) eq 0}">
				<div class="centerAlign">No debug data found</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${debugInformation}" var="debug">
					<p class="marginLeft debug-info">${debug}</p>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="modal-footer"></div>
</div>
