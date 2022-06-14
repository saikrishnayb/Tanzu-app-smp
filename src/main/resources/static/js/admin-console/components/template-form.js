var $unsavedChangesModal = $('#unsaved-changes-modal');
var $templateComponenttable = $("#template-Component-table");
var $templateTable = $("#template-table");
var $templateModal = $("#template-modal");
var $ruleModal = $('#component-rule-popup');
var $selectedTemplateType =  $('#selectedTemplateType'); // Either Active OR ALL templates
var $toggleSelection =  $('#toggleSelection'); // components display criteria
var $getAlertModal = $('#component-associatedToRules-alert-popup'); //Alert popup when component unchecked Available for Rules 
var $errorModal=$('#error-modal');
ModalUtil.initializeModal($templateModal);

$unsavedChangesModal.dialog({
  autoOpen: false,
  modal: true,
  dialogClass: 'popupModal',
  width: 370,
  minHeight: 125,
  resizable: false,
  closeOnEscape: false
});
 
$('#poCatAssID').on('change', function(event, forceContinue) {
  
  forceContinue = forceContinue === undefined? false : forceContinue;
  
  var select = this;
  
  var isNotEditPageMode = select.getAttribute('data-edit-page-mode') != 'true';
  if(isNotEditPageMode) return true;
  
  var isDirty = $templateComponenttable.data('isDirty');
  if(isDirty && !forceContinue) {
    $unsavedChangesModal.dialog('open');
    return false;
  }
  
  var selectedTemplate = select.options[select.selectedIndex].getAttribute('data-template-id');
  
  $('.save').addClass('buttonDisabled');
  processingImageAndTextHandler('visible','Loading data...');
  window.location.href = './create-modify-template-page?isCreatePage=false&templateId='+selectedTemplate+'&toggleSelection='+toggleSelection;
  
});

$templateComponenttable.on('change', 'input', function() {
  $templateComponenttable.data('isDirty', true);
});

$('.po-cat-ass-id-search').on('click', function() {
  $('#poCatAssID').trigger('change');
});

$('.unsaved-modal-close').on('click', function() {
  $unsavedChangesModal.dialog('close');
});

$('.unsaved-modal-continue').on('click', function() {
  $('#poCatAssID').trigger('change', [true]);
});

$templateTable.on('click', '.sequence-edit', function() {
  
  var templateId = this.parentNode.querySelector('input.template-id').value;
  
  var $getComponentSequencePromise = $.get('get-template-component-sequence', {templateId: templateId});
  
  $getComponentSequencePromise.done(function(modalContent) {
    $templateModal.html(modalContent);
    ModalUtil.openModal($templateModal);
  }).fail(function() {
    parent.window.displayAlertModal('Something went wrong with the request, please try again later.');
  });
  
});

