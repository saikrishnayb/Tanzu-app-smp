<!-- global modals -->
<div id="ajax-error-modal" class="modal">
	<p>Something went wrong try again</p>
	<div style="position: absolute; bottom: 3px; right: 5px;">
		<a id="error-ok" class="buttonPrimary deactivate" tabIndex="-1">Okay</a>
	</div>
</div>

<div id="customized-ajax-error" class="modal ajax-error-modal">
	<div class="custom-ajax-error-div">
		<img src="${commonStaticUrl}/images/warning.png">
		<p></p>
	</div>
</div>

<!-- Overlays -->
<div class="overlayadmin" style="visibility: hidden;"></div>
<div class="processingImg" style="visibility: hidden;">
	<div>
		<img src="${commonStaticUrl}/images/spinner-big.gif" />
	</div>
	<div id="processingText"></div>
</div>

<!-- Scripts -->
<script src="${commonStaticUrl}/js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>
<script src="${commonStaticUrl}/js/jquery.fixedMenu.js" type="text/javascript"></script>
<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
<script src="${commonStaticUrl}/js/common.js" type="text/javascript"></script>

<script src="${baseUrl}/js/global/v1/global.js" type="text/javascript"></script>
<script src="${baseUrl}/js/global/v1/globaloverlay.js" type="text/javascript"></script>