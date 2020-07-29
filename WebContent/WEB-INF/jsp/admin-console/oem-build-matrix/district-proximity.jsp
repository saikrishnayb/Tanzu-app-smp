<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>OEM Build Matrix</title>
	
	<%@ include file="../../global/v2/header.jsp"%>
	<link href="${baseUrl}/css/admin-console/oem-build-matrix/district-proximity.css" rel="stylesheet" type="text/css" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<%@ include file="../../global/navigation/sub-nav.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../global/navigation/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<%@ include file="../../global/v2/page-error-container.jsp"%>
			<span class="floatRight addRow push-right"> </span>
			<h1>Proximity Configuration - ${plantData.plantName} - ${plantData.city}, ${plantData.state}</h1>
			<input type="hidden" id="plantId" value="${plantData.plantId}">
			<c:choose>
				<c:when test="${isDataAvailable eq true}">
					<div class="tier-main-div">
						<c:forEach items="${proximityModel.tiers}" var="tierModel" varStatus="theCount">
							<c:set var="tier" value="${tierModel.tier}" />
							
							<div class="tier-div ${theCount.index < 2 ? 'vertical-line' : ''}" id="tier${tier}-div">
								<div class="tier-header">
									<h2 id="tier${tier}-header">Tier ${tier}</h2>
								</div>
								<div class="tier-filter-div" id="tier${tier}-filter-div">
									Filter <input id="tier${tier}" class="filter" data-alf="#list${tier}" type="text" placeholder="" />
									<ul id="list${tier}" class="filterable">
										<c:forEach items="${tierModel.areas}" var="areaModel">
											<c:set var="area" value="${areaModel.freightMileage.area}" />
											<c:set var="areaDesc" value="${areaModel.freightMileage.areaDesc}"></c:set>
											<li>
												<c:set var="hasproximity" value="N"/>
												<c:forEach items="${areaModel.proximities}" var="entry">
													<c:set var="proximityData" value="${entry.value}" />
													<c:if test="${not empty proximityData}">
														<c:set var="hasproximity" value="Y"/>
													</c:if>
												</c:forEach>
												<label class="<c:if test="${hasproximity=='Y'}">hasProximity</c:if> dist-proximity-lbl">${area} - ${areaDesc}</label>
												<ul class="list${tier}-subtier">
													<fieldset class="fieldset">
														<legend class="legend-view"></legend>
														<c:forEach items="${areaModel.proximities}" var="entry">
															<c:set var="district" value="${entry.key}" />
															<c:set var="proximity" value="${entry.value}" />
															<li class="district-values">
																<input type="checkbox" class="district-checkbox" value="${district}" area="${area}" tier="${tier}"
																	<c:if test="${not empty proximity}">checked="checked" proximityId="${proximity.proximityId}"</c:if>
																/>
																${district}
														</li>
														</c:forEach>
													</fieldset>
												</ul>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</c:forEach>
					</div>
					 

					<div class="save-proximity">
						<a href="${baseAppUrl}/admin-console/oem-build-matrix/maintenance-summary.htm" class="buttonSecondary">Cancel</a>
						<a id="save-proximitybtn" class="buttonPrimary buttonDisabled">Save</a>
					</div>

				</c:when>
				<c:otherwise>
					<div>No data found</div>
					<div class="cancel-button-container">
						<a href="${baseAppUrl}/admin-console/oem-build-matrix/maintenance-summary.htm" class="buttonSecondary">Cancel</a>
					</div>
				</c:otherwise>
			</c:choose>
			<form id="district-proximity-form" name="districtProximityForm" data-plant-id="${plantData.plantId}" method="POST" action="${baseAppUrl}/admin-console/oem-build-matrix/save-district-proximity"></form>
		</div>
	</div>

	<%@ include file="../../global/v2/footer.jsp" %>
	<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery.accordion-live-filter.js"></script>
	<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery.selectall.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/js/admin-console/oem-build-matrix/district-proximity.js"></script>
</body>
</html>