package com.ffg.rrn.controller;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.filter.AssessmentReport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportController {

    @GetMapping("/reportrequest")
    public String getPropertyList(@RequestParam(required=false) List<Property> propertyList, Model model){
        /*List<Property>*/ propertyList = new AssessmentReport().getAllProperty();
        model.addAttribute("propertyList", propertyList);
        return "reportrequest";
    }
}