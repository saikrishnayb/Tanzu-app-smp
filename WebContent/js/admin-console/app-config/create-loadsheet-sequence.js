var srcId;
var destId;
var col1;
var col2;
var srcGrp;
var destGrp;
var groupCount=1; //get this value based on max group count

j2(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-sequence");
	//var commonStaticUrl =j2('#common-static-url').val();
	
	groupCount=j2("#numberOfGroups").val();
	
	$('#addGroupModal').dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		resizable: false,
		title: 'Add Group Name',
		closeOnEscape: false,
		open: function(event, ui) {j2(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();}
	});
	
	
	initializeComponentTables();
	//Search for assigned components table
	j2("#assignedSearch").keyup(function(){
        $this = this;
        // Show only matching TR, hide rest of them
        j2.each(j2("#assignedComponentsTable tbody tr"), function() {
        	
        	//Implementing smart search as like datatables
        	var searchKeys=new Array();
        	searchKeys = j2($this).val().toLowerCase().split(" ");
        	var recFound=true;
        	for(var index=0;index<searchKeys.length;index++){
        		if(recFound){
        			if(j2(this).text().toLowerCase().indexOf(searchKeys[index]) === -1){
        				recFound=false;
        				
        				//if row doesn't have showchilds class then hide 
                    	if(j2(this).hasClass('showChilds')){
        					j2(this).show();
        				}else{
        					j2(this).hide();
        					//if header doesn't have rows hide groupheader
        					var lngth=j2(this).parent().children(':visible').length;
        					if(lngth == 1){
        						j2(this).parent().children().first().hide();
        					}
        					
        				}
        				//remove showchilds class for all the rows under the group
        				if(j2(this).hasClass('group')){
        					j2(this).parent().children().each(function(index,data){
        						
        						j2(this).removeClass('showChilds');  
        						j2(this).hide();
        					
        					});
        				}
                    
        				
        			}else{
        				recFound=true;
        				
        				//Add show childs class for all the rows under the group
                    	if(j2(this).hasClass('group')){
                    		
                    			j2(this).parent().children().each(function(index,data){
        						j2(this).addClass('showChilds');  
        					
        					});
                    		
                    	}else{
                    		//To display the Group header
                    		j2(this).parent().children().first().show();
                    	}
                    	
                    	j2(this).show();
        				
        				
        			}
        		}
        		
        		
        	}
        });
        
    });
	
	//serach for unassigned components table
	j2("#unAssignedSearch").keyup(function(){
        $this = this;
        // Show only matching TR, hide rest of them
        j2.each(j2("#unAssignedComponentsTable tbody tr"), function() {
        	
        	//Implementing smart search as like datatables
        	var searchKeys=new Array();
        	searchKeys = j2($this).val().toLowerCase().split(" ");
        	var recFound=true;
        	for(var index=0;index<searchKeys.length;index++){
        		if(recFound){
        			if(j2(this).text().toLowerCase().indexOf(searchKeys[index]) === -1){
        				recFound=false;
        				j2(this).hide();
        			}else{
        				recFound=true;
        				j2(this).show();
        			}
        		}
        	}
        	
        });
        j2("#hiddenRow").hide(); //hide the hidden row while copying the group
    }); 

	//delete row
	j2('#assignedComponentsTable').on( 'click', '#deleteRow', function () {
		 var row=j2(this).parents("tr");
		 
		 row.find('td')[1].remove();
		 row.find('td')[1].remove();
		 j2(row).appendTo("#unAssignedComponentsTable");
		 
		 
		 resetSequence('#assignedComponentsTable');
		  j2("#hiddenRow").hide(); //hide the hidden row while copying the group
		 
	} );
	 
	j2("#name").on("focus",function(){
		j2("#ErrorMsg").hide();
		j2("#name").removeClass("errorMsgInput");
	});
	
	//delete Group
	j2('#assignedComponentsTable').on( 'click', '#deleteGroup', function () {
		 var tbody=j2(this).parents("tbody");
		 var content = tbody.contents();
		tbody.remove(); 
		j2(content).each(function(index,data){
			if(j2(data).find('td').length == 6 ){
				j2(data).find('td')[1].remove();
				j2(data).find('td')[1].remove();
				j2(data).appendTo("#unAssignedComponentsTable tbody");
			}
		});
		
		  j2("#hiddenRow").hide(); //hide the hidden row while copying the group 
	});
	
	
	//Refresh Unassigned Componets on change of category or typr
	j2("#categoryID").on("change",function(){
		var type=j2("#typeID option:selected").val();
		reloadUnassignedComponents(this.value,type);
		
	});
	
	j2("#typeID").on("change",function(){
		var category=j2("#categoryID option:selected").val();
		reloadUnassignedComponents(category,this.value);
		
	}); 
	
	//disable CategoryId and TypeId if pageAction is Edit or Copy
	var pageAction=j2("#pageAction").val();
	if(pageAction == "EDIT" || pageAction == "COPY"){
		j2("#categoryID").attr("disabled",true);
		j2("#typeID").attr("disabled",true);
	}
	//disable all the actions ifpageAction is View.
	if(pageAction == "VIEW"){
		j2("#categoryID").attr("disabled",true);
		j2("#typeID").attr("disabled",true);
		j2("#oem").attr("disabled",true);
		j2("#name").prop("readonly", true);
		j2("#description").prop("readonly", true);
		j2("#unAssignedComponentsTable").sortable("disable");
		j2("#assignedComponentsTable").sortable("disable");
	    j2("#unAssignedComponentsTable tbody").sortable("disable");
	    j2("#assignedComponentsTable tbody").sortable("disable");
	}
	
	
});