var toggleSelection=$toggleSelection.val();
var templateSelection=$selectedTemplateType.val();
$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-template");
	$('.back').on('click', function(){
	  processingImageAndTextHandler('visible','Loading data...');
	  window.location.href = './template';
	});
	
	$templateForm=$("#template-form");
	var $confirmOrgDeactivationModal = $('#deactivate-modal');
	var $confirmOrgActivationModal =$('#activate-modal');
	
	//$chekIds=$( "input[id^='chekIds']");
	var	$saveTemplateCreate=$("#save-template-create");
	var	$saveTemplateEdit=$("#save-template-edit");
	var $editChckBox=$("input[id^='editable-'");
	var $requiredChckBox=$("input[id^='required-'");
	var $viewChckBox=$("input[id^='viewable-'");
	var $forRulesChckBox=$("input[id^='forRules-'");
	var iDisplayLength = 10;//tableRowLengthCalc();
	//org summary table
	$templateTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bStateSave": true,
		"bAutoWidth": false, //cray cray
		"bLengthChange": true, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"aoColumnDefs": [
		                 {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 {"sWidth": "75px", "aTargets": [ 0 ] },
		                 {"bSearchable": false, "aTargets": [0]}
		                ],
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": 100 , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "No Results Found"}, //Message displayed when no records are found
		"search": {
			type: "text",
			bRegex: true,
			bSmart: true
			 },
		"fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
      	var paginateRow = $(this).parent().children('div.dataTables_paginate');
      	var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);
      	 
      	if (pageCount > 1)  {
      		paginateRow.css("display", "block");
      	} else {
      		paginateRow.css("display", "none");
      	}
      	
      	//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
      	var infoRow = $(this).parent().children('div.dataTables_info');
      	var rowCount = this.fnSettings().fnRecordsDisplay();
      	if (rowCount > 0) {
      		infoRow.css("display", "block");
      	} else {
      		infoRow.css("display", "none");
      	}
      	
		}
	} );
	
	var tempCompFilters='<div id="org-desc-div" style="float: left; text-align: right;">'+
					'<a id="showActiveTemplates">Show Active Templates </a>&nbsp;&nbsp;|&nbsp;&nbsp;<a id="showAllTemplates">Show All Templates</a>&nbsp;&nbsp;'+
				'</div>';
	$("#template-table_wrapper").prepend(tempCompFilters);
	$showActiveTemplates=$("#template-table_wrapper").find("#showActiveTemplates");
	$showAllTemplates=$('#template-table_wrapper').find("#showAllTemplates");
	$templateComponenttable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bAutoWidth": false, //cray cray
		"bStateSave": true,
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"sRowSelect": "multi",
		"aoColumnDefs": [
		                 {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] }
		                 ],
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": iDisplayLength , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "No Results Found"}, //Message displayed when no records are found
		"fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
	var paginateRow = $(this).parent().children('div.dataTables_paginate');
	var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);
	 
	if (pageCount > 1)  {
		paginateRow.css("display", "block");
	} else {
		paginateRow.css("display", "none");
	}
	
	//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
	var infoRow = $(this).parent().children('div.dataTables_info');
	var rowCount = this.fnSettings().fnRecordsDisplay();
	if (rowCount > 0) {
		infoRow.css("display", "block");
	} else {
		infoRow.css("display", "none");
	}
	
	//to highlight the selected component   
	var seletedComp=$("#tempCompId").val();
	if(seletedComp != null){
		   $("#component-"+seletedComp).addClass("row_selected");
	}
	   
		}
	} );
	var strHTML='<div id="org-desc-div" style="float: left; text-align: right;">'+
					'<label>&nbsp;<span class=requiredField>*</span></label>'+ 
					'<a id="showAll"> Show All</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a id="showSelected">Show Selected</a>|&nbsp;&nbsp;<a id="showRules">Show with Rules</a>'+
				'</div>';
	$("#template-Component-table_wrapper").prepend(strHTML);
	$showAll=$('#template-Component-table_wrapper').find(' #showAll');
	$showSelected=$('#template-Component-table_wrapper').find("#showSelected");
	$showRules =$('#template-Component-table_wrapper').find('#showRules');
	$confirmOrgDeactivationModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$confirmOrgActivationModal.dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 110,
		resizable: false,
		closeOnEscape: false,
		open: function(event, ui) { }
	});
	
	$saveTemplateCreate.on("click",function(){
		createOrUpdate(true);
	});
	
	$saveTemplateEdit.on("click",function(){
		createOrUpdate(false);
	});
	
	$forRulesChckBox.on("click",function(){
		var str =this.id.split("-").pop();
		var $curforRulesObj=$( "#forRules-"+str);
		var $curEditObj=$( "#editable-"+str);
		var $curDispOtherPOObj=$( "#dispOtherPO-"+str);
		var $curExcelObj=$( "#excel-"+str);
		var	$includeClassId=$('#template-comp-tr-'+str);
		if($(this).is(':checked')){
			$curDispOtherPOObj.attr("disabled", false);
			$curExcelObj.attr("disabled", false);
			$includeClassId.html("hasSelected");
		}else{
			$curEditObj.attr("disabled", false);
			$curDispOtherPOObj.attr("disabled", true);
			$curExcelObj.attr("disabled", true);
			$curDispOtherPOObj.attr('checked', false);
			$curExcelObj.attr('checked', false);
			$includeClassId.html("");
			isAssociatedToRule(this,$curforRulesObj);
			
		}
	});
	
	$requiredChckBox.on("click",function(){
		var str =this.id.split("-").pop();
		var	$curforRulesObj=$( "#forRules-"+str);
		var	$curEditObj=$( "#editable-"+str);
		var	$curViewObj=$( "#viewable-"+str);
		var	$curDispOtherPOObj=$( "#dispOtherPO-"+str);
		var $curExcelObj=$( "#excel-"+str);
		var	$includeClassId=$('#template-comp-tr-'+str);
		if($(this).is(':checked')){
			$curEditObj.attr('checked', true);
			$curEditObj.attr("disabled", true);
			$curDispOtherPOObj.attr("disabled", false);
			$curExcelObj.attr("disabled", false);
			$curViewObj.attr('checked', true);
			$curViewObj.attr("disabled", true);
			$curforRulesObj.attr('checked', true);
			$curforRulesObj.attr("disabled", true);
			$includeClassId.html("hasSelected");
		}else{
			$curEditObj.attr("disabled", false);
			$includeClassId.html("");
		}
	});
	
	$editChckBox.on("click",function(){
		var str =this.id.split("-").pop();
		var	$curforRulesObj=$( "#forRules-"+str);
		var	$curDispOtherPOObj=$( "#dispOtherPO-"+str);
		var	$curExcelObj=$( "#excel-"+str);
		var	$curViewObj=$( "#viewable-"+str);
		var	$includeClassId=$('#template-comp-tr-'+str);
		if($(this).is(':checked')){
			$curDispOtherPOObj.attr("disabled", false);
			$curExcelObj.attr("disabled", false);
			$curViewObj.attr('checked', true);
			$curViewObj.attr("disabled", true);
			$curforRulesObj.attr("checked", true);
			$curforRulesObj.attr("disabled", true);
			$includeClassId.html("hasSelected");
		}else{
			$curViewObj.attr("disabled", false);
			$includeClassId.html("");
		}
	});
	
	$viewChckBox.on("click",function(){
		var str =this.id.split("-").pop();
		var	$curforRulesObj=$( "#forRules-"+str);
		var	$curDispOtherPOObj=$( "#dispOtherPO-"+str);
		var	$curExcelObj=$( "#excel-"+str);
		var	$includeClassId=$('#template-comp-tr-'+str);
		if($(this).is(':checked')){
			$curDispOtherPOObj.attr("disabled", false);
			$curExcelObj.attr("disabled", false);
			$curforRulesObj.attr('checked', true);
			$curforRulesObj.attr("disabled", true);
			$includeClassId.html("hasSelected");
		}else{
			$curforRulesObj.attr('checked', true);
			$curforRulesObj.attr("disabled", false);
			$includeClassId.html("");
		}
	});
	//deactivate modal
	$templateTable.on("click", ".deactivate", function(){
		
		var $this =  $(this);
		var templateName = $this.closest('.template-row').find('.template-name').text();
		var templateId = $this.closest('.template-row').find('.template-id').val();
		var $getDeactivateorgModalContentPromise = $.get('get-deactivate-template-modal-content', {templateName:templateName,templateId:templateId});
		
		
		$getDeactivateorgModalContentPromise.done(function(data){
			$confirmOrgDeactivationModal.html(data);
			openModal($confirmOrgDeactivationModal);
			
		});
		
		openModal($confirmOrgDeactivationModal);
	});
	
	//Activate modal
	$templateTable.on("click", ".activate", function(){
		
		var $this =  $(this);
		var templateName = $this.closest('.template-row').find('.template-name').text();
		var templateId = $this.closest('.template-row').find('.template-id').val();
		$confirmOrgActivationModal.find("#templateName").text(templateName);
		$confirmOrgActivationModal.find("#template-id").val(templateId);
		openModal($confirmOrgActivationModal);
		
	});
	
	$templateForm.on('keypress', function(e) {
		if (e.which == 13) {
			$saveTemplateEdit.trigger('click');
			event.preventDefault();
		}
	});	
	
	//deactivate execution
	$confirmOrgDeactivationModal.on("click", ".deactivate-confirm", function(){
		var templateId = $('#template-id').val();
		var $deactivateorgPromise = $.post('deactivate-template', {templateId:templateId});
		$deactivateorgPromise.done(function(data){
			closeModal($confirmOrgDeactivationModal);
			location.assign('./template?selectedTemplateType='+templateSelection);
		});
	});
	
	//Activate execution
	$confirmOrgActivationModal.on("click", ".activate-confirm", function(){
		var templateId = $('#template-id').val();
		var $activateorgPromise = $.post('activate-template', {templateId:templateId});
		$activateorgPromise.done(function(data){
		location.assign('./template?selectedTemplateType='+templateSelection);
		});
	});
	
	
	//edit modal
	$templateTable.on("click", ".edit-template", function(){
		var $this =  $(this);
		var templateID = $this.closest('.template-row').find('.template-id').val();
		location.assign('./create-modify-template-page?isCreatePage=false&templateId=' + templateID);
	});
	var isCreateOrEdit=$('#is-create-page-id').val();
	 $showSelected.click(function(e){
		 $(this).css("color", "blue");
		 $(this).css("font-weight", "bold");
		 $showAll.css("color", "");
		 $showAll.css("font-weight", "normal");
		 $showRules.css("color", "");
		 $showRules.css("font-weight", "normal");
		 toggleSelection="SELECTED";
		 //false value for fnDraw function will retain pagination.
		 $templateComponenttable.fnDraw(localStorage.getItem("retainPagination")=='NO'); 
	 });
	 
	 $showAll.click(function(e){
		 $(this).css("color", "blue");
		 $(this).css("font-weight", "bold");
		 $showSelected.css("color", "");
		 $showSelected.css("font-weight", "normal");
		 $showRules.css("color", "");
		 $showRules.css("font-weight", "normal");
		 toggleSelection="ALL";
		 $templateComponenttable.fnDraw(localStorage.getItem("retainPagination")=='NO');
	 });
	 
	 $showRules.click(function(e){
		 $(this).css("color", "blue");
		 $(this).css("font-weight", "bold");
		 $showSelected.css("color", "");
		 $showSelected.css("font-weight", "normal");
		 $showAll.css("color", "");
		 $showAll.css("font-weight", "normal");
		 toggleSelection="RULES";
		 $templateComponenttable.fnDraw(localStorage.getItem("retainPagination")=='NO');
	 });
	 
	 $showAllTemplates.click(function(e){
		 $(this).css("color", "blue");
		 $(this).css("font-weight", "bold");
		 $showActiveTemplates.css("color", "");
		 $showActiveTemplates.css("font-weight", "normal");
		 templateSelection="ALL";
		 $templateTable.fnDraw();
	 });
	 
	 $showActiveTemplates.click(function(e){
		 $(this).css("color", "blue");
		 $(this).css("font-weight", "bold");
		 $showAllTemplates.css("color", "");
		 $showAllTemplates.css("font-weight", "normal");
		 templateSelection="ACTIVE";
		 $templateTable.fnDraw();
	 });
	 
	 $templateComponenttable.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {
         if(toggleSelection=="SELECTED"){
                var row = $($("#template-Component-table").dataTable().fnGetNodes(iDataIndex));
                var val =row.find(("#template-comp-tr-"+iDataIndex));
                if(val[0].innerHTML =='hasSelected'){
                      return true;
                }else {
                      return false;
                }
         }else if(toggleSelection=="RULES"){// show components which are having rules
                var row = $($("#template-Component-table").dataTable().fnGetNodes(iDataIndex));
                var val =row.find(("#componentWithRule-"+iDataIndex));
                if(val[0].innerHTML =='hasRule'){
                      return true;
                }else {
                      return false;
                }
         }else{
                return true;
         }
  });
    // To retain the filtered criteria
	if(toggleSelection == "ALL" || isCreateOrEdit == "true"){
		$showAll.trigger( "click" );
	 }else if(toggleSelection == "SELECTED"){
		$showSelected.trigger( "click" );
	 }else{
		$showRules.trigger( "click" );
	 }
	localStorage.setItem("retainPagination", "NO");
 	$templateTable.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {
		 if(templateSelection=="ACTIVE"){
			 var row = $($templateTable.dataTable().fnGetNodes()[iDataIndex]);
			 var val =row.find(("#status-"+iDataIndex)).val();
			if(val =='A'){
				return true;
			}else {
				return false;
			}
		}else{
			return true;
		}
	});
 	
 	$('#activate-modal').on("click", ".cancel-activate", function(){
 		closeModal($confirmOrgActivationModal);
 	});
 	if(templateSelection=='ACTIVE'){
        $showActiveTemplates.trigger("click");
 	}else{
 		$showAllTemplates.trigger("click");
 	}

});

