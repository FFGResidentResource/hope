package com.ffg.rrn.report;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssessmentReportBuilder extends Report{



    private List<String> category = new ArrayList<>();
    private List<Long> data = new ArrayList<>();
    private Map<String, Long> dataMap = new LinkedHashMap<>(category.size());


    public Map<String, Long> genderData(Long propertyId) {
        category = getGenderList(propertyId);
        data = getGenderDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> ethnicityData(Long propertyId) {
        category = getEthnicityList(propertyId);
        data = getEthnicityDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> raceData(Long propertyId) {
        category = getRaceDistributionList(propertyId);
        data = getRaceDistributionFrequency(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> headOfHouseholdData(Long propertyId) {
        category = getHeadOfHousehold(propertyId);
        data = getHeadOfHouseholdDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> veteranData(Long propertyId) {
        category = getVeteranStatus(propertyId);
        data = getVeteranStatusDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> disabilityData(Long propertyId) {
        category = getDisabilityStatus(propertyId);
        data = getDisabilityDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> returningCitizenData(Long propertyId) {
        category = getReturningCitizenList(propertyId);
        data = getReturningCitizenDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> SNAPData(Long propertyId) {
        category = getSNAPList(propertyId);
        data = getSNAPDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> SSIData(Long propertyId) {
        category = getSSIList(propertyId);
        data = getSSIDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> SSDIData(Long propertyId) {
        category = getSSDIList(propertyId);
        data = getSSDIDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> healthCoverageData(Long propertyId) {
        category = getHealthCoverageList(propertyId);
        data = getHealthCoverageDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> educationLevelData(Long propertyId) {
        category = getEducationLevel(propertyId);
        data = getEducationLevelDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> incomeData(Long propertyId) {
        category = getHouseHoldIncome(propertyId);
        data = getHouseHoldIncomeDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> homeSafetyData(Long propertyId) {
        category = getHomeSafetyList(propertyId);
        data = getHomeSafetyDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> languageData(Long propertyId) {
        category = getLanguageList(propertyId);
        data = getLanguageDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> ageRangeData(Long propertyId) {
        category = getAgeRange(propertyId);
        data = getAgeRangeDistribution(propertyId);
        for (Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)) {
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }


}
