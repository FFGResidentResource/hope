package com.ffg.rrn.dao;

import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
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
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.toIntExact;

@Repository
@Transactional
public class PropertyDAO extends JdbcDaoSupport {

    // todo: SERVICE_PROVIDER is unmapped
    // private static final String SQL_INSERT_PROPERTY = "INSERT INTO PROPERTY (PROP_ID, PROP_NAME, CITY, STATE, COUNTY, UNIT, UNIT_FEE,  ACTIVE, TOTAL_RESIDENTS, RESIDENT_COUNCIL) VALUES (?,?,?,?,?,?,?,?,?,?)";
    // insert into property (prop_id, prop_name, city, state, county, unit, unit_fee, active, total_residents, resident_council, service_provider) DEFAULT VALUES
    private static final String SQL_INSERT_PROPERTY = "INSERT INTO PROPERTY (PROP_NAME, CITY, STATE, COUNTY, UNIT, UNIT_FEE, ACTIVE, TOTAL_RESIDENTS, RESIDENT_COUNCIL) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_PROPERTY = "UPDATE PROPERTY SET PROP_NAME=?, CITY=?, STATE=?, COUNTY=?, UNIT=?, UNIT_FEE=?, ACTIVE=?, TOTAL_RESIDENTS=?, RESIDENT_COUNCIL=? WHERE PROP_ID=?";

    @Autowired
    public PropertyDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    /**
     * Update the existing property by id.
     *
     * @param property
     * @return number of rows updated, should be 1 for unduplicated keys
     * @throws SQLException
     */
    public int updateExistingProperty(@Valid Property property) throws SQLException {

        this.getJdbcTemplate().getDataSource().getConnection().setAutoCommit(true);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = this.getJdbcTemplate().update(cnx -> {
            PreparedStatement preparedStatement = cnx.prepareStatement(SQL_UPDATE_PROPERTY);
            preparedStatement.setString(1, property.getPropertyName());
            preparedStatement.setString(2, property.getCity());
            preparedStatement.setString(3, property.getState());
            preparedStatement.setString(4, property.getCounty());
            preparedStatement.setInt(5, property.getUnit());
            preparedStatement.setInt(6, property.getUnitFee());
            preparedStatement.setBoolean(7, property.getActive());
            preparedStatement.setInt(8, property.getNoOfResident());
            preparedStatement.setBoolean(9, property.getResidentCouncil());
            preparedStatement.setInt(10, property.getPropertyId());
            return preparedStatement;
        }, keyHolder);
        System.out.println("Rows updated: "+rows);
        return rows;
    }

    public void insertNewProperty(@Valid Property property) throws SQLException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.getJdbcTemplate().getDataSource().getConnection().setAutoCommit(true);
            this.getJdbcTemplate().update(cnx -> {
                PreparedStatement preparedStatement = cnx.prepareStatement(SQL_INSERT_PROPERTY);
                preparedStatement.setString(1, property.getPropertyName());
                preparedStatement.setString(2, property.getCity());
                preparedStatement.setString(3, property.getState());
                preparedStatement.setString(4, property.getCounty());
                preparedStatement.setInt(5, property.getUnit());
                preparedStatement.setInt(6, property.getUnitFee());
                preparedStatement.setBoolean(7, property.getActive());
                preparedStatement.setInt(8, property.getNoOfResident());
                preparedStatement.setBoolean(9, property.getResidentCouncil());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException dke) {
            System.out.println("Duplicate key, trying update instead...");
            updateExistingProperty(property);
        } catch (Exception ex) {
            System.out.println("Ignoring error:" + ex);
        }
    }

    public List<Property> getAllProperty() {
        PropertyMapper rowMapper = new PropertyMapper();
        return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);
    }

    public Long saveProperty(Property property) {

        System.out.println("saveProperty "+property.getPropertyId());
        System.out.println("Num Units: "+property.getUnit());
        try {

            try {
                // todo: implement existence check
                System.out.println("Update existing property");
                if(updateExistingProperty(property)==0)
                    insertNewProperty(property);
                else
                    throw new IllegalStateException("Duplicate keys detected on update");
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Create new property entry");
                insertNewProperty(property);
            }

            return Long.valueOf(property.getPropertyId());
        } catch(Exception e) {
            // DEBUG
            e.printStackTrace();
            return -1L;
        }
    }
}