function createOrUpdate(isCreate){
	$('.error-messages-container').addClass('displayNone');
	var tempDesc=$("#templateDesc").val();
	var poCatAssID=$("#poCatAssID").val();
	var currentTimeStamp = new Date().getTime();
	var checkedListJSON='';
	var isFirst=true;
	
	$($("#template-Component-table").dataTable().fnGetNodes()).each(function(index) {
		var row = $(this);
		var checkBoxArry=row.find(':checkbox');
		var hiddenArry=row.find('input:hidden');
		if(checkBoxArry !=null && checkBoxArry.length >0){
			var forRules=checkBoxArry[0].checked;
			var viewable=checkBoxArry[1].checked;
			var editable=checkBoxArry[2].checked;
			var required=checkBoxArry[3].checked;
			var otherPO=checkBoxArry[4].checked;
			var excel=checkBoxArry[5].checked;
			var hasTempCompId=checkBoxArry[6].checked;// to check existing component
			
			if(viewable || editable || required || otherPO || excel || forRules || hasTempCompId){
				if(hiddenArry !=null && hiddenArry.length >0){
					var componentId=hiddenArry[0].value;
					var componentName=hiddenArry[1].value;
					var templateComponentId = hiddenArry[2].value;
					checkedListJSON=getComponetJSON(componentName,componentId,templateComponentId,forRules, viewable, editable, required, otherPO, excel, checkedListJSON, isFirst);
					isFirst=false;
				}
			}
		}
	});
	if(validate($templateForm)){
		var url='create-template?';
		if(!isCreate){
			var templateId=$("#template-id").val();
			url='update-template?templateId='+templateId+"&";
		}	
		$.ajax({
			url: url+'tempDesc='+tempDesc+ '&poCatAssID='+poCatAssID+ '&_=' + currentTimeStamp,
			data:'['+checkedListJSON+']',
	        processData: false,
	        contentType: 'application/json',
	        type: 'POST',
	        success: function(status){
			        	if($.isNumeric(status)){
			        	     localStorage.setItem("retainPagination", "YES");
			        		 processingImageAndTextHandler('visible','Loading data...');
				        	 location.assign('./create-modify-template-page?isCreatePage=false&templateId='+status+'&toggleSelection='+toggleSelection);
			        	}else{
			        		$errorModal.text(status);
							$errorModal.dialog("option", "title", "Error");
				        	ModalUtil.openModal($errorModal);
			        	}
			  }
		});
		
	}else{
		$('.error-messages-container').removeClass('displayNone');
	}
}

