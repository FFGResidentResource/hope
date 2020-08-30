/**
 * 
 */
package com.ffg.rrn.model;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGTeam
 *
 */
@Data
@JsonView
public class CategoryPercentage {

	private String category;
	private Integer percentage;
	
}
