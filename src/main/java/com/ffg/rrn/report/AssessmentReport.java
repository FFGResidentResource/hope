package com.ffg.rrn.report;

import java.util.*;

public class AssessmentReport extends AssessmentReportBuilder {


    private List<String> category = new ArrayList<>();
    private List<Long> data = new ArrayList<>();

    public class MergeListsToMap<K, V> implements Iterable<Map.Entry<K, V>> {
        private final List<K> keys;
        private final List<V> values;
        private int baseValue = 0;

        public MergeListsToMap(List<K> keys, List<V> values) {
            // do all the validations here
            this.keys = keys;
            this.values = values;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new Iterator<Map.Entry<K, V>>() {
                @Override
                public boolean hasNext() {
                    return keys.size() > baseValue;
                }

                @Override
                public Map.Entry<K, V> next() {
                    Map.Entry<K, V> e = new AbstractMap.SimpleEntry<>(keys.get(baseValue), values.get(baseValue));
                    baseValue += 1;
                    return e;
                }
            };
        }

    }

    public Map<String, Long> genderData(Long propertyId){
        category = getGenderList(propertyId);
        data = getGenderDistribution(propertyId);
        Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
        for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> ethnicityData(Long propertyId){
        category = getEthnicityList(propertyId);
        data = getEthnicityDistribution(propertyId);
        Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
        for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> raceData(Long propertyId){
        category = getRaceDistributionList(propertyId);
        data = getRaceDistributionFrequency(propertyId);
        Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
        for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }

    public Map<String, Long> headOfHouseholdData(Long propertyId){
        category = getHeadOfHousehold(propertyId);
        data = getHeadOfHouseholdDistribution(propertyId);
        Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
        for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
            dataMap.put(e.getKey(), e.getValue());
        }
        return dataMap;
    }
     public Map<String, Long> veteranData(Long propertyId){
        category = getVeteranStatus(propertyId);
        data = getVeteranStatusDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     public Map<String, Long> disabilityData(Long propertyId){
        category = getDisabilityStatus(propertyId);
        data = getDisabilityDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     public Map<String, Long> returningCitizenData(Long propertyId){
        category = getReturningCitizenList(propertyId);
        data = getReturningCitizenDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     public Map<String, Long> SNAPData(Long propertyId){
        category = getSNAPList(propertyId);
        data = getSNAPDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> SSILData(Long propertyId){
        category = getSSIList(propertyId);
        data = getSSIDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> SSDIData(Long propertyId){
        category = getSSDIList(propertyId);
        data = getSSDIDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> healthCoverageData(Long propertyId){
        category = getHealthCoverageList(propertyId);
        data = getHealthCoverageDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> educationLevelData(Long propertyId){
        category = getEducationLevel(propertyId);
        data = getEducationLevelDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> incomeData(Long propertyId){
        category = getHouseHoldIncome(propertyId);
        data = getHouseHoldIncomeDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> homeSafetyData(Long propertyId) {
        category = getHomeSafetyList(propertyId);
        data = getHomeSafetyDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> languageData(Long propertyId){
        category = getLanguageList(propertyId);
        data = getLanguageDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }

     Map<String, Long> ageRangeData(Long propertyId) {
        category = getAgeRange(propertyId);
        data = getAgeRangeDistribution(propertyId);
         Map<String, Long> dataMap = new LinkedHashMap<>(category.size());
         for(Map.Entry<String, Long> e : new MergeListsToMap<>(category, data)){
             dataMap.put(e.getKey(), e.getValue());
         }
         return dataMap;
     }


}