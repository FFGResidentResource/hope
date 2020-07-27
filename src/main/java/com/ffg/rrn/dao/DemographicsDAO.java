package com.ffg.rrn.dao;

import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.model.Property;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DemographicsDAO {

    static {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException ex) {

        }
    }


    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/rrn", "ffg", "dbadmin1");
    }


    private void closeConnection(Connection connection) {
        if(connection == null)
            return;
        try{
            connection.close();
        }catch (SQLException ex){

        }
    }


    private static final String SQL_LIST_PROPERTY = "SELECT prop_name, prop_id, unit, unit_fee, active, total_residents, resident_council FROM property" ;
    private static final String RAW_DEMOGRAPHIC_DATA = "SELECT resident.resident_id, active, is_resident, resident.address, prop_id, ack_pr, ref_type, wants_survey, via_voicemail, via_email, via_text, allow_contact, choice, \n" +
            "demographics_question_choice.choice_id, question_id, date_added, date_modified, service_coord, photo_release, demographics_question_choice.type\n" +
            " FROM demographics_question_choice\n" +
            "        LEFT OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
            "        LEFT OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id";


    public List<Demographics> findAllResidentDemographicsData(){
        List<Demographics> userDemographicsData = new ArrayList<>();
        Connection connection =  null;
        try{
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(RAW_DEMOGRAPHIC_DATA);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Demographics demographics = new Demographics();

                demographics.setResidentId(rs.getLong("resident_id"));
                demographics.setActive(rs.getBoolean("active"));
                demographics.setIsResident(rs.getBoolean("is_resident"));
                demographics.setAddress(rs.getString("address"));
                demographics.setPropertyId(rs.getLong("prop_id"));
                demographics.setAckRightToPrivacy(rs.getBoolean("ack_pr"));
                demographics.setRefId(rs.getInt("ref_type"));
                demographics.setWantSurvey(rs.getBoolean("wants_survey"));
                demographics.setViaVoicemail(rs.getBoolean("via_voicemail"));
                demographics.setViaText(rs.getBoolean("via_text"));
                demographics.setViaEmail(rs.getBoolean("via_email"));
                demographics.setAllowContact(rs.getBoolean("allow_contact"));
                demographics.setChoice(rs.getString("choice"));
                demographics.setChoiceId(rs.getLong("choice_id"));
                demographics.setQuestionId(rs.getLong("question_id"));
                demographics.setDateAdded(rs.getTimestamp("date_added"));
                demographics.setDateModified(rs.getTimestamp("date_modified"));
                demographics.setServiceCoord(rs.getString("service_coord"));
                demographics.setPhotoRelease(rs.getBoolean("photo_release"));
                demographics.setType(rs.getString("type"));

                userDemographicsData.add(demographics);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeConnection(connection);
        }
        return userDemographicsData;

    }

    public List<Property> getAllPropertyObjects() {
        PropertyMapper propertyMapper = new PropertyMapper();
        List<Property> propertyList = new ArrayList<>();
        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_PROPERTY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Property p = new Property();
                p.setPropertyId(rs.getInt("prop_id"));
                p.setPropertyName(rs.getString("prop_name"));
                p.setUnit(rs.getInt("unit"));
                p.setUnitFee(rs.getInt("unit_fee"));
                p.setActive(rs.getBoolean("active"));
                p.setNoOfResident(rs.getInt("total_residents"));
                p.setResidentCouncil(rs.getBoolean("resident_council"));

                propertyList.add(p);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeConnection(connection);
        }
        return propertyList;
    }
}