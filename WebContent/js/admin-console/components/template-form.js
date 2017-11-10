var $errMsg = $('.edit-buttons').find('.error-messages-container').find('.errorMsg');
var toggleSelection="ALL";
$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-template");
	$('.back').on('click', function(){
		parent.history.back();
		return false;
	});
	
	$templateForm=$("#template-form");
	var $confirmOrgDeactivationModal = $('#deactivate-modal');
	$templateTable=$("#template-table");
	$templateComponenttable=$("#template-Component-table");
	//$chekIds=$( "input[id^='chekIds']");
	$saveTemplateCreate=$("#save-template-create");
	$saveTemplateEdit=$("#save-template-edit");
	$editChckBox=$("input[id^='editable-'");
	$requiredChckBox=$("input[id^='required-'");
	$dispOtherPOChckBox=$("input[id^='dispOtherPO-'");
	$excelChckBox=$("input[id^='excel-'");
	$viewChckBox=$("input[id^='viewable-'");
	var iDisplayLength = 10;//tableRowLengthCalc();
	//org summary table
	$templateTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bStateSave": true,
		"bAutoWidth": false, //cray cray
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"aoColumnDefs": [
		                 {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] }
		                 ],
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": iDisplayLength , //number of records per page for pagination
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
	
	
	$templateComponenttable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"bAutoWidth": false, //cray cray
		//"bStateSave": true,
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
		}
	} );
	//$("#template-Component-table_filter").hide();
	var strHTML='<div id="org-desc-div" style="float: left; text-align: right;">'+
					'<label>&nbsp;<span class=requiredField>*</span></label>'+ 
					'<a id="showAll"> Show All</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a id="showSelected">Show Selected</a>'+
				'</div>';
	$("#template-Component-table_wrapper").prepend(strHTML);
	$showAll=$('#template-Component-table_wrapper').find(' #showAll');
	$showSelected=$('#template-Component-table_wrapper').find("#showSelected");
	
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
	

	$saveTemplateCreate.on("click",function(){
		createOrUpdate(true);
	});
	
	$saveTemplateEdit.on("click",function(){
		createOrUpdate(false);
	});
	
	$requiredChckBox.on("click",function(){
		var str =this.id.split("-").pop();
		 $curEditObj=$( "#editable-"+str);
		 $curViewObj=$( "#viewable-"+str);
		 $curDispOtherPOObj=$( "#dispOtherPO-"+str);
		 $curExcelObj=$( "#excel-"+str);
		 $includeClassId=$('#template-comp-tr-'+str);
		if($(this).is(':checked')){
			$curEditObj.attr('checked', true);
			$curEditObj.attr("disabled", true);
			$curDispOtherPOObj.attr("disabled", false);
			$curExcelObj.attr("disabled", false);
			$curViewObj.attr('checked', true);
			$curViewObj.attr("disabled", true);
			$includeClassId.html("1~~9");
		}else{
			//$curEditObj.attr('checked', false);
			$curEditObj.attr("disabled", false);
			//$curDispOtherPOObj.attr("disabled", true);
			//$curExcelObj.attr("disabled", true);
			//$curDispOtherPOObj.attr('checked', false);
			//$curExcelObj.attr('checked', false);
			//$curViewObj.attr('checked', false);
			//$curViewObj.attr("disabled", false);
			$includeClassId.html("");
		}
	});
	
	$editChckBox.on("click",function(){
		var str =this.id.split("-").pop();
		 $curDispOtherPOObj=$( "#dispOtherPO-"+str);
		 $curExcelObj=$( "#excel-"+str);
		 $curViewObj=$( "#viewable-"+str);
		 $includeClassId=$('#template-comp-tr-'+str);
		if($(this).is(':checked')){
			$curDispOtherPOObj.attr("disabled", false);
			$curExcelObj.attr("disabled", false);
			$curViewObj.attr('checked', true);
			$curViewObj.attr("disabled", true);
			//$includeClassId.addClass("include-filter-class");
			$includeClassId.html("1~~9");
		}else{
			//$curDispOtherPOObj.attr("disabled", true);
			//$curExcelObj.attr("disabled", true);
			//$curDispOtherPOObj.attr('checked', false);
			//$curExcelObj.attr('checked', false);
			//$curViewObj.attr('checked', false);
			$curViewObj.attr("disabled", false);
			//$includeClassId.removeClass("include-filter-class");
			$includeClassId.html("");
		}
	});
	
	$viewChckBox.on("click",function(){
		var str =this.id.split("-").pop();
		 $curDispOtherPOObj=$( "#dispOtherPO-"+str);
		 $curExcelObj=$( "#excel-"+str);
		 $includeClassId=$('#template-comp-tr-'+str);
		if($(this).is(':checked')){
			$curDispOtherPOObj.attr("disabled", false);
			$curExcelObj.attr("disabled", false);
			//$includeClassId.addClass("include-filter-class");
			$includeClassId.html("1~~9");
		}else{
			$curDispOtherPOObj.attr("disabled", true);
			$curExcelObj.attr("disabled", true);
			$curDispOtherPOObj.attr('checked', false);
			$curExcelObj.attr('checked', false);
			//$includeClassId.removeClass("include-filter-class");
			$includeClassId.html("");
		}
	});
	//deactivate modal
	$templateTable.on("click", ".deactivate", function(){
		
		var $this =  $(this);
		var templateName = $this.closest('.template-row').find('.template-name').text();
		var templateId = $this.closest('.template-row').find('.template-id').val();
		var $getDeactivateorgModalContentPromise = $.get('get-deactivate-template-modal-content.htm', {templateName:templateName,templateId:templateId});
		
		
		$getDeactivateorgModalContentPromise.done(function(data){
			$confirmOrgDeactivationModal.html(data);
			openModal($confirmOrgDeactivationModal);
			
		});
		
		openModal($confirmOrgDeactivationModal);
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
		var $deactivateorgPromise = $.post('delete-template.htm', {templateId:templateId});
		
		$deactivateorgPromise.done(function(data){
			$('.template-id').each(function(){
				var orgIdMatch = $(this).val();
				var isorgIdMatch = (orgIdMatch == templateId);
				if(isorgIdMatch){
					var $orgRow = $(this).closest('.template-row');
					var nRow = $orgRow[0];
					
					$('#template-table').dataTable().fnDeleteRow(nRow);
				}
			});
			closeModal($confirmOrgDeactivationModal);
		});
	});
	
	//edit modal
	$templateTable.on("click", ".edit-template", function(){
		var $this =  $(this);
		var templateID = $this.closest('.template-row').find('.template-id').val();
		location.assign('./create-modify-template-page.htm?isCreatePage=false&templateId=' + templateID);
	});
	var isCreateOrEdit=$('#is-create-page-id').val();
	 $showSelected.click(function(e){
		 $(this).css("color", "blue");
		 $(this).css("font-weight", "bold");
		 $showAll.css("color", "");
		 $showAll.css("font-weight", "normal");
		 toggleSelection="SELECTED";
		 $templateComponenttable.fnDraw();
	 });
	 
	 $showAll.click(function(e){
		 $(this).css("color", "blue");
		 $(this).css("font-weight", "bold");
		 $showSelected.css("color", "");
		 $showSelected.css("font-weight", "normal");
		 toggleSelection="ALL";
		 $templateComponenttable.fnDraw();
	 });
	 $templateComponenttable.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {
		 if(toggleSelection=="SELECTED"){
			 var row = $($("#template-Component-table").dataTable().fnGetNodes()[iDataIndex]);
			 var divArry=row.find('div');
			if(divArry[0].innerHTML =='1~~9'){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	});
	 
 	if(isCreateOrEdit !=null || isCreateOrEdit !=undefined){
 		if(isCreateOrEdit==='true'){
 			$showAll.trigger( "click" );
 		}else{
 			 $showSelected.trigger( "click" );
 		}
 	}
});

