package com.ffg.rrn.report.filter;

import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.model.Property;

import java.util.List;

public interface Report {

    public List<Property> getAllProperty();
    public List<Demographics> getAllDemographicObjects();

}
