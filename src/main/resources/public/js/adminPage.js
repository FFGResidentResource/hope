var table;
var currentRow;

jQuery(document).ready(
		
		function(){
			
			jQuery('a').parent().removeClass('active');
			var path = window.location.pathname;
			if(path == '/newResident' || path == "/getResidentById"){			
				jQuery("a[href='/newResident']").parent().addClass('active');
			} else if (path == '/admin') {
				jQuery("a[href='/admin']").parent().addClass('active');
			}else if (path == '/allResident') {
				jQuery("a[href='/allResident']").parent().addClass('active');
			};
		
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
							"order" : [ [ 3, "desc" ] ],
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
	
					
//This section still does not add disabled when a row is clicked a second time - unselected.
					jQuery('#scTable tbody').on( 'click', 'tr', function () {
						var tr = $(this);
						currentRow = table.row(this).data();
						
						console.log(currentRow);
						
						if ($(this).hasClass('selected')) {
				        	$(this).removeClass('selected');
				        	
				        	if (currentRow.active == "t") {
				        		jQuery("#_deactivateServiceCoordinator").attr('disabled');
				        	} else {
				        		jQuery("#_activateServiceCoordinator").attr('disabled');
				        	}
				        }
				        else {
				            table.$('tr.selected').removeClass('selected');
				            $(this).addClass('selected');
				            //jQuery("#_scId").val(currentRow.scID);
				            
				            if (currentRow.active == "t") {
				        		jQuery("#_deactivateServiceCoordinator").removeAttr('disabled');
				        	} else {
				        		jQuery("#_activateServiceCoordinator").removeAttr('disabled');
				        	}
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
	
	
	function showForm() {
		document.getElementById("scForm").style.display = "none";
		} 
