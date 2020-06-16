package com.ffg.rrn.report.filter;
import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.service.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentReport implements Report {

    DemographicsDAO demographicsDAO;

    @Autowired
    private ResidentServiceImpl residentService;


    public AssessmentReport(){}

    @Override
    public List<Property> getAllProperty() {

        List<Property> propertyList = demographicsDAO.getAllPropertyObjects();

        return propertyList;
    }

    @Override
    public List<Demographics> getAllDemographicObjects() {
        List<Demographics> demographicsList = demographicsDAO.getAllDemographicObjects();

        return demographicsList;
    }

    public List<String> getLanguagesSpokenAtHome(){

        return demographicsDAO.getPrimaryLanguagesSpokenAtHome()
    }
}
