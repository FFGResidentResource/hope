jQuery(document).ready(function() {

	debugger;
	jQuery('a').parent().removeClass('active');
	var path = window.location.pathname;
	if (path == '/newResident' || path == "/getResidentById") {
		jQuery("a[href='/newResident']").parent().addClass('active');
	} else if (path == '/admin') {
		jQuery("a[href='/admin']").parent().addClass('active');
	} else if (path == '/allResident') {
		jQuery("a[href='/allResident']").parent().addClass('active');
	}

	stepCounter();
});

var isSubmitted = false;
function saveOrUpdateResident(form){
  if(!isSubmitted){
     form.action="saveResident";
     form.submit();
     jQuery("#btnSubmit").attr("disabled", true);
     isSubmitted = true;
  } else{
    alert("System is processing, please be patient.");
  }
};

function deactivateResident(form){
  if(!isSubmitted){
     form.action="deactivateResident";
     form.submit();
     jQuery("#btnDeactivateResident").attr("disabled", true);
     isSubmitted = true;
  } else{
    alert("System is processing, please be patient.");
  }
};

function stepCounter() {

	if (res != null && res.wsCounter.signUpComplete) {
		jQuery('#addResident_View').attr('class',
				'col-xs-1 bs-wizard-step complete');
	}
	if (res != null && res.wsCounter.assessmentComplete) {
		jQuery('#ssm_View').attr('class', 'col-xs-1 bs-wizard-step complete');
	}
};

function toggleForm(prefix) {

	var formName = "#" + prefix;

	// Toggle Form
	jQuery("[id$='_Form']").removeClass('hideme')
	jQuery("[id$='_Form']").addClass('hideme');

	jQuery(formName + "_Form").removeClass("hideme");

	// Toggle Wizard
	jQuery("[id$='_View']").removeClass('disabled');
	jQuery("[id$='_View']").addClass('disabled');

	stepCounter();

	jQuery(formName + "_View").removeClass("disabled");
	jQuery(formName + "_View").removeClass('active');
	jQuery(formName + "_View").addClass('active');

};

document.getElementById('allowcontact').onchange = function() {
	document.getElementById('viaemail').disabled = this.checked;
	document.getElementById('email').disabled = this.checked;
	document.getElementById('viavoicemail').disabled = this.checked;
	document.getElementById('voicemail').disabled = this.checked;
	document.getElementById('viatext').disabled = this.checked;
	document.getElementById('text').disabled = this.checked;	
};

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
			
			if(lifeDomain == 'HOUSING'){
				calculateHousingScore();	
			}
		},
		error : function(){
			jQuery('input[id^='+ prefix + 'Questionnaire_]:radio').prop('checked',false);			
			jQuery('#'+prefix+'Submit').val('Save ' + lifeDomain + ' Assessment');
			
			if(lifeDomain == 'HOUSING'){
				calculateHousingScore();	
			}
		}
	});
};

function reset(chk){
	$('.setable').val(''); $('.setable').prop('checked', false);$(chk).trigger('change');
};

