package com.ffg.rrn.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.DemographicChoice;
import com.ffg.rrn.model.DemographicQuestions;

/**
 * @author FFGRRNTeam
 *
 */

public class DemoQuestionsMapper implements RowMapper<DemographicQuestions> {
	
	public static final String DEMOQ_SQL //
	= "select question_id, question from DEMOGRAPHICS_QUESTIONS"; 

@Override
public DemographicQuestions mapRow(ResultSet rs, int row) throws SQLException {

	DemographicQuestions r = new DemographicQuestions();

r.setQuestionId(rs.getInt("question_id"));
r.setQuestion(rs.getString("question"));

return r;
}

}







