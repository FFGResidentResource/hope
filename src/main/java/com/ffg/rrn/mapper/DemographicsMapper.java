package com.ffg.rrn.mapper;

import com.ffg.rrn.model.Demographics;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DemographicsMapper implements RowMapper<Demographics>{


    private static final String SQL_GET_DEMOGRAPHICS ="SELECT resident.resident_id, active, is_resident, prop_id, ack_pr, ref_type, wants_survey, via_voicemail, via_text, allow_contact, choice, demographics_question_choice.choice_id, question_id, date_added, date_modified, service_coord, demographics_question_choice.type, photo_release FROM resident \n" +
            "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id \n" +
            "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id;";


    @Override
    public Demographics mapRow(ResultSet rs, int row) throws SQLException {

        Demographics demographics = new Demographics();

        demographics.setResidentId(rs.getLong("resident_id"));
        demographics.setActive(rs.getBoolean("active"));
        demographics.setIsResident(rs.getBoolean("is_resident"));
        demographics.setPropertyId(rs.getInt("prop_id"));
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
