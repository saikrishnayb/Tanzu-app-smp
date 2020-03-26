selectCurrentNavigation("tab-oem-build-matrix", "left-nav-business-award-maint");
	
var $businessAwardMainTable = $('#business-award-maint-table');
var $addBtn = $('#add-mfr');
var $deleteBtn = $('#delete-mfr');
var $saveBtn = $('#save-oem-mix');
var $oemMixModal = $('#oem-mix-modal');

$businessAwardMainDataTable = $businessAwardMainTable.DataTable( {
	"bPaginate" : true, //enable pagination
	"bStateSave" : true, //To retrieve the data on click of back button
	"sPaginationType" : "two_button",
	"bLengthChange" : true, //enable change of records per page, not recommended
	"bFilter" : true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
	"bAutoWidth" : false,
	"bSort" : true, //Allow sorting by column header
	"bInfo" : true, //Showing 1 to 10 of 11 entries
	"sPaginationType" : "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
	"iDisplayLength" : 100, //number of records per page for pagination
	"columnDefs": [
		{"targets": [ 0], "className":   "hidden", "searchable": false, "sortable": false },
		{"targets": [1], "searchable": false, "sortable": false }
	],
	"dom": "t",
	drawCallback : function () {
	    var api = this.api();
        var rows = api.rows({page:'current'}).nodes();
        var rowsLength = rows.length;

	    var noTableRows = rows === 0;
	    if(noTableRows) return;
	    
	    var columnLength = $(rows[0]).find('td').length;
	    if(!columnLength)
	      columnLength = 1
	    
	    var lastCategory = "";

	    api.column(0, {page:'current'}).nodes().each(function (node, index) {
	      
	      var category = node.textContent;
	      
	      var sameCategory = category === lastCategory;
	      
	      if(sameCategory) return true;
	      
	      var poCategory = $(rows[index]).find('td.po-category').text();
	      
	      var groupingHeaderTrElement = document.createElement('tr');
	      groupingHeaderTrElement.className = "second-level-group";
	      groupingHeaderTrElement.setAttribute("data-po-category", poCategory);
	      
	      var tdElement = document.createElement('td');
	      tdElement.colSpan = columnLength;
	      tdElement.innerHTML = poCategory;
	      
	      groupingHeaderTrElement.appendChild(tdElement);
	      rows[index].parentNode.insertBefore(groupingHeaderTrElement, rows[index]);
	      
	      lastCategory = poCategory;
	      
	    });
	    
	  }
	  
});

ModalUtil.initializeModal($oemMixModal, "auto");

$('#mix-search').on('keyup',function(){
	$businessAwardMainDataTable.search($(this).val()).draw() ;
});

$('#mass_select_all').on('click', function() {
	var rows, checked;
	var  allPagesd = $businessAwardMainDataTable.rows().nodes()
	if ($(this).is(':checked')) {
		$(allPagesd).find('input[type="checkbox"]').prop('checked', true);
	} 
	else {
		$(allPagesd).find('input[type="checkbox"]').prop('checked', false);
	}
       // $(this).toggleClass('allChecked');
});

$addBtn.on('click', function(){
	
	var $getAddOemModalPromise = $.ajax({
		type : 'POST',
		url : baseBuildMatrixUrl + '/load-add-oem-popup'
	});
	  
	$getAddOemModalPromise.done(function(data) {
		var htmlContent = data;
		$oemMixModal.html(htmlContent);
		
		ModalUtil.openModal($oemMixModal);
	});
});
  

function saveCheckedBoxes(id) {
	var checkedRowCount = 0;
	var rowCount = $businessAwardMainDataTable.rows().nodes().length;
	$($businessAwardMainDataTable.rows().nodes()).each(function() {
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