function getComponetJSON(compName,compId,templateComponentId,forRules,viewable,editable,required,otherPO,excel,checkedListJSON,isFirst){
	var component={};
	component.componentName=compName;
	component.componentId=compId;
	component.viewable=viewable;
	component.editable=editable;
	component.required=required;
	component.dispOtherPO=otherPO;
	component.excel=excel;
	component.forRules=forRules;
	component.templateComponentId=templateComponentId;
	if(isFirst){
		checkedListJSON=checkedListJSON+JSON.stringify(component);
	}else{
		checkedListJSON=checkedListJSON+","+JSON.stringify(component);
	}
	return checkedListJSON;
}
function msieversion() {

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))     // If Internet Explorer, return version number
       return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
    else                 // If another browser, return 0
        return 0;
}


function validate($editForm){

	var flag = true;
	if(validateFormTextFields($editForm) == false){
		if($("#templateDesc").hasClass('errorMsgInput')){
			$errorModal.text("Error Template Desc invalid!");
			$errorModal.dialog("option", "title", "Error");
        	ModalUtil.openModal($errorModal);
		}
		if($("#poCatAssID").hasClass('errorMsgInput')){
			$errorModal.text("Error Cat/Sub-Cat invalid!");
			$errorModal.dialog("option", "title", "Error");
        	ModalUtil.openModal($errorModal);
		}
		flag = false;
	}
	return flag;
}