function calculateHouseHoldScore(){
	
};
function calculatNetworkSupportScore(){
	
};
function calculateEducationScore(){
	
};
function calculateEmploymentScore(){
	
};
function calculateMoneyMgmtScore(){
	
	jQuery("#_lifeDomain").val('MONEY MANAGEMENT');
	
	jQuery('#mmst_6_body').text('');
	jQuery('#mmst_7_body').text('');
	jQuery('[id^=mmst_]').removeClass('danger').removeClass('success').removeClass('info').removeClass('warning');
	
	var qChoice1 = jQuery('input[id^=_moneyMgmtQuestionnaire_9]:radio');
	var qChoice2 = jQuery('input[id^=_moneyMgmtQuestionnaire_10]:radio');
	var qChoice3 = jQuery('input[id^=_moneyMgmtQuestionnaire_11]:radio');
	var qChoice4 = jQuery('input[id^=_moneyMgmtQuestionnaire_12]:radio');
	var qChoice5 = jQuery('input[id^=_moneyMgmtQuestionnaire_13]:radio');
	var qChoice6 = jQuery('input[id^=_moneyMgmtQuestionnaire_14]:radio');
	var qChoice7 = jQuery('input[id^=_moneyMgmtQuestionnaire_15]:radio');
	var qChoice8 = jQuery('input[id^=_moneyMgmtQuestionnaire_16]:radio');
	var qChoice9 = jQuery('input[id^=_moneyMgmtQuestionnaire_17]:radio');
	var qChoice10 = jQuery('input[id^=_moneyMgmtQuestionnaire_18]:radio');
	
	if(qChoice1[1].checked == true){		
		jQuery('input[id^=_moneyMgmtQuestionnaire_10]').prop('disabled', true);
		jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', true);			
	}
	else if (qChoice1[1].checked == false){	
		jQuery('input[id^=_moneyMgmtQuestionnaire_10]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', false);
		
		if(qChoice2[0].checked == true){		
			jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', true);
			jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', true);
			jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', true);					
		}else if(qChoice2[0].checked == false){		
			jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', false);
			jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', false);
			jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);
			
			if(qChoice3[1].checked == true){	
				jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', true);
				jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', true);					
			}else if(qChoice3[1].checked == false){		
				jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', false);
				jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);
				
				if(qChoice4[0].checked == true){
					jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', true);					
				}else if(qChoice4[0].checked == false){
					jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);					
				}
			}			
		}		
	}	
	
	if(qChoice6[1].checked == true){
		jQuery('input[id^=_moneyMgmtQuestionnaire_15]').prop('disabled', false);
		jQuery('[id^=mmst_1_]').removeClass('danger').addClass('danger');
		jQuery('#currentMoneyMgmtScore').val(1);
		jQuery('#mmst_6_body').text(1);
		jQuery('#currentMoneyMgmtGoal').val(2);
		jQuery('#mmst_7_body').text(2);			
	}
	else if(qChoice6[0].checked == true){
		
		jQuery('input[id^=_moneyMgmtQuestionnaire_15]').prop('disabled', true);
		
		if(qChoice7[0].checked == true){
			jQuery('input[id^=_moneyMgmtQuestionnaire_16]').prop('disabled', true);
		}else if(qChoice7[1].checked == true){
			jQuery('input[id^=_moneyMgmtQuestionnaire_16]').prop('disabled', false);
		}
		
		if(qChoice8[0].checked == true){		
			jQuery('[id^=mmst_2_]').removeClass('warning').addClass('warning');
			jQuery('#currentMoneyMgmtScore').val(2);
			jQuery('#mmst_6_body').text(2);
			jQuery('#currentMoneyMgmtGoal').val(3);
			jQuery('#mmst_7_body').text(3);			
		}else if(qChoice8[1].checked == true){
			jQuery('input[id^=_moneyMgmtQuestionnaire_17]').prop('disabled', false);
			
			if(qChoice9[1].checked == true){
				jQuery('[id^=mmst_2_]').removeClass('warning').addClass('warning');
				jQuery('#currentMoneyMgmtScore').val(2);
				jQuery('#mmst_6_body').text(2);
				jQuery('#currentMoneyMgmtGoal').val(3);
				jQuery('#mmst_7_body').text(3);		
			}else {
				
				if(qChoice10[0].checked == true){
					
					jQuery('[id^=mmst_3_]').removeClass('info').addClass('info');
					jQuery('#currentMoneyMgmtScore').val(3);
					jQuery('#mmst_6_body').text(3);
					jQuery('#currentMoneyMgmtGoal').val(4);
					jQuery('#mmst_7_body').text(4);	
				} 
				else if(qChoice10[1].checked == true){
					
					jQuery('[id^=mmst_4_]').removeClass('success').addClass('success');
					jQuery('#currentMoneyMgmtScore').val(4);
					jQuery('#mmst_6_body').text(4);
					jQuery('#currentMoneyMgmtGoal').val(5);
					jQuery('#mmst_7_body').text(5);	
				} 
				else if(qChoice10[2].checked == true){
					
					jQuery('[id^=mmst_5_]').removeClass('success').addClass('success');
					jQuery('#currentMoneyMgmtScore').val(5);
					jQuery('#mmst_6_body').text(5);
					jQuery('#currentMoneyMgmtGoal').val(5);
					jQuery('#mmst_7_body').text(5);	
				}			
			}			
		}		
	}	
};

