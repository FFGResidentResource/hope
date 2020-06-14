package com.ffg.rrn.dao;

import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DemographicsDAO extends JdbcDaoSupport {
    /* to prepare query for report variables */

    private static final String SQL_LIST_PROPERTY = "SELECT prop_name, prop_id FROM property" ;

    private static final String SQL_GET_ALL_PROPERTIES ="SELECT *\n" +
            "FROM resident\n" +
            "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id\n" +
            "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id";

    private static final String SQL_DEMOGRAPHICS_QUESTION = "SELECT resident.resident_id, resident.prop_id, demographics_question_choice.choice_id, demographics_choices.choice FROM resident \n" +
            "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id \n" +
            "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id WHERE date_modified >= ? AND date_modified < ? AND question_id = ? AND prop_id = ?";

    private static final String SQL_DEMOGRAPHICS_COUNT = "SELECT count(*) FROM resident \n" +
            "FULL OUTER JOIN demographics_question_choice ON demographics_question_choice.resident_id = resident.resident_id \n" +
            "FULL OUTER JOIN demographics_choices ON demographics_question_choice.choice_id =  demographics_choices.choice_id WHERE date_modified >= ? AND date_modified < ? AND question_id = ? AND prop_id = ?";

    @Autowired
    public DemographicsDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Property> getAllProperty() {
       PropertyMapper propertyMapper = new PropertyMapper();
       List<Property> propertyList = this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, propertyMapper);

      return propertyList;
    }

    public DemographicsDAO() {
    }
}