function getRulesByTemplateComponentId(templateComponentId,templateName){
	var templateId = $("#template-id").val();
	$("#templateComponentId").val(templateComponentId); 
	var $getRulesByTemplateComponentId = $.get('get-rule-popup', {templateComponentId:templateComponentId,templateId:templateId});
	$getRulesByTemplateComponentId.done(function(modalContent){
		$ruleModal.html(modalContent);
		$ruleModal.dialog('option', 'title', "Configure Rules for "+templateName); 
		 openModal($ruleModal);
		 var ruleCount=$("#ruleCount").val();
			if(ruleCount==0){
				addNewRule();
			}
	}).fail(function(jqXHR, textStatus,errorThrown) {
	 	   showLoading=false;
	 	  	$errorModal.text(jqXHR.responseText);
	 	 	$errorModal.dialog("option", "title", "Error");
	      	ModalUtil.openModal($errorModal);
		  }
		);		
}

function isAssociatedToRule(currentRow,$curforRulesObj){
		var row = $(currentRow).closest('#template-Component-table tbody tr');
		var templateId = $("#template-id").val();
		var componentId = row.find('#templateName').attr('data-compId');
		var componentName = row.find('#templateName').attr('data-fullname');
		var $getRuleNamesByTemplateComponentId = $.get('check-iscomponent-associatedToRules',{templateId:templateId,componentId:componentId,componentName:componentName});
		$getRuleNamesByTemplateComponentId.done(function(modalContent){
			if(modalContent.indexOf("<li>") != -1){
				$curforRulesObj.attr("checked", true);
				$getAlertModal.html(modalContent);
				openModal($getAlertModal);
			}
			
		});
}

