<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
		<title>Cost Sheet Tolerances</title>
		<%@ include file="../../../jsp/global/v1/header.jsp" %>

		<link href="${baseUrl}/css/admin-console/app-config/cost-sheet-tolerances.css" rel="stylesheet" type="text/css"/>
	</head>

	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>

			<div class="leftNavAdjacentContainer">
				<div class="full-width">
					<h1>Cost Sheet Tolerances</h1>
					<table id="toleranceTable">
						<thead>
							<tr>
								<th class="viewCol"></th>
								<th>PO Category</th>
								<th>Make</th>
								<th>Cost Tolerance</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="tolerance" items="${tolerances}">
							<tr class="tolerance-row">
								<td class="editable centerAlign">
									<a class="rightMargin edit-tolerance">Edit</a>
									<a href="javascript:void(0)" >
										<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/>
									</a>
									<input class="tolerance-id" type="hidden" value="${tolerance.toleranceId}"/>
								</td>
								<td><c:out value="${tolerance.poCategory.poCategoryName}" /></td>
								<td><c:out value="${tolerance.mfrCode}" /></td>
								<td>$<c:out value="${tolerance.tolerance}" /></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>

					<div id="toleranceModal" class="modal"></div>
				</div>
			</div>
		</div> 
		
		<%@ include file="../../../jsp/global/v1/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/cost-sheet-tolerances.js" type="text/javascript"></script>
	</body>
</html>