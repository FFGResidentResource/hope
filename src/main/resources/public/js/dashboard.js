var selectedProperties = [];
var oneTimeToggle = false;
var chart;
var dataArray;

jQuery(document).ready(function() {
	
	jQuery(jQuery("[name^='_year_']")[0]).attr('checked','true'); //[0] will be always current year retrieved from DB in sort desc order
		
    jQuery("input[name^='_propId_']").each(function (key, value){
		selectedProperties.push(value.id);
	});
	
	jQuery("input[name^='_propId_']").prop('checked',true);
	console.log(selectedProperties);
	
	// each graph one by one need to be called here, below is just first one - TODO
	genderPercentage();

	
	jQuery("input[name^='_propId_']").on('change', function () {
		generateReport();
	});
    
});

//Onchange of Property checkBoxes
function generateReport(){
	
	selectedProperties = [];	
	 jQuery("input[name^='_propId_']").each(function (key, value){
		console.log(value.checked);
		if(value.checked){
			selectedProperties.push(value.id);
		}
	});
	
	genderPercentage();
}


function genderPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/genderPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			debugger;
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage]
						];
						
			if(chart != null){				
				chart.load({
					columns : dataArray					
				})
			}else{		
					chart = c3.generate({
					bindto : '#genderChart',
					data : {
					    columns : dataArray,
					    type : 'bar'
					},
					labels:{
						 format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
					    pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe', '#e5bfd1', '#8a8084', '#d4c5ca', '#d8b52f', '#f1d496']
					},					
					axis : {
					    x : {
						tick : {
						    values : [ '' ]
						}
					    },
					    y : {
						min : 5,
						tick: {
					            format: function (d) {
					               return (parseInt(d) == d) ? d + ' %' : null; 						
					             }
					          }
					    }
					},
					tooltip: {
  						format: {
    						title: function (x, index) { return 'Gender % '}
  								}
					}
					
				    });	
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
	
}

function ethnicityPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/ethnicityPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#ethnicityChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe', '#e5bfd1', '#8a8084']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Ethnicity % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function languagePercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/languagePercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]

			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#languageChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Language % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function maritalStatusPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/maritalStatusPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#maritalStatusChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Marital Status % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function racePercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/racePercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage],
				[response[8].category,response[8].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#raceChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe', '#e5bfd1', '#8a8084', '#d4c5ca', '#d8b52f', '#f1d496','#719dd7']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Race % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function householdPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/householdPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#householdChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe', '#e5bfd1', '#8a8084']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Household % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function veteranPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/veteranPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#veteranChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe', '#e5bfd1', '#8a8084']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Veteran % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function disabilityPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/disabilityPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#disabilityChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe', '#e5bfd1', '#8a8084']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Disability % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function exOffenderPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/exOffenderPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#exOffenderChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe', '#e5bfd1', '#8a8084']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Ex Offender % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function ssiPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/ssiPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#ssiChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'SSI % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function ssdiPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/ssdiPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#ssdiChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [ '#75b3d5', '#c3d1e9','#ba8fbe']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'SSDI % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function healthPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/healthPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage],
				[response[8].category,response[8].percentage],
				[response[9].category,response[9].percentage],
				[response[10].category,response[10].percentage],
				[response[11].category,response[11].percentage],
				[response[12].category,response[12].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#healthChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [  '#83614f', '#c1a197', '#ba8fbe', '#e5bfd1', '#8a8084', '#d4c5ca', '#d8b52f', '#f1d496', '#75b3d5', '#c3d1e9', '#3296dc', '#ffbb78', '#d3d093']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Health % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function educationPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/educationPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			debugger;
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage],
				[response[8].category,response[8].percentage],
				[response[9].category,response[9].percentage],
				[response[10].category,response[10].percentage],
				[response[11].category,response[11].percentage]
			];

			if(chart != null){
				chart.load({
					columns : dataArray
				})
			}else{
				chart = c3.generate({
					bindto : '#educationChart',
					data : {
						columns : dataArray,
						type : 'bar'
					},
					labels:{
						format:function (v, id, i, j) { return v + '%'; }
					},
					color: {
						pattern: [  '#83614f', '#c1a197', '#ba8fbe', '#e5bfd1', '#8a8084', '#d4c5ca', '#d8b52f', '#f1d496', '#75b3d5', '#c3d1e9', '#3296dc', '#ffbb78']
					},
					axis : {
						x : {
							tick : {
								values : [ '' ]
							}
						},
						y : {
							min : 5,
							tick: {
								format: function (d) {
									return (parseInt(d) == d) ? d + ' %' : null;
								}
							}
						}
					},
					tooltip: {
						format: {
							title: function (x, index) { return 'Education % '}
						}
					}

				});
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}


