$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-sequence");
	var commonStaticUrl =$('#common-static-url').val();
	$templateTable=$("#sequence-table");
	var iDisplayLength = 10;//tableRowLengthCalc();
	//sequence table
	$templateTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "asc" ]], //default sort column
		"bPaginate": true, //enable pagination
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [0] } ],//disable sorting for specific column indexes
		"bAutoWidth": false, //cray cray
		"bLengthChange": false, //enable change of records per page, not recommended
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"bInfo": true, //Showing 1 to 10 of 11 entries
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
	
	// category and type drop downs
	var dropdowns='<div id="org-desc-div" style="float: left; text-align: right;">'+
	'<label>Category :</label>'+ 
	'<select  id="category"  onChange="getLoadsheetSequences()" ></select>'+
	'<label style="margin-left: 5px;">  Type :</label>'+ 
	'<select  id="type"  onChange="getLoadsheetSequences()" ></select>'+
    '</div>';
	
	//Add loadsheet sequence link
	var strHTML='<span style="margin-right: 10px;" class="floatLeft addRow">'+
	'<a href="create-loadsheet.htm" onclick="javascript:loadProcessImage();">Add Loadsheet Sequence </a>'+
	'<img src='+commonStaticUrl+'/images/add.png class="centerImage handCursor" alt="Add Load sheet Sequence"/>'+
    '</span>';

	// show Add loadsheet sequence link except for view mode.
	if($('#viewMode').val()!='Y'){
	$("#sequence-table_filter").prepend(strHTML);
	}
	$("#sequence-table_wrapper").prepend(dropdowns);
	
	//populate category and type dropdown values..
	$categoryOptions = $("#categoryHideen > option").clone();
	$('#category').append($categoryOptions);
	$('#category').val($('#selectedCategory').val());
	
	$typeOptions = $("#typeHidden > option").clone();
	$('#type').append($typeOptions);
	$('#type').val($('#selectedType').val());
	
	
	$('#sequence-table tbody tr').on( 'click', '#deleteRule', function () {
		
		
		var $this = $(this);
		var ruleId=$this.closest('tr').find('#ruleId').val();
		
		
		$.ajax({
			  type: "POST",
			  url: "./delete-sequence.htm",
			  data: {ruleId : ruleId},
			  success: function(data){
				  
				 var $row = $this.closest("tr");
				$loadsheetRuleTable.dataTable().fnDeleteRow($row[0]);
			  }
			});
		
		
		
		
		
	});
	
	
	});
	/* function to load the loadsheet sequences based on selected category and type*/
	function getLoadsheetSequences(){
		var category = $('#category').val();
		var type = $('#type').val();
		var viewMode = $('#viewMode').val();
		var path= 'get-loadsheet-sequence.htm?categoryId=&category='+category+'&type='+type+'&viewMode='+viewMode;
		window.location.href = path;
		processingImageAndTextHandler('visible','Loading data...');
	}

	