function initializeComponentTables(){
j2("table").sortable({
  helper: fixWidthHelper1,
  start: function(e, ui) {
    // modify ui.placeholder however you like
    ui.placeholder.find('tr').outerHeight(ui.item.outerHeight(true));
},
  update: function(e, ui) {
    // modify ui.placeholder however you like
	var noGroup = ui.item.closest('table').hasClass('no-group');
	if(noGroup) {
      ui.item.find('.group').remove();
	}
	
  },
  beforeStop: function(e,ui){
	
  },
  receive: function (e,ui){
	  
	  j2(ui.item).remove();	//remove the tbody   from first table
	  //remove extra columns in rows
	  ui.item.children().each(function(i,data){
		  
		 var rowData=new Array();
		  j2(this).children().each(function(i,data){
			rowData.push(data);  
		  });
		rowData.splice(1,2);
		j2(this).children().replaceWith(rowData);
		
			j2('#unAssignedComponentsTable tbody').append(j2(this));//append the rows to the second table tbody
	});
	  
	  j2("#hiddenRow").hide(); //hide the hidden row while copying the group
	  
	  if(j2('#assignedComponentsTable tbody').length == 1){
			j2("#hiddenHeader").show();
		}
		
	},
  placeholder: "ui-state-highlight",
  forcePlaceholderSize: true,
  connectWith: "table",
  items: "tbody:not(.header)",
  
}).disableSelection();

j2("table tbody").sortable({
  cancel: ".group .pointer",
  helper: fixWidthHelper,
  start: function(e, ui) {
	// modify ui.placeholder however you like
    ui.placeholder.find('tr').outerHeight(ui.item.outerHeight(true));
	srcId=j2(this).parent().attr('id');
	srcGrp=j2(this).children().first().find('.groupName').text();
	},
  over: function( event, ui ) {
	destId=j2(this).parent().attr('id');
	//Modifying the placeholder width based on the table
	var extraRows;
	if(destId=="assignedComponentsTable"){
		extraRows='<td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td>';
		j2(ui.placeholder).html(extraRows); 
	}else{
		extraRows='<td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td><td colspan="1">&nbsp;</td>';
		j2(ui.placeholder).html(extraRows); 
	}
  },
  receive: AddRemoveExtraColumns ,
  update: function(e, ui) {
	  resetSequence('#assignedComponentsTable');
	  
	  //remove the comgrpseqid if it's dragged from some other group
	  destGrp=j2(this).children().first().find('.groupName').text();
	  if(srcGrp!=destGrp){
		 var draggedRow=j2(ui.item);
		j2(draggedRow).find('.compGrpSeqId').val('0');
	  }
	  
	  },
  placeholder: "ui-state-highlight",
  forcePlaceholderSize: true,
  items: "tr:not(.group)",
  connectWith: "table tbody"
}).disableSelection();

}
function fixWidthHelper(e, ui) {
  ui.children().each(function() {
    j2(this).width(j2(this).width());
  });
  return ui;
}

function fixWidthHelper1(e, ui) {
  ui.find('tr').children().each(function() {
    j2(this).width(j2(this).width());
  });
  return ui;
}
//Renumber table rows
function resetSequence(tableID) {
   j2("#assignedComponentsTable tr").each(function () { 
   count = j2(this).parent().children().index(j2(this)) + 0;
   j2(this).find('.seq').html(count); 
});
   j2(".groupName").attr('contenteditable','false');

}

