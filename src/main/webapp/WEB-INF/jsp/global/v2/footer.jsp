<!-- ***** Global Modals ***** -->
<%-- An empty modal that appears on every page and can be filled with any content the page wants to put in it --%>
<div id="modal-global" class="modal row">
	<%@ include file="modal-error-container.jsp"%>
</div>

<%-- Displays an error message to a user --%>
<div id="modal-error" class="modal row" title="Error">
  <div class="modal-content col-xs-12" data-modal-width="325">
	<div class="modal-body row">
      <div class="col-xs-12">
  	    <p class="error-text">Something went wrong with the request. Please try again.</p>
      </div>
	</div>
	<div class="modal-footer row">
      <div class="col-xs-12">
	    <a class="buttonPrimary btn btn-modal-error-ok modal-close">OK</a>
      </div>
	</div>
  </div>
</div>

<%-- If there is a stack trace associated with an error message, this modal is shown when the user clicks "show details" on the main error modal --%>
<div id="modal-stack-trace" class="modal row" title="Stack Trace">
  <div class="modal-content col-xs-12" data-modal-max-width="960">
    <div class="modal-body row">
      <div class="col-xs-12">
      	<%-- We need this inline styling because the horizontal scrollbar in Chrome doesn't work properly with border-radius.
      		   see: http://stackoverflow.com/questions/38456518/horizontal-textarea-scrollbar-cant-be-grabbed-in-chrome-when-border-radius-is-a --%>
        <div class="alert alert-danger" style="position: relative;">
          <p class="exception-message text-danger"></p>
          <p class="exception-stack-trace text-danger"></p>
        </div>
      </div>
    </div>
    <div class="modal-footer row">
      <div class="col-xs-12">
        <span class="successful-copy bold text-success ui-helper-hidden">Copied to clipboard successfully!</span>
        <a class="buttonSecondary btn btn-copy-stack" href="javascript:void(0);">Copy to Clipboard</a>
        <a class="buttonPrimary btn btn-modal-error-ok modal-close">OK</a>
      </div>
    </div>
  </div>
</div>

<!-- ***** Scripts ***** -->
<script type="text/javascript" src="${commonStaticUrl}/js/common.js"></script>

<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery-ui-1.12.1.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery.dataTables-1.10.12.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery.dataTables.scroller.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/dataTables.responsive-2.0.1.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/dataTables.fixedColumns.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery.multiselect-2.0.1.js" type="text/javascript"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery.multiselect.filter-2.0.1.js" type="text/javascript"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/jquery/jquery.mask-1.7.7.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/bootstrap/bootstrap.min.js" type="text/javascript"></script>


<script type="text/javascript" src="${baseUrl}/js/global/v2/modal-util.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/loading-util.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/number-util.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/file-download-helper.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/ritsu.js"></script>
<script type="text/javascript" src="${baseUrl}/js/global/v2/global.js"></script>