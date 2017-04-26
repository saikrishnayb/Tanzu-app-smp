var $componentManagementTable = $('#component-management-table');

/******* Listeners ************************************************************/
$componentManagementTable.on('click', '.visible-component-check', function() {

  var checkbox = this;
  var componentId = $(checkbox).closest('tr').attr('data-component-id');
  var componentGroupId = $(checkbox).closest('tr').attr('data-component-group-id');

  //window.parent.showLoading();

  var $copyCorpComponentPromise = $.post('copy-corp-component.htm', {componentId : componentId, componentGroupId: componentGroupId});

  $copyCorpComponentPromise.done(function() {
    checkbox.disabled = true;
  }).always(function() {
    //window.parent.hideLoading();
  });

});

/******* Initialization *******************************************************/
selectCurrentNavigation("tab-components", "left-nav-component-management");

$componentManagementTable.dataTable({
  "aaSorting" : [[0, "asc" ]],
  "bPaginate" : true,
  "bLengthChange" : false,
  "bFilter" : true,
  "bSort" : true,
  "bInfo" : true,
  "sPaginationType" : "full_numbers",
  "iDisplayLength" : 12
});


/******* Helper Functions ****************/