function AddRemoveExtraColumns(e,ui){
	
	if(srcId!=destId){
		var rowData=new Array();
		//var rowData=ui.item.children().parent().html();
		ui.item.children().each(function(i,data){
			rowData.push(data);
		});
		
		
		if(j2('#assignedComponentsTable tbody').length == 1){
			
			ui.sender.sortable("cancel");//cancel dropping if groups are not there
			j2("#ErrorMsg span").text("Please Create Group first");
			j2("#ErrorMsg").show();
			parent.resizeAfterPaginationChange();
			
		}else{
			
			//if columns size is 6 remove the extra 2 columns
			if(rowData.length==6){
				rowData.splice(1,2);
				ui.item.children().replaceWith(rowData);
			}else{
				var col1='<td style="width:5%" class="editable centerAlign"><img class="rightMargin" id="deleteRow" src="https://staticdev.penske.com/common/images/delete.png"></td>';
				var col2='<td style="width:7%" class="centerAlign seq"></td>';
				ui.item.children().each(function(i,data){
					if(i==1){
						j2(this).replaceWith(col1);
					}else if(i==2){
						j2(this).replaceWith(col2);
					}else if (i==3){
						j2( rowData[1] ).insertBefore(j2(this));
						j2( rowData[2] ).insertBefore(j2(this));
						
					}
					
				});
		}
		
		
		}
		//to add a hidden row in table 2 if table size is 0
		if(j2('#unAssignedComponentsTable tr').length == 2){
			j2("#hiddenRow").show();
		}else{
			j2("#hiddenRow").hide();
		}
	}
	  
   
	
}

j2("#assignedComponentsTable").on('keypress', '.groupName' , function(e){
	var editGroupId=j2(this).attr('id');
	editGroupId="editgroup-"+editGroupId.split('-')[1];
	check_charcount(editGroupId,50, e);
});
//preventing paste event for group Name
j2("#assignedComponentsTable").on('paste', function() {
	   return false; // to prevent user insert
});

/* to remove Editable on click of Enter in Groupname*/
j2(document).keypress('.groupName',function(event){
	if (event.keyCode == 13) {
		j2(".groupName").attr('contenteditable','false');
    }
});



/* function to check the group name character count */
function check_charcount(content_id, max, e)
{   
    if(e.which != 8 && j2('#'+content_id).text().length >= max)
    {
       e.preventDefault();
    }
}

/* function to edit the group name */
j2("#assignedComponentsTable").on('click', '.editGroup' , function(e){
	var editGroupId=j2(this).attr('id');
	editGroupId="editgroup-"+editGroupId.split('-')[1];
	j2('#'+editGroupId).attr('contenteditable','true');
	// to clear the defalut group name
	if(j2('#'+editGroupId).text().trim().toUpperCase()=='GROUP NAME'){
		j2('#'+editGroupId).text('');
	}
	j2('#'+editGroupId).focus();
	var textNode = j2('#'+editGroupId).get(0);
	var caret = 0; // insert caret at first character
	var range = document.createRange();
	range.setStart(textNode, caret);
	range.setEnd(textNode, caret);
	var sel = window.getSelection();
	sel.removeAllRanges();
	sel.addRange(range);
	event.stopPropagation();
	});

j2(".groupName").click(function() {
	event.stopPropagation();
	
});

/* to make group name non editable */
j2(document).click(function() {
	j2(".groupName").attr('contenteditable','false');
	
});


