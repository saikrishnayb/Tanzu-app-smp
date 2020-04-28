<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="modalName" value='${editPopup eq true ? "Update Attribute Value" : "Add Attribute"}' />
<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="400">
	<div class="row modal-body">
		<form id="editAttributeForm" class="editAttributeForm form-horizontal" data-save-attr-id="${buildAttribute.attributeId}">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<input type="hidden" value="${buildAttribute.attributeId}" class="attributeId" name="attributeId" /> <input type="hidden"
				value="${buildAttribute.getAttributeValueList()}" class="attr-original-values">
			<input type="text" autofocus="autofocus" class="display-none">
			<div class="col-xs-12">
				<c:choose>
					<c:when test="${editPopup eq true}">
						<div class="form-group">
							<div class="col-xs-5">
								<label>Attribute:</label>
							</div>
							<div class="col-xs-7">
								<label id="attributeName">${buildAttribute.attributeName}</label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5">
								<label>Attribute Values:</label>
							</div>
							<div class="col-xs-7">
								<select id="values" name="values" style="width:175px;" multiple onchange="enableUpdate()">
									<c:forEach items="${buildAttribute.attributeValues}" var="attrValue">
										<option value="${attrValue.attributeValueId}" selected>${attrValue.attributeValue}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<div class="col-xs-5">
								<label> Attribute</label>
							</div>
							<div class="col-xs-7">
								<label id="attributeName">${buildAttribute.attributeName}</label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5">
								<label> Attribute Value</label>
							</div>
							<div class="col-xs-7">
								<input style="width: 173px;" class="attributeValue input alpha alpha-numeric" id="attributeValue" type="text"
									maxlength="20"/>
							</div>
						</div>
						<div class="form-group">
							<div id="ErrorMsg" class="col-xs-7 col-xs-offset-5 error-text floatLeft error-messages-container displayNone">
								<img src="${commonStaticUrl}/images/warning.png"></img>
								<span class="errorMsg"></span>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<c:choose>
			<c:when test="${editPopup eq true}">
				<div class="save-attribute">
					<a class="buttonPrimary buttonDisabled floatRight"
						id="update-attr" data-save-attr-id="${buildAttribute.attributeId}" href="#"
						onclick="">Save</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="save-attribute">
					<a class="buttonPrimary floatRight" id="create-attr"
						data-save-attr-id="${buildAttribute.attributeId}" href="#" onclick="">Save</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/edit-attribute.js" type="text/javascript"></script>	