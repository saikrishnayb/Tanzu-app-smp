$(document).ready(function() {

	selectCurrentNavigation("tab-oem-build-matrix", "left-nav-business-award-maint");
	
	  $businessAwardMainTable = $('#business-award-maint-table').DataTable( {
		  	"bPaginate" : true, //enable pagination
			"bStateSave" : true, //To retrieve the data on click of back button
			"sPaginationType" : "two_button",
			"bLengthChange" : true, //enable change of records per page, not recommended
			"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
			"bAutoWidth" : false,
			"bSort" : false, //Allow sorting by column header
			"bInfo" : true, //Showing 1 to 10 of 11 entries
			"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
			"iDisplayLength" : 100, //number of records per page for pagination
			 order: [[0, 'asc']],
	        "columnDefs": [
	            {
	                "targets": [ 0 ],
	                "visible": false,
	                "searchable": false
	            }],
	        rowGroup: {
	            startRender: function ( rows, group ) {
	            return $('<tr/>')
                    .append( '<td class="rowgroupHeader" colspan="4">'+group+'</td>' );
                   
            },
	            endRender: function ( rows, group ) {
	                var valueAvg = rows
	                    .data()
	                    .pluck(3)
	                    .reduce( function (a, b) {
	                        return a + $(b).val()*1;
	                    }, 0) / rows.count();
	               
	                	return $('<tr/>')
	                    .append( '<td/><td/><td><input type="text" class='+group+' style="width: 7%;text-align: center;" disabled value="'+valueAvg.toFixed(2)+'">%</td>' );
	            },
	            dataSrc: 0
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
	    } );
	  
	$('#mass_select_all').on('click', function() {
		   var rows, checked;
		 var  allPagesd = $businessAwardMainTable.rows().nodes()
		  if ($(this).is(':checked')) {
	            $(allPagesd).find('input[type="checkbox"]').prop('checked', true);
	        } else {
	            $(allPagesd).find('input[type="checkbox"]').prop('checked', false);
	        }
		 });
	  
	  $('.oem-value').autoNumeric('init',{vMax:'99.99', vMin:'0.00'});
	  $('.oem-value').on('keyup keypress',function(){
		  var oemId=$(this).attr('oem-id');
		  $('.oem-value[oem-id='+oemId+']').trigger('input');
	  });
});

function saveCheckedBoxes(id) {
	var checkedRowCount = 0;
	var rowCount = $businessAwardMainTable.rows().nodes().length;
	$($businessAwardMainTable.rows().nodes()).each(function() {
		 if($(this).find('#select-oem'). prop("checked") == true ) {
			 checkedRowCount++;
			 if(rowCount == checkedRowCount) {
				$('#mass_select_all').prop('checked', true); 
				}
		 } else {
				$('#mass_select_all').prop('checked', false);
		 }
	 });	 
}

function calculateAverage(currentoemId,groupCategory)
{
	var avg=parseFloat($("[oem-id="+currentoemId+"]").val());
	var count=1;
	$($businessAwardMainTable.rows().nodes()).each(function() {
		
		var $valueInput=$(this).find('.oem-value')
		if($valueInput.attr('group-name')==groupCategory && $valueInput.attr('oem-id') != currentoemId)
			{
			avg += parseFloat($valueInput.val());
			count++;
			}
		
	});
	avg/=count;
	$("."+groupCategory).val(avg.toFixed(2));
}