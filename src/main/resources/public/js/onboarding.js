jQuery(document).ready(function() {

    debugger;
    var currentResidentId = (jQuery('#_resID').val() != '') ? currentResidentId = jQuery('#_resID').val() : 0;
    var suffix = '&residentId=' + currentResidentId;

    var assessmentLinks = jQuery('a[id^="_load"]');

    jQuery.each(assessmentLinks, function(idx, obj) {
	var currHref = jQuery(obj).attr('href');
	var prefix = currHref.split('&');
	jQuery(obj).attr('href', prefix[0] + suffix);
    });
    
    jQuery.ajax({
	type : "POST",
	contentType : "application/json",
	url : "/getAllResidents",
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {

	    table = jQuery('#onboardingAllResidentTable').DataTable({
		"data" : data,
		"columns" : [ {
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
		} ],
		"order" : [ [ 3, "desc" ] ],
		pageLength : 6,
		pagingType : "full_numbers"
	    });

	    if (jQuery("#_propertyGrant").text() != 'All') {
		table.columns(2).search(jQuery("#_propertyGrant").text()).draw();
	    }
	    
	    jQuery("#onboardingAllResidentTable_length").addClass("hideme");
	    jQuery("#onboardingAllResidentTable_filter input").addClass("input-sm");	   
	    jQuery("#onboardingAllResidentTable_filter input").attr("placeholder", 'Wild Search');
	    
	    
	    jQuery('#onboardingAllResidentTable tbody').on('click', 'tr', function() {

		var tr = $(this);
		currentRow = table.row(this).data();

		console.log(currentRow);

		if ($(this).hasClass('selected')) {
		    $(this).removeClass('selected');
		    jQuery("#_loadResident").prop('disabled', true);

		    jQuery('a[id^="_load"]').attr('disabled', true);

		    jQuery("#_hScoreGoal").text('--/--');
		    jQuery("#_mmScoreGoal").text('--/--');
		    jQuery("#_empScoreGoal").text('--/--');
		    jQuery("#_eduScoreGoal").text('--/--');
		    jQuery("#_nsScoreGoal").text('--/--');
		    jQuery("#_hhScoreGoal").text('--/--');
		} else {
		    jQuery('a[id^="_load"]').attr('disabled', false);
		    jQuery("#_resId").val(currentRow.residentId);
		    jQuery("#_loadResident").prop('disabled', false);

		    table.$('tr.selected').removeClass('selected');
		    $(this).addClass('selected');

		    /*
		     * Following code builds hyperlink for each
		     * Assessment buttons when a row is clicked in all
		     * Resident Table
		     */
		    var suffix = '&residentId=' + currentRow.residentId;
		    var assessmentLinks = jQuery('a[id^="_load"]');

		    jQuery.each(assessmentLinks, function(idx, obj) {
			var currHref = jQuery(obj).attr('href');
			var prefix = currHref.split('&');
			jQuery(obj).attr('href', prefix[0] + suffix);
		    });
		    
		    

		    /*
		     * Following code populates score and goal once a
		     * row a clicked
		     */
		    jQuery.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/getAllLatestScoreGoal?residentId=" + currentRow.residentId,
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(data) {
			    debugger;
			    jQuery("#_hScoreGoal").text(data["HOUSING"]);
			    jQuery("#_mmScoreGoal").text(data["MONEY MANAGEMENT"]);
			    jQuery("#_empScoreGoal").text(data["EMPLOYMENT"]);
			    jQuery("#_eduScoreGoal").text(data["EDUCATION"]);
			    jQuery("#_nsScoreGoal").text(data["NETWORK SUPPORT"]);
			    jQuery("#_hhScoreGoal").text(data["HOUSEHOLD MANAGEMENT"]);
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

});

