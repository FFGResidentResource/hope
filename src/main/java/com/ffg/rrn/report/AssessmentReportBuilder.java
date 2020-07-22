package com.ffg.rrn.report;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssessmentReportBuilder extends Report{

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
    private List<Long> data;

    public List<String> getGenderList(Long propertyId){

        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
                keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getGenderDistribution(Long propertyId) {

        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long) 1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()) {
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }


    public List<String> getEthnicityList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount((long)propertyId, (long)2);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getEthnicityDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)2);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }

    public List<String> getRaceDistributionList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)3);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getRaceDistributionFrequency(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)3);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHeadOfHousehold(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)4);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHeadOfHouseholdDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)4);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getVeteranStatus(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)5);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getVeteranStatusDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)5);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());

    }   public List<String> getDisabilityStatus(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)6);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getDisabilityDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)6);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getReturningCitizenList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)7);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getReturningCitizenDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)7);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getSNAPList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)8);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getSNAPDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)8);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getSSIList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)9);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getSSIDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)9);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getSSDIList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)10);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getSSDIDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)10);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHealthCoverageList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)11);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHealthCoverageDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)11);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getEducationLevel(Long propertyId){
         Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)12);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getEducationLevelDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)12);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHouseHoldIncome(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)13);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHouseHoldIncomeDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)13);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHomeSafetyList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)14);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHomeSafetyDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)14);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getLanguageList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)15);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getLanguageDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)15);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getAgeRange(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)16);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getAgeRangeDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)16);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }

}
