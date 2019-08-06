<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="${baseUrl}/css/admin-console/app-config/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/dataTables.responsive.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/common.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/v2/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/global.css" rel="stylesheet" type="text/css"/>
<!--[if IE ]>
  <link href="${baseUrl}/css/global/ie.css" rel="stylesheet" type="text/css"/>
<![endif]-->

<%-- Most scripts get put in the footer of the page, but the main jQuery script has to be in the header, since the $ is very occasionally referenced in page bodies. (ex: $.parseJSON()) --%>
<script src="${baseUrl}/js/v2/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
  sessionStorage.setItem('commonStaticUrl', '${commonStaticUrl}');
  sessionStorage.setItem('baseUrl', '${baseUrl}');
  sessionStorage.setItem('baseAppUrl', '${baseAppUrl}');
</script>