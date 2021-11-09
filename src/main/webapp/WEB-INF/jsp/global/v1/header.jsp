<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- For Lame IE8 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link href="${commonStaticUrl}/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
<link href="${commonStaticUrl}/css/fixedMenu_style1.css" rel="stylesheet" type="text/css"/>
<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>

<link href="${baseUrl}/css/global/v1/global.css" rel="stylesheet" type="text/css"/>

<%-- Most scripts get put in the footer of the page, but the main jQuery script has to be in the header, since the $ is very occasionally referenced in page bodies. (ex: $.parseJSON()) --%>
<script src="${commonStaticUrl}/js/jquery-1.7.2.min.js" type="text/javascript"></script>

<script type="text/javascript">
  sessionStorage.setItem('commonStaticUrl', '${commonStaticUrl}');
  sessionStorage.setItem('baseUrl', '${baseUrl}');
  sessionStorage.setItem('baseAppUrl', '${baseAppUrl}');
</script>