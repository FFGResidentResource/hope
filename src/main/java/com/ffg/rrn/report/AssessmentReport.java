package com.ffg.rrn.report;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AssessmentReport extends Report{

    /*
    This is to move most of the processing to the backend, so that question Ids will be hard-coded for now.
    Assessment Report will extract information from Report.class. This will include categories include
    Demographic information, Education, Health and Safety.
     */

    ArrayList<Collection> keys = new ArrayList<>();
    ArrayList<Collection> values = new ArrayList<>();

    public List<String> getGenderList(Integer propertyId){

        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
                keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getGenderDistribution(Integer propertyId){

        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)1);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }


    public List<String> getEthnicityList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)2);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getEthnicityDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)2);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }

    public List<String> getRaceDistributionList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)3);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getRaceDistributionFrequency(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)3);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHeadOfHousehold(Integer propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)4);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHeadOfHouseholdDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)4);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getVeteranStatus(Integer propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)5);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getVeteranStatusDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)5);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());

    }   public List<String> getDisabilityStatus(Integer propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)6);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getDisabilityDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)6);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getReturningCitizenList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)7);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getReturningCitizenDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)7);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getSNAPList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)8);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getSNAPDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)8);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getSSIList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)9);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getSSIDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)9);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getSSDIList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)10);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getSSDIDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)10);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHealthCoverageList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)11);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHealthCoverageDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)11);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getEducationLevel(Integer propertyId){
         Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)12);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getEducationLevelDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)12);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHouseHoldIncome(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)13);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHouseHoldIncomeDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)13);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getHomeSafetyList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)14);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getHomeSafetyDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)14);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getLanguageList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)15);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getLanguageDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)15);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    public List<String> getAgeRange(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)16);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    public List<Long> getAgeRangeDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)16);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }

}
