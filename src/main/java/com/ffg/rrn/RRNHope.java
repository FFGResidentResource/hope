package com.ffg.rrn;

import com.ffg.rrn.report.AssessmentReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication
public class RRNHope {



	public static void main(String[] args) {
		SpringApplication.run(RRNHope.class, args);

		System.out.println(new AssessmentReport().genderData((long)1));

		System.out.println(new AssessmentReport().veteranData((long)1));

		System.out.println(new AssessmentReport().raceData((long)1));
	}



}

