var srcId;
var destId;
var col1;
var col2;
var groupCount=1; //get this value based on max group count
$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-sequence");
	//var commonStaticUrl =$('#common-static-url').val();
	
	groupCount=$("#numberOfGroups").val();
	
	initializeComponentTables();
	//Search for assigned components table
	$("#assignedSearch").keyup(function(){
        $this = this;
        // Show only matching TR, hide rest of them
        $.each($("#assignedComponentsTable tbody tr"), function() {
            if($(this).text().toLowerCase().indexOf($($this).val().toLowerCase()) === -1)
               $(this).hide();
            else
               $(this).show();                
        });
        
    });
	
	//serach for unassigned components table
	$("#unAssignedSearch").keyup(function(){
        $this = this;
        // Show only matching TR, hide rest of them
        $.each($("#unAssignedComponentsTable tbody tr"), function() {
            if($(this).text().toLowerCase().indexOf($($this).val().toLowerCase()) === -1)
               $(this).hide();
            else
               $(this).show();                
        });
        $("#hiddenRow").hide(); //hide the hidden row while copying the group
    }); 

	//delete row
	$('#assignedComponentsTable').on( 'click', '#deleteRow', function () {
		 var row=$(this).parents("tr");
		 
		 //remove the name attribute
		 $(row).find('.componentId').removeAttr('name'); 
		 $(row).find('.componentSequence').removeAttr('name'); 
		 $(row).find('.compGrpSeqId').removeAttr('name');
		 $(row).find('.grpMasterId').removeAttr('name');
		 
		 row.find('td')[1].remove();
		 row.find('td')[1].remove();
		 $(row).appendTo("#unAssignedComponentsTable");
		 
		 
		 resetSequence('#assignedComponentsTable');
		  $("#hiddenRow").hide(); //hide the hidden row while copying the group
		 
	} );
	 
	
	
	//delete Group
	$('#assignedComponentsTable').on( 'click', '#deleteGroup', function () {
		 var tbody=$(this).parents("tbody");
		 var content = tbody.contents();
		tbody.remove(); 
		$(content).each(function(index,data){
			if($(data).find('td').length == 6 ){
				$(data).find('td')[1].remove();
				$(data).find('td')[1].remove();
				$(data).appendTo("#unAssignedComponentsTable tbody");
			}
		});
		
		  $("#hiddenRow").hide(); //hide the hidden row while copying the group 
	});
	
	
	//Refresh Unassigned Componets on change of category or typr
	$("#categoryID").on("change",function(){
		var type=$("#typeID option:selected").val();
		reloadUnassignedComponents(this.value,type);
		
	});
	
	$("#typeID").on("change",function(){
		var category=$("#categoryID option:selected").val();
		reloadUnassignedComponents(category,this.value);
		
	});


});

function initializeComponentTables(){
$("table").sortable({
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
	  
	  ui.item[0].remove();	//remove the tbody   from first table
	  //remove extra columns in rows
	  ui.item.children().each(function(i,data){
		  
		 var rowData=new Array();
		  $(this).children().each(function(i,data){
			rowData.push(data);  
		  });
		rowData.splice(1,2);
		$(this).children().replaceWith(rowData);
		
			$('#unAssignedComponentsTable tbody').append($(this));//append the rows to the second table tbody
	});
	  
	  $("#hiddenRow").hide(); //hide the hidden row while copying the group
	
	},
  placeholder: "ui-state-highlight",
  forcePlaceholderSize: true,
  connectWith: "table",
  items: "tbody:not(.header)",
  
}).disableSelection();

$("table tbody").sortable({
  cancel: ".group .pointer",
  helper: fixWidthHelper,
  start: function(e, ui) {
	// modify ui.placeholder however you like
    ui.placeholder.find('tr').outerHeight(ui.item.outerHeight(true));
	srcId=$(this).parent().attr('id');
	},
  over: function( event, ui ) {
	destId=$(this).parent().attr('id');
  },
  receive: AddRemoveExtraColumns ,
  update: function(e, ui) {resetSequence('#assignedComponentsTable');},
  placeholder: "ui-state-highlight",
  forcePlaceholderSize: true,
  items: "tr:not(.group)",
  connectWith: "table tbody"
}).disableSelection();

}
function fixWidthHelper(e, ui) {
  ui.children().each(function() {
    $(this).width($(this).width());
  });
  return ui;
}

function fixWidthHelper1(e, ui) {
  ui.find('tr').children().each(function() {
    $(this).width($(this).width());
  });
  return ui;
}
//Renumber table rows
function resetSequence(tableID) {
   $("#assignedComponentsTable tr").each(function () { 
   count = $(this).parent().children().index($(this)) + 0;
   $(this).find('.seq').html(count); 
});

}

