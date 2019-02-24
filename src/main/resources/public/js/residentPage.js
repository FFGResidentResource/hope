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
																	data : 'residentId'																	
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
																	data : 'voiceMail',
																	render : function(t,type,row){
																		if(row.viaVoicemail == true){
																			return row.voiceMail + '&nbsp;<span class="glyphicon glyphicon-hand-left"></span>';
																		}
																		return row.voiceMail;
																	}
																},
																{
																	data : 'text',
																	render : function(t,type,row){
																		if(row.viaText == true){
																			return row.text + '&nbsp;<span class="glyphicon glyphicon-hand-left"></span>';
																		}
																		return row.text;
																	}
																},
																{
																	data : 'email',
																	render : function(t,type,row){
																		if(row.viaEmail == true){
																			return row.email + '&nbsp;<span class="glyphicon glyphicon-hand-left"></span>';
																		}
																		return row.email;
																	}
																},
																{
																	data : 'photoRelease',
																	render: function(t,type,row){
																		if(row.photoRelease == true){
																			return '<span class="glyphicon glyphicon-ok"></span>';
																		}
																		return '<span class="glyphicon glyphicon-remove"></span>';
																	}
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
																},
																{
																	data : 'refValue',
																	visible:false
																},
																{
																	data : 'address',
																	visible:false
																}
																],
														"order" : [ [ 9, "desc" ] ],
														pageLength : 5,
														pagingType : "full_numbers"
													});
									
									jQuery('#residentTable tbody').on('click', 'td.details-control', function (e) {
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
									    
									    e.preventDefault();
									    return false;
									});
									
									jQuery('#residentTable tbody').on( 'click', 'tr', function () {
										
										var tr = $(this);
										var row = table.row( tr );
										
										if ($(this).hasClass('selected') ) {
								        	$(this).removeClass('selected');
								        }
								        else {
								            table.$('tr.selected').removeClass('selected');
								            $(this).addClass('selected');
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
            '<td>Referral:</td>'+
            '<td>'+d.refValue+'</td>'+
        '</tr>'+
        '<tr>'+
        	'<td>Address:</td>'+
        	'<td>'+d.address+'</td>'+
        '</tr>'+
        '<tr>'+
        	'<td>Assessment:</td>'+
        	'<td>'+d.aValue+'</td>'+
        '</tr>'+
    '</table>';
}