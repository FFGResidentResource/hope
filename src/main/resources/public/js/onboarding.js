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
			return moment(row.dateAdded).format("MM/DD/YY hh:mm A");
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

	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });

});

