/**
 * 
 */
var $createRuleTable;
var grpIndex=0;//will assign the max group value from jsp
var frmAryIdx=0;//will assign the max def list size value
var commonStaticUrl;
var maxRowsCount=10; //Max number of rows can be for one Group
var maxGroupsCount=10; //Max number of Groups can be added
var deletedRowIdArray =new Array(); //to store the deleted rowdef id's
$(document).ready(function() {
	 $("#ruleName").focus();
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-rules");
	commonStaticUrl = window.sessionStorage.getItem('commonStaticUrl');
	var $loadsheetRuleTable = $('#createRule-Table');
	var $assigendTable=$('#Assigned-Table');
	
	//Customized search for Create Rule Table
	$.fn.dataTableExt.ofnSearch['html-input'] = function(value) {
		return $(value).val();
	};
	$.fn.dataTableExt.ofnSearch['html-select'] = function(value) {
		var $id=$(value).attr('id');
		return $("#"+$id+' option:selected').text();
	};
	
	$createRuleTable=initializeRuleTable($loadsheetRuleTable);
	dataTableCustomSearch();
	initializeAssignedTable($assigendTable);
	//Appending AddCriteriaGroup in data table
	var $addCriteriaGrp=$("#AddCriteriaGroup").html();
	$($addCriteriaGrp).appendTo("#createRule-Table_filter");
	$("#createRule-Table_filter").addClass("floatRight");
	$("#createRule-Table_wrapper").css("width","99%");
	
	
	$("#Assigned-Table_wrapper").css("width","99%");
	
	$("#footerDiv").insertBefore("#Assigned-Table_info"); 
	$("#footerDiv").show();
	
	 //To Delete a row in the table
	 $('#createRule-Table tbody').on( 'click', '#deleteRow', function () {
		 
		 //pushing the rule def id to deleted array
		 var ruleDefId=$this.closest("tr").find('#ruleDefId').val();
		 if(!(typeof ruleDefId === "undefined")){
			 deletedRowIdArray.push(ruleDefId);
		 }
		 clearErrorMessage();
		 $createRuleTable
	        .row( $(this).parents('tr') )
	        .remove()
	        .draw();
		 
		} );
	 
	 //Getting the Max criteriaGroupVal 
	 grpIndex=parseInt($('#createRule-Table tbody .criteriaGroupVal:last').val());
	 frmAryIdx=parseInt($('#numberOfRows').val());
	 
	 //onload apply odd even rule
	 applyOddEvenRule();
	 
	 $("#ruleName").on("focus",function(){
			$("#ErrorMsg").hide();
			$("#ruleName").removeClass("errorMsgInput");
		});
	 
	 //chosen plugin to add search filter in select dropdown
	 
	 $(".searchSelect").chosen();
	 $("#create-rule-form").on('keypress', function(e) {
			if (e.which == 13) {
				submitCreateRuleForm();
			}
		});
	 
});

/**
 * Method to Custom filter create rule data table based on input and select elements
 */
function dataTableCustomSearch(){
	
	$(document).on('keyup','#createRule-Table td input', function(e) {
		var $td = $(this).parent();
		caretPos=this.selectionStart;
	    $(this).attr('value', this.value);
	    $createRuleTable.cell($td).invalidate('dom').draw();
	    $(this).focus();
	    this.setSelectionRange(caretPos, caretPos);
	});
	$(document).on('change','#createRule-Table td select', function() {
	    var $td = $(this).parent();
	    var value = this.value;
	    $td.find('option').each(function(i, o) {
	      $(o).removeAttr('selected');
	      if ($(o).val() == value) $(o).attr('selected', true);
	    });
	    $createRuleTable.cell($td).invalidate('dom').draw();
	}); 
	
}

function initializeRuleTable($loadsheetRuleTable){
	
	var $createRuleTable=$loadsheetRuleTable.DataTable({ //All of the below are optional
		"bPaginate": false, //enable pagination
		//"scrollY": "500px",
		"scrollCollapse": true,
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": false, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		"columnDefs": [
		               	{ "type": "html-input", "targets": [3] },
		               	{ "type": "html-select", "targets": [1,2] }
		               	] ,
		"fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
			
	var paginateRow = $(this).parent().children('div.dataTables_paginate');
	var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

			if (pageCount > 1){
				paginateRow.css("display", "block");
			} 
			else{
				paginateRow.css("display", "none");
			}
			
			//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
			var infoRow = $(this).parent().children('div.dataTables_info');
			var rowCount = this.fnSettings().fnRecordsDisplay();
			if (rowCount > 0){
				infoRow.css("display", "block");
			} 
			else{
				infoRow.css("display", "none");
			}
		}
});
	
	return $createRuleTable;
}

function initializeAssignedTable($assigendTable){
	
	$assigendTable.dataTable({ //All of the below are optional
		"aaSorting"			: [[ 2, "desc" ]], //default sort column
		"bPaginate"			: true, //enable pagination
		"bLengthChange"		: true, //enable change of records per page, not recommended
		"bFilter"			: true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort"				: true, //Allow sorting by column header
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [0] } ],//disable sorting for specific column indexes
		"bInfo"				: true, //Showing 1 to 10 of 11 entries
		"sPaginationType"	: "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength"	: -1 , //number of records per page for pagination
		"aLengthMenu"		: [[10, 25, 50,100,-1], [10, 25, 50,100,"All"]],//number of records per page for pagination
		"oLanguage"			: {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		"search"			: {
								type: "text",
								bRegex: true,
								bSmart: true
								 },
		"fnDrawCallback"	: function() { //This will hide the pagination menu if we only have 1 page.
			
	var paginateRow = $(this).parent().children('div.dataTables_paginate');
	var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

			if (pageCount > 1){
				paginateRow.css("display", "block");
			} 
			else{
				paginateRow.css("display", "none");
			}
			
			//This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
			var infoRow = $(this).parent().children('div.dataTables_info');
			var rowCount = this.fnSettings().fnRecordsDisplay();
			if (rowCount > 0){
				infoRow.css("display", "block");
			} 
			else{
				infoRow.css("display", "none");
			}
		}
});
	
}

/*Function to load operands based on selected component*/
function loadOperands(grpIndex,rIndex){
	
	var operandsSet = {
		    "<" 	: 	'<',
		    "<=" 	: 	'<=',
		    "="		:	"=",	
		    ">"		:	">",
		    ">="	:	">="
		    	
		};
		var componentType =$('#componentsDropDown-G_'+grpIndex+'-R_'+rIndex+' option:selected').val();
		if(!(typeof componentType === "undefined")){
		
			componentType=componentType.split("-");
			componentType=componentType[1];
			var $operandID = $('#operandsID-G_'+grpIndex+'-R_'+rIndex);
			$operandID.empty();
			if(!(typeof componentType === "undefined") ){
				$operandID.removeAttr('disabled');
				/*If component type is Y/N append operandsset*/
				if(componentType != "T"){
					$.each(operandsSet, function(val, text) {
						$operandID.append(
					        $('<option></option>').val(val).html(text)
					    );
					});
				}else{//if it is T append only =
					$operandID.append(
					        $('<option></option>').val("=").html("=")
					    );
				}
			}else{
				$operandID.attr('disabled', 'disabled');
			}
		
		}
		
}
//function to add new row.
function addNewRow(gIndex){
	showLoading().then(addRow.bind(null,gIndex)).then(hideLoading);
}
//function to show the loading image 
var showLoading = function(gIndex) {
    var defer = $.Deferred();
    processingImageAndTextHandler('visible','Adding New Row...');
    setTimeout(function() {
        defer.resolve(gIndex);
    }, 1);

    return defer;
};
//function to hide the loading image 
var hideLoading = function() {
    var defer = $.Deferred();
    processingImageAndTextHandler('hidden');
    defer.resolve(); 
    return defer;
};


