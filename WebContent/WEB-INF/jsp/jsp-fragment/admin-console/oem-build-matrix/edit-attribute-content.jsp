<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row modal-body">
	<form id="editAttributeForm" class="editAttributeForm" data-save-attr-id="${buildAttribute.attributeId}">
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
					<div class="row">
						<div class="col-xs-5">
							<label>Attribute:</label>
						</div>
						<div class="col-xs-7">
							<label id="attributeName">${buildAttribute.attributeName}</label>
						</div>
					</div>
					<div class="row ">
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
					<div class="row">
						<div class="col-xs-5">
							<label> Attribute</label>
						</div>
						<div class="col-xs-7">
							<label id="attributeName">${buildAttribute.attributeName}</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-5">
							<label> Attribute Value</label>
						</div>
						<div class="col-xs-7">
							<input style="width: 173px;" class="attributeValue input alpha alpha-numeric" id="attributeValue" type="text"
								maxlength="20"/>
							<div id="ErrorMsg" class="error-text floatLeft error-messages-container displayNone">
								<img src="${commonStaticUrl}/images/warning.png"></img>
									<span class="errorMsg"></span>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</form>
</div>
