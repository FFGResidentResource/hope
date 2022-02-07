/**
 *
 */
package com.ffg.rrn.model;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

import java.util.ArrayList;
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

    private Boolean checked;

    // DEBUG @JsonView
    private List<Property> propertyList;

    public Property() {
        propertyList = new ArrayList<Property>();
    }

    public Property(int propertyId, String propertyName, int unit, int unitFee, Boolean active, int noOfResident, Boolean residentCouncil, String city, String state, String county, Boolean checked) {
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
        this.checked = checked;
    }

    @Override
    public String toString() { return this.propertyName + " under edit"; }
}
