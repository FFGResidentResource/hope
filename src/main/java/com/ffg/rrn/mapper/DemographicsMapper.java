package com.ffg.rrn.mapper;

import com.ffg.rrn.model.Demographics;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DemographicsMapper implements RowMapper<Demographics>{


    @Override
    public Demographics mapRow(ResultSet rs, int row) throws SQLException {

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
