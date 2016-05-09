<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"  scope="page" />
<%
	String none = "NONE";
	String count = "COUNT";
	String days = "DAYS";
	pageContext.setAttribute("none", none);
	pageContext.setAttribute("count", count);
	pageContext.setAttribute("days", days);
%>

<!-- Form for submitting a notification to update. -->
<form id="notification-form">
	<h3 class="notification-name">${notification.alertMetric}</h3>
	<div>
		<span>Mark it out of compliance when </span>
		<span> 
			<select name="complianceType" class="compliance-dropdown">
				<option value="NONE" <c:if test="${notification.complianceType == none}"> selected</c:if> >NONE</option>
				<option value="COUNT" <c:if test="${notification.complianceType == count}"> selected</c:if> >COUNT</option>
				<option value="DAYS" <c:if test="${notification.complianceType == days}"> selected</c:if> >DAYS</option>
			</select>
		</span>
		<span> &#62; </span> 
		<span><input id="compliance-count" name="complianceCount" class="text-number" type="text" value="${notification.complianceCount}" /></span>
		<span class="compliance-error error"><img class="error-img" src="${commonStaticUrl}/images/warning.png" /><span class="errorMsg">Compliance Count Must Be a Positive Number</span></span>
	</div>
	<hr>
	<!-- Used for editing escalation days. -->
	<div>
		Escalation 1 Escalate After 
		<input id="escalation1-count" name="escalationOneDays" class="text-number esc1-days" type="text" value="${notification.escalationOneDays}" /> 
		Days
		<span class="esc1-error error"><img class="error-img" src="${commonStaticUrl}/images/warning.png" /><span class="errorMsg esc1-error-message">Escalation Days Must Be a Positive Number</span></span>
	</div>
	<div>
		Escalation 2 Escalate After 
		<input id="escalation2-count" name="escalationTwoDays" class="text-number esc2-days" type="text" value="${notification.escalationTwoDays}"/> 
		Days
		<span class="esc2-error error"><img class="error-img" src="${commonStaticUrl}/images/warning.png" /><span class="errorMsg esc2-error-message">Escalation Days Must Be a Positive Number</span></span>
	</div>
	<div>
		Escalation 3 Escalate After 
		<input id="escalation3-count" name="escalationThreeDays" class="text-number esc3-days" type="text" value="${notification.escalationThreeDays}"/> 
		Days
		<span class="esc3-error error"><img class="error-img" src="${commonStaticUrl}/images/warning.png" /><span class="errorMsg esc3-error-message">Escalation Days Must Be a Positive Number</span></span>
	</div>
	
	<!-- Vendor contacts on modal. -->
	<c:if test="${notification.visibilityVendor == 1}">			
		<div id="vendor-contacts">
			<h3><a href="#">Vendor</a></h3>
			<!-- <div class="contact-header"><span class="contact-header-text">Vendor</span></div> -->
			<div id="vendor-contact-list">
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 1</h3>
					<div id="esc1-vendor-emails" class="vendor-emails">
						<span class="email-checkbox">
							<input type="checkbox" name="vendorPrimaryEsc1" id="esc1-vendor-primary" <c:if test="${notification.esc1VendorPrimary != false}"> checked</c:if> />
						</span>
						<span>Vendor Primary</span><br>
						<span class="email-checkbox">
							<input type="checkbox" name="vendorSecondaryEsc1" id="esc1-vendor-secondary" <c:if test="${notification.esc1VendorSecondary != false}"> checked</c:if> />
						</span>
						<span>Vendor Secondary</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="vendorEsc1AdditionalChecked" <c:if test="${notification.vendorEsc1Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span>
					</div>
					<div class="addl-smc-users" id="esc1Vendor">
						<c:forEach var="np" items="${notification.vendorEsc1Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="vendorEsc1Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc1Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
				
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 2</h3>
					<div id="esc2-vendor-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="vendorPrimaryEsc2" id="esc2-vendor-primary" <c:if test="${notification.esc2VendorPrimary != false}"> checked</c:if> /></span>
						<span>Vendor Primary</span><br>
						<span class="email-checkbox"><input type="checkbox" name="vendorSecondaryEsc2" id="esc2-vendor-secondary" <c:if test="${notification.esc2VendorSecondary != false}"> checked</c:if> /></span>
						<span>Vendor Secondary</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="vendorEsc2AdditionalChecked" <c:if test="${notification.vendorEsc2Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc2Vendor">
						<c:forEach var="np" items="${notification.vendorEsc2Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="vendorEsc2Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc2Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
				
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 3</h3>
					<div id="esc3-vendor-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="vendorPrimaryEsc3" id="esc3-vendor-primary" <c:if test="${notification.esc3VendorPrimary != false}"> checked</c:if> /></span>
						<span>Vendor Primary</span><br>
						<span class="email-checkbox"><input type="checkbox" name="vendorSecondaryEsc3" id="esc3-vendor-secondary" <c:if test="${notification.esc3VendorSecondary != false}"> checked</c:if>/></span>
						<span>Vendor Secondary</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="vendorEsc3AdditionalChecked" <c:if test="${notification.vendorEsc3Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc3Vendor">
						<c:forEach var="np" items="${notification.vendorEsc3Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="vendorEsc3Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc3Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
			</div>
		</div>
	</c:if>
	
	<!-- Purchasing contacts on modal. -->
	<c:if test="${notification.visibilityPurchasing == 1}">			
		<div id="purchasing-contacts">
			<h3><a href="#">Purchasing</a></h3>
			<!-- <div class="contact-header"><span class="contact-header-text">Purchasing</span></div> -->
			<div>
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 1</h3>
					<div id="esc-1-purchasing-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="penskeVssEsc1" id="penskeVssEsc1" <c:if test="${notification.esc1PenskeVss != false}"> checked</c:if> /></span>
						<span>Penske VSS</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="purchasingEsc1AdditionalChecked" <c:if test="${notification.purchasingEsc1Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc1Purchasing">
						<c:forEach var="np" items="${notification.purchasingEsc1Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="purchasingEsc1Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc1Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
				
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 2</h3>
					<div id="esc2-purchasing-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="penskeVssEsc2" id="esc2-penske-vss" <c:if test="${notification.esc2PenskeVss != false}"> checked</c:if> /></span>
						<span>Penske VSS</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="purchasingEsc2AdditionalChecked" <c:if test="${notification.purchasingEsc2Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc2Purchasing">
						<c:forEach var="np" items="${notification.purchasingEsc2Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="purchasingEsc2Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc1Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
				
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 3</h3>
					<div id="esc3-purchasing-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="penskeVssEsc3" id="esc3-penske-vss" <c:if test="${notification.esc3PenskeVss != false}"> checked</c:if> /></span>
						<span>Penske VSS</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="purchasingEsc3AdditionalChecked" <c:if test="${notification.purchasingEsc3Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc3Purchasing">
						<c:forEach var="np" items="${notification.purchasingEsc3Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="purchasingEsc3Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc1Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
			</div>
		</div>
	</c:if>
	
	<!-- Planning contacts on modal. -->
	<c:if test="${notification.visibilityPlanning == 1}">			
		<div id="planning-contacts">
			<h3><a href="#">Planning</a></h3>
			<!-- <div class="contact-header"><span class="contact-header-text">Planning</span></div> -->
			<div>
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 1</h3>
					<div id="esc-1-planning-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="penskeAnalystEsc1" id="esc1-penske-analyst" <c:if test="${notification.esc1PenskeAnalyst != false}"> checked</c:if>/></span>
						<span>Penske Analyst</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="planningEsc1AdditionalChecked" <c:if test="${notification.planningEsc1Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc1Planning">
						<c:forEach var="np" items="${notification.planningEsc1Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="planningEsc1Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc1Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
				
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 2</h3>
					<div id="esc-2-planning-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="penskeAnalystEsc2" id="esc2-penske-analyst" <c:if test="${notification.esc2PenskeAnalyst != false}"> checked</c:if>/></span>
						<span>Penske Analyst</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="planningEsc2AdditionalChecked" <c:if test="${notification.planningEsc2Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc2Planning">
						<c:forEach var="np" items="${notification.planningEsc2Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="planningEsc2Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc1Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
				
				<div class="threeColumnContainer escalation-column">
					<h3 class="escalation-header">Escalation 3</h3>
					<div id="esc-3-planning-emails" class="vendor-emails">
						<span class="email-checkbox"><input type="checkbox" name="penskeAnalystEsc3" id="esc3-penske-analyst" <c:if test="${notification.esc3PenskeAnalyst != false}"> checked</c:if>/></span>
						<span>Penske Analyst</span><br>
					</div>
					<div class="add-user">
						<span class="addl-users-checkbox-span"><input type="checkbox" class="addl-users-checkbox" name="planningEsc3AdditionalChecked" <c:if test="${notification.planningEsc3Additional != false}"> checked</c:if>/></span>
						<span class="additional-users">Additional SMC User(s)</span> 
						<span class="add-button-span"><a class="addl-users-button"><img src="${commonStaticUrl}/images/add.png" class="add-button" /></a></span><br>
					</div>
					<div class="addl-smc-users" id="esc3Planning">
						<c:forEach var="np" items="${notification.planningEsc3Parties}">
							<c:if test="${np.isEmail == 1}">
								<div class="addl-user">
									<a class="delete-user-link"><img src="${commonStaticUrl}/images/delete.png" class="delete-user-image"/></a>
									${np.contact}
									<input type="hidden" name="planningEsc3Additional" class="hidden-email" value="${np.contact}" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<span class="additional-users-input">
						<select id="vendorEsc1Additional" class="addl-users-select">
							<option value=""></option>
						</select>
					</span><br>
					<span class="cancel-addl-users-link-span">
						<a class="cancel-addl-users-link">Cancel</a>
					</span>
				</div>
			</div>
		</div>
	</c:if>
	
	<div class="note">
		<span>Note: If the Additional SMC User(s) checkbox is unchecked when a user clicks save, and there are additonal users on the list, all those additional users will be deleted from that escalation level and group.</span>
	</div>
	
	<!-- Save Button and Cancel Link -->
	<span id="cancel-and-save" class="primary-functions">
		<input type="hidden" value="${notification.notificationId}" class="notification-id-modal" name="notificationId">
		<a id="edit-notification-cancel" class="cancel">Cancel</a>
		<a class="buttonPrimary save-edit" id="edit-notification-save">Save</a>
	</span>
	
	<!-- Hidden inputs for the visibility -->
	<input type="hidden" name="visibilityVendor" value="${notification.visibilityVendor}" />
	<input type="hidden" name="visibilityPurchasing" value="${notification.visibilityPurchasing}" />
	<input type="hidden" name="visibilityPlanning" value="${notification.visibilityPlanning}" />
</form>

<!-- Scripts -->
<script src="${context}/js/admin-console/app-config/modals/edit-notification-modal.js" type="text/javascript"></script>