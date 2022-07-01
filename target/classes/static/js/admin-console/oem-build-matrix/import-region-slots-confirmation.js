var $importSlotsTable = $('#import-slots-table');

$importSlotsDataTable = $importSlotsTable.DataTable({
	'paging': false,
	'ordering': false,
	'info': false,
	'scrollY': '500px',
	'scrollX': true,
	'lengthChange': false,
	'searching': false,
	'autoWidth': true,
	'responsive': false,
	'columnDefs': [
		{targets: [0], width: '100px'},
		{targets: ['import-slots-table-header'], width: '120px'}
	],
	"language": {
	      "emptyTable": "You have uploaded an Excel file with all incorrect dates. Please check which year the excel is for and upload it for that year or select the correct document."
	 }
});

$('#import-confirm-btn').on('click', function(){
	$('#import-region-slots-confirm-form').submit();
});