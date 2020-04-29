<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Edit Dimension</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="pragma" content="no-cache" />
<script src="${baseUrl}/js/admin-console/oem-build-matrix/edit-dimension.js" type="text/javascript"></script>
<link href="${baseUrl}/css/admin-console/oem-build-matrix/attribute-maintenance.css" rel="stylesheet" type="text/css" />
<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="attribute-view">
		<div class="dimension-class">
			<label>Select all which are applicable</label>
		</div>
		<table id="attribute-view">
	    	<tbody>
	    	<tr>
	        <td id="tdata1"></td>
	        <td id="tdata2">
	        <c:set var="contains" value="false" />
				<c:forEach items="${bodyPlantCapability.dimensionValues}" var="dimension">
					<c:forEach var="item" items="${bodyPlantCapability.selectedDimensions}">
						<c:if test="${item eq dimension}">
							<c:set var="contains" value="true" />
						</c:if>
					</c:forEach>
					<input type="checkbox" id="checkbox-display" class="dimensionSelect" 
						name="dimension" value="${dimension }" <c:if test="contains == true">checked</c:if> />
					${dimension}
					<br />
				</c:forEach>
	        </td>
	        <td id="tdata2"></td>
	   		</tr>
			</tbody>
		</table>
	</div>
	<div class="save-update">
		<c:choose>
					<c:when test="${editPopup eq true}">
					<a class="buttonPrimary" id="update-dimension" onclick="">Update</a>
					</c:when>
					<c:otherwise>
					<a class="buttonPrimary create-dimension"  onclick="">Save</a>
					</c:otherwise>
		</c:choose>
	</div>
</body>
</html>