/* to submit the form */
function submitLoadSheetForm(){
	 clearUnassignedComponentsfromFormBinding();
	if(validateFields()){
		
			var j2loadSheetSequncingForm=j2("#loadsheet-sequencing-form");
			
			//Setting the group name and seq to form
			j2(".groupName").each(function(index,data){
				var editGroup=j2(this).attr("id");
				var count=editGroup.split("-")[1];
				j2("#formGroupName-"+count).val(j2("#"+editGroup).text());
				j2("#formGroupSeq-"+count).val(index+1);
				
			});
			
			var groupIndex=0;
			
			//Setting the hidden component id's and display seq
			j2("#assignedComponentsTable tbody").each(function (index,data) {
				j2(data).children().each(function(componentIndex,component){
					
					if(!j2(component).hasClass('group')){//for setting component hidden field names
						
						// j2(component).find('.seq').html(componentIndex);
						j2(component).find('.componentId').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].componentId'); 
						j2(component).find('.componentSequence').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].displaySeq'); 
						j2(component).find('.componentSequence').attr('value',componentIndex);
						j2(component).find('.compGrpSeqId').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].compGrpSeqId');
						j2(component).find('.grpMasterId').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].grpMasterId');
					}else{//update group index
						var groupId=j2(component).find('.groupName').attr("id");
						groupIndex=groupId.split("-")[1];
						
					}
				});
				
			});
			
			
			
			var seqMasterId=j2("#seqMasterId").val();
			var pageAction=j2("#pageAction").val();
			
			if(seqMasterId=="0"){//Perform Insert
				j2loadSheetSequncingForm.attr("action","create-sequence.htm");
			}else{//perform Update
				if(pageAction == "EDIT"){//Edit Action
					j2loadSheetSequncingForm.attr("action","update-sequence.htm");
				}else if(pageAction == "COPY"){//Copy action
					j2loadSheetSequncingForm.attr("action","create-sequence.htm");
				}
				
			}
			
			var seqName= j2("#name").val();
			var seqMasterId=0;
			if(pageAction == "EDIT"){
				seqMasterId=j2("#seqMasterId").val();
			}
			
			processingImageAndTextHandler('visible','Loading data...');
			//Check for unique sequence Name
			displayFlag=false;
			var showLoading=true;
			var category=j2("#categoryID").val();
			var type=j2("#typeID").val();
			var mfr=j2("#oem").val();
			j2.ajax({
				  type: "POST",
				  url: "./check-unique-name.htm",
				  cache:false,
				  data: {seqName : seqName,seqId:seqMasterId,category:category,type:type,mfr:mfr},
				  success: function(isUnique){
					  
					  if(isUnique==0){
						  showLoading=true;
						  j2("#categoryID").attr("disabled",false);
							j2("#typeID").attr("disabled",false);
							j2loadSheetSequncingForm.submit();
						  
					  }else{
						showLoading=false;
						if(isUnique==1){
						j2("#ErrorMsg span").text("Entered Sequence Name already exists ! please enter different name.");
						j2("#ErrorMsg").show();
						j2("#name").addClass("errorMsgInput");
						}else{
							j2("#ErrorMsg span").text("Loadsheet sequence with the Same Category, Type and MFR already Exists.");
							j2("#ErrorMsg").show();
						}
						
						parent.resizeAfterPaginationChange();
						}
					
				  },
				});
			
			j2(document).ajaxComplete(function() {
				if(showLoading){
					processingImageAndTextHandler('visible','Loading data...');
				}else{
					processingImageAndTextHandler('hidden');
				}
			});

	}
	
}


/*function to validate fileds*/
function validateFields(){
	
	var rtrnFlag=true;
	
	if((j2("#name").val()=="")) {
		j2("#ErrorMsg span").text("Please enter Name");
		j2("#ErrorMsg").show();
		j2("#name").addClass("errorMsgInput");
		parent.resizeAfterPaginationChange();
		return false;
	}else if((j2("#categoryID option:selected").text()=="")) {
			
		
		j2("#name").removeClass("errorMsgInput");
		
			
		j2("#ErrorMsg span").text("Please select Category");
		j2("#ErrorMsg").show();
		
		j2("#categoryID").addClass("errorMsgInput");
		parent.resizeAfterPaginationChange();
		return false;
		
	}else{
		
		j2("#name").removeClass("errorMsgInput");
		j2("#categoryID").removeClass("errorMsgInput");
		
		//Validate the Assigned Components
		
		//check for Available group's
		if(j2("#assignedComponentsTable tbody:not(.displayNone)").children().size() == 0){
				
				j2("#ErrorMsg span").text("Please add atleast one Group");
				j2("#ErrorMsg").show();
				parent.resizeAfterPaginationChange();
				return false;
		}
		
		//check for Available Componets in each group
		j2("#assignedComponentsTable tbody:not(.displayNone)").each(function (index,data) {
			
				if(j2(data).children().size() < 2){
					j2("#ErrorMsg span").text("Please add atleast one Component for each group");
					j2("#ErrorMsg").show();
					parent.resizeAfterPaginationChange();
					rtrnFlag= false;
					return false;
				}else{
					j2("#ErrorMsg").hide();
				}
				
			
		});
		
		//checking For Duplicate Group names
		var grpNamesArray = new Array();
		j2("#assignedComponentsTable tbody:not(.displayNone)").each(function (index,data) {
			
			j2(data).children().each(function(componentIndex,component){
				
				if(j2(component).hasClass('group')){//for setting component hidden field names
					var curGrpName=j2(component).find('.groupName').text();
					
					if(curGrpName.trim() !=''){
						var convertedName=curGrpName.trim().toUpperCase();
						
						if(j2.inArray(convertedName, grpNamesArray) == -1) { //Not found
							grpNamesArray.push(convertedName);
						  } else {
							  j2("#ErrorMsg span").text("Group name: "+curGrpName+" found duplicate, Please change the group name.");
							  j2("#ErrorMsg").show();
							  parent.resizeAfterPaginationChange();
							  rtrnFlag= false;
							  return false;
						  }   
					}else{
						  j2("#ErrorMsg span").text("Please enter Group Name for all groups.");
						  j2("#ErrorMsg").show();
						  parent.resizeAfterPaginationChange();
						  rtrnFlag= false;
						  return false;
					}
					
					
				}
					
				});
		});
	}
	
	return rtrnFlag;
}

