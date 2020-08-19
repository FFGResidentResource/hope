package com.ffg.rrn.report;

import java.util.*;

public class OverviewReportBuilder extends Report {


    public List<Map<Collection<String>, Collection<Long>>> aggregateDemographicData(Long questionId){
        return getAllDemographicParameterCount(questionId);
    }

    public List<String> getDemographicTypes(){
        return demographicTypes();
    }

    public Map<String, Integer> demographicTypeMap(){
        List<String> demotypes = new OverviewReportBuilder().getDemographicTypes();
        Map<String, Integer> demographicMap = new LinkedHashMap<>();
        List<Integer> madeUpIds = new LinkedList<>();
        for (int i = 1; i < 17; i++){
            madeUpIds.add(i);
        }
        for (Map.Entry<String, Integer> e : new MergeListsToMap<>(demotypes, madeUpIds)) {
            demographicMap.put(e.getKey(), e.getValue());
        }
        return demographicMap;
    }



}
