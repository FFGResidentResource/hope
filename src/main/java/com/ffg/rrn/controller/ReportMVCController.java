package com.ffg.rrn.controller;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.Report;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping
public class ReportMVCController {


    @GetMapping("/reportrequest")
    public String getPropertyList(@RequestParam(value="propertyId", required=false) Integer propertyId, Model model){
        List<Property> propertyList = new Report().getAllProperty();
        model.addAttribute("propertyList", propertyList);

        return "reportrequest";
    }
    @PostMapping("/reportrequest/{propertyId}")
    public String buildChart(@PathVariable("{propertyId}")Long propertyId, Model model){



        return "redirect:/reportrequest";
    }

    @GetMapping("/reportrequest/{proprtyId}")
    public String showChartVariables(@PathVariable("propertyId")Long propertyId, Model model){

        return "redirect:/reportrequest";
    }

}
