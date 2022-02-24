 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <div class="modal-content col-xs-12" data-modal-title='Confirm Updated Information' data-modal-width="470">
	 <div class="row modal-body">
		<div class="col-xs-12">
			 <input type="hidden" id="isSSOUserUpdated" name="isSSOUserUpdated" value="${ssoDataUpdated.ssoUserUpdated}"/>
			  <c:if test="${ssoDataUpdated.ssoUserUpdated == true}">
			            <div class="refresh-modal-body single-line-content">
			                <div class="Table">
			                    <div class="Heading">
			                            <div  class="Cell"></div>
			                            <div class="Cell">Old Info</div>
			                            <div class="Cell">New Info</div>
			                    </div>
			                        <div class="Row">
			                            <div  class="Cell">Email</div>
			                            <div class="Cell">${editableUser.email}</div>
			                            <div  class="Cell" id="newUser.email">${ssoDataUpdated.email}</div>
			                        </div>
			                        <div class="Row">
			                            <div  class="Cell" >First Name</div>
			                            <div class="Cell" >${editableUser.firstName}</div>
			                            <div  class="Cell" id="newUser.firstName">${ssoDataUpdated.firstName}</div>
			                        </div>
			                        <div class="Row">
			                            <div class="Cell" >Last Name</div>
			                            <div class="Cell">${editableUser.lastName}</div>
			                            <div  class="Cell" id="newUser.lastName">${ssoDataUpdated.lastName}</div>
			                            
			                        </div>
			                        <div class="Row">
			                            <div  class="Cell">Phone</div>
			                            <div class="Cell">${editableUser.phone}</div>
			                            <div  class="Cell" id="newUser.phone">${ssoDataUpdated.phone}</div>
			                        </div>
			                        
			                          <div class="Row">
			                            <div  class="Cell">GESSOUID</div>
			                            <div class="Cell">${editableUser.gessouid}</div>
			                            <div  class="Cell" id="newUser.phone">${ssoDataUpdated.gessouid}</div>
			                        </div>
			                    </div>
			              
			            </div>
			            
			        <div class="sso-refresh-buttons-div">
				<a class="secondaryLink cancel" tabIndex="-1">Cancel</a> 
				<a class="buttonPrimary refresh-confirm" tabIndex="-1">Confrim</a>
			</div>
			</c:if>
			 <c:if test="${ssoDataUpdated.ssoUserUpdated == false}">
			 <div class="refresh-modal-body single-line-content">
			 <h3><b>User information is up-to-date.</b></h3>
			 </div>
			 <div class="sso-refresh-buttons-div">
				<a class="secondaryLink cancel" tabIndex="-1">Cancel</a> 
				
			</div>
			 </c:if>
		</div>
	</div>
</div>