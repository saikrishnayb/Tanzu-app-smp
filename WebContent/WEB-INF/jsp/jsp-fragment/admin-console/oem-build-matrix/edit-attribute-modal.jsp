<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../../../jsp-fragment/admin-console/oem-build-matrix/edit-attribute-content.jsp" %>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/edit-attribute.js" type="text/javascript"></script>	
<div>
	<c:choose>
		<c:when test="${editPopup eq true}">
			<div class="update-attribute">
				<a class="buttonPrimary buttonDisabled round-corner-btn-cls"
					id="update-attr" data-save-attr-id="${buildAttribute.attributeId}" href="#"
					onclick="">Save</a>
			</div>
		</c:when>
		<c:otherwise>
			<div class="save-attribute">
				<a class="buttonPrimary round-corner-btn-cls" id="create-attr"
					data-save-attr-id="${buildAttribute.attributeId}" href="#" onclick="">Save</a>
			</div>
		</c:otherwise>
	</c:choose>
</div>
