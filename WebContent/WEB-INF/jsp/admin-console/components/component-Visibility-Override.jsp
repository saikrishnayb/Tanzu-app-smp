<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    <script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	     <link href="${baseUrl}/css/admin-console/components/vendor-templates.css" rel="stylesheet" type="text/css"/> 
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
					
				<div class="table_div" >
						<span class="clear-both floatRight addRow">
							<!--  <a  class="add-template">Create Template</a>-->
							<a href="${baseUrl}/admin-console/components/create-modify-comp-visiblity-override-page.htm?isCreatePage=true&overrideId=0">Create Component Visibility Override
								<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
							</a>
						</span>
						<table  id="visiblity-override-table" >
							<thead>
								<tr>
									<th></th>
									<th>PO Category</th>
									<th>Dependent Component</th>
									<th>Override Types</th>
									<th>Controlling Component</th>
									<th>Controlling Value</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${overrideList}" var="overrideObj">
									<tr class="visiblity-override-row">
										<td class="editable centerAlign">
											<a class="rightMargin edit-visiblity-override">Edit</a>
											<a ><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-visiblity-override"/></a>
											<input  class="visiblity-override-id" type=hidden value="${overrideObj.visiblityOverrideId}"/>
										</td>
										<td>${overrideObj.poCategoryAssocName}</td>
										<td>${overrideObj.dependentComponentName}</td>
										<td>${overrideObj.overrideType}</td>
										<td>${overrideObj.controllingComponentName}</td>
										<td>${overrideObj.controllingValue}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="delete-visiblity-override-modal" class="delete-visiblity-override modal"></div>			
					
			</div>
		</div> 
		
	</body>
	<!-- Scripts -->
	<script src="${baseUrl}/js/admin-console/components/comp-visiblity-override.js" type="text/javascript"></script>
	
</html>