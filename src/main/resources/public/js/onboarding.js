jQuery(document).ready(function() {

    debugger;
    var selectedResidentId = jQuery.urlParam('residentId');
       
    jQuery.ajax({
	type : "POST",
	contentType : "application/json",
	url : "/getAllResidents",
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {

	    /* Building DataTable for All Resident on Onboarding Tab */
	    table = jQuery('#onboardingAllResidentTable').DataTable({
		"data" : data,
		"columns" : [{		    	
		    	data : 'residentId',
		    	render : function(t, type, row) {
				return '_'+row.residentId+'_';
			},
			visible:false
		    },
		    {
			type: 'num',
			data : 'residentId'		    
		    }, {
		    data : 'firstName',
		    render : function(t, type, row) {
			return row.firstName + ' ' + row.middle + ' ' + row.lastName;
		    }
		}, {
		    data : 'propertyName'
		}, {
		    data : 'dateAdded.time',
		    render : {
			_ : 'display',
			sort : 'timestamp'
		    },
		    render : function(t, type, row) {
			return moment(row.dateAdded).format("MM/DD/YY HH:mm");
		    }
		}, {
		    data : 'serviceCoord'
		},
		 {
		    data : 'voiceMail',
			visible: false
		},
		 {
		    data : 'text',
			visible: false
		},
		 {
		    data : 'email',
			visible: false
		},
		 {
		    data : 'address',
			visible: false
		} ],
		"order" : [ [ 1, "desc" ] ],
		pageLength : 8,
		pagingType : "full_numbers"
	    });  
	    
	    //Step - CheckGrants	    
	    if (jQuery("#_propertyGrant").text() != 'All') {
		table.columns(3).search(jQuery("#_propertyGrant").text()).draw();
	    }
	    
	    //Filter on resident_id after Grant Filter
	    if(selectedResidentId != null){
		
		table.columns(0).search('_'+selectedResidentId+'_').draw();
		table.row(':eq(0)', { page: 'current' }).select();	
		
		var fullName = jQuery("#onboardingAllResidentTable tbody tr td:eq(1)").text();
		jQuery("#_selectedResident").text(fullName);
	    }   
	    
	    //If table goes empty read its value - "No matching records found"
	    var emptyString = jQuery('.dataTables_empty').text(); 
	    
	    jQuery("#onboardingAllResidentTable_length").addClass("hideme");
	    jQuery("#onboardingAllResidentTable_filter input").addClass("input-sm");	   
	    jQuery("#onboardingAllResidentTable_filter input").attr("placeholder", 'Wild Search');   
	    
	    if(selectedResidentId != null && emptyString != 'No matching records found'){
		
		 suffixResidentIdOnEachStep(selectedResidentId);
		 onboadingStepsStatus(selectedResidentId);
		 getAllLatestScoreGoal(selectedResidentId);	 
		 
		 jQuery('a[id^="_load"]').attr('disabled', false);
		 jQuery('a[id^="_load"]').attr('onclick', 'return true;');
	    }else{
		
		jQuery("#_currentSelectedResident").text('');
		jQuery('a[id^="_load"]').attr('disabled', true);
		 jQuery('a[id^="_load"]').attr('onclick', 'return false;');
	    }
	    
	    
	    jQuery('#onboardingAllResidentTable tbody').on('click', 'tr', function() {

		var tr = $(this);
		currentRow = table.row(this).data();

		console.log(currentRow);

		if ($(this).hasClass('selected')) {
		    $(this).removeClass('selected');	    

		    jQuery("#_hScoreGoal").text('--/--');
		    jQuery("#_mmScoreGoal").text('--/--');
		    jQuery("#_empScoreGoal").text('--/--');
		    jQuery("#_eduScoreGoal").text('--/--');
		    jQuery("#_nsScoreGoal").text('--/--');
		    jQuery("#_hhScoreGoal").text('--/--');
		    
		    jQuery('a[id^="_load"]').attr('disabled', true);
		    jQuery('a[id^="_load"]').attr('onclick', 'return false;');
		    
		    jQuery("#_selectedResident").text('');
		    
		} else {
		    jQuery('a[id^="_load"]').attr('disabled', false);		    		   
		    jQuery('a[id^="_load"]').attr('onclick', 'return true;');

		    table.$('tr.selected').removeClass('selected');
		    $(this).addClass('selected');	
		    
		    jQuery("#_selectedResident").text(' '+ currentRow.firstName + ' ' + currentRow.middle + ' ' + currentRow.lastName);
		    
		    suffixResidentIdOnEachStep(currentRow.residentId);		    
		    onboadingStepsStatus(currentRow.residentId);
		    getAllLatestScoreGoal(currentRow.residentId);
		}
	    });

	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });

});

function getAllLatestScoreGoal(residentId){
    
    /*
     * Following code populates score and goal once a
     * row a clicked
     */
    jQuery.ajax({
	type : "POST",
	contentType : "application/json",
	url : "/getAllLatestScoreGoal?residentId=" + residentId,
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {
	    debugger;
	    jQuery("#_hScoreGoal").text((data["HOUSING"] == '0 / 0') ? 'DK / DA' : data["HOUSING"]);
	    jQuery("#_mmScoreGoal").text((data["MONEY MANAGEMENT"] == '0 / 0' ) ?  'DK / DA' : data["MONEY MANAGEMENT"]);
	    jQuery("#_empScoreGoal").text((data["EMPLOYMENT"] == '0 / 0') ? 'DK / DA' : data["EMPLOYMENT"]);
	    jQuery("#_eduScoreGoal").text((data["EDUCATION"] == '0 / 0') ? 'DK / DA' : data["EDUCATION"]);
	    jQuery("#_nsScoreGoal").text((data["NETWORK SUPPORT"] == '0 / 0') ? 'DK / DA' : data["NETWORK SUPPORT"]);
	    jQuery("#_hhScoreGoal").text((data["HOUSEHOLD MANAGEMENT"] == '0 / 0') ? 'DK / DA' : data["HOUSEHOLD MANAGEMENT"]);
	},
	error : function(e) {
	    console.log("ERROR retrieving Score and Goal: ", e);
	}
    });
}

function onboadingStepsStatus(residentId) {
    
    /*
     * Following code put Green CheckBox and Green Color on each Step
     */
    jQuery.ajax({
	type : "POST",
	contentType : "application/json",
	url : "/getOnboardingStepStatus?residentId="+residentId,
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {
	   
	    if(data.referralFormComplete == true){
		jQuery("#_refFormComplete").removeClass('hideme');
		jQuery("#_loadReferralForm").removeClass('btn-default').removeClass('btn-success').addClass('btn-success');
	    }else{
		jQuery("#_refFormComplete").removeClass('hideme').addClass('hideme');
		jQuery("#_loadReferralForm").removeClass('btn-default').removeClass('btn-success').addClass('btn-default');
	    }
	    if(data.signUpComplete == true){
		jQuery("#_signUpComplete").removeClass('hideme')
		jQuery("#_loadResident").removeClass('btn-default').removeClass('btn-success').addClass('btn-success');
	    }else{
		jQuery("#_signUpComplete").removeClass('hideme').addClass('hideme');
		jQuery("#_loadResident").removeClass('btn-default').removeClass('btn-success').addClass('btn-default');
	    }
	    if(data.actionPlanComplete == true){
		jQuery("#_actionPlanComplete").removeClass('hideme')
		jQuery("#_loadActionPlan").removeClass('btn-default').removeClass('btn-success').addClass('btn-success');
	    }else{
		jQuery("#_actionPlanComplete").removeClass('hideme').addClass('hideme');
		jQuery("#_loadActionPlan").removeClass('btn-default').removeClass('btn-success').addClass('btn-default');
	    }
	    if(data.contactNotesComplete == true){
		jQuery("#_contactNotesComplete").removeClass('hideme')
		jQuery("#_loadCaseNotes").removeClass('btn-default').removeClass('btn-success').addClass('btn-success');
	    }else{
		jQuery("#_contactNotesComplete").removeClass('hideme').addClass('hideme');
		jQuery("#_loadCaseNotes").removeClass('btn-default').removeClass('btn-success').addClass('btn-default');
	    }
	    if(data.housingComplete == true){
		jQuery("#_housingComplete").removeClass('hideme');		
	    }else{
		jQuery("#_housingComplete").removeClass('hideme').addClass('hideme');		
	    }
	    if(data.moneyMgmtComplete == true){
		jQuery("#_moneyMgmtComplete").removeClass('hideme');		
	    }else{
		jQuery("#_moneyMgmtComplete").removeClass('hideme').addClass('hideme');		
	    }
	    if(data.employmentComplete == true){
		jQuery("#_empComplete").removeClass('hideme');		
	    }else{
		jQuery("#_empComplete").removeClass('hideme').addClass('hideme');		
	    }
	    if(data.educationComplete == true){
		jQuery("#_eduComplete").removeClass('hideme');		
	    }else{
		jQuery("#_eduComplete").removeClass('hideme').addClass('hideme');		
	    }
	    if(data.netSuppComplete == true){
		jQuery("#_netSuppComplete").removeClass('hideme');		
	    }else{
		jQuery("#_netSuppComplete").removeClass('hideme').addClass('hideme');		
	    }
	    if(data.householdComplete == true){
		jQuery("#_hhComplete").removeClass('hideme');		
	    }else{
		jQuery("#_hhComplete").removeClass('hideme').addClass('hideme');		
	    }
	    
	    
	},
	error : function(e) {
	    console.log("ERROR retrieving Completed Steps: ", e);
	}
    });
    
}

function suffixResidentIdOnEachStep(residentId){
    
    /*
     * Following code builds hyperlink for each
     * Assessment buttons when a row is clicked in all
     * Resident Table
     */
    var suffix = '&residentId=' + residentId;
    var assessmentLinks = jQuery('a[id^="_load"]');

    jQuery.each(assessmentLinks, function(idx, obj) {
	var currHref = jQuery(obj).attr('href');
	var prefix = currHref.split('&');
	jQuery(obj).attr('href', prefix[0] + suffix);
    });
    
}

