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