package com.ffg.rrn.dao;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ffg.rrn.model.AssessmentQuestionnaire;
import com.ffg.rrn.model.DemographicQuestions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResidentDAOTest {
	
	@Autowired 
	private ResidentDAO residentDAO;
	
	
	private static final Logger logger= LoggerFactory.getLogger(ResidentDAOTest.class);
	
	@Test
	   public void testGetAllQuestionaires() throws Exception{
		List<DemographicQuestions> questionList = new ArrayList<DemographicQuestions>();
		questionList = residentDAO.getAllDemoQuestions();
		questionList.forEach(question -> { System.out.println(question.getQuestion()); });
		
	}

}