function calculateHousingScore(){
	
	jQuery("#_lifeDomain").val('HOUSING');

	jQuery('#hst_6_body').text('');
	jQuery('#hst_7_body').text('');
	jQuery('[id^=hst_]').removeClass('danger').removeClass('success').removeClass('info').removeClass('warning');
	
	debugger;
	var qChoice1 = jQuery('input[id^=_housingQuestionnaire_1]:radio');
	var qChoice2 = jQuery('input[id^=_housingQuestionnaire_2]:radio');
	var qChoice3 = jQuery('input[id^=_housingQuestionnaire_3]:radio');
	var qChoice4 = jQuery('input[id^=_housingQuestionnaire_4]:radio');
	var qChoice5 = jQuery('input[id^=_housingQuestionnaire_5]:radio');
	var qChoice6 = jQuery('input[id^=_housingQuestionnaire_6]:radio');
	var qChoice7 = jQuery('input[id^=_housingQuestionnaire_7]:radio');
	var qChoice8 = jQuery('input[id^=_housingQuestionnaire_8]:radio');
	
	if(qChoice1[1].checked == true){		
		jQuery('[id^=hst_1_]').removeClass('danger').addClass('danger');
		jQuery('#currentHousingScore').val(1);		
		jQuery('#hst_6_body').text(1);
		jQuery('#currentHousingGoal').val(2);
		jQuery('#hst_7_body').text(2);
		
	}else if(qChoice2[0].checked == true){		
		jQuery('[id^=hst_1_]').removeClass('danger').addClass('danger');
		jQuery('#currentHousingScore').val(1);
		jQuery('#hst_6_body').text(1);
		jQuery('#currentHousingGoal').val(2);
		jQuery('#hst_7_body').text(2);
						
	}else if(qChoice4[1].checked == true){		
		jQuery('[id^=hst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentHousingScore').val(2);
		jQuery('#hst_6_body').text(2);
		jQuery('#currentHousingGoal').val(3);
		jQuery('#hst_7_body').text(3);
				
	}
	else if(qChoice5[0].checked == true){		
		jQuery('[id^=hst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentHousingScore').val(2);
		jQuery('#hst_6_body').text(2);
		jQuery('#currentHousingGoal').val(3);
		jQuery('#hst_7_body').text(3);
					
	}
	else if(qChoice6[0].checked == true){		
		jQuery('[id^=hst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentHousingScore').val(2);
		jQuery('#hst_6_body').text(2);
		jQuery('#currentHousingGoal').val(3);
		jQuery('#hst_7_body').text(3);
					
	}
	else if(qChoice7[1].checked == true){		
		jQuery('[id^=hst_3_]').removeClass('info').addClass('info');
		jQuery('#currentHousingScore').val(3);
		jQuery('#hst_6_body').text(3);
		jQuery('#currentHousingGoal').val(4);
		jQuery('#hst_7_body').text(4);
		
	}
	else if(qChoice8[0].checked == true){		
		jQuery('[id^=hst_4_]').removeClass('success').addClass('success');
		jQuery('#currentHousingScore').val(4);
		jQuery('#hst_6_body').text(4);
		jQuery('#currentHousingGoal').val(5);
		jQuery('#hst_7_body').text(5);
		
	}
	else if(qChoice8[1].checked == true){		
		jQuery('[id^=hst_5_]').removeClass('success').addClass('success');
		jQuery('#currentHousingScore').val(5);
		jQuery('#hst_6_body').text(5);
		jQuery('#currentHousingGoal').val(5);
		jQuery('#hst_7_body').text(5);
	}
	
	if(qChoice3[0].checked == true){
		jQuery('input[id^=_housingQuestionnaire_4]').prop('checked', false);
		jQuery('input[id^=_housingQuestionnaire_5]').prop('checked', false);
		jQuery('input[id^=_housingQuestionnaire_4]').prop('disabled', true);
		jQuery('input[id^=_housingQuestionnaire_5]').prop('disabled', true);			
	}else if (qChoice3[0].checked == false){	
		jQuery('input[id^=_housingQuestionnaire_4]').prop('disabled', false);
		jQuery('input[id^=_housingQuestionnaire_5]').prop('disabled', false);		
	}	
};