function createOrUpdate(isCreate){
	$('.error-messages-container').addClass('displayNone');
	var checkedList=[];
	var tempDesc=$("#templateDesc").val();
	var poCatAssID=$("#poCatAssID").val();
	var currentTimeStamp = new Date().getTime();
	var checkedListJSON='';
	var isFirst=true;
	var i=1;
	
	$($("#template-Component-table").dataTable().fnGetNodes()).each(function(index) {
		var row = $(this);
		var checkBoxArry=row.find(':checkbox');
		var hiddenArry=row.find('input:hidden');
		if(checkBoxArry !=null && checkBoxArry.length >0){
			var viewable=checkBoxArry[0].checked;
			var editable=checkBoxArry[1].checked;
			var required=checkBoxArry[2].checked;
			var otherPO=checkBoxArry[3].checked;
			var excel=checkBoxArry[4].checked;
			if(viewable || editable || required || otherPO || excel){
				if(hiddenArry !=null && hiddenArry.length >0){
					var componentId=hiddenArry[0].value;
					var componentName=hiddenArry[1].value;
					//console.log("componentName: "+componentName);
					checkedListJSON=getComponetJSON(componentName,componentId,viewable, editable, required, otherPO, excel, checkedListJSON, isFirst);
					isFirst=false;
				}
			}
		}
	});
	if(validate($templateForm)){
		var url='create-template.htm?';
		if(!isCreate){
			var templateId=$("#template-id").val();
			url='update-template.htm?templateId='+templateId+"&";
		}	
		var $createPromise = $.ajax({
			url: url+'tempDesc='+tempDesc+ '&poCatAssID='+poCatAssID+ '&_=' + currentTimeStamp,
			data:'['+checkedListJSON+']',
	        processData: false,
	        contentType: 'application/json',
	        type: 'POST'
		});
		
		$createPromise.done(function(data){
			location.assign('./template.htm');
		});
		$createPromise.fail(function (jqXHR, textStatus) {
		     if(jqXHR.responseText.indexOf('Template Already exists.')>0){
		    	 $errMsg.text('Template Already exists.');
		    	 $('.error-messages-container').removeClass('displayNone');
			  }
		});
	}else{
		$('.error-messages-container').removeClass('displayNone');
	}
}

function getComponetJSON(compName,compId,viewable,editable,required,otherPO,excel,checkedListJSON,isFirst){
	var component={};
	component.componentName=compName;
	component.componentId=compId;
	component.viewable=viewable;
	component.editable=editable;
	component.required=required;
	component.dispOtherPO=otherPO;
	component.excel=excel;
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
			$errMsg.text('Error Template Desc invalid!');
		}
		if($("#poCatAssID").hasClass('errorMsgInput')){
			$errMsg.text('Error Cat/Sub-Cat invalid!');
		}
		flag = false;
	}
	else if(!$viewChckBox.is(':checked') && !$editChckBox.is(':checked') &&  !$requiredChckBox.is(':checked') && !$dispOtherPOChckBox.is(':checked')
			&& !$excelChckBox.is(':checked')){
		$errMsg.text('Error Component selection invalid!');
		flag = false;
	}
	
	return flag;
}