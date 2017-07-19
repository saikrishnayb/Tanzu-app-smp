<!DOCTYPE html>
<html>
<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

</head>
<body>
	<form:form name="excelSeqCompForm" id="excelSeqCompForm" modelAttribute="templateComponents" method="POST">
	<form:input path="templateId" type="hidden"/>
	<div style="display:inline-block" class="floatRight">
	<div id="ErrorMsg" style="clear:both;margin-right: 235px;"
				class="floatLeft error-messages-container displayNone">
				<img src="${commonStaticUrl}/images/warning.png"></img> <span
					class="errorMsg">Error while updating template component sequences.</span>
	</div>
		<div class="floatRight" style="display:inline-block">
			<label>Search:  </label><input type="text" id="componentSearch" />
		</div>
	</div>
	<div id="table-wrapper">
	<div id="table-scroll">
	<table id="componetsTable" style="width:100%;height:250px;">
		<thead class="header fixedHeader">
			<tr>
				<th class="pointer-column" style="width:6%"></th>
				<th style="width:6%" >Seq</th>
				<th style="width:58%" >Component</th>
				<th style="width:30%">Component ID</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${templateComponents.components}" var="component" varStatus="cmpIndex">
				<tr >
					<td class="pointer" style="width:6%"> <span class="icon-bar"></span>
						  <span class="icon-bar"></span>
						  <span class="icon-bar"></span>
						  <form:input type="hidden" path="components[${cmpIndex.count -1}].componentSequence" class="seq" value=${component.componentSequence }/>
						  <form:input type="hidden" path="components[${cmpIndex.count -1}].componentId" class="componentId" value="${component.componentId}"/>
						  </td>
				    <td class="seq" style="width:6%">${component.componentSequence }</td>
					<td style="width:58%">${component.displayName }</td>
					<td style="width:30%">${component.componentId }</td>
					
				</tr>
			</c:forEach>
	
		</tbody>
	</table>

	</div>
	</div>
	<div class="floatRight">
				<a class="buttonPrimary" id="submitComponentSeq" style="margin-top:25px;" href="javascript:void(0)" onclick="submitComponentSeqForm();">Save</a>
	</div>
	</form:form>

	
	
</body>
</html>

