/**
 *
 */
package com.ffg.rrn.model;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author FFGRRNTeam
 *
 */

@Data
@JsonView
public class Property {

    private int propertyId;
    private String propertyName;
    private int unit;
    private int unitFee;
    private Boolean active;
    private int noOfResident;
    private Boolean residentCouncil;

    private String city;
    private String state;
    private String county;

    private String serviceProvider;

    private Boolean checked;

    // DEBUG @JsonView
    private List<Property> propertyList;
    private List<String> serviceProviders;

    public Property() {
        propertyList = new ArrayList<Property>();
    }

    public Property(int propertyId, String propertyName, int unit, int unitFee,
                    Boolean active, int noOfResident, Boolean residentCouncil,
                    String city, String state, String county,
                    String serviceProvider, Boolean checked) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.unit = unit;
        this.unitFee = unitFee;
        this.active = active;
        this.noOfResident = noOfResident;
        this.residentCouncil = residentCouncil;
        this.city = city;
        this.state = state;
        this.county = county;
        this.serviceProvider = serviceProvider;
        this.checked = checked;
        this.serviceProviders =  new ArrayList<String>(Arrays.asList(
                "ABCAP", "FSA", "ILCAO", "NCR", "OTR", "RRN", "Sourcepoint", "UNK"
        ));
    }

    @Override
    public String toString() { return this.propertyName + " under edit"; }
}
