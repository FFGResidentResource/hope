package com.ffg.rrn.controller;

import com.ffg.rrn.model.Dashboard;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.report.AssessmentReportBuilder;
import com.ffg.rrn.report.PerformanceReportBuilder;
import com.ffg.rrn.report.Report;
import com.ffg.rrn.service.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class QuarterlyMVCController extends BaseController {

    @Autowired
    private ResidentServiceImpl residentService;

    @RequestMapping(value = "/quarterly", method = { RequestMethod.GET, RequestMethod.POST })
    public String getDashboardParameters(Model model, Principal principal) throws Exception {

        // (1) (en)
        // After user login successfully.
        String serviceCoord = null;
        if (principal != null) {
            serviceCoord = populateSCinModel(model, principal);
        }

        List<Property> allProperty = residentService.getAllProperty(getSessionUsername());

        Dashboard dashboard = new Dashboard();
        dashboard.setProperties(allProperty);

        int total = 0;
        if (!CollectionUtils.isEmpty(allProperty)) {
            for (Property property : allProperty) {
                total = total + property.getUnit();
            }
            dashboard.setTotalNoOfUnits(total);
        }

        long total2 = 0;
        if (!CollectionUtils.isEmpty(allProperty)) {
            for (Property property : allProperty) {
                total2 = total2 + property.getNoOfResident();
            }
            dashboard.setTotalNoOfResidents(total2);
        }

        dashboard.setTotalActiveResidents(residentService.getTotalActiveResident());
        dashboard.setNewResidents(residentService.getNewResidents());
        dashboard.setOngoingResidents(residentService.getOngoingResidents());

        model.addAttribute("dashboard", dashboard);


        return "quarterly";
    }

}
