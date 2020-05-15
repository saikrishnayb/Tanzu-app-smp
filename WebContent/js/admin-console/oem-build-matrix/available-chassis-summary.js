selectCurrentNavigation("tab-oem-build-matrix", "");

/****************** Datatables Initialization ******************/
var eligibleUnitsTable = $('#eligible-unit-table').DataTable({
  "paging": false,
  "info": false,
  "searching": true,
  "order": [[1,"asc"]],
  "columnDefs": [
      {"targets": [0,5,6], "orderable": false},
      //only search the unit number 
      {"targets":[0,2,3,4,5,6], "searchable": false},
      {"targets": [0], "className": "checkbox-cell"},
      {"targets": [1], "className": "unit-range-cell"},
      {"targets": [2], "className": "corp-cell"},
      {"targets": [3], "className": "district-cell"},
      {"targets": [4], "className": "quantity-cell"},
      {"targets": [5], "className": "select-excluded-unit-quantity-cell"}
  ],
  //Just show the table plz
  "dom": 't',
  "scrollCollapse": true,
  "scrollY": "575px",
  responsive: true
});

var excludedUnitsTable = $('#excluded-units-table').DataTable({
  "paging":false,
  "info": false,
  "searching": false,
  "order": [[1,"asc"]],
  "columnDefs": [
	{"targets": [0,5], "orderable": false},
	{"targets": [1], "className": "unit-range-cell"},
    {"targets": [2], "className": "corp-cell"},
    {"targets": [3], "className": "district-cell"},
    {"targets": [4], "className": "quantity-cell"}
  ],
  "language": {
    "zeroRecords": "",
    "emptyTable": ""
  },
  "scrollCollapse": true,
  "scrollY": "575px",
  responsive: true
});

updateQuantities();

/****************** Modal Initialization ******************/
var $clearExcludeConfirmationModal = $('#modal-clear-exclude-confirmation');

ModalUtil.initializeModal($clearExcludeConfirmationModal);

/****************** Global variables ******************/
var rowUpdatedTimer;
var buildId = $('.available-units-table-top').data('build-id');

/****************** Event Handlers ******************/
/** Select Unit Quantity **/
$('.unit-quantity-textbox').on('keyup', function() {
  this.value = this.value.replace(/[^0-9\.]/g,'');
  var row = $(this).closest('tr');
  var unitQuantity = row.find('td.quantity-cell').html().trim();
  if(parseInt(this.value) > parseInt(unitQuantity))
    $(this).addClass('error-input');
  else {
    $(this).removeClass('error-input');
  }
});

$('.unit-quantity-textbox').on('blur', function() {
  updateSelectedUnitQuantity();
});
/** Unit Number Search **/
$('#unit-search').on('keyup', function() {
  var valueSearched=$(this).val();
  
  //this clears any existing filter
  $.fn.dataTable.ext.search.pop();
  
  //creates a custom filter to filter
  //this filters out the rows with unit arrays with unit numbers that do not contain the value entered
  //works with partial and full unit numbers
  //if nothing is entered, it won't apply any filter
  $.fn.dataTable.ext.search.push(
    function(settings, data, dataIndex) {
      //parse the array contained in the rows data-unit-array attribute
      var unitArray = JSON.parse(eligibleUnitsTable.row(dataIndex).node().getAttribute('data-unit-array'));
      if(!unitArray)
    	  return false;
      
      var valInUnitArray = false;
      //loop over strings in the array to see if value entered is in the string
      unitArray.forEach(function(unit) {
        if(unit.toString().includes(valueSearched))
          valInUnitArray = true;
        });
      return valInUnitArray;
    }
  );
  
  //draw table with filter
  eligibleUnitsTable.draw();
});