function AddRemoveExtraColumns(e,ui){
	
	if(srcId!=destId){
		var rowData=new Array();
		//var rowData=ui.item.children().parent().html();
		ui.item.children().each(function(i,data){
			rowData.push(data);
		});
		
		
		
		//if columns size is 6 remove the extra 2 columns
		if(rowData.length==6){
			rowData.splice(1,2);
			ui.item.children().replaceWith(rowData);
		}else{
			var col1='<td style="width:5%" class="editable centerAlign"><img class="rightMargin" id="deleteRow" src="https://staticdev.penske.com/common/images/delete.png"></td>';
			var col2='<td style="width:7%" class="centerAlign seq"></td>';
			ui.item.children().each(function(i,data){
				 if(i==1){
					 $(this).replaceWith(col1);
				 }else if(i==2){
					 $(this).replaceWith(col2);
				}else if (i==3){
					 $( rowData[1] ).insertBefore($(this));
					 $( rowData[2] ).insertBefore($(this));
					
				 }
				
			});
		
		}
		//to add a hidden row in table 2 if table size is 0
		if($('#unAssignedComponentsTable tr').length == 2){
			$("#hiddenRow").show();
		}else{
			$("#hiddenRow").hide();
		}
	}
	  
   
	
}

$("#assignedComponentsTable").on('keypress', '.groupName' , function(e){
	var editGroupId=$(this).attr('id');
	editGroupId="editgroup-"+editGroupId.split('-')[1];
	check_charcount(editGroupId,50, e);
});
/* function to check the group name character count */
function check_charcount(content_id, max, e)
{   
    if(e.which != 8 && $('#'+content_id).text().length > max)
    {
       e.preventDefault();
    }
}

/* function to edit the group name */
$("#assignedComponentsTable").on('click', '.editGroup' , function(e){
	var editGroupId=$(this).attr('id');
	editGroupId="editgroup-"+editGroupId.split('-')[1];
	$('#'+editGroupId).attr('contenteditable','true');
	$('#'+editGroupId).focus();
	var textNode = $('#'+editGroupId).get(0);
	var caret = 0; // insert caret at first character
	var range = document.createRange();
	range.setStart(textNode, caret);
	range.setEnd(textNode, caret);
	var sel = window.getSelection();
	sel.removeAllRanges();
	sel.addRange(range);
	event.stopPropagation();
	});

/* to make group name non editable */
$(document).click(function() {
	$(".groupName").attr('contenteditable','false');
	
});


/* to submit the from */
function submitLoadSheetForm(){
	
	var $loadSheetSequncingForm=$("#loadsheet-sequencing-form");
	
	//Setting the group name and seq to form
	$(".groupName").each(function(index,data){
		var editGroup=$(this).attr("id");
		var count=editGroup.split("-")[1];
		$("#formGroupName-"+count).val($("#"+editGroup).text());
		$("#formGroupSeq-"+count).val(index+1);
		
	});
	
	var groupIndex=0;
	
	//Setting the hidden component id's and display seq
	  $("#assignedComponentsTable tbody").each(function (index,data) {
			$(data).children().each(function(componentIndex,component){
				
		        if(!$(component).hasClass('group')){//for setting component hidden field names
		        	
			       // $(component).find('.seq').html(componentIndex);
			        $(component).find('.componentId').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].componentId'); 
					$(component).find('.componentSequence').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].displaySeq'); 
					 $(component).find('.componentSequence').attr('value',componentIndex);
					 $(component).find('.compGrpSeqId').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].compGrpSeqId');
					 $(component).find('.grpMasterId').attr('name','groupMasterList[' + groupIndex+'].compGrpSeqList['+componentIndex+'].grpMasterId');
					  }else{//update group index
						  var groupId=$(component).find('.groupName').attr("id");
						  groupIndex=groupId.split("-")[1];
						  
					}
				});
		
		});

	
	
	var seqMasterId=$("#seqMasterId").val();
	
	if(seqMasterId=="0"){//Perform Insert
		$loadSheetSequncingForm.attr("action","create-sequence.htm");
	}else{//perform Update
		$loadSheetSequncingForm.attr("action","update-sequence.htm");
	}
	
	
	processingImageAndTextHandler('visible','Loading data...');
	$loadSheetSequncingForm.submit();
	
}

/* function to add new group */
function Addgroup(){
	groupCount++;	
	$("#assignedComponentsTable").append(
	 '<tbody class="ui-sortable-handle ui-sortable">'+
	          '<tr class="group"><td style="width:10%" colspan="1" class="pointer">'+
			  '<span class="icon-bar"></span>'+
			  '<span class="icon-bar"></span>'+
			  '<span class="icon-bar"></span>'+
			  '</td>'+
			  '<td style="width:5%" class="editable centerAlign">'+
			  '<img class="rightMargin" id="deleteGroup" src="https://staticdev.penske.com/common/images/delete.png"></td>'+
			  '<td style="width:7%"></td>'+
			  '<td colspan="3"><div id="editgroup-'+groupCount+'" class="groupName" style="float: left">GROUP NAME</div> <div id="edit-'+groupCount+'"'+
			  'style="float: right;margin-right: 1%;" class="editGroup"> Edit</div>'+
			  '<input type="hidden" id="formGroupSeq-'+groupCount+'"  name="groupMasterList['+groupCount+'].displaySeq"/><input type="hidden" id="formGroupName-'+groupCount+'" name="groupMasterList['+groupCount+'].name"/>'+
			  '<input type="hidden" name="groupMasterList['+groupCount+'].grpMasterId"/>'+
			  '<input type="hidden" path="groupMasterList['+groupCount+'].seqMasterId"/>'+
			  '</td></tr></tbody>');
	initializeComponentTables();
	}


function reloadUnassignedComponents(category,type){
	
	processingImageAndTextHandler('visible','Loading data...');
	window.location.href = './open-create-sequence.htm?category='+category+'&type='+type;
	
}
