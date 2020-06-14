package com.ffg.rrn.report;

import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.model.Property;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ReportSelector extends VerticalLayout implements KeyNotifier {

    ReportRequest reportRequest = new ReportRequest();

    private final DemographicsDAO demographicsDAO;

    private Grid<ReportRequest> reportRequestGrid = new Grid<>(ReportRequest.class);

    public ReportSelector(DemographicsDAO demographicsDAO) {
        this.demographicsDAO = demographicsDAO;
        add(new H1("Choose Report"), buildForm(), reportRequestGrid);
    }



    private Component buildForm(){
        //Create UI components
        TextField reportYear = new TextField("Report Year");
        ComboBox<Property> propertyComboBox = new ComboBox<>();
        ComboBox<String> reportTypeComboBox = new ComboBox<>();
        propertyComboBox.setLabel("Property");
        reportTypeComboBox.setLabel("Type of Report");
        List<Property> propertyList = new ArrayList<>();
        List<String> reportList = new ArrayList<>();

        //generate the report names from ReportRequest.getReportType
        reportList.addAll(Arrays.asList(reportRequest.getReportType()));
        propertyList = new DemographicsDAO().getAllProperty();

        //set properties on the property comboBoxes
        propertyComboBox.setItemLabelGenerator(Property::getPropertyName);
        propertyComboBox.setItems(propertyList);
        reportTypeComboBox.setItems(reportList);
        propertyComboBox.setEnabled(false);
        propertyComboBox.addValueChangeListener(e -> {
           Property property = e.getValue();
           boolean enabled = property != null && property.getActive();
           propertyComboBox.setEnabled(enabled);
           if(enabled){
               reportRequest.setReportYear(reportYear.getValue());
               reportRequest.setPropertyName(property.getPropertyName());
           }
        });
        
        Button getReportButton = new Button("GetReport");
        Div errorsLayout = new Div();

        //configure UI components
        getReportButton.setThemeName("primary");

        //Wrap componments in layouts
        HorizontalLayout formLayout =  new HorizontalLayout(reportYear, propertyComboBox, reportTypeComboBox, getReportButton);
        Div wrapperLayout = new Div(formLayout, errorsLayout);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        wrapperLayout.setWidth("100%");

        return wrapperLayout;
    }
}
