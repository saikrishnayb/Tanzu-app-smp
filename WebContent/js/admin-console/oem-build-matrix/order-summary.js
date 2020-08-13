var $orderSummaryTable = $('#order-summary-table');
var $addToBuildBtn = $('#add-to-build');

selectCurrentNavigation("tab-oem-build-matrix", "");

var $orderSummaryDataTable = $orderSummaryTable.DataTable({ //All of the below are optional
		"bPaginate" : true, //enable pagination
		"bStateSave" : false, //To retrieve the data on click of back button
		"sPaginationType" : "two_button",
		"aaSorting" : [], //default sort column
		"aoColumnDefs" : [ {
			'bSortable' : false,
			'aTargets' : [ 0 ]
		} ],
		"bLengthChange" : true, //enable change of records per page, not recommended
		"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bAutoWidth" : false,
		"bSort" : true, //Allow sorting by column header
		"bInfo" : true, //Showing 1 to 10 of 11 entries
		"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		"iDisplayLength" : 100, //number of records per page for pagination
		"oLanguage" : {
			"sEmptyTable" : "No Results Found"
		},
		"dom": 'tip',
		//"sScrollY": 246, //Adds a vertical scroll bar if the content exceeds this amount
		//"sScrollXInner": "100%" 
		"fnDrawCallback" : function() { //This will hide the pagination menu if we only have 1 page.
			var paginateRow = $(this).parent().children('div.dataTables_paginate');
			var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);

			if (pageCount > 1) {
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
});

calculateBodiesOnOrder();

$('#order-search').on('keyup',function(){
	$("#mass-select-all").prop('checked', false);
	var isChecked = $('#show-selected-checkbox').is(':checked');
	if(isChecked) {
	    $.fn.dataTable.ext.search.push(
		    function (settings, data, dataIndex){             
		        return ($($orderSummaryDataTable.row(dataIndex).node()).hasClass('row-selected')) ? true : false;
		    }
		);
		  
	    $orderSummaryDataTable.search($(this).val()).draw() ;
		  
		$.fn.dataTable.ext.search.pop();
	}
	else {
		$orderSummaryDataTable.search($(this).val()).draw() ;
	}
});

$("#show-selected-checkbox").on('click', function(){
	var isChecked = $(this).is(':checked');
	if(isChecked) {
	    $.fn.dataTable.ext.search.push(
		    function (settings, data, dataIndex){             
		        return ($($orderSummaryDataTable.row(dataIndex).node()).hasClass('row-selected')) ? true : false;
		    }
		);
		  
	    $orderSummaryDataTable.draw();
		  
		$.fn.dataTable.ext.search.pop();
	}
	else {
		$("#mass-select-all").prop('checked', false);
		$orderSummaryDataTable.draw();
	}
});

$('#mass-select-all').on('click', function() {
	 var isChecked = $(this).is(':checked');
	 var  $rows = $orderSummaryTable.find('.approved-order-row');
	 $rows.each(function(){
		 var $row = $(this);
		 if (isChecked) {
			 $row.find('input[type="checkbox"]').prop('checked', true);
			 $row.addClass('row-selected');
	     } 
		 else {
			 $row.find('input[type="checkbox"]').prop('checked', false);
			 $row.removeClass('row-selected');
	     }
		 
	 })
	  
       // $(this).toggleClass('allChecked');
	  calculateBodiesOnOrder();
 });
	
$('#add-to-build').on('click', function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var selectedRows = [];
	
	$($orderSummaryDataTable.rows().nodes()).each(function(){
		if($(this).find('.select-order').prop('checked')){
			selectedRows.push(this);
		}
	});
	
	if(selectedRows.length < 1)
		return false;
	
	var orderSelectionForm = $('#order-selection-form').empty();
		
	for (var i = 0; i < selectedRows.length; i++) {
		var $row = $(selectedRows[i]);
		var orderId = $row.data('order-id');
		var deliveryId = $row.data('delivery-id');
		var unitsToConsider = $row.find('.units-to-consider').val();
		
		var orderIdInput = document.createElement('input');
		orderIdInput.type = 'hidden';
		orderIdInput.name = 'selectedOrders[' + i + '].orderId'
		orderIdInput.value = orderId;
		orderSelectionForm.append(orderIdInput);
	    
		var deliveryIdInput = document.createElement('input');
		deliveryIdInput.type = 'hidden';
		deliveryIdInput.name = 'selectedOrders[' + i + '].deliveryId'
		deliveryIdInput.value = deliveryId;
		orderSelectionForm.append(deliveryIdInput);
		
		var unitsToConsiderInput = document.createElement('input');
		unitsToConsiderInput.type = 'hidden';
		unitsToConsiderInput.name = 'selectedOrders[' + i + '].unitsToConsider'
		unitsToConsiderInput.value = unitsToConsider;
		orderSelectionForm.append(unitsToConsiderInput);
	}
	
	var buildId = orderSelectionForm.data('build-id');
	buildId = buildId == "" ? null : buildId;
	var buildIdInput = document.createElement('input');
	buildIdInput.type = 'hidden';
	buildIdInput.name = 'buildId'
	buildIdInput.value = buildId;
	orderSelectionForm.append(buildIdInput);
	
	orderSelectionForm.submit();
	
});

