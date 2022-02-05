package com.ffg.rrn.dao;

import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class PropertyDAO extends JdbcDaoSupport{

    // todo: SERVICE_PROVIDER is unmapped
    private static final String SQL_INSERT_PROPERTY = "INSERT INTO PROPERTY (PROP_ID, PROP_NAME, CITY, STATE, COUNTY, UNIT, UNIT_FEE,  ACTIVE, TOTAL_RESIDENTS, RESIDENT_COUNCIL) VALUES (?,to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json), to_json(?::json), to_json(?::json),to_json(?::json))";
    private static final String SQL_UPDATE_PROPERTY = "UPDATE PROPERTY SET PROP_NAME=?, CITY=?, STATE=?, COUNTY=?, UNIT=?, UNIT_FEE=?, ACTIVE=?, TOTAL_RESIDENTS=?, RESIDENT_COUNCIL=? WHERE PROP_ID=?";

    @Autowired
    public PropertyDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public long insertNewProperty(@Valid Property property) {

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] pkColumnNames = new String[] { "prop_id" };

        this.getJdbcTemplate().update(conn -> buildInsertProperty(conn, property, pkColumnNames), keyHolder);

        int propId = keyHolder.getKey().intValue();
        property.setPropertyId(propId);

        return propId;
    }

    private PreparedStatement buildInsertProperty(Connection connection, @Valid Property property, String[] pkColumnNames) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PROPERTY, pkColumnNames);

        ps.setLong(1, property.getPropertyId());
        ps.setString(2, property.getPropertyName());
        ps.setString(3, property.getCity());
        ps.setString(4, property.getState());
        ps.setString(5, property.getCounty());
        ps.setLong(6, property.getUnit());
        ps.setLong(7, property.getUnitFee());
        ps.setBoolean(8, property.getActive());
        ps.setLong(9, property.getNoOfResident());
        ps.setBoolean(10, property.getResidentCouncil());
        return ps;
    }

    private PreparedStatement buildUpdateResidentPS(Connection connection, Property property) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PROPERTY);

        ps.setString(1, property.getPropertyName());
        ps.setString(2, property.getCity());
        ps.setString(3, property.getState());
        ps.setString(4, property.getCounty());
        ps.setLong(5, property.getUnit());
        ps.setLong(6, property.getUnitFee());
        ps.setBoolean(7, property.getActive());
        ps.setLong(8, property.getNoOfResident());
        ps.setBoolean(9, property.getResidentCouncil());
        ps.setLong(10, property.getUnit());
        return ps;
    }

    public List<Property> getAllProperty() {
        PropertyMapper rowMapper = new PropertyMapper();
        return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);
    }
}
