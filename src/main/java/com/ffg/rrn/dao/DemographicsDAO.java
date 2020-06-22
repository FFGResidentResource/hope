package com.ffg.rrn.dao;

import com.ffg.rrn.model.Demographics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DemographicsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DemographicsDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DemographicsDAO() {

    }

    private static final String RAW_DEMOGRAPHIC_DATA = "SELECT resident.resident_id, active, is_resident, resident.address, prop_id, ack_pr, ref_type, wants_survey, via_voicemail, via_text, allow_contact, choice, \n" +
            "demographics_question_choice.choice_id, question_id, date_added, date_modified, service_coord, photo_release, demographics_question_choice.type\n" +
            " FROM demographics_question_choice\n" +
            "        LEFT OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
            "        LEFT OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id";


    public List<Demographics> findAllResidentDemographicsData(){
        List<Demographics> userDemographicsData = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(RAW_DEMOGRAPHIC_DATA);
        while(results.next()) {
            userDemographicsData.add(mapRowToDemographics(results));
        }
        return userDemographicsData;

    }
    public List<Demographics> findAllResidentDemographicsDataByPropertyId(Long propertyId){
        List<Demographics> userDemographicsData = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(RAW_DEMOGRAPHIC_DATA, propertyId);
        while(results.next()) {
            userDemographicsData.add(mapRowToDemographics(results));
        }
        return userDemographicsData;
    }


    private Demographics mapRowToDemographics(SqlRowSet rs) {

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

        return demographics;
    }


}