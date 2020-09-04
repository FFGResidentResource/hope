/**
 * 
 */
package com.ffg.rrn.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGTEam
 *
 */
@Data
@JsonView
public class AssessmentByDateAndScoreGoal {
	
	private List<ResidentAssessmentQuestionnaire> raqs;
	
	private ResidentScoreGoal rsg;

}
