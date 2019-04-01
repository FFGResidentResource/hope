/**
 * 
 */
package com.ffg.rrn.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 *
 */
@JsonView
@Data
public class ResidentAssessmentQuestionnaire {

	private Integer raqId;	
	private Long residentId;
	private Integer questionNumber;	
	private Integer questionId;	
	private String question;	
	private Integer choiceId;
	private String lifeDomain;
	private Date onThisDate;
	
	@JsonView
	private List<Choice> choices;
	
	
	

}