$ruleModal.dialog({
	autoOpen: false,
	modal: true,
	dialogClass: 'popupModal',
	width: 1200,
	maxHeight:500,
	my: "center",
	at: "center",
	of: window,
	left:120,
	resizable: false,
	closeOnEscape: false,
	create: function() {
           $(this).closest('div.ui-dialog')
                  .find('.ui-dialog-titlebar-close')
                  .click(function(e) {
                	  localStorage.setItem("retainPagination", "YES");
                	  processingImageAndTextHandler('visible','Loading data...');
                	  var templateId = $("#template-id").val();
                	  location.assign('./create-modify-template-page?isCreatePage=false&templateId='+templateId+'&toggleSelection='+toggleSelection);
               	   	  return false;
           });
       }
});


$getAlertModal.dialog({
	autoOpen: false,
	modal: true,
	dialogClass: 'popupModal',
	width: 450,
	minHeight: 110,
	resizable: false,
	closeOnEscape: false,
	open: function(event, ui) { }
});


function addNewRule(){
	var templateComponentId = $("#templateComponentId").val();
    var templateId = $("#template-id").val();
	var $createNewRule = $.get('load-create-rule',{templateComponentId:templateComponentId,templateId:templateId});
	$createNewRule.done(function(modalContent){
		$(".ruleDescription").html(modalContent);
		showLoading=false;
	});
}
//for exception display on modal
$errorModal.dialog({
	autoOpen: false,
	modal: true,
	dialogClass: 'popupModal',
	width: 500,
	minHeight: 0,
	resizable: false,
	closeOnEscape: true
});	

