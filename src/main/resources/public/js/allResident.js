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
				}, 
				{
				    data : 'residentId'
				}, 
				{
				    data : 'active',
				    visible : false
				}, 
				{
				    data : 'firstName',
				    render : function(t, type, row) {
					var spanStr = (row.viaVoicemail == false && row.viaText == false && row.viaEmail == false ) ? '&nbsp;<span class="glyphicon glyphicon-unchecked" style="color:red;"></span>' : '';					
					return row.firstName + ' ' + row.middle + ' ' + row.lastName + spanStr ;
				    }
				},
				{
				   	data : 'ackRightToPrivacy',
				    render : function(t, type, row) {
					if (row.ackRightToPrivacy == true) {
					    return '<span style="color:blue">Onboarded</span>';
					}
					return '<span style="color:crimson">Pending</span>';
				    }
				}, 				
				{
				    data : 'propertyName'
				}, 
				{
				    data : 'voiceMail',
				    render : function(t, type, row) {
					if (row.viaVoicemail == true) {
					    return row.voiceMail + '&nbsp;<span class="glyphicon glyphicon-check" style="color:blue;"></span>';
					}
					return row.voiceMail; 
				    }
				}, 
				{
				    data : 'text',
				    render : function(t, type, row) {
					if (row.viaText == true) {
					    return row.text + '&nbsp;<span class="glyphicon glyphicon-check" style="color:blue;"></span>';
					}
					return row.text;
				    }
				}, 
				{
				    data : 'email',
				    render : function(t, type, row) {
					if (row.viaEmail == true) {
					    return '<a href="mailto:' + row.email + '">' + row.email +'</a>' + '&nbsp;<span class="glyphicon glyphicon-check" style="color:blue;"></span>';
					}else if(row.email == null){
						return '';
					}
					return '<a href="mailto:' + row.email + '">' + row.email +'</a>';
				    }
				}, 
				{
				    data : 'photoRelease',
				    render : function(t, type, row) {
					if (row.photoRelease == true) {
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
				    render : function(t, type, row) {
					return moment(row.dateAdded).format("MM/DD/YY");
				    }
				}, 
				{
				    data : 'serviceCoord'
				}, 
				{
				    data : 'refValue',
				    visible : false
				}, 
				{
				    data : 'address',
				    visible : false
				} ],
				"order" : [ [ 10, "desc" ] ],
				pageLength : 8,
				pagingType : "full_numbers",
				"initComplete" : function(settings, json) {
				    var radioHtml = '&nbsp;&nbsp;&nbsp;&nbsp;<span><input type="radio" name="residents" value="all" onchange="filterActives(this);"> All '
					    + ' <input type="radio" name="residents" value="true" onchange="filterActives(this);"> Active '
					    + ' <input type="radio" name="residents" value="false" onchange="filterActives(this);"> Inactive </span>'						
						+ ' &nbsp;&nbsp;&nbsp;&nbsp;<span> <strong><u>Legend:</u></strong> Blue Check (</span><span class="glyphicon glyphicon-check" style="color:blue;"></span><span>) icon = <strong>only contact me via:</strong></span>'
						+ ' &nbsp;&nbsp;&nbsp;&nbsp;<span> Red Unchecked (</strong></span><span class="glyphicon glyphicon-unchecked" style="color:red;"></span><span>) icon = <strong>resident opt out to be contacted OR intake pending</strong></span>';
				    
				   

				    // This prints all radio Options on
				    // AllResident DataTables.
				    var pre = jQuery('.dataTables_length').html();
					var post = jQuery("#_onboardingDiv").html();
				    jQuery('.dataTables_length').html(pre + radioHtml + post);

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
		
							var aHref = jQuery("#_onboarding").attr('href') + currentRow.residentId;
							jQuery("#_onboarding").attr('href', aHref);
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
	

	var chart2 = c3.generate({
	bindto:'#singleResidentChart',
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
				fit:true,
				rotate: -75,
                format: "%e %b %y"
            }
        }
    }
});
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
		        text: 'Resident Per Property',
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
	    type : 'bar',
		lables: true
	},	
	color: {
	    //pattern: ['#74412B', '#BE8260', '#D7B095', '#A58E87', '#8A8683', '#5A4D4C', '#595775', '#ABA6BF', '#0444BF', '#0584F2', '#A7414A', '#BDA589', '#F18904', '#F49F05', '#F3CD05', '#36688D', '#A37c27', '#6A8A82', '#00743F', '#F2A104','#DE8CF0','#BED905','#93A806','#F46A4E','#F4874B','#A3586D', '#0ABDA0','#D6618F','#720017']
		 pattern: ['#7B68EE',
'#483D8B',
'#000080',
'#0000CD',
'#0000FF',
'#4169E1',
'#4682B4',
'#1E90FF',
'#00BFFF',
'#87CEFA',
'#B0E0E6',
'#006400',
'#008000',
'#2E8B57',
'#3CB371',
'#90EE90',
'#00FF7F',
'#6B8E23',
'#808000',
'#556B2F',
'#FFE4B5',
'#FFDAB9',
'#EEE8AA',
'#F0E68C',
'#BDB76B',
'#FFFF00',
'#FF8C00',
'#FFA500',
'#FF7F50',
'#FF6347',
'#FF4500',
'#FFD700',
'#FFA07A',
'#FA8072',
'#E9967A',
'#F08080',
'#CD5C5C',
'#DC143C',
'#B22222',
'#FF0000',
'#8B0000',
'#FF00FF',
'#BA55D3',
'#9370DB',
'#8A2BE2',
'#9400D3',
'#9932CC',
'#8B008B',
'#E6E6FA',
'#D8BFD8',
'#DDA0DD'
]
	},
	grid: {
        y: {
            lines: [{value: 0}]
        }
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
	},
					tooltip: {
  						format: {
    						title: function (x, index) { return 'All Properties'}
  								}
					}
	
    });

};


