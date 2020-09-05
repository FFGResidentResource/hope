var table;
var currentRow;

jQuery(document).ready(
	function() {

	    jQuery('a[id^="_load"]').attr('disabled', true);

	    // $('#residentTable').DataTable();

	    jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/getAllResidents",
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {

		    buildPieChartData(data);

		    table = jQuery('#residentTable').DataTable(
			    {
				"data" : data,
				"columns" : [ {
				    "className" : 'details-control',
				    "orderable" : false,
				    "data" : null,
				    "defaultContent" : ''
				}, {
				    data : 'residentId'
				}, {
				    data : 'active',
				    visible : false
				}, {
				    data : 'firstName',
				    render : function(t, type, row) {
					var spanStr = (row.viaVoicemail == false && row.viaText == false && row.viaEmail == false ) ? '&nbsp;<span class="glyphicon glyphicon-unchecked" style="color:red;"></span>' : '';					
					return row.firstName + ' ' + row.middle + ' ' + row.lastName + spanStr ;
				    }
				}, {
				    data : 'propertyName'
				}, {
				    data : 'voiceMail',
				    render : function(t, type, row) {
					if (row.viaVoicemail == true) {
					    return row.voiceMail + '&nbsp;<span class="glyphicon glyphicon-check" style="color:blue;"></span>';
					}
					return row.voiceMail; 
				    }
				}, {
				    data : 'text',
				    render : function(t, type, row) {
					if (row.viaText == true) {
					    return row.text + '&nbsp;<span class="glyphicon glyphicon-check" style="color:blue;"></span>';
					}
					return row.text;
				    }
				}, {
				    data : 'email',
				    render : function(t, type, row) {
					if (row.viaEmail == true) {
					    return row.email + '&nbsp;<span class="glyphicon glyphicon-check" style="color:blue;"></span>';
					}
					return row.email;
				    }
				}, {
				    data : 'photoRelease',
				    render : function(t, type, row) {
					if (row.photoRelease == true) {
					    return '<span class="glyphicon glyphicon-ok"></span>';
					}
					return '<span class="glyphicon glyphicon-remove"></span>';
				    }
				}, {
				    data : 'dateAdded.time',
				    render : {
					_ : 'display',
					sort : 'timestamp'
				    },
				    render : function(t, type, row) {
					return moment(row.dateAdded).format("MM/DD/YY hh:mm A");
				    }
				}, {
				    data : 'serviceCoord'
				}, {
				    data : 'refValue',
				    visible : false
				}, {
				    data : 'address',
				    visible : false
				} ],
				"order" : [ [ 9, "desc" ] ],
				pageLength : 10,
				pagingType : "full_numbers",
				"initComplete" : function(settings, json) {
				    var radioHtml = '&nbsp;&nbsp;&nbsp;&nbsp;<span><input type="radio" name="residents" value="all" onchange="filterActives(this);"> All '
					    + ' <input type="radio" name="residents" value="true" onchange="filterActives(this);"> Active '
					    + ' <input type="radio" name="residents" value="false" onchange="filterActives(this);"> Inactive </span>'						
						+ ' &nbsp;&nbsp;&nbsp;&nbsp;<span> <strong><u>Legend:</u></strong> Blue Check (</span><span class="glyphicon glyphicon-check" style="color:blue;"></span><span>) icon = <strong>only contact me via:</strong></span>'
						+ ' &nbsp;&nbsp;&nbsp;&nbsp;<span> Red Unchecked (</strong></span><span class="glyphicon glyphicon-unchecked" style="color:red;"></span><span>) icon = <strong>resident opt out to be contacted.</strong></span>';
				    
				   

				    // This prints all radio Options on
				    // AllResident DataTables.
				    var pre = jQuery('.dataTables_length').html();
				    jQuery('.dataTables_length').html(pre + radioHtml);

				}
			    });
		    
		    if(jQuery("#_propertyGrant").text() != 'All'){
			table.columns(4).search(jQuery("#_propertyGrant").text()).draw();
		    }

		    jQuery('#residentTable tbody').on('click', 'td.details-control', function(e) {
			var tr = jQuery(this).closest('tr');
			var row = table.row(tr);

			if (row.child.isShown()) {
			    // This row is already open - close it
			    row.child.hide();
			    tr.removeClass('shown');
			} else {
			    // Open this row
			    row.child(format(row.data())).show();
			    tr.addClass('shown');
			}

			e.preventDefault();
			return false;
		    });

		    	jQuery('#residentTable tbody').on('click', 'tr', function() {

						var tr = $(this);
						currentRow = table.row(this).data();
			
						console.log(currentRow);
			
						if ($(this).hasClass('selected')) {
						    $(this).removeClass('selected');
						   
						} else {
						   
						    table.$('tr.selected').removeClass('selected');
						    $(this).addClass('selected');
			
						    /*
						     * Following code populates Score Card Chart for individual Resident
						     */
						   
					    jQuery.ajax({
							type : "POST",
							contentType : "application/json",
							url : "/getIndividualScoreCard",
							dataType : 'json',
							data: JSON.stringify(currentRow.residentId),
							cache : false,
							timeout : 600000,
							success : function(data) {
							   
								buildScoreChart(data);
							    
							},
							error : function(e) {
							    console.log("ERROR retrieving Score and Goal: ", e);
							}
						    });
						}
		   		});
			},
			error : function(e) {
			    console.log("ERROR : ", e);
			}
	    });
	    _defaultActive();
	});

