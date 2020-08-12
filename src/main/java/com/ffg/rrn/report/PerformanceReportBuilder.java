package com.ffg.rrn.report;

import com.ffg.rrn.model.Property;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PerformanceReportBuilder extends Report {

    private List<String> category = new ArrayList<>();
    private List<Long> data = new ArrayList<>();
    private Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
    public List<Integer> propertyIdList = getAllProperty().stream().map(Property::getPropertyId).collect(Collectors.toList());
    private List<Map<String, Long>> allDemoData = new LinkedList<>();



    public List<String> propertyNameList = getAllProperty().stream().map(Property::getPropertyName).collect(Collectors.toList());
    public List<Property> propertyList = getAllProperty();

    public List<Map<String, Long>> getAllGenderData(){
      for (Integer propId : propertyIdList){
          allDemoData.add(new AssessmentReportBuilder().genderData(Long.valueOf(propId)));
      }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllEthnicitiesData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().ethnicityData(Long.valueOf(propId)));
        }
        return allDemoData;
    }

    public List<Map<String, Long>> getAllRaceData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().raceData(Long.valueOf(propId)));
        }
        return allDemoData;
    }

    public List<Map<String, Long>> getAllHeadOfHouseholdData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().headOfHouseholdData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllVeteranData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().veteranData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllDisabilityData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().disabilityData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllReturningCitizenData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().returningCitizenData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllSNAPData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().SNAPData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllSSIData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().SSIData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllSSDIData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().SSDIData(Long.valueOf(propId)));
        }
        return allDemoData;
    }

    public List<Map<String, Long>> getAllHealthCoverageData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().healthCoverageData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllEducationalData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().educationLevelData(Long.valueOf(propId)));
        }
        return allDemoData;
    }

    public List<Map<String, Long>> getAllIncomeData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().incomeData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllHomeSafetyData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().homeSafetyData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllLanguageData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().languageData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
    public List<Map<String, Long>> getAllAgeData(){
        for (Integer propId : propertyIdList){
            allDemoData.add(new AssessmentReportBuilder().ageRangeData(Long.valueOf(propId)));
        }
        return allDemoData;
    }
}