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
			<h1>Proximity Configuration - MORGAN - PORTLAND, OR</h1>
			<div class="leftpane">
				<h2>Tier 1</h2>
				<div>
					Filter <input id="tier1" class="filter" data-alf="#list1" type="text" placeholder="" />
					<ul id="list1" class="filterable">
						<c:forEach items="${districtProximityList}" var="districtProximity">
							<c:choose>
								<c:when test="${districtProximity.tier == 1}">
									<li><label> ${districtProximity.area}</label>
										<ul>
											<fieldset class="fieldset">
												<legend class="legend-view"></legend>
												<%-- <c:forEach items="${districtProximityList}" var="districtProximity">
										<li><input type="checkbox" value="1" checked="checked">${districtProximity.districtValues}</li>
										</c:forEach> --%>
											</fieldset>
										</ul></li>
								</c:when>
							</c:choose>
						</c:forEach>
					</ul>
				</div>
				<div class="vl"></div>
			</div>
			<div class="middlepane">
				<h2>Tier 2</h2>
				<div>
					Filter <input id="tier2" class="filter" data-alf="#list2" type="text" placeholder="">
				</div>
				<ul id="list2" class="filterable">
					<c:forEach items="${districtProximityList}" var="districtProximity">
						<c:choose>
							<c:when test="${districtProximity.tier == 2}">
								<li><label> ${districtProximity.area}</label>
									<ul>
										<fieldset class="fieldset">
											<legend class="legend-view"></legend>
										</fieldset>
									</ul></li>
							</c:when>
						</c:choose>
					</c:forEach>
				</ul>
				<div class="vl2"></div>
			</div>
			<div class="rightpane">
				<h2>Tier 3</h2>
				<div>
					Filter <input id="tier3" class="filter" data-alf="#list3" type="text" placeholder="">
				</div>
				<ul id="list3" class="filterable">
					<c:forEach items="${districtProximityList}" var="districtProximity">
						<c:choose>
							<c:when test="${districtProximity.tier == 3}">
								<li><label> ${districtProximity.area}</label>
									<ul>
										<fieldset class="fieldset">
											<legend class="legend-view"></legend>
										</fieldset>
									</ul></li>
							</c:when>
						</c:choose>
					</c:forEach>
				</ul>
				<script>
					$("fieldset").selectAll({
						buttonParent : "legend",
						buttonWrapperHTML : "",
				
						buttonSelectBeforeHTML : "<span class='ui-icon ui-icon-check' id='check-uncheck-display'></span>",
						buttonSelectText : "Check All",
						buttonSelectAfterHTML : "",
				
						buttonDeSelectBeforeHTML : "<span class='ui-icon ui-icon-closethick' id='check-uncheck-display'></span>",
						buttonDeSelectText : "Uncheck All",
						buttonDeSelectAfterHTML : "",
				
						buttonExtraClasses : "btn btn-default"
					});
				</script>
				<script>
					$('.filter').accordionLiveFilter();
				</script>
			</div>
			<div class="save-proximity">
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