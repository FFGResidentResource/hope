package com.ffg.rrn.report;

import com.ffg.rrn.controller.BaseController;
import com.ffg.rrn.model.Demographics;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins="*")
public class PerformanceReportBuilder {

//    @GetMapping("/{propertyId}")
//    public List<Map<String, Long>> getDemographicDataList(@PathVariable("propertyId")Long propertyId){
//
//        List<Map<String, Long>> demoData = new LinkedList<>();
//        demoData.add(new AssessmentReportBuilder().genderData(propertyId));
//        demoData.add(new AssessmentReportBuilder().ethnicityData(propertyId));
//        demoData.add(new AssessmentReportBuilder().raceData(propertyId));
//        demoData.add(new AssessmentReportBuilder().headOfHouseholdData(propertyId));
//        demoData.add(new AssessmentReportBuilder().veteranData(propertyId));
//        demoData.add(new AssessmentReportBuilder().disabilityData(propertyId));
//        demoData.add(new AssessmentReportBuilder().returningCitizenData(propertyId));
//        demoData.add(new AssessmentReportBuilder().SNAPData(propertyId));
//        demoData.add(new AssessmentReportBuilder().SSIData(propertyId));
//        demoData.add(new AssessmentReportBuilder().SSDIData(propertyId));
//        demoData.add(new AssessmentReportBuilder().healthCoverageData(propertyId));
//        demoData.add(new AssessmentReportBuilder().educationLevelData(propertyId));
//        demoData.add(new AssessmentReportBuilder().incomeData(propertyId));
//        demoData.add(new AssessmentReportBuilder().homeSafetyData(propertyId));
//        demoData.add(new AssessmentReportBuilder().languageData(propertyId));
//        demoData.add(new AssessmentReportBuilder().ageRangeData(propertyId));
//
//        return demoData;
//    }
}