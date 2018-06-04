$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-global-exceptions");
	var $exceptionTable = $('#exceptionTable');
	initDataTable($exceptionTable);
	var $editExceptionModal = $("#modal-edit-global-exception");
	var $deleteExceptionModal = $("#modal-delete-global-exception");
	
	$editExceptionModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 700,
		minHeight: 235,
		resizable: false,
		title: 'Edit Global Exception',
		closeOnEscape: false,
		open: function(event, ui) { },
		close:function(event,ui){
			$('.provided-by').removeClass('selectedProvider');
		}
	});
	$deleteExceptionModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 600,
		minHeight: 235,
		resizable: false,
		title: 'Delete Global Exception',
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$exceptionTable.on("click", ".edit-exception", function(){
		
		var $thisRow = $(this).closest("tr");
		var exceptionId = $thisRow.find(".global-exception-id").val();
		$thisRow.closest("tr").find('.provided-by').addClass('selectedProvider');
		var unitNumber	= $('#unitNumSearch').val();
		var poNumber	= $("#poSearch").val();
		var $editGlobalExceptionModalPromise = $.get("get-global-exceptions-edit-modal.htm", {exceptionId:exceptionId});
		$editGlobalExceptionModalPromise.done( function(data){
			
			$editExceptionModal.html(data);
			openModal($editExceptionModal);
		});
	});
	$exceptionTable.on("click", ".delete-exception", function(){
		
		var $thisRow = $(this).closest("tr");
		var exceptionId = $thisRow.find(".global-exception-id").val();
		var $deleteGlobalExceptionModalPromise = $.get("get-global-exceptions-delete-modal.htm", {exceptionId:exceptionId});
		$deleteGlobalExceptionModalPromise.done( function(data){
			$deleteExceptionModal.html(data);
			openModal($deleteExceptionModal);
		});
	});
	$editExceptionModal.on("change", "#provided-by-list", function(){
		
		var $dropdown = $('#provided-by-list');
		var $subDropdown = $('#provided-by-sub');
		var selectedOption = $dropdown.val();
		var $getSubGroupPromise = $.get("get-sub-groups.htm", {selectedOption:selectedOption});
		$getSubGroupPromise.done( function(data){
			
			$subDropdown.children().remove();
			var list = data;
			$subDropdown.append("<option value='default'></option>");
			for(var i = 0; i < list.length; i++){
				$subDropdown.append("<option value='" + list[i] + "'>" + list[i] + "</option>");
			}
		});
	});
	$editExceptionModal.on("click", ".edit-global-exception-confirm", function(){
		if($(".edit-global-exception-confirm").hasClass('buttonDisabled'))
			return false;
		var providerVendor = $('#provider-vendor').val();
		var providervendorId = $('#provider-vendor').val().split('-')[1];
		var poCategoryAssociationId = providerVendor.split("-")[0];
		var exceptionId = $('#exception-id-modal').val();
		var $globalForm = $('#edit-global-exception-form');
		var $errorDiv = $('#error-message');
		$errorDiv.addClass("hidden");
		var validated = validateFormTextFields($globalForm);
		
		if(validated){
			
			var $getEditGlobalExceptionPromise = $.get("edit-global-exception.htm", {exceptionId:exceptionId, providervendorId:providervendorId,poCategoryAssociationId:poCategoryAssociationId});
			$getEditGlobalExceptionPromise.done( function(){
					var selectedProviderVendorName = $('#provider-vendor').find('option:selected').attr('name');
					$('.selectedProvider').text(selectedProviderVendorName);
					closeModal($editExceptionModal);
			});
		}
		else{
			
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $('#provider-vendor').hasClass('errorMsgInput') ){
				
				$errorSpan.text("To be Provided By cannot be blank");
				$errorDiv.removeClass('hidden');
			}
		}
	});
	$deleteExceptionModal.on("click", ".delete-global-exception-confirm", function(){
		
		var exceptionId = $('#exception-id-modal').val();
		var $deleteExceptionPromise = $.get("delete-global-exception.htm", {exceptionId:exceptionId});
		$deleteExceptionPromise.done( function(){
			
			$exceptionTable.find('.global-exception-id').each(function(){
				
				var $this = $(this);
				var exceptionIdMatches = $this.val() == exceptionId;
				if(exceptionIdMatches){
					
					var $row = $this.closest("tr");
					$exceptionTable.fnDeleteRow( $row[0] );
				}
			});
			$deleteExceptionModal.dialog('close').empty();
		});
	});
	
	//search for  componentName
	$("#componentNameSearch").keyup(function() {
			$this = this;
		   // Show only matching TR and to hide rest
		   $.each($("#exceptionTable tbody tr"), function() {
		     //Implementing smart search 
		     var searchKeys = new Array();
		     searchKeys = $($this).val().toLowerCase().split(" ");
		     var recFound = true;
		     for (var index = 0; index < searchKeys.length; index++) {
		       if (recFound) {
		         if ($(this).find('.component-name').text().toLowerCase().indexOf(searchKeys[index]) === -1) {
		           recFound = false;
		           $(this).hide();
		         } else {
		           recFound = true;
		           $(this).show();
		         }
		       }
		     }

		   });
	 });

		//search for  vendorName
	$("#vendorNameSearch").keyup(function() {
			debugger;
			 $this = this;
		   // Show only matching TR, hide rest of them
		   $.each($("#exceptionTable tbody tr"), function() {
		     //Implementing smart search 
		     var searchKeys = new Array();
		     searchKeys = $($this).val().toLowerCase().split(" ");
		     var recFound = true;
		     for (var index = 0; index < searchKeys.length; index++) {
		       if (recFound) {
		         if ($(this).find('.po-group .vendor-name').text().toLowerCase().indexOf(searchKeys[index]) === -1) {
		           recFound = false;
		           $(this).hide();
		         } else {
		           recFound = true;
		           $(this).show();
		         }
		       }
		     }

		   });
	   });
	
	//to trigger fuzzy-search after form-submit
	var compName = $("#componentNameSearch").val();
	var vendorName = $("#vendorNameSearch").val();
	var unitNumber = $("#unitNumSearch").val();
	var poNumber =$("#poSearch").val();
		if(compName != '')
			$("#componentNameSearch").keyup();
		if(vendorName != '')
			$("#vendorNameSearch").keyup();
		if(unitNumber!='' || poNumber!=''){
		 $("#advancedSearch").removeClass('collapsedImage').addClass('expandedImage');
	  	 $("#search-content").removeClass("displayNone").addClass("displayBlock");
	   	 $("#advancedSearch").text('Hide Adavanced Search');
		}
	
$editExceptionModal.on("change", "#provider-vendor", function(){
	  var changeDowndown=new Array();
	 $("#provider-vendor").each(function(){
		 var idValue=this.id;
		 if($(this).val()!=""){
			 changeDowndown.push(idValue);
		 }
	 });
	 
	 if(changeDowndown.length==0){
		 $("#updateButton").addClass("buttonDisabled"); 
	 }else{
		 $("#updateButton").removeClass("buttonDisabled");
	 }
	
	});	

//search for unitNumber/poNumber
$("#searchException").on('click', function(e){
	if(validateSearchFields()){
		hideErrorMessages();
		processingImageAndTextHandler('visible','Loading data...');
		$('#search-exception-form').submit();
	}
});

$("input").on('click', function(e){
	hideErrorMessages();
});
});

function initDataTable($exceptionTable){
	
		$exceptionTable.dataTable({ //All of the below are optional
		"aaSorting": [[ 4, "desc" ]], //default sort column
		"bPaginate": false, //enable pagination
		"bStateSave": true,
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": false, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0 ]}],
	//	"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
	//	"iDisplayLength": 10 , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
	});
}




//reset to refresh the page
$(".reset").on('click', function(){
	 hideErrorMessages();
	$("#unitNumSearch").val('');
	$("#poSearch").val('');
	processingImageAndTextHandler('visible','Loading data...');
	window.location.href = 'global-exceptions.htm';
	
});

function toggleContent(contentId,spanId){
	
	if($("#" + contentId).is(":visible")){
		//Currently Expanded
		$("#" + spanId).removeClass('expandedImage').addClass('collapsedImage');
		$("#" + contentId).removeClass("displayBlock").addClass("displayNone");
		$("#" + spanId).text('Show Advanced Search');

	}
	else{
		//Currently Collapsed
	   $("#" + spanId).removeClass('collapsedImage').addClass('expandedImage');
	   $("#" + contentId).removeClass("displayNone").addClass("displayBlock");
	   $("#" + spanId).text('Hide Adavanced Search');

	}
}

function validateSearchFields()
{
	hideErrorMessages();
	var unitNumber = $("#unitNumSearch").val();
	var poNumber =$("#poSearch").val();
	if(poNumber==''  && unitNumber=='')
	{
		$("#poSearch,#unitNumSearch").addClass("errorMsgInput");
		$(".basicValidation").show();
		return false;
	}
		if(!validateNumbers(poNumber))
		{
			$("#poSearch").addClass("errorMsgInput");
			$(".basicPoNum").show();
			return false;
		}
		return true;
	
}

function hideErrorMessages(){
	$(".basicValidation").hide();
	$(".basicPoNum").hide();
	$("#poSearch").removeClass("errorMsgInput");
	$("#unitNumSearch").removeClass("errorMsgInput");
}

function validateNumbers(inputValue)
{
	var numbers = new RegExp("^[0-9]*$");
	if(numbers.test(inputValue))
		{
			return true ;
		}
	else
		{
			return false;
		}


}



