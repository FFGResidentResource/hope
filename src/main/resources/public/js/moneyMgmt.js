jQuery(document).ready(function() {
    
    validateAndShowMessage();
    
});


function calculateMoneyMgmtScore(){
	
	jQuery("#_mmlifeDomain").val('MONEY MANAGEMENT');
	
	jQuery('#currentMoneyMgmtScore').val('');	
	jQuery('#currentMoneyMgmtGoal').val('');
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
	    
		jQuery('input[id^=_moneyMgmtQuestionnaire_10]').prop('checked', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('checked', false);	
		jQuery('input[id^=_moneyMgmtQuestionnaire_10]').prop('disabled', true);
		jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', true);	
		jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);
	}
	else {		
	
		jQuery('input[id^=_moneyMgmtQuestionnaire_10]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);
	
		
		if(qChoice2[0].checked == true){
			jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('checked', false);
			jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('checked', false);
			jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('checked', false);	
			jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', true);
			jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', true);
			jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', true);					
		}else if(qChoice2[0].checked == false){		
			jQuery('input[id^=_moneyMgmtQuestionnaire_11]').prop('disabled', false);
			jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', false);
			jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);
			
			if(qChoice3[1].checked == true){
				jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('checked', false);
				jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('checked', false);	
				jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', true);
				jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', true);					
			}else if(qChoice3[1].checked == false){		
				jQuery('input[id^=_moneyMgmtQuestionnaire_12]').prop('disabled', false);
				jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);
				
				if(qChoice4[0].checked == true){
					jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('checked', false);	
					jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', true);					
				}else if(qChoice4[0].checked == false){
					jQuery('input[id^=_moneyMgmtQuestionnaire_13]').prop('disabled', false);					
				}
			}			
		}		
	}	
	
	if(qChoice6[1].checked == true){
		
		jQuery('[id^=mmst_1_]').removeClass('danger').addClass('danger');
		jQuery('#currentMoneyMgmtScore').val(1);
		jQuery('#mmst_6_body').text(1);
		jQuery('#currentMoneyMgmtGoal').val(2);
		jQuery('#mmst_7_body').text(2);	
		
		jQuery('input[id^=_moneyMgmtQuestionnaire_15]').prop('checked', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_16]').prop('checked', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_17]').prop('checked', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_18]').prop('checked', false);
		
		jQuery('input[id^=_moneyMgmtQuestionnaire_15]').prop('disabled', true);
		jQuery('input[id^=_moneyMgmtQuestionnaire_16]').prop('disabled', true);
		jQuery('input[id^=_moneyMgmtQuestionnaire_17]').prop('disabled', true);
		jQuery('input[id^=_moneyMgmtQuestionnaire_18]').prop('disabled', true);
		
	}
	else {	
		
		jQuery('input[id^=_moneyMgmtQuestionnaire_15]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_16]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_17]').prop('disabled', false);
		jQuery('input[id^=_moneyMgmtQuestionnaire_18]').prop('disabled', false);
		
		if(qChoice6[0].checked == true){
			jQuery('input[id^=_moneyMgmtQuestionnaire_15]').prop('disabled', true);
		}
		
		if(qChoice7[0].checked == true){
			
			jQuery('input[id^=_moneyMgmtQuestionnaire_16]').prop('checked', false);
			jQuery('input[id^=_moneyMgmtQuestionnaire_16]').prop('disabled', true);
			
		}		
		else{			
			if(qChoice8[0].checked == true){
				jQuery('input[id^=_moneyMgmtQuestionnaire_17]').prop('checked', false);
				jQuery('input[id^=_moneyMgmtQuestionnaire_18]').prop('checked', false);
				
				jQuery('input[id^=_moneyMgmtQuestionnaire_17]').prop('disabled', true);
				jQuery('input[id^=_moneyMgmtQuestionnaire_18]').prop('disabled', true);
				
				jQuery('[id^=mmst_2_]').removeClass('warning').addClass('warning');
				jQuery('#currentMoneyMgmtScore').val(2);
				jQuery('#mmst_6_body').text(2);
				jQuery('#currentMoneyMgmtGoal').val(3);
				jQuery('#mmst_7_body').text(3);
			}			
			else {
				
				if(qChoice9[1].checked == true){
					jQuery('[id^=mmst_2_]').removeClass('warning').addClass('warning');
					jQuery('#currentMoneyMgmtScore').val(2);
					jQuery('#mmst_6_body').text(2);
					jQuery('#currentMoneyMgmtGoal').val(3);
					jQuery('#mmst_7_body').text(3);	
					
					jQuery('input[id^=_moneyMgmtQuestionnaire_18]').prop('checked', false);					
					jQuery('input[id^=_moneyMgmtQuestionnaire_18]').prop('disabled', true);
					
				}
				
				else{					
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
	}	
}