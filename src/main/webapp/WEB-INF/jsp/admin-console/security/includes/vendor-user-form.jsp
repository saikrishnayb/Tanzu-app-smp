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
			
			<div class="single-line-content email-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>  style="margin-top: 18px;">
				<label for="email">Email <span class=requiredField>*</span></label> 
				<input id="email" tabindex="1" name="email" type="text" class='input alpha alpha-email' value="${editableUser.email}"/>
			</div>
			
			<div id="sso-id-div" class="single-line-content">
					<label for="sso-id">User Name <span class=requiredField>*</span></label> 
					<input id="sso-id" tabindex="2" name="ssoId" class='input alpha alpha-username  <c:if test = "${isCreatePage == false}"> borderless</c:if>'  type="text" value="${editableUser.ssoId}"  <c:if test = "${isCreatePage == false}">readonly</c:if> />
					<input id="sso-old-id" type="hidden" value="${editableUser.ssoId}"/>
					<c:if test = "${isCreatePage == false}">
				 	<span id="refreshSSODetails" class="reloadImage"><a href="#" tabindex="-1" id="refreshSSO"></a></span>
				  </c:if>
			</div>
			
			<c:if test = "${isCreatePage == true}">
				<div class="single-line-content">
					User Names can contain letters and numbers only
				</div>						
			</c:if>
							
			<div class="single-line-content first-name-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>>
				<label for="first-name">First Name <span class=requiredField>*</span></label> 
				<input id="first-name" tabindex=3 name="firstName" type="text" class="input alpha alpha-name " value="${editableUser.firstName}" />
			</div>
	
			<div class="single-line-content last-name-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>>
				<label for="last-name">Last Name <span class=requiredField>*</span></label> 
				<input id="last-name" tabindex=4 name="lastName" class="input alpha alpha-name " type="text" value="${editableUser.lastName}" />
			</div>
	
			<div class="single-line-content phone-container" <c:if test = "${isCreatePage == true && currentUser.penskeUser}">class="displayNone"</c:if>>
				<label for="phone">Phone </label> 
				<input id="phone" tabindex=5 class="input phone numeric numeric-phone  optional" name="phone" type="text" value="${editableUser.phone}" /> 
				<input id="extension" tabindex=6 class="input extension optional numeric numeric-extension " name="extension" type="text" value="${editableUser.extension}" placeholder="ext." />
			</div>
			
			<div class="single-line-content">
				<label for="user-type">User Type<span class=requiredField>*</span></label> 
				<c:forEach items="${userTypes}" var="type">
					<c:if test = "${isCreatePage == false}">
						 <c:if test="${editableUser.userType.userTypeId eq type.userTypeId}">
						 	<input id="userType.description" tabindex=7 class="input alpha alpha-name " name="userType.description" type="text" value="${type.description}" readonly/>
						 	<input id="user-type" class="input numeric numeric-whole usertype " name="userType.userTypeId" type="hidden" value="${type.userTypeId}" />    
						 </c:if>
					</c:if>
					<c:if test = "${isCreatePage == true}">
						<input id="userType.description" tabindex="-1" class="input alpha alpha-name borderless " name="userType.description" type="text" value="${type.description}" readonly />  
						<input id="user-type" class="input numeric numeric-whole usertype " name="userType.userTypeId" type="hidden" value="${type.userTypeId}" />  
					</c:if>
				</c:forEach>
			</div>
						
			<div id="bu-div"
				class="single-line-content">
				<label for="bu">Business Unit<span class=requiredField>*</span></label> 
					<select id="bulist" tabindex=8 class="input numeric numeric-whole" name="orgId" style="width:100%">
					<option value=''>Select</option>
					<c:forEach items="${orgList}" var="org">
						<option value="${org.orgId}"
							<c:if test="${org.orgId eq editableUser.orgId}"> selected</c:if> >
							${org.orgName}</option>
					</c:forEach> 
				</select>
			</div>
						
			<div class="single-line-content">
				<label for="user-role">User Role<span class=requiredField>*</span></label> 
				<select id="user-role" tabindex=9 name="role.roleId" class="input numeric numeric-whole">
					<option value="">Select User Role</option>
					<c:forEach items="${userRoles}" var="role">
						<option value="${role.roleId}" <c:if test="${editableUser.role.roleName eq role.roleName}"> selected </c:if>>${role.roleName}</option>
					</c:forEach>
				</select>
			</div>
	
			<div class="daily-opt-in-container">
				<input id="daily-email-opt-in" tabindex=10 name="dailyOptIn" type="checkbox" <c:if test="${(editableUser.dailyOptIn) || (isCreatePage == true)}">checked=checked</c:if> />
				<label for="daily-email-opt-in">Opt-in to Daily Summary Email</label> 
			</div>
							
		</form>

		<span class="errorMsg">* indicates a required field</span>
	
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