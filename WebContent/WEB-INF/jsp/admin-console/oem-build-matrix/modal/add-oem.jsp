<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Add OEM</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="pragma" content="no-cache" />

<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/global/v2/jquery.multiselect.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="add-oem">
		<div class="add-oem-class">
			<label>PO Category: </label>
		</div>
		<div class="add-oem-class">
			<select id="po-category-drpdown" name="po-category-drpdown" class="dropdown-view">
						<option value="">Select</option>
						<c:forEach items="${poCategoryList}" var="poCategory">
							<option value="${poCategory}" >${poCategory}</option>
						</c:forEach>
			</select>
		</div>
	</div>
	<div class="add-oem">
		<div class="add-oem-class">
			<label>OEM Name: </label>
		</div>
		<div class="add-oem-class">
			<select id="oem-name-drpdown" name="oem-name-drpdown" class="dropdown-view" multiple>
						<option value="">Select</option>
						<c:forEach items="${oemList}" var="oemName">
							<option value="${oemName}" >${oemName}</option>
						</c:forEach>
			</select>
		</div>
	</div>	
	<div class="save-or-update">
		<c:choose>
					<c:when test="${editPopup eq true}">
					<a class="buttonPrimary" id="update-dimension" href="#" onclick="">Update</a>
					</c:when>
					<c:otherwise>
					<a class="buttonPrimary" id="create-dimension" href="#" onclick="">Save</a>
					</c:otherwise>
		</c:choose>
	</div>
	<script src="${baseUrl}/js/admin-console/oem-build-matrix/add-oem.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/v2/jquery.multiselect.js" type="text/javascript"></script>
</body>
</html>