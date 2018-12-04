<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- *****************************preview model of signature file***************************************** -->
<div id="sign-file-modal" class="modal" title="Preview of Buyer Signature"></div>
<!-- *****************************preview model of initials file***************************************** -->
<div id="init-file-modal" class="modal" title="Preview of Buyer Initials"></div>

<div id="sso-updated-information" class="refresh-modal modal" title="Confirm Updated Information">

</div>
<fieldset id="user-fieldset" class="user-fieldset">
				<div class="user-form-container">
					<form id="user-form" <c:if test="${isCreatePage == true}"> action="${baseAppUrl}/admin-console/security/create-user.htm"  </c:if> 
										 <c:if test="${isCreatePage != true}"> action="${baseAppUrl}/admin-console/security/edit-user-submit.htm" </c:if> 
										 enctype="multipart/form-data" method="POST">
						<input id="user-id" name="userId" type="text" class="displayNone" class="input" value="<c:out value="${editableUser.userId}"/>"/>
						<input id="vendor-ids" name="vendorIds" type="text" class="displayNone" class="input"/>
						<input id="ge-sso-id" name="gessouid" type="text" class="displayNone" class="input" value="<c:out value="${editableUser.gessouid}"/>"/>
						<div id="sso-id-div" class="single-line-content">
								<label for="sso-id">SSO ID <span class=requiredField>*</span></label> 
								<input id="sso-id" name="ssoId"  class="input alpha alpha-numeric" tabindex=1 type="text" value="<c:out value="${editableUser.ssoId}"/>"/>
							<c:if test = "${isCreatePage == false}">
							 <span id="refreshSSODetails" class="reloadImage"><a href="#" id="refreshSSO"></a></span>
							  </c:if>
						</div>
						<c:if test = "${isCreatePage == true}">
						<div id="ssolookup-buttons-div" class="single-line-content ssolookup-buttons-div" style="margin-top: 1px;">
						<label for="ssolookup"> </label> 
							
							<a class="buttonSecondary floatRight ssolookup" href="#" style="height: 15px;margin-right: 42px;">SSO Lookup</a></div>
							</c:if>
						<div class="single-line-content email-container" <c:if test = "${isCreatePage == true && currentUser.visibleToPenske}">class="displayNone"</c:if>  style="margin-top: 18px;">
							<label for="email">Email <span class=requiredField>*</span></label> 
							<input id="email" name="email" tabindex=-1 type="text" class="input alpha alpha-email borderless" value="${editableUser.email}" readonly/>
						</div>

						<div class="single-line-content first-name-container" <c:if test = "${isCreatePage == true && currentUser.visibleToPenske}">class="displayNone"</c:if>>
							<label for="first-name">First Name <span class=requiredField>*</span></label> 
							<input id="first-name" name="firstName" tabindex=-1 type="text" class="input alpha alpha-name borderless" value="${editableUser.firstName}" readonly/>
						</div>

						<div class="single-line-content last-name-container" <c:if test = "${isCreatePage == true && currentUser.visibleToPenske}">class="displayNone"</c:if>>
							<label for="last-name">Last Name <span class=requiredField>*</span></label> 
							<input id="last-name" name="lastName" tabindex=-1 class="input alpha alpha-name borderless" type="text" value="${editableUser.lastName}" readonly/>
						</div>

						<div class="single-line-content phone-container" <c:if test = "${isCreatePage == true && currentUser.visibleToPenske}">class="displayNone"</c:if>>
							<label for="phone">Phone </label> 
							<input id="phone" tabindex=-1 class="input phone numeric numeric-phone borderless optional" name="phone" type="text" value="${editableUser.phone}" readonly/> 
							<input id="extension" tabindex=-1 class="input extension optional numeric numeric-extension borderless" name="extension" type="text" value="${editableUser.extension}" placeholder="ext." readonly/>
						</div>
						<div class="single-line-content">
							<label for="user-type">User Type<span class=requiredField>*</span></label> 
							
						<c:forEach items="${userTypes}" var="type">
								<c:if test = "${isCreatePage == false}">
								
								 <c:if test="${editableUser.userType.userTypeId eq type.userTypeId}">
								<!--   <span >${type.userType}</span> -->
								 <input id="userType.description" tabindex=-1 class="input alpha alpha-name borderless" name="userType.description" type="text" value="${type.description}" readonly/>
								  <input id="user-type" tabindex=-1 class="input numeric numeric-whole usertype borderless" name="userType.userTypeId" type="hidden" value="${type.userTypeId}" readonly/>    
								 </c:if>
								 </c:if>
								 <c:if test = "${isCreatePage == true}">
								 <c:if test="${currentUser.userType eq type.userTypeId}">
								  <input id="userType.description" tabindex=-1 class="input alpha alpha-name borderless" name="userType.description" type="text" value="${type.description}" readonly/>  
								  <input id="user-type" tabindex=-1 class="input numeric numeric-whole usertype borderless" name="userType.userTypeId" type="hidden" value="${type.userTypeId}" readonly/>  
								 </c:if>
								 </c:if>
						</c:forEach>
						</div>
					
						<div id="bu-div"
							class="single-line-content">
							<label for="bu">Business Unit<span class=requiredField>*</span></label> 
								<select id="bulist" tabindex=2 class="input numeric numeric-whole" name="orgId">
								<option value=''>Select</option>
								<c:forEach items="${orgList}" var="org">
									<option value="${org.orgId}"
										<c:if test="${org.orgId eq editableUser.orgId}"> selected</c:if> >
										${org.orgName}</option>
								</c:forEach> 
							</select>
						</div>
					

						<div class="single-line-content" <c:if test = "${isCreatePage == true && currentUser.visibleToPenske}"> class="displayNone"</c:if>>
							<label for="user-role">User Role<span class=requiredField>*</span></label> 
							<select id="user-role" tabindex=3 name="role.roleId" class="input numeric numeric-whole">
								<option value="">Select User Role</option>
								<c:forEach items="${userRoles}" var="role">
									<option value="${role.roleId}" <c:if test="${editableUser.role.roleName eq role.roleName}"> selected </c:if>>${role.roleName}</option>
								</c:forEach>
							</select>
						</div>
			
						<div id="user-dept-div" class="single-line-content <c:if test="${editableUser.userType.userTypeId ne 1}"> displayNone</c:if>
																		   <c:if test = "${isCreatePage == true && currentUser.vendorUser}">displayNone</c:if>">
							<label for="user-dept">User Dept</label> 
							<select id="user-dept" tabindex=4
								<c:if test="${editableUser.userType.userTypeId eq 1}">name="userDept.userDeptId"</c:if>
								class="input numeric numeric-whole<c:if test="${editableUser.userType.description eq 'Supplier User'}"> optional</c:if>
																  <c:if test="${currentUser.vendorUser}"> optional</c:if>">
								<option value="">Select User Depart</option>
								<c:forEach items="${userDepts}" var="dept">
									<option value="${dept.userDeptId}"
										<c:if test="${dept.userDeptId eq editableUser.userDept.userDeptId}"> selected</c:if>
										<c:if test="${editableUser.userType.description == 'Supplier User'}">selected</c:if>>${dept.description}</option>
								</c:forEach>
							</select>
						</div>

				
						<!-- *****************************************************file upload******************************************************** -->

						<div id="signature-file-div" class="single-line-content file-input-container <c:if test = "${editableUser.userType.userTypeId ne 1}">displayNone </c:if>
																									 <c:if test = "${isCreatePage == true && currentUser.vendorUser}">displayNone</c:if>">
							<label>Signature File</label> 
							<span class="file-input-span">
								<input type="file" tabindex="-1" class="input sign-file-hidden-input optional" name="signatureImage"/>
							</span> 
							<input id="sign-file-name" class="file-text-box" readonly="readonly" tabindex="-1" <c:if test = "${editableUser.hasSignFile == true }">value="Signature File Exists"</c:if>/>  
							<a id="signature-add"><img src="${commonStaticUrl}/images/add.png"/></a> 
							<c:if test = "${editableUser.hasSignFile == true }">
							<input type="hidden" id="hasSignFile" name="hasSignFile" value="${editableUser.hasSignFile}" />
							<a id="signature-delete"><img src="${commonStaticUrl}/images/delete.png"/></a> 
							<a id="signature-preview"><img src="${commonStaticUrl}/images/search.png"/></a></c:if>
						</div>
						<div id="initials-file-div" class="single-line-content file-input-container <c:if test = "${editableUser.userType.userTypeId ne 1}">displayNone </c:if>
																									<c:if test = "${isCreatePage == true && currentUser.vendorUser}">displayNone</c:if>">
							<label>Initials File</label> 
							<span class="file-input-span">
								<input type="file" tabindex="-1" class="input init-file-hidden-input optional" name="initialsImage"/>
							</span> 
							<input id="init-file-name" class="file-text-box" readonly="readonly" tabindex="-1" <c:if test = "${editableUser.hasInitFile == true }">value="Initials File Exists"</c:if>/>
							<a id="intials-add"><img src="${commonStaticUrl}/images/add.png"/></a>  
							<c:if test = "${editableUser.hasInitFile == true}"><a id="intials-delete"><img src="${commonStaticUrl}/images/delete.png"/></a> <a id="intials-preview"><img src="${commonStaticUrl}/images/search.png"/></a>
								<input type="hidden" id="hasInitFile" name="hasInitFile" value="${editableUser.hasInitFile}" />
							</c:if>
						</div>
					</form>
					<span class="errorMsg">* indicates a required field</span>
				</div>
				<div id="permissions-half">
					<!-- *************************************************Role Permissions************************************************ -->
					<div id="role-permissions" class="role-permissions">
					<c:choose>
    					<c:when test="${tabPermissionsMap != null}">
							<c:forEach items="${tabPermissionsMap}" var="entry">
								<div id="permission-tab-accordions" class="width-half initial-accordion permission-tab-accordions accordion-headers">
									<h3>
										<span></span> 
										<a href="#" class="initial-accordion">${entry.key}</a>
									</h3>
									<div>
										<c:forEach items="${entry.value}" var="permission">
											<div class="permissions-checkbox" style="padding: 2px;">
												${permission.description}
											</div>
										</c:forEach>
									</div>
								</div>
							</c:forEach>
					</c:when>
					<c:otherwise>
						<c:if test = "${isCreatePage == false}">
							<h3><center>No permission attached to this Role</center></h3>
						</c:if>
					</c:otherwise>
				</c:choose>
					</div>
					<!-- *************************************************vendor templates************************************************ -->
					<div id="templates">
						<c:forEach items="${vendorTemplates}" var="location">
							<div class="width-half initial-accordion templates-accordion accordion-headers">
								<h3>
									<span class="ui-icon ui-icon-triangle-1-e"></span> 
									<a href="#" class="initial-accordion">${location.manufacturer} - ${location.city}, ${location.state} - ${location.vendorNumber} Templates</a>
								</h3>
								<div>
						<c:forEach items="${location.templatePoCategorySubCategory}" var="template">
							<div class="width-half initial-accordion templates-accordion component-accordions">
								<h3>
									<span></span> 
									<a href="#" class="initial-accordion">${template.poCategory.categoryName} - ${template.subCategory.subCategoryName}</a>
								</h3>
								<div>
									<div class="full-width">
										<table class="po-cat-table" >
											<thead>
												<tr>
													<th>Component Name</th>
													<th>Editable</th>
													<th>Visible</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${template.templateComponents}" var="component">
													<tr>
														<td>${component.componentName}</td>
														<td><input type="checkbox" 
																<c:if test="${component.visible eq '1'}">
																		checked
																</c:if> onclick="return false"/>
														</td>
														<td><input type="checkbox"
																<c:if test="${component.editable eq '1'}">
																	checked
																</c:if> onclick="return false"/>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</c:forEach>
						</div>
						</div>
					</c:forEach>
					</div>
				</div>
			</fieldset>