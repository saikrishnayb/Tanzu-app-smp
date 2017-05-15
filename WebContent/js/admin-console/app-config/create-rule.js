/**
 * 
 */
var $createRuleTable;
var grpIndex=0;//will assign the max group value from jsp
var frmAryIdx=0;
var commonStaticUrl;
$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-rules");
	commonStaticUrl =$('#common-static-url').val();
	var $loadsheetRuleTable = $('#createRule-Table');
	initializeRuleTable($loadsheetRuleTable);
	//Appending AddCriteriaGroup in data table
	var $addCriteriaGrp=$("#AddCriteriaGroup").html();
	$($addCriteriaGrp).appendTo("#createRule-Table_filter");
	$("#createRule-Table_filter").addClass("floatRight");
	
	 //To Delete a row in the table
	 $('#createRule-Table tbody').on( 'click', '#deleteRow', function () {
		 $createRuleTable
	        .row( $(this).parents('tr') )
	        .remove()
	        .draw();
		 
		} );
	 
	 //Getting the Max criteriaGroupVal 
	 grpIndex=$('#createRule-Table tbody .criteriaGroupVal:last').val();
	 
	 //onload apply odd even rule
	 applyOddEvenRule();
	 
});

function initializeRuleTable($loadsheetRuleTable){
	
	$createRuleTable=$loadsheetRuleTable.DataTable({ //All of the below are optional
		"bPaginate": false, //enable pagination
		"scrollY": true,
		"stateSave": true,
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": false, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
		"sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength": 10 , //number of records per page for pagination
		"oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		"search": {
			type: "text",
			bRegex: true,
			bSmart: true
			 },
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
		componentType=componentType.split("-");
		componentType=componentType[1];
		var $operandID = $('#operandsID-G_'+grpIndex+'-R_'+rIndex);
		$operandID.empty();
		if(!(typeof componentType === "undefined") ){
			$operandID.removeAttr('disabled');
			/*If component type is Y/N append operandsset*/
			if(componentType != "N"){
				$.each(operandsSet, function(val, text) {
					$operandID.append(
				        $('<option></option>').val(val).html(text)
				    );
				});
			}else{//if it is N append only =
				$operandID.append(
				        $('<option></option>').val("=").html("=")
				    );
			}
		}else{
			$operandID.attr('disabled', 'disabled');
		}
	
}

function addNewRow(gIndex){
	
	frmAryIdx=frmAryIdx+1;
	//get rowcount based on group count
	var rowCount=0;
	for (var i=1;i<=gIndex;i++){
			rowCount =rowCount + $('.group'+i).length;
		}
	
	//var rIndex=$('.group'+gIndex).length+1;
	//getting rowindex based  on last divid in a group
	var lastDivID=$('#createRule-Table tbody .group'+gIndex+':last').attr('id');
	var lstIndx=lastDivID.lastIndexOf("_");
	var rIndex=parseInt(lastDivID.substr(lstIndx+1,lstIndx.length))+1;
	
	jQuery.fn.dataTable.Api.register('row.addByPos()', function(data, index) {     
	    var currentPage = this.page();

	    //insert the row  
	    var rowNode=this.row.add(data).draw().node();
	    	    
	    /*Apply the BG Clolor based on Parent BG color*/
	    if($('#createRule-Table tbody .group'+gIndex+':last').hasClass( "grayRow" )){
	    	$(rowNode).addClass("grayRow group"+gIndex);
	    }else{
	    	$(rowNode).addClass("whiteRow group"+gIndex);
	    }
	    
		 $(rowNode).children(':nth-child(1)').addClass('editable centerAlign');
		 $(rowNode).attr('id','G_'+gIndex+'-R_'+rIndex);
		 
	    //move added row to desired index
	    var rowCount = this.data().length-1,
	        insertedRow = this.row(rowCount).data(),
	        tempRow;
	    
	    
	    //Swapping the rows to desired location
	    for (var i=rowCount;i>=index;i--) {
	        tempRow = this.row(i-1).data();
	        
	        //preserving the textbox value while swapping the rows
	        var textId=tempRow[3];
	        var compId=tempRow[1];
	        var opId=tempRow[2];
	        textId=textId.split('"');
	        compId=compId.split('"');
	        opId=opId.split('"');
	        
	        var textOldVal=$("#"+textId[1]).val();
	        var compOldVal=$("#"+compId[1]).val();
	        var opOldVal=$("#"+opId[1]).val();
	        
	        var tempNode=this.row(i).data(tempRow).draw().node();
	        var insertNode=this.row(i-1).data(insertedRow).draw().node();
	        
	        //swapping the classes and IDs
	        var tempClass=$(tempNode).attr("class");
	        var insertClas=$(insertNode).attr("class");
	        var tempID=$(tempNode).attr("id");
	        var insertID=$(insertNode).attr("id");
	        //remove old classes and ID's
	        $(tempNode).removeClass(tempClass);
	        $(insertNode).removeClass(insertClas);
	        $(tempNode).removeAttr('id');
	        $(insertNode).removeAttr('id');
	        //set new classes and ID's
	        $(tempNode).addClass(insertClas);
	        $(insertNode).addClass(tempClass);
	        $(tempNode).attr('id',insertID);
	        $(insertNode).attr('id',tempID);
	        
	        loadDropDownsDuringSwap(compId[1]);
	        $("#"+textId[1]).val(textOldVal);
	        $("#"+compId[1]).val(compOldVal);
	        
	        loadOperandDuringSwap(compId[1]);
	        $("#"+opId[1]).val(opOldVal);
	        
	    }   
	    
 //refresh the current page
	    this.page(currentPage).draw(false);
	});
	
	var table = $("#createRule-Table").DataTable();
	var rowData=getRowData(gIndex,rIndex);
	table.row.addByPos(rowData, rowCount+1);
	addDropDownData(gIndex,rIndex);
	
	applyOddEvenRule();
}

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
	
	//Increasing the group index
	grpIndex=grpIndex+1;
	frmAryIdx=frmAryIdx+1;
	createGroupHeader(grpIndex);
	applyOddEvenRule();
}

