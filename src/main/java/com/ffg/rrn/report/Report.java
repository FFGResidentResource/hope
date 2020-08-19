package com.ffg.rrn.report;
import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.model.Property;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class Report implements IReport {


    private DemographicsDAO demographicsDAO = new DemographicsDAO();


    public Report(){}

    private static <T> Predicate<T> distinctByKey( Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors)
    {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }


    @Override
    public List<Demographics> getAllDemographicObjects() {
        List<Demographics> demographicsList = demographicsDAO.findAllResidentDemographicsData();

        return demographicsList;
    }


    public List<Property> getAllProperty(){
        List<Property> propertyList = demographicsDAO.getAllPropertyObjects();
        return propertyList;
    }

    /*
    This method filters the List of Demographics data into on demographic object by property ID.

     */
    private List<Demographics> filterDemographicsDataByPropertyId(Long propertyId) {
        List<Demographics> demographicsByProperty = new ArrayList<>();
        List<Demographics> tempDemographics = demographicsDAO.findAllResidentDemographicsData();

        for(Demographics demographics : tempDemographics) {

            if(demographics.getPropertyId().equals(propertyId)) {

                demographicsByProperty.add(demographics);

            }
            //sort by resident Id
            Collections.sort(demographicsByProperty, new Comparator<Demographics>() {
                        @Override
                        public int compare(Demographics o1, Demographics o2) {
                            return o1.getResidentId().compareTo(o2.getResidentId());
                        }
                    });

        }
        return demographicsByProperty;

    }

    private List<Demographics> filterDemographicsDataByQuestionId(Long questionId) {
        List<Demographics> demographicsByQuestion = new ArrayList<>();
        List<Demographics> tempDemographics = demographicsDAO.findAllResidentDemographicsData();

        for(Demographics demographics : tempDemographics) {

            if(demographics.getQuestionId().equals(questionId)) {

                demographicsByQuestion.add(demographics);

            }
            //sort by resident Id
            Collections.sort(demographicsByQuestion, new Comparator<Demographics>() {
                @Override
                public int compare(Demographics o1, Demographics o2) {
                    return o1.getResidentId().compareTo(o2.getResidentId());
                }
            });

        }
        return demographicsByQuestion;

    }

    // Filter Demographics by PropertyId and by question ID
    private List<Demographics> filterByPropertyIdByQuestionId(Long propertyId, Long questionId){
        List<Demographics> demographicsByPropertyIdQuestionId = filterDemographicsDataByPropertyId(propertyId);

        Predicate<Demographics> filterByQuestionId = d -> d.getQuestionId() == questionId;

        //get demographics data based on the QuestionID
        List<Demographics> datafilteredByQIDPID = demographicsByPropertyIdQuestionId.stream()
                .filter(filterByQuestionId).collect(Collectors.toList());
        return datafilteredByQIDPID;
    }


    //Function to map Choice to Choice ID since they're not on the same table.
    private Map<Long, String> mapChoiceId2choice(Long propertyId, Long questionId){
        List<Demographics> getDemographics = filterByPropertyIdByQuestionId(propertyId, questionId);
        List<Demographics> getDemographics2 = filterByPropertyIdByQuestionId(propertyId, questionId);
        List<String> choices = getDemographics.stream().distinct().map(Demographics::getChoice).collect(Collectors.toList());
        List<Long> choiceId = getDemographics2.stream().distinct().map(Demographics::getChoiceId).collect(Collectors.toList());

      Map<Long, String> mapChoice = new HashMap<>();

      for (int i = 0; i < choiceId.size(); i++) {
          mapChoice.put(choiceId.get(i), choices.get(i));
      }

        return mapChoice;
    }


    private Map<Set<Map.Entry<Long, String>>, Set<Map.Entry<Long, Long>>> mapChoioce2IDAndMap2Count(Long propertyId, Long questionId){
        //get the unique choices
        List<Demographics> myDemographicList  = filterByPropertyIdByQuestionId(propertyId, questionId);
        List<Long> uniqueVariableIDs = myDemographicList.stream().distinct().map(Demographics::getChoiceId).collect(Collectors.toList());
        //count all the choice Ids
        long countAllChoiceIDs = myDemographicList.stream().map(Demographics::getChoiceId).count();
        //get the mapping of choiceId to Choices
        Map<Long, String> variablesCount = mapChoiceId2choice(propertyId, questionId);
        Map<Long, Long> frequencyCount4ChoiceId = new HashMap<>();
        long count = 0;
        for(Long variable : uniqueVariableIDs){

            count = myDemographicList.stream().filter(e -> e.getChoiceId().equals(variable)).count();
            frequencyCount4ChoiceId.put(variable, count);
        }

        Map<Long, Long> collateFrequencyCount = frequencyCount4ChoiceId;
        Map<Set<Map.Entry<Long, String>>, Set<Map.Entry<Long, Long>>> mapChoice2Count = new HashMap<>();

        Set<Long> keys = variablesCount.keySet();
        for (Long K : keys) {
            if(keys.equals(collateFrequencyCount.keySet())){
                mapChoice2Count.put(variablesCount.entrySet(), collateFrequencyCount.entrySet());
            }
        }

        return mapChoice2Count;
    }

    protected List<String> demographicTypes(){
        List<String> demoTypes = getAllDemographicObjects().stream().map(Demographics::getType).distinct().collect(Collectors.toList());

        return demoTypes;
    }


    protected Map<Collection<String>, Collection<Long>> getAnswerOccurenceCount(Long propertyId, Long questionId) {
        Map<Long, String> mapChoiceToID = mapChoiceId2choice(propertyId, questionId);

        List<Demographics> myDemographicList  = filterByPropertyIdByQuestionId(propertyId, questionId);
        List<Long> uniqueVariableIDs = myDemographicList.stream().distinct().map(Demographics::getChoiceId).collect(Collectors.toList());
        //long countAllChoiceIDs = myDemographicList.stream().map(Demographics::getChoiceId).count();
        Map<Long, Long> frequencyCount4ChoiceId = new HashMap<>();
        long count = 0;
        for(Long variable : uniqueVariableIDs){
            count = myDemographicList.stream().filter(e -> e.getChoiceId().equals(variable)).count();
            frequencyCount4ChoiceId.put(variable, count);
        }
        Map<Long, Long> collateFrequencyCount = frequencyCount4ChoiceId;
        Map<Collection<String>, Collection<Long>> mapChoice2Count = new HashMap<>();

        mapChoiceToID.forEach((K, v) -> {
           mapChoice2Count.put(mapChoiceToID.values(), collateFrequencyCount.values());
        });

        return mapChoice2Count;
    }


    protected List<Map<Collection<String>, Collection<Long>>> getAllDemographicParameterCount(Long questionId) {
        List<Property> propertyList = getAllProperty();
        List<Integer> propertyIdList = propertyList.stream().map(Property::getPropertyId).collect(Collectors.toList());

        List<Map<Collection<String>, Collection<Long>>> ListAllMappedChoices = new LinkedList<>();
        for(Integer propertyId : propertyIdList){
            ListAllMappedChoices.add(getAnswerOccurenceCount((long)propertyId, questionId));
        }

        return ListAllMappedChoices;
    }



    /*
    Method to generate all the report variables so far taking in just the property id when called from the UI
 */


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

    protected List<String> getGenderList(Long propertyId){

        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getGenderDistribution(Long propertyId) {

        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long) 1);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()) {
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }


    protected List<String> getEthnicityList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount((long)propertyId, (long)2);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getEthnicityDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)2);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }

    protected List<String> getRaceDistributionList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)3);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getRaceDistributionFrequency(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)3);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getHeadOfHousehold(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)4);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getHeadOfHouseholdDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)4);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getVeteranStatus(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)5);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getVeteranStatusDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)5);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());

    }
    protected List<String> getDisabilityStatus(Long propertyId){
        ArrayList<Collection> keys = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)6);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getDisabilityDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)6);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getReturningCitizenList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)7);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getReturningCitizenDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)7);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getSNAPList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)8);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getSNAPDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)8);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getSSIList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)9);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getSSIDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)9);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getSSDIList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)10);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getSSDIDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)10);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getHealthCoverageList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)11);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getHealthCoverageDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)11);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getEducationLevel(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)12);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getEducationLevelDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)12);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getHouseHoldIncome(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)13);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getHouseHoldIncomeDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)13);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getHomeSafetyList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)14);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getHomeSafetyDistribution(Long propertyId){
        ArrayList<Collection> values = new ArrayList<>();
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)14);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getLanguageList(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)15);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getLanguageDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)15);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }
    protected List<String> getAgeRange(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)16);
        for (Map.Entry<Collection<String>, Collection<Long>> entry : getData.entrySet()){
            keys.add(entry.getKey());
        }
        return (List<String>) keys.get(0).stream().collect(Collectors.toList());
    }

    protected List<Long> getAgeRangeDistribution(Long propertyId){
        Map<Collection<String>, Collection<Long>> getData = getAnswerOccurenceCount(propertyId, (long)16);
        for(Map.Entry<Collection<String>, Collection<Long>>  entry: getData.entrySet()){
            values.add(entry.getValue());
        }
        return (List<Long>) values.get(0).stream().collect(Collectors.toList());
    }




}
