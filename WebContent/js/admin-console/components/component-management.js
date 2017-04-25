var $componentManagementTable = $('#component-management-table');

/******* Listeners ************************************************************/

/******* Initialization *******************************************************/
selectCurrentNavigation("tab-components", "left-nav-component-management");

$componentManagementTable.dataTable({
  "aaSorting" : [[1, "asc" ]],
  "bPaginate" : true,
  "bLengthChange" : false,
  "bFilter" : false,
  "bSort" : true,
  "bInfo" : true,
  "sPaginationType" : "full_numbers",
  "iDisplayLength" : 12
});


/******* Helper Functions ****************/