/** Unit Range Checkboxes**/
$('#select-all-unit-ranges').on('click', function() {
  if($(this).prop('checked')===true) {
    $('.select-unit-range').each(function() {
      if($(this).prop('disabled')!=true) {
        $(this).prop('checked',true);
        var quantityTextBox = $(this).closest('tr').find('#select-unit-quantity')
        quantityTextBox.prop('disabled', true);
        quantityTextBox.val("");
        quantityTextBox.removeClass('error-input');
      }
    });
    $('#multi-exclude-btn').removeClass('buttonDisabled');
  }
  else {
    $('.select-unit-range').each(function() {
      if($(this).prop('disabled')!=true) {
        $(this).prop('checked',false);
        var row = $(this).closest('tr');
        if(parseInt(row.find('.quantity-cell').html().trim()) != 1)
          row.find('#select-unit-quantity').prop('disabled', false);
      }
    });
    $('#multi-exclude-btn').addClass('buttonDisabled');
  }
  updateSelectedUnitQuantity();
});

$('.select-unit-range').on('click', function() {
  var boxesChecked = 0;
  var boxesTotal = $('.select-unit-range').length;
  $('.select-unit-range').each(function() {
    if($(this).prop('checked')===true)
      boxesChecked++;
  });
  
  if(boxesChecked === boxesTotal)
    $('#select-all-unit-ranges').prop('checked',true);
  else
    $('#select-all-unit-ranges').prop('checked',false);
  
  if(boxesChecked > 0)
    $('#multi-exclude-btn').removeClass('buttonDisabled');
  else
    $('#multi-exclude-btn').addClass('buttonDisabled');
  
  var row = $(this).closest('tr');
  if($(this).prop('checked')===true) {
    row.find('#select-unit-quantity').prop('disabled', true);
    row.find('#select-unit-quantity').val("");
    row.find('#select-unit-quantity').removeClass('error-input');
  }
  else {
    if(parseInt(row.find('.quantity-cell').html().trim()) != 1)
      row.find('#select-unit-quantity').prop('disabled', false);
  }
  updateSelectedUnitQuantity();
});

/** Exclude Buttons **/
$('#multi-exclude-btn').on('click', function() {
  var rows = [];
  $('.select-unit-range:checked').each(function() {
    if($(this).prop('disabled')!=true)
      rows.push($(this).closest('tr'));
  });
  
  var excludedUnits = []
  rows.forEach(function(row, index) {
	  var unitArray = JSON.parse(row.attr("data-unit-array"));
	  excludedUnits = excludedUnits.concat(unitArray);
  });
  
  $.post(baseBuildMatrixUrl + '/exclude-units', { 
	  excludedUnits:excludedUnits 
  }).done(function(){
	  excludeEntireRange(rows);
  })
});

$('#eligible-unit-table').on('click', '.exclude-btn', function() {
  var row = $(this).closest('tr');
  var selectExcludedQunatityCell = row.find('.select-excluded-unit-quantity-cell');
  var selectedUnitQunatity = selectExcludedQunatityCell.find('#select-unit-quantity').val();
  
  if(selectedUnitQunatity === "") {
    var excludedUnits = JSON.parse(row.attr("data-unit-array"));
    var rows = [row];
    
    $.post(baseBuildMatrixUrl + '/exclude-units', { 
  	  excludedUnits:excludedUnits 
    }).done(function(){
  	  excludeEntireRange(rows);
    })
  }
  else {
	  var selectedUnitQuantity = parseInt(row.find('#select-unit-quantity').val());
	  var unitArray = JSON.parse(row.attr('data-unit-array'));
	  
	  if(selectedUnitQuantity > unitArray.length)
	      return false;
	  
	  var excludedUnits = unitArray.slice(0,selectedUnitQuantity);
	  
	  $.post(baseBuildMatrixUrl + '/exclude-units', { 
		  excludedUnits:excludedUnits 
	  }).done(function(){
		  excludePartialRange(row, excludedUnits);
	  })
  }
})

/** Clear/Delete Excluded **/
$('#clear-excluded-units-btn').on('click', function() {
  ModalUtil.openModal($clearExcludeConfirmationModal);
});

$('#cancel-clear-btn').on('click', function() {
  ModalUtil.closeModal($clearExcludeConfirmationModal);
});

