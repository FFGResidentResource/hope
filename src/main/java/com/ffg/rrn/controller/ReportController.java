package com.ffg.rrn.controller;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.AssessmentReport;
import com.ffg.rrn.report.GParams;
import com.ffg.rrn.report.Report;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/reportrequest")
public class ReportController {

    private GParams gParams;

    @GetMapping
    public String getPropertyList(@RequestParam(value="propertyId", required=false) Integer propertyId, Model model){
        List<Property> propertyList = new Report().getAllProperty();
        model.addAttribute("propertyList", propertyList);

        return "reportrequest";
    }

    @PostMapping
    public String displayChart(@RequestParam(value = "propertyId") Integer propertyId, @ModelAttribute Model model){
        model.addAttribute("Gender Data", gParams.genderDataset(propertyId));
        model.addAttribute("Ethnicity Data", gParams.ethnicityDataset(propertyId));
        model.addAttribute("Race Data", gParams.raceDataset(propertyId));
        model.addAttribute("Head of Household", gParams.householdDataset(propertyId));
        model.addAttribute("Veteran Status", gParams.veteranDataset(propertyId));
        model.addAttribute("Disability Data", gParams.disabilityDataset(propertyId));
        model.addAttribute("Returning Citizen", gParams.returningDataset(propertyId));
        model.addAttribute("SNAP Data", gParams.snapDataset(propertyId));
        model.addAttribute("SSI Data", gParams.ssiDataset(propertyId));
        model.addAttribute("SSDI Information", gParams.ssdiDataset(propertyId));
        model.addAttribute("Health Coverage", gParams.healthDataset(propertyId));
        model.addAttribute("Educational Level", gParams.educationDataset(propertyId));
        model.addAttribute("Home Safety", gParams.safetyDataset(propertyId));
        model.addAttribute("Primary Language", gParams.languageDataset(propertyId));
        model.addAttribute("Age Range", gParams.ageDataset(propertyId));
        return "reportrequest";
    }




}
