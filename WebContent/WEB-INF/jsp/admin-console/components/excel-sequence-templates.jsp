<!DOCTYPE html>
<html>
	<head> 
	   <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>  
	    	<!-- Scripts -->	
	    <%@ include file="../../../jsp/jsp-fragment/global/global-scripts.jsp" %>
	    <link href="${pageContext.request.contextPath}/css/admin-console/components/vendor-templates.css" rel="stylesheet" type="text/css"/> 	  	
		<link href="${context}/css/admin-console/app-config/create-loadsheet-sequence.css" rel="stylesheet" type="text/css" />
		<script src="${context}/js/admin-console/components/excel-sequence-templates.js" type="text/javascript"></script>
		
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
				
				<div class="excelSeqNamesClass">
					<div id="table-wrapper">
					<div id="table-scroll">
					<table id="excelSeqName-Table" style="width:100%;height:250px;">
						<thead class="header fixedHeader">
							<tr>
							<th class="leftAlign templateNameClass">Template</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="excelSeqTemplate" items="${excelSeqTemplates}" varStatus="counter">
								<c:if test="${counter.count%2!=0}">
									<c:set var="rowClass" value="odd" scope="page"></c:set>
								</c:if>
								<c:if test="${counter.count%2==0}">
									<c:set var="rowClass" value="even" scope="page"></c:set>
								</c:if>
								<tr class="${rowClass }">
									<td class="leftAlign templateNameClass" id=${excelSeqTemplate.templateID }>${excelSeqTemplate.templateName }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					</div>
				</div>
				<div class="displaySeqDetailsClass">
				
					<!-- Display please wait image -->
					<div id="showWait" class="displayNone">
						<fieldset class="fieldsetCustom">
							<img alt="please wait" class="waitClass" src="${context}/images/please-wait.png">
						</fieldset>
					</div>
					
					<div id="componentDetails" class="displayNone">
						
					</div>
				
				</div>
				
			</div>		
		</div> 
		
	<input type="hidden" id="common-url" value="${commonStaticUrl}"/>
	<script src="${context}/js/v2/jquery-2.2.4.min.js"></script>
	<script src="${context}/js/v2/jquery-ui.min.js"></script>
	</body>
</html>