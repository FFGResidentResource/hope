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
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/reportrequest")
public class ReportMVCController {

    @RequestMapping(method = RequestMethod.GET)
    public String getPropertyList(Model model){
        List<Property> propertyList = new Report().getAllProperty();
        model.addAttribute("propertyList", propertyList);

        return "reportrequest";
    }

    @GetMapping("/{propertyId}")
    public ModelAndView getDemographicDataList(@PathVariable("propertyId")Long propertyId, ModelAndView model){

        model.addObject("GenderData", new AssessmentReportBuilder().genderData(propertyId));
        model.addObject("EthnicityData", new AssessmentReportBuilder().ethnicityData(propertyId));
        model.addObject("RaceData", new AssessmentReportBuilder().raceData(propertyId));
        model.addObject("HeadOfHousehold", new AssessmentReportBuilder().headOfHouseholdData(propertyId));
        model.addObject("VeteranData", new AssessmentReportBuilder().veteranData(propertyId));
        model.addObject("DisabilityData", new AssessmentReportBuilder().disabilityData(propertyId));
        model.addObject("ReturningCitizenData", new AssessmentReportBuilder().returningCitizenData(propertyId));
        model.addObject("SNAPData", new AssessmentReportBuilder().SNAPData(propertyId));
        model.addObject("SSIData", new AssessmentReportBuilder().SSIData(propertyId));
        model.addObject("SSDIData", new AssessmentReportBuilder().SSDIData(propertyId));
        model.addObject("HealthData", new AssessmentReportBuilder().healthCoverageData(propertyId));
        model.addObject("EducationData", new AssessmentReportBuilder().educationLevelData(propertyId));
        model.addObject("IncomeData", new AssessmentReportBuilder().incomeData(propertyId));
        model.addObject("HomeSafetyData", new AssessmentReportBuilder().homeSafetyData(propertyId));
        model.addObject("LanguageData", new AssessmentReportBuilder().languageData(propertyId));
        model.addObject("AgeRangeData", new AssessmentReportBuilder().ageRangeData(propertyId));
        model.setViewName("reportrequest");
        return model;
    }


}