function _defaultActive() {
    var rds = $("input[name='residents']");
    if (rds.length == 0)
	setTimeout(function() {
	    _defaultActive();
	}, 300);
    else {
	$(rds).each(function() {
	    if ($(this).val() == "true" && $(this).is(":radio")) {
		$(this).attr("checked", "checked");
		table.columns(2).search("true").draw();
	    }
	});
    }
}

/* Formatting function for row details - modify as you need */
function format(d) {
	
	var childListString = (d.childList != null) ? d.childList : '';
	
	var tableBodyStr = jQuery("table.hideme").html();
	
	tableBodyStr = tableBodyStr.replace("d.refValue", d.refValue);
	tableBodyStr = tableBodyStr.replace("d.gender", d.gender);
	tableBodyStr = tableBodyStr.replace("d.address", d.address);
	tableBodyStr = tableBodyStr.replace("d.age", d.age);
	tableBodyStr = tableBodyStr.replace("d.ethnicity", d.ethnicity);
	tableBodyStr = tableBodyStr.replace("d.race", d.race);
	tableBodyStr = tableBodyStr.replace("d.maritalStatus", d.maritalStatus);
	tableBodyStr = tableBodyStr.replace("d.primaryLanguage", d.primaryLanguage);
	tableBodyStr = tableBodyStr.replace("d.houseHold", d.houseHold);
	tableBodyStr = tableBodyStr.replace("d.veteran", d.veteran);
	tableBodyStr = tableBodyStr.replace("d.disabilityStatus", d.disabilityStatus);
	tableBodyStr = tableBodyStr.replace("d.rcOrExOff", d.rcOrExOff);
	tableBodyStr = tableBodyStr.replace("d.annualGross", d.annualGross);
	tableBodyStr = tableBodyStr.replace("d.healthCoverage", d.healthCoverage);
	tableBodyStr = tableBodyStr.replace("d.highestEdu", d.highestEdu);
	
	// this will have Subtable from allResident.html
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + tableBodyStr +'</table>' 
		+ '<table><tr>' + '<td><strong>Children:</strong></td>' + '<td>' + childListString + '</td></tr></table>';
}

function filterActives(dat) {
    // Toggles Active
    if (dat.value != "all") {
	table.columns(2).search(dat.value).draw();
    } else {
	table.columns(2).search('').draw();
    }
}

function buildScoreChart(data){
	
	var chart = c3.generate({
	bindto:'#singleResidentChart',
	title: {
		        show: false,
		        text: 'Selected Resident SelfSufficiency Progress',
		        position: 'top-center',   // top-left, top-center and top-right
		        padding: {
		          top: 20,
		          right: 20,
		          bottom: 40,
		          left: 50
		        }
		
      		},
    data: {
        x: 'x',
//        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
        columns: data,
		labels:true
    },
    axis: {
        x: {
            type: 'timeseries',
            tick: {
                format: '%Y-%m-%d'
            }
        },
		y: {
        tick : {values: [0,1,2,3,4,5]}
   		 }
    }
});
	d3.select('.c3-axis.c3-axis-x').attr('clip-path', "");
}

function buildPieChartData(data) {

    var columns = [];
    var arr = [];
    var found = false;

    jQuery.each(data, function(key, resident) {
	found = false;
	if (key == 0) {
	    arr = [];
	    arr.push(resident.propertyName);
	    arr.push(1);
	    columns.push(arr);
	} else {
	    jQuery.each(columns, function(index, value) {
		if (columns[index][0] == resident.propertyName) {
		    found = true;
		    columns[index][1] = columns[index][1] + 1;
		}
	    });
	    if (found == false) {
		arr = [];
		arr.push(resident.propertyName);
		arr.push(1);
		columns.push(arr);
	    }

	}
    });

    var chart = c3.generate({
	bindto : '#allResidentPieChart',
	title: {
		        show: false,
		        text: 'Referral By Site View',
		        position: 'top-center',   // top-left, top-center and top-right
		        padding: {
		          top: 20,
		          right: 20,
		          bottom: 40,
		          left: 50
		        }					
      		},
	data : {
	    columns : columns,
	    type : 'bar'
	},
	donut:{
		label: {
      		format: function (value) { return value; }
    	}
	},
	
	color: {
	    pattern: ['#3296dc', '#719dd7', '#e29305', '#ffbb78', '#81923a', '#d3d093', '#ab5624', '#e4a896', '#7677bb', '#c1b4d5', '#83614f', '#c1a197', '#ba8fbe', '#e5bfd1', '#8a8084', '#d4c5ca', '#d8b52f', '#f1d496', '#75b3d5', '#c3d1e9']
	},
	axis : {
	    x : {
		tick : {
		    values : [ '' ]
		}
	    },
	    y : {
		min : 0,
		tick: {
	                format: function (d) {
	                   return (parseInt(d) == d) ? d : null; 						
	                }
	            }
	    // Range includes padding, set 0 if no padding needed
	    // padding: {top:0, bottom:0}
	    }
	}
	
    });

};