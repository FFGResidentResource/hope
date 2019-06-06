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
    
    if(lifeDomain == 'EMPLOYMENT'){
	calculateEmploymentScore();
    }
    
    if(lifeDomain == 'EDUCATION'){
	calculateEducationScore();
    }
    
    if(lifeDomain == 'NETWORK SUPPORT'){
	calculatNetworkSupportScore();
    }
    
    if(lifeDomain == 'HOUSEHOLD MANAGEMENT'){
	calculateHouseholdScore();
    }
}

function saveAssessment(form){
   if($('#isNewAssessmentAllowed').val()=='false'){
     if($('#_dates').val()=='NewAssessment'){
       alert("Only ONE assessment is allowed in 6 months. Please update the latest assessment.");
       return false;
     } else{
        var dateOfLatestAssessment = $('#dateOfLatestAssessment').val();
        if(dateOfLatestAssessment!=$('#_dates').val()){
           alert("Please update the latest assessment, "+dateOfLatestAssessment);
           return false;
        }
     }
   }

   if($('#isNewAssessmentAllowed').val()=='true' &&
     $('#_dates').val()!='NewAssessment'){
      alert("Please create a new assessment. Any assessment older than 6 months is not editable.");
      return false;
   }

   //other validation logic
   form.submit();
}
function saveAndNextAssessment(form){
   form.action="/saveAssessmentAndGoToNext";
   saveAssessment(form);
}

function validateAndShowMessage(){
    
    var todaysDate = moment().format('DD-MMM-YYYY').toUpperCase();
    
    //check for Today's Date
    var todaysAssessmentExists = false;
    jQuery("#_dates option").each(function(){
	
	if(jQuery(this).val() == todaysDate){
	    todaysAssessmentExists = true;
	}	
    });
    
    if(todaysAssessmentExists == true &&  jQuery("#_dates option:selected").val().inlcudes('new')){
	jQuery(".text-danger").removeClass('hideme');
	jQuery("input[type='submit']").prop('disabled', true);
    }else{
	jQuery(".text-danger").removeClass('hideme').addClass('hideme');
	jQuery("input[type='submit']").prop('disabled', false);
    }
}


