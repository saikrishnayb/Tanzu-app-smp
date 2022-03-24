<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- ***** Stylesheets ***** -->
<link href="${baseUrl}/css/global/v2/jquery/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/jquery/jquery.scroller.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/jquery/dataTables.responsive.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/jquery/fixedColumns.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/jquery/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/global/v2/jquery/jquery.multiselect.filter.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/css/global/v2/bootstrap/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/bootstrap/bootstrap-overwrites.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/common.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/global.css" rel="stylesheet" type="text/css"/>


<%-- Most scripts get put in the footer of the page, but the main jQuery script has to be in the header, since the $ is very occasionally referenced in page bodies. (ex: $.parseJSON()) --%>
<script src="${baseUrl}/js/global/v2/jquery/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
	sessionStorage.setItem('commonStaticUrl', '${commonStaticUrl}');
	sessionStorage.setItem('baseUrl', '${baseUrl}');
	sessionStorage.setItem('baseAppUrl', '${baseAppUrl}');
	sessionStorage.setItem('baseAdminConsoleUrl', '${baseAdminConsoleUrl}');
	sessionStorage.setItem('baseBuildMatrixUrl', '${baseBuildMatrixUrl}');
	sessionStorage.setItem('supportNum', '${supportNum}');
</script>