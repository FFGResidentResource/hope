var table;
jQuery(document)
		.ready(
				function() {				
					
					jQuery('a').parent().removeClass('active');
					var path = window.location.pathname;
					if (path == '/residents' || path == '/addResident') {
						jQuery("a[href='/residents']").parent().addClass(
								'active');
					} else if (path == '/admin') {
						jQuery("a[href='/admin']").parent().addClass('active');
					}

					

					// $('#residentTable').DataTable();

					jQuery
							.ajax({
								type : "POST",
								contentType : "application/json",
								url : "/getAllResidents",
								dataType : 'json',
								cache : false,
								timeout : 600000,
								success : function(data) {
									
									table = jQuery('#residentTable')
											.DataTable(
													{
														"data" : data,
														"columns" : [
																{
													                "className":      'details-control',
													                "orderable":      false,
													                "data":           null,
													                "defaultContent": ''
													            },
																{
																	data : 'active',
																	visible: false
																},
																{
																	data : 'firstName',
																	render : function(t,type,row) {
																		return row.firstName
																				+ ' '
																				+ row.middle
																				+ ' '
																				+ row.lastName;
																	}
																},
																{
																	data : 'propertyName'
																},
																{
																	data : 'voiceMail'
																},
																{
																	data : 'text'
																},
																{
																	data : 'email'
																},
																{
																	data : 'dateAdded.time',
																	render : {
																		_ : 'display',
																		sort : 'timestamp'
																	},
																	render : function(t,type,row) {																		
																		return moment(row.dateAdded).format("MM/DD/YY hh:mm A");
																	}
																},
																{
																	data : 'serviceCoord'
																} ],
														"order" : [ [ 6, "desc" ] ],
														pageLength : 5,
														pagingType : "full_numbers"
													});
									
									jQuery('#residentTable tbody').on('click', 'td.details-control', function () {
									    var tr = jQuery(this).closest('tr');
									    var row = table.row( tr );

									    if ( row.child.isShown() ) {
									        // This row is already open - close it
									        row.child.hide();
									        tr.removeClass('shown');
									    }
									    else {
									        // Open this row
									        row.child( format(row.data()) ).show();
									        tr.addClass('shown');
									    }
									});

								},
								error : function(e) {
									console.log("ERROR : ", e);
								}
							});					
				});

function toggleForm(prefix){	
	
	var formName = "#"+prefix;
	
	//Toggle Form
	jQuery("[id$='_Form']").removeClass('hideme')
	jQuery("[id$='_Form']").addClass('hideme');
	
	jQuery(formName+"_Form").removeClass("hideme");
	
	//Toggle Wizard
	jQuery("[id$='_View']").removeClass('disabled');
	jQuery("[id$='_View']").addClass('disabled');
	
	jQuery(formName+"_View").removeClass("disabled");
	jQuery(formName+"_View").removeClass('active');
	jQuery(formName+"_View").addclass('active');
}

/* Formatting function for row details - modify as you need */
function format ( d ) {
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
        '<tr>'+
            '<td>Children:</td>'+
            '<td>'+d.childList+'</td>'+
        '</tr>'+        
    '</table>';
}