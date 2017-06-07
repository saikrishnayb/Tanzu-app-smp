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
							<label for="ruleName">Name:</label>
						</div>
						<div class="inputClass">
							<input name="ruleName" id="ruleName" maxlength="50" type="text"  style="width:96%" />
						</div>
					</div>

					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<label for="description">Description:</label>
						</div>
						<div class="inputClass" >
							<input name="description" id="description" maxlength="100" type="text" style="width:96%"/>
						</div>
					</div>
					<div class="inputWidth">
						<div class="labelClass"></div>
						<div class="inputClass"></div>
					</div>
				</div>
				<div class="rowClass">
					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<label for="category">Category:</label>
						</div>
						<div class="inputClass">
							<select style="width:96%">
								<option></option>
								<c:forEach items="${categoriesList}" var="category">
									<option value="${category}">${category}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<label for="type">Type:</label>
						</div>
						<div class="inputClass">
							<select style="width:96%">
								<option></option>
								<c:forEach items="${typesList}" var="type">
									<option value="${type}">${type}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="inputWidth">
						<div class="labelClass rightAlign">
							<label for="mfr">MFR:</label>
						</div>
						<div class="inputClass">
							<select style="width:96%">
								<option></option>
								<c:forEach items="${mfrList}" var="mfr">
									<option value="${mfr}">${mfr}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>


			</div>

			<div class="floatRight" style="width:10%">
				<div class="floatRight">
					<a class="buttonSecondary" href="loadsheet-sequence.htm"
						onclick="javascript:loadProcessImage();">Back</a>
				</div>
			</div>
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
						<div class="floatLeft" style="margin-left: 10%;">
					    <a href="#" onclick='Addgroup()'>Add Group<img  class="leftPad centerImage handCursor adder rightMargin" src="https://staticdev.penske.com/common/images/add.png" alt="Add Criteria Group"></img></a>
						</div>
						<div class="floatRight">
							<label>Search:  </label><input type="text" id="assignedSearch" />
						</div>
						
					</div>
					<div>
					<table id="assignedComponentsTable">
						<thead class="header">
							<tr>
								<th class="pointer-column"></th>
								<th class="delete-column"></th>
								<th>Seq</th>
								<th>Component Group</th>
								<th>Sub Group</th>
								<th>Component</th>
							</tr>
						</thead>
						<tbody>
							<tr class="group">
								<td colspan="1" class="pointer"><span class="icon-bar"></span>
									<span class="icon-bar"></span> <span class="icon-bar"></span></td>
								<td class="editable centerAlign"><img class="rightMargin" id="deleteGroup"
									src="https://staticdev.penske.com/common/images/delete.png"></td>
								<td></td>
								<td colspan="3"><div id="editgroup-0" class="groupName" style="float: left">GROUP NAME</div> <div id="edit-0" style="float: right;margin-right: 1%;" class="editGroup"> Edit</div></td>
							</tr>
							
						</tbody>
						
					</table>
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
					<table id="unAssignedComponentsTable" class="display no-group">
						<thead class="header">
							<tr>
								<th class="pointer-column"></th>
								<th>Component Group</th>
								<th>Sub Group</th>
								<th>Component</th>
							</tr>
						</thead>
						<tbody>
						<tr id="hiddenRow" class="displayNone"></tr>
							<c:forEach items="${componentDetailsList }" var="component" varStatus="loopIndex">
						        <tr>
						          <td class="pointer">
						            <span class="icon-bar"></span>
						            <span class="icon-bar"></span>
						            <span class="icon-bar"></span>
						          </td>
						          <td>${component.componentGroup}</td>
						          <td>${component.subGroup}</td>
						          <td>${component.componentName}</td>
						        </tr>
						      </c:forEach>
							
						</tbody>
					</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${context}/js/v2/jquery-2.2.4.min.js"></script>
	<script src="${context}/js/v2/jquery-ui.min.js"></script>
	<script src="${context}/js/admin-console/app-config/create-loadsheet-sequence.js" type="text/javascript"></script>


</body>
</html>