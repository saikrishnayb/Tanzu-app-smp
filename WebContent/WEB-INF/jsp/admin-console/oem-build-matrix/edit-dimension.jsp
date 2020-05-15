<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="modalName" value="Edit Dimension" />
<div class="modal-content col-xs-12" data-modal-title="${modalName} - ${attributeName}" data-modal-width="400">
	<div class="row modal-body">
		<form id="editAttributeForm" class="editAttributeForm form-horizontal" data-save-attr-id="${attributeId}">
			<input type="text" autofocus="autofocus" class="display-none">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> <span class="errorMsg"></span>
			</div>
			<input type="hidden" value="${attributeId}" class="attributeId" /> 
			<input type="hidden" value="${plantId}" class="plantId" /> 
			<input type="hidden" value="${attributeKey}" class="attributeKey" />
			<div class="col-xs-12">
				<div class="col-xs-10">
					<label class="attribute-values-lbl">Select all which are applicable</label>
				</div>
				<div class="form-group">
					<div class="col-xs-7">
						<c:forEach items="${bodyPlantCapability}" var="bodyPlantCapability">
							<input type="hidden" class="values" value="${bodyPlantCapability.attributeValuesMap}" />
							<c:forEach items="${bodyPlantCapability.attributeValuesMap}" var="attributeValue">
								<ul class="attribute-values">
									<li class="attribute-values-display">
										<c:if test="${attributeValue.value}">
											<input type="checkbox" name="type" class="attibute-values-checkbox" value="${attributeValue.key}" id="attribute-values" />${attributeValue.key}
										</c:if> 
										<c:if test="${!attributeValue.value}">
											<input type="checkbox" class="disallow-attibute-values-checkbox" value="${attributeValue.key}" id="attribute-values" checked />${attributeValue.key}
										</c:if>
									</li>
								</ul>
							</c:forEach>
						</c:forEach>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="save-attribute">
			<a class="buttonPrimary buttonDisabled floatRight" id="update-capability" data-save-attr-id="${attributeId}">Save</a>
		</div>
	</div>
</div>

<script	src="${baseUrl}/js/admin-console/oem-build-matrix/edit-dimension.js" type="text/javascript"></script>
