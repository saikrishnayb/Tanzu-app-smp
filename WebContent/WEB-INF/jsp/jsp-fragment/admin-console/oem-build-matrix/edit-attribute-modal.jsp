<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="col-xs-12">
	<%@ include file="../../../jsp-fragment/admin-console/oem-build-matrix/edit-attribute-content.jsp" %>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/edit-attribute.js" type="text/javascript"></script>	
	<div class="modal-footer row">
		<c:choose>
			<c:when test="${editPopup eq true}">
				<div class="save-attribute col-xs-12">
					<a class="buttonPrimary buttonDisabled floatRight"
						id="update-attr" data-save-attr-id="${buildAttribute.attributeId}" href="#"
						onclick="">Save</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="save-attribute col-xs-12">
					<a class="buttonPrimary floatRight" id="create-attr"
						data-save-attr-id="${buildAttribute.attributeId}" href="#" onclick="">Save</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