$('#clear-excluded-units-confirm-btn').on('click', function() {
  var rows = $(excludedUnitsTable.rows().nodes());
  var excludedUnits = [];
  rows.each(function() {
	  var unitArray = JSON.parse($(this).attr("data-unit-array"));
	  excludedUnits = excludedUnits.concat(unitArray);
  });
  
  $.post(baseBuildMatrixUrl + '/delete-excluded-units', { 
	  excludedUnits:excludedUnits 
  }).done(function(){
	  deleteExcludedRows(rows);
	  ModalUtil.closeModal($clearExcludeConfirmationModal);
	  updateQuantities();
  })
});

$('#excluded-units-table').on('click', '.delete-exclude', function() {
  var row = $(this).closest('tr');
  var rows = $([row]);
  
  var excludedUnits = JSON.parse(row.attr("data-unit-array"));
  
  $.post(baseBuildMatrixUrl + '/delete-excluded-units', { 
	  excludedUnits:excludedUnits 
  }).done(function(){
	  deleteExcludedRows(rows);
	  updateQuantities();
  })
});

/****************** Functions ******************/
/** Exclude Row(s) **/
function excludeEntireRange(rows) {
  var addedUpdatedRows = [];
  rows.forEach(function(row, index) {
    var unitArray = JSON.parse(row.attr('data-unit-array'));
    var rowId = row.attr('data-row-id');
    var unitRange = row.find('td.unit-range-cell').html().trim();
    var corp = row.find('td.corp-cell').html().trim();
    var location = row.find('td.district-cell').html().trim();
    var unitQuantity = row.find('td.quantity-cell').html().trim();
    
    applyExcludedRowStyles(row);
    
    var rowAlreadyExists = false;
    var excludedUnitRow;
    $(excludedUnitsTable.rows().nodes()).each(function(index) {
      if($(this).attr('data-row-id') == rowId) {
        rowAlreadyExists = true;
        excludedUnitRow = $(this);
      }
    });
    
    if(rowAlreadyExists) {
      updateExcludedRow(excludedUnitRow, unitArray)
      resetEligibleUnitRow(row);
    }
    else {
      addExcludedRow(rowId, unitArray, corp, location)
    }
  });
  
  $('#clear-excluded-units-btn').removeClass('buttonDisabled');

  updateQuantities();
}

function excludePartialRange(row, selectedUnits) {
  var selectedUnitQuantity = parseInt(row.find('#select-unit-quantity').val());
  var unitArray = JSON.parse(row.attr('data-unit-array'));
  
  var rowId = row.attr('data-row-id');
  var unitRange = row.find('td.unit-range-cell').html().trim();
  var corp = row.find('td.corp-cell').html().trim();
  var location = row.find('td.district-cell').html().trim();
  var unitQuantity = row.find('td.quantity-cell').html().trim();
  
  var selectedUnitsSize = selectedUnits.length;
  
  var excludedRowIsEntireRange = false;
  if(selectedUnitQuantity === unitArray.length) {
    var originalArray = JSON.parse(row.attr('data-original-unit-array'));
    
    applyExcludedRowStyles(row);
    resetEligibleUnitRow(row);
    
    excludedRowIsEntireRange = true;
  }
  else {
    var leftoverUnits = unitArray.slice(selectedUnitQuantity);
    var leftoverLength = leftoverUnits.length;
    row.attr('data-unit-array', "[" + leftoverUnits + "]");
    if(leftoverLength > 1)
      row.find('td.unit-range-cell').html(leftoverUnits[0] + "-" + leftoverUnits[leftoverLength-1])
    else
      row.find('td.unit-range-cell').html(leftoverUnits[0]);
    row.find('td.quantity-cell').html(leftoverLength);
    
    excludedRowIsEntireRange = false;
  }
  
  var rowAlreadyExists = false;
  var excludedUnitRow;
  $(excludedUnitsTable.rows().nodes()).each(function(index) {
    if($(this).attr('data-row-id') == rowId) {
      rowAlreadyExists = true;
      excludedUnitRow = $(this);
    }
  });
  
  if(rowAlreadyExists) {
    updateExcludedRow(excludedUnitRow, selectedUnits)
  }
  else {
    addExcludedRow(rowId, selectedUnits, corp, location)
  }
  
  $('#clear-excluded-units-btn').removeClass('buttonDisabled');
  row.find('#select-unit-quantity').val("");
  
  updateQuantities();
}

/** Modifying rows on Eligible Units Table **/
function applyExcludedRowStyles(row) {
  row.addClass('excluded-row');
  row.find('input').each(function(index) {
    $(this).prop('disabled', true);
  });
    
  row.find('td .exclude-btn').addClass('buttonDisabled');
  row.find('td .exclude-btn').html('EXCLUDED');
  row.find('.select-unit-range').prop('checked', true);
}

function removeExcludedRowStyles(row) {
  row.removeClass('excluded-row');
  row.find('input').each(function(index) {
    $(this).prop('disabled', false);
  });
  
  row.find('td .exclude-btn').removeClass('buttonDisabled');
  row.find('td .exclude-btn').html('EXCLUDE');
  row.find('.select-unit-range').prop('checked', false);
}

function resetEligibleUnitRow(row) {
  var originalArray = JSON.parse(row.attr('data-original-unit-array'));
  row.find('td.quantity-cell').html(originalArray.length);
  if(originalArray.length === 1) {
    row.find('td.unit-range-cell').html(originalArray[0]);
    row.find('#select-unit-quantity').prop('disabled', true);
  }
  else
    row.find('td.unit-range-cell').html(originalArray[0] + "-" + originalArray[originalArray.length-1]);
  
  row.attr("data-unit-array", "[" + originalArray + "]");
}

/** Adding/updating row(s) on Excluded Units Table **/
function addExcludedRow(rowId, unitArray, corp, location) {
  var addedUnitRange;
  var unitArraySize = unitArray.length;
  if(unitArraySize === 1)
    addedUnitRange = unitArray[0];
  else
    addedUnitRange = unitArray[0] + "-" + unitArray[unitArraySize-1];
  var addedRow = excludedUnitsTable.row.add([
    '<i class="fa fa-trash delete-exclude"></i>',
    '<span class="row-excluded-range">' + addedUnitRange + '</span>',
    corp,
    location,
    '<span class="row-excluded-quantity">' + unitArraySize + '</span>',
    '<a class="buttonSecondary btn excluded-btn" >EXCLUDED</a>'
  ]).draw().node();
  
  $(addedRow).attr("data-unit-array", "[" + unitArray + "]");
  $(addedRow).attr("data-row-id", rowId);
  
  $(addedRow).addClass('row-updated');
  
  rowUpdatedTimer = window.setTimeout(function() {
    $(addedRow).removeClass('row-updated').addClass('row-normal');
  }, 1000);
}

function updateExcludedRow(existingUnitRow, addedUnitsArray) {
  var existingUnitArray = JSON.parse($(existingUnitRow).attr("data-unit-array"));
  var newArray = existingUnitArray.concat(addedUnitsArray);
    
  $(existingUnitRow).find('span.row-excluded-range').html(newArray[0] + "-" + newArray[newArray.length-1]);
  $(existingUnitRow).find('span.row-excluded-quantity').html(newArray.length);
  $(existingUnitRow).attr("data-unit-array", "[" + newArray + "]");
    
  $(existingUnitRow).addClass('row-updated');
    
  rowUpdatedTimer = window.setTimeout(function() {
    $(existingUnitRow).removeClass('row-updated').addClass('row-normal');
  }, 1000);
}

/** Updated Quantities **/
function updateExcludedQuantity() {
  var $chassisAvailableBadge = $('.chassis-available-badge');
  var excludedUnitQuantity = 0;
  var rows = $(excludedUnitsTable.rows().nodes());
  if(rows.length === 0) {
    $('#excluded-unit-quantity').html(0);
    $chassisAvailableBadge.text($chassisAvailableBadge.data('chassis-available'));
  }
  else {
    $('#excluded-units-table').find('tbody tr').each(function(index) {
      var row = $(this);
      excludedUnitQuantity += parseInt(row.find('span.row-excluded-quantity').html());
    });
    $('#excluded-unit-quantity').html(excludedUnitQuantity);
    $chassisAvailableBadge.text($chassisAvailableBadge.data('chassis-available') - excludedUnitQuantity)
  }
}