/* function to add new group */
function Addgroup(groupName){
	groupCount++;	
	j2("#assignedComponentsTable").append(
	 '<tbody class="ui-sortable-handle ui-sortable">'+
	          '<tr class="group"><td style="width:10%" colspan="1" class="pointer">'+
			  '<span class="icon-bar"></span>'+
			  '<span class="icon-bar"></span>'+
			  '<span class="icon-bar"></span>'+
			  '</td>'+
			  '<td style="width:5%" class="editable centerAlign">'+
			  '</td>'+
			  '<td style="width:7%"></td>'+
			  '<td colspan="3">'+
			  '<div>'+
			  '<div  style="height:20px;" id="editgroup-'+groupCount+'" class="groupName floatLeft">'+groupName+'</div> <div id="edit-'+groupCount+'"'+
			  'style="float: right;margin-right: 1%;" class="editGroup floatRight"><u> Edit </u></div>'+
			  '</div>'+
			  '<input type="hidden" id="formGroupSeq-'+groupCount+'"  name="groupMasterList['+groupCount+'].displaySeq"/><input type="hidden" id="formGroupName-'+groupCount+'" name="groupMasterList['+groupCount+'].name"/>'+
			  '<input type="hidden" name="groupMasterList['+groupCount+'].grpMasterId"/>'+
			  '<input type="hidden" path="groupMasterList['+groupCount+'].seqMasterId"/>'+
			  '</td></tr></tbody>');
	initializeComponentTables();
	j2("#hiddenHeader").hide();
	j2("#ErrorMsg").hide();
	}


function reloadUnassignedComponents(category,type){
	processingImageAndTextHandler('visible','Loading data...');
	j2("#loadsheet-sequencing-form").attr("action", "open-create-sequence.htm");  //change the form action
	j2("#loadsheet-sequencing-form").submit();  // submit the form
	
	//window.location.href = './open-create-loadsheet.htm?category='+category+'&type='+type;
	
}
/* function to clear unassigned components from the form binding */
function clearUnassignedComponentsfromFormBinding(){
	  j2("#unAssignedComponentsTable tr").each(function () { 
		  // count = j2(this).parent().children().index(j2(this)) + 0;
		  // var row=j2(this).parents("tr");
			 
			 //remove the name attribute
		   j2(this).find('.componentId').removeAttr('name'); 
		   j2(this).find('.componentSequence').removeAttr('name'); 
		   j2(this).find('.compGrpSeqId').removeAttr('name');
		   j2(this).find('.grpMasterId').removeAttr('name');
		});
	
}

function openAddGroupModal(){
	$('#addGroupModal').dialog('open');
	  $("#AddGrpErrorMsg").hide();
	
}
function addGroupName(){
	var newGrpName=j2('#newGroupName').val();
	
	if(j2.trim(newGrpName)==""){
		 j2("#AddGrpErrorMsg span").text("Please enter Group Name");
		  j2("#AddGrpErrorMsg").show();
	}else{
		//Checking if the group name already exists
		var grpNamesArray = new Array();
		j2("#assignedComponentsTable tbody:not(.displayNone)").each(function (index,data) {
			j2(data).children().each(function(componentIndex,component){
				var curGrpName=j2(component).find('.groupName').text();
				var convertedName=curGrpName.trim().toUpperCase();
				if(j2(component).hasClass('group')){
						grpNamesArray.push(convertedName);
				}
				});
		});
		
		var convrtdNewGrpName=newGrpName.trim().toUpperCase();
		if(j2.inArray(convrtdNewGrpName, grpNamesArray) == -1) { //Not found
			
			Addgroup(j2('#newGroupName').val());
			closeaddGroupDialog();
			
		  } else {
			  
			  j2("#AddGrpErrorMsg span").text("Group Name: "+newGrpName+" already exists !");
			  j2("#AddGrpErrorMsg").show();
			}   

		
	}
}


function closeaddGroupDialog(){
	$('#newGroupName').val('');
	$('#addGroupModal').dialog('close');
}
