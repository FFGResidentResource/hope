jQuery(document).ready(function() {

    jQuery.ajax({
	type : "POST",
	contentType : "application/json",
	url : "/getAllLatestScoreGoal?residentId=" + jQuery('#_residentIdText').text(),
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {

	    // this changed background color in current SSM Table under action
	    // plan

	    if (data["HOUSING"][0] == '1') {
		jQuery("#_housingSSM1").addClass('danger');
	    } else if (data["HOUSING"][0] == '2') {
		jQuery("#_housingSSM2").addClass('warning');
	    } else if (data["HOUSING"][0] == '3') {
		jQuery("#_housingSSM3").addClass('info');
	    } else if (data["HOUSING"][0] == '4') {
		jQuery("#_housingSSM4").addClass('success');
	    } else if (data["HOUSING"][0] == '5') {
		jQuery("#_housingSSM5").addClass('success');
	    }
	    
	    
	    if (data["MONEY MANAGEMENT"][0] == '1') {
		jQuery("#_moneySSM1").addClass('danger');
	    } else if (data["MONEY MANAGEMENT"][0] == '2') {
		jQuery("#_moneySSM2").addClass('warning');
	    } else if (data["MONEY MANAGEMENT"][0] == '3') {
		jQuery("#_moneySSM3").addClass('info');
	    } else if (data["MONEY MANAGEMENT"][0] == '4') {
		jQuery("#_moneySSM4").addClass('success');
	    } else if (data["MONEY MANAGEMENT"][0] == '5') {
		jQuery("#_moneySSM5").addClass('success');
	    }
	    
	    
	    if (data["EDUCATION"][0] == '1') {
		jQuery("#_educationSSM1").addClass('danger');
	    } else if (data["EDUCATION"][0] == '2') {
		jQuery("#_educationSSM2").addClass('warning');
	    } else if (data["EDUCATION"][0] == '3') {
		jQuery("#_educationSSM3").addClass('info');
	    } else if (data["EDUCATION"][0] == '4') {
		jQuery("#_educationSSM4").addClass('success');
	    } else if (data["EDUCATION"][0] == '5') {
		jQuery("#_educationSSM5").addClass('success');
	    }
	    
	    
	    if (data["EMPLOYMENT"][0] == '1') {
		jQuery("#_employmentSSM1").addClass('danger');
	    } else if (data["EMPLOYMENT"][0] == '2') {
		jQuery("#_employmentSSM2").addClass('warning');
	    } else if (data["EMPLOYMENT"][0] == '3') {
		jQuery("#_employmentSSM3").addClass('info');
	    } else if (data["EMPLOYMENT"][0] == '4') {
		jQuery("#_employmentSSM4").addClass('success');
	    } else if (data["EMPLOYMENT"][0] == '5') {
		jQuery("#_employmentSSM5").addClass('success');
	    }
	    
	    
	    if (data["NETWORK SUPPORT"][0] == '1') {
		jQuery("#_netorkSSM1").addClass('danger');
	    } else if (data["NETWORK SUPPORT"][0] == '2') {
		jQuery("#_netorkSSM2").addClass('warning');
	    } else if (data["NETWORK SUPPORT"][0] == '3') {
		jQuery("#_netorkSSM3").addClass('info');
	    } else if (data["NETWORK SUPPORT"][0] == '4') {
		jQuery("#_netorkSSM4").addClass('success');
	    } else if (data["NETWORK SUPPORT"][0] == '5') {
		jQuery("#_netorkSSM5").addClass('success');
	    }
	    
	    if (data["HOUSEHOLD MANAGEMENT"][0] == '1') {
		jQuery("#_householdSSM1").addClass('danger');
	    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '2') {
		jQuery("#_householdSSM2").addClass('warning');
	    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '3') {
		jQuery("#_householdSSM3").addClass('info');
	    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '4') {
		jQuery("#_householdSSM4").addClass('success');
	    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '5') {
		jQuery("#_householdSSM5").addClass('success');
	    }

	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });
});