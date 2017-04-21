<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>"> 
     
    <title>My JSP 'component-management.jsp' starting page</title>
    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 
  </head>
  
  <body>
  <%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
  <div id="mainContent" class="borderTop">
  <%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp" %>
  				
  				<!-- table -->
					<div class="table_div" >
											
						<table class="component_table" id="component-table" >
							<thead>
								<tr>
									<th></th>
									<th>Group</th>
									<th>Sub-Group</th>
									<th>Sub Component</th>
									<th>Visible</th>
								</tr>
							</thead>
							<tbody>
							
								<c:forEach items="${componentList}" var="componentItem" varStatus="count">
								<tr class="component-row">
									 <td>${componentItem.componentGroup}</td>
									 <td>${componentItem.subGroup}</td> 
									 <td>${componentItem.subComponentGroup}</td> 
									 <td>${componentItem.visible}</td> 
								</tr>
								</c:forEach>

							</tbody>
						</table>
						
					</div>
  
  </div>
    
  <input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
  
  
  
  </body>
  
  	<!-- Scripts -->
	<script src="${commonStaticUrl}/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>
	<script src="${commonStaticUrl}/js/common.js" type="text/javascript"></script>
		<!-- Scripts -->
	<script src="${context}/js/admin-console/components/component-management.js" type="text/javascript"></script>
</html>
