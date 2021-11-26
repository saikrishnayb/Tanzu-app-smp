<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="modalName" value="Region Association - ${plantData.plantName}" />

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="470">
	<div class="row modal-body">
		<form id="region-association-form">
			<input type="text" autofocus="autofocus" class="ui-helper-hidden">
			<input type="hidden" value="${plantData.plantId}" class="plantId" name="plantId"/>
			<input type="hidden" value="${regionData}" class="regionData" name="regionData"/>
			<div>
			<ul id="region-div">
				<c:forEach items="${regionMap}" var="entry">
					<c:set var="region" value="${entry.value}"/>
					<c:set var="ra" value="${raByRegion.get(region.region)}"/>
					<c:choose>
						<c:when test="${empty ra}">
							<c:set var="isAssociated" value="${false}"/>
							<c:set var="regionAssociationId" value=""/>
						</c:when>
						<c:otherwise>
							<c:set var="isAssociated" value="${true}"/>
							<c:set var="regionAssociationId" value="${ra.regionAssociationId}"/>
						</c:otherwise>
					</c:choose>
					<li class="region-value">
					<input type="checkbox" class="region-value-input" value="${region.region}" region-desc="${region.regionDesc}"
					<c:if test="${isAssociated}">checked="checked" region-association-id="${regionAssociationId}"</c:if>/>${region.region} - ${region.regionDesc}
					</li>
				</c:forEach>
			</ul>
			</div>
			<div class="row floatRight offline-row right-padding">
				<a class="secondaryLink" id="cancel-btn">Cancel</a>
				<a class="buttonPrimary clear-left buttonDisabled" id="save-region-association" tabindex=8>Save</a>
		     </div>
			
		</form>
		<form id="save-region-association-form" name="saveRegionAssociationForm" data-plant-id="${plantData.plantId}" method="POST" action="${baseAppUrl}/admin-console/oem-build-matrix/save-region-association"></form>
	</div>
</div>