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
public class Property {
	
	private int propertyId;
	private String propertyName;
	private int unit;
	private int unitFee;
	private Boolean active;

}