var addRow= function(gIndex){
	
	
	if(!checkMaxrowsCount(gIndex)){
		return false;
	}
	
	frmAryIdx=frmAryIdx+1;
	
	//getting rowindex based  on last divid in a group
	var lastDivID=$('#createRule-Table tbody .group'+gIndex+':last').attr('id');
	var lstIndx=lastDivID.lastIndexOf("_");
	var rIndex=parseInt(lastDivID.substr(lstIndx+1,lstIndx.length))+1;
	
	
	$('#createRule-Table').DataTable().destroy();
	
	 var prevRowIndex=rIndex-1;
	 preRowId='G_'+gIndex+'-R_'+prevRowIndex;
	 preRowId='G_'+gIndex+'-R_'+prevRowIndex;
	 var newRowColor="grayRow";
	 if($("#"+preRowId).hasClass( "grayRow" )){
		 newRowColor="grayRow";
	 }else{
		 newRowColor="whiteRow";
	 }
	 $("#"+preRowId).after('<tr  class="even '+newRowColor+' group'+gIndex+'" id="G_'+gIndex+'-R_'+rIndex+'"><td class="editable centerAlign"></td>'+
			 '<td><select class="searchSelect" id="componentsDropDown-G_'+gIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].componentId" onchange="loadOperands('+gIndex+','+rIndex+')" style="width:786px"><option></option></select></td>'+
			 '<td><select id="operandsID-G_'+gIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].operand" disabled=""></select></td>'+
			 '<td><input id="valueID-G_'+gIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].value" maxlength="30" type="text">'+
			 '<input type="hidden" name="ruleDefinitionsList['+frmAryIdx+'].criteriaGroup" value="'+gIndex+'"></td>'+
			 '<td><a><img src="'+commonStaticUrl+'/images/delete.png"id="deleteRow" class="centerImage handCursor"  alt="Delete Row"/></a>'+
			 '</td></tr>');
	//table.row.addByPos(rowData, rowCount+1);
	addDropDownData(gIndex,rIndex);
	
	var $loadsheetRuleTable = $('#createRule-Table');
	$createRuleTable=initializeRuleTable($loadsheetRuleTable);
	
	//Appending AddCriteriaGroup in data table
	var $addCriteriaGrp=$("#AddCriteriaGroup").html();
	$($addCriteriaGrp).appendTo("#createRule-Table_filter");
	$("#createRule-Table_filter").addClass("floatRight");
	$("#createRule-Table_wrapper").css("width","99%");
	 var defer = $.Deferred();
	 defer.resolve(); 
	 $(".searchSelect").chosen();
};

function loadDropDownsDuringSwap(compId){
	var grpIndex=compId.substring(compId.indexOf("_")+1,compId.lastIndexOf("-"));
	var lstIndx=compId.lastIndexOf("_");
	var rIndex=compId.substr(lstIndx+1,lstIndx.length);
	addDropDownData(grpIndex,rIndex);
}

function loadOperandDuringSwap(compId){
	var grpIndex=compId.substring(compId.indexOf("_")+1,compId.lastIndexOf("-"));
	var lstIndx=compId.lastIndexOf("_");
	var rIndex=compId.substr(lstIndx+1,lstIndx.length);
	loadOperands(grpIndex,rIndex);
}

function addNewGroup(){
	
	if(!checkMaxCriteriaGroupCount()){
		return false;
	}
	
	//Increasing the group index
	grpIndex=grpIndex+1;
	frmAryIdx=frmAryIdx+1;
	createGroupHeader(grpIndex);
	applyOddEvenRule();
	 $(".searchSelect").chosen();
}

function createGroupHeader(grpIndex){
	
	var rowNode=$createRuleTable.row.add( [
	                                       '<a href="javascript:void(0)" class="rightMargin" onClick="copyGroup('+grpIndex+')">Copy</a><a href="javascript:void(0)" onClick="deleteGroup('+grpIndex+');"><img src="'+commonStaticUrl+'/images/delete.png" class="centerImage rightMargin delete-button"/></a>',
	                	               	   '<select class="searchSelect" id="componentsDropDown-G_'+grpIndex+'-R_1" name="ruleDefinitionsList['+frmAryIdx+'].componentId" onChange="loadOperands('+grpIndex+',1)" style="width:786px"><option></option></select>',
	                	            	   '<select id="operandsID-G_'+grpIndex+'-R_1" name="ruleDefinitionsList['+frmAryIdx+'].operand" disabled></select>',
	                	            	   '<input id="valueID-G_'+grpIndex+'-R_1" name="ruleDefinitionsList['+frmAryIdx+'].value" maxlength="30" type="text"><input type="hidden" name="ruleDefinitionsList['+frmAryIdx+'].criteriaGroup" value="'+grpIndex+'">',
	                	            	   '<a><img src="'+commonStaticUrl+'/images/add.png" tabindex=0 class="centerImage handCursor adder" onclick="addNewRow('+grpIndex+');" alt="Add Row"/></a>'
	                                	  ] ).draw().node();
	

		$(rowNode).addClass("groupHeader group"+grpIndex);
		$(rowNode).attr('id','G_'+grpIndex+'-R_1');
		$(rowNode).children(':nth-child(1)').addClass('editable centerAlign');
		
		addDropDownData(grpIndex,1);
	
}

function deleteGroup(grpIndex){
	
	
	//get the rowDefID's before delete
	$('#createRule-Table tbody .group'+grpIndex).each(function () {
		 var ruleDefId=$(this).closest("tr").find('#ruleDefId').val();
		 if(!(typeof ruleDefId === "undefined")){
			 deletedRowIdArray.push(ruleDefId);
		 }
	});
	var rows = $createRuleTable.rows( '.group'+grpIndex).remove().draw();
	applyOddEvenRule();
	clearErrorMessage();
	
}

/*Method to apply different background colors for alternative groups*/
function applyOddEvenRule(){
	
var gCountsArray = new Array();
	
	$('#createRule-Table tbody .groupHeader').each(function () {
		var nodeId=$(this).attr('id');
		 var gCount=nodeId.substring(nodeId.indexOf("_")+1,nodeId.lastIndexOf("-"));
		    gCountsArray.push(gCount);
	});
	applyBGColors(gCountsArray.sort(function(gCount1, gCount2){return gCount1-gCount2;}));
	
}

function applyBGColors(gCountsArray){

	$.each(gCountsArray,function(index,gValue){
		var regExp="G_"+gValue;
		if(index%2==0){
			$('tr[id^="'+regExp+'"]').removeClass("grayRow");
			$('tr[id^="'+regExp+'"]').addClass("whiteRow");
	    }else{
	    	$('tr[id^="'+regExp+'"]').removeClass("whiteRow");
	    	$('tr[id^="'+regExp+'"]').addClass("grayRow");
	    }
			
		});
	
}

function copyGroup(srcGrpIndex){
	showGroupLoading().then(copyGroupData.bind(null,srcGrpIndex)).then(hideLoading);
}

//function to show the loading image 
var showGroupLoading = function(gIndex) {
    var defer = $.Deferred();

    processingImageAndTextHandler('visible','Copying...');
    setTimeout(function() {
        defer.resolve(gIndex);
    }, 1);

    return defer;
};

//Method to Copy the Group
var copyGroupData= function(srcGrpIndex){
	
	var childRowNode=null;
	
	//getTheCount of current group Rows 
	var rowCount=0;
	$('#createRule-Table tbody .group'+srcGrpIndex).each(function (index,val) {
		rowCount=rowCount+1;
	});
	
	if(!checkMaxCriteriaGroupCount()){
		return false;
	}
	
	//get the total number of groups in page
	grpIndex=grpIndex+1;
	frmAryIdx=frmAryIdx+1;
	var newGroupColor="grayRow";
	if($('#createRule-Table tbody .groupHeader:last').hasClass( "grayRow" )){
		newGroupColor="whiteRow";
	}else{
		newGroupColor="grayRow";
	}
	createGroupHeader(grpIndex);
	
	//Add the extra rows for the destination group
	if(rowCount>1){
		for(var rIndex=2;rIndex<=rowCount;rIndex++){
			frmAryIdx=frmAryIdx+1;	//increasing the form array Index count for every row
			var rowData=getRowData(grpIndex,rIndex);
			childRowNode=$createRuleTable.row.add(rowData).draw().node();
			
			if($('#createRule-Table tbody .group'+grpIndex+':last').hasClass( "grayRow" )){
				$(childRowNode).addClass("grayRow group"+grpIndex);
			}else{
				$(childRowNode).addClass("whiteRow group"+grpIndex);
			}
			
			$(childRowNode).children(':nth-child(1)').addClass('editable centerAlign');
			$(childRowNode).attr('id','G_'+grpIndex+'-R_'+rIndex);
			addDropDownData(grpIndex,rIndex);	
		}
	}
	
	copyDatatoDestGroup(srcGrpIndex,grpIndex);
	
	//apply background color for ned group.
	var regExp="G_"+grpIndex;
	if(newGroupColor=="grayRow"){
		$('tr[id^="'+regExp+'"]').removeClass("whiteRow");
    	$('tr[id^="'+regExp+'"]').addClass("grayRow");
    	
    }else{
    	$('tr[id^="'+regExp+'"]').removeClass("grayRow");
		$('tr[id^="'+regExp+'"]').addClass("whiteRow");
    }
	
	 var defer = $.Deferred();
	 defer.resolve(); 
	 $(".searchSelect").chosen();
};

//Method to Copy data to the newly creadted group from src group
function copyDatatoDestGroup(srcGrp,destGrp){
	
	var srcDivIdsArray=new Array();
	var destDivIdArray=new Array();;
	
	$('#createRule-Table tbody .group'+srcGrp).each(function () {
		srcDivIdsArray.push($(this).attr('id'));
	});
	$('#createRule-Table tbody .group'+destGrp).each(function () {
		destDivIdArray.push($(this).attr('id'));
	});
	
	for(var indx=0;indx<srcDivIdsArray.length;indx++){
		
		var srcDivId=srcDivIdsArray[indx];
		var destDivId=destDivIdArray[indx];
		
		//Getting source group indexes
		var srcGrpIndex=srcDivId.substring(srcDivId.indexOf("_")+1,srcDivId.lastIndexOf("-"));
		var srcLstIndx=srcDivId.lastIndexOf("_");
		var srcIndex=srcDivId.substr(srcLstIndx+1,srcLstIndx.length);
		//getting destination group indexes
		var destGrpIndex=destDivId.substring(destDivId.indexOf("_")+1,destDivId.lastIndexOf("-"));
		var destLstIndx=destDivId.lastIndexOf("_");
		var destIndex=destDivId.substr(destLstIndx+1,destLstIndx.length);
		
		
		$("#valueID-G_"+destGrpIndex+'-R_'+destIndex).attr("value",$("#valueID-G_"+srcGrpIndex+'-R_'+srcIndex).val());
		$("#componentsDropDown-G_"+destGrpIndex+'-R_'+destIndex).val($("#componentsDropDown-G_"+srcGrpIndex+'-R_'+srcIndex).val());
	     
		loadOperandDuringSwap(destDivId);
	    $("#operandsID-G_"+destGrpIndex+'-R_'+destIndex).val($("#operandsID-G_"+srcGrpIndex+'-R_'+srcIndex).val());
	    
	    //Invalidate row and load data from DOM for search functionality
	    var $tr=$("#valueID-G_"+destGrpIndex+'-R_'+destIndex).parent().parent();
	    $createRuleTable.row($tr).invalidate('dom').draw();
	}
	 $(".searchSelect").chosen();	
	
}

//Method to form the row Data
function getRowData(grpIndex,rIndex){
	
	
	var rowData=[ '',
    '<select class="searchSelect" id="componentsDropDown-G_'+grpIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].componentId" onChange="loadOperands('+grpIndex+','+rIndex+')" style="width:786px"><option></option></select>',
    '<select id="operandsID-G_'+grpIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].operand" disabled></select>',
    '<input id="valueID-G_'+grpIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].value" maxlength="30" type="text"><input type="hidden" name="ruleDefinitionsList['+frmAryIdx+'].criteriaGroup" value="'+grpIndex+'">',
    '<a><img src="'+commonStaticUrl+'/images/delete.png" id="deleteRow" tabindex=0 class="centerImage handCursor"  alt="Delete Row"/></a>'];
	
	return rowData;
	
}

//Method add dropdown data in the newly added dropdown
function addDropDownData(grpIndex,rIndex){
	
	var $options = $("#componentsDropDown > option").clone();
	$('#componentsDropDown-G_'+grpIndex+'-R_'+rIndex).append($options);
	
}

