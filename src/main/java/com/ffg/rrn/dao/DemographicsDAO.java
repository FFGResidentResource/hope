package com.ffg.rrn.dao;

import com.ffg.rrn.mapper.DemographicsMapper;
import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class DemographicsDAO extends JdbcDaoSupport {
    /* to prepare query for report variables */

    private DemographicsMapper demographicsMapper = new DemographicsMapper();

    private static final String SQL_LIST_PROPERTY = "SELECT prop_name, prop_id FROM property" ;

    private static final String DEMOGRAPHICSQL ="SELECT *\n" +
            "FROM resident\n" +
            "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id\n" +
            "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id ORDER BY prop_id";

    private static final String SQL_DEMOGRAPHICS_QUESTION = "SELECT resident.resident_id, resident.prop_id, demographics_question_choice.choice_id, demographics_choices.choice FROM resident \n" +
            "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id \n" +
            "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id WHERE date_modified >= ? AND date_modified < ? AND question_id = ? AND prop_id = ?";

    private static final String SQL_DEMOGRAPHICS_COUNT = "SELECT count(*) FROM resident \n" +
            "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id \n" +
            "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id WHERE date_modified >= ? AND date_modified < ? AND question_id = ? AND prop_id = ?";

   private static final String GET_ALL_LANGUAGES = "SELECT choice, demographics_question_choice.choice_id, question_id, demographics_question_choice.type FROM demographics_question_choice\n" +
           "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id WHERE question_id = 15"; //question_id(15) refers to Languages spoken

    @Autowired
    public DemographicsDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public DemographicsDAO() {}

    public List<Property> getAllPropertyObjects() {
       PropertyMapper propertyMapper = new PropertyMapper();
       List<Property> propertyList = this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, propertyMapper);

      return propertyList;
    }

    public List<Demographics> findDemographicInfoByPropertyId(Integer propertyId) {
        Object[] params = new Object[]{propertyId};

        String sql = "SELECT resident.resident_id, active, is_resident, prop_id, ack_pr, ref_type, wants_survey, via_voicemail, via_text, allow_contact, choice, \n" +
                "demographics_question_choice.choice_id, question_id, date_added, date_modified, service_coord, demographics_question_choice.type, photo_release FROM resident \n" +
                "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id \n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id WHERE prop_id = ?";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getAllDemographicObjects(){

        return this.getJdbcTemplate().query(DEMOGRAPHICSQL, demographicsMapper);
    }

    public List<Demographics> getLanguageCountByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select count(demographics_question_choice.choice_id)\n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 15 AND demographics_choices.type LIKE 'Language%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getGenderByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 1 AND demographics_choices.type LIKE 'Gender%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getEthnicityByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 2 AND demographics_choices.type LIKE 'Ethnicity%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getRaceByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 3 AND demographics_choices.type LIKE 'Race%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getHeadOfHouseholdByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 4 AND demographics_choices.type LIKE 'HeadOfHousehold%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }
    public List<Demographics> getVeteranStatusByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 5 AND demographics_choices.type LIKE 'Veteran%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }
    public List<Demographics> getDisabilityStatusByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 6 AND demographics_choices.type LIKE 'Disability%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }
    public List<Demographics> getReturningCitizenByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 7 AND demographics_choices.type LIKE 'Ex-Offender%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }
    public List<Demographics> getSNAPRecipientByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 8 AND demographics_choices.type LIKE 'SNAP%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getSSIRecipientByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 9 AND demographics_choices.type LIKE 'SSI%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }
    public List<Demographics> getSSDIRecipientByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE question_id = 10 AND demographics_choices.type LIKE 'SSDI%' ORDER BY prop_id";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getHealthCoverageStatusByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 11 AND demographics_choices.type LIKE 'Health%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getEducationLevelByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 12 AND demographics_choices.type LIKE 'Education%' ";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getHouseholdAnnualIncomeByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 13 AND demographics_choices.type LIKE 'Income%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getHouseholdSafetyByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 14 AND demographics_choices.type LIKE 'HouseholdSafety%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getAgeRangeByProperty(Long propertyId) {
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 16 AND demographics_choices.type LIKE 'Age%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);
    }

    public List<Demographics> getPrimaryLanguagesSpokenAtHomeByProperty(Long propertyId){
        Object[] params = new Object[]{propertyId};
        String sql = "select demographics_question_choice.resident_id, question_id, demographics_question_choice.choice_id, demographics_question_choice.type, prop_id, demographics_choices.choice \n" +
                "FROM demographics_question_choice\n" +
                "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "WHERE prop_id = ? AND question_id = 15 AND demographics_choices.type LIKE 'Language%'";

        return this.getJdbcTemplate().query(sql, params, demographicsMapper);

    }

    public Long getNumberOfEnglishSpeakersByProperty(Long propertyId){
        Object[] params = new Object[]{propertyId};
        String sql = "select count(demographics_question_choice.choice_id)\n" +
                "        FROM demographics_question_choice\n" +
                "        FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id = demographics_choices.choice_id\n" +
                "        FULL OUTER JOIN resident ON resident.resident_id = demographics_question_choice.resident_id\n" +
                "        WHERE question_id = 1 AND demographics_choices.choice_id = 14;\n";

        return this.getJdbcTemplate().queryForObject(sql, params, Long.class);
    }
}
