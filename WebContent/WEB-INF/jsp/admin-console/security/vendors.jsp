<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    <link href="${baseUrl}/css/admin-console/security/vendors.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<!-- Advanced Search -->
				<span id="advanced-search" class="expandableContent handCursor 
				<c:if test="${hasBeenSearched eq false}">collapsedImage</c:if>
				<c:if test="${hasBeenSearched eq true}">expandedImage</c:if>
				 floatRight margin-bottom-10"
				onclick="toggleContent('search-content','advanced-search');">Show Search Criteria</span>
				
				<div id="search-content" class="<c:if test="${hasBeenSearched eq false}">displayNone</c:if> 
				<c:if test="${hasBeenSearched eq true}">displayBlock</c:if> clear-both">
					<form id="advanced-search-form" method="get" action="./vendors-advanced-search.htm">
						<fieldset style="width: 765px;">
							<legend>Advanced Search</legend>
							<div class="form-column floatLeft margin-bottom-10">
								<label class="floatLeft clear-left leftLbl">Vendor Name</label>
								<input class="floatLeft" name="vendorName" tabindex=1 type="text" value="<c:out value="${searchedVendor.vendorName}"/>" autocomplete="off" maxlength="50" />
								
								<label class="floatLeft clear-left leftLbl">Vendor Number</label>
								<input class="floatLeft" id="vendorNumber" tabindex=3 name="vendorNumber" maxlength="9" type="text" <c:if test="${searchedVendor.vendorNumber ne 0}">value="<c:out value="${searchedVendor.vendorNumber}"/>"</c:if> autocomplete="off" />
								
								<label class="floatLeft clear-left leftLbl">Corp Code</label>
								<input class="floatLeft" name="corpCode" tabindex=5 type="text" value="<c:out value="${searchedVendor.corpCode}"/>" autocomplete="off" maxlength="4" />
								
								<label class="floatLeft clear-left leftLbl">MFR Code</label>
								<input class="floatLeft" tabindex=8 name="searchMfrCode" type="text" value="<c:out value="${searchedVendor.searchMfrCode}"/>" autocomplete="off" maxlength="4" />
								<c:if test="${isPenskeUser}">
									<label class="floatLeft clear-left leftLbl" >Alerts</label>
									<select class="floatLeft" tabindex=9 name="alertType">
										<option value="0">Select...</option>
										<c:forEach var="alert" items="${alertTypeList}">
										<option value="${alert.alertType}" <c:if test="${searchedVendor.alertType eq alert.alertType}">selected</c:if>>${alert.alertName}</option>
										</c:forEach>
									</select>
								</c:if>
							</div>
							
							<div class="form-column floatLeft margin-bottom-10">
								<label class="floatLeft clear-left rlbl">Notification Exception</label>
								<select class="floatLeft" tabindex=2 name="notificationException">
									<option value="-1">Select...</option>
									<option value="Y" <c:if test="${searchedVendor.notificationException eq 'Y'}">selected</c:if>>Yes</option>
									<option value="N" <c:if test="${searchedVendor.notificationException eq 'N'}">selected</c:if>>No</option>
								</select>
								
								<label class="floatLeft clear-left rlbl">Annual Agreement</label>
								<select class="floatLeft" tabindex=4 name="annualAgreement">
									<option value="-1">Select...</option>
									<option value="Y" <c:if test="${searchedVendor.annualAgreement eq 'Y'}">selected</c:if>>Yes</option>
									<option value="N" <c:if test="${searchedVendor.annualAgreement eq 'N'}">selected</c:if>>No</option>
								</select>
								
								<label class="floatLeft clear-left rlbl">Planning Analyst</label>
								<select class="floatLeft" tabindex=7 name="planningAnalyst.userId">
									<option value="0">Select...</option>
									<c:forEach var="analyst" items="${analysts}">
									<option value="${analyst.userId}" <c:if test="${searchedVendor.planningAnalyst.userId eq analyst.userId}">selected</c:if>>${analyst.firstName} ${analyst.lastName}</option>
									</c:forEach>
								</select>
								
								<label class="floatLeft clear-left rlbl">Supply Specialist</label>
								<select class="floatLeft" tabindex=8 name="supplySpecialist.userId">
									<option value="0">Select...</option>
									<c:forEach var="specialist" items="${specialists}">
									<option value="${specialist.userId}" <c:if test="${searchedVendor.supplySpecialist.userId eq specialist.userId}">selected</c:if>>${specialist.firstName} ${specialist.lastName}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						
						<div class="floatRight button-div clear-both" id="search-buttons-div">
							<a class="buttonPrimary floatRight search" tabindex=11>Search</a>
							<a class="secondaryLink floatRight reset" tabindex=10>Reset</a>
							<div class="error floatRight hidden">
								<img src="${commonStaticUrl}/images/warning.png">
								<span class="errorMsg"></span>
							</div>
						</div>
					</form>
				</div>
				
				<!-- Vendor Table -->
				<table id="vendor-table">
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Corp Code</th>
							<th>Vendor Name</th>
							<th>Vendor Id</th>
							<th>MFR</th>
							<th>Annual Agreement</th>
							<th>Primary Contact</th>
							<th>Contact Phone</th>
							<th>Assigned Analyst</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="vendor" items="${vendors}">
						<tr>
							<td class="editable centerAlign">
								<input class="update-checkbox" type="checkbox" />
								<a class="rightMargin edit-vendor">Edit</a>
								<a class="rightMargin view-vendor">View</a>
								<input type="hidden" name="vendorId" value="${vendor.vendorId}" />
								<input type="hidden" name="notificationException" value="${vendor.notificationException}" />
								<input type="hidden" name="supplySpecialist" value="${vendor.supplySpecialist.userId}" />
							</td>
							<td class="corp-code">${vendor.corpCode}</td>
							<td class="vendor-name">${vendor.vendorName}</td>
							<td class="vendor-number">${vendor.vendorNumber}</td>
							<td class="mfr-code">
                <c:forEach items="${vendor.mfrCodes}" var="mfrCode" varStatus="loopTagStatus">
                  ${mfrCode} 
                  
                  <c:if test="${fn:length(vendor.mfrCodes) != loopTagStatus.count}">
                    <br>
                  </c:if>
                </c:forEach>
              </td>
							<td class="annual-agreement">
								<c:if test="${vendor.annualAgreement eq 'Y'}">Yes</c:if>
								<c:if test="${vendor.annualAgreement eq 'N'}">No</c:if>
							</td>
							<td class="primary-contact">${vendor.primaryContact.firstName} ${vendor.primaryContact.lastName}</td>
							<td class="contact-phone">${vendor.primaryContact.phoneNumber}</td>
							<td class="planning-analyst">
								<input name="planningAnalyst" type="hidden" value="${vendor.planningAnalyst.userId}" />
								<span>${vendor.planningAnalyst.firstName} ${vendor.planningAnalyst.lastName}</span>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="button-div clear-both floatRight">
					<a id="mass-update" class="buttonPrimary floatRight margin-right">Update Selected</a>
					<div class="error floatRight hidden">
						<img src="${commonStaticUrl}/images/warning.png">
						<span class="errorMsg"></span>
					</div>
				</div>
			</div>
			
			<!-- Edit Vendor Modal -->
			<div id="edit-vendor-modal" class="modal"></div>
			
			<!-- View Vendor Modal -->
			<div id="view-vendor-modal" class="modal"></div>
			
			<!-- Mass Update Modal -->
			<div id="mass-update-modal" class="modal"></div>
		</div> 
		
	</body>
	<!-- Scripts -->
	<script src="${baseUrl}/js/admin-console/security/vendors.js" type="text/javascript"></script>
</html>