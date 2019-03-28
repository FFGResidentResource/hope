/**
 * 
 */
package com.ffg.rrn.model;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 *
 */
@JsonView
@Data
public class AssessmentQuestionnaire {

	private Integer questionId;
	
	private Integer questionNumber;

	private String question;

	private String lifeDomain;

	private Integer sort;

}
