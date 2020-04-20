<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>
<html>
<head>
<title>OEM Build Matrix</title>
<%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
<script type="text/javascript" src="${baseUrl}/jQuery/jquery.accordion-live-filter.js"></script>
<script type="text/javascript" src="${baseUrl}/jQuery/jquery.selectall.min.js"></script>
<link href="${baseUrl}/css/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/global/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/jquery.multiselect.filter.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/admin-console/oem-build-matrix/district-proximity.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/admin-console/oem-build-matrix/build-matrix-global.css" rel="stylesheet" type="text/css" />
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body style="overflow-y:visible;">
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/oem-build-matrix/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<span class="floatRight addRow push-right"> </span>
			<div id="PopupError" style="display:none">
				<span class="errorMsg"> Hmm, something went wrong. See if you could try again. </span>
			</div>
			
			<h1>Proximity Configuration - ${plantName} - ${plantCity}, ${plantState}</h1>
			
			<div class="tier-main-div">
				<div class="tier-div vertical-line" id="tier1-div">
					<div class="tier-header"><h2 id="tier1-header">Tier 1</h2></div>
					<div class="tier-filter-div" id="tier1-filter-div">
						Filter <input id="tier1" class="filter" data-alf="#list1" type="text" placeholder="" />
						<ul id="list1" class="filterable">
							<c:forEach items="${freightMileageData}" var="freightMileage">
										<li>
											<label> ${freightMileage.area}</label>
											<ul>
												<fieldset class="fieldset">
													<legend class="legend-view"></legend>
													<c:forEach items="${freightMileage.districts}" var="district">
													<c:set var="contains" value="false" />
													<c:forEach items="${districtProximityList}" var="proximity">
														<c:if test="${proximity.district eq district and proximity.tier eq 1}">
															<c:set var="contains" value="true" />
														</c:if>
													</c:forEach>
														<li class="district-values"><input type="checkbox" value="${district}"  <c:if test="${contains eq true}">checked="checked"</c:if> >${district}</li>
													</c:forEach>
												</fieldset>
											</ul>
										</li>
							</c:forEach>
						</ul>
					</div>
				</div>
								
				<div class="tier-div vertical-line" id="tier2-div">
					<div class="tier-header"><h2 id="tier2-header">Tier 2</h2></div>
					<div class="tier-filter-div" id="tier2-filter-div">
						Filter <input id="tier2" class="filter" data-alf="#list2" type="text" placeholder="">
						<ul id="list2" class="filterable">
							<c:forEach items="${freightMileageData}" var="freightMileage">
										<li>
											<label> ${freightMileage.area}</label>
											<ul>
												<fieldset class="fieldset">
													<legend class="legend-view"></legend>
													<c:forEach items="${freightMileage.districts}" var="district">
													<c:set var="contains" value="false" />
													<c:forEach items="${districtProximityList}" var="proximity">
														<c:if test="${ proximity.tier eq 2 and proximity.district eq district}">
															<c:set var="contains" value="true" />
														</c:if>
													</c:forEach>
														<li class="district-values"><input type="checkbox" value="${district}" <c:if test="${contains eq true}">checked="checked"</c:if> >${district}</li>
													</c:forEach>
												</fieldset>
											</ul>
										</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				
				<div class="tier-div" id="tier3-div">
					<div class="tier-header"><h2 id="tier3-header">Tier 3</h2></div>
					<div class="tier-filter-div" id="tier3-filter-div">
						Filter <input id="tier3" class="filter" data-alf="#list3" type="text" placeholder="">
					
						<ul id="list3" class="filterable">
							<c:forEach items="${freightMileageData}" var="freightMileage">
										<li>
											<label> ${freightMileage.area}</label>
											<ul>
												<fieldset class="fieldset">
													<legend class="legend-view"></legend>
													<c:forEach items="${freightMileage.districts}" var="district">
														<c:set var="contains" value="false" />
														<c:forEach items="${districtProximityList}" var="proximity">
															<c:if test="${proximity.tier eq 3 and proximity.district eq district}">
																<c:set var="contains" value="true" />
															</c:if>
														</c:forEach>
														<li class="district-values"><input type="checkbox" value="${district}" <c:if test="${contains eq true}">checked= "checked"</c:if>>${district}</li>
													</c:forEach>
												</fieldset>
											</ul>
										</li>
							</c:forEach>
						</ul>
						<script>
							$("fieldset").selectAll({
								buttonParent : "legend",
								buttonWrapperHTML : "",
						
								buttonSelectBeforeHTML : "<span class='ui-icon ui-icon-check' id='check-uncheck-display'></span>",
								buttonSelectText : "",
								buttonSelectAfterHTML : "<a class='no-text-decoration' id='check-uncheck-display'>Check All</a>",
						
								buttonDeSelectBeforeHTML : "<span id='right-padding'></span><span class='ui-icon ui-icon-closethick' id='check-uncheck-display'></span>",
								buttonDeSelectText : "",
								buttonDeSelectAfterHTML : "<a class='no-text-decoration' id='check-uncheck-display'>Uncheck All</a>",
						
								buttonExtraClasses : ""
							});
						</script>
						<script>
							$('.filter').accordionLiveFilter();
						</script>
					</div>
				</div>
			</div>
			<div class="save-proximity">
					<a href="${baseAppUrl}/admin-console/oem-build-matrix/maintenance-summary.htm" onclick="javascript:loadProcessImage();" class="buttonSecondary round-corner-btn-cls">Cancel</a>
					<a href="#" class="buttonPrimary buttonDisabled round-corner-btn-cls" onClick="saveProximity()">Save</a>
		    </div>
		</div>
	</div>
	
	<%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
	<script type="text/javascript" src="${baseUrl}/jQuery/jquery.multiselect.js"></script>
	<script type="text/javascript" src="${baseUrl}/jQuery/jquery.multiselect.filter.js"></script>
	<script type="text/javascript" src="${baseUrl}/js/admin-console/oem-build-matrix/district-proximity.js"></script>
</body>
</html>