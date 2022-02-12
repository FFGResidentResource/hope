var table;
var currentRow;

jQuery(document).ready(
    function () {

        jQuery('a[id^="_load"]').attr('disabled', true);

        jQuery('input[id^="_prop_"]').prop('disabled', jQuery('#_isActive').prop('checked'));

        jQuery.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getAllProperties",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                table = jQuery('#propertyTable').DataTable(
                    {
                        "data": data,
                        "columns": [
                            {
                                data: 'active',
                                render: function (t, type, row) {
                                    if (row.active) {
                                        return 'ACTIVE';
                                    } else {
                                        return 'INACTIVE';
                                    }
                                }
                            },
                            {data: 'propertyName'},
                            {data: 'unit'},
                            {data: 'unitFee'},
                            {data: 'noOfResident'},
                            {
                                data: 'residentCouncil',
                                render: function (t, type, row) {
                                    if (row.residentCouncil) {
                                        return 'YES';
                                    } else {
                                        return 'NO';
                                    }
                                }
                            },
                            {data: 'city'},
                            {data: 'state'},
                            {data: 'county'}
                        ],
                        "order": [[3, "desc"]],
                        pageLength: 8,
                        pagingType: "full_numbers"
                    });

                jQuery('#propertyTable tbody').on('click', 'tr', function () {

                    var tr = $(this);
                    currentRow = table.row(this).data();

                    console.log(currentRow);

                    if ($(this).hasClass('selected')) {
                        $(this).removeClass('selected');
                        jQuery('a[id^="_load"]').attr('disabled', true);
                        jQuery('select').prop('selectedIndex', '');
                        jQuery('#inputPropertyId').val(currentRow.propertyId);
                        jQuery('input:text').val('');
                        jQuery('#inputPropertyName').val('');
                        jQuery('#inputNumberUnits').val(0);
                        jQuery('#inputUnitFee').val(0);
                        jQuery('#inputNumberResidents').val(0);
                        jQuery('#_isResidentCouncil').prop('checked', currentRow.residentCouncil);
                        jQuery("#_isActive").prop('checked', true);


                        jQuery('input[id^="_prop_"]').prop('checked', true);
                        jQuery('input[id^="_prop_"]').prop('disabled', false);

                        selectServiceProviderOption();
                    } else {
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');

                        jQuery('#inputPropertyId').val(currentRow.propertyId);
                        jQuery('input:text').val('');
                        jQuery('#inputPropertyName').val('');
                        jQuery('#inputNumberUnits').val(0);
                        jQuery('#inputUnitFee').val(0);
                        jQuery('#inputNumberResidents').val(0);
                        jQuery('#_isResidentCouncil').prop('checked', currentRow.residentCouncil);
                        jQuery("#_isActive").prop('checked', true);

                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');

                        jQuery("#inputPropertyName").val(currentRow.propertyName);
                        jQuery("#inputNumberUnits").val(currentRow.unit);
                        jQuery("#inputUnitFee").val(currentRow.unitFee);
                        jQuery("#inputNumberResidents").val(currentRow.noOfResident);
                        jQuery("#inputCity").val(currentRow.city);
                        jQuery("#inputState").val(currentRow.state);
                        jQuery("#inputCounty").val(currentRow.county);
                        jQuery("#inputServiceProvider").val(currentRow.serviceProvider);

                        selectServiceProviderOption();
                    }
                });
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    });


function activeCheck(that) {
    jQuery("input[id^='_prop_']").prop('checked', false);
    jQuery("input[id^='_prop_']").prop('disabled', jQuery(that).prop('checked'));
}

/**
 *
 * @returns
 */
function validateFields() {

    debugger;
}

function selectServiceProvider() {
    document.getElementById('inputServiceProvider').value =
        document.getElementById('cbServiceProvider').value;
}

function selectServiceProviderOption() {
    document.getElementById('cbServiceProvider').value =
        document.getElementById('inputServiceProvider').value;
}

function resetInputFields() {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');

        jQuery('a[id^="_load"]').attr('disabled', true);

        jQuery('select').prop('selectedIndex', '');
        jQuery('#inputPropertyId').val(currentRow.propertyId);
        jQuery('input:text').val('');
        jQuery('#inputPropertyName').val('');
        jQuery('#inputNumberUnits').val(0);
        jQuery('#inputUnitFee').val(0);
        jQuery('#inputNumberResidents').val(0);
        jQuery('#_isResidentCouncil').prop('checked', currentRow.residentCouncil);
        jQuery("#_isActive").prop('checked', true);

        jQuery('input[id^="_prop_"]').prop('checked', true);
        jQuery('input[id^="_prop_"]').prop('disabled', false);
}
