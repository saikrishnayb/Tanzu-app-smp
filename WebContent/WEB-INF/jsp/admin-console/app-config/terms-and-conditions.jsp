<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
	    <title>SMC Terms And Conditions</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/terms-and-conditions.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
			
				<div class="frequency-div">
					<span>Frequency to prompt Annual Agreement holders with Terms and Conditions affirmation dialog:</span>
					<span>
						<input type="text" id="t-and-c-frequency" class="number-text-box" value="${tandcFrequency}" />
					</span>
					<a class="buttonPrimary save-frequency" id="save-frequency" tabindex=0>Save</a>
					<span class="frequency-error">
						<img class="error-img" src="${commonStaticUrl}/images/warning.png" />
						<span class="error-message">Must Be a Positive Number</span>
					</span>
				</div>
				
				<!-- Terms and Conditions Table -->
				<table id="t-and-c-table">
					<thead>
						<tr>
							<th></th>
							<th>Version Number</th>
							<th>Start Date</th>
							<th>End Date</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tandcList}" var="tandc">
							<tr class="t-and-c-row">
								<td class="editable centerAlign">
									<a class="view-t-and-c" tabindex=0>View</a>
									<input type="hidden" value="${tandc.versionNumber}" class="tandc-version-number" />
								</td>
								<td class="version-number">${tandc.versionNumber}</td>
								<td class="start-date">${tandc.formattedStartDate}</td>
								<td class="end-date">${tandc.formattedEndDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<!-- Success/Fail Alerts -->
				<div class="form-submit-status-container">
					
				</div>
				
			</div>
		</div>
		
		<!-- Status Alert -->
		<div class="status-alerts">
			<div class="alert alert-success centerAlign hidden" role="alert">
				<h3>Successfully Saved!</h3>
			</div>
			<div class="alert alert-danger centerAlign hidden" role="alert">
				<h3>Save Failed</h3>
			</div>
		</div>
		
		<!-- View Terms and Conditions Modal -->
		<div id="tandc-view-modal" class="modal view-tandc-modal" title="Terms and Conditions">
			<div class="tandc"></div>
			<div class="ok-button-div">
				<a id="close-modal-tc" class="buttonPrimary centerAlign close-modal-tc">OK</a>
			</div>
		</div>
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/terms-and-conditions.js" type="text/javascript"></script>
	</body>
</html>