package com.ffg.rrn.dao;

import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.model.Property;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
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
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.toIntExact;

@Repository
@Transactional
public class PropertyDAO extends JdbcDaoSupport {

    private static final String SQL_INSERT_PROPERTY = "INSERT INTO PROPERTY (PROP_NAME, CITY, STATE, COUNTY, SERVICE_PROVIDER, UNIT, UNIT_FEE, ACTIVE, TOTAL_RESIDENTS, RESIDENT_COUNCIL) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_PROPERTY = "UPDATE PROPERTY SET CITY=?, STATE=?, COUNTY=?, SERVICE_PROVIDER=?, UNIT=?, UNIT_FEE=?, ACTIVE=?, TOTAL_RESIDENTS=?, RESIDENT_COUNCIL=? WHERE PROP_NAME=?";
    private static final String SQL_GETALL_PROPERTIES = "SELECT PROP_NAME, CITY, STATE, COUNTY," +
            " UNIT, UNIT_FEE, TOTAL_RESIDENTS, RESIDENT_COUNCIL, SERVICE_PROVIDER, ACTIVE FROM PROPERTY " +
            "ORDER BY PROP_NAME ASC";

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

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = this.getJdbcTemplate().update(cnx -> {
            PreparedStatement preparedStatement = cnx.prepareStatement(SQL_UPDATE_PROPERTY);
            preparedStatement.setString(1, property.getCity());
            preparedStatement.setString(2, property.getState());
            preparedStatement.setString(3, property.getCounty());
            preparedStatement.setString(4, property.getServiceProvider());
            preparedStatement.setInt(5, property.getUnit());
            preparedStatement.setInt(6, property.getUnitFee());
            preparedStatement.setBoolean(7, property.getActive());
            preparedStatement.setInt(8, property.getNoOfResident());
            preparedStatement.setBoolean(9, property.getResidentCouncil());
            preparedStatement.setString(10, property.getPropertyName());
            return preparedStatement;
        }, keyHolder);
        return rows;
    }

    public void insertNewProperty(@Valid Property property) throws SQLException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.getJdbcTemplate().update(cnx -> {
                PreparedStatement preparedStatement = cnx.prepareStatement(SQL_INSERT_PROPERTY);
                preparedStatement.setString(1, property.getPropertyName());
                preparedStatement.setString(2, property.getCity());
                preparedStatement.setString(3, property.getState());
                preparedStatement.setString(4, property.getCounty());
                preparedStatement.setString(5, property.getServiceProvider());
                preparedStatement.setInt(6, property.getUnit());
                preparedStatement.setInt(7, property.getUnitFee());
                preparedStatement.setBoolean(8, property.getActive());
                preparedStatement.setInt(9, property.getNoOfResident());
                preparedStatement.setBoolean(10, property.getResidentCouncil());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException dke) {
            updateExistingProperty(property);
        } catch (Exception ex) {
            // todo: better error handling, let UI move forward and log the error
            System.out.println("Ignoring error:" + ex);
        }
    }

    public List<Property> getAllProperty() {
        PropertyMapper rowMapper = new PropertyMapper();
        return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);
    }

    public Long saveProperty(Property property) {

        try {

            try {
                if(updateExistingProperty(property)==0)
                    insertNewProperty(property);
            } catch (EmptyResultDataAccessException e) {
                // todo: better error handling, is this even thrown anymore now that we're not preceding every write
                // with a query?
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

    public JRDataSource getPropertyDataSource() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection cnx = DriverManager
                    .getConnection("jdbc:postgresql://axon1:5432/rrhope",
                            "admin", "stonewall");
            PreparedStatement ps = cnx.prepareStatement(SQL_GETALL_PROPERTIES);
            ResultSet rs = ps.executeQuery();
            return new JRResultSetDataSource(rs);
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
