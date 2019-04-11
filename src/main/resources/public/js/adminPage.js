var table;
var currentRow;

jQuery(document).ready(
		
		function(){
		
			jQuery.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/getAllServiceCoordinators",
				dataType : 'json',
				cache : false,
				timeout : 600000,
				success : function(data) {
					table = jQuery('#scTable')
					.DataTable({
							"data" : data,
							"columns" : [{
											"orderable":      false,
											"data":           null,
											"defaultContent": ''
										},
										{	data : 'scId'},
										{	data : 'active'},
										{	data : 'userName'},
										{	data : 'email'},
										{	data : 'createdOn'},
										{	data : 'lastLogin'}
										],
							"order" : [ [ 4, "desc" ] ],
							pageLength : 15,
							pagingType : "full_numbers",
							"initComplete": function(settings, json) {
								var radioHtml = '&nbsp;&nbsp;&nbsp;&nbsp;<span><input type="radio" name="serviceCoordinators" value="all" onchange="filterActives(this);"> All '
									+' <input type="radio" name="serviceCoordinators" value="t" onchange="filterActives(this);"> Active '
									+' <input type="radio" name="serviceCoordinators" value="f" onchange="filterActives(this);"> Inactive </span>';
								
								//This prints all radio Options on AllResident DataTables.
								var pre = jQuery('.dataTables_length').html();
								jQuery('.dataTables_length').html(pre + radioHtml);
							}
					});
				},
				error : function(e) {
					console.log("ERROR : ", e);
				}
			});
		}
	);

	function filterActives (dat) {
		//Toggles Active
		if(dat.value != "all"){
			table.columns(2).search(dat.value).draw();
		}
		else {
			table.columns(2).search('').draw();
		}
	}
	
