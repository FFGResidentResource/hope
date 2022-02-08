package com.ffg.rrn.service;

import com.ffg.rrn.dao.PropertyDAO;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.utils.PasswordUtils;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl {

    @Autowired
    private PropertyDAO propertyDAO;

    public List<Property> getAllProperties() {
        return this.propertyDAO.getAllProperty();
    }

    public Long saveProperty(final Property property) {
        return this.propertyDAO.saveProperty(property);
    }

    public JRDataSource getPropertyDataSource() {
        return this.propertyDAO.getPropertyDataSource();
    }
}
