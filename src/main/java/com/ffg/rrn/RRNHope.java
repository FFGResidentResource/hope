package com.ffg.rrn;

import com.ffg.rrn.report.PerformanceReportBuilder;
import com.ffg.rrn.report.Report;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RRNHope {


	public static void main(String[] args) {
		SpringApplication.run(RRNHope.class, args);
		System.out.println(new PerformanceReportBuilder().getAllGenderData());
	}
}

