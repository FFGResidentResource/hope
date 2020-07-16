package com.ffg.rrn.report;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.Calendar;
import java.util.List;

public class JFreeParams {

    public PieDataset createGenderDataSet(Integer propertyId){
        List<String> dataList = new AssessmentReport().getGenderList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getGenderDistribution(propertyId);
        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
        while(dataList.iterator().hasNext()){
            defaultPieDataset.setValue(dataList.iterator().next(), dataDistribution.iterator().next());
        }
        return defaultPieDataset;
    }
    //used as third parameter required to create default value.
    public String getDatasetDate(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year);
    }

    public DefaultCategoryDataset createGenderDataset(Integer propertyId){
        List<String> dataList = new AssessmentReport().getGenderList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getGenderDistribution(propertyId);
        DefaultCategoryDataset genderDataSet = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            genderDataSet.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return genderDataSet;
    }

    public DefaultCategoryDataset createEthnicityDataset(Integer propertyId) {
        List<String> dataList = new AssessmentReport().getEthnicityList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getEthnicityDistribution(propertyId);

        DefaultCategoryDataset ethnicityDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()) {
            ethnicityDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return ethnicityDataset;
    }

    public DefaultCategoryDataset createRaceDataset(Integer propertyId){
        List<String> dataList = new AssessmentReport().getRaceDistributionList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getRaceDistributionFrequency(propertyId);
        DefaultCategoryDataset raceDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            raceDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return raceDataset;
    }

    public DefaultCategoryDataset createHeadOfHouseholdDataset(Integer propertyId){
        List<String> dataList = new AssessmentReport().getHeadOfHousehold(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getHeadOfHouseholdDistribution(propertyId);
        DefaultCategoryDataset householdDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            householdDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return householdDataset;
    }

    public DefaultCategoryDataset createVeteranStatusDataset(Integer propertyId){
        List<String> dataList = new AssessmentReport().getVeteranStatus(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getVeteranStatusDistribution(propertyId);

        DefaultCategoryDataset veteranDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            veteranDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return veteranDataset;
    }

    public DefaultCategoryDataset createDisabilityDataset(Integer propertyId) {
        List<String> dataList =  new AssessmentReport().getDisabilityStatus(propertyId);
        List<Long> dataDistribution =  new AssessmentReport().getDisabilityDistribution(propertyId);
        DefaultCategoryDataset disabilityDataset =  new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            disabilityDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return disabilityDataset;
    }

    public DefaultCategoryDataset createReturningCitizenDataset(Integer propertyId){
        List<String> dataList =  new AssessmentReport().getReturningCitizenList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getReturningCitizenDistribution(propertyId);
        DefaultCategoryDataset returningCitizenDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            returningCitizenDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return returningCitizenDataset;
    }

    public DefaultCategoryDataset createSNAPListDataset(Integer propertyId){
        List<String> dataList = new AssessmentReport().getSNAPList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getSNAPDistribution(propertyId);
        DefaultCategoryDataset snapDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            snapDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return snapDataset;
    }

    public DefaultCategoryDataset createSSIDataset(Integer propertyId){
        List<String> dataList =  new AssessmentReport().getSSIList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getSSIDistribution(propertyId);
        DefaultCategoryDataset ssiDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            ssiDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return ssiDataset;
    }

    public DefaultCategoryDataset createSSDIDataset(Integer propertyId) {
        List<String> dataList = new AssessmentReport().getSSDIList(propertyId);
        List<Long> dataDistribution =  new AssessmentReport().getSSDIDistribution(propertyId);
        DefaultCategoryDataset ssdiDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            ssdiDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return ssdiDataset;
    }

    public DefaultCategoryDataset createHealthCoverageDataset(Integer propertyId) {
        List<String> dataList = new AssessmentReport().getHealthCoverageList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getHealthCoverageDistribution(propertyId);
        DefaultCategoryDataset healthDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            healthDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return healthDataset;
    }

    public DefaultCategoryDataset createEducationDataset(Integer propertyId) {
        List<String> dataList = new AssessmentReport().getEducationLevel(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getEducationLevelDistribution(propertyId);
        DefaultCategoryDataset educationDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            educationDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return educationDataset;
    }

    public DefaultCategoryDataset createHouseholdIncomeDataset(Integer propertyId) {
        List<String> dataList = new AssessmentReport().getHouseHoldIncome(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getHouseHoldIncomeDistribution(propertyId);
        DefaultCategoryDataset incomeDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            incomeDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }

        return incomeDataset;
    }

    public DefaultCategoryDataset createHomeSafetyDataset(Integer propertyId){
        List<String> dataList =  new AssessmentReport().getHomeSafetyList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getHomeSafetyDistribution(propertyId);
        DefaultCategoryDataset safetyDataset = new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            safetyDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return safetyDataset;
    }

    public DefaultCategoryDataset createLanguageDataset(Integer propertyId) {
        List<String> dataList = new AssessmentReport().getLanguageList(propertyId);
        List<Long> dataDistribution = new AssessmentReport().getLanguageDistribution(propertyId);
        DefaultCategoryDataset languageDataset =  new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            languageDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return languageDataset;
    }

    public DefaultCategoryDataset createAgeDataset(Integer propertyId) {
        List<String> dataList =  new AssessmentReport().getAgeRange(propertyId);
        List<Long> dataDistribution =  new AssessmentReport().getAgeRangeDistribution(propertyId);
        DefaultCategoryDataset ageDataset =  new DefaultCategoryDataset();
        while(dataList.iterator().hasNext()){
            ageDataset.addValue(dataDistribution.iterator().next(), dataList.iterator().next(), getDatasetDate());
        }
        return ageDataset;
    }

}