function createGroupHeader(grpIndex){
	
	var table = $("#createRule-Table").DataTable();
	var rowNode=table.row.add( [
	                                       '<a href="#" class="rightMargin" onClick="copyGroup('+grpIndex+')">Copy</a><a href="#" onClick="deleteGroup('+grpIndex+');"><img src="'+commonStaticUrl+'/images/delete.png" class="centerImage rightMargin delete-button"/></a>',
	                	               	   '<select id="componentsDropDown-G_'+grpIndex+'-R_1" name="ruleDefinitionsList['+frmAryIdx+'].componentId" onChange="loadOperands('+grpIndex+',1)" style="width:100%"><option></option></select>',
	                	            	   '<select id="operandsID-G_'+grpIndex+'-R_1" name="ruleDefinitionsList['+frmAryIdx+'].operand" disabled></select>',
	                	            	   '<input id="valueID-G_'+grpIndex+'-R_1" name="ruleDefinitionsList['+frmAryIdx+'].value" type="text"><input type="hidden" name="ruleDefinitionsList['+frmAryIdx+'].criteriaGroup" value="'+grpIndex+'">',
	                	            	   '<a><img src="'+commonStaticUrl+'/images/add.png" class="centerImage handCursor adder" onclick="addNewRow('+grpIndex+');" alt="Add Row"/></a>'
	                                	  ] ).draw().node();
	

		$(rowNode).addClass("groupHeader group"+grpIndex);
		$(rowNode).attr('id','G_'+grpIndex+'-R_1');
		$(rowNode).children(':nth-child(1)').addClass('editable centerAlign');
		
		addDropDownData(grpIndex,1);
	
}

function deleteGroup(grpIndex){
	
	var table = $("#createRule-Table").DataTable();
	
	var rows = table.rows( '.group'+grpIndex).remove().draw();
	
	applyOddEvenRule();
	
}

/*Method to apply different background colors for alternative groups*/
function applyOddEvenRule(){
	
	var table = $("#createRule-Table").DataTable();
	var gCountsArray = new Array();
	var curNode=null;
	
	table.rows().eq(0).each( function ( index ) {
	    curNode = table.row( index ).draw().node();
	    var nodeId=$(curNode).attr("id");
	    var gCount=nodeId.substring(nodeId.indexOf("_")+1,nodeId.lastIndexOf("-"));
	    gCountsArray.push(gCount);
	 
	} );
	
	applyBGColors(jQuery.unique( gCountsArray ).sort());
	
}

