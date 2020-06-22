package com.ffg.rrn.report.filter;
import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.service.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AssessmentReport implements Report {

   private  Demographics demographics;

   @Autowired
   private DemographicsDAO demographicsDAO = new DemographicsDAO();

    @Autowired
    private ResidentServiceImpl residentService;


    public AssessmentReport(){}

    public static <T> Predicate<T> distinctByKey( Function<? super T, ?> keyExtractor) {
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


    public List<Demographics> queryAllDemographicsData() {
        List<Demographics> rawDemographicsData = demographicsDAO.findAllResidentDemographicsData();

        return rawDemographicsData;
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





    


    /*
    generate data for languages spoken at home
//     */
//    public Map<String, Long> primaryLanguage(Long residentId){
//        List<Demographics> rawData = demographicsDAO.getPrimaryLanguagesSpokenAtHomeByProperty(residentId);
//        //get a total count of all the type(matching Language) returned from database::total language count
//        Long totalLanguageCount = rawData.stream().map(Demographics::getType).count();
//
//        //get the unique count of all choice-types from the list of demographic entities returned from query.
//        List<String> uniqueLanguages = rawData.stream().map(Demographics::getChoice).distinct().collect(Collectors.toList());
//        List<Map<String, Long>> getTheCount = null;
//        for (Demographics demographics : rawData) {
//
//            Map<String, Long> LanguageByCount = rawData.stream().collect(Collectors.groupingBy(Demographics::getChoice, Collectors.counting()));
//
//        }

//        public static <T, NewKey> Map<NewKey, List<T>> groupingBy(List<T> list, Function<T, NewKey> function) {
//            return list.stream().collect(Collectors.groupingBy(function, LinkedHashMap::new, mapping(Function.identity(), Collectors.toList())))
//    }

//    public static <T, NewKey> Map<NewKey, List<T>> groupingBy(List<T> list, Function<T, NewKey> function) {
//        return list.stream().collect(Collectors.groupingBy(function, LinkedHashMap::new, mapping(Function.identity(), Collectors.toList())))
//
//    }
}
