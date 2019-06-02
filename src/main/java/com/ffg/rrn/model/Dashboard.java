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


}
