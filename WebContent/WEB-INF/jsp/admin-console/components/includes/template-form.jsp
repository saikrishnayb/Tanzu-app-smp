<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="org-form-container">
		<div id="component-rule-popup" ></div>
		<div id="component-associatedToRules-alert-popup" ></div>
		<form id="template-form"   method="POST" <c:if test="${isCreatePage eq true}"> action="${baseAppUrl}/admin-console/security/create-template.htm" </c:if>
			<c:if test="${isCreatePage eq false}"> action="${baseAppUrl}/admin-console/security/update-template.htm" </c:if>> 
			<input id="template-id" name="templateID" type="text" class="displayNone" class="input" value="${editableTemplate.templateID}"/>
			<input type="hidden" id="is-create-page-id" value="${isCreatePage}" />
			<input type="hidden" id="tempCompId" value="${tempCompId}" />
			<div id="org-components" class="org-component">
				<div class="single-line-content">
					<label for="poCatAssID">Cat/Sub Cat<span class=requiredField>*</span></label> 
          
					<select id="poCatAssID" tabindex=1 name="poCatAssID" class="input numeric numeric-whole" data-edit-page-mode="${isCreatePage eq false}">
						<option value="">Select Cat/SubCat...</option>
						<c:forEach items="${allPoAssocList}" var="poAssocList">
						 <c:if test="${isCreatePage eq false}">
							<option value="${poAssocList.poCatAssocID}" <c:if test="${editableTemplate.poCatAssID eq poAssocList.poCatAssocID}">selected</c:if> data-template-id="${poAssocList.templateId}">
                              ${poAssocList.poCatSubCatDesc}
                            </option>
							</c:if>
						<c:if test="${isCreatePage eq true}">
								<option value="${poAssocList.poCatAssocID}">${poAssocList.poCatSubCatDesc}</option>
						</c:if>
						</c:forEach>
					</select>
          
                      <img class="po-cat-ass-id-search" src="${commonStaticUrl}/images/search.png" alt="search" title="Search">
				</div>
				<div id="org-id-div" class="single-line-content">
						<label for="templateDesc">Description <span class=requiredField>*</span></label> 
						<input id="templateDesc" tabindex=2 name="templateDesc"  class="input alpha alpha-numeric"  maxlength="50" autocomplete="off"  type="text" <c:if test="${isCreatePage eq false}">value='${editableTemplate.templateDesc}'</c:if> />
				</div>
				<div class="full-width">
					<table id="template-Component-table" >
						<thead>
							<tr>
								<th class="viewCol"  style="display: none"></th> 
								<th>Component Name</th>
								<th>For Rules</th>
								<th>View</th>
								<th>Editable</th>
								<th>Required</th>
								<th>Disp Other PO</th>
								 <th>Excel</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allComponent}" var="comps" varStatus="status">
							<tr class="template-comp-row" id="component-${comps.templateComponentId}">
								<td class="template-comp" style="display: none"> 
				              	<div id="template-comp-tr-${status.index}" ><c:if test="${comps.forRules eq true}">hasSelected</c:if></div>
                                <div  id="componentWithRule-${status.index}"><c:if test="${comps.ruleCount ne 0}">hasRule</c:if></div>
								<input name="componentList['${status.index}'].componentId" id="componentId-${status.index}"  class="org-id" type=hidden value="${comps.componentId}"/>
								<input name="componentList['${status.index}'].fullName" id="componentName-${status.index}"  class="org-id" type=hidden value="${comps.fullName}"/>
								<input name="templateComponentId" id="templateComponentId"   type=hidden value="${comps.templateComponentId}"/>
								</td>
								<td class="template-comp" data-compId="${comps.componentId}" data-tempCompId="${comps.templateComponentId}" data-fullName="${comps.fullName}" data-ruleCount="${comps.ruleCount}" id="templateName">${comps.fullName}
									<c:if test="${comps.templateComponentId ne 0 and comps.forRules eq true}">
										<a  class="secondaryLink  floatRight ruleCount" 
											onClick="getRulesByTemplateComponentId('${comps.templateComponentId}','${comps.fullName}')">
											Rules<c:if test="${comps.ruleCount ne 0}">(${comps.ruleCount})</c:if>
										</a>
									</c:if>
								</td>
								<td><input type="checkbox" class="availableForRules" id="forRules-${status.index}" name="componentList['${status.index}'].forRules" 
								<c:if test="${comps.forRules eq true or comps.viewable eq true }">checked="checked"</c:if> 
								<c:if test="${(comps.viewable eq true)}">disabled</c:if> /></td>
								<td><input type="checkbox" id="viewable-${status.index}" name="componentList['${status.index}'].viewable" 
								<c:if test="${comps.viewable eq true}">checked="checked"</c:if> 
								<c:if test="${(comps.required eq true) || (comps.editable eq true)}">  disabled </c:if> /></td>
								<td><input type="checkbox" id="editable-${status.index}" name="componentList['${status.index}'].editable" 
								<c:if test="${comps.editable eq true}">checked="checked"</c:if> 
								<c:if test="${(comps.required eq true)}">  disabled </c:if> /></td>
								<td><input type="checkbox" id="required-${status.index}" name="componentList['${status.index}'].required"  
								<c:if test="${comps.required eq true}">checked="checked"</c:if>/></td>
								<td><input type="checkbox" id="dispOtherPO-${status.index}" name="componentList['${status.index}'].dispOtherPO" 
								<c:if test="${comps.dispOtherPO eq true}">checked="checked"</c:if>  
								<c:if test="${comps.forRules ne true}">  disabled </c:if> /></td>
								<td><input type="checkbox" id="excel-${status.index}" name="componentList['${status.index}'].excel" 
								<c:if test="${comps.excel eq true}">checked="checked"</c:if>
								<c:if test="${comps.forRules ne true}">  disabled </c:if> />
								<input class="hidden" type="checkbox" id="templateComponentId-${status.index}" name="templateComponentId['${status.index}']" 
								<c:if test="${comps.templateComponentId ne 0}">checked="checked"</c:if>/>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>	