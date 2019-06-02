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
	    
	    jQuery("#_resTotalQ1").text(data.q1Residents);
	    jQuery("#_resTotalQ2").text(data.q2Residents);
	    jQuery("#_resTotalQ3").text(data.q3Residents);
	    jQuery("#_resTotalQ4").text(data.q4Residents);
	    
	    jQuery("#_signupQ1").text(data.q1SignUpComplete);
	    jQuery("#_signupQ2").text(data.q2SignUpComplete);
	    jQuery("#_signupQ3").text(data.q3SignUpComplete);
	    jQuery("#_signupQ4").text(data.q4SignUpComplete);
	    
	    
	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });
    
}
