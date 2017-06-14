<!DOCTYPE html>
<html>
<head>
	<title>Loadsheet Sequence</title>
	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
	<link href="${context}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>
	<Script>
	var hostUrl='${context}';
	</Script>
</head>

<!-- ***************************deactivate modal********************************** -->
<div id="Sequence-modal" class="Sequence modal" title="Loadsheet Sequence">
</div>
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
		<input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
			<div class="full-width">
			<h1 style="display: inline-block;">Loadsheet Sequencing</h1>
			<!-- show back button only if the page is opened from load sheet management View/Set Load sheet links. -->
			<c:if test="${viewMode ne ''}"><a class="buttonSecondary floatRight clear-left back" href="loadsheet-management.htm">Back</a></c:if>
			<input id="selectedCategory" type="hidden" name="ruleCount"  value="${selectedCategory}"/>
	        <input type="hidden" id="selectedType" value="${selectedType}"/>
	        <input type="hidden" id="viewMode" value="${viewMode}"/>
			<select  id="categoryHideen"  onChange="getLoadsheetSequences()" style="display: none">
			        <option value=""></option>
					<c:forEach items="${categories}" var="category">
                   		<option value="${category}">${category}</option>
                   	</c:forEach>
			</select>
			<select id="typeHidden" onChange="getLoadsheetSequences()" style="display: none">
					<option value=""></option>
					<c:forEach items="${types}" var="type">
                   		<option value="${type}">${type}</option>
                   	</c:forEach>
			</select>
				
				<table id="sequence-table" >
					<thead>
						<tr>
							<th class="viewCol" style="width:9%"></th>
							<th>Loadsheet Sequence Name</th>
							<th>Description</th>
							<th>Category</th>
							<th>Type</th>
							<th>OEM</th>
							<th>Edit By</th>
							<th>Edit Date</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sequences}" var="sequence">
						<tr class="sequence-row">
							<td class="editable centerAlign">
							    <c:if test="${viewMode ne 'Y' }">
								<a class="rightMargin edit-sequence" href="open-edit-sequence.htm?seqMasterId=${sequence.id}&action=EDIT&category=${selectedCategory}&type=${selectedType}&viewMode=${viewMode}">Edit</a>
								<a class="rightMargin copy-sequence" onclick="processingImageAndTextHandler('visible','Loading data...');" href="open-edit-sequence.htm?seqMasterId=${sequence.id}&action=COPY&category=${selectedCategory}&type=${selectedType}&viewMode=${viewMode}">Copy</a>
								<img src="${commonStaticUrl}/images/delete.png" id="deleteSequence"  class="centerImage rightMargin deactivate"/>
								<input type="hidden"  id="sequenceId"  value="${sequence.id}"/>
								<input type="hidden" id="sequenceName" value="${sequence.name}"/>
								</c:if>
								<c:if test="${viewMode eq 'Y' }">
								<a class="rightMargin edit-sequence" onclick="processingImageAndTextHandler('visible','Loading data...');" href="open-edit-sequence.htm?seqMasterId=${sequence.id}&action=VIEW&category=${selectedCategory}&type=${selectedType}&viewMode=${viewMode}">View</a>
								</c:if>
							</td>
							<td>${sequence.name}</td>
							<td>${sequence.description}</td>
							<td>${sequence.category}</td>
							<td>${sequence.type}</td>
							<td>${sequence.oem}</td>
							<td>${sequence.editedBy}</td>
							<td>${sequence.fmtEditedDate}</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Delete Sequence Confirmation Popup -->
		<div id="confirmDeleteModal">
			<p id="deleteMessage"></p>
	
			<div style="position:absolute;bottom:3px;right:5px;">
				<a href="javascript:void(0)" class="secondaryLink" onclick="closeConfirmDialog();"tabIndex="-1">No</a> 
				<a href="javascript:void(0)" class="buttonPrimary" onclick="confirmDeleteSequence()" tabIndex="-1">Yes</a>
			</div>
		</div>
</body>
<!-- Scripts -->		
<script src="${context}/js/admin-console/app-config/loadsheet-sequence.js"	type="text/javascript"></script>
</html>