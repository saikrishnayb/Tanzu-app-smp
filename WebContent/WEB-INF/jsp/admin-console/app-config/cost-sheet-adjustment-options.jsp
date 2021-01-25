<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
		<title>Cost Sheet Adjustment Options</title>
		<%@ include file="../../../jsp/global/v1/header.jsp" %>

		<link href="${baseUrl}/css/admin-console/app-config/cost-sheet-adjustment-options.css" rel="stylesheet" type="text/css"/>
	</head>

	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>

			<div class="leftNavAdjacentContainer">
				<div class="full-width">
					<h1>Cost Sheet Adjustment Options</h1>
					<table id="optionTable">
						<thead>
							<tr>
								<th class="viewCol"></th>
								<th>Order Code</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="option" items="${adjustmentOptions}">
							<tr class="option-row">
								<td class="editable centerAlign">
									<a class="rightMargin edit-option">Edit</a>
									<a href="javascript:void(0)" >
										<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/>
									</a>
									<input class="option-id" type="hidden" value="${option.optionId}"/>
								</td>
								<td>${option.orderCode}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>

					<div id="optionModal" class="modal"></div>
				</div>
			</div>
		</div> 
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/cost-sheet-adjustment-options.js" type="text/javascript"></script>
	</body>
</html>