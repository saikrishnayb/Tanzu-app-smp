<!DOCTYPE html>
<html>
<head>
<title>SMC Loadsheet Sequencing</title>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include
	file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>

<link href="${context}/css/admin-console/app-config/create-loadsheet-sequence.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp"%>
		<div class="leftNavAdjacentContainer">
			<form:form id="loadsheet-sequencing-form" modelAttribute="seqMaster" method="POST">
			<form:input path="id" id="seqMasterId" type="hidden" />
			<form:input path="pageAction" id="pageAction" type="hidden" />
			<div>
			<div class="inputsDiv">
			<div class="floatLeft" style="width:20%">
				<div class="floatLeft">
					<h1>Loadsheet Sequencing</h1>
				</div>
			</div>
			<div class=floatLeft style="width:70%">
				<div class="rowClass">
					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<form:label path="name" for="ruleName">Name:</form:label>
						</div>
						<div class="inputClass">
							<form:input id="name" path="name" maxlength="50" type="text"  style="width:96%" />
						</div>
					</div>

					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<form:label path="description" for="description">Description:</form:label>
						</div>
						<div class="inputClass" >
							<form:input path="description" id="description" maxlength="100" type="text" style="width:96%"/>
						</div>
					</div>
					<div class="inputWidth">
						<div class="labelClass"></div>
						<div class="inputClass"></div>
					</div>
				</div>
				<div class="secondRowClass">
					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<form:label path="category" for="category">Category:</form:label>
						</div>
						<div class="inputClass">
							<form:select path="category" style="width:96%" id="categoryID">
								<form:option value=""></form:option>
								<c:forEach items="${categoriesList}" var="category">
									<form:option value="${category}">${category}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<form:label path="type" for="type">Type:</form:label>
						</div>
						<div class="inputClass">
							<form:select path="type" style="width:96%" id="typeID">
								<form:option value=""></form:option>
								<c:forEach items="${typesList}" var="type">
									<form:option value="${type}">${type}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<form:label path="oem" for="mfr">MFR:</form:label>
						</div>
						<div class="inputClass">
							<form:select path="oem" style="width:96%">
								<form:option value=""></form:option>
								<c:forEach items="${mfrList}" var="mfr">
									<form:option value="${fn:substring(mfr, 0, 3)}">${mfr}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>


			</div>

			<div class="floatRight" style="width:10%">
				<div class="floatRight">
					<a class="buttonSecondary" href="get-loadsheet-sequence.htm?categoryId=&category=${selectedCategoryinListPage}&type=${selectedTypeinListPage}&viewMode=${selectedViewMode}"
						onclick="javascript:loadProcessImage();">Back</a>
				</div>
			</div>
			</div>
			<div id="ErrorMsg" style="clear:both" class="floatLeft error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class="errorMsg"></span>
			</div>
			<div style="clear:both;margin-top:5px;">
				<hr class="noshade" />
			</div>

			<div id="ErrorMsg" style="clear:both"
				class="floatLeft error-messages-container displayNone">
				<img src="${commonStaticUrl}/images/warning.png"></img> <span
					class="errorMsg"></span>
			</div>
			</div>
			<div class="unitAssignmnet">
				<div id="assignedDiv">
					<div id="headerContainer">
						<div class="floatLeft">
							<h2>Assigned Components</h2>
						</div>
						<div class="floatRight">
						<c:if test="${seqMaster.pageAction ne 'VIEW'}">
						<div id="addGroup" class="floatLeft">
					    <a href="#" onclick='Addgroup()'>Add Group<img  class="leftPad centerImage handCursor adder rightMargin" src="https://staticdev.penske.com/common/images/add.png" alt="Add Criteria Group"></img></a>
						</div>
						</c:if>
						<div class="floatRight">
							<label>Search:  </label><input type="text" id="assignedSearch" />
						</div>
						</div>
					</div>
					<div>
					<div id="table-wrapper">
					<div id="table-scroll">
					<table id="assignedComponentsTable">
						<col style="width:10%"/>
						<col style="width:5%"/>
						<col style="width:7%"/>
						<col style="width:25%"/>
						<col style="width:25%"/>
						<col style="width:28%"/>
						<thead class="header fixedHeader">
							<tr>
								<th style="width:10%" class="pointer-column"></th>
								<th style="width:5%" class="delete-column"></th>
								<th style="width:7%">Seq</th>
								<th style="width:25%">Component Group</th>
								<th style="width:25%">Sub Group</th>
								<th style="width:28%">Component</th>
							</tr>
						</thead>
						<tbody id="hiddenHeader" class="displayNone">
							<tr ></tr>
						</tbody>
						<c:forEach items="${seqMaster.groupMasterList }" var="grpMasterData" varStatus="grpIndex">
						<tbody>
							<!-- Displaying the Header -->
							<tr class="group">
								<td style="width:10%" class="pointer"><span class="icon-bar"></span>
									<span class="icon-bar"></span> <span class="icon-bar"></span>
									<form:input type="hidden" id="formGroupSeq-${grpIndex.count -1  }"  path="groupMasterList[${grpIndex.count -1 }].displaySeq"/><form:input type="hidden" id="formGroupName-${grpIndex.count -1}" path="groupMasterList[${grpIndex.count -1}].name"/>
									<form:input type="hidden" path="groupMasterList[${grpIndex.count -1 }].grpMasterId"/>
									<form:input type="hidden" path="groupMasterList[${grpIndex.count -1 }].seqMasterId"/>
									</td>
								<td style="width:5%" class="editable centerAlign"></td>
								<td style="width:7%"></td>
								<td  colspan="3">
								<div>
								<div style="height:20px;" id="editgroup-${grpIndex.count- 1}" class="groupName floatLeft">${grpMasterData.name }</div> 
								<c:if test="${seqMaster.pageAction ne 'VIEW'}">
								<div id="edit-${grpIndex.count -1}" style="margin-right: 1%;" class="editGroup floatRight"><u> Edit </u></div>
								</c:if>
								</div>
								</td>
							</tr>
							<!-- Dislpaying the components assigned to group -->
							<c:forEach items="${grpMasterData.compGrpSeqList }" var="compData" varStatus="cmpIndex">
								<tr>
						          <td style="width:10%" class="pointer">
						            <span class="icon-bar"></span>
						            <span class="icon-bar"></span>
						            <span class="icon-bar"></span>
						            <form:input type="hidden" path="groupMasterList[${grpIndex.count -1}].compGrpSeqList[${cmpIndex.count -1 }].componentId" class="componentId" />
                      				<form:input type="hidden" path="groupMasterList[${grpIndex.count -1}].compGrpSeqList[${cmpIndex.count -1}].displaySeq" class="componentSequence" />
                      				<form:input type="hidden" path="groupMasterList[${grpIndex.count -1}].compGrpSeqList[${cmpIndex.count -1}].compGrpSeqId" class="compGrpSeqId" />
                      				<form:input type="hidden" path="groupMasterList[${grpIndex.count -1}].compGrpSeqList[${cmpIndex.count -1}].grpMasterId" class="grpMasterId" />
						          </td>
						          <td style="width:5%" class="editable centerAlign"><c:if test="${seqMaster.pageAction ne 'VIEW'}"><img class="rightMargin" id="deleteRow" src="https://staticdev.penske.com/common/images/delete.png"></c:if></td>
						          <td style="width:7%" class="centerAlign seq">${compData.displaySeq}</td>
						          <td style="width:25%">${compData.componentGroup}</td>
						          <td style="width:25%">${compData.subGroup}</td>
						          <td style="width:28%">${compData.componentName}</td>
						        </tr>
							</c:forEach>
							
						</tbody>
						</c:forEach>
						
					</table>
					</div>
					</div>
					</div>
				</div>
				<div id="unAssignedDiv">
					<div id="headerContainer">
					    
						<div class="floatLeft">
							<h2>Unassigned Components</h2>
						</div>
						
						<div class="floatRight">
							<label>Search:  </label><input type="text" id="unAssignedSearch" />
						</div>
					</div>
					
					<div>
					<div id="table-wrapper">
					<div id="table-scroll">
					<table id="unAssignedComponentsTable" class="display no-group" style="height:200px;">
						<col style="width:10%"/>
						<col style="width:30%"/>
						<col style="width:30%"/>
						<col style="width:30%"/>
						<thead class="header fixedHeader" >
							<tr>
								<th style="width:10%" class="pointer-column"></th>
								<th style="width:30%">Component Group</th>
								<th style="width:30%">Sub Group</th>
								<th style="width:30%">Component</th>
							</tr>
						</thead>
						<tbody>
						<tr id="hiddenRow" class="displayNone"></tr>
							<c:forEach items="${unassignedComponents }" var="component" varStatus="loopIndex">
						        <tr>
						          <td style="width:10%" class="pointer">
						            <span class="icon-bar"></span>
						            <span class="icon-bar"></span>
						            <span class="icon-bar"></span>
						            <input type="hidden" class="componentId" value="${component.componentId}">
                      				<input type="hidden" class="componentSequence" value="">
                      				<input type="hidden" class="compGrpSeqId" value="">
                      				<input type="hidden" class="grpMasterId" value="">
						          </td>
						          <td style="width:30%">${component.componentGroup}</td>
						          <td style="width:30%">${component.subGroup}</td>
						          <td style="width:30%">${component.componentName}</td>
						        </tr>
						      </c:forEach>
							
						</tbody>
					</table>
					</div>
					</div>
					</div>
				</div>
			</div> <!-- end of unit assignmnets div -->
			<c:if test="${seqMaster.pageAction ne 'VIEW'}">
			<div class="floatRight">
				<a class="buttonPrimary" style="margin-top:25px;" href="#" onclick="submitLoadSheetForm();">Save</a>
			</div>
			</c:if>
		</form:form>
		</div>
	</div>
	
	<input type="hidden" id="numberOfGroups" value="${fn:length(seqMaster.groupMasterList)}"/>
	<script src="${context}/js/v2/jquery-2.2.4.min.js"></script>
	<script src="${context}/js/v2/jquery-ui.min.js"></script>
	<script src="${context}/js/admin-console/app-config/create-loadsheet-sequence.js" type="text/javascript"></script>


</body>
</html>