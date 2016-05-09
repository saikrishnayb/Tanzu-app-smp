<div id="footer">
	<span id="leftFooter"> 
		<script>getLeftFooter();</script> 
	</span>

	<ul id="rightFooter" class="utility-list">
		<li>Questions? Call ${supportNum}</li>
		<li><a class="exit">Exit</a>
		</li>
	</ul>
</div>
<!-- global modals -->

<div id="ajax-error-modal" class="modal">
	<p> Something went wrong try again</p>
	<div style="position:absolute;bottom:3px;right:5px;">
		<a id="error-ok" class="buttonPrimary deactivate" tabIndex="-1">Okay</a>
	</div>
</div>
<div id="spinner-modal" class="modal">
	<img id="spinnerBig" src="${commonStaticUrl}/images/spinner-big.gif"/>
</div>
<input type="hidden" id="application-user-role-id" value="${currentUser.roleId}" />
<div id="role-modal" class="modal role-modal" title="Application Permissions For User"></div>
<div id="customized-ajax-error" class="modal ajax-error-modal"><div class="custom-ajax-error-div"><img src="${commonStaticUrl}/images/warning.png"><p></p></div></div>
<!-- Status Alert -->
<div class="status-alerts">
	<div class="alert alert-success centerAlign hidden" role="alert">
		<h3>Successfully Saved!</h3>
	</div>
	<div class="alert alert-danger centerAlign hidden" role="alert">
		<h3>Save Failed</h3>
	</div>
</div>