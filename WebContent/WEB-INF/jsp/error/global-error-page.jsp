<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <link href="${commonStaticUrl}/css/common.css" rel="stylesheet" type="text/css"/>      
    <link href="${baseUrl}/css/global/global.css" rel="stylesheet" type="text/css"/>
    <title>Global Error Page</title>
  </head>
  <body>
    <div id="mainContent">
      <p style="color: #CC0000"> 
        <span>${errorModel.message}</span>
        <br> 
        Please Contact Support @ 1-866-926-7240 for further assistance.
      </p>
    </div>
  </body>
</html>
