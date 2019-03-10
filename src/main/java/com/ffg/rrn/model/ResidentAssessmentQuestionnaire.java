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
@JsonView
@Data
public class ResidentAssessmentQuestionnaire {

	private Integer raqId;
	
	private Long residentId;
	
	private Integer questionNumber;
	
	private String question;
	
	private Integer choiceId;

	private String lifeDomain;
	
	private List<Choice> choices;
	

}
