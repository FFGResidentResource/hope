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

	private String year;

	private Integer quarter;

	private List<Property> properties;

	private Integer q1ServiceHours;
	private Integer q2ServiceHours;
	private Integer q3ServiceHours;
	private Integer q4ServiceHours;

	private Integer q1Units;
	private Integer q2Units;
	private Integer q3Units;
	private Integer q4Units;

	private Integer q1Residents;
	private Integer q2Residents;
	private Integer q3Residents;
	private Integer q4Residents;

	private Integer q1ResServed;
	private Integer q2ResServed;
	private Integer q3ResServed;
	private Integer q4ResServed;

	private Integer q1SignUpComplete;
	private Integer q2SignUpComplete;
	private Integer q3SignUpComplete;
	private Integer q4SignUpComplete;

	private Integer q1AssessmentComplete;
	private Integer q2AssessmentComplete;
	private Integer q3AssessmentComplete;
	private Integer q4AssessmentComplete;

	/* Referral REasons 1 to 14 */
	private Integer rr1q1Count;
	private Integer rr1q2Count;
	private Integer rr1q3Count;
	private Integer rr1q4Count;

	private Integer rr2q1Count;
	private Integer rr2q2Count;
	private Integer rr2q3Count;
	private Integer rr2q4Count;

	private Integer rr3q1Count;
	private Integer rr3q2Count;
	private Integer rr3q3Count;
	private Integer rr3q4Count;

	private Integer rr4q1Count;
	private Integer rr4q2Count;
	private Integer rr4q3Count;
	private Integer rr4q4Count;

	private Integer rr5q1Count;
	private Integer rr5q2Count;
	private Integer rr5q3Count;
	private Integer rr5q4Count;

	private Integer rr6q1Count;
	private Integer rr6q2Count;
	private Integer rr6q3Count;
	private Integer rr6q4Count;

	private Integer rr7q1Count;
	private Integer rr7q2Count;
	private Integer rr7q3Count;
	private Integer rr7q4Count;

	private Integer rr8q1Count;
	private Integer rr8q2Count;
	private Integer rr8q3Count;
	private Integer rr8q4Count;

	private Integer rr9q1Count;
	private Integer rr9q2Count;
	private Integer rr9q3Count;
	private Integer rr9q4Count;

	private Integer rr10q1Count;
	private Integer rr10q2Count;
	private Integer rr10q3Count;
	private Integer rr10q4Count;

	private Integer rr11q1Count;
	private Integer rr11q2Count;
	private Integer rr11q3Count;
	private Integer rr11q4Count;

	private Integer rr12q1Count;
	private Integer rr12q2Count;
	private Integer rr12q3Count;
	private Integer rr12q4Count;

	private Integer rr13q1Count;
	private Integer rr13q2Count;
	private Integer rr13q3Count;
	private Integer rr13q4Count;

	private Integer rr14q1Count;
	private Integer rr14q2Count;
	private Integer rr14q3Count;
	private Integer rr14q4Count;

	private Integer totalActiveResident;

}
