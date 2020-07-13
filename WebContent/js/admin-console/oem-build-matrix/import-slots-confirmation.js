var $importSlotsTable = $('#import-slots-table');

$importSlotsDataTable = $importSlotsTable.DataTable({
	'paging': false,
	'ordering': false,
	'info': false,
	'scrollY': '500px',
	'lengthChange': false,
	'searching': false,
	'autoWidth': true,
	'responsive': false,
	'columnDefs': [
		{targets: [0], width: '100px'},
		{targets: ['import-slots-table-header'], width: '120px'}
	]
});

$('#import-confirm-btn').on('click', function(){
	$('#import-slots-confirm-form').submit();
});