/**
 * 
 */
package com.ffg.rrn.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 *
 */
@Data
@JsonView
public class ResidentScoreGoal {

	private Long rsgId;
	private Long residentId;
	private String lifeDomain;
	private Integer score;
	private Integer goal;
	private Date onThisDate;

}
