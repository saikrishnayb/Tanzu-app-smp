var $componentManagementTable = $('#component-management-table');

/******* Listeners ************************************************************/
$componentManagementTable.on('click', '.visible-component-check', function() {

  var checkbox = this;
  var componentId = $(checkbox).closest('tr').attr('data-component-id');
  var componentGroupId = $(checkbox).closest('tr').attr('data-component-group-id');
  var $copyCorpComponentPromise = $.post('copy-corp-component.htm', {componentId : componentId, componentGroupId: componentGroupId});

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
	  var $allowDuplictaeComponentPromise = $.post('allow-duplicate-components.htm', {componentId : componentId,allowDuplicates:isAllowDuplicateChecked});
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
