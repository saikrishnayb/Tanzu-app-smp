<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<head>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/modal/split-by-type.css" rel="stylesheet" type="text/css" />
</head>
<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="Define Body Split by Type" data-modal-width="650">
	<div class="row modal-body">
		<div class="error col-xs-12" id="errorMessage" style="display: none">
			<img src="${commonStaticUrl}/images/warning.png" /> 
			<span class="errorMsg"></span>
		</div>
		<div class="col-xs-12 body-split-container">
			<div class="row body-split-header-row">
				<div class="col-xs-2">&emsp;</div>
				<div class="col-xs-2">
					<label>Total</label>
				</div>
				<div class="col-xs-2">
					<label>Van</label>
				</div>
				<div class="col-xs-2">
					<label>Reefer</label>
				</div>
				<div class="col-xs-2">
					<label>Flatbed</label>
				</div>
				<div class="col-xs-2">&emsp;</div>
			</div>
			<c:choose>
				<c:when test="${!empty bodySplitsForRun}">
					<c:forEach items="${bodySplitsForRun}" var="entry" varStatus="bodySplitLoop">
						<c:set var="make" value="${entry.key}"/>
						<c:set var="bodySplit" value="${entry.value}"/>
						<div class="row body-split-input-row">
							<div class="col-xs-2">
								<select class="mfr-select">
									<option value=""></option>
									<c:forEach items="${bodyMakeAttribute.attributeValues}" var="buildAttributeValue" varStatus="buildAttributeLoop">
										<c:set var="bodyMake" value="${buildAttributeValue.attributeValue}" />
										<option value="${bodyMake}"
											<c:if test="${make eq bodyMake}"> selected</c:if>>${bodyMake}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-xs-2">
								<input type="text" class="total" disabled>
							</div>
							<div class="col-xs-2">
								<input type="text" class="van-qty split-unit-input numbers-only" value="${bodySplit.vanQty}">
							</div>
							<div class="col-xs-2">
								<input type="text" class="reefer-qty split-unit-input numbers-only" value="${bodySplit.reeferQty}">
							</div>
							<div class="col-xs-2">
								<input type="text" class="flatbed-qty split-unit-input numbers-only" value="${bodySplit.flatbedQty}">
							</div>
							<div class="col-xs-2">
								<c:choose>
									<c:when test="${!bodySplitLoop.last}">
										<a class="delete-body-split-row">
											<i class="fa fa-trash"></i>
										</a>
										<a class="add-body-split-row hidden">
											<i class="fa fa-plus-circle"></i>
										</a>
									</c:when>
									<c:otherwise>
										<a class="delete-body-split-row hidden">
											<i class="fa fa-trash"></i>
										</a>
										<a class="add-body-split-row">
											<i class="fa fa-plus-circle"></i>
										</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="row body-split-input-row">
						<div class="col-xs-2">
							<select class="mfr-select">
								<option value=""></option>
								<c:forEach items="${bodyMakeAttribute.attributeValues}" var="buildAttributeValue" varStatus="buildAttributeLoop">
									<c:set var="bodyMake" value="${buildAttributeValue.attributeValue}" />
									<option value="${bodyMake}">${bodyMake}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-2">
							<input type="text" class="total" disabled>
						</div>
						<div class="col-xs-2">
							<input type="text" class="van-qty split-unit-input numbers-only" value="">
						</div>
						<div class="col-xs-2">
							<input type="text" class="reefer-qty split-unit-input numbers-only" value="">
						</div>
						<div class="col-xs-2">
							<input type="text" class="flatbed-qty split-unit-input numbers-only" value="">
						</div>
						<div class="col-xs-2">
							<a class="delete-body-split-row hidden">
								<i class="fa fa-trash"></i>
							</a>
							<a class="add-body-split-row">
								<i class="fa fa-plus-circle"></i>
							</a>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="modal-footer">
		<div class="button-div">
			<a class="buttonPrimary buttonDisabled floatRight"
				id="save-split-by-type-btn" data-build-id="${buildId}"
				onclick="">Save</a>
		</div>
	</div>
	<form id="split-by-type-form" class="split-by-type-form" data-build-id="${buildId}"></form>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/split-by-type.js" type="text/javascript"></script>