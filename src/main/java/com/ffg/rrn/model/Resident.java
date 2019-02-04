/**
 * 
 */
package com.ffg.rrn.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 * 
	RESIDENT_ID		BIGINT PRIMARY KEY NOT NULL,
	ACTIVE			BOOLEAN DEFAULT FALSE,
	FIRST_NAME		VARCHAR(20),
	MIDDLE			VARCHAR(20),
	LAST_NAME		VARCHAR(20),
	UNIT_ID			BIGINT REFERENCES UNIT(UNIT_ID),
	VOICEMAIL_NO	VARCHAR(20),
	TEXT_NO			VARCHAR(20),
	EMAIL			VARCHAR(128),
	ALLOW_CONTACT	BOOLEAN DEFAULT FALSE,
	WANTS_SURVEY	BOOLEAN DEFAULT FALSE,
	PHOTO_RELEASE	BOOLEAN DEFAULT FALSE,
	DATE_ADDED		TIMESTAMP DEFAULT NOW(),
	SERVICE_COORD	BIGINT REFERENCES SERVICE_COORDINATOR(SC_ID)
**/ 

@Data
@JsonView
public class Resident {
	
	private Long residentId;	
	private Boolean active;
	private String firstName;
	private String middle;
	private String lastName;
	private String unitName;
	private String voiceMail;
	private String text;
	private String email;
	private Boolean allowContact;
	private Boolean wantSurvey;
	private Boolean photoRelease;
	private Date dateAdded;
	private String serviceCoord;
	
	
	public Resident() {
		
	}


	public Resident(Long residentId, Boolean active, String firstName, String middle, String lastName, String unitName,
			String voiceMail, String text, String email, Boolean allowContact, Boolean wantSurvey, Boolean photoRelease,
			Date dateAdded, String serviceCoord) {
		super();
		this.residentId = residentId;
		this.active = active;
		this.firstName = firstName;
		this.middle = middle;
		this.lastName = lastName;
		this.unitName = unitName;
		this.voiceMail = voiceMail;
		this.text = text;
		this.email = email;
		this.allowContact = allowContact;
		this.wantSurvey = wantSurvey;
		this.photoRelease = photoRelease;
		this.dateAdded = dateAdded;
		this.serviceCoord = serviceCoord;
	}

}
