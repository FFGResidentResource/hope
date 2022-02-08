package com.ffg.rrn.controller;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.service.PropertyServiceImpl;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PropertyController extends BaseController {

    @Autowired
    private PropertyServiceImpl propertyService;

    @ModelAttribute("propertyCoordinator")
    public Property initialize() {
        return new Property();
    }

    @RequestMapping(value = "/property", method = RequestMethod.GET)
    public String propertyPage(@ModelAttribute Property property,
                               Model model, Principal principal) {
        if (principal != null) {
            populateSCinModel(model, principal);
        }

        List<Property> allProperty = propertyService.getAllProperties();

        Property aProperty = new Property();
        aProperty.setPropertyList(allProperty);

        model.addAttribute("property", aProperty);

        return "propertyPage";
    }

    @PostMapping("/saveProperty")
    public String saveProperty(Model model, Principal principal, @ModelAttribute("property") @Valid Property property,
                                         BindingResult bindingResult) {

        try {
            propertyService.saveProperty(property);
        }
        catch(Exception ex) {
            throw ex;
        }
        finally{
            return "redirect:/property";
        }
    }

    /**
     * Example report file invocation:
     *  http://localhost:8080/reportProperty/RRN_Hope_sample2.jasper
     * @param fileName
     * @return
     */
    @RequestMapping("/reportProperty/{fileName:.+}")
    public ResponseEntity reportProperty(@PathVariable  String fileName) {

        String fileBasePath = "/Development/hope/src/main/resources/reports/";

        try {
            JRDataSource dataSource = propertyService.getPropertyDataSource();
            Map parameters = new HashMap();
            String sourceFileName = fileBasePath + fileName; // todo: change to reports in resource folder
            String fillFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, dataSource);
            System.out.println("fillFileName:"+fillFileName);
            String pdfFileName = JasperExportManager.exportReportToPdfFile(fillFileName);
            System.out.println("PDF File:"+pdfFileName);
            Path path = Paths.get(pdfFileName);
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
