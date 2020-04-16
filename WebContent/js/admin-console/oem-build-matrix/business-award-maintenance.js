selectCurrentNavigation("tab-oem-build-matrix", "left-nav-business-award-maint");
var $businessAwardMainTable = $('#business-award-maint-table');
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
		{"targets": [ 0], "className":   "hidden", "sortable": false },
		{"targets": [1,2], "sortable": false }
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
	    
	    var lastAttributeName = "";
	    var lastAttributeId = "";

	    api.column(0, {page:'current'}).nodes().each(function (node, index) {
	      
    	if(index + 1 === api.column(0, {page:'current'}).nodes().length) {
	    	  var attributeName = $(rows[index]).find('td.attribute-name').text();
		      var attributeId = $(rows[index]).data('attribute-id');
		      
	    	  var totalTrElement = document.createElement('tr');
		      totalTrElement.className = "total-row";
		      totalTrElement.setAttribute("data-attribute-id", attributeId);
		      
		      var totalTd = document.createElement('td');
		      totalTd.innerHTML = '<label class="floatRight">Total</label>';
		      totalTrElement.appendChild(totalTd);
		      
		      var totalInputTd = document.createElement('td');
		      totalInputTd.innerHTML = '<input class="total-percentage" type="text" value="0" disabled>%';
		      totalTrElement.appendChild(totalInputTd);
		      
		      $(rows[index]).after(totalTrElement);
	      }
	    	
	      var attribute = node.textContent;
	      
	      var sameAttribute = attribute === lastAttributeName;
	      
	      if(sameAttribute) return true;
	      
	      var attributeName = $(rows[index]).find('td.attribute-name').text();
	      var attributeId = $(rows[index]).data('attribute-id');
	      
	      if(index != 0) {
		      var totalTrElement = document.createElement('tr');
		      totalTrElement.className = "total-row";
		      totalTrElement.setAttribute("data-attribute-id", lastAttributeId);
		      
		      var totalTd = document.createElement('td');
		      totalTd.innerHTML = '<label class="floatRight">Total</label>';
		      totalTrElement.appendChild(totalTd);
		      
		      var totalInputTd = document.createElement('td');
		      totalInputTd.innerHTML = '<input class="total-percentage" type="text" value="0" disabled>%';
		      totalTrElement.appendChild(totalInputTd);
		      
		      rows[index].parentNode.insertBefore(totalTrElement, rows[index]);
	      }
	      
	      var groupingHeaderTrElement = document.createElement('tr');
	      groupingHeaderTrElement.className = "second-level-group";
	      groupingHeaderTrElement.setAttribute("data-attribute-name", attributeName);
	      
	      var tdElement = document.createElement('td');
	      tdElement.colSpan = columnLength;
	      tdElement.innerHTML = attributeName;
	      
	      groupingHeaderTrElement.appendChild(tdElement);
	      rows[index].parentNode.insertBefore(groupingHeaderTrElement, rows[index]);
	      
	      lastAttributeName = attributeName;
	      lastAttributeId = attributeId;
	      
	    });
	    
	  }
	  

});

ritsu.storeInitialFormValues('input');

ModalUtil.initializeModal($oemMixModal, "auto");

updateTotals();

$('#mix-search').on('keyup',function(){
	$businessAwardMainDataTable.search($(this).val()).draw() ;
});

$businessAwardMainTable.on('input', 'input', function(){
	var isDirty = ritsu.isFormDirty('input');
	
	var noEmpties = true;
	var $percentageInputs = $('.attribute-percentage');
	$percentageInputs.each(function(){
		if($(this).val().trim() == '')
			noEmpties = false;
	})
	
	updateTotals();
	
	var allEqual100Percent = true
	$('.total-percentage').each(function(){
		var percentage = parseInt($(this).val());
		if(percentage != 100)
			allEqual100Percent = false;
	});
	
	if(isDirty && noEmpties && allEqual100Percent)
		$saveBtn.removeClass('buttonDisabled');
	else
		$saveBtn.addClass('buttonDisabled');
});

$saveBtn.on('click', function(){
	if($(this).hasClass('buttonDisabled'))
		return false;
	
	var $rows = $('.attribute-row');
	var $businessAwardForm = $('#business-award-form').empty();
	
	$rows.each(function(index){
		var $row = $(this); 
		
		var attributeValueId = $row.data('attribute-value-id');
		var groupId = $row.data('group-id');
		var percentage = parseInt($row.find('.attribute-percentage').val());
		
		var attributeValueIdInput = document.createElement('input');
		attributeValueIdInput.type = 'hidden';
		attributeValueIdInput.name = 'businessAwardRows[' + index + '].attributeValueId'
		attributeValueIdInput.value = attributeValueId;
		$businessAwardForm.append(attributeValueIdInput);
	    
		var groupIdInput = document.createElement('input');
		groupIdInput.type = 'hidden';
		groupIdInput.name = 'businessAwardRows[' + index + '].groupId'
		groupIdInput.value = groupId;
		$businessAwardForm.append(groupIdInput);
		
		var percentageInput = document.createElement('input');
		percentageInput.type = 'hidden';
		percentageInput.name = 'businessAwardRows[' + index + '].percentage'
		percentageInput.value = percentage;
		$businessAwardForm.append(percentageInput);
	});
	
	$.post(baseBuildMatrixUrl + '/save-business-award-maint', $businessAwardForm.serialize()).done(function(){
    	$saveBtn.addClass('buttonDisabled');
    	ritsu.storeInitialFormValues('input');
    })
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

function updateTotals() {
	$('.total-row').each(function(){
		var $totalRow = $(this);
		var attributeId = $totalRow.data('attribute-id');
		
		var totalPercentage = 0;
		var $attributeRows = $businessAwardMainTable.find('.attribute-row[data-attribute-id=' + attributeId + ']');
		
		$attributeRows.each(function(){
			var $row = $(this);
			var percentage = $row.find('.attribute-percentage').val();
			if(percentage.trim() == '')
				percentage = "0";
			totalPercentage += parseInt(percentage);
		});
		
		$totalRow.find('.total-percentage').val(totalPercentage);
	})
}