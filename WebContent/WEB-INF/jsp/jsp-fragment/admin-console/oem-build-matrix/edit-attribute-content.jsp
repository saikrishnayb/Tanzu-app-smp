<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<form id="editAttributeForm" class="editAttributeForm">
			
	<div class="error" id="errorMessage" style="display: none">
		<img src="${commonStaticUrl}/images/warning.png" /> <span
		class="errorMsg"></span>
	</div>
	<input type="hidden" value="${attribute.attributeId}" class="attributeId" name="attributeId"/>
	<input type="hidden" value="${attribute.values}" class="attr-original-values">
	<div class="attribute-row">
		<div class="attribute-class">
			<label> Name:</label>
		</div>


		<div style="display: inline-block;">
			<c:choose>
			<c:when test="${editPopup eq true}">
				<label id="attributeName">${attribute.attributeName}</label>
			</c:when>
			<c:otherwise>
				<input  style="width: 173px;"  class="attributeName input alpha alpha-numeric round-corner-text-box" id="attributeName" type="text" value="${attribute.attributeName}"/>
			</c:otherwise>
			</c:choose>
		
		</div>


	</div>
	<div class="attribute-row">
		<div class="attribute-class">
			<label> Values:</label>
		</div>


		<div class="attribute-class">
			<c:choose> 
			<c:when test="${editPopup eq true}">
				<select id="values" name="values" style="width:175px;" multiple onchange="enableUpdate()">
					<c:forEach items="${attribute.values}" var="attrValue">
						<option value="${attrValue}" selected>${attrValue}</option>
					</c:forEach>
				</select>
			</c:when>
			<c:otherwise>
				<input style="width: 173px;" class="attributeValue input alpha alpha-numeric round-corner-text-box" id="attributeValue" type="text" value="${attribute.attributeValue}"/>
				<div id="ErrorMsg" class="floatLeft error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class="errorMsg"></span>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
	</div>	
		
	
	</form>
</div>