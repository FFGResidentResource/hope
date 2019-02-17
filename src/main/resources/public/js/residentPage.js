jQuery(document)
		.ready(
				function() {				
					
					jQuery('a').parent().removeClass('active');
					var path = window.location.pathname;
					if (path == '/residents') {
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

									debugger;
									$('#residentTable')
											.dataTable(
													{
														"data" : data,
														"columns" : [
																{
																	data : 'active'
																},
																{
																	data : 'firstName',
																	render : function(
																			t,
																			type,
																			row) {
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
