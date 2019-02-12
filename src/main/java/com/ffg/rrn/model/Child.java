/**
 * 
 */
package com.ffg.rrn.model;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 * 
 * CHILD_ID			BIGINT PRIMARY KEY NOT NULL,
 * FULL_NAME		VARCHAR(50),	
 * PARENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID)
 *
 */
@Data
@JsonView
public class Child {

	private Long childId;
	private String fullName;
	private Long parentId;
}
