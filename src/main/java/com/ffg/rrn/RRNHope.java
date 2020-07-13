package com.ffg.rrn;

import com.ffg.rrn.dao.DemographicsDAO;
import com.ffg.rrn.report.AssessmentReport;
import com.ffg.rrn.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RRNHope {



	public static void main(String[] args) {
		SpringApplication.run(RRNHope.class, args);

		System.out.println(new AssessmentReport().getGenderDistribution(16));
		System.out.println(new AssessmentReport().getGenderList(16));
	}



}

