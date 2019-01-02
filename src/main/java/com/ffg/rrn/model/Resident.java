/**
 * 
 */
package com.ffg.rrn.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author FFGRRNTeam
 * 
 * RESIDENT_ID      BIGINT PIMARY KEY NOT NULL,
 * FIRST_NAME       VARCHAR(20) NOT NULL,
 * MIDDLE        	VARCHAR(20),
 * LAST_NAME        VARCHAR(20) NOT NULL,
 * ADDRESS			VARCHAR(128),
 * CITY				VARCHAR(20),
 * STATE			VARCHAR(20),
 * ZIP_CODE			VARCHAR(20),
 * PHONE			VARCHAR(15),
 * MOBILE_PHONE		VARCHAR(15),
 * EMAIL			VARCHAR(128),
 * COMMUNITY_NAME	VARCHAR(30), 
 * LAST_UPDATED		TIMESTAMP NOT NULL DEFAULT NOW(),
 * LAST_UPDATED_BY	VARCHAR(50) 
**/ 

@Data
public class Resident {
	
	private Long residentId;	
	private String firstName;	
	private String middle;	
	private String lastName;	
	private String address;	
	private String city;	
	private String state;	
	private String zipCode;	
	private String phone;	
	private String mobilePhone;	
	private String email;
	private String communityName;	
	private Timestamp lastUpdated;	
	private String lastUpdatedBy;
	
	public Resident() {
		
	}

}
