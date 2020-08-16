package com.ffg.rrn.controller;


import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.PerformanceReportBuilder;
import com.ffg.rrn.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/overview")
public class OverviewController extends BaseController {

    @Autowired
    PerformanceReportBuilder performanceReportBuilder;

    List<Property> propertyList = new Report().getAllProperty();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showOverview(ModelAndView model, Principal principal){
        model.addObject("propertyList", propertyList);
        model.addObject("principal", principal);
        model.addObject("propertyIdList", performanceReportBuilder.propertyIdList);
        model.addObject("propertyNameList", performanceReportBuilder.propertyNameList);
        model.setViewName("overview");
        return model;
    }

    @RequestMapping(value = "/gender", method = { RequestMethod.GET, RequestMethod.POST })
    public String showGenderInfoOverview(Model model){
        model.addAttribute("allGenderList", performanceReportBuilder.getAllGenderData());
        return "forward:/overview";
    }

    @RequestMapping(value="/ethnicity", method = { RequestMethod.GET, RequestMethod.POST })
    public String showEthnicityOverview(Model model){
        model.addAttribute("allEthnicityList", performanceReportBuilder.getAllEthnicitiesData());

        return "forward:/overview";
    }

    @RequestMapping(value="/race", method = { RequestMethod.GET, RequestMethod.POST })
    public String showRaceOverview(Model model){
        model.addAttribute("allRaceList", performanceReportBuilder.getAllRaceData());

        return "forward:/overview";
    }

    @RequestMapping(value="/household", method = { RequestMethod.GET, RequestMethod.POST })
    public String showHoHOverview(Model model){
        model.addAttribute("allHeadOfHouseholdData", performanceReportBuilder.getAllHeadOfHouseholdData());
        return "forward:/overview";
    }

    @RequestMapping(value="/veteran", method = { RequestMethod.GET, RequestMethod.POST })
    public String showVeteranOverview(Model model){
        model.addAttribute("allVeteranData", performanceReportBuilder.getAllVeteranData());
        return "forward:/overview";
    }

    @RequestMapping(value="/disability", method = { RequestMethod.GET, RequestMethod.POST })
    public String showDisabilityOverview(Model model){
        model.addAttribute("allDisabilityData", performanceReportBuilder.getAllDisabilityData());
        return "forward:/overview";
    }

    @RequestMapping(value="/returning", method = { RequestMethod.GET, RequestMethod.POST })
    public String showReturningCitizenOverview(Model model){
        model.addAttribute("allReturningCitizenData", performanceReportBuilder.getAllReturningCitizenData());
        return "forward:/overview";
    }

    @RequestMapping(value="/snap", method = { RequestMethod.GET, RequestMethod.POST })
    public String showSNAPOverview(Model model){
        model.addAttribute("allSNAPData", performanceReportBuilder.getAllSNAPData());
        return "forward:/overview";
    }

    @RequestMapping(value="/ssi", method = { RequestMethod.GET, RequestMethod.POST })
    public String showSSIOverview(Model model){
        model.addAttribute("allSSIData", performanceReportBuilder.getAllSSIData());
        return "forward:/overview";
    }

    @RequestMapping(value="/ssdi", method = { RequestMethod.GET, RequestMethod.POST })
    public String showSSDIOverview(Model model){
        model.addAttribute("allSSDIData", performanceReportBuilder.getAllSSDIData());
        return "forward:/overview";
    }

    @RequestMapping(value="/health", method = { RequestMethod.GET, RequestMethod.POST })
    public String showHealthCoverageOverview(Model model){
        model.addAttribute("allHealthCoverageData", performanceReportBuilder.getAllHealthCoverageData());
        return "forward:/overview";
    }

    @RequestMapping(value="/education", method = { RequestMethod.GET, RequestMethod.POST })
    public String showEducationOverview(Model model){
        model.addAttribute("allEducationalData", performanceReportBuilder.getAllEducationalData());
        return "forward:/overview";
    }

    @RequestMapping(value="/income", method = { RequestMethod.GET, RequestMethod.POST })
    public String showHIncomeOverview(Model model){
        model.addAttribute("allIncomeData", performanceReportBuilder.getAllIncomeData());
        return "forward:/overview";
    }

    @RequestMapping(value="/safety", method = { RequestMethod.GET, RequestMethod.POST })
    public String showSafetyInfoOverview(Model model){
        model.addAttribute("allHomeSafetyData", performanceReportBuilder.getAllHomeSafetyData());
        return "forward:/overview";
    }

    @RequestMapping(value="/language", method = { RequestMethod.GET, RequestMethod.POST })
    public String showLanguageOverview(Model model){
        model.addAttribute("allLanguageData", performanceReportBuilder.getAllLanguageData());
        return "forward:/overview";
    }

    @RequestMapping(value="/age", method = { RequestMethod.GET, RequestMethod.POST })
    public String showAgeOverview(Model model) {
        model.addAttribute("allAgeData", performanceReportBuilder.getAllAgeData());
        return "forward:/overview";
    }

}
