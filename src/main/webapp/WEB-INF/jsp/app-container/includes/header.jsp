<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tl"%>

	<div id="header">
	<img class="penskeLogo" src="${commonStaticUrl}/images/penske_logo.png" alt="Penske"/>
	<div id="title" style="width:490px;">
		<h1>Supplier Management Center</h1>
		<div style="display:inline-block;">
			<a href="#" onclick="refreshFrameWithoutWarnings();return false;">Refresh</a>
		</div>
	</div>
		<!-- Code for disabling the Buddy link -->
		<c:set var="displayLink"  value="display" scope="page"/>
		<c:if test="${(vendorUser) || (not empty errorModel.message)}">
			<c:set var="displayLink" value="hide" scope="page"/>
		</c:if>
		<div id="utility">

            <ul class="utility-list">
              <li>Welcome, ${userDetails.firstName} ${userDetails.lastName}</li>
              <c:if test="${displayLink eq 'display' }">
                <li>
                	<a href="#" onclick="openBuddyPopup();return false;">Buddy</a>
                	<img id="hasBuddies" src="${commonStaticUrl}/images/status_green.png" style="padding-left:3px;display: ${hasBuddies ? 'inline-block' : 'none'}" />
               	</li>
      
                <tl:isAuthorized tabName="Admin Console" secFunction="VENDOR_FILTER">
                  <li>
                    <tl:penskeOnly>
                      <a class="vendor-filter">Vendor Filter</a>
                      <span class="vendor-filter-toggle-container ${hasVendors? '' : 'hidden'} ${hasVendorFilterActivated? 'on' : 'off'}">
                        <img class="vendor-filter-toggle vendor-filter-toggle-on" src="${commonStaticUrl}/images/status_green.png" style="padding-left:3px;" />
                        <img class="vendor-filter-toggle vendor-filter-toggle-off" src="../images/vendor-filter-off.png" style="padding-left:3px;"/>
                      </span>
                    </tl:penskeOnly>
                  </li>
                </tl:isAuthorized>
      
              </c:if>
              <li><a href="#" id="helpLink">Help</a></li>
              <li><a class="exit" href="#" onclick="logOut();">Log off</a></li>
            </ul>
            <hr noshade="noshade" />

            <p class="last-login">${lastLogin}</p>
		
    </div>
</div>

<div id="helpSelector" style="display: none;">
<div style="text-align:center;">
	<a id="frequentlyAsked">Frequently Asked Questions</a><br/><br/>
	<a id="howToVideos">How-To Instructional Videos</a><br/><br/>		
	<p>
	For all other help topics, email or call our Supplier Management Support Specialists. 
	</br>
	</br>			
	E: <a href="mailto:SupplierManagementCenter@penske.com?subject=Supplier Management Support">SupplierManagementCenter@penske.com</a>
	</br>
	</br>
	P: ${supportNum}
	</p>
</div>		

	
</div>
<div id="helpPopup" style="display: none;"></div>

<div id="buddyPopup" style="display: none;">
	<jsp:include page="/WEB-INF/jsp/app-container/modal/buddy.jsp" ></jsp:include>
</div>
<!-- Pop UP for Session Time out  -->
		<div id="sessionTimeoutPopup" style="display:none;">

<div id="sessionExpiryTime">
	
</div>

<div style="position:absolute;bottom:3px;right:5px;">
	<a href="#" class="secondaryLink" onclick="logOut();" tabIndex="-1">No</a>
	<a href="#" class="buttonPrimary" tabIndex="-1" onclick="validateSession();return false;">Yes</a>
</div>
	</div>
	<!-- END OF POP UP -->	
    
    
    <div class="modal modal-utility"></div>