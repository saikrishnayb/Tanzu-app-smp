<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../../../jsp-fragment/admin-console/oem-build-matrix/edit-attribute-content.jsp" %>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/edit-attribute.js" type="text/javascript"></script>	
<div style="width:100%;padding-top: 9px;text-align:right">
		<div>
			<div class="rightAlign">
				<c:choose>
					<c:when test="${editPopup eq true}">
					<a class="buttonPrimary buttonDisabled" id="update-attr" href="#" onclick="">Save</a>
					</c:when>
					<c:otherwise>
					<a class="buttonPrimary" id="create-attr" href="#" onclick="">Save</a>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
</div>	
