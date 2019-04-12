function getHistoricalAssessmentByResidentIdAndLifeDomain(that, residentId, lifeDomain, prefix){
	
	jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/getHistoricalAssessmentByResidentIdAndLifeDomain?residentId="+residentId+"&onThisDate="+that.value+"&lifeDomain="+lifeDomain,
		dataType : 'json',		
		cache : false,
		timeout : 600000,
		success : function(response) {
			jQuery('#'+prefix+'Submit').val('Update Historical '+lifeDomain +' Assessment');
			jQuery.each(response, function(idx, obj){				
				jQuery('#'+prefix+'Questionnaire_'+obj.questionId+'_'+obj.choiceId).prop('checked', true);
			});
			
			calculateAllScores(lifeDomain);
			//TODO - populate other Score()
		},
		error : function(){
			jQuery('input[id^='+ prefix + 'Questionnaire_]:radio').prop('checked',false);			
			jQuery('#'+prefix+'Submit').val('Save ' + lifeDomain + ' Assessment');
			
			calculateAllScores(lifeDomain);
			
		}
	});
}


/**
 * following method will populate score and goal when you select historical date under each Assessment
 * @param lifeDomain
 * @returns
 */
function calculateAllScores(lifeDomain){
    
    if(lifeDomain == 'HOUSING'){
	calculateHousingScore();	
    }
    if(lifeDomain == 'MONEY MANAGEMENT'){
	calculateMoneyMgmtScore();    
    }
    
    //TODO - add code here
    
}

