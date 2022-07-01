$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-sequence");
	var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
	$sequenceTable=$("#sequence-table");
	var iDisplayLength = -1;//tableRowLengthCalc();
	//sequence table
	$sequenceTable.dataTable( { //All of the below are optional
		"aaSorting"			: [[ 1, "asc" ]], //default sort column
		"bPaginate"			: true, //enable pagination
		"bStateSave"		: true,	//To retrieve the data on click of back button
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [0] },
		                      {"bSearchable": false, "aTargets": [0]}],//disable sorting for specific column indexes
		"bLengthChange"		: true, //enable change of records per page, not recommended
		"bFilter"			: true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort"				: true, //Allow sorting by column header
		"bInfo"				: true, //Showing 1 to 10 of 11 entries
		"sPaginationType"	: "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength"	: iDisplayLength , //number of records per page for pagination
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
		},
		"fnStateSave": function (oSettings, oData) {
            localStorage.setItem( 'loadSheetSeqTable', JSON.stringify(oData) );
        },
        "fnStateLoad": function (oSettings) {
            return JSON.parse( localStorage.getItem('loadSheetSeqTable') );
        }
	} );
	
	
	// category and type drop downs
	var dropdowns='<form name="open-sequencing-form" id="open-sequencing-form" action="open-create-sequence" method="POST"><div id="org-desc-div" style="float: left; text-align: right;">'+
	'<label>Category :</label>'+ 
	'<select name="category" id="category"  onChange="getLoadsheetSequences()" ></select>'+
	'<label style="margin-left: 5px;">  Type :</label>'+ 
	'<select name="type" style="width:100px;"  id="type" onChange="getLoadsheetSequences()" ></select>'+
	'<input id="backButton" name="viewMode" type="hidden" value="">'+
	'<input id="pageAction" name="pageAction" type="hidden" value="BACK">'+
    '</div></form>';
	
	//Add loadsheet sequence link
	var strHTML='<span style="margin-right: 10px;" class="floatLeft addRow">'+
	'<a href="#" onclick="submitOpenSequenceForm();" onclick="javascript:loadProcessImage();">Add Loadsheet Sequence </a>'+
	'<img src='+commonStaticUrl+'/images/add.png class="centerImage handCursor" alt="Add Load sheet Sequence"/>'+
    '</span>';

	// show Add loadsheet sequence link except for view mode.
	if($('#viewMode').val()!='Y'){
	$("#sequence-table_filter").prepend(strHTML);
	}
	$("#sequence-table_wrapper").prepend(dropdowns);
	$("#sequence-table_wrapper").prepend($("#sequence-table_length"));
	
	//populate category and type dropdown values..
	$categoryOptions = $("#categoryHideen > option").clone();
	$('#category').append($categoryOptions);
	$('#category').val($('#selectedCategory').val());
	
	$typeOptions = $("#typeHidden > option").clone();
	$('#type').append($typeOptions);
	$('#type').val($('#selectedType').val());
	
	
	$('#confirmDeleteModal').dialog({
		autoOpen: false,
		modal: true,
		dialogClass: 'popupModal',
		width: 370,
		minHeight: 150,
		resizable: false,
		title: 'Confirm',
		closeOnEscape: false,
		open: function(event, ui) {$(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();}
	});
	
	
	$('#sequence-table').on( 'click', '#deleteSequence', function () {
		
		$selectedRow = $(this);
		var sequenceName=$selectedRow.closest('tr').find('#sequenceName').val();
		openConfirmModal(sequenceName);
		
	});
	
	
	});
	/* function to load the loadsheet sequences based on selected category and type*/
	function getLoadsheetSequences(){
		var category = $('#category').val();
		var type = $('#type').val();
		var viewMode = $('#viewMode').val();
		var path= 'get-loadsheet-sequence?categoryId=&category='+category+'&type='+type+'&viewMode='+viewMode;
		window.location.href = path;
		processingImageAndTextHandler('visible','Loading data...');
	}

	function openConfirmModal(sequenceName){
		$('#confirmDeleteModal').dialog('open');
		$('#deleteMessage').text("Do you really want to delete the Sequence: "+sequenceName +" ?");
	}

	function confirmDeleteSequence(){
		
		
		var sequenceId=$selectedRow.closest('tr').find('#sequenceId').val();
		
		$.ajax({
			  type: "POST",
			  url: "./delete-sequence",
			  data: {sequenceId : sequenceId},
			  success: function(data){  
				var $row = $selectedRow.closest("tr");
				$sequenceTable.dataTable().fnDeleteRow($row[0]);
				closeConfirmDialog();
			  }
			});
		
	}


	function closeConfirmDialog(){
		$('#selectedRow').remove();
		$('#confirmDeleteModal').dialog('close');
	}
	
	function submitOpenSequenceForm(){
		$("#backButton").val($('#viewMode').val());
		processingImageAndTextHandler('visible','Loading data...');
		$("#open-sequencing-form").submit();  // submit the form
	}



