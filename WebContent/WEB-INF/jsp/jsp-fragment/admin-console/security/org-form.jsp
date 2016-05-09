<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- *****************************preview model of signature file***************************************** -->
<div id="sign-file-modal" class="modal" title="Preview of Buyer Signature"></div>
<!-- *****************************preview model of initials file***************************************** -->
<div id="init-file-modal" class="modal" title="Preview of Buyer Initials"></div>

<div id="sso-updated-information" class="refresh-modal modal" title="Confirm Updated Information">

</div>
<div id="vendor-filter-model" class="vendor-filter modal" title="Filter Associated Vendor" style="display:none">
	<form id="org-form"   method="POST">
		<div class="vendor-filter-form-container">
			<div class="single-line-content">
				<label for="corp" style="width: 20%;display:inline-block;">Corp</label> 
				<input id="corp" name="corp"  type="text" size="5" maxlength="4"/>
			</div>
			<div class="single-line-content">
				<label for="vendor-name" style="width: 20%;display:inline-block;">Vendor Name</label> 
				<input id="vendor-name" name="vendorName"  type="text"  />
			</div>
			<div class="filter-edit-buttons">
				<a id="back-id" class="secondaryLink back" tabIndex="-1">Cancel</a> 
				<a id="filter-vendor" class="buttonPrimary filter-vendor-Btn" tabIndex="-1">Filter</a>
					<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class="errorMsg"></span>
				</div>
			</div>
			<!--  <div id="org-id-div" class="single-line-content">
				<label for="city" style="align:right">City</label> 
				<input id="city" name="city" type="text" />
			</div>-->
		</div>
	</form>
</div>
<fieldset id="user-fieldset" class="user-fieldset user-fieldset-height">
				<div class="org-form-container">
						<form id="org-form"   method="POST" 	<c:if test="${isCreatePage eq true}"> action="${pageContext.request.contextPath}/admin-console/security/create-org.htm" </c:if>
						<c:if test="${isCreatePage eq false}"> action="${pageContext.request.contextPath}/admin-console/security/update-org.htm" </c:if>> 
						<input id="org-id" name="orgId" type="text" class="displayNone" class="input" value="${editableOrg.orgId}"/>
					<div id="org-components" class="org-component">
						<div id="org-id-div" class="single-line-content">
								<label for="orgName">Org Name <span class=requiredField>*</span></label> 
								<input id="orgName" name="orgName"  class="input alpha alpha-numeric"  maxlength="50" autocomplete="off"  type="text" <c:if test="${isCreatePage eq false}">value='${editableOrg.orgName}'</c:if> />
							
						</div>
					
						<div class="single-line-content">
							<label for="parent-org">Parent Org<span class=requiredField>*</span></label> 
							<select id="parent-org" name="parentOrgId" class="input numeric numeric-whole">
								<option value="">Select Parent Org</option>
								<c:forEach items="${orgList}" var="org">
								 <c:if test="${isCreatePage eq false}">
								 	<c:if test="${editableOrg.orgId ne org.orgId}">
										<option value="${org.orgId}" <c:if test="${editableOrg.parentOrgId eq org.orgId}">selected</c:if> >${org.orgName}</option>
									</c:if>
								</c:if>
								<c:if test="${isCreatePage eq true}">
										<option value="${org.orgId}">${org.orgName}</option>
								</c:if>
								</c:forEach>
							</select>
						</div>
						
						<div id="org-desc-div" class="single-line-content">
								<label for="orgDescription">Description<span class=requiredField>*</span></label> 
							<textarea rows="4" cols="50" id="orgDescription" name="orgDescription" maxlength="250" class="input alpha alpha-numeric"><c:if test="${isCreatePage eq false}">${editableOrg.orgDescription}</c:if></textarea> 
						</div>
				<div class="single-line-content"> 
							<label for="associatedVendor">Associated  Vendor: &nbsp;(<a href="#" class="vendor-filter">filter</a>)<span class=requiredField>*</span></label> 
						</div>
				<c:if test="${isCreatePage eq true}">
				<div id="create-vendor-hierarchy-container" style="height:300px;overflow-y: auto;">
					<img id="loading" src="${commonStaticUrl}/images/spinner-big.gif" style="max-width: 100%;height: auto;display: none;"/>
					<div id="vendor-hierarchy" class="floatLeft clear-left jstree">
						<h3>Select Parent Org to display vendors.</h3>
					</div> 
				</div>
				</c:if>
				<c:if test="${isCreatePage eq false}">
					<div id="edit-vendor-hierarchy-container" style="height:300px;overflow-y: auto;">
						<img id="loading" src="${commonStaticUrl}/images/spinner-big.gif" alt="Loading...." style="max-width: 100%;height: auto;"/>
						<div id="edit-vendor-hierarchy" class="floatLeft clear-left jstree">
							<%@ include file="../../../jsp-fragment/admin-console/security/vendor-hierarchy.jsp" %> 
						</div>
					</div>
					<input id="selVendorList" name="selVendorList" type="text" class="displayNone" class="input" value="${selVendorList}"/>
				</c:if>
					<div class="single-line-content" style="float:right">
						<span class="errorMsg">* indicates a required field</span>
					</div>
					</div>
					</form>
					
				</div>
				
			</fieldset>