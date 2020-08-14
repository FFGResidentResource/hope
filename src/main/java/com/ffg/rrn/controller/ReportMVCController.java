package com.ffg.rrn.controller;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.AssessmentReportBuilder;
import com.ffg.rrn.report.IReport;
import com.ffg.rrn.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.security.Principal;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/reportrequest")
public class ReportMVCController extends BaseController{

    @RequestMapping(method = RequestMethod.GET)
    public String getPropertyList(Model model, Principal principal){
        // (1) (en)
        // After user login successfully.
        String serviceCoord = null;
        if (principal != null) {
            serviceCoord = populateSCinModel(model, principal);
        }
        List<Property> propertyList = new Report().getAllProperty();

        model.addAttribute("propertyList", propertyList);
        model.addAttribute("principal", principal);


        return "reportrequest";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String loadPropertyList(Model model, Principal principal){
        String serviceCoord = null;
        if (principal != null) {
            serviceCoord = populateSCinModel(model, principal);
        }
        List<Property> propertyList = new Report().getAllProperty();
        model.addAttribute("propertyList", propertyList);
        return "quarterly";
    }

    @RequestMapping("/{propertyId}")
    public String getDemographicDataList(@PathVariable("propertyId")Long propertyId, Model model){

        model.addAttribute("GenderData", new AssessmentReportBuilder().genderData(propertyId));
        model.addAttribute("EthnicityData", new AssessmentReportBuilder().ethnicityData(propertyId));
        model.addAttribute("RaceData", new AssessmentReportBuilder().raceData(propertyId));
        model.addAttribute("HeadOfHousehold", new AssessmentReportBuilder().headOfHouseholdData(propertyId));
        model.addAttribute("VeteranData", new AssessmentReportBuilder().veteranData(propertyId));
        model.addAttribute("DisabilityData", new AssessmentReportBuilder().disabilityData(propertyId));
        model.addAttribute("ReturningCitizenData", new AssessmentReportBuilder().returningCitizenData(propertyId));
        model.addAttribute("SNAPData", new AssessmentReportBuilder().SNAPData(propertyId));
        model.addAttribute("SSIData", new AssessmentReportBuilder().SSIData(propertyId));
        model.addAttribute("SSDIData", new AssessmentReportBuilder().SSDIData(propertyId));
        model.addAttribute("HealthData", new AssessmentReportBuilder().healthCoverageData(propertyId));
        model.addAttribute("EducationData", new AssessmentReportBuilder().educationLevelData(propertyId));
        model.addAttribute("IncomeData", new AssessmentReportBuilder().incomeData(propertyId));
        model.addAttribute("HomeSafetyData", new AssessmentReportBuilder().homeSafetyData(propertyId));
        model.addAttribute("LanguageData", new AssessmentReportBuilder().languageData(propertyId));
        model.addAttribute("AgeRangeData", new AssessmentReportBuilder().ageRangeData(propertyId));

        return "forward:/reportrequest";
    }


}