$('.units-to-consider').on('input', function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
    
    var val = this.value;
    if(val == "")
    	val = "0";
    var unitsToConsider = parseInt(val);
    var $row = $(this).closest('.approved-order-row');
    var unfulfilledQty = $row.data('unfulfilled-qty');
    
    if(unitsToConsider > unfulfilledQty || unitsToConsider == 0){
    	$(this).addClass('error-input');
    }
    else {
    	$(this).removeClass('error-input');
    }
    
    calculateBodiesOnOrder();
});

function saveCheckedBoxes(id) {
	var checkedRowCount = 0;
	var rowCount = $orderSummaryDataTable.rows().nodes().length;
	var sum = 0;
	var hasErrors = false;
	$($orderSummaryDataTable.rows().nodes()).each(function() {
		 if($(this).find('.select-order').prop("checked") == true ) {
			 $(this).addClass('row-selected');
			 checkedRowCount++;
			 if(rowCount == checkedRowCount) {
				$('#mass-select-all').prop('checked', true); 
				}
			 var unitsToConsider = $(this).find('.units-to-consider').val();
			 if(unitsToConsider == "")
				 unitsToConsider = "0";
			 var quantity =  parseInt(unitsToConsider, 10);
			 sum += quantity;
		 } else {
				$('#mass-select-all').prop('checked', false);
				$(this).removeClass('row-selected');
		 }
		 if($(this).find('.units-to-consider').hasClass('error-input'))
			 hasErrors = true;
	 });
	$('#bodies-on-order').html(sum);
	checkAddToBuild(hasErrors);
}

function calculateBodiesOnOrder(){
	var sum = 0;
	var hasErrors = false;
	$($orderSummaryDataTable.rows().nodes()).each(function() {
		if($(this).find('.select-order').prop("checked") == true ) {
			var unitsToConsider = $(this).find('.units-to-consider').val();
			if(unitsToConsider == "")
				unitsToConsider = "0";
			var quantity =  parseInt(unitsToConsider, 10);
			sum += quantity;
		}
		if($(this).find('.units-to-consider').hasClass('error-input'))
			 hasErrors = true;
	});
	$('#bodies-on-order').html(sum);
	checkAddToBuild(hasErrors);
}

function checkAddToBuild(hasErrors) {
	var sum = parseInt($('#bodies-on-order').html());
	
	if(sum > 0 && !hasErrors)
		$addToBuildBtn.removeClass('buttonDisabled');
	else
		$addToBuildBtn.addClass('buttonDisabled');
}
