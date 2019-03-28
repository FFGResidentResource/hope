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

@Data
@JsonView
public class AssessmentType {
	
	private int aId;
	private String aValue;

}
