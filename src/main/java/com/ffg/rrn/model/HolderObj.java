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
public class HolderObj {

	private String year;
	private String selectedProperties;
	
}
