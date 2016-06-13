<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fieldset id="user-fieldset" class="user-fieldset user-fieldset-height">
	<div class="org-form-container">
		<form id="template-form"   method="POST" <c:if test="${isCreatePage eq true}"> action="${pageContext.request.contextPath}/admin-console/security/create-template.htm" </c:if>
			<c:if test="${isCreatePage eq false}"> action="${pageContext.request.contextPath}/admin-console/security/update-template.htm" </c:if>> 
			<input id="template-id" name="templateID" type="text" class="displayNone" class="input" value="${editableTemplate.templateID}"/>
			<input type="hidden" id="is-create-page-id" value="${isCreatePage}" />
			<div id="org-components" class="org-component">
				<div class="single-line-content">
					<label for="poCatAssID">Cat/Sub Cat<span class=requiredField>*</span></label> 
					<select id="poCatAssID" name="poCatAssID" class="input numeric numeric-whole">
						<option value="">Select Cat/SubCat...</option>
						<c:forEach items="${allPoAssocList}" var="poAssocList">
						 <c:if test="${isCreatePage eq false}">
							<option value="${poAssocList.poCatAssocID}" <c:if test="${editableTemplate.poCatAssID eq poAssocList.poCatAssocID}">selected</c:if> >${poAssocList.poCatSubCatDesc}</option>
							</c:if>
						<c:if test="${isCreatePage eq true}">
								<option value="${poAssocList.poCatAssocID}">${poAssocList.poCatSubCatDesc}</option>
						</c:if>
						</c:forEach>
					</select>
				</div>
				<div id="org-id-div" class="single-line-content">
						<label for="templateDesc">Description <span class=requiredField>*</span></label> 
						<input id="templateDesc" name="templateDesc"  class="input alpha alpha-numeric"  maxlength="50" autocomplete="off"  type="text" <c:if test="${isCreatePage eq false}">value='${editableTemplate.templateDesc}'</c:if> />
				</div>
				<div class="full-width">
					<table id="template-Component-table" >
						<thead>
							<tr>
								<th class="viewCol"  style="display: none"></th> 
								<th>Component Name</th>
								<th>View</th>
								<th>Editable</th>
								<th>Required</th>
								<th>Disp Other PO</th>
								 <th>Excel</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allComponent}" var="comps" varStatus="status">
							<tr class="template-comp-row">
								<td class="template-comp" style="display: none">
								<div id="template-comp-tr-${status.index}" class="displayNone"><c:if test="${comps.viewable eq true}">1~~9</c:if></div>
								<input name="componentList['${status.index}'].componentId" id="componentId-${status.index}"  class="org-id" type=hidden value="${comps.componentId}"/>
								<input name="componentList['${status.index}'].fullName" id="componentName-${status.index}"  class="org-id" type=hidden value="${comps.fullName}"/>
								
								</td>
								<td class="template-comp">${comps.fullName}</td>
								<td><input type="checkbox" id="viewable-${status.index}" name="componentList['${status.index}'].viewable" 
								<c:if test="${comps.viewable eq true}">checked="checked"</c:if> /></td>
								<td><input type="checkbox" id="editable-${status.index}" name="componentList['${status.index}'].editable" 
								<c:if test="${comps.editable eq true}">checked="checked"</c:if> /></td>
								<td><input type="checkbox" id="required-${status.index}" name="componentList['${status.index}'].required"  
								<c:if test="${comps.required eq true}">checked="checked"</c:if> /></td>
								<td><input type="checkbox" id="dispOtherPO-${status.index}" name="componentList['${status.index}'].dispOtherPO" 
								<c:if test="${comps.dispOtherPO eq true}">checked="checked"</c:if>  
								<c:if test="${comps.viewable ne true}">  disabled </c:if> /></td>
								<td><input type="checkbox" id="excel-${status.index}" name="componentList['${status.index}'].excel" 
								<c:if test="${comps.excel eq true}">checked="checked"</c:if>
								<c:if test="${comps.viewable ne true}">  disabled </c:if> /></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>	
</fieldset>