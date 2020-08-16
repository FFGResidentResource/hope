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
