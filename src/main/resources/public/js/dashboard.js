//Some of the Graphs are not purely database driven, if you add something in html e.g. a new resident outcome achieved category then you have to add that in actionPlan.html and then you have to add it here in this file under OutcomeAchievedQuarterly Method
// Charting and Graphing is completely based on C3 D3 JS chart Library. All we doing is massaging javaScript Data what we get from database via Jquery Ajax Calls

window.onbeforeprint = function() {
	
	jQuery("#_propListPrint").text('');
	jQuery(selectedProperties).each(function (idx, val){	
		console.log(val);
		var str = '#'+Number(val) + ':checked';
		if(jQuery(str)){
			jQuery("#_propListPrint").append(val+ ". "+ jQuery(str).next('span').text() + "  ");
		}		
	});
}

var selectedProperties = [];
var oneTimeToggle = false;
var chart, chartEth, chartLang, chartMS, chartHouseHold, chartRace, chartVeteran, chartDis, chartExOff, chartSsi, chartSsdi, chartEdu, chartHealth, chartIA, chartPC, chartFS, chartMT, chartSD, chartSN, chartIRC, chartOL , chartHoh, noShowChart, servicesChart, aChart, saChart, refTypeChart, outAchChart, resServedChart,refReasonChart, movingUpChart, movingDownChart, chartSignUP, chartHC, chartUnEmpReason, chartBarrierToEdu, chartPsy, chartPsa;
var dataArray;
var reset = 0;

var colors = ['#7B68EE',
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
];

jQuery(document).ready(function() {
	
	
	
	jQuery(jQuery('input:radio')[0]).prop('checked',true); //[0] will be always current year retrieved from DB in sort desc order
		
    jQuery("input[name^='_propId_']").each(function (key, value){
		selectedProperties.push(value.id);
	});
	
	jQuery("input[name^='_propId_']").prop('checked',true);
	console.log(selectedProperties);
	
	// each graph one by one need to be called here, below is just first one - TODO
	generateReport();	
	
	$('input:radio').change(function(){
			generateAllQuarterlyReport();
	});
	
	$("input[name^='_propId_']").change(function(){
			generateReport();
	});
    
});

function toggleProperties(that){
	
	jQuery("input[name^='_propId_']").prop('checked',!that.checked);
	generateReport();
}

function citySelection(that){	
	
	if(reset < 1){
		jQuery("input[name^='_propId_']").prop('checked',false);
		reset = 1;
	}
	jQuery("input[name*='_City_"+jQuery(that).next().text()+"']").prop('checked',jQuery(that).prop('checked'));
	generateReport();
	
}

function countySelection(that){	
	
	if(reset < 1){
		jQuery("input[name^='_propId_']").prop('checked',false);
		reset = 1;
	}
	
	jQuery("input[name*='_County_"+jQuery(that).next().text()+"']").prop('checked',jQuery(that).prop('checked'));
	generateReport();
	
}

function stateSelection(that){	

	if(reset < 1){
		jQuery("input[name^='_propId_']").prop('checked',false);
		reset = 1;
	}
	
	jQuery("input[name*='_State_"+jQuery(that).next().text()+"']").prop('checked',jQuery(that).prop('checked'));
	generateReport();
}

//Onchange of Property checkBoxes
function generateReport(){
	
	selectedProperties = [];	
	 jQuery("input[name^='_propId_']").each(function (key, value){
		console.log(value.checked);
		if(value.checked){
			selectedProperties.push(value.id);
		}
	});
	
	signUpPercentage();
	genderPercentage();
	ethnicityPercentage();
	languagePercentage();
	maritalStatusPercentage();
	racePercentage();
	householdPercentage();
	veteranPercentage(); 
	disabilityPercentage();
	exOffenderPercentage();
	ssiPercentage();
	ssdiPercentage();
	healthPercentage();
	educationPercentage();
	foodShortagePercentage();
	modTransPercentage();
	internetAccessPercentage();
	prefferedContactPercentage();
	safeDayPercentage();
	safeNightPercentage();
	intResCouncilPercentage();
	occLengthPercentage();
	hohTypePercentage();
	hcPercentage();
	unEmpReasonPercentage();
	barrierToEduPercentage();
	psyPercentage();
	psaPercentage();
	
	generateAllQuarterlyReport();
	
}

function generateAllQuarterlyReport(){
	
	//Quarterly Report - Begin
	
	noShowsQuarterly();
	servicesQuarterly();
	assessmentQuarterly();
	serviceAgencyQuarterly();
	refTypeQuarterly();
	outcomeQuarterly();
	resServedQuarterly();
	refReasonsQuarterly();
	movingUpQuarterly();
	movingDownQuarterly();
	
	
}

