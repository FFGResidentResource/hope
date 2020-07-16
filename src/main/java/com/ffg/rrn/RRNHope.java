package com.ffg.rrn;

import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.report.AssessmentReport;
import com.ffg.rrn.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;

@SpringBootApplication
public class RRNHope {



	public static void main(String[] args) {
		SpringApplication.run(RRNHope.class, args);

		System.out.println(new AssessmentReport().getGenderList(2));
		System.out.println(new AssessmentReport().getGenderDistribution(2));
		System.out.println(new Report().getAnswerOccurenceCount((long)1, (long)2));
		System.out.println(Calendar.getInstance().get(Calendar.YEAR));
	}



}

