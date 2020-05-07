<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="modalName" value="Edit Dimension" />
<div class="modal-content col-xs-12" data-modal-title="${modalName} - ${buildAttribute.attributeName}" data-modal-width="400">
	<div class="row modal-body">
		<form id="editAttributeForm" class="editAttributeForm form-horizontal data-save-attr-id="${buildAttribute.attributeId}">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<input type="hidden" value="${buildAttribute.attributeId}" class="attributeId" name="attributeId" /> <input type="hidden"
				value="${buildAttribute.getAttributeValueList()}"
				class="attr-original-values">
			<div class="col-xs-12">
				<div class="col-xs-10">
					<label class="attribute-values-lbl">Select all which are applicable</label>
				</div>
				<div class="form-group">
					<div class="col-xs-7">
						<c:forEach items="${buildAttribute.attributeValues}"
							var="attrValue">
							<ul class="attribute-values">
								<li class="attribute-values-display"><input type="checkbox"
									value="${attrValue.attributeValueId}" />${attrValue.attributeValue}</li>
							</ul>
						</c:forEach>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="save-attribute">
			<a class="buttonPrimary buttonDisabled floatRight" id="update-attr"
				data-save-attr-id="${buildAttribute.attributeId}" onclick="">Save</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/edit-dimension.js" type="text/javascript"></script>