function ongoingResidents(that){
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/ongoingResidents",
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			jQuery("#_ongoingBadge").text(response);	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
	
}

function newResidents(that){
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/newResidents",
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			jQuery("#_newBadge").text(response);	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
	
}

function movingUpQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	var dataArrayPerf = null;
	
	var groupArray =  [['Resident Moving Up']];
	dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'],groupArray[0]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/movingUpQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			for(i = 1; i < 5; i++){
				
				var countFound = false
				jQuery(response).each(function(idx,val){					
					if(Number(val.quarter) == i){						
						countFound = true;
						dataArrayPerf[1][i] = 	val.count;					
					}				
				})				
				if(!countFound){					
					dataArrayPerf[1][i] = 0;
				}
			}
			
			if(movingUpChart != null){				
				movingUpChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					movingUpChart = generateCategoryChart("#movingUpChart", "Resident Moving Up", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function movingDownQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	var dataArrayPerf = null;
	
	var groupArray =  [['Resident Moving Down']];
	dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'],groupArray[0]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/movingDownQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			for(i = 1; i < 5; i++){
				
				var countFound = false
				jQuery(response).each(function(idx,val){					
					if(Number(val.quarter) == i){						
						countFound = true;
						dataArrayPerf[1][i] = 	val.count;					
					}				
				})				
				if(!countFound){					
					dataArrayPerf[1][i] = 0;
				}
			}
			
			if(movingDownChart != null){				
				movingDownChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					movingDownChart= generateCategoryChart("#movingDownChart", "Resident Moving Down", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}


function resServedQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	var dataArrayPerf = null;
	
	var groupArray =  [['Resident Served']];
	dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'],groupArray[0]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/residentServedQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			for(i = 1; i < 5; i++){
				
				var countFound = false
				jQuery(response).each(function(idx,val){					
					if(Number(val.quarter) == i){						
						countFound = true;
						dataArrayPerf[1][i] = 	val.count;					
					}				
				})				
				if(!countFound){					
					dataArrayPerf[1][i] = 0;
				}
			}
			
			if(resServedChart != null){				
				resServedChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					resServedChart= generateCategoryChart("#resServedChart", "Resident Served", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function refReasonsQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	
	var groupArray =  [["Non/late payment of rent"], ["Utility Shut-off, scheduled for (Date):"], ["Housekeeping/home management"],["Lease violation for:"],["Employment/job readiness"],
						["Education/job training"],["Noticeable change in:"],["Resident-to-resident conflict issues"],["Suspected abuse/domestic violence/exploitation"],["Childcare/afterschool care"],
						["Transportation"],["Safety"],["Healthcare/medical issues"],
						["Other:"]];
	var dataArrayPerf = [["x", "Q1", "Q2", "Q3", "Q4"], ["Non/late payment of rent"], ["Utility Shut-off, scheduled for (Date):"], ["Housekeeping/home management"],["Lease violation for:"],["Employment/job readiness"],
						["Education/job training"],["Noticeable change in:"],["Resident-to-resident conflict issues"],["Suspected abuse/domestic violence/exploitation"],["Childcare/afterschool care"],
						["Transportation"],["Safety"],["Healthcare/medical issues"],
						["Other:"]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
			
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/refReasonQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
		
		jQuery(response).each(function(idx,val){
			jQuery(groupArray).each(function(p,category){		
				
				if(val.category == category){
					//if category matches then only check for each quarter
					for(j = 1; j < 5; j++){
					
						// This shows on each quarter some Catory found
						if(val.quarter == j ){									
							dataArrayPerf[p+1][j] = 	val.count;												
						}else{
							dataArrayPerf[p+1][j] = 0;
						}	
					}
				}		
			});	
						
		});	
			
			if(refReasonChart != null){				
				refReasonChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					refReasonChart= generateCategoryChart("#refReasonsChart", "Referral Reason", groupArray, dataArrayPerf);					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function outcomeQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	
	var groupArray =  [["Rectify lease violation"], ["Remain housed overtime"], ["Avoid moving out becuase of non-payment of rent"],["Avoid utility-shut off"],["Purchase a home"],
						["Increase gross income(e.g., child support, gov't benefits)"],["Establish a bank account"],["Reduce reliance upon government benefits (e.g., section 8, food stamps)"],["Secure financial assistance(e.g. for help with car repairs)"],["Recieve financial literacy education"],
						["Increase employment income"],["Secured employment"],["Remain employed for 90 days"],
						["Enroll child(ren) in pre-k, head start, or other early education program"],["Enroll child(ren) in school, afterschool, or other early education program"],["Enroll child(ren) in day care"],["Complete secondary education program"],["Complete vocational/job training program"],
						["Feel safe in own/building"],["Feel safe in surrounding neighborhood"],["Feel knowledgable about where to go for help"],["Register to vote"],["Improve socialization or network of support"],
						["Improve housekeeping skills or conditions"],["Secure donated materials(e.g. clothes, school supplies, furniture)"],
						["Reduce use of hospital ER"],["Identify a place to receive routine primary care services"],["Visit a healthcare provider for a routine checkup"],["Enroll in or change health insurance"],["Reduce food insecurity"],["Improve ability to manage health/mental health condition"]];
	var dataArrayPerf = [["x", "Q1", "Q2", "Q3", "Q4"], ["Rectify lease violation"], ["Remain housed overtime"], ["Avoid moving out becuase of non-payment of rent"],["Avoid utility-shut off"],["Purchase a home"],
						["Increase gross income(e.g., child support, gov't benefits)"],["Establish a bank account"],["Reduce reliance upon government benefits (e.g., section 8, food stamps)"],["Secure financial assistance(e.g. for help with car repairs)"],["Recieve financial literacy education"],
						["Increase employment income"],["Secured employment"],["Remain employed for 90 days"],
						["Enroll child(ren) in pre-k, head start, or other early education program"],["Enroll child(ren) in school, afterschool, or other early education program"],["Enroll child(ren) in day care"],["Complete secondary education program"],["Complete vocational/job training program"],
						["Feel safe in own/building"],["Feel safe in surrounding neighborhood"],["Feel knowledgable about where to go for help"],["Register to vote"],["Improve socialization or network of support"],
						["Improve housekeeping skills or conditions"],["Secure donated materials(e.g. clothes, school supplies, furniture)"],
						["Reduce use of hospital ER"],["Identify a place to receive routine primary care services"],["Visit a healthcare provider for a routine checkup"],["Enroll in or change health insurance"],["Reduce food insecurity"],["Improve ability to manage health/mental health condition"]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
			
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/outcomesAchievedQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
		
		jQuery(response).each(function(idx,val){
			jQuery(groupArray).each(function(p,category){		
				
				if(val.category == category){
					//if category matches then only check for each quarter
					for(j = 1; j < 5; j++){
					
						// This shows on each quarter some Catory found
						if(val.quarter == j ){									
							dataArrayPerf[p+1][j] = 	val.count;												
						}else{
							dataArrayPerf[p+1][j] = 0;
						}	
					}
				}		
			});	
						
		});	
			
			if(outAchChart != null){				
				outAchChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					outAchChart= generateCategoryChart("#outAchChart", "Outcomes Achieved Chart (You can click on individual x axis category to turn off category result from chart)", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function refTypeQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	
	var groupArray =  [['Self(Resident)/Walk-In'], ['Property Mgmt (Includes Community Manager and Maintenance)'], ['Service Coordinator'],['Other Resident'],['Other']];
	var dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'], ['Self(Resident)/Walk-In'], ['Property Mgmt (Includes Community Manager and Maintenance)'], ['Service Coordinator'],['Other Resident'],['Other']]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
			
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/refTypeQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
		
		jQuery(response).each(function(idx,val){
			jQuery(groupArray).each(function(p,category){		
				
				if(val.category == category){
					//if category matches then only check for each quarter
					for(j = 1; j < 5; j++){
					
						// This shows on each quarter some Catory found
						if(val.quarter == j ){									
							dataArrayPerf[p+1][j] = 	val.count;												
						}else{
							dataArrayPerf[p+1][j] = 0;
						}	
					}
				}		
			});	
						
		});	
			
			if(refTypeChart != null){				
				refTypeChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					refTypeChart= generateCategoryChart("#refTypeChart", "Resident Referred By", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function refTypeQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	
	var groupArray =  [['Self(Resident)/Walk-In'], ['Property Mgmt (Includes Community Manager and Maintenance)'], ['Service Coordinator'],['Other Resident'],['Other']];
	var dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'], ['Self(Resident)/Walk-In'], ['Property Mgmt (Includes Community Manager and Maintenance)'], ['Service Coordinator'],['Other Resident'],['Other']]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
			
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/refTypeQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
		
		jQuery(response).each(function(idx,val){
			jQuery(groupArray).each(function(p,category){		
				
				if(val.category == category){
					//if category matches then only check for each quarter
					for(j = 1; j < 5; j++){
					
						// This shows on each quarter some Catory found
						if(val.quarter == j ){									
							dataArrayPerf[p+1][j] = 	val.count;												
						}else{
							dataArrayPerf[p+1][j] = 0;
						}	
					}
				}		
			});	
						
		});	
			
			if(refTypeChart != null){				
				refTypeChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					refTypeChart= generateCategoryChart("#refTypeChart", "Resident Referred By", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function serviceAgencyQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	var dataArrayPerf = null;
	
	var groupArray =  [['Resident referred to Service Agency']];
	dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'],groupArray[0]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/serviceAgencyQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			for(i = 1; i < 5; i++){
				
				var countFound = false
				jQuery(response).each(function(idx,val){					
					if(Number(val.quarter) == i){						
						countFound = true;
						dataArrayPerf[1][i] = 	val.count;					
					}				
				})				
				if(!countFound){					
					dataArrayPerf[1][i] = 0;
				}
			}
			
			if(saChart != null){				
				saChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					saChart= generateCategoryChart("#serviceAgencyChart", "Referred to Service Agency", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function assessmentQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	var dataArrayPerf = null;
	
	var groupArray =  [['All Assessment Completed']];
	dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'],groupArray[0]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/assessmentCompletedQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			for(i = 1; i < 5; i++){
				
				var countFound = false
				jQuery(response).each(function(idx,val){					
					if(Number(val.quarter) == i){						
						countFound = true;
						dataArrayPerf[1][i] = 	val.count;					
					}				
				})				
				if(!countFound){					
					dataArrayPerf[1][i] = 0;
				}
			}
			
			if(aChart != null){				
				aChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					aChart= generateCategoryChart("#assessmentChart", "All Assessment Completed", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function servicesQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	
	var groupArray =  [['Advocacy'], ['Assessments'], ['Benefits/Insurance'],['Case Management'],['Child/day care'],['Computer training'],['Conflct resolution'],['Crisis intervention'],['Education program (GED, etc.)'],['Emergency assistance'],['Family support'],['Finanicial	management/literacy'],['Financial aid'],['Healthcare'],['Home management'],['Homemaker'],['Home ownership'],['Job readiness'],['Lease intervention'],['Leagal assistance'],['Meals'],['Mental health'],['Monitoring'],['Parenting'],['Prescriptions'],['School intervention'],['Substance abuse'],['Transportation'],['Veteran services'],['Vocational training'],['Youth programs'], ['Other']];
	var dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'], ['Advocacy'], ['Assessments'], ['Benefits/Insurance'],['Case Management'],['Child/day care'],['Computer training'],['Conflct resolution'],['Crisis intervention'],['Education program (GED, etc.)'],['Emergency assistance'],['Family support'],['Finanicial management/literacy'],['Financial aid'],['Healthcare'],['Home management'],['Homemaker'],['Home ownership'],['Job readiness'],['Lease intervention'],['Leagal assistance'],['Meals'],['Mental health'],['Monitoring'],['Parenting'],['Prescriptions'],['School intervention'],['Substance abuse'],['Transportation'],['Veteran services'],['Vocational training'],['Youth programs'], ['Other']]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
			
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/serviceProvidedQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
		
		jQuery(response).each(function(idx,val){
			jQuery(groupArray).each(function(p,category){		
				
				if(val.category == category){
					//if category matches then only check for each quarter
					for(j = 1; j < 5; j++){
					
						// This shows on each quarter some Catory found
						if(val.quarter == j ){									
							dataArrayPerf[p+1][j] = 	val.count;												
						}else{
							dataArrayPerf[p+1][j] = 0;
						}	
					}
				}		
			});	
						
		});	
			
			if(servicesChart != null){				
				servicesChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					servicesChart= generateCategoryChart("#servicesChart", "Services Provided by Category", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}


function noShowsQuarterly(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArrayPerf = null;
	
	var groupArray =  [['No Shows']];
	dataArrayPerf = [['x', 'Q1', 'Q2', 'Q3', 'Q4'],groupArray[0]]; //Q1, Q2,Q3,Q4 values will be filled by logic below
	
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/noShowsQuarterly",
		data: JSON.stringify({'selectedProperties':selectedProps, 'year':jQuery("input:radio:checked").val()}),
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			for(i = 1; i < 5; i++){
				
				var countFound = false
				jQuery(response).each(function(idx,val){					
					if(Number(val.quarter) == i){						
						countFound = true;
						dataArrayPerf[1][i] = 	val.count;					
					}				
				})				
				if(!countFound){					
					dataArrayPerf[1][i] = 0;
				}
			}
			
			if(noShowChart != null){				
				noShowChart.load({
					columns : dataArrayPerf					
				})
			}else{		
					noShowChart= generateCategoryChart("#noShowsChart", "No Shows", groupArray, dataArrayPerf);
					
				}			
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function generateCategoryChart(id, title, groupArray, dataArrayPerf){

			c3.generate({
			bindto : id,
			title: {
		        show: false,
		        text: title,
		        position: 'top-center',   // top-left, top-center and top-right
		        padding: {
		          top: 20,
		          right: 20,
		          bottom: 40,
		          left: 50
		        }
		
      		},
			color: {	    
		 		pattern: colors
			},		
    		data: {
        		x : 'x',
        		columns: dataArrayPerf,
		        groups: groupArray,
		        type: 'bar',
		        labels: true
			    },
			    axis: {
			        x: {
			            type: 'category' // this needed to load string x value
			        }
			    }
			});
}

function generateChart(attachId, title){
	
	c3.generate({
					bindto : attachId,
					title: {
        show: false,
        text: title,
        position: 'top-center',   // top-left, top-center and top-right
        padding: {
          top: 20,
          right: 20,
          bottom: 40,
          left: 50
        }
		
      },
					data : {
					    columns : dataArray,
					    type : 'bar',
						labels:{
							 format:function (v, id, i, j) { return v + '%'; }
						},
					},
			color: {	    
		 		pattern: colors
			},				
					axis : {
					    x : {
						tick : {
						    values : [ '' ]
						}
					    },
					    y : {						
						tick: {
					            format: function (d) {
					               return (parseInt(d) == d) ? d + ' %' : null; 						
					             }
					          }
					    }
					},
					tooltip: {
  						format: {
    						title: function (x, index) { return title+' % '}
  								}
					}					
				    });	
	
}

function signUpPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/signUpPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {			
			
			dataArray = [[response[0].category,response[0].percentage]];
						
			if(chartSignUP != null){				
				chartSignUP.load({
					columns : dataArray					
				})
			}else{		
					chartSignUP= generateChart("#signUpChart", "Engagement");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function intResCouncilPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/intResCouncilPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage]
						];
						
			if(chartIRC != null){				
				chartIRC.load({
					columns : dataArray					
				})
			}else{		
					chartIRC= generateChart("#intResCouncilChart", "Interest in Resident Council");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function hohTypePercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/hohTypePercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage]
						];
						
			if(chartHoh != null){				
				chartHoh.load({
					columns : dataArray					
				})
			}else{		
					chartHoh = generateChart("#hohTypeChart", "Household Type");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}


function internetAccessPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/internetAccessPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage]
						];
						
			if(chartIA != null){				
				chartIA.load({
					columns : dataArray					
				})
			}else{		
					chartIA = generateChart("#internetAccessChart", "Has Computer with Internet Access");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function prefferedContactPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/prefContactPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage]
						];
						
			if(chartPC != null){				
				chartPC.load({
					columns : dataArray					
				})
			}else{		
					chartPC = generateChart("#prefContactChart", "Preferred Method of Contact");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function occLengthPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/occupancyLengthPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
						];
						
			if(chartOL != null){				
				chartOL.load({
					columns : dataArray					
				})
			}else{		
					chartOL = generateChart("#occLengthChart", "Occupancy Length");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function safeDayPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/safeDayPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage]
						];
						
			if(chartSD != null){				
				chartSD.load({
					columns : dataArray					
				})
			}else{		
					chartSD = generateChart("#safeDayChart", "Feels Safe at Home During the Day");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function safeNightPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/safeNightPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage]
						];
						
			if(chartSN != null){				
				chartSN.load({
					columns : dataArray					
				})
			}else{		
					chartSN = generateChart("#safeNightChart", "Feels Safe at Home During the Night");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}



function foodShortagePercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/expFoodShortagePercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage]
						];
						
			if(chartFS != null){				
				chartFS.load({
					columns : dataArray					
				})
			}else{		
					chartFS = generateChart("#footShortageChart", "Experiences Food Shortage");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
}

function modTransPercentage(){
	
	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;
	
	jQuery.ajax({	
		type : "POST",
		contentType : "application/json",
		url : "/modeOfTransportationPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {
			
			
			dataArray = [[response[0].category,response[0].percentage],
						 [response[1].category,response[1].percentage],
					[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage]
						];
						
			if(chartMT != null){				
				chartMT.load({
					columns : dataArray					
				})
			}else{		
					chartMT = generateChart("#modTransChart", "Mode of Transportation");
					
				}	
		},		
		error : function(e) {
		    console.log("ERROR : ", e);
		}
    });
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
					chart = generateChart("#genderChart", "Gender");
					
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chartEth != null){
				chartEth.load({
					columns : dataArray
				})
			}else{
				chartEth = generateChart("#ethnicityChart", "Ethnicity");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]

			];

			if(chartLang != null){
				chartLang.load({
					columns : dataArray
				})
			}else{
				chartLang = generateChart("#languageChart", "Language");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chartMS != null){
				chartMS.load({
					columns : dataArray
				})
			}else{
				chartMS = generateChart("#maritalStatusChart", "MaritalStatus");
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

			if(chartRace != null){
				chartRace.load({
					columns : dataArray
				})
			}else{
				chartRace = generateChart("#raceChart", "Race");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chartHouseHold != null){
				chartHouseHold.load({
					columns : dataArray
				})
			}else{
				chartHouseHold = generateChart("#householdChart", "Household");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chartVeteran != null){
				chartVeteran.load({
					columns : dataArray
				})
			}else{
				chartVeteran = generateChart("#veteranChart", "Veteran");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage]
			];

			if(chartDis != null){
				chartDis.load({
					columns : dataArray
				})
			}else{
				chartDis = generateChart("#disabilityChart", "Disability");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chartExOff != null){
				chartExOff.load({
					columns : dataArray
				})
			}else{
				chartExOff = generateChart("#exOffenderChart", "Ex Off / Returning Citizen");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chartSsi != null){
				chartSsi.load({
					columns : dataArray
				})
			}else{
				chartSsi = generateChart("#ssiChart", "SSI");
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

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage]
			];

			if(chartSsdi != null){
				chartSsdi.load({
					columns : dataArray
				})
			}else{
				chartSsdi = generateChart("#ssdiChart", "SSDI");
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

			if(chartHealth != null){
				chartHealth.load({
					columns : dataArray
				})
			}else{
				chartHealth = generateChart("#healthChart", "Health");
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

			if(chartEdu != null){
				chartEdu.load({
					columns : dataArray
				})
			}else{
				chartEdu = generateChart("#educationChart", "Education");
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}


function hcPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/hcPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage]
			];

			if(chartHC != null){
				chartHC.load({
					columns : dataArray
				})
			}else{
				chartHC = generateChart("#hcChart", "Health Condition");
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}


function unEmpReasonPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/unEmpReasonPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage],
				[response[8].category,response[8].percentage],
				[response[9].category,response[9].percentage]
			];

			if(chartUnEmpReason != null){
				chartUnEmpReason.load({
					columns : dataArray
				})
			}else{
				chartUnEmpReason = generateChart("#unEmpReasonChart", "Primary reason for Unemployment");
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function barrierToEduPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/barrierToEduPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage],
				[response[8].category,response[8].percentage],
				[response[9].category,response[9].percentage]
			];

			if(chartBarrierToEdu != null){
				chartBarrierToEdu.load({
					columns : dataArray
				})
			}else{
				chartBarrierToEdu = generateChart("#barrierToEduChart", "Primary Barrier to College/Job training ");
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function psyPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/psyPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			
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

			if(chartPsy != null){
				chartPsy.load({
					columns : dataArray
				})
			}else{
				chartPsy = generateChart("#psyChart", "Program and Services desired for Youth  ");
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function psaPercentage(){

	var selectedProps = JSON.stringify(selectedProperties);
	dataArray = null;

	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/psaPercentage",
		data: selectedProps,
		dataType : 'json',
		cache : false,
		timeout : 60000,
		success : function(response) {

			
			dataArray = [[response[0].category,response[0].percentage],
				[response[1].category,response[1].percentage],
				[response[2].category,response[2].percentage],
				[response[3].category,response[3].percentage],
				[response[4].category,response[4].percentage],
				[response[5].category,response[5].percentage],
				[response[6].category,response[6].percentage],
				[response[7].category,response[7].percentage]
			];

			if(chartPsa != null){
				chartPsa.load({
					columns : dataArray
				})
			}else{
				chartPsa = generateChart("#psaChart", "Program and Services desired for Adult  ");
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