function updateSelectedUnitQuantity() {
  var selectedUnitQuantity = 0;
  var rows = $(eligibleUnitsTable.rows().nodes());
  rows.each(function() {
    var entireRowSelected = $(this).find('.select-unit-range').prop('checked') && $(this).find('.select-unit-range').prop('disabled') === false;
    var unitQuantity = $(this).find('#select-unit-quantity').val().trim();
    if(entireRowSelected) {
      selectedUnitQuantity += parseInt($(this).find('td.quantity-cell').html());
    }
    if(unitQuantity != '' && !$(this).find('#select-unit-quantity').hasClass('error-input'))
      selectedUnitQuantity += parseInt(unitQuantity);
  });
  
  $('#selected-unit-quantity').html(selectedUnitQuantity);
}

function checkCounts(){
	var $chassisAvailableBadge = $('.chassis-available-badge');
	var chassisAvailable = parseInt($chassisAvailableBadge.text());
	
	var $bodiesOnOrderBadge = $('.bodies-on-order-badge');
	var bodiesOnOrder = parseInt($bodiesOnOrderBadge.text());
	if(bodiesOnOrder > chassisAvailable) {
		$('.too-many-bodies-div').removeClass('hidden');
		$('#continue').addClass('buttonDisabled');
	}
	else {
		$('.too-many-bodies-div').addClass('hidden');
		$('#continue').removeClass('buttonDisabled');
	}
}

function updateQuantities() {
  updateSelectedUnitQuantity();
  updateExcludedQuantity();
  checkCounts();
}

/** Delete Excluded Rows **/
function deleteExcludedRows(rows) {
  rows.each(function(index) {
    var rowId = $(this).attr('data-row-id');
    var eligibleTableRow = $(eligibleUnitsTable.row('tr[data-row-id=' + rowId + ']').node());
    
    if(eligibleTableRow.length == 0) { 
    	var $row = $(this);
    	var addedUnitRange;
    	var unitArray = JSON.parse($row.attr('data-unit-array'));
		var unitArraySize = unitArray.length;
		var corp = $row.find('.corp-cell').text();
		var location = $row.find('.district-cell').text();
		
		
		
		var disabledText;
		if(unitArraySize === 1) {
		  addedUnitRange = unitArray[0];
		  disabledText = 'disabled="disabled"';
		}
		else {
		  addedUnitRange = unitArray[0] + "-" + unitArray[unitArraySize-1];
		  disabledText = '';
		}
    	
    	var addedRow = eligibleUnitsTable.row.add([
    	    '<input type="checkbox" class="select-unit-range" />',
    	    addedUnitRange,
    	    corp,
    	    location,
    	    unitArraySize,
    	    '<input type="text" min=0 max="' + unitArraySize + '" id="select-unit-quantity" class="unit-quantity-textbox" pattern="\d+" ' + disabledText + '>',
    	    '<a class="buttonSecondary btn exclude-btn">EXCLUDE</a>'
    	  ]).draw().node();
    	  
    	  $(addedRow).attr("data-unit-array", "[" + unitArray + "]");
    	  $(addedRow).attr("data-row-id", rowId);
    	  $(addedRow).attr("data-original-unit-array", "[" + unitArray + "]");
    	  $(addedRow).find('.checkbox-cell').attr('data-start-unit', unitArray[0]);
    	  $(addedRow).find('.checkbox-cell').attr('data-end-unit', unitArray[unitArraySize - 1]);
    	  
    	  $(addedRow).addClass('row-updated');
    	  
    	  rowUpdatedTimer = window.setTimeout(function() {
    	    $(addedRow).removeClass('row-updated').addClass('row-normal');
    	  }, 1000);
    }
    else {
	    removeExcludedRowStyles(eligibleTableRow);
	    resetEligibleUnitRow(eligibleTableRow);
    }
    
    excludedUnitsTable.row(this).remove().draw();
  });
  $('#select-all-unit-ranges').prop('checked', false);
  if(excludedUnitsTable.rows().nodes().length==0){
    $('#clear-excluded-units-btn').addClass('buttonDisabled');
  }
}