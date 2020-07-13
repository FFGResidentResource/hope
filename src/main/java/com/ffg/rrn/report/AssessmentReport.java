package com.ffg.rrn.report;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssessmentReport extends Report{

    /*
    This is to move most of the processing to the backend, so that question Ids will be hard-coded for now.
    Assessment Report will extract information from Report.class. This will include categories include
    Demographic information, Education, Health and Safety.
     */

    ArrayList<Collection> keys = new ArrayList<>();
    ArrayList<Collection> values = new ArrayList<>();

    public Collection<String> getGenderList(Integer propertyId){

        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
                keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getGenderDistribution(Integer propertyId){

        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)1);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }

    public Collection<String> getEthnicityList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)2);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getEthnicityDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)2);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }

    public Collection<String> getRaceDistributionList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)3);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getRaceDistributionFrequency(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)3);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getHeadOfHousehold(Integer propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)4);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getHeadOfHouseholdDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)4);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getVeteranStatus(Integer propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)5);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getVeteranStatusDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)5);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }   public Collection<String> getDisabilityStatus(Integer propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)6);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getDisabilityDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)6);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getReturningCitizenList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)7);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getReturningCitizenDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)7);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getSNAPList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)8);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getSNAPDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)8);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getSSIList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)9);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getSSIDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)9);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getSSDIList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)10);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getSSDIDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)10);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getHealthCoverageList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)11);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getHealthCoverageDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)11);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getEducationLevel(Integer propertyId){
         Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)12);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getEducationLevelDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)12);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getHouseHoldIncome(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)13);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getHouseHoldIncomeDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)13);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getHomeSafetyList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)14);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getHomeSafetyDistribution(Integer propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)14);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getLanguageList(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)15);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getLanguageDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)15);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }
    public Collection<String> getAgeRange(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)16);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getLanguage.entrySet()){
            keys.add(entry.getKey());
        }
        return keys.get(0);
    }

    public Collection<Long> getAgeRangeDistribution(Integer propertyId){
        Map<Collection<String>, Collection<Long>> getLanguage = getAnswerOccurenceCount((long)propertyId, (long)16);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getLanguage.entrySet()){
            values.add(entry.getValue());
        }
        return values.get(0);
    }

}