//will refactor below Later
function pullDashboard(){    
   
    var checkString = '';    
    $(':checkbox').each(function() {
	checkString = checkString + '{"propertyId":"' +this.value +'","checked":"' + this.checked + '"},';
    });
    
    jQuery("#_yearHeading").text(jQuery("#ddlYear").val());
    
    checkString = checkString.substring(0, checkString.length - 1);    
    var dash = '{"year":"'+jQuery("#ddlYear").val()+ '",' +'"quarter":"' +jQuery("#ddlQuarter").val()+ '",' +'"properties": [ '+checkString+' ]}';
    
    jQuery.ajax({
	type : "POST",
	contentType: "application/json; charset=utf-8",
	url : "/pullDashboard",
	data: dash,
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {  
	    
	    
	    var chart = c3.generate({
		bindto : '#_dashboardChart1',
		data : {
		    x: 'x',
		    columns: [
			    ['x', 'Q1', 'Q2', 'Q3', 'Q4'],
		            ['Resident Served', data.residentServedQ1, data.residentServedQ2, data.residentServedQ3, data.residentServedQ4],
		            ['SignUp Completed', data.q1SignUpComplete, data.q2SignUpComplete, data.q3SignUpComplete, data.q4SignUpComplete],
		            ['Assessment Completed', data.q1AssessmentComplete, data.q2AssessmentComplete, data.q3AssessmentComplete, data.q4AssessmentComplete]
		        ],
		    type : 'bar',
		    labels: true
		},
		axis : {
		    x: {
		            type: 'category'		            	           
		        },
		    y : {
			min : 1,
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
	    
	    
	    var chart2 = c3.generate({
		bindto : '#_dashboardChart2',
		data : {
		    x: 'x',
		    columns: [
			    ['x', 'Q1', 'Q2', 'Q3', 'Q4'],
		            ['HOUSING', data.housingQ1Count, data.housingQ2Count, data.housingQ3Count, data.housingQ4Count],
		            ['MONEY MGMT', data.mmQ1Count, data.mmQ2Count, data.mmQ3Count, data.mmQ4Count],
		            ['EMP', data.empQ1Count, data.empQ2Count, data.empQ3Count, data.empQ4Count],
		            ['EDU', data.eduQ1Count, data.eduQ2Count, data.eduQ3Count, data.eduQ4Count],
		            ['NETWORK SUPP', data.nsQ1Count, data.nsQ2Count, data.nsQ3Count, data.nsQ4Count],
		            ['HOUSEHOLD', data.hhQ1Count, data.hhQ2Count, data.hhQ3Count, data.hhQ4Count]
		        ],
		    type : 'bar',
		    labels: true
		},
		color: {
		    pattern: ['#719dd7', '#e29305', '#81923a', '#ab5624', '#e4a896', '#7677bb', '#c1b4d5', '#83614f', '#c1a197', '#ba8fbe', '#e5bfd1', '#8a8084', '#d4c5ca', '#d8b52f', '#f1d496', '#75b3d5', '#c3d1e9', '#3296dc', '#ffbb78', '#d3d093']
		},
		axis : {
		    x: {
		            type: 'category'		            	           
		        },
		    y : {
			min : 1,
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
	    
	    
	    jQuery("#_signupQ1").text(data.q1SignUpComplete);
	    jQuery("#_signupQ2").text(data.q2SignUpComplete);
	    jQuery("#_signupQ3").text(data.q3SignUpComplete);
	    jQuery("#_signupQ4").text(data.q4SignUpComplete);
	    
	    jQuery('#_signupYTD').text(data.q1SignUpComplete + data.q2SignUpComplete + data.q3SignUpComplete + data.q4SignUpComplete);
	    
	    
	    jQuery("#_resServedQ1").text(data.residentServedQ1);
	    jQuery("#_resServedQ2").text(data.residentServedQ2);
	    jQuery("#_resServedQ3").text(data.residentServedQ3);
	    jQuery("#_resServedQ4").text(data.residentServedQ4);
	    
	    jQuery('#_resServedYTD').text(data.residentServedQ1 + data.residentServedQ2 + data.residentServedQ3 + data.residentServedQ4);
	    
	    jQuery("#_ssmQ1").text(data.q1AssessmentComplete);
	    jQuery("#_ssmQ2").text(data.q2AssessmentComplete);
	    jQuery("#_ssmQ3").text(data.q3AssessmentComplete);
	    jQuery("#_ssmQ4").text(data.q4AssessmentComplete);
	    
	    jQuery('#_ssmYTD').text(data.q1AssessmentComplete + data.q2AssessmentComplete + data.q3AssessmentComplete + data.q4AssessmentComplete);
	    
	    
	    jQuery("#_rr1Q1").text(data.rr1q1Count);
	    jQuery("#_rr1Q2").text(data.rr1q2Count);
	    jQuery("#_rr1Q3").text(data.rr1q3Count);
	    jQuery("#_rr1Q4").text(data.rr1q4Count);	    
	    jQuery('#_rr1YTD').text(data.rr1q1Count + data.rr1q2Count + data.rr1q3Count + data.rr1q4Count);
	    
	    
	    jQuery("#_rr2Q1").text(data.rr2q1Count);
	    jQuery("#_rr2Q2").text(data.rr2q2Count);
	    jQuery("#_rr2Q3").text(data.rr2q3Count);
	    jQuery("#_rr2Q4").text(data.rr2q4Count);	    
	    jQuery('#_rr2YTD').text(data.rr2q1Count + data.rr2q2Count + data.rr2q3Count + data.rr2q4Count);
	    
	    jQuery("#_rr3Q1").text(data.rr3q1Count);
	    jQuery("#_rr3Q2").text(data.rr3q2Count);
	    jQuery("#_rr3Q3").text(data.rr3q3Count);
	    jQuery("#_rr3Q4").text(data.rr3q4Count);	    
	    jQuery('#_rr3YTD').text(data.rr3q1Count + data.rr3q2Count + data.rr3q3Count + data.rr3q4Count);
	    
	    jQuery("#_rr4Q1").text(data.rr4q1Count);
	    jQuery("#_rr4Q2").text(data.rr4q2Count);
	    jQuery("#_rr4Q3").text(data.rr4q3Count);
	    jQuery("#_rr4Q4").text(data.rr4q4Count);	    
	    jQuery('#_rr4YTD').text(data.rr4q1Count + data.rr4q2Count + data.rr4q3Count + data.rr4q4Count);
	    
	    jQuery("#_rr5Q1").text(data.rr5q1Count);
	    jQuery("#_rr5Q2").text(data.rr5q2Count);
	    jQuery("#_rr5Q3").text(data.rr5q3Count);
	    jQuery("#_rr5Q4").text(data.rr5q4Count);	    
	    jQuery('#_rr5YTD').text(data.rr5q1Count + data.rr5q2Count + data.rr5q3Count + data.rr5q4Count);
	    
	    jQuery("#_rr6Q1").text(data.rr6q1Count);
	    jQuery("#_rr6Q2").text(data.rr6q2Count);
	    jQuery("#_rr6Q3").text(data.rr6q3Count);
	    jQuery("#_rr6Q4").text(data.rr6q4Count);	    
	    jQuery('#_rr6YTD').text(data.rr6q1Count + data.rr1q2Count + data.rr6q3Count + data.rr6q4Count);
	    
	    jQuery("#_rr7Q1").text(data.rr7q1Count);
	    jQuery("#_rr7Q2").text(data.rr7q2Count);
	    jQuery("#_rr7Q3").text(data.rr7q3Count);
	    jQuery("#_rr7Q4").text(data.rr7q4Count);	    
	    jQuery('#_rr7YTD').text(data.rr7q1Count + data.rr7q2Count + data.rr7q3Count + data.rr7q4Count);
	    
	    jQuery("#_rr8Q1").text(data.rr8q1Count);
	    jQuery("#_rr8Q2").text(data.rr8q2Count);
	    jQuery("#_rr8Q3").text(data.rr8q3Count);
	    jQuery("#_rr8Q4").text(data.rr8q4Count);	    
	    jQuery('#_rr8YTD').text(data.rr8q1Count + data.rr8q2Count + data.rr8q3Count + data.rr8q4Count);
	    
	    
	    jQuery("#_rr9Q1").text(data.rr9q1Count);
	    jQuery("#_rr9Q2").text(data.rr9q2Count);
	    jQuery("#_rr9Q3").text(data.rr9q3Count);
	    jQuery("#_rr9Q4").text(data.rr9q4Count);	    
	    jQuery('#_rr9YTD').text(data.rr9q1Count + data.rr9q2Count + data.rr9q3Count + data.rr9q4Count);
	    
	    jQuery("#_rr10Q1").text(data.rr10q1Count);
	    jQuery("#_rr10Q2").text(data.rr10q2Count);
	    jQuery("#_rr10Q3").text(data.rr10q3Count);
	    jQuery("#_rr10Q4").text(data.rr10q4Count);	    
	    jQuery('#_rr10YTD').text(data.rr10q1Count + data.rr10q2Count + data.rr10q3Count + data.rr10q4Count);
	    
	    jQuery("#_rr11Q1").text(data.rr11q1Count);
	    jQuery("#_rr11Q2").text(data.rr11q2Count);
	    jQuery("#_rr11Q3").text(data.rr11q3Count);
	    jQuery("#_rr11Q4").text(data.rr11q4Count);	    
	    jQuery('#_rr11YTD').text(data.rr11q1Count + data.rr11q2Count + data.rr11q3Count + data.rr11q4Count);
	    
	    jQuery("#_rr12Q1").text(data.rr12q1Count);
	    jQuery("#_rr12Q2").text(data.rr12q2Count);
	    jQuery("#_rr12Q3").text(data.rr12q3Count);
	    jQuery("#_rr12Q4").text(data.rr12q4Count);	    
	    jQuery('#_rr12YTD').text(data.rr12q1Count + data.rr12q2Count + data.rr12q3Count + data.rr12q4Count);
	    
	    jQuery("#_rr13Q1").text(data.rr13q1Count);
	    jQuery("#_rr13Q2").text(data.rr13q2Count);
	    jQuery("#_rr13Q3").text(data.rr13q3Count);
	    jQuery("#_rr13Q4").text(data.rr13q4Count);	    
	    jQuery('#_rr13YTD').text(data.rr13q1Count + data.rr13q2Count + data.rr13q3Count + data.rr13q4Count);
	    
	    jQuery("#_rr14Q1").text(data.rr14q1Count);
	    jQuery("#_rr14Q2").text(data.rr14q2Count);
	    jQuery("#_rr14Q3").text(data.rr14q3Count);
	    jQuery("#_rr14Q4").text(data.rr14q4Count);	    
	    jQuery('#_rr14YTD').text(data.rr14q1Count + data.rr14q2Count + data.rr14q3Count + data.rr14q4Count);
	    
	    jQuery("#_housingQ1").text(data.housingQ1Count);
	    jQuery("#_housingQ2").text(data.housingQ2Count);
	    jQuery("#_housingQ3").text(data.housingQ3Count);
	    jQuery("#_housingQ4").text(data.housingQ4Count);
	    jQuery('#_housingYTD').text(data.housingQ1Count + data.housingQ2Count + data.housingQ3Count + data.housingQ4Count);
	    
	    jQuery("#_mmQ1").text(data.mmQ1Count);
	    jQuery("#_mmQ2").text(data.mmQ2Count);
	    jQuery("#_mmQ3").text(data.mmQ3Count);
	    jQuery("#_mmQ4").text(data.mmQ4Count);
	    jQuery('#_mmYTD').text(data.mmQ1Count + data.mmQ2Count + data.mmQ3Count + data.mmQ4Count);
	    
	    jQuery("#_empQ1").text(data.empQ1Count);
	    jQuery("#_empQ2").text(data.empQ2Count);
	    jQuery("#_empQ3").text(data.empQ3Count);
	    jQuery("#_empQ4").text(data.empQ4Count);
	    jQuery('#_empYTD').text(data.empQ1Count + data.empQ2Count + data.empQ3Count + data.empQ4Count);
	    
	    jQuery("#_eduQ1").text(data.eduQ1Count);
	    jQuery("#_eduQ2").text(data.eduQ2Count);
	    jQuery("#_eduQ3").text(data.eduQ3Count);
	    jQuery("#_eduQ4").text(data.eduQ4Count);
	    jQuery('#_eduYTD').text(data.eduQ1Count + data.eduQ2Count + data.eduQ3Count + data.eduQ4Count);
	    
	    jQuery("#_nsQ1").text(data.nsQ1Count);
	    jQuery("#_nsQ2").text(data.nsQ2Count);
	    jQuery("#_nsQ3").text(data.nsQ3Count);
	    jQuery("#_nsQ4").text(data.nsQ4Count);
	    jQuery('#_nsYTD').text(data.nsQ1Count + data.nsQ2Count + data.nsQ3Count + data.nsQ4Count);
	    
	    jQuery("#_hhQ1").text(data.hhQ1Count);
	    jQuery("#_hhQ2").text(data.hhQ2Count);
	    jQuery("#_hhQ3").text(data.hhQ3Count);
	    jQuery("#_hhQ4").text(data.hhQ4Count);
	    jQuery('#_hhYTD').text(data.hhQ1Count + data.hhQ2Count + data.hhQ3Count + data.hhQ4Count);    
	   
	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });
    
}