function applyBGColors(gCountsArray){
	
var table = $("#createRule-Table").DataTable();

$.each(gCountsArray,function(index,gValue){
	table.rows().eq(0).each( function ( i ) {
		curNode = table.row( i ).draw().node();
		var nodeId=$(curNode).attr("id");
	    var gCount=nodeId.substring(nodeId.indexOf("_")+1,nodeId.lastIndexOf("-"));
	    
	    if(gCount==gValue){
	    	if(index%2==0){
		    	$(curNode).removeClass("grayRow");
		    	$(curNode).addClass("whiteRow");
		    }else{
		    	$(curNode).removeClass("whiteRow");
		    	$(curNode).addClass("grayRow");
		    }
	    	
	    }
		
	});
		
	});
	
}

//Method to Copy the Group
function copyGroup(srcGrpIndex){
	
	
	
	var table = $("#createRule-Table").DataTable();
	var childRowNode=null;
	
	//getTheCount of current group Rows 
	var rowCount=0;
	$('#createRule-Table tbody .group'+srcGrpIndex).each(function (index,val) {
		rowCount=rowCount+1;
	});
	
	//get the total number of groups in page
	grpIndex=grpIndex+1;
	frmAryIdx=frmAryIdx+1;
	createGroupHeader(grpIndex);
	
	//Add the extra rows for the destination group
	if(rowCount>1){
		for(var rIndex=2;rIndex<=rowCount;rIndex++){
			frmAryIdx=frmAryIdx+1;	//increasing the form array Index count for every row
			var rowData=getRowData(grpIndex,rIndex);
			childRowNode=table.row.add(rowData).draw().node();
			
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
	
	applyOddEvenRule();
}

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
		var sgrpIndex=srcDivId.substring(srcDivId.indexOf("_")+1,srcDivId.lastIndexOf("-"));
		var slstIndx=srcDivId.lastIndexOf("_");
		var srIndex=srcDivId.substr(slstIndx+1,slstIndx.length);
		//getting destination group indexes
		var dgrpIndex=destDivId.substring(destDivId.indexOf("_")+1,destDivId.lastIndexOf("-"));
		var dlstIndx=destDivId.lastIndexOf("_");
		var drIndex=destDivId.substr(dlstIndx+1,dlstIndx.length);
		
		
		$("#valueID-G_"+dgrpIndex+'-R_'+drIndex).val($("#valueID-G_"+sgrpIndex+'-R_'+srIndex).val());
		$("#componentsDropDown-G_"+dgrpIndex+'-R_'+drIndex).val($("#componentsDropDown-G_"+sgrpIndex+'-R_'+srIndex).val());
	     
		loadOperandDuringSwap(destDivId);
	    $("#operandsID-G_"+dgrpIndex+'-R_'+drIndex).val($("#operandsID-G_"+sgrpIndex+'-R_'+srIndex).val());
		
	}
		
	
}

//Method to form the row Data
function getRowData(grpIndex,rIndex){
	
	
	var rowData=[ '',
    '<select id="componentsDropDown-G_'+grpIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].componentId" onChange="loadOperands('+grpIndex+','+rIndex+')" style="width:100%"><option></option></select>',
    '<select id="operandsID-G_'+grpIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].operand" disabled></select>',
    '<input id="valueID-G_'+grpIndex+'-R_'+rIndex+'" name="ruleDefinitionsList['+frmAryIdx+'].value" type="text"><input type="hidden" name="ruleDefinitionsList['+frmAryIdx+'].criteriaGroup" value="'+grpIndex+'">',
    '<a><img src="'+commonStaticUrl+'/images/delete.png"id="deleteRow" class="centerImage handCursor"  alt="Delete Row"/></a>'];
	
	return rowData;
	
}

//Method add dropdown data in the newly added dropdown
function addDropDownData(grpIndex,rIndex){
	
	var $options = $("#componentsDropDown > option").clone();
	$('#componentsDropDown-G_'+grpIndex+'-R_'+rIndex).append($options);
	
}

//Method to sumbit the form
function submitCreateRuleForm(){
	$("#create-rule-form").submit();
}