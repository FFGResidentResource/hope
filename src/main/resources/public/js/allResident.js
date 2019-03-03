var table;
var currentRow;
jQuery(document)
		.ready(
				function() {				
					
					
					jQuery('a').parent().removeClass('active');
					var path = window.location.pathname;
					if(path == '/newResident' || path == "/getResidentById"){
						jQuery("a[href='/newResident']").parent().addClass('active');
					} else if (path == '/admin') {
						jQuery("a[href='/admin']").parent().addClass('active');
					}else if (path == '/allResident') {
						jQuery("a[href='/allResident']").parent().addClass('active');
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
														pageLength : 15,
														pagingType : "full_numbers",
														"initComplete": function(settings, json) {
															var radioHtml = '&nbsp;&nbsp;&nbsp;&nbsp;<span><input type="radio" name="residents" value="all" onchange="filterActives(this);"> All '
																+' <input type="radio" name="residents" value="true" onchange="filterActives(this);"> Active '
																+' <input type="radio" name="residents" value="false" onchange="filterActives(this);"> Inactive </span>';
															
															//This prints all radio Options on AllResident DataTables.
															var pre = jQuery('.dataTables_length').html();
															jQuery('.dataTables_length').html(pre + radioHtml);
														 }
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
										currentRow = table.row(this).data();
										
										console.log(currentRow);
										
										if ($(this).hasClass('selected') ) {
								        	$(this).removeClass('selected');
								        	jQuery("#_loadResident").addAttr('disabled');
								        }
								        else {
								            table.$('tr.selected').removeClass('selected');
								            $(this).addClass('selected');
								            
								            jQuery("#_resId").val(currentRow.residentId);
								            jQuery("#_loadResident").removeAttr('disabled');
								        }									
																		
								    });

								},
								error : function(e) {
									console.log("ERROR : ", e);
								}
							});					
				});


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
        '<tr>'+
        	'<td>Children:</td>'+
        	'<td>'+d.childList+'</td>'+
        '</tr>'+
    '</table>';
}

function filterActives (dat) {
	//Toggles Active
	if(dat.value != "all"){
		table.columns(2).search(dat.value).draw();
	}
	else {
		table.columns(2).search('').draw();
	}
}