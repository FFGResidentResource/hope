package com.ffg.rrn;

import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.report.filter.Report;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class RRNHope {




	public static void main(String[] args) {
		SpringApplication.run(RRNHope.class, args);
		Report report = new Report();

		System.out.println(report.getAllDemographicObjects());
		System.out.println(report.filterByPropertyIdByQuestionId((long)1, (long)5));

	}



}

