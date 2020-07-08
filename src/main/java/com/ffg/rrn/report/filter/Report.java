package com.ffg.rrn.report.filter;
import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.service.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class Report implements IReport {


    @Autowired
   private DemographicsDAO demographicsDAO;



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
    public List<Demographics> filterDemographicsDataByPropertyId(Long propertyId) {
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
    // Filter Demographics by PropertyId and by question ID
    public List<Demographics> filterByPropertyIdByQuestionId(Long propertyId, Long questionId){
        List<Demographics> demographicsByPropertyIdQuestionId = this.filterDemographicsDataByPropertyId(propertyId);

        Predicate<Demographics> filterByQuestionId = d -> d.getQuestionId() == questionId;

        //get demographics data based on the QuestionID
        List<Demographics> datafilteredByQIDPID = demographicsByPropertyIdQuestionId.stream()
                .filter(filterByQuestionId).collect(Collectors.toList());
        return datafilteredByQIDPID;
    }

    //Function to map Choice to Choice ID since they're not on the same table.
    private Map<Long, String> mapChoiceId2choice(Long propertyId, Long questionId){
        List<Demographics> getDemographics = this.filterByPropertyIdByQuestionId(propertyId, questionId);
        List<Demographics> getDemographics2 = this.filterByPropertyIdByQuestionId(propertyId, questionId);
        List<String> choices = getDemographics.stream().distinct().map(Demographics::getChoice).collect(Collectors.toList());
        List<Long> choiceId = getDemographics2.stream().distinct().map(Demographics::getChoiceId).collect(Collectors.toList());

      Map<Long, String> mapChoice = new HashMap<>();

      for (int i = 0; i < choiceId.size(); i++) {
          mapChoice.put(choiceId.get(i), choices.get(i));
      }

        return mapChoice;
    }

    public Map<Set<Map.Entry<Long, String>>, Set<Map.Entry<Long, Long>>> mapChoioce2IDAndMap2Count(Long propertyId, Long questionId){
        //get the unique choices
        List<Demographics> myDemographicList  = this.filterByPropertyIdByQuestionId(propertyId, questionId);
        List<Long> uniqueVariableIDs = myDemographicList.stream().distinct().map(Demographics::getChoiceId).collect(Collectors.toList());
        //count all the choice Ids
        long countAllChoiceIDs = myDemographicList.stream().map(Demographics::getChoiceId).count();
        //get the mapping of choiceId to Choices
        Map<Long, String> variablesCount = this.mapChoiceId2choice(propertyId, questionId);
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


    public Map<Collection<String>, Collection<Long>> getAnswerOccurenceCount(Long propertyId, Long questionId) {
        Map<Long, String> mapChoiceToID = this.mapChoiceId2choice(propertyId, questionId);

        List<Demographics> myDemographicList  = this.filterByPropertyIdByQuestionId(propertyId, questionId);
        List<Long> uniqueVariableIDs = myDemographicList.stream().distinct().map(Demographics::getChoiceId).collect(Collectors.toList());
        long countAllChoiceIDs = myDemographicList.stream().map(Demographics::getChoiceId).count();
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


}
