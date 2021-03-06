<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head> 
	    <title>Excel Uploads</title>
	    <%@ include file="../../../jsp/global/v1/header.jsp" %>
	    
		<link href="${baseUrl}/css/admin-console/app-config/dynamic-rules.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
		
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/global/navigation/admin-console/app-config/left-nav.jsp" %>

			<div class="leftNavAdjacentContainer">
				<c:choose>
					<c:when test="${access eq true}">
						
						<form action="./upload" method="POST" enctype="multipart/form-data" id="uploadForm">
							<div>
								<input type="radio" name="uploadSelect" value = "t"><label>Transport</label> 
								<br>
								<input type="radio" name="uploadSelect" value = "v"> <label>Vendor</label>
								<div class = 'errorMsg' id= 'radioBttnRequiredMsg' hidden>Please select a report type.</div>
							</div>
							<br>
							<br>
							<label for="sampleInput">
								<input id="fileName" class="fileInputTextbox" readonly="readonly" tabindex="-1"/>
							</label>	
										
							<span class="fileInputSpan">
								<a href="#" class="buttonSecondary fileInputButton">Browse</a>
								<input type="file" class="fileInputHidden" name="file" id="sampleInput"
								          onchange="javascript: document.getElementById('fileName').value = this.value" tabindex="-1"/>		
							</span>	
							
							<br><br>
							<a  id = "fileUploadSubmit" class="buttonPrimary" tabindex=0>Upload Excel</a>
							<c:if test = "${not empty transport_message}">
								<div>
									<br>
									<br>
									<label>${transport_message}</label>
								</div>		
							</c:if>	
						</form>
						
					</c:when>
					<c:otherwise>
				        <span style="float: center;color: #CC0000" >
							You are not authorized to see the Excel Upload page. Please Contact Support for further assistance.
						</span> 
				 	</c:otherwise>
				</c:choose>
			</div>
		</div>
	</body>
	<%@ include file="../../../jsp/global/v1/footer.jsp" %>
	<script src="${baseUrl}/js/admin-console/app-config/excel-uploads.js" type="text/javascript"></script>
</html>