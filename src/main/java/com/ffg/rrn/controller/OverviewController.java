package com.ffg.rrn.controller;


import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.PerformanceReportBuilder;
import com.ffg.rrn.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class OverviewController extends BaseController {

    @Autowired
    PerformanceReportBuilder performanceReportBuilder;

    @RequestMapping(value = "/overview" , method = RequestMethod.GET)
        public ModelAndView getOverview(ModelAndView model){
        List<Property> propertyList = new Report().getAllProperty();
        model.addObject("propertyList", propertyList);
        model.addObject("propertyNameList", performanceReportBuilder.propertyNameList);
//        model.addObject("gender", performanceReportBuilder.getAllGenderData());
//        model.addObject("ethnicity", performanceReportBuilder.getAllEthnicitiesData());
//        model.addObject("race", performanceReportBuilder.getAllRaceData());
//        model.addObject("hoh", performanceReportBuilder.getAllHeadOfHouseholdData());
//        model.addObject("veteran", performanceReportBuilder.getAllVeteranData());
//        model.addObject("disability", performanceReportBuilder.getAllDisabilityData());
//        model.addObject("ex-offender", performanceReportBuilder.getAllReturningCitizenData());
//        model.addObject("snap", performanceReportBuilder.getAllSNAPData());
//        model.addObject("ssi", performanceReportBuilder.getAllSSIData());
//        model.addObject("ssdi", performanceReportBuilder.getAllSSDIData());
//        model.addObject("health", performanceReportBuilder.getAllHealthCoverageData());
//        model.addObject("education", performanceReportBuilder.getAllEducationalData());
//        model.addObject("income", performanceReportBuilder.getAllIncomeData());
//        model.addObject("safety", performanceReportBuilder.getAllHomeSafetyData());
//        model.addObject("language", performanceReportBuilder.getAllLanguageData());
//        model.addObject("age", performanceReportBuilder.getAllAgeData());
        model.setViewName("overview");
        return model;
    }

    @RequestMapping(value = "/overview/gender", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showGenderInfoOverview(){
       return performanceReportBuilder.getAllGenderData();

    }

    @RequestMapping(value="/overview/ethnicity", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showEthnicityOverview(){
        return performanceReportBuilder.getAllEthnicitiesData();
    }

    @RequestMapping(value="/overview/race", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showRaceOverview(){
        return performanceReportBuilder.getAllRaceData();
    }

    @RequestMapping(value="/overview/household", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showHoHOverview(){
        return performanceReportBuilder.getAllHeadOfHouseholdData();
    }

    @RequestMapping(value="/overview/veteran", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showVeteranOverview(){
        return performanceReportBuilder.getAllVeteranData();
    }

    @RequestMapping(value="/overview/disability", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showDisabilityOverview(){
        return performanceReportBuilder.getAllDisabilityData();
    }

    @RequestMapping(value="/overview/returning", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showReturningCitizenOverview(){
        return performanceReportBuilder.getAllReturningCitizenData();
    }

    @RequestMapping(value="/overview/snap", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showSNAPOverview(){
        return performanceReportBuilder.getAllSNAPData();
    }

    @RequestMapping(value="/overview/ssi", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showSSIOverview(){
        return performanceReportBuilder.getAllSSIData();
    }

    @RequestMapping(value="/overview/ssdi", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showSSDIOverview(){
        return performanceReportBuilder.getAllSSDIData();
    }

    @RequestMapping(value="/overview/health", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showHealthCoverageOverview(){
       return performanceReportBuilder.getAllHealthCoverageData();
    }

    @RequestMapping(value="/overview/education", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showEducationOverview(){
        return performanceReportBuilder.getAllEducationalData();
    }

    @RequestMapping(value="/overview/income", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showHIncomeOverview(){
        return performanceReportBuilder.getAllIncomeData();
    }

    @RequestMapping(value="/overview/safety", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showSafetyInfoOverview(){
        return performanceReportBuilder.getAllHomeSafetyData();
    }

    @RequestMapping(value="/overview/language", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showLanguageOverview(){
        return performanceReportBuilder.getAllLanguageData();
    }

    @RequestMapping(value="/overview/age", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Long>> showAgeOverview() {
        return performanceReportBuilder.getAllAgeData();
    }

}