//Method to sumbit the form
function submitCreateRuleForm(){
	
	if(validateFields()){
		processingImageAndTextHandler('visible','Loading data...');
		var $createRuleForm=$("#create-rule-form");
		var pageAction=$("#pageAction").val();
		
		//appending deleted refid's to form
		 $("<input type='hidden'  value='"+deletedRowIdArray+"' />")
	     .attr("name", "deletedRuleDefIds")
	     .appendTo($createRuleForm);
		
		
		if(pageAction=="CREATE"){
			$createRuleForm.attr("action","create-rule");
		}else if(pageAction=="UPDATE")
		{
			$createRuleForm.attr("action","update-rule");
		}
		
		
		var ruleId=$("#ruleId").val();
		var ruleName=$("#ruleName").val();
		processingImageAndTextHandler('visible','Loading data...');
		//Check for unique Rule Name
		displayFlag=false;
		var showLoading=true;
		$.ajax({
			  type: "POST",
			  url: "./check-unique-rule-name",
			  cache:false,
			  data: {ruleName : ruleName,ruleId:ruleId},
			  success: function(isUnique){
				  
				  if(isUnique){
					showLoading=true;
					$createRuleForm.submit();
					  
				  }else{
					showLoading=false;
					$("#ErrorMsg span").text("Entered Rule Name already exists ! please enter different name.");
					$("#ErrorMsg").show();
					$("#ruleName").addClass("errorMsgInput");
					}
				
			  },
			});
		
		$(document).ajaxComplete(function() {
			if(showLoading){
				processingImageAndTextHandler('visible','Loading data...');
			}else{
				processingImageAndTextHandler('hidden');
			}
		});
	}
	
}

//Method to validate the fileds
function validateFields(){
	
	var rtrnFlag=true;
	if(($("#ruleName").val()=="") || $("#description").val()=="") {
		$("#ErrorMsg span").text("Please Enter Rule Name And Description");
		$("#ErrorMsg").show();
		$("#ruleName").addClass("errorMsgInput");
		$("#description").addClass("errorMsgInput");
		return false;
	}else{
		$("#ErrorMsg").hide();
		$("#ruleName").removeClass("errorMsgInput");
		$("#description").removeClass("errorMsgInput");
		//If table is empty
		if ( ! $createRuleTable.data().count() ) {
			$("#ErrorMsg span").text("Please add atleast one criteria group");
			$("#ErrorMsg").show();
			return false;
		}else{
			
			//validate the rule definitions
			$createRuleTable.rows().every( function ( index ) {
				if(rtrnFlag){
					
					var curRow = $createRuleTable.row( index ).data();
				    var $comId=curRow[1].split('"')[1];
				    var ruleValId=curRow[3].split('"')[1];
				    var compVal=$("#"+$comId).val();
				    var ruleVal=$("#"+ruleValId).val();
				    
				    if(compVal=="" || ruleVal==""){
				    	
				    	$("#ErrorMsg span").text("Please select component ID's and enter component value's");
						$("#ErrorMsg").show();
						$("#"+$comId).addClass("errorMsgInput");
						$("#"+ruleValId).addClass("errorMsgInput");
						rtrnFlag = false;
						
				    }else{
				    	$("#ErrorMsg").hide();
						$("#"+$comId).removeClass("errorMsgInput");
						$("#"+ruleValId).removeClass("errorMsgInput");
						rtrnFlag = true;;
				    }
					
				}
			    
			    
			} );
				return rtrnFlag;
		}
		
		
		
	}
	
	
	
}

/**
 * Method to check the max rows count for create rule fieldset
 */
function checkMaxrowsCount(gCnt){
	
	var rowCount=1;
	$('#createRule-Table tbody .group'+gCnt).each(function () {
		rowCount=rowCount+1;
	});
	
	if(rowCount > maxRowsCount){
		$("#ErrorMsg span").text("Reached maximum number of rows("+maxRowsCount+") for this Criteria Group");
		$("#ErrorMsg").show();
		return false;
	}else{
		$("#ErrorMsg").hide();
		return true;
	}
	
	
}

function checkMaxCriteriaGroupCount(){
	
	var gCnt=1;
	$('#createRule-Table tbody .groupHeader').each(function () {
		gCnt=gCnt+1;
	});
	
	if(gCnt > maxGroupsCount){
		$("#ErrorMsg span").text("Reached maximum number of Criteria Groups("+maxRowsCount+") for this Rule");
		$("#ErrorMsg").show();
		return false;
	}else{
		$("#ErrorMsg").hide();
		return true;
	}
	
}
/**
 * Method to clear the error message
 */
function clearErrorMessage(){
	
	$("#ErrorMsg").hide();
}
