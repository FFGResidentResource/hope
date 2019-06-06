jQuery(document).ready(function() {

    var now = new Date();
    var year = now.getFullYear();
    var ddl = document.getElementById("ddlYear");
    for (var y = 0; y < 5; y++) {
	var opt = document.createElement("option");
	opt.value = (year - y).toString();
	opt.innerText = (year - y).toString();
	ddl.appendChild(opt);
    }

    var quarter = Math.floor((now.getMonth() + 3) / 3);
    jQuery("#ddlQuarter").val(quarter);
    
    jQuery("#_yearHeading").text(jQuery("#ddlYear").val());   

});


function pullDashboard(){    
   
    var checkString = '';    
    $(':checkbox').each(function() {
	checkString = checkString + '{"propertyId":"' +this.value +'","checked":"' + this.checked + '"},';
    });
    
    jQuery("#_yearHeading").text(jQuery("#ddlYear").val());
    
    checkString = checkString.substring(0, checkString.length - 1);    
    var dash = '{"year":"'+jQuery("#ddlYear").val()+ '",' +'"quarter":"' +jQuery("#ddlQuarter").val()+ '",' +'"properties": [ '+checkString+' ]}';
    
    jQuery.ajax({
	type : "POST",
	contentType: "application/json; charset=utf-8",
	url : "/pullDashboard",
	data: dash,
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {
	    
	    jQuery("#_unitsTotalQ1").text(data.q1Units);
	    jQuery("#_unitsTotalQ2").text(data.q2Units);
	    jQuery("#_unitsTotalQ3").text(data.q3Units);
	    jQuery("#_unitsTotalQ4").text(data.q4Units);
	    
	    jQuery('#_unitsTotalYTD').text(data.q1Units + data.q2Units + data.q3Units + data.q4Units);
	    
	    jQuery("#_resTotalQ1").text(data.q1Residents);
	    jQuery("#_resTotalQ2").text(data.q2Residents);
	    jQuery("#_resTotalQ3").text(data.q3Residents);
	    jQuery("#_resTotalQ4").text(data.q4Residents);
	    
	    jQuery('#_resTotalYTD').text(data.q1Residents + data.q2Residents + data.q3Residents + data.q4Residents);
	    
	    jQuery("#_signupQ1").text(data.q1SignUpComplete);
	    jQuery("#_signupQ2").text(data.q2SignUpComplete);
	    jQuery("#_signupQ3").text(data.q3SignUpComplete);
	    jQuery("#_signupQ4").text(data.q4SignUpComplete);
	    
	    jQuery('#_signupYTD').text(data.q1SignUpComplete + data.q2SignUpComplete + data.q3SignUpComplete + data.q4SignUpComplete);
	    
	    jQuery("#_ssmQ1").text(data.q1AssessmentComplete);
	    jQuery("#_ssmQ2").text(data.q2AssessmentComplete);
	    jQuery("#_ssmQ3").text(data.q3AssessmentComplete);
	    jQuery("#_ssmQ4").text(data.q4AssessmentComplete);
	    
	    jQuery('#_ssmYTD').text(data.q1AssessmentComplete + data.q2AssessmentComplete + data.q3AssessmentComplete + data.q4AssessmentComplete);
	    
	    
	    jQuery("#_rr1Q1").text(data.rr1q1Count);
	    jQuery("#_rr1Q2").text(data.rr1q2Count);
	    jQuery("#_rr1Q3").text(data.rr1q3Count);
	    jQuery("#_rr1Q4").text(data.rr1q4Count);	    
	    jQuery('#_rr1YTD').text(data.rr1q1Count + data.rr1q2Count + data.rr1q3Count + data.rr1q4Count);
	    
	    
	    jQuery("#_rr2Q1").text(data.rr2q1Count);
	    jQuery("#_rr2Q2").text(data.rr2q2Count);
	    jQuery("#_rr2Q3").text(data.rr2q3Count);
	    jQuery("#_rr2Q4").text(data.rr2q4Count);	    
	    jQuery('#_rr2YTD').text(data.rr2q1Count + data.rr2q2Count + data.rr2q3Count + data.rr2q4Count);
	    
	    jQuery("#_rr3Q1").text(data.rr3q1Count);
	    jQuery("#_rr3Q2").text(data.rr3q2Count);
	    jQuery("#_rr3Q3").text(data.rr3q3Count);
	    jQuery("#_rr3Q4").text(data.rr3q4Count);	    
	    jQuery('#_rr3YTD').text(data.rr3q1Count + data.rr3q2Count + data.rr3q3Count + data.rr3q4Count);
	    
	    jQuery("#_rr4Q1").text(data.rr4q1Count);
	    jQuery("#_rr4Q2").text(data.rr4q2Count);
	    jQuery("#_rr4Q3").text(data.rr4q3Count);
	    jQuery("#_rr4Q4").text(data.rr4q4Count);	    
	    jQuery('#_rr4YTD').text(data.rr4q1Count + data.rr4q2Count + data.rr4q3Count + data.rr4q4Count);
	    
	    jQuery("#_rr5Q1").text(data.rr5q1Count);
	    jQuery("#_rr5Q2").text(data.rr5q2Count);
	    jQuery("#_rr5Q3").text(data.rr5q3Count);
	    jQuery("#_rr5Q4").text(data.rr5q4Count);	    
	    jQuery('#_rr5YTD').text(data.rr5q1Count + data.rr5q2Count + data.rr5q3Count + data.rr5q4Count);
	    
	    jQuery("#_rr6Q1").text(data.rr6q1Count);
	    jQuery("#_rr6Q2").text(data.rr6q2Count);
	    jQuery("#_rr6Q3").text(data.rr6q3Count);
	    jQuery("#_rr6Q4").text(data.rr6q4Count);	    
	    jQuery('#_rr6YTD').text(data.rr6q1Count + data.rr1q2Count + data.rr6q3Count + data.rr6q4Count);
	    
	    jQuery("#_rr7Q1").text(data.rr7q1Count);
	    jQuery("#_rr7Q2").text(data.rr7q2Count);
	    jQuery("#_rr7Q3").text(data.rr7q3Count);
	    jQuery("#_rr7Q4").text(data.rr7q4Count);	    
	    jQuery('#_rr7YTD').text(data.rr7q1Count + data.rr7q2Count + data.rr7q3Count + data.rr7q4Count);
	    
	    jQuery("#_rr8Q1").text(data.rr8q1Count);
	    jQuery("#_rr8Q2").text(data.rr8q2Count);
	    jQuery("#_rr8Q3").text(data.rr8q3Count);
	    jQuery("#_rr8Q4").text(data.rr8q4Count);	    
	    jQuery('#_rr8YTD').text(data.rr8q1Count + data.rr8q2Count + data.rr8q3Count + data.rr8q4Count);
	    
	    
	    jQuery("#_rr9Q1").text(data.rr9q1Count);
	    jQuery("#_rr9Q2").text(data.rr9q2Count);
	    jQuery("#_rr9Q3").text(data.rr9q3Count);
	    jQuery("#_rr9Q4").text(data.rr9q4Count);	    
	    jQuery('#_rr9YTD').text(data.rr9q1Count + data.rr9q2Count + data.rr9q3Count + data.rr9q4Count);
	    
	    jQuery("#_rr10Q1").text(data.rr10q1Count);
	    jQuery("#_rr10Q2").text(data.rr10q2Count);
	    jQuery("#_rr10Q3").text(data.rr10q3Count);
	    jQuery("#_rr10Q4").text(data.rr10q4Count);	    
	    jQuery('#_rr10YTD').text(data.rr10q1Count + data.rr10q2Count + data.rr10q3Count + data.rr10q4Count);
	    
	    jQuery("#_rr11Q1").text(data.rr11q1Count);
	    jQuery("#_rr11Q2").text(data.rr11q2Count);
	    jQuery("#_rr11Q3").text(data.rr11q3Count);
	    jQuery("#_rr11Q4").text(data.rr11q4Count);	    
	    jQuery('#_rr11YTD').text(data.rr11q1Count + data.rr11q2Count + data.rr11q3Count + data.rr11q4Count);
	    
	    jQuery("#_rr12Q1").text(data.rr12q1Count);
	    jQuery("#_rr12Q2").text(data.rr12q2Count);
	    jQuery("#_rr12Q3").text(data.rr12q3Count);
	    jQuery("#_rr12Q4").text(data.rr12q4Count);	    
	    jQuery('#_rr12YTD').text(data.rr12q1Count + data.rr12q2Count + data.rr12q3Count + data.rr12q4Count);
	    
	    jQuery("#_rr13Q1").text(data.rr13q1Count);
	    jQuery("#_rr13Q2").text(data.rr13q2Count);
	    jQuery("#_rr13Q3").text(data.rr13q3Count);
	    jQuery("#_rr13Q4").text(data.rr13q4Count);	    
	    jQuery('#_rr13YTD').text(data.rr13q1Count + data.rr13q2Count + data.rr13q3Count + data.rr13q4Count);
	    
	    jQuery("#_rr14Q1").text(data.rr14q1Count);
	    jQuery("#_rr14Q2").text(data.rr14q2Count);
	    jQuery("#_rr14Q3").text(data.rr14q3Count);
	    jQuery("#_rr14Q4").text(data.rr14q4Count);	    
	    jQuery('#_rr14YTD').text(data.rr14q1Count + data.rr14q2Count + data.rr14q3Count + data.rr14q4Count);
	    
	    jQuery("#_housingQ1").text(data.housingQ1Count);
	    jQuery("#_housingQ2").text(data.housingQ2Count);
	    jQuery("#_housingQ3").text(data.housingQ3Count);
	    jQuery("#_housingQ4").text(data.housingQ4Count);
	    jQuery('#_housingYTD').text(data.housingQ1Count + data.housingQ2Count + data.housingQ3Count + data.housingQ4Count);
	    
	    jQuery("#_mmQ1").text(data.mmQ1Count);
	    jQuery("#_mmQ2").text(data.mmQ2Count);
	    jQuery("#_mmQ3").text(data.mmQ3Count);
	    jQuery("#_mmQ4").text(data.mmQ4Count);
	    jQuery('#_mmYTD').text(data.mmQ1Count + data.mmQ2Count + data.mmQ3Count + data.mmQ4Count);
	    
	    jQuery("#_empQ1").text(data.empQ1Count);
	    jQuery("#_empQ2").text(data.empQ2Count);
	    jQuery("#_empQ3").text(data.empQ3Count);
	    jQuery("#_empQ4").text(data.empQ4Count);
	    jQuery('#_empYTD').text(data.empQ1Count + data.empQ2Count + data.empQ3Count + data.empQ4Count);
	    
	    jQuery("#_eduQ1").text(data.eduQ1Count);
	    jQuery("#_eduQ2").text(data.eduQ2Count);
	    jQuery("#_eduQ3").text(data.eduQ3Count);
	    jQuery("#_eduQ4").text(data.eduQ4Count);
	    jQuery('#_eduYTD').text(data.eduQ1Count + data.eduQ2Count + data.eduQ3Count + data.eduQ4Count);
	    
	    jQuery("#_nsQ1").text(data.nsQ1Count);
	    jQuery("#_nsQ2").text(data.nsQ2Count);
	    jQuery("#_nsQ3").text(data.nsQ3Count);
	    jQuery("#_nsQ4").text(data.nsQ4Count);
	    jQuery('#_nsYTD').text(data.nsQ1Count + data.nsQ2Count + data.nsQ3Count + data.nsQ4Count);
	    
	    jQuery("#_hhQ1").text(data.hhQ1Count);
	    jQuery("#_hhQ2").text(data.hhQ2Count);
	    jQuery("#_hhQ3").text(data.hhQ3Count);
	    jQuery("#_hhQ4").text(data.hhQ4Count);
	    jQuery('#_hhYTD').text(data.hhQ1Count + data.hhQ2Count + data.hhQ3Count + data.hhQ4Count);
	    
	    
	    jQuery('#_totalResidents').text(data.totalActiveResident)
	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });
    
}
