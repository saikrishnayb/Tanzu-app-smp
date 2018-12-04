<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Subject Management</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    <link href="${baseUrl}/css/admin-console/app-config/subject-management.css" rel="stylesheet" type="text/css"/>
		<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				<!-- Subject Table -->
				<table id="subject-table">
					<thead>
						<tr>
							<th class="viewCol"></th>
							<th>Subject</th>
							<th>Department</th>
							<th>Type</th>
							<th>Active</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="subject" items="${subjects}">
						<tr>
							<td class="editable centerAlign">
								<a class="rightMargin edit-subject">Edit</a>
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin handCursor delete-subject"/>
								<input name="subjectId" type="hidden" value="${subject.subjectId}" />
							</td>
							<td><span id="subject-name">${subject.subjectName}</span></td>
							<td>
								<input name="department" type="hidden" value="${subject.department}" />
								<c:if test="${subject.department eq 0}"><span class="department-span">Vehicle Planning</span></c:if>
								<c:if test="${subject.department eq 1}"><span class="department-span">Vehicle Supply</span></c:if>
							</td>
							<td>
								<input name="type" type="hidden" value="${subject.type}" />
								<c:if test="${subject.type eq 0}"><span class="type-span">Info Request</span></c:if>
								<c:if test="${subject.type eq 1}"><span class="type-span">Change Request</span></c:if>
							</td>
							<td>
								<input name="status" type="hidden" value="${subject.status}" />
								<c:if test="${subject.status eq 0}"><span class="status-span">No</span></c:if>
								<c:if test="${subject.status eq 1}"><span class="status-span">Yes</span></c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<span class="floatRight addRow">
					<a class="add-subject">Create Subject</a>
					<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor add-subject" alt="Add Row"/>
				</span>
				
				<!-- Edit Subject Modal -->
				<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/edit-subject-modal.jsp" %>
				
				<!-- Add Subject Modal -->
				<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/add-subject-modal.jsp" %>
			</div>
		</div> 
		
		<%@ include file="../../../jsp/jsp-fragment/global/footer.jsp" %>
	</body>
	<!-- Scripts -->
	<script src="${baseUrl}/js/admin-console/app-config/subject-management.js" type="text/javascript"></script>
</html>