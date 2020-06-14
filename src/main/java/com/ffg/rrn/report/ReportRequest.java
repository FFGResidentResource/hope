package com.ffg.rrn.report;

import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.model.Property;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import java.util.*;

public class ReportRequest {
    //user will enter the four digit year
    private Property property = new Property();
    private String propertyName = property.getPropertyName();
    private String reportType = null;

    public String getPropertyName() {
        return propertyName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @NotBlank(message="Enter Year YYYY")
    private String reportYear = "";
    //report-type options from list or comboBox


   /* private static final String[] firstQuarter = {"01-01", "03-31"};
    private static final String[] secondQuarter = {"04-01", "06-30"};
    private static final String[] thirdQuarter = {"07-01", "09-30"};
    private static final String[] fourthQuarter = {"10-01,", "12-31"};
    */

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear;
    }
}
