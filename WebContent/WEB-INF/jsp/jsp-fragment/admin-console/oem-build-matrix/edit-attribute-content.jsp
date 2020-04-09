<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<form id="editAttributeForm" class="editAttributeForm" save-attr-id="${attribute.attributeId}">
		<div class="error" id="errorMessage" style="display: none">
			<img src="${commonStaticUrl}/images/warning.png" /> 
			<span class="errorMsg"></span>
		</div>
		<input type="hidden" value="${attribute.attributeId}" class="attributeId" name="attributeId" /> <input type="hidden"
			value="${attribute.values}" class="attr-original-values">
		<input type="text" autofocus="autofocus" class="display-none">
		<c:choose>
			<c:when test="${editPopup eq true}">
				<div class="attribute-row">
					<div class="attribute-class">
						<label> Name:</label>
					</div>
					<div class="attibute-display">
						<label id="attributeName">${attribute.attributeName}</label>
					</div>
				</div>
				<div class="attribute-row">
					<div class="attribute-class">
						<label> Values:</label>
					</div>
					<div class="attribute-class">
						<select id="values" name="values" style="width:175px;" multiple onchange="enableUpdate()">
							<c:forEach items="${attribute.values}" var="attrValue">
								<option value="${attrValue}" selected>${attrValue}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="add-attribute-row">
					<div class="add-attribute-class">
						<label> Attribute</label>
					</div>
					<div class="attibute-display">
						<select id="attributeName" name="attributeName" class="attribute-name round-corner-text-box">
							<option value="attributeName">${attribute.attributeName}</option>
 						</select>
					</div>
				</div>
				<div class="add-attribute-class">
					<label> Attribute Value</label>
				</div>
				<div class="add-attribute-class">
					<input style="width: 173px;" class="attributeValue input alpha alpha-numeric" id="attributeValue" type="text"
						value="${attribute.attributeValue}" maxlength="20"/>
					<div id="ErrorMsg" class="error-text floatLeft error-messages-container displayNone">
						<img src="${commonStaticUrl}/images/warning.png"></img>
							<span class="errorMsg"></span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</form>
</div>
