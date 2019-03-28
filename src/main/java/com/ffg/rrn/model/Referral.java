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
public class Referral {
	
	private int refId;
	private String refValue;

}
