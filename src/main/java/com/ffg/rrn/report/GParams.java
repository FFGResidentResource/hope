package com.ffg.rrn.report;

import org.springframework.security.core.parameters.P;
import sun.awt.image.ImageWatched;

import java.util.*;

public class GParams {

    public List<String> list;
    public List<Long> data;

    public Map<String, Long> genderDataset(Integer propertyId){
        Map<String, Long> genderDataset = new LinkedHashMap<>();
        list =  new AssessmentReport().getGenderList(propertyId);
        data =  new AssessmentReport().getGenderDistribution(propertyId);
        while(list.iterator().hasNext()){
            genderDataset.put(list.iterator().next(), data.iterator().next());
        }
        return genderDataset;
    }

    public Map<String, Long> ethnicityDataset(Integer propertyId){
        Map<String, Long> ethnicityDataset = new LinkedHashMap<>();
        list = new AssessmentReport().getEthnicityList(propertyId);
        data = new AssessmentReport().getEducationLevelDistribution(propertyId);
        while(list.iterator().hasNext()){
            ethnicityDataset.put(list.iterator().next(), data.iterator().next());
        }
        return ethnicityDataset;
    }

    public Map<String, Long> raceDataset(Integer propertyId){
        Map<String, Long> raceDataset = new LinkedHashMap<>();
        list = new AssessmentReport().getRaceDistributionList(propertyId);
        data = new AssessmentReport().getRaceDistributionFrequency(propertyId);
        while(list.iterator().hasNext()){
            raceDataset.put(list.iterator().next(), data.iterator().next());
        }
        return raceDataset;
    }

    public Map<String, Long> householdDataset(Integer propertyId){
        Map<String, Long> householdDataset =  new LinkedHashMap<>();
        list =  new AssessmentReport().getHeadOfHousehold(propertyId);
        data =  new AssessmentReport().getHeadOfHouseholdDistribution(propertyId);
        while(list.iterator().hasNext()){
            householdDataset.put(list.iterator().next(), data.iterator().next());
        }
        return householdDataset;
    }

    public Map<String, Long> veteranDataset(Integer propertyId){
        Map<String, Long> veteranDataset = new LinkedHashMap<>();
        list =  new AssessmentReport().getVeteranStatus(propertyId);
        data = new AssessmentReport().getVeteranStatusDistribution(propertyId);
        while(list.iterator().hasNext()){
            veteranDataset.put(list.iterator().next(), data.iterator().next());
        }
        return veteranDataset;
    }

    public Map<String, Long> disabilityDataset(Integer propertyId){
        Map<String, Long> disabilityDataset = new LinkedHashMap<>();
        list =  new AssessmentReport().getDisabilityStatus(propertyId);
        data = new AssessmentReport().getDisabilityDistribution(propertyId);
        while(list.iterator().hasNext()){
            disabilityDataset.put(list.iterator().next(), data.iterator().next());
        }
        return disabilityDataset;
    }

    public Map<String, Long> returningDataset(Integer propertyId){
        Map<String, Long> returning = new LinkedHashMap<>();
        list = new AssessmentReport().getReturningCitizenList(propertyId);
        data =  new AssessmentReport().getReturningCitizenDistribution(propertyId);
        while(list.iterator().hasNext()){
            returning.put(list.iterator().next(), data.iterator().next());
        }
        return returning;
    }

    public Map<String, Long> snapDataset(Integer propertyId){
        Map<String, Long> snapDataset = new LinkedHashMap<>();
        list = new AssessmentReport().getSNAPList(propertyId);
        data = new AssessmentReport().getSNAPDistribution(propertyId);
        while(list.iterator().hasNext()){
            snapDataset.put(list.iterator().next(), data.iterator().next());
        }
        return snapDataset;
    }

    public Map<String, Long> ssiDataset(Integer propertyId){
        Map<String, Long> ssiDataset = new LinkedHashMap<>();
        list = new AssessmentReport().getSSIList(propertyId);
        data =  new AssessmentReport().getSSIDistribution(propertyId);
        while(list.iterator().hasNext()){
            ssiDataset.put(list.iterator().next(), data.iterator().next());
        }
        return ssiDataset;
    }

    public Map<String, Long> ssdiDataset(Integer propertyId) {
        Map<String, Long> ssdiDataset =  new LinkedHashMap<>();
        list = new AssessmentReport().getSSDIList(propertyId);
        data = new AssessmentReport().getSSDIDistribution(propertyId);
        while(list.iterator().hasNext()){
            ssdiDataset.put(list.iterator().next(), data.iterator().next());
        }
        return ssdiDataset;
    }

    public Map<String, Long> healthDataset(Integer propertyId) {
        Map<String, Long> healthDataset = new LinkedHashMap<>();
        list = new AssessmentReport().getHealthCoverageList(propertyId);
        data = new AssessmentReport().getHealthCoverageDistribution(propertyId);
        while(list.iterator().hasNext()){
            healthDataset.put(list.iterator().next(), data.iterator().next());
        }
        return healthDataset;
    }
     public Map<String, Long> educationDataset(Integer propertyId){
        Map<String, Long> educationDataset = new LinkedHashMap<>();
        list =  new AssessmentReport().getEducationLevel(propertyId);
        data = new AssessmentReport().getEducationLevelDistribution(propertyId);
         while(list.iterator().hasNext()){
             educationDataset.put(list.iterator().next(), data.iterator().next());
         }
         return educationDataset;
     }

     public Map<String, Long> incomeDataset(Integer propertyId){
        Map<String, Long> incomeDataset =  new LinkedHashMap<>();
        list = new AssessmentReport().getHouseHoldIncome(propertyId);
        data =  new AssessmentReport().getHouseHoldIncomeDistribution(propertyId);
        while(list.iterator().hasNext()){
            incomeDataset.put(list.iterator().next(), data.iterator().next());
        }
        return incomeDataset;
     }

     public Map<String, Long> safetyDataset(Integer propertyId){
        Map<String, Long> safetyDataset =  new LinkedHashMap<>();
        list = new AssessmentReport().getHomeSafetyList(propertyId);
        data = new AssessmentReport().getHomeSafetyDistribution(propertyId);
        while(list.iterator().hasNext()){
            safetyDataset.put(list.iterator().next(), data.iterator().next());
        }
        return safetyDataset;
     }

     public Map<String, Long> languageDataset(Integer propertyId){
        Map<String, Long> languageDataset = new LinkedHashMap<>();
        list = new AssessmentReport().getLanguageList(propertyId);
        data = new AssessmentReport().getLanguageDistribution(propertyId);
        while(list.iterator().hasNext()){
            languageDataset.put(list.iterator().next(), data.iterator().next());
        }
        return languageDataset;
     }

     public Map<String, Long> ageDataset(Integer propertyId) {
        Map<String, Long> ageDataset = new LinkedHashMap<>();
        list = new AssessmentReport().getAgeRange(propertyId);
        data = new AssessmentReport().getAgeRangeDistribution(propertyId);
        while(list.iterator().hasNext()){
            ageDataset.put(list.iterator().next(), data.iterator().next());
        }
        return ageDataset;
     }
}
