var $componentManagementTable = $('#component-management-table');
var $componentModal = $("#component-modal");

ModalUtil.initializeModal($componentModal);

/******* Listeners ************************************************************/
$componentManagementTable.on('click', '.visible-component-check', function() {

  var checkbox = this;
  var componentId = $(checkbox).closest('tr').attr('data-component-id');
  var componentGroupNumber = $(checkbox).closest('tr').attr('data-component-group-number');
  var $copyCorpComponentPromise = $.post('copy-corp-component', {componentId : componentId, componentGroupNumber: componentGroupNumber});

  $copyCorpComponentPromise.done(function() {
    var allowDuplicateCheckBox = $(checkbox).closest('tr').has('.allow-duplicate-check').find(':checkbox');
    allowDuplicateCheckBox.prop('disabled',false).prop('checked', true);
    $(checkbox).prop('disabled',true);
  });
});


$componentManagementTable.on('click', '.allow-duplicate-check', function() {
	  var checkbox = this;
	  var isAllowDuplicateChecked = $(checkbox).prop('checked');
	  var componentId = $(checkbox).closest('tr').attr('data-component-id');
	  var $allowDuplictaeComponentPromise = $.post('allow-duplicate-components', {componentId : componentId,allowDuplicates:isAllowDuplicateChecked});
});

$('.hold-payment-link').on('click', function() {
	var $this = $(this);
	var $componentRow = $this.closest('.component-row');
	var componentId = $componentRow.data('component-id');
	
	var $getHoldPaymentContentPromise =$.get("get-hold-payment-content", {componentId:componentId});
	$getHoldPaymentContentPromise.done(function(data){
		$componentModal.html(data);
		ModalUtil.openModal($componentModal);
	});
});

/******* Initialization *******************************************************/
selectCurrentNavigation("tab-components", "left-nav-component-management");

$componentManagementTable.dataTable({
  "aaSorting" : [[0, "asc" ]],
  "bPaginate" : true,
  "bLengthChange" : true,
  "bFilter" : true,
  "bSort" : true,
  "bInfo" : true,
  "sPaginationType" : "full_numbers",
  "iDisplayLength" : 12,
  "fnDrawCallback": function() {  //This will hide the pagination menu if we only have 1 page.
    
  }
});


/******* Helper Functions ****************/
