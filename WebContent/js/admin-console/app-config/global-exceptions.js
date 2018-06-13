$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-global-exceptions");
	var $exceptionTable = $('#exceptionTable');
	initDataTable($exceptionTable);
	var $editExceptionModal = $("#modal-edit-global-exception");
	var $deleteExceptionModal = $("#modal-delete-global-exception");
	var showLoading = false;
	$exceptionTable.fnDraw();
	toggleClear();
	
	$editExceptionModal.dialog({
		
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 700,
		minHeight: 235,
		resizable: false,
		position: { my: "center", at: "center", of: window },
		title: 'Edit Global Exception',
		closeOnEscape: false,
		close:function(event,ui){
			$('#edit-global-exception-modal').remove(); 
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
		position: { my: "center", at: "center", of: window },
		closeOnEscape: false,
		close:function(event,ui){
			$('#deleteModal').remove();
		}
	});
	
	$exceptionTable.on("click", ".edit-exception", function(){
		
		var $thisRow = $(this).closest("tr");
		var unitNumber = $("#searchException").attr("unitNumSearch");
		var poNumber =$("#searchException").attr("poSearch");
		var exceptionId = $thisRow.find(".global-exception-id").val();
		$thisRow.closest("tr").find('.provided-by').addClass('selectedProvider');
		$thisRow.closest("tr").find('.modified-date').addClass('currentModifiedDate');
		var $editGlobalExceptionModalPromise = $.get("get-global-exceptions-edit-modal.htm", {exceptionId:exceptionId,unitNumber:unitNumber,poNumber:poNumber});
		$editGlobalExceptionModalPromise.done( function(data){
			
			$editExceptionModal.html(data);
			openModal($editExceptionModal);
			showLoading = false;
		});
	});
	$exceptionTable.on("click", ".delete-exception", function(){
		
		var $thisRow = $(this).closest("tr");
		var exceptionId = $thisRow.find(".global-exception-id").val();
		var $deleteGlobalExceptionModalPromise = $.get("get-global-exceptions-delete-modal.htm", {exceptionId:exceptionId});
		processingImageAndTextHandler('visible','Loading data...');
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
			
			$.ajax({
				url : "edit-global-exception.htm",
				type: "GET",  
				data: {exceptionId:exceptionId, providervendorId:providervendorId,poCategoryAssociationId:poCategoryAssociationId},
				success : function() {
					 closeModal($editExceptionModal);
					 reload();
				}
			}); 
		}
		else{
			showLoading = false;
			var $errorSpan = $('#message-span');
			// check each form field for error class
			if( $('#provider-vendor').hasClass('errorMsgInput') ){
				
				$errorSpan.text("To be Provided By cannot be blank");
				$errorDiv.removeClass('hidden');
			}
		}
	});
	$(document).ajaxComplete(function() {
		if(showLoading){
			processingImageAndTextHandler('visible','Processing...');
		}else{
			processingImageAndTextHandler('hidden');
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
			$deleteExceptionModal.dialog('close');
		});
	});
	
	//search for  componentName & vendorName
	$(".fuzzySearch").on('input',function(){
		$exceptionTable.fnDraw();
	 });

	function toggleClear(){
		var compName = $("#componentNameSearch").val();
		var vendorName = $("#vendorNameSearch").val();
		var unitNum = $("#unitNumSearch").val();
		var poNum = $("#poSearch").val();
		if(compName != '' || vendorName != '' || unitNum != '' || poNum != ''){
			 $(".reset").removeClass("buttonDisabled");
		}else{
			 $(".reset").addClass("buttonDisabled");
		}
		
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

function reload(){
	showLoading = true;
	$('#search-exception-form').submit();
}


//reset to refresh the page
$(".reset").on('click', function(){
	if($('.reset').hasClass('buttonDisabled') )
		return false;
	hideErrorMessages();
	$("#unitNumSearch").val('');
	$("#poSearch").val('');
	$("#componentNameSearch").val('');
	$("#vendorNameSearch").val('');
	processingImageAndTextHandler('visible','Loading data...');
	window.location.href = 'global-exceptions.htm';
});

$('#search-exception-form input').on('input',function(){
	toggleClear();
});

});

function initDataTable($exceptionTable){
	
		$exceptionTable.dataTable({ //All of the below are optional
		"aaSorting": [[ 4, "desc" ]], //default sort column
		"bPaginate": false, //enable pagination
		"bStateSave": true,
		"sScrollY"  : "435px",//enable scroll 
		"sScrollX"  : "100%", 
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": false, //Showing 1 to 10 of 11 entries
		"aoColumnDefs": [{"bSortable": false, "aTargets": [ 0,1,2,3 ]}],
		"oLanguage": {"sEmptyTable": "No matching records found"}, //Message displayed when no records are found
		"fnDrawCallback": function() { 
			//This change would allow the data table 
        	//to adjust with dynamically after the page has been loaded
        	$(".dataTables_scrollHead").css("width","99.8%");
        	$(".dataTables_scrollHeadInner").css("width","98.5%");
        	$(".dataTable").css("width","99.8%");
        	$(".dataTables_scrollBody").css("width","100%"); 
		}
	});
		
	$('#exceptionTable_filter').hide();
	$exceptionTable.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {
		
		var  compSearch = $("#componentNameSearch").val().toLowerCase();
		var  vendorSearch= $("#vendorNameSearch").val().toLowerCase();
		 var row = $($exceptionTable.dataTable().fnGetNodes()[iDataIndex]);
		 var componentName =row.find('.component-name').text().toLowerCase();
		 var  vendorName= row.find('.po-group .vendor-name').text().toLowerCase();
		 if(compSearch == "" && vendorSearch == ""){
			 return true;
		 }
		 else if(compSearch != "" && vendorSearch == ""){
			 if(componentName.indexOf(compSearch) > -1){
				 return true;
			 }
		 }else if(compSearch == "" && vendorSearch != ""){
			 if(vendorName.indexOf(vendorSearch) > -1){
				 return true;
			 }
		 }else{
			 if(vendorName.indexOf(vendorSearch) > -1 && componentName.indexOf(compSearch) > -1){
				 return true;
			 }
		 }
		return false;	 
		
		});
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

