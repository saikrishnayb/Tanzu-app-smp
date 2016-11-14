<!DOCTYPE html>
<html>
<head>
	<title>SMC Home</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
	<link href="${context}/css/admin-console/security/users.css" rel="stylesheet" type="text/css"/>
</head>

<!-- ******************************edit modal******************************** -->
<div id="edit-modal" class="modal edit-user-modal" title="Edit User Information"></div>

<!-- ***************************deactivate modal********************************** -->
<div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Account Deactivation">
</div>
<!-- *******************************user account table************************** -->
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/security/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
	       <span style="float: center;color: #CC0000" >
				You are not authorized to see this page. Please Contact Support for further assistance.
			</span> 
		</div>
	</div>
	<input type="hidden" id="tabNavUser" value="left-nav-vendor-users">
</body>
<!-- Scripts -->
<script src="${context}/js/admin-console/security/users.js" type="text/javascript"></script>
</html>