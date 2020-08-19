package com.ffg.rrn.controller;


import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.AssessmentReportBuilder;
import com.ffg.rrn.report.OverviewReportBuilder;
import com.ffg.rrn.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OverviewController extends BaseController {



    @RequestMapping(value = "/overview" , method = RequestMethod.GET)
        public ModelAndView getOverview(ModelAndView model){
        List<Property> propertyList = new Report().getAllProperty();
        model.addObject("propertyList", propertyList);
        model.addObject("demographicTypes", new OverviewReportBuilder().demographicTypeMap());
        model.setViewName("overview");
        return model;
    }


    @RequestMapping(value = "/overview/{questionId}", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Map<Collection<String>, Collection<Long>>> getDemographicAggregates(@PathVariable("questionId")Long questionId, Model model){
        model.addAttribute("demographicAggregates", new OverviewReportBuilder().aggregateDemographicData(questionId));

        return new OverviewReportBuilder().aggregateDemographicData(questionId);
    }

}
