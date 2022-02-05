package com.ffg.rrn.service;

import com.ffg.rrn.dao.PropertyDAO;
import com.ffg.rrn.model.Property;
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
}
