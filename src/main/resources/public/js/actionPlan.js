jQuery(document).ready(
	function() {   

	    jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/getAllLatestScoreGoal?residentId=" +jQuery('#_residentIdText').text(),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {
		    
		    debugger;
		},
		error : function(e) {
		    console.log("ERROR : ", e);
		}
	    });
});