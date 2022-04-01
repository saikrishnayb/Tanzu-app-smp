<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset id="user-fieldset" class="user-fieldset">
	<div class="user-form-container col-xs-4">
		<form id="user-form-vendor" 
				<c:if test="${isCreatePage == true}"> 
					action="${baseAppUrl}/admin-console/security/create-user.htm"  
				</c:if> 
				<c:if test="${isCreatePage != true}"> 
					action="${baseAppUrl}/admin-console/security/edit-user-submit.htm" </c:if> 
				enctype="multipart/form-data" method="POST">
			
			<input id="user-id" name="userId" type="text" class="displayNone" class="input" value="${editableUser.userId}"/>
			<input id="vendor-ids" name="vendorIds" type="text" class="displayNone" class="input"/>
			<input type="hidden" id="isCreateOrEdit" value="${isCreatePage}">
			<input type="hidden" id="returnFlg" name="returnFlg" value="${returnFlg}">
			
			<div class="form-group email-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>  style="margin-top: 18px;">
				<label for="email" class="col-xs-4">Email <span class=requiredField>*</span></label> 
				<div class="col-xs-8 no-left-padding">
					<input id="email" name="email" type="text" class='input alpha alpha-email common-form-control' required maxlength="100" value="${editableUser.email}"/>
				</div>
			</div>
			
			<div id="sso-id-div" class="form-group">
				<label for="sso-id" class="col-xs-4">User Name <span class=requiredField>*</span></label> 
				<c:choose>
					<c:when test = "${isCreatePage == false}">
						<div class="col-xs-7 no-left-padding">
							<input id="sso-id" required name="ssoId" data-no-spaces class='input alpha alpha-numeric common-form-control borderless' maxlength="100" type="text" value="${editableUser.ssoId}" readonly disabled/>
							<input id="sso-old-id" type="hidden" value="${editableUser.ssoId}"/>
						</div>
						<div class="col-xs-1 no-left-padding">
					 			<span id="refreshSSODetails" class="reloadImage"><a href="#" tabindex="-1" id="refreshSSO"></a></span>
					  	</div>
				  	</c:when>
				  	<c:otherwise>
					  	<div class="col-xs-8 no-left-padding">
							<input id="sso-id" required name="ssoId" data-no-spaces class='input alpha alpha-numeric common-form-control' maxlength="100" type="text" value="${editableUser.ssoId}" />
							<input id="sso-old-id" type="hidden" value="${editableUser.ssoId}"/>
						</div>
				  	</c:otherwise>
				 </c:choose>
			</div>
			
			<c:if test = "${isCreatePage == true}">
				<div class="form-group">
					<div class="col-xs-12">
						<i>User Names can contain letters and numbers only</i>
					</div>
				</div>						
			</c:if>
							
			<div class="form-group first-name-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>>
				<label for="first-name" class="col-xs-4">First Name <span class=requiredField>*</span></label> 
				<div class="col-xs-8 no-left-padding">
					<input id="first-name" required name="firstName" type="text" class="input alpha alpha-name common-form-control" maxlength="100" value="${editableUser.firstName}" />
				</div>
			</div>
	
			<div class="form-group last-name-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>>
				<label for="last-name" class="col-xs-4">Last Name <span class=requiredField>*</span></label> 
				<div class="col-xs-8 no-left-padding">
					<input id="last-name" required name="lastName" class="input alpha alpha-name common-form-control" type="text" value="${editableUser.lastName}" />
				</div>
			</div>
	
			<div class="form-group phone-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>>
				<label for="phone" class="col-xs-4">Phone </label> 
				<div class="no-left-padding col-xs-8">
					<div class="row no-gutters">
						<div class="col-xs-9">
							<input id="phone" required class="input phone numeric numeric-phone common-form-control" name="phone" type="text" value="${editableUser.phone}" /> 
						</div>
						<div class="col-xs-3 no-left-padding">
							<input id="extension" class="input extension numeric common-form-control" name="extension" type="text" value="${editableUser.extension}" placeholder="ext." />
						</div>
					</div>
				</div>			
			</div>
			
			<div class="form-group">
				<label for="user-type" class="col-xs-4">User Type<span class=requiredField>*</span></label> 
				<div class="col-xs-8 no-left-padding">
					<c:forEach items="${userTypes}" var="type">
						<c:if test = "${isCreatePage == false}">
							 <c:if test="${editableUser.userType.userTypeId eq type.userTypeId}">
							 	<input id="userType.description" class="input alpha alpha-name common-form-control" name="userType.description" type="text" value="${type.description}" readonly/>
							 	<input id="user-type" class="input numeric numeric-whole usertype " name="${isCreatePage? 'userTypeId' : 'userType.userTypeId'}" type="hidden" value="${type.userTypeId}" />    
							 </c:if>
						</c:if>
						<c:if test = "${isCreatePage == true}">
							<input id="userType.description" tabindex="-1" class="input alpha alpha-name borderless common-form-control" name="userType.description" type="text" value="${type.description}" readonly />  
							<input id="user-type" class="input numeric numeric-whole usertype " name="${isCreatePage? 'userTypeId' : 'userType.userTypeId'}" type="hidden" value="${type.userTypeId}" />  
						</c:if>
					</c:forEach>
				</div>
			</div>
						
			<div id="bu-div"
				class="form-group">
				<label for="bu" class="col-xs-4">Business Unit<span class=requiredField>*</span></label>
				<div class="col-xs-8 no-left-padding"> 
					<select id="bulist" required class="input numeric numeric-whole common-form-control" name="orgId">
					<option value=''>Select</option>
					<c:forEach items="${orgList}" var="org">
						<option value="${org.orgId}"
							<c:if test="${org.orgId eq editableUser.orgId}"> selected</c:if> >
							${org.orgName}</option>
					</c:forEach> 
					</select>
				</div>
			</div>
						
			<div class="form-group">
				<label for="user-role" class="col-xs-4">User Role<span class=requiredField>*</span></label>
				<div class="col-xs-8 no-left-padding"> 
					<select id="user-role" required name="${isCreatePage? 'roleId' : 'role.roleId'}" class="input numeric numeric-whole common-form-control">
						<option value="">Select User Role</option>
						<c:forEach items="${userRoles}" var="role">
							<option value="${role.roleId}" <c:if test="${editableUser.role.roleName eq role.roleName}"> selected </c:if>>${role.roleName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
	
			<div class="daily-opt-in-container col-xs-12">
				<input id="daily-email-opt-in" name="dailyOptIn" type="checkbox" <c:if test="${(editableUser.dailyOptIn) || (isCreatePage == true)}">checked=checked</c:if> />
				<label for="daily-email-opt-in">Opt-in to Daily Summary Email</label> 
			</div>
			
			<c:if test="${isCreatePage and not currentUser.isVendorUser()}">
				<div class="daily-opt-in-container col-xs-12">
					<input id="hold-enrollment-email" name="holdEnrollmentEmail" type="checkbox" />
					<label for="hold-enrollment-email">Hold Enrollment Email until PO Issued</label> 
				</div>
			</c:if>			
		</form>

		<span class="errorMsg col-xs-12">* indicates a required field</span>
	
	</div>
	<div id="permissions-half" class="col-xs-8">
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
						<h3 class="text-center">No permission attached to this Role</h3>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</fieldset>