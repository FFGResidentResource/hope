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

    public long insertNewProperty(@Valid Property property) {

        System.out.println("propId: " + property.getPropertyId());
        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
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
            // todo: implement
        } catch (Exception ex) {
            System.out.println("Ignoring error:" + ex);
        }

        return -1;
    }

    private PreparedStatement buildInsertProperty(Connection connection, @Valid Property property, String[] pkColumnNames) throws SQLException {

        System.out.println("buildInsertProperty");

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

    private PreparedStatement buildUpdateProperty(Connection connection, Property property, String[] pkColumnNames) throws SQLException {

        System.out.println("buildUpdateProperty");

        PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_PROPERTY, pkColumnNames);

        ps.setString(1, property.getPropertyName());
        ps.setString(2, property.getCity());
        ps.setString(3, property.getState());
        ps.setString(4, property.getCounty());
        ps.setLong(5, property.getUnit());
        ps.setLong(6, property.getUnitFee());
        ps.setBoolean(7, property.getActive());
        ps.setLong(8, property.getNoOfResident());
        ps.setBoolean(9, property.getResidentCouncil());
        ps.setLong(10, property.getPropertyId());

        return ps;
    }

    public List<Property> getAllProperty() {
        System.out.println("getAllProperty");
        PropertyMapper rowMapper = new PropertyMapper();
        return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);
    }

    public Long saveProperty(Property property) {

        System.out.println("saveProperty");

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] pkColumnNames = new String[]{"prop_id"};

        try {
            // If no records found, it will go in Catch Exception else next line
            this.getJdbcTemplate().queryForObject("select 1 from property where prop_id = ? ", new Object[]{property.getPropertyId()}, Integer.class);
            this.getJdbcTemplate().update(conn -> buildUpdateProperty(conn, property, pkColumnNames), keyHolder);
        } catch (EmptyResultDataAccessException e) {
            insertNewProperty(property);
        }

        return Long.valueOf(property.getPropertyId());
    }
}
