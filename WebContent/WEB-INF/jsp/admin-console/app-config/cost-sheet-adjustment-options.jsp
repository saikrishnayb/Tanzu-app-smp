<!DOCTYPE html>
<html>
	<head> 
		<title>Cost Sheet Adjustment Options</title>
		<%@ include file="../../../jsp/global/v2/header.jsp" %>

		<link href="${baseUrl}/css/admin-console/app-config/cost-sheet-adjustment-options.css" rel="stylesheet" type="text/css"/>
	</head>

	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>

			<div class="leftNavAdjacentContainer">
				<%@ include file="../../global/v2/page-error-container.jsp"%>
				<div class="row">
					<div class="col-xs-12">
						<h1>Cost Sheet Adjustment Options</h1>
					</div>
				</div>
				<div class="row">

					<%@ include file="../app-config/fragment/cost-sheet-adjustment-option-table.jsp"%>

				</div>

				<div id="optionModal" class="modal row"></div>

			</div>
		</div> 
		
		<%@ include file="../../../jsp/global/v2/footer.jsp" %>
		<script src="${baseUrl}/js/admin-console/app-config/cost-sheet-adjustment-options.js" type="text/javascript"></script>
	</body>
</html>