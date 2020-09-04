/**
 * 
 */
package com.ffg.rrn.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 *
 */
@Data
@JsonView
public class Dashboard {
	
	private List<String> yearList;

	private String year;

	private Integer quarter;

	private Integer currentQuarter;

	private List<Property> properties;
	
	private List<String> countyList;
	private List<String> cityList;
	private List<String> stateList;

	private Integer q1ServiceHours;
	private Integer q2ServiceHours;
	private Integer q3ServiceHours;
	private Integer q4ServiceHours;

	private Integer q1SignUpComplete;
	private Integer q2SignUpComplete;
	private Integer q3SignUpComplete;
	private Integer q4SignUpComplete;

	private Integer q1AssessmentComplete;
	private Integer q2AssessmentComplete;
	private Integer q3AssessmentComplete;
	private Integer q4AssessmentComplete;

	/* Referral REasons 1 to 14 */
	private Long rr1q1Count;
	private Long rr1q2Count;
	private Long rr1q3Count;
	private Long rr1q4Count;

	private Long rr2q1Count;
	private Long rr2q2Count;
	private Long rr2q3Count;
	private Long rr2q4Count;

	private Long rr3q1Count;
	private Long rr3q2Count;
	private Long rr3q3Count;
	private Long rr3q4Count;

	private Long rr4q1Count;
	private Long rr4q2Count;
	private Long rr4q3Count;
	private Long rr4q4Count;

	private Long rr5q1Count;
	private Long rr5q2Count;
	private Long rr5q3Count;
	private Long rr5q4Count;

	private Long rr6q1Count;
	private Long rr6q2Count;
	private Long rr6q3Count;
	private Long rr6q4Count;

	private Long rr7q1Count;
	private Long rr7q2Count;
	private Long rr7q3Count;
	private Long rr7q4Count;

	private Long rr8q1Count;
	private Long rr8q2Count;
	private Long rr8q3Count;
	private Long rr8q4Count;

	private Long rr9q1Count;
	private Long rr9q2Count;
	private Long rr9q3Count;
	private Long rr9q4Count;

	private Long rr10q1Count;
	private Long rr10q2Count;
	private Long rr10q3Count;
	private Long rr10q4Count;

	private Long rr11q1Count;
	private Long rr11q2Count;
	private Long rr11q3Count;
	private Long rr11q4Count;

	private Long rr12q1Count;
	private Long rr12q2Count;
	private Long rr12q3Count;
	private Long rr12q4Count;

	private Long rr13q1Count;
	private Long rr13q2Count;
	private Long rr13q3Count;
	private Long rr13q4Count;

	private Long rr14q1Count;
	private Long rr14q2Count;
	private Long rr14q3Count;
	private Long rr14q4Count;

	private Long housingQ1Count;
	private Long housingQ2Count;
	private Long housingQ3Count;
	private Long housingQ4Count;

	private Long mmQ1Count;
	private Long mmQ2Count;
	private Long mmQ3Count;
	private Long mmQ4Count;

	private Long empQ1Count;
	private Long empQ2Count;
	private Long empQ3Count;
	private Long empQ4Count;

	private Long eduQ1Count;
	private Long eduQ2Count;
	private Long eduQ3Count;
	private Long eduQ4Count;

	private Long nsQ1Count;
	private Long nsQ2Count;
	private Long nsQ3Count;
	private Long nsQ4Count;

	private Long hhQ1Count;
	private Long hhQ2Count;
	private Long hhQ3Count;
	private Long hhQ4Count;

	private Long residentServedQ1;
	private Long residentServedQ2;
	private Long residentServedQ3;
	private Long residentServedQ4;

	private Long totalActiveResidents;

	private Integer totalNoOfUnits;

	private Long totalNoOfResidents;

	private Long ongoingResidents;

	private Long newResidents;

}
