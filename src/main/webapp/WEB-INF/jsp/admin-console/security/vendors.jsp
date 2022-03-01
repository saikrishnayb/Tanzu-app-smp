<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
	    <link href="${baseUrl}/css/admin-console/security/vendors.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/security/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<tl:isAuthorized tabName="Admin Console" secFunction="EXPORT_VENDOR_ACTIVITY">
					<a id="export-vendor-activity" class="buttonSecondary" style="margin-bottom: 10px;">Export</a>
				</tl:isAuthorized>
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
								<input class="floatLeft" name="vendorName" type="text" value="<c:out value="${searchedVendor.vendorName}"/>" autocomplete="off" maxlength="50" />
								
								<label class="floatLeft clear-left leftLbl">Vendor Number</label>
								<input class="floatLeft" id="vendorNumber" name="vendorNumber" maxlength="9" type="text" <c:if test="${searchedVendor.vendorNumber ne 0}">value="<c:out value="${searchedVendor.vendorNumber}"/>"</c:if> autocomplete="off" />
								
								<label class="floatLeft clear-left leftLbl">Corp Code</label>
								<input class="floatLeft" name="corpCode"  type="text" value="<c:out value="${searchedVendor.corpCode}"/>" autocomplete="off" maxlength="4" />
								
								<label class="floatLeft clear-left leftLbl">MFR Code</label>
								<input class="floatLeft" name="searchMfrCode" type="text" value="<c:out value="${searchedVendor.searchMfrCode}"/>" autocomplete="off" maxlength="4" />
								<c:if test="${isPenskeUser}">
									<label class="floatLeft clear-left leftLbl" >Alerts</label>
									<select class="floatLeft"  name="alertType">
										<option value="0">Select...</option>
										<c:forEach var="alert" items="${alertTypeList}">
										<option value="${alert.alertType}" <c:if test="${searchedVendor.alertType eq alert.alertType}">selected</c:if>>${alert.alertName}</option>
										</c:forEach>
									</select>
								</c:if>
							</div>
							
							<div class="form-column floatLeft margin-bottom-10">
								<label class="floatLeft clear-left rlbl">Notification Exception</label>
								<select class="floatLeft"  name="notificationException">
									<option value="-1">Select...</option>
									<option value="Y" <c:if test="${searchedVendor.notificationException eq 'Y'}">selected</c:if>>Yes</option>
									<option value="N" <c:if test="${searchedVendor.notificationException eq 'N'}">selected</c:if>>No</option>
								</select>
								
								<label class="floatLeft clear-left rlbl">Annual Agreement</label>
								<select class="floatLeft" name="annualAgreement">
									<option value="-1">Select...</option>
									<option value="Y" <c:if test="${searchedVendor.annualAgreement eq 'Y'}">selected</c:if>>Yes</option>
									<option value="N" <c:if test="${searchedVendor.annualAgreement eq 'N'}">selected</c:if>>No</option>
								</select>
								
								<label class="floatLeft clear-left rlbl">Planning Analyst</label>
								<select class="floatLeft"  name="planningAnalyst.userId">
									<option value="0">Select...</option>
									<c:forEach var="analyst" items="${analysts}">
									<option value="${analyst.userId}" <c:if test="${searchedVendor.planningAnalyst.userId eq analyst.userId}">selected</c:if>>${analyst.firstName} ${analyst.lastName}</option>
									</c:forEach>
								</select>
								
								<label class="floatLeft clear-left rlbl">Supply Specialist</label>
								<select class="floatLeft" name="supplySpecialist.userId">
									<option value="0">Select...</option>
									<c:forEach var="specialist" items="${specialists}">
									<option value="${specialist.userId}" <c:if test="${searchedVendor.supplySpecialist.userId eq specialist.userId}">selected</c:if>>${specialist.firstName} ${specialist.lastName}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						
						<div class="floatRight button-div clear-both" id="search-buttons-div">
							<a class="buttonPrimary floatRight search" >Search</a>
							<a class="secondaryLink floatRight reset">Reset</a>
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
							<td class="vendor-name">
								<span class="vendor-address-expand-collapse">
									<img src="https://staticdev.penske.com/common/images/collapsed.png"
										data-collapsed-src="https://staticdev.penske.com/common/images/collapsed.png"
										data-expanded-src="https://staticdev.penske.com/common/images/expanded.png"
										class="vendor-address-icon va-collapsed" />
									${vendor.vendorName}
								</span>
								<br>
								<div class="vendor-address ui-helper-hidden">
									<label>Shipping Address</label><br>
									${vendor.shippingAddress1}<br>
									${vendor.shippingAddress2}<br>
									${vendor.shippingCity}, ${vendor.shippingState} ${vendor.shippingZipCode}	
								</div>
							</td>
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
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/security/vendors.js" type="text/javascript"></script>
	</body>
</html>