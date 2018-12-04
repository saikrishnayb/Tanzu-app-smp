<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- For Lame IE8 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<script src="${commonStaticUrl}/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${commonStaticUrl}/js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>
<link href="${commonStaticUrl}/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
<link href="${commonStaticUrl}/css/fixedMenu_style1.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/css/global/global.css" rel="stylesheet" type="text/css"/>

<script src="${commonStaticUrl}/js/jquery.fixedMenu.js" type="text/javascript"></script>
<script src="${commonStaticUrl}/js/common.js" type="text/javascript"></script>
<script src="${baseUrl}/js/global/global.js" type="text/javascript"></script>

<script type="text/javascript">
  sessionStorage.setItem('commonStaticUrl', '${commonStaticUrl}');
  sessionStorage.setItem('baseUrl', '${baseUrl}');
  sessionStorage.setItem('baseAppUrl', '${baseAppUrl}');
</script>

<!-- Link ie.css files here. -->
<!--[if IE ]>
	<link href="${baseUrl}/css/global/ie.css" rel="stylesheet" type="text/css"/>
<![endif]-->
<!--[if lt IE 9]>
	<script src="${baseUrl}/js/plugins/html5shiv.min.js" type="text/javascript"></script>
	<script src="${baseUrl}/js/plugins/respond.min.js" type="text/javascript"></script>
	
	<link href="${baseUrl}/css/global/ie8.css" rel="stylesheet" type="text/css"/>
<![endif]-->
