package com.ffg.rrn.report;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssessmentReportBuilder extends Report{



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



    /*
    This is to move most of the processing to the backend, so that question Ids will be hard-coded for now.
    Assessment Report will extract information from Report.class. This will include categories include
    Demographic information, Education, Health and Safety.
     */

    /*define placeholders for temporary datasets*/
    private ArrayList<Collection> keys = new ArrayList<>();
    private ArrayList<Collection> values = new ArrayList<>();
    private Map<String, Long> map = new LinkedHashMap<String, Long>();
    private List<String> list;
    //private List<Long> data;

    private List<String> getGenderList(Long propertyId){

        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
                keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getGenderDistribution(Long propertyId) {

        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long) 1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()) {
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }


    private List<String> getEthnicityList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount((long)propertyId, (long)2);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getEthnicityDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)2);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }

    private List<String> getRaceDistributionList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)3);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getRaceDistributionFrequency(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)3);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getHeadOfHousehold(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)4);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getHeadOfHouseholdDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)4);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getVeteranStatus(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)5);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getVeteranStatusDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)5);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());

    }
    private List<String> getDisabilityStatus(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)6);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getDisabilityDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)6);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getReturningCitizenList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)7);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getReturningCitizenDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)7);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getSNAPList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)8);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getSNAPDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)8);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getSSIList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)9);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getSSIDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)9);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getSSDIList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)10);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getSSDIDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)10);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getHealthCoverageList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)11);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getHealthCoverageDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)11);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getEducationLevel(Long propertyId){
         Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)12);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getEducationLevelDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)12);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getHouseHoldIncome(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)13);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getHouseHoldIncomeDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)13);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getHomeSafetyList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)14);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getHomeSafetyDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)14);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getLanguageList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)15);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getLanguageDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)15);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    private List<String> getAgeRange(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)16);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    private List<Long> getAgeRangeDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)16);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }

}
