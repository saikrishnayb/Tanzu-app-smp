//Initialization
selectCurrentNavigation('tab-app-config', 'left-nav-ship-thru-lead-time');

$('#shipThruLeadTime').DataTable({
  order: [[ 1, "asc" ]],
  columnDefs: [
    {"className": "text-right", "targets": [6]},
    {"orderable": false,        "targets": [0]},
    {"width":     "50px",      "targets": [0]}
  ],
  stateSave: true,
  autoWidth: false,
  responsive: